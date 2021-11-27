package lotr.client.fx;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityMallornEntSummonFX extends EntityDiggingFX {
    private Entity summoner;
    private Entity summoned;
    private float arcParam;

    public LOTREntityMallornEntSummonFX(World world, double d, double d1, double d2, double d3, double d4, double d5, int summonerID, int summonedID, float t, Block block, int meta, int color) {
        super(world, d, d1, d2, d3, d4, d5, block, meta);
        this.motionX = d3;
        this.motionY = d4;
        this.motionZ = d5;
        this.summoner = this.worldObj.getEntityByID(summonerID);
        this.summoned = this.worldObj.getEntityByID(summonedID);
        this.arcParam = t;
        this.particleMaxAge = (int) (40.0f * this.arcParam);
        this.particleRed *= (color >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (color >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (color & 0xFF) / 255.0f;
        this.particleScale *= 2.0f;
        this.particleGravity = 0.0f;
        this.renderDistanceWeight = 10.0;
        this.noClip = true;
        this.updateArcPos();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.updateArcPos();
    }

    private void updateArcPos() {
        if(this.summoner == null || this.summoned == null || !this.summoner.isEntityAlive() || !this.summoned.isEntityAlive()) {
            this.setDead();
            return;
        }
        double[] posA = new double[] {this.summoner.posX, this.summoner.boundingBox.minY + this.summoner.height * 0.7, this.summoner.posZ};
        double[] posC = new double[] {this.summoned.posX, this.summoned.boundingBox.minY + this.summoned.height * 0.7, this.summoned.posZ};
        double[] posB = new double[] {(posA[0] + posC[0]) / 2.0, (posA[1] + posC[1]) / 2.0 + 20.0, (posA[2] + posC[2]) / 2.0};
        double[] ab = this.lerp(posA, posB, this.arcParam);
        double[] bc = this.lerp(posB, posC, this.arcParam);
        double[] abbc = this.lerp(ab, bc, this.arcParam);
        this.posX = abbc[0];
        this.posY = abbc[1];
        this.posZ = abbc[2];
    }

    private double[] lerp(double[] a, double[] b, float t) {
        double[] ab = new double[a.length];
        for(int i = 0; i < ab.length; ++i) {
            ab[i] = a[i] + (b[i] - a[i]) * t;
        }
        return ab;
    }
}
