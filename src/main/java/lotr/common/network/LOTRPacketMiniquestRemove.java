package lotr.common.network;

import java.util.UUID;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketMiniquestRemove implements IMessage {
    private UUID questUUID;
    private boolean wasCompleted;
    private boolean addToCompleted;

    public LOTRPacketMiniquestRemove() {
    }

    public LOTRPacketMiniquestRemove(LOTRMiniQuest quest, boolean wc, boolean atc) {
        this.questUUID = quest.questUUID;
        this.wasCompleted = wc;
        this.addToCompleted = atc;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeLong(this.questUUID.getMostSignificantBits());
        data.writeLong(this.questUUID.getLeastSignificantBits());
        data.writeBoolean(this.wasCompleted);
        data.writeBoolean(this.addToCompleted);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.questUUID = new UUID(data.readLong(), data.readLong());
        this.wasCompleted = data.readBoolean();
        this.addToCompleted = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketMiniquestRemove, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketMiniquestRemove packet, MessageContext context) {
            if(!LOTRMod.proxy.isSingleplayer()) {
                EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                LOTRMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.wasCompleted);
                if(removeQuest != null) {
                    if(packet.addToCompleted) {
                        pd.completeMiniQuest(removeQuest);
                    }
                    else {
                        pd.removeMiniQuest(removeQuest, packet.wasCompleted);
                    }
                }
                else {
                    FMLLog.warning("Tried to remove a LOTR miniquest that doesn't exist");
                }
            }
            return null;
        }
    }

}
