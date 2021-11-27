package lotr.common.entity.npc;

import java.util.UUID;

import lotr.common.LOTRAchievement;
import lotr.common.enchant.*;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityMarshWraithBall;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityMarshWraith extends LOTREntityNPC {
    public UUID attackTargetUUID;
    private boolean checkedForAttackTarget;
    private int timeUntilDespawn = -1;
    public LOTREntityMarshWraith(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.tasks.addTask(0, new LOTREntityAIRangedAttack(this, 1.6, 40, 12.0f));
        this.tasks.addTask(1, new EntityAIWander(this, 1.0));
        this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    public int getSpawnFadeTime() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public void setSpawnFadeTime(int i) {
        this.dataWatcher.updateObject(16, i);
    }

    public int getDeathFadeTime() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public void setDeathFadeTime(int i) {
        this.dataWatcher.updateObject(17, i);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HOSTILE;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("SpawnFadeTime", this.getSpawnFadeTime());
        nbt.setInteger("DeathFadeTime", this.getDeathFadeTime());
        if(this.attackTargetUUID != null) {
            nbt.setLong("TargetUUIDMost", this.attackTargetUUID.getMostSignificantBits());
            nbt.setLong("TargetUUIDLeast", this.attackTargetUUID.getLeastSignificantBits());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSpawnFadeTime(nbt.getInteger("SpawnFadeTime"));
        this.setDeathFadeTime(nbt.getInteger("DeathFadeTime"));
        if(nbt.hasKey("TargetUUIDMost") && nbt.hasKey("TargetUUIDLeast")) {
            this.attackTargetUUID = new UUID(nbt.getLong("TargetUUIDMost"), nbt.getLong("TargetUUIDLeast"));
        }
    }

    @Override
    public void setInWeb() {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && !this.isDead) {
            int hover = 2;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY);
            int k = MathHelper.floor_double(this.posZ);
            double newY = this.posY;
            for(int j1 = 0; j1 <= hover; ++j1) {
                int j2 = j - j1;
                Block block = this.worldObj.getBlock(i, j2, k);
                Material material = block.getMaterial();
                if(!material.isSolid() && !material.isLiquid()) continue;
                newY = Math.max(newY, j + j1 + 1);
            }
            this.motionY += (newY - this.posY) * 0.04;
        }
        if(this.rand.nextBoolean()) {
            this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
        }
        if(!this.worldObj.isRemote) {
            int i;
            if(this.getAttackTarget() == null && this.attackTargetUUID != null && !this.checkedForAttackTarget) {
                for(i = 0; i < this.worldObj.loadedEntityList.size(); ++i) {
                    Entity entity = (Entity) this.worldObj.loadedEntityList.get(i);
                    if(!(entity instanceof EntityLiving) || !entity.getUniqueID().equals(this.attackTargetUUID)) continue;
                    this.setAttackTarget((EntityLiving) entity);
                    break;
                }
                this.checkedForAttackTarget = true;
            }
            if(this.getSpawnFadeTime() < 30) {
                this.setSpawnFadeTime(this.getSpawnFadeTime() + 1);
            }
            if(this.getDeathFadeTime() > 0) {
                this.setDeathFadeTime(this.getDeathFadeTime() - 1);
            }
            if(this.getSpawnFadeTime() == 30 && this.getDeathFadeTime() == 0) {
                if(this.getAttackTarget() == null || this.getAttackTarget().isDead) {
                    this.setDeathFadeTime(30);
                }
                else {
                    int k;
                    int j;
                    if(this.timeUntilDespawn == -1) {
                        this.timeUntilDespawn = 100;
                    }
                    if(this.worldObj.getBlock(i = MathHelper.floor_double(this.getAttackTarget().posX), j = MathHelper.floor_double(this.getAttackTarget().boundingBox.minY), k = MathHelper.floor_double(this.getAttackTarget().posZ)).getMaterial() == Material.water || this.worldObj.getBlock(i, j - 1, k).getMaterial() == Material.water) {
                        this.timeUntilDespawn = 100;
                    }
                    else if(this.timeUntilDespawn > 0) {
                        --this.timeUntilDespawn;
                    }
                    else {
                        this.setDeathFadeTime(30);
                        this.setAttackTarget(null);
                    }
                }
            }
            if(this.getDeathFadeTime() == 1) {
                this.setDead();
            }
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        if(this.getSpawnFadeTime() == 30 && this.getDeathFadeTime() == 0) {
            LOTREntityMarshWraithBall ball = new LOTREntityMarshWraithBall(this.worldObj, this, target);
            this.playSound("lotr:wraith.marshWraith_shoot", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
            this.worldObj.spawnEntityInWorld(ball);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        ItemStack itemstack;
        boolean vulnerable = false;
        Entity entity = damagesource.getEntity();
        if(entity instanceof EntityLivingBase && entity == damagesource.getSourceOfDamage() && (itemstack = ((EntityLivingBase) entity).getHeldItem()) != null && LOTREnchantmentHelper.hasEnchant(itemstack, LOTREnchantment.baneWraith)) {
            vulnerable = true;
        }
        if(vulnerable && this.getDeathFadeTime() == 0) {
            boolean flag = super.attackEntityFrom(damagesource, f);
            if(flag) {
                this.timeUntilDespawn = 100;
            }
            return flag;
        }
        return false;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote) {
            this.setDeathFadeTime(30);
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int flesh = 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        for(int l = 0; l < flesh; ++l) {
            this.dropItem(Items.rotten_flesh, 1);
        }
        this.dropChestContents(LOTRChestContents.MARSH_REMAINS, 1, 3 + i);
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMarshWraith;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected String getHurtSound() {
        return "lotr:wight.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:wight.death";
    }

    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    @Override
    protected void func_145780_a(int i, int j, int k, Block block) {
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        return false;
    }
}
