package lotr.common.world;

import lotr.common.LOTRDimension;
import lotr.common.world.map.LOTRFixedStructures;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

public class LOTRTeleporterUtumno
extends Teleporter {
    private WorldServer worldObj;
    private int targetX;
    private int targetZ;

    private LOTRTeleporterUtumno(WorldServer world) {
        super(world);
        this.worldObj = world;
    }

    public static LOTRTeleporterUtumno newTeleporter(int dimension) {
        WorldServer world = DimensionManager.getWorld(dimension);
        if (world == null) {
            DimensionManager.initDimension(dimension);
            world = DimensionManager.getWorld(dimension);
        }
        return new LOTRTeleporterUtumno(world);
    }

    public void setTargetCoords(int x, int z) {
        this.targetX = x;
        this.targetZ = z;
    }

    public void placeInPortal(Entity entity, double d, double d1, double d2, float f) {
        int j;
        int k;
        int i;
        block4: {
            i = 0;
            j = 256;
            k = 0;
            if (this.worldObj.provider.dimensionId == LOTRDimension.UTUMNO.dimensionID) {
                for (int l = 0; l < 10000; ++l) {
                    int x = this.targetX;
                    int z = this.targetZ;
                    int y = LOTRUtumnoLevel.ICE.corridorBaseLevels[LOTRUtumnoLevel.ICE.corridorBaseLevels.length - 1];
                    int targetFuzz = 32;
                    int x1 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, x - targetFuzz, x + targetFuzz);
                    int z1 = MathHelper.getRandomIntegerInRange(this.worldObj.rand, z - targetFuzz, z + targetFuzz);
                    int yFuzz = 3;
                    for (int j1 = -yFuzz; j1 <= yFuzz; ++j1) {
                        int y1 = y + j1;
                        if (!this.worldObj.getBlock(x1, y1 - 1, z1).isOpaqueCube() || !this.worldObj.isAirBlock(x1, y1, z1) || !this.worldObj.isAirBlock(x1, y1, z1)) continue;
                        i = x1;
                        j = y1;
                        k = z1;
                        break block4;
                    }
                }
            } else {
                double randomDistance = MathHelper.getRandomDoubleInRange(this.worldObj.rand, 40.0, 80.0);
                float angle = this.worldObj.rand.nextFloat() * 3.1415927f * 2.0f;
                i = LOTRFixedStructures.UTUMNO_ENTRANCE.xCoord + (int)(randomDistance * MathHelper.sin(angle));
                k = LOTRFixedStructures.UTUMNO_ENTRANCE.zCoord + (int)(randomDistance * MathHelper.cos(angle));
                j = this.worldObj.getTopSolidOrLiquidBlock(i, k);
            }
        }
        entity.setLocationAndAngles(i + 0.5, j + 1.0, k + 0.5, entity.rotationYaw, 0.0f);
        entity.fallDistance = 0.0f;
    }
}

