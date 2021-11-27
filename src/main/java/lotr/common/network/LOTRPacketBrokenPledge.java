package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketBrokenPledge implements IMessage {
    private int cooldown;
    private int cooldownStart;
    private LOTRFaction brokenFac;

    public LOTRPacketBrokenPledge() {
    }

    public LOTRPacketBrokenPledge(int cd, int start, LOTRFaction f) {
        this.cooldown = cd;
        this.cooldownStart = start;
        this.brokenFac = f;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.cooldown);
        data.writeInt(this.cooldownStart);
        if(this.brokenFac == null) {
            data.writeByte(-1);
        }
        else {
            data.writeByte(this.brokenFac.ordinal());
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.cooldown = data.readInt();
        this.cooldownStart = data.readInt();
        byte facID = data.readByte();
        if(facID >= 0) {
            this.brokenFac = LOTRFaction.forID(facID);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketBrokenPledge, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBrokenPledge packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            pd.setPledgeBreakCooldown(packet.cooldown);
            pd.setPledgeBreakCooldownStart(packet.cooldownStart);
            pd.setBrokenPledgeFaction(packet.brokenFac);
            return null;
        }
    }

}
