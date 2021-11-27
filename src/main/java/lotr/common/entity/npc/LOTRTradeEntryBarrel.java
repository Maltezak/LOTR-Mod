package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBarrel;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.item.ItemStack;

public class LOTRTradeEntryBarrel extends LOTRTradeEntry {
    public LOTRTradeEntryBarrel(ItemStack itemstack, int cost) {
        super(itemstack, cost);
    }

    @Override
    public ItemStack createTradeItem() {
        ItemStack drinkItem = super.createTradeItem();
        ItemStack barrelItem = new ItemStack(LOTRMod.barrel);
        LOTRTileEntityBarrel barrel = new LOTRTileEntityBarrel();
        barrel.setInventorySlotContents(9, new ItemStack(drinkItem.getItem(), LOTRBrewingRecipes.BARREL_CAPACITY, drinkItem.getItemDamage()));
        barrel.barrelMode = 2;
        LOTRItemBarrel.setBarrelDataFromTE(barrelItem, barrel);
        return barrelItem;
    }
}
