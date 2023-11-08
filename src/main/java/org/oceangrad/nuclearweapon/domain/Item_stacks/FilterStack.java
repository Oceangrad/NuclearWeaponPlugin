package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

public final class FilterStack {
    public static ItemStack getFilterStack(){
        ItemStack filterStack = new ItemStack(Material.PAPER);

        ItemMeta filterStackMeta = filterStack.getItemMeta();

        filterStackMeta.setDisplayName(ChatColor.AQUA + "Filter");
        filterStackMeta.getPersistentDataContainer().set(Keys.FILTER, PersistentDataType.INTEGER, 0);

        filterStack.setItemMeta(filterStackMeta);

        return filterStack;
    }
}
