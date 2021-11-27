package lotr.common.entity.npc;

import java.util.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class LOTRTravellingTraderInfo {
    private LOTREntityNPC theEntity;
    private LOTRTravellingTrader theTrader;
    public int timeUntilDespawn = -1;
    private List escortUUIDs = new ArrayList();

    public LOTRTravellingTraderInfo(LOTRTravellingTrader entity) {
        this.theEntity = (LOTREntityNPC) (entity);
        this.theTrader = entity;
    }

    public void startVisiting(EntityPlayer entityplayer) {
        ChatComponentText componentName;
        this.timeUntilDespawn = 24000;
        if(this.theEntity.worldObj.playerEntities.size() <= 1) {
            componentName = new ChatComponentText(this.theEntity.getCommandSenderName());
            componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            LOTRSpeech.messageAllPlayers(new ChatComponentTranslation("lotr.travellingTrader.arrive", componentName));
        }
        else {
            componentName = new ChatComponentText(this.theEntity.getCommandSenderName());
            componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
            LOTRSpeech.messageAllPlayersInWorld(this.theEntity.worldObj, new ChatComponentTranslation("lotr.travellingTrader.arriveMP", componentName, entityplayer.getCommandSenderName()));
        }
        int i = MathHelper.floor_double(this.theEntity.posX);
        int j = MathHelper.floor_double(this.theEntity.boundingBox.minY);
        int k = MathHelper.floor_double(this.theEntity.posZ);
        this.theEntity.setHomeArea(i, j, k, 16);
        int escorts = 2 + this.theEntity.worldObj.rand.nextInt(3);
        for(int l = 0; l < escorts; ++l) {
            LOTREntityNPC escort = this.theTrader.createTravellingEscort();
            if(escort == null) continue;
            escort.setLocationAndAngles(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ, this.theEntity.rotationYaw, this.theEntity.rotationPitch);
            escort.isNPCPersistent = true;
            escort.spawnRidingHorse = false;
            escort.onSpawnWithEgg(null);
            this.theEntity.worldObj.spawnEntityInWorld(escort);
            escort.setHomeArea(i, j, k, 16);
            escort.isTraderEscort = true;
            this.escortUUIDs.add(escort.getUniqueID());
        }
    }

    private void removeEscorts() {
        for(Object obj : this.theEntity.worldObj.loadedEntityList) {
            Entity entity = (Entity) obj;
            UUID entityUUID = entity.getUniqueID();
            for(Object uuid : this.escortUUIDs) {
                if(!entityUUID.equals(uuid)) continue;
                entity.setDead();
            }
        }
    }

    public void onUpdate() {
        if(!this.theEntity.worldObj.isRemote) {
            if(this.timeUntilDespawn > 0) {
                --this.timeUntilDespawn;
            }
            if(this.timeUntilDespawn == 2400) {
                for(Object player : this.theEntity.worldObj.playerEntities) {
                    LOTRSpeech.sendSpeechAndChatMessage((EntityPlayer) player, this.theEntity, this.theTrader.getDepartureSpeech());
                }
            }
            if(this.timeUntilDespawn == 0) {
                ChatComponentText componentName = new ChatComponentText(this.theEntity.getCommandSenderName());
                componentName.getChatStyle().setColor(EnumChatFormatting.YELLOW);
                LOTRSpeech.messageAllPlayersInWorld(this.theEntity.worldObj, new ChatComponentTranslation("lotr.travellingTrader.depart", componentName));
                this.theEntity.setDead();
                this.removeEscorts();
            }
        }
    }

    public void onDeath() {
        if(!this.theEntity.worldObj.isRemote && this.timeUntilDespawn >= 0) {
            LOTRSpeech.messageAllPlayers(this.theEntity.func_110142_aN().func_151521_b());
            this.removeEscorts();
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("DespawnTime", this.timeUntilDespawn);
        NBTTagList escortTags = new NBTTagList();
        for(Object obj : this.escortUUIDs) {
            if(!(obj instanceof UUID)) continue;
            NBTTagCompound escortData = new NBTTagCompound();
            escortData.setLong("UUIDMost", ((UUID) obj).getMostSignificantBits());
            escortData.setLong("UUIDLeast", ((UUID) obj).getLeastSignificantBits());
            escortTags.appendTag(escortData);
        }
        nbt.setTag("Escorts", escortTags);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.timeUntilDespawn = nbt.getInteger("DespawnTime");
        this.escortUUIDs.clear();
        NBTTagList tags = nbt.getTagList("Escorts", 10);
        if(tags != null) {
            for(int i = 0; i < tags.tagCount(); ++i) {
                NBTTagCompound escortData = tags.getCompoundTagAt(i);
                this.escortUUIDs.add(new UUID(escortData.getLong("UUIDMost"), escortData.getLong("UUIDLeast")));
            }
        }
    }
}
