package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTRPacketNPCCombatStance implements IMessage {
    private int entityID;
    private boolean combatStance;

    public LOTRPacketNPCCombatStance() {
    }

    public LOTRPacketNPCCombatStance(int id, boolean flag) {
        this.entityID = id;
        this.combatStance = flag;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.combatStance);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.entityID = data.readInt();
        this.combatStance = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketNPCCombatStance, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketNPCCombatStance packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            Entity entity = world.getEntityByID(packet.entityID);
            if(entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC) entity).clientCombatStance = packet.combatStance;
            }
            return null;
        }
    }

}
