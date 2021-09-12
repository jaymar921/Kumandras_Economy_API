package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.entity.EntityTypeCheck;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Jobs {

    static KumandrasEconomy main;
    static Map<Player, Double> amount;
    static Map<Player, Integer> counter;
    static Map<Player, Integer>lumberStat;
    static Map<Player, Integer> plantingStat;
    static List<Location> blockedLocation;
    static List<Player> villagerInRadius;
    public EntityTypeCheck mob;
    static final DecimalFormat fmt = new DecimalFormat("#,###,###.##");

    public Jobs(KumandrasEconomy main){
        Jobs.main = main;
        amount = new ConcurrentHashMap<>();
        counter = new ConcurrentHashMap<>();
        lumberStat =  new ConcurrentHashMap<>();
        plantingStat =  new ConcurrentHashMap<>();
        blockedLocation = new Vector<>();
        villagerInRadius = new Vector<>();
        mob = new EntityTypeCheck();
        incomeCounterRunnable();
        blockedLocationCounterRunnable();
        villagerRadiusRunnable();
    }

    private static void incomeCounterRunnable(){
        new BukkitRunnable(){
            public void run(){
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    playerIncomeStatus(player);
                    LumberStat(player);
                    PlantingStat(player);
                }
            }
        }.runTaskTimer(main,10,2);
    }
    private static void blockedLocationCounterRunnable(){
        new BukkitRunnable(){
            public void run(){
                blockedLocation.clear();
            }
        }.runTaskTimer(main,10,6000);
    }

    private static void villagerRadiusRunnable(){
        new BukkitRunnable(){
            public void run(){
                villagerInRadius.clear();
            }
        }.runTaskTimer(main, 20, 20*5L);
    }

    protected static void LumberStat(Player player){
        if(lumberStat.containsKey(player)){
            int stat = lumberStat.get(player);
            if(stat-- <=0)
                lumberStat.remove(player);
            lumberStat.replace(player,stat);
        }
    }

    protected static void PlantingStat(Player player){
        if(plantingStat.containsKey(player)){
            int stat = plantingStat.get(player);
            if(stat-- <=0)
                plantingStat.remove(player);
            plantingStat.replace(player,stat);
        }
    }

    protected static void playerIncomeStatus(Player player){
        if(counter.containsKey(player)){
            int count = counter.get(player);
            if(amount.get(player)>0)
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN+"+"+ChatColor.YELLOW+fmt.format(amount.get(player))+main.getRegistryConfiguration().currency_prefix));
            else
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED+fmt.format(amount.get(player))+main.getRegistryConfiguration().currency_prefix));

            if(count-- <= 0){
                amount.remove(player);
                counter.remove(player);
            }
            counter.replace(player,count);
        }
    }

    protected synchronized void addPlayer(Player player, double income){
        if(amount.containsKey(player)){
            double previous = amount.get(player);
            amount.replace(player, previous+income);
        }else{
            amount.put(player, income);
        }
        counter.put(player, 120);
    }
    protected synchronized void LumberListPlayer(Player player){
        lumberStat.put(player, 50);
    }

    protected synchronized void PlantingListPlayer(Player player){
        plantingStat.put(player, 50);
    }

    protected synchronized void blockLocation(Location location){
        blockedLocation.add(location);
    }

    protected synchronized void playerInVillagerRadius(Player player){
        villagerInRadius.add(player);
    }
}
