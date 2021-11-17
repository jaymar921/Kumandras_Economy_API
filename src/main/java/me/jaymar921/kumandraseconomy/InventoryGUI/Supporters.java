package me.jaymar921.kumandraseconomy.InventoryGUI;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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

        ui.setItem(10,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM3ODY4YjQ3M2YyNWQ0MDAxZjhiNjQ0NDQ1ODMzYmUwMWZhMzIzYTgyNGY3ZWU2ZDU0ZjIzN2Y1ZDZkNTVmYyJ9fX0", "JayMar920", "Developer"));

        ui.setItem(11,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjQ2Mjg4Zjc0YzVhZGYyYzNhNTRjOGM0NjJkMWM0N2IwYjcwZWEwZGZjMzliNThjNDg3OWY5NmQ1YWY1NWEyYyJ9fX0", "JayMar921", "Developer"));

        ui.setItem(12,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxNWM0NjQ0ZjkxYWY5NGZhYWZjZjU4NWFlMmIzMTYyMDg1ZjYxMjk4NTAzOGM5ZTkzYzc4MzMzZTczY2FhNiJ9fX0","MikaPikaChu", "Supporter"));

        ui.setItem(13,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVmODg5MjJjYmJjNjBjODNkMmM4ZmMxYjgwYzE3NmJjYmRmODNmODliMTdmMDMzMDE5Y2VkOTI2YWZlYTY0YSJ9fX0","JhonoBrine", "Supporter","A 'Special' Person that you can blame for the Phoenix/Stella"));

        ui.setItem(14,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAwNDhlODQ3MGM0MTYxMjI2OWZmZmU1ZTkyODI4MmI3NjlmYjVhNzU1ZDkyNzg5Njk0NjA5Y2EzNWQwZWU2NyJ9fX0","Eliteleonidus", "Supporter"));

        ui.setItem(15,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE1ZTBjMGFhNGI1ZDQ3YWQyNWU4NGQzYzFlN2IyYjJiZTZmNjczMTY0MDZhMmFiMmZkMDYzM2ZmODY2OWM1ZiJ9fX0","Pirschjagerr", "Supporter"));

        ui.setItem(16,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAwNDhlODQ3MGM0MTYxMjI2OWZmZmU1ZTkyODI4MmI3NjlmYjVhNzU1ZDkyNzg5Njk0NjA5Y2EzNWQwZWU2NyJ9fX0","Sekai47", "Developer"));

        ui.setItem(19,createSkull("eyJ0aW1lc3RhbXAiOjE1ODcyOTkyOTczNDYsInByb2ZpbGVJZCI6ImQ2MGYzNDczNmExMjQ3YTI5YjgyY2M3MTViMDA0OGRiIiwicHJvZmlsZU5hbWUiOiJCSl9EYW5pZWwiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzMxYjlhODYxOGY3Yjc4ZTdlMDgxMDk2MTZlMDdjN2Q5MWRmYjRlZWE3NmI2NDQ3MjZhMzQxMTJlNWE0MjJmZGYifX19", "Playeruan", "Supporter","Discord: "+ChatColor.LIGHT_PURPLE+"discord.gg/Mm4DYJvMBW"));

        ui.setItem(20,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAwNDhlODQ3MGM0MTYxMjI2OWZmZmU1ZTkyODI4MmI3NjlmYjVhNzU1ZDkyNzg5Njk0NjA5Y2EzNWQwZWU2NyJ9fX0", "Amiz", "Supporter"));

        ui.setItem(21,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmI5NTIwOWQzNmM1YWExYmY2YzZmMzA3ZjA5YjE2ZDkwNTg4NDRhYzU2MDM0MGM4OGY4Mzk0NjgyZWY1N2EwYSJ9fX0", "Jay Gameplay", "Supporter"));

        ui.setItem(22,createSkull("ewogICJ0aW1lc3RhbXAiIDogMTU5ODIyMTk0NDIxOCwKICAicHJvZmlsZUlkIiA6ICIwZjczMDA3NjEyNGU0NGM3YWYxMTE1NDY5YzQ5OTY3OSIsCiAgInByb2ZpbGVOYW1lIiA6ICJPcmVfTWluZXIxMjMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzAyZDQ1MDNlMDk0MDZhZmZmYjk3ODY3NjI2MjM1Yjg3OTM5ZDJhYTM5NWM2ZWRhZjA0MTQ4ZDcxNTdkMjk5OCIKICAgIH0KICB9Cn0=", "DeadeyeSkunk", "Supporter"));

        ui.setItem(23,createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAwNDhlODQ3MGM0MTYxMjI2OWZmZmU1ZTkyODI4MmI3NjlmYjVhNzU1ZDkyNzg5Njk0NjA5Y2EzNWQwZWU2NyJ9fX0=", "Tattoodude72614", "Supporter (Roger Turley's partner)","Discord: "+ChatColor.LIGHT_PURPLE+"discord.gg/DpT3QVPGxZ"));

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

    private static ItemStack createSkull(String url, String name, String lore) {
        @SuppressWarnings("deprecation")
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        headMeta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+name);
        headMeta.setLore(Arrays.asList(ChatColor.GREEN+lore));

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    private static ItemStack createSkull(String url, String name, String lore, String lore2) {
        @SuppressWarnings("deprecation")
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if (url.isEmpty())
            return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        headMeta.setDisplayName(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+name);
        List<String> lores = new ArrayList<>();
        lores.add(ChatColor.GREEN+lore);
        lores.add(ChatColor.GREEN+lore2);
        headMeta.setLore(lores);

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
}
