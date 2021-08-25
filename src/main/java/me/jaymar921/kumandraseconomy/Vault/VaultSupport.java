package me.jaymar921.kumandraseconomy.Vault;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultSupport {

    public Economy economy;
    public String EconomyName;

    KumandrasEconomy plugin;

    public VaultSupport(KumandrasEconomy plugin) {
        this.plugin = plugin;
        StartVault();
    }

    public void StartVault() {
        if(registerVault())
            plugin.getLogger().info(ChatColor.GREEN+"Kumandra's Economy is set to secondary Economy");
    }

    public boolean registerVault() {
        RegisteredServiceProvider<Economy> eco = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if(economy == null) {
            economy = eco.getProvider();
            if(eco.getProvider().getName()!=null)
                EconomyName = eco.getPlugin().getName();
            else
                EconomyName = "Foreign";
            plugin.getDataHandler().getPluginsRegistered().add(eco.getPlugin().getName());
        }
        return (economy!=null);
    }
}
