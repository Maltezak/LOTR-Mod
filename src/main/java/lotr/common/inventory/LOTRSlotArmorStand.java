package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRSlotArmorStand
extends Slot {
    private int armorSlot;
    private Entity armorTestEntity;

    public LOTRSlotArmorStand(IInventory inv, int i, int j, int k, int a, Entity entity) {
        super(inv, i, j, k);
        this.armorSlot = a;
        this.armorTestEntity = entity;
    }

    public int getSlotStackLimit() {
        return 1;
    }

    public boolean isItemValid(ItemStack itemstack) {
        if (itemstack != null) {
            Item item = itemstack.getItem();
            return item.isValidArmor(itemstack, this.armorSlot, this.armorTestEntity);
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getBackgroundIconIndex() {
        return ItemArmor.func_94602_b(this.armorSlot);
    }
}

