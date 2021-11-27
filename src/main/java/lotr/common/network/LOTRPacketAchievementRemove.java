package lotr.common.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRPacketAchievementRemove implements IMessage {
    private LOTRAchievement achievement;

    public LOTRPacketAchievementRemove() {
    }

    public LOTRPacketAchievementRemove(LOTRAchievement ach) {
        this.achievement = ach;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.achievement.category.ordinal());
        data.writeShort(this.achievement.ID);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte catID = data.readByte();
        short achID = data.readShort();
        LOTRAchievement.Category cat = LOTRAchievement.Category.values()[catID];
        this.achievement = LOTRAchievement.achievementForCategoryAndID(cat, achID);
    }

    public static class Handler implements IMessageHandler<LOTRPacketAchievementRemove, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketAchievementRemove packet, MessageContext context) {
            LOTRAchievement achievement = packet.achievement;
            if(achievement != null && !LOTRMod.proxy.isSingleplayer()) {
                EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                LOTRLevelData.getData(entityplayer).removeAchievement(achievement);
            }
            return null;
        }
    }

}
