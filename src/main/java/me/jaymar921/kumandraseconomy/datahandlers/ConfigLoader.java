package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import org.bukkit.ChatColor;

public class ConfigLoader {

    static KumandrasEconomy plugin;
    RegistryConfiguration registryConfiguration;
    public ConfigLoader(KumandrasEconomy main){
        plugin = main;
        LoadConfiguration();
        registerConfiguration();
    }

    public void LoadConfiguration(){
        registryConfiguration = new RegistryConfiguration();
        if(plugin.getConfig().contains("Separate_Economy"))
            registryConfiguration.separate_economy = plugin.getConfig().getBoolean("Separate_Economy");
        if(plugin.getConfig().contains("Currency"))
            registryConfiguration.currency_economy = plugin.getConfig().getDouble("Currency");
        if(plugin.getConfig().contains("Currency_Prefix"))
            registryConfiguration.currency_prefix = plugin.getConfig().getString("Currency_Prefix");
        if(plugin.getConfig().contains("RequestTradingSessionExpiry"))
            registryConfiguration.requestTradeSessionExpiry = plugin.getConfig().getInt("RequestTradingSessionExpiry");
        if(plugin.getConfig().contains("TradingIncreaseValue"))
            registryConfiguration.tradingIncrease = plugin.getConfig().getDouble("TradingIncreaseValue");

        loadDeliveryHandler(registryConfiguration);
    }

    private void loadDeliveryHandler(RegistryConfiguration registryConfiguration){
        long cheap_timer = 180;
        long regular_timer = 180;
        long fast_timer = 180;
        long priority_timer = 180;

        double cheap_price = 25;
        double regular_price = 40;
        double fast_price = 75;
        double priority_price = 100;

        if(plugin.getConfig().contains("Cheap_Delivery_Timer"))
            cheap_timer = plugin.getConfig().getLong("Cheap_Delivery_Timer");
        if(plugin.getConfig().contains("Regular_Delivery_Timer"))
            regular_timer = plugin.getConfig().getLong("Regular_Delivery_Timer");
        if(plugin.getConfig().contains("Fast_Delivery_Timer"))
            fast_timer = plugin.getConfig().getLong("Fast_Delivery_Timer");
        if(plugin.getConfig().contains("Priority_Delivery_Timer"))
            priority_timer = plugin.getConfig().getLong("Priority_Delivery_Timer");

        if(plugin.getConfig().contains("Cheap_Delivery_Price"))
            cheap_price = plugin.getConfig().getDouble("Cheap_Delivery_Price");
        if(plugin.getConfig().contains("Regular_Delivery_Price"))
            regular_price = plugin.getConfig().getDouble("Regular_Delivery_Price");
        if(plugin.getConfig().contains("Fast_Delivery_Price"))
            fast_price = plugin.getConfig().getDouble("Fast_Delivery_Price");
        if(plugin.getConfig().contains("Priority_Delivery_Price"))
            priority_price = plugin.getConfig().getDouble("Priority_Delivery_Price");

        DeliveryDataHandler deliveryHandler = new DeliveryDataHandler();
        deliveryHandler.setCheap_delivery_price(cheap_price);
        deliveryHandler.setRegular_delivery_price(regular_price);
        deliveryHandler.setFast_delivery_price(fast_price);
        deliveryHandler.setPriority_delivery_price(priority_price);
        deliveryHandler.setCheap_delivery_timer(cheap_timer);
        deliveryHandler.setRegular_delivery_timer(regular_timer);
        deliveryHandler.setFast_delivery_timer(fast_timer);
        deliveryHandler.setPriority_delivery_timer(priority_timer);

        registryConfiguration.deliveryHandler = deliveryHandler;
    }

    public void registerConfiguration(){
        plugin.setRegistryConfiguration(registryConfiguration);
        plugin.getLogger().info(ChatColor.YELLOW+"Configuration Loaded");
    }


}
