package org.oceangrad.nuclearweapon.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.oceangrad.nuclearweapon.domain.Item_stacks.FilterStack;
import org.oceangrad.nuclearweapon.domain.Item_stacks.HazmatStack;
import org.oceangrad.nuclearweapon.domain.Item_stacks.NuclearItemStack;
import org.oceangrad.nuclearweapon.domain.Item_stacks.SuperGunpowderStack;

public final class Recipes {
    public static void register(){
        Bukkit.addRecipe(getNuclearCoordsRecipe());
        Bukkit.addRecipe(getSuperGunpowderRecipe());
        Bukkit.addRecipe(getNuclearRecipe());
        Bukkit.addRecipe(getFilterRecipe());
        Bukkit.addRecipe(getHazmatRecipe());
    }

//    public static Recipe getNuclearCoordsRecipe(){
//        ItemStack nuclearTemplate = NuclearItemStack.getNuclearTemplateStack();
//
//        SmithingRecipe nuclearCoordsRecipe = new SmithingRecipe(Keys.NUCLEAR_COORDS_RECIPE, nuclearTemplate, new RecipeChoice.MaterialChoice(Material.FIREWORK_ROCKET), new RecipeChoice.MaterialChoice(Material.PAPER));
//
//        return nuclearCoordsRecipe;
//    }

    public static Recipe getNuclearCoordsRecipe(){
        ItemStack nuclearTemplate = NuclearItemStack.getNuclearTemplateStack();

        ShapelessRecipe nuclearCoordsRecipe = new ShapelessRecipe(Keys.NUCLEAR_COORDS_RECIPE, nuclearTemplate);
        nuclearCoordsRecipe.addIngredient(1, Material.PAPER);
        nuclearCoordsRecipe.addIngredient(1, Material.FIREWORK_ROCKET);

        return nuclearCoordsRecipe;
    }
    public static Recipe getSuperGunpowderRecipe(){
        ShapelessRecipe superGunpowderRecipe = new ShapelessRecipe(Keys.SUPER_GUNPOWDER_RECIPE, SuperGunpowderStack.getSuperGunpowder());
        superGunpowderRecipe.addIngredient(9, Material.GUNPOWDER);

        return superGunpowderRecipe;
    }

    public static Recipe getNuclearRecipe(){
        ShapedRecipe nuclearRecipe = new ShapedRecipe(Keys.NUCLEAR_RECIPE, NuclearItemStack.getNuclearTemplateStack());
        nuclearRecipe.shape(
                "NNN",
                "NSN",
                "NPN"
        );

        nuclearRecipe.setIngredient('N', new RecipeChoice.MaterialChoice(Material.NETHERITE_BLOCK));
        nuclearRecipe.setIngredient('S', new RecipeChoice.ExactChoice(SuperGunpowderStack.getSuperGunpowder()));
        nuclearRecipe.setIngredient('P', new RecipeChoice.ExactChoice(new ItemStack(Material.PAPER)));

        return  nuclearRecipe;
    }

    public static Recipe getHazmatRecipe(){
        ShapedRecipe hazmatRecipe = new ShapedRecipe(Keys.HAZMAT_RECIPE, HazmatStack.getHazmatStack());
        hazmatRecipe.shape(
                "LLL", // l - leather
                "LFL" // f - filter
        );

        hazmatRecipe.setIngredient('L', new RecipeChoice.MaterialChoice(Material.LEATHER));
        hazmatRecipe.setIngredient('F', new RecipeChoice.ExactChoice(FilterStack.getFilterStack()));

        return hazmatRecipe;
    }
    public static Recipe getFilterRecipe(){
        ShapedRecipe filterRecipe = new ShapedRecipe(Keys.FILTER_RECIPE, FilterStack.getFilterStack());
        filterRecipe.shape(
                "PPP", // p - paper
                "PPP"
        );

        filterRecipe.setIngredient('P', new RecipeChoice.MaterialChoice(Material.PAPER));

        return filterRecipe;
    }
}
