package lotr.common.world.structure2;

import java.util.Random;

import lotr.common.entity.npc.LOTRNames;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGulfVillageSign extends LOTRWorldGenGulfStructure {
    private String[] signText = LOTRNames.getHaradVillageName(new Random());

    public LOTRWorldGenGulfVillageSign(boolean flag) {
        super(flag);
    }

    public LOTRWorldGenGulfVillageSign setSignText(String[] s) {
        this.signText = s;
        return this;
    }

    @Override
    public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
        int k1;
        int i1;
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if(this.restrictions && !this.isSurface(world, i1 = 0, this.getTopBlock(world, i1, k1 = 0) - 1, k1)) {
            return false;
        }
        for(int j12 = 0; (((j12 >= 0) || !this.isOpaque(world, 0, j12, 0)) && (this.getY(j12) >= 0)); --j12) {
            this.setBlockAndMetadata(world, 0, j12, 0, this.boneBlock, this.boneMeta);
            this.setGrassToDirt(world, 0, j12 - 1, 0);
        }
        this.setBlockAndMetadata(world, 0, 1, 0, this.boneBlock, this.boneMeta);
        this.setBlockAndMetadata(world, 0, 2, 0, this.boneBlock, this.boneMeta);
        this.setBlockAndMetadata(world, 0, 3, 0, this.boneBlock, this.boneMeta);
        this.setBlockAndMetadata(world, 0, 4, 0, this.boneBlock, this.boneMeta);
        this.setBlockAndMetadata(world, -1, 4, 0, this.boneWallBlock, this.boneWallMeta);
        this.setBlockAndMetadata(world, 1, 4, 0, this.boneWallBlock, this.boneWallMeta);
        this.setBlockAndMetadata(world, 0, 4, -1, this.boneWallBlock, this.boneWallMeta);
        this.setBlockAndMetadata(world, 0, 4, 1, this.boneWallBlock, this.boneWallMeta);
        this.placeSkull(world, random, -1, 5, 0);
        this.placeSkull(world, random, 1, 5, 0);
        this.placeSkull(world, random, 0, 5, -1);
        this.placeSkull(world, random, 0, 5, 1);
        this.setBlockAndMetadata(world, -1, 3, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 1, 3, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 3, -1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 3, 1, Blocks.torch, 3);
        if(this.signText != null) {
            this.placeSign(world, -1, 2, 0, Blocks.wall_sign, 5, this.signText);
            this.placeSign(world, 1, 2, 0, Blocks.wall_sign, 4, this.signText);
            this.placeSign(world, 0, 2, -1, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 0, 2, 1, Blocks.wall_sign, 3, this.signText);
        }
        return true;
    }
}
