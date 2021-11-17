package me.jaymar921.kumandraseconomy.economy;

import me.jaymar921.kumandraseconomy.InventoryGUI.DeliveryGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.entity.DeliveryType;
import me.jaymar921.kumandraseconomy.entity.mobs.*;
import me.jaymar921.kumandraseconomy.utility.LangParse;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DeliveryHandler {

    Map<String, Inventory> deliveryInventory;
    Map<String, DeliveryType> deliveryType;
    Map<Player, Player> scheduleDelivery;
    Map<String, List<Player>> id_delivery;
    Map<String, LivingEntity> entityDelivery;
    Map<String, String> lang;
    KumandrasEconomy plugin;
    public DeliveryHandler(KumandrasEconomy plugin){
        this.plugin = plugin;
        deliveryInventory = plugin.getDataHandler().getDeliverInventory();
        scheduleDelivery = new HashMap<>();
        deliveryType = new HashMap<>();
        entityDelivery = new HashMap<>();
        id_delivery = new HashMap<>();
        lang = plugin.getDataHandler().getLanguageData();
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
        }else if(type.equals(DeliveryType.REGULAR) || type.equals(DeliveryType.FAST)){
            Inventory deliver_Inventory = new DeliveryGUI(plugin.getRegistryConfiguration().deliveryHandler).getTenSlotUI(title, toPlayer.getName());
            deliveryInventory.put(deliver_id,deliver_Inventory);
            deliveryType.put(deliver_id, type);
            id_delivery.put(deliver_id,players);
        }else if(type.equals(DeliveryType.PRIORITY)){
            Inventory deliver_Inventory = new DeliveryGUI(plugin.getRegistryConfiguration().deliveryHandler).getFifteenSlotUI(title, toPlayer.getName());
            deliveryInventory.put(deliver_id,deliver_Inventory);
            deliveryType.put(deliver_id, type);
            id_delivery.put(deliver_id,players);
        }
    }

    public String spawnEntity(Player player, DeliveryType type){
        String d_id = player.getName()+"'s Delivery - ID: "+new Random().nextInt(200);
        if(type.equals(DeliveryType.CHEAP)){
            entityDelivery.put(d_id,new ChickenEntity().spawnEntity(player.getLocation(),d_id));
            player.sendMessage(ChatColor.AQUA+lang.get("deliveryChicken"));
        }
        if(type.equals(DeliveryType.REGULAR)){
            entityDelivery.put(d_id,new PigEntity().spawnEntity(player.getLocation(),d_id));
            player.sendMessage(ChatColor.AQUA+lang.get("deliveryPig"));
        }
        if(type.equals(DeliveryType.FAST)){
            if(Math.random() <= 0.5){
                entityDelivery.put(d_id,new SheepEntity().spawnEntity(player.getLocation(),d_id));
                player.sendMessage(ChatColor.AQUA+lang.get("deliverySheep"));
            }else{
                entityDelivery.put(d_id,new CowEntity().spawnEntity(player.getLocation(),d_id));
                player.sendMessage(ChatColor.AQUA+lang.get("deliveryCow"));
            }
        }
        if(type.equals(DeliveryType.PRIORITY)){
            entityDelivery.put(d_id,new ParrotEntity().spawnEntity(player.getLocation(),d_id));
            player.sendMessage(ChatColor.AQUA+lang.get("deliveryParrot"));
        }
        return d_id;
    }

    public void startDelivery(DeliveryType type, String id, Player player){

        Inventory inventory = deliveryInventory.get(id);

        ItemMeta meta;

        switch (type.getValue()){
            case 1:
                meta = Objects.requireNonNull(inventory.getItem(8)).getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.YELLOW+"[Accept]");
                Objects.requireNonNull(inventory.getItem(8)).setItemMeta(meta);
                break;
            case 2:
            case 3:
                meta = Objects.requireNonNull(inventory.getItem(17)).getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.YELLOW+"[Accept]");
                Objects.requireNonNull(inventory.getItem(17)).setItemMeta(meta);
                break;
            case 4:
                meta = Objects.requireNonNull(inventory.getItem(17)).getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.YELLOW+"[Accept]");
                Objects.requireNonNull(inventory.getItem(26)).setItemMeta(meta);
        }


        Player recipient = scheduleDelivery.get(player);

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1.0f, 1.0f);

        travelSetup(type,player, recipient, id);

        deliveryInventory.replace(id, inventory);
    }

    public void travelSetup(DeliveryType type,Player origin, final Player destination, final String delivery_id){
        //fly the entity
        new BukkitRunnable(){
            final Particle.DustOptions dop_orange = new Particle.DustOptions(Color.fromRGB(255, 165, 0), 1);
            double y = entityDelivery.get(delivery_id).getLocation().getY();
            final double finalLocation = entityDelivery.get(delivery_id).getLocation().getY()+10.0;
            public void run(){
                if(entityDelivery.get(delivery_id).isDead() || !entityDelivery.get(delivery_id).isValid()){
                    cancel();
                    origin.sendMessage(ChatColor.DARK_AQUA+lang.get("entityKilled"));
                    return;
                }
                Location location = entityDelivery.get(delivery_id).getLocation();
                entityDelivery.get(delivery_id).getWorld().spawnParticle(Particle.REDSTONE, location, 20,dop_orange);
                location.setY(y+=0.2);
                entityDelivery.get(delivery_id).teleport(location);
                if(y> (finalLocation)){
                    cancel();
                    entityDelivery.get(delivery_id).setInvisible(true);
                    travelSetupLand(type,destination,delivery_id);
                    String area = ChatColor.YELLOW+""+destination.getLocation().getBlockX()+", "+destination.getLocation().getBlockY()+", "+destination.getLocation().getBlockZ();
                    destination.sendMessage(ChatColor.DARK_AQUA+lang.get("comingDelivery")+" ["+area+ChatColor.DARK_AQUA+"]");
                }
            }
        }.runTaskTimer(plugin,0,1);

    }

    public void travelSetupLand(DeliveryType type, final Player destination, final String delivery_id){
        long time = 100;

        switch (type.getValue()){
            case 1:
                time = plugin.getRegistryConfiguration().deliveryHandler.getCheap_delivery_timer()*20;
                break;
            case 2:
                time = plugin.getRegistryConfiguration().deliveryHandler.getRegular_delivery_timer()*20;
                break;
            case 3:
                time = plugin.getRegistryConfiguration().deliveryHandler.getFast_delivery_timer()*20;
                break;
            case 4:
                time = plugin.getRegistryConfiguration().deliveryHandler.getPriority_delivery_timer()*20;
        }


        plugin.getLogger().info(ChatColor.YELLOW+delivery_id+" is on schedule: "+time/20+"s, "+ChatColor.RED+"do not reload the server!");

        Location destination_location = destination.getLocation();
        destination_location.setY(destination.getLocation().getY()+20.0);
        entityDelivery.get(delivery_id).teleport(destination_location);
        //land the entity
        new BukkitRunnable(){
            final Particle.DustOptions dop_orange = new Particle.DustOptions(Color.fromRGB(255, 165, 0), 1);
            double y = entityDelivery.get(delivery_id).getLocation().getY();
            public void run(){
                if(entityDelivery.get(delivery_id).isDead() || !entityDelivery.get(delivery_id).isValid()){
                    cancel();
                    destination.sendMessage(ChatColor.DARK_AQUA+lang.get("entityKilled"));
                    return;
                }
                if(entityDelivery.get(delivery_id).isInvisible())
                    entityDelivery.get(delivery_id).setInvisible(false);

                Location entity_loc = entityDelivery.get(delivery_id).getLocation();
                entityDelivery.get(delivery_id).getWorld().spawnParticle(Particle.REDSTONE, entity_loc, 20,dop_orange);
                entity_loc.setY(y-=0.3);
                entityDelivery.get(delivery_id).teleport(entity_loc);

                if(!entityDelivery.get(delivery_id).getWorld().getBlockAt(entityDelivery.get(delivery_id).getLocation()).isPassable()){
                    cancel();
                    entityDelivery.get(delivery_id).getWorld().spawnParticle(Particle.CLOUD, entityDelivery.get(delivery_id).getLocation(), 20);
                    entityDelivery.get(delivery_id).getWorld().playSound(entityDelivery.get(delivery_id).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0f, 2.0f);
                    destination.sendMessage(ChatColor.DARK_AQUA+ LangParse.string(lang.get("deliverArrive"),ChatColor.YELLOW+"accept"+ChatColor.DARK_AQUA));
                    plugin.getLogger().info(ChatColor.YELLOW+delivery_id+" has arrived, waiting for pickup. "+ChatColor.RED+"Do not reload the server!");
                }
            }
        }.runTaskTimer(plugin,time,1);
    }

    public void deliveryAccepted(String id){
        entityDelivery.get(id).remove();
        entityDelivery.remove(id);
        deliveryInventory.remove(id);
        deliveryType.remove(id);
        id_delivery.remove(id);
        int delivery = entityDelivery.size();
        plugin.getLogger().info(ChatColor.YELLOW+id+" was received. "+ChatColor.GREEN+delivery+" pending delivery schedule/s");
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

    public Map<String, LivingEntity> getEntityDelivery() {
        return entityDelivery;
    }
}
