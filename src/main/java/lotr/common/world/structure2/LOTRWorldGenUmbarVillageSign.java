package lotr.common.world.structure2;

public class LOTRWorldGenUmbarVillageSign extends LOTRWorldGenSouthronVillageSign {
    public LOTRWorldGenUmbarVillageSign(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
