package lotr.common.entity.animal;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockTorch;
import lotr.common.entity.*;
import lotr.common.world.biome.*;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTREntityButterfly extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity {
    private LOTRBlockTorch elfTorchBlock;
    private ChunkCoordinates currentFlightTarget;
    public int flapTime = 0;

    public LOTREntityButterfly(World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public ButterflyType getButterflyType() {
        byte i = this.dataWatcher.getWatchableObjectByte(16);
        if(i < 0 || i >= ButterflyType.values().length) {
            i = 0;
        }
        return ButterflyType.values()[i];
    }

    public void setButterflyType(ButterflyType type) {
        this.setButterflyType(type.ordinal());
    }

    public void setButterflyType(int i) {
        this.dataWatcher.updateObject(16, (byte) i);
    }

    public boolean isButterflyStill() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setButterflyStill(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(this.rand, 0.08, 0.12));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = MathHelper.floor_double(this.posX);
        MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenMirkwood || biome instanceof LOTRBiomeGenWoodlandRealm) {
            this.setButterflyType(ButterflyType.MIRKWOOD);
        }
        else if(biome instanceof LOTRBiomeGenLothlorien) {
            this.setButterflyType(ButterflyType.LORIEN);
        }
        else if(biome instanceof LOTRBiomeGenFarHaradJungle) {
            this.setButterflyType(ButterflyType.JUNGLE);
        }
        else {
            this.setButterflyType(ButterflyType.COMMON);
        }
        return data;
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
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
        if(this.isButterflyStill()) {
            this.motionZ = 0.0;
            this.motionY = 0.0;
            this.motionX = 0.0;
            this.posY = MathHelper.floor_double(this.posY);
            if(this.worldObj.isRemote) {
                if(this.rand.nextInt(200) == 0) {
                    this.flapTime = 40;
                }
                if(this.flapTime > 0) {
                    --this.flapTime;
                }
            }
        }
        else {
            this.motionY *= 0.6;
            if(this.worldObj.isRemote) {
                this.flapTime = 0;
            }
            if(this.getButterflyType() == ButterflyType.LORIEN) {
                LOTRBlockTorch.TorchParticle particle;
                double d = this.posX;
                double d1 = this.posY;
                double d2 = this.posZ;
                if(this.elfTorchBlock == null) {
                    Random torchRand = new Random();
                    torchRand.setSeed(this.entityUniqueID.getLeastSignificantBits());
                    this.elfTorchBlock = (LOTRBlockTorch) LOTRWorldGenElfHouse.getRandomTorch(torchRand);
                }
                if((particle = this.elfTorchBlock.createTorchParticle(this.rand)) != null) {
                    particle.spawn(d, d1, d2);
                }
            }
        }
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if(this.isButterflyStill()) {
            int k;
            int j;
            int i = MathHelper.floor_double(this.posX);
            if(!this.worldObj.getBlock(i, j = (int) this.posY - 1, k = MathHelper.floor_double(this.posZ)).isSideSolid(this.worldObj, i, j, k, ForgeDirection.UP)) {
                this.setButterflyStill(false);
            }
            else if(this.rand.nextInt(400) == 0 || this.worldObj.getClosestPlayerToEntity(this, 3.0) != null) {
                this.setButterflyStill(false);
            }
        }
        else {
            if(((this.currentFlightTarget != null) && (!this.worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || (this.currentFlightTarget.posY < 1)))) {
                this.currentFlightTarget = null;
            }
            if(this.currentFlightTarget == null || this.rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0f) {
                this.currentFlightTarget = new ChunkCoordinates((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
            }
            double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            double d0 = this.currentFlightTarget.posX + 0.5 - this.posX;
            double d1 = this.currentFlightTarget.posY + 0.5 - this.posY;
            double d2 = this.currentFlightTarget.posZ + 0.5 - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5 - this.motionX) * speed;
            this.motionY += (Math.signum(d1) * 0.7 - this.motionY) * speed;
            this.motionZ += (Math.signum(d2) * 0.5 - this.motionZ) * speed;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
            float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += f1;
            if(this.rand.nextInt(150) == 0 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), (int) this.posY - 1, MathHelper.floor_double(this.posZ)).isNormalCube()) {
                this.setButterflyStill(true);
            }
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
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        boolean flag = super.attackEntityFrom(damagesource, f);
        if(flag && !this.worldObj.isRemote && this.isButterflyStill()) {
            this.setButterflyStill(false);
        }
        return flag;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setButterflyType(nbt.getInteger("ButterflyType"));
        this.setButterflyStill(nbt.getBoolean("ButterflyStill"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("ButterflyType", this.getButterflyType().ordinal());
        nbt.setBoolean("ButterflyStill", this.isButterflyStill());
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
    public boolean allowLeashing() {
        return false;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }

    public enum ButterflyType {
        MIRKWOOD("mirkwood"), LORIEN("lorien"), COMMON("common"), JUNGLE("jungle");

        public final String textureDir;

        ButterflyType(String s) {
            this.textureDir = s;
        }
    }

}
