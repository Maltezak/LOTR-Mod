package lotr.common.world.structure2;

public class LOTRWorldGenUmbarFortCorner extends LOTRWorldGenSouthronFortCorner {
    public LOTRWorldGenUmbarFortCorner(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
