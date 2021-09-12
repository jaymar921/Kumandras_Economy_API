package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinLeaveEvent implements Listener {

    private KumandrasEconomy plugin;
    public PlayerJoinLeaveEvent(KumandrasEconomy plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())){
            player.sendMessage(ChatColor.GOLD+"Welcome "+ChatColor.GREEN+player.getName()+ChatColor.GOLD+"!");
            player.sendMessage(ChatColor.GOLD+"["+ChatColor.GREEN+"Kumandra's Economy"+ChatColor.GOLD+"] "+ChatColor.GREEN+"Your account has been created!");
            PlayerStatus status = new PlayerStatus(player.getUniqueId().toString(),0);
            plugin.getDataHandler().getStatusHolder().put(player.getUniqueId().toString(), status);
        }



        if(player.isOp() && plugin.getRegistryConfiguration().newVersionRelease) {
            new BukkitRunnable() {
                public void run() {
                    player.sendMessage("");
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"["+ChatColor.GOLD+plugin.getDescription().getName()+ChatColor.LIGHT_PURPLE+"] "+ChatColor.GREEN+"Kumandra's Update!");
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"["+ChatColor.GOLD+plugin.getDescription().getName()+ChatColor.LIGHT_PURPLE+"] "+ChatColor.GREEN+"New version: "+ChatColor.RED+plugin.getRegistryConfiguration().newVersion);
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"["+ChatColor.GOLD+plugin.getDescription().getName()+ChatColor.LIGHT_PURPLE+"] "+ChatColor.GREEN+"Current version: "+ChatColor.RED+plugin.getDescription().getVersion());
                    player.sendMessage("");
                }
            }.runTaskLater(plugin, 20);
        }
    }
}
