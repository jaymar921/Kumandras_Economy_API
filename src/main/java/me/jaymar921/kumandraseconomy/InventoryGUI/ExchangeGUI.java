package me.jaymar921.kumandraseconomy.InventoryGUI;

import me.jaymar921.kumandraseconomy.ItemHandler.PlayerHeads;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExchangeGUI {
    private static final DecimalFormat df2 = new DecimalFormat("###,###,###.##");
    private final String currency_prefix = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix;
    private final String foreign_economy = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().foreign_economy;
    private final Map<String,String> lang = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getDataHandler().getLanguageData();
    public Inventory getExchangeGUI(Player player, PlayerStatus status){
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLUE+""+ChatColor.BOLD+lang.get("CurrencyExchange"));

        ItemStack item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        for (int i = 0; i < 54; i++){
            gui.setItem(i, item);
        }

        item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        for (int i = 0; i < 9; i++){
            gui.setItem(i, item);
        }
        for (int i = 45; i < 54; i++){
            gui.setItem(i, item);
        }

        item = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        for (int i = 9; i < 18; i++){
            gui.setItem(i, item);
        }
        for (int i = 36; i < 45; i++){
            gui.setItem(i, item);
        }

        item = new PlayerHeads().getPlayerHead(player);
        meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+currency_prefix+" "+lang.get("EconomyAccount"));
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+lang.get("AccName")+" "+ChatColor.GOLD+""+ChatColor.BOLD+player.getName());
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+lang.get("Bal")+" "+ChatColor.GOLD+"$"+ChatColor.BOLD+status.getBalance());
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+lang.get("PlayerLevel")+" "+ChatColor.GOLD+""+ChatColor.BOLD+player.getLevel());
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(13, item);

        double currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        double kumandras_money = 0;
        double foreign_money = kumandras_money/currency;

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+foreign_economy+" "+lang.get("Currency")+" -> "+currency_prefix);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 50;
        foreign_money = kumandras_money/currency;

        lore.add(ChatColor.GOLD+""+df2.format(foreign_money)+" "+foreign_economy+" "+lang.get("Currency")+" = "+ kumandras_money+ currency_prefix);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(19, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+currency_prefix+" "+lang.get("Currency")+" -> "+foreign_economy);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 50*currency;
        foreign_money = 50;

        lore.add(ChatColor.GOLD+""+df2.format(kumandras_money)+" "+currency_prefix+" "+lang.get("Currency")+" = "+ foreign_money+ " "+ foreign_economy);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(28, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+foreign_economy+" "+lang.get("Currency")+" -> "+currency_prefix);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 100;
        foreign_money = kumandras_money/currency;

        lore.add(ChatColor.GOLD+""+df2.format(foreign_money)+" "+foreign_economy+" "+lang.get("Currency")+" = "+ kumandras_money+ currency_prefix);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(21, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+currency_prefix+" "+lang.get("Currency")+" -> "+foreign_economy);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 100*currency;
        foreign_money = 100;

        lore.add(ChatColor.GOLD+""+df2.format(kumandras_money)+" "+currency_prefix+" "+lang.get("Currency")+" = "+ foreign_money+ " "+ foreign_economy);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(30, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+foreign_economy+" "+lang.get("Currency")+" -> "+currency_prefix);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 500;
        foreign_money = kumandras_money/currency;

        lore.add(ChatColor.GOLD+""+df2.format(foreign_money)+" "+foreign_economy+" "+lang.get("Currency")+" = "+ kumandras_money+ currency_prefix);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(23, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+currency_prefix+" "+lang.get("Currency")+" -> "+foreign_economy);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 500*currency;
        foreign_money = 500;

        lore.add(ChatColor.GOLD+""+df2.format(kumandras_money)+" "+currency_prefix+" "+lang.get("Currency")+" = "+ foreign_money+ " "+ foreign_economy);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(32, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+foreign_economy+" "+lang.get("Currency")+" -> "+currency_prefix);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 1000;
        foreign_money = kumandras_money/currency;

        lore.add(ChatColor.GOLD+""+df2.format(foreign_money)+" "+foreign_economy+" "+lang.get("Currency")+" = "+ kumandras_money+ currency_prefix);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(25, item);

        item = new ItemStack(Material.PAPER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+lang.get("Exchange"));
        lore = new ArrayList<>();
        lore.add(ChatColor.GREEN+""+ChatColor.BOLD+currency_prefix+" "+lang.get("Currency")+" -> "+foreign_economy);

        currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
        kumandras_money = 1000*currency;
        foreign_money = 1000;

        lore.add(ChatColor.GOLD+""+df2.format(kumandras_money)+" "+currency_prefix+" "+lang.get("Currency")+" = "+ foreign_money+ " "+ foreign_economy);
        lore.add(ChatColor.AQUA+lang.get("Convert"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        gui.setItem(34, item);

        item = new ItemStack(Material.BARRIER);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.setDisplayName(ChatColor.YELLOW+""+ChatColor.BOLD+"["+lang.get("Exit")+"]");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        gui.setItem(40, item);

        return gui;
    }
}
