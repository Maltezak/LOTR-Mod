package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class LOTREntityPickpocketFX
extends EntityFX {
    protected float bounciness;
    private double motionBeforeGround;

    public LOTREntityPickpocketFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.particleGravity = 1.0f;
        this.particleMaxAge = 30 + this.rand.nextInt(40);
        this.noClip = false;
        this.bounciness = 1.0f;
    }

    public void onUpdate() {
        super.onUpdate();
        this.updatePickpocketIcon();
        if (!this.onGround) {
            this.motionBeforeGround = this.motionY;
        } else {
            this.motionY = this.motionBeforeGround * (-this.bounciness);
        }
    }

    protected void updatePickpocketIcon() {
        this.setParticleTextureIndex(160 + this.particleAge / 2 % 8);
    }
}

