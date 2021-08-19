package me.jaymar921.kumandraseconomy.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradingHandler {
    private Map<String, Inventory> tradeInventory = new HashMap<>();
    private Map<Inventory, List<String>> tradePersonnel = new HashMap<>();
    private Map<String, String> requestTradePersonnel = new HashMap<>();

    public Map<String, Inventory> getTradeInventory() {return tradeInventory;}
    public Map<Inventory,List<String>> getTradePersonnel() {return tradePersonnel;}
    public Map<String, String> getRequestTradePersonnel(){return requestTradePersonnel;}

    public void createTrade(Player trader, Player buyer){
        Inventory inv = createTradeInventory(trader.getName(), buyer.getName());
        getTradeInventory().put(trader.getUniqueId().toString(), inv);
        List<String> playerUUIDs = new ArrayList<>();
        playerUUIDs.add(trader.getUniqueId().toString());
        playerUUIDs.add(buyer.getUniqueId().toString());
        getTradePersonnel().put(inv,playerUUIDs);
        getRequestTradePersonnel().put(buyer.getUniqueId().toString(),trader.getUniqueId().toString());
    }

    public Inventory createTradeInventory(String trader, String buyer){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+trader +" and "+buyer+"'s trade");

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

        item = new ItemStack(Material.GOLD_NUGGET);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.GOLD+"Increment by $1");
        item.setItemMeta(meta);

        inv.setItem(38, item);
        inv.setItem(42, item);

        meta.setDisplayName(ChatColor.GOLD+"Decrement by $1");
        item.setItemMeta(meta);

        inv.setItem(37, item);
        inv.setItem(42, item);

        item = new ItemStack(Material.EMERALD);
        meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Trade for "+ChatColor.GOLD+"$1");
        item.setItemMeta(meta);

        inv.setItem(39, item);
        inv.setItem(43, item);

        return inv;
    }
}
