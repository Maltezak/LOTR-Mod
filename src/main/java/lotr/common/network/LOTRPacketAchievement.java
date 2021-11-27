package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketAchievement implements IMessage {
    private LOTRAchievement achievement;
    private boolean display;

    public LOTRPacketAchievement() {
    }

    public LOTRPacketAchievement(LOTRAchievement ach, boolean disp) {
        this.achievement = ach;
        this.display = disp;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.achievement.category.ordinal());
        data.writeShort(this.achievement.ID);
        data.writeBoolean(this.display);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte catID = data.readByte();
        short achID = data.readShort();
        LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
        this.achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
        this.display = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketAchievement, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketAchievement packet, MessageContext context) {
            LOTRAchievement achievement = packet.achievement;
            if(achievement != null) {
                if(!LOTRMod.proxy.isSingleplayer()) {
                    EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                    LOTRLevelData.getData(entityplayer).addAchievement(achievement);
                }
                if(packet.display) {
                    LOTRMod.proxy.queueAchievement(achievement);
                }
            }
            return null;
        }
    }

}
