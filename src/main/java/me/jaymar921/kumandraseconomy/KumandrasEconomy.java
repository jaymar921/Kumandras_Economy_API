package me.jaymar921.kumandraseconomy;

import me.jaymar921.kumandraseconomy.CommandExecutor.KumandraCommand;
import me.jaymar921.kumandraseconomy.CommandExecutor.KumandraTabbing;
import me.jaymar921.kumandraseconomy.CommandExecutor.TradeCommand;
import me.jaymar921.kumandraseconomy.CommandExecutor.TradeTabbing;
import me.jaymar921.kumandraseconomy.InventoryGUI.DeliveryGUI;
import me.jaymar921.kumandraseconomy.InventoryGUI.JobsGUI;
import me.jaymar921.kumandraseconomy.InventoryGUI.Supporters;
import me.jaymar921.kumandraseconomy.Listeners.*;
import me.jaymar921.kumandraseconomy.Vault.VaultLoader;
import me.jaymar921.kumandraseconomy.Vault.VaultSupport;
import me.jaymar921.kumandraseconomy.Version.VersionChecker;
import me.jaymar921.kumandraseconomy.datahandlers.*;
import me.jaymar921.kumandraseconomy.economy.DeliveryHandler;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.economy.TradingHandler;
import me.jaymar921.kumandraseconomy.entity.DeliveryEntity;
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
    private Inventory jobsGUI;
    private DeliveryHandler deliveryHandler;
    private ShopDataHandler shopDataHandler;
    private Inventory supporters;
    private VersionChecker version;
    private QuestEvent questEvent;

    private RegistryConfiguration registryConfiguration;


    @Override
    public void onEnable() {
        //save default config.yml
        saveDefaultConfig();
        //register classes
        ConfigurationSerialization.registerClass(PlayerStatus.class);
        ConfigurationSerialization.registerClass(QuestData.class);

        //Load Registry Configuration
        new ConfigLoader(this);

        //load handlers
        dataHandler = new dataHandler();
        dataHandlerLoader = new dataHandlerLoader(this);
        tradingHandler = new TradingHandler(this);
        shopDataHandler = new ShopDataHandler(this);
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
        pluginManager.registerEvents(new TradingListener(this), this);
        pluginManager.registerEvents(new ShopListener(this), this);
        pluginManager.registerEvents(new JobsListener(this), this);
        if(getRegistryConfiguration().QuestAllowed)
            pluginManager.registerEvents(questEvent, this);
        //register Commands
        registerCommands();

        //Set API
        api = new KumandrasAPI(this);
    }

    @Override
    public void onDisable() {
        saveDataLoader();
        getShopDataHandler().saveShopData();
        questEvent.flush();
        new DeliveryEntity(this).deleteMobs();
        vaultLoader.unregisterVault();
    }

    private void instantiateClasses(){
        vaultLoader = new VaultLoader(this);
        deliveryGUI = new DeliveryGUI(registryConfiguration.deliveryHandler).DeliveryUI();
        jobsGUI = new JobsGUI().joinJobUI();
        supporters = new Supporters().getSupporters();
        deliveryHandler = new DeliveryHandler(this);
        version = new VersionChecker(Bukkit.getServer().getVersion());
        questEvent = new QuestEvent(this);
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
        this.getCommand("kumandra").setTabCompleter(new KumandraTabbing());

        this.getCommand("ktrade").setExecutor(new TradeCommand(this));
        this.getCommand("ktrade").setTabCompleter(new TradeTabbing());
    }

    public TradingHandler getTradingHandler(){return tradingHandler;}

    public KumandrasAPI getApi(){
        return api;
    }


    public RegistryConfiguration getRegistryConfiguration(){return registryConfiguration;}

    public VaultSupport getVault(){ return vaultLoader.getVaultSupport();}

    public Inventory getDeliveryGUI(){return deliveryGUI;}

    public Inventory getJobsGUI(){return jobsGUI;}

    public Inventory getSupporters(){return supporters;}

    public DeliveryHandler getDeliveryHandler() { return deliveryHandler;}

    public ShopDataHandler getShopDataHandler() {return shopDataHandler;}

    public QuestEvent getQuestEvent() {return  questEvent;}

    public VersionChecker getVersion(){return version;}

    public void setRegistryConfiguration(RegistryConfiguration registryConfiguration){this.registryConfiguration =registryConfiguration;}
}
