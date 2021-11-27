package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.player.*;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class LOTRPacketCreateCWP
implements IMessage {
    private String name;

    public LOTRPacketCreateCWP() {
    }

    public LOTRPacketCreateCWP(String s) {
        this.name = s;
    }

    public void toBytes(ByteBuf data) {
        byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
    }

    public void fromBytes(ByteBuf data) {
        short length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketCreateCWP, IMessage> {
        public IMessage onMessage(LOTRPacketCreateCWP packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            int numWaypoints = pd.getCustomWaypoints().size();
            if (numWaypoints <= pd.getMaxCustomWaypoints()) {
                IChatComponent[] protectionMessage = new IChatComponent[1];
                boolean protection = LOTRBannerProtection.isProtected(world, entityplayer, LOTRBannerProtection.forPlayer_returnMessage(entityplayer, LOTRBannerProtection.Permission.FULL, protectionMessage), true);
                if (!protection) {
                    String wpName = LOTRCustomWaypoint.validateCustomName(packet.name);
                    if (wpName != null) {
                        LOTRCustomWaypoint.createForPlayer(wpName, entityplayer);
                    }
                } else {
                    IChatComponent clientMessage = protectionMessage[0];
                    LOTRPacketCWPProtectionMessage packetMessage = new LOTRPacketCWPProtectionMessage(clientMessage);
                    LOTRPacketHandler.networkWrapper.sendTo(packetMessage, entityplayer);
                }
            }
            return null;
        }
    }

}

