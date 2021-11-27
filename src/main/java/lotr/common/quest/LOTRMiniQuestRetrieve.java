package lotr.common.quest;

import java.util.*;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestRetrieve extends LOTRMiniQuestCollect {
    public Class killEntityType;
    public boolean hasDropped = false;

    public LOTRMiniQuestRetrieve(LOTRPlayerData pd) {
        super(pd);
    }

    public static UUID getRetrieveQuestID(ItemStack itemstack) {
        if(itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("LOTRRetrieveID")) {
            String id = itemstack.getTagCompound().getString("LOTRRetrieveID");
            return UUID.fromString(id);
        }
        return null;
    }

    public static void setRetrieveQuest(ItemStack itemstack, LOTRMiniQuest quest) {
        if(itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setString("LOTRRetrieveID", quest.questUUID.toString());
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("KillClass", LOTREntities.getStringFromClass(this.killEntityType));
        nbt.setBoolean("HasDropped", this.hasDropped);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.killEntityType = LOTREntities.getClassFromString(nbt.getString("KillClass"));
        this.hasDropped = nbt.getBoolean("HasDropped");
    }

    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.killEntityType != null && EntityLivingBase.class.isAssignableFrom(this.killEntityType);
    }

    @Override
    public String getQuestObjective() {
        if(this.collectTarget == 1) {
            return StatCollector.translateToLocalFormatted("lotr.miniquest.retrieve1", this.collectItem.getDisplayName());
        }
        return StatCollector.translateToLocalFormatted("lotr.miniquest.retrieveMany", this.collectTarget, this.collectItem.getDisplayName());
    }

    @Override
    public String getProgressedObjectiveInSpeech() {
        if(this.collectTarget == 1) {
            return this.collectItem.getDisplayName();
        }
        return this.collectTarget + " " + this.collectItem.getDisplayName();
    }

    @Override
    protected boolean isQuestItem(ItemStack itemstack) {
        if(super.isQuestItem(itemstack)) {
            UUID retrieveQuestID = LOTRMiniQuestRetrieve.getRetrieveQuestID(itemstack);
            return retrieveQuestID.equals(this.questUUID);
        }
        return false;
    }

    @Override
    public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
        if(!this.hasDropped && this.killEntityType.isAssignableFrom(entity.getClass())) {
            ItemStack itemstack = this.collectItem.copy();
            LOTRMiniQuestRetrieve.setRetrieveQuest(itemstack, this);
            this.hasDropped = true;
            this.updateQuest();
        }
    }

    public static class QFRetrieve extends LOTRMiniQuestCollect.QFCollect<LOTRMiniQuestRetrieve> {
        private Class entityType;

        public QFRetrieve(String name) {
            super(name);
        }

        public QFRetrieve setKillEntity(Class entityClass) {
            this.entityType = entityClass;
            return this;
        }

        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestRetrieve.class;
        }

        @Override
        public LOTRMiniQuestRetrieve createQuest(LOTREntityNPC npc, Random rand) {
            LOTRMiniQuestRetrieve quest = super.createQuest(npc, rand);
            quest.killEntityType = this.entityType;
            return quest;
        }
    }

}
