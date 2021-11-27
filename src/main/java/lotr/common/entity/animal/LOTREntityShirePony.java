package lotr.common.entity.animal;

import lotr.common.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityShirePony
extends LOTREntityHorse {
    public static float PONY_SCALE = 0.8f;
    public boolean breedingFlag = false;

    public LOTREntityShirePony(World world) {
        super(world);
        this.setSize(this.width * PONY_SCALE, this.height * PONY_SCALE);
    }

    public int getHorseType() {
        if (this.breedingFlag) {
            return 0;
        }
        return this.worldObj.isRemote ? 0 : 1;
    }

    public boolean func_110259_cr() {
        return false;
    }

    @Override
    protected void onLOTRHorseSpawn() {
        double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth *= 0.75);
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength *= 0.5);
        double moveSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed *= 0.8);
    }

    @Override
    protected double clampChildHealth(double health) {
        return MathHelper.clamp_double(health, 10.0, 28.0);
    }

    @Override
    protected double clampChildJump(double jump) {
        return MathHelper.clamp_double(jump, 0.2, 1.0);
    }

    @Override
    protected double clampChildSpeed(double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.3);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable other) {
        LOTREntityShirePony otherPony = (LOTREntityShirePony)other;
        this.breedingFlag = true;
        otherPony.breedingFlag = true;
        EntityAgeable child = super.createChild(otherPony);
        this.breedingFlag = false;
        otherPony.breedingFlag = false;
        return child;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer)this.riddenByEntity;
            if (this.isHorseSaddled() && this.isChested()) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideShirePony);
            }
        }
    }

    protected String getLivingSound() {
        super.getLivingSound();
        return "mob.horse.idle";
    }

    protected String getHurtSound() {
        super.getHurtSound();
        return "mob.horse.hit";
    }

    protected String getDeathSound() {
        super.getDeathSound();
        return "mob.horse.death";
    }

    protected String getAngrySoundName() {
        super.getAngrySoundName();
        return "mob.horse.angry";
    }
}

