package lotr.common.world.map;

import java.util.*;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public enum LOTRWaypoint implements LOTRAbstractWaypoint
{
    HIMLING(Region.OCEAN, LOTRFaction.UNALIGNED, 485, 523),
    TOL_FUIN(Region.OCEAN, LOTRFaction.UNALIGNED, 357, 542),
    TOL_MORWEN(Region.OCEAN, LOTRFaction.UNALIGNED, 87, 698),
    MENELTARMA_SUMMIT(Region.MENELTARMA, LOTRFaction.UNALIGNED, 64, 1733, true),
    HOBBITON(Region.SHIRE, LOTRFaction.HOBBIT, 815, 727),
    BRANDYWINE_BRIDGE(Region.SHIRE, LOTRFaction.UNALIGNED, 853, 725),
    SARN_FORD(Region.SHIRE, LOTRFaction.UNALIGNED, 883, 802),
    LONGBOTTOM(Region.SHIRE, LOTRFaction.HOBBIT, 820, 765),
    MICHEL_DELVING(Region.SHIRE, LOTRFaction.HOBBIT, 796, 739),
    WILLOWBOTTOM(Region.SHIRE, LOTRFaction.HOBBIT, 845, 752),
    BUCKLEBURY(Region.SHIRE, LOTRFaction.HOBBIT, 857, 734),
    WHITFURROWS(Region.SHIRE, LOTRFaction.HOBBIT, 843, 727),
    FROGMORTON(Region.SHIRE, LOTRFaction.HOBBIT, 831, 728),
    OATBARTON(Region.SHIRE, LOTRFaction.HOBBIT, 822, 701),
    SCARY(Region.SHIRE, LOTRFaction.HOBBIT, 840, 713),
    NEEDLEHOLE(Region.SHIRE, LOTRFaction.HOBBIT, 806, 708),
    LITTLE_DELVING(Region.SHIRE, LOTRFaction.HOBBIT, 785, 718),
    WAYMEET(Region.SHIRE, LOTRFaction.UNALIGNED, 807, 733),
    TUCKBOROUGH(Region.SHIRE, LOTRFaction.HOBBIT, 815, 741),
    NOBOTTLE(Region.SHIRE, LOTRFaction.HOBBIT, 797, 710),
    TIGHFIELD(Region.SHIRE, LOTRFaction.HOBBIT, 778, 712),
    BROCKENBORINGS(Region.SHIRE, LOTRFaction.HOBBIT, 833, 710),
    DEEPHALLOW(Region.SHIRE, LOTRFaction.HOBBIT, 850, 749),
    STOCK(Region.SHIRE, LOTRFaction.HOBBIT, 849, 737),
    BYWATER(Region.SHIRE, LOTRFaction.HOBBIT, 820, 730),
    OVERHILL(Region.SHIRE, LOTRFaction.HOBBIT, 817, 720),
    HAYSEND(Region.SHIRE, LOTRFaction.HOBBIT, 858, 747),
    HAY_GATE(Region.SHIRE, LOTRFaction.HOBBIT, 856, 728),
    GREENHOLM(Region.SHIRE, LOTRFaction.HOBBIT, 762, 745),
    WITHYWINDLE_VALLEY(Region.OLD_FOREST, LOTRFaction.UNALIGNED, 881, 749),
    FORLOND(Region.LINDON, LOTRFaction.HIGH_ELF, 526, 718),
    HARLOND(Region.LINDON, LOTRFaction.HIGH_ELF, 605, 783),
    MITHLOND_NORTH(Region.LINDON, LOTRFaction.HIGH_ELF, 669, 717),
    MITHLOND_SOUTH(Region.LINDON, LOTRFaction.HIGH_ELF, 679, 729),
    FORLINDON(Region.LINDON, LOTRFaction.HIGH_ELF, 532, 638),
    HARLINDON(Region.LINDON, LOTRFaction.HIGH_ELF, 611, 878),
    TOWER_HILLS(Region.LINDON, LOTRFaction.HIGH_ELF, 710, 742),
    AMON_EREB(Region.LINDON, LOTRFaction.HIGH_ELF, 500, 708),
    BELEGOST(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 622, 600),
    NOGROD(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 626, 636),
    MOUNT_RERIR(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 592, 525),
    MOUNT_DOLMED(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 599, 627),
    THORIN_HALLS(Region.BLUE_MOUNTAINS, LOTRFaction.BLUE_MOUNTAINS, 641, 671),
    ARVEDUI_MINES(Region.BLUE_MOUNTAINS, LOTRFaction.UNALIGNED, 663, 489),
    THRAIN_HALLS(Region.BLUE_MOUNTAINS, LOTRFaction.BLUE_MOUNTAINS, 669, 793),
    NORTH_DOWNS(Region.ERIADOR, LOTRFaction.UNALIGNED, 930, 626),
    SOUTH_DOWNS(Region.ERIADOR, LOTRFaction.UNALIGNED, 960, 768),
    ERYN_VORN(Region.ERIADOR, LOTRFaction.UNALIGNED, 747, 957),
    THARBAD(Region.ERIADOR, LOTRFaction.UNALIGNED, 979, 878),
    FORNOST(Region.ERIADOR, LOTRFaction.UNALIGNED, 897, 652),
    ANNUMINAS(Region.ERIADOR, LOTRFaction.UNALIGNED, 814, 661),
    GREENWAY_CROSSROADS(Region.ERIADOR, LOTRFaction.UNALIGNED, 920, 810),
    BREE(Region.BREE_LAND, LOTRFaction.BREE, 915, 734),
    STADDLE(Region.BREE_LAND, LOTRFaction.UNALIGNED, 924, 734),
    COMBE(Region.BREE_LAND, LOTRFaction.UNALIGNED, 927, 731),
    ARCHET(Region.BREE_LAND, LOTRFaction.UNALIGNED, 928, 728),
    FORSAKEN_INN(Region.BREE_LAND, LOTRFaction.UNALIGNED, 950, 743),
    WEATHERTOP(Region.LONE_LANDS, LOTRFaction.UNALIGNED, 998, 723),
    LAST_BRIDGE(Region.LONE_LANDS, LOTRFaction.UNALIGNED, 1088, 714),
    OLD_ELF_WAY(Region.LONE_LANDS, LOTRFaction.UNALIGNED, 1028, 847),
    RIVENDELL(Region.RIVENDELL_VALE, LOTRFaction.HIGH_ELF, 1173, 721),
    FORD_BRUINEN(Region.RIVENDELL_VALE, LOTRFaction.HIGH_ELF, 1163, 723),
    THE_TROLLSHAWS(Region.TROLLSHAWS, LOTRFaction.UNALIGNED, 1130, 703),
    CARN_DUM(Region.ANGMAR, LOTRFaction.ANGMAR, 1010, 503),
    WEST_GATE(Region.EREGION, LOTRFaction.UNALIGNED, 1134, 873),
    OST_IN_EDHIL(Region.EREGION, LOTRFaction.UNALIGNED, 1112, 870),
    NORTH_DUNLAND(Region.DUNLAND, LOTRFaction.DUNLAND, 1073, 946),
    SOUTH_DUNLAND(Region.DUNLAND, LOTRFaction.DUNLAND, 1070, 1027),
    FORDS_OF_ISEN(Region.DUNLAND, LOTRFaction.UNALIGNED, 1102, 1087),
    DWARROWVALE(Region.DUNLAND, LOTRFaction.UNALIGNED, 1080, 990),
    WULFBURG(Region.DUNLAND, LOTRFaction.UNALIGNED, 1077, 1098),
    LOND_DAER(Region.ENEDWAITH, LOTRFaction.UNALIGNED, 867, 1004),
    ENEDWAITH_ROAD(Region.ENEDWAITH, LOTRFaction.UNALIGNED, 1025, 1050),
    MOUTHS_ISEN(Region.ENEDWAITH, LOTRFaction.UNALIGNED, 871, 1127),
    ISENGARD(Region.NAN_CURUNIR, LOTRFaction.ISENGARD, 1102, 1058),
    CAPE_OF_FOROCHEL(Region.FORODWAITH, LOTRFaction.UNALIGNED, 786, 390),
    SOUTH_FOROCHEL(Region.FORODWAITH, LOTRFaction.UNALIGNED, 825, 459),
    WITHERED_HEATH(Region.FORODWAITH, LOTRFaction.UNALIGNED, 1441, 556),
    MOUNT_GUNDABAD(Region.MISTY_MOUNTAINS, LOTRFaction.GUNDABAD, 1195, 592),
    MOUNT_GRAM(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1106, 589),
    HIGH_PASS(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1222, 706),
    MOUNT_CARADHRAS(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1166, 845),
    MOUNT_CELEBDIL(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1158, 864),
    MOUNT_FANUIDHOL(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1191, 854),
    MOUNT_METHEDRAS(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1111, 1031),
    GOBLIN_TOWN(Region.MISTY_MOUNTAINS, LOTRFaction.GUNDABAD, 1220, 696),
    EAGLES_EYRIE(Region.MISTY_MOUNTAINS, LOTRFaction.UNALIGNED, 1246, 685),
    DAINS_HALLS(Region.GREY_MOUNTAINS, LOTRFaction.UNALIGNED, 1262, 554),
    SCATHA(Region.GREY_MOUNTAINS, LOTRFaction.UNALIGNED, 1335, 562),
    CARROCK(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1281, 681),
    OLD_FORD(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1284, 702),
    GLADDEN_FIELDS(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1294, 790),
    DIMRILL_DALE(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1177, 864),
    FIELD_OF_CELEBRANT(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1281, 960),
    RAUROS(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1345, 1130),
    BEORN(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1302, 680),
    FOREST_GATE(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1303, 655),
    FRAMSBURG(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1251, 590),
    ANDUIN_CROSSROADS(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1285, 905),
    EAST_RHOVANION_ROAD(Region.VALES_OF_ANDUIN, LOTRFaction.UNALIGNED, 1354, 966),
    THRANDUIL_HALLS(Region.WOODLAND_REALM, LOTRFaction.WOOD_ELF, 1420, 633),
    DOL_GULDUR(Region.MIRKWOOD, LOTRFaction.DOL_GULDUR, 1339, 894),
    MIRKWOOD_MOUNTAINS(Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1430, 672),
    RHOSGOBEL(Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1343, 762),
    ENCHANTED_RIVER(Region.MIRKWOOD, LOTRFaction.UNALIGNED, 1396, 650),
    RIVER_GATE(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1474, 696),
    EAST_BIGHT(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1437, 824),
    OLD_RHOVANION(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1524, 870),
    DORWINION_CROSSROADS(Region.WILDERLAND, LOTRFaction.UNALIGNED, 1680, 882),
    EREBOR(Region.DALE, LOTRFaction.DURINS_FOLK, 1463, 609),
    DALE_CITY(Region.DALE, LOTRFaction.DALE, 1464, 615),
    LONG_LAKE(Region.DALE, LOTRFaction.DALE, 1461, 632),
    RUNNING_FORD(Region.DALE, LOTRFaction.UNALIGNED, 1534, 749),
    REDWATER_FORD(Region.DALE, LOTRFaction.UNALIGNED, 1651, 690),
    DALE_CROSSROADS(Region.DALE, LOTRFaction.UNALIGNED, 1567, 680),
    DALE_PORT(Region.DALE, LOTRFaction.UNALIGNED, 1657, 768),
    WEST_PEAK(Region.IRON_HILLS, LOTRFaction.UNALIGNED, 1588, 608),
    EAST_PEAK(Region.IRON_HILLS, LOTRFaction.UNALIGNED, 1729, 610),
    CARAS_GALADHON(Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1242, 902),
    CERIN_AMROTH(Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1230, 897),
    NIMRODEL(Region.LOTHLORIEN, LOTRFaction.LOTHLORIEN, 1198, 894),
    DERNDINGLE(Region.FANGORN, LOTRFaction.FANGORN, 1163, 1030),
    WELLINGHALL(Region.FANGORN, LOTRFaction.FANGORN, 1153, 1014),
    TREEBEARD_HILL(Region.FANGORN, LOTRFaction.FANGORN, 1200, 1030),
    WOLD(Region.ROHAN, LOTRFaction.UNALIGNED, 1285, 1015),
    EDORAS(Region.ROHAN, LOTRFaction.ROHAN, 1190, 1148),
    HELMS_DEEP(Region.ROHAN, LOTRFaction.UNALIGNED, 1128, 1115),
    HELMS_CROSSROADS(Region.ROHAN, LOTRFaction.UNALIGNED, 1136, 1108),
    URUK_HIGHLANDS(Region.ROHAN, LOTRFaction.ISENGARD, 1131, 1057),
    MERING_STREAM(Region.ROHAN, LOTRFaction.UNALIGNED, 1299, 1202),
    ENTWADE(Region.ROHAN, LOTRFaction.UNALIGNED, 1239, 1104),
    EASTMARK(Region.ROHAN, LOTRFaction.UNALIGNED, 1286, 1130),
    ALDBURG(Region.ROHAN, LOTRFaction.UNALIGNED, 1223, 1178),
    GRIMSLADE(Region.ROHAN, LOTRFaction.UNALIGNED, 1153, 1122),
    CORSAIRS_LANDING(Region.ROHAN, LOTRFaction.UNALIGNED, 992, 1113),
    FRECA_HOLD(Region.ROHAN, LOTRFaction.UNALIGNED, 1099, 1144),
    DUNHARROW(Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 1175, 1154),
    TARLANG(Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 1205, 1213),
    RAS_MORTHIL(Region.WHITE_MOUNTAINS, LOTRFaction.UNALIGNED, 845, 1332),
    MINAS_TIRITH(Region.GONDOR, LOTRFaction.GONDOR, 1419, 1247),
    CAIR_ANDROS(Region.GONDOR, LOTRFaction.GONDOR, 1427, 1207),
    HALIFIRIEN(Region.GONDOR, LOTRFaction.UNALIGNED, 1309, 1205),
    CALENHAD(Region.GONDOR, LOTRFaction.UNALIGNED, 1330, 1212),
    MINRIMMON(Region.GONDOR, LOTRFaction.UNALIGNED, 1350, 1219),
    ERELAS(Region.GONDOR, LOTRFaction.UNALIGNED, 1367, 1222),
    NARDOL(Region.GONDOR, LOTRFaction.UNALIGNED, 1384, 1228),
    EILENACH(Region.GONDOR, LOTRFaction.UNALIGNED, 1402, 1228),
    AMON_DIN(Region.GONDOR, LOTRFaction.UNALIGNED, 1416, 1231),
    OSGILIATH_WEST(Region.GONDOR, LOTRFaction.UNALIGNED, 1428, 1246),
    OSGILIATH_EAST(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1435, 1246),
    EMYN_ARNEN(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1437, 1267),
    HENNETH_ANNUN(Region.ITHILIEN, LOTRFaction.GONDOR, 1443, 1192),
    CROSSROADS_ITHILIEN(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1450, 1236),
    NORTH_ITHILIEN(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1447, 1151),
    CROSSINGS_OF_POROS(Region.ITHILIEN, LOTRFaction.UNALIGNED, 1442, 1370),
    PELARGIR(Region.LEBENNIN, LOTRFaction.GONDOR, 1390, 1348),
    LINHIR(Region.LEBENNIN, LOTRFaction.UNALIGNED, 1292, 1342),
    ANDUIN_MOUTH(Region.LEBENNIN, LOTRFaction.UNALIGNED, 1273, 1369),
    IMLOTH_MELUI(Region.LOSSARNACH, LOTRFaction.UNALIGNED, 1397, 1254),
    CROSSINGS_ERUI(Region.LOSSARNACH, LOTRFaction.UNALIGNED, 1412, 1272),
    CALEMBEL(Region.LAMEDON, LOTRFaction.GONDOR, 1235, 1248),
    ETHRING(Region.LAMEDON, LOTRFaction.UNALIGNED, 1256, 1259),
    ERECH(Region.BLACKROOT_VALE, LOTRFaction.GONDOR, 1186, 1205),
    GREEN_HILLS(Region.PINNATH_GELIN, LOTRFaction.UNALIGNED, 1045, 1273),
    DOL_AMROTH(Region.DOR_EN_ERNIL, LOTRFaction.GONDOR, 1158, 1333),
    EDHELLOND(Region.DOR_EN_ERNIL, LOTRFaction.GONDOR, 1189, 1293),
    TARNOST(Region.DOR_EN_ERNIL, LOTRFaction.UNALIGNED, 1241, 1300),
    TOLFALAS_ISLAND(Region.TOLFALAS, LOTRFaction.UNALIGNED, 1240, 1414),
    AMON_HEN(Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1335, 1131),
    AMON_LHAW(Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1372, 1120),
    ARGONATH(Region.EMYN_MUIL, LOTRFaction.UNALIGNED, 1347, 1112),
    NORTH_UNDEEP(Region.BROWN_LANDS, LOTRFaction.UNALIGNED, 1319, 988),
    SOUTH_UNDEEP(Region.BROWN_LANDS, LOTRFaction.UNALIGNED, 1335, 1024),
    MORANNON(Region.DAGORLAD, LOTRFaction.UNALIGNED, 1470, 1131),
    UDUN(Region.MORDOR, LOTRFaction.MORDOR, 1470, 1145),
    MOUNT_DOOM(Region.MORDOR, LOTRFaction.MORDOR, 1533, 1204),
    BARAD_DUR(Region.MORDOR, LOTRFaction.MORDOR, 1573, 1196),
    MINAS_MORGUL(Region.MORDOR, LOTRFaction.MORDOR, 1461, 1239),
    DURTHANG(Region.MORDOR, LOTRFaction.MORDOR, 1464, 1159),
    CARACH_ANGREN(Region.MORDOR, LOTRFaction.MORDOR, 1493, 1166),
    CIRITH_UNGOL(Region.MORDOR, LOTRFaction.MORDOR, 1479, 1225),
    MORIGOST(Region.MORDOR, LOTRFaction.MORDOR, 1558, 1286),
    NARGROTH(Region.MORDOR, LOTRFaction.MORDOR, 1640, 1248),
    AMON_ANGREN(Region.MORDOR, LOTRFaction.MORDOR, 1663, 1245),
    SEREGOST(Region.MORDOR, LOTRFaction.MORDOR, 1682, 1214),
    FELLBEASTS(Region.MORDOR, LOTRFaction.MORDOR, 1754, 1164),
    EASTERN_GUARD(Region.MORDOR, LOTRFaction.MORDOR, 1840, 1137),
    NURNEN_NORTHERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1696, 1324),
    NURNEN_SOUTHERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1718, 1369),
    NURNEN_WESTERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1650, 1363),
    NURNEN_EASTERN_SHORE(Region.NURN, LOTRFaction.MORDOR, 1758, 1316),
    THAURBAND(Region.NURN, LOTRFaction.MORDOR, 1643, 1354),
    VALLEY_OF_SPIDERS(Region.NAN_UNGOL, LOTRFaction.MORDOR, 1512, 1400),
    DORWINION_PORT(Region.DORWINION, LOTRFaction.UNALIGNED, 1784, 863),
    DORWINION_COURT(Region.DORWINION, LOTRFaction.DORWINION, 1758, 939),
    DORWINION_FORD(Region.DORWINION, LOTRFaction.UNALIGNED, 1776, 986),
    DORWINION_HILLS(Region.DORWINION, LOTRFaction.UNALIGNED, 1733, 950),
    RHUN_ROAD_WAY(Region.RHUN, LOTRFaction.UNALIGNED, 2228, 835),
    BALCARAS(Region.RHUN, LOTRFaction.UNALIGNED, 2231, 1058),
    KHAND_NORTH_ROAD(Region.RHUN, LOTRFaction.UNALIGNED, 1932, 1331),
    RHUN_CAPITAL(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1867, 984),
    KHAMUL_TOWER(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1888, 878),
    MORDOR_FORD(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1834, 1112),
    RHUN_NORTH_CITY(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1903, 914),
    BAZYLAN(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1921, 889),
    BORDER_TOWN(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1794, 979),
    RHUN_SEA_CITY(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1837, 956),
    RHUN_NORTH_FORD(Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 1942, 811),
    RHUN_NORTHEAST(Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 2045, 815),
    RHUN_SOUTH_PASS(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1869, 1055),
    RHUN_EAST_CITY(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 2010, 962),
    RHUN_EAST_TOWN(Region.RHUN_KHAGANATE, LOTRFaction.RHUDEL, 1983, 936),
    RHUN_SOUTHEAST(Region.RHUN_KHAGANATE, LOTRFaction.UNALIGNED, 1900, 1141),
    BARAZ_DUM(Region.RED_MOUNTAINS, LOTRFaction.UNALIGNED, 2326, 800),
    CROSSINGS_OF_HARAD(Region.HARONDOR, LOTRFaction.UNALIGNED, 1503, 1544),
    HARNEN_SEA_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1343, 1561),
    HARNEN_ROAD_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1518, 1563),
    HARNEN_BLACK_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1566, 1482),
    CROSSINGS_OF_LITHNEN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1539, 1545),
    HARNEN_RIVER_TOWN(Region.HARNEDOR, LOTRFaction.NEAR_HARAD, 1447, 1558),
    KHAND_FORD(Region.LOSTLADEN, LOTRFaction.UNALIGNED, 1778, 1432),
    UMBAR_CITY(Region.UMBAR, LOTRFaction.NEAR_HARAD, 1214, 1689),
    UMBAR_GATE(Region.UMBAR, LOTRFaction.UNALIGNED, 1252, 1698),
    GATE_HERUMOR(Region.UMBAR, LOTRFaction.NEAR_HARAD, 1097, 1721),
    CEDAR_ROAD(Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1034, 1848),
    FERTILE_VALLEY(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1169, 1821),
    GARDENS_BERUTHIEL(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1245, 1781),
    AIN_AL_HARAD(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1265, 1737),
    GATE_FUINUR(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1218, 1631),
    COAST_FORTRESS(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1245, 1582),
    SANDHILL_TOWN(Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1277, 1600),
    COAST_RIVER_TOWN(Region.SOUTHRON_COASTS, LOTRFaction.UNALIGNED, 1080, 1837),
    COAST_CITY(Region.SOUTHRON_COASTS, LOTRFaction.NEAR_HARAD, 1165, 1742),
    DESERT_TOWN(Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1563, 1611),
    SOUTH_DESERT_TOWN(Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1141, 1976),
    DESERT_RIVER_TOWN(Region.HARAD_DESERT, LOTRFaction.UNALIGNED, 1191, 1984),
    GULF_OF_HARAD(Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1724, 1982),
    GULF_CITY(Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1640, 1922),
    GULF_FORD(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1686, 2032),
    GULF_TRADE_TOWN(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1692, 2001),
    GULF_NORTH_TOWN(Region.GULF_HARAD, LOTRFaction.NEAR_HARAD, 1626, 1874),
    GULF_EAST_BAY(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 2036, 2081),
    GULF_EAST_PORT(Region.GULF_HARAD, LOTRFaction.UNALIGNED, 1847, 2049),
    MOUNT_SAND(Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 959, 1899),
    MOUNT_GREEN(Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 884, 2372),
    MOUNT_THUNDER(Region.HARAD_MOUNTAINS, LOTRFaction.UNALIGNED, 1019, 2590),
    GREAT_PLAINS_NORTH(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1308, 2067),
    GREAT_PLAINS_SOUTH(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1462, 2452),
    GREAT_PLAINS_WEST(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1048, 2215),
    GREAT_PLAINS_EAST(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1637, 2176),
    GREEN_VALLEY(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1557, 2622),
    HARAD_LAKES(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1774, 2310),
    LAKE_HARAD(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1100, 2592),
    HARADUIN_MOUTH(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1846, 2838),
    ISLE_MIST(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1533, 3573),
    TAURELONDE(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 901, 2613),
    HARAD_HORN(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1105, 3778),
    TOWN_BONES(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1832, 2188),
    HARADUIN_BRIDGE(Region.FAR_HARAD, LOTRFaction.UNALIGNED, 1621, 2673),
    JUNGLE_CITY_TRADE(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 952, 2656),
    JUNGLE_CITY_OLD(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1084, 2670),
    JUNGLE_CITY_NORTH(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1419, 2604),
    JUNGLE_CITY_EAST(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1594, 2766),
    JUNGLE_CITY_CAPITAL(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1380, 2861),
    JUNGLE_CITY_DEEP(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1184, 3237),
    JUNGLE_CITY_WATCH(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1590, 2940),
    JUNGLE_CITY_CAVES(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1257, 3054),
    JUNGLE_CITY_STONE(Region.FAR_HARAD_JUNGLE, LOTRFaction.TAURETHRIM, 1236, 2787),
    JUNGLE_LAKES(Region.FAR_HARAD_JUNGLE, LOTRFaction.UNALIGNED, 1550, 2856),
    TROLL_ISLAND(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1966, 2342),
    BLACK_COAST(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1936, 2496),
    BLOOD_RIVER(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1897, 2605),
    SHADOW_POINT(Region.PERTOROGWAITH, LOTRFaction.UNALIGNED, 1952, 2863),
    OLD_JUNGLE_RUIN(Region.PERTOROGWAITH_FOREST, LOTRFaction.UNALIGNED, 1834, 2523);

    private Region region;
    public LOTRFaction faction;
    private int imgX;
    private int imgY;
    private int xCoord;
    private int zCoord;
    private boolean isHidden;

    LOTRWaypoint(Region r, LOTRFaction f, int x, int y) {
        this(r, f, x, y, false);
    }

    LOTRWaypoint(Region r, LOTRFaction f, int x, int y, boolean hide) {
        this.region = r;
        this.region.waypoints.add(this);
        this.faction = f;
        this.imgX = x;
        this.imgY = y;
        this.xCoord = LOTRWaypoint.mapToWorldX(x);
        this.zCoord = LOTRWaypoint.mapToWorldZ(y);
        this.isHidden = hide;
    }

    public static int mapToWorldX(double x) {
        return (int)Math.round((x - 810.0 + 0.5) * LOTRGenLayerWorld.scale);
    }

    public static int mapToWorldZ(double z) {
        return (int)Math.round((z - 730.0 + 0.5) * LOTRGenLayerWorld.scale);
    }

    public static int mapToWorldR(double r) {
        return (int)Math.round(r * LOTRGenLayerWorld.scale);
    }

    public static int worldToMapX(double x) {
        return (int)Math.round(x / LOTRGenLayerWorld.scale - 0.5 + 810.0);
    }

    public static int worldToMapZ(double z) {
        return (int)Math.round(z / LOTRGenLayerWorld.scale - 0.5 + 730.0);
    }

    public static int worldToMapR(double r) {
        return (int)Math.round(r / LOTRGenLayerWorld.scale);
    }

    @Override
    public int getX() {
        return this.imgX;
    }

    @Override
    public int getY() {
        return this.imgY;
    }

    @Override
    public int getXCoord() {
        return this.xCoord;
    }

    @Override
    public int getZCoord() {
        return this.zCoord;
    }

    @Override
    public int getYCoord(World world, int i, int k) {
        return LOTRMod.getTrueTopBlock(world, i, k);
    }

    @Override
    public int getYCoordSaved() {
        return 64;
    }

    @Override
    public String getCodeName() {
        return this.name();
    }

    @Override
    public String getDisplayName() {
        return StatCollector.translateToLocal("lotr.waypoint." + this.getCodeName());
    }

    @Override
    public String getLoreText(EntityPlayer entityplayer) {
        return StatCollector.translateToLocal("lotr.waypoint." + this.getCodeName() + ".info");
    }

    @Override
    public boolean hasPlayerUnlocked(EntityPlayer entityplayer) {
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if (pd.isFTRegionUnlocked(this.region)) {
            if (this.isCompatibleAlignment(entityplayer)) {
                return true;
            }
            if (this.isConquestUnlockable(entityplayer)) {
                return this.isConquered(entityplayer);
            }
        }
        return false;
    }

    public boolean isCompatibleAlignment(EntityPlayer entityplayer) {
        if (this.faction == LOTRFaction.UNALIGNED) {
            return true;
        }
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        return pd.getAlignment(this.faction) >= 0.0f;
    }

    public boolean isConquestUnlockable(EntityPlayer entityplayer) {
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        World world = entityplayer.worldObj;
        LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(this.getXCoord(), this.getZCoord());
        LOTRFaction pledgeFac = pd.getPledgeFaction();
        return pledgeFac != null && pledgeFac.isBadRelation(this.faction) && LOTRConquestGrid.getConquestEffectIn(world, zone, pledgeFac) == LOTRConquestGrid.ConquestEffective.EFFECTIVE;
    }

    private boolean isConquered(EntityPlayer entityplayer) {
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        World world = entityplayer.worldObj;
        LOTRConquestZone zone = LOTRConquestGrid.getZoneByWorldCoords(this.getXCoord(), this.getZCoord());
        LOTRFaction pledgeFac = pd.getPledgeFaction();
        return pledgeFac != null && zone.getConquestStrength(pledgeFac, world) >= 500.0f;
    }

    public boolean isUnlockedByConquest(EntityPlayer entityplayer) {
        return !this.isCompatibleAlignment(entityplayer) && this.isConquestUnlockable(entityplayer) && this.isConquered(entityplayer);
    }

    @Override
    public LOTRAbstractWaypoint.WaypointLockState getLockState(EntityPlayer entityplayer) {
        if (this.hasPlayerUnlocked(entityplayer)) {
            return this.isUnlockedByConquest(entityplayer) ? LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED_CONQUEST : LOTRAbstractWaypoint.WaypointLockState.STANDARD_UNLOCKED;
        }
        return LOTRAbstractWaypoint.WaypointLockState.STANDARD_LOCKED;
    }

    @Override
    public boolean isHidden() {
        return this.isHidden;
    }

    @Override
    public int getID() {
        return this.ordinal();
    }

    public static LOTRWaypoint waypointForName(String name) {
        for (LOTRWaypoint wp : LOTRWaypoint.values()) {
            if (!wp.getCodeName().equals(name)) continue;
            return wp;
        }
        return null;
    }

    public static List<LOTRAbstractWaypoint> listAllWaypoints() {
        ArrayList<LOTRAbstractWaypoint> list = new ArrayList<>();
        list.addAll(Arrays.asList(LOTRWaypoint.values()));
        return list;
    }

    public static Region regionForName(String name) {
        for (Region region : Region.values()) {
            if (!region.name().equals(name)) continue;
            return region;
        }
        return null;
    }

    public static Region regionForID(int id) {
        for (Region region : Region.values()) {
            if (region.ordinal() != id) continue;
            return region;
        }
        return null;
    }

    public static enum Region {
        OCEAN,
        MENELTARMA,
        SHIRE,
        OLD_FOREST,
        LINDON,
        BLUE_MOUNTAINS,
        ERIADOR,
        BREE_LAND,
        MIDGEWATER,
        LONE_LANDS,
        RIVENDELL_VALE,
        TROLLSHAWS,
        COLDFELLS,
        ETTENMOORS,
        ANGMAR,
        EREGION,
        DUNLAND,
        ENEDWAITH,
        NAN_CURUNIR,
        FORODWAITH,
        MISTY_MOUNTAINS,
        GREY_MOUNTAINS,
        VALES_OF_ANDUIN,
        WOODLAND_REALM,
        MIRKWOOD,
        WILDERLAND,
        DALE,
        IRON_HILLS,
        LOTHLORIEN,
        FANGORN,
        ROHAN,
        WHITE_MOUNTAINS,
        PUKEL,
        GONDOR,
        ITHILIEN,
        LEBENNIN,
        LOSSARNACH,
        LAMEDON,
        BLACKROOT_VALE,
        PINNATH_GELIN,
        DOR_EN_ERNIL,
        TOLFALAS,
        EMYN_MUIL,
        NINDALF,
        BROWN_LANDS,
        DAGORLAD,
        MORDOR,
        NURN,
        NAN_UNGOL,
        DORWINION,
        RHUN,
        RHUN_KHAGANATE,
        TOL_RHUNAER,
        RED_MOUNTAINS,
        HARONDOR,
        HARNEDOR,
        LOSTLADEN,
        UMBAR,
        SOUTHRON_COASTS,
        HARAD_DESERT,
        GULF_HARAD,
        HARAD_MOUNTAINS,
        FAR_HARAD,
        FAR_HARAD_JUNGLE,
        PERTOROGWAITH,
        PERTOROGWAITH_FOREST;

        public List<LOTRWaypoint> waypoints = new ArrayList<>();
    }

}

