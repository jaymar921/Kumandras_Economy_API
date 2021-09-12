package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Lumberjack extends Jobs implements Listener {

    public Lumberjack(KumandrasEconomy main){
        super(main);
    }

    @EventHandler
    private void cutLogs(BlockBreakEvent event){
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(event.getPlayer().getUniqueId().toString());

        if(playerStatus.getJobs().contains(JobList.LUMBERJACK.toString())){
            if(isLog(event.getBlock().getType()) && !blockedLocation.contains(event.getBlock().getLocation())){
                if(lumberStat.containsKey(event.getPlayer()))
                    return;
                double balance = playerStatus.getBalance();
                double income = main.getRegistryConfiguration().breakingLogs;
                playerStatus.setBalance(balance+income);
                addPlayer(event.getPlayer(), income);
                blockLocation(event.getBlock().getLocation());
            }else if(isSapling(event.getBlock().getType())){
                if(!plantingStat.containsKey(event.getPlayer())) {
                    blockLocation(event.getBlock().getLocation());
                    PlantingListPlayer(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    private void placingTrees(BlockPlaceEvent event){
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(event.getPlayer().getUniqueId().toString());

        if(playerStatus.getJobs().contains(JobList.LUMBERJACK.toString())){
            if(isLog(event.getBlock().getType())){
                if(!lumberStat.containsKey(event.getPlayer())) {
                    blockLocation(event.getBlock().getLocation());
                    LumberListPlayer(event.getPlayer());
                }
            }else if(isSapling(event.getBlock().getType()) && !blockedLocation.contains(event.getBlock().getLocation())){
                if(plantingStat.containsKey(event.getPlayer()))
                    return;
                double balance = playerStatus.getBalance();
                double income = main.getRegistryConfiguration().plantingTrees;
                playerStatus.setBalance(balance+income);
                addPlayer(event.getPlayer(), income);
                blockLocation(event.getBlock().getLocation());
            }
        }
    }



    private boolean isLog(Material material){
        if(material.equals(Material.ACACIA_LOG))
            return true;
        else if(material.equals(Material.BIRCH_LOG))
            return true;
        else if(material.equals(Material.OAK_LOG))
            return true;
        else if(material.equals(Material.DARK_OAK_LOG))
            return true;
        else if(material.equals(Material.JUNGLE_LOG))
            return true;
        else if(material.equals(Material.SPRUCE_LOG))
            return true;
        else if(material.equals(Material.STRIPPED_ACACIA_LOG))
            return true;
        else if(material.equals(Material.STRIPPED_DARK_OAK_LOG))
            return true;
        else if(material.equals(Material.STRIPPED_BIRCH_LOG))
            return true;
        else if(material.equals(Material.STRIPPED_OAK_LOG))
            return true;
        else if(material.equals(Material.STRIPPED_JUNGLE_LOG))
            return true;
        else if(material.equals(Material.STRIPPED_SPRUCE_LOG))
            return true;

        if(main.getVersion().support_1_16()){
            if(material.equals(Material.WARPED_STEM))return true;
            else if(material.equals(Material.CRIMSON_STEM))return true;
        }
        return false;
    }
    private boolean isSapling(Material material){
        if(material.equals(Material.OAK_SAPLING))
            return true;
        else if(material.equals(Material.SPRUCE_SAPLING))
            return true;
        else if(material.equals(Material.BIRCH_SAPLING))
            return true;
        else if(material.equals(Material.JUNGLE_SAPLING))
            return true;
        else if(material.equals(Material.ACACIA_SAPLING))
            return true;
        else return material.equals(Material.DARK_OAK_SAPLING);
    }
}
