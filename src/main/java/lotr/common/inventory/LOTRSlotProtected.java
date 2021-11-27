package lotr.common.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRSlotProtected extends Slot {
    public LOTRSlotProtected(IInventory inv, int i, int j, int k) {
        super(inv, i, j, k);
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return false;
    }
}
