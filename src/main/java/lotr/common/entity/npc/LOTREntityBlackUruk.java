package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBlackUruk extends LOTREntityMordorOrc {
    public LOTREntityBlackUruk(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.isWeakOrc = false;
        this.npcShield = LOTRShields.ALIGNMENT_BLACK_URUK;
    }

    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(7);
        if(i == 0 || i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.scimitarBlackUruk));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBlackUruk));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlackUruk));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlackUrukPoisoned));
        }
        else if(i == 6) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerBlackUruk));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBlackUruk));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsBlackUruk));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsBlackUruk));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBlackUruk));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetBlackUruk));
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBlackUruk;
    }

    @Override
    protected void dropOrcItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.BLACK_URUK_FORT, 1, 2 + i);
        }
        if(flag) {
            int shinyShirtChance = 6000;
            shinyShirtChance -= i * 500;
            if(this.rand.nextInt(shinyShirtChance = Math.max(shinyShirtChance, 1)) == 0) {
                this.dropItem(LOTRMod.bodyMithril, 1);
            }
        }
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }

    @Override
    public boolean canOrcSkirmish() {
        return false;
    }
}
