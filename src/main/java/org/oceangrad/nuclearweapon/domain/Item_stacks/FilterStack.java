package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

import java.util.List;

public final class FilterStack {
    public static ItemStack getFilterStack(){
        ItemStack filterStack = new ItemStack(Material.PAPER);

        ItemMeta filterStackMeta = filterStack.getItemMeta();

        filterStackMeta.setDisplayName(ChatColor.AQUA + "Filter");
        filterStackMeta.getPersistentDataContainer().set(Keys.FILTER, PersistentDataType.INTEGER, 0);

        CustomModelDataComponent customData = filterStackMeta.getCustomModelDataComponent();
        customData.setFloats(List.of(32425549f));
        filterStackMeta.setCustomModelDataComponent(customData);

        filterStack.setItemMeta(filterStackMeta);

        return filterStack;
    }
}
