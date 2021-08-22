package me.jaymar921.kumandraseconomy.economy;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class EconomyImplementer implements Economy {

    KumandrasEconomy plugin;
    public EconomyImplementer(KumandrasEconomy main){
        plugin = main;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "Kumandra's Economy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return "Kd";
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        assert player != null;
        return plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString());
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        return plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString());
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        Player player = Bukkit.getPlayer(playerName);
        assert player != null;
        if(player.getWorld().getName().equals(worldName))
            return plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString());
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        if(player.getPlayer().getWorld().getName().equals(worldName))
            return plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString());
        return false;
    }

    @Override
    public double getBalance(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        assert player != null;
        UUID uuid = player.getUniqueId();
        return plugin.getDataHandler().getStatusHolder().get(uuid.toString()).getBalance();
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
            return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance();
        return 0;
    }

    @Override
    public double getBalance(String playerName, String world) {
        Player player = Bukkit.getPlayer(playerName);
        assert player != null;
        if(player.getWorld().getName().equals(world)){
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
                return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance();
        }
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        if(player.getPlayer().getWorld().getName().equals(world))
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
                return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance();
        return 0;
    }

    @Override
    public boolean has(String playerName, double amount) {
        Player player = Bukkit.getPlayer(playerName);
        assert player != null;
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
            return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance()>=amount;
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
            return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance()>=amount;
        return false;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        Player player = Bukkit.getPlayer(playerName);
        assert player != null;
        if(player.getWorld().getName().equals(worldName))
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
                return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance()>=amount;
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        if(player.getPlayer().getWorld().getName().equals(worldName))
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
                return plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString()).getBalance()>=amount;
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        Player player = Bukkit.getPlayer(playerName);
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
            PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            balance = playerStatus.getBalance();
            if(balance>=amount){
                responseType = EconomyResponse.ResponseType.SUCCESS;
                balance-=amount;
                playerStatus.setBalance(balance);
                plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);
            }else{
                responseType = EconomyResponse.ResponseType.FAILURE;
                errorMsg = "Player balance is less than amount";
            }
        }else
            errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
            PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            balance = playerStatus.getBalance();
            if(balance>=amount){
                responseType = EconomyResponse.ResponseType.SUCCESS;
                balance-=amount;
                playerStatus.setBalance(balance);
                plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);
            }else{
                responseType = EconomyResponse.ResponseType.FAILURE;
                errorMsg = "Player balance is less than amount";
            }
        }else
            errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        Player player = Bukkit.getPlayer(playerName);
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(player.getWorld().getName().equals(worldName))
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
                PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                balance = playerStatus.getBalance();
                if(balance>=amount){
                    responseType = EconomyResponse.ResponseType.SUCCESS;
                    balance-=amount;
                    playerStatus.setBalance(balance);
                    plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);
                }else{
                    responseType = EconomyResponse.ResponseType.FAILURE;
                    errorMsg = "Player balance is less than amount";
                }
            }else
                errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(player.getPlayer().getWorld().getName().equals(worldName))
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
                PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                balance = playerStatus.getBalance();
                if(balance>=amount){
                    responseType = EconomyResponse.ResponseType.SUCCESS;
                    balance-=amount;
                    playerStatus.setBalance(balance);
                    plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);
                }else{
                    responseType = EconomyResponse.ResponseType.FAILURE;
                    errorMsg = "Player balance is less than amount";
                }
            }else
                errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        Player player = Bukkit.getPlayer(playerName);
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
            PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            balance = playerStatus.getBalance();

            responseType = EconomyResponse.ResponseType.SUCCESS;
            balance+=amount;
            playerStatus.setBalance(balance);
            plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);

        }else
            errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
            PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            balance = playerStatus.getBalance();

            responseType = EconomyResponse.ResponseType.SUCCESS;
            balance+=amount;
            playerStatus.setBalance(balance);
            plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);

        }else
            errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        Player player = Bukkit.getPlayer(playerName);
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(player.getWorld().getName().equals(worldName))
        if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
            PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
            balance = playerStatus.getBalance();

            responseType = EconomyResponse.ResponseType.SUCCESS;
            balance+=amount;
            playerStatus.setBalance(balance);
            plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);

        }else
            errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        EconomyResponse.ResponseType responseType = EconomyResponse.ResponseType.NOT_IMPLEMENTED;
        double balance = 0;
        String errorMsg = "";
        if(player.getPlayer().getWorld().getName().equals(worldName))
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString())) {
                PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                balance = playerStatus.getBalance();

                responseType = EconomyResponse.ResponseType.SUCCESS;
                balance+=amount;
                playerStatus.setBalance(balance);
                plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);

            }else
                errorMsg = "Player data not found";
        return new EconomyResponse(amount, balance, responseType, errorMsg);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
