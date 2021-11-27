package lotr.common.world.structure2;

import java.util.Random;

import com.google.common.math.IntMath;

import lotr.common.entity.animal.LOTREntityLion;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class LOTRWorldGenTicketBooth extends LOTRWorldGenEasterlingStructureTown {
    public LOTRWorldGenTicketBooth(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        int j12;
        int j13;
        int i1;
        int k1;
        int i12;
        this.setOriginAndRotation(world, i, j, k, rotation, 3, 3);
        this.setupRandomBlocks(random);
        int woolType = 14;
        Block woodBlock = Blocks.planks;
        int woodMeta = 0;
        Block stairBlock = Blocks.oak_stairs;
        Block seatBlock = Blocks.oak_stairs;
        Block fillerBlock = this.getBiome(world, 0, 0).fillerBlock;
        for(k1 = -2; k1 <= 15; ++k1) {
            for(i12 = -2; i12 <= 9; ++i12) {
                if(i12 >= 5 && k1 <= 1) continue;
                this.setBlockAndMetadata(world, i12, 0, k1, Blocks.cobblestone, 0);
                j13 = -1;
                while(!this.isOpaque(world, i12, j13, k1) && this.getY(j13) >= 0) {
                    this.setBlockAndMetadata(world, i12, j13, k1, fillerBlock, 0);
                    this.setGrassToDirt(world, i12, j13 - 1, k1);
                    --j13;
                }
                for(j13 = 1; j13 <= 3; ++j13) {
                    this.setBlockAndMetadata(world, i12, j13, k1, woodBlock, woodMeta);
                }
                if(k1 > 2) {
                    for(j13 = 4; j13 <= 5; ++j13) {
                        this.setBlockAndMetadata(world, i12, j13, k1, woodBlock, woodMeta);
                    }
                }
                this.setBlockAndMetadata(world, i12, 2, k1, Blocks.stonebrick, 0);
            }
        }
        for(k1 = 3; k1 <= 14; ++k1) {
            for(i12 = -1; i12 <= 8; ++i12) {
                for(j13 = 1; j13 <= 4; ++j13) {
                    this.setAir(world, i12, j13, k1);
                }
                if(k1 > 9 || IntMath.mod(k1, 2) != 1) {
                    this.setBlockAndMetadata(world, i12, 0, k1, Blocks.wool, woolType);
                }
                if(k1 <= 9 && IntMath.mod(k1, 2) == 1 && i12 != 3 && i12 != 4) {
                    this.setBlockAndMetadata(world, i12, 1, k1, seatBlock, 3);
                }
                if(i12 != 3 && i12 != 4) continue;
                this.setBlockAndMetadata(world, i12, 0, k1, Blocks.cobblestone, 0);
            }
        }
        for(j12 = 0; j12 <= 4; ++j12) {
            for(i12 = 2; i12 <= 5; ++i12) {
                if(j12 >= 1 && j12 <= 3 && i12 >= 3 && i12 <= 4) {
                    this.setBlockAndMetadata(world, i12, j12, 14, Blocks.stained_hardened_clay, 15);
                    continue;
                }
                this.setBlockAndMetadata(world, i12, j12, 14, Blocks.hardened_clay, 0);
            }
        }
        for(k1 = -2; k1 <= 2; ++k1) {
            for(j1 = 1; j1 <= 2; ++j1) {
                this.setAir(world, 3, j1, k1);
            }
        }
        for(k1 = -1; k1 <= 0; ++k1) {
            for(j1 = 1; j1 <= 2; ++j1) {
                for(int i13 = -1; i13 <= 1; ++i13) {
                    this.setAir(world, i13, j1, k1);
                    if(k1 != -1 || j1 != 2) continue;
                    this.setAir(world, i13, j1, k1 - 1);
                }
            }
        }
        this.setBlockAndMetadata(world, -1, 2, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 2, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 0, 1, -2, Blocks.fence, 0);
        this.setBlockAndMetadata(world, -1, 2, -2, Blocks.glass_pane, 0);
        this.setBlockAndMetadata(world, 1, 2, -2, Blocks.glass_pane, 0);
        for(k1 = 4; k1 <= 14; ++k1) {
            this.setBlockAndMetadata(world, -1, 4, k1, stairBlock, 4);
            this.setBlockAndMetadata(world, 8, 4, k1, stairBlock, 5);
        }
        for(i1 = 0; i1 <= 7; ++i1) {
            this.setBlockAndMetadata(world, i1, 4, 3, stairBlock, 7);
            if(i1 > 1 && i1 < 6) continue;
            this.setBlockAndMetadata(world, i1, 4, 14, stairBlock, 6);
        }
        for(j12 = 0; j12 <= 4; ++j12) {
            Block block = woodBlock;
            int meta = woodMeta;
            if(j12 == 2) {
                block = Blocks.glowstone;
                meta = 0;
            }
            this.setBlockAndMetadata(world, -1, j12, 3, block, meta);
            this.setBlockAndMetadata(world, -1, j12, 14, block, meta);
            this.setBlockAndMetadata(world, 8, j12, 3, block, meta);
            this.setBlockAndMetadata(world, 8, j12, 14, block, meta);
        }
        for(i1 = -2; i1 <= 4; ++i1) {
            if(i1 == 3) {
                this.setBlockAndMetadata(world, i1, 3, -3, woodBlock, woodMeta);
                continue;
            }
            this.setBlockAndMetadata(world, i1, 3, -3, stairBlock, 2);
        }
        for(k1 = -2; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, -3, 3, k1, stairBlock, 1);
        }
        for(k1 = -2; k1 <= 0; ++k1) {
            this.setBlockAndMetadata(world, 5, 3, k1, stairBlock, 0);
        }
        this.generateSupports(world, 5, 3, 1, stairBlock, 2, woodBlock, woodMeta);
        for(i1 = 6; i1 <= 9; ++i1) {
            this.setBlockAndMetadata(world, i1, 3, 1, stairBlock, 2);
        }
        for(i1 = -2; i1 <= 9; ++i1) {
            this.setBlockAndMetadata(world, i1, 5, 2, stairBlock, 2);
            this.setBlockAndMetadata(world, i1, 5, 16, stairBlock, 3);
        }
        for(k1 = 3; k1 <= 15; ++k1) {
            this.setBlockAndMetadata(world, -3, 5, k1, stairBlock, 1);
            this.setBlockAndMetadata(world, 10, 5, k1, stairBlock, 0);
        }
        this.setBlockAndMetadata(world, 10, 3, 2, stairBlock, 0);
        this.setBlockAndMetadata(world, 10, 3, 3, stairBlock, 0);
        this.generateSupports(world, -3, 3, -3, stairBlock, 2, woodBlock, woodMeta);
        this.generateSupports(world, 5, 3, -3, stairBlock, 2, woodBlock, woodMeta);
        this.generateSupports(world, 10, 3, 1, stairBlock, 2, woodBlock, woodMeta);
        this.generateSupports(world, 10, 3, 4, stairBlock, 3, woodBlock, woodMeta);
        this.generateSupports(world, -3, 3, 4, stairBlock, 3, woodBlock, woodMeta);
        this.setBlockAndMetadata(world, -3, 5, 2, stairBlock, 2);
        this.setBlockAndMetadata(world, 10, 5, 2, stairBlock, 2);
        this.generateSupports(world, -3, 5, 16, stairBlock, 3, woodBlock, woodMeta);
        this.generateSupports(world, 10, 5, 16, stairBlock, 3, woodBlock, woodMeta);
        this.setBlockAndMetadata(world, 3, 1, -2, Blocks.wooden_door, 1);
        this.setBlockAndMetadata(world, 3, 2, -2, Blocks.wooden_door, 8);
        for(k1 = 5; k1 <= 12; ++k1) {
            if(IntMath.mod(k1, 3) == 1) continue;
            this.setBlockAndMetadata(world, -1, 2, k1, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 8, 2, k1, Blocks.torch, 1);
        }
        for(i1 = 1; i1 <= 6; ++i1) {
            if(i1 <= 1 || i1 >= 6) {
                this.setBlockAndMetadata(world, i1, 2, 14, Blocks.torch, 4);
            }
            if(i1 > 2 && i1 < 5) continue;
            this.setBlockAndMetadata(world, i1, 2, 3, Blocks.torch, 3);
        }
        this.setBlockAndMetadata(world, -2, 2, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 2, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 4, 2, -3, Blocks.torch, 4);
        this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, new String[] {"---------------", "Now showing:", "The Lion King", "---------------"});
        LOTREntityLion lion = new LOTREntityLion(world);
        lion.setCustomNameTag("Ticket Lion");
        lion.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0E8);
        lion.setHealth(lion.getMaxHealth());
        this.spawnNPCAndSetHome(lion, world, 0, 1, -1, 4);
        this.setBlockAndMetadata(world, 0, 1, 2, Blocks.chest, 3);
        TileEntityChest chest = (TileEntityChest) this.getTileEntity(world, 0, 1, 2);
        if(chest != null) {
            int lootAmount = 2 + random.nextInt(4);
            for(int l = 0; l < lootAmount; ++l) {
                chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getBasicLoot(random));
            }
        }
        this.setBlockAndMetadata(world, 0, 2, 2, Blocks.trapdoor, 1);
        this.placeSign(world, 3, 2, 13, Blocks.wall_sign, 2, new String[] {"", "Showings", "postponed", ""});
        this.placeSign(world, 4, 2, 13, Blocks.wall_sign, 2, new String[] {"", "until further", "notice.", ""});
        return true;
    }

    private ItemStack getBasicLoot(Random random) {
        int i = random.nextInt(11);
        switch(i) {
            default: {
                return new ItemStack(Items.stick, 2 + random.nextInt(4));
            }
            case 1: {
                return new ItemStack(Items.paper, 1 + random.nextInt(3));
            }
            case 2: {
                return new ItemStack(Items.book, 1 + random.nextInt(2));
            }
            case 3: {
                return new ItemStack(Items.bread, 3 + random.nextInt(2));
            }
            case 4: {
                return new ItemStack(Items.compass);
            }
            case 5: {
                return new ItemStack(Items.gold_nugget, 2 + random.nextInt(6));
            }
            case 6: {
                return new ItemStack(Items.apple, 1 + random.nextInt(3));
            }
            case 7: {
                return new ItemStack(Items.string, 2 + random.nextInt(2));
            }
            case 8: {
                return new ItemStack(Items.bowl, 1 + random.nextInt(4));
            }
            case 9: {
                return new ItemStack(Items.cookie, 1 + random.nextInt(3));
            }
            case 10:
        }
        return new ItemStack(Items.coal, 1 + random.nextInt(2));
    }

    private void generateSupports(World world, int i, int j, int k, Block stairBlock, int stairMeta, Block woodBlock, int woodMeta) {
        this.setBlockAndMetadata(world, i, j, k, stairBlock, stairMeta);
        int j1 = -1;
        while(!this.isOpaque(world, i, j + j1, k) && this.getY(j + j1) >= 0) {
            Block block = Blocks.fence;
            int meta = 0;
            Block below = world.getBlock(i, j + j1, k);
            if(below.getMaterial().isLiquid()) {
                block = Blocks.planks;
                meta = woodMeta;
            }
            this.setBlockAndMetadata(world, i, j + j1, k, block, meta);
            --j1;
        }
    }

    public static boolean generatesAt(World world, int i, int k) {
        return LOTRFixedStructures.generatesAtMapImageCoords(i, k, 1583, 2527);
    }
}
