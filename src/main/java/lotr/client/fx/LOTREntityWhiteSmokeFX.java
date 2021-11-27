package lotr.client.fx;

import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWhiteSmokeFX extends EntitySmokeFX {
    public LOTREntityWhiteSmokeFX(World world, double d, double d1, double d2, double d3, double d4, double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        this.particleGreen = this.particleBlue = (MathHelper.randomFloatClamp(this.rand, 0.6f, 1.0f));
        this.particleRed = this.particleBlue;
    }
}
