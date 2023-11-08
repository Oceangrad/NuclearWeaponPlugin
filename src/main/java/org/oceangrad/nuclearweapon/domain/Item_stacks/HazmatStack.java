package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

public final class HazmatStack {
    public static ItemStack getHazmatStack(){
        ItemStack hazmatStack = new ItemStack(Material.LEATHER_HELMET);

        Damageable hazmatStackMeta = (Damageable) hazmatStack.getItemMeta();

        hazmatStackMeta.setDisplayName(ChatColor.AQUA + "Hazmat");
        hazmatStackMeta.getPersistentDataContainer().set(Keys.HAZMAT, PersistentDataType.INTEGER, 0);

        hazmatStack.setItemMeta(hazmatStackMeta);

        return hazmatStack;
    }
}
