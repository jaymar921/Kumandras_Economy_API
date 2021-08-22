package me.jaymar921.kumandraseconomy.Vault;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.EconomyImplementer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class Vault {

    KumandrasEconomy plugin;

    private Economy economy;
    public Vault(KumandrasEconomy plugin){
        this.plugin = plugin;
    }

    public void hook(){
        economy = new EconomyImplementer(plugin);
        Bukkit.getServicesManager().register(Economy.class, economy , plugin, ServicePriority.Normal);
        plugin.getLogger().info(ChatColor.GREEN+"Vault API hooked");
    }

    public void unHook(){
        Bukkit.getServicesManager().unregister(Economy.class, economy);
    }
}
