package lotr.common.entity.npc;

import java.util.Random;

import lotr.common.*;
import lotr.common.block.LOTRBlockCorruptMallorn;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemEntDraught;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityEnt extends LOTREntityTree {
    private Random branchRand = new Random();
    public int eyesClosed;
    public ChunkCoordinates saplingHealTarget;
    public boolean canHealSapling = true;

    public LOTREntityEnt(World world) {
        super(world);
        this.setSize(1.4f, 4.6f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIEntHealSapling(this, 1.5));
        this.tasks.addTask(1, new LOTREntityAIAttackOnCollide(this, 2.0, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
        this.tasks.addTask(3, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 10.0f, 0.02f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.addTargetTasks(true);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) 0);
    }

    public boolean isHealingSapling() {
        return this.dataWatcher.getWatchableObjectByte(18) == 1;
    }

    public void setHealingSapling(boolean flag) {
        this.dataWatcher.updateObject(18, flag ? (byte) 1 : 0);
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getEntName(this.rand));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.FANGORN;
    }

    @Override
    public void setAttackTarget(EntityLivingBase target, boolean speak) {
        super.setAttackTarget(target, speak);
        if (this.getAttackTarget() == null) {
            this.canHealSapling = true;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if(this.saplingHealTarget != null) {
            nbt.setInteger("SaplingHealX", this.saplingHealTarget.posX);
            nbt.setInteger("SaplingHealY", this.saplingHealTarget.posY);
            nbt.setInteger("SaplingHealZ", this.saplingHealTarget.posZ);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("EntName")) {
            this.familyInfo.setName(nbt.getString("EntName"));
        }
        if(nbt.hasKey("SaplingHealX")) {
            int x = nbt.getInteger("SaplingHealX");
            int y = nbt.getInteger("SaplingHealY");
            int z = nbt.getInteger("SaplingHealZ");
            this.saplingHealTarget = new ChunkCoordinates(x, y, z);
        }
    }

    @Override
    public String getNPCName() {
        return this.familyInfo.getName();
    }

    public int getExtraHeadBranches() {
        long l = this.getUniqueID().getLeastSignificantBits();
        l = l * 365620672396L ^ l * 12784892284L ^ l;
        l = l * l * 18569660L + l * 6639092L;
        this.branchRand.setSeed(l);
        if(this.branchRand.nextBoolean()) {
            return 0;
        }
        return MathHelper.getRandomIntegerInRange(this.branchRand, 2, 5);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.worldObj.isRemote) {
            if(this.eyesClosed > 0) {
                --this.eyesClosed;
            }
            else if(this.rand.nextInt(400) == 0) {
                this.eyesClosed = 30;
            }
            if(this.isHealingSapling()) {
                for(int l = 0; l < 2; ++l) {
                    float angle = this.rotationYawHead + 90.0f + MathHelper.randomFloatClamp(this.rand, -40.0f, 40.0f);
                    angle = (float) Math.toRadians(angle);
                    double d = this.posX + MathHelper.cos(angle) * 1.5;
                    double d1 = this.boundingBox.minY + this.height * MathHelper.randomFloatClamp(this.rand, 0.3f, 0.6f);
                    double d2 = this.posZ + MathHelper.sin(angle) * 1.5;
                    double d3 = MathHelper.cos(angle) * 0.06;
                    double d4 = -0.03;
                    double d5 = MathHelper.sin(angle) * 0.06;
                    LOTRMod.proxy.spawnParticle("leafGold_30", d, d1, d2, d3, d4, d5);
                }
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            float knockbackModifier = 1.5f;
            entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, 0.15, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
            return true;
        }
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(!this.worldObj.isRemote && flag) {
            if(damagesource.getEntity() != null) {
                this.setHealingSapling(false);
            }
            if(this.getAttackTarget() != null) {
                this.canHealSapling = false;
            }
        }
        return flag;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer && this.saplingHealTarget != null) {
            int i = this.saplingHealTarget.posX;
            int j = this.saplingHealTarget.posY;
            int k = this.saplingHealTarget.posZ;
            Block block = this.worldObj.getBlock(i, j, k);
            int meta = this.worldObj.getBlockMetadata(i, j, k);
            if(block == LOTRMod.corruptMallorn) {
                if(++meta >= LOTRBlockCorruptMallorn.ENT_KILLS) {
                    LOTRBlockCorruptMallorn.summonEntBoss(this.worldObj, i, j, k);
                }
                else {
                    this.worldObj.setBlockMetadataWithNotify(i, j, k, meta, 3);
                }
            }
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killEnt;
    }

    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        if(flag) {
            int dropChance = 10 - i * 2;
            if(dropChance < 1) {
                dropChance = 1;
            }
            if(this.rand.nextInt(dropChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.entDraught, 1, this.rand.nextInt(LOTRItemEntDraught.draughtTypes.length)), 0.0f);
            }
        }
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 5 + this.rand.nextInt(6);
    }

    @Override
    protected float getSoundVolume() {
        return 1.5f;
    }

    @Override
    protected void func_145780_a(int i, int j, int k, Block block) {
        this.playSound("lotr:ent.step", 0.75f, this.getSoundPitch());
    }

    @Override
    protected LOTRAchievement getTalkAchievement() {
        return LOTRAchievement.talkEnt;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            return "ent/ent/friendly";
        }
        return "ent/ent/hostile";
    }
}
