package lotr.common.world.structure2;

public class LOTRWorldGenUmbarPasture extends LOTRWorldGenSouthronPasture {
    public LOTRWorldGenUmbarPasture(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
