package lotr.common.network;

import java.util.UUID;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketHiredInfo
implements IMessage {
    private int entityID;
    public boolean isHired;
    public UUID hiringPlayer;
    public LOTRHiredNPCInfo.Task task;
    public String squadron;
    public int xpLvl;

    public LOTRPacketHiredInfo() {
    }

    public LOTRPacketHiredInfo(int i, UUID player, LOTRHiredNPCInfo.Task t, String sq, int lvl) {
        this.entityID = i;
        this.hiringPlayer = player;
        this.isHired = this.hiringPlayer != null;
        this.task = t;
        this.squadron = sq;
        this.xpLvl = lvl;
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.isHired);
        if (this.isHired) {
            data.writeLong(this.hiringPlayer.getMostSignificantBits());
            data.writeLong(this.hiringPlayer.getLeastSignificantBits());
        }
        data.writeByte(this.task.ordinal());
        if (StringUtils.isNullOrEmpty(this.squadron)) {
            data.writeShort(-1);
        } else {
            byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeShort(sqBytes.length);
            data.writeBytes(sqBytes);
        }
        data.writeShort(this.xpLvl);
    }

    public void fromBytes(ByteBuf data) {
        this.entityID = data.readInt();
        this.isHired = data.readBoolean();
        this.hiringPlayer = this.isHired ? new UUID(data.readLong(), data.readLong()) : null;
        this.task = LOTRHiredNPCInfo.Task.forID(data.readByte());
        short sqLength = data.readShort();
        if (sqLength > -1) {
            this.squadron = data.readBytes(sqLength).toString(Charsets.UTF_8);
        }
        this.xpLvl = data.readShort();
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketHiredInfo, IMessage> {
        public IMessage onMessage(LOTRPacketHiredInfo packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).hiredNPCInfo.receiveBasicData(packet);
            }
            return null;
        }
    }

}

