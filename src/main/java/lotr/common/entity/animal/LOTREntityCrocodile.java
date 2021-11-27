package lotr.common.entity.animal;

import java.util.List;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.biome.LOTRBiomeGenFarHaradSwamp;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityCrocodile extends EntityMob {
    private EntityAIBase targetAI;
    private boolean prevCanTarget = true;

    public LOTREntityCrocodile(World world) {
        super(world);
        this.setSize(2.1f, 0.7f);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 1.5, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
        this.targetAI = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);
        this.targetTasks.addTask(1, this.targetAI);
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, LOTREntityNPC.class, 400, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, 0);
    }

    public int getSnapTime() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void setSnapTime(int i) {
        this.dataWatcher.updateObject(20, i);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:crocodile.say";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:crocodile.death";
    }

    @Override
    public void onLivingUpdate() {
        EntityAnimal entityanimal;
        int i;
        List list;
        EntityLivingBase entity;
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.isInWater()) {
            this.motionY += 0.02;
        }
        if(!this.worldObj.isRemote && this.getAttackTarget() != null && (!(entity = this.getAttackTarget()).isEntityAlive() || entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
            this.setAttackTarget(null);
        }
        if(!this.worldObj.isRemote) {
            boolean canTarget;
            canTarget = this.getBrightness(1.0f) < 0.5f;
            if(canTarget != this.prevCanTarget) {
                if(canTarget) {
                    this.targetTasks.addTask(1, this.targetAI);
                }
                else {
                    this.targetTasks.removeTask(this.targetAI);
                }
            }
            this.prevCanTarget = canTarget;
        }
        if(!this.worldObj.isRemote && (i = this.getSnapTime()) > 0) {
            this.setSnapTime(i - 1);
        }
        if(this.getAttackTarget() == null && this.worldObj.rand.nextInt(1000) == 0 && !(list = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, this.boundingBox.expand(12.0, 6.0, 12.0))).isEmpty() && (entityanimal = (EntityAnimal) list.get(this.rand.nextInt(list.size()))).getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null) {
            this.setAttackTarget(entityanimal);
        }
    }

    @Override
    protected Item getDropItem() {
        return Items.rotten_flesh;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int count = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        block7: for(int j = 0; j < count; ++j) {
            int drop = this.rand.nextInt(5);
            switch(drop) {
                case 0: {
                    this.dropItem(Items.bone, 1);
                    continue block7;
                }
                case 1: {
                    this.dropItem(Items.fish, 1);
                    continue block7;
                }
                case 2: {
                    this.dropItem(Items.leather, 1);
                    continue block7;
                }
                case 3: {
                    this.dropItem(LOTRMod.zebraRaw, 1);
                    continue block7;
                }
                case 4: {
                    this.dropItem(LOTRMod.gemsbokHide, 1);
                }
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = super.attackEntityAsMob(entity);
        if(flag) {
            if(!this.worldObj.isRemote) {
                this.setSnapTime(20);
            }
            this.worldObj.playSoundAtEntity(this, "lotr:crocodile.snap", this.getSoundVolume(), this.getSoundPitch());
        }
        return flag;
    }

    @Override
    public boolean getCanSpawnHere() {
        List nearbyCrocodiles = this.worldObj.getEntitiesWithinAABB(this.getClass(), this.boundingBox.expand(24.0, 12.0, 24.0));
        if(nearbyCrocodiles.size() > 3) {
            return false;
        }
        if(this.worldObj.checkNoEntityCollision(this.boundingBox) && this.isValidLightLevel() && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0) {
            for(int i = -8; i <= 8; ++i) {
                for(int j = -8; j <= 8; ++j) {
                    for(int k = -8; k <= 8; ++k) {
                        int j1;
                        int k1;
                        int i1 = MathHelper.floor_double(this.posX) + i;
                        if(!this.worldObj.blockExists(i1, j1 = MathHelper.floor_double(this.posY) + j, k1 = MathHelper.floor_double(this.posZ) + k) || (this.worldObj.getBlock(i1, j1, k1)).getMaterial() != Material.water) continue;
                        if(this.posY > 60.0) {
                            return true;
                        }
                        if(this.rand.nextInt(50) != 0) continue;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isValidLightLevel() {
        int i = MathHelper.floor_double(this.posX);
        if(this.worldObj.getBiomeGenForCoords(i, MathHelper.floor_double(this.posZ)) instanceof LOTRBiomeGenFarHaradSwamp) {
            return true;
        }
        return super.isValidLightLevel();
    }

    @Override
    public void moveEntityWithHeading(float f, float f1) {
        if(!this.worldObj.isRemote && this.isInWater() && this.getAttackTarget() != null) {
            this.moveFlying(f, f1, 0.1f);
        }
        super.moveEntityWithHeading(f, f1);
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }
}
