package me.jaymar921.kumandraseconomy.InventoryGUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Supporters {

    public Inventory getSupporters(){
        Inventory ui = Bukkit.createInventory(null, 36, ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"Plugin Supporters");

        for(int i = 0; i < 36; i++){
            ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE+"♥");
            item.setItemMeta(meta);
            ui.setItem(i,item);
        }
        ItemStack item = new ItemStack(Material.DARK_OAK_SIGN);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        for(int i = 0; i < 36; i++){
            item = ui.getItem(i);
            assert item != null;
            meta = item.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY,1,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            ui.setItem(i,item);
        }

        return ui;
    }

    public ItemStack getHead(Player playerName){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();

        skull.setOwningPlayer(playerName);
        item.setItemMeta(skull);
        return item;
    }


}
