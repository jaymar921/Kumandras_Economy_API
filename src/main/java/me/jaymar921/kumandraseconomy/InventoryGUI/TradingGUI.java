package me.jaymar921.kumandraseconomy.InventoryGUI;

import me.jaymar921.kumandraseconomy.ItemHandler.PlayerHeads;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TradingGUI {

    public Inventory createTradeInventory(String trader, String buyer){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN+"Trading Session ID: "+new Random().nextInt(1000));

        ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        //set slot
        for(int i = 0; i < 9; i++)
            inv.setItem(i,item);
        for(int i = 45; i < 54; i++)
            inv.setItem(i,item);
        inv.setItem(9,item);
        inv.setItem(18,item);
        inv.setItem(27,item);
        inv.setItem(36,item);
        inv.setItem(13,item);
        inv.setItem(22,item);
        inv.setItem(31,item);
        inv.setItem(40,item);
        inv.setItem(17,item);
        inv.setItem(26,item);
        inv.setItem(35,item);
        inv.setItem(44,item);

        item = new ItemStack(Material.MAGENTA_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+"Trading: "+trader+" -> "+buyer);
        meta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE+"Trader: "+trader,ChatColor.LIGHT_PURPLE+"Trader partner: "+buyer,ChatColor.GREEN+"Trader UI will be on the left",ChatColor.GREEN+"Trader partner UI will be on the right",ChatColor.DARK_AQUA+"Click on "+ChatColor.GREEN+"SET TRADE"+ChatColor.DARK_AQUA+" to trade items",ChatColor.RED+"Closing the UI will end the trading session"));
        item.setItemMeta(meta);
        inv.setItem(4, item);
        inv.setItem(13, item);
        inv.setItem(22, item);
        inv.setItem(31, item);
        inv.setItem(40, item);
        inv.setItem(49, item);

        item = new PlayerHeads().getPlayerHead(trader);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Trader"));
        item.setItemMeta(meta);
        inv.setItem(2, item);

        item = new PlayerHeads().getPlayerHead(buyer);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Trading Partner"));
        item.setItemMeta(meta);
        inv.setItem(6, item);

        item = new ItemStack(Material.GOLD_INGOT);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+"Increment by "+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().tradingIncrease+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix);
        item.setItemMeta(meta);

        inv.setItem(38, item);
        inv.setItem(42, item);

        meta.setDisplayName(ChatColor.GOLD+"Decrement by "+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().tradingIncrease+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix);
        item.setItemMeta(meta);

        inv.setItem(37, item);
        inv.setItem(41, item);

        item = new ItemStack(Material.EMERALD);
        meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Trade "+trader+" items");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"for "+ChatColor.GOLD+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().tradingIncrease+ KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix, ChatColor.RED+"(Not set)"));
        item.setItemMeta(meta);

        inv.setItem(39, item);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Trade "+buyer+" items");
        item.setItemMeta(meta);
        inv.setItem(43, item);

        item = new ItemStack(Material.BOOK);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.GREEN+"SET TRADE");
        meta.setLore(null);

        NamespacedKey itemdata = new NamespacedKey(KumandrasEconomy.getPlugin(KumandrasEconomy.class), "itemData");
        meta.getPersistentDataContainer().set(itemdata, PersistentDataType.INTEGER, new Random().nextInt(100000));
        item.setItemMeta(meta);
        inv.setItem(48, item);
        meta.getPersistentDataContainer().set(itemdata, PersistentDataType.INTEGER, new Random().nextInt(100000));
        item.setItemMeta(meta);
        inv.setItem(52, item);

        return inv;
    }

    public List<Integer> traderSlot(){
        List<Integer> integers = new ArrayList<>();
        integers.add(10);
        integers.add(11);
        integers.add(12);
        integers.add(19);
        integers.add(20);
        integers.add(21);
        integers.add(28);
        integers.add(29);
        integers.add(30);
        return integers;
    }

    public List<Integer> buyerSlot(){
        List<Integer> integers = new ArrayList<>();
        integers.add(14);
        integers.add(15);
        integers.add(16);
        integers.add(23);
        integers.add(24);
        integers.add(25);
        integers.add(32);
        integers.add(33);
        integers.add(34);
        return integers;
    }

    public ItemStack glassItem(){
        ItemStack item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

}
