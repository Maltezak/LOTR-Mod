package lotr.common.entity.npc;

import java.util.*;

import lotr.common.*;
import lotr.common.network.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldServer;

public class LOTRFamilyInfo {
    private LOTREntityNPC theEntity;
    public Class marriageEntityClass;
    public Item marriageRing;
    public float marriageAlignmentRequired;
    public LOTRAchievement marriageAchievement;
    public int potentialMaxChildren;
    public int timeToMature;
    public int breedingDelay;
    public UUID spouseUniqueID;
    public int children;
    public int maxChildren;
    public UUID maleParentID;
    public UUID femaleParentID;
    public UUID ringGivingPlayer;
    private boolean doneFirstUpdate = false;
    private boolean resendData = true;
    private int age;
    private boolean male;
    private String name;
    private int drunkTime;
    private int timeUntilDrunkSpeech;

    public LOTRFamilyInfo(LOTREntityNPC npc) {
        this.theEntity = npc;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int i) {
        this.age = i;
        this.markDirty();
    }

    public boolean isMale() {
        return this.male;
    }

    public void setMale(boolean flag) {
        this.male = flag;
        this.markDirty();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String s) {
        this.name = s;
        this.markDirty();
    }

    public boolean isDrunk() {
        return this.drunkTime > 0;
    }

    public void setDrunkTime(int i) {
        boolean prevDrunk = this.isDrunk();
        this.drunkTime = i;
        if(this.isDrunk() != prevDrunk) {
            this.markDirty();
        }
    }

    private void markDirty() {
        if(!this.theEntity.worldObj.isRemote) {
            if(this.theEntity.ticksExisted > 0) {
                this.resendData = true;
            }
            else {
                this.sendDataToAllWatchers();
            }
        }
    }

    public void sendData(EntityPlayerMP entityplayer) {
        LOTRPacketFamilyInfo packet = new LOTRPacketFamilyInfo(this.theEntity.getEntityId(), this.getAge(), this.isMale(), this.getName(), this.isDrunk());
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    private void sendDataToAllWatchers() {
        int x = MathHelper.floor_double(this.theEntity.posX) >> 4;
        int z = MathHelper.floor_double(this.theEntity.posZ) >> 4;
        PlayerManager playermanager = ((WorldServer) this.theEntity.worldObj).getPlayerManager();
        List players = this.theEntity.worldObj.playerEntities;
        for(Object obj : players) {
            EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
            if(!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) continue;
            this.sendData(entityplayer);
        }
    }

    public void receiveData(LOTRPacketFamilyInfo packet) {
        this.setAge(packet.age);
        this.setMale(packet.isMale);
        this.setName(packet.name);
        if(packet.isDrunk) {
            this.setDrunkTime(100000);
        }
        else {
            this.setDrunkTime(0);
        }
    }

    public void onUpdate() {
        if(!this.theEntity.worldObj.isRemote) {
            if(!this.doneFirstUpdate) {
                this.doneFirstUpdate = true;
            }
            if(this.resendData) {
                this.sendDataToAllWatchers();
                this.resendData = false;
            }
            if(this.getAge() < 0) {
                this.setAge(this.getAge() + 1);
            }
            else if(this.getAge() > 0) {
                this.setAge(this.getAge() - 1);
            }
            if(this.drunkTime > 0) {
                this.setDrunkTime(this.drunkTime - 1);
            }
            if(this.isDrunk()) {
                this.theEntity.addPotionEffect(new PotionEffect(Potion.confusion.id, 20));
                if(this.timeUntilDrunkSpeech > 0) {
                    --this.timeUntilDrunkSpeech;
                }
                if(this.theEntity.isEntityAlive() && this.theEntity.getAttackTarget() == null && this.timeUntilDrunkSpeech == 0) {
                    double range = 12.0;
                    List players = this.theEntity.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.theEntity.boundingBox.expand(range, range, range));
                    for(Object obj : players) {
                        String speechBank;
                        EntityPlayer entityplayer = (EntityPlayer) obj;
                        if(!entityplayer.isEntityAlive() || entityplayer.capabilities.isCreativeMode || (speechBank = this.theEntity.getSpeechBank(entityplayer)) == null || this.theEntity.getRNG().nextInt(3) != 0) continue;
                        this.theEntity.sendSpeechBank(entityplayer, speechBank);
                    }
                    this.timeUntilDrunkSpeech = 20 * MathHelper.getRandomIntegerInRange(this.theEntity.getRNG(), 5, 20);
                }
            }
        }
    }

    public boolean canMarryNPC(LOTREntityNPC npc) {
        if(npc.getClass() != this.theEntity.getClass() || npc.familyInfo.spouseUniqueID != null || npc.familyInfo.getAge() != 0 || npc.getEquipmentInSlot(4) != null) {
            return false;
        }
        if(npc == this.theEntity || npc.familyInfo.isMale() == this.isMale() || this.maleParentID != null && this.maleParentID == npc.familyInfo.maleParentID || this.femaleParentID != null && this.femaleParentID == npc.familyInfo.femaleParentID) {
            return false;
        }
        ItemStack heldItem = npc.getEquipmentInSlot(0);
        return heldItem != null && heldItem.getItem() == this.marriageRing;
    }

    public LOTREntityNPC getSpouse() {
        if(this.spouseUniqueID == null) {
            return null;
        }
        List list = this.theEntity.worldObj.getEntitiesWithinAABB(this.theEntity.getClass(), this.theEntity.boundingBox.expand(16.0, 8.0, 16.0));
        for(Object element : list) {
            Entity entity = (Entity) element;
            if(!(entity instanceof LOTREntityNPC) || entity == this.theEntity || !entity.getUniqueID().equals(this.spouseUniqueID)) continue;
            LOTREntityNPC npc = (LOTREntityNPC) entity;
            if(npc.familyInfo.spouseUniqueID == null || !this.theEntity.getUniqueID().equals(npc.familyInfo.spouseUniqueID)) continue;
            return npc;
        }
        return null;
    }

    public LOTREntityNPC getParentToFollow() {
        UUID parentToFollowID = this.isMale() ? this.maleParentID : this.femaleParentID;
        List list = this.theEntity.worldObj.getEntitiesWithinAABB(this.theEntity.getClass(), this.theEntity.boundingBox.expand(16.0, 8.0, 16.0));
        for(Object element : list) {
            Entity entity = (Entity) element;
            if(!(entity instanceof LOTREntityNPC) || entity == this.theEntity || parentToFollowID == null || !entity.getUniqueID().equals(parentToFollowID)) continue;
            return (LOTREntityNPC) entity;
        }
        return null;
    }

    public boolean interact(EntityPlayer entityplayer) {
        if(this.theEntity.hiredNPCInfo.isActive) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if(itemstack != null && itemstack.getItem() == this.marriageRing && LOTRLevelData.getData(entityplayer).getAlignment(this.theEntity.getFaction()) >= this.marriageAlignmentRequired && this.theEntity.getClass() == this.marriageEntityClass && this.getAge() == 0 && this.theEntity.getEquipmentInSlot(0) == null && this.theEntity.getEquipmentInSlot(4) == null && this.spouseUniqueID == null) {
            if(!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
                if(itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
            }
            if(!this.theEntity.worldObj.isRemote) {
                this.theEntity.setCurrentItemOrArmor(0, new ItemStack(this.marriageRing));
                this.ringGivingPlayer = entityplayer.getUniqueID();
            }
            this.theEntity.isNPCPersistent = true;
            return true;
        }
        return false;
    }

    public EntityPlayer getRingGivingPlayer() {
        if(this.ringGivingPlayer != null) {
            for(Object obj : this.theEntity.worldObj.playerEntities) {
                EntityPlayer entityplayer = (EntityPlayer) obj;
                if(!entityplayer.getUniqueID().equals(this.ringGivingPlayer)) continue;
                return entityplayer;
            }
        }
        return null;
    }

    public void setChild() {
        this.setAge(-this.timeToMature);
    }

    public void setMaxBreedingDelay() {
        float f = this.breedingDelay;
        this.setAge((int) (f *= 0.5f + this.theEntity.getRNG().nextFloat() * 0.5f));
    }

    public int getRandomMaxChildren() {
        return 1 + this.theEntity.getRNG().nextInt(this.potentialMaxChildren);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("NPCAge", this.getAge());
        nbt.setBoolean("NPCMale", this.isMale());
        if(this.getName() != null) {
            nbt.setString("NPCName", this.getName());
        }
        nbt.setInteger("NPCDrunkTime", this.drunkTime);
        if(this.spouseUniqueID != null) {
            nbt.setLong("SpouseUUIDMost", this.spouseUniqueID.getMostSignificantBits());
            nbt.setLong("SpouseUUIDLeast", this.spouseUniqueID.getLeastSignificantBits());
        }
        nbt.setInteger("Children", this.children);
        nbt.setInteger("MaxChildren", this.maxChildren);
        if(this.maleParentID != null) {
            nbt.setLong("MaleParentUUIDMost", this.maleParentID.getMostSignificantBits());
            nbt.setLong("MaleParentUUIDLeast", this.maleParentID.getLeastSignificantBits());
        }
        if(this.femaleParentID != null) {
            nbt.setLong("FemaleParentUUIDMost", this.femaleParentID.getMostSignificantBits());
            nbt.setLong("FemaleParentUUIDLeast", this.femaleParentID.getLeastSignificantBits());
        }
        if(this.ringGivingPlayer != null) {
            nbt.setLong("RingGivingPlayerUUIDMost", this.ringGivingPlayer.getMostSignificantBits());
            nbt.setLong("RingGivingPlayerUUIDLeast", this.ringGivingPlayer.getLeastSignificantBits());
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.setAge(nbt.getInteger("NPCAge"));
        if(nbt.hasKey("NPCMale")) {
            this.setMale(nbt.getBoolean("NPCMale"));
        }
        if(nbt.hasKey("NPCName")) {
            this.setName(nbt.getString("NPCName"));
        }
        this.setDrunkTime(nbt.getInteger("NPCDrunkTime"));
        if(nbt.hasKey("SpouseUUIDMost") && nbt.hasKey("SpouseUUIDLeast")) {
            this.spouseUniqueID = new UUID(nbt.getLong("SpouseUUIDMost"), nbt.getLong("SpouseUUIDLeast"));
        }
        this.children = nbt.getInteger("Children");
        this.maxChildren = nbt.getInteger("MaxChildren");
        if(nbt.hasKey("MaleParentUUIDMost") && nbt.hasKey("MaleParentUUIDLeast")) {
            this.maleParentID = new UUID(nbt.getLong("MaleParentUUIDMost"), nbt.getLong("MaleParentUUIDLeast"));
        }
        if(nbt.hasKey("FemaleParentUUIDMost") && nbt.hasKey("FemaleParentUUIDLeast")) {
            this.femaleParentID = new UUID(nbt.getLong("FemaleParentUUIDMost"), nbt.getLong("FemaleParentUUIDLeast"));
        }
        if(nbt.hasKey("RingGivingPlayer")) {
            this.ringGivingPlayer = new UUID(nbt.getLong("RingGivingPlayerUUIDMost"), nbt.getLong("RingGivingPlayerUUIDLeast"));
        }
    }
}
