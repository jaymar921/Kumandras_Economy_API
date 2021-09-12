package me.jaymar921.kumandraseconomy.InventoryGUI.enums;

public enum JobList {

    FARMER("Farmer"),
    LUMBERJACK("Lumberjack"),
    MINER("Miner"),
    HUNTER("Hunter"),
    GUARDIAN("Guardian"),
    BUILDER("Builder"),
    FISHERMAN("Fisherman");

    private final String job;

    JobList(String job){
        this.job = job;
    }

    public String toString(){
        return job;
    }
}
