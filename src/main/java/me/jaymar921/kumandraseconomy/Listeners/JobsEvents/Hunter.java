package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.entity.EntityTypeCheck;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Hunter extends Jobs implements Listener {

    public Hunter(KumandrasEconomy main){
        super(main);
    }

    @EventHandler
    private void attackingHostileMobs(EntityDeathEvent event){
        //make sure player is the attacker
        if(event.getEntity().getKiller() == null)
            return;
        Player killer = event.getEntity().getKiller();
        //check if player has the job
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(killer.getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.HUNTER.toString()))
            return;
        //get the attacked entity
        Entity attackedEntity = event.getEntity();

        if(new EntityTypeCheck().isHostile(attackedEntity)){
            double balance = playerStatus.getBalance();
            double income = main.getRegistryConfiguration().hunter;
            playerStatus.setBalance(balance+income);
            addPlayer(killer, income);
        }
    }

}
