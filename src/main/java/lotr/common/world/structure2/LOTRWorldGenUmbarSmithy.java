package lotr.common.world.structure2;

public class LOTRWorldGenUmbarSmithy extends LOTRWorldGenSouthronSmithy {
    public LOTRWorldGenUmbarSmithy(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
