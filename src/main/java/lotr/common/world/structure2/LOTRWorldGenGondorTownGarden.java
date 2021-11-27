package lotr.common.world.structure2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTownGarden extends LOTRWorldGenGondorStructure {
    public LOTRWorldGenGondorTownGarden(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -3; i1 <= 3; ++i1) {
                for(k1 = 0; k1 <= 3; ++k1) {
                    int j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -3; i1 <= 3; ++i1) {
            for(k1 = 0; k1 <= 3; ++k1) {
                int j1;
                int i2 = Math.abs(i1);
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i1, j1, k1, this.rockSlabDoubleBlock, this.rockSlabDoubleMeta);
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
                if(i2 <= 2 && k1 >= 1 && k1 <= 2) {
                    this.setBlockAndMetadata(world, i1, 0, k1, Blocks.grass, 0);
                }
                if(i2 != 3 || k1 != 0 && k1 != 3) continue;
                this.setBlockAndMetadata(world, i1, 1, k1, this.rockWallBlock, this.rockWallMeta);
                this.setBlockAndMetadata(world, i1, 2, k1, Blocks.torch, 5);
            }
        }
        for(int k12 = 1; k12 <= 2; ++k12) {
            ItemStack flower = this.getRandomFlower(world, random);
            for(int i12 = -2; i12 <= 2; ++i12) {
                this.setBlockAndMetadata(world, i12, 1, k12, Block.getBlockFromItem(flower.getItem()), flower.getItemDamage());
            }
        }
        return true;
    }
}
