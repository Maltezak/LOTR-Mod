package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.item.LOTRItemMug;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotBarrel extends Slot {
    private LOTRTileEntityBarrel theBarrel;
    private boolean isWater;

    public LOTRSlotBarrel(LOTRTileEntityBarrel inv, int i, int j, int k) {
        super(inv, i, j, k);
        this.theBarrel = inv;
    }

    public LOTRSlotBarrel setWaterSource() {
        this.isWater = true;
        return this;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        if(this.theBarrel.barrelMode == 0) {
            if(this.isWater) {
                return LOTRBrewingRecipes.isWaterSource(itemstack);
            }
            return true;
        }
        return false;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getBackgroundIconIndex() {
        IIcon iIcon;
        if(this.getSlotIndex() > 5) {
            iIcon = LOTRItemMug.barrelGui_emptyBucketSlotIcon;
        }
        else {
            iIcon = null;
        }
        return iIcon;
    }
}
