package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.InventoryGUI.ExchangeGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.Vault.VaultSupport;
import me.jaymar921.kumandraseconomy.datahandlers.DeliveryDataHandler;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.entity.DeliveryType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryClick implements Listener {

    KumandrasEconomy plugin;
    private final String foreign_economy;
    private final String kumandras_prefix;
    public InventoryClick(KumandrasEconomy plugin){
        this.plugin = plugin;
        foreign_economy = plugin.getRegistryConfiguration().foreign_economy;
        kumandras_prefix = plugin.getRegistryConfiguration().currency_prefix;
    }
    @EventHandler
    private void playerClickBalanceGUI(InventoryClickEvent event){
        if(plugin.getDataHandler().getBalanceGUI().containsKey(event.getWhoClicked().getUniqueId().toString())){
            if(event.getClickedInventory()==null)
                return;
            if(plugin.getDataHandler().getBalanceGUI().get(event.getWhoClicked().getUniqueId().toString())==null)
                return;
            if(event.getClickedInventory().equals(plugin.getDataHandler().getBalanceGUI().get(event.getWhoClicked().getUniqueId().toString()))) {
                if(event.getRawSlot()==13){
                    PlayerStatus status = plugin.getDataHandler().getStatusHolder().get(event.getWhoClicked().getUniqueId().toString());
                    Inventory exchangeGUI = new ExchangeGUI().getExchangeGUI((Player) event.getWhoClicked(),status);
                    plugin.getDataHandler().getExchangeGUI().put(event.getWhoClicked().getUniqueId(),exchangeGUI);
                    event.getWhoClicked().openInventory(exchangeGUI);
                }

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void playerClickExchangeGUI(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(plugin.getDataHandler().getExchangeGUI().containsKey(player.getUniqueId())){
            if(event.getClickedInventory().equals(plugin.getDataHandler().getExchangeGUI().get(player.getUniqueId()))){
                event.setCancelled(true);

                //Declarations
                PlayerStatus playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());
                double currency = KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_economy;
                double kumandras_money = 0;
                double foreign_money = kumandras_money/currency;

                //Vault Support
                VaultSupport vault = plugin.getVault();
                switch (event.getRawSlot()){
                    case 19:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 50;
                            foreign_money = kumandras_money/currency;
                            if(vault.economy.getBalance(player)>=foreign_money){
                                vault.economy.withdrawPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()+kumandras_money);
                                player.sendMessage(ChatColor.GREEN+"You have successfully exchanged "+ChatColor.GOLD + "$" +foreign_money+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + kumandras_money + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+foreign_economy+" balance for exchange");
                            }
                        }
                        break;
                    case 21:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 100;
                            foreign_money = kumandras_money/currency;
                            if(vault.economy.getBalance(player)>=foreign_money){
                                vault.economy.withdrawPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()+kumandras_money);
                                player.sendMessage(ChatColor.GREEN+"You have successfully exchanged "+ChatColor.GOLD + "$" +foreign_money+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + kumandras_money + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+foreign_economy+" balance for exchange");
                            }
                        }
                        break;
                    case 23:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 500;
                            foreign_money = kumandras_money/currency;
                            if(vault.economy.getBalance(player)>=foreign_money){
                                vault.economy.withdrawPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()+kumandras_money);
                                player.sendMessage(ChatColor.GREEN+"You have successfully exchanged "+ChatColor.GOLD + "$" +foreign_money+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + kumandras_money + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+foreign_economy+" balance for exchange");
                            }
                        }
                        break;
                    case 25:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 1000;
                            foreign_money = kumandras_money/currency;
                            if(vault.economy.getBalance(player)>=foreign_money){
                                vault.economy.withdrawPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()+kumandras_money);
                                player.sendMessage(ChatColor.GREEN+"You have successfully exchanged "+ChatColor.GOLD + "$" +foreign_money+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + kumandras_money + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+foreign_economy+" balance for exchange");
                            }
                        }
                        break;
                    case 40:
                        player.closeInventory();
                        return;
                }


                Inventory update = new ExchangeGUI().getExchangeGUI(player,playerStatus);
                plugin.getDataHandler().getExchangeGUI().replace(player.getUniqueId(),update);
                player.openInventory(update);
            }
        }
    }

    @EventHandler
    private void playerClickDeliveryUI(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory()== null) return;
        if(event.getClickedInventory().equals(plugin.getDeliveryGUI())){
            event.setCancelled(true);

            PlayerStatus playerStatus = null;
            DeliveryDataHandler deliveryHandler = plugin.getRegistryConfiguration().deliveryHandler;

            //check player data
            if(plugin.getDataHandler().getStatusHolder().containsKey(player.getUniqueId().toString()))
                playerStatus = plugin.getDataHandler().getStatusHolder().get(player.getUniqueId().toString());

            if(playerStatus == null)
                return;

            double player_balance= playerStatus.getBalance();
            switch (event.getRawSlot()){
                case 10:
                    if(player_balance>=deliveryHandler.getCheap_delivery_price()){
                        player_balance-=deliveryHandler.getCheap_delivery_price();
                        playerStatus.setBalance(player_balance);
                        player.sendMessage(ChatColor.AQUA+"A delivery Chicken has arrived, right click to add items");
                        Player recepient = plugin.getDeliveryHandler().getScheduleDelivery().get(player);
                        plugin.getDeliveryHandler().createDelivery(DeliveryType.CHEAP, player, recepient);
                        player.closeInventory();
                    }
            }

            plugin.getDataHandler().getStatusHolder().replace(player.getUniqueId().toString(), playerStatus);
        }
    }

    @EventHandler
    private void clickDeliverItem(InventoryClickEvent event){
        if(event.getClickedInventory()== null) return;
        Player player = (Player) event.getWhoClicked();

        if(plugin.getDeliveryHandler().getDeliveryInventory().containsValue(event.getClickedInventory())){

            Inventory inventory = null;
            String key = null;
            for(String deliveryKey : plugin.getDeliveryHandler().getDeliveryInventory().keySet()){
                if(plugin.getDeliveryHandler().getDeliveryInventory().get(deliveryKey).equals(event.getClickedInventory())) {
                    inventory = plugin.getDeliveryHandler().getDeliveryInventory().get(deliveryKey);
                    key = deliveryKey;
                    break;
                }
            }

            if(inventory!=null && key != null){
                DeliveryType deliveryType = plugin.getDeliveryHandler().getDeliveryType().get(key);
                if(deliveryType.equals(DeliveryType.CHEAP)){
                    if(event.getRawSlot()==0||event.getRawSlot()==1||event.getRawSlot()==7)
                        event.setCancelled(true);
                    if(event.getRawSlot()==8) {
                        if(event.getCurrentItem()!=null)
                        if(event.getCurrentItem().hasItemMeta())
                            if(event.getCurrentItem().getItemMeta().getDisplayName()!=null)
                                if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Send items")){
                                    if(plugin.getDeliveryHandler().getScheduleDelivery().containsKey(player)) {
                                        plugin.getDeliveryHandler().startDelivery(deliveryType, key, player);
                                        player.sendMessage(ChatColor.GREEN+"You have sent the delivery");
                                        player.closeInventory();
                                    }else{
                                        player.sendMessage(ChatColor.RED+"You have not booked any delivery");
                                        player.closeInventory();
                                    }
                                }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW+"[Accept]")){
                                    event.setCancelled(true);
                                    List<ItemStack> items = new ArrayList<>();
                                    Inventory inv = plugin.getDeliveryHandler().getDeliveryInventory().get(key);
                                    for(int i = 2; i < 7; i++)
                                        items.add(inv.getItem(i));

                                    for(ItemStack itemStack : items){
                                        if(itemStack==null)
                                            continue;
                                        if(player.getInventory().firstEmpty()==-1)
                                            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                                        else
                                            player.getInventory().addItem(itemStack);
                                    }
                                    for(Player p : plugin.getDeliveryHandler().getId_delivery().get(key)){
                                        p.sendMessage(ChatColor.GREEN+""+key+" - Has been Accepted");
                                    }

                                    player.sendMessage(ChatColor.GREEN+"You have received the delivery");
                                    plugin.getDeliveryHandler().deliveryAccepted(key);
                                    player.closeInventory();
                                }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
