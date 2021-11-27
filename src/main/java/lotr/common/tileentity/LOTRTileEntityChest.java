package lotr.common.tileentity;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityChest extends TileEntity implements IInventory {
    private ItemStack[] chestContents = new ItemStack[this.getSizeInventory()];
    public float lidAngle;
    public float prevLidAngle;
    public String textureName;
    private int numPlayersUsing;
    private int ticksSinceSync;
    private String customName;

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.chestContents[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if(this.chestContents[i] != null) {
            if(this.chestContents[i].stackSize <= j) {
                ItemStack itemstack = this.chestContents[i];
                this.chestContents[i] = null;
                this.markDirty();
                return itemstack;
            }
            ItemStack itemstack = this.chestContents[i].splitStack(j);
            if(this.chestContents[i].stackSize == 0) {
                this.chestContents[i] = null;
            }
            this.markDirty();
            return itemstack;
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if(this.chestContents[i] != null) {
            ItemStack itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.chestContents[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.chest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String s) {
        this.customName = s;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList itemTags = nbt.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < itemTags.tagCount(); ++i) {
            NBTTagCompound slotData = itemTags.getCompoundTagAt(i);
            int slot = slotData.getByte("Slot") & 0xFF;
            if(slot < 0 || slot >= this.chestContents.length) continue;
            this.chestContents[slot] = ItemStack.loadItemStackFromNBT(slotData);
        }
        if(nbt.hasKey("CustomName", 8)) {
            this.customName = nbt.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList itemTags = new NBTTagList();
        for(int i = 0; i < this.chestContents.length; ++i) {
            if(this.chestContents[i] == null) continue;
            NBTTagCompound slotData = new NBTTagCompound();
            slotData.setByte("Slot", (byte) i);
            this.chestContents[i].writeToNBT(slotData);
            itemTags.appendTag(slotData);
        }
        nbt.setTag("Items", itemTags);
        if(this.hasCustomInventoryName()) {
            nbt.setString("CustomName", this.customName);
        }
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
    public void updateEntity() {
        super.updateEntity();
        this.prevLidAngle = this.lidAngle;
        ++this.ticksSinceSync;
        if(!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            float range = 5.0f;
            List players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - range, this.yCoord - range, this.zCoord - range, this.xCoord + 1 + range, this.yCoord + 1 + range, this.zCoord + 1 + range));
            for(Object obj : players) {
                EntityPlayer entityplayer = (EntityPlayer) obj;
                if(!(entityplayer.openContainer instanceof ContainerChest) || (((ContainerChest) entityplayer.openContainer).getLowerChestInventory()) != this) continue;
                ++this.numPlayersUsing;
            }
        }
        if(this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if(this.numPlayersUsing == 0 && this.lidAngle > 0.0f || this.numPlayersUsing > 0 && this.lidAngle < 1.0f) {
            float pre = this.lidAngle;
            float incr = 0.1f;
            this.lidAngle = this.numPlayersUsing > 0 ? (this.lidAngle += incr) : (this.lidAngle -= incr);
            this.lidAngle = Math.min(this.lidAngle, 1.0f);
            this.lidAngle = Math.max(this.lidAngle, 0.0f);
            float thr = 0.5f;
            if(this.lidAngle < thr && pre >= thr) {
                this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    public void openInventory() {
        if(this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }
        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }

    @Override
    public void closeInventory() {
        if(this.getBlockType() instanceof LOTRBlockChest) {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }

    @Override
    public boolean receiveClientEvent(int i, int j) {
        if(i == 1) {
            this.numPlayersUsing = j;
            return true;
        }
        return super.receiveClientEvent(i, j);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord, this.zCoord - 1, this.xCoord + 2, this.yCoord + 2, this.zCoord + 2);
    }
}
