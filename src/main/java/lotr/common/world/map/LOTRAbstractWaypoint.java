package lotr.common.world.map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface LOTRAbstractWaypoint {
    int getX();

    int getY();

    int getXCoord();

    int getZCoord();

    int getYCoord(World var1, int var2, int var3);

    int getYCoordSaved();

    String getCodeName();

    String getDisplayName();

    String getLoreText(EntityPlayer var1);

    boolean hasPlayerUnlocked(EntityPlayer var1);

    WaypointLockState getLockState(EntityPlayer var1);

    boolean isHidden();

    int getID();

    public enum WaypointLockState {
        STANDARD_LOCKED(0, 200),
        STANDARD_UNLOCKED(4, 200),
        STANDARD_UNLOCKED_CONQUEST(8, 200),
        CUSTOM_LOCKED(0, 204),
        CUSTOM_UNLOCKED(4, 204),
        SHARED_CUSTOM_LOCKED(0, 208),
        SHARED_CUSTOM_UNLOCKED(4, 208);

        public final int iconU;
        public final int iconV;

        WaypointLockState(int u, int v) {
            this.iconU = u;
            this.iconV = v;
        }
    }

}

