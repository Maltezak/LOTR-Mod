package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketEditNPCRespawner implements IMessage {
    private int spawnerID;
    private NBTTagCompound spawnerData;
    public boolean destroy;

    public LOTRPacketEditNPCRespawner() {
    }

    public LOTRPacketEditNPCRespawner(LOTREntityNPCRespawner spawner) {
        this.spawnerID = spawner.getEntityId();
        this.spawnerData = new NBTTagCompound();
        spawner.writeSpawnerDataToNBT(this.spawnerData);
    }

    @Override
    public void toBytes(ByteBuf data) {
        data.writeInt(this.spawnerID);
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.spawnerData);
        }
        catch(IOException e) {
            FMLLog.severe("Error writing spawner data");
            e.printStackTrace();
        }
        data.writeBoolean(this.destroy);
    }

    @Override
    public void fromBytes(ByteBuf data) {
        this.spawnerID = data.readInt();
        try {
            this.spawnerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch(IOException e) {
            FMLLog.severe("Error reading spawner data");
            e.printStackTrace();
        }
        this.destroy = data.readBoolean();
    }

    public static class Handler implements IMessageHandler<LOTRPacketEditNPCRespawner, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketEditNPCRespawner packet, MessageContext context) {
            EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            World world = entityplayer.worldObj;
            Entity sEntity = world.getEntityByID(packet.spawnerID);
            if(sEntity instanceof LOTREntityNPCRespawner) {
                LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) sEntity;
                if(entityplayer.capabilities.isCreativeMode) {
                    spawner.readSpawnerDataFromNBT(packet.spawnerData);
                }
                if(packet.destroy) {
                    spawner.onBreak();
                }
            }
            return null;
        }
    }

}
