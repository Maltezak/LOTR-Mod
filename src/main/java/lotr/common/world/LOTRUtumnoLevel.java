package lotr.common.world;

import java.util.*;

import com.google.common.primitives.Ints;

import lotr.common.LOTRMod;
import lotr.common.world.spawning.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public enum LOTRUtumnoLevel {
    ICE(13819887, 180, 240, 4, 4), OBSIDIAN(2104109, 92, 180, 6, 5), FIRE(6295040, 0, 92, 8, 7);

    public final int fogColor;
    public final int baseLevel;
    public final int topLevel;
    public final int corridorWidth;
    public final int corridorWidthStart;
    public final int corridorWidthEnd;
    public final int corridorHeight;
    public final int[] corridorBaseLevels;
    private static boolean initSpawnLists;
    private LOTRBiomeSpawnList npcSpawnList = new LOTRBiomeSpawnList("UtumnoLevel_" + this.name());
    public Block brickBlock;
    public int brickMeta;
    public Block brickStairBlock;
    public Block brickGlowBlock;
    public int brickGlowMeta;
    public Block tileBlock;
    public int tileMeta;
    public Block pillarBlock;
    public int pillarMeta;
    private static Random lightRand;
    private static NoiseGeneratorPerlin noiseGenXZ;
    private static NoiseGeneratorPerlin noiseGenY;
    private static NoiseGeneratorPerlin corridorNoiseY;
    private static NoiseGeneratorPerlin corridorNoiseX;
    private static NoiseGeneratorPerlin corridorNoiseZ;

    LOTRUtumnoLevel(int fog, int base, int top, int cWidth, int cHeight) {
        this.fogColor = fog;
        this.baseLevel = base;
        this.topLevel = top;
        this.corridorWidth = cWidth;
        this.corridorWidthStart = 8 - cWidth / 2;
        this.corridorWidthEnd = this.corridorWidthStart + cWidth;
        this.corridorHeight = cHeight;
        ArrayList<Integer> baseLevels = new ArrayList<>();
        int y = this.baseLevel;
        while((y += this.corridorHeight * 2) < top - 5) {
            baseLevels.add(y);
        }
        this.corridorBaseLevels = Ints.toArray(baseLevels);
    }

    public int getLowestCorridorFloor() {
        return this.corridorBaseLevels[0] - 1;
    }

    public int getHighestCorridorRoof() {
        return this.corridorBaseLevels[this.corridorBaseLevels.length - 1] + this.corridorHeight;
    }

    public LOTRBiomeSpawnList getNPCSpawnList() {
        return this.npcSpawnList;
    }

    public static LOTRUtumnoLevel forY(int y) {
        for(LOTRUtumnoLevel level : LOTRUtumnoLevel.values()) {
            if(y < level.baseLevel) continue;
            return level;
        }
        return FIRE;
    }

    public static void setupLevels() {
        if(initSpawnLists) {
            return;
        }
        LOTRUtumnoLevel.ICE.brickBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.ICE.brickMeta = 2;
        LOTRUtumnoLevel.ICE.brickStairBlock = LOTRMod.stairsUtumnoBrickIce;
        LOTRUtumnoLevel.ICE.brickGlowBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.ICE.brickGlowMeta = 3;
        LOTRUtumnoLevel.ICE.tileBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.ICE.tileMeta = 6;
        LOTRUtumnoLevel.ICE.pillarBlock = LOTRMod.utumnoPillar;
        LOTRUtumnoLevel.ICE.pillarMeta = 1;
        LOTRUtumnoLevel.OBSIDIAN.brickBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.OBSIDIAN.brickMeta = 4;
        LOTRUtumnoLevel.OBSIDIAN.brickStairBlock = LOTRMod.stairsUtumnoBrickObsidian;
        LOTRUtumnoLevel.OBSIDIAN.brickGlowBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.OBSIDIAN.brickGlowMeta = 5;
        LOTRUtumnoLevel.OBSIDIAN.tileBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.OBSIDIAN.tileMeta = 7;
        LOTRUtumnoLevel.OBSIDIAN.pillarBlock = LOTRMod.utumnoPillar;
        LOTRUtumnoLevel.OBSIDIAN.pillarMeta = 2;
        LOTRUtumnoLevel.FIRE.brickBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.FIRE.brickMeta = 0;
        LOTRUtumnoLevel.FIRE.brickStairBlock = LOTRMod.stairsUtumnoBrickFire;
        LOTRUtumnoLevel.FIRE.brickGlowBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.FIRE.brickGlowMeta = 1;
        LOTRUtumnoLevel.FIRE.tileBlock = LOTRMod.utumnoBrick;
        LOTRUtumnoLevel.FIRE.tileMeta = 8;
        LOTRUtumnoLevel.FIRE.pillarBlock = LOTRMod.utumnoPillar;
        LOTRUtumnoLevel.FIRE.pillarMeta = 0;
        LOTRUtumnoLevel.ICE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_ICE, 10));
        LOTRUtumnoLevel.OBSIDIAN.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_OBSIDIAN, 10));
        LOTRUtumnoLevel.FIRE.npcSpawnList.newFactionList(100).add(LOTRBiomeSpawnList.entry(LOTRSpawnList.UTUMNO_FIRE, 10));
        initSpawnLists = true;
    }

    public static void generateTerrain(World world, Random rand, int chunkX, int chunkZ, Block[] blocks, byte[] metadata) {
        boolean hugeHoleChunk = rand.nextInt(16) == 0;
        boolean hugeRavineChunk = rand.nextInt(16) == 0;
        long seed = world.getSeed();
        lightRand.setSeed(seed *= chunkX / 2 * 67839703L + chunkZ / 2 * 368093693L);
        boolean chunkHasGlowing = lightRand.nextInt(4) > 0;
        for(int i = 0; i < 16; ++i) {
            for(int k = 0; k < 16; ++k) {
                int blockX = chunkX * 16 + i;
                int blockZ = chunkZ * 16 + k;
                double genNoiseXZHere = noiseGenXZ.func_151601_a(blockX * 0.2, blockZ * 0.2);
                double corridorNoiseYHere = corridorNoiseY.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + corridorNoiseY.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
                double corridorNoiseXHere = corridorNoiseX.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + corridorNoiseX.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
                double corridorNoiseZHere = corridorNoiseZ.func_151601_a(blockX * 0.02, blockZ * 0.02) * 0.67 + corridorNoiseZ.func_151601_a(blockX * 0.1, blockZ * 0.1) * 0.33;
                for(int j = 255; j >= 0; --j) {
                    LOTRUtumnoLevel utumnoLevel = LOTRUtumnoLevel.forY(j);
                    int blockY = j;
                    int blockIndex = (k * 16 + i) * 256 + j;
                    if(j <= 0 + rand.nextInt(5) || j >= 255 - rand.nextInt(3)) {
                        blocks[blockIndex] = Blocks.bedrock;
                    }
                    else {
                        double genNoiseYHere = noiseGenY.func_151601_a(blockY * 0.4, 0.0);
                        double genNoise = (genNoiseXZHere + genNoiseYHere * 0.5) / 1.5;
                        if(genNoise > -0.2) {
                            blocks[blockIndex] = utumnoLevel.brickBlock;
                            metadata[blockIndex] = (byte) utumnoLevel.brickMeta;
                            if(chunkHasGlowing) {
                                boolean glowing = false;
                                if(utumnoLevel == ICE && rand.nextInt(16) == 0) {
                                    glowing = true;
                                }
                                else if(utumnoLevel == OBSIDIAN && rand.nextInt(12) == 0) {
                                    glowing = true;
                                }
                                else if(utumnoLevel == FIRE && rand.nextInt(8) == 0) {
                                    glowing = true;
                                }
                                if(glowing) {
                                    blocks[blockIndex] = utumnoLevel.brickGlowBlock;
                                    metadata[blockIndex] = (byte) utumnoLevel.brickGlowMeta;
                                }
                            }
                        }
                        else if(utumnoLevel == ICE) {
                            if(genNoise < -0.5) {
                                blocks[blockIndex] = Blocks.stone;
                                metadata[blockIndex] = 0;
                            }
                            else {
                                blocks[blockIndex] = Blocks.packed_ice;
                                metadata[blockIndex] = 0;
                            }
                        }
                        else if(utumnoLevel == OBSIDIAN) {
                            if(genNoise < -0.5) {
                                blocks[blockIndex] = Blocks.stained_hardened_clay;
                                metadata[blockIndex] = 15;
                            }
                            else {
                                blocks[blockIndex] = Blocks.obsidian;
                                metadata[blockIndex] = 0;
                            }
                        }
                        else if(utumnoLevel == FIRE) {
                            blocks[blockIndex] = Blocks.obsidian;
                            metadata[blockIndex] = 0;
                        }
                        int levelFuzz = 2;
                        if(j <= utumnoLevel.getLowestCorridorFloor() - levelFuzz || j >= utumnoLevel.getHighestCorridorRoof() + levelFuzz) {
                            blocks[blockIndex] = utumnoLevel.brickBlock;
                            metadata[blockIndex] = (byte) utumnoLevel.brickMeta;
                        }
                        if(genNoise < 0.5) {
                            for(int corridorBase : utumnoLevel.corridorBaseLevels) {
                                if(j != corridorBase - 1) continue;
                                blocks[blockIndex] = utumnoLevel.tileBlock;
                                metadata[blockIndex] = (byte) utumnoLevel.tileMeta;
                            }
                        }
                    }
                    int actingY = j;
                    actingY += (int) Math.round(corridorNoiseYHere * 1.15);
                    actingY = MathHelper.clamp_int(actingY, 0, 255);
                    int actingX = blockX;
                    int actingZ = blockZ;
                    int actingXInChunk = (actingX += (int) Math.round(corridorNoiseXHere * 1.7)) & 0xF;
                    int actingZInChunk = (actingZ += (int) Math.round(corridorNoiseZHere * 1.7)) & 0xF;
                    boolean carveHugeHole = hugeHoleChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
                    boolean carveHugeRavine = hugeRavineChunk && actingY >= utumnoLevel.corridorBaseLevels[0] && actingY < utumnoLevel.corridorBaseLevels[utumnoLevel.corridorBaseLevels.length - 1];
                    boolean carveCorridor = false;
                    for(int corridorBase : utumnoLevel.corridorBaseLevels) {
                        carveCorridor = actingY >= corridorBase && actingY < corridorBase + utumnoLevel.corridorHeight;
                        if(carveCorridor) break;
                    }
                    if(carveHugeHole && chunkX % 2 == 0 && chunkZ % 2 == 0) {
                        if(i >= utumnoLevel.corridorWidthStart + 1 && i <= utumnoLevel.corridorWidthEnd - 1 && k >= utumnoLevel.corridorWidthStart + 1 && k <= utumnoLevel.corridorWidthEnd - 1) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                        else if(i >= utumnoLevel.corridorWidthStart && i <= utumnoLevel.corridorWidthEnd && k >= utumnoLevel.corridorWidthStart && k <= utumnoLevel.corridorWidthEnd) {
                            blocks[blockIndex] = utumnoLevel.brickGlowBlock;
                            metadata[blockIndex] = (byte) utumnoLevel.brickGlowMeta;
                        }
                    }
                    if(chunkX % 2 == 0) {
                        if(carveCorridor && actingZInChunk >= utumnoLevel.corridorWidthStart && actingZInChunk <= utumnoLevel.corridorWidthEnd) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                        if(carveHugeRavine && actingXInChunk >= utumnoLevel.corridorWidthStart + 1 && actingXInChunk <= utumnoLevel.corridorWidthEnd - 1) {
                            blocks[blockIndex] = Blocks.air;
                            metadata[blockIndex] = 0;
                        }
                    }
                    if(chunkZ % 2 != 0) continue;
                    if(carveCorridor && actingXInChunk >= utumnoLevel.corridorWidthStart && actingXInChunk <= utumnoLevel.corridorWidthEnd) {
                        blocks[blockIndex] = Blocks.air;
                        metadata[blockIndex] = 0;
                    }
                    if(!carveHugeRavine || actingZInChunk < utumnoLevel.corridorWidthStart + 1 || actingZInChunk > utumnoLevel.corridorWidthEnd - 1) continue;
                    blocks[blockIndex] = Blocks.air;
                    metadata[blockIndex] = 0;
                }
            }
        }
    }

    static {
        initSpawnLists = false;
        lightRand = new Random();
        noiseGenXZ = new NoiseGeneratorPerlin(new Random(5628506078940526L), 1);
        noiseGenY = new NoiseGeneratorPerlin(new Random(1820268708369704034L), 1);
        corridorNoiseY = new NoiseGeneratorPerlin(new Random(89230369345425L), 1);
        corridorNoiseX = new NoiseGeneratorPerlin(new Random(824595069307073L), 1);
        corridorNoiseZ = new NoiseGeneratorPerlin(new Random(759206035530266067L), 1);
    }
}
