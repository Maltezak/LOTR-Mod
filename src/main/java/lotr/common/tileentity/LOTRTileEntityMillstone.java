package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockMillstone;
import lotr.common.recipe.LOTRMillstoneRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityMillstone extends TileEntity implements IInventory, ISidedInventory {
    protected ItemStack[] inventory = new ItemStack[2];
    private String specialMillstoneName;
    public boolean isMilling;
    public int currentMillTime = 0;
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
        return this.hasCustomInventoryName() ? this.specialMillstoneName : StatCollector.translateToLocal("container.lotr.millstone");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.specialMillstoneName != null && this.specialMillstoneName.length() > 0;
    }

    public void setSpecialMillstoneName(String s) {
        this.specialMillstoneName = s;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte slot = itemData.getByte("Slot");
            if(slot < 0 || slot >= this.inventory.length) continue;
            this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
        }
        this.isMilling = nbt.getBoolean("Milling");
        this.currentMillTime = nbt.getInteger("MillTime");
        if(nbt.hasKey("CustomName")) {
            this.specialMillstoneName = nbt.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.inventory.length; ++i) {
            if(this.inventory[i] == null) continue;
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte) i);
            this.inventory[i].writeToNBT(itemData);
            items.appendTag(itemData);
        }
        nbt.setTag("Items", items);
        nbt.setBoolean("Milling", this.isMilling);
        nbt.setInteger("MillTime", this.currentMillTime);
        if(this.hasCustomInventoryName()) {
            nbt.setString("CustomName", this.specialMillstoneName);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(value = Side.CLIENT)
    public int getMillProgressScaled(int i) {
        return this.currentMillTime * i / 200;
    }

    public boolean isMilling() {
        return this.isMilling;
    }

    protected void toggleMillstoneActive() {
        LOTRBlockMillstone.toggleMillstoneActive(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public void updateEntity() {
        boolean needUpdate = false;
        if(!this.worldObj.isRemote) {
            boolean powered = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
            if(powered && !this.isMilling) {
                this.isMilling = true;
                this.currentMillTime = 0;
                needUpdate = true;
                this.toggleMillstoneActive();
            }
            else if(!powered && this.isMilling) {
                this.isMilling = false;
                this.currentMillTime = 0;
                needUpdate = true;
                this.toggleMillstoneActive();
            }
            if(this.isMilling && this.canMill()) {
                ++this.currentMillTime;
                if(this.currentMillTime == 200) {
                    this.currentMillTime = 0;
                    this.millItem();
                    needUpdate = true;
                }
            }
            else if(this.currentMillTime != 0) {
                this.currentMillTime = 0;
                needUpdate = true;
            }
        }
        if(needUpdate) {
            this.markDirty();
        }
    }

    private boolean canMill() {
        ItemStack itemstack = this.inventory[0];
        if(itemstack == null) {
            return false;
        }
        LOTRMillstoneRecipes.MillstoneResult result = LOTRMillstoneRecipes.getMillingResult(itemstack);
        if(result == null) {
            return false;
        }
        ItemStack resultItem = result.resultItem;
        ItemStack inResultSlot = this.inventory[1];
        if(inResultSlot == null) {
            return true;
        }
        if(!inResultSlot.isItemEqual(resultItem)) {
            return false;
        }
        int resultSize = inResultSlot.stackSize + resultItem.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= resultItem.getMaxStackSize();
    }

    private void millItem() {
        if(this.canMill()) {
            ItemStack itemstack = this.inventory[0];
            LOTRMillstoneRecipes.MillstoneResult result = LOTRMillstoneRecipes.getMillingResult(itemstack);
            ItemStack resultItem = result.resultItem;
            float chance = result.chance;
            if(this.worldObj.rand.nextFloat() < chance) {
                ItemStack inResultSlot = this.inventory[1];
                if(inResultSlot == null) {
                    inResultSlot = resultItem.copy();
                }
                else if(inResultSlot.isItemEqual(resultItem)) {
                    inResultSlot.stackSize += resultItem.stackSize;
                }
                this.inventory[1] = inResultSlot;
            }
            --itemstack.stackSize;
            if(itemstack.stackSize <= 0) {
                this.inventory[0] = null;
            }
        }
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
        if(slot == 0) {
            return itemstack != null && LOTRMillstoneRecipes.getMillingResult(itemstack) != null;
        }
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if(side == 0) {
            return new int[] {1};
        }
        if(side == 1) {
            return new int[] {0};
        }
        return new int[] {0};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
        return this.isItemValidForSlot(slot, itemstack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
        if(side == 0) {
            return true;
        }
        return true;
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
        if(packet.func_148857_g() != null && packet.func_148857_g().hasKey("CustomName")) {
            this.specialMillstoneName = packet.func_148857_g().getString("CustomName");
        }
    }
}
