package me.jaymar921.kumandraseconomy.Vault;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.EconomyImplementer;
import net.milkbowl.vault.economy.Economy;

public class VaultLoader {

    KumandrasEconomy plugin;

    private Vault vault;
    private VaultSupport vaultSupport;


    public VaultLoader(KumandrasEconomy plugin){
        this.plugin = plugin;
    }

    public void registerVault(){
        if(plugin.getRegistryConfiguration().separate_economy){
            vaultSupport = new VaultSupport(plugin);
            plugin.getRegistryConfiguration().foreign_economy = vaultSupport.EconomyName;
        }else{
            vault = new Vault(plugin);
            vault.hook();
        }

    }

    public void unregisterVault(){
        if(!plugin.getRegistryConfiguration().separate_economy)
            vault.unHook();
    }

    public VaultSupport getVaultSupport(){return vaultSupport;}
}
