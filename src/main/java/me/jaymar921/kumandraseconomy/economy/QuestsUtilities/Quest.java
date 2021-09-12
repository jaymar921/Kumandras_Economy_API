package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

import org.bukkit.entity.Player;

import java.util.List;

public interface Quest {

    public void setQuestMessage(List<String> message);
    public void setQuestRewards(QuestRewards questRewards);
    public void setQuestDuration(Long duration);
    public void setQuestCount(int count);

    public QuestType getQuestType();
    public String getQuestTitle();
    public List<String> getQuestMessage();
    public QuestRewards getQuestRewards();
    public Long getQuestDuration();
    public String getPlayerUuid();
    public String getQuestTask();
    public int getQuestCount();
}
