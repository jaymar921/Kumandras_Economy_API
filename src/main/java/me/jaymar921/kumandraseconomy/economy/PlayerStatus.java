package me.jaymar921.kumandraseconomy.economy;


import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatus implements ConfigurationSerializable {

    private String uuid;
    private double balance;

    public PlayerStatus(String uuid, double balance){
        this.uuid = uuid;
        this.balance = balance;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public String getUuid(){
        return uuid;
    }

    public double getBalance(){
        return balance;
    }

    @Override
    public Map<String, Object> serialize(){
        Map<String, Object> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("balance", balance);
        return data;
    }

    public static PlayerStatus deserialize(Map<String, Object> args){
        return new PlayerStatus((String)args.get("uuid"), (double)args.get("balance"));
    }
}
