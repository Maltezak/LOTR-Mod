package lotr.common.world.structure2;

public class LOTRWorldGenUmbarTownTree extends LOTRWorldGenSouthronTownTree {
    public LOTRWorldGenUmbarTownTree(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
