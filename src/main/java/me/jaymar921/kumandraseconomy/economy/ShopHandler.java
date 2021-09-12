package me.jaymar921.kumandraseconomy.economy;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.entity.mobs.VillagerEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopHandler {

    KumandrasEconomy main = KumandrasEconomy.getPlugin(KumandrasEconomy.class);

    public void createShop(String name, Location location){
        String shop_name = ChatColor.AQUA+">"+ChatColor.YELLOW+""+ChatColor.BOLD+name+ChatColor.AQUA+"<";
        new VillagerEntity().spawnEntity(location, shop_name);

        main.getShopDataHandler().getShopLocation().put(name, location);
    }

    public Inventory cleanUp(Inventory inventory){
        ItemStack[] items = inventory.getContents();
        ItemStack[] items2 = new ItemStack[inventory.getSize()];
        for(int i = 0; i < items.length; i++) {
            if(items[i] != null)
                items2[i] = returnUnamed(items[i]);
            else
                items2[i] = items[i];
        }
        inventory.setContents(items2);
        return inventory;
    }

    public ItemStack returnUnamed(ItemStack itemStack){
        if(unwanted(itemStack.getType())){
            ItemMeta meta = itemStack.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.GREEN+" ");
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public boolean unwanted(Material material){
        if(material.equals(Material.ORANGE_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.BLACK_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.GREEN_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.GRAY_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.BLUE_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.BROWN_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.RED_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.WHITE_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.LIGHT_BLUE_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.PINK_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.LIME_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.PURPLE_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.MAGENTA_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE))
            return true;
        else if(material.equals(Material.CYAN_STAINED_GLASS_PANE))
            return true;
        else return material.equals(Material.YELLOW_STAINED_GLASS_PANE);
    }
}
