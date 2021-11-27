package lotr.common.world.structure2;

public abstract class LOTRWorldGenUmbarTownWall extends LOTRWorldGenSouthronTownWall {
    public LOTRWorldGenUmbarTownWall(boolean flag) {
        super(flag);
    }

    public static class Extra extends LOTRWorldGenSouthronTownWall.Extra {
        public Extra(boolean flag) {
            super(flag);
        }

        @Override
        protected boolean isUmbar() {
            return true;
        }
    }

    public static class SideMid extends LOTRWorldGenSouthronTownWall.SideMid {
        public SideMid(boolean flag) {
            super(flag);
        }

        @Override
        protected boolean isUmbar() {
            return true;
        }
    }

    public static class Long extends LOTRWorldGenSouthronTownWall.Long {
        public Long(boolean flag) {
            super(flag);
        }

        @Override
        protected boolean isUmbar() {
            return true;
        }
    }

    public static class Short extends LOTRWorldGenSouthronTownWall.Short {
        public Short(boolean flag) {
            super(flag);
        }

        @Override
        protected boolean isUmbar() {
            return true;
        }
    }

}
