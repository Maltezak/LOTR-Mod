package lotr.common.world.structure2;

public class LOTRWorldGenUmbarTraining extends LOTRWorldGenSouthronTraining {
    public LOTRWorldGenUmbarTraining(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
