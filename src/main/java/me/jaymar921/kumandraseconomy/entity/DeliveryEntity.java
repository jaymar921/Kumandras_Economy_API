package me.jaymar921.kumandraseconomy.entity;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class DeliveryEntity {

    KumandrasEconomy main;
    public DeliveryEntity (KumandrasEconomy plugin){
        main = plugin;
    }

    public void deleteMobs(){
        if(!main.getDeliveryHandler().getEntityDelivery().isEmpty())
        for(String entity_id : main.getDeliveryHandler().getEntityDelivery().keySet()){
            main.getDeliveryHandler().getEntityDelivery().get(entity_id).remove();
        }
    }
}
