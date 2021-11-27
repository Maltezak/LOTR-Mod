package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.quest.*;
import net.minecraft.entity.*;
import net.minecraft.world.World;

public class LOTREntityRuffianBrute
extends LOTREntityBreeRuffian {
    public LOTREntityRuffianBrute(World world) {
        super(world);
    }

    @Override
    protected int addBreeAttackAI(int prio) {
        this.tasks.addTask(prio, new LOTREntityAIAttackOnCollide(this, 1.5, false));
        return prio;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killRuffianBrute;
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.RUFFIAN_BRUTE.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.RUFFIAN_BRUTE;
    }
}

