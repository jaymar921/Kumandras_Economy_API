package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dataHandler {

    private Map<String, PlayerStatus> statusHolder = new HashMap<>();
    private List<String> pluginsRegistered = new ArrayList<>();
    private Map<String, Inventory> playerInventory = new HashMap<>();
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

    public Map<String, Inventory> getPlayerInventory(){ return playerInventory;}

}
