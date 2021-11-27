package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketSetPlayerRotation implements IMessage {
    private float rotYaw;

    public LOTRPacketSetPlayerRotation() {
    }

    public LOTRPacketSetPlayerRotation(float f) {
        this.rotYaw = f;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeFloat(this.rotYaw);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.rotYaw = data.readFloat();
    }

    public static class Handler implements IMessageHandler<LOTRPacketSetPlayerRotation, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketSetPlayerRotation packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            if(entityplayer != null) {
                entityplayer.rotationYaw = packet.rotYaw;
            }
            return null;
        }
    }

}
