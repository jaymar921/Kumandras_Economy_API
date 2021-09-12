package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.economy.ShopHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class ShopListener implements Listener {

    KumandrasEconomy main;
    private final DecimalFormat fmt = new DecimalFormat("###,###,###,###.##");
    public ShopListener(KumandrasEconomy main){
        this.main = main;
    }

    @EventHandler
    private void cloneEvent(InventoryOpenEvent event){
        Player player = (Player) event.getPlayer();
        if(main.getShopDataHandler().getModifyShopUI().containsKey(player)){
            String shop = main.getShopDataHandler().getModifyShopUI().get(player);
            main.getShopDataHandler().cloningSuccess(shop);
            main.getShopDataHandler().getModifyShopUI().remove(player);
            //get the inventory
            Inventory inventory = Bukkit.createInventory(null, event.getInventory().getSize(), ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+shop);
            inventory.setContents(event.getInventory().getContents());
            inventory = new ShopHandler().cleanUp(inventory);
            List<Double> set_non_clickable = new ArrayList<>();
            for(int i = 0; i < event.getInventory().getSize(); i++)
                set_non_clickable.add(-1.0);
            main.getShopDataHandler().getShopItemsPrices().put(shop,set_non_clickable);
            main.getShopDataHandler().getShopItems().put(shop,inventory);

            player.sendMessage(ChatColor.GREEN+"Prototype cloning was successful");
            player.sendMessage(ChatColor.DARK_AQUA+"Go near "+ChatColor.AQUA+shop+ChatColor.DARK_AQUA+" and type "+ChatColor.YELLOW+"/kumandra shop modify shopUI Price");

            new BukkitRunnable(){
                @Override
                public void run() {
                    player.closeInventory();
                }
            }.runTaskLater(main,5);
        }
    }

    @EventHandler
    private void openShop(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof LivingEntity))
            return;
        LivingEntity entity = (LivingEntity) event.getRightClicked();

        if(entity.getCustomName()==null)
            return;

        String entityName = entity.getCustomName();

        for(String shops : main.getShopDataHandler().getShopLocation().keySet()){
            if(entityName.contains(shops)){
                if(main.getShopDataHandler().getShopLocation().get(shops).getWorld().equals(entity.getWorld()))
                    if(main.getShopDataHandler().getShopLocation().get(shops).distance(entity.getLocation())<=2){
                        Player player = event.getPlayer();
                        if(main.getShopDataHandler().getShopItems().containsKey(shops)) {
                            player.openInventory(main.getShopDataHandler().getShopItems().get(shops));
                            event.setCancelled(true);
                        }
                    }
            }
        }
    }

    @EventHandler
    private void attackingEntity(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity))
            return;
        LivingEntity entity = (LivingEntity) event.getEntity();

        if(entity.getCustomName()==null)
            return;

        String entityName = entity.getCustomName();

        for(String shops : main.getShopDataHandler().getShopLocation().keySet()){
            if(entityName.contains(shops)){
                if(main.getShopDataHandler().getShopLocation().get(shops).getWorld().equals(entity.getWorld()))
                    if(main.getShopDataHandler().getShopLocation().get(shops).distance(entity.getLocation())<=2){
                        if(main.getShopDataHandler().getShopItems().containsKey(shops)) {
                            event.setCancelled(true);
                        }
                    }
            }
        }
    }

    @EventHandler
    private void setupPrices(InventoryClickEvent event){
        Inventory shopUI = null;
        String shopName = null;
        Player player = (Player)event.getWhoClicked();
        for(String shop : main.getShopDataHandler().getShopItems().keySet()){
            if(main.getShopDataHandler().getShopItems().get(shop).equals(event.getClickedInventory())) {
                shopUI = main.getShopDataHandler().getShopItems().get(shop);
                shopName = shop;
            }
        }

        if(shopUI==null)
            return;
        int slot = event.getRawSlot();

        if(!main.getShopDataHandler().getPricingSession().contains(shopName))
            return;

        if(main.getShopDataHandler().getShopItemsPrices().get(shopName).get(event.getRawSlot())== -1)
            event.setCancelled(true);

        ItemStack item = event.getInventory().getItem(slot);
        assert item != null;
        String itemName = item.getItemMeta().getDisplayName();



        player.sendMessage(ChatColor.DARK_AQUA+"Setting price for "+itemName+ChatColor.DARK_AQUA+".");
        player.sendMessage(ChatColor.AQUA+"Type the price in the chat bar");
        main.getShopDataHandler().getSettingPriceSession().put(player, slot);
        main.getShopDataHandler().getSettingPriceSessionShop().put(player,shopName);
        main.getShopDataHandler().getSettingPrice().add(shopName);
        player.closeInventory();
    }

    @EventHandler
    private void chatSetPrice(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(!main.getShopDataHandler().getSettingPriceSession().containsKey(player))
            return;

        String message = event.getMessage();
        double price = 0;
        try{
            price = Double.parseDouble(message);
            if(price<=-2){
                player.sendMessage(ChatColor.RED+"Invalid Price");
                return;
            }
        }catch (Exception e){
            player.sendMessage(ChatColor.RED+"Invalid Price");
            return;
        }
        int slot = main.getShopDataHandler().getSettingPriceSession().get(player);
        String shop = main.getShopDataHandler().getSettingPriceSessionShop().get(player);

        //Set the price for slot
        main.getShopDataHandler().getShopItemsPrices().get(shop).set(slot, price);

        double finalPrice = price;
        new BukkitRunnable(){
            @Override
            public void run() {
                //get the inventory
                Inventory inventory = main.getShopDataHandler().getShopItems().get(shop);
                ItemStack item = inventory.getItem(slot);
                assert item!=null;
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                List<String> lore = meta.getLore();
                if(lore==null)
                    lore = new ArrayList<>();
                List<String> newLore = new ArrayList<>();
                for(String l : lore)
                    if(!l.contains("Price"))
                        newLore.add(l);
                if(finalPrice>0)
                newLore.add(ChatColor.DARK_AQUA+"↠Price: "+ finalPrice +main.getRegistryConfiguration().currency_prefix);

                meta.setLore(newLore);
                item.setItemMeta(meta);
                inventory.setItem(slot,item);
                main.getShopDataHandler().getShopItems().replace(shop,inventory);

                player.sendMessage(ChatColor.DARK_AQUA+"Setting price for "+meta.getDisplayName()+ChatColor.DARK_AQUA+" was successful");
                player.sendMessage(ChatColor.DARK_AQUA+"Price: "+ChatColor.YELLOW+fmt.format(finalPrice)+main.getRegistryConfiguration().currency_prefix);
                player.openInventory(main.getShopDataHandler().getShopItems().get(shop));
                main.getShopDataHandler().getSettingPrice().remove(shop);
            }
        }.runTaskLater(main, 5);

        event.setCancelled(true);
    }

    @EventHandler
    private void playerSavePrice(InventoryCloseEvent event){
        Inventory shopUI = null;
        String shopName = null;
        for(String shop : main.getShopDataHandler().getShopItems().keySet()){
            if(main.getShopDataHandler().getShopItems().get(shop).equals(event.getInventory())) {
                shopUI = main.getShopDataHandler().getShopItems().get(shop);
                shopName = shop;
            }
        }
        if(shopUI==null)
            return;
        if(main.getShopDataHandler().getSettingPrice().contains(shopName))
            return;
        if(main.getShopDataHandler().getPricingSession().contains(shopName))
            if(main.getShopDataHandler().getSettingPriceSessionShop().containsKey((Player) event.getPlayer())){
                Player player = (Player) event.getPlayer();
                player.sendMessage(ChatColor.GREEN+"------------------------");
                player.sendMessage(ChatColor.DARK_GREEN+"> "+ChatColor.YELLOW+shopName+ChatColor.DARK_GREEN+" < - Prices is set");
                player.sendMessage(ChatColor.GREEN+"-----------------------");
                main.getShopDataHandler().getSettingPriceSessionShop().remove(player);
                main.getShopDataHandler().getSettingPriceSession().remove(player);
                main.getShopDataHandler().getPricingSession().remove(shopName);
            }
    }

    @EventHandler
    private void BuyShop(InventoryClickEvent event){
        Inventory shopUI = null;
        String shopName = null;
        for(String shop : main.getShopDataHandler().getShopItems().keySet()){
            if(main.getShopDataHandler().getShopItems().get(shop).equals(event.getClickedInventory())) {
                shopUI = main.getShopDataHandler().getShopItems().get(shop);
                shopName = shop;
            }
        }

        if(shopUI==null)
            return;
        double price = main.getShopDataHandler().getShopItemsPrices().get(shopName).get(event.getRawSlot());
        if(price >= -1)
            event.setCancelled(true);
        if(main.getShopDataHandler().getSettingPrice().contains(shopName))
            return;
        if(price > 0){
            int slot = event.getRawSlot();
            int count = event.getClickedInventory().getItem(slot).getAmount();
            ItemStack itemStack = new ItemStack(event.getClickedInventory().getItem(slot).getType());
            ItemMeta meta = event.getClickedInventory().getItem(slot).getItemMeta();
            assert meta != null;
            List<String> lore = meta.getLore();
            if(lore == null)
                lore = new ArrayList<>();
            List<String> lore2 = new ArrayList<>();
            for(String l : lore)
                if(!l.contains("Price"))
                    lore2.add(l);
            meta.setLore(lore2);
            //item is ready
            itemStack.setItemMeta(meta);

            //check if player has balance
            Player player = (Player)event.getWhoClicked();
            if(main.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())){
                PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());

                double balance = playerStatus.getBalance();

                if(balance<=price){
                    player.sendMessage(ChatColor.RED+"You do not have enough balance to buy this item");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    return;
                }
                balance-=price;
                playerStatus.setBalance(balance);

                main.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);

                //give to player
                for(int i = 0 ; i<count;i++){
                    if(player.getInventory().firstEmpty()==-1)
                        player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                    else
                        player.getInventory().addItem(itemStack);
                }
                player.sendMessage(ChatColor.GREEN+"Payment was successful, new balance: "+ChatColor.YELLOW+fmt.format(balance)+main.getRegistryConfiguration().currency_prefix);
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0f, 2.0f);
            }
        }

    }
}
