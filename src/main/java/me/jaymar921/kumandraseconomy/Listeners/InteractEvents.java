package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractEvents implements Listener {

    KumandrasEconomy plugin;

    public InteractEvents(KumandrasEconomy plugin){
        this.plugin = plugin;
    }

    @EventHandler
    private void rightClickOnDeliveryMobs(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof LivingEntity))
            return;
        LivingEntity entity = (LivingEntity) event.getRightClicked();

        if(entity.getCustomName()==null)
            return;

        String entityName = entity.getCustomName();

        if(plugin.getDeliveryHandler().getDeliveryInventory().containsKey(entityName))
            event.getPlayer().openInventory(plugin.getDeliveryHandler().getDeliveryInventory().get(entityName));
    }
}
