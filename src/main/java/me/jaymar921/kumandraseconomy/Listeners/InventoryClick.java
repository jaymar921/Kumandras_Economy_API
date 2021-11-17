package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.InventoryGUI.ExchangeGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.Vault.VaultSupport;
import me.jaymar921.kumandraseconomy.datahandlers.DeliveryDataHandler;
import me.jaymar921.kumandraseconomy.economy.PlayerStatus;
import me.jaymar921.kumandraseconomy.entity.DeliveryType;
import me.jaymar921.kumandraseconomy.utility.LangParse;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryClick implements Listener {

    KumandrasEconomy plugin;
    private final String foreign_economy;
    private final String kumandras_prefix;
    private final DecimalFormat format = new DecimalFormat("#,###,###,###.##");
    private final Map<String,String> lang;
    public InventoryClick(KumandrasEconomy plugin){
        this.plugin = plugin;
        lang = plugin.getDataHandler().getLanguageData();
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
                if(plugin.getRegistryConfiguration().separate_economy)
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
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD + "$" +format.format(foreign_money)+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(kumandras_money) + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+ LangParse.string(lang.get("notEnoughMoney"),foreign_economy));
                            }
                        }
                        break;
                    case 28:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 50*currency;
                            foreign_money = 50;
                            if(playerStatus.getBalance()>=kumandras_money){
                                vault.economy.depositPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()-kumandras_money);
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD +format.format(kumandras_money)+ChatColor.DARK_AQUA+kumandras_prefix + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(foreign_money) + " "+ ChatColor.DARK_AQUA + foreign_economy);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+format.format(kumandras_money)+kumandras_prefix+" balance for exchange");
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
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD + "$" +format.format(foreign_money)+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(kumandras_money) + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+LangParse.string(lang.get("notEnoughMoney"),foreign_economy));
                            }
                        }
                        break;
                    case 30:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 100*currency;
                            foreign_money = 100;
                            if(playerStatus.getBalance()>=kumandras_money){
                                vault.economy.depositPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()-kumandras_money);
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD +format.format(kumandras_money)+ChatColor.DARK_AQUA+kumandras_prefix + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(foreign_money) + " "+ ChatColor.DARK_AQUA + foreign_economy);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+format.format(kumandras_money)+kumandras_prefix+" balance for exchange");
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
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD + "$" +format.format(foreign_money)+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(kumandras_money) + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+LangParse.string(lang.get("notEnoughMoney"),foreign_economy));
                            }
                        }
                        break;
                    case 32:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 500*currency;
                            foreign_money = 500;
                            if(playerStatus.getBalance()>=kumandras_money){
                                vault.economy.depositPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()-kumandras_money);
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD +format.format(kumandras_money)+ChatColor.DARK_AQUA+kumandras_prefix + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(foreign_money) + " "+ ChatColor.DARK_AQUA + foreign_economy);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+format.format(kumandras_money)+kumandras_prefix+" balance for exchange");
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
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD + "$" +format.format(foreign_money)+" "+ChatColor.DARK_AQUA+foreign_economy + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(kumandras_money) + ChatColor.DARK_AQUA + kumandras_prefix);
                            }else{
                                player.sendMessage(ChatColor.RED+LangParse.string(lang.get("notEnoughMoney"),foreign_economy));
                            }
                        }
                        break;
                    case 34:
                        //check if player has account from the foreign economy
                        if(vault.economy.hasAccount(player)){
                            kumandras_money = 1000*currency;
                            foreign_money = 1000;
                            if(playerStatus.getBalance()>=kumandras_money){
                                vault.economy.depositPlayer(player, foreign_money);
                                playerStatus.setBalance(playerStatus.getBalance()-kumandras_money);
                                player.sendMessage(ChatColor.GREEN+lang.get("successExchange")+" "+ChatColor.GOLD +format.format(kumandras_money)+ChatColor.DARK_AQUA+kumandras_prefix + ChatColor.GREEN + " for "+ChatColor.GOLD + format.format(foreign_money) + " "+ ChatColor.DARK_AQUA + foreign_economy);
                            }else{
                                player.sendMessage(ChatColor.RED+"You do not have enough "+format.format(kumandras_money)+kumandras_prefix+" balance for exchange");
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
                        player.sendMessage(ChatColor.GREEN+"You paid "+ChatColor.YELLOW+deliveryHandler.getCheap_delivery_price()+kumandras_prefix+ChatColor.GREEN+" for cheap delivery");
                        Player recipient = plugin.getDeliveryHandler().getScheduleDelivery().get(player);
                        plugin.getDeliveryHandler().createDelivery(DeliveryType.CHEAP, player, recipient);
                        player.closeInventory();
                    }else{
                        player.sendMessage(ChatColor.RED+"You do not have enough Kumandra's Money to pay the delivery");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    break;
                case 12:
                    if(player_balance>=deliveryHandler.getRegular_delivery_price()){
                        player_balance-=deliveryHandler.getRegular_delivery_price();
                        playerStatus.setBalance(player_balance);
                        player.sendMessage(ChatColor.GREEN+"You paid "+ChatColor.YELLOW+deliveryHandler.getRegular_delivery_price()+kumandras_prefix+ChatColor.GREEN+" for regular delivery");
                        Player recipient = plugin.getDeliveryHandler().getScheduleDelivery().get(player);
                        plugin.getDeliveryHandler().createDelivery(DeliveryType.REGULAR, player, recipient);
                        player.closeInventory();
                    }else{
                        player.sendMessage(ChatColor.RED+"You do not have enough Kumandra's Money to pay the delivery");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    break;
                case 14:
                    if(player_balance>=deliveryHandler.getFast_delivery_price()){
                        player_balance-=deliveryHandler.getFast_delivery_price();
                        playerStatus.setBalance(player_balance);
                        player.sendMessage(ChatColor.GREEN+"You paid "+ChatColor.YELLOW+deliveryHandler.getFast_delivery_price()+kumandras_prefix+ChatColor.GREEN+" for fast delivery");
                        Player recipient = plugin.getDeliveryHandler().getScheduleDelivery().get(player);
                        plugin.getDeliveryHandler().createDelivery(DeliveryType.FAST, player, recipient);
                        player.closeInventory();
                    }else{
                        player.sendMessage(ChatColor.RED+"You do not have enough Kumandra's Money to pay the delivery");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    break;
                case 16:
                    if(player_balance>=deliveryHandler.getPriority_delivery_price()){
                        player_balance-=deliveryHandler.getPriority_delivery_price();
                        playerStatus.setBalance(player_balance);
                        player.sendMessage(ChatColor.GREEN+"You paid "+ChatColor.YELLOW+deliveryHandler.getPriority_delivery_price()+kumandras_prefix+ChatColor.GREEN+" for priority delivery");
                        Player recipient = plugin.getDeliveryHandler().getScheduleDelivery().get(player);
                        plugin.getDeliveryHandler().createDelivery(DeliveryType.PRIORITY, player, recipient);
                        player.closeInventory();
                    }else{
                        player.sendMessage(ChatColor.RED+"You do not have enough Kumandra's Money to pay the delivery");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
                    }
                    break;
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
                                if(event.getCurrentItem().getItemMeta().getDisplayName().contains(lang.get("SendTo"))){
                                    if(plugin.getDeliveryHandler().getScheduleDelivery().containsKey(player)) {
                                        plugin.getDeliveryHandler().startDelivery(deliveryType, key, player);
                                        player.sendMessage(ChatColor.GREEN+lang.get("youSentDelivery"));
                                        player.closeInventory();
                                    }else{
                                        player.sendMessage(ChatColor.RED+lang.get("noBooked"));
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
                                        p.sendMessage(ChatColor.GREEN+""+key+" "+lang.get("wasReceived"));
                                    }

                                    player.sendMessage(ChatColor.GREEN+lang.get("yourReceived"));
                                    plugin.getDeliveryHandler().deliveryAccepted(key);
                                    player.closeInventory();
                                }
                        event.setCancelled(true);
                    }
                }else if(deliveryType.equals(DeliveryType.REGULAR) || deliveryType.equals(DeliveryType.FAST)){
                    if(event.getRawSlot()==0||event.getRawSlot()==1||event.getRawSlot()==7||event.getRawSlot()==8||event.getRawSlot()==9||event.getRawSlot()==10||event.getRawSlot()==16)
                        event.setCancelled(true);
                    if(event.getRawSlot()==17) {
                        if(event.getCurrentItem()!=null)
                            if(event.getCurrentItem().hasItemMeta())
                                if(event.getCurrentItem().getItemMeta().getDisplayName()!=null)
                                    if(event.getCurrentItem().getItemMeta().getDisplayName().contains(lang.get("SendTo"))){
                                        if(plugin.getDeliveryHandler().getScheduleDelivery().containsKey(player)) {
                                            plugin.getDeliveryHandler().startDelivery(deliveryType, key, player);
                                            player.sendMessage(ChatColor.GREEN+lang.get("youSentDelivery"));
                                            player.closeInventory();
                                        }else{
                                            player.sendMessage(ChatColor.RED+lang.get("noBooked"));
                                            player.closeInventory();
                                        }
                                    }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW+"[Accept]")){
                                        event.setCancelled(true);
                                        List<ItemStack> items = new ArrayList<>();
                                        Inventory inv = plugin.getDeliveryHandler().getDeliveryInventory().get(key);
                                        for(int i = 2; i < 7; i++)
                                            items.add(inv.getItem(i));
                                        for(int i = 11; i < 16; i++)
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
                                            p.sendMessage(ChatColor.GREEN+""+key+" "+lang.get("wasReceived"));
                                        }

                                        player.sendMessage(ChatColor.GREEN+lang.get("yourReceived"));
                                        plugin.getDeliveryHandler().deliveryAccepted(key);
                                        player.closeInventory();
                                    }
                        event.setCancelled(true);
                    }
                }else if(deliveryType.equals(DeliveryType.PRIORITY)){
                    if(event.getRawSlot()==0||event.getRawSlot()==1||event.getRawSlot()==7||event.getRawSlot()==8||event.getRawSlot()==9||event.getRawSlot()==10||event.getRawSlot()==16||event.getRawSlot()==17
                            ||event.getRawSlot()==18||event.getRawSlot()==19||event.getRawSlot()==25)
                        event.setCancelled(true);
                    if(event.getRawSlot()==26) {
                        if(event.getCurrentItem()!=null)
                            if(event.getCurrentItem().hasItemMeta())
                                if(event.getCurrentItem().getItemMeta().getDisplayName()!=null)
                                    if(event.getCurrentItem().getItemMeta().getDisplayName().contains(lang.get("SendTo"))){
                                        if(plugin.getDeliveryHandler().getScheduleDelivery().containsKey(player)) {
                                            plugin.getDeliveryHandler().startDelivery(deliveryType, key, player);
                                            player.sendMessage(ChatColor.GREEN+lang.get("youSentDelivery"));
                                            player.closeInventory();
                                        }else{
                                            player.sendMessage(ChatColor.RED+lang.get("noBooked"));
                                            player.closeInventory();
                                        }
                                    }else if(event.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.YELLOW+"[Accept]")){
                                        event.setCancelled(true);
                                        List<ItemStack> items = new ArrayList<>();
                                        Inventory inv = plugin.getDeliveryHandler().getDeliveryInventory().get(key);
                                        for(int i = 2; i < 7; i++)
                                            items.add(inv.getItem(i));
                                        for(int i = 11; i < 16; i++)
                                            items.add(inv.getItem(i));
                                        for(int i = 20; i < 25; i++)
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
                                            p.sendMessage(ChatColor.GREEN+""+key+" "+lang.get("wasReceived"));
                                        }

                                        player.sendMessage(ChatColor.GREEN+lang.get("yourReceived"));
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
