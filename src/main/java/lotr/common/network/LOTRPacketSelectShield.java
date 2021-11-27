package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayerMP;

public class LOTRPacketSelectShield implements IMessage {
    private LOTRShields shield;

    public LOTRPacketSelectShield() {
    }

    public LOTRPacketSelectShield(LOTRShields s) {
        this.shield = s;
    }

    @Override
    public void toBytes(ByteBuf data) {
        if(this.shield == null) {
            data.writeByte(-1);
        }
        else {
            data.writeByte(this.shield.shieldID);
            data.writeByte(this.shield.shieldType.ordinal());
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte shieldID = data.readByte();
        if(shieldID == -1) {
            this.shield = null;
        }
        else {
            byte shieldTypeID = data.readByte();
            if(shieldTypeID < 0 || shieldTypeID >= LOTRShields.ShieldType.values().length) {
                FMLLog.severe("Failed to update LOTR shield on server side: There is no shieldtype with ID " + shieldTypeID);
            }
            else {
                LOTRShields.ShieldType shieldType = LOTRShields.ShieldType.values()[shieldTypeID];
                if(shieldID < 0 || shieldID >= shieldType.list.size()) {
                    FMLLog.severe("Failed to update LOTR shield on server side: There is no shield with ID " + shieldID + " for shieldtype " + shieldTypeID);
                }
                else {
                    this.shield = shieldType.list.get(shieldID);
                }
            }
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketSelectShield, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketSelectShield packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            LOTRShields shield = packet.shield;
            if(shield == null || shield.canPlayerWear(entityplayer)) {
                LOTRLevelData.getData(entityplayer).setShield(shield);
                LOTRLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
            }
            else {
                FMLLog.severe("Failed to update LOTR shield on server side: Player " + entityplayer.getCommandSenderName() + " cannot wear shield " + shield.name());
            }
            return null;
        }
    }

}
