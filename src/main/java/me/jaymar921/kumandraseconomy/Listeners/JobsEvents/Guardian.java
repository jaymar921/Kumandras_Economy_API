package me.jaymar921.kumandraseconomy.Listeners.JobsEvents;

import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class Guardian extends Jobs implements Listener {

    public Guardian(KumandrasEconomy main){
        super(main);
    }

    @EventHandler
    private void attackingHostileMobsGUARDIAN(EntityDeathEvent event){
        //make sure player is the attacker
        if(event.getEntity().getKiller() == null)
            return;
        Player killer = event.getEntity().getKiller();
        //check if player has the job
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(killer.getUniqueId().toString());
        if(!playerStatus.getJobs().contains(JobList.GUARDIAN.toString()))
            return;
        //get the attacked entity
        Entity attackedEntity = event.getEntity();
        //check if villager is in location
        checkVillagerRadius(killer);

        if(!villagerInRadius.contains(killer))
            return;

        if(mob.isHostile(attackedEntity)){
            double balance = playerStatus.getBalance();
            double income = main.getRegistryConfiguration().guardian;
            playerStatus.setBalance(balance+income);
            addPlayer(killer, income);
        }
    }

    private void checkVillagerRadius(Player player){
        for(Entity entities : player.getWorld().getNearbyEntities(player.getLocation(), main.getRegistryConfiguration().villagerRadius, 10, main.getRegistryConfiguration().villagerRadius)){
            if(entities.getType().equals(EntityType.VILLAGER)) {
                playerInVillagerRadius(player);
                return;
            }
        }
    }
}
