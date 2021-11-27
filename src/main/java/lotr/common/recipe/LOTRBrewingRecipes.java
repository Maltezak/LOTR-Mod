package lotr.common.recipe;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class LOTRBrewingRecipes {
    private static ArrayList<ShapelessOreRecipe> recipes = new ArrayList();
    public static int BARREL_CAPACITY = 16;

    public static void createBrewingRecipes() {
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugAle, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugMiruvor, BARREL_CAPACITY), LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.mallornNut, LOTRMod.elanor, LOTRMod.niphredil, Items.sugar);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugOrcDraught, BARREL_CAPACITY), LOTRMod.morgulShroom, LOTRMod.morgulShroom, LOTRMod.morgulShroom, "bone", "bone", "bone");
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugMead, BARREL_CAPACITY), Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar, Items.sugar);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugCider, BARREL_CAPACITY), "apple", "apple", "apple", "apple", "apple", "apple");
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugPerry, BARREL_CAPACITY), LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear, LOTRMod.pear);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugCherryLiqueur, BARREL_CAPACITY), LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry, LOTRMod.cherry);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugRum, BARREL_CAPACITY), Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds, Items.reeds);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugAthelasBrew, BARREL_CAPACITY), LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas, LOTRMod.athelas);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenTonic, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb, LOTRMod.mithrilNugget);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugDwarvenAle, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, LOTRMod.dwarfHerb, LOTRMod.dwarfHerb);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugVodka, BARREL_CAPACITY), Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugMapleBeer, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, Items.wheat, LOTRMod.mapleSyrup, LOTRMod.mapleSyrup);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugAraq, BARREL_CAPACITY), LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date, LOTRMod.date);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugCarrotWine, BARREL_CAPACITY), Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugBananaBeer, BARREL_CAPACITY), LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana, LOTRMod.banana);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugMelonLiqueur, BARREL_CAPACITY), Items.melon, Items.melon, Items.melon, Items.melon, Items.melon, Items.melon);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugCactusLiqueur, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugTorogDraught, BARREL_CAPACITY), Items.reeds, Items.reeds, Items.rotten_flesh, Items.rotten_flesh, Blocks.dirt, LOTRMod.rhinoHorn);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugLemonLiqueur, BARREL_CAPACITY), LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon, LOTRMod.lemon);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugLimeLiqueur, BARREL_CAPACITY), LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime, LOTRMod.lime);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugCornLiquor, BARREL_CAPACITY), LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn, LOTRMod.corn);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugRedWine, BARREL_CAPACITY), LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed, LOTRMod.grapeRed);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugWhiteWine, BARREL_CAPACITY), LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite, LOTRMod.grapeWhite);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugMorgulDraught, BARREL_CAPACITY), LOTRMod.morgulFlower, LOTRMod.morgulFlower, LOTRMod.morgulFlower, "bone", "bone", "bone");
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugPlumKvass, BARREL_CAPACITY), Items.wheat, Items.wheat, Items.wheat, LOTRMod.plum, LOTRMod.plum, LOTRMod.plum);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugTermiteTequila, BARREL_CAPACITY), Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, Blocks.cactus, LOTRMod.termite);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugSourMilk, BARREL_CAPACITY), Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket, Items.milk_bucket);
        LOTRBrewingRecipes.addBrewingRecipe(new ItemStack(LOTRMod.mugPomegranateWine, BARREL_CAPACITY), LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate, LOTRMod.pomegranate);
    }

    private static void addBrewingRecipe(ItemStack result, Object... ingredients) {
        if(ingredients.length != 6) {
            throw new IllegalArgumentException("Brewing recipes must contain exactly 6 items");
        }
        recipes.add(new ShapelessOreRecipe(result, ingredients));
    }

    public static boolean isWaterSource(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.water_bucket;
    }

    public static ItemStack findMatchingRecipe(LOTRTileEntityBarrel barrel) {
        for(int i = 6; i < 9; ++i) {
            ItemStack itemstack = barrel.getStackInSlot(i);
            if(LOTRBrewingRecipes.isWaterSource(itemstack)) continue;
            return null;
        }
        block1: for(ShapelessOreRecipe recipe : recipes) {
            ArrayList<Object> ingredients = new ArrayList<>(recipe.getInput());
            for(int i = 0; i < 6; ++i) {
                ItemStack itemstack = barrel.getStackInSlot(i);
                if(itemstack == null) continue;
                boolean inRecipe = false;
                Iterator<Object> it = ingredients.iterator();
                while(it.hasNext()) {
                    boolean match = false;
                    Object next = it.next();
                    if(next instanceof ItemStack) {
                        match = LOTRRecipes.checkItemEquals((ItemStack) next, itemstack);
                    }
                    else if(next instanceof ArrayList) {
                        for(ItemStack item : ((ArrayList<ItemStack>) next)) {
                            match = match || LOTRRecipes.checkItemEquals(item, itemstack);
                        }
                    }
                    if(!match) continue;
                    inRecipe = true;
                    ingredients.remove(next);
                    break;
                }
                if(!inRecipe) continue block1;
            }
            if(!ingredients.isEmpty()) continue;
            return recipe.getRecipeOutput().copy();
        }
        return null;
    }
}
