package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRInventoryHiredReplacedItems extends LOTRInventoryNPC {
    private boolean[] hasReplacedEquipment = new boolean[7];
    public static final int HELMET = 0;
    public static final int BODY = 1;
    public static final int LEGS = 2;
    public static final int BOOTS = 3;
    public static final int MELEE = 4;
    public static final int BOMB = 5;
    public static final int RANGED = 6;
    private boolean replacedMeleeWeapons = false;

    public LOTRInventoryHiredReplacedItems(LOTREntityNPC npc) {
        super("HiredReplacedItems", npc, 7);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        for(int i = 0; i < this.hasReplacedEquipment.length; ++i) {
            boolean flag = this.hasReplacedEquipment[i];
            nbt.setBoolean("ReplacedFlag_" + i, flag);
        }
        nbt.setBoolean("ReplacedMelee", this.replacedMeleeWeapons);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        for(int i = 0; i < this.hasReplacedEquipment.length; ++i) {
            this.hasReplacedEquipment[i] = nbt.getBoolean("ReplacedFlag_" + i);
        }
        this.replacedMeleeWeapons = nbt.getBoolean("ReplacedMelee");
    }

    private ItemStack getReplacedEquipment(int i) {
        ItemStack item = this.getStackInSlot(i);
        return item == null ? null : item.copy();
    }

    private void setReplacedEquipment(int i, ItemStack item, boolean flag) {
        this.setInventorySlotContents(i, item);
        this.hasReplacedEquipment[i] = flag;
        if(!flag && i == 4) {
            if(this.replacedMeleeWeapons) {
                this.theNPC.npcItemsInv.setIdleItem(this.theNPC.npcItemsInv.getReplacedIdleItem());
                this.theNPC.npcItemsInv.setMeleeWeaponMounted(this.theNPC.npcItemsInv.getReplacedMeleeWeaponMounted());
                this.theNPC.npcItemsInv.setIdleItemMounted(this.theNPC.npcItemsInv.getReplacedIdleItemMounted());
                this.theNPC.npcItemsInv.setReplacedMeleeWeaponMounted(null);
                this.theNPC.npcItemsInv.setReplacedIdleItem(null);
                this.theNPC.npcItemsInv.setReplacedIdleItemMounted(null);
                this.replacedMeleeWeapons = false;
            }
            this.updateHeldItem();
        }
    }

    public boolean hasReplacedEquipment(int i) {
        return this.hasReplacedEquipment[i];
    }

    private void equipReplacement(int i, ItemStack itemstack) {
        if(i == 4) {
            boolean idleMelee = false;
            if(ItemStack.areItemStacksEqual(this.theNPC.npcItemsInv.getMeleeWeapon(), this.theNPC.npcItemsInv.getIdleItem())) {
                idleMelee = true;
            }
            this.theNPC.npcItemsInv.setMeleeWeapon(itemstack);
            if(!this.replacedMeleeWeapons) {
                this.theNPC.npcItemsInv.setReplacedIdleItem(this.theNPC.npcItemsInv.getIdleItem());
                this.theNPC.npcItemsInv.setReplacedMeleeWeaponMounted(this.theNPC.npcItemsInv.getMeleeWeaponMounted());
                this.theNPC.npcItemsInv.setReplacedIdleItemMounted(this.theNPC.npcItemsInv.getIdleItemMounted());
                this.replacedMeleeWeapons = true;
            }
            this.theNPC.npcItemsInv.setMeleeWeaponMounted(itemstack);
            if(idleMelee) {
                this.theNPC.npcItemsInv.setIdleItem(itemstack);
                this.theNPC.npcItemsInv.setIdleItemMounted(itemstack);
            }
            this.updateHeldItem();
        }
        else if(i == 6) {
            this.theNPC.npcItemsInv.setRangedWeapon(itemstack);
            this.updateHeldItem();
        }
        else if(i == 5) {
            this.theNPC.npcItemsInv.setBomb(itemstack);
            this.updateHeldItem();
        }
        else {
            this.theNPC.setCurrentItemOrArmor(this.getNPCArmorSlot(i), itemstack);
        }
    }

    public ItemStack getEquippedReplacement(int i) {
        if(i == 4) {
            return this.theNPC.npcItemsInv.getMeleeWeapon();
        }
        if(i == 6) {
            return this.theNPC.npcItemsInv.getRangedWeapon();
        }
        if(i == 5) {
            return this.theNPC.npcItemsInv.getBomb();
        }
        return this.theNPC.getEquipmentInSlot(this.getNPCArmorSlot(i));
    }

    private int getNPCArmorSlot(int i) {
        return 4 - i;
    }

    public void onEquipmentChanged(int i, ItemStack newItem) {
        if(newItem == null) {
            if(this.hasReplacedEquipment(i)) {
                ItemStack itemstack = this.getReplacedEquipment(i);
                this.equipReplacement(i, itemstack);
                this.setReplacedEquipment(i, null, false);
            }
        }
        else {
            if(!this.hasReplacedEquipment(i)) {
                ItemStack itemstack = this.getEquippedReplacement(i);
                this.setReplacedEquipment(i, itemstack, true);
            }
            this.equipReplacement(i, newItem.copy());
        }
    }

    private void updateHeldItem() {
        if(!this.theNPC.npcItemsInv.getIsEating()) {
            this.theNPC.refreshCurrentAttackMode();
        }
    }

    public void dropAllReplacedItems() {
        for (int i = 0; i < 7; ++i) {
            ItemStack itemstack;
            if (!this.hasReplacedEquipment(i) || (itemstack = this.getEquippedReplacement(i)) == null) continue;
            this.theNPC.npcDropItem(itemstack, 0.0f, false, true);
            this.equipReplacement(i, this.getReplacedEquipment(i));
            this.setReplacedEquipment(i, null, false);
        }
    }
}
