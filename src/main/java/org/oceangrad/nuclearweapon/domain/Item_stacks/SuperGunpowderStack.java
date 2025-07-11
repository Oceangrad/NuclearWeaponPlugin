package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

import java.util.List;

public final class SuperGunpowderStack {
    public static ItemStack getSuperGunpowder(){
        ItemStack sGunpowder = new ItemStack(Material.GUNPOWDER);

        ItemMeta sGunpowderMeta = sGunpowder.getItemMeta();

        sGunpowderMeta.setDisplayName("Super Gunpowder");

        CustomModelDataComponent customData = sGunpowderMeta.getCustomModelDataComponent();
        customData.setFloats(List.of(235345345f));
        sGunpowderMeta.setCustomModelDataComponent(customData);

        sGunpowderMeta.getPersistentDataContainer().set(Keys.SUPER_GUNPOWDER, PersistentDataType.INTEGER, 0);

        sGunpowder.setItemMeta(sGunpowderMeta);

        return sGunpowder;
    }
}
