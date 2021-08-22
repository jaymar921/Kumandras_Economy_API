package me.jaymar921.kumandraseconomy.InventoryGUI;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.DeliveryDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DeliveryGUI {

    DeliveryDataHandler deliveryHandler;
    String kumandra_prefix = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix;
    public DeliveryGUI(DeliveryDataHandler deliveryHandler){
        this.deliveryHandler = deliveryHandler;
    }

    public Inventory DeliveryUI(){
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.BLUE+""+ChatColor.BOLD+"Delivery Booking");

        ItemStack item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        for (int i = 0; i < 27; i++){
            gui.setItem(i, item);
        }

        item = new ItemStack(Material.CHICKEN_SPAWN_EGG);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Cheap Delivery");
        List<String> lores = new ArrayList<>();
        lores.add(" ");
        lores.add(ChatColor.DARK_AQUA+"Slots: "+ChatColor.YELLOW+"5");
        lores.add(ChatColor.DARK_AQUA+"Delivery Time: "+ChatColor.YELLOW+deliveryHandler.getCheap_delivery_timer()+"s");
        lores.add(ChatColor.DARK_AQUA+"Delivery Price: "+ChatColor.YELLOW+deliveryHandler.getCheap_delivery_price()+kumandra_prefix);
        lores.add(ChatColor.LIGHT_PURPLE+"Click to pay");
        meta.setLore(lores);
        item.setItemMeta(meta);
        lores.clear();

        gui.setItem(10, item);

        item = new ItemStack(Material.PIG_SPAWN_EGG);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Regular Delivery");
        lores = new ArrayList<>();
        lores.add(" ");
        lores.add(ChatColor.DARK_AQUA+"Slots: "+ChatColor.YELLOW+"10");
        lores.add(ChatColor.DARK_AQUA+"Delivery Time: "+ChatColor.YELLOW+deliveryHandler.getRegular_delivery_timer()+"s");
        lores.add(ChatColor.DARK_AQUA+"Delivery Price: "+ChatColor.YELLOW+deliveryHandler.getRegular_delivery_price()+kumandra_prefix);
        lores.add(ChatColor.LIGHT_PURPLE+"Click to pay");
        meta.setLore(lores);
        item.setItemMeta(meta);
        lores.clear();

        gui.setItem(12, item);

        item = new ItemStack(Material.SHEEP_SPAWN_EGG);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Fast Delivery");
        lores = new ArrayList<>();
        lores.add(" ");
        lores.add(ChatColor.DARK_AQUA+"Slots: "+ChatColor.YELLOW+"10");
        lores.add(ChatColor.DARK_AQUA+"Delivery Time: "+ChatColor.YELLOW+deliveryHandler.getFast_delivery_timer()+"s");
        lores.add(ChatColor.DARK_AQUA+"Delivery Price: "+ChatColor.YELLOW+deliveryHandler.getFast_delivery_price()+kumandra_prefix);
        lores.add(ChatColor.LIGHT_PURPLE+"Click to pay");
        meta.setLore(lores);
        item.setItemMeta(meta);
        lores.clear();

        gui.setItem(14, item);

        item = new ItemStack(Material.COW_SPAWN_EGG);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"Priority Delivery");
        lores = new ArrayList<>();
        lores.add(" ");
        lores.add(ChatColor.DARK_AQUA+"Slots: "+ChatColor.YELLOW+"15");
        lores.add(ChatColor.DARK_AQUA+"Delivery Time: "+ChatColor.YELLOW+deliveryHandler.getPriority_delivery_timer()+"s");
        lores.add(ChatColor.DARK_AQUA+"Delivery Price: "+ChatColor.YELLOW+deliveryHandler.getPriority_delivery_price()+kumandra_prefix);
        lores.add(ChatColor.LIGHT_PURPLE+"Click to pay");
        meta.setLore(lores);
        item.setItemMeta(meta);
        lores.clear();

        gui.setItem(16, item);



        return gui;
    }

    public Inventory getFiveSlotUI(String title, String recipient){
        Inventory ui = Bukkit.createInventory(null, 9, ChatColor.GREEN+title);

        ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        ui.setItem(0, item);
        ui.setItem(1, item);
        ui.setItem(7, item);

        item = new ItemStack(Material.DARK_OAK_SIGN);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.YELLOW+"[DELIVER]");
        meta.setDisplayName(ChatColor.GREEN+"Send items to "+recipient);
        item.setItemMeta(meta);
        ui.setItem(8,item);

        return ui;
    }

}
