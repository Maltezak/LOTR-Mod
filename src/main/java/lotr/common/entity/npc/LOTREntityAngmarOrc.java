package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarOrc extends LOTREntityOrc {
    public LOTREntityAngmarOrc(World world) {
        super(world);
    }

    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(10);
        if(i == 0 || i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordAngmar));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeAngmar));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmar));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerAngmarPoisoned));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerAngmar));
        }
        else if(i == 7) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pickaxeAngmar));
        }
        else if(i == 8) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.axeAngmar));
        }
        else if(i == 9) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmAngmar));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearAngmar));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsAngmar));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsAngmar));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyAngmar));
        if(this.rand.nextInt(5) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetAngmar));
        }
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killAngmarOrc;
    }

    @Override
    protected void dropOrcItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.ANGMAR_TENT, 1, 2 + i);
        }
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "angmar/orc/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
                return "angmar/orc/friendly";
            }
            return "angmar/orc/neutral";
        }
        return "angmar/orc/hostile";
    }

    @Override
    protected String getOrcSkirmishSpeech() {
        return "angmar/orc/skirmish";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ANGMAR.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.ANGMAR;
    }
}
