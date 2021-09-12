package me.jaymar921.kumandraseconomy.economy;


import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerStatus implements ConfigurationSerializable {

    private String uuid;
    private double balance;
    private List<String> jobs;

    public PlayerStatus(String uuid, double balance){
        this.uuid = uuid;
        this.balance = balance;
        jobs = new ArrayList<>();
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

    public void setJobs(List<String> jobs) {
        this.jobs = jobs;
    }

    public List<String> getJobs() {
        return jobs;
    }

    @Override
    public Map<String, Object> serialize(){
        Map<String, Object> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("balance", balance);
        data.put("jobs", jobs);
        return data;
    }

    public static PlayerStatus deserialize(Map<String, Object> args){
        PlayerStatus playerStatus = new PlayerStatus((String)args.get("uuid"), (double)args.get("balance"));
        playerStatus.setJobs((List<String>) args.get("jobs"));
        return playerStatus;
    }
}
