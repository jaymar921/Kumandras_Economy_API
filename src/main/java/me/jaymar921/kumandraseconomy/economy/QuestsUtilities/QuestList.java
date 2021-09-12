package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class QuestList implements Iterable<PlayerQuest>,PlayerQuestList{

    PlayerQuest[] quests;
    int list_size;
    int size;

    public QuestList(int initial_size){
        quests = new PlayerQuest[initial_size];
        list_size = initial_size;
        size = 0;
    }
    public QuestList(){
        this(10);
    }

    //sentinel methods
    public boolean isEmpty(){return size==0;}
    public boolean isFull(){return size==list_size;}

    //increase List capacity
    private void increaseCapacity(){
        PlayerQuest[] temporary = quests;
        quests = new PlayerQuest[list_size+=5];
        for(int i = 0; i < temporary.length; i++)
            quests[i] = temporary[i];
    }

    //Add quest in the list
    public boolean addQuest(PlayerQuest playerQuest){
        if(isFull())
            increaseCapacity();
        quests[size++] = playerQuest;
        return true;
    }

    //Find Quest
    private int isFound(PlayerQuest playerQuest){
        for(int i = 0; i < size; i++){
            if(quests[i] == playerQuest)
                return i;
        }
        return -1;
    }
    //Remove Quest
    public boolean removeQuest(PlayerQuest playerQuest){
        int location = isFound(playerQuest);
        if(location == -1)
            return false;
        quests[location] = null;
        for(int i = location; i < size; i++){
            if(quests[i+1]!=null) {
                quests[i] = quests[i + 1];
            }
        }
        size--;
        return true;
    }

    //Get Quest
    public PlayerQuest getQuest(Player player){
        for(int i = 0; i < size; i++)
            if(quests[i].getPlayerUuid().equals(player.getUniqueId().toString()))
                return quests[i];
        return null;
    }

    //Check if contains Quest
    public boolean containsQuest(PlayerQuest playerQuest){
        return isFound(playerQuest)!=-1;
    }
    //utility methods
    public PlayerQuest[] toArray(){
        return Arrays.copyOf(quests, size);
    }
    public int size(){return size;}
    public int capacity(){return list_size;}

    public QuestList getInstance(){return this;}

    @Override
    public Iterator<PlayerQuest> iterator()
    {
        return new Iterator<PlayerQuest>()
        {
            private int i = 0;

            /**
             * Returns true if the array has more elements.
             */
            @Override
            public boolean hasNext() {
                return (i < list_size && quests[i] != null);
            }

            /**
             * Returns the next element in the iteration.
             * @throws NoSuchElementException if the iteration has no
             * more elements
             */
            @Override
            public PlayerQuest next()
            {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return quests[i++];
            }
        };
    }
}
