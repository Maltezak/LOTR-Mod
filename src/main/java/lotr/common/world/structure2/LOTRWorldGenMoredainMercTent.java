package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemBanner;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercTent extends LOTRWorldGenStructureBase2 {
    private Block fenceBlock;
    private int fenceMeta;
    private Block tentBlock;
    private int tentMeta;
    private Block tent2Block;
    private int tent2Meta;
    private Block tableBlock;
    private LOTRChestContents chestContents;
    private LOTRItemBanner.BannerType bannerType;

    public LOTRWorldGenMoredainMercTent(boolean flag) {
        super(flag);
    }

    @Override
    protected void setupRandomBlocks(Random random) {
        this.fenceBlock = LOTRMod.fence2;
        this.fenceMeta = 2;
        int randomWool = random.nextInt(3);
        if(randomWool == 0) {
            this.tentBlock = Blocks.wool;
            this.tentMeta = 14;
        }
        else if(randomWool == 1) {
            this.tentBlock = Blocks.wool;
            this.tentMeta = 12;
        }
        else if(randomWool == 2) {
            this.tentBlock = Blocks.wool;
            this.tentMeta = 1;
        }
        this.tent2Block = Blocks.wool;
        this.tent2Meta = 15;
        this.chestContents = LOTRChestContents.MOREDAIN_MERC_TENT;
        if(random.nextBoolean()) {
            this.tableBlock = LOTRMod.nearHaradTable;
            this.bannerType = LOTRItemBanner.BannerType.NEAR_HARAD;
        }
        else {
            this.tableBlock = LOTRMod.moredainTable;
            this.bannerType = LOTRItemBanner.BannerType.MOREDAIN;
        }
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            for(i1 = -2; i1 <= 2; ++i1) {
                for(k1 = -3; k1 <= 3; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(this.isSurface(world, i1, j1, k1)) continue;
                    return false;
                }
            }
        }
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k1 = -3; k1 <= 3; ++k1) {
                for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i1, j1, k1)) && (this.getY(j1) >= 0)); --j1) {
                    int randomGround = random.nextInt(3);
                    if(randomGround == 0) {
                        if(j1 == 0) {
                            this.setBiomeTop(world, i1, j1, k1);
                        }
                        else {
                            this.setBiomeFiller(world, i1, j1, k1);
                        }
                    }
                    else if(randomGround == 1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.gravel, 0);
                    }
                    else if(randomGround == 2) {
                        this.setBlockAndMetadata(world, i1, j1, k1, Blocks.sandstone, 0);
                    }
                    this.setGrassToDirt(world, i1, j1 - 1, k1);
                }
                for(j1 = 1; j1 <= 3; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        for(int k12 = -3; k12 <= 3; ++k12) {
            boolean tent2 = IntMath.mod(k12, 2) == 0;
            Block block = tent2 ? this.tent2Block : this.tentBlock;
            int meta = tent2 ? this.tent2Meta : this.tentMeta;
            for(int i12 : new int[] {-2, 2}) {
                for(int j12 = 1; j12 <= 2; ++j12) {
                    this.setBlockAndMetadata(world, i12, j12, k12, block, meta);
                }
                this.setGrassToDirt(world, i12, 0, k12);
            }
            this.setBlockAndMetadata(world, -1, 3, k12, block, meta);
            this.setBlockAndMetadata(world, 1, 3, k12, block, meta);
            this.setBlockAndMetadata(world, 0, 4, k12, block, meta);
            if(Math.abs(k12) != 3) continue;
            this.setBlockAndMetadata(world, 0, 5, k12, block, meta);
        }
        for(int j13 = 1; j13 <= 3; ++j13) {
            this.setBlockAndMetadata(world, 0, j13, -3, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 0, j13, 3, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 2, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -1, 2, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, 3, Blocks.torch, 1);
        if(random.nextBoolean()) {
            this.placeChest(world, random, -1, 1, 0, 4, this.chestContents);
            this.setBlockAndMetadata(world, -1, 1, -1, Blocks.crafting_table, 0);
            this.setGrassToDirt(world, -1, 0, -1);
            this.setBlockAndMetadata(world, -1, 1, 1, this.tableBlock, 0);
            this.setGrassToDirt(world, -1, 0, 1);
        }
        else {
            this.placeChest(world, random, 1, 1, 0, 5, this.chestContents);
            this.setBlockAndMetadata(world, 1, 1, -1, Blocks.crafting_table, 0);
            this.setGrassToDirt(world, 1, 0, -1);
            this.setBlockAndMetadata(world, 1, 1, 1, this.tableBlock, 0);
            this.setGrassToDirt(world, 1, 0, 1);
        }
        this.placeWallBanner(world, 0, 5, -3, this.bannerType, 2);
        this.placeWallBanner(world, 0, 5, 3, this.bannerType, 0);
        return true;
    }
}
