package lotr.common.recipe;

import java.util.*;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;

public class LOTREntJarRecipes {
    private static Map<ItemStack, ItemStack> recipes = new HashMap<>();

    public static void createDraughtRecipes() {
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 0), new ItemStack(LOTRMod.entDraught, 1, 0));
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 1), new ItemStack(LOTRMod.entDraught, 1, 1));
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 2), new ItemStack(LOTRMod.entDraught, 1, 2));
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 3), new ItemStack(LOTRMod.entDraught, 1, 3));
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 4), new ItemStack(LOTRMod.entDraught, 1, 4));
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornPlant, 1, 5), new ItemStack(LOTRMod.entDraught, 1, 5));
        LOTREntJarRecipes.addDraughtRecipe(new ItemStack(LOTRMod.fangornRiverweed), new ItemStack(LOTRMod.entDraught, 1, 6));
    }

    private static void addDraughtRecipe(ItemStack ingredient, ItemStack result) {
        recipes.put(ingredient, result);
    }

    public static ItemStack findMatchingRecipe(ItemStack input) {
        if(input == null) {
            return null;
        }
        for(ItemStack recipeInput : recipes.keySet()) {
            if(!LOTRRecipes.checkItemEquals(recipeInput, input)) continue;
            return recipes.get(recipeInput).copy();
        }
        return null;
    }
}
