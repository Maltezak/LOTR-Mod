package lotr.common.entity.animal;

import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.World;

public class LOTREntityDesertScorpion extends LOTREntityScorpion implements LOTRBiomeGenNearHarad.ImmuneToHeat {
    public boolean pyramidSpawned = false;

    public LOTREntityDesertScorpion(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected int getRandomScorpionScale() {
        return this.rand.nextInt(2);
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && (this.spawningFromSpawner || this.pyramidSpawned || this.posY < 60.0 || this.rand.nextInt(500) == 0);
    }

    @Override
    public boolean isValidLightLevel() {
        return this.spawningFromSpawner || this.pyramidSpawned || super.isValidLightLevel();
    }
}
