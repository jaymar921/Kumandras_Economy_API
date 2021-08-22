package me.jaymar921.kumandraseconomy.entity.mobs;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class SheepEntity {

    @SuppressWarnings("deprecation")
    public LivingEntity spawnEntity(@NotNull final Location location, String customName){
        Entity sheep = location.getWorld().spawnEntity(location, EntityType.CHICKEN);
        LivingEntity Sheep = (LivingEntity) sheep;

        //PlaySound
        location.getWorld().playSound(location, Sound.ENTITY_SHEEP_AMBIENT, 1.0f, 1.0f);
        //Set Display Name
        Sheep.setCustomName(customName);
        //Set max health
        Sheep.setMaxHealth(20);
        Sheep.setHealth(Sheep.getMaxHealth());
        //Add Resistance
        Sheep.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,99999, 100, false, false));

        //Set the entity to silent
        Sheep.setSilent(true);
        //Remove AI
        Sheep.setAI(false);

        return Sheep;
    }

}
