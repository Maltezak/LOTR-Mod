package lotr.client.fx;

import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.world.World;

public class LOTREntityMusicFX extends EntityNoteFX {
    private double noteMoveX;
    private double noteMoveY;
    private double noteMoveZ;

    public LOTREntityMusicFX(World world, double d, double d1, double d2, double d3, double d4, double d5, double pitch) {
        super(world, d, d1, d2, pitch, 0.0, 0.0);
        this.noteMoveX = d3;
        this.noteMoveY = d4;
        this.noteMoveZ = d5;
        this.particleMaxAge = 8 + this.rand.nextInt(20);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        double decel = 0.98;
        this.noteMoveX *= decel;
        this.noteMoveY *= decel;
        this.noteMoveZ *= decel;
        this.motionX = this.noteMoveX;
        this.motionY = this.noteMoveY;
        this.motionZ = this.noteMoveZ;
    }
}
