package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockKebabStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.*;

public class LOTRTileEntityKebabStand extends TileEntity implements IInventory {
    public static final int MEAT_SLOTS = 8;
    private ItemStack[] inventory = new ItemStack[8];
    private boolean[] cooked = new boolean[8];
    private int cookTime;
    private int fuelTime;
    private boolean cookedClient;
    private boolean cookingClient;
    private int meatAmountClient;
    private float kebabSpin;
    private float prevKebabSpin;

    public String getStandTextureName() {
        Block block = this.getBlockType();
        if(block instanceof LOTRBlockKebabStand) {
            return ((LOTRBlockKebabStand) block).getStandTextureName();
        }
        return "";
    }

    public float getKebabSpin(float f) {
        return this.prevKebabSpin + (this.kebabSpin - this.prevKebabSpin) * f;
    }

    public boolean isCooked() {
        if(this.worldObj != null && this.worldObj.isRemote) {
            return this.cookedClient;
        }
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            if(!this.cooked[i]) continue;
            return true;
        }
        return false;
    }

    private boolean isFullyCooked() {
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack == null || this.cooked[i]) continue;
            return false;
        }
        return true;
    }

    public boolean isCooking() {
        if(this.worldObj != null && this.worldObj.isRemote) {
            return this.cookingClient;
        }
        return this.fuelTime > 0;
    }

    public int getMeatAmount() {
        if(this.worldObj != null && this.worldObj.isRemote) {
            return this.meatAmountClient;
        }
        int meats = 0;
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack == null) continue;
            ++meats;
        }
        return meats;
    }

    public boolean isMeat(ItemStack meat) {
        if(meat == null) {
            return false;
        }
        Item item = meat.getItem();
        if(item instanceof ItemFood && ((ItemFood) item).isWolfsFavoriteMeat()) {
            ItemStack cookedFood = FurnaceRecipes.smelting().getSmeltingResult(meat);
            return cookedFood != null;
        }
        return false;
    }

    public boolean hasEmptyMeatSlot() {
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack != null) continue;
            return true;
        }
        return false;
    }

    public boolean addMeat(ItemStack meat) {
        ItemStack copyMeat = meat.copy();
        copyMeat.stackSize = 1;
        boolean added = false;
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack != null) continue;
            this.setInventorySlotContents(i, copyMeat);
            this.cooked[i] = false;
            added = true;
            break;
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
        return added;
    }

    public ItemStack removeFirstMeat() {
        ItemStack itemstack;
        int i;
        ItemStack meat = null;
        for(i = this.getSizeInventory() - 1; i >= 0; --i) {
            itemstack = this.getStackInSlot(i);
            if(itemstack == null || !this.cooked[i]) continue;
            meat = itemstack;
            this.setInventorySlotContents(i, null);
            this.cooked[i] = false;
            break;
        }
        if(meat == null) {
            for(i = this.getSizeInventory() - 1; i >= 0; --i) {
                itemstack = this.getStackInSlot(i);
                if(itemstack == null || this.cooked[i]) continue;
                meat = itemstack;
                this.setInventorySlotContents(i, null);
                break;
            }
        }
        if(this.isCooking() && this.getMeatAmount() == 0) {
            this.stopCooking();
        }
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
        return meat;
    }

    private boolean canCook() {
        return !this.isFullyCooked() && this.getMeatAmount() > 0;
    }

    private void startCooking(int i) {
        this.cookTime = 0;
        this.fuelTime = i;
    }

    private void addFuel(int i) {
        this.fuelTime += i;
    }

    private void stopCooking() {
        this.cookTime = 0;
        this.fuelTime = 0;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!this.worldObj.isRemote) {
            int fuel;
            boolean prevCooking = this.isCooking();
            boolean prevCooked = this.isCooked();
            if(this.isCooking()) {
                if(!this.canCook()) {
                    this.stopCooking();
                }
                else {
                    ++this.cookTime;
                    if(this.cookTime > this.fuelTime) {
                        int fuel2 = this.takeFuelFromBelow();
                        if(fuel2 > 0) {
                            this.addFuel(fuel2);
                        }
                        else {
                            this.stopCooking();
                        }
                    }
                    else if(this.cookTime >= 200) {
                        this.cookFirstMeat();
                    }
                }
            }
            else if(this.canCook() && (fuel = this.takeFuelFromBelow()) > 0) {
                this.startCooking(fuel);
            }
            if(this.isCooking() != prevCooking || this.isCooked() != prevCooked) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                this.markDirty();
            }
        }
        else if(this.isCooking()) {
            this.prevKebabSpin = this.kebabSpin;
            this.kebabSpin += 4.0f;
            if(this.worldObj.rand.nextInt(4) == 0) {
                double d = this.xCoord + this.worldObj.rand.nextFloat();
                double d1 = this.yCoord + this.worldObj.rand.nextFloat() * 0.2f;
                double d2 = this.zCoord + this.worldObj.rand.nextFloat();
                this.worldObj.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
                this.worldObj.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
            }
        }
        else if(this.kebabSpin > 0.0f) {
            this.prevKebabSpin = this.kebabSpin;
            this.kebabSpin += 20.0f;
            if((float) Math.ceil(this.kebabSpin / 360.0f) > (float) Math.ceil(this.prevKebabSpin / 360.0f)) {
                float ds = this.kebabSpin - this.prevKebabSpin;
                this.kebabSpin = 0.0f;
                this.prevKebabSpin = this.kebabSpin - ds;
            }
        }
        else {
            this.kebabSpin = 0.0f;
            this.prevKebabSpin = 0.0f;
        }
    }

    private void cookFirstMeat() {
        this.cookTime = 0;
        this.fuelTime -= 200;
        for(int i = this.getSizeInventory() - 1; i >= 0; --i) {
            ItemStack itemstack = this.getStackInSlot(i);
            if(itemstack == null || this.cooked[i]) continue;
            this.setInventorySlotContents(i, new ItemStack(LOTRMod.kebab));
            this.cooked[i] = true;
            break;
        }
    }

    private int takeFuelFromBelow() {
        TileEntity belowTE = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if(belowTE instanceof IInventory) {
            IInventory inv = (IInventory) (belowTE);
            for(int i = 0; i < inv.getSizeInventory(); ++i) {
                ItemStack itemstack = inv.getStackInSlot(i);
                if(itemstack == null || !TileEntityFurnace.isItemFuel(itemstack)) continue;
                int fuel = TileEntityFurnace.getItemBurnTime(itemstack);
                --itemstack.stackSize;
                if(itemstack.stackSize <= 0) {
                    inv.setInventorySlotContents(i, null);
                }
                else {
                    inv.setInventorySlotContents(i, itemstack);
                }
                return fuel;
            }
        }
        return 0;
    }

    public void generateCookedKebab(int kebab) {
        for(int i = 0; i < kebab && i < this.getSizeInventory(); ++i) {
            this.setInventorySlotContents(i, new ItemStack(LOTRMod.kebab));
            this.cooked[i] = true;
        }
    }

    public boolean shouldSaveBlockData() {
        return this.getMeatAmount() > 0;
    }

    public void onReplaced() {
        this.stopCooking();
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if(this.inventory[i] != null) {
            if(this.inventory[i].stackSize <= j) {
                ItemStack itemstack = this.inventory[i];
                this.inventory[i] = null;
                return itemstack;
            }
            ItemStack itemstack = this.inventory[i].splitStack(j);
            if(this.inventory[i].stackSize == 0) {
                this.inventory[i] = null;
            }
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if(this.inventory[i] != null) {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.inventory[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "KebabStand";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeKebabStandToNBT(nbt);
    }

    public void writeKebabStandToNBT(NBTTagCompound nbt) {
        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.inventory.length; ++i) {
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte) i);
            ItemStack slotItem = this.inventory[i];
            boolean slotCooked = this.cooked[i];
            itemData.setBoolean("SlotItem", slotItem != null);
            if(slotItem != null) {
                slotItem.writeToNBT(itemData);
            }
            itemData.setBoolean("SlotCooked", slotCooked);
            items.appendTag(itemData);
        }
        nbt.setTag("Items", items);
        nbt.setShort("CookTime", (short) this.cookTime);
        nbt.setShort("FuelTime", (short) this.fuelTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readKebabStandFromNBT(nbt);
    }

    public void readKebabStandFromNBT(NBTTagCompound nbt) {
        NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        this.cooked = new boolean[this.inventory.length];
        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte slot = itemData.getByte("Slot");
            if(slot < 0 || slot >= this.inventory.length) continue;
            boolean slotItem = itemData.getBoolean("SlotItem");
            if(slotItem) {
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
            }
            this.cooked[i] = itemData.getBoolean("SlotCooked");
        }
        this.cookTime = nbt.getShort("CookTime");
        this.fuelTime = nbt.getShort("FuelTime");
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return false;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        data.setBoolean("Cooked", this.isCooked());
        data.setBoolean("Cooking", this.isCooking());
        data.setByte("Meats", (byte) this.getMeatAmount());
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound data = packet.func_148857_g();
        this.cookedClient = data.getBoolean("Cooked");
        this.cookingClient = data.getBoolean("Cooking");
        this.meatAmountClient = data.getByte("Meats");
    }
}
