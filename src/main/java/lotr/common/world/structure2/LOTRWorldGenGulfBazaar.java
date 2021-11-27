package lotr.common.world.structure2;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.npc.*;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenGulfBazaar extends LOTRWorldGenGulfStructure {
    private static Class[] stalls = new Class[] {Mason.class, Butcher.class, Brewer.class, Fish.class, Baker.class, Miner.class, Goldsmith.class, Lumber.class, Hunter.class, Blacksmith.class, Farmer.class};

    public LOTRWorldGenGulfBazaar(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -17; i1 <= 17; ++i1) {
                for(int k1 = -12; k1 <= 8; ++k1) {
                    j1 = this.getTopBlock(world, i1, k1) - 1;
                    if(!this.isSurface(world, i1, j1, k1)) {
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
        for(int i1 = -17; i1 <= 17; ++i1) {
            for(int k1 = -12; k1 <= 8; ++k1) {
                int i2 = Math.abs(i1);
                int k2 = Math.abs(k1);
                if(i2 >= 5 && i2 <= 9 && k2 >= 10 && k2 <= 12) {
                    for(j1 = 1; j1 <= 5; ++j1) {
                        this.setAir(world, i1, j1, k1);
                    }
                }
                if((((k1 < -6) || (k1 > -5) || (i2 > 4)) && ((k1 != -5) || (i2 < 10) || (i2 > 12)) && ((k2 != 4) || (i2 > 14)) && ((k2 < 2) || (k2 > 3) || (i2 > 15)) && ((k2 > 1) || (i2 > 16)) && ((k1 != 5) || (i2 > 12)) && ((k1 != 6) || (i2 > 9))) && (k1 != 7 || i2 > 4)) continue;
                for(j1 = 1; j1 <= 6; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("gulf_bazaar");
        this.addBlockMetaAliasOption("BRICK", 6, this.brickBlock, this.brickMeta);
        this.addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK", 8, Blocks.sandstone, 0);
        this.addBlockAliasOption("BRICK_STAIR", 6, this.brickStairBlock);
        this.addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockAliasOption("BRICK_STAIR", 8, Blocks.sandstone_stairs);
        this.addBlockMetaAliasOption("BRICK_WALL", 6, this.brickWallBlock, this.brickWallMeta);
        this.addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wallStoneV, 4);
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("BEAM2", this.beam2Block, this.beam2Meta);
        this.associateBlockMetaAlias("BEAM2|4", this.beam2Block, this.beam2Meta | 4);
        this.associateBlockMetaAlias("BEAM2|8", this.beam2Block, this.beam2Meta | 8);
        this.addBlockMetaAliasOption("GROUND", 10, Blocks.sand, 0);
        this.addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 2, LOTRMod.dirtPath, 0);
        this.associateBlockMetaAlias("WOOL", Blocks.wool, 14);
        this.associateBlockMetaAlias("CARPET", Blocks.carpet, 14);
        this.associateBlockMetaAlias("WOOL2", Blocks.wool, 15);
        this.associateBlockMetaAlias("CARPET2", Blocks.carpet, 15);
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeAnimalJar(world, -5, 4, -2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -7, 5, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeWallBanner(world, -3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 3, 4, -7, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, -7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, -8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, -6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        this.placeWallBanner(world, 7, 10, -8, LOTRItemBanner.BannerType.HARAD_GULF, 2);
        this.placeWallBanner(world, 7, 10, -6, LOTRItemBanner.BannerType.HARAD_GULF, 0);
        this.placeWallBanner(world, 6, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 3);
        this.placeWallBanner(world, 8, 10, -7, LOTRItemBanner.BannerType.HARAD_GULF, 1);
        for(int i1 : new int[] {-7, 7}) {
            j1 = 1;
            int k1 = -11;
            LOTREntityGulfHaradWarrior guard = new LOTREntityGulfHaradWarrior(world);
            guard.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(guard, world, i1, j1, k1, 4);
        }
        ArrayList<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
        while(stallClasses.size() > 5) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2) stallClasses.get(3).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall4 = (LOTRWorldGenStructureBase2) stallClasses.get(4).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            this.generateSubstructure(stall0, world, random, -9, 1, 0, 3);
            this.generateSubstructure(stall1, world, random, -5, 1, 5, 1);
            this.generateSubstructure(stall2, world, random, 0, 1, 6, 1);
            this.generateSubstructure(stall3, world, random, 8, 1, 2, 3);
            this.generateSubstructure(stall4, world, random, 11, 1, -2, 0);
        }
        catch(Exception e) {
            e.printStackTrace();
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
            this.setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, 1, 2, 3, Blocks.hay_block, 0);
            this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3)), true);
            this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
            LOTREntityGulfFarmer trader = new LOTREntityGulfFarmer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
            return true;
        }
    }

    private static class Blacksmith extends LOTRWorldGenStructureBase2 {
        public Blacksmith(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 2, 1, 2, Blocks.anvil, 3);
            this.placeArmorStand(world, 1, 1, 2, 0, new ItemStack[] {null, new ItemStack(LOTRMod.bodyGulfHarad), null, null});
            this.placeWeaponRack(world, 0, 2, 2, 1, new LOTRWorldGenGulfBazaar(false).getRandomGulfWeapon(random));
            this.placeWeaponRack(world, 3, 2, 2, 3, new LOTRWorldGenGulfBazaar(false).getRandomGulfWeapon(random));
            LOTREntityGulfBlacksmith trader = new LOTREntityGulfBlacksmith(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
            this.placeSkull(world, random, 2, 3, 2);
            this.placeSkull(world, random, 3, 2, 2);
            this.spawnItemFrame(world, 2, 2, 2, 2, new ItemStack(LOTRMod.lionFur));
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
            this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.spearHarad));
            LOTREntityGulfHunter trader = new LOTREntityGulfHunter(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.wood8, 3);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.wood8, 3);
            this.placeFlowerPot(world, 0, 2, 2, new ItemStack(Blocks.sapling, 1, 4));
            this.placeFlowerPot(world, 3, 2, 1, new ItemStack(LOTRMod.sapling8, 1, 3));
            LOTREntityGulfLumberman trader = new LOTREntityGulfLumberman(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
            return true;
        }
    }

    private static class Goldsmith extends LOTRWorldGenStructureBase2 {
        public Goldsmith(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.birdCage, 3);
            this.setBlockAndMetadata(world, 2, 3, 2, LOTRMod.goldBars, 0);
            this.placeFlowerPot(world, 0, 2, 1, this.getRandomFlower(world, random));
            LOTREntityGulfGoldsmith trader = new LOTREntityGulfGoldsmith(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.oreCopper, 0);
            this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.pickaxeBronze));
            LOTREntityGulfMiner trader = new LOTREntityGulfMiner(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, Blocks.furnace, 2);
            this.setBlockAndMetadata(world, 1, 1, 2, LOTRMod.chestBasket, 2);
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
            this.setBlockAndMetadata(world, 3, 2, 2, LOTRMod.bananaCake, 0);
            this.placeWeaponRack(world, 0, 2, 2, 1, new ItemStack(LOTRMod.rollingPin));
            LOTREntityGulfBaker trader = new LOTREntityGulfBaker(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, Blocks.cauldron, 3);
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
            this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placeWeaponRack(world, 1, 2, 3, 0, new ItemStack(Items.fishing_rod));
            LOTREntityGulfFishmonger trader = new LOTREntityGulfFishmonger(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.barrel, 3);
            this.placeMug(world, random, 1, 2, 0, 0, LOTRFoods.GULF_HARAD_DRINK);
            this.placeMug(world, random, 0, 2, 2, 3, LOTRFoods.GULF_HARAD_DRINK);
            this.placeMug(world, random, 3, 2, 1, 1, LOTRFoods.GULF_HARAD_DRINK);
            this.placeFlowerPot(world, 2, 2, 3, this.getRandomFlower(world, random));
            LOTREntityGulfBrewer trader = new LOTREntityGulfBrewer(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.placePlate_item(world, random, 1, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.rabbitRaw, 1 + random.nextInt(3)), true);
            this.placePlate_item(world, random, 0, 2, 2, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3)), true);
            this.placePlate_item(world, random, 3, 2, 1, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3)), true);
            this.placeSkull(world, random, 2, 2, 3);
            LOTREntityGulfButcher trader = new LOTREntityGulfButcher(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
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
            this.setBlockAndMetadata(world, 2, 1, 2, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, 2, 2, 2, LOTRMod.brick3, 13);
            this.placeFlowerPot(world, 0, 2, 2, this.getRandomFlower(world, random));
            this.placeWeaponRack(world, 3, 2, 2, 3, new ItemStack(LOTRMod.pickaxeBronze));
            LOTREntityGulfMason trader = new LOTREntityGulfMason(world);
            this.spawnNPCAndSetHome(trader, world, 2, 1, 1, 4);
            return true;
        }
    }

}
