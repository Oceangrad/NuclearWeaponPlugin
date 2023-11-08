package org.oceangrad.nuclearweapon.domain;

import org.bukkit.potion.PotionEffectType;

public class PoisonSideEffect {
    private int amplifier;
    private PotionEffectType effectType;

    public PoisonSideEffect(int amplifier, PotionEffectType effectType){
        this.amplifier = amplifier;
        this.effectType = effectType;
    }

    public Integer getAmplifier(){
        return amplifier;
    }
    public PotionEffectType getEffectType(){
        return effectType;
    }
}
