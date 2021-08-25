package me.jaymar921.kumandraseconomy.datahandlers;

import org.bukkit.entity.Player;

public class TradingData {

    private Player owner;
    private double price;
    private String role;
    public boolean isSet;
    public boolean isPaid;

    public double getPrice() {
        return price;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
