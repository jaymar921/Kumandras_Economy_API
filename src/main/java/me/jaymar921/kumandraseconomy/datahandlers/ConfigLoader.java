package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.Version.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.EnumSet;
import java.util.List;
import java.util.Vector;

public class ConfigLoader {

    static KumandrasEconomy plugin;
    RegistryConfiguration registryConfiguration;
    public ConfigLoader(KumandrasEconomy main){
        plugin = main;
        LoadConfiguration();
        registerConfiguration();
        checkForUpdate();
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
        if(plugin.getConfig().contains("QuestChance"))
            registryConfiguration.QuestChance = plugin.getConfig().getDouble("QuestChance");
        if(plugin.getConfig().contains("QuestInterval"))
            registryConfiguration.QuestTime = plugin.getConfig().getLong("QuestInterval");

        if(plugin.getConfig().contains("AllowQuest"))
            registryConfiguration.QuestAllowed = plugin.getConfig().getBoolean("AllowQuest");

        loadJobs(registryConfiguration);
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

    public void loadJobs(RegistryConfiguration registryConfiguration){
        double cropHarvesting = 0.25;
        double breedingAnimals = 0.53;
        double breakingLogs = 0.22;
        double plantingTrees = 0.34;
        List<Material> consideredMiningBlocks = new Vector<>();
        consideredMiningBlocks.add(Material.STONE);
        consideredMiningBlocks.add(Material.DIORITE);
        consideredMiningBlocks.add(Material.ANDESITE);
        consideredMiningBlocks.add(Material.GRANITE);
        consideredMiningBlocks.add(Material.PRISMARINE);
        double miningBlocks = 0.20;
        List<Material> consideredMiningOres = new Vector<>();
        consideredMiningBlocks.add(Material.COAL_ORE);
        consideredMiningBlocks.add(Material.IRON_ORE);
        consideredMiningBlocks.add(Material.GOLD_ORE);
        consideredMiningBlocks.add(Material.DIAMOND_ORE);
        consideredMiningBlocks.add(Material.EMERALD_ORE);
        consideredMiningBlocks.add(Material.NETHER_GOLD_ORE);
        consideredMiningBlocks.add(Material.NETHER_QUARTZ_ORE);
        double miningOres = 0.32;
        double hunter = 0.30;
        double villagerRadius = 20.0;
        double guardian = 0.35;
        List<Material> consideredBuildingBlocks = new Vector<>();
        double builder = 0.15;
        double fisherman = 0.23;
        double luckyFisherman = 0.35;
        int jobs = 2;

        //Load config
        if(plugin.getConfig().contains("CropsHarvesting"))
            cropHarvesting = plugin.getConfig().getDouble("CropsHarvesting");
        if(plugin.getConfig().contains("BreedingAnimals"))
            breedingAnimals = plugin.getConfig().getDouble("BreedingAnimals");
        if(plugin.getConfig().contains("BreakingLogs"))
            breakingLogs = plugin.getConfig().getDouble("BreakingLogs");
        if(plugin.getConfig().contains("PlantingTrees"))
            plantingTrees = plugin.getConfig().getDouble("PlantingTrees");
        if(plugin.getConfig().contains("MiningBlocks"))
            miningBlocks = plugin.getConfig().getDouble("MiningBlocks");
        if(plugin.getConfig().contains("MiningOres"))
            miningOres = plugin.getConfig().getDouble("MiningOres");
        if(plugin.getConfig().contains("Hunter"))
            hunter = plugin.getConfig().getDouble("Hunter");
        if(plugin.getConfig().contains("Guardian"))
            guardian = plugin.getConfig().getDouble("Guardian");
        if(plugin.getConfig().contains("CropsHarvesting"))
            builder = plugin.getConfig().getDouble("Builder");
        if(plugin.getConfig().contains("Fisherman"))
            fisherman = plugin.getConfig().getDouble("Fisherman");
        if(plugin.getConfig().contains("LuckyFisherman"))
            luckyFisherman = plugin.getConfig().getDouble("LuckyFisherman");
        if(plugin.getConfig().contains("Jobs"))
            jobs = plugin.getConfig().getInt("Jobs");

        if(plugin.getConfig().contains("ConsideredMiningBlocks")){
            List<String> strings = plugin.getConfig().getStringList("ConsideredMiningBlocks");
            List<Material> blocks = new Vector<>();
            for(String material : strings){
                EnumSet.allOf(Material.class).forEach(mats ->{
                    if(mats.toString().equals(material))
                        blocks.add(mats);
                });
            }
            consideredMiningBlocks = blocks;
        }
        if(plugin.getConfig().contains("ConsideredMiningOres")){
            List<String> strings = plugin.getConfig().getStringList("ConsideredMiningOres");
            List<Material> blocks = new Vector<>();
            for(String material : strings){
                EnumSet.allOf(Material.class).forEach(mats ->{
                    if(mats.toString().equals(material))
                        blocks.add(mats);
                });
            }
            consideredMiningOres = blocks;
        }
        if(plugin.getConfig().contains("ConsideredBlocksForBuilding")){
            List<String> strings = plugin.getConfig().getStringList("ConsideredBlocksForBuilding");
            List<Material> blocks = new Vector<>();
            if(!strings.contains("DEFAULT"))
            for(String material : strings){
                EnumSet.allOf(Material.class).forEach(mats ->{
                    if(mats.toString().equals(material))
                        blocks.add(mats);
                });
            }
            consideredBuildingBlocks = blocks;
        }

        registryConfiguration.cropHarvesting = cropHarvesting;
        registryConfiguration.breedingAnimals = breedingAnimals;
        registryConfiguration.breakingLogs = breakingLogs;
        registryConfiguration.plantingTrees = plantingTrees;
        registryConfiguration.consideredMineBlocks = consideredMiningBlocks;
        registryConfiguration.miningBlocks = miningBlocks;
        registryConfiguration.consideredOres = consideredMiningOres;
        registryConfiguration.miningOres = miningOres;
        registryConfiguration.hunter = hunter;
        registryConfiguration.villagerRadius = villagerRadius;
        registryConfiguration.guardian = guardian;
        registryConfiguration.consideredBuilding = consideredBuildingBlocks;
        registryConfiguration.builder = builder;
        registryConfiguration.fisherman = fisherman;
        registryConfiguration.luckyFisherman = luckyFisherman;
        registryConfiguration.jobs = jobs;

    }

    public void registerConfiguration(){
        plugin.setRegistryConfiguration(registryConfiguration);
        plugin.getLogger().info(ChatColor.YELLOW+"Configuration Loaded");
    }

    private void checkForUpdate(){
        new UpdateChecker(plugin, 89793).getVersion(version -> {
            if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                plugin.getLogger().info("\u001B[33m----------[Kumandra's Economy]----------");
                plugin.getLogger().info("\u001B[32mYou installed the latest version. [\u001B[36m"+version+"\u001B[32m]");
                plugin.getLogger().info("\u001B[32mInfo at: \u001B[34m"+plugin.getDescription().getWebsite());
                plugin.getLogger().info("\u001B[32mSupport the developer :)");
                plugin.getLogger().info("\u001B[32mYoutube: \u001B[34mhttps://www.youtube.com/c/jaymar921/");
                plugin.getLogger().info("\u001B[33m-----------------------------------------"+"\u001B[35m]");
            } else {
                plugin.getLogger().info("\u001B[35m----------[Kumandra's Economy]----------");
                plugin.getLogger().info("\u001B[35mYou are currently using [\u001B[31m"+plugin.getDescription().getVersion()+"\u001B[35m]");
                plugin.getLogger().info("\u001B[35mThere's a new update available [\u001B[31m"+version+"\u001B[35m]");
                plugin.getLogger().info("\u001B[35mLink: \u001B[32m"+plugin.getDescription().getWebsite());
                plugin.getLogger().info("\u001B[35mSupport the developer :)");
                plugin.getLogger().info("\u001B[35mYoutube: \u001B[32mhttps://www.youtube.com/c/jaymar921/");
                plugin.getLogger().info("\u001B[35m-----------------------------------------"+"\u001B[35m]");

                plugin.getRegistryConfiguration().newVersion = version;
                plugin.getRegistryConfiguration().newVersionRelease = true;
            }
        });
    }


}
