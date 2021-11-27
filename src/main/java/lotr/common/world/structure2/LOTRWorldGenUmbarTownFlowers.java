package lotr.common.world.structure2;

public class LOTRWorldGenUmbarTownFlowers extends LOTRWorldGenSouthronTownFlowers {
    public LOTRWorldGenUmbarTownFlowers(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
