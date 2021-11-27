package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class LOTREntityUrukHai extends LOTREntityOrc {
    public LOTREntityUrukHai(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.isWeakOrc = false;
        this.npcShield = LOTRShields.ALIGNMENT_URUK_HAI;
    }

    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(8);
        if(i == 0 || i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarUruk));
        }
        else if(i == 2 || i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeUruk));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUruk));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUruk));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUrukPoisoned));
        }
        else if(i == 7) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUruk));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUruk));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUruk));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUruk));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUruk));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUruk));
        }
        return data;
    }

    @Override
    protected void dropOrcItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.URUK_TENT, 1, 2 + i);
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
            double range = 12.0;
            List nearbySpawners = this.worldObj.getEntitiesWithinAABB(LOTREntityNPCRespawner.class, this.boundingBox.expand(range, range, range));
            for(Object obj : nearbySpawners) {
                LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner) obj;
                if(spawner.spawnClass1 == null || !LOTREntityUrukHai.class.isAssignableFrom(spawner.spawnClass1)) continue;
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.raidUrukCamp);
                break;
            }
        }
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ISENGARD;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUrukHai;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }

    @Override
    public boolean canOrcSkirmish() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "isengard/orc/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
                return "isengard/orc/friendly";
            }
            return "isengard/orc/neutral";
        }
        return "isengard/orc/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ISENGARD.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.ISENGARD;
    }
}
