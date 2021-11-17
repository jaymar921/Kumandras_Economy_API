package me.jaymar921.kumandraseconomy.InventoryGUI;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.Configurations.LanguageConfig;
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
import java.util.Map;

public class JobsGUI {

    String c;
    RegistryConfiguration r;
    DecimalFormat fmt = new DecimalFormat("#.##");
    private final Map<String,String> lang;
    public JobsGUI(){
        KumandrasEconomy main = KumandrasEconomy.getPlugin(KumandrasEconomy.class);
        lang = main.getDataHandler().getLanguageData();
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
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("Farmer"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+lang.get("F1"),ChatColor.GREEN+lang.get("F2"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("F3")+" "+ChatColor.YELLOW+fmt.format(r.cropHarvesting)+c,ChatColor.LIGHT_PURPLE+lang.get("F4")+" "+ChatColor.YELLOW+fmt.format(r.breedingAnimals)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob")));
        item.setItemMeta(meta);
        inventory.setItem(10, item);

        //LumberJack
        item = new ItemStack(Material.IRON_AXE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("LumberJack"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+lang.get("LM1"),ChatColor.GREEN+lang.get("LM2"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("LM3")+" "+ChatColor.YELLOW+fmt.format(r.breakingLogs)+c,ChatColor.LIGHT_PURPLE+lang.get("LM4")+" "+ChatColor.YELLOW+fmt.format(r.plantingTrees)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob")));
        item.setItemMeta(meta);
        inventory.setItem(11, item);

        //Miner
        item = new ItemStack(Material.IRON_PICKAXE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("Miner"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+lang.get("MN1"),ChatColor.GREEN+lang.get("MN2"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("MN3")+" "+ChatColor.YELLOW+fmt.format(r.miningBlocks)+c,ChatColor.LIGHT_PURPLE+lang.get("MN4")+" "+ChatColor.YELLOW+fmt.format(r.miningOres)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob"),ChatColor.YELLOW+lang.get("ShiftClickJob")));
        item.setItemMeta(meta);
        inventory.setItem(12, item);

        //Hunter
        item = new ItemStack(Material.IRON_SWORD);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("Hunter"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+ lang.get("HN1"),ChatColor.GREEN+lang.get("HN2"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("HN3")+" "+ChatColor.YELLOW+fmt.format(r.hunter)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob")));
        item.setItemMeta(meta);
        inventory.setItem(13, item);

        //Guardian
        item = new ItemStack(Material.IRON_CHESTPLATE);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("Guardian"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+lang.get("GD1"),ChatColor.GREEN+lang.get("GD2"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("GD3")+" "+ChatColor.YELLOW+fmt.format(r.guardian)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob")));
        item.setItemMeta(meta);
        inventory.setItem(14, item);

        //Builder
        item = new ItemStack(Material.BRICKS);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("Builder"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+lang.get("BD1"),ChatColor.GREEN+lang.get("BD2"),ChatColor.GREEN+lang.get("BD3"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("BD4")+" "+ChatColor.YELLOW+fmt.format(r.builder)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob"),ChatColor.YELLOW+lang.get("ShiftClickJob")));
        item.setItemMeta(meta);
        inventory.setItem(15, item);

        //Fisherman
        item = new ItemStack(Material.FISHING_ROD);
        meta = item.getItemMeta();
        assert meta != null;
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.DARK_AQUA+""+ChatColor.BOLD+lang.get("Fisherman"));
        meta.setLore(Arrays.asList(ChatColor.GREEN+lang.get("FS1"),ChatColor.GREEN+lang.get("FS2"),"",ChatColor.AQUA+lang.get("Salary"),ChatColor.LIGHT_PURPLE+lang.get("FS3")+" "+ChatColor.YELLOW+fmt.format(r.fisherman)+c,ChatColor.LIGHT_PURPLE+lang.get("FS4")+" "+ChatColor.YELLOW+fmt.format(r.luckyFisherman)+c,ChatColor.YELLOW+lang.get("LeftClickJob"),ChatColor.YELLOW+lang.get("RightClickJob")));
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
                ChatColor.RED+"Youtube: "+ChatColor.YELLOW+""+ChatColor.BOLD+"JayMar921",
                ChatColor.DARK_PURPLE+"Discord: "+ChatColor.YELLOW+""+ChatColor.BOLD+"dhCTCHJXkE"
        ));
        item.setItemMeta(meta);
        inventory.setItem(26, item);

        return  inventory;
    }
}
