package lotr.common.network;

import java.util.*;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.fac.*;

public class LOTRPacketFactionRelations implements IMessage {
    private Type packetType;
    private Map<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> fullMap;
    private LOTRFactionRelations.FactionPair singleKey;
    private LOTRFactionRelations.Relation singleRelation;

    public static LOTRPacketFactionRelations fullMap(Map<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> map) {
        LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
        pkt.packetType = Type.FULL_MAP;
        pkt.fullMap = map;
        return pkt;
    }

    public static LOTRPacketFactionRelations reset() {
        LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
        pkt.packetType = Type.RESET;
        return pkt;
    }

    public static LOTRPacketFactionRelations oneEntry(LOTRFactionRelations.FactionPair pair, LOTRFactionRelations.Relation rel) {
        LOTRPacketFactionRelations pkt = new LOTRPacketFactionRelations();
        pkt.packetType = Type.ONE_ENTRY;
        pkt.singleKey = pair;
        pkt.singleRelation = rel;
        return pkt;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.packetType.ordinal());
        if(this.packetType == Type.FULL_MAP) {
            for(Map.Entry<LOTRFactionRelations.FactionPair, LOTRFactionRelations.Relation> e : this.fullMap.entrySet()) {
                LOTRFactionRelations.FactionPair key = e.getKey();
                LOTRFactionRelations.Relation rel = e.getValue();
                data.writeByte(key.getLeft().ordinal());
                data.writeByte(key.getRight().ordinal());
                data.writeByte(rel.ordinal());
            }
            data.writeByte(-1);
        }
        else if(this.packetType != Type.RESET && this.packetType == Type.ONE_ENTRY) {
            data.writeByte(this.singleKey.getLeft().ordinal());
            data.writeByte(this.singleKey.getRight().ordinal());
            data.writeByte(this.singleRelation.ordinal());
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte typeID = data.readByte();
        this.packetType = Type.forID(typeID);
        if(this.packetType == Type.FULL_MAP) {
            this.fullMap = new HashMap<>();
            byte fac1ID = 0;
            while((fac1ID = data.readByte()) >= 0) {
                byte fac2ID = data.readByte();
                byte relID = data.readByte();
                LOTRFaction f1 = LOTRFaction.forID(fac1ID);
                LOTRFaction f2 = LOTRFaction.forID(fac2ID);
                LOTRFactionRelations.FactionPair key = new LOTRFactionRelations.FactionPair(f1, f2);
                LOTRFactionRelations.Relation rel = LOTRFactionRelations.Relation.forID(relID);
                this.fullMap.put(key, rel);
            }
        }
        else if(this.packetType != Type.RESET && this.packetType == Type.ONE_ENTRY) {
            byte fac1ID = data.readByte();
            byte fac2ID = data.readByte();
            byte relID = data.readByte();
            LOTRFaction f1 = LOTRFaction.forID(fac1ID);
            LOTRFaction f2 = LOTRFaction.forID(fac2ID);
            this.singleKey = new LOTRFactionRelations.FactionPair(f1, f2);
            this.singleRelation = LOTRFactionRelations.Relation.forID(relID);
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketFactionRelations, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFactionRelations packet, MessageContext context) {
            if(!LOTRMod.proxy.isSingleplayer()) {
                Type t = packet.packetType;
                if(t == Type.FULL_MAP) {
                    LOTRFactionRelations.resetAllRelations();
                    for(Map.Entry e : packet.fullMap.entrySet()) {
                        LOTRFactionRelations.FactionPair key = (LOTRFactionRelations.FactionPair) e.getKey();
                        LOTRFactionRelations.Relation rel = (LOTRFactionRelations.Relation) (e.getValue());
                        LOTRFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
                    }
                }
                else if(t == Type.RESET) {
                    LOTRFactionRelations.resetAllRelations();
                }
                else if(t == Type.ONE_ENTRY) {
                    LOTRFactionRelations.FactionPair key = packet.singleKey;
                    LOTRFactionRelations.Relation rel = packet.singleRelation;
                    LOTRFactionRelations.overrideRelations(key.getLeft(), key.getRight(), rel);
                }
            }
            return null;
        }
    }

    public enum Type {
        FULL_MAP, RESET, ONE_ENTRY;

        public static Type forID(int id) {
            for(Type t : Type.values()) {
                if(t.ordinal() != id) continue;
                return t;
            }
            return null;
        }
    }

}
