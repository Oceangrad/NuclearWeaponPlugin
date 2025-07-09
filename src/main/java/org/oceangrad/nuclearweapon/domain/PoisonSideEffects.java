package org.oceangrad.nuclearweapon.domain;

import org.bukkit.potion.PotionEffectType;

import java.util.List;

public final class PoisonSideEffects {
    public static final List<PoisonSideEffect> POISONED_SIDE_EFFECTS = List.of(
            new PoisonSideEffect(1, PotionEffectType.POISON),
            new PoisonSideEffect(1, PotionEffectType.BLINDNESS),
            new PoisonSideEffect(1, PotionEffectType.NAUSEA),
            new PoisonSideEffect(4, PotionEffectType.HUNGER)
    );

    public static PoisonSideEffect getByPotionEffectType(PotionEffectType effectType){
        for(PoisonSideEffect effect : POISONED_SIDE_EFFECTS){
            if(effect.getEffectType().equals(effectType)){
                return effect;
            }
        }
        return POISONED_SIDE_EFFECTS.get(0);
    }
}
