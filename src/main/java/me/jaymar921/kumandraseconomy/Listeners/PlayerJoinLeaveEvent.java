package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
    }
}
