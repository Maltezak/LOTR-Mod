package lotr.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityEntJar extends TileEntity {
    public int drinkMeta = -1;
    public int drinkAmount;
    public static int MAX_CAPACITY = 6;

    @Override
    public void updateEntity() {
        if(!this.worldObj.isRemote && (this.worldObj.canLightningStrikeAt(this.xCoord, this.yCoord, this.zCoord) || this.worldObj.canLightningStrikeAt(this.xCoord, this.yCoord + 1, this.zCoord)) && this.worldObj.rand.nextInt(2500) == 0) {
            this.fillWithWater();
        }
    }

    public boolean fillFromBowl(ItemStack itemstack) {
        if(this.drinkMeta == -1 && this.drinkAmount == 0) {
            this.drinkMeta = itemstack.getItemDamage();
            ++this.drinkAmount;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
            return true;
        }
        if(this.drinkMeta == itemstack.getItemDamage() && this.drinkAmount < MAX_CAPACITY) {
            ++this.drinkAmount;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
            return true;
        }
        return false;
    }

    public void fillWithWater() {
        if(this.drinkMeta == -1 && this.drinkAmount < MAX_CAPACITY) {
            ++this.drinkAmount;
        }
        this.drinkAmount = Math.min(this.drinkAmount, MAX_CAPACITY);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
    }

    public void consume() {
        --this.drinkAmount;
        if(this.drinkAmount <= 0) {
            this.drinkMeta = -1;
        }
        this.drinkAmount = Math.max(this.drinkAmount, 0);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("DrinkMeta", this.drinkMeta);
        nbt.setInteger("DrinkAmount", this.drinkAmount);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.drinkMeta = nbt.getInteger("DrinkMeta");
        this.drinkAmount = nbt.getInteger("DrinkAmount");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
    }
}
