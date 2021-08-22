package me.jaymar921.kumandraseconomy.datahandlers;

public class DeliveryDataHandler {

    private long cheap_delivery_timer;
    private long regular_delivery_timer;
    private long fast_delivery_timer;
    private long priority_delivery_timer;

    private double cheap_delivery_price;
    private double regular_delivery_price;
    private double fast_delivery_price;
    private double priority_delivery_price;

    public long getCheap_delivery_timer(){return cheap_delivery_timer;}
    public long getRegular_delivery_timer(){return regular_delivery_timer;}
    public long getFast_delivery_timer(){return fast_delivery_timer;}
    public long getPriority_delivery_timer(){return priority_delivery_timer;}

    public void setCheap_delivery_timer(long timer){cheap_delivery_timer = timer;}
    public void setRegular_delivery_timer(long timer){regular_delivery_timer = timer;}
    public void setFast_delivery_timer(long timer){fast_delivery_timer = timer;}
    public void setPriority_delivery_timer(long timer) {
        priority_delivery_timer = timer;}

    public double getCheap_delivery_price() {
        return cheap_delivery_price;
    }

    public double getFast_delivery_price() {
        return fast_delivery_price;
    }

    public double getPriority_delivery_price() {
        return priority_delivery_price;
    }

    public double getRegular_delivery_price() {
        return regular_delivery_price;
    }

    public void setCheap_delivery_price(double cheap_delivery_price) {
        this.cheap_delivery_price = cheap_delivery_price;
    }

    public void setFast_delivery_price(double fast_delivery_price) {
        this.fast_delivery_price = fast_delivery_price;
    }

    public void setPriority_delivery_price(double priority_delivery_price) {
        this.priority_delivery_price = priority_delivery_price;
    }

    public void setRegular_delivery_price(double regular_delivery_price) {
        this.regular_delivery_price = regular_delivery_price;
    }
}
