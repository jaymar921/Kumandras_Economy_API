package me.jaymar921.kumandraseconomy.InventoryGUI;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.RegistryConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;

public class JobsGUI {

    String c;
    RegistryConfiguration r;
    DecimalFormat fmt = new DecimalFormat("#.##");
    public JobsGUI(){
        KumandrasEconomy main = KumandrasEconomy.getPlugin(KumandrasEconomy.class);
        c = main.getRegistryConfiguration().currency_prefix;
        r = main.getRegistryConfiguration();
    }

    public Inventory joinJobUI(){
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.DARK_BLUE+"   "+ChatColor.BOLD+"["+ChatColor.DARK_PURPLE+""+ChatColor.BOLD+"Kumandra's Jobs list"+ChatColor.DARK_BLUE+""+ChatColor.BOLD+"]");

        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE+" ");
        item.setItemMeta(meta);
        for(int i = 0; i < 27; i++)
            inventory.setItem(i, item);

        //Farmer
        item = new ItemStack(Material.IRON_HOE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Farmer");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by harvesting crops",ChatColor.GREEN+"and breeding animals","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Harvesting crops: "+ChatColor.YELLOW+fmt.format(r.cropHarvesting)+c,ChatColor.LIGHT_PURPLE+"Breeding Animals: "+ChatColor.YELLOW+fmt.format(r.breedingAnimals)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)"));
        item.setItemMeta(meta);
        inventory.setItem(10, item);

        //LumberJack
        item = new ItemStack(Material.IRON_AXE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Lumberjack");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by chopping trees",ChatColor.GREEN+"and planting trees","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Chopping Trees: "+ChatColor.YELLOW+fmt.format(r.breakingLogs)+c,ChatColor.LIGHT_PURPLE+"Planting Trees: "+ChatColor.YELLOW+fmt.format(r.plantingTrees)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)"));
        item.setItemMeta(meta);
        inventory.setItem(11, item);

        //Miner
        item = new ItemStack(Material.IRON_PICKAXE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Miner");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by mining considered",ChatColor.GREEN+"blocks and ores","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Mining Blocks: "+ChatColor.YELLOW+fmt.format(r.miningBlocks)+c,ChatColor.LIGHT_PURPLE+"Mining Ores: "+ChatColor.YELLOW+fmt.format(r.miningOres)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)",ChatColor.YELLOW+"(Shift Click for more info)"));
        item.setItemMeta(meta);
        inventory.setItem(12, item);

        //Hunter
        item = new ItemStack(Material.IRON_SWORD);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Hunter");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by killing hostile",ChatColor.GREEN+"mobs around your site","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Hostile Mobs: "+ChatColor.YELLOW+fmt.format(r.hunter)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)"));
        item.setItemMeta(meta);
        inventory.setItem(13, item);

        //Guardian
        item = new ItemStack(Material.IRON_CHESTPLATE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Guardian");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by protecting",ChatColor.GREEN+"villagers from hostile creatures","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Guardian: "+ChatColor.YELLOW+fmt.format(r.guardian)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)"));
        item.setItemMeta(meta);
        inventory.setItem(14, item);

        //Builder
        item = new ItemStack(Material.BRICKS);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Builder");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by building",ChatColor.GREEN+"a structures using the",ChatColor.GREEN+"considered building blocks","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Builder: "+ChatColor.YELLOW+fmt.format(r.builder)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)",ChatColor.YELLOW+"(Shift Click for more info)"));
        item.setItemMeta(meta);
        inventory.setItem(15, item);

        //Fisherman
        item = new ItemStack(Material.FISHING_ROD);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+"Fisherman");
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Earn money by fishing",ChatColor.GREEN+"and getting treasures","",ChatColor.AQUA+"Salary",ChatColor.LIGHT_PURPLE+"Fisherman: "+ChatColor.YELLOW+fmt.format(r.fisherman)+c,ChatColor.LIGHT_PURPLE+"Treasure find bonus: "+ChatColor.YELLOW+fmt.format(r.luckyFisherman)+c,ChatColor.YELLOW+"(Left Click to Apply Job)",ChatColor.YELLOW+"(Right Click to leave Job)"));
        item.setItemMeta(meta);
        inventory.setItem(16, item);

        //Information
        item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_BLUE+""+ChatColor.BOLD+"Kumandra's Economy");
        meta.setLore(Arrays.asList(
                ChatColor.LIGHT_PURPLE+"Made by: "+ChatColor.YELLOW+""+ChatColor.BOLD+"JayMar921",
                ChatColor.RED+"Youtube: "+ChatColor.YELLOW+""+ChatColor.BOLD+"JayMar921"
        ));
        item.setItemMeta(meta);
        inventory.setItem(26, item);

        return  inventory;
    }
}
