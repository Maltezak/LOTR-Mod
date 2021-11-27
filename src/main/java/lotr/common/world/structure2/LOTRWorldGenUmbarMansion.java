package lotr.common.world.structure2;

public class LOTRWorldGenUmbarMansion extends LOTRWorldGenSouthronMansion {
    public LOTRWorldGenUmbarMansion(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
