package lotr.common.entity.npc;

import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRInventoryNPCItems extends LOTRInventoryNPC {
    private static int IDLE_ITEM = 0;
    private static int WEAPON_MELEE = 1;
    private static int WEAPON_RANGED = 2;
    private static int SPEAR_BACKUP = 3;
    private static int EATING_BACKUP = 4;
    private static int IDLE_ITEM_MOUNTED = 5;
    private static int WEAPON_MELEE_MOUNTED = 6;
    private static int REPLACED_IDLE = 7;
    private static int REPLACED_MELEE_MOUNTED = 8;
    private static int REPLACED_IDLE_MOUNTED = 9;
    private static int BOMBING_ITEM = 10;
    private static int BOMB = 11;
    private boolean isEating = false;

    public LOTRInventoryNPCItems(LOTREntityNPC npc) {
        super("NPCItemsInv", npc, 12);
    }

    public void setIsEating(boolean flag) {
        this.isEating = flag;
        this.theNPC.sendIsEatingToWatchers();
    }

    public boolean getIsEating() {
        return this.isEating;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("NPCEating", this.isEating);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isEating = nbt.getBoolean("NPCEating");
        if(this.isEating) {
            this.theNPC.setCurrentItemOrArmor(0, this.getEatingBackup());
            this.setEatingBackup(null);
            this.setIsEating(false);
        }
    }

    public ItemStack getIdleItem() {
        ItemStack item = this.getStackInSlot(IDLE_ITEM);
        return item == null ? null : item.copy();
    }

    public void setIdleItem(ItemStack item) {
        this.setInventorySlotContents(IDLE_ITEM, item);
    }

    public ItemStack getMeleeWeapon() {
        ItemStack item = this.getStackInSlot(WEAPON_MELEE);
        return item == null ? null : item.copy();
    }

    public void setMeleeWeapon(ItemStack item) {
        this.setInventorySlotContents(WEAPON_MELEE, item);
    }

    public ItemStack getRangedWeapon() {
        ItemStack item = this.getStackInSlot(WEAPON_RANGED);
        return item == null ? null : item.copy();
    }

    public void setRangedWeapon(ItemStack item) {
        this.setInventorySlotContents(WEAPON_RANGED, item);
    }

    public ItemStack getSpearBackup() {
        ItemStack item = this.getStackInSlot(SPEAR_BACKUP);
        return item == null ? null : item.copy();
    }

    public void setSpearBackup(ItemStack item) {
        this.setInventorySlotContents(SPEAR_BACKUP, item);
    }

    public ItemStack getEatingBackup() {
        ItemStack item = this.getStackInSlot(EATING_BACKUP);
        return item == null ? null : item.copy();
    }

    public void setEatingBackup(ItemStack item) {
        this.setInventorySlotContents(EATING_BACKUP, item);
    }

    public ItemStack getIdleItemMounted() {
        ItemStack item = this.getStackInSlot(IDLE_ITEM_MOUNTED);
        return item == null ? null : item.copy();
    }

    public void setIdleItemMounted(ItemStack item) {
        this.setInventorySlotContents(IDLE_ITEM_MOUNTED, item);
    }

    public ItemStack getMeleeWeaponMounted() {
        ItemStack item = this.getStackInSlot(WEAPON_MELEE_MOUNTED);
        return item == null ? null : item.copy();
    }

    public void setMeleeWeaponMounted(ItemStack item) {
        this.setInventorySlotContents(WEAPON_MELEE_MOUNTED, item);
    }

    public ItemStack getReplacedIdleItem() {
        ItemStack item = this.getStackInSlot(REPLACED_IDLE);
        return item == null ? null : item.copy();
    }

    public void setReplacedIdleItem(ItemStack item) {
        this.setInventorySlotContents(REPLACED_IDLE, item);
    }

    public ItemStack getReplacedMeleeWeaponMounted() {
        ItemStack item = this.getStackInSlot(REPLACED_MELEE_MOUNTED);
        return item == null ? null : item.copy();
    }

    public void setReplacedMeleeWeaponMounted(ItemStack item) {
        this.setInventorySlotContents(REPLACED_MELEE_MOUNTED, item);
    }

    public ItemStack getReplacedIdleItemMounted() {
        ItemStack item = this.getStackInSlot(REPLACED_IDLE_MOUNTED);
        return item == null ? null : item.copy();
    }

    public void setReplacedIdleItemMounted(ItemStack item) {
        this.setInventorySlotContents(REPLACED_IDLE_MOUNTED, item);
    }

    public ItemStack getBombingItem() {
        ItemStack item = this.getStackInSlot(BOMBING_ITEM);
        return item == null ? null : item.copy();
    }

    public void setBombingItem(ItemStack item) {
        this.setInventorySlotContents(BOMBING_ITEM, item);
    }

    public ItemStack getBomb() {
        ItemStack item = this.getStackInSlot(BOMB);
        return item == null ? null : item.copy();
    }

    public void setBomb(ItemStack item) {
        this.setInventorySlotContents(BOMB, item);
    }
}
