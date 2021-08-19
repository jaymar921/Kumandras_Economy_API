package me.jaymar921.kumandraseconomy;

import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class KumandrasAPI {

    public static KumandrasEconomy plugin;

    public KumandrasAPI(KumandrasEconomy main){
        plugin = main;
    }


    public Double getBalance(@NotNull Player player){
        String uuid = player.getUniqueId().toString();
        double balance = 0;
        if(plugin.getDataHandler().getStatusHolder().containsKey(uuid)){
            PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(uuid);
            return status.getBalance();
        }
        return null;
    }

    public boolean deposit(@NotNull Player player, double amount){
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())){
            PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            double balance = status.getBalance() + amount;
            status.setBalance(balance);
            return true;
        }
        return false;
    }

    public boolean withdraw(@NotNull Player player, double amount){
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())){
            PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            double balance = 0;

            if(status.getBalance()>amount)
                balance = status.getBalance() - amount;
            else
                return false;
            status.setBalance(balance);
            return true;
        }
        return false;
    }
}
