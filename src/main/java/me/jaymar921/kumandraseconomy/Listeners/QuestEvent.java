package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.QuestData;
import me.jaymar921.kumandraseconomy.datahandlers.QuestDataHandler;
import me.jaymar921.kumandraseconomy.economy.QuestHandler;
import me.jaymar921.kumandraseconomy.economy.QuestsUtilities.PlayerQuest;
import me.jaymar921.kumandraseconomy.economy.QuestsUtilities.QuestRewards;
import me.jaymar921.kumandraseconomy.economy.QuestsUtilities.QuestType;
import me.jaymar921.kumandraseconomy.economy.QuestsUtilities.RewardType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class QuestEvent implements Listener {
    KumandrasEconomy main;

    QuestDataHandler questDataHandler;
    QuestHandler questHandler;
    Map<Player, QuestData> pendingQuest;
    Map<Player, Entity> pendingEntity;
    List<Player> recentQuest;
    List<Animals> animals;
    List<Block> listedPlaced;
    public QuestEvent(KumandrasEconomy main){
        this.main = main;
        pendingQuest = new HashMap<>();
        pendingEntity = new HashMap<>();
        questDataHandler =  new QuestDataHandler(main);
        questHandler = new QuestHandler(main);
        animals = new Vector<>();
        recentQuest = new Vector<>();
        listedPlaced = new Vector<>();
        ClearDataRunnable();
        clearRecentQuest();
        CreateQuestRunnable();
        craftQuest();
    }

    public void clearRecentQuest(){
        new BukkitRunnable(){
            public void run(){
                recentQuest.clear();
            }
        }.runTaskTimer(main, 20*61, 20*60*(main.getRegistryConfiguration().QuestTime*2));
    }

    private void CreateQuestRunnable(){
        if(!main.getRegistryConfiguration().QuestAllowed) {
            main.getLogger().info(ChatColor.DARK_AQUA + "[Disabling Quests :>]");
            return;
        }
        main.getLogger().info(ChatColor.DARK_AQUA + "[Enabling Quests :>]");
        new BukkitRunnable(){
            public void run(){
                flush();
                pendingQuest.clear();
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    if(recentQuest.contains(player))
                        continue;
                    if(questHandler.getQuestList().getQuest(player)==null)
                        getEntity(player);
                }
            }
        }.runTaskTimer(main, 20*60, 20*60*main.getRegistryConfiguration().QuestTime);
    }

    private void ClearDataRunnable(){
        new BukkitRunnable(){
            public void run(){
                listedPlaced.clear();
                for(Animals an : animals){
                    NamespacedKey ns = new NamespacedKey(main,"AnimalData");
                    if(an.getPersistentDataContainer().has(ns,PersistentDataType.STRING))
                        an.getPersistentDataContainer().remove(ns);
                }
            }
        }.runTaskTimer(main,10,20*300);
    }

    public void flush(){
        for(Animals an : animals){
            NamespacedKey ns = new NamespacedKey(main,"AnimalData");
            if(an.getPersistentDataContainer().has(ns,PersistentDataType.STRING))
                an.getPersistentDataContainer().remove(ns);
        }
        questHandler.flush();
    }

    @EventHandler
    private void rightClickEntity(PlayerInteractEntityEvent event){
        if(event.getRightClicked() instanceof Villager || event.getRightClicked() instanceof Animals){

            if(questHandler.getQuestEntity().containsKey(event.getRightClicked())){
                if(!pendingQuest.containsKey(event.getPlayer())){
                    event.getPlayer().sendMessage(ChatColor.RED+"Sorry but this quest not for you");
                    return;
                }
                ShowQuest(pendingQuest.get(event.getPlayer()), event.getPlayer());
                pendingEntity.put(event.getPlayer(), event.getRightClicked());
                event.setCancelled(true);
            }
        }
    }

    public void ShowQuest(QuestData questData, Player player){

        String reward = "";
        if(questData.getRewardType().contains("ITEM")) {
            if (questData.getItemReward().hasItemMeta()) {
                if (questData.getItemReward().getItemMeta() != null)
                    if (questData.getItemReward().getItemMeta().getDisplayName().length() > 0)
                        reward = questData.getItemReward().getItemMeta().getDisplayName();
            } else {
                reward = questData.getItemReward().getType().toString();
            }
        }else if(questData.getRewardType().contains("EXP")){
            reward = questData.getExpReward()+" exp";
        }else if(questData.getRewardType().contains("MONEY")){
            reward = questData.getExpReward()+main.getRegistryConfiguration().currency_prefix;
        }
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        assert meta != null;

        StringBuilder sb = new StringBuilder();
        for(String msg : questData.getMessage())
            sb.append(ChatColor.DARK_GRAY).append(ChatColor.BOLD).append(msg).append("\n");

        BaseComponent[] page = new ComponentBuilder(""+ChatColor.RED+""+ChatColor.BOLD+""+ChatColor.UNDERLINE+"Start Quest?")
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kumandra questAccept"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_GREEN+""+ChatColor.BOLD+"Accept Quest!").create()))
                .create();
        BaseComponent[] event = new ComponentBuilder(ChatColor.DARK_PURPLE +"➼"+ChatColor.BOLD+questData.getTitle()+"\n"+
                ChatColor.DARK_GREEN+"••"+questData.getType()+" QUEST••\n\n"+
                ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Duration: "+ChatColor.DARK_BLUE+""+ChatColor.BOLD+getFormattedTime(questData.getDuration())+" \n"+
                ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Reward: "+ChatColor.DARK_BLUE+""+ChatColor.BOLD + reward+"\n\n"+
                ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Task: \n"+sb.toString())
                .append("\n").append(page).create();

        meta.setTitle(ChatColor.YELLOW +""+ChatColor.BOLD+questData.getTitle());
        meta.setAuthor(ChatColor.GREEN+questData.getType().toLowerCase()+" quest");
        /*
        meta.addPage(
                ChatColor.DARK_PURPLE +"➼"+ChatColor.BOLD+questData.getTitle()+"\n\n"+
                        ChatColor.DARK_GREEN+"•"+questData.getType()+" QUEST\n"+
                        ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Task: "+ChatColor.DARK_BLUE+""+ChatColor.BOLD+questData.getTask()+"\n"+
                        ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Duration: "+ChatColor.DARK_BLUE+""+ChatColor.BOLD+questData.getDuration()+" s\n"+
                        ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Reward: "+ChatColor.DARK_BLUE+""+ChatColor.BOLD + reward+"\n\n"+sb.toString()
        );*/
        meta.spigot().addPage(event);
        pendingQuest.put(player, questData);
        book.setItemMeta(meta);
        player.openBook(book);
    }


    public PlayerQuest generateQuest(QuestData questData, String uuid){
        QuestType questType = null;
        if(questData.getType().contains("ANIMAL"))
            questType = QuestType.ANIMAL;
        else if(questData.getType().contains("VILLAGER"))
            questType = QuestType.VILLAGER;

        PlayerQuest QUEST = new PlayerQuest(uuid, questType, questData.getTitle(), questData.getTask(), questData.getCount());

        //REWARD
        QuestRewards reward = null;
        if(questData.getRewardType().toLowerCase().contains("exp")) {
            reward = new QuestRewards(RewardType.EXP);
            reward.setReward(questData.getExpReward());
        }else if(questData.getRewardType().toLowerCase().contains("item")) {
            reward = new QuestRewards(RewardType.ITEM);
            reward.setReward(questData.getItemReward());
        }else if(questData.getRewardType().toLowerCase().contains("money")) {
            reward = new QuestRewards(RewardType.MONEY);
            reward.setReward(questData.getExpReward());
        }

        QUEST.setQuestMessage(questData.getMessage());
        QUEST.setQuestRewards(reward);
        QUEST.setQuestDuration(System.currentTimeMillis()+(long)(questData.getDuration()* 1000L));

        return QUEST;
    }

    @EventHandler
    private void kilLQuestEvent(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null)
            return;
        Player player = event.getEntity().getKiller();

        if(questHandler.getQuestList().getQuest(player)==null)
            return;
        PlayerQuest quest = questHandler.getQuestList().getQuest(player);
        if(quest.getQuestTask().contains("KILL")){
            String task = quest.getQuestTask().split("_")[1];
            if(event.getEntity().getType().toString().contains(task)){
                if(quest.getQuestCount()==0)
                    return;
                quest.setQuestCount(quest.getQuestCount()-1);
                if(quest.getQuestCount()>1)
                    player.sendMessage(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+event.getEntity().getType()+"S"+ChatColor.GREEN+" to kill "+ChatColor.GOLD+">> "+quest.getQuestCount());
                else if(quest.getQuestCount()>0)
                    player.sendMessage(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+event.getEntity().getType()+ChatColor.GREEN+" to kill "+ChatColor.GOLD+">> "+quest.getQuestCount());
                else
                    player.sendMessage(ChatColor.GREEN+"You killed the last "+ChatColor.LIGHT_PURPLE+event.getEntity().getType());
                event.setDroppedExp(0);
                event.getDrops().clear();

            }
        }
    }

    @EventHandler
    private void mineQuest(BlockBreakEvent event){
        Player player = event.getPlayer();
        PlayerQuest quest = questHandler.getQuestList().getQuest(player);
        if(listedPlaced.contains(event.getBlock())) {
            event.setCancelled(true);
            return;
        }
        if(quest==null)
            return;
        if(!quest.getQuestTask().toLowerCase().contains("mine"))
            return;
        String task = quest.getQuestTask().split("_")[1];
        if(quest.getQuestTask().split("_").length==3)
            task += "_"+quest.getQuestTask().split("_")[2];
        if(event.getBlock().getType().toString().toLowerCase().contains(task.toLowerCase())){
            if(quest.getQuestCount()==0)
                return;
            quest.setQuestCount(quest.getQuestCount()-1);
            if(event.getBlock().getType().toString().contains("LOG")){
                if(quest.getQuestCount()>1)
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+"S"+ChatColor.GREEN+" to chop "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                else if(quest.getQuestCount()>0)
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+ChatColor.GREEN+" to chop "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                else
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"You chopped the last "+ChatColor.LIGHT_PURPLE+task));
            }else{
                if(quest.getQuestCount()>1)
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+"S"+ChatColor.GREEN+" to mine "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                else if(quest.getQuestCount()>0)
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+ChatColor.GREEN+" to mine "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                else
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"You mined the last "+ChatColor.LIGHT_PURPLE+task));
            }
            event.setExpToDrop(0);
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void placeQuest(BlockPlaceEvent event){
        Player player = event.getPlayer();
        PlayerQuest quest = questHandler.getQuestList().getQuest(player);
        if(quest==null)
            return;
        if(!quest.getQuestTask().toLowerCase().contains("place"))
            return;
        String task = quest.getQuestTask().split("_")[1];
        if(quest.getQuestTask().split("_").length==3)
            task += "_"+quest.getQuestTask().split("_")[2];
        if(event.getBlock().getType().toString().toLowerCase().contains(task.toLowerCase())){
            if(quest.getQuestCount()==0)
                return;
            quest.setQuestCount(quest.getQuestCount()-1);
            if(quest.getQuestCount()>1)
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+"S"+ChatColor.GREEN+" to place "+ChatColor.GOLD+">> "+quest.getQuestCount()));
            else if(quest.getQuestCount()>0)
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+ChatColor.GREEN+" to place "+ChatColor.GOLD+">> "+quest.getQuestCount()));
            else
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"You placed the last "+ChatColor.LIGHT_PURPLE+task));
            listedPlaced.add(event.getBlock());
            listedPlaced.add(event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().subtract(0,1,0)));
        }
    }


    private void craftQuest(){
        new BukkitRunnable(){
            public void run(){
                for(PlayerQuest quest : questHandler.getQuestList()){
                    if(quest.getQuestTask().contains("CRAFT")){
                        for(Player player : Bukkit.getServer().getOnlinePlayers()){
                            if(quest.getPlayerUuid().equals(player.getUniqueId().toString())){
                                String task = quest.getQuestTask().split("_")[1];
                                if(quest.getQuestTask().split("_").length==3)
                                    task+= "_"+quest.getQuestTask().split("_")[2];
                                boolean contain_item = false;
                                for(int i = 0; i < player.getInventory().getSize(); i++){
                                    ItemStack itemStack = player.getInventory().getItem(i);
                                    if(itemStack == null)
                                        continue;
                                    if(itemStack.getItemMeta()!=null)
                                        if(itemStack.getItemMeta().hasEnchants())
                                            continue;
                                    if(itemStack.getType().toString().contains(task)){
                                        int item_count = itemStack.getAmount();
                                        if(item_count> quest.getQuestCount()){
                                            itemStack.setAmount(item_count- quest.getQuestCount());
                                            quest.setQuestCount(0);
                                        }else{
                                            player.getInventory().setItem(i, new ItemStack(Material.AIR));
                                            quest.setQuestCount(quest.getQuestCount()-item_count);
                                        }
                                        contain_item = true;
                                    }
                                }

                                if(contain_item){
                                    if(quest.getQuestCount()>1)
                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+"S"+ChatColor.GREEN+" to obtain "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                                    else if(quest.getQuestCount()>0)
                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+ChatColor.GREEN+" to obtain "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                                    else
                                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"You obtained the last "+ChatColor.LIGHT_PURPLE+task));
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(main, 20,20);
    }

    @EventHandler
    private void FeedQuest(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();

        if(questHandler.getQuestList().getQuest(player)==null)
            return;

        PlayerQuest quest = questHandler.getQuestList().getQuest(player);

        if(!(event.getRightClicked() instanceof Animals))
            return;
        Animals animal = (Animals) event.getRightClicked();

        NamespacedKey ns = new NamespacedKey(main,"AnimalData");
        animal.getPersistentDataContainer().set(ns, PersistentDataType.STRING, "animal"+new Random());
        if(animals.contains(animal)) {
            player.sendMessage(ChatColor.DARK_GRAY+"You already fed this animal");
            return;
        }
        new BukkitRunnable(){
            public void run(){
                if(animal.isLoveMode())
                    if(quest.getQuestTask().contains("FEED")){
                        String task = quest.getQuestTask().split("_")[1];
                        if(animal.getType().toString().contains(task)){
                            quest.setQuestCount(quest.getQuestCount()-1);
                            if(quest.getQuestCount()>1)
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+"S"+ChatColor.GREEN+" to feed "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                            else if(quest.getQuestCount()>0)
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"Remaining "+ChatColor.LIGHT_PURPLE+task+ChatColor.GREEN+" to feed "+ChatColor.GOLD+">> "+quest.getQuestCount()));
                            else
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,TextComponent.fromLegacyText(ChatColor.GREEN+"You fed the last "+ChatColor.LIGHT_PURPLE+task));

                        animals.add(animal);
                        }
                    }
            }
        }.runTaskLater(main, 10);
    }


    public QuestHandler getQuestHandler(){
        return questHandler;
    }

    public Map<Player, QuestData> getPendingQuest() {
        return pendingQuest;
    }

    private void getEntity(Player player){
        List<Entity> entities = (List<Entity>) player.getWorld().getNearbyEntities(player.getLocation(), 30,20,30);
        for(Entity entity : entities){

            if(entity instanceof Villager || entity instanceof Animals){

                if(entity instanceof Villager && !questDataHandler.hasVillagerQuest())
                    continue;
                if(entity instanceof Animals && !questDataHandler.hasAnimalQuest())
                    continue;
                if(entity.getCustomName()!=null)
                    if(entity.getCustomName().length()>0)
                        continue;
                if(Math.random() <= main.getRegistryConfiguration().QuestChance) {
                    questHandler.addQuestEntity(entity);
                    player.sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"A Quest Entity has appeared near you");
                    player.sendMessage(ChatColor.GRAY+""+ChatColor.UNDERLINE+"you have "+main.getRegistryConfiguration().QuestTime+" minutes before it disappear");
                    main.getLogger().info(ChatColor.GREEN+"Quest Entity Appeared at ["+ChatColor.AQUA+entity.getLocation().getBlockX()+","+entity.getLocation().getBlockY()+","+entity.getLocation().getBlockZ()+ChatColor.GREEN+"]");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,2.0f,2.0f);
                    List<QuestData> data = questDataHandler.getQuestData();
                    List<QuestData> questDataList = new Vector<>();
                    for(QuestData questData : data){
                        if(entity instanceof Villager) {
                            if (questData.getType().toLowerCase().contains("villager"))
                                questDataList.add(questData);
                        }else {
                            if (questData.getType().toLowerCase().contains("animal"))
                                questDataList.add(questData);
                        }
                    }

                    pendingQuest.put(player,getQuestData(questDataList));
                    break;
                }
            }
        }
    }

    public String getFormattedTime(int duration){
        int mins = duration/60;
        int secs = duration - (mins*60);
        return mins+"m "+secs+"s";
    }

    public QuestData getQuestData(List<QuestData> DATA){
        return DATA.get(new Random().nextInt(DATA.size()));
    }

    public Map<Player, Entity> getPendingEntity() {
        return pendingEntity;
    }

    public List<Player> getRecentQuest() {
        return recentQuest;
    }
}
