package me.jaymar921.kumandraseconomy.datahandlers;

import org.bukkit.Material;

import java.util.List;

public class RegistryConfiguration {

    public Boolean separate_economy;
    public double currency_economy;
    public String currency_prefix;
    public String foreign_economy;
    public DeliveryDataHandler deliveryHandler;
    public int requestTradeSessionExpiry;
    public double tradingIncrease;

    //updates
    public boolean newVersionRelease = false;
    public String newVersion;

    public double cropHarvesting;
    public double breedingAnimals;
    public double plantingTrees;
    public double breakingLogs;
    public List<Material> consideredMineBlocks;
    public double miningBlocks;
    public List<Material> consideredOres;
    public double miningOres;
    public double hunter;
    public double villagerRadius;
    public double guardian;
    public List<Material> consideredBuilding;
    public double builder;
    public double fisherman;
    public double luckyFisherman;
    public int jobs;

    public double QuestChance = 0.4;
    public long QuestTime = 5;

    public boolean QuestAllowed = true;
}
