package me.jaymar921.kumandraseconomy.economy;

import me.jaymar921.kumandraseconomy.InventoryGUI.TradingGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.TradingData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TradingHandler {
    KumandrasEconomy main;
              // trader   inventory
    private Map<String, Inventory> tradeInventory = new HashMap<>();
              //inventory, Player UUIDS
    private Map<Inventory, List<String>> tradePersonnel = new HashMap<>();
              //buyer uuid, trader uuid
    private Map<String, String> requestTradePersonnel = new HashMap<>();
              //trader uuid, players
    private Map<String, List<Player>> activeSession = new HashMap<>();

    private Map<Inventory, List<TradingData>> tradingDataSession = new HashMap<>();

    private List<String> sessionID = new ArrayList<>();

    public TradingHandler(KumandrasEconomy main){
        this.main = main;
    }

    public Map<String, Inventory> getTradeInventory() {return tradeInventory;}
    public Map<Inventory,List<String>> getTradePersonnel() {return tradePersonnel;}
    public Map<String, String> getRequestTradePersonnel(){return requestTradePersonnel;}
    public Map<String, List<Player>> getActiveSession() {return activeSession;}
    public Map<Inventory, List<TradingData>> getTradingDataSession() {return tradingDataSession;}
    public List<String> getSessionID() {return sessionID;}

    public void createTrade(Player trader, Player buyer){
        Inventory inv = new TradingGUI().createTradeInventory(trader.getName(), buyer.getName());
        getTradeInventory().put(trader.getUniqueId().toString(), inv);
        List<String> playerUUIDs = new ArrayList<>();
        playerUUIDs.add(trader.getUniqueId().toString());
        playerUUIDs.add(buyer.getUniqueId().toString());
        getTradePersonnel().put(inv,playerUUIDs);
        getRequestTradePersonnel().put(buyer.getUniqueId().toString(),trader.getUniqueId().toString());

        sessionScheduler(trader.getUniqueId().toString(), buyer.getUniqueId().toString());
    }

    public void activateSession(String TraderUuid){
        sessionID.add(TraderUuid);
        new BukkitRunnable(){

            public void run(){
                sessionID.remove(TraderUuid);
            }

        }.runTaskLater(main,(long)main.getRegistryConfiguration().requestTradeSessionExpiry*20);
    }

    public void startActiveSession(Player trader, Player buyer){
        List<Player> players = Arrays.asList(trader, buyer);

        activeSession.put(trader.getUniqueId().toString(), players);

        TradingData traderData = new TradingData();
        traderData.setOwner(trader);
        traderData.setPrice(main.getRegistryConfiguration().tradingIncrease);
        traderData.setRole("trader");
        traderData.isSet = false;
        traderData.isPaid = false;
        TradingData buyerData= new TradingData();
        buyerData.setOwner(buyer);
        buyerData.setPrice(main.getRegistryConfiguration().tradingIncrease);
        buyerData.setRole("buyer");
        buyerData.isSet = false;
        buyerData.isPaid = false;

        List<TradingData> tradingList = new ArrayList<>();
        tradingList.add(traderData);
        tradingList.add(buyerData);

        getTradingDataSession().put(getTradeInventory().get(trader.getUniqueId().toString()), tradingList);
    }

    private void sessionScheduler(String TraderUuid, String buyerUUID){
        new BukkitRunnable(){

            public void run(){
                if(activeSession.containsKey(TraderUuid))
                    return;
                if(sessionID.contains(TraderUuid))
                    return;
                getRequestTradePersonnel().remove(buyerUUID);
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    if(player.getUniqueId().toString().equals(TraderUuid))
                        player.sendMessage(ChatColor.RED+"Your trade request has expired");
                    if(player.getUniqueId().toString().equals(buyerUUID))
                        player.sendMessage(ChatColor.RED+"The trade request expired");
                }
            }

        }.runTaskLater(main,(long)main.getRegistryConfiguration().requestTradeSessionExpiry*20);
    }

    public List<Integer> denySlots(){
        List<Integer> data = new ArrayList<>();
        for(int i = 0; i < 9; i++)
            data.add(i);
        for(int i = 45; i < 54; i++)
            data.add(i);
        data.add(9);
        data.add(18);
        data.add(27);
        data.add(36);
        data.add(13);
        data.add(22);
        data.add(31);
        data.add(40);
        data.add(17);
        data.add(26);
        data.add(35);
        data.add(44);
        data.add(38);
        data.add(37);
        data.add(41);
        data.add(42);
        data.add(39);
        data.add(43);
        return data;
    }

}
