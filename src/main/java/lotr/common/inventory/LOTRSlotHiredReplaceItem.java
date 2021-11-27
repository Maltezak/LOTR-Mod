package lotr.common.inventory;

import cpw.mods.fml.relauncher.*;
import lotr.common.entity.npc.*;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class LOTRSlotHiredReplaceItem extends Slot {
    private LOTREntityNPC theNPC;
    private LOTRInventoryHiredReplacedItems npcInv;
    private Slot parentSlot;

    public LOTRSlotHiredReplaceItem(Slot slot, LOTREntityNPC npc) {
        super(slot.inventory, slot.getSlotIndex(), slot.xDisplayPosition, slot.yDisplayPosition);
        int i;
        this.parentSlot = slot;
        this.theNPC = npc;
        this.npcInv = this.theNPC.hiredReplacedInv;
        if(!this.theNPC.worldObj.isRemote && this.npcInv.hasReplacedEquipment(i = this.getSlotIndex())) {
            this.inventory.setInventorySlotContents(i, this.npcInv.getEquippedReplacement(i));
        }
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return this.parentSlot.isItemValid(itemstack) && this.theNPC.canReEquipHired(this.getSlotIndex(), itemstack);
    }

    @Override
    public int getSlotStackLimit() {
        return this.parentSlot.getSlotStackLimit();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getBackgroundIconIndex() {
        return this.parentSlot.getBackgroundIconIndex();
    }

    @Override
    public void onSlotChanged() {
        super.onSlotChanged();
        if(!this.theNPC.worldObj.isRemote) {
            this.npcInv.onEquipmentChanged(this.getSlotIndex(), this.getStack());
        }
    }
}
