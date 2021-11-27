package lotr.common.quest;

import java.util.Random;

import lotr.common.LOTRPlayerData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;

public abstract class LOTRMiniQuestKill extends LOTRMiniQuest {
    public int killTarget;
    public int killCount;

    public LOTRMiniQuestKill(LOTRPlayerData pd) {
        super(pd);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Target", this.killTarget);
        nbt.setInteger("Count", this.killCount);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.killTarget = nbt.getInteger("Target");
        this.killCount = nbt.getInteger("Count");
    }

    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.killTarget > 0;
    }

    @Override
    public String getQuestObjective() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.kill", this.killTarget, this.getKillTargetName());
    }

    protected abstract String getKillTargetName();

    @Override
    public String getObjectiveInSpeech() {
        return this.killTarget + " " + this.getKillTargetName();
    }

    @Override
    public String getProgressedObjectiveInSpeech() {
        return this.killTarget - this.killCount + " " + this.getKillTargetName();
    }

    @Override
    public String getQuestProgress() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.kill.progress", this.killCount, this.killTarget);
    }

    @Override
    public String getQuestProgressShorthand() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", this.killCount, this.killTarget);
    }

    @Override
    public float getCompletionFactor() {
        return (float) this.killCount / (float) this.killTarget;
    }

    @Override
    public ItemStack getQuestIcon() {
        return new ItemStack(Items.iron_sword);
    }

    @Override
    public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
        if(this.killCount >= this.killTarget) {
            this.complete(entityplayer, npc);
        }
        else {
            this.sendProgressSpeechbank(entityplayer, npc);
        }
    }

    @Override
    public float getAlignmentBonus() {
        return this.killTarget * this.rewardFactor;
    }

    @Override
    public int getCoinBonus() {
        return Math.round(this.killTarget * 2.0f * this.rewardFactor);
    }

    public static abstract class QFKill<Q extends LOTRMiniQuestKill> extends LOTRMiniQuest.QuestFactoryBase<Q> {
        private int minTarget;
        private int maxTarget;

        public QFKill(String name) {
            super(name);
        }

        public QFKill setKillTarget(int min, int max) {
            this.minTarget = min;
            this.maxTarget = max;
            return this;
        }

        @Override
        public Q createQuest(LOTREntityNPC npc, Random rand) {
            LOTRMiniQuestKill quest = super.createQuest(npc, rand);
            quest.killTarget = MathHelper.getRandomIntegerInRange(rand, this.minTarget, this.maxTarget);
            return (Q) quest;
        }
    }

}
