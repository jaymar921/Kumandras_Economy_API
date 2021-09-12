package me.jaymar921.kumandraseconomy.entity.mobs;

import me.jaymar921.kumandraseconomy.KumandrasEconomy;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class VillagerEntity {

    public LivingEntity spawnEntity(@NotNull Location location, String customName){
        location.setPitch(10.0f);
        location.setX(location.getBlockX()+0.5);
        location.setZ(location.getBlockZ()+0.5);
        Entity villager = location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        LivingEntity Villager = (LivingEntity) villager;

        location.getWorld().playSound(location, Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);

        Villager.setCustomName(customName);

        Villager.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 100, false, false));

        Villager.setSilent(true);

        Villager.setAI(false);

        Villager.getPersistentDataContainer().set(new NamespacedKey(KumandrasEconomy.getPlugin(KumandrasEconomy.class), "VillagerShop"), PersistentDataType.STRING, customName);

        return Villager;
    }
}
