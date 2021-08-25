package me.jaymar921.kumandraseconomy.CommandExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TradeTabbing implements TabCompleter {
    List<String> arguments = new ArrayList<>();


    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(arguments.isEmpty()){
            arguments.add("Accept");
            arguments.add("Deny");
        }
        List<String> result = new ArrayList<>();
        if(args.length == 1){
            for(String arg : arguments)
                if(arg.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(arg);
        }
        return result;
    }
}
