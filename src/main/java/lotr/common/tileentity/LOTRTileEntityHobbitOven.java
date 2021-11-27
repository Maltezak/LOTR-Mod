package lotr.common.tileentity;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockHobbitOven;
import lotr.common.inventory.LOTRSlotStackSize;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.*;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.*;
import net.minecraft.util.StatCollector;

public class LOTRTileEntityHobbitOven extends TileEntity implements IInventory, ISidedInventory {
    private ItemStack[] inventory = new ItemStack[19];
    public int ovenCookTime = 0;
    public int currentItemFuelValue = 0;
    public int currentCookTime = 0;
    private String specialOvenName;
    private int[] inputSlots = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private int[] outputSlots = new int[] {9, 10, 11, 12, 13, 14, 15, 16, 17};
    private int fuelSlot = 18;

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
        return this.hasCustomInventoryName() ? this.specialOvenName : StatCollector.translateToLocal("container.lotr.hobbitOven");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.specialOvenName != null && this.specialOvenName.length() > 0;
    }

    public void setOvenName(String s) {
        this.specialOvenName = s;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte byte0 = itemData.getByte("Slot");
            if(byte0 < 0 || byte0 >= this.inventory.length) continue;
            this.inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
        }
        this.ovenCookTime = nbt.getShort("BurnTime");
        this.currentCookTime = nbt.getShort("CookTime");
        this.currentItemFuelValue = TileEntityFurnace.getItemBurnTime(this.inventory[18]);
        if(nbt.hasKey("CustomName")) {
            this.specialOvenName = nbt.getString("CustomName");
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
        nbt.setShort("BurnTime", (short) this.ovenCookTime);
        nbt.setShort("CookTime", (short) this.currentCookTime);
        if(this.hasCustomInventoryName()) {
            nbt.setString("CustomName", this.specialOvenName);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(value = Side.CLIENT)
    public int getCookProgressScaled(int i) {
        return this.currentCookTime * i / 400;
    }

    @SideOnly(value = Side.CLIENT)
    public int getCookTimeRemainingScaled(int i) {
        if(this.currentItemFuelValue == 0) {
            this.currentItemFuelValue = 400;
        }
        return this.ovenCookTime * i / this.currentItemFuelValue;
    }

    public boolean isCooking() {
        return this.ovenCookTime > 0;
    }

    @Override
    public void updateEntity() {
        boolean cooking = this.ovenCookTime > 0;
        boolean needUpdate = false;
        if(this.ovenCookTime > 0) {
            --this.ovenCookTime;
        }
        if(!this.worldObj.isRemote) {
            if(this.ovenCookTime == 0 && this.canCookAnyItem()) {
                this.currentItemFuelValue = this.ovenCookTime = TileEntityFurnace.getItemBurnTime(this.inventory[18]);
                if(this.ovenCookTime > 0) {
                    needUpdate = true;
                    if(this.inventory[18] != null) {
                        --this.inventory[18].stackSize;
                        if(this.inventory[18].stackSize == 0) {
                            this.inventory[18] = this.inventory[18].getItem().getContainerItem(this.inventory[18]);
                        }
                    }
                }
            }
            if(this.isCooking() && this.canCookAnyItem()) {
                ++this.currentCookTime;
                if(this.currentCookTime == 400) {
                    this.currentCookTime = 0;
                    for(int i = 0; i < 9; ++i) {
                        this.cookItem(i);
                    }
                    needUpdate = true;
                }
            }
            else {
                this.currentCookTime = 0;
            }
            if(cooking != this.ovenCookTime > 0) {
                needUpdate = true;
                LOTRBlockHobbitOven.setOvenActive(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if(needUpdate) {
            this.markDirty();
        }
    }

    private boolean canCookAnyItem() {
        for(int i = 0; i < 9; ++i) {
            if(!this.canCook(i)) continue;
            return true;
        }
        return false;
    }

    private boolean canCook(int i) {
        if(this.inventory[i] == null) {
            return false;
        }
        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[i]);
        if(!LOTRTileEntityHobbitOven.isCookResultAcceptable(result)) {
            return false;
        }
        if(this.inventory[i + 9] == null) {
            return true;
        }
        if(!this.inventory[i + 9].isItemEqual(result)) {
            return false;
        }
        int resultSize = this.inventory[i + 9].stackSize + result.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
    }

    private void cookItem(int i) {
        if(this.canCook(i)) {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[i]);
            if(this.inventory[i + 9] == null) {
                this.inventory[i + 9] = itemstack.copy();
            }
            else if(this.inventory[i + 9].isItemEqual(itemstack)) {
                this.inventory[i + 9].stackSize += itemstack.stackSize;
            }
            --this.inventory[i].stackSize;
            if(this.inventory[i].stackSize <= 0) {
                this.inventory[i] = null;
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

    public static boolean isCookResultAcceptable(ItemStack result) {
        if(result == null) {
            return false;
        }
        Item item = result.getItem();
        return item instanceof ItemFood || item == LOTRMod.pipeweed || item == Item.getItemFromBlock(LOTRMod.driedReeds);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if(slot < 9) {
            return itemstack == null ? false : LOTRTileEntityHobbitOven.isCookResultAcceptable(FurnaceRecipes.smelting().getSmeltingResult(itemstack));
        }
        if(slot < 18) {
            return false;
        }
        return TileEntityFurnace.isItemFuel(itemstack);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if(side == 0) {
            ArrayList<Integer> list = new ArrayList<>();
            for(int i : this.outputSlots) {
                list.add(i);
            }
            list.add(this.fuelSlot);
            int[] temp = new int[list.size()];
            for(int i = 0; i < temp.length; ++i) {
                temp[i] = list.get(i);
            }
            return temp;
        }
        if(side == 1) {
            ArrayList<LOTRSlotStackSize> list = new ArrayList<>();
            for(int slot : this.inputSlots) {
                int size = this.getStackInSlot(slot) == null ? 0 : this.getStackInSlot(slot).stackSize;
                list.add(new LOTRSlotStackSize(slot, size));
            }
            Collections.sort(list);
            int[] temp = new int[this.inputSlots.length];
            for(int i = 0; i < temp.length; ++i) {
                LOTRSlotStackSize obj = list.get(i);
                temp[i] = obj.slot;
            }
            return temp;
        }
        return new int[] {this.fuelSlot};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
        return this.isItemValidForSlot(slot, itemstack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
        if(side == 0) {
            if(slot == this.fuelSlot) {
                return itemstack.getItem() == Items.bucket;
            }
            return true;
        }
        return true;
    }

    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet) {
        if(packet.func_148857_g() != null && packet.func_148857_g().hasKey("CustomName")) {
            this.specialOvenName = packet.func_148857_g().getString("CustomName");
        }
    }
}
