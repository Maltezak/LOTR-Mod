package lotr.client.fx;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.world.World;

public class LOTREntityMTCHealFX extends EntitySpellParticleFX {
    private int baseTextureIndex;

    public LOTREntityMTCHealFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        this.particleRed = 1.0f;
        this.particleGreen = 0.3f;
        this.particleBlue = 0.3f;
        this.particleScale *= 3.0f;
        this.particleMaxAge = 30;
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
        this.renderDistanceWeight = 10.0;
        this.noClip = true;
        this.setBaseSpellTextureIndex(128);
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if(this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.particleAlpha = 0.5f + 0.5f * ((float) this.particleAge / (float) this.particleMaxAge);
    }

    @Override
    public void setBaseSpellTextureIndex(int i) {
        super.setBaseSpellTextureIndex(i);
        this.baseTextureIndex = i;
    }
}
