package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.Configurations.PlayerStatusDataHandler;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class dataHandlerLoader {

    KumandrasEconomy plugin;
    PlayerStatusDataHandler playerData;

    public dataHandlerLoader(KumandrasEconomy plugin){
        this.plugin = plugin;
        playerData = new PlayerStatusDataHandler(plugin);
    }

    @SuppressWarnings("unchecked")
    public void loadData(){
        if(plugin.getMySQLConfig().getConfig().contains("EnableDatabase"))
            if(plugin.getMySQLConfig().getConfig().getBoolean("EnableDatabase"))
                if(MySQLHandler.loadData(plugin))
                    return;
        //load Status Holder
        if(playerData.getConfig().contains("PlayerData")){
            List<Map<?,?>> DATA = playerData.getConfig().getMapList("PlayerData");
            for(Map<?,?> d : DATA)
                plugin.getDataHandler().setStatusHolder((Map<String, PlayerStatus>) d);
        }
    }

    public void saveData(){
        if(plugin.getMySQLConfig().getConfig().contains("EnableDatabase"))
            if(plugin.getMySQLConfig().getConfig().getBoolean("EnableDatabase"))
                if(MySQLHandler.saveData(plugin))
                    return;
        //save Status Holder
        List<Map<String, PlayerStatus>> DATA = new ArrayList<>();
        DATA.add(plugin.getDataHandler().getStatusHolder());
        playerData.getConfig().set("PlayerData", DATA);
        playerData.saveConfig();
    }
}
