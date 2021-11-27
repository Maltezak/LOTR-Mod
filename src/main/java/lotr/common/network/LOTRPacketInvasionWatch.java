package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityInvasionSpawner;

public class LOTRPacketInvasionWatch
implements IMessage {
    private int invasionEntityID;
    private boolean overrideAlreadyWatched;

    public LOTRPacketInvasionWatch() {
    }

    public LOTRPacketInvasionWatch(LOTREntityInvasionSpawner invasion, boolean override) {
        this.invasionEntityID = invasion.getEntityId();
        this.overrideAlreadyWatched = override;
    }

    public void toBytes(ByteBuf data) {
        data.writeInt(this.invasionEntityID);
        data.writeBoolean(this.overrideAlreadyWatched);
    }

    public void fromBytes(ByteBuf data) {
        this.invasionEntityID = data.readInt();
        this.overrideAlreadyWatched = data.readBoolean();
    }

    public static class Handler
    implements IMessageHandler<LOTRPacketInvasionWatch, IMessage> {
        public IMessage onMessage(LOTRPacketInvasionWatch packet, MessageContext context) {
            LOTRMod.proxy.handleInvasionWatch(packet.invasionEntityID, packet.overrideAlreadyWatched);
            return null;
        }
    }

}

