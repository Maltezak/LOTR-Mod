package lotr.common.entity.projectile;

import net.minecraft.entity.*;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityMarshWraithBall extends EntityThrowable {
    public int animationTick;
    public Entity attackTarget;

    public LOTREntityMarshWraithBall(World world) {
        super(world);
        this.setSize(0.75f, 0.75f);
    }

    public LOTREntityMarshWraithBall(World world, EntityLivingBase entityliving) {
        super(world, entityliving);
        this.setSize(0.75f, 0.75f);
    }

    public LOTREntityMarshWraithBall(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        this.setSize(0.75f, 0.75f);
    }

    public LOTREntityMarshWraithBall(World world, EntityLivingBase entityliving, EntityLivingBase target) {
        super(world, entityliving);
        this.setSize(0.75f, 0.75f);
        this.attackTarget = target;
        this.posX = entityliving.posX;
        this.posY = entityliving.boundingBox.minY + entityliving.getEyeHeight() - 0.1;
        this.posZ = entityliving.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
        double d = target.posX - this.posX;
        double d1 = target.boundingBox.minY + target.height / 2.0f - this.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = MathHelper.sqrt_double(d * d + d2 * d2);
        if(d3 >= 1.0E-7) {
            float f2 = (float) (Math.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
            float f3 = (float) (-(Math.atan2(d1, d3) * 180.0 / 3.141592653589793));
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, f2, f3);
            this.setThrowableHeading(d, d1, d2, this.func_70182_d(), 1.0f);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (short) 0);
    }

    public int getBallAge() {
        return this.dataWatcher.getWatchableObjectShort(16);
    }

    public void setBallAge(int age) {
        this.dataWatcher.updateObject(16, (short) age);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("BallAge", this.getBallAge());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBallAge(nbt.getInteger("BallAge"));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(this.ticksExisted % 2 == 0) {
            ++this.animationTick;
            if(this.animationTick >= 16) {
                this.animationTick = 0;
            }
        }
        if(!this.worldObj.isRemote) {
            this.setBallAge(this.getBallAge() + 1);
            if(this.getBallAge() >= 200) {
                this.setDead();
            }
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if(!this.worldObj.isRemote) {
                this.setDead();
            }
        }
        else if(m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            Entity entity = m.entityHit;
            if(this.attackTarget != null && entity == this.attackTarget) {
                if(entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5.0f) && entity instanceof EntityLivingBase) {
                    int duration = 5;
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                }
                if(!this.worldObj.isRemote) {
                    this.setDead();
                }
            }
        }
    }

    @Override
    protected float func_70182_d() {
        return 0.5f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0f;
    }
}
