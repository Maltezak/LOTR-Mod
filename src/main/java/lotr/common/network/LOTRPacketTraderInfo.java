package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.inventory.LOTRContainerTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketTraderInfo implements IMessage {
    public NBTTagCompound traderData;

    public LOTRPacketTraderInfo() {
    }

    public LOTRPacketTraderInfo(NBTTagCompound nbt) {
        this.traderData = nbt;
    }

    @Override
    public void toBytes(ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.traderData);
        }
        catch(IOException e) {
            FMLLog.severe("Error writing trader data");
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        try {
            this.traderData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch(IOException e) {
            FMLLog.severe("Error reading trader data");
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketTraderInfo, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketTraderInfo packet, MessageContext context) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            Container container = entityplayer.openContainer;
            if(container instanceof LOTRContainerTrade) {
                LOTRContainerTrade containerTrade = (LOTRContainerTrade) container;
                containerTrade.theTraderNPC.traderNPCInfo.receiveClientPacket(packet);
            }
            return null;
        }
    }

}
