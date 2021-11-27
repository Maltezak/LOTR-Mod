package lotr.common.world.structure2;

public class LOTRWorldGenUmbarTownCorner extends LOTRWorldGenSouthronTownCorner {
    public LOTRWorldGenUmbarTownCorner(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
