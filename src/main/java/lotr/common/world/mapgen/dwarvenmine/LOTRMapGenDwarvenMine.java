package lotr.common.world.mapgen.dwarvenmine;

import java.util.*;

import lotr.common.LOTRDimension;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.*;
import lotr.common.world.village.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.*;

public class LOTRMapGenDwarvenMine
extends MapGenStructure {
    private static List spawnBiomes;
    private int spawnChance = 150;
    private static List spawnBiomesRuined;
    private int spawnChanceRuined = 500;

    private static void setupSpawnBiomes() {
        if (spawnBiomes == null) {
            spawnBiomes = new ArrayList();
            spawnBiomesRuined = new ArrayList();
            for (LOTRBiome biome : LOTRDimension.MIDDLE_EARTH.biomeList) {
                boolean mine = false;
                boolean ruined = false;
                if (biome instanceof LOTRBiomeGenIronHills) {
                    mine = true;
                }
                if (biome instanceof LOTRBiomeGenBlueMountains && !(biome instanceof LOTRBiomeGenBlueMountainsFoothills)) {
                    mine = true;
                }
                if (biome instanceof LOTRBiomeGenGreyMountains) {
                    ruined = true;
                }
                if (biome instanceof LOTRBiomeGenErebor) {
                    mine = true;
                }
                if (mine) {
                    spawnBiomes.add(biome);
                }
                if (!ruined) continue;
                spawnBiomesRuined.add(biome);
            }
        }
    }

    protected boolean canSpawnStructureAtCoords(int i, int k) {
        LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)this.worldObj.getWorldChunkManager();
        LOTRVillagePositionCache cache = worldChunkMgr.getStructureCache(this);
        LocationInfo cacheLocation = cache.getLocationAt(i, k);
        if (cacheLocation != null) {
            return cacheLocation.isPresent();
        }
        int i1 = i * 16 + 8;
        int k1 = k * 16 + 8;
        LOTRMapGenDwarvenMine.setupSpawnBiomes();
        if (this.worldObj.getWorldChunkManager().areBiomesViable(i1, k1, 0, spawnBiomes) ? this.rand.nextInt(this.spawnChance) == 0 : this.worldObj.getWorldChunkManager().areBiomesViable(i1, k1, 0, spawnBiomesRuined) && this.rand.nextInt(this.spawnChanceRuined) == 0) {
            return cache.markResult(i, k, LocationInfo.RANDOM_GEN_HERE).isPresent();
        }
        return cache.markResult(i, k, LocationInfo.NONE_HERE).isPresent();
    }

    protected StructureStart getStructureStart(int i, int k) {
        int i1 = i * 16 + 8;
        int k1 = k * 16 + 8;
        BiomeGenBase biome = this.worldObj.getWorldChunkManager().getBiomeGenAt(i1, k1);
        boolean ruined = spawnBiomesRuined.contains(biome);
        return new LOTRStructureDwarvenMineStart(this.worldObj, this.rand, i, k, ruined);
    }

    public String func_143025_a() {
        return "LOTR.DwarvenMine";
    }

    public static void register() {
        MapGenStructureIO.registerStructure(LOTRStructureDwarvenMineStart.class, "LOTR.DwarvenMine");
        MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineEntrance.class, "LOTR.DwarvenMine.Entrance");
        MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineCorridor.class, "LOTR.DwarvenMine.Corridor");
        MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineCrossing.class, "LOTR.DwarvenMine.Crossing");
        MapGenStructureIO.func_143031_a(LOTRComponentDwarvenMineStairs.class, "LOTR.DwarvenMine.Stairs");
    }
}

