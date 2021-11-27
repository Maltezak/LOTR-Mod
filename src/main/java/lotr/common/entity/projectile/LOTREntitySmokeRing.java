package lotr.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class LOTREntitySmokeRing extends EntityThrowable {
    public static int MAX_AGE = 300;
    public int renderSmokeAge = -1;
    public int prevRenderSmokeAge = -1;

    public LOTREntitySmokeRing(World world) {
        super(world);
    }

    public LOTREntitySmokeRing(World world, EntityLivingBase entityliving) {
        super(world, entityliving);
    }

    public LOTREntitySmokeRing(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public int getSmokeAge() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    public void setSmokeAge(int age) {
        this.dataWatcher.updateObject(16, age);
    }

    public int getSmokeColour() {
        return this.dataWatcher.getWatchableObjectByte(17);
    }

    public void setSmokeColour(int colour) {
        this.dataWatcher.updateObject(17, (byte) colour);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("SmokeAge", this.getSmokeAge());
        nbt.setInteger("SmokeColour", this.getSmokeColour());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setSmokeAge(nbt.getInteger("SmokeAge"));
        this.setSmokeColour(nbt.getInteger("SmokeColour"));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(this.isInWater() && !this.worldObj.isRemote) {
            this.setDead();
        }
        this.prevRenderSmokeAge = this.renderSmokeAge == -1 ? (this.renderSmokeAge = this.getSmokeAge()) : this.renderSmokeAge;
        if(!this.worldObj.isRemote) {
            this.setSmokeAge(this.getSmokeAge() + 1);
            if(this.getSmokeAge() >= MAX_AGE) {
                this.setDead();
            }
        }
        this.renderSmokeAge = this.getSmokeAge();
    }

    public float getRenderSmokeAge(float f) {
        float smokeAge = this.prevRenderSmokeAge + (this.renderSmokeAge - this.prevRenderSmokeAge) * f;
        return smokeAge / MAX_AGE;
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && m.entityHit == this.getThrower()) {
            return;
        }
        if(!this.worldObj.isRemote) {
            this.setDead();
        }
    }

    @Override
    protected float func_70182_d() {
        return 0.1f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0f;
    }
}
