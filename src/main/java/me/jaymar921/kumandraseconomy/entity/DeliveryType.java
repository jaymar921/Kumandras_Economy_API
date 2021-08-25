package me.jaymar921.kumandraseconomy.entity;

public enum DeliveryType {
    CHEAP(1),
    REGULAR(2),
    FAST(3),
    PRIORITY(4);

    private final int value;

    DeliveryType(int value){
        this.value = value;
    }

    public int getValue(){return value;}

}
