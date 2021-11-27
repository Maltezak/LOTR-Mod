package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntitySwordCommandMarker
extends Entity {
    private int particleAge;
    private int particleMaxAge;

    public LOTREntitySwordCommandMarker(World world, double d, double d1, double d2) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(d, d1, d2);
        this.particleAge = 0;
        this.particleMaxAge = 30;
    }

    protected void entityInit() {
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
    }

    public void onUpdate() {
        super.onUpdate();
        this.posY -= 0.35;
        ++this.particleAge;
        if (this.particleAge >= this.particleMaxAge) {
            this.setDead();
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean isEntityInvulnerable() {
        return true;
    }

    public boolean canBePushed() {
        return false;
    }
}

