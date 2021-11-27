package lotr.common.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class LOTRRecipesTreasurePile implements IRecipe {
    private Block treasureBlock;
    private Item ingotItem;

    public LOTRRecipesTreasurePile(Block block, Item item) {
        this.treasureBlock = block;
        this.ingotItem = item;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return this.getCraftingResult(inv) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        int ingredientCount = 0;
        int ingredientTotalSize = 0;
        int resultCount = 0;
        int resultMeta = 0;
        for(int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack itemstack = inv.getStackInSlot(i);
            if(itemstack == null) continue;
            if(itemstack.getItem() == Item.getItemFromBlock(this.treasureBlock)) {
                ++ingredientCount;
                int meta = itemstack.getItemDamage();
                ingredientTotalSize += meta + 1;
                continue;
            }
            return null;
        }
        if(ingredientCount > 0) {
            if(ingredientCount == 1) {
                if(ingredientTotalSize > 1) {
                    resultCount = ingredientTotalSize;
                    resultMeta = 0;
                }
            }
            else {
                resultCount = 1;
                resultMeta = ingredientTotalSize - 1;
            }
        }
        if(resultCount <= 0 || resultMeta > 7) {
            return null;
        }
        if(ingredientCount == 1 && ingredientTotalSize == 8) {
            return new ItemStack(this.ingotItem, 4);
        }
        return new ItemStack(this.treasureBlock, resultCount, resultMeta);
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
