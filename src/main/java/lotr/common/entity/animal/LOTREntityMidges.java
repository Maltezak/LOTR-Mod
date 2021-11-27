package lotr.common.entity.animal;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.world.biome.LOTRBiomeGenMidgewater;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityMidges extends EntityLiving implements LOTRAmbientCreature {
    private ChunkCoordinates currentFlightTarget;
    private EntityPlayer playerTarget;
    public Midge[] midges;

    public LOTREntityMidges(World world) {
        super(world);
        this.setSize(2.0f, 2.0f);
        this.renderDistanceWeight = 0.5;
        this.midges = new Midge[3 + this.rand.nextInt(6)];
        for(int l = 0; l < this.midges.length; ++l) {
            this.midges[l] = new Midge();
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entity) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6;
        for(Midge midge : this.midges) {
            midge.update();
        }
        if(this.rand.nextInt(5) == 0) {
            this.playSound("lotr:midges.swarm", this.getSoundVolume(), this.getSoundPitch());
        }
        if(!this.worldObj.isRemote && this.isEntityAlive()) {
            int chance;
            boolean inMidgewater = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ)) instanceof LOTRBiomeGenMidgewater;
            chance = inMidgewater ? 100 : 500;
            if(this.rand.nextInt(chance) == 0) {
                double range = inMidgewater ? 16.0 : 24.0;
                int threshold = inMidgewater ? 6 : 5;
                List list = this.worldObj.getEntitiesWithinAABB(LOTREntityMidges.class, this.boundingBox.expand(range, range, range));
                if(list.size() < threshold) {
                    LOTREntityMidges moreMidges = new LOTREntityMidges(this.worldObj);
                    moreMidges.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rand.nextFloat() * 360.0f, 0.0f);
                    moreMidges.onSpawnWithEgg(null);
                    this.worldObj.spawnEntityInWorld(moreMidges);
                }
            }
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if(this.currentFlightTarget != null && !this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ)) {
            this.currentFlightTarget = null;
        }
        if(this.playerTarget != null && (!this.playerTarget.isEntityAlive() || this.getDistanceSqToEntity(this.playerTarget) > 256.0)) {
            this.playerTarget = null;
        }
        if(this.playerTarget != null) {
            if(this.rand.nextInt(400) == 0) {
                this.playerTarget = null;
            }
            else {
                this.currentFlightTarget = new ChunkCoordinates((int) this.playerTarget.posX, (int) this.playerTarget.posY + 3, (int) this.playerTarget.posZ);
            }
        }
        else if(this.rand.nextInt(100) == 0) {
            EntityPlayer closestPlayer = this.worldObj.getClosestPlayerToEntity(this, 12.0);
            if(closestPlayer != null && this.rand.nextInt(7) == 0) {
                this.playerTarget = closestPlayer;
            }
            else {
                int height;
                int i = (int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7);
                int j = (int) this.posY + this.rand.nextInt(4) - this.rand.nextInt(3);
                int k = (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7);
                if(j < 1) {
                    j = 1;
                }
                if(j > (height = this.worldObj.getTopSolidOrLiquidBlock(i, k)) + 8) {
                    j = height + 8;
                }
                this.currentFlightTarget = new ChunkCoordinates(i, j, k);
            }
        }
        if(this.currentFlightTarget != null) {
            double dx = this.currentFlightTarget.posX + 0.5 - this.posX;
            double dy = this.currentFlightTarget.posY + 0.5 - this.posY;
            double dz = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX += (Math.signum(dx) * 0.5 - this.motionX) * 0.1;
            this.motionY += (Math.signum(dy) * 0.7 - this.motionY) * 0.1;
            this.motionZ += (Math.signum(dz) * 0.5 - this.motionZ) * 0.1;
            this.moveForward = 0.2f;
        }
        else {
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
        }
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    protected void fall(float f) {
    }

    @Override
    protected void updateFallState(double d, boolean flag) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        Entity attacker;
        super.onDeath(damagesource);
        if(!this.worldObj.isRemote && damagesource instanceof EntityDamageSourceIndirect && (attacker = damagesource.getEntity()) instanceof LOTREntityNPC) {
            LOTREntityNPC npc = (LOTREntityNPC) attacker;
            if(npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
                EntityPlayer entityplayer = npc.hiredNPCInfo.getHiringPlayer();
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.shootDownMidges);
            }
        }
    }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        if(j < 62) {
            return false;
        }
        return this.worldObj.getBlock(i, j - 1, k) == this.worldObj.getBiomeGenForCoords(i, k).topBlock && super.getCanSpawnHere();
    }

    @Override
    public boolean allowLeashing() {
        return false;
    }

    @Override
    protected boolean interact(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        int id = LOTREntities.getEntityID(this);
        if(id > 0 && LOTREntities.spawnEggs.containsKey(id)) {
            return new ItemStack(LOTRMod.spawnEgg, 1, id);
        }
        return null;
    }

    public class Midge {
        public float midge_posX;
        public float midge_posY;
        public float midge_posZ;
        public float midge_prevPosX;
        public float midge_prevPosY;
        public float midge_prevPosZ;
        private float midge_initialPosX;
        private float midge_initialPosY;
        private float midge_initialPosZ;
        public float midge_rotation;
        private int midgeTick;
        private int maxMidgeTick = 80;

        public Midge() {
            this.midge_initialPosX = this.midge_posX = -1.0f + LOTREntityMidges.this.rand.nextFloat() * 2.0f;
            this.midge_initialPosY = this.midge_posY = LOTREntityMidges.this.rand.nextFloat() * 2.0f;
            this.midge_initialPosZ = this.midge_posZ = -1.0f + LOTREntityMidges.this.rand.nextFloat() * 2.0f;
            this.midge_rotation = LOTREntityMidges.this.rand.nextFloat() * 360.0f;
            this.midgeTick = LOTREntityMidges.this.rand.nextInt(this.maxMidgeTick);
        }

        public void update() {
            this.midge_prevPosX = this.midge_posX;
            this.midge_prevPosY = this.midge_posY;
            this.midge_prevPosZ = this.midge_posZ;
            ++this.midgeTick;
            if(this.midgeTick > this.maxMidgeTick) {
                this.midgeTick = 0;
            }
            this.midge_posY = this.midge_initialPosY + 0.5f * MathHelper.sin(this.midgeTick / 6.2831855f);
        }
    }

}
