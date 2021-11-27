package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.client.LOTRTickHandlerClient;
import lotr.common.entity.ai.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class LOTREntityHuornBase extends LOTREntityTree {
    public boolean ignoringFrustumForRender = false;

    public LOTREntityHuornBase(World world) {
        super(world);
        this.setSize(1.5f, 4.0f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 1.5, false));
        this.tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public boolean isHuornActive() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setHuornActive(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(4.0);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean isInRangeToRender3d(double d, double d1, double d2) {
        EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
        float f = LOTRTickHandlerClient.renderTick;
        double viewX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * f;
        double viewY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * f;
        double viewZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * f;
        Frustrum camera = new Frustrum();
        camera.setPosition(viewX, viewY, viewZ);
        AxisAlignedBB expandedBB = this.boundingBox.expand(2.0, 3.0, 2.0);
        if(camera.isBoundingBoxInFrustum(expandedBB)) {
            this.ignoringFrustumForRender = true;
            this.ignoreFrustumCheck = true;
        }
        return super.isInRangeToRender3d(d, d1, d2);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote) {
            boolean active = !this.getNavigator().noPath() || this.getAttackTarget() != null && this.getAttackTarget().isEntityAlive();
            this.setHuornActive(active);
        }
    }

    @Override
    protected int decreaseAirSupply(int i) {
        return i;
    }

    @Override
    public void applyEntityCollision(Entity entity) {
        if(this.isHuornActive()) {
            super.applyEntityCollision(entity);
        }
        else {
            double x = this.motionX;
            double y = this.motionY;
            double z = this.motionZ;
            super.applyEntityCollision(entity);
            this.motionX = x;
            this.motionY = y;
            this.motionZ = z;
        }
    }

    @Override
    public void collideWithEntity(Entity entity) {
        if(this.isHuornActive()) {
            super.collideWithEntity(entity);
        }
        else {
            double x = this.motionX;
            double y = this.motionY;
            double z = this.motionZ;
            super.collideWithEntity(entity);
            this.motionX = x;
            this.motionY = y;
            this.motionZ = z;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && !this.worldObj.isRemote && !this.isHuornActive()) {
            this.setHuornActive(true);
        }
        return flag;
    }

    @Override
    protected String getHurtSound() {
        return Blocks.log.stepSound.getBreakSound();
    }

    @Override
    protected String getDeathSound() {
        return Blocks.log.stepSound.getBreakSound();
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
}
