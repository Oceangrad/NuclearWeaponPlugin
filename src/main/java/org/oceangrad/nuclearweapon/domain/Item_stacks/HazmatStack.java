package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

import java.util.List;

public final class HazmatStack {
    public static ItemStack getHazmatStack(){
        ItemStack hazmatStack = new ItemStack(Material.LEATHER_HELMET);

        Damageable hazmatStackMeta = (Damageable) hazmatStack.getItemMeta();

        hazmatStackMeta.setDisplayName(ChatColor.AQUA + "Hazmat");
        hazmatStackMeta.getPersistentDataContainer().set(Keys.HAZMAT, PersistentDataType.INTEGER, 0);

        CustomModelDataComponent customData = hazmatStackMeta.getCustomModelDataComponent();
        customData.setFloats(List.of(234346866f));
        hazmatStackMeta.setCustomModelDataComponent(customData);

        hazmatStack.setItemMeta(hazmatStackMeta);

        return hazmatStack;
    }
}
