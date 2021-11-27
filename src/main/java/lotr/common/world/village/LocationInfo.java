package lotr.common.world.village;

import lotr.common.world.map.LOTRWaypoint;

public class LocationInfo {
    public static final LocationInfo RANDOM_GEN_HERE = new LocationInfo(0, 0, 0, "RANDOM_GEN");
    public static final LocationInfo SPAWNED_BY_PLAYER = new LocationInfo(0, 0, 0, "PLAYER_SPAWNED");
    public static final LocationInfo NONE_HERE = new LocationInfo(0, 0, 0, "NONE"){

        @Override
        public boolean isPresent() {
            return false;
        }
    };
    public final int posX;
    public final int posZ;
    public final int rotation;
    public final String name;
    private boolean isFixedLocation = false;
    private LOTRWaypoint associatedWaypoint;

    public LocationInfo(int x, int z, int r, String s) {
        this.posX = x;
        this.posZ = z;
        this.rotation = r;
        this.name = s;
    }

    public LocationInfo setFixedLocation(LOTRWaypoint wp) {
        this.isFixedLocation = true;
        this.associatedWaypoint = wp;
        return this;
    }

    public boolean isFixedLocation() {
        return this.isFixedLocation;
    }

    public LOTRWaypoint getAssociatedWaypoint() {
        return this.associatedWaypoint;
    }

    public boolean isPresent() {
        return true;
    }

}

