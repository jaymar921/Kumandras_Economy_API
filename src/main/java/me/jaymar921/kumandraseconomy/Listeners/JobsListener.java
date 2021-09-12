package me.jaymar921.kumandraseconomy.Listeners;
import me.jaymar921.kumandraseconomy.InventoryGUI.enums.JobList;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.Listeners.JobsEvents.*;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.EnumSet;
import java.util.List;
import java.util.Vector;

public class JobsListener implements Listener {

    KumandrasEconomy main;
    public JobsListener(KumandrasEconomy main){
        this.main = main;

        //register Jobs Handlers
        Bukkit.getServer().getPluginManager().registerEvents(new Farmer(main), main);
        Bukkit.getServer().getPluginManager().registerEvents(new Lumberjack(main), main);
        Bukkit.getServer().getPluginManager().registerEvents(new Miner(main), main);
        Bukkit.getServer().getPluginManager().registerEvents(new Hunter(main), main);
        Bukkit.getServer().getPluginManager().registerEvents(new Guardian(main), main);
        Bukkit.getServer().getPluginManager().registerEvents(new Builder(main), main);
        Bukkit.getServer().getPluginManager().registerEvents(new Fisherman(main), main);
    }

    @EventHandler
    private void JOBSItemClickDeny(InventoryClickEvent event){
        if(event.getClickedInventory()!=null)
            if(event.getClickedInventory().equals(main.getJobsGUI()))
                event.setCancelled(true);
    }

    @EventHandler
    private void supportersItemClickDeny(InventoryClickEvent event){
        if(event.getClickedInventory()!=null)
            if(event.getClickedInventory().equals(main.getSupporters()))
                event.setCancelled(true);
    }

    @EventHandler
    private void applyJob(InventoryClickEvent event){
        if(event.getClickedInventory()!= main.getJobsGUI())
            return;
        Player player = (Player)event.getWhoClicked();
        PlayerStatus playerStatus = main.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
        ClickType clickType = event.getClick();
        int slot = event.getRawSlot();

        List<JobList> playerJobs = new Vector<>();
        for(String jobs : playerStatus.getJobs())
            EnumSet.allOf(JobList.class).forEach(job -> {
                if(job.toString().contains(jobs))
                    playerJobs.add(job);
            });

        if(playerJobs.size()>= main.getRegistryConfiguration().jobs && clickType.isLeftClick() && !clickType.isShiftClick() && (slot>=10) && (slot<=16)){
            player.sendMessage(ChatColor.RED+"You have reached the maximum number of jobs");
            return;
        }

        switch (slot){
            case 10:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.FARMER)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.FARMER.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Farmer");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.FARMER)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.FARMER.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Farmer");
                }
                break;
            case 11:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.LUMBERJACK)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.LUMBERJACK.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Lumberjack");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.LUMBERJACK)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.LUMBERJACK.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Lumberjack");
                }
                break;
            case 12:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.MINER)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.MINER.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Miner");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.MINER)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.MINER.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Miner");
                }else if(clickType.isShiftClick()){
                    player.sendMessage(ChatColor.GREEN+"Considered mining blocks: "+ChatColor.YELLOW+main.getRegistryConfiguration().consideredMineBlocks.toString());
                    player.sendMessage(ChatColor.GREEN+"Considered ore blocks: "+ChatColor.YELLOW+main.getRegistryConfiguration().consideredOres.toString());
                }
                break;
            case 13:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.HUNTER)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.HUNTER.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Hunter");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.HUNTER)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.HUNTER.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Hunter");
                }
                break;
            case 14:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.GUARDIAN)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.GUARDIAN.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Guardian");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.GUARDIAN)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.GUARDIAN.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Guardian");
                }
                break;
            case 15:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.BUILDER)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.BUILDER.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Builder");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.BUILDER)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.BUILDER.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Builder");
                }else if(clickType.isShiftClick()){
                    if(!main.getRegistryConfiguration().consideredBuilding.isEmpty())
                        player.sendMessage(ChatColor.GREEN+"Considered blocks: "+ChatColor.YELLOW+main.getRegistryConfiguration().consideredBuilding.toString());
                    else
                        player.sendMessage(ChatColor.GREEN+"Considered blocks: "+ChatColor.YELLOW+"[All Blocks]");
                }
                break;
            case 16:
                if(clickType.isLeftClick() && !clickType.isShiftClick()){
                    if(playerJobs.contains(JobList.FISHERMAN)){
                        player.sendMessage(ChatColor.RED+"You already took the job");
                        return;
                    }
                    playerStatus.getJobs().add(JobList.FISHERMAN.toString());
                    player.sendMessage(ChatColor.GREEN+"You are now hired as a "+ChatColor.AQUA+"Fisherman");
                }else if(clickType.isRightClick()){
                    if(!playerJobs.contains(JobList.FISHERMAN)){
                        player.sendMessage(ChatColor.RED+"You did not took this job");
                        return;
                    }
                    playerStatus.getJobs().remove(JobList.FISHERMAN.toString());
                    player.sendMessage(ChatColor.GREEN+"You just left your job as a "+ChatColor.AQUA+"Fisherman");
                }
                break;
            case 26:
                if(main.getVersion().support_1_17()){
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_AXOLOTL_IDLE_AIR, 1.0F,1.0F);
                    player.getWorld().spawnParticle(Particle.GLOW,player.getLocation(),10);
                    player.getWorld().spawnParticle(Particle.FLASH,player.getLocation(),2);
                    player.getWorld().spawnParticle(Particle.CRIMSON_SPORE,player.getLocation(),10);
                }else {
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1, 1);
                }
                if(Math.random()<=0.2) {
                    player.openInventory(main.getSupporters());
                    player.sendMessage(ChatColor.LIGHT_PURPLE+"Hello "+ChatColor.GOLD+player.getName()+ChatColor.LIGHT_PURPLE+", support the developer by subscribing to his "+ChatColor.RED+"Youtube Channel"+ChatColor.LIGHT_PURPLE+" :D");
                }
        }
    }
}
