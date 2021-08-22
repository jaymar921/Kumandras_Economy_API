package me.jaymar921.kumandraseconomy.economy;

import me.jaymar921.kumandraseconomy.InventoryGUI.DeliveryGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.entity.DeliveryType;
import me.jaymar921.kumandraseconomy.entity.mobs.ChickenEntity;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DeliveryHandler {

    Map<String, Inventory> deliveryInventory;
    Map<String, DeliveryType> deliveryType;
    Map<Player, Player> scheduleDelivery;
    Map<String, List<Player>> id_delivery;
    Map<String, LivingEntity> entityDelivery;

    KumandrasEconomy plugin;
    public DeliveryHandler(KumandrasEconomy plugin){
        this.plugin = plugin;
        deliveryInventory = plugin.getDataHandler().getDeliverInventory();
        scheduleDelivery = new HashMap<>();
        deliveryType = new HashMap<>();
        entityDelivery = new HashMap<>();
        id_delivery = new HashMap<>();
    }

    public void createDelivery(DeliveryType type, Player fromPlayer, Player toPlayer){
        String deliver_id = spawnEntity(fromPlayer, type);
        String title = fromPlayer.getName()+"'s Delivery";
        List<Player> players = new ArrayList<>();
        players.add(toPlayer);
        players.add(fromPlayer);
        if(type.equals(DeliveryType.CHEAP)){
            Inventory deliver_Inventory = new DeliveryGUI(plugin.getRegistryConfiguration().deliveryHandler).getFiveSlotUI(title, toPlayer.getName());
            deliveryInventory.put(deliver_id,deliver_Inventory);
            deliveryType.put(deliver_id, type);

            id_delivery.put(deliver_id,players);
        }
    }

    public String spawnEntity(Player player, DeliveryType type){
        String d_id = player.getName()+"'s Delivery - ID: "+new Random().nextInt(200);
        if(type.equals(DeliveryType.CHEAP)){
            entityDelivery.put(d_id,new ChickenEntity().spawnEntity(player.getLocation(),d_id));
        }
        return d_id;
    }

    public void startDelivery(DeliveryType type, String id, Player player){
        entityDelivery.get(id).remove();
        Inventory inventory = deliveryInventory.get(id);

        ItemMeta meta = inventory.getItem(8).getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW+"[Accept]");
        inventory.getItem(8).setItemMeta(meta);

        Player recipient = scheduleDelivery.get(player);
        if(type.equals(DeliveryType.CHEAP)){
            entityDelivery.put(id, new ChickenEntity().spawnEntity(recipient.getLocation(),id));
        }
        deliveryInventory.replace(id, inventory);
    }
    public void deliveryAccepted(String id){
        entityDelivery.get(id).remove();
        deliveryInventory.remove(id);
        deliveryType.remove(id);
        id_delivery.remove(id);
    }

    public Map<Player, Player> getScheduleDelivery() {
        return scheduleDelivery;
    }

    public Map<String, Inventory> getDeliveryInventory() {
        return deliveryInventory;
    }

    public Map<String, DeliveryType> getDeliveryType() {
        return deliveryType;
    }

    public Map<String, List<Player>> getId_delivery() {
        return id_delivery;
    }
}
