package lotr.common.world.structure2;

public class LOTRWorldGenHarnedorTavernRuined extends LOTRWorldGenHarnedorTavern {
    public LOTRWorldGenHarnedorTavernRuined(boolean flag) {
        super(flag);
    }

    @Override
    protected boolean isRuined() {
        return true;
    }
}
