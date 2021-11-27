package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import lotr.common.fac.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketFactionData implements IMessage {
    private LOTRFaction faction;
    private NBTTagCompound factionNBT;

    public LOTRPacketFactionData() {
    }

    public LOTRPacketFactionData(LOTRFaction f, NBTTagCompound nbt) {
        this.faction = f;
        this.factionNBT = nbt;
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeByte(this.faction.ordinal());
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.factionNBT);
        }
        catch(IOException e) {
            FMLLog.severe("Error writing faction data");
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteBuf data) {
        byte factionID = data.readByte();
        this.faction = LOTRFaction.forID(factionID);
        try {
            this.factionNBT = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch(IOException e) {
            FMLLog.severe("Error reading faction data");
            e.printStackTrace();
        }
    }

    public static class Handler implements IMessageHandler<LOTRPacketFactionData, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketFactionData packet, MessageContext context) {
            if(!LOTRMod.proxy.isSingleplayer()) {
                EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                LOTRFaction faction = packet.faction;
                if(faction != null) {
                    LOTRFactionData factionData = pd.getFactionData(faction);
                    factionData.load(packet.factionNBT);
                }
            }
            return null;
        }
    }

}
