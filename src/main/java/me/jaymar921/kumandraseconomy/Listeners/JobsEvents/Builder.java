package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Builder extends Jobs implements Listener {

    public Builder(KumandrasEconomy main){
        super(main);
    }

    @EventHandler
    private void builder(BlockPlaceEvent event){
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(event.getPlayer().getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.BUILDER.toString()))return;

        if(main.getRegistryConfiguration().consideredBuilding.contains(event.getBlock().getType()) || main.getRegistryConfiguration().consideredBuilding.isEmpty()){
            double balance = playerStatus.getBalance();
            double income = main.getRegistryConfiguration().builder;
            playerStatus.setBalance(balance+income);
            addPlayer(event.getPlayer(), income);
            blockLocation(event.getBlock().getLocation());
        }
    }

    @EventHandler
    private void builderBreak(BlockBreakEvent event){
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(event.getPlayer().getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.BUILDER.toString()))return;

        if(main.getRegistryConfiguration().consideredBuilding.contains(event.getBlock().getType()) || main.getRegistryConfiguration().consideredBuilding.isEmpty()){
            if(blockedLocation.contains(event.getBlock().getLocation())) {
                double balance = playerStatus.getBalance();
                double income = -main.getRegistryConfiguration().builder;
                playerStatus.setBalance(balance + income);
                addPlayer(event.getPlayer(), income);
                blockLocation(event.getBlock().getLocation());
                blockedLocation.remove(event.getBlock().getLocation());
            }
        }
    }
}
