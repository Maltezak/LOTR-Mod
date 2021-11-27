package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.projectile.LOTREntityTrollSnowball;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.potion.*;
import net.minecraft.world.World;

public class LOTREntitySnowTroll extends LOTREntityTroll {
    private EntityAIBase rangedAttackAI = this.getTrollRangedAttackAI();
    private EntityAIBase meleeAttackAI;

    public LOTREntitySnowTroll(World world) {
        super(world);
        this.isImmuneToFrost = true;
    }

    @Override
    public float getTrollScale() {
        return 0.8f;
    }

    @Override
    public EntityAIBase getTrollAttackAI() {
        this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, false);
        return this.meleeAttackAI;
    }

    protected EntityAIBase getTrollRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.2, 20, 30, 24.0f);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(21, (byte) 0);
    }

    public boolean isThrowingSnow() {
        return this.dataWatcher.getWatchableObjectByte(21) == 1;
    }

    public void setThrowingSnow(boolean flag) {
        this.dataWatcher.updateObject(21, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
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
    public double getMeleeRange() {
        return 12.0;
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setThrowingSnow(false);
        }
        if(mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(3, this.meleeAttackAI);
            this.setThrowingSnow(false);
        }
        if(mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(3, this.rangedAttackAI);
            this.setThrowingSnow(true);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            int difficulty;
            int duration;
            if(entity instanceof EntityLivingBase && (duration = (difficulty = this.worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
            }
            return true;
        }
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        EntityArrow template = new EntityArrow(this.worldObj, this, target, f * 1.6f, 0.5f);
        LOTREntityTrollSnowball snowball = new LOTREntityTrollSnowball(this.worldObj, this);
        snowball.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
        snowball.motionX = template.motionX;
        snowball.motionY = template.motionY;
        snowball.motionZ = template.motionZ;
        this.worldObj.spawnEntityInWorld(snowball);
        this.playSound("random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
        this.swingItem();
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
                this.worldObj.spawnParticle("snowballpoof", this.posX + this.rand.nextGaussian() * this.width * 0.5, this.posY + this.rand.nextDouble() * this.height, this.posZ + this.rand.nextGaussian() * this.width * 0.5, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int furs = 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int l = 0; l < furs; ++l) {
            this.dropItem(LOTRMod.fur, 1);
        }
        int snows = 2 + this.rand.nextInt(4) + this.rand.nextInt(i * 2 + 1);
        for(int l = 0; l < snows; ++l) {
            this.dropItem(Items.snowball, 1);
        }
    }

    @Override
    public void dropTrollItems(boolean flag, int i) {
        if(this.rand.nextBoolean()) {
            super.dropTrollItems(flag, i);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killSnowTroll;
    }

    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return null;
    }
}
