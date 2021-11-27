package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTRPacketMiniquestOfferClose implements IMessage {
    private int npcID;
    private boolean accept;

    public LOTRPacketMiniquestOfferClose() {
    }

    public LOTRPacketMiniquestOfferClose(int id, boolean flag) {
        this.npcID = id;
        this.accept = flag;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.npcID);
        data.writeBoolean(this.accept);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.npcID = data.readInt();
        this.accept = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketMiniquestOfferClose, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketMiniquestOfferClose packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity npcEntity = world.getEntityByID(packet.npcID);
            if(npcEntity instanceof LOTREntityNPC) {
                LOTREntityNPC npc = (LOTREntityNPC) npcEntity;
                npc.questInfo.receiveOfferResponse(entityplayer, packet.accept);
            }
            return null;
        }
    }

}
