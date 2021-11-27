package lotr.client.fx;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.world.World;

public class LOTREntityMallornEntHealFX extends EntityDiggingFX {
    public LOTREntityMallornEntHealFX(World world, double d, double d1, double d2, double d3, double d4, double d5, Block block, int meta, int color) {
        super(world, d, d1, d2, d3, d4, d5, block, meta);
        this.particleRed *= (color >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (color >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (color & 0xFF) / 255.0f;
        this.particleScale *= 2.0f;
        this.particleMaxAge = 30;
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
        this.particleGravity = 0.0f;
        this.renderDistanceWeight = 10.0;
        this.noClip = true;
    }
}
