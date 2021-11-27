package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;

public class LOTRWorldGenBlueMountainsSmithy extends LOTRWorldGenDwarfSmithy {
    public LOTRWorldGenBlueMountainsSmithy(boolean flag) {
        super(flag);
        this.baseBrickBlock = LOTRMod.brick;
        this.baseBrickMeta = 14;
        this.carvedBrickBlock = LOTRMod.brick3;
        this.carvedBrickMeta = 0;
        this.pillarBlock = LOTRMod.pillar;
        this.pillarMeta = 3;
        this.tableBlock = LOTRMod.blueDwarvenTable;
        this.barsBlock = LOTRMod.blueDwarfBars;
    }

    @Override
    protected LOTREntityDwarf createSmith(World world) {
        return new LOTREntityBlueMountainsSmith(world);
    }

    @Override
    protected LOTRChestContents getChestContents() {
        return LOTRChestContents.BLUE_MOUNTAINS_SMITHY;
    }
}
