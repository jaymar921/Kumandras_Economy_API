package me.jaymar921.kumandraseconomy.CommandExecutor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tabbing implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
        if(arguments.isEmpty()){
             arguments.add("Balance");
             if(sender.hasPermission("kumandraseconomy.kumandra.deposit"))
                 arguments.add("Deposit");
             arguments.add("Pay");
             arguments.add("Trade");
             arguments.add("Jobs");
        }
        List<String> result = new ArrayList<>();
        if(args.length== 1){
             for(String args1: arguments) {
                 if(args1.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(args1);
             }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("deposit")){
                for(String args1 : players())
                    if(args1.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(args1);
            }
        }
        if(args.length == 3){
            if(args[0].equalsIgnoreCase("deposit")) {
                if("amount".toLowerCase().startsWith(args[2].toLowerCase()))
                    result.add("amount");
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("pay")){
                for(String args1 : players())
                    if(args1.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(args1);
            }
        }

        if(args.length == 3){
            if(args[0].equalsIgnoreCase("pay")){
                if("amount".toLowerCase().startsWith(args[2].toLowerCase())    )
                    result.add("amount");
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("trade")){
                for(String args1 : players())
                    if(args1.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(args1);
            }
        }

        return result;
    }

    private List<String> players(){
        List<String>names = new ArrayList<>();
        for(Player player : Bukkit.getServer().getOnlinePlayers())
            names.add(player.getName());
        return names;
    }
    
}
