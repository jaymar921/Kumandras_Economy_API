package me.jaymar921.kumandraseconomy.economy.QuestsUtilities;

import org.bukkit.entity.Player;

@FunctionalInterface
public interface GenerateQuest {

    public void createQuest(Player player, PlayerQuest playerQuest);
}
