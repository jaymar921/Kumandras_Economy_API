package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface QuestComplete {
    public PlayerQuest completedQuest(Player player);
}
