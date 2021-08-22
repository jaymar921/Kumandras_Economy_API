package me.jaymar921.kumandraseconomy;

import me.jaymar921.kumandraseconomy.CommandExecutor.KumandraCommand;
import me.jaymar921.kumandraseconomy.CommandExecutor.Tabbing;
import me.jaymar921.kumandraseconomy.CommandExecutor.TradeCommand;
import me.jaymar921.kumandraseconomy.InventoryGUI.DeliveryGUI;
import me.jaymar921.kumandraseconomy.Listeners.InteractEvents;
import me.jaymar921.kumandraseconomy.Listeners.InventoryClick;
import me.jaymar921.kumandraseconomy.Listeners.PlayerJoinLeaveEvent;
import me.jaymar921.kumandraseconomy.Vault.Vault;
import me.jaymar921.kumandraseconomy.Vault.VaultLoader;
import me.jaymar921.kumandraseconomy.Vault.VaultSupport;
import me.jaymar921.kumandraseconomy.datahandlers.ConfigLoader;
import me.jaymar921.kumandraseconomy.datahandlers.RegistryConfiguration;
import me.jaymar921.kumandraseconomy.datahandlers.dataHandler;
import me.jaymar921.kumandraseconomy.datahandlers.dataHandlerLoader;
import me.jaymar921.kumandraseconomy.economy.DeliveryHandler;
import me.jaymar921.kumandraseconomy.economy.EconomyImplementer;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.economy.TradingHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KumandrasEconomy extends JavaPlugin {

    private dataHandler dataHandler;
    private dataHandlerLoader dataHandlerLoader;
    private KumandrasAPI api;
    private TradingHandler tradingHandler;
    private VaultLoader vaultLoader;
    private Inventory deliveryGUI;
    private DeliveryHandler deliveryHandler;

    private RegistryConfiguration registryConfiguration;


    @Override
    public void onEnable() {
        //save default config.yml
        saveDefaultConfig();
        //register classes
        ConfigurationSerialization.registerClass(PlayerStatus.class);

        //Load Registry Configuration
        new ConfigLoader(this);

        //load handlers
        dataHandler = new dataHandler();
        dataHandlerLoader = new dataHandlerLoader(this);
        tradingHandler = new TradingHandler();
        loadDataLoader();

        //instantiate classes
        instantiateClasses();
        //Register Vault
        vaultLoader.registerVault();
        //register Events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinLeaveEvent(this),this);
        pluginManager.registerEvents(new InventoryClick(this), this);
        pluginManager.registerEvents(new InteractEvents(this), this);

        //register Commands
        registerCommands();

        //Set API
        api = new KumandrasAPI(this);
    }

    @Override
    public void onDisable() {
        saveDataLoader();
        vaultLoader.unregisterVault();
    }

    private void instantiateClasses(){
        vaultLoader = new VaultLoader(this);
        deliveryGUI = new DeliveryGUI(registryConfiguration.deliveryHandler).DeliveryUI();
        deliveryHandler = new DeliveryHandler(this);
    }

    public dataHandler getDataHandler(){
        return dataHandler;
    }

    private void loadDataLoader(){
        dataHandlerLoader.loadData();
    }

    private void saveDataLoader(){
        dataHandlerLoader.saveData();
    }

    private void registerCommands(){
        this.getCommand("kumandra").setExecutor(new KumandraCommand(this));
        this.getCommand("kumandra").setTabCompleter(new Tabbing());

        this.getCommand("trade").setExecutor(new TradeCommand(this));
    }

    public TradingHandler getTradingHandler(){return tradingHandler;}

    public KumandrasAPI getApi(){
        return api;
    }


    public RegistryConfiguration getRegistryConfiguration(){return registryConfiguration;}

    public VaultSupport getVault(){ return vaultLoader.getVaultSupport();}

    public Inventory getDeliveryGUI(){return deliveryGUI;}

    public DeliveryHandler getDeliveryHandler() { return deliveryHandler;}

    public void setRegistryConfiguration(RegistryConfiguration registryConfiguration){this.registryConfiguration =registryConfiguration;}
}
