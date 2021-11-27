package lotr.common.fac;

import java.text.*;

import lotr.common.util.LOTRLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public class LOTRAlignmentValues {
    public static final float MAX_ALIGNMENT = 10000.0f;
    public static final AlignmentBonus MARRIAGE_BONUS = new AlignmentBonus(5.0f, "lotr.alignment.marriage");
    public static final AlignmentBonus FANGORN_TREE_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.cutFangornTree");
    public static final AlignmentBonus ROHAN_HORSE_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.killRohanHorse");
    public static final AlignmentBonus VINEYARD_STEAL_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.vineyardSteal");
    public static final AlignmentBonus PICKPOCKET_PENALTY = new AlignmentBonus(-1.0f, "lotr.alignment.pickpocket");
    private static final DecimalFormat alignFormat = new DecimalFormat(",##0.0");
    private static final DecimalFormat conqFormat = new DecimalFormat(",##0.00");
    private static final DecimalFormatSymbols alignFormatSymbols = new DecimalFormatSymbols();

    public static AlignmentBonus createMiniquestBonus(float alignment) {
        return new AlignmentBonus(alignment, "lotr.alignment.miniQuest");
    }

    public static AlignmentBonus createPledgePenalty(float alignment) {
        return new AlignmentBonus(alignment, "lotr.alignment.breakPledge");
    }

    public static String formatAlignForDisplay(float alignment) {
        return LOTRAlignmentValues.formatAlignForDisplay(alignment, alignFormat, true);
    }

    public static String formatConqForDisplay(float conq, boolean prefixPlus) {
        return LOTRAlignmentValues.formatAlignForDisplay(conq, conqFormat, prefixPlus);
    }

    private static String formatAlignForDisplay(float alignment, DecimalFormat dFormat, boolean prefixPlus) {
        LOTRAlignmentValues.setupDecimalFormat(dFormat);
        String s = dFormat.format(alignment);
        if (prefixPlus && !s.startsWith("-")) {
            s = "+" + s;
        }
        return s;
    }

    private static DecimalFormat setupDecimalFormat(DecimalFormat dFormat) {
        String groupSeparator;
        char decimalSeparatorChar = '.';
        char groupSeparatorChar = ',';
        String decimalSeparator = StatCollector.translateToLocal("lotr.alignment.decimal_separator_char");
        if (decimalSeparator.length() == 1) {
            decimalSeparatorChar = decimalSeparator.charAt(0);
        }
        if ((groupSeparator = StatCollector.translateToLocal("lotr.alignment.group_separator_char")).length() == 1) {
            groupSeparatorChar = groupSeparator.charAt(0);
        }
        alignFormatSymbols.setDecimalSeparator(decimalSeparatorChar);
        alignFormatSymbols.setGroupingSeparator(groupSeparatorChar);
        dFormat.setDecimalFormatSymbols(alignFormatSymbols);
        return dFormat;
    }

    public static float parseDisplayedAlign(String alignmentText) {
        DecimalFormat dFormat = alignFormat;
        LOTRAlignmentValues.setupDecimalFormat(dFormat);
        if (alignmentText.startsWith("+")) {
            alignmentText = alignmentText.substring("+".length());
        }
        try {
            return dFormat.parse(alignmentText).floatValue();
        }
        catch (ParseException e) {
            LOTRLog.logger.error("Could not parse alignment value from display string " + alignmentText);
            e.printStackTrace();
            return 0.0f;
        }
    }

    public static void notifyAlignmentNotHighEnough(EntityPlayer entityplayer, float alignmentRequired, LOTRFaction faction) {
        ChatComponentText componentAlignReq = new ChatComponentText(LOTRAlignmentValues.formatAlignForDisplay(alignmentRequired));
        componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.insufficientAlignment", componentAlignReq, faction.factionName()));
    }

    public static void notifyAlignmentNotHighEnough(EntityPlayer entityplayer, float alignmentRequired, LOTRFaction faction1, LOTRFaction faction2) {
        ChatComponentText componentAlignReq = new ChatComponentText(LOTRAlignmentValues.formatAlignForDisplay(alignmentRequired));
        componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.insufficientAlignment2", componentAlignReq, faction1.factionName(), faction2.factionName()));
    }

    public static void notifyAlignmentNotHighEnough(EntityPlayer entityplayer, float alignmentRequired, LOTRFaction faction1, LOTRFaction faction2, LOTRFaction faction3) {
        ChatComponentText componentAlignReq = new ChatComponentText(LOTRAlignmentValues.formatAlignForDisplay(alignmentRequired));
        componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.insufficientAlignment3", componentAlignReq, faction1.factionName(), faction2.factionName(), faction3.factionName()));
    }

    public static void notifyMiniQuestsNeeded(EntityPlayer entityplayer, LOTRFaction faction) {
        entityplayer.addChatMessage(new ChatComponentTranslation("chat.lotr.requireMiniQuest", faction.factionName()));
    }

    public static class AlignmentBonus {
        public float bonus;
        public String name;
        public boolean needsTranslation = true;
        public boolean isKill = false;
        public boolean killByHiredUnit = false;
        public boolean isCivilianKill = false;

        public AlignmentBonus(float f, String s) {
            this.bonus = f;
            this.name = s;
        }

        public static float scalePenalty(float penalty, float alignment) {
            if (alignment > 0.0f && penalty < 0.0f) {
                float factor = alignment / 50.0f;
                factor = MathHelper.clamp_float(factor, 1.0f, 20.0f);
                penalty *= factor;
            }
            return penalty;
        }
    }

    public static class Levels {
        public static final float USE_TABLE = 1.0f;
        public static final float USE_PORTAL = 1.0f;
        public static final float CONQUEST_HORN = 1500.0f;
        public static final float HOBBIT_MARRY = 100.0f;
        public static final float HOBBIT_CHILD_FOLLOW = 200.0f;
        public static final float HOBBIT_SHIRRIFF_TRADE = 50.0f;
        public static final float HOBBIT_FLEE = -100.0f;
        public static final float HOBBIT_FARMER_TRADE = 0.0f;
        public static final float BREE_CAPTAIN_TRADE = 100.0f;
        public static final float BREE_BLACKSMITH_TRADE = 50.0f;
        public static final float BREE_MARKET_TRADE = 0.0f;
        public static final float BREE_FARMER_TRADE = 0.0f;
        public static final float RANGER_NORTH_CAPTAIN_TRADE = 300.0f;
        public static final float DUNEDAIN_BLACKSMITH_TRADE = 50.0f;
        public static final float BLUE_DWARF_MINER_TRADE = 100.0f;
        public static final float BLUE_DWARF_COMMANDER_TRADE = 200.0f;
        public static final float BLUE_DWARF_MERCHANT_TRADE = 0.0f;
        public static final float BLUE_DWARF_SMITH_TRADE = 100.0f;
        public static final float HIGH_ELF_LORD_TRADE = 300.0f;
        public static final float HIGH_ELF_SMITH_TRADE = 100.0f;
        public static final float RIVENDELL_TRADER_TRADE = 75.0f;
        public static final float TROLL_TRUST = 100.0f;
        public static final float WOOD_ELF_CAPTAIN_TRADE = 250.0f;
        public static final float WOOD_ELF_SMITH_TRADE = 100.0f;
        public static final float DALE_CAPTAIN_TRADE = 100.0f;
        public static final float DALE_BLACKSMITH_TRADE = 50.0f;
        public static final float DALE_BAKER_TRADE = 0.0f;
        public static final float DALE_MERCHANT_TRADE = 0.0f;
        public static final float DWARF_MINER_TRADE = 100.0f;
        public static final float DWARF_COMMANDER_TRADE = 200.0f;
        public static final float DWARF_MARRY = 200.0f;
        public static final float DWARF_MERCHANT_TRADE = 0.0f;
        public static final float DWARF_SMITH_TRADE = 100.0f;
        public static final float GALADHRIM_TRADER_TRADE = 75.0f;
        public static final float GALADHRIM_LORD_TRADE = 300.0f;
        public static final float GALADHRIM_SMITH_TRADE = 100.0f;
        public static final float ROHIRRIM_MARSHAL_TRADE = 150.0f;
        public static final float ROHAN_BLACKSMITH_TRADE = 50.0f;
        public static final float ROHAN_SHIELDMAIDEN = 150.0f;
        public static final float ROHAN_FARMER_TRADE = 0.0f;
        public static final float ROHAN_MARKET_TRADE = 0.0f;
        public static final float ROHAN_STABLE_TRADE = 50.0f;
        public static final float DUNLENDING_WARLORD_TRADE = 100.0f;
        public static final float SPAWN_HUORN = 500.0f;
        public static final float GONDOR_BLACKSMITH_TRADE = 50.0f;
        public static final float GONDORIAN_CAPTAIN_TRADE = 200.0f;
        public static final float RANGER_ITHILIEN_CAPTAIN_TRADE = 300.0f;
        public static final float DOL_AMROTH_CAPTAIN_TRADE = 200.0f;
        public static final float LOSSARNACH_CAPTAIN_TRADE = 150.0f;
        public static final float PELARGIR_CAPTAIN_TRADE = 200.0f;
        public static final float PINNATH_GELIN_CPTAIN_TRADE = 200.0f;
        public static final float BLACKROOT_CAPTAIN_TRADE = 150.0f;
        public static final float GONDOR_FARMER_TRADE = 0.0f;
        public static final float LEBENNIN_CAPTAIN_TRADE = 150.0f;
        public static final float GONDOR_MARKET_TRADE = 0.0f;
        public static final float LAMEDON_CAPTAIN_TRADE = 200.0f;
        public static final float ORC_FLEE = -500.0f;
        public static final float ORC_FRIENDLY = 100.0f;
        public static final float MORDOR_TRUST = 100.0f;
        public static final float MORDOR_ORC_TRADER_TRADE = 100.0f;
        public static final float MORDOR_ORC_MERCENARY_CAPTAIN_TRADE = 150.0f;
        public static final float BLACK_URUK_CAPTAIN_TRADE = 400.0f;
        public static final float MORDOR_SPIDER_KEEPER_TRADE = 250.0f;
        public static final float MORDOR_ORC_SLAVER_TRADE = 200.0f;
        public static final float MORGUL_FLOWERS = 250.0f;
        public static final float WICKED_DWARF_TRADE = 100.0f;
        public static final float ANGMAR_ORC_MERCENARY_CAPTAIN_TRADE = 150.0f;
        public static final float ANGMAR_ORC_TRADER_TRADE = 100.0f;
        public static final float ANGMAR_HILLMAN_CHIEFTAIN_TRADE = 100.0f;
        public static final float GUNDABAD_ORC_MERCENARY_CAPTAIN_TRADE = 100.0f;
        public static final float GUNDABAD_ORC_TRADER_TRADE = 50.0f;
        public static final float URUK_HAI_TRADER_TRADE = 100.0f;
        public static final float URUK_HAI_MERCENARY_CAPTAIN_TRADE = 150.0f;
        public static final float WARG_RIDE = 50.0f;
        public static final float SPIDER_RIDE = 50.0f;
        public static final float DOL_GULDUR_CAPTAIN_TRADE = 150.0f;
        public static final float DOL_GULDUR_ORC_TRADER_TRADE = 100.0f;
        public static final float DORWINION_CAPTAIN_TRADE = 150.0f;
        public static final float DORWINION_ELF_CAPTAIN_TRADE = 250.0f;
        public static final float DORWINION_ELF_VINTNER_TRADE = 50.0f;
        public static final float DORWINION_VINEKEEPER_TRADE = 0.0f;
        public static final float DORWINION_VINEYARD_ALLOW = 2000.0f;
        public static final float DORWINION_MERCHANT_TRADE = 0.0f;
        public static final float EASTERLING_BLACKSMITH_TRADE = 50.0f;
        public static final float EASTERLING_WARLORD_TRADE = 150.0f;
        public static final float EASTERLING_MARKET_TRADE = 0.0f;
        public static final float EASTERLING_FARMER_TRADE = 0.0f;
        public static final float NEAR_HARADRIM_WARLORD_TRADE = 150.0f;
        public static final float HARNEDOR_WARLORD_TRADE = 150.0f;
        public static final float UMBAR_CAPTAIN_TRADE = 150.0f;
        public static final float CORSAIR_CAPTAIN_TRADE = 150.0f;
        public static final float CORSAIR_SLAVER_TRADE = 0.0f;
        public static final float NOMAD_WARLORD_TRADE = 150.0f;
        public static final float GULF_WARLORD_TRADE = 150.0f;
        public static final float NEAR_HARAD_MERCHANT_TRADE = 0.0f;
        public static final float NOMAD_MERCHANT_TRADE = 0.0f;
        public static final float NEAR_HARAD_BAZAAR_TRADE = 0.0f;
        public static final float NEAR_HARAD_BLACKSMITH_TRADE = 50.0f;
        public static final float MOREDAIN_MERCENARY_TRADE = 0.0f;
        public static final float GONDOR_RENEGADE = 50.0f;
        public static final float NEAR_HARAD_FARMER_TRADE = 0.0f;
        public static final float MOREDAIN_CHIEFTAIN_TRADE = 150.0f;
        public static final float MOREDAIN_VILLAGE_TRADE = 0.0f;
        public static final float TAUREDAIN_CHIEFTAIN_TRADE = 200.0f;
        public static final float TAUREDAIN_SHAMAN_TRADE = 100.0f;
        public static final float TAUREDAIN_FARMER_TRADE = 0.0f;
        public static final float TAUREDAIN_SMITH_TRADE = 50.0f;
        public static final float HALF_TROLL_WARLORD_TRADE = 200.0f;
        public static final float HALF_TROLL_SCAVENGER_TRADE = 50.0f;
    }

    public static class Bonuses {
        public static final float HOBBIT = 1.0f;
        public static final float HOBBIT_BOUNDER = 2.0f;
        public static final float HOBBIT_SHIRRIFF = 5.0f;
        public static final float HOBBIT_BARTENDER = 2.0f;
        public static final float HOBBIT_ORCHARDER = 2.0f;
        public static final float HOBBIT_FARMER = 2.0f;
        public static final float DARK_HUORN = 1.0f;
        public static final float BREE_MAN = 1.0f;
        public static final float BREE_GUARD = 2.0f;
        public static final float BREE_CAPTAIN = 5.0f;
        public static final float BREE_BLACKSMITH = 2.0f;
        public static final float BREE_INNKEEPER = 2.0f;
        public static final float BREE_HOBBIT = 1.0f;
        public static final float BREE_MARKET_TRADER = 2.0f;
        public static final float BREE_FARMER = 2.0f;
        public static final float DUNEDAIN = 1.0f;
        public static final float RANGER_NORTH = 2.0f;
        public static final float RANGER_NORTH_CAPTAIN = 5.0f;
        public static final float DUNEDAIN_BLACKSMITH = 2.0f;
        public static final float BLUE_DWARF = 1.0f;
        public static final float BLUE_DWARF_WARRIOR = 2.0f;
        public static final float BLUE_DWARF_COMMANDER = 5.0f;
        public static final float BLUE_DWARF_MINER = 2.0f;
        public static final float BLUE_DWARF_MERCHANT = 2.0f;
        public static final float BLUE_DWARF_SMITH = 2.0f;
        public static final float HIGH_ELF = 1.0f;
        public static final float HIGH_ELF_WARRIOR = 2.0f;
        public static final float HIGH_ELF_LORD = 5.0f;
        public static final float HIGH_ELF_SMITH = 2.0f;
        public static final float RIVENDELL_TRADER = 2.0f;
        public static final float GUNDABAD_ORC = 1.0f;
        public static final float GUNDABAD_ORC_MERCENARY_CAPTAIN = 5.0f;
        public static final float GUNDABAD_WARG = 2.0f;
        public static final float GUNDABAD_URUK = 2.0f;
        public static final float GUNDABAD_ORC_TRADER = 2.0f;
        public static final float ANGMAR_ORC = 1.0f;
        public static final float ANGMAR_ORC_WARRIOR = 2.0f;
        public static final float ANGMAR_ORC_MERCENARY_CAPTAIN = 5.0f;
        public static final float ANGMAR_WARG = 2.0f;
        public static final float ANGMAR_ORC_TRADER = 2.0f;
        public static final float ANGMAR_HILLMAN = 1.0f;
        public static final float ANGMAR_HILLMAN_WARRIOR = 2.0f;
        public static final float ANGMAR_HILLMAN_CHIEFTAIN = 5.0f;
        public static final float TROLL = 3.0f;
        public static final float MOUNTAIN_TROLL = 4.0f;
        public static final float SNOW_TROLL = 3.0f;
        public static final float BARROW_WIGHT = 2.0f;
        public static final float WOOD_ELF = 1.0f;
        public static final float WOOD_ELF_WARRIOR = 2.0f;
        public static final float WOOD_ELF_CAPTAIN = 5.0f;
        public static final float WOOD_ELF_SMITH = 2.0f;
        public static final float MIRKWOOD_SPIDER = 1.0f;
        public static final float DOL_GULDUR_ORC = 1.0f;
        public static final float DOL_GULDUR_CAPTAIN = 5.0f;
        public static final float MIRK_TROLL = 4.0f;
        public static final float DOL_GULDUR_ORC_TRADER = 2.0f;
        public static final float DALE_MAN = 1.0f;
        public static final float DALE_MILITIA = 2.0f;
        public static final float DALE_SOLDIER = 2.0f;
        public static final float DALE_CAPTAIN = 5.0f;
        public static final float DALE_BLACKSMITH = 2.0f;
        public static final float DALE_BAKER = 2.0f;
        public static final float DALE_MERCHANT = 2.0f;
        public static final float DWARF = 1.0f;
        public static final float DWARF_WARRIOR = 2.0f;
        public static final float DWARF_COMMANDER = 5.0f;
        public static final float DWARF_MINER = 2.0f;
        public static final float DWARF_MERCHANT = 2.0f;
        public static final float DWARF_SMITH = 2.0f;
        public static final float GALADHRIM = 1.0f;
        public static final float GALADHRIM_WARRIOR = 2.0f;
        public static final float GALADHRIM_LORD = 5.0f;
        public static final float GALADHRIM_TRADER = 2.0f;
        public static final float GALADHRIM_SMITH = 2.0f;
        public static final float DUNLENDING = 1.0f;
        public static final float DUNLENDING_WARRIOR = 2.0f;
        public static final float DUNLENDING_WARLORD = 5.0f;
        public static final float DUNLENDING_BARTENDER = 2.0f;
        public static final float ENT = 3.0f;
        public static final float HUORN = 2.0f;
        public static final float ROHIRRIM = 1.0f;
        public static final float ROHIRRIM_WARRIOR = 2.0f;
        public static final float ROHIRRIM_MARSHAL = 5.0f;
        public static final float ROHAN_BLACKSMITH = 2.0f;
        public static final float ROHAN_MEADHOST = 2.0f;
        public static final float ROHAN_FARMER = 2.0f;
        public static final float ROHAN_MARKET_TRADER = 2.0f;
        public static final float ISENGARD_SNAGA = 1.0f;
        public static final float URUK_HAI = 2.0f;
        public static final float URUK_HAI_MERCENARY_CAPTAIN = 5.0f;
        public static final float URUK_HAI_TRADER = 2.0f;
        public static final float URUK_WARG = 2.0f;
        public static final float GONDOR_MAN = 1.0f;
        public static final float GONDOR_MILITIA = 2.0f;
        public static final float GONDOR_SOLDIER = 2.0f;
        public static final float GONDOR_CAPTAIN = 5.0f;
        public static final float GONDOR_BLACKSMITH = 2.0f;
        public static final float RANGER_ITHILIEN = 2.0f;
        public static final float RANGER_ITHILIEN_CAPTAIN = 5.0f;
        public static final float SWAN_KNIGHT = 2.0f;
        public static final float DOL_AMROTH_CAPTAIN = 5.0f;
        public static final float GONDOR_FARMER = 2.0f;
        public static final float GONDOR_BARTENDER = 2.0f;
        public static final float GONDOR_MARKET_TRADER = 2.0f;
        public static final float MORDOR_ORC = 1.0f;
        public static final float MORDOR_ORC_MERCENARY_CAPTAIN = 5.0f;
        public static final float BLACK_URUK_CAPTAIN = 5.0f;
        public static final float MORDOR_ORC_TRADER = 2.0f;
        public static final float MORDOR_ORC_SLAVER = 2.0f;
        public static final float MORDOR_ORC_SPIDER_KEEPER = 5.0f;
        public static final float MORDOR_WARG = 2.0f;
        public static final float OLOG_HAI = 4.0f;
        public static final float MORDOR_SPIDER = 1.0f;
        public static final float WICKED_DWARF = 2.0f;
        public static final float BLACK_URUK = 2.0f;
        public static final float DORWINION_MAN = 1.0f;
        public static final float DORWINION_GUARD = 2.0f;
        public static final float DORWINION_CAPTAIN = 5.0f;
        public static final float DORWINION_ELF = 2.0f;
        public static final float DORWINION_ELF_WARRIOR = 3.0f;
        public static final float DORWINION_ELF_CAPTAIN = 5.0f;
        public static final float DORWINION_ELF_VINTNER = 2.0f;
        public static final float DORWINION_FARMER = 2.0f;
        public static final float DORWINION_MERCHANT = 2.0f;
        public static final float EASTERLING = 1.0f;
        public static final float EASTERLING_WARRIOR = 2.0f;
        public static final float EASTERLING_BLACKSMITH = 2.0f;
        public static final float EASTERLING_WARLORD = 5.0f;
        public static final float EASTERLING_MARKET_TRADER = 2.0f;
        public static final float EASTERLING_BARTENDER = 2.0f;
        public static final float EASTERLING_FARMER = 2.0f;
        public static final float NEAR_HARADRIM = 1.0f;
        public static final float NEAR_HARADRIM_WARRIOR = 2.0f;
        public static final float NEAR_HARADRIM_WARLORD = 5.0f;
        public static final float NEAR_HARADRIM_TRADER = 2.0f;
        public static final float NEAR_HARADRIM_BARTENDER = 2.0f;
        public static final float NEAR_HARADRIM_BLACKSMITH = 2.0f;
        public static final float NEAR_HARADRIM_FARMER = 2.0f;
        public static final float MOREDAIN = 1.0f;
        public static final float MOREDAIN_WARRIOR = 2.0f;
        public static final float MOREDAIN_CHIEFTAIN = 5.0f;
        public static final float MOREDAIN_TRADER = 2.0f;
        public static final float TAUREDAIN = 1.0f;
        public static final float TAUREDAIN_WARRIOR = 2.0f;
        public static final float TAUREDAIN_CHIEFTAIN = 5.0f;
        public static final float TAUREDAIN_TRADER = 2.0f;
        public static final float TAUREDAIN_FARMER = 2.0f;
        public static final float HALF_TROLL = 1.0f;
        public static final float HALF_TROLL_WARRIOR = 2.0f;
        public static final float HALF_TROLL_WARLORD = 5.0f;
        public static final float HALF_TROLL_SCAVENGER = 2.0f;
        public static final float MOUNTAIN_TROLL_CHIEFTAIN = 50.0f;
        public static final float MALLORN_ENT = 50.0f;
    }

}

