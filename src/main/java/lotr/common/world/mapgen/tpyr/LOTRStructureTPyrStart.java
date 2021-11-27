package lotr.common.world.mapgen.tpyr;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class LOTRStructureTPyrStart extends StructureStart {
    public LOTRStructureTPyrStart() {
    }

    public LOTRStructureTPyrStart(World world, Random random, int i, int j) {
        LOTRComponentTauredainPyramid startComponent = new LOTRComponentTauredainPyramid(world, 0, random, (i << 4) + 8, (j << 4) + 8);
        this.components.add(startComponent);
        startComponent.buildComponent(startComponent, this.components, random);
        this.updateBoundingBox();
    }
}
