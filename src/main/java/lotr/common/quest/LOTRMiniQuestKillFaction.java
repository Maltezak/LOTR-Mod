package lotr.common.quest;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRMiniQuestKillFaction extends LOTRMiniQuestKill {
    public LOTRFaction killFaction;

    public LOTRMiniQuestKillFaction(LOTRPlayerData pd) {
        super(pd);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("KillFaction", this.killFaction.codeName());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.killFaction = LOTRFaction.forName(nbt.getString("KillFaction"));
    }

    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.killFaction != null;
    }

    @Override
    protected String getKillTargetName() {
        return this.killFaction.factionEntityName();
    }

    @Override
    public void onKill(EntityPlayer entityplayer, EntityLivingBase entity) {
        if(this.killCount < this.killTarget && LOTRMod.getNPCFaction(entity) == this.killFaction) {
            ++this.killCount;
            this.updateQuest();
        }
    }

    public static class QFKillFaction extends LOTRMiniQuestKill.QFKill<LOTRMiniQuestKillFaction> {
        private LOTRFaction killFaction;

        public QFKillFaction(String name) {
            super(name);
        }

        public QFKillFaction setKillFaction(LOTRFaction faction, int min, int max) {
            this.killFaction = faction;
            this.setKillTarget(min, max);
            return this;
        }

        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestKillFaction.class;
        }

        @Override
        public LOTRMiniQuestKillFaction createQuest(LOTREntityNPC npc, Random rand) {
            LOTRMiniQuestKillFaction quest = super.createQuest(npc, rand);
            quest.killFaction = this.killFaction;
            return quest;
        }
    }

}
