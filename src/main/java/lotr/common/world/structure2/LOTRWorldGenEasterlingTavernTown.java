package lotr.common.world.structure2;

public class LOTRWorldGenEasterlingTavernTown extends LOTRWorldGenEasterlingTavern {
    public LOTRWorldGenEasterlingTavernTown(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean useTownBlocks() {
        return true;
    }
}
