package lotr.common.world.structure2;

public class LOTRWorldGenUmbarFortGate extends LOTRWorldGenSouthronFortGate {
    public LOTRWorldGenUmbarFortGate(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isUmbar() {
        return true;
    }
}
