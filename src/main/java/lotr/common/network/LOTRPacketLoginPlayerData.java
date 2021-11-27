package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketLoginPlayerData implements IMessage {
    private NBTTagCompound playerData;

    public LOTRPacketLoginPlayerData() {
    }

    public LOTRPacketLoginPlayerData(NBTTagCompound nbt) {
        this.playerData = nbt;
    }

    @Override
    public void toBytes(ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.playerData);
        }
        catch(IOException e) {
            FMLLog.severe("Error writing LOTR login player data");
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        try {
            this.playerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch(IOException e) {
            FMLLog.severe("Error reading LOTR login player data");
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketLoginPlayerData, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketLoginPlayerData packet, MessageContext context) {
            NBTTagCompound nbt = packet.playerData;
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if(!LOTRMod.proxy.isSingleplayer()) {
                pd.load(nbt);
            }
            LOTRMod.proxy.setWaypointModes(pd.showWaypoints(), pd.showCustomWaypoints(), pd.showHiddenSharedWaypoints());
            return null;
        }
    }

}
