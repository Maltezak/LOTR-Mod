package lotr.client.fx;

import java.awt.Color;

import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityRiverWaterFX extends EntitySpellParticleFX {
    public LOTREntityRiverWaterFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int color) {
        super(world, d, d1, d2, d3, d4, d5);
        Color c = new Color(color);
        float[] rgb = c.getColorComponents(null);
        this.particleRed = MathHelper.randomFloatClamp(this.rand, rgb[0] - 0.3f, rgb[0] + 0.3f);
        this.particleGreen = MathHelper.randomFloatClamp(this.rand, rgb[1] - 0.3f, rgb[1] + 0.3f);
        this.particleBlue = MathHelper.randomFloatClamp(this.rand, rgb[2] - 0.3f, rgb[2] + 0.3f);
        this.particleRed = MathHelper.clamp_float(this.particleRed, 0.0f, 1.0f);
        this.particleGreen = MathHelper.clamp_float(this.particleGreen, 0.0f, 1.0f);
        this.particleBlue = MathHelper.clamp_float(this.particleBlue, 0.0f, 1.0f);
        this.particleScale = 0.5f + this.rand.nextFloat() * 0.5f;
        this.particleMaxAge = 20 + this.rand.nextInt(20);
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.particleAlpha = 0.5f + 0.5f * ((float) this.particleAge / (float) this.particleMaxAge);
    }
}
