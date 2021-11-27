package lotr.common.recipe;

import java.util.*;

import cpw.mods.fml.common.registry.GameRegistry;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class LOTRMillstoneRecipes {
    private static Map<ItemStack, MillstoneResult> recipeList = new HashMap<>();

    public static void createRecipes() {
        LOTRMillstoneRecipes.addRecipe(Blocks.stone, new ItemStack(Blocks.cobblestone));
        LOTRMillstoneRecipes.addRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel), 0.75f);
        LOTRMillstoneRecipes.addRecipe(new ItemStack(LOTRMod.rock, 1, 0), new ItemStack(LOTRMod.mordorGravel), 0.75f);
        LOTRMillstoneRecipes.addRecipe(Blocks.gravel, new ItemStack(Items.flint), 0.25f);
        LOTRMillstoneRecipes.addRecipe(LOTRMod.mordorGravel, new ItemStack(Items.flint), 0.25f);
        LOTRMillstoneRecipes.addRecipe(LOTRMod.obsidianGravel, new ItemStack(LOTRMod.obsidianShard), 1.0f);
        LOTRMillstoneRecipes.addRecipe(LOTRMod.oreSalt, new ItemStack(LOTRMod.salt));
        LOTRMillstoneRecipes.addRecipe(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(Blocks.sand, 2, 0));
        LOTRMillstoneRecipes.addRecipe(new ItemStack(LOTRMod.redSandstone, 1, 0), new ItemStack(Blocks.sand, 2, 1));
        LOTRMillstoneRecipes.addRecipe(new ItemStack(LOTRMod.whiteSandstone, 1, 0), new ItemStack(LOTRMod.whiteSand, 2, 0));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(Blocks.brick_block, 1, 0), new ItemStack(LOTRMod.redBrick, 1, 1));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(Blocks.stonebrick, 1, 0), new ItemStack(Blocks.stonebrick, 1, 2));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 0), new ItemStack(LOTRMod.brick, 1, 7));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 1), new ItemStack(LOTRMod.brick, 1, 3));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 6), new ItemStack(LOTRMod.brick4, 1, 5));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 11), new ItemStack(LOTRMod.brick, 1, 13));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick, 1, 15), new ItemStack(LOTRMod.brick3, 1, 11));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 0), new ItemStack(LOTRMod.brick2, 1, 1));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 3), new ItemStack(LOTRMod.brick2, 1, 5));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick2, 1, 8), new ItemStack(LOTRMod.brick2, 1, 9));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 2), new ItemStack(LOTRMod.brick3, 1, 4));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 5), new ItemStack(LOTRMod.brick3, 1, 7));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 10), new ItemStack(LOTRMod.brick6, 1, 13));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick3, 1, 13), new ItemStack(LOTRMod.brick3, 1, 14));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick4, 1, 0), new ItemStack(LOTRMod.brick4, 1, 2));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 1), new ItemStack(LOTRMod.brick6, 1, 4));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 2), new ItemStack(LOTRMod.brick5, 1, 5));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 8), new ItemStack(LOTRMod.brick5, 1, 10));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick5, 1, 11), new ItemStack(LOTRMod.brick5, 1, 14));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.brick6, 1, 6), new ItemStack(LOTRMod.brick6, 1, 7));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 0), new ItemStack(LOTRMod.pillar2, 1, 0));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 1), new ItemStack(LOTRMod.pillar, 1, 2));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 10), new ItemStack(LOTRMod.pillar, 1, 11));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.pillar, 1, 12), new ItemStack(LOTRMod.pillar, 1, 13));
        LOTRMillstoneRecipes.addCrackedBricks(new ItemStack(LOTRMod.pillar2, 1, 13), new ItemStack(LOTRMod.pillar2, 1, 14));
    }

    public static void addRecipe(Block block, ItemStack result) {
        LOTRMillstoneRecipes.addRecipe(block, result, 1.0f);
    }

    public static void addRecipe(Block block, ItemStack result, float chance) {
        LOTRMillstoneRecipes.addRecipe(Item.getItemFromBlock(block), result, chance);
    }

    public static void addRecipe(Item item, ItemStack result) {
        LOTRMillstoneRecipes.addRecipe(new ItemStack(item, 1, 32767), result, 1.0f);
    }

    public static void addRecipe(Item item, ItemStack result, float chance) {
        LOTRMillstoneRecipes.addRecipe(new ItemStack(item, 1, 32767), result, chance);
    }

    public static void addRecipe(ItemStack itemstack, ItemStack result) {
        LOTRMillstoneRecipes.addRecipe(itemstack, result, 1.0f);
    }

    public static void addRecipe(ItemStack itemstack, ItemStack result, float chance) {
        recipeList.put(itemstack, new MillstoneResult(result, chance));
    }

    public static void addCrackedBricks(ItemStack itemstack, ItemStack result) {
        LOTRMillstoneRecipes.addRecipe(itemstack, result, 1.0f);
        GameRegistry.addSmelting(itemstack, result, 0.1f);
    }

    public static MillstoneResult getMillingResult(ItemStack itemstack) {
        for (Map.Entry<ItemStack, MillstoneResult> e : recipeList.entrySet()) {
            ItemStack target = e.getKey();
            MillstoneResult result = e.getValue();
            if (!LOTRMillstoneRecipes.matches(itemstack, target)) continue;
            return result;
        }
        return null;
    }

    private static boolean matches(ItemStack itemstack, ItemStack target) {
        return target.getItem() == itemstack.getItem() && (target.getItemDamage() == 32767 || target.getItemDamage() == itemstack.getItemDamage());
    }

    public static class MillstoneResult {
        public final ItemStack resultItem;
        public final float chance;

        public MillstoneResult(ItemStack itemstack, float f) {
            this.resultItem = itemstack;
            this.chance = f;
        }
    }

}

