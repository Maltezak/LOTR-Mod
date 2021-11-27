package lotr.common.world.structure2;

public class LOTRWorldGenUmbarLamp extends LOTRWorldGenSouthronLamp {
    public LOTRWorldGenUmbarLamp(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
