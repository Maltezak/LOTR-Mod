package lotr.common.world.biome;

public class LOTRBiomeGenNurnen extends LOTRBiomeGenNurn {
    public LOTRBiomeGenNurnen(int i, boolean major) {
        super(i, major);
        this.npcSpawnList.clear();
        this.clearBiomeVariants();
    }

    @Override
    public boolean getEnableRiver() {
        return false;
    }
}
