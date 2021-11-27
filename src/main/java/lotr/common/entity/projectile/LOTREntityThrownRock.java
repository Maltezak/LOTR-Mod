package lotr.common.entity.projectile;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityThrownRock extends EntityThrowable {
    private int rockRotation;
    private float damage;

    public LOTREntityThrownRock(World world) {
        super(world);
        this.setSize(4.0f, 4.0f);
    }

    public LOTREntityThrownRock(World world, EntityLivingBase entityliving) {
        super(world, entityliving);
        this.setSize(4.0f, 4.0f);
    }

    public LOTREntityThrownRock(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
        this.setSize(4.0f, 4.0f);
    }

    public void setDamage(float f) {
        this.damage = f;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
    }

    public boolean getSpawnsTroll() {
        return this.dataWatcher.getWatchableObjectByte(16) == 1;
    }

    public void setSpawnsTroll(boolean flag) {
        this.dataWatcher.updateObject(16, flag ? (byte) 1 : 0);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(!this.inGround) {
            ++this.rockRotation;
            if(this.rockRotation > 60) {
                this.rockRotation = 0;
            }
            this.rotationPitch = this.rockRotation / 60.0f * 360.0f;
            while(this.rotationPitch - this.prevRotationPitch < -180.0f) {
                this.prevRotationPitch -= 360.0f;
            }
            while(this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                this.prevRotationPitch += 360.0f;
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("RockDamage", this.damage);
        nbt.setBoolean("Troll", this.getSpawnsTroll());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setDamage(nbt.getFloat("RockDamage"));
        this.setSpawnsTroll(nbt.getBoolean("Troll"));
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(!this.worldObj.isRemote) {
            boolean flag = false;
            if(m.entityHit != null && m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), this.damage)) {
                flag = true;
            }
            if(m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                flag = true;
            }
            if(flag) {
                if(this.getSpawnsTroll()) {
                    LOTREntityTroll troll = new LOTREntityTroll(this.worldObj);
                    if(this.rand.nextInt(3) == 0) {
                        troll = new LOTREntityMountainTroll(this.worldObj);
                    }
                    troll.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rand.nextFloat() * 360.0f, 0.0f);
                    troll.onSpawnWithEgg(null);
                    this.worldObj.spawnEntityInWorld(troll);
                }
                this.worldObj.setEntityState(this, (byte) 15);
                int drops = 1 + this.rand.nextInt(3);
                for(int l = 0; l < drops; ++l) {
                    this.dropItem(Item.getItemFromBlock(Blocks.cobblestone), 1);
                }
                this.playSound("lotr:troll.rockSmash", 2.0f, (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
                this.setDead();
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 15) {
            for(int l = 0; l < 32; ++l) {
                LOTRMod.proxy.spawnParticle("largeStone", this.posX + this.rand.nextGaussian() * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + this.rand.nextGaussian() * this.width, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    protected float func_70182_d() {
        return 0.75f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.1f;
    }
}
