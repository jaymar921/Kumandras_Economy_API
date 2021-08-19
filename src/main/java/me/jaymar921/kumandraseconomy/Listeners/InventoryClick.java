package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    KumandrasEconomy plugin;
    public InventoryClick(KumandrasEconomy plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void playerClickInventory(InventoryClickEvent event){
        if(plugin.getDataHandler().getPlayerInventory().containsKey(event.getWhoClicked().getUniqueId().toString())){
            if(event.getClickedInventory()==null)
                return;
            if(plugin.getDataHandler().getPlayerInventory().get(event.getWhoClicked().getUniqueId().toString())==null)
                return;
            if(event.getClickedInventory().equals(plugin.getDataHandler().getPlayerInventory().get(event.getWhoClicked().getUniqueId().toString())))
                event.setCancelled(true);
        }
    }
}
