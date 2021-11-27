package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarBazaar extends LOTRWorldGenSouthronBazaar {
    private static Class[] stallsUmbar = new Class[] {Lumber.class, Mason.class, Fish.class, Baker.class, Goldsmith.class, Farmer.class, Blacksmith.class, Brewer.class, Miner.class, Florist.class, Butcher.class};

    public LOTRWorldGenUmbarBazaar(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }

    @Override
    protected Class[] getStallClasses() {
        return stallsUmbar;
    }

    private static class Butcher extends LOTRWorldGenStructureBase2 {
        public Butcher(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
            this.placeKebabStand(world, random, 0, 2, 1, LOTRMod.kebabStand, 3);
            this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 1), true);
            LOTREntityUmbarButcher trader = new LOTREntityUmbarButcher(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }

    private static class Florist extends LOTRWorldGenStructureBase2 {
        public Florist(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.placeFlowerPot(world, -2, 2, 0, this.getRandomFlower(world, random));
            this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
            this.setBlockAndMetadata(world, -1, 0, 1, Blocks.grass, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.doubleFlower, 3);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.doubleFlower, 11);
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.grass, 0);
            this.plantFlower(world, random, 1, 2, 1);
            this.setBlockAndMetadata(world, 1, 1, 0, Blocks.trapdoor, 12);
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.trapdoor, 15);
            LOTREntityUmbarFlorist trader = new LOTREntityUmbarFlorist(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.iron_ore, 0);
            this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(Items.iron_pickaxe));
            LOTREntityUmbarMiner trader = new LOTREntityUmbarMiner(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
            this.placeMug(world, random, -2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
            this.placeMug(world, random, 2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
            LOTREntityUmbarBrewer trader = new LOTREntityUmbarBrewer(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, -1, 1, 1, Blocks.anvil, 3);
            this.placeArmorStand(world, 1, 1, 1, 0, new ItemStack[] {new ItemStack(LOTRMod.helmetNearHarad), new ItemStack(LOTRMod.bodyNearHarad), null, null});
            this.placeWeaponRack(world, -2, 2, 0, 1, new LOTRWorldGenUmbarBazaar(false).getRandomHaradWeapon(random));
            this.placeWeaponRack(world, 2, 2, 0, 3, new LOTRWorldGenUmbarBazaar(false).getRandomHaradWeapon(random));
            LOTREntityNearHaradBlacksmith trader = new LOTREntityNearHaradBlacksmith(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }

    private static class Farmer extends LOTRWorldGenStructureBase2 {
        public Farmer(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.hay_block, 0);
            this.setBlockAndMetadata(world, -1, 1, -1, Blocks.hay_block, 0);
            this.placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.lettuce, 1 + random.nextInt(3), 1), true);
            LOTREntityUmbarFarmer trader = new LOTREntityUmbarFarmer(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, -1, 1, -1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, 1, 1, -1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.goldBars, 0);
            this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, LOTRMod.birdCage, 2);
            this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, LOTRMod.birdCage, 3);
            LOTREntityUmbarGoldsmith trader = new LOTREntityUmbarGoldsmith(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.furnace, 2);
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.planks2, 2);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.planks2, 2);
            this.placePlate_item(world, random, -1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(Items.bread, 1 + random.nextInt(3)), true);
            this.placePlate_item(world, random, 1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3)), true);
            this.placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, this.getRandomFlower(world, random));
            LOTREntityUmbarBaker trader = new LOTREntityUmbarBaker(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, -1, 1, -1, Blocks.sponge, 0);
            this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 0), true);
            this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.fish, 1 + random.nextInt(3), 1), true);
            LOTREntityUmbarFishmonger trader = new LOTREntityUmbarFishmonger(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.brick6, 6);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.slabSingle13, 2);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick6, 9);
            this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(Items.iron_pickaxe));
            LOTREntityUmbarMason trader = new LOTREntityUmbarMason(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.wood4, 10);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.wood4, 2);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.wood4, 2);
            this.placeFlowerPot(world, -2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
            this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling8, 1, 3));
            LOTREntityUmbarLumberman trader = new LOTREntityUmbarLumberman(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }

}
