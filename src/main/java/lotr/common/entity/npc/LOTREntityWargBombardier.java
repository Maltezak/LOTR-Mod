package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIWargBombardierAttack;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class LOTREntityWargBombardier extends LOTREntityWarg {
    public LOTREntityWargBombardier(World world) {
        super(world);
    }

    @Override
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIWargBombardierAttack(this, 1.7);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(21, (byte) 35);
        this.dataWatcher.addObject(22, (byte) 1);
    }

    public int getBombFuse() {
        return this.dataWatcher.getWatchableObjectByte(21);
    }

    public void setBombFuse(int i) {
        this.dataWatcher.updateObject(21, (byte) i);
    }

    public int getBombStrengthLevel() {
        return this.dataWatcher.getWatchableObjectByte(22);
    }

    public void setBombStrengthLevel(int i) {
        this.dataWatcher.updateObject(22, (byte) i);
    }

    @Override
    public LOTREntityNPC createWargRider() {
        return null;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("BombFuse", (byte) this.getBombFuse());
        nbt.setByte("BombStrengthLevel", (byte) this.getBombStrengthLevel());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBombFuse(nbt.getByte("BombFuse"));
        this.setBombStrengthLevel(nbt.getByte("BombStrengthLevel"));
    }

    @Override
    public boolean canWargBeRidden() {
        return false;
    }

    @Override
    public boolean isMountSaddled() {
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(this.getBombFuse() < 35) {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 2.2, this.posZ, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void setAttackTarget(EntityLivingBase target, boolean speak) {
        super.setAttackTarget(target, speak);
        if (target != null) {
            this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0f, 1.0f);
        }
    }
}
