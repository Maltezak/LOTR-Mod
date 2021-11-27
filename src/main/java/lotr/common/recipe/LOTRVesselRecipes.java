package lotr.common.recipe;

import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRVesselRecipes {
    public static void addRecipes(ItemStack result, Object[] ingredients) {
        LOTRVesselRecipes.addRecipes(result, null, ingredients);
    }

    public static void addRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
        List<IRecipe> recipes = LOTRVesselRecipes.generateRecipes(result, drinkBase, ingredients);
        for(IRecipe r : recipes) {
            GameRegistry.addRecipe(r);
        }
    }

    public static List<IRecipe> generateRecipes(ItemStack result, Object[] ingredients) {
        return LOTRVesselRecipes.generateRecipes(result, null, ingredients);
    }

    public static List<IRecipe> generateRecipes(ItemStack result, Item drinkBase, Object[] ingredients) {
        ArrayList<IRecipe> recipes = new ArrayList<>();
        for(LOTRItemMug.Vessel v : LOTRItemMug.Vessel.values()) {
            ArrayList<Object> vIngredients = new ArrayList<>();
            ItemStack vBase = v.getEmptyVessel();
            if(drinkBase != null) {
                vBase = new ItemStack(drinkBase);
                LOTRItemMug.setVessel(vBase, v, true);
            }
            vIngredients.add(vBase);
            vIngredients.addAll(Arrays.asList(ingredients));
            ItemStack vResult = result.copy();
            LOTRItemMug.setVessel(vResult, v, true);
            ShapelessOreRecipe recipe = new ShapelessOreRecipe(vResult, vIngredients.toArray());
            recipes.add(recipe);
        }
        return recipes;
    }
}
