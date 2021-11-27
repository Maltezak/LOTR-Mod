package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityPebble extends EntityThrowable {
    private boolean isSling = false;

    public LOTREntityPebble(World world) {
        super(world);
    }

    public LOTREntityPebble(World world, EntityLivingBase entityliving) {
        super(world, entityliving);
    }

    public LOTREntityPebble(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    public LOTREntityPebble setSling() {
        this.isSling = true;
        return this;
    }

    public boolean isSling() {
        return this.isSling;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Sling", this.isSling);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.isSling = nbt.getBoolean("Sling");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float speed = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if(speed > 0.1f && this.motionY < 0.0 && this.isInWater()) {
            float factor = MathHelper.randomFloatClamp(this.rand, 0.4f, 0.8f);
            this.motionX *= factor;
            this.motionZ *= factor;
            this.motionY += factor;
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(m.entityHit != null) {
            float damage = this.isSling ? 2.0f : 1.0f;
            m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
        }
        if(!this.worldObj.isRemote) {
            this.entityDropItem(new ItemStack(LOTRMod.pebble), 0.0f);
            this.setDead();
        }
    }

    @Override
    protected float func_70182_d() {
        return 1.0f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.04f;
    }
}
