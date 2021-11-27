package lotr.common.entity.animal;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityRabbit extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity {
    private static final String fleeSound = "lotr:rabbit.flee";

    public LOTREntityRabbit(World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIFlee(this, 2.0));
        this.tasks.addTask(2, new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 4.0f, 1.3, 1.5, 0.05f, fleeSound));
        this.tasks.addTask(2, new LOTREntityAIAvoidWithChance(this, LOTREntityNPC.class, 4.0f, 1.3, 1.5, 0.05f, fleeSound));
        this.tasks.addTask(3, new LOTREntityAIRabbitEatCrops(this, 1.2));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0f, 0.05f));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public boolean isRabbitEating() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setRabbitEating(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && !this.worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && this.isRabbitEating()) {
            EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.attackRabbit);
        }
        return flag;
    }

    @Override
    public void dropFewItems(boolean flag, int i) {
        int meat = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < meat; ++l) {
            if(this.isBurning()) {
                this.dropItem(LOTRMod.rabbitCooked, 1);
                continue;
            }
            this.dropItem(LOTRMod.rabbitRaw, 1);
        }
    }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        if(super.getCanSpawnHere()) {
            boolean flag = LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
            if(flag) {
                int i = MathHelper.floor_double(this.posX);
                return !this.anyFarmhandsNearby(i, MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            }
        }
        return false;
    }

    public boolean anyFarmhandsNearby(int i, int j, int k) {
        int range = 16;
        List farmhands = this.worldObj.getEntitiesWithinAABB(LOTRFarmhand.class, AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1).expand(range, range, range));
        return !farmhands.isEmpty();
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        Block block = this.worldObj.getBlock(i, j - 1, k);
        if(block == Blocks.grass) {
            return 10.0f;
        }
        return this.worldObj.getLightBrightness(i, j, k) - 0.5f;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 1 + this.rand.nextInt(2);
    }

    @Override
    protected String getHurtSound() {
        return "lotr:rabbit.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:rabbit.death";
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }
}
