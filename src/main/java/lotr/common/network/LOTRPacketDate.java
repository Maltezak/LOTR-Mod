package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketDate implements IMessage {
    public NBTTagCompound dateData;
    private boolean doUpdate;

    public LOTRPacketDate() {
    }

    public LOTRPacketDate(NBTTagCompound nbt, boolean flag) {
        this.dateData = nbt;
        this.doUpdate = flag;
    }

    @Override
    public void toBytes(ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.dateData);
        }
        catch(IOException e) {
            FMLLog.severe("Error writing LOTR date");
            e.printStackTrace();
        }
        data.writeBoolean(this.doUpdate);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        try {
            this.dateData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch(IOException e) {
            FMLLog.severe("Error reading LOTR date");
            e.printStackTrace();
        }
        this.doUpdate = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketDate, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketDate packet, MessageContext context) {
            LOTRDate.loadDates(packet.dateData);
            if(packet.doUpdate) {
                LOTRMod.proxy.displayNewDate();
            }
            return null;
        }
    }

}
