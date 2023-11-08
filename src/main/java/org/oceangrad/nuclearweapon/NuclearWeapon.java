package org.oceangrad.nuclearweapon;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.oceangrad.nuclearweapon.events.NuclearEvents;
import org.oceangrad.nuclearweapon.executors.*;
import org.oceangrad.nuclearweapon.tab_completers.CureTabCompleter;
import org.oceangrad.nuclearweapon.tab_completers.SpawnNuclearWeaponTabCompleter;
import org.oceangrad.nuclearweapon.util.Keys;
import org.oceangrad.nuclearweapon.util.Recipes;
import org.oceangrad.nuclearweapon.domain.PoisonSideEffect;
import org.oceangrad.nuclearweapon.domain.PoisonSideEffects;

import java.util.Random;

public final class NuclearWeapon extends JavaPlugin {

    public static final float EXPLOSION_FORCE = 150f;
    public static final int Y_MAX = 360;
    public static final int POISONED_ZONE_MAX_LIFETIME = 240000; // в секундах

    @Override
    public void onEnable() {

        for (World world : Bukkit.getWorlds()){
            for(Firework firework : world.getEntitiesByClass(Firework.class)){
                PersistentDataContainer data = firework.getPersistentDataContainer();

                if(!data.has(NamespacedKey.fromString("is-nuclear"), PersistentDataType.INTEGER))
                    continue;

                float length = data.get(NamespacedKey.fromString("flight-length"), PersistentDataType.FLOAT);

                Vector from = new Vector(data.get(NamespacedKey.fromString("x-start"), PersistentDataType.FLOAT), 0, data.get(NamespacedKey.fromString("z-start"), PersistentDataType.FLOAT));
                Vector to = new Vector(data.get(NamespacedKey.fromString("x-end"), PersistentDataType.FLOAT), 0, data.get(NamespacedKey.fromString("z-end"), PersistentDataType.FLOAT));

                Vector difference = new Vector((float) (from.getX() - to.getX()), 0, (float) (from.getZ() - to.getZ()));

                switch (data.get(NamespacedKey.fromString("stage"), PersistentDataType.INTEGER)){
                    case 1:
                        launch1Stage(firework, difference, from, to, length);
                    case 2:
                        launch2Stage(firework, difference, from, length);
                        break;
                    case 3:
                        launch3Stage(firework);
                        break;
                }
            }

            for(Marker marker : world.getEntitiesByClass(Marker.class)){
                if(marker.getPersistentDataContainer().has(Keys.POISONED_ZONE, PersistentDataType.INTEGER)){
                    startPoisonedZoneLifetimeCountdown(marker);
                }
            }
        }

        getCommand("createBomb").setExecutor(new SpawnNuclearWeaponCommandExecutor());
        getCommand("createBomb").setTabCompleter(new SpawnNuclearWeaponTabCompleter());

        getCommand("giveBomb").setExecutor(new GiveNuclearCommandExecutor());

        getCommand("cure").setExecutor(new CureCommandExecutor());
        getCommand("cure").setTabCompleter(new CureTabCompleter());

        getCommand("clearSideEffect").setExecutor(new ClearSideEffectsCommandExecutor());

        getCommand("cureWorld").setExecutor(new CureWorldCommandExecutor());

        getCommand("giveHazmat").setExecutor(new GiveHazmatCommandExecutor());

        getServer().getPluginManager().registerEvents(new NuclearEvents(), this);

        Recipes.register();

    }

    public static boolean clearPlayerSideEffect(Player player) {
        if(player.getPersistentDataContainer().has(Keys.POISON_SIDE_EFFECT_TYPE, PersistentDataType.STRING)){
            player.getPersistentDataContainer().remove(Keys.POISON_SIDE_EFFECT_TYPE);
            return true;
        }

        return false;
    }

    public static void cureWorld(World world){
        for(Marker marker : world.getEntitiesByClass(Marker.class)){
            if(marker.getPersistentDataContainer().has(Keys.POISONED_ZONE, PersistentDataType.INTEGER)){
                Bukkit.getScheduler().cancelTask(marker.getPersistentDataContainer().get(Keys.POISONED_ZONE_ID, PersistentDataType.INTEGER));
                marker.remove();
            }
        }
    }

    public static void cureAllWorlds(){
        for(World world : Bukkit.getWorlds()){
            cureWorld(world);
        }
    }

    public static PotionEffectType getPlayerPoisonEffect(Player player){
        if(!player.getPersistentDataContainer().has(Keys.POISON_SIDE_EFFECT_TYPE, PersistentDataType.STRING)){
            player.getPersistentDataContainer().set(Keys.POISON_SIDE_EFFECT_TYPE, PersistentDataType.STRING,
                    PoisonSideEffects.POISONED_SIDE_EFFECTS.get(
                            new Random().nextInt(PoisonSideEffects.POISONED_SIDE_EFFECTS.size())
                    ).getEffectType().getName()
            );
        }

        return PotionEffectType.getByName(player.getPersistentDataContainer().get(Keys.POISON_SIDE_EFFECT_TYPE, PersistentDataType.STRING));
    }

    public static void cureZoneAtPlayer(Player player){
        for(Entity poisonedZone : player.getNearbyEntities(30, 30, 30)){
            if(poisonedZone.getPersistentDataContainer().has(Keys.POISONED_ZONE, PersistentDataType.INTEGER)){
                Bukkit.getScheduler().cancelTask(poisonedZone.getPersistentDataContainer().get(Keys.POISONED_ZONE_ID, PersistentDataType.INTEGER));
                poisonedZone.remove();
                player.sendMessage( ChatColor.GREEN + "cured this zone");
                return;
            }
        }

        player.sendMessage( ChatColor.RED + "No zone to cure");
    }

    public static void cureAllZonesAtPlayer(Player player){
        for(Entity poisonedZone : player.getNearbyEntities(30, 30, 30)){
            if(poisonedZone.getPersistentDataContainer().has(Keys.POISONED_ZONE, PersistentDataType.INTEGER)){
                Bukkit.getScheduler().cancelTask(poisonedZone.getPersistentDataContainer().get(Keys.POISONED_ZONE_ID, PersistentDataType.INTEGER));
                poisonedZone.remove();
                player.sendMessage( ChatColor.GREEN + "cured this zone");
            }
        }
        player.sendMessage( ChatColor.RED + "No zone to cure");
    }

    // РЕКОМЕНДУЕМЫЙ РАДИУС ЗАПУСКА - 100 БЛОКОВ
    public static void launchNuclear(World world, Vector from, Vector to){
        Firework nuclear = (Firework) world.spawnEntity(new Location(world, from.getX(), from.getY(), from.getZ()), EntityType.FIREWORK);
        PersistentDataContainer nuclearData = nuclear.getPersistentDataContainer();

        nuclearData.set(NamespacedKey.fromString("is-nuclear"), PersistentDataType.INTEGER, 0);

        Vector difference = new Vector((float) (from.getX() - to.getX()), 0, (float) (from.getZ() - to.getZ()));

        float xLen = (float) Math.abs(difference.getX());
        float zLen = (float) Math.abs(difference.getZ());

        float length = (float) Math.sqrt(Math.pow(xLen, 2) + Math.pow(zLen, 2));

        nuclearData.set(NamespacedKey.fromString("flight-length"), PersistentDataType.FLOAT, length);

        nuclearData.set(NamespacedKey.fromString("x-start"), PersistentDataType.FLOAT, (float)from.getX());
        nuclearData.set(NamespacedKey.fromString("x-end"), PersistentDataType.FLOAT, (float)to.getX());

        nuclearData.set(NamespacedKey.fromString("z-start"), PersistentDataType.FLOAT, (float)from.getZ());
        nuclearData.set(NamespacedKey.fromString("z-end"), PersistentDataType.FLOAT, (float)to.getZ());

        nuclear.setMaxLife(999999);
        nuclear.setInvulnerable(true);
        nuclear.setShotAtAngle(true);
        nuclear.setVelocity(new Vector(0, 0, 0));

        launch1Stage(nuclear, difference, from, to, length);
    }

    public static void launch1Stage(Firework nuclear, Vector difference, Vector from, Vector to, float length){
        PersistentDataContainer nuclearData = nuclear.getPersistentDataContainer();

        nuclearData.set(NamespacedKey.fromString("stage"), PersistentDataType.INTEGER, 1);

        BukkitScheduler scheduler = Bukkit.getScheduler();

        if(nuclearData.has(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER)){
            scheduler.cancelTask(nuclearData.get(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER));
        }

        nuclearData.set(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER, scheduler.scheduleSyncRepeatingTask(JavaPlugin.getPlugin(NuclearWeapon.class), new Runnable() {
            @Override
            public void run() {
                nuclear.setVelocity(new Vector(0, 1, 0));
                nuclear.setLife(0);

                if(nuclear.getLocation().getY() > Y_MAX){
                    nuclear.setVelocity(new Vector(0, 0, 0));
                    scheduler.cancelTask(nuclearData.get(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER));

                    launch2Stage(nuclear, difference, from, length);
                }
            }
        }, 0L, 1L));

    }

    public static void launch2Stage(Firework nuclear, Vector difference, Vector from, float length){
        PersistentDataContainer nuclearData = nuclear.getPersistentDataContainer();

        nuclearData.set(NamespacedKey.fromString("stage"), PersistentDataType.INTEGER, 2);

        BukkitScheduler scheduler = Bukkit.getScheduler();

        if(nuclearData.has(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER)){
            scheduler.cancelTask(nuclearData.get(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER));
        }

        nuclearData.set(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER, scheduler.scheduleSyncRepeatingTask(JavaPlugin.getPlugin(NuclearWeapon.class), new Runnable() {
            @Override
            public void run() {
                nuclear.setLife(0);
                nuclear.setVelocity(new Vector(-difference.getX()*0.1, 0, -difference.getZ()*0.1));

                float xLenght = (float) Math.abs(from.getX() - nuclear.getLocation().getX());
                float zLenght = (float) Math.abs(from.getZ() - nuclear.getLocation().getZ());

                float flightLength = (float) Math.sqrt(Math.pow(xLenght, 2) + Math.pow(zLenght, 2));

                if(flightLength >= length-1){
                    nuclear.setLife(0);

                    launch3Stage(nuclear);

                    scheduler.cancelTask(nuclearData.get(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER));
                }
            }
        }, 0L, 1L));
    }

    public static void launch3Stage(Firework nuclear){
        PersistentDataContainer nuclearData = nuclear.getPersistentDataContainer();
        nuclearData.set(NamespacedKey.fromString("stage"), PersistentDataType.INTEGER, 1);

        nuclear.setVelocity(new Vector(0, -1, 0));
    }

    public static void startPoisonedZoneLifetimeCountdown(Marker marker){
        BukkitScheduler scheduler = Bukkit.getScheduler();

        if(!marker.getPersistentDataContainer().has(Keys.POISONED_ZONE_LIFETIME, PersistentDataType.INTEGER))
            marker.getPersistentDataContainer().set(Keys.POISONED_ZONE_LIFETIME, PersistentDataType.INTEGER, POISONED_ZONE_MAX_LIFETIME);

        marker.getPersistentDataContainer().set(Keys.POISONED_ZONE_ID, PersistentDataType.INTEGER,
                scheduler.scheduleSyncRepeatingTask(JavaPlugin.getPlugin(NuclearWeapon.class), new Runnable() {
                    @Override
                    public void run() {

                        if(marker.getPersistentDataContainer().get(Keys.POISONED_ZONE_LIFETIME, PersistentDataType.INTEGER) <= 1){
                            scheduler.cancelTask(marker.getPersistentDataContainer().get(Keys.POISONED_ZONE_ID, PersistentDataType.INTEGER));
                            marker.remove();
                        }

                        for(Entity e : marker.getNearbyEntities(30, 30, 30)){
                            if(e instanceof LivingEntity current_entity){
                                boolean isPlayer = current_entity instanceof Player;

                                if(isPlayer){
                                    Player player = (Player) current_entity;

                                    if(player.getEquipment().getHelmet() == null || !player.getEquipment().getHelmet().getItemMeta().getPersistentDataContainer().has(Keys.HAZMAT, PersistentDataType.INTEGER)){
                                        PoisonSideEffect effect = PoisonSideEffects.getByPotionEffectType(getPlayerPoisonEffect((Player) current_entity));

                                        current_entity.addPotionEffect(new PotionEffect(effect.getEffectType(), 40, effect.getAmplifier(), true, false));
                                        current_entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1, true, false));
                                    }
                                    else{
                                        ItemStack currentHazmat = player.getEquipment().getHelmet();

                                        Damageable hazmatMeta = (Damageable) currentHazmat.getItemMeta();

                                        if(hazmatMeta.getDamage() >= currentHazmat.getType().getMaxDurability())
                                        {
                                            currentHazmat.setAmount(0);

                                            player.playSound(player, Sound.ENTITY_ITEM_BREAK, 5, 1);

                                        }
                                        else{
                                            if(hazmatMeta.hasEnchant(Enchantment.DURABILITY)){
                                                float enLevel = hazmatMeta.getEnchantLevel(Enchantment.DURABILITY);
                                                float randFloat = new Random().nextFloat();

                                                if(randFloat > enLevel/4){
                                                    hazmatMeta.setDamage(hazmatMeta.getDamage()+1);
                                                }
                                            }
                                            else{
                                                hazmatMeta.setDamage(hazmatMeta.getDamage()+1);
                                            }

                                            currentHazmat.setItemMeta(hazmatMeta);
                                        }
                                    }
                                }
                                else{
                                    current_entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 1, true, false));
                                }

                            }
                        }

                        marker.getPersistentDataContainer().set(Keys.POISONED_ZONE_LIFETIME, PersistentDataType.INTEGER, marker.getPersistentDataContainer().get(Keys.POISONED_ZONE_LIFETIME, PersistentDataType.INTEGER) - 1);
                    }
                }, 0L, 20L)
        );
    }
}
