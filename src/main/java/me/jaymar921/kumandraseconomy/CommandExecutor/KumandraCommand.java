package me.jaymar921.kumandraseconomy.CommandExecutor;

import me.jaymar921.kumandraseconomy.Inventory.InventoryGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public class KumandraCommand implements CommandExecutor {

    static KumandrasEconomy plugin;
    public KumandraCommand(KumandrasEconomy main){
        plugin = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("Kumandra") || label.equalsIgnoreCase("kd")){
            if(sender instanceof Player){
                Player player = (Player) sender;

                if(player.hasPermission("kumandraseconomy.kumandra")){

                    if(args.length>0){
                        if(args[0].equalsIgnoreCase("balance")){
                            if(player.hasPermission("kumandraseconomy.kumandra.balance")){
                                //player.sendMessage("You just opened your balance");
                                PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                                List<String> plugins = plugin.getDataHandler().getPluginsRegistered();
                                Inventory inventory = new InventoryGUI().BalanceInventory(player,status, plugins);
                                plugin.getDataHandler().getPlayerInventory().put(player.getUniqueId().toString(),inventory);
                                player.openInventory(inventory);
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("deposit")){
                            if(player.hasPermission("kumandraseconomy.kumandra.deposit")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    String playername = args[1];
                                    if(args.length>2){
                                        double amount = 0;
                                        try{
                                            amount = Double.parseDouble(args[2]);
                                        }catch (Exception e){player.sendMessage(ChatColor.RED+"Invalid Amount");return true;}

                                        String uuid = "";
                                        for(OfflinePlayer offP : Bukkit.getServer().getOfflinePlayers()){
                                            if(offP!=null){
                                                if(offP.getName().equals(playername)){
                                                    uuid = offP.getUniqueId().toString();

                                                    if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                        PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                        double balance = status.getBalance() + amount;
                                                        status.setBalance(balance);
                                                        plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                    }else{
                                                        PlayerStatus status = new PlayerStatus(uuid, amount);
                                                        plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                        player.sendMessage(ChatColor.AQUA+playername+"'s account has been created, added "+amount);
                                                    }
                                                    for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                        if(receiver.getUniqueId().toString().equals(uuid))
                                                            receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" had deposited $"+amount+" to your account");
                                                    player.sendMessage(ChatColor.GREEN+"You have added "+amount+ " to "+playername+"'s account");

                                                    return true;
                                                }
                                            }
                                        }
                                        if(uuid.equals("")){
                                            player.sendMessage(ChatColor.RED+"Could not find "+playername);
                                            return true;
                                        }


                                    }else{
                                        player.sendMessage(ChatColor.RED+"You need to specify the amount");
                                        return true;
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED+"You need to specify the player");
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have the permission to use this command");
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("pay")){
                            if(player.hasPermission("kumandraseconomy.kumandra.pay")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    String playername = args[1];
                                    if(player.getName().equals(playername)){
                                        player.sendMessage(ChatColor.RED+"You cannot send money to yourself");
                                        return true;
                                    }
                                    if(args.length>2){
                                        double amount = 0;
                                        try{
                                            amount = Double.parseDouble(args[2]);
                                        }catch (Exception e){player.sendMessage(ChatColor.RED+"Invalid Amount");return true;}


                                        String uuid = "";
                                        for(OfflinePlayer offP : Bukkit.getServer().getOfflinePlayers()){
                                            if(offP!=null){
                                                if(offP.getName().equals(playername)){
                                                    uuid = offP.getUniqueId().toString();
                                                    PlayerStatus stat = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                                                    if(stat.getBalance()<amount){
                                                        player.sendMessage(ChatColor.RED+"You do not have enough balance to pay!");
                                                        return true;
                                                    }

                                                    if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                        PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                        double balance = status.getBalance() + amount;
                                                        status.setBalance(balance);
                                                        plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                    }else{
                                                        PlayerStatus status = new PlayerStatus(uuid, amount);
                                                        plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                        player.sendMessage(ChatColor.AQUA+playername+"'s account has been created, added "+amount);
                                                    }

                                                    double userBal = stat.getBalance() - amount;
                                                    stat.setBalance(userBal);
                                                    plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(),stat);
                                                    for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                        if(receiver.getUniqueId().toString().equals(uuid))
                                                            receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" had deposited $"+amount+" to your account");
                                                    player.sendMessage(ChatColor.GREEN+"You have paid "+amount+ " to "+playername+"'s account");
                                                    player.sendMessage(ChatColor.GREEN+"Your running balance: "+ChatColor.GOLD+"$"+stat.getBalance());


                                                    return true;
                                                }
                                            }
                                        }
                                        if(uuid.equals("")){
                                            player.sendMessage(ChatColor.RED+playername+" was not found!");
                                        }

                                    }else{
                                        player.sendMessage(ChatColor.RED+"You need to specify the amount");
                                        return true;
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED+"You need to specify the player");
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have the permission to use this command");
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("trade")){
                            if(player.hasPermission("kumandraseconomy.kumandra.trade")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    String playername = args[1];
                                    if(player.getName().equals(playername)){
                                        player.sendMessage(ChatColor.RED+"You cannot trade yourself");
                                        return true;
                                    }
                                    boolean isOnline = false;
                                    for(Player buyer : Bukkit.getServer().getOnlinePlayers())
                                        if(buyer.getName().equals(playername)) {
                                            isOnline = true;
                                            plugin.getTradingHandler().createTrade(player, buyer);
                                            buyer.sendMessage(ChatColor.AQUA+"You have a trade with "+ChatColor.GREEN+player.getName());
                                            buyer.sendMessage(ChatColor.AQUA+"/Trade accept "+ChatColor.YELLOW+"- to accept");
                                            buyer.sendMessage(ChatColor.AQUA+"/Trade deny "+ChatColor.YELLOW+"- to deny");
                                            buyer.sendMessage(ChatColor.AQUA+"Trading Session will expire by 20s");
                                            player.sendMessage(ChatColor.AQUA+"You have sent a trade request to "+ChatColor.GREEN+playername);
                                            player.sendMessage(ChatColor.AQUA+"Trading Session will expire by 20s");
                                        }
                                    //trading
                                    if(!isOnline){
                                        player.sendMessage(ChatColor.RED+"The player must be online");
                                        return true;
                                    }


                                }else{
                                    player.sendMessage(ChatColor.RED+"You need to specify the player");
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have the permission to use this command");
                                return true;
                            }
                        }
                    }else{
                        player.sendMessage(ChatColor.AQUA+"You need to specify the sub command");
                    }


                }

                return true;
            }
            plugin.getLogger().info(ChatColor.RED+"Only players can use this command ["+label+"]");
            return true;
        }
        return false;
    }

}
