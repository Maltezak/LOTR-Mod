package lotr.client.fx;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityChillFX extends EntitySmokeFX {
    public LOTREntityChillFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        this.particleGreen = this.particleBlue = (MathHelper.randomFloatClamp(this.rand, 0.8f, 1.0f));
        this.particleRed = this.particleBlue;
        this.setParticleTextureIndex(this.rand.nextInt(8));
        this.particleMaxAge *= 6;
        float blue = this.rand.nextFloat() * 0.25f;
        this.particleRed *= 1.0f - blue;
        this.particleGreen *= 1.0f - blue;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.particleAge;
        if(this.particleAge >= this.particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.96;
        this.motionY *= 0.96;
        this.motionZ *= 0.96;
        this.motionY -= 0.005;
        if(this.onGround) {
            this.motionX *= 0.7;
            this.motionZ *= 0.7;
        }
    }
}
