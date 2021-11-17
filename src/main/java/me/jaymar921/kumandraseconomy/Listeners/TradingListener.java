package me.jaymar921.kumandraseconomy.Listeners;

import me.jaymar921.kumandraseconomy.InventoryGUI.TradingGUI;
import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import me.jaymar921.kumandraseconomy.datahandlers.TradingData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TradingListener implements Listener {
    private final Map<String,String> lang;
    private static final DecimalFormat df2 = new DecimalFormat("###,###,###.##");
    KumandrasEconomy plugin;
    List<Integer> traderSlot;
    List<Integer> buyerSlot;
    public TradingListener(KumandrasEconomy plugin){
        this.plugin = plugin;
        lang = plugin.getDataHandler().getLanguageData();
        traderSlot = new TradingGUI().traderSlot();
        buyerSlot = new TradingGUI().buyerSlot();
    }

    @EventHandler
    private void leftClickInvalidTradingItems(InventoryClickEvent event){
        if(!plugin.getTradingHandler().getTradePersonnel().containsKey(event.getInventory()))
            return;

        List<Integer> deny_slots = plugin.getTradingHandler().denySlots();
        if(deny_slots.contains(event.getRawSlot())){
            event.setCancelled(true);
        }
        if(event.isShiftClick())
            event.setCancelled(true);
    }

    @EventHandler
    private void playerLeaveInventory(InventoryCloseEvent event){
        if(!plugin.getTradingHandler().getTradePersonnel().containsKey(event.getInventory()))
            return;

        for(TradingData d : plugin.getTradingHandler().getTradingDataSession().get(event.getInventory()))
            if(d.getOwner().equals(event.getPlayer())) {
                if(d.getRole().equals("trader")){
                    for(int i = 0; i < event.getInventory().getSize(); i++)
                        if (traderSlot.contains(i))
                            if(event.getInventory().getItem(i)!=null)
                                d.getOwner().getInventory().addItem(event.getInventory().getItem(i));
                }else{
                    for(int i = 0; i < event.getInventory().getSize(); i++)
                        if (buyerSlot.contains(i))
                            if(event.getInventory().getItem(i)!=null)
                                d.getOwner().getInventory().addItem(event.getInventory().getItem(i));
                }
            }
        List<Player> players = new ArrayList<>();
        for(String key_uuid : plugin.getTradingHandler().getActiveSession().keySet())
            if(plugin.getTradingHandler().getActiveSession().get(key_uuid).contains((Player)event.getPlayer())) {
                players = plugin.getTradingHandler().getActiveSession().get(key_uuid);
                plugin.getTradingHandler().getActiveSession().remove(key_uuid);
                break;
            }

        if(!players.isEmpty()){
            for(Player player : players){
                player.sendMessage(ChatColor.RED+lang.get("TradingEnded"));
                if(!event.getPlayer().equals(player))
                    player.closeInventory();
            }

        }

    }

    @EventHandler
    private void TradingSystem(InventoryClickEvent event){
        if(!plugin.getTradingHandler().getTradePersonnel().containsKey(event.getClickedInventory()))
            return;
        TradingData data = null;
        TradingData partner_data = null;
        for(TradingData d : plugin.getTradingHandler().getTradingDataSession().get(event.getClickedInventory()))
            if(d.getOwner().equals(event.getWhoClicked()))
                data = d;
            else
                partner_data = d;

        if(data == null)
            return;

        int slot = event.getRawSlot();

        if(event.getClick().equals(ClickType.SHIFT_LEFT)) {
            event.setCancelled(true);
        }
        if(data.getRole().equals("trader")){
            if(buyerSlot.contains(slot)){
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                event.setCancelled(true);
            }
            if(data.isSet && slot!=43){
                data.getOwner().sendMessage(ChatColor.RED+lang.get("TradingWasSet"));
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                event.setCancelled(true);
                return;
            }
            if(slot == 37){
                if(data.getPrice()>plugin.getRegistryConfiguration().tradingIncrease){
                    data.setPrice(data.getPrice()-plugin.getRegistryConfiguration().tradingIncrease);
                }else{
                    data.getOwner().sendMessage(ChatColor.RED+lang.get("TradingPriceZero")+plugin.getRegistryConfiguration().currency_prefix);
                    data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                }
            }else if(slot == 38){
                data.setPrice(data.getPrice()+plugin.getRegistryConfiguration().tradingIncrease);
            }else if(slot== 39){
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                event.setCancelled(true);
            }else if(slot == 48 && !event.getClickedInventory().getItem(48).getType().equals(Material.ORANGE_STAINED_GLASS_PANE)){
                int items = 0;
                for(int i = 0; i < event.getClickedInventory().getSize(); i++){
                    if(traderSlot.contains(i)){
                        if(event.getClickedInventory().getItem(i)!=null)
                            items++;
                    }
                }
                if(items!=0){
                    data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_YES,1.0f,1.0f);
                    data.getOwner().sendMessage(ChatColor.GREEN+lang.get("TradingSet"));
                    data.isSet = true;
                    event.getClickedInventory().setItem(48, new TradingGUI().glassItem());
                    if(event.getClickedInventory().getItem(39)!=null) {
                        ItemStack slot_39 = event.getClickedInventory().getItem(39);
                        assert slot_39 != null;
                        ItemMeta meta = slot_39.getItemMeta();
                        assert meta != null;
                        meta.setDisplayName(ChatColor.LIGHT_PURPLE+lang.get("Trade")+" "+data.getOwner().getName()+" "+lang.get("Items"));
                        meta.setLore(Arrays.asList(ChatColor.GREEN+"for "+ChatColor.GOLD+data.getPrice()+ KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix, ChatColor.GREEN+lang.get("TradeIsSet")));
                        slot_39.setItemMeta(meta);
                        event.getClickedInventory().setItem(39, slot_39);
                    }
                }else{
                    data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                    data.getOwner().sendMessage(ChatColor.RED+lang.get("EmptyTrade"));
                }
            }else if(slot == 41 || slot == 42 || slot == 52){
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
            }else if(slot == 43){
                if(partner_data!=null){
                    if(!partner_data.isSet)
                        return;
                    if(partner_data.isPaid)
                        return;
                    double partner_price = partner_data.getPrice();
                    double player_balance = plugin.getDataHandler().getStatusHolder().get(event.getWhoClicked().getUniqueId().toString()).getBalance();

                    if(player_balance<partner_price){
                        partner_data.getOwner().sendMessage(ChatColor.DARK_AQUA+""+event.getWhoClicked().getName()+ChatColor.RED+" "+lang.get("TradeCantAfford1"));
                        event.getWhoClicked().sendMessage(ChatColor.RED+lang.get("TradeCantAfford")+" "+ChatColor.GOLD+df2.format(player_balance)+plugin.getRegistryConfiguration().currency_prefix);
                        return;
                    }else{
                        for(int i =0; i < event.getClickedInventory().getSize(); i++){
                            if(buyerSlot.contains(i))
                                if(event.getClickedInventory().getItem(i)!=null){
                                    if(event.getWhoClicked().getInventory().firstEmpty()==-1)
                                        event.getWhoClicked().getWorld().dropItemNaturally(event.getWhoClicked().getLocation(), event.getClickedInventory().getItem(i));
                                    else
                                        event.getWhoClicked().getInventory().addItem(event.getClickedInventory().getItem(i));
                                    event.getClickedInventory().setItem(i, new ItemStack(Material.AIR));
                                }
                        }
                        player_balance-=partner_price;
                        plugin.getDataHandler().getStatusHolder().get(event.getWhoClicked().getUniqueId().toString()).setBalance(player_balance);
                        partner_data.getOwner().sendMessage(ChatColor.DARK_AQUA+""+event.getWhoClicked().getName()+ChatColor.GREEN+" "+lang.get("BoughtTrade"));
                        partner_data.isPaid = true;
                        event.getWhoClicked().sendMessage(ChatColor.GREEN+lang.get("BoughtTrade1")+" "+ChatColor.GOLD+df2.format(player_balance)+plugin.getRegistryConfiguration().currency_prefix);
                        if(data.isPaid && partner_data.isPaid){
                            event.getWhoClicked().closeInventory();
                        }
                        return;
                    }

                }
            }

            //update slot 39
            if(event.getClickedInventory().getItem(39)!=null && !data.isSet) {
                ItemStack slot_39 = event.getClickedInventory().getItem(39);
                assert slot_39 != null;
                ItemMeta meta = slot_39.getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.LIGHT_PURPLE+lang.get("Trade")+" "+data.getOwner().getName()+" "+lang.get("Items"));
                meta.setLore(Arrays.asList(ChatColor.GREEN+"for "+ChatColor.GOLD+data.getPrice()+ KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix, ChatColor.RED+lang.get("NotSet")));
                slot_39.setItemMeta(meta);
                event.getClickedInventory().setItem(39, slot_39);
            }
        }else if(data.getRole().equals("buyer")){
            if(traderSlot.contains(slot)){
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                event.setCancelled(true);
            }
            if(data.isSet && slot!=39){
                data.getOwner().sendMessage(ChatColor.RED+lang.get("TradingWasSet"));
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                event.setCancelled(true);
                return;
            }
            if(slot == 41){
                if(data.getPrice()>plugin.getRegistryConfiguration().tradingIncrease){
                    data.setPrice(data.getPrice()-plugin.getRegistryConfiguration().tradingIncrease);
                }else{
                    data.getOwner().sendMessage(ChatColor.RED+lang.get("TradingPriceZero")+plugin.getRegistryConfiguration().currency_prefix);
                    data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                }
            }else if(slot == 42){
                data.setPrice(data.getPrice()+plugin.getRegistryConfiguration().tradingIncrease);
            }else if(slot== 43){
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                event.setCancelled(true);
            }else if(slot == 52 && !event.getClickedInventory().getItem(52).getType().equals(Material.ORANGE_STAINED_GLASS_PANE)){
                int items = 0;
                for(int i = 0; i < event.getClickedInventory().getSize(); i++){
                    if(buyerSlot.contains(i)){
                        if(event.getClickedInventory().getItem(i)!=null)
                            items++;
                    }
                }
                if(items!=0){
                    data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_YES,1.0f,1.0f);
                    data.getOwner().sendMessage(ChatColor.GREEN+lang.get("TradingSet"));
                    data.isSet = true;
                    event.getClickedInventory().setItem(52, new TradingGUI().glassItem());
                    if(event.getClickedInventory().getItem(43)!=null) {
                        ItemStack slot_43 = event.getClickedInventory().getItem(43);
                        assert slot_43 != null;
                        ItemMeta meta = slot_43.getItemMeta();
                        assert meta != null;
                        meta.setDisplayName(ChatColor.LIGHT_PURPLE+lang.get("Trade")+" "+data.getOwner().getName()+" "+lang.get("Items"));
                        meta.setLore(Arrays.asList(ChatColor.GREEN+"for "+ChatColor.GOLD+data.getPrice()+ KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix, ChatColor.GREEN+lang.get("TradeIsSet")));
                        slot_43.setItemMeta(meta);
                        event.getClickedInventory().setItem(43, slot_43);
                    }
                }else{
                    data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
                    data.getOwner().sendMessage(ChatColor.RED+lang.get("EmptyTrade"));
                }
            }else if(slot == 37 || slot == 38 || slot == 48){
                data.getOwner().playSound(data.getOwner().getLocation(), Sound.ENTITY_VILLAGER_NO,1.0f,1.0f);
            }else if(slot == 39){
                if(partner_data!=null){
                    if(!partner_data.isSet)
                        return;
                    if(partner_data.isPaid)
                        return;
                    double partner_price = partner_data.getPrice();
                    double player_balance = plugin.getDataHandler().getStatusHolder().get(event.getWhoClicked().getUniqueId().toString()).getBalance();

                    if(player_balance<partner_price){
                        partner_data.getOwner().sendMessage(ChatColor.DARK_AQUA+""+event.getWhoClicked().getName()+ChatColor.RED+" "+lang.get("TradeCantAfford1"));
                        event.getWhoClicked().sendMessage(ChatColor.RED+lang.get("TradeCantAfford")+" "+ChatColor.GOLD+df2.format(player_balance)+plugin.getRegistryConfiguration().currency_prefix);
                        return;
                    }else{
                        for(int i =0; i < event.getClickedInventory().getSize(); i++){
                            if(traderSlot.contains(i))
                                if(event.getClickedInventory().getItem(i)!=null){
                                    if(event.getWhoClicked().getInventory().firstEmpty()==-1)
                                        event.getWhoClicked().getWorld().dropItemNaturally(event.getWhoClicked().getLocation(), event.getClickedInventory().getItem(i));
                                    else
                                        event.getWhoClicked().getInventory().addItem(event.getClickedInventory().getItem(i));
                                    event.getClickedInventory().setItem(i, new ItemStack(Material.AIR));
                                }
                        }
                        player_balance-=partner_price;
                        plugin.getDataHandler().getStatusHolder().get(event.getWhoClicked().getUniqueId().toString()).setBalance(player_balance);
                        partner_data.getOwner().sendMessage(ChatColor.DARK_AQUA+""+event.getWhoClicked().getName()+ChatColor.GREEN+" "+lang.get("BoughtTrade"));
                        partner_data.isPaid = true;
                        event.getWhoClicked().sendMessage(ChatColor.GREEN+lang.get("BoughtTrade1")+" "+ChatColor.GOLD+df2.format(player_balance)+plugin.getRegistryConfiguration().currency_prefix);
                        if(data.isPaid && partner_data.isPaid){
                            event.getWhoClicked().closeInventory();
                        }
                        return;
                    }

                }
            }

            //update slot 43
            if(event.getClickedInventory().getItem(43)!=null && !data.isSet) {
                ItemStack slot_43 = event.getClickedInventory().getItem(43);
                assert slot_43 != null;
                ItemMeta meta = slot_43.getItemMeta();
                assert meta != null;
                meta.setDisplayName(ChatColor.LIGHT_PURPLE+"Trade "+data.getOwner().getName()+" items");
                meta.setLore(Arrays.asList(ChatColor.GREEN+"for "+ChatColor.GOLD+data.getPrice()+ KumandrasEconomy.getPlugin(KumandrasEconomy.class).getRegistryConfiguration().currency_prefix, ChatColor.RED+"(Not set)"));
                slot_43.setItemMeta(meta);
                event.getClickedInventory().setItem(43, slot_43);
            }
        }



    }

}
