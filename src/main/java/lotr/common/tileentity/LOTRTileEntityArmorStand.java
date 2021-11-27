package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRTileEntityArmorStand extends TileEntity implements IInventory {
    private ItemStack[] inventory = new ItemStack[4];
    public int ticksExisted;

    @Override
    public void setWorldObj(World world) {
        super.setWorldObj(world);
        this.ticksExisted = world.rand.nextInt(100);
    }

    @Override
    public void updateEntity() {
        ++this.ticksExisted;
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
                this.markDirty();
                return itemstack;
            }
            ItemStack itemstack = this.inventory[i].splitStack(j);
            if(this.inventory[i].stackSize == 0) {
                this.inventory[i] = null;
            }
            this.markDirty();
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
        this.markDirty();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("container.lotr.armorStand");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readArmorStandFromNBT(nbt);
    }

    private void readArmorStandFromNBT(NBTTagCompound nbt) {
        NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for(int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound itemData = items.getCompoundTagAt(i);
            byte byte0 = itemData.getByte("Slot");
            if(byte0 < 0 || byte0 >= this.inventory.length) continue;
            this.inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeArmorStandToNBT(nbt);
    }

    private void writeArmorStandToNBT(NBTTagCompound nbt) {
        NBTTagList items = new NBTTagList();
        for(int i = 0; i < this.inventory.length; ++i) {
            if(this.inventory[i] == null) continue;
            NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte) i);
            this.inventory[i].writeToNBT(itemData);
            items.appendTag(itemData);
        }
        nbt.setTag("Items", items);
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

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord, this.zCoord - 1, this.xCoord + 1, this.yCoord + 2, this.zCoord + 1);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        this.writeArmorStandToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        this.readArmorStandFromNBT(packet.func_148857_g());
    }
}
