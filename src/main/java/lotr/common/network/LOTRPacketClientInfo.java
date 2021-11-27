package lotr.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.LOTRDimension.DimensionRegion;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.LOTRMiniQuestEvent;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketClientInfo implements IMessage {
    private LOTRFaction viewingFaction;
    private Map<LOTRDimension.DimensionRegion, LOTRFaction> changedRegionMap;
    private boolean showWP;
    private boolean showCWP;
    private boolean showHiddenSWP;

    public LOTRPacketClientInfo() {
    }

    public LOTRPacketClientInfo(LOTRFaction f, Map<LOTRDimension.DimensionRegion, LOTRFaction> crMap, boolean w, boolean cw, boolean h) {
        this.viewingFaction = f;
        this.changedRegionMap = crMap;
        this.showWP = w;
        this.showCWP = cw;
        this.showHiddenSWP = h;
    }

    @Override
    public void toBytes(ByteBuf data) {
        if(this.viewingFaction == null) {
            data.writeByte(-1);
        }
        else {
            data.writeByte(this.viewingFaction.ordinal());
        }
        int changedRegionsSize = this.changedRegionMap != null ? this.changedRegionMap.size() : 0;
        data.writeByte(changedRegionsSize);
        if(changedRegionsSize > 0) {
            for(Map.Entry<LOTRDimension.DimensionRegion, LOTRFaction> e : this.changedRegionMap.entrySet()) {
                LOTRDimension.DimensionRegion reg = e.getKey();
                LOTRFaction fac = e.getValue();
                data.writeByte(reg.ordinal());
                data.writeByte(fac.ordinal());
            }
        }
        data.writeBoolean(this.showWP);
        data.writeBoolean(this.showCWP);
        data.writeBoolean(this.showHiddenSWP);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        int changedRegionsSize;
        byte factionID = data.readByte();
        if(factionID >= 0) {
            this.viewingFaction = LOTRFaction.forID(factionID);
        }
        if((changedRegionsSize = data.readByte()) > 0) {
            this.changedRegionMap = new HashMap<>();
            for(int l = 0; l < changedRegionsSize; ++l) {
                LOTRDimension.DimensionRegion reg = LOTRDimension.DimensionRegion.forID(data.readByte());
                LOTRFaction fac = LOTRFaction.forID(data.readByte());
                this.changedRegionMap.put(reg, fac);
            }
        }
        this.showWP = data.readBoolean();
        this.showCWP = data.readBoolean();
        this.showHiddenSWP = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketClientInfo, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketClientInfo packet, MessageContext context) {
            Map<DimensionRegion, LOTRFaction> changedRegionMap;
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if(packet.viewingFaction != null) {
                LOTRFaction prevFac = pd.getViewingFaction();
                LOTRFaction newFac = packet.viewingFaction;
                pd.setViewingFaction(newFac);
                if(prevFac != newFac && prevFac.factionRegion == newFac.factionRegion) {
                    pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignment());
                }
                if(prevFac.factionRegion != newFac.factionRegion) {
                    pd.distributeMQEvent(new LOTRMiniQuestEvent.CycleAlignmentRegion());
                }
            }
            if((changedRegionMap = packet.changedRegionMap) != null) {
                for(LOTRDimension.DimensionRegion reg : changedRegionMap.keySet()) {
                    LOTRFaction fac = (changedRegionMap.get(reg));
                    pd.setRegionLastViewedFaction(reg, fac);
                }
            }
            pd.setShowWaypoints(packet.showWP);
            pd.setShowCustomWaypoints(packet.showCWP);
            pd.setShowHiddenSharedWaypoints(packet.showHiddenSWP);
            return null;
        }
    }

}
