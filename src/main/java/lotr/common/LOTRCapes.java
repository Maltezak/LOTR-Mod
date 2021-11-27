package lotr.common;

import net.minecraft.util.ResourceLocation;

public class LOTRCapes {
    public static ResourceLocation GONDOR = LOTRCapes.forName("gondor");
    public static ResourceLocation TOWER_GUARD = LOTRCapes.forName("gondorTowerGuard");
    public static ResourceLocation RANGER = LOTRCapes.forName("ranger");
    public static ResourceLocation RANGER_ITHILIEN = LOTRCapes.forName("ranger_ithilien");
    public static ResourceLocation LOSSARNACH = LOTRCapes.forName("lossarnach");
    public static ResourceLocation PELARGIR = LOTRCapes.forName("pelargir");
    public static ResourceLocation BLACKROOT = LOTRCapes.forName("blackroot");
    public static ResourceLocation PINNATH_GELIN = LOTRCapes.forName("pinnathGelin");
    public static ResourceLocation LAMEDON = LOTRCapes.forName("lamedon");
    public static ResourceLocation ROHAN = LOTRCapes.forName("rohan");
    public static ResourceLocation DALE = LOTRCapes.forName("dale");
    public static ResourceLocation DUNLENDING_BERSERKER = LOTRCapes.forName("dunlendingBerserker");
    public static ResourceLocation GALADHRIM = LOTRCapes.forName("galadhrim");
    public static ResourceLocation GALADHRIM_TRADER = LOTRCapes.forName("galadhrimTrader");
    public static ResourceLocation WOOD_ELF = LOTRCapes.forName("woodElf");
    public static ResourceLocation HIGH_ELF = LOTRCapes.forName("highElf");
    public static ResourceLocation RIVENDELL = LOTRCapes.forName("rivendell");
    public static ResourceLocation RIVENDELL_TRADER = LOTRCapes.forName("rivendellTrader");
    public static ResourceLocation NEAR_HARAD = LOTRCapes.forName("nearHarad");
    public static ResourceLocation SOUTHRON_CHAMPION = LOTRCapes.forName("haradChampion");
    public static ResourceLocation GULF_HARAD = LOTRCapes.forName("gulf");
    public static ResourceLocation TAURETHRIM = LOTRCapes.forName("taurethrim");
    public static ResourceLocation GALADHRIM_SMITH = LOTRCapes.forName("galadhrimSmith");
    public static ResourceLocation DORWINION_CAPTAIN = LOTRCapes.forName("dorwinionCaptain");
    public static ResourceLocation DORWINION_ELF_CAPTAIN = LOTRCapes.forName("dorwinionElfCaptain");
    public static ResourceLocation GANDALF = LOTRCapes.forName("gandalf");
    public static ResourceLocation GANDALF_SANTA = LOTRCapes.forName("santa");

    private static ResourceLocation forName(String s) {
        return new ResourceLocation("lotr:cape/" + s + ".png");
    }
}

