package org.oceangrad.nuclearweapon.events;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.oceangrad.nuclearweapon.domain.Item_stacks.NuclearItemStack;
import org.oceangrad.nuclearweapon.NuclearWeapon;
import org.oceangrad.nuclearweapon.util.Keys;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.oceangrad.nuclearweapon.NuclearWeapon.EXPLOSION_FORCE;

public class NuclearEvents implements Listener {

    @EventHandler
    public static void OnNuclearHit(ProjectileHitEvent event){
        boolean isNuclear = event.getEntity() instanceof Firework && event.getEntity().getPersistentDataContainer().has(NamespacedKey.fromString("is-nuclear"), PersistentDataType.INTEGER);
        if(!isNuclear)
            return;

        BukkitScheduler scheduler = Bukkit.getScheduler();

        Firework nuclear = (Firework) event.getEntity();
        PersistentDataContainer nuclearData = nuclear.getPersistentDataContainer();

        scheduler.cancelTask(nuclearData.get(NamespacedKey.fromString("flight-id"), PersistentDataType.INTEGER));
        nuclear.setLife(nuclear.getMaxLife());

        if(event.getHitBlock() != null){
            event.getHitBlock().getWorld().createExplosion(event.getHitBlock().getLocation(), EXPLOSION_FORCE, true, true);

            Marker marker = (Marker) event.getHitBlock().getWorld().spawnEntity(event.getHitBlock().getLocation().add(0.5, 2, 0.5), EntityType.MARKER);

            marker.getBoundingBox().expand(new Vector(30, 30, 30));
            marker.getPersistentDataContainer().set(Keys.POISONED_ZONE, PersistentDataType.INTEGER, 0);

            NuclearWeapon.startPoisonedZoneLifetimeCountdown(marker);
        }
        else{
            event.getHitEntity().getWorld().createExplosion(event.getHitEntity().getLocation(), EXPLOSION_FORCE, true, true);

            Marker marker = (Marker) event.getHitEntity().getWorld().spawnEntity(event.getHitEntity().getLocation().add(0.5, 2, 0.5), EntityType.MARKER);

            marker.getBoundingBox().expand(new Vector(30, 30, 30));
            marker.getPersistentDataContainer().set(Keys.POISONED_ZONE, PersistentDataType.INTEGER, 0);

            NuclearWeapon.startPoisonedZoneLifetimeCountdown(marker);
        }
    }

    @EventHandler
    public static void OnNuclearLaunch(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() instanceof Player){
            Player player = (Player) event.getEntity().getShooter();
            ItemStack nuclearItem = player.getItemInHand();
            ItemMeta nuclearMeta = nuclearItem.getItemMeta();

            if(nuclearMeta.getPersistentDataContainer().has(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER)){
                NuclearWeapon.launchNuclear(
                        player.getWorld(),
                        event.getEntity().getLocation().toVector(),
                        new Vector(nuclearMeta.getPersistentDataContainer().get(NamespacedKey.fromString("x-end"), PersistentDataType.FLOAT), 0, nuclearMeta.getPersistentDataContainer().get(NamespacedKey.fromString("z-end"), PersistentDataType.FLOAT))
                );

                event.setCancelled(true);
            }
        }
    }



    @EventHandler
    public static void DispenserNuclear(BlockDispenseEvent event){
        ItemStack nuclearItem = event.getItem();
        ItemMeta nuclearMeta = nuclearItem.getItemMeta();

        if(nuclearMeta.getPersistentDataContainer().has(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER)){
            NuclearWeapon.launchNuclear(
                    event.getBlock().getWorld(),
                    event.getBlock().getLocation().add(0.5, 1, 0.5).toVector(),
                    new Vector(nuclearMeta.getPersistentDataContainer().get(NamespacedKey.fromString("x-end"), PersistentDataType.FLOAT), 0, nuclearMeta.getPersistentDataContainer().get(NamespacedKey.fromString("z-end"), PersistentDataType.FLOAT))
            );
        }
    }

    @EventHandler
    public static void ProgramNuclearWithCoords(CraftItemEvent event){
//        ItemStack nuclear = event.getInventory().all(Material.FIREWORK_ROCKET).get(0);
//        ItemStack coordsPaper = event.getInventory().all(Material.PAPER).get(0);

/*
        for (ItemStack itemStack : event.getInventory().getMatrix()) {
            if(itemStack == null)
                continue;
            Bukkit.broadcastMessage(itemStack.getType().toString());
        }
*/

        ItemStack nuclear = Arrays.stream(event.getInventory().getMatrix())
                .filter(item -> item != null && item.getType() == Material.FIREWORK_ROCKET)
                .collect(Collectors.toList()).get(0);

        ItemStack coordsPaper = Arrays.stream(event.getInventory().getMatrix())
                .filter(item -> item != null && item.getType() == Material.PAPER)
                .collect(Collectors.toList()).get(0);

//        for (ItemStack itemStack : collect) {
//            Bukkit.broadcastMessage(itemStack.getType().toString());
//        }

//        Arrays.stream(event.getInventory().getMatrix())


        if(!nuclear.getItemMeta().getPersistentDataContainer().has(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER)){
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }

        List<Float> coords;

        try{
            coords = Arrays.stream(coordsPaper.getItemMeta().getDisplayName().split(",")).map(Float::parseFloat).toList();
        }
        catch (NumberFormatException e){
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }

        if(!nuclear.getItemMeta().getPersistentDataContainer().has(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER)){
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }

        if(coords.size() < 2){
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }

        Vector to = new Vector(coords.get(0)+0.5, 0, coords.get(1)+0.5);

        ItemStack newNuclear = NuclearItemStack.getNuclearStack(event.getWhoClicked().getLocation().toVector(), to);

        event.setCurrentItem(newNuclear);
    }

    @EventHandler
    public static void CraftNuclear(CraftItemEvent event){
        if(!event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER))
            return;

        event.setCurrentItem(NuclearItemStack.getNuclearStack(event.getWhoClicked().getLocation().toVector(), event.getWhoClicked().getLocation().toVector()));
    }
}
