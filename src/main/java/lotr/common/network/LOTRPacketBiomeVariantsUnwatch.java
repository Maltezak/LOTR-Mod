package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.*;

public class LOTRPacketBiomeVariantsUnwatch implements IMessage {
    private int chunkX;
    private int chunkZ;

    public LOTRPacketBiomeVariantsUnwatch() {
    }

    public LOTRPacketBiomeVariantsUnwatch(int x, int z) {
        this.chunkX = x;
        this.chunkZ = z;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.chunkX);
        data.writeInt(this.chunkZ);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.chunkX = data.readInt();
        this.chunkZ = data.readInt();
    }

    public static class Handler implements IMessageHandler<LOTRPacketBiomeVariantsUnwatch, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBiomeVariantsUnwatch packet, MessageContext context) {
            int chunkX;
            int chunkZ;
            World world = LOTRMod.proxy.getClientWorld();
            if(world.blockExists((chunkX = packet.chunkX) << 4, 0, (chunkZ = packet.chunkZ) << 4)) {
                LOTRBiomeVariantStorage.clearChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ));
            }
            else {
                FMLLog.severe("Client received LOTR biome variant unwatch packet for nonexistent chunk at %d, %d", chunkX << 4, chunkZ << 4);
            }
            return null;
        }
    }

}
