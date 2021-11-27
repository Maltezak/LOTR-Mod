package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

public class LOTRPacketNPCSquadron implements IMessage {
    private int npcID;
    private String squadron;

    public LOTRPacketNPCSquadron() {
    }

    public LOTRPacketNPCSquadron(LOTREntityNPC npc, String s) {
        this.npcID = npc.getEntityId();
        this.squadron = s;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.npcID);
        if(StringUtils.isNullOrEmpty(this.squadron)) {
            data.writeInt(-1);
        }
        else {
            byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeInt(sqBytes.length);
            data.writeBytes(sqBytes);
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.npcID = data.readInt();
        int length = data.readInt();
        if(length > -1) {
            this.squadron = data.readBytes(length).toString(Charsets.UTF_8);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketNPCSquadron, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketNPCSquadron packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity npc = world.getEntityByID(packet.npcID);
            if(npc instanceof LOTREntityNPC) {
                LOTREntityNPC hiredNPC = (LOTREntityNPC) npc;
                if(hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    String squadron = packet.squadron;
                    if(!StringUtils.isNullOrEmpty(squadron)) {
                        squadron = LOTRSquadrons.checkAcceptableLength(squadron);
                        hiredNPC.hiredNPCInfo.setSquadron(squadron);
                    }
                    else {
                        hiredNPC.hiredNPCInfo.setSquadron("");
                    }
                }
            }
            return null;
        }
    }

}
