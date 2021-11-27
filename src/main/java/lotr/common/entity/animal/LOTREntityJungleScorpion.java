package lotr.common.entity.animal;

import net.minecraft.world.World;

public class LOTREntityJungleScorpion extends LOTREntityScorpion {
    public LOTREntityJungleScorpion(World world) {
        super(world);
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && (this.spawningFromSpawner || this.posY < 60.0 || this.rand.nextInt(100) == 0);
    }
}
