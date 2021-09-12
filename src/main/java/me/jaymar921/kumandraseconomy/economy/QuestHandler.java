package me.jaymar921.kumandraseconomy.economy;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.QuestsUtilities.*;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class QuestHandler {

    //Quest List
    QuestList questList;
    //Entity Map with Bucket Task
    Map<Entity, BukkitTask> questEntity;

    KumandrasEconomy main;
    public QuestHandler(KumandrasEconomy main){
        this.main = main;
        questList = new QuestList();
        questEntity = new HashMap<>();
        //start Runnable
        QuestRunnable();
        QuestEntityRemove();
    }

    private void QuestRunnable(){
        new BukkitRunnable(){
            public void run(){
                if(!questList.isEmpty()) {
                    for(PlayerQuest quest : questList){
                        //check if quest expires
                        checkQuestExpiry(quest);
                        //check if quest completed
                        checkQuestSuccess(quest);
                    }
                }
            }
        }.runTaskTimer(main, 10, 20);
    }

    private void QuestEntityRemove(){
        new BukkitRunnable(){
            public void run(){
                flush();
            }
        }.runTaskTimer(main, 20*59, 20*60*main.getRegistryConfiguration().QuestTime);
    }

    public void flush(){
        if(questEntity.size()>0)
            for(Entity entity : questEntity.keySet()) {
                removeQuestEntity(entity);
            }
    }

    public void addQuest(Player player, PlayerQuest playerQuest){
        Quest().createQuest(player,playerQuest);
    }

    private GenerateQuest Quest(){
        return (p, q) -> {
            if(questList.addQuest(q)) {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "You accepted the Quest " + ChatColor.GREEN
                        + ">> " + ChatColor.YELLOW + "" + ChatColor.BOLD + q.getQuestTitle());
                for(String msg : q.getQuestMessage())
                    p.sendMessage(ChatColor.GREEN+msg);
                p.sendMessage(ChatColor.GRAY + "[for feeding quest, only mobs with heart particles are counted]");
                main.getLogger().info(ChatColor.YELLOW + "Quest on going [" + q.getQuestTitle() + "]"+ChatColor.RED+" "+questList.size()+" quest/s pending");
            }else
                main.getLogger().info(ChatColor.RED+"Could not add quest ["+q.getQuestTitle()+"]");
        };
    }

    private QuestComplete QuestComplete(){
        return (p) -> {
            PlayerQuest quest = questList.getQuest(p);
            if((questList.size()-1)>0)
                main.getLogger().info(ChatColor.YELLOW + "Completed Quest [" + quest.getQuestTitle() + "]"+ChatColor.RED+" "+(questList.size()-1)+" quest/s pending");
            else
                main.getLogger().info(ChatColor.YELLOW + "Completed Quest [" + quest.getQuestTitle() + "]"+ChatColor.GREEN+" "+(questList.size()-1)+" quest pending");
            p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD +"Quest Complete: "+ChatColor.YELLOW+""+ChatColor.BOLD+quest.getQuestTitle());
            main.getQuestEvent().getRecentQuest().add(p);
            //get reward
            RewardType reward = quest.getQuestRewards().getRewardType();
            switch (reward.getId()){
                case 0:
                    int exp = (int) quest.getQuestRewards().getReward();
                    p.sendMessage(ChatColor.LIGHT_PURPLE+"Reward: "+ChatColor.YELLOW+""+exp+" exp");
                    p.giveExp(exp);
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,2.0f,2.0f);
                    break;
                case 1:
                    int money = (int) quest.getQuestRewards().getReward();
                    p.sendMessage(ChatColor.LIGHT_PURPLE+"Reward: "+ChatColor.YELLOW+""+money+main.getRegistryConfiguration().currency_prefix);
                    double balance = main.getDataHandler().getStatusHolder().get(p.getUniqueId().toString()).getBalance();
                    main.getDataHandler().getStatusHolder().get(p.getUniqueId().toString()).setBalance(balance+money);
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,2.0f,2.0f);
                    break;
                case 2:
                    ItemStack item = (ItemStack) quest.getQuestRewards().getReward();
                    if(p.getInventory().firstEmpty() == -1)
                        p.getWorld().dropItemNaturally(p.getLocation(),item);
                    else
                        p.getInventory().addItem(item);
                    if(item.getItemMeta()!=null) {
                        if (item.getItemMeta().getDisplayName().length() > 0)
                            p.sendMessage(ChatColor.LIGHT_PURPLE + "Reward: " + ChatColor.YELLOW + "" + item.getItemMeta().getDisplayName());
                    }else
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "Reward: " + ChatColor.YELLOW + "" + item.getType().toString());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,2.0f,2.0f);
            }
            return quest;
        };
    }

    private FailedQuest failedQuestExpired(){
        return (q) -> {
            for(Player players : Bukkit.getServer().getOnlinePlayers())
                if(q.getPlayerUuid().equals(players.getUniqueId().toString()))
                    players.sendMessage(ChatColor.RED+"Your quest "+ChatColor.YELLOW+""+ChatColor.BOLD+q.getQuestTitle()+ChatColor.RED+" has expired!");
            questList.removeQuest(q);
            if(questList.size()>0)
                main.getLogger().info(ChatColor.AQUA + "Failed Quest [" + q.getQuestTitle() + "]"+ChatColor.RED+" "+questList.size()+" quest/s pending");
            else
                main.getLogger().info(ChatColor.AQUA + "Failed Quest [" + q.getQuestTitle() + "]"+ChatColor.GREEN+" "+questList.size()+" quest pending");

        };
    }

    private void checkQuestExpiry(PlayerQuest quest){
        if(quest.getQuestDuration()<=System.currentTimeMillis())
            failedQuestExpired().Failed(quest);
        if((quest.getQuestDuration()-System.currentTimeMillis())/1000 == 10 && (quest.getQuestDuration()-System.currentTimeMillis())/1000 > 9)
            for(Player player : Bukkit.getServer().getOnlinePlayers())
                if(quest.getPlayerUuid().equals(player.getUniqueId().toString()))
                    player.sendMessage(ChatColor.RED+"Your quest "+ChatColor.YELLOW+""+ChatColor.BOLD+quest.getQuestTitle()+ChatColor.RED+" is about to end in 10s");
    }

    private void checkQuestSuccess(PlayerQuest quest){
        //if count == 0
        if(quest.getQuestCount() <= 0)
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(quest.getPlayerUuid().contains(player.getUniqueId().toString()))
                    questList.removeQuest(QuestComplete().completedQuest(player));
            }
    }

    public QuestList getQuestList(){
        return questList;
    }

    public Map<Entity, BukkitTask> getQuestEntity() {
        return questEntity;
    }
    public void removeQuestEntity(Entity entity){
        if(questEntity.containsKey(entity)){
            NamespacedKey namespacedKey = new NamespacedKey(main,"QuestEntity");
            entity.getPersistentDataContainer().remove(namespacedKey);
            entity.setCustomName("");
            questEntity.get(entity).cancel();
            questEntity.remove(entity);
        }
    }

    public void addQuestEntity(Entity entity){
        //adding Persistent data to the entity
        NamespacedKey namespacedKey = new NamespacedKey(main,"QuestEntity");
        entity.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, "ID"+new Random().nextInt());
        entity.setCustomName(ChatColor.GREEN+""+ChatColor.BOLD+">>"+ChatColor.YELLOW+""+ChatColor.BOLD+"Quest Request"+ChatColor.GREEN+""+ChatColor.BOLD+"<<");
        questEntity.put(entity, new BukkitRunnable(){
            public void run(){
                if(!entity.isValid() || entity.isDead())
                    cancel();
                Location location = entity.getLocation();
                location.setY(entity.getLocation().getY()+1);
                location.getWorld().spawnParticle(Particle.TOTEM, location, 15);
            }
        }.runTaskTimer(main, 10, 40));
    }
}
