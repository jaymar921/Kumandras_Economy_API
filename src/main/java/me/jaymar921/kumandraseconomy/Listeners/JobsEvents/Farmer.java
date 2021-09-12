package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;


public class Farmer extends Jobs implements Listener {

    public Farmer(KumandrasEconomy main){
        super(main);
    }


    @EventHandler
    private void breakingCrops(BlockBreakEvent event){
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(event.getPlayer().getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.FARMER.toString()))
            return;
        if(isCrop(event.getBlock())) {
            double balance = playerStatus.getBalance();
            double income = main.getRegistryConfiguration().cropHarvesting;
            playerStatus.setBalance(balance+income);
            addPlayer(event.getPlayer(), income);
        }
    }

    @EventHandler
    private void breedingEvent(EntityBreedEvent event){
        if(!(event.getBreeder() instanceof Player))
            return;
        Player player = (Player) event.getBreeder();
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.FARMER.toString()))
            return;
        double balance = playerStatus.getBalance();
        double income = main.getRegistryConfiguration().breedingAnimals;
        playerStatus.setBalance(balance+income);
        addPlayer(player,income);
    }









    protected boolean isCrop(Block block){
        if(!(block.getBlockData() instanceof Ageable))
            return false;
        Ageable ageable = (Ageable) block.getBlockData();
        return ageable.getAge() == ageable.getMaximumAge();
    }
}
