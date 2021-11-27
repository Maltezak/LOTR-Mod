package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCIsEating implements IMessage {
    private int entityID;
    private boolean isEating;

    public LOTRPacketNPCIsEating() {
    }

    public LOTRPacketNPCIsEating(int id, boolean flag) {
        this.entityID = id;
        this.isEating = flag;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.isEating);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.entityID = data.readInt();
        this.isEating = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketNPCIsEating, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketNPCIsEating packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            Entity entity = world.getEntityByID(packet.entityID);
            if(entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC) entity).clientIsEating = packet.isEating;
            }
            return null;
        }
    }

}
