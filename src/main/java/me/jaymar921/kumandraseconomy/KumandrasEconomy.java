package me.jaymar921.kumandraseconomy;

import me.jaymar921.kumandraseconomy.CommandExecutor.KumandraCommand;
import me.jaymar921.kumandraseconomy.CommandExecutor.Tabbing;
import me.jaymar921.kumandraseconomy.CommandExecutor.TradeCommand;
import me.jaymar921.kumandraseconomy.Listeners.InventoryClick;
import me.jaymar921.kumandraseconomy.Listeners.PlayerJoinLeaveEvent;
import me.jaymar921.kumandraseconomy.datahandlers.dataHandler;
import me.jaymar921.kumandraseconomy.datahandlers.dataHandlerLoader;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.economy.TradingHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class KumandrasEconomy extends JavaPlugin {

    private dataHandler dataHandler;
    private dataHandlerLoader dataHandlerLoader;
    private KumandrasAPI api;
    private TradingHandler tradingHandler;

    @Override
    public void onEnable() {
        //register classes
        ConfigurationSerialization.registerClass(PlayerStatus.class);

        //load handlers
        dataHandler = new dataHandler();
        dataHandlerLoader = new dataHandlerLoader(this);
        tradingHandler = new TradingHandler();
        loadDataLoader();

        //register Events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinLeaveEvent(this),this);
        pluginManager.registerEvents(new InventoryClick(this), this);

        //register Commands
        registerCommands();

        //Set API
        api = new KumandrasAPI(this);
    }

    @Override
    public void onDisable() {
        saveDataLoader();
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

}
