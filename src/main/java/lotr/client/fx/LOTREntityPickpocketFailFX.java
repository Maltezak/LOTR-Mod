package lotr.client.fx;

import net.minecraft.world.World;

public class LOTREntityPickpocketFailFX
extends LOTREntityPickpocketFX {
    public LOTREntityPickpocketFailFX(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setParticleTextureIndex(176 + this.rand.nextInt(6));
        this.particleGravity = 0.6f;
        this.bounciness = 0.5f;
    }

    @Override
    protected void updatePickpocketIcon() {
    }
}

