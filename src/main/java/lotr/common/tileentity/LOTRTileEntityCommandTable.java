package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRTileEntityCommandTable extends TileEntity {
    private int zoomExp;
    public int getZoomExp() {
        return this.zoomExp;
    }

    public void setZoomExp(int i) {
        this.zoomExp = MathHelper.clamp_int(i, -2, 2);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
    }

    public void toggleZoomExp() {
        int z = this.zoomExp;
        z = z <= -2 ? 2 : --z;
        this.setZoomExp(z);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeTableToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readTableFromNBT(nbt);
    }

    private void writeTableToNBT(NBTTagCompound nbt) {
        nbt.setByte("Zoom", (byte) this.zoomExp);
    }

    private void readTableFromNBT(NBTTagCompound nbt) {
        this.zoomExp = nbt.getByte("Zoom");
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        this.writeTableToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound data = packet.func_148857_g();
        this.readTableFromNBT(data);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord, this.zCoord - 1, this.xCoord + 2, this.yCoord + 2, this.zCoord + 2);
    }
}
