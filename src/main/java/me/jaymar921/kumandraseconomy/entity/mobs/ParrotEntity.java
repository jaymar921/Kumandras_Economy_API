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

public class ParrotEntity {

    @SuppressWarnings("deprecation")
    public LivingEntity spawnEntity(@NotNull final Location location, String customName){
        location.setPitch(0.0f);
        Entity parrot = location.getWorld().spawnEntity(location, EntityType.PARROT);
        LivingEntity Parrot = (LivingEntity) parrot;

        //PlaySound
        location.getWorld().playSound(location, Sound.ENTITY_PARROT_AMBIENT, 1.0f, 1.0f);
        //Set Display Name
        Parrot.setCustomName(customName);
        //Set max health
        Parrot.setMaxHealth(20);
        Parrot.setHealth(Parrot.getMaxHealth());
        //Add Resistance
        Parrot.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,99999, 20, false, false));

        //Set the entity to silent
        Parrot.setSilent(true);
        //Remove AI
        Parrot.setAI(false);
        //Add persistent
        Parrot.getPersistentDataContainer().set(new NamespacedKey(KumandrasEconomy.getPlugin(KumandrasEconomy.class), "DeliverParrot"), PersistentDataType.STRING, customName);

        return Parrot;
    }
}
