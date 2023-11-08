package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.oceangrad.nuclearweapon.util.Keys;

import java.util.List;


public final class NuclearItemStack {
    public static ItemStack getNuclearStack(Vector from, Vector to){
        ItemStack nuclearStack = new ItemStack(Material.FIREWORK_ROCKET);

        ItemMeta nuclearMeta = nuclearStack.getItemMeta();

        nuclearMeta.setDisplayName("Nuclear Rocket");
        nuclearMeta.getPersistentDataContainer().set(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER, 0);
//        nuclearMeta.getPersistentDataContainer().set(NamespacedKey.fromString("world"), PersistentDataType.STRING, world.getName());

        nuclearMeta.getPersistentDataContainer().set(NamespacedKey.fromString("x-start"), PersistentDataType.FLOAT, (float) from.getX());
        nuclearMeta.getPersistentDataContainer().set(NamespacedKey.fromString("x-end"), PersistentDataType.FLOAT, (float) to.getX());

        nuclearMeta.getPersistentDataContainer().set(NamespacedKey.fromString("z-start"), PersistentDataType.FLOAT, (float) from.getZ());
        nuclearMeta.getPersistentDataContainer().set(NamespacedKey.fromString("z-end"), PersistentDataType.FLOAT, (float) to.getZ());

        nuclearMeta.getPersistentDataContainer().set(NamespacedKey.fromString("y-start"), PersistentDataType.FLOAT, (float) from.getY());

        nuclearMeta.setLore(List.of(String.format(ChatColor.GOLD + "x: %s; z: %s", to.getX()-0.5, to.getZ()-0.5)));

        nuclearStack.setItemMeta(nuclearMeta);

        return nuclearStack;
    }

    public static ItemStack getNuclearTemplateStack(){
        ItemStack nuclearStack = new ItemStack(Material.FIREWORK_ROCKET);

        ItemMeta nuclearMeta = nuclearStack.getItemMeta();

        nuclearMeta.setDisplayName("Nuclear Rocket");
        nuclearMeta.getPersistentDataContainer().set(Keys.NUCLEAR_ITEM, PersistentDataType.INTEGER, 0);

        nuclearStack.setItemMeta(nuclearMeta);

        return nuclearStack;
    }
}
