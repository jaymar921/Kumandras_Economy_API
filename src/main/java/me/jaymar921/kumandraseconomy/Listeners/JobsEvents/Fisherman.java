package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class Fisherman extends Jobs implements Listener {

    public Fisherman(KumandrasEconomy main){
        super(main);
    }

    @EventHandler
    private void FishingEvent(PlayerFishEvent event){
        Player player = event.getPlayer();
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.FISHERMAN.toString()))
            return;
        if(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH))
        if(event.getCaught() instanceof Item){
            Item item_entity = (Item)event.getCaught();
            ItemStack item = item_entity.getItemStack();

            if(isFish(item.getType())){
                double balance = playerStatus.getBalance();
                double income = main.getRegistryConfiguration().fisherman;
                playerStatus.setBalance(balance+income);
                addPlayer(event.getPlayer(), income);
            }else if(isTreasure(item.getType())){
                double balance = playerStatus.getBalance();
                double income = main.getRegistryConfiguration().luckyFisherman;
                playerStatus.setBalance(balance+income);
                addPlayer(event.getPlayer(), income);
            }
        }
    }




    private boolean isTreasure(Material material){
        if(material.equals(Material.BOW))
            return true;
        else if(material.equals(Material.ENCHANTED_BOOK))
            return true;
        else if(material.equals(Material.FISHING_ROD))
            return true;
        else if(material.equals(Material.NAME_TAG))
            return true;
        else if(material.equals(Material.NAUTILUS_SHELL))
            return true;
        else return material.equals(Material.SADDLE);
    }

    private boolean isFish(Material material){
        if(material.equals(Material.COD))
            return true;
        else if(material.equals(Material.SALMON))
            return true;
        else if(material.equals(Material.PUFFERFISH))
            return true;
        else return material.equals(Material.TROPICAL_FISH);
    }
}
