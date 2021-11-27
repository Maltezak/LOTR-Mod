package lotr.common.world;

import lotr.common.*;
import lotr.common.entity.item.LOTREntityPortal;
import net.minecraft.entity.Entity;
import net.minecraft.world.*;

public class LOTRTeleporter
extends Teleporter {
    private WorldServer world;
    private boolean makeRingPortal;

    public LOTRTeleporter(WorldServer worldserver, boolean flag) {
        super(worldserver);
        this.world = worldserver;
        this.makeRingPortal = flag;
    }

    public void placeInPortal(Entity entity, double d, double d1, double d2, float f) {
        int j;
        int k;
        int i;
        if (this.world.provider.dimensionId == LOTRDimension.MIDDLE_EARTH.dimensionID) {
            i = 0;
            k = 0;
            j = LOTRMod.getTrueTopBlock(this.world, i, k);
        } else {
            i = LOTRLevelData.overworldPortalX;
            k = LOTRLevelData.overworldPortalZ;
            j = LOTRLevelData.overworldPortalY;
        }
        entity.setLocationAndAngles(i + 0.5, j + 1.0, k + 0.5, entity.rotationYaw, 0.0f);
        if (this.world.provider.dimensionId == LOTRDimension.MIDDLE_EARTH.dimensionID && LOTRLevelData.madeMiddleEarthPortal == 0) {
            LOTRLevelData.setMadeMiddleEarthPortal(1);
            if (this.makeRingPortal) {
                if (this.world.provider instanceof LOTRWorldProviderMiddleEarth) {
                    ((LOTRWorldProviderMiddleEarth)this.world.provider).setRingPortalLocation(i, j, k);
                }
                LOTREntityPortal portal = new LOTREntityPortal(this.world);
                portal.setLocationAndAngles(i + 0.5, j + 3.5, k + 0.5, 0.0f, 0.0f);
                this.world.spawnEntityInWorld(portal);
            }
        }
    }
}

