package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketHiredUnitDismiss implements IMessage {
    private int entityID;
    private int action;

    public LOTRPacketHiredUnitDismiss() {
    }

    public LOTRPacketHiredUnitDismiss(int id, int a) {
        this.entityID = id;
        this.action = a;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeByte(this.action);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.entityID = data.readInt();
        this.action = data.readByte();
    }

    public static class Handler implements IMessageHandler<LOTRPacketHiredUnitDismiss, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketHiredUnitDismiss packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity npc = world.getEntityByID(packet.entityID);
            if(npc instanceof LOTREntityNPC) {
                LOTREntityNPC hiredNPC = (LOTREntityNPC) npc;
                if(hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    int action = packet.action;
                    boolean closeScreen = false;
                    if(action == 0) {
                        hiredNPC.hiredNPCInfo.dismissUnit(false);
                        Entity mount = hiredNPC.ridingEntity;
                        Entity rider = hiredNPC.riddenByEntity;
                        if(mount instanceof LOTREntityNPC) {
                            LOTREntityNPC mountNPC = (LOTREntityNPC) mount;
                            if(mountNPC.hiredNPCInfo.isActive && mountNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                                mountNPC.hiredNPCInfo.dismissUnit(false);
                            }
                        }
                        if(rider instanceof LOTREntityNPC) {
                            LOTREntityNPC riderNPC = (LOTREntityNPC) rider;
                            if(riderNPC.hiredNPCInfo.isActive && riderNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                                riderNPC.hiredNPCInfo.dismissUnit(false);
                            }
                        }
                        closeScreen = true;
                    }
                    if(closeScreen) {
                        entityplayer.closeScreen();
                    }
                }
            }
            return null;
        }
    }

}
