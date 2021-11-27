package lotr.common.entity.animal;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityFish extends EntityWaterMob implements LOTRRandomSkinEntity {
    private ChunkCoordinates currentSwimTarget;
    private int swimTargetTime = 0;
    public LOTREntityFish(World world) {
        super(world);
        this.setSize(0.5f, 0.5f);
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
    }

    public FishType getFishType() {
        byte i = this.dataWatcher.getWatchableObjectByte(16);
        if(i < 0 || i >= FishType.values().length) {
            i = 0;
        }
        return FishType.values()[i];
    }

    public void setFishType(FishType type) {
        this.setFishType(type.ordinal());
    }

    public void setFishType(int i) {
        this.dataWatcher.updateObject(16, (byte) i);
    }

    public String getFishTextureDir() {
        return this.getFishType().textureDir;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(this.rand, 0.04, 0.08));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = MathHelper.floor_double(this.posX);
        MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        this.worldObj.getBiomeGenForCoords(i, k);
        if(this.rand.nextInt(30) == 0) {
            this.setFishType(FishType.CLOWNFISH);
        }
        else if(this.rand.nextInt(8) == 0) {
            this.setFishType(FishType.SALMON);
        }
        else {
            this.setFishType(FishType.COMMON);
        }
        return data;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setFishType(nbt.getInteger("FishType"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("FishType", this.getFishType().ordinal());
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int drops = this.rand.nextInt(2 + i);
        for(int l = 0; l < drops; ++l) {
            if(this.getFishType() == FishType.SALMON) {
                this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 0.0f);
                continue;
            }
            if(this.getFishType() == FishType.CLOWNFISH) {
                this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 0.0f);
                continue;
            }
            this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 0.0f);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.isInWater() && !this.worldObj.isRemote) {
            this.motionX = 0.0;
            this.motionY -= 0.08;
            this.motionY *= 0.98;
            this.motionZ = 0.0;
        }
    }

    @Override
    public boolean isInWater() {
        double d = 0.5;
        return this.worldObj.isMaterialInBB(this.boundingBox.expand(d, d, d), Material.water);
    }

    @Override
    protected void updateEntityActionState() {
        ++this.entityAge;
        if(this.currentSwimTarget != null && !this.isValidSwimTarget(this.currentSwimTarget.posX, this.currentSwimTarget.posY, this.currentSwimTarget.posZ)) {
            this.currentSwimTarget = null;
            this.swimTargetTime = 0;
        }
        if(this.currentSwimTarget == null || this.rand.nextInt(200) == 0 || this.getDistanceSqToSwimTarget() < 4.0) {
            for(int l = 0; l < 16; ++l) {
                int i = MathHelper.floor_double(this.posX);
                int j = MathHelper.floor_double(this.posY);
                int k = MathHelper.floor_double(this.posZ);
                if(!this.isValidSwimTarget(i += this.rand.nextInt(16) - this.rand.nextInt(16), j += MathHelper.getRandomIntegerInRange(this.rand, -2, 4), k += this.rand.nextInt(16) - this.rand.nextInt(16))) continue;
                this.currentSwimTarget = new ChunkCoordinates(i, j, k);
                this.swimTargetTime = 0;
                break;
            }
        }
        if(this.currentSwimTarget != null) {
            double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            double d0 = this.currentSwimTarget.posX + 0.5 - this.posX;
            double d1 = this.currentSwimTarget.posY + 0.5 - this.posY;
            double d2 = this.currentSwimTarget.posZ + 0.5 - this.posZ;
            this.motionX += (Math.signum(d0) * 0.5 - this.motionX) * speed;
            this.motionY += (Math.signum(d1) * 0.5 - this.motionY) * speed;
            this.motionZ += (Math.signum(d2) * 0.5 - this.motionZ) * speed;
            float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
            float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
            this.moveForward = 0.5f;
            this.rotationYaw += f1;
            ++this.swimTargetTime;
            if(this.swimTargetTime >= 200) {
                this.currentSwimTarget = null;
                this.swimTargetTime = 0;
            }
        }
        this.despawnEntity();
    }

    private boolean isValidSwimTarget(int i, int j, int k) {
        return this.worldObj.getBlock(i, j, k).getMaterial() == Material.water;
    }

    private double getDistanceSqToSwimTarget() {
        double d = this.currentSwimTarget.posX + 0.5;
        double d1 = this.currentSwimTarget.posY + 0.5;
        double d2 = this.currentSwimTarget.posZ + 0.5;
        return this.getDistanceSq(d, d1, d2);
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }

    public enum FishType {
        COMMON("common"), SALMON("salmon"), CLOWNFISH("clownfish");

        public final String textureDir;

        FishType(String s) {
            this.textureDir = s;
        }
    }

}
