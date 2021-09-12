package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

public class QuestRewards{

    Object reward;
    RewardType rewardType;

    public QuestRewards(RewardType rewardType){
        this.rewardType = rewardType;
    }

    public  <Reward> void setReward (Reward reward){
        this.reward = reward;
    }

    public RewardType getRewardType(){
        return rewardType;
    }

    public <Reward>Object getReward(){
        return reward;
    }
}
