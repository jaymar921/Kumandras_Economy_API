package me.jaymar921.kumandraseconomy.entity.mobs;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class CowEntity {

    @SuppressWarnings("deprecation")
    public LivingEntity spawnEntity(@NotNull final Location location, String customName){
        Entity cow = location.getWorld().spawnEntity(location, EntityType.CHICKEN);
        LivingEntity Cow = (LivingEntity) cow;

        //PlaySound
        location.getWorld().playSound(location, Sound.ENTITY_COW_AMBIENT, 1.0f, 1.0f);
        //Set Display Name
        Cow.setCustomName(customName);
        //Set max health
        Cow.setMaxHealth(20);
        Cow.setHealth(Cow.getMaxHealth());
        //Add Resistance
        Cow.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,99999, 100, false, false));

        //Set the entity to silent
        Cow.setSilent(true);
        //Remove AI
        Cow.setAI(false);

        return Cow;
    }

}
