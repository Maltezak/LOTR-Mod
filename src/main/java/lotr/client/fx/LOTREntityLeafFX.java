package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class LOTREntityLeafFX
extends EntityFX {
    private int[] texIndices;
    public LOTREntityLeafFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int[] tex) {
        super(world, d, d1, d2, d3, d4, d5);
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
        this.particleScale = 0.15f + this.rand.nextFloat() * 0.5f;
        this.texIndices = tex;
        this.particleMaxAge = 600;
    }

    public LOTREntityLeafFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int[] tex, int age) {
        this(world, d, d1, d2, d3, d4, d5, tex);
        this.particleMaxAge = age;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        ++this.particleAge;
        this.setParticleTextureIndex(this.texIndices[this.particleAge / 4 % this.texIndices.length]);
        if (this.onGround || this.particleAge >= this.particleMaxAge || this.posY < 0.0) {
            this.setDead();
        }
    }

    public int getFXLayer() {
        return 1;
    }

    public void setParticleTextureIndex(int i) {
        this.particleTextureIndexX = i % 8;
        this.particleTextureIndexY = i / 8;
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float uMin = this.particleTextureIndexX / 8.0f;
        float uMax = uMin + 0.125f;
        float vMin = this.particleTextureIndexY / 8.0f;
        float vMax = vMin + 0.125f;
        float scale = 0.25f * this.particleScale;
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * f - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * f - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * f - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV(x - f1 * scale - f4 * scale, y - f2 * scale, z - f3 * scale - f5 * scale, uMax, vMax);
        tessellator.addVertexWithUV(x - f1 * scale + f4 * scale, y + f2 * scale, z - f3 * scale + f5 * scale, uMax, vMin);
        tessellator.addVertexWithUV(x + f1 * scale + f4 * scale, y + f2 * scale, z + f3 * scale + f5 * scale, uMin, vMin);
        tessellator.addVertexWithUV(x + f1 * scale - f4 * scale, y - f2 * scale, z + f3 * scale - f5 * scale, uMin, vMax);
    }
}

