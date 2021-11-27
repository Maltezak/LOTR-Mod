package lotr.common.inventory;

import lotr.common.entity.npc.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

public class LOTRContainerHiredWarriorInventory extends Container {
    private LOTREntityNPC theNPC;
    public LOTRInventoryHiredReplacedItems npcInv;
    public IInventory proxyInv;
    private final int npcFullInvSize;
    private int npcActiveSlotCount;

    public LOTRContainerHiredWarriorInventory(InventoryPlayer inv, LOTREntityNPC entity) {
        int i;
        this.theNPC = entity;
        this.npcInv = this.theNPC.hiredReplacedInv;
        this.npcFullInvSize = this.npcInv.getSizeInventory();
        this.proxyInv = new InventoryBasic("npcTemp", false, this.npcFullInvSize);
        for(int i2 = 0; i2 < 4; ++i2) {
            LOTRSlotHiredReplaceItem slot = new LOTRSlotHiredReplaceItem(new LOTRSlotArmorStand(this.proxyInv, i2, 80, 21 + i2 * 18, i2, inv.player), this.theNPC);
            this.addSlotToContainer(slot);
        }
        int[] arrn = new int[1];
        arrn[0] = 4;
        for(int i3 : arrn) {
            LOTRSlotHiredReplaceItem slot = new LOTRSlotHiredReplaceItem(new LOTRSlotMeleeWeapon(this.proxyInv, i3, 50, 48), this.theNPC);
            this.addSlotToContainer(slot);
        }
        if(this.theNPC instanceof LOTREntityOrc && ((LOTREntityOrc) this.theNPC).isOrcBombardier()) {
            int i4 = 5;
            LOTRSlotHiredReplaceItem slot = new LOTRSlotHiredReplaceItem(new LOTRSlotBomb(this.proxyInv, i4, 110, 48), this.theNPC);
            this.addSlotToContainer(slot);
        }
        for(i = 0; i < this.npcFullInvSize; ++i) {
            if(this.getSlotFromInventory(this.proxyInv, i) == null) continue;
            ++this.npcActiveSlotCount;
        }
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 165));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.theNPC != null && this.theNPC.isEntityAlive() && this.theNPC.hiredNPCInfo.isActive && this.theNPC.hiredNPCInfo.getHiringPlayer() == entityplayer && this.theNPC.hiredNPCInfo.getTask() == LOTRHiredNPCInfo.Task.WARRIOR && entityplayer.getDistanceSqToEntity(this.theNPC) <= 144.0;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if(!this.theNPC.worldObj.isRemote) {
            this.theNPC.hiredNPCInfo.sendClientPacket(true);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(i);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(slot.inventory == this.proxyInv) {
                if(!this.mergeItemStack(itemstack1, this.npcActiveSlotCount, this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else {
                for(int j = 0; j < this.npcFullInvSize; ++j) {
                    Slot npcSlot = this.getSlotFromInventory(this.proxyInv, j);
                    if(npcSlot == null) continue;
                    int npcSlotNo = npcSlot.slotNumber;
                    if(!npcSlot.isItemValid(itemstack1) || this.mergeItemStack(itemstack1, npcSlotNo, npcSlotNo + 1, false)) continue;
                    return null;
                }
            }
            if(itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }
            if(itemstack1.stackSize != itemstack.stackSize) {
                slot.onPickupFromSlot(entityplayer, itemstack1);
            }
            else {
                return null;
            }
        }
        return itemstack;
    }
}
