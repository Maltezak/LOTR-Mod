package lotr.client.fx;

import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.world.World;

public class LOTREntityMarshLightFX extends EntityFlameFX {
    public LOTREntityMarshLightFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
        this.setParticleTextureIndex(49);
        this.particleMaxAge = 40 + this.rand.nextInt(20);
        this.particleGreen = this.particleBlue = 0.75f + this.rand.nextFloat() * 0.25f;
        this.particleRed = this.particleBlue;
    }
}
