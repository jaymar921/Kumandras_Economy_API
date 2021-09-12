package me.jaymar921.kumandraseconomy.entity;


import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityTypeCheck {

    public boolean isHostile(Entity entity){
        if(entity.getType().equals(EntityType.CREEPER))
            return true;
        else if(entity.getType().equals(EntityType.SKELETON))
            return true;
        else if(entity.getType().equals(EntityType.WITCH))
            return true;
        else if(entity.getType().equals(EntityType.WITHER))
            return true;
        else if(entity.getType().equals(EntityType.WITHER_SKELETON))
            return true;
        else if(entity.getType().equals(EntityType.DROWNED))
            return true;
        else if(entity.getType().equals(EntityType.ENDERMAN))
            return true;
        else if(entity.getType().equals(EntityType.ENDERMITE))
            return true;
        else if(entity.getType().equals(EntityType.ZOMBIE))
            return true;
        else if(entity.getType().equals(EntityType.HUSK))
            return true;
        else if(entity.getType().equals(EntityType.ZOMBIE_VILLAGER))
            return true;
        else if(entity.getType().equals(EntityType.PIGLIN))
            return true;
        else if(entity.getType().equals(EntityType.PIGLIN_BRUTE))
            return true;
        else if(entity.getType().equals(EntityType.PILLAGER))
            return true;
        else if(entity.getType().equals(EntityType.ILLUSIONER))
            return true;
        else if(entity.getType().equals(EntityType.ENDER_DRAGON))
            return true;
        else return entity.getType().equals(EntityType.ZOMBIFIED_PIGLIN);
    }
}
