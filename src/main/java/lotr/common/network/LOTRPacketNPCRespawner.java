package lotr.common.network;

import java.io.IOException;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class LOTRPacketNPCRespawner implements IMessage {
    private int spawnerID;
    private NBTTagCompound spawnerData;

    public LOTRPacketNPCRespawner() {
    }

    public LOTRPacketNPCRespawner(LOTREntityNPCRespawner spawner) {
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
    }

    public static class Handler implements IMessageHandler<LOTRPacketNPCRespawner, IMessage> {
        @Override
        public IMessage onMessage(LOTRPacketNPCRespawner packet, MessageContext context) {
            World world = LOTRMod.proxy.getClientWorld();
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            Entity entity = world.getEntityByID(packet.spawnerID);
            if(entity instanceof LOTREntityNPCRespawner) {
                LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) entity;
                spawner.readSpawnerDataFromNBT(packet.spawnerData);
                entityplayer.openGui(LOTRMod.instance, 45, world, entity.getEntityId(), 0, 0);
            }
            return null;
        }
    }

}
