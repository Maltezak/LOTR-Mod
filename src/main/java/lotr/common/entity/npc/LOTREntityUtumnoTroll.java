package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LOTREntityUtumnoTroll extends LOTREntityTroll {
    public LOTREntityUtumnoTroll(World world) {
        super(world);
    }

    @Override
    public float getTrollScale() {
        return 1.5f;
    }

    @Override
    public EntityAIBase getTrollAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 2.0, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }

    @Override
    protected boolean hasTrollName() {
        return false;
    }

    @Override
    protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public void onTrollDeathBySun() {
        this.worldObj.playSoundAtEntity(this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        this.worldObj.setEntityState(this, (byte) 15);
        this.setDead();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 15) {
            super.handleHealthUpdate(b);
            for(int l = 0; l < 64; ++l) {
                LOTRMod.proxy.spawnParticle("largeStone", this.posX + this.rand.nextGaussian() * this.width * 0.5, this.posY + this.rand.nextDouble() * this.height, this.posZ + this.rand.nextGaussian() * this.width * 0.5, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUtumnoTroll;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 5 + this.rand.nextInt(6);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return null;
    }
}
