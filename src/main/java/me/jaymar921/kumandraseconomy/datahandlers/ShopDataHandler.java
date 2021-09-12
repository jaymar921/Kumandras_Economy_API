package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.Configurations.ShopConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class ShopDataHandler {

    Map<String, Location> shopLocation;
    Map<String, Inventory> shopItems;
    Map<String, List<Double>> shopItemsPrices;
    Map<Player, String> modifyShopUI;
    List<String> cloningSession;
    List<String> pricingSession;
    List<String> settingPrice;
    Map<Player, Integer> settingPriceSession;
    Map<Player, String> settingPriceSessionShop;

    ShopConfiguration configuration;
    KumandrasEconomy main;

    public ShopDataHandler(KumandrasEconomy main){
        this.main = main;

        shopLocation = new HashMap<>();
        shopItems = new HashMap<>();
        shopItemsPrices = new HashMap<>();
        modifyShopUI = new HashMap<>();
        cloningSession = new ArrayList<>();
        pricingSession = new ArrayList<>();
        settingPriceSession = new HashMap<>();
        settingPriceSessionShop = new HashMap<>();
        settingPrice = new ArrayList<>();

        configuration = new ShopConfiguration(main);
        loadShopData();
        shopBuffer();
    }

    public Map<String, Location> getShopLocation(){return shopLocation;}
    public Map<String, List<Double>> getShopItemsPrices() {return shopItemsPrices;}
    public Map<String, Inventory> getShopItems() { return shopItems;}

    public Map<Player, String> getModifyShopUI() {return modifyShopUI;}
    public Map<Player, Integer> getSettingPriceSession() {return settingPriceSession;}
    public Map<Player, String> getSettingPriceSessionShop() {return settingPriceSessionShop;}
    public List<String> getPricingSession() {return pricingSession;}
    public List<String> getSettingPrice(){return settingPrice;}

    public void shopBuffer(){
        new BukkitRunnable(){
            public void run(){
                for(Location location : shopLocation.values()){
                    for(Entity entity:location.getWorld().getNearbyEntities(location, 2, 2, 2)){
                        if(entity instanceof LivingEntity){
                            if(((LivingEntity)entity).getCustomName()!=null){
                                ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 100, false, false));
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(main, 20, 20*60*10);
    }

    public void cloningSession(Player player, String shopName){
        modifyShopUI.put(player, shopName);
        new BukkitRunnable(){
            public void run(){
                if(cloningSession.contains(shopName))
                    return;
                modifyShopUI.remove(player);
                player.sendMessage(ChatColor.RED+"Cloning "+ChatColor.AQUA+shopName+ChatColor.RED+" session has expired.");
            }
        }.runTaskLater(main,20*20);
    }

    public void cloningSuccess(String shop){
        cloningSession.add(shop);
        new BukkitRunnable(){
            public void run(){
                cloningSession.remove(shop);
            }
        }.runTaskLater(main,20*20);
    }

    public void setPrice(Player player, String shop){
        pricingSession.add(shop);
        new BukkitRunnable(){
            int i = 0;
            public void run() {
                if(i < 7)
                    player.sendMessage(pricingSessionChat().get(i));
                if(i>7)
                    cancel();
                i++;
            }
        }.runTaskTimer(main,0,10);
        new BukkitRunnable(){

            int i = 0;
            int count = 5;
            public void run() {
                if(i>2){
                    if(count<=0) {
                        player.openInventory(getShopItems().get(shop));
                        cancel();
                        return;
                    }
                    player.sendTitle(" ", ChatColor.DARK_AQUA+"Opening shop in "+ChatColor.RED+""+(count--), 0, 20, 15);
                }
                i++;
            }
        }.runTaskTimer(main,70,20);

    }

    public void saveShopData(){
        //save Location
        List<Map<String, Location>> location_data = new ArrayList<>();
        location_data.add(shopLocation);
        configuration.getConfig().set("SHOP_LOCATION", location_data);
        //Save prices
        List<Map<String, List<Double>>> price_data = new ArrayList<>();
        price_data.add(shopItemsPrices);

        configuration.getConfig().set("SHOP_PRICES", price_data);
        //Save InventoryItems
        List<ItemStack> itemStacks = new ArrayList<>();
        for(String shop : shopItems.keySet()){
            itemStacks = new ArrayList<>();
            for(int i = 0; i < shopItems.get(shop).getSize(); i++)
                itemStacks.add(shopItems.get(shop).getItem(i));
            configuration.getConfig().set(shop, itemStacks);
        }
        configuration.saveConfig();
    }

    @SuppressWarnings("unchecked")
    public void loadShopData(){
        //Load location
        if(configuration.getConfig().contains("SHOP_LOCATION")){
            List<Map<?,?>> location_data = configuration.getConfig().getMapList("SHOP_LOCATION");
            for(Map<?,?> data : location_data)
                shopLocation = (Map<String, Location>) data;
        }
        //load prices
        if(configuration.getConfig().contains("SHOP_PRICES")){
            List<Map<?, ?>> location_data = configuration.getConfig().getMapList("SHOP_PRICES");
            for(Map<?,?> data : location_data)
                shopItemsPrices = (Map<String, List<Double>>) data;
        }
        //load Inventories
        for(String shop : shopItemsPrices.keySet()){
            if(configuration.getConfig().contains(shop)){
                List<ItemStack> itemStacks = (List<ItemStack>) configuration.getConfig().get(shop);
                Inventory inventory = Bukkit.createInventory(null, itemStacks.size(), ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+shop);
                ItemStack[] items = new ItemStack[itemStacks.size()];
                for(int i = 0 ; i < items.length; i++)
                    items[i] = itemStacks.get(i);
                inventory.setContents(items);
                shopItems.put(shop,inventory);
            }
        }

    }

    private List<String> pricingSessionChat(){
        List<String> chat = new ArrayList<>();

        chat.add(ChatColor.GREEN+"=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        chat.add(ChatColor.GREEN+"  SETTING SHOP Prices");
        chat.add(ChatColor.DARK_AQUA+"Left Click "+ChatColor.YELLOW+"on Item to set prices");
        chat.add(ChatColor.AQUA+"Enter a positive amount to set the price");
        chat.add(ChatColor.AQUA+"Set price to '"+ChatColor.GOLD+"0"+ChatColor.AQUA+"' to lock the item");
        chat.add(ChatColor.DARK_AQUA+"Close Inventory "+ChatColor.YELLOW+"to save");
        chat.add(ChatColor.GREEN+"=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        return chat;
    }
}
