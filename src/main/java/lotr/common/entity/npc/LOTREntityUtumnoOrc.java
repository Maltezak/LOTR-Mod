package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUtumnoOrc extends LOTREntityOrc {
    public LOTREntityUtumnoOrc(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.isWeakOrc = false;
    }

    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, true);
    }

    @Override
    public void setupNPCName() {
        if(this.rand.nextInt(5) == 0) {
            this.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(this.rand, this.rand.nextBoolean()));
        }
        else {
            this.familyInfo.setName(LOTRNames.getOrcName(this.rand));
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(6);
        if(i == 0 || i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
        }
        else if(i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
        }
        else if(i == 5) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
        }
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearUtumno));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUtumno));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUtumno));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUtumno));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUtumno));
        }
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUtumnoOrc;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.65f;
    }

    @Override
    public boolean canOrcSkirmish() {
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return "utumno/orc/hostile";
    }
}
