package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import net.minecraft.block.Block;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainVillageFarm extends LOTRWorldGenTauredainHouse {
    private Block cropBlock;
    private int cropMeta;
    private Item seedItem;
    private boolean melon;

    public LOTRWorldGenTauredainVillageFarm(boolean flag) {
        super(flag);
    }

    @Override
    protected int getOffset() {
        return 4;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int i1;
        if(!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
            return false;
        }
        int randomCrop = random.nextInt(8);
        if(randomCrop == 0 || randomCrop == 1) {
            this.cropBlock = Blocks.potatoes;
            this.cropMeta = 7;
            this.seedItem = Items.potato;
            this.melon = false;
        }
        else if(randomCrop == 2 || randomCrop == 3) {
            this.cropBlock = LOTRMod.cornStalk;
            this.cropMeta = 0;
            this.seedItem = Item.getItemFromBlock(LOTRMod.cornStalk);
            this.melon = false;
        }
        else if(randomCrop == 4) {
            this.cropBlock = Blocks.wheat;
            this.cropMeta = 7;
            this.seedItem = Items.wheat_seeds;
            this.melon = false;
        }
        else if(randomCrop == 5) {
            this.cropBlock = Blocks.carrots;
            this.cropMeta = 7;
            this.seedItem = Items.carrot;
            this.melon = false;
        }
        else if(randomCrop == 6 || randomCrop == 7) {
            this.cropBlock = Blocks.melon_stem;
            this.cropMeta = 7;
            this.seedItem = Items.melon_seeds;
            this.melon = true;
        }
        for(i1 = -4; i1 <= 4; ++i1) {
            for(int k1 = -3; k1 <= 3; ++k1) {
                for(int j1 = 1; j1 <= 4; ++j1) {
                    this.setAir(world, i1, j1, k1);
                }
            }
        }
        this.loadStrScan("taurethrim_farm");
        this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", this.brickWallBlock, this.brickWallMeta);
        this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
        this.associateBlockMetaAlias("CROP", this.cropBlock, this.cropMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        if(this.melon) {
            for(int k1 = -2; k1 <= 2; ++k1) {
                this.setBlockAndMetadata(world, 0, 1, k1, this.brickBlock, this.brickMeta);
            }
            for(i1 = -1; i1 <= 1; ++i1) {
                this.setBlockAndMetadata(world, i1, 0, 0, Blocks.stained_hardened_clay, 12);
                this.setBlockAndMetadata(world, i1, 1, 0, Blocks.water, 0);
                this.setAir(world, i1, 2, 0);
            }
            for(int k1 : new int[] {-1, 1}) {
                for(int i12 = -3; i12 <= 3; ++i12) {
                    if(i12 == 0) continue;
                    this.setBlockAndMetadata(world, i12, 0, k1, Blocks.sand, 0);
                    this.setBlockAndMetadata(world, i12, 1, k1, LOTRMod.mudGrass, 0);
                }
            }
        }
        if(random.nextInt(3) == 0) {
            LOTREntityTauredainFarmer farmer = new LOTREntityTauredainFarmer(world);
            this.spawnNPCAndSetHome(farmer, world, 0, 2, 1, 4);
        }
        LOTREntityTauredainFarmhand farmhand1 = new LOTREntityTauredainFarmhand(world);
        farmhand1.seedsItem = this.seedItem;
        this.spawnNPCAndSetHome(farmhand1, world, -2, 2, 0, 6);
        LOTREntityTauredainFarmhand farmhand2 = new LOTREntityTauredainFarmhand(world);
        farmhand2.seedsItem = this.seedItem;
        this.spawnNPCAndSetHome(farmhand2, world, 2, 2, 0, 6);
        return true;
    }
}
