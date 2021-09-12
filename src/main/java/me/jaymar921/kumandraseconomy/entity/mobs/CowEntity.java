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

public class CowEntity {

    @SuppressWarnings("deprecation")
    public LivingEntity spawnEntity(@NotNull final Location location, String customName){
        location.setPitch(0.0f);
        Entity cow = location.getWorld().spawnEntity(location, EntityType.COW);
        LivingEntity Cow = (LivingEntity) cow;

        //PlaySound
        location.getWorld().playSound(location, Sound.ENTITY_COW_AMBIENT, 1.0f, 1.0f);
        //Set Display Name
        Cow.setCustomName(customName);
        //Set max health
        Cow.setMaxHealth(20);
        Cow.setHealth(Cow.getMaxHealth());
        //Add Resistance
        Cow.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,99999, 20, false, false));

        //Set the entity to silent
        Cow.setSilent(true);
        //Remove AI
        Cow.setAI(false);

        //Add persistent
        Cow.getPersistentDataContainer().set(new NamespacedKey(KumandrasEconomy.getPlugin(KumandrasEconomy.class), "DeliverCow"), PersistentDataType.STRING, customName);

        return Cow;
    }

}
