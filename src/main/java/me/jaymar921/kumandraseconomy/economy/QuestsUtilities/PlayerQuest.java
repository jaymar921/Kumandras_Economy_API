package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

import java.util.List;

public class PlayerQuest implements Quest{

    private final QuestType questType;
    private final String questTitle;
    private List<String> questMessage;
    private QuestRewards questRewards;
    private final String questTask;
    private int count;
    private Long duration;
    private final String PlayerUuid;

    public PlayerQuest(String PlayerUuid, QuestType questType, String questTitle, String questTask, int count){
        this.questType = questType;
        this.questTitle = questTitle;
        this.PlayerUuid = PlayerUuid;
        this.questTask = questTask;
        this.count = count;
    }

    public void setQuestMessage(List<String> message){
        this.questMessage = message;
    }
    public void setQuestRewards(QuestRewards questRewards){
        this.questRewards =questRewards;
    }
    public void setQuestDuration(Long duration){
        this.duration = duration;
    }
    public void setQuestCount(int count){this.count = count;}

    public QuestType getQuestType(){
        return questType;
    }
    public String getQuestTitle(){
        return questTitle;
    }
    public List<String> getQuestMessage(){
        return questMessage;
    }
    public QuestRewards getQuestRewards(){
        return questRewards;
    }
    public Long getQuestDuration(){
        return duration;
    }
    public String getPlayerUuid(){return PlayerUuid;}
    public String getQuestTask(){return questTask;}
    public int getQuestCount(){return count;}


}
