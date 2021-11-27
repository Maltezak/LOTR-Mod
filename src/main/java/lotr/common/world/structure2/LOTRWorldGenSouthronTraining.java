package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTraining extends LOTRWorldGenSouthronStructure {
    public LOTRWorldGenSouthronTraining(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -7; i1 <= 7; ++i1) {
                for(int k1 = -5; k1 <= 5; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -7; i1 <= 7; ++i1) {
            for(int k1 = -5; k1 <= 5; ++k1) {
                for(int j1 = 1; j1 <= 4; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("southron_training");
        this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
        this.addBlockMetaAliasOption("GROUND", 5, Blocks.sand, 0);
        this.addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, Blocks.sand, 1);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWeaponRack(world, -5, 2, -4, 2, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, 5, 2, -4, 2, this.getRandomHaradWeapon(random));
        this.placeSkull(world, 0, 3, 2, 0);
        this.placeSkull(world, -5, 3, 0, 12);
        this.placeSkull(world, 5, 3, 0, 4);
        return true;
    }
}
