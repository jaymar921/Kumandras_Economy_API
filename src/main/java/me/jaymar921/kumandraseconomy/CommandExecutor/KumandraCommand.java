package me.jaymar921.kumandraseconomy.CommandExecutor;

import me.jaymar921.kumandraseconomy.InventoryGUI.BalanceGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.QuestData;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.economy.ShopHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KumandraCommand implements CommandExecutor {

    static KumandrasEconomy plugin;
    private final DecimalFormat fmt = new DecimalFormat("###,###,###,###.##");
    private final Map<String,String> lang = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getDataHandler().getLanguageData();
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
                                //check if player has data
                                if(!plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())){
                                    PlayerStatus status = new PlayerStatus(player.getUniqueId().toString(),0);
                                    plugin.getDataHandler().getStatusHolder().put(player.getUniqueId().toString(), status);
                                }
                                PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                                List<String> plugins = plugin.getDataHandler().getPluginsRegistered();
                                Inventory inventory = new BalanceGUI().BalanceInventory(player,status, plugins);
                                plugin.getDataHandler().getBalanceGUI().put(player.getUniqueId().toString(),inventory);
                                player.openInventory(inventory);
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("economy")){
                            if(player.hasPermission("kumandraseconomy.kumandra.economy")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    String playername = args[1];
                                    if(args.length>2){

                                        if(args[2].equalsIgnoreCase("deposit")){
                                            if(args.length>3){
                                                double amount = 0;
                                                try{
                                                    amount = Double.parseDouble(args[3]);
                                                }catch (Exception e){player.sendMessage(ChatColor.RED+lang.get("InvalidAMT"));return true;}

                                                String uuid = "";
                                                boolean found=false;
                                                for(Player offP : Bukkit.getServer().getOnlinePlayers()){
                                                    if(offP!=null){
                                                        if(offP.getName().equals(playername)){
                                                            uuid = offP.getUniqueId().toString();
                                                            found = true;
                                                            if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                                PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                                double balance = status.getBalance() + amount;
                                                                status.setBalance(balance);
                                                                plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                            }else{
                                                                PlayerStatus status = new PlayerStatus(uuid, amount);
                                                                plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                                player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccCreated")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix);
                                                            }
                                                            for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                                if(receiver.getUniqueId().toString().equals(uuid))
                                                                    receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadDeposit")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+" "+lang.get("YourAcc"));
                                                            player.sendMessage(ChatColor.GREEN+lang.get("YouAdded")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());

                                                            return true;
                                                        }
                                                    }
                                                }
                                                if(!found){
                                                    for(OfflinePlayer offP : Bukkit.getServer().getOfflinePlayers()){
                                                        if(offP!=null){
                                                            if(offP.getName().equals(playername)){
                                                                uuid = offP.getUniqueId().toString();
                                                                found = true;
                                                                if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                                    PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                                    double balance = status.getBalance() + amount;
                                                                    status.setBalance(balance);
                                                                    plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                                }else{
                                                                    PlayerStatus status = new PlayerStatus(uuid, amount);
                                                                    plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                                    player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccCreated")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix);
                                                                }
                                                                for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                                    if(receiver.getUniqueId().toString().equals(uuid))
                                                                        receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadDeposit")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+" "+lang.get("YourAcc"));
                                                                player.sendMessage(ChatColor.GREEN+lang.get("YouAdded")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());

                                                                return true;
                                                            }
                                                        }
                                                    }
                                                }
                                                if(uuid.equals("")){
                                                    player.sendMessage(ChatColor.RED+lang.get("CouldNotFind")+" "+playername);
                                                    return true;
                                                }
                                            }else{
                                                player.sendMessage(ChatColor.RED+lang.get("SpecifyAMT"));
                                                return true;
                                            }
                                        }
                                        if(args[2].equalsIgnoreCase("deduct")){
                                            if(args.length>3){
                                                double amount = 0;
                                                try{
                                                    amount = -Double.parseDouble(args[3]);
                                                }catch (Exception e){player.sendMessage(ChatColor.RED+lang.get("InvalidAMT"));return true;}

                                                String uuid = "";
                                                boolean found=false;
                                                for(Player offP : Bukkit.getServer().getOnlinePlayers()){
                                                    if(offP!=null){
                                                        if(offP.getName().equals(playername)){
                                                            uuid = offP.getUniqueId().toString();
                                                            found = true;
                                                            if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                                PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                                double balance = status.getBalance() + amount;
                                                                if(balance<=0)
                                                                    balance = 0;
                                                                status.setBalance(balance);
                                                                plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                            }else{
                                                                PlayerStatus status = new PlayerStatus(uuid, 0);
                                                                plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                                player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix);
                                                            }
                                                            for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                                if(receiver.getUniqueId().toString().equals(uuid))
                                                                    receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadDeducted")+" "+ChatColor.GOLD+fmt.format(+amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+" "+lang.get("YourAcc"));
                                                            player.sendMessage(ChatColor.GREEN+lang.get("youDeduct")+" "+ChatColor.GOLD+fmt.format(+amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());

                                                            return true;
                                                        }
                                                    }
                                                }
                                                if(!found){
                                                    for(OfflinePlayer offP : Bukkit.getServer().getOfflinePlayers()){
                                                        if(offP!=null){
                                                            if(offP.getName().equals(playername)){
                                                                uuid = offP.getUniqueId().toString();
                                                                found = true;
                                                                if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                                    PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                                    double balance = status.getBalance() + amount;
                                                                    if(balance<=-0)
                                                                        balance = 0;
                                                                    status.setBalance(balance);
                                                                    plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                                }else{
                                                                    PlayerStatus status = new PlayerStatus(uuid, 0);
                                                                    plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                                    player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix);
                                                                }
                                                                for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                                    if(receiver.getUniqueId().toString().equals(uuid))
                                                                        receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadDeducted")+" "+ChatColor.GOLD+fmt.format(+amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+" "+lang.get("YourAcc"));
                                                                player.sendMessage(ChatColor.GREEN+lang.get("youDeduct")+" "+ChatColor.GOLD+fmt.format(+amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());

                                                                return true;
                                                            }
                                                        }
                                                    }
                                                }
                                                if(uuid.equals("")){
                                                    player.sendMessage(ChatColor.RED+lang.get("CouldNotFind")+" "+playername);
                                                    return true;
                                                }
                                            }else{
                                                player.sendMessage(ChatColor.RED+lang.get("SpecifyAMT"));
                                                return true;
                                            }
                                        }
                                        if(args[2].equalsIgnoreCase("reset")){

                                            String uuid = "";
                                            boolean found=false;
                                            for(Player offP : Bukkit.getServer().getOnlinePlayers()){
                                                if(offP!=null){
                                                    if(offP.getName().equals(playername)){
                                                        uuid = offP.getUniqueId().toString();
                                                        found = true;
                                                        if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                            PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                            double balance = 0;
                                                            status.setBalance(balance);
                                                            plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                        }else{
                                                            PlayerStatus status = new PlayerStatus(uuid, 0);
                                                            plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                            player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix);
                                                        }
                                                        for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                            if(receiver.getUniqueId().toString().equals(uuid))
                                                                receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+" "+lang.get("YourAcc"));
                                                        player.sendMessage(ChatColor.GREEN+lang.get("youSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());

                                                        return true;
                                                    }
                                                }
                                            }
                                            if(!found){
                                                for(OfflinePlayer offP : Bukkit.getServer().getOfflinePlayers()){
                                                    if(offP!=null){
                                                        if(offP.getName().equals(playername)){
                                                            uuid = offP.getUniqueId().toString();
                                                            found = true;
                                                            if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
                                                                PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
                                                                double balance = 0;
                                                                status.setBalance(balance);
                                                                plugin.getDataHandler().getStatusHolder().replace(uuid, status);
                                                            }else{
                                                                PlayerStatus status = new PlayerStatus(uuid, 0);
                                                                plugin.getDataHandler().getStatusHolder().put(uuid, status);
                                                                player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix);
                                                            }
                                                            for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                                if(receiver.getUniqueId().toString().equals(uuid))
                                                                    receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+" "+lang.get("YourAcc"));
                                                            player.sendMessage(ChatColor.GREEN+lang.get("youSet")+" "+ChatColor.GOLD+fmt.format(0)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());

                                                            return true;
                                                        }
                                                    }
                                                }
                                            }
                                            if(uuid.equals("")){
                                                player.sendMessage(ChatColor.RED+lang.get("CouldNotFind")+" "+playername);
                                                return true;
                                            }
                                            return true;
                                        }

                                    }else{
                                        player.sendMessage(ChatColor.RED+lang.get("invalidArg"));
                                        return true;
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED+lang.get("specifyPlayer"));
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+lang.get("noPermission"));
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("pay")){
                            if(player.hasPermission("kumandraseconomy.kumandra.pay")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    String playername = args[1];
                                    if(player.getName().equals(playername)){
                                        player.sendMessage(ChatColor.RED+lang.get("sendMoneyToSelf"));
                                        return true;
                                    }
                                    if(args.length>2){
                                        double amount = 0;
                                        try{
                                            amount = Double.parseDouble(args[2]);
                                        }catch (Exception e){player.sendMessage(ChatColor.RED+lang.get("InvalidAMT"));return true;}


                                        String uuid = "";
                                        for(OfflinePlayer offP : Bukkit.getServer().getOfflinePlayers()){
                                            if(offP!=null){
                                                if(offP.getName().equals(playername)){
                                                    uuid = offP.getUniqueId().toString();
                                                    PlayerStatus stat = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                                                    if(stat.getBalance()<amount){
                                                        player.sendMessage(ChatColor.RED+lang.get("notEnough"));
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
                                                        player.sendMessage(ChatColor.AQUA+playername+"'s "+lang.get("AccCreated")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix);
                                                    }

                                                    double userBal = stat.getBalance() - amount;
                                                    stat.setBalance(userBal);
                                                    plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(),stat);
                                                    for(Player receiver: Bukkit.getServer().getOnlinePlayers())
                                                        if(receiver.getUniqueId().toString().equals(uuid))
                                                            receiver.sendMessage(ChatColor.GREEN+""+player.getName()+" "+lang.get("hadDeposit")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+lang.get("YourAcc"));
                                                    player.sendMessage(ChatColor.GREEN+lang.get("youPaid")+" "+ChatColor.GOLD+fmt.format(amount)+plugin.getRegistryConfiguration().currency_prefix+ChatColor.GREEN+ " "+lang.get("To")+" "+playername+"'s "+lang.get("Account").toLowerCase());
                                                    player.sendMessage(ChatColor.GREEN+lang.get("yourRunningBal")+" "+ChatColor.GOLD+""+fmt.format(stat.getBalance())+plugin.getRegistryConfiguration().currency_prefix);


                                                    return true;
                                                }
                                            }
                                        }
                                        if(uuid.equals("")){
                                            player.sendMessage(ChatColor.RED+playername+" "+lang.get("wasNotFound"));
                                        }

                                    }else{
                                        player.sendMessage(ChatColor.RED+lang.get("SpecifyAMT"));
                                        return true;
                                    }
                                }else{
                                    player.sendMessage(ChatColor.RED+lang.get("specifyPlayer"));
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+lang.get("noPermission"));
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
                                            buyer.sendMessage(ChatColor.AQUA+"You have a trade request with "+ChatColor.GREEN+player.getName());
                                            buyer.sendMessage(ChatColor.AQUA+"/kTrade accept "+ChatColor.YELLOW+"- to accept");
                                            buyer.sendMessage(ChatColor.AQUA+"/kTrade deny "+ChatColor.YELLOW+"- to deny");
                                            buyer.sendMessage(ChatColor.DARK_AQUA+"Trading Request will expire on 20s");
                                            player.sendMessage(ChatColor.AQUA+"You have sent a trade request to "+ChatColor.GREEN+playername);
                                            player.sendMessage(ChatColor.DARK_AQUA+"Trading Request will expire on 20s");
                                        }
                                    //trading
                                    if(!isOnline){
                                        player.sendMessage(ChatColor.RED+lang.get("playerOnline"));
                                        return true;
                                    }


                                }else{
                                    player.sendMessage(ChatColor.RED+lang.get("specifyPlayer"));
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+lang.get("noPermission"));
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("deliver")){
                            if(player.hasPermission("kumandraseconomy.kumandra.deliver")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    String playername = args[1];
                                    if(player.getName().equals(playername)){
                                        player.sendMessage(ChatColor.RED+lang.get("DeliverSelf"));
                                        return true;
                                    }
                                    boolean isOnline = false;
                                    for(Player recipient : Bukkit.getServer().getOnlinePlayers())
                                        if(recipient.getName().equals(playername)) {
                                            isOnline = true;
                                            //
                                            //player.sendMessage("A deliver chicken has appeared");
                                            plugin.getDeliveryHandler().getScheduleDelivery().put(player, recipient);
                                            player.openInventory(plugin.getDeliveryGUI());
                                        }
                                    //trading
                                    if(!isOnline){
                                        player.sendMessage(ChatColor.RED+lang.get("playerOnline"));
                                        return true;
                                    }


                                }else{
                                    player.sendMessage(ChatColor.RED+ lang.get("specifyPlayer"));
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+lang.get("noPermission"));
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("jobs")){
                            if(player.hasPermission("kumandraseconomy.kumandra.job")) {
                                player.openInventory(plugin.getJobsGUI());
                                return true;
                            } else{
                                player.sendMessage(ChatColor.RED+lang.get("noPermission"));
                                return true;
                            }
                        }else if(args[0].equalsIgnoreCase("questAccept")){
                            if(plugin.getRegistryConfiguration().QuestAllowed)
                                if(plugin.getQuestEvent().getPendingQuest().containsKey(player)){
                                    QuestData quest = plugin.getQuestEvent().getPendingQuest().get(player);
                                    plugin.getQuestEvent().getQuestHandler().addQuest(player,plugin.getQuestEvent().generateQuest(quest, player.getUniqueId().toString()));
                                    plugin.getQuestEvent().getQuestHandler().removeQuestEntity(plugin.getQuestEvent().getPendingEntity().get(player));
                                    plugin.getQuestEvent().getPendingQuest().remove(player);
                                    return true;
                                }
                            return false;
                        }else if(args[0].equalsIgnoreCase("shops")){
                            if(player.hasPermission("kumandraseconomy.kumandra.shop")){
                                //player.sendMessage("You just opened your balance");
                                if(args.length>1){
                                    if(player.hasPermission("kumandraseconomy.kumandra.shopAdmin")) {
                                        if (args[1].equalsIgnoreCase("Create")) {
                                            if(args.length>2){
                                                String shopname = args[2];
                                                if(plugin.getShopDataHandler().getShopItems().containsKey(shopname)){
                                                    player.sendMessage(ChatColor.RED+lang.get("shopNameTaken"));
                                                    return true;
                                                }
                                                new ShopHandler().createShop(shopname, player.getLocation());
                                                player.sendMessage(ChatColor.GREEN+"Shop "+ChatColor.AQUA+shopname+ChatColor.GREEN+" was created");
                                                player.sendMessage(ChatColor.DARK_AQUA+lang.get("shopReady"));
                                                return true;
                                            }else{
                                                player.sendMessage(ChatColor.RED+lang.get("specifyShopName"));
                                                return true;
                                            }
                                        }else if (args[1].equalsIgnoreCase("Modify")) {
                                            if(args.length>2){
                                                if(args[2].equalsIgnoreCase("ShopUI")){
                                                    if(args.length>3){
                                                        if(args[3].equalsIgnoreCase("clone")){
                                                            //check nearest shop
                                                            for(String shop : plugin.getShopDataHandler().getShopLocation().keySet())
                                                                if(plugin.getShopDataHandler().getShopLocation().get(shop).getWorld().equals(player.getWorld()))
                                                                    if(plugin.getShopDataHandler().getShopLocation().get(shop).distance(player.getLocation())<=2) {
                                                                        player.sendMessage(ChatColor.DARK_GREEN+"Modify Shop UI: "+ChatColor.AQUA+shop);
                                                                        player.sendMessage(ChatColor.DARK_AQUA+"Prototype shop cloning ready, open the "+ChatColor.YELLOW+"chest/double chest"+ChatColor.DARK_AQUA+" to copy the contents");
                                                                        player.sendMessage(ChatColor.GREEN+"Cloning session will expire by "+ChatColor.RED+"20s");
                                                                        plugin.getShopDataHandler().cloningSession(player, shop);
                                                                        return true;
                                                                    }
                                                            player.sendMessage(ChatColor.DARK_AQUA+"You must be 2 blocks near a shop to use this command");
                                                            player.sendMessage(ChatColor.DARK_AQUA+"Do "+ChatColor.GREEN+"/kumandra Shop Create"+ChatColor.DARK_AQUA+" if you haven't created a shop");
                                                            return true;
                                                        }else if(args[3].equalsIgnoreCase("Price")){
                                                            //check nearest shop
                                                            for(String shop : plugin.getShopDataHandler().getShopLocation().keySet())
                                                                if(plugin.getShopDataHandler().getShopLocation().get(shop).getWorld().equals(player.getWorld()))
                                                                    if(plugin.getShopDataHandler().getShopLocation().get(shop).distance(player.getLocation())<=2) {
                                                                        //check if shop has inventory
                                                                        if(plugin.getShopDataHandler().getShopItems().containsKey(shop)){
                                                                            plugin.getShopDataHandler().setPrice(player,shop);
                                                                        }else{
                                                                            player.sendMessage(ChatColor.DARK_AQUA+"Shop doesn't have a cloned UI");
                                                                            player.sendMessage(ChatColor.DARK_AQUA+"Do "+ChatColor.GREEN+"/kumandra Shop Modify ShopUI clone"+ChatColor.DARK_AQUA+" if you haven't created a shop");
                                                                        }
                                                                        return true;
                                                                    }
                                                            player.sendMessage(ChatColor.DARK_AQUA+"You must be 2 blocks near a shop to use this command");
                                                            player.sendMessage(ChatColor.DARK_AQUA+"Do "+ChatColor.GREEN+"/kumandra Shop Create"+ChatColor.DARK_AQUA+" if you haven't created a shop");
                                                            return true;
                                                        }
                                                    }else{
                                                        player.sendMessage(ChatColor.DARK_GREEN+"To modify the shop UI, you must first create a prototype shop using a "+ChatColor.YELLOW+"chest/double "+ChatColor.DARK_GREEN+"chest. Once you're done, go to near the shop you want to modify the UI and type"+ChatColor.DARK_AQUA+" /kumandra shops modify ShopUI clone");
                                                        return true;
                                                    }
                                                }else{
                                                    player.sendMessage(ChatColor.RED+lang.get("invalidArgument"));
                                                }
                                                return true;
                                            }else{
                                                player.sendMessage(ChatColor.RED+lang.get("invalidArgument"));
                                                return true;
                                            }
                                        }else if (args[1].equalsIgnoreCase("Delete")) {
                                            if(args.length>2){
                                                String shopname = args[2];
                                                if(plugin.getShopDataHandler().getShopLocation().containsKey(shopname)){
                                                    player.sendMessage(ChatColor.DARK_AQUA+"> "+ChatColor.AQUA+shopname+ChatColor.DARK_AQUA+" < - was deleted");
                                                    for(Entity entity : plugin.getShopDataHandler().getShopLocation().get(shopname).getWorld().getNearbyEntities(plugin.getShopDataHandler().getShopLocation().get(shopname), 2,2,2)){
                                                        if(entity instanceof LivingEntity){
                                                            if(((LivingEntity)entity).getCustomName()!=null)
                                                                if(((LivingEntity)entity).getCustomName().contains(shopname))
                                                                    entity.remove();
                                                        }
                                                    }
                                                    plugin.getShopDataHandler().getShopLocation().remove(shopname);
                                                    if(plugin.getShopDataHandler().getShopItemsPrices().containsKey(shopname))
                                                        plugin.getShopDataHandler().getShopItemsPrices().remove(shopname);
                                                    if(plugin.getShopDataHandler().getShopItems().containsKey(shopname))
                                                        plugin.getShopDataHandler().getShopItems().remove(shopname);
                                                }else {
                                                    player.sendMessage(ChatColor.DARK_AQUA+"> "+ChatColor.AQUA+shopname+ChatColor.DARK_AQUA+" < - was not found");
                                                }
                                                return true;
                                            }else{
                                                player.sendMessage(ChatColor.RED+lang.get("specifyShopName"));
                                                return true;
                                            }
                                        }else {
                                            player.sendMessage(ChatColor.RED+lang.get("invalidArgument"));
                                            return true;
                                        }
                                    }else{
                                    player.sendMessage(ChatColor.RED+lang.get("noPermission"));
                                    return true;
                                }
                                }else{
                                    if(plugin.getShopDataHandler().getShopLocation().size()>0){
                                        for(String shopName : plugin.getShopDataHandler().getShopLocation().keySet()){
                                            int x = plugin.getShopDataHandler().getShopLocation().get(shopName).getBlockX();
                                            int y = plugin.getShopDataHandler().getShopLocation().get(shopName).getBlockY();
                                            int z = plugin.getShopDataHandler().getShopLocation().get(shopName).getBlockZ();
                                            String distance = "";
                                            if(plugin.getShopDataHandler().getShopLocation().get(shopName).getWorld().equals(player.getWorld()))
                                                distance = ""+fmt.format(plugin.getShopDataHandler().getShopLocation().get(shopName).distance(player.getLocation()))+" "+lang.get("blocks");
                                            else
                                                distance = lang.get("tooFar");
                                            player.sendMessage(ChatColor.GREEN+"Shop: "+ChatColor.YELLOW+shopName+ChatColor.GREEN+" ["+ChatColor.AQUA+x+","+y+","+z+ChatColor.WHITE+" - "+lang.get("distance")+": "+distance+ChatColor.GREEN+"]");
                                        }
                                    }
                                    return true;
                                }
                            }else{
                                player.sendMessage(ChatColor.RED+lang.get("noPermission"));
                                return true;
                            }
                        }
                    }else{
                        player.sendMessage(ChatColor.AQUA+""+lang.get("specifySubCommand"));
                    }


                }

                return true;
            }
            plugin.getLogger().info(ChatColor.RED+lang.get("onlyPlayersUseCommand")+" ["+label+"]");
            return true;
        }
        return false;
    }

}
