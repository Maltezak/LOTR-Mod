package lotr.common.entity.projectile;

import java.util.List;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.network.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityGandalfFireball extends EntityThrowable {
    public int animationTick;

    public LOTREntityGandalfFireball(World world) {
        super(world);
    }

    public LOTREntityGandalfFireball(World world, EntityLivingBase entityliving) {
        super(world, entityliving);
    }

    public LOTREntityGandalfFireball(World world, double d, double d1, double d2) {
        super(world, d, d1, d2);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (short) 0);
    }

    public int getFireballAge() {
        return this.dataWatcher.getWatchableObjectShort(16);
    }

    public void setFireballAge(int age) {
        this.dataWatcher.updateObject(16, (short) age);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("FireballAge", this.getFireballAge());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setFireballAge(nbt.getInteger("FireballAge"));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(this.ticksExisted % 5 == 0) {
            ++this.animationTick;
            if(this.animationTick >= 4) {
                this.animationTick = 0;
            }
        }
        if(!this.worldObj.isRemote) {
            this.setFireballAge(this.getFireballAge() + 1);
            if(this.getFireballAge() >= 200) {
                this.explode(null);
            }
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition m) {
        if(!this.worldObj.isRemote) {
            Entity entity;
            if(m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                this.explode(null);
            }
            else if(m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && this.isEntityVulnerable(entity = m.entityHit)) {
                this.explode(entity);
            }
        }
    }

    private void explode(Entity target) {
        List entities;
        if(this.worldObj.isRemote) {
            return;
        }
        this.worldObj.playSoundAtEntity(this, "lotr:item.gandalfFireball", 4.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.FIREBALL_GANDALF_WHITE, this);
        LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(this, 64.0));
        if(target != null && this.isEntityVulnerable(target)) {
            target.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), 10.0f);
        }
        if(!(entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(6.0, 6.0, 6.0))).isEmpty()) {
            for(Object entitie : entities) {
                float damage;
                EntityLivingBase entity = (EntityLivingBase) entitie;
                if(entity == target || !this.isEntityVulnerable(entity) || ((damage = 10.0f - this.getDistanceToEntity(entity) * 0.5f) <= 0.0f)) continue;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), damage);
            }
        }
        this.setDead();
    }

    private boolean isEntityVulnerable(Entity entity) {
        if(entity == this.getThrower()) {
            return false;
        }
        if(!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if(entity instanceof EntityPlayer) {
            return LOTRLevelData.getData((EntityPlayer) entity).getAlignment(LOTRFaction.HIGH_ELF) < 0.0f;
        }
        return !LOTRFaction.HIGH_ELF.isGoodRelation(LOTRMod.getNPCFaction(entity));
    }

    @Override
    protected float func_70182_d() {
        return 1.5f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0f;
    }
}
