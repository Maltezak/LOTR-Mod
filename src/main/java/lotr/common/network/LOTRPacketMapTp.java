package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class LOTRPacketMapTp implements IMessage {
    private int xCoord;
    private int zCoord;

    public LOTRPacketMapTp() {
    }

    public LOTRPacketMapTp(int x, int z) {
        this.xCoord = x;
        this.zCoord = z;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.xCoord);
        data.writeInt(this.zCoord);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.xCoord = data.readInt();
        this.zCoord = data.readInt();
    }

    public static class Handler implements IMessageHandler<LOTRPacketMapTp, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketMapTp packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
            if(isOp) {
                int i = packet.xCoord;
                int k = packet.zCoord;
                int j = world.getTopSolidOrLiquidBlock(i, k);
                String[] args = new String[] {Integer.toString(i), Integer.toString(j), Integer.toString(k)};
                new CommandTeleport().processCommand(entityplayer, args);
            }
            return null;
        }
    }

}
