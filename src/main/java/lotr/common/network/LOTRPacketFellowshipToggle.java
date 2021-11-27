package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fellowship.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketFellowshipToggle extends LOTRPacketFellowshipDo {
    private ToggleFunction function;

    public LOTRPacketFellowshipToggle() {
    }

    public LOTRPacketFellowshipToggle(LOTRFellowshipClient fs, ToggleFunction f) {
        super(fs);
        this.function = f;
    }

    @Override
    public void toBytes(ByteBuf data) {
        super.toBytes(data);
        data.writeByte(this.function.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf data) {
        super.fromBytes(data);
        this.function = ToggleFunction.values()[data.readByte()];
    }

    public static class Handler implements IMessageHandler<LOTRPacketFellowshipToggle, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFellowshipToggle packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRFellowship fellowship = packet.getFellowship();
            if(fellowship != null) {
                LOTRPlayerData playerData = LOTRLevelData.getData(entityplayer);
                if(packet.function == ToggleFunction.PVP) {
                    boolean current = fellowship.getPreventPVP();
                    playerData.setFellowshipPreventPVP(fellowship, !current);
                }
                else if(packet.function == ToggleFunction.HIRED_FF) {
                    boolean current = fellowship.getPreventHiredFriendlyFire();
                    playerData.setFellowshipPreventHiredFF(fellowship, !current);
                }
                else if(packet.function == ToggleFunction.MAP_SHOW) {
                    boolean current = fellowship.getShowMapLocations();
                    playerData.setFellowshipShowMapLocations(fellowship, !current);
                }
            }
            return null;
        }
    }

    public enum ToggleFunction {
        PVP, HIRED_FF, MAP_SHOW;

    }

}
