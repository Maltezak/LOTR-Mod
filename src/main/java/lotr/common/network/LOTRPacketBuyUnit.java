package lotr.common.network;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.*;
import lotr.common.inventory.LOTRContainerUnitTrade;
import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.Container;
import net.minecraft.util.StringUtils;

public class LOTRPacketBuyUnit implements IMessage {
    private int tradeIndex;
    private String squadron;

    public LOTRPacketBuyUnit() {
    }

    public LOTRPacketBuyUnit(int tr, String s) {
        this.tradeIndex = tr;
        this.squadron = s;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.tradeIndex);
        if(!StringUtils.isNullOrEmpty(this.squadron)) {
            byte[] squadronBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeInt(squadronBytes.length);
            data.writeBytes(squadronBytes);
        }
        else {
            data.writeInt(-1);
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.tradeIndex = data.readByte();
        int squadronLength = data.readInt();
        if(squadronLength > -1) {
            this.squadron = data.readBytes(squadronLength).toString(Charsets.UTF_8);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketBuyUnit, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketBuyUnit packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerUnitTrade) {
                LOTRContainerUnitTrade tradeContainer = (LOTRContainerUnitTrade) container;
                LOTRHireableBase unitTrader = tradeContainer.theUnitTrader;
                int tradeIndex = packet.tradeIndex;
                LOTRUnitTradeEntry trade = null;
                if(unitTrader instanceof LOTRUnitTradeable) {
                    LOTRUnitTradeEntry[] tradeList = ((LOTRUnitTradeable) unitTrader).getUnits().tradeEntries;
                    if(tradeIndex >= 0 && tradeIndex < tradeList.length) {
                        trade = tradeList[tradeIndex];
                    }
                }
                else if(unitTrader instanceof LOTRMercenary) {
                    trade = LOTRMercenaryTradeEntry.createFor((LOTRMercenary) unitTrader);
                }
                String squadron = packet.squadron;
                squadron = LOTRSquadrons.checkAcceptableLength(squadron);
                if(trade != null) {
                    trade.hireUnit(entityplayer, unitTrader, squadron);
                    if(unitTrader instanceof LOTRMercenary) {
                        ((EntityPlayer) entityplayer).closeScreen();
                    }
                }
                else {
                    LOTRLog.logger.error("LOTR: Error player " + entityplayer.getCommandSenderName() + " trying to hire unit from " + unitTrader.getNPCName() + " - trade is null or bad index!");
                }
            }
            return null;
        }
    }

}
