package lotr.common.world.structure2;

public class LOTRWorldGenUmbarWell extends LOTRWorldGenSouthronWell {
    public LOTRWorldGenUmbarWell(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
