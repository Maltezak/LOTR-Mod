package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.world.World;

public abstract class LOTRWorldGenNPCRespawner extends LOTRWorldGenStructureBase2 {
    public LOTRWorldGenNPCRespawner(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        LOTREntityNPCRespawner spawner = new LOTREntityNPCRespawner(world);
        this.setupRespawner(spawner);
        this.placeNPCRespawner(spawner, world, 0, 1, 0);
        return true;
    }

    public abstract void setupRespawner(LOTREntityNPCRespawner var1);
}
