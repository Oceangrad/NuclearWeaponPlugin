package org.oceangrad.nuclearweapon.domain.Item_stacks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.oceangrad.nuclearweapon.util.Keys;

public final class SuperGunpowderStack {
    public static ItemStack getSuperGunpowder(){
        ItemStack sGunpowder = new ItemStack(Material.GUNPOWDER);

        ItemMeta sGunpowderMeta = sGunpowder.getItemMeta();

        sGunpowderMeta.setDisplayName("Super Gunpowder");
        sGunpowderMeta.getPersistentDataContainer().set(Keys.SUPER_GUNPOWDER, PersistentDataType.INTEGER, 0);

        sGunpowder.setItemMeta(sGunpowderMeta);

        return sGunpowder;
    }
}
