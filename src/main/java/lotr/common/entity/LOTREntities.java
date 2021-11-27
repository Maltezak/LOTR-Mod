package lotr.common.entity;

import java.util.*;

import cpw.mods.fml.common.registry.EntityRegistry;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.*;
import lotr.common.entity.item.*;
import lotr.common.entity.npc.*;
import lotr.common.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.world.World;

public class LOTREntities {
    public static HashMap<Integer, SpawnEggInfo> spawnEggs = new LinkedHashMap<>();
    public static Map<String, Integer> stringToIDMapping = new HashMap<>();
    public static Map<Integer, String> IDToStringMapping = new HashMap<>();
    public static Map<Class<? extends Entity>, Integer> classToIDMapping = new HashMap<>();

    public static void registerCreature(Class<? extends Entity> entityClass, String name, int id, int eggBackground, int eggSpots) {
        LOTREntities.registerEntity(entityClass, name, id, 80, 3, true);
        spawnEggs.put(id, new SpawnEggInfo(id, eggBackground, eggSpots));
    }

    public static void registerCreature(Class<? extends Entity> entityClass, String name, int id) {
        LOTREntities.registerEntity(entityClass, name, id, 80, 3, true);
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int updateRange, int updateFreq, boolean sendVelocityUpdates) {
        EntityRegistry.registerModEntity(entityClass, name, id, LOTRMod.instance, updateRange, updateFreq, sendVelocityUpdates);
        String fullName = (String)EntityList.classToStringMapping.get(entityClass);
        stringToIDMapping.put(fullName, id);
        IDToStringMapping.put(id, fullName);
        classToIDMapping.put(entityClass, id);
    }

    public static void registerEntities() {
        LOTREntities.registerCreature(LOTREntityHorse.class, "Horse", 1, 8601889, 4136462);
        LOTREntities.registerCreature(LOTREntityHobbit.class, "Hobbit", 2, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityMordorOrc.class, "MordorOrc", 3, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityShirePony.class, "ShirePony", 4, 6109994, 13017995);
        LOTREntities.registerCreature(LOTREntityMordorWarg.class, "MordorWarg", 5, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityGondorSoldier.class, "GondorSoldier", 6, 5327948, 15063770);
        LOTREntities.registerCreature(LOTREntityGondorMan.class, "GondorMan", 7, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGaladhrimElf.class, "GaladhrimElf", 8, 9337185, 15920555);
        LOTREntities.registerCreature(LOTREntityHobbitBartender.class, "HobbitBartender", 9, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityRohanMan.class, "RohanMan", 10, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityGaladhrimWarrior.class, "GaladhrimWarrior", 11, 12697274, 15382870);
        LOTREntities.registerCreature(LOTREntityMordorOrcBombardier.class, "MordorOrcBombardier", 12, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityMordorOrcTrader.class, "MordorOrcTrader", 13, 5979436, 13421772);
        LOTREntities.registerCreature(LOTREntityMordorOrcArcher.class, "MordorOrcArcher", 14, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityGondorRuinsWraith.class, "GondorRuinsWraith", 15, 12698049, 4802889);
        LOTREntities.registerCreature(LOTREntityGondorBlacksmith.class, "GondorBlacksmith", 16, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGaladhrimTrader.class, "GaladhrimTrader", 17, 2047778, 16762150);
        LOTREntities.registerCreature(LOTREntityDwarf.class, "Dwarf", 18, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityDwarfWarrior.class, "DwarfWarrior", 19, 2238506, 7108730);
        LOTREntities.registerCreature(LOTREntityDwarfMiner.class, "DwarfMiner", 20, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityMarshWraith.class, "MarshWraith", 21, 10524036, 6179636);
        LOTREntities.registerCreature(LOTREntityMordorWargBombardier.class, "MordorWargBombardier", 22, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityMordorOrcMercenaryCaptain.class, "MordorOrcMercenaryCaptain", 23, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityGondorianCaptain.class, "GondorianCaptain", 24, 5327948, 15063770);
        LOTREntities.registerCreature(LOTREntityDwarfCommander.class, "DwarfCommander", 25, 2238506, 7108730);
        LOTREntities.registerCreature(LOTREntityDwarfAxeThrower.class, "DwarfAxeThrower", 26, 2238506, 7108730);
        LOTREntities.registerCreature(LOTREntityGondorArcher.class, "GondorArcher", 27, 5327948, 15063770);
        LOTREntities.registerCreature(LOTREntityUrukHai.class, "UrukHai", 28, 2369050, 5790015);
        LOTREntities.registerCreature(LOTREntityUrukHaiCrossbower.class, "UrukHaiCrossbower", 29, 2369050, 5790015);
        LOTREntities.registerCreature(LOTREntityUrukHaiBerserker.class, "UrukHaiBerserker", 30, 2369050, 14408662);
        LOTREntities.registerCreature(LOTREntityUrukHaiTrader.class, "UrukHaiTrader", 31, 5979436, 13421772);
        LOTREntities.registerCreature(LOTREntityUrukHaiMercenaryCaptain.class, "UrukHaiMercenaryCaptain", 32, 2369050, 5790015);
        LOTREntities.registerCreature(LOTREntityTroll.class, "Troll", 33, 10848082, 4796702);
        LOTREntities.registerCreature(LOTREntityOlogHai.class, "OlogHai", 34, 4147260, 2237218);
        LOTREntities.registerCreature(LOTREntityGaladhrimLord.class, "GaladhrimLord", 35, 12697274, 15382870);
        LOTREntities.registerCreature(LOTREntityUrukHaiSapper.class, "UrukHaiSapper", 36, 2369050, 5790015);
        LOTREntities.registerCreature(LOTREntityMirkwoodSpider.class, "MirkwoodSpider", 37, 2630945, 1315088);
        LOTREntities.registerCreature(LOTREntityWoodElf.class, "WoodElf", 38, 2314529, 16764574);
        LOTREntities.registerCreature(LOTREntityWoodElfScout.class, "WoodElfScout", 39, 336140, 3891251);
        LOTREntities.registerCreature(LOTREntityRohanBarrowWraith.class, "RohanBarrowWraith", 40, 12698049, 4802889);
        LOTREntities.registerCreature(LOTREntityRohirrimWarrior.class, "Rohirrim", 41, 5524296, 13546384);
        LOTREntities.registerCreature(LOTREntityRohirrimArcher.class, "RohirrimArcher", 42, 5524296, 13546384);
        LOTREntities.registerCreature(LOTREntityRohirrimMarshal.class, "RohirrimMarshal", 43, 6180940, 14857848);
        LOTREntities.registerCreature(LOTREntityHobbitBounder.class, "HobbitShirriff", 44, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityHobbitShirriff.class, "HobbitShirriffChief", 45, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityRohanBlacksmith.class, "RohanBlacksmith", 46, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRangerNorth.class, "RangerNorth", 47, 4473912, 2302748);
        LOTREntities.registerCreature(LOTREntityRangerIthilien.class, "RangerIthilien", 48, 4015141, 1382669);
        LOTREntities.registerCreature(LOTREntityDunlendingWarrior.class, "DunlendingWarrior", 49, 5192753, 9337975);
        LOTREntities.registerCreature(LOTREntityDunlendingArcher.class, "DunlendingArcher", 50, 5192753, 9337975);
        LOTREntities.registerCreature(LOTREntityDunlendingWarlord.class, "DunlendingWarlord", 51, 5192753, 9337975);
        LOTREntities.registerCreature(LOTREntityDunlending.class, "Dunlending", 52, 15897714, 3679258);
        LOTREntities.registerCreature(LOTREntityDunlendingBartender.class, "DunlendingBartender", 53, 15897714, 3679258);
        LOTREntities.registerCreature(LOTREntityRohanShieldmaiden.class, "RohanShieldmaiden", 54, 5524296, 13546384);
        LOTREntities.registerCreature(LOTREntityEnt.class, "Ent", 55, 3681048, 6788650);
        LOTREntities.registerCreature(LOTREntityMountainTroll.class, "MountainTroll", 56, 9991001, 5651753);
        LOTREntities.registerCreature(LOTREntityMountainTrollChieftain.class, "MountainTrollChieftain", 57);
        LOTREntities.registerCreature(LOTREntityHuorn.class, "Huorn", 58, 3681048, 6788650);
        LOTREntities.registerCreature(LOTREntityDarkHuorn.class, "DarkHuorn", 59, 2498067, 2643485);
        LOTREntities.registerCreature(LOTREntityWoodElfWarrior.class, "WoodElfWarrior", 60, 12231576, 5856300);
        LOTREntities.registerCreature(LOTREntityWoodElfCaptain.class, "WoodElfCaptain", 61, 12231576, 5856300);
        LOTREntities.registerCreature(LOTREntityRohanMeadhost.class, "RohanMeadhost", 62, 6567206, 14392402);
        LOTREntities.registerCreature(LOTREntityButterfly.class, "Butterfly", 63, 2498589, 16747542);
        LOTREntities.registerCreature(LOTREntityMidges.class, "Midges", 64, 5653040, 1972242);
        LOTREntities.registerCreature(LOTREntityAngmarOrcMercenaryCaptain.class, "AngmarOrcMercenaryCaptain", 65, 3224873, 5601097);
        LOTREntities.registerCreature(LOTREntityDunedain.class, "Dunedain", 66, 15638664, 6832694);
        LOTREntities.registerCreature(LOTREntityNurnSlave.class, "NurnSlave", 67, 8613981, 4864555);
        LOTREntities.registerCreature(LOTREntityRabbit.class, "Rabbit", 68, 9860178, 5520938);
        LOTREntities.registerCreature(LOTREntityWildBoar.class, "Boar", 69, 6635562, 4069378);
        LOTREntities.registerCreature(LOTREntityHobbitOrcharder.class, "HobbitOrcharder", 70, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityMordorOrcSlaver.class, "MordorOrcSlaver", 71, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityMordorSpider.class, "MordorSpider", 72, 1511181, 12917534);
        LOTREntities.registerCreature(LOTREntityMordorOrcSpiderKeeper.class, "MordorOrcSpiderKeeper", 73, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityAngmarOrc.class, "AngmarOrc", 74, 3224873, 5601097);
        LOTREntities.registerCreature(LOTREntityAngmarOrcArcher.class, "AngmarOrcArcher", 75, 3224873, 5601097);
        LOTREntities.registerCreature(LOTREntityAngmarOrcBombardier.class, "AngmarOrcBombardier", 76, 3224873, 5601097);
        LOTREntities.registerCreature(LOTREntityGundabadOrc.class, "GundabadOrc", 77, 3352346, 8548435);
        LOTREntities.registerCreature(LOTREntityGundabadOrcArcher.class, "GundabadOrcArcher", 78, 3352346, 8548435);
        LOTREntities.registerCreature(LOTREntityGundabadOrcMercenaryCaptain.class, "GundabadOrcMercenaryCaptain", 79, 2563350, 6382678);
        LOTREntities.registerCreature(LOTREntityRangerNorthCaptain.class, "RangerNorthCaptain", 80, 4473912, 2302748);
        LOTREntities.registerCreature(LOTREntityGundabadWarg.class, "GundabadWarg", 81, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityAngmarWarg.class, "AngmarWarg", 82, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityAngmarWargBombardier.class, "AngmarWargBombardier", 83, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityUrukWarg.class, "UrukWarg", 84, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityUrukWargBombardier.class, "UrukWargBombardier", 85, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityLion.class, "Lion", 86, 13345354, 10838576);
        LOTREntities.registerCreature(LOTREntityLioness.class, "Lioness", 87, 13346908, 11238466);
        LOTREntities.registerCreature(LOTREntityGiraffe.class, "Giraffe", 88, 13608551, 6966048);
        LOTREntities.registerCreature(LOTREntityZebra.class, "Zebra", 89, 15000804, 4340020);
        LOTREntities.registerCreature(LOTREntityRhino.class, "Rhino", 90, 6118481, 12171165);
        LOTREntities.registerCreature(LOTREntityCrocodile.class, "Crocodile", 91, 2896659, 986886);
        LOTREntities.registerCreature(LOTREntityNearHaradrim.class, "NearHaradrim", 92, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityNearHaradrimWarrior.class, "NearHaradrimWarrior", 93, 2171169, 11868955);
        LOTREntities.registerCreature(LOTREntityHighElf.class, "HighElf", 94, 16761223, 15721387);
        LOTREntities.registerCreature(LOTREntityGemsbok.class, "Gemsbok", 95, 11759423, 15920343);
        LOTREntities.registerCreature(LOTREntityFlamingo.class, "Flamingo", 96, 16087966, 16374243);
        LOTREntities.registerCreature(LOTREntityHobbitFarmer.class, "HobbitFarmer", 97, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityHobbitFarmhand.class, "HobbitFarmhand", 98, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityHighElfWarrior.class, "HighElfWarrior", 99, 14935016, 7040410);
        LOTREntities.registerCreature(LOTREntityHighElfLord.class, "HighElfLord", 100, 14935016, 7040410);
        LOTREntities.registerCreature(LOTREntityGondorBannerBearer.class, "GondorBannerBearer", 101, 5327948, 15063770);
        LOTREntities.registerCreature(LOTREntityRohanBannerBearer.class, "RohanBannerBearer", 102, 5524296, 13546384);
        LOTREntities.registerCreature(LOTREntityMordorBannerBearer.class, "MordorBannerBearer", 103, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityGaladhrimBannerBearer.class, "GaladhrimBannerBearer", 104, 12697274, 15382870);
        LOTREntities.registerCreature(LOTREntityWoodElfBannerBearer.class, "WoodElfBannerBearer", 105, 12231576, 5856300);
        LOTREntities.registerCreature(LOTREntityDunlendingBannerBearer.class, "DunlendingBannerBearer", 106, 5192753, 9337975);
        LOTREntities.registerCreature(LOTREntityUrukHaiBannerBearer.class, "UrukHaiBannerBearer", 107, 2369050, 5790015);
        LOTREntities.registerCreature(LOTREntityDwarfBannerBearer.class, "DwarfBannerBearer", 108, 2238506, 7108730);
        LOTREntities.registerCreature(LOTREntityAngmarBannerBearer.class, "AngmarBannerBearer", 109, 3224873, 5601097);
        LOTREntities.registerCreature(LOTREntityNearHaradBannerBearer.class, "NearHaradBannerBearer", 110, 2171169, 11868955);
        LOTREntities.registerCreature(LOTREntityHighElfBannerBearer.class, "HighElfBannerBearer", 111, 14935016, 7040410);
        LOTREntities.registerCreature(LOTREntityJungleScorpion.class, "JungleScorpion", 112, 2630945, 1315088);
        LOTREntities.registerCreature(LOTREntityDesertScorpion.class, "DesertScorpion", 113, 16376764, 11772801);
        LOTREntities.registerCreature(LOTREntityBird.class, "Bird", 114, 7451872, 7451872);
        LOTREntities.registerCreature(LOTREntityCrebain.class, "Crebain", 115, 2434341, 10490368);
        LOTREntities.registerCreature(LOTREntityCamel.class, "Camel", 116, 13150061, 9203768);
        LOTREntities.registerCreature(LOTREntityNearHaradrimArcher.class, "NearHaradrimArcher", 117, 2171169, 11868955);
        LOTREntities.registerCreature(LOTREntityNearHaradrimWarlord.class, "NearHaradrimWarlord", 118, 2171169, 11868955);
        LOTREntities.registerCreature(LOTREntityBlueDwarf.class, "BlueDwarf", 119, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityBlueDwarfWarrior.class, "BlueDwarfWarrior", 120, 3161673, 6257551);
        LOTREntities.registerCreature(LOTREntityBlueDwarfAxeThrower.class, "BlueDwarfAxeThrower", 121, 3161673, 6257551);
        LOTREntities.registerCreature(LOTREntityBlueDwarfBannerBearer.class, "BlueDwarfBannerBearer", 122, 3161673, 6257551);
        LOTREntities.registerCreature(LOTREntityBlueDwarfCommander.class, "BlueDwarfCommander", 123, 3161673, 6257551);
        LOTREntities.registerCreature(LOTREntityBlueDwarfMiner.class, "BlueDwarfMiner", 124, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntitySouthronBrewer.class, "NearHaradDrinksTrader", 125, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronMiner.class, "NearHaradMineralsTrader", 126, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronFlorist.class, "NearHaradPlantsTrader", 127, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronButcher.class, "NearHaradFoodTrader", 128, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityBlueDwarfMerchant.class, "BlueDwarfMerchant", 129, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityBandit.class, "Bandit", 130, 16225652, 5323553);
        LOTREntities.registerCreature(LOTREntityRangerNorthBannerBearer.class, "RangerNorthBannerBearer", 131, 4473912, 2302748);
        LOTREntities.registerCreature(LOTREntityElk.class, "Elk", 132, 15459801, 11905424);
        LOTREntities.registerCreature(LOTREntityGondorTowerGuard.class, "GondorTowerGuard", 133, 5327948, 15063770);
        LOTREntities.registerCreature(LOTREntityNearHaradMerchant.class, "NearHaradMerchant", 134, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityHaradPyramidWraith.class, "HaradPyramidWraith", 135, 10854007, 15590575);
        LOTREntities.registerCreature(LOTREntityDolGuldurOrc.class, "DolGuldurOrc", 136, 4408654, 2040101);
        LOTREntities.registerCreature(LOTREntityDolGuldurOrcArcher.class, "DolGuldurOrcArcher", 137, 4408654, 2040101);
        LOTREntities.registerCreature(LOTREntityDolGuldurBannerBearer.class, "DolGuldurBannerBearer", 138, 4408654, 2040101);
        LOTREntities.registerCreature(LOTREntityDolGuldurOrcChieftain.class, "DolGuldurChieftain", 139, 4408654, 2040101);
        LOTREntities.registerCreature(LOTREntityMirkTroll.class, "MirkTroll", 140, 4408654, 2040101);
        LOTREntities.registerCreature(LOTREntityGundabadBannerBearer.class, "GundabadBannerBearer", 141, 3352346, 8548435);
        LOTREntities.registerCreature(LOTREntityTermite.class, "Termite", 142, 12748857, 7948066);
        LOTREntities.registerCreature(LOTREntityDikDik.class, "DikDik", 143, 12023867, 6833961);
        LOTREntities.registerCreature(LOTREntityBlackUruk.class, "BlackUruk", 144, 988430, 2830632);
        LOTREntities.registerCreature(LOTREntityBlackUrukArcher.class, "BlackUrukArcher", 145, 988430, 2830632);
        LOTREntities.registerCreature(LOTREntityHalfTroll.class, "HalfTroll", 146, 6903359, 3614236);
        LOTREntities.registerCreature(LOTREntityRangerIthilienCaptain.class, "RangerIthilienCaptain", 147, 4015141, 1382669);
        LOTREntities.registerCreature(LOTREntityRangerIthilienBannerBearer.class, "RangerIthilienBannerBearer", 148, 4015141, 1382669);
        LOTREntities.registerCreature(LOTREntityHalfTrollWarrior.class, "HalfTrollWarrior", 149, 9403002, 6244662);
        LOTREntities.registerCreature(LOTREntityHalfTrollBannerBearer.class, "HalfTrollBannerBearer", 150, 9403002, 6244662);
        LOTREntities.registerCreature(LOTREntityHalfTrollWarlord.class, "HalfTrollWarlord", 151, 9403002, 6244662);
        LOTREntities.registerCreature(LOTREntityAngmarOrcTrader.class, "AngmarOrcTrader", 152, 5979436, 13421772);
        LOTREntities.registerCreature(LOTREntityDolGuldurOrcTrader.class, "DolGuldurOrcTrader", 153, 4408654, 2040101);
        LOTREntities.registerCreature(LOTREntityHalfTrollScavenger.class, "HalfTrollScavenger", 154, 6903359, 3614236);
        LOTREntities.registerCreature(LOTREntityGaladhrimSmith.class, "GaladhrimSmith", 155, 9337185, 15920555);
        LOTREntities.registerCreature(LOTREntityHighElfSmith.class, "HighElfSmith", 156, 16761223, 15721387);
        LOTREntities.registerCreature(LOTREntityWoodElfSmith.class, "WoodElfSmith", 157, 2314529, 16764574);
        LOTREntities.registerCreature(LOTREntitySwanKnight.class, "SwanKnight", 158, 2302535, 15918822);
        LOTREntities.registerCreature(LOTREntityDolAmrothCaptain.class, "DolAmrothCaptain", 159, 2302535, 15918822);
        LOTREntities.registerCreature(LOTREntityDolAmrothBannerBearer.class, "DolAmrothBannerBearer", 160, 3227005, 14278898);
        LOTREntities.registerCreature(LOTREntitySwan.class, "Swan", 161, 16119285, 15571785);
        LOTREntities.registerCreature(LOTREntityMoredain.class, "Moredain", 162, 5323303, 2168848);
        LOTREntities.registerCreature(LOTREntityMoredainWarrior.class, "MoredainWarrior", 163, 8998697, 5057302);
        LOTREntities.registerCreature(LOTREntityMoredainBannerBearer.class, "MoredainBannerBearer", 164, 8998697, 5057302);
        LOTREntities.registerCreature(LOTREntityMoredainChieftain.class, "MoredainChieftain", 165, 13807978, 11166513);
        LOTREntities.registerCreature(LOTREntityMoredainHuntsman.class, "MoredainHuntsman", 166, 5323303, 2168848);
        LOTREntities.registerCreature(LOTREntityMoredainHutmaker.class, "MoredainHutmaker", 167, 5323303, 2168848);
        LOTREntities.registerCreature(LOTREntityDunlendingBerserker.class, "DunlendingBerserker", 168, 5192753, 16050121);
        LOTREntities.registerCreature(LOTREntityAngmarHillman.class, "AngmarHillman", 169, 11828586, 2891544);
        LOTREntities.registerCreature(LOTREntityAngmarHillmanWarrior.class, "AngmarHillmanWarrior", 170, 11828586, 2891544);
        LOTREntities.registerCreature(LOTREntityAngmarHillmanChieftain.class, "AngmarHillmanChieftain", 171, 11828586, 2891544);
        LOTREntities.registerCreature(LOTREntityAngmarHillmanBannerBearer.class, "AngmarHillmanBannerBearer", 172, 11828586, 2891544);
        LOTREntities.registerCreature(LOTREntityAngmarHillmanAxeThrower.class, "AngmarHillmanAxeThrower", 173, 11828586, 2891544);
        LOTREntities.registerCreature(LOTREntityDunlendingAxeThrower.class, "DunlendingAxeThrower", 174, 5192753, 9337975);
        LOTREntities.registerCreature(LOTREntityIronHillsMerchant.class, "IronHillsMerchant", 175, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityMallornEnt.class, "MallornEnt", 176);
        LOTREntities.registerCreature(LOTREntityScrapTrader.class, "ScrapTrader", 177, 16225652, 5323553);
        LOTREntities.registerCreature(LOTREntityTauredain.class, "Tauredain", 178, 4468770, 12948008);
        LOTREntities.registerCreature(LOTREntityTauredainWarrior.class, "TauredainWarrior", 179, 5652267, 9165389);
        LOTREntities.registerCreature(LOTREntityTauredainBannerBearer.class, "TauredainBannerBearer", 180, 5652267, 9165389);
        LOTREntities.registerCreature(LOTREntityTauredainChieftain.class, "TauredainChieftain", 181, 5652267, 9165389);
        LOTREntities.registerCreature(LOTREntityTauredainBlowgunner.class, "TauredainBlowgunner", 182, 5652267, 9165389);
        LOTREntities.registerCreature(LOTREntityBarrowWight.class, "BarrowWight", 183, 529926, 3111505);
        LOTREntities.registerCreature(LOTREntityTauredainShaman.class, "TauredainShaman", 184, 4468770, 12948008);
        LOTREntities.registerCreature(LOTREntityGaladhrimWarden.class, "GaladhrimWarden", 185, 10527645, 8027255);
        LOTREntities.registerCreature(LOTREntityTauredainFarmer.class, "TauredainFarmer", 186, 4468770, 12948008);
        LOTREntities.registerCreature(LOTREntityTauredainFarmhand.class, "TauredainFarmhand", 187, 4468770, 12948008);
        LOTREntities.registerCreature(LOTREntityDwarfSmith.class, "DwarfSmith", 188, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityBlueMountainsSmith.class, "BlueDwarfSmith", 189, 16353133, 15357472);
        LOTREntities.registerCreature(LOTREntityTauredainPyramidWraith.class, "TauredainPyramidWraith", 190, 12698049, 4802889);
        LOTREntities.registerCreature(LOTREntityGundabadUruk.class, "GundabadUruk", 191, 2563350, 6382678);
        LOTREntities.registerCreature(LOTREntityGundabadUrukArcher.class, "GundabadUrukArcher", 192, 2563350, 6382678);
        LOTREntities.registerCreature(LOTREntityIsengardSnaga.class, "IsengardSnaga", 193, 4339500, 8352349);
        LOTREntities.registerCreature(LOTREntityIsengardSnagaArcher.class, "IsengardSnagaArcher", 194, 4339500, 8352349);
        LOTREntities.registerCreature(LOTREntityBanditHarad.class, "BanditHarad", 195, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityDeer.class, "Deer", 196, 5978669, 11968394);
        LOTREntities.registerCreature(LOTREntityDaleMan.class, "DaleMan", 197, 16755851, 5252113);
        LOTREntities.registerCreature(LOTREntityDaleLevyman.class, "DaleLevyman", 198, 7034184, 5252113);
        LOTREntities.registerCreature(LOTREntityDaleSoldier.class, "DaleSoldier", 199, 11776947, 481419);
        LOTREntities.registerCreature(LOTREntityDaleArcher.class, "DaleArcher", 200, 11776947, 481419);
        LOTREntities.registerCreature(LOTREntityDaleBannerBearer.class, "DaleBannerBearer", 201, 11776947, 481419);
        LOTREntities.registerCreature(LOTREntityDaleCaptain.class, "DaleCaptain", 202, 11776947, 481419);
        LOTREntities.registerCreature(LOTREntityDaleBlacksmith.class, "DaleBlacksmith", 203, 16755851, 5252113);
        LOTREntities.registerCreature(LOTREntityDorwinionMan.class, "DorwinionMan", 204, 16755851, 12213157);
        LOTREntities.registerCreature(LOTREntityDorwinionGuard.class, "DorwinionGuard", 205, 9005901, 6178167);
        LOTREntities.registerCreature(LOTREntityDorwinionCaptain.class, "DorwinionCaptain", 206, 9005901, 6178167);
        LOTREntities.registerCreature(LOTREntityDorwinionBannerBearer.class, "DorwinionBannerBearer", 207, 9005901, 6178167);
        LOTREntities.registerCreature(LOTREntityDorwinionElf.class, "DorwinionElf", 208, 16761223, 8538746);
        LOTREntities.registerCreature(LOTREntityDorwinionElfWarrior.class, "DorwinionElfWarrior", 209, 13420999, 8407696);
        LOTREntities.registerCreature(LOTREntityDorwinionElfBannerBearer.class, "DorwinionElfBannerBearer", 210, 13420999, 8407696);
        LOTREntities.registerCreature(LOTREntityDorwinionElfCaptain.class, "DorwinionElfCaptain", 211, 13420999, 8407696);
        LOTREntities.registerCreature(LOTREntityDaleBaker.class, "DaleBaker", 212, 16755851, 5252113);
        LOTREntities.registerCreature(LOTREntityDorwinionElfVintner.class, "DorwinionElfVintner", 213, 9721246, 5648736);
        LOTREntities.registerCreature(LOTREntityDorwinionVinehand.class, "DorwinionVinehand", 214, 16755851, 12213157);
        LOTREntities.registerCreature(LOTREntityDorwinionVinekeeper.class, "DorwinionVinekeeper", 215, 16755851, 12213157);
        LOTREntities.registerCreature(LOTREntityDorwinionMerchantElf.class, "DorwinionMerchant", 216, 16761223, 8538746);
        LOTREntities.registerCreature(LOTREntityDaleMerchant.class, "DaleMerchant", 217, 16755851, 5252113);
        LOTREntities.registerCreature(LOTREntityAurochs.class, "Aurochs", 218, 7488812, 3217935);
        LOTREntities.registerCreature(LOTREntityKineAraw.class, "KineAraw", 219, 16702665, 12890019);
        LOTREntities.registerCreature(LOTREntityDorwinionCrossbower.class, "DorwinionCrossbower", 220, 9005901, 6178167);
        LOTREntities.registerCreature(LOTREntityLossarnachAxeman.class, "LossarnachAxeman", 221, 11578026, 3812901);
        LOTREntities.registerCreature(LOTREntityLossarnachBannerBearer.class, "LossarnachBannerBearer", 222, 11578026, 3812901);
        LOTREntities.registerCreature(LOTREntityBlackUrukBannerBearer.class, "BlackUrukBannerBearer", 223, 988430, 2830632);
        LOTREntities.registerCreature(LOTREntityPelargirMarine.class, "PelargirMarine", 224, 13090494, 1475447);
        LOTREntities.registerCreature(LOTREntityPelargirBannerBearer.class, "PelargirBannerBearer", 225, 13090494, 1475447);
        LOTREntities.registerCreature(LOTREntityPinnathGelinSoldier.class, "PinnathGelinSoldier", 226, 11183011, 29235);
        LOTREntities.registerCreature(LOTREntityPinnathGelinBannerBearer.class, "PinnathGelinBannerBearer", 227, 11183011, 29235);
        LOTREntities.registerCreature(LOTREntityBlackrootSoldier.class, "BlackrootSoldier", 228, 11183011, 3881016);
        LOTREntities.registerCreature(LOTREntityBlackrootBannerBearer.class, "BlackrootBannerBearer", 229, 11183011, 3881016);
        LOTREntities.registerCreature(LOTREntityBlackrootArcher.class, "BlackrootArcher", 230, 11183011, 3881016);
        LOTREntities.registerCreature(LOTREntityGondorLevyman.class, "GondorLevyman", 231, 10789794, 6833716);
        LOTREntities.registerCreature(LOTREntityNanUngolBannerBearer.class, "NanUngolBannerBearer", 232, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityMinasMorgulBannerBearer.class, "MinasMorgulBannerBearer", 233, 3353378, 7042407);
        LOTREntities.registerCreature(LOTREntityDolAmrothSoldier.class, "DolAmrothSoldier", 234, 3227005, 14278898);
        LOTREntities.registerCreature(LOTREntityDolAmrothArcher.class, "DolAmrothArcher", 235, 3227005, 14278898);
        LOTREntities.registerCreature(LOTREntityGondorFarmer.class, "GondorFarmer", 236, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorFarmhand.class, "GondorFarmhand", 237, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityLebenninLevyman.class, "LebenninLevyman", 238, 14866637, 3573666);
        LOTREntities.registerCreature(LOTREntityLebenninBannerBearer.class, "LebenninBannerBearer", 239, 14866637, 3573666);
        LOTREntities.registerCreature(LOTREntityGondorBartender.class, "GondorBartender", 240, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorGreengrocer.class, "GondorGreengrocer", 241, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorLumberman.class, "GondorLumberman", 242, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorMason.class, "GondorMason", 243, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorBrewer.class, "GondorBrewer", 244, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorFlorist.class, "GondorFlorist", 245, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorButcher.class, "GondorButcher", 246, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorFishmonger.class, "GondorFishmonger", 247, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityGondorBaker.class, "GondorBaker", 248, 13547685, 5652538);
        LOTREntities.registerCreature(LOTREntityLossarnachCaptain.class, "LossarnachCaptain", 249, 11578026, 3812901);
        LOTREntities.registerCreature(LOTREntityPelargirCaptain.class, "PelargirCaptain", 250, 13090494, 1475447);
        LOTREntities.registerCreature(LOTREntityPinnathGelinCaptain.class, "PinnathGelinCaptain", 251, 11183011, 29235);
        LOTREntities.registerCreature(LOTREntityBlackrootCaptain.class, "BlackrootCaptain", 252, 11183011, 3881016);
        LOTREntities.registerCreature(LOTREntityLebenninCaptain.class, "LebenninCaptain", 253, 5327948, 15063770);
        LOTREntities.registerCreature(LOTREntityLamedonSoldier.class, "LamedonSoldier", 254, 12103600, 3624035);
        LOTREntities.registerCreature(LOTREntityLamedonArcher.class, "LamedonArcher", 255, 12103600, 3624035);
        LOTREntities.registerCreature(LOTREntityLamedonBannerBearer.class, "LamedonBannerBearer", 256, 12103600, 3624035);
        LOTREntities.registerCreature(LOTREntityLamedonCaptain.class, "LamedonCaptain", 257, 12103600, 3624035);
        LOTREntities.registerCreature(LOTREntityLamedonHillman.class, "LamedonHillman", 258, 13547685, 2108991);
        LOTREntities.registerCreature(LOTREntityRohanFarmhand.class, "RohanFarmhand", 259, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityGorcrow.class, "Gorcrow", 260, 928034, 5451403);
        LOTREntities.registerCreature(LOTREntitySeagull.class, "Seagull", 261, 15920107, 13997863);
        LOTREntities.registerCreature(LOTREntityRohanFarmer.class, "RohanFarmer", 262, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanLumberman.class, "RohanLumberman", 263, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanBuilder.class, "RohanBuilder", 264, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanBrewer.class, "RohanBrewer", 265, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanButcher.class, "RohanButcher", 266, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanFishmonger.class, "RohanFishmonger", 267, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanBaker.class, "RohanBaker", 268, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityRohanOrcharder.class, "RohanOrcharder", 269, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityDunedainBlacksmith.class, "DunedainBlacksmith", 270, 15638664, 6832694);
        LOTREntities.registerCreature(LOTREntityRohanStablemaster.class, "RohanStablemaster", 271, 16424833, 13406801);
        LOTREntities.registerCreature(LOTREntityBear.class, "Bear", 272, 7492416, 4008994);
        LOTREntities.registerCreature(LOTREntityEasterling.class, "Easterling", 273, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingWarrior.class, "EasterlingWarrior", 274, 7486267, 15251832);
        LOTREntities.registerCreature(LOTREntityEasterlingBannerBearer.class, "EasterlingBannerBearer", 275, 7486267, 15251832);
        LOTREntities.registerCreature(LOTREntityEasterlingArcher.class, "EasterlingArcher", 276, 7486267, 15251832);
        LOTREntities.registerCreature(LOTREntityEasterlingBlacksmith.class, "EasterlingBlacksmith", 277, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingWarlord.class, "EasterlingWarlord", 278, 14265689, 12004653);
        LOTREntities.registerCreature(LOTREntityEasterlingFireThrower.class, "EasterlingFireThrower", 279, 7486267, 15251832);
        LOTREntities.registerCreature(LOTREntityEasterlingLevyman.class, "EasterlingLevyman", 280, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityDorwinionMerchantMan.class, "DorwinionMerchantMan", 281, 16755851, 12213157);
        LOTREntities.registerCreature(LOTREntityEasterlingGoldWarrior.class, "EasterlingGoldWarrior", 282, 14265689, 12004653);
        LOTREntities.registerCreature(LOTREntityEasterlingLumberman.class, "EasterlingLumberman", 283, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingMason.class, "EasterlingMason", 284, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingButcher.class, "EasterlingButcher", 285, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingBrewer.class, "EasterlingBrewer", 286, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingFishmonger.class, "EasterlingFishmonger", 287, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingBaker.class, "EasterlingBaker", 288, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingHunter.class, "EasterlingHunter", 289, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingFarmer.class, "EasterlingFarmer", 290, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingGoldsmith.class, "EasterlingGoldsmith", 291, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityEasterlingBartender.class, "EasterlingBartender", 292, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityDorwinionElfArcher.class, "DorwinionElfArcher", 293, 13420999, 8407696);
        LOTREntities.registerCreature(LOTREntityEasterlingFarmhand.class, "EasterlingFarmhand", 294, 16093531, 6176050);
        LOTREntities.registerCreature(LOTREntityRivendellElf.class, "RivendellElf", 295, 16761223, 15721387);
        LOTREntities.registerCreature(LOTREntityRivendellWarrior.class, "RivendellWarrior", 296, 14738662, 10723248);
        LOTREntities.registerCreature(LOTREntityRivendellLord.class, "RivendellLord", 297, 14738662, 10723248);
        LOTREntities.registerCreature(LOTREntityRivendellBannerBearer.class, "RivendellBannerBearer", 298, 14738662, 10723248);
        LOTREntities.registerCreature(LOTREntityRivendellSmith.class, "RivendellSmith", 299, 16761223, 15721387);
        LOTREntities.registerCreature(LOTREntityEsgarothBannerBearer.class, "EsgarothBannerBearer", 300, 11776947, 481419);
        LOTREntities.registerCreature(LOTREntityRivendellTrader.class, "RivendellTrader", 301, 869480, 15003391);
        LOTREntities.registerCreature(LOTREntityFish.class, "Fish", 302, 7053203, 11913189);
        LOTREntities.registerCreature(LOTREntityGundabadOrcTrader.class, "GundabadOrcTrader", 303, 5979436, 13421772);
        LOTREntities.registerCreature(LOTREntityNearHaradBlacksmith.class, "NearHaradBlacksmith", 304, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySnowTroll.class, "SnowTroll", 305, 14606046, 11059905);
        LOTREntities.registerCreature(LOTREntityHarnedhrim.class, "Harnedhrim", 306, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorWarrior.class, "HarnedorWarrior", 307, 7016721, 14852422);
        LOTREntities.registerCreature(LOTREntityHarnedorArcher.class, "HarnedorArcher", 308, 7016721, 14852422);
        LOTREntities.registerCreature(LOTREntityHarnedorBannerBearer.class, "HarnedorBannerBearer", 309, 7016721, 14852422);
        LOTREntities.registerCreature(LOTREntityUmbarian.class, "Umbarian", 310, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarWarrior.class, "UmbarWarrior", 311, 2960680, 13540692);
        LOTREntities.registerCreature(LOTREntityUmbarArcher.class, "UmbarArcher", 312, 2960680, 13540692);
        LOTREntities.registerCreature(LOTREntityUmbarBannerBearer.class, "UmbarBannerBearer", 313, 2960680, 13540692);
        LOTREntities.registerCreature(LOTREntityMoredainMercenary.class, "MoredainMercenary", 314, 8998697, 14528351);
        LOTREntities.registerCreature(LOTREntityGulfHaradrim.class, "GulfHaradrim", 315, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfHaradWarrior.class, "GulfWarrior", 316, 7478299, 12827550);
        LOTREntities.registerCreature(LOTREntityGulfHaradArcher.class, "GulfArcher", 317, 7478299, 12827550);
        LOTREntities.registerCreature(LOTREntityGulfHaradBannerBearer.class, "GulfBannerBearer", 318, 7478299, 12827550);
        LOTREntities.registerCreature(LOTREntityCorsair.class, "Corsair", 319, 5521973, 12813617);
        LOTREntities.registerCreature(LOTREntityNomad.class, "Nomad", 320, 8278064, 853765);
        LOTREntities.registerCreature(LOTREntityNomadWarrior.class, "NomadWarrior", 321, 10063441, 5658198);
        LOTREntities.registerCreature(LOTREntityNomadArcher.class, "NomadArcher", 322, 10063441, 5658198);
        LOTREntities.registerCreature(LOTREntityNomadBannerBearer.class, "NomadBannerBearer", 323, 10063441, 5658198);
        LOTREntities.registerCreature(LOTREntityHarnedorWarlord.class, "HarnedorWarlord", 324, 7016721, 14852422);
        LOTREntities.registerCreature(LOTREntityUmbarCaptain.class, "UmbarCaptain", 325, 2960680, 13540692);
        LOTREntities.registerCreature(LOTREntityCorsairCaptain.class, "CorsairCaptain", 326, 5521973, 12813617);
        LOTREntities.registerCreature(LOTREntityNomadChieftain.class, "NomadChieftain", 327, 10063441, 5658198);
        LOTREntities.registerCreature(LOTREntityGulfHaradWarlord.class, "GulfWarlord", 328, 7478299, 12827550);
        LOTREntities.registerCreature(LOTREntitySouthronChampion.class, "SouthronChampion", 329, 2171169, 11868955);
        LOTREntities.registerCreature(LOTREntityHaradSlave.class, "HaradSlave", 330, 9860177, 5579298);
        LOTREntities.registerCreature(LOTREntityCorsairSlaver.class, "CorsairSlaver", 331, 5521973, 12813617);
        LOTREntities.registerCreature(LOTREntityHarnedorBlacksmith.class, "HarnedorBlacksmith", 332, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityUmbarBlacksmith.class, "UmbarBlacksmith", 333, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityGulfBlacksmith.class, "GulfBlacksmith", 334, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGondorRenegade.class, "GondorRenegade", 335, 1776411, 13936679);
        LOTREntities.registerCreature(LOTREntityNomadMerchant.class, "NomadMerchant", 336, 13551017, 7825215);
        LOTREntities.registerCreature(LOTREntityHarnedorBartender.class, "HarnedorBartender", 337, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorLumberman.class, "HarnedorLumberman", 338, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorMason.class, "HarnedorMason", 339, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorButcher.class, "HarnedorButcher", 340, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorBrewer.class, "HarnedorBrewer", 341, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorFishmonger.class, "HarnedorFishmonger", 342, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorBaker.class, "HarnedorBaker", 343, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorHunter.class, "HarnedorHunter", 344, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorMiner.class, "HarnedorMiner", 345, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntitySouthronLumberman.class, "SouthronLumberman", 346, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronMason.class, "SouthronMason", 347, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronFishmonger.class, "SouthronFishmonger", 348, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronBaker.class, "SouthronBaker", 349, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntitySouthronGoldsmith.class, "SouthronGoldsmith", 350, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarLumberman.class, "UmbarLumberman", 351, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarButcher.class, "UmbarButcher", 352, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarBrewer.class, "UmbarBrewer", 353, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarFishmonger.class, "UmbarFishmonger", 354, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarBaker.class, "UmbarBaker", 355, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarFlorist.class, "UmbarFlorist", 356, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarMiner.class, "UmbarMiner", 357, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarGoldsmith.class, "UmbarGoldsmith", 358, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarMason.class, "UmbarMason", 359, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityNomadMason.class, "NomadMason", 360, 8278064, 853765);
        LOTREntities.registerCreature(LOTREntityNomadBrewer.class, "NomadBrewer", 361, 8278064, 853765);
        LOTREntities.registerCreature(LOTREntityNomadMiner.class, "NomadMiner", 362, 8278064, 853765);
        LOTREntities.registerCreature(LOTREntityGulfMason.class, "GulfMason", 363, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfButcher.class, "GulfButcher", 364, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfBrewer.class, "GulfBrewer", 365, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfFishmonger.class, "GulfFishmonger", 366, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfBaker.class, "GulfBaker", 367, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfMiner.class, "GulfMiner", 368, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfGoldsmith.class, "GulfGoldsmith", 369, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfLumberman.class, "GulfLumberman", 370, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityGulfHunter.class, "GulfHunter", 371, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityNomadArmourer.class, "NomadArmourer", 372, 8278064, 853765);
        LOTREntities.registerCreature(LOTREntitySouthronBartender.class, "SouthronBartender", 373, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarBartender.class, "UmbarBartender", 374, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityGulfBartender.class, "GulfBartender", 375, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorFarmhand.class, "HarnedorFarmhand", 376, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityHarnedorFarmer.class, "HarnedorFarmer", 377, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntitySouthronFarmer.class, "SouthronFarmer", 378, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityUmbarFarmer.class, "UmbarFarmer", 379, 10779229, 2960685);
        LOTREntities.registerCreature(LOTREntityGulfFarmer.class, "GulfFarmer", 380, 9854777, 1181187);
        LOTREntities.registerCreature(LOTREntityTauredainSmith.class, "TauredainSmith", 381, 4468770, 12948008);
        LOTREntities.registerCreature(LOTREntityWhiteOryx.class, "WhiteOryx", 382, 16381146, 8154724);
        LOTREntities.registerCreature(LOTREntityBlackUrukCaptain.class, "BlackUrukCaptain", 383, 988430, 2830632);
        LOTREntities.registerCreature(LOTREntityWickedDwarf.class, "WickedDwarf", 384, 14516076, 8869453);
        LOTREntities.registerCreature(LOTREntityBreeMan.class, "BreeMan", 385, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeGuard.class, "BreeGuard", 386, 9335640, 3681573);
        LOTREntities.registerCreature(LOTREntityBreeBannerBearer.class, "BreeBannerBearer", 387, 9335640, 3681573);
        LOTREntities.registerCreature(LOTREntityBreeCaptain.class, "BreeCaptain", 388, 9335640, 3681573);
        LOTREntities.registerCreature(LOTREntityBreeBlacksmith.class, "BreeBlacksmith", 389, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeInnkeeper.class, "BreeInnkeeper", 390, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeHobbit.class, "BreeHobbit", 391, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityRuffianSpy.class, "RuffianSpy", 392, 14713187, 5191213);
        LOTREntities.registerCreature(LOTREntityRuffianBrute.class, "RuffianBrute", 393, 14713187, 5191213);
        LOTREntities.registerCreature(LOTREntityBreeHobbitInnkeeper.class, "BreeHobbitInnkeeper", 394, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityBreeBaker.class, "BreeBaker", 395, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeButcher.class, "BreeButcher", 396, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeBrewer.class, "BreeBrewer", 397, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeMason.class, "BreeMason", 398, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeLumberman.class, "BreeLumberman", 399, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeFlorist.class, "BreeFlorist", 400, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeFarmer.class, "BreeFarmer", 401, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeFarmhand.class, "BreeFarmhand", 402, 14254950, 6573367);
        LOTREntities.registerCreature(LOTREntityBreeHobbitBaker.class, "BreeHobbitBaker", 403, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityBreeHobbitButcher.class, "BreeHobbitButcher", 404, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityBreeHobbitBrewer.class, "BreeHobbitBrewer", 405, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityBreeHobbitFlorist.class, "BreeHobbitFlorist", 406, 16752511, 8010275);
        LOTREntities.registerCreature(LOTREntityUtumnoOrc.class, "UtumnoOrc", 800, 2694951, 10377233);
        LOTREntities.registerCreature(LOTREntityUtumnoOrcArcher.class, "UtumnoOrcArcher", 801, 2694951, 10377233);
        LOTREntities.registerCreature(LOTREntityUtumnoWarg.class, "UtumnoWarg", 802, 4600617, 2694422);
        LOTREntities.registerCreature(LOTREntityUtumnoIceWarg.class, "UtumnoIceWarg", 803, 15066080, 9348269);
        LOTREntities.registerCreature(LOTREntityUtumnoObsidianWarg.class, "UtumnoObsidianWarg", 804, 2960169, 1644310);
        LOTREntities.registerCreature(LOTREntityUtumnoFireWarg.class, "UtumnoFireWarg", 805, 6958364, 13530368);
        LOTREntities.registerCreature(LOTREntityUtumnoIceSpider.class, "UtumnoIceSpider", 806, 15594495, 7697919);
        LOTREntities.registerCreature(LOTREntityBalrog.class, "Balrog", 807, 1772037, 13009920);
        LOTREntities.registerCreature(LOTREntityTormentedElf.class, "TormentedElf", 808, 14079919, 4337710);
        LOTREntities.registerCreature(LOTREntityUtumnoTroll.class, "UtumnoTroll", 809, 10580563, 7422265);
        LOTREntities.registerCreature(LOTREntityUtumnoSnowTroll.class, "UtumnoSnowTroll", 810, 14606046, 11059905);
        LOTREntities.registerCreature(LOTREntityGollum.class, "Gollum", 1001, 13417872, 9471333);
        LOTREntities.registerCreature(LOTREntitySaruman.class, "Saruman", 1002, 15132390, 11776947);
        LOTREntities.registerCreature(LOTREntityGandalf.class, "Gandalf", 1003, 9605778, 5923198);
        LOTREntities.registerEntity(LOTREntityPortal.class, "Portal", 2000, 80, 3, true);
        LOTREntities.registerEntity(LOTREntitySmokeRing.class, "SmokeRing", 2001, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityOrcBomb.class, "OrcBomb", 2002, 160, 10, true);
        LOTREntities.registerEntity(LOTREntityGandalfFireball.class, "GandalfFireball", 2003, 64, 10, true);
        LOTREntities.registerEntity(LOTREntitySpear.class, "Spear", 2004, 64, Integer.MAX_VALUE, false);
        LOTREntities.registerEntity(LOTREntityPlate.class, "Plate", 2005, 64, 3, true);
        LOTREntities.registerEntity(LOTREntityWargskinRug.class, "WargRug", 2006, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityMarshWraithBall.class, "MarshWraithBall", 2007, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityThrowingAxe.class, "ThrowingAxe", 2008, 64, Integer.MAX_VALUE, false);
        LOTREntities.registerEntity(LOTREntityCrossbowBolt.class, "CrossbowBolt", 2009, 64, Integer.MAX_VALUE, false);
        LOTREntities.registerEntity(LOTREntityStoneTroll.class, "StoneTroll", 2010, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityPebble.class, "Pebble", 2011, 64, 3, true);
        LOTREntities.registerEntity(LOTREntityMysteryWeb.class, "MysteryWeb", 2012, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityTraderRespawn.class, "TraderRespawn", 2013, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityThrownRock.class, "ThrownRock", 2014, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityBarrel.class, "Barrel", 2015, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityBanner.class, "Banner", 2016, 160, 3, true);
        LOTREntities.registerEntity(LOTREntityBannerWall.class, "WallBanner", 2017, 160, Integer.MAX_VALUE, false);
        LOTREntities.registerEntity(LOTREntityInvasionSpawner.class, "InvasionSpawner", 2018, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityThrownTermite.class, "ThrownTermite", 2019, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityConker.class, "Conker", 2020, 64, 3, true);
        LOTREntities.registerEntity(LOTREntityBossTrophy.class, "BossTrophy", 2022, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityMallornLeafBomb.class, "MallornLeafBomb", 2023, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityFishHook.class, "LOTRFishHook", 2024, 64, 5, true);
        LOTREntities.registerEntity(LOTREntityDart.class, "Dart", 2025, 64, Integer.MAX_VALUE, false);
        LOTREntities.registerEntity(LOTREntityNPCRespawner.class, "NPCRespawner", 2027, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityFallingTreasure.class, "FallingTreasure", 2028, 160, 20, true);
        LOTREntities.registerEntity(LOTREntityFallingFireJar.class, "FallingFireJar", 2029, 160, 20, true);
        LOTREntities.registerEntity(LOTREntityFirePot.class, "ThrownFirePot", 2030, 64, 3, true);
        LOTREntities.registerEntity(LOTREntityArrowPoisoned.class, "ArrowPoison", 2031, 64, 20, false);
        LOTREntities.registerEntity(LOTREntityTrollSnowball.class, "TrollSnowball", 2032, 64, 10, true);
        LOTREntities.registerEntity(LOTREntityLionRug.class, "LionRug", 2033, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityBearRug.class, "BearRug", 2034, 80, 3, true);
        LOTREntities.registerEntity(LOTREntityGiraffeRug.class, "GiraffeRug", 2035, 80, 3, true);
    }

    public static int getEntityID(Entity entity) {
        return LOTREntities.getEntityIDFromClass(entity.getClass());
    }

    public static int getEntityIDFromClass(Class entityClass) {
        return classToIDMapping.get(entityClass);
    }

    public static String getStringFromClass(Class entityClass) {
        return (String)EntityList.classToStringMapping.get(entityClass);
    }

    public static int getIDFromString(String name) {
        if (!stringToIDMapping.containsKey(name)) {
            return 0;
        }
        return stringToIDMapping.get(name);
    }

    public static Class<? extends Entity> getClassFromString(String name) {
        return (Class)EntityList.stringToClassMapping.get(name);
    }

    public static String getStringFromID(int id) {
        return IDToStringMapping.get(id);
    }

    public static Entity createEntityByClass(Class entityClass, World world) {
        Entity entity = null;
        try {
            entity = (Entity)entityClass.getConstructor(World.class).newInstance(world);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    public static Set<String> getAllEntityNames() {
        return Collections.unmodifiableSet(stringToIDMapping.keySet());
    }

    public static class SpawnEggInfo {
        public final int spawnedID;
        public final int primaryColor;
        public final int secondaryColor;

        public SpawnEggInfo(int i, int j, int k) {
            this.spawnedID = i;
            this.primaryColor = j;
            this.secondaryColor = k;
        }
    }

}

