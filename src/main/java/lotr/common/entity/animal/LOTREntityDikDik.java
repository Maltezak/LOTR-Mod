package lotr.common.entity.animal;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.*;
import lotr.common.entity.ai.LOTREntityAIAvoidWithChance;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntityDikDik extends EntityCreature implements LOTRAmbientCreature, LOTRRandomSkinEntity {
    public LOTREntityDikDik(World world) {
        super(world);
        this.setSize(0.6f, 1.0f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, LOTREntityLionBase.class, 12.0f, 1.5, 2.0));
        this.tasks.addTask(1, new LOTREntityAIAvoidWithChance(this, EntityPlayer.class, 12.0f, 1.5, 2.0, 0.1f));
        this.tasks.addTask(2, new EntityAIPanic(this, 2.0));
        this.tasks.addTask(3, new EntityAIWander(this, 1.2));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        if(super.getCanSpawnHere()) {
            return LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
        }
        return false;
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
    public int getTalkInterval() {
        return 300;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:deer.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:deer.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:deer.death";
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 1.3f;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }
}
