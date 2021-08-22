package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class dataHandler {

    private Map<String, PlayerStatus> statusHolder = new HashMap<>();
    private List<String> pluginsRegistered = new ArrayList<>();
    private Map<String, Inventory> balanceGUI = new HashMap<>();
    private Map<UUID, Inventory> exchangeGUI = new HashMap<>();
    private Map<String, Inventory> deliverInventory = new HashMap<>();


    public Map<String,PlayerStatus> getStatusHolder(){
        return statusHolder;
    }

    public void setStatusHolder(Map<String,PlayerStatus> statusHolder){
        this.statusHolder = statusHolder;
    }

    public List<String> getPluginsRegistered(){ return pluginsRegistered;}
    public void setPluginsRegistered(List<String> pluginsRegistered){ this.pluginsRegistered = pluginsRegistered;}

    public Map<String, Inventory> getDeliverInventory() {return deliverInventory;}

    public Map<String, Inventory> getBalanceGUI(){ return balanceGUI;}

    public Map<UUID, Inventory> getExchangeGUI() {
        return exchangeGUI;
    }
}
