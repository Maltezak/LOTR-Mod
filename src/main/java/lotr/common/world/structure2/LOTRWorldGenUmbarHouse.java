package lotr.common.world.structure2;

public class LOTRWorldGenUmbarHouse extends LOTRWorldGenSouthronHouse {
    public LOTRWorldGenUmbarHouse(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
