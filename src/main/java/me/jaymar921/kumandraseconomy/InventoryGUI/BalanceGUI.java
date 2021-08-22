package me.jaymar921.kumandraseconomy.InventoryGUI;

import me.jaymar921.kumandraseconomy.ItemHandler.PlayerHeads;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
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
import java.util.List;

public class BalanceGUI {

    private final String currency_prefix = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix;

    public Inventory BalanceInventory(Player player, PlayerStatus status, List<String> plugins){
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.BLUE+""+ChatColor.BOLD+player.getName()+"'s Account");

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

        item = new PlayerHeads().getPlayerHead(player);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Kumandra's Economy Account");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+"Account Name: "+ChatColor.GOLD+""+ChatColor.BOLD+player.getName());
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+"Balance: "+ChatColor.GOLD+ChatColor.BOLD+status.getBalance()+currency_prefix);
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+"Player Level: "+ChatColor.GOLD+""+ChatColor.BOLD+player.getLevel());
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(11, item);

        if(KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().separate_economy) {
            item = new ItemStack(Material.PAPER);
            meta = item.getItemMeta();
            assert meta != null;
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Exchange");
            lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Currency status");
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "‣1.00 "+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().foreign_economy+" Money");
            lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "    ↓");
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "‣"+KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy + " Kumandra's Economy" + " Money");
            meta.setLore(lore);
            item.setItemMeta(meta);

            gui.setItem(13, item);
        }

        item = new ItemStack(Material.ENDER_EYE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Plugin Supports");
        lore = new ArrayList<>();
        lore.add(ChatColor.GOLD+"Supported Plugins");
        lore.add(" ");
        if(!plugins.isEmpty()) {
            for (String p_names : plugins)
                lore.add(ChatColor.DARK_GREEN + "" + p_names);
        }else{
            lore.add(ChatColor.RED+"None --");
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(15, item);


        return gui;
    }



}
