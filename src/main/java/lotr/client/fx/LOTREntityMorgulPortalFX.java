package lotr.client.fx;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.world.World;

public class LOTREntityMorgulPortalFX extends EntitySpellParticleFX {
    public LOTREntityMorgulPortalFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        this.particleRed = 0.2f;
        this.particleGreen = 0.8f;
        this.particleBlue = 0.4f;
        this.particleScale = 0.5f + this.rand.nextFloat() * 0.5f;
        this.particleMaxAge = 20 + this.rand.nextInt(20);
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    @Override
    public float getBrightness(float f) {
        return 1.0f;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.particleAlpha = 0.5f + 0.5f * ((float) this.particleAge / (float) this.particleMaxAge);
        this.motionX *= 1.1;
        this.motionZ *= 1.1;
    }
}
