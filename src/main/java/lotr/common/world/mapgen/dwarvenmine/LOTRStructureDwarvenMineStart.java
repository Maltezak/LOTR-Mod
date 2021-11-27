package lotr.common.world.mapgen.dwarvenmine;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class LOTRStructureDwarvenMineStart extends StructureStart {
    public LOTRStructureDwarvenMineStart() {
    }

    public LOTRStructureDwarvenMineStart(World world, Random random, int i, int j, boolean r) {
        LOTRComponentDwarvenMineEntrance startComponent = new LOTRComponentDwarvenMineEntrance(world, 0, random, (i << 4) + 8, (j << 4) + 8, r);
        this.components.add(startComponent);
        startComponent.buildComponent(startComponent, this.components, random);
        this.updateBoundingBox();
    }
}
