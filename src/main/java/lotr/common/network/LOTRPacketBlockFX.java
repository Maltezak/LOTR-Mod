package lotr.common.network;

import java.util.Random;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import net.minecraft.world.World;

public class LOTRPacketBlockFX implements IMessage {
    private Type type;
    private int blockX;
    private int blockY;
    private int blockZ;

    public LOTRPacketBlockFX() {
    }

    public LOTRPacketBlockFX(Type t, int i, int j, int k) {
        this.type = t;
        this.blockX = i;
        this.blockY = j;
        this.blockZ = k;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.type.ordinal());
        data.writeInt(this.blockX);
        data.writeInt(this.blockY);
        data.writeInt(this.blockZ);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte typeID = data.readByte();
        this.type = Type.values()[typeID];
        this.blockX = data.readInt();
        this.blockY = data.readInt();
        this.blockZ = data.readInt();
    }

    public static class Handler implements IMessageHandler<LOTRPacketBlockFX, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBlockFX packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            int i = packet.blockX;
            int j = packet.blockY;
            int k = packet.blockZ;
            Random rand = world.rand;
            if(packet.type == Type.UTUMNO_EVAPORATE) {
                for(int l = 0; l < 8; ++l) {
                    world.spawnParticle("largesmoke", i + rand.nextFloat(), j + rand.nextFloat(), k + rand.nextFloat(), 0.0, 0.0, 0.0);
                }
            }
            return null;
        }
    }

    public enum Type {
        UTUMNO_EVAPORATE;

    }

}
