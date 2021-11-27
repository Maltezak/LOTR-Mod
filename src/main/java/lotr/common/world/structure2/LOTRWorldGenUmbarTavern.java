package lotr.common.world.structure2;

public class LOTRWorldGenUmbarTavern extends LOTRWorldGenSouthronTavern {
    public LOTRWorldGenUmbarTavern(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
