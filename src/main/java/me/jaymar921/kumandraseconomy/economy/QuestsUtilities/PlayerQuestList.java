package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface PlayerQuestList {

    //Operational Methods
    public boolean addQuest(PlayerQuest playerQuest);
    public boolean removeQuest(PlayerQuest playerQuest);
    public boolean containsQuest(PlayerQuest playerQuest);
    public PlayerQuest getQuest(Player player);
    //Sentinel Methods
    public boolean isFull();
    public boolean isEmpty();
    //Utility Methods
    public int size();
    public int capacity();
    public PlayerQuest[] toArray();
    //Class Instance
    public QuestList getInstance();

}
