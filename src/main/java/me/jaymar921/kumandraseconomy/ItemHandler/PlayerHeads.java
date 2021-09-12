package me.jaymar921.kumandraseconomy.ItemHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class PlayerHeads {

    public ItemStack getPlayerHead(Player player){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD,1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(player);
        head.setItemMeta(meta);
        return head;
    }

    public ItemStack getPlayerHead(String name){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD,1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        for(Player player : Bukkit.getServer().getOnlinePlayers())
            if(player.getName().equals(name)) {
                assert meta != null;
                meta.setOwningPlayer(player);
                meta.setDisplayName(ChatColor.LIGHT_PURPLE+player.getName());
            }
        head.setItemMeta(meta);
        return head;
    }
}
