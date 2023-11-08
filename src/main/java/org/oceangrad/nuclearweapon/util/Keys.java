package org.oceangrad.nuclearweapon.util;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.oceangrad.nuclearweapon.NuclearWeapon;

public final class Keys {
    public static NamespacedKey NUCLEAR_ITEM = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "nuclearItem");
    public static NamespacedKey NUCLEAR_COORDS_RECIPE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "nuclearCoordsRecipe");
    public static NamespacedKey NUCLEAR_RECIPE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "nuclearRecipe");
    public static NamespacedKey SUPER_GUNPOWDER_RECIPE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "superGunpowderRecipe");
    public static NamespacedKey SUPER_GUNPOWDER = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "superGunpowder");
    public static NamespacedKey POISONED_ZONE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "poisonedZone");
    public static NamespacedKey POISONED_PLAYER_ID = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "poisonedPlayerId");
    public static NamespacedKey POISONED_ZONE_LIFETIME = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "poisonedZoneLifetime");
    public static NamespacedKey POISONED_ZONE_ID = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "poisonedZoneId");
    public static NamespacedKey POISON_SIDE_EFFECT_TYPE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "sideEffect");
    public static NamespacedKey HAZMAT = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "hazmat");
    public static NamespacedKey HAZMAT_RECIPE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "hazmatRecipe");
    public static NamespacedKey FILTER = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "filter");
    public static NamespacedKey FILTER_RECIPE = new NamespacedKey(JavaPlugin.getPlugin(NuclearWeapon.class), "filterRecipe");

}
