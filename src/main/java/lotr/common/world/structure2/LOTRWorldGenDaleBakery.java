package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.*;
import net.minecraft.world.World;

public class LOTRWorldGenDaleBakery extends LOTRWorldGenDaleStructure {
    public LOTRWorldGenDaleBakery(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        int k17;
        int j1;
        int i12;
        int i2;
        int k12;
        int i13;
        int k13;
        int k14;
        int k15;
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i14 = -7; i14 <= 7; ++i14) {
                for(k17 = -4; k17 <= 15; ++k17) {
                    int j12 = this.getTopBlock(world, i14, k17) - 1;
                    Object block = this.getBlock(world, i14, j12, k17);
                    if(block != Blocks.grass) {
                        return false;
                    }
                    if(j12 < minHeight) {
                        minHeight = j12;
                    }
                    if(j12 > maxHeight) {
                        maxHeight = j12;
                    }
                    if(maxHeight - minHeight <= 6) continue;
                    return false;
                }
            }
        }
        for(i12 = -5; i12 <= 5; ++i12) {
            for(k12 = 0; k12 <= 13; ++k12) {
                j1 = 0;
                while(!this.isOpaque(world, i12, j1, k12) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.brickBlock, this.brickMeta);
                    this.setGrassToDirt(world, i12, j1 - 1, k12);
                    --j1;
                }
                for(j1 = 1; j1 <= 7; ++j1) {
                    this.setAir(world, i12, j1, k12);
                }
                if(Math.abs(i12) == 5 || k12 == 0 || k12 == 13) {
                    for(j1 = 1; j1 <= 3; ++j1) {
                        this.setBlockAndMetadata(world, i12, j1, k12, this.brickBlock, this.brickMeta);
                    }
                    continue;
                }
                this.setBlockAndMetadata(world, i12, 0, k12, this.floorBlock, this.floorMeta);
            }
        }
        for(i12 = -6; i12 <= 6; ++i12) {
            for(k12 = -1; k12 <= 14; ++k12) {
                if((Math.abs(i12) != 6 || k12 != -1 && k12 != 14) && (Math.abs(i12) != 1 || k12 != -1)) continue;
                for(j1 = 4; (((j1 >= 1) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.woodBeamBlock, this.woodBeamMeta);
                    this.setGrassToDirt(world, i12, j1 - 1, k12);
                }
            }
        }
        for(i12 = -1; i12 <= 1; ++i12) {
            if(Math.abs(i12) == 1) {
                k12 = -2;
                for(j1 = 2; (((j1 >= 1) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                    this.setBlockAndMetadata(world, i12, j1, k12, this.fenceBlock, this.fenceMeta);
                }
                continue;
            }
            if(i12 != 0) continue;
            k12 = -1;
            for(j1 = 0; (((j1 >= 0) || !this.isOpaque(world, i12, j1, k12)) && (this.getY(j1) >= 0)); --j1) {
                this.setBlockAndMetadata(world, i12, j1, k12, this.floorBlock, this.floorMeta);
                this.setGrassToDirt(world, i12, j1 - 1, k12);
            }
        }
        for(i12 = -5; i12 <= 5; ++i12) {
            this.setBlockAndMetadata(world, i12, 4, -1, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i12, 4, 14, this.brickBlock, this.brickMeta);
        }
        for(k13 = 0; k13 <= 13; ++k13) {
            this.setBlockAndMetadata(world, -6, 4, k13, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, 6, 4, k13, this.brickBlock, this.brickMeta);
        }
        for(i12 = -7; i12 <= 7; ++i12) {
            this.setBlockAndMetadata(world, i12, 4, -2, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, i12, 4, 15, this.brickSlabBlock, this.brickSlabMeta | 8);
        }
        for(k13 = -2; k13 <= 15; ++k13) {
            this.setBlockAndMetadata(world, -7, 4, k13, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, 7, 4, k13, this.brickSlabBlock, this.brickSlabMeta | 8);
        }
        for(int i15 : new int[] {-5, 2}) {
            this.setBlockAndMetadata(world, i15, 2, -1, Blocks.trapdoor, 12);
            this.setBlockAndMetadata(world, i15, 3, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, i15, 4, -2, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i15 + 1, 2, 0, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15 + 1, 3, -1, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, i15 + 2, 2, 0, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15 + 2, 3, -1, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, i15 + 3, 2, -1, Blocks.trapdoor, 12);
            this.setBlockAndMetadata(world, i15 + 3, 3, -1, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, i15 + 3, 4, -2, this.brickBlock, this.brickMeta);
        }
        int[] k16 = new int[] {-5, 2};
        k12 = k16.length;
        for(j1 = 0; j1 < k12; ++j1) {
            int i15;
            i15 = k16[j1];
            this.setBlockAndMetadata(world, i15, 2, 14, Blocks.trapdoor, 13);
            this.setBlockAndMetadata(world, i15, 3, 14, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, i15, 4, 15, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i15 + 1, 2, 13, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15 + 1, 3, 14, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, i15 + 2, 2, 13, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, i15 + 2, 3, 14, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, i15 + 3, 2, 14, Blocks.trapdoor, 13);
            this.setBlockAndMetadata(world, i15 + 3, 3, 14, this.brickSlabBlock, this.brickSlabMeta | 8);
            this.setBlockAndMetadata(world, i15 + 3, 4, 15, this.brickBlock, this.brickMeta);
        }
        for(k14 = 0; k14 <= 13; ++k14) {
            if(k14 == 0 || k14 == 3 || k14 == 6 || k14 == 8 || k14 == 13) {
                this.setBlockAndMetadata(world, -6, 2, k14, Blocks.trapdoor, 15);
                this.setBlockAndMetadata(world, -6, 3, k14, this.brickSlabBlock, this.brickSlabMeta | 8);
                this.setBlockAndMetadata(world, -7, 4, k14, this.brickBlock, this.brickMeta);
                this.setBlockAndMetadata(world, 6, 2, k14, Blocks.trapdoor, 14);
                this.setBlockAndMetadata(world, 6, 3, k14, this.brickSlabBlock, this.brickSlabMeta | 8);
                this.setBlockAndMetadata(world, 7, 4, k14, this.brickBlock, this.brickMeta);
            }
            if(k14 != 1 && k14 != 2 && k14 != 4 && k14 != 5 && (k14 < 9 || k14 > 12)) continue;
            this.setBlockAndMetadata(world, -5, 2, k14, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, -6, 3, k14, this.plankSlabBlock, this.plankSlabMeta);
            this.setBlockAndMetadata(world, 5, 2, k14, this.fenceBlock, this.fenceMeta);
            this.setBlockAndMetadata(world, 6, 3, k14, this.plankSlabBlock, this.plankSlabMeta);
        }
        for(k14 = -2; k14 <= -1; ++k14) {
            this.setBlockAndMetadata(world, -1, 3, k14, Blocks.wool, 14);
            this.setBlockAndMetadata(world, 0, 3, k14, Blocks.wool, 0);
            this.setBlockAndMetadata(world, 1, 3, k14, Blocks.wool, 14);
        }
        this.setBlockAndMetadata(world, 0, 1, -1, this.doorBlock, 3);
        this.setBlockAndMetadata(world, 0, 2, -1, this.doorBlock, 8);
        this.setBlockAndMetadata(world, 0, 0, 0, this.floorBlock, this.floorMeta);
        this.setAir(world, 0, 1, 0);
        this.setAir(world, 0, 2, 0);
        for(k14 = 0; k14 <= 13; ++k14) {
            for(i13 = -5; i13 <= 5; ++i13) {
                i2 = Math.abs(i13);
                if(i2 >= 1 && i2 <= 3 && (k14 >= 2 && k14 <= 6 || k14 >= 8 && k14 <= 11)) {
                    this.setBlockAndMetadata(world, i13, 4, k14, this.plankSlabBlock, this.plankSlabMeta | 8);
                    continue;
                }
                this.setBlockAndMetadata(world, i13, 4, k14, this.plankBlock, this.plankMeta);
            }
            for(i13 = -6; i13 <= 6; ++i13) {
                this.setBlockAndMetadata(world, i13, 5, k14, this.roofBlock, this.roofMeta);
            }
            for(i13 = -5; i13 <= 5; ++i13) {
                this.setBlockAndMetadata(world, i13, 6, k14, this.roofBlock, this.roofMeta);
            }
        }
        for(int k171 : new int[] {-1, 14}) {
            int i16;
            for(i16 = -6; i16 <= 6; ++i16) {
                this.setBlockAndMetadata(world, i16, 5, k171, this.plankBlock, this.plankMeta);
            }
            for(i16 = -5; i16 <= 5; ++i16) {
                this.setBlockAndMetadata(world, i16, 6, k171, this.plankBlock, this.plankMeta);
            }
        }
        int[] k18 = new int[] {-2, 15};
        i13 = k18.length;
        for(i2 = 0; i2 < i13; ++i2) {
            k17 = k18[i2];
            this.setBlockAndMetadata(world, -6, 5, k17, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, -5, 6, k17, this.roofStairBlock, 4);
            this.setBlockAndMetadata(world, 5, 6, k17, this.roofStairBlock, 5);
            this.setBlockAndMetadata(world, 6, 5, k17, this.roofStairBlock, 5);
            for(int i17 : new int[] {-3, 0, 3}) {
                for(int j13 = 5; j13 <= 6; ++j13) {
                    this.setBlockAndMetadata(world, i17, j13, k17, this.brickBlock, this.brickMeta);
                }
            }
        }
        for(int k19 = -2; k19 <= 15; ++k19) {
            this.setBlockAndMetadata(world, -7, 5, k19, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -6, 6, k19, this.roofStairBlock, 1);
            this.setBlockAndMetadata(world, -5, 7, k19, this.roofStairBlock, 1);
            for(i13 = -4; i13 <= 4; ++i13) {
                this.setBlockAndMetadata(world, i13, 7, k19, this.roofBlock, this.roofMeta);
            }
            for(i13 = -2; i13 <= 2; ++i13) {
                this.setBlockAndMetadata(world, i13, 8, k19, this.roofSlabBlock, this.roofSlabMeta);
            }
            this.setBlockAndMetadata(world, 5, 7, k19, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 6, 6, k19, this.roofStairBlock, 0);
            this.setBlockAndMetadata(world, 7, 5, k19, this.roofStairBlock, 0);
        }
        for(int j14 = 1; j14 <= 9; ++j14) {
            this.setBlockAndMetadata(world, 0, j14, 11, Blocks.brick_block, 0);
            this.setBlockAndMetadata(world, -1, j14, 12, Blocks.brick_block, 0);
            this.setAir(world, 0, j14, 12);
            this.setBlockAndMetadata(world, 1, j14, 12, Blocks.brick_block, 0);
            this.setBlockAndMetadata(world, 0, j14, 13, Blocks.brick_block, 0);
        }
        for(int j15 : new int[] {1, 8}) {
            this.setBlockAndMetadata(world, 0, j15, 12, LOTRMod.hearth, 0);
            this.setBlockAndMetadata(world, 0, j15 + 1, 12, Blocks.fire, 0);
        }
        this.setBlockAndMetadata(world, -2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 3, 1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 3, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 3, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -4, 3, 10, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 3, 10, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 3, 12, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 3, 12, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 4, 4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -2, 3, 4, LOTRMod.chandelier, 3);
        this.setBlockAndMetadata(world, 2, 4, 4, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, 2, 3, 4, LOTRMod.chandelier, 3);
        for(i1 = -4; i1 <= 4; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, 7, this.brickBlock, this.brickMeta);
            this.setBlockAndMetadata(world, i1, 3, 7, this.fenceBlock, this.fenceMeta);
        }
        this.setBlockAndMetadata(world, -1, 1, 7, this.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 1, 7, this.brickSlabBlock, this.brickSlabMeta | 8);
        this.setBlockAndMetadata(world, 1, 1, 7, this.brickStairBlock, 5);
        this.setBlockAndMetadata(world, 3, 1, 7, this.fenceGateBlock, 0);
        this.setBlockAndMetadata(world, -1, 1, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 1, 11, Blocks.brick_stairs, 2);
        this.setBlockAndMetadata(world, 1, 1, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -1, 2, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, 0, 2, 11, this.barsBlock, 0);
        this.setBlockAndMetadata(world, 1, 2, 11, Blocks.furnace, 2);
        this.setBlockAndMetadata(world, -1, 3, 11, Blocks.brick_block, 0);
        this.setBlockAndMetadata(world, 1, 3, 11, Blocks.brick_block, 0);
        for(i1 = -2; i1 <= 2; ++i1) {
            for(k12 = 10; k12 <= 12; ++k12) {
                this.setBlockAndMetadata(world, i1, 4, k12, Blocks.brick_block, 0);
            }
        }
        this.setBlockAndMetadata(world, -2, 1, 1, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 1, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, -4, 1, 1, this.plankBlock, this.plankMeta);
        for(k15 = 2; k15 <= 5; ++k15) {
            this.setBlockAndMetadata(world, -4, 1, k15, this.plankSlabBlock, this.plankSlabMeta | 8);
        }
        this.setBlockAndMetadata(world, -4, 1, 6, this.plankBlock, this.plankMeta);
        this.setBlockAndMetadata(world, -3, 1, 6, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, -2, 1, 6, this.plankBlock, this.plankMeta);
        for(k15 = 1; k15 <= 6; ++k15) {
            this.placeRandomCake(world, random, -4, 2, k15);
        }
        for(i1 = -3; i1 <= -2; ++i1) {
            this.placeRandomCake(world, random, i1, 2, 1);
            this.placeRandomCake(world, random, i1, 2, 6);
        }
        this.setBlockAndMetadata(world, 2, 1, 3, this.plankStairBlock, 7);
        this.setBlockAndMetadata(world, 2, 1, 4, this.plankStairBlock, 6);
        this.placeRandomCake(world, random, 2, 2, 3);
        this.placeRandomCake(world, random, 2, 2, 4);
        this.setBlockAndMetadata(world, 4, 1, 1, this.plankBlock, this.plankMeta);
        for(k15 = 2; k15 <= 5; ++k15) {
            this.setBlockAndMetadata(world, 4, 1, k15, this.plankSlabBlock, this.plankSlabMeta | 8);
        }
        this.setBlockAndMetadata(world, 4, 1, 6, this.plankBlock, this.plankMeta);
        for(k15 = 1; k15 <= 6; ++k15) {
            this.placeRandomCake(world, random, 4, 2, k15);
        }
        for(i1 = -4; i1 <= -3; ++i1) {
            this.setBlockAndMetadata(world, i1, 1, 8, this.plankBlock, this.plankMeta);
            for(k12 = 9; k12 <= 11; ++k12) {
                this.setBlockAndMetadata(world, i1, 1, k12, this.plankSlabBlock, this.plankSlabMeta | 8);
            }
            this.setBlockAndMetadata(world, i1, 1, 12, this.plankBlock, this.plankMeta);
            for(k12 = 8; k12 <= 12; ++k12) {
                this.placeRandomCake(world, random, i1, 2, k12);
            }
        }
        this.setBlockAndMetadata(world, 4, 1, 8, this.plankBlock, this.plankMeta);
        this.placeRandomCake(world, random, 4, 2, 8);
        this.setBlockAndMetadata(world, 4, 1, 9, Blocks.cauldron, 3);
        this.setBlockAndMetadata(world, 4, 1, 10, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 4, 1, 11, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 4, 1, 12, this.plankBlock, this.plankMeta);
        this.placeRandomCake(world, random, 4, 2, 12);
        this.setBlockAndMetadata(world, 3, 1, 12, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.placeRandomCake(world, random, 3, 2, 12);
        this.setBlockAndMetadata(world, 2, 1, 12, this.plankBlock, this.plankMeta);
        this.placeBarrel(world, random, 2, 2, 12, 2, LOTRFoods.DALE_DRINK);
        this.spawnItemFrame(world, 5, 3, 9, 3, new ItemStack(Items.clock));
        LOTREntityDaleBaker baker = new LOTREntityDaleBaker(world);
        this.spawnNPCAndSetHome(baker, world, 0, 1, 8, 8);
        this.setBlockAndMetadata(world, 0, 5, -3, this.plankSlabBlock, this.plankSlabMeta | 8);
        this.setBlockAndMetadata(world, 0, 6, -3, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 6, -4, this.plankSlabBlock, this.plankSlabMeta);
        this.setBlockAndMetadata(world, 0, 5, -4, this.fenceBlock, this.fenceMeta);
        this.setBlockAndMetadata(world, 0, 4, -4, this.plankBlock, this.plankMeta);
        String[] bakeryName = LOTRNames.getDaleBakeryName(random, baker.getNPCName());
        baker.setSpecificLocationName(bakeryName[0] + " " + bakeryName[1]);
        this.setBlockAndMetadata(world, -1, 4, -4, Blocks.wall_sign, 5);
        this.setBlockAndMetadata(world, 1, 4, -4, Blocks.wall_sign, 4);
        for(int i18 : new int[] {-1, 1}) {
            TileEntity te = this.getTileEntity(world, i18, 4, -4);
            if(!(te instanceof TileEntitySign)) continue;
            TileEntitySign sign = (TileEntitySign) te;
            sign.signText[1] = bakeryName[0];
            sign.signText[2] = bakeryName[1];
        }
        return true;
    }

    private void placeRandomCake(World world, Random random, int i, int j, int k) {
        if(random.nextBoolean()) {
            Block cakeBlock = null;
            if(random.nextBoolean()) {
                cakeBlock = LOTRMod.dalishPastry;
            }
            else {
                int randomCake = random.nextInt(4);
                if(randomCake == 0) {
                    cakeBlock = Blocks.cake;
                }
                else if(randomCake == 1) {
                    cakeBlock = LOTRMod.appleCrumble;
                }
                else if(randomCake == 2) {
                    cakeBlock = LOTRMod.berryPie;
                }
                else if(randomCake == 3) {
                    cakeBlock = LOTRMod.marzipanBlock;
                }
            }
            this.setBlockAndMetadata(world, i, j, k, cakeBlock, 0);
        }
    }
}
