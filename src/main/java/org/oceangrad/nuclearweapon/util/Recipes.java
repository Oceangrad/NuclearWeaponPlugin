package org.oceangrad.nuclearweapon.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.oceangrad.nuclearweapon.domain.Item_stacks.*;

public final class Recipes {
    public static void register(){
        Bukkit.addRecipe(getNuclearCoordsRecipe());
        Bukkit.addRecipe(getSuperGunpowderRecipe());
        Bukkit.addRecipe(getNuclearRecipe());
        Bukkit.addRecipe(getFilterRecipe());
        Bukkit.addRecipe(getHazmatRecipe());
        Bukkit.addRecipe(getNuclearCoreRecipe());
    }

    public static Recipe getNuclearCoordsRecipe(){
        ItemStack nuclearTemplate = NuclearItemStack.getNuclearTemplateStack();

        ShapelessRecipe nuclearCoordsRecipe = new ShapelessRecipe(Keys.NUCLEAR_COORDS_RECIPE, nuclearTemplate);
        nuclearCoordsRecipe.addIngredient(1, Material.PAPER);
        nuclearCoordsRecipe.addIngredient(1, Material.FIREWORK_ROCKET);

        return nuclearCoordsRecipe;
    }
    public static Recipe getSuperGunpowderRecipe(){
        ShapelessRecipe superGunpowderRecipe = new ShapelessRecipe(Keys.SUPER_GUNPOWDER_RECIPE, SuperGunpowderStack.getSuperGunpowder());
        superGunpowderRecipe.addIngredient(9, SuperGunpowderStack.getSuperGunpowder().getData());

        return superGunpowderRecipe;
    }

    public static Recipe getNuclearCoreRecipe(){
        ShapedRecipe nuclearCoreRecipe = new ShapedRecipe(Keys.NUCLEAR_CORE_RECIPE, NuclearCoreStack.getNuclearCore());
        nuclearCoreRecipe.shape(
                "SSS",
                "SCS",
                "SSS"
        );

        nuclearCoreRecipe.setIngredient('S', new RecipeChoice.ExactChoice(SuperGunpowderStack.getSuperGunpowder()));
        nuclearCoreRecipe.setIngredient('C', new RecipeChoice.ExactChoice(new ItemStack(Material.END_CRYSTAL)));

        return nuclearCoreRecipe;
    }

    public static Recipe getNuclearRecipe(){
        ShapedRecipe nuclearRecipe = new ShapedRecipe(Keys.NUCLEAR_RECIPE, NuclearItemStack.getNuclearTemplateStack());
        nuclearRecipe.shape(
                "N",
                "C",
                "P"
        );

        nuclearRecipe.setIngredient('N', new RecipeChoice.MaterialChoice(Material.NETHERITE_INGOT));
        nuclearRecipe.setIngredient('C', new RecipeChoice.ExactChoice(NuclearCoreStack.getNuclearCore()));
        nuclearRecipe.setIngredient('P', new RecipeChoice.ExactChoice(new ItemStack(Material.PAPER)));

        return  nuclearRecipe;
    }

    public static Recipe getHazmatRecipe(){
        ShapedRecipe hazmatRecipe = new ShapedRecipe(Keys.HAZMAT_RECIPE, HazmatStack.getHazmatStack());
        hazmatRecipe.shape(
                "LLL",
                "LFL"
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
