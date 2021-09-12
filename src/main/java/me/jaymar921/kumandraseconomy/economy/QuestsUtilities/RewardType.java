package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

public enum RewardType {
    EXP(0),
    MONEY(1),
    ITEM(2);

    private final int id;
    RewardType(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
