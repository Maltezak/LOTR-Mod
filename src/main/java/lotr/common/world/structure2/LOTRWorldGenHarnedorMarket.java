package lotr.common.world.structure2;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenHarnedorMarket extends LOTRWorldGenHarnedorStructure {
    private static Class[] stalls = new Class[] {Brewer.class, Fish.class, Butcher.class, Baker.class, Lumber.class, Miner.class, Mason.class, Hunter.class, Blacksmith.class, Farmer.class};

    public LOTRWorldGenHarnedorMarket(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j2;
        int k1;
        int i1;
        int j1;
        int k12;
        int i12;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i13 = -9; i13 <= 9; ++i13) {
                for(int k13 = -9; k13 <= 9; ++k13) {
                    j1 = this.getTopBlock(world, i13, k13) - 1;
                    if(!this.isSurface(world, i13, j1, k13)) {
                        return false;
                    }
                    if(j1 < minHeight) {
                        minHeight = j1;
                    }
                    if(j1 > maxHeight) {
                        maxHeight = j1;
                    }
                    if(maxHeight - minHeight <= 12) continue;
                    return false;
                }
            }
        }
        for(int i14 = -8; i14 <= 8; ++i14) {
            for(int k14 = -8; k14 <= 8; ++k14) {
                int i2 = Math.abs(i14);
                int k2 = Math.abs(k14);
                if((((i2 > 6) || (k2 > 6)) && ((i2 != 7) || (k2 > 4)) && ((k2 != 7) || (i2 > 4)) && ((i2 != 8) || (k2 > 1))) && (k2 != 8 || i2 > 1)) continue;
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i14, j1, k14);
                }
                j1 = -1;
                while(!this.isOpaque(world, i14, j1, k14) && this.getY(j1) >= 0) {
                    this.setBlockAndMetadata(world, i14, j1, k14, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i14, j1 - 1, k14);
                    --j1;
                }
            }
        }
        this.loadStrScan("harnedor_market");
        this.associateBlockMetaAlias("WOOD", this.woodBlock, this.woodMeta);
        this.associateBlockMetaAlias("WOOD|12", this.woodBlock, this.woodMeta | 0xC);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("PLANK2", this.plank2Block, this.plank2Meta);
        this.associateBlockMetaAlias("ROOF", this.roofBlock, this.roofMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeWallBanner(world, 0, 5, -2, LOTRItemBanner.BannerType.NEAR_HARAD, 2);
        this.placeWallBanner(world, 0, 5, 2, LOTRItemBanner.BannerType.NEAR_HARAD, 0);
        this.placeWallBanner(world, -2, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 3);
        this.placeWallBanner(world, 2, 5, 0, LOTRItemBanner.BannerType.NEAR_HARAD, 1);
        this.spawnItemFrame(world, 2, 2, -3, 3, this.getHarnedorFramedItem(random));
        this.spawnItemFrame(world, -2, 2, 3, 1, this.getHarnedorFramedItem(random));
        this.placeWeaponRack(world, -3, 2, 1, 6, this.getRandomHarnedorWeapon(random));
        this.placeArmorStand(world, 2, 1, -2, 2, new ItemStack[] {new ItemStack(LOTRMod.helmetHarnedor), null, null, null});
        this.placeFlowerPot(world, -2, 2, 2, this.getRandomFlower(world, random));
        this.placeAnimalJar(world, 2, 1, 1, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
        this.placeAnimalJar(world, -3, 1, -1, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -2, 3, -2, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, 6, 3, 1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeSkull(world, random, 2, 4, -5);
        ArrayList<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
        while(stallClasses.size() > 4) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            this.generateSubstructure(stall0, world, random, 2, 1, 2, 0);
            this.generateSubstructure(stall1, world, random, 2, 1, -2, 1);
            this.generateSubstructure(stall2, world, random, -2, 1, -2, 2);
            this.generateSubstructure(stall3, world, random, -2, 1, 2, 3);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            int j12;
            for(int step = 0; step < 12 && !this.isOpaque(world, i1, j12 = 0 - step, k12 = -9 - step); ++step) {
                this.setBlockAndMetadata(world, i1, j12, k12, this.plank2StairBlock, 2);
                this.setGrassToDirt(world, i1, j12 - 1, k12);
                j2 = j12 - 1;
                while(!this.isOpaque(world, i1, j2, k12) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k12, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i1, j2 - 1, k12);
                    --j2;
                }
            }
        }
        for(i1 = -1; i1 <= 1; ++i1) {
            int j13;
            for(int step = 0; step < 12 && !this.isOpaque(world, i1, j13 = 0 - step, k12 = 9 + step); ++step) {
                this.setBlockAndMetadata(world, i1, j13, k12, this.plank2StairBlock, 3);
                this.setGrassToDirt(world, i1, j13 - 1, k12);
                j2 = j13 - 1;
                while(!this.isOpaque(world, i1, j2, k12) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i1, j2, k12, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i1, j2 - 1, k12);
                    --j2;
                }
            }
        }
        for(k1 = -1; k1 <= 1; ++k1) {
            int j14;
            for(int step = 0; step < 12 && !this.isOpaque(world, i12 = -9 - step, j14 = 0 - step, k1); ++step) {
                this.setBlockAndMetadata(world, i12, j14, k1, this.plank2StairBlock, 1);
                this.setGrassToDirt(world, i12, j14 - 1, k1);
                j2 = j14 - 1;
                while(!this.isOpaque(world, i12, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k1, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i12, j2 - 1, k1);
                    --j2;
                }
            }
        }
        for(k1 = -1; k1 <= 1; ++k1) {
            int j15;
            for(int step = 0; step < 12 && !this.isOpaque(world, i12 = 9 + step, j15 = 0 - step, k1); ++step) {
                this.setBlockAndMetadata(world, i12, j15, k1, this.plank2StairBlock, 0);
                this.setGrassToDirt(world, i12, j15 - 1, k1);
                j2 = j15 - 1;
                while(!this.isOpaque(world, i12, j2, k1) && this.getY(j2) >= 0) {
                    this.setBlockAndMetadata(world, i12, j2, k1, this.plank2Block, this.plank2Meta);
                    this.setGrassToDirt(world, i12, j2 - 1, k1);
                    --j2;
                }
            }
        }
        return true;
    }

    private static class Farmer extends LOTRWorldGenStructureBase2 {
        public Farmer(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 2, 1, 4, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, 3, 1, 2, LOTRMod.berryBush, 9);
            this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.berryBush, 9);
            this.placePlate_item(world, random, 3, 2, 0, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
            this.placePlate_item(world, random, 0, 2, 4, LOTRMod.woodPlateBlock, this.getRandomFarmFood(random), true);
            LOTREntityHarnedorFarmer trader = new LOTREntityHarnedorFarmer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }

        private ItemStack getRandomFarmFood(Random random) {
            ItemStack[] items = new ItemStack[] {new ItemStack(LOTRMod.orange), new ItemStack(LOTRMod.lemon), new ItemStack(LOTRMod.lime), new ItemStack(Items.carrot), new ItemStack(Items.potato), new ItemStack(LOTRMod.lettuce), new ItemStack(LOTRMod.turnip)};
            ItemStack ret = items[random.nextInt(items.length)].copy();
            ret.stackSize = 1 + random.nextInt(3);
            return ret;
        }
    }

    private static class Blacksmith extends LOTRWorldGenStructureBase2 {
        public Blacksmith(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeWeaponRack(world, 3, 2, 0, 2, new LOTRWorldGenHarnedorMarket(false).getRandomHarnedorWeapon(random));
            this.placeWeaponRack(world, 0, 2, 4, 3, new LOTRWorldGenHarnedorMarket(false).getRandomHarnedorWeapon(random));
            this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, 3, 1, 3, Blocks.anvil, 1);
            this.placeArmorStand(world, 4, 1, 2, 0, new ItemStack[] {new ItemStack(LOTRMod.helmetHarnedor), new ItemStack(LOTRMod.bodyHarnedor), null, null});
            this.placeArmorStand(world, 2, 1, 4, 1, null);
            LOTREntityHarnedorBlacksmith trader = new LOTREntityHarnedorBlacksmith(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Hunter extends LOTRWorldGenStructureBase2 {
        public Hunter(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.placePlate_item(world, random, 3, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.deerRaw, 1 + random.nextInt(3), 0), true);
            this.spawnItemFrame(world, 4, 2, 3, 2, new ItemStack(LOTRMod.fur));
            this.spawnItemFrame(world, 3, 2, 4, 3, new ItemStack(Items.leather));
            LOTREntityHarnedorHunter trader = new LOTREntityHarnedorHunter(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Mason extends LOTRWorldGenStructureBase2 {
        public Mason(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
            this.placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(LOTRMod.pickaxeBronze));
            this.setBlockAndMetadata(world, 4, 1, 2, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, 2, 1, 3, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.redSandstone, 0);
            LOTREntityHarnedorMason trader = new LOTREntityHarnedorMason(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Miner extends LOTRWorldGenStructureBase2 {
        public Miner(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeWeaponRack(world, 2, 2, 0, 2, new ItemStack(LOTRMod.pickaxeBronze));
            this.placeWeaponRack(world, 0, 2, 3, 3, new ItemStack(LOTRMod.shovelBronze));
            this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.oreTin, 0);
            LOTREntityHarnedorMiner trader = new LOTREntityHarnedorMiner(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Lumber extends LOTRWorldGenStructureBase2 {
        public Lumber(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
            this.placeFlowerPot(world, 0, 2, 2, new ItemStack(LOTRMod.sapling8, 1, 3));
            this.placeFlowerPot(world, 0, 2, 4, new ItemStack(LOTRMod.sapling7, 1, 3));
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 2, 1, 4, LOTRMod.wood6, 3);
            this.setBlockAndMetadata(world, 2, 1, 3, LOTRMod.wood6, 11);
            this.setBlockAndMetadata(world, 4, 1, 2, LOTRMod.woodBeam8, 11);
            this.placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(LOTRMod.axeBronze));
            LOTREntityHarnedorLumberman trader = new LOTREntityHarnedorLumberman(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Baker extends LOTRWorldGenStructureBase2 {
        public Baker(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 0, 2, 4, LOTRMod.lemonCake, 0);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.marzipanBlock, 0);
            this.placeWeaponRack(world, 2, 2, 4, 7, new ItemStack(LOTRMod.rollingPin));
            LOTREntityHarnedorBaker trader = new LOTREntityHarnedorBaker(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Butcher extends LOTRWorldGenStructureBase2 {
        public Butcher(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.kebab, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 4, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.kebab, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 3, 1, 3, Blocks.furnace, 2);
            this.placeKebabStand(world, random, 3, 2, 3, LOTRMod.kebabStand, 2);
            this.setBlockAndMetadata(world, 2, 3, 3, LOTRMod.kebabBlock, 0);
            this.setBlockAndMetadata(world, 2, 4, 3, LOTRMod.fence2, 2);
            this.setBlockAndMetadata(world, 2, 5, 3, LOTRMod.fence2, 2);
            LOTREntityHarnedorButcher trader = new LOTREntityHarnedorButcher(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Fish extends LOTRWorldGenStructureBase2 {
        public Fish(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
            this.placePlate_item(world, random, 0, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placeFlowerPot(world, 0, 2, 4, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.placePlate_item(world, random, 3, 2, 3, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.setBlockAndMetadata(world, 2, 1, 4, Blocks.cauldron, 3);
            this.placeWeaponRack(world, 4, 2, 2, 6, new ItemStack(Items.fishing_rod));
            LOTREntityHarnedorFishmonger trader = new LOTREntityHarnedorFishmonger(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

    private static class Brewer extends LOTRWorldGenStructureBase2 {
        public Brewer(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeMug(world, random, 3, 2, 0, 0, LOTRFoods.HARNEDOR_DRINK);
            this.placeMug(world, random, 0, 2, 2, 1, LOTRFoods.HARNEDOR_DRINK);
            this.setBlockAndMetadata(world, 0, 2, 4, LOTRMod.barrel, 4);
            this.setBlockAndMetadata(world, 3, 1, 3, LOTRMod.woodSlabSingle4, 15);
            this.setBlockAndMetadata(world, 3, 2, 3, LOTRMod.barrel, 2);
            LOTREntityHarnedorBrewer trader = new LOTREntityHarnedorBrewer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 2, 4);
            return true;
        }
    }

}
