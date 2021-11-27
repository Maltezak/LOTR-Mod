package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.*;

public abstract class LOTRPacketFellowshipDo implements IMessage {
    private UUID fellowshipID;

    public LOTRPacketFellowshipDo() {
    }

    public LOTRPacketFellowshipDo(LOTRFellowshipClient fsClient) {
        this.fellowshipID = fsClient.getFellowshipID();
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeLong(this.fellowshipID.getMostSignificantBits());
        data.writeLong(this.fellowshipID.getLeastSignificantBits());
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.fellowshipID = new UUID(data.readLong(), data.readLong());
    }

    protected LOTRFellowship getFellowship() {
        return LOTRFellowshipData.getFellowship(this.fellowshipID);
    }
}
