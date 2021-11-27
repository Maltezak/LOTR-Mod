package lotr.common.world.structure2;

import java.util.*;

import lotr.common.*;
import lotr.common.entity.animal.*;
import lotr.common.entity.npc.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenNomadBazaarTent extends LOTRWorldGenNomadStructure {
    private static Class[] stalls = new Class[] {Mason.class, Brewer.class, Miner.class, Armourer.class};

    public LOTRWorldGenNomadBazaarTent(boolean flag) {
        super(flag);
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int j1;
        this.setOriginAndRotation(world, i, j, k, rotation, 7);
        this.setupRandomBlocks(random);
        if(this.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for(int i1 = -14; i1 <= 14; ++i1) {
                for(int k1 = -6; k1 <= 8; ++k1) {
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
                    if(maxHeight - minHeight <= 8) continue;
                    return false;
                }
            }
        }
        for(int i1 = -14; i1 <= 14; ++i1) {
            for(int k1 = -6; k1 <= 8; ++k1) {
                Math.abs(i1);
                Math.abs(k1);
                if(!this.isSurface(world, i1, 0, k1)) {
                    this.laySandBase(world, i1, 0, k1);
                }
                for(j1 = 1; j1 <= 8; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("nomad_bazaar");
        this.associateBlockMetaAlias("PLANK", this.plankBlock, this.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", this.plankSlabBlock, this.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", this.plankSlabBlock, this.plankSlabMeta | 8);
        this.associateBlockAlias("PLANK_STAIR", this.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockMetaAlias("BEAM", this.beamBlock, this.beamMeta);
        this.associateBlockMetaAlias("TENT", this.tentBlock, this.tentMeta);
        this.associateBlockMetaAlias("TENT2", this.tent2Block, this.tent2Meta);
        this.associateBlockMetaAlias("CARPET", this.carpetBlock, this.carpetMeta);
        this.associateBlockMetaAlias("CARPET2", this.carpet2Block, this.carpet2Meta);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeSkull(world, random, -8, 2, -4);
        this.placeBarrel(world, random, 7, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
        this.placeBarrel(world, random, 8, 2, -4, 3, LOTRFoods.NOMAD_DRINK);
        this.placeAnimalJar(world, -7, 2, -4, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
        this.placeAnimalJar(world, 9, 1, 5, LOTRMod.birdCageWood, 0, null);
        this.placeAnimalJar(world, 4, 3, 2, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, -4, 4, 5, LOTRMod.birdCage, 2, new LOTREntityBird(world));
        this.placeAnimalJar(world, -4, 5, -1, LOTRMod.birdCage, 0, new LOTREntityBird(world));
        this.placeAnimalJar(world, 0, 5, 5, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
        ArrayList<Class> stallClasses = new ArrayList<>(Arrays.asList(stalls));
        while(stallClasses.size() > 3) {
            stallClasses.remove(random.nextInt(stallClasses.size()));
        }
        try {
            LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2) stallClasses.get(0).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2) stallClasses.get(1).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2) stallClasses.get(2).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
            this.generateSubstructure(stall0, world, random, -4, 1, 6, 0);
            this.generateSubstructure(stall1, world, random, 0, 1, 6, 0);
            this.generateSubstructure(stall2, world, random, 4, 1, 6, 0);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private static class Armourer extends LOTRWorldGenStructureBase2 {
        public Armourer(boolean flag) {
            super(flag);
        }

        @Override
        public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
            this.setOriginAndRotation(world, i, j, k, rotation, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, Blocks.anvil, 1);
            this.placeArmorStand(world, 0, 1, 1, 0, new ItemStack[] {new ItemStack(LOTRMod.helmetMoredainLion), new ItemStack(LOTRMod.bodyHarnedor), new ItemStack(LOTRMod.legsNomad), new ItemStack(LOTRMod.bootsNomad)});
            this.placeWeaponRack(world, -1, 2, -2, 2, new LOTRWorldGenNomadBazaarTent(false).getRandomNomadWeapon(random));
            LOTREntityNomadArmourer trader = new LOTREntityNomadArmourer(world);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, LOTRMod.oreCopper, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreTin, 0);
            this.setBlockAndMetadata(world, 1, 2, 1, Blocks.lapis_ore, 0);
            this.setBlockAndMetadata(world, 1, 1, 0, Blocks.lapis_ore, 0);
            this.placeWeaponRack(world, 0, 2, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
            LOTREntityNomadMiner trader = new LOTREntityNomadMiner(world);
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
            this.setBlockAndMetadata(world, 0, 1, 1, Blocks.cauldron, 3);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
            this.placeMug(world, random, -1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
            this.placeMug(world, random, 1, 2, -2, 0, LOTRFoods.NOMAD_DRINK);
            LOTREntityNomadBrewer trader = new LOTREntityNomadBrewer(world);
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
            this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, -1, 3, 1, LOTRMod.redSandstone, 0);
            this.setBlockAndMetadata(world, -1, 1, 0, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, -1, 2, 0, Blocks.sandstone, 0);
            this.setBlockAndMetadata(world, 0, 1, 1, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, 0, 2, 1, LOTRMod.slabSingle4, 0);
            this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick, 15);
            this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.slabSingle4, 0);
            this.placeWeaponRack(world, 1, 3, 1, 6, new ItemStack(LOTRMod.pickaxeBronze));
            LOTREntityNomadMason trader = new LOTREntityNomadMason(world);
            this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
            return true;
        }
    }

}
