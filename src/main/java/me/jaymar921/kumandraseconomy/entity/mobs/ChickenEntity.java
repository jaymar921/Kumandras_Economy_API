package me.jaymar921.kumandraseconomy.entity.mobs;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class ChickenEntity {

    @SuppressWarnings("deprecation")
    public LivingEntity spawnEntity(@NotNull final Location location,String customName){
        Entity chicken = location.getWorld().spawnEntity(location, EntityType.CHICKEN);
        LivingEntity Chicken = (LivingEntity) chicken;

        //PlaySound
        location.getWorld().playSound(location, Sound.ENTITY_CHICKEN_AMBIENT, 1.0f, 1.0f);

        //Set Display Name
        Chicken.setCustomName(customName);
        //Set max health
        Chicken.setMaxHealth(20);
        Chicken.setHealth(Chicken.getMaxHealth());
        //Add Resistance
        Chicken.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,99999, 100, false, false));

        //Set the entity to silent
        Chicken.setSilent(true);

        //Remove AI
        Chicken.setAI(false);

        return Chicken;
    }
}
