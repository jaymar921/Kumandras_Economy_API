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

public class PigEntity {

    @SuppressWarnings("deprecation")
    public LivingEntity spawnEntity(@NotNull final Location location, String customName){
        Entity pig = location.getWorld().spawnEntity(location, EntityType.PIG);
        LivingEntity Pig = (LivingEntity) pig;

        //PlaySound
        location.getWorld().playSound(location, Sound.ENTITY_PIG_AMBIENT, 1.0f, 1.0f);
        //Set Display Name
        Pig.setCustomName(customName);
        //Set max health
        Pig.setMaxHealth(20);
        Pig.setHealth(Pig.getMaxHealth());
        //Add Resistance
        Pig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,99999, 20, false, false));

        //Set the entity to silent
        Pig.setSilent(true);
        //Remove AI
        Pig.setAI(false);
        //Add persistent
        Pig.getPersistentDataContainer().set(new NamespacedKey(KumandrasEconomy.getPlugin(KumandrasEconomy.class), "DeliverChicken"), PersistentDataType.STRING, customName);

        return Pig;
    }
}
