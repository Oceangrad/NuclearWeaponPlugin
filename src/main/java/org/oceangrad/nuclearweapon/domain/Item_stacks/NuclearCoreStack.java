package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

import java.util.List;

public final class NuclearCoreStack {
    public static ItemStack getNuclearCore(){
        ItemStack nuclearCore = new ItemStack(Material.GUNPOWDER);

        ItemMeta nuclearCoreMeta = nuclearCore.getItemMeta();

        nuclearCoreMeta.setDisplayName("Nuclear Core");

        CustomModelDataComponent customData = nuclearCoreMeta.getCustomModelDataComponent();
        customData.setFloats(List.of(3434886f));
        nuclearCoreMeta.setCustomModelDataComponent(customData);

        nuclearCoreMeta.getPersistentDataContainer().set(Keys.NUCLEAR_CORE, PersistentDataType.INTEGER, 0);

        nuclearCore.setItemMeta(nuclearCoreMeta);

        return nuclearCore;
    }
}
