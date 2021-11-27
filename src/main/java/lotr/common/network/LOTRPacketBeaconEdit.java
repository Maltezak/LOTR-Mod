package lotr.common.network;

import java.util.UUID;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.*;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class LOTRPacketBeaconEdit implements IMessage {
    private int beaconX;
    private int beaconY;
    private int beaconZ;
    private UUID fellowshipID;
    private String beaconName;
    private boolean releasePlayer;

    public LOTRPacketBeaconEdit() {
    }

    public LOTRPacketBeaconEdit(int i, int j, int k, UUID fsID, String name, boolean release) {
        this.beaconX = i;
        this.beaconY = j;
        this.beaconZ = k;
        this.fellowshipID = fsID;
        this.beaconName = name;
        this.releasePlayer = release;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.beaconX);
        data.writeInt(this.beaconY);
        data.writeInt(this.beaconZ);
        boolean hasFs = this.fellowshipID != null;
        data.writeBoolean(hasFs);
        if(hasFs) {
            data.writeLong(this.fellowshipID.getMostSignificantBits());
            data.writeLong(this.fellowshipID.getLeastSignificantBits());
        }
        boolean hasName = this.beaconName != null;
        data.writeBoolean(hasName);
        if(hasName) {
            byte[] nameBytes = this.beaconName.getBytes(Charsets.UTF_8);
            data.writeShort(nameBytes.length);
            data.writeBytes(nameBytes);
        }
        data.writeBoolean(this.releasePlayer);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.beaconX = data.readInt();
        this.beaconY = data.readInt();
        this.beaconZ = data.readInt();
        if(data.readBoolean()) {
            this.fellowshipID = new UUID(data.readLong(), data.readLong());
        }
        if(data.readBoolean()) {
            short length = data.readShort();
            ByteBuf nameBytes = data.readBytes(length);
            this.beaconName = nameBytes.toString(Charsets.UTF_8);
        }
        this.releasePlayer = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketBeaconEdit, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBeaconEdit packet, MessageContext context) {
            LOTRTileEntityBeacon beacon;
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            TileEntity te = entityplayer.worldObj.getTileEntity(packet.beaconX, packet.beaconY, packet.beaconZ);
            if(te instanceof LOTRTileEntityBeacon && (beacon = (LOTRTileEntityBeacon) te).isPlayerEditing(entityplayer)) {
                LOTRFellowship fellowship = null;
                if(packet.fellowshipID != null) {
                    fellowship = LOTRFellowshipData.getFellowship(packet.fellowshipID);
                }
                if(fellowship != null && !fellowship.isDisbanded() && fellowship.containsPlayer(entityplayer.getUniqueID())) {
                    beacon.setFellowship(fellowship);
                }
                else {
                    beacon.setFellowship(null);
                }
                if(packet.beaconName != null) {
                    beacon.setBeaconName(packet.beaconName);
                }
                else {
                    beacon.setBeaconName(null);
                }
                if(packet.releasePlayer) {
                    beacon.releaseEditingPlayer(entityplayer);
                }
            }
            return null;
        }
    }

}
