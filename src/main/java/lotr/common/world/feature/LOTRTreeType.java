package lotr.common.world.feature;

import java.util.Random;

import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.gen.feature.*;

public enum LOTRTreeType {
    OAK(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(4) == 0) {
                return new LOTRWorldGenGnarledOak(flag);
            }
            return new LOTRWorldGenSimpleTrees(flag, 4, 6, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_TALL(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(4) == 0) {
                return new LOTRWorldGenGnarledOak(flag).setMinMaxHeight(6, 10);
            }
            return new LOTRWorldGenSimpleTrees(flag, 8, 12, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_TALLER(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 12, 16, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_ITHILIEN_HIDEOUT(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 6, 6, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBigTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_FANGORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_FANGORN_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 0, Blocks.leaves, 0).setNoLeaves();
        }
    }), OAK_SWAMP(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenSwamp();
        }
    }), OAK_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log, 0);
        }
    }), OAK_DESERT(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDesertTrees(flag, Blocks.log, 0, Blocks.leaves, 0);
        }
    }), OAK_SHRUB(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenShrub(Blocks.log, 0, Blocks.leaves, 0);
        }
    }), BIRCH(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(3) != 0) {
                return new LOTRWorldGenAspen(flag).setBlocks(Blocks.log, 2, Blocks.leaves, 2).setMinMaxHeight(8, 16);
            }
            return new LOTRWorldGenSimpleTrees(flag, 5, 7, Blocks.log, 2, Blocks.leaves, 2);
        }
    }), BIRCH_TALL(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 8, 11, Blocks.log, 2, Blocks.leaves, 2);
        }
    }), BIRCH_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBigTrees(flag, Blocks.log, 2, Blocks.leaves, 2);
        }
    }), BIRCH_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(Blocks.log, 2, Blocks.leaves, 2);
        }
    }), BIRCH_FANGORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 2, Blocks.leaves, 2);
        }
    }), BIRCH_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log, 2);
        }
    }), SPRUCE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenTaiga2(flag);
        }
    }), SPRUCE_THIN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenTaiga1();
        }
    }), SPRUCE_MEGA(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenMegaPineTree(flag, true);
        }
    }), SPRUCE_MEGA_THIN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenMegaPineTree(flag, false);
        }
    }), SPRUCE_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log, 1);
        }
    }), JUNGLE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenTrees(flag, 7, 3, 3, true);
        }
    }), JUNGLE_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenMegaJungle(flag, 10, 20, 3, 3);
        }
    }), JUNGLE_CLOUD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenMegaJungle(flag, 30, 30, 3, 3);
        }
    }), JUNGLE_SHRUB(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenShrub(Blocks.log, 3, Blocks.leaves, 3);
        }
    }), JUNGLE_FANGORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, Blocks.log, 3, Blocks.leaves, 3).setHeightFactor(1.5f);
        }
    }), ACACIA(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenSavannaTree(flag);
        }
    }), ACACIA_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDeadTrees(Blocks.log2, 0);
        }
    }), DARK_OAK(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new WorldGenCanopyTree(flag);
        }
    }), DARK_OAK_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(Blocks.log2, 1, Blocks.leaves2, 1);
        }
    }), SHIRE_PINE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenShirePine(flag);
        }
    }), MALLORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 6, 9, LOTRMod.wood, 1, LOTRMod.leaves, 1);
        }
    }), MALLORN_BOUGHS(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMallorn(flag);
        }
    }), MALLORN_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood, 1, LOTRMod.leaves, 1);
        }
    }), MALLORN_EXTREME(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMallornExtreme(flag);
        }
    }), MALLORN_EXTREME_SAPLING(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMallornExtreme(flag).setSaplingGrowth();
        }
    }), MIRK_OAK(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 4, 7, 0, true);
        }
    }), MIRK_OAK_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 16, 1, true);
        }
    }), MIRK_OAK_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 4, 7, 0, true).setDead();
        }
    }), RED_OAK(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 6, 9, 0, false).setRedOak();
        }
    }), RED_OAK_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 17, 1, false).setRedOak();
        }
    }), RED_OAK_WEIRWOOD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 20, 1, false).setBlocks(LOTRMod.wood9, 0, LOTRMod.leaves, 3);
        }
    }), CHARRED(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenCharredTrees();
        }
    }), CHARRED_FANGORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood, 3, Blocks.air, 0).setNoLeaves();
        }
    }), APPLE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 7, LOTRMod.fruitWood, 0, LOTRMod.fruitLeaves, 0);
        }
    }), PEAR(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 5, LOTRMod.fruitWood, 1, LOTRMod.fruitLeaves, 1);
        }
    }), CHERRY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2);
        }
    }), CHERRY_MORDOR(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.fruitWood, 2, LOTRMod.fruitLeaves, 2).disableRestrictions();
        }
    }), MANGO(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.fruitWood, 3, LOTRMod.fruitLeaves, 3);
        }
    }), LEBETHRON(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
        }
    }), LEBETHRON_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
        }
    }), LEBETHRON_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood2, 0, LOTRMod.leaves2, 0);
        }
    }), LEBETHRON_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 0);
        }
    }), BEECH(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 5, 9, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), BEECH_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), BEECH_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), BEECH_FANGORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1);
        }
    }), BEECH_FANGORN_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood2, 1, LOTRMod.leaves2, 1).setNoLeaves();
        }
    }), BEECH_DEAD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDeadTrees(LOTRMod.wood2, 1);
        }
    }), HOLLY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenHolly(flag);
        }
    }), HOLLY_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenHolly(flag).setLarge();
        }
    }), BANANA(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBanana(flag);
        }
    }), MAPLE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 8, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
        }
    }), MAPLE_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
        }
    }), MAPLE_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood3, 0, LOTRMod.leaves3, 0);
        }
    }), LARCH(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenLarch(flag);
        }
    }), DATE_PALM(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPalm(flag, LOTRMod.wood3, 2, LOTRMod.leaves3, 2).setMinMaxHeight(5, 8).setDates();
        }
    }), MANGROVE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMangrove(flag);
        }
    }), CHESTNUT(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 5, 7, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
        }
    }), CHESTNUT_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBigTrees(flag, LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
        }
    }), CHESTNUT_PARTY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPartyTrees(LOTRMod.wood4, 0, LOTRMod.leaves4, 0);
        }
    }), BAOBAB(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenBaobab(flag);
        }
    }), CEDAR(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenCedar(flag);
        }
    }), CEDAR_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenCedar(flag).setMinMaxHeight(15, 30);
        }
    }), FIR(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFir(flag);
        }
    }), PINE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPine(flag);
        }
    }), PINE_SHRUB(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenShrub(LOTRMod.wood5, 0, LOTRMod.leaves5, 0);
        }
    }), LEMON(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 1, LOTRMod.leaves5, 1);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 1, LOTRMod.leaves5, 1);
        }
    }), ORANGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 2, LOTRMod.leaves5, 2);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 2, LOTRMod.leaves5, 2);
        }
    }), LIME(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood5, 3, LOTRMod.leaves5, 3);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood5, 3, LOTRMod.leaves5, 3);
        }
    }), MAHOGANY(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenCedar(flag).setBlocks(LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHangingLeaves();
        }
    }), MAHOGANY_FANGORN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenFangornTrees(flag, LOTRMod.wood6, 0, LOTRMod.leaves6, 0).setHeightFactor(1.5f);
        }
    }), WILLOW(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenWillow(flag);
        }
    }), WILLOW_WATER(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenWillow(flag).setNeedsWater();
        }
    }), CYPRESS(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenCypress(flag);
        }
    }), CYPRESS_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenCypress(flag).setLarge();
        }
    }), OLIVE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenOlive(flag);
        }
    }), OLIVE_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenOlive(flag).setMinMaxHeight(5, 8).setExtraTrunkWidth(1);
        }
    }), ASPEN(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenAspen(flag);
        }
    }), ASPEN_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenAspen(flag).setExtraTrunkWidth(1).setMinMaxHeight(14, 25);
        }
    }), GREEN_OAK(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 4, 7, 0, false).setGreenOak();
        }
    }), GREEN_OAK_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 12, 16, 1, false).setGreenOak();
        }
    }), GREEN_OAK_EXTREME(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenMirkOak(flag, 25, 45, 2, false).setGreenOak();
        }
    }), LAIRELOSSE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenLairelosse(flag);
        }
    }), LAIRELOSSE_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenLairelosse(flag).setExtraTrunkWidth(1).setMinMaxHeight(8, 12);
        }
    }), ALMOND(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenAlmond(flag);
        }
    }), PLUM(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenSimpleTrees(flag, 4, 6, LOTRMod.wood8, 0, LOTRMod.leaves8, 0);
        }
    }), REDWOOD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenRedwood(flag);
        }
    }), REDWOOD_2(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenRedwood(flag).setExtraTrunkWidth(1);
        }
    }), REDWOOD_3(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenRedwood(flag).setTrunkWidth(1);
        }
    }), REDWOOD_4(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenRedwood(flag).setTrunkWidth(1).setExtraTrunkWidth(1);
        }
    }), REDWOOD_5(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenRedwood(flag).setTrunkWidth(2);
        }
    }), POMEGRANATE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            if(rand.nextInt(3) == 0) {
                return new LOTRWorldGenOlive(flag).setBlocks(LOTRMod.wood8, 2, LOTRMod.leaves8, 2);
            }
            return new LOTRWorldGenDesertTrees(flag, LOTRMod.wood8, 2, LOTRMod.leaves8, 2);
        }
    }), PALM(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenPalm(flag, LOTRMod.wood8, 3, LOTRMod.leaves8, 3).setMinMaxHeight(6, 11);
        }
    }), DRAGONBLOOD(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDragonblood(flag, 3, 7, 0);
        }
    }), DRAGONBLOOD_LARGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDragonblood(flag, 6, 10, 1);
        }
    }), DRAGONBLOOD_HUGE(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenDragonblood(flag, 8, 16, 2);
        }
    }), KANUKA(new ITreeFactory() {

        @Override
        public WorldGenAbstractTree createTree(boolean flag, Random rand) {
            return new LOTRWorldGenKanuka(flag);
        }
    }), NULL(null);

    private ITreeFactory treeFactory;

    LOTRTreeType(ITreeFactory factory) {
        this.treeFactory = factory;
    }

    public WorldGenAbstractTree create(boolean flag, Random rand) {
        return this.treeFactory.createTree(flag, rand);
    }

    public static class WeightedTreeType extends WeightedRandom.Item {
        public final LOTRTreeType treeType;

        public WeightedTreeType(LOTRTreeType tree, int i) {
            super(i);
            this.treeType = tree;
        }
    }

    private static interface ITreeFactory {
        WorldGenAbstractTree createTree(boolean var1, Random var2);
    }

}
