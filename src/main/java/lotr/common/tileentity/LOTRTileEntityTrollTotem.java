package lotr.common.tileentity;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityMountainTrollChieftain;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityTrollTotem extends TileEntity {
    private int prevJawRotation;
    private int jawRotation;
    private boolean prevCanSummon;
    private boolean clientCanSummon;

    @Override
    public void updateEntity() {
        if(!this.worldObj.isRemote) {
            boolean flag = this.canSummon();
            if(flag != this.prevCanSummon) {
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }
            this.prevCanSummon = flag;
        }
        else {
            this.prevJawRotation = this.jawRotation;
            if(this.jawRotation < 60 && this.canSummon()) {
                ++this.jawRotation;
            }
            else if(this.jawRotation > 0 && !this.canSummon()) {
                --this.jawRotation;
            }
        }
    }

    public float getJawRotation(float f) {
        float rotation = this.prevJawRotation + (this.jawRotation - this.prevJawRotation) * f;
        return rotation / 60.0f * -35.0f;
    }

    public boolean canSummon() {
        if(this.worldObj.isRemote) {
            return this.clientCanSummon;
        }
        if(this.worldObj.isDaytime()) {
            return false;
        }
        if(!this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)) {
            return false;
        }
        if(this.getBlockType() == LOTRMod.trollTotem && (this.getBlockMetadata() & 3) == 0) {
            int rotation = (this.getBlockMetadata() & 0xC) >> 2;
            if(this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == LOTRMod.trollTotem && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1, this.zCoord) == (1 | rotation << 2) && this.worldObj.getBlock(this.xCoord, this.yCoord - 2, this.zCoord) == LOTRMod.trollTotem && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 2, this.zCoord) == (2 | rotation << 2)) {
                return true;
            }
        }
        return false;
    }

    public void summon() {
        if(!this.worldObj.isRemote) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 1, this.zCoord);
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord - 2, this.zCoord);
            LOTREntityMountainTrollChieftain troll = new LOTREntityMountainTrollChieftain(this.worldObj);
            troll.setLocationAndAngles(this.xCoord + 0.5, this.yCoord - 2, this.zCoord + 0.5, this.worldObj.rand.nextFloat() * 360.0f, 0.0f);
            troll.onSpawnWithEgg(null);
            this.worldObj.spawnEntityInWorld(troll);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        data.setBoolean("CanSummon", this.canSummon());
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound data = packet.func_148857_g();
        this.clientCanSummon = data.getBoolean("CanSummon");
    }
}
