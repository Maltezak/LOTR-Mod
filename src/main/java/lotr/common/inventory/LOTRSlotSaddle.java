package lotr.common.inventory;

import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRSlotSaddle extends Slot {
    public LOTRSlotSaddle(IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return super.isItemValid(itemstack) && itemstack.getItem() == Items.saddle && !this.getHasStack();
    }
}
