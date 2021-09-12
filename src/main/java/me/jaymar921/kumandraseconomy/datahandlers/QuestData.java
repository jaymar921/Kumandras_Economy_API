package me.jaymar921.kumandraseconomy.datahandlers;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestData implements ConfigurationSerializable {

    private final String Title;
    private final String Type;
    private final List<String> message;
    private final String RewardType;
    private final ItemStack ItemReward;
    private final Integer expReward;
    private final String task;
    private final Integer count;
    private final Integer duration;

    public QuestData(String title, String type, List<String> message, String rewardType, ItemStack ItemReward, int expReward, String task, int count, int duration){
        Title= title;
        Type = type;
        this.message = message;
        RewardType = rewardType;
        this.ItemReward = ItemReward;
        this.expReward = expReward;
        this.task = task;
        this.count = count;
        this.duration = duration;
    }

    public String getTitle(){return Title;}
    public String getType(){return Type;}
    public List<String> getMessage(){return message;}
    public String getRewardType(){return RewardType;}
    public ItemStack getItemReward(){return ItemReward;}
    public int  getExpReward(){return expReward;}
    public String getTask(){return task;}
    public int getCount(){return count;}
    public int getDuration(){return duration;}

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("Title", Title);
        data.put("Type", Type);
        data.put("Message", message);
        data.put("RewardType", RewardType);
        data.put("ExpReward", expReward);
        data.put("ItemReward", ItemReward);
        data.put("Task", task);
        data.put("Count", count);
        data.put("Duration", duration);
        return data;
    }


    @NotNull
    @SuppressWarnings("unchecked")
    public static QuestData deserialize(@NotNull Map<String, Object> args){
        return new QuestData(
                (String)args.get("Title"),
                (String)args.get("Type"),
                (List<String>)args.get("Message"),
                (String)args.get("RewardType"),
                (ItemStack) args.get("ItemReward"),
                (Integer) args.get("ExpReward"),
                (String) args.get("Task"),
                (Integer) args.get("Count"),
                (Integer) args.get("Duration")
        );
    }
}
