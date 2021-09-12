package me.jaymar921.kumandraseconomy.datahandlers;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.Configurations.CustomQuest;
import me.jaymar921.kumandraseconomy.datahandlers.Configurations.QuestConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.LinkedList;
import java.util.List;

public class QuestDataHandler {


    private final QuestConfiguration questConfiguration;
    private CustomQuest customQuest;
    private List<QuestData> questData;
    private boolean hasAnimalQuest = false;
    private boolean hasVillagerQuest = false;
    public QuestDataHandler(KumandrasEconomy main){
        questData = new LinkedList<>();
        questConfiguration = new QuestConfiguration(main);
        main.getLogger().info(ChatColor.GREEN+"Loaded ["+loadData()+ "] Kumandra's Economy Quests");

        loadQuests();
        if(Bukkit.getServer().getPluginManager().getPlugin("CustomEnchantments")!=null){
            if(!Bukkit.getServer().getPluginManager().getPlugin("CustomEnchantments").getDescription().getAuthors().contains("JayMar921"))
                return;
            customQuest = new CustomQuest(main);
            main.getLogger().info(ChatColor.GREEN+"Loaded ["+loadCustomQuest()+ "] Quests from Custom Enchantments");
        }
    }
    public void loadQuests(){
        for(QuestData q : questData)
            if(q.getType().toLowerCase().contains("villager"))
                hasVillagerQuest = true;
            else if(q.getType().toLowerCase().contains("animal"))
                hasAnimalQuest = true;
    }


    public List<QuestData> getQuestData() {
        return questData;
    }
    public boolean hasAnimalQuest(){return hasAnimalQuest;}
    public boolean hasVillagerQuest(){return hasVillagerQuest;}
    @SuppressWarnings("unchecked")
    public int loadData(){
        if(questConfiguration.getConfig().contains("QUESTS"))
            questData = (List<QuestData>) questConfiguration.getConfig().getList("QUESTS");
        return questData.size();
    }

    @SuppressWarnings("unchecked")
    public int loadCustomQuest(){
        if(customQuest.getConfig().contains("QUESTS")){
            List<QuestData> data = (List<QuestData>) customQuest.getConfig().getList("QUESTS");
            for(QuestData d : data)
                questData.add(d);
            return data.size();
        }
        return 0;
    }


}
