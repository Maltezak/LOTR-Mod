package lotr.common.world.spawning;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemConquestHorn;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.*;

public enum LOTRInvasions {
    HOBBIT(LOTRFaction.HOBBIT),
    BREE(LOTRFaction.BREE),
    RANGER_NORTH(LOTRFaction.RANGER_NORTH),
    BLUE_MOUNTAINS(LOTRFaction.BLUE_MOUNTAINS),
    HIGH_ELF_LINDON(LOTRFaction.HIGH_ELF, "lindon"),
    HIGH_ELF_RIVENDELL(LOTRFaction.HIGH_ELF, "rivendell"),
    GUNDABAD(LOTRFaction.GUNDABAD),
    GUNDABAD_WARG(LOTRFaction.GUNDABAD, "warg"),
    ANGMAR(LOTRFaction.ANGMAR),
    ANGMAR_HILLMEN(LOTRFaction.ANGMAR, "hillmen"),
    ANGMAR_WARG(LOTRFaction.ANGMAR, "warg"),
    WOOD_ELF(LOTRFaction.WOOD_ELF),
    DOL_GULDUR(LOTRFaction.DOL_GULDUR),
    DALE(LOTRFaction.DALE),
    DWARF(LOTRFaction.DURINS_FOLK),
    GALADHRIM(LOTRFaction.LOTHLORIEN),
    DUNLAND(LOTRFaction.DUNLAND),
    URUK_HAI(LOTRFaction.ISENGARD),
    FANGORN(LOTRFaction.FANGORN),
    ROHAN(LOTRFaction.ROHAN),
    GONDOR(LOTRFaction.GONDOR),
    GONDOR_ITHILIEN(LOTRFaction.GONDOR, "ithilien"),
    GONDOR_DOL_AMROTH(LOTRFaction.GONDOR, "dolAmroth"),
    GONDOR_LOSSARNACH(LOTRFaction.GONDOR, "lossarnach"),
    GONDOR_PELARGIR(LOTRFaction.GONDOR, "pelargir"),
    GONDOR_PINNATH_GELIN(LOTRFaction.GONDOR, "pinnathGelin"),
    GONDOR_BLACKROOT(LOTRFaction.GONDOR, "blackroot"),
    GONDOR_LEBENNIN(LOTRFaction.GONDOR, "lebennin"),
    GONDOR_LAMEDON(LOTRFaction.GONDOR, "lamedon"),
    MORDOR(LOTRFaction.MORDOR),
    MORDOR_BLACK_URUK(LOTRFaction.MORDOR, "blackUruk"),
    MORDOR_NAN_UNGOL(LOTRFaction.MORDOR, "nanUngol"),
    MORDOR_WARG(LOTRFaction.MORDOR, "warg"),
    DORWINION(LOTRFaction.DORWINION),
    DORWINION_ELF(LOTRFaction.DORWINION, "elf"),
    RHUN(LOTRFaction.RHUDEL),
    NEAR_HARAD_HARNEDOR(LOTRFaction.NEAR_HARAD, "harnedor"),
    NEAR_HARAD_COAST(LOTRFaction.NEAR_HARAD, "coast"),
    NEAR_HARAD_UMBAR(LOTRFaction.NEAR_HARAD, "umbar"),
    NEAR_HARAD_CORSAIR(LOTRFaction.NEAR_HARAD, "corsair"),
    NEAR_HARAD_NOMAD(LOTRFaction.NEAR_HARAD, "nomad"),
    NEAR_HARAD_GULF(LOTRFaction.NEAR_HARAD, "gulf"),
    MOREDAIN(LOTRFaction.MORWAITH),
    TAUREDAIN(LOTRFaction.TAURETHRIM),
    HALF_TROLL(LOTRFaction.HALF_TROLL);

    public final LOTRFaction invasionFaction;
    private final String subfaction;
    public List<InvasionSpawnEntry> invasionMobs = new ArrayList<>();
    private Item invasionIcon;

    LOTRInvasions(LOTRFaction f) {
        this(f, null);
    }

    LOTRInvasions(LOTRFaction f, String s) {
        this.invasionFaction = f;
        this.subfaction = s;
    }

    public String codeName() {
        String s = this.invasionFaction.codeName();
        if (this.subfaction != null) {
            s = s + "_" + this.subfaction;
        }
        return s;
    }

    public List<String> codeNameAndAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        if (this.subfaction != null) {
            String subfactionAdd = "_" + this.subfaction;
            aliases.add(this.invasionFaction.codeName() + subfactionAdd);
            for (String al : this.invasionFaction.listAliases()) {
                aliases.add(al + subfactionAdd);
            }
        } else {
            aliases.add(this.invasionFaction.codeName());
            aliases.addAll(this.invasionFaction.listAliases());
        }
        return aliases;
    }

    public String invasionName() {
        if (this.subfaction == null) {
            return this.invasionFaction.factionName();
        }
        return StatCollector.translateToLocal("lotr.invasion." + this.codeName());
    }

    public String codeNameHorn() {
        return "lotr.invasion." + this.codeName() + ".horn";
    }

    public ItemStack getInvasionIcon() {
        Item sword = this.invasionIcon;
        if (sword == null) {
            sword = Items.iron_sword;
        }
        return new ItemStack(sword);
    }

    public final ItemStack createConquestHorn() {
        ItemStack horn = new ItemStack(LOTRMod.conquestHorn);
        LOTRItemConquestHorn.setInvasionType(horn, this);
        return horn;
    }

    public static void createMobLists() {
        LOTRInvasions.HOBBIT.invasionIcon = LOTRMod.hobbitPipe;
        LOTRInvasions.HOBBIT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHobbitBounder.class, 15));
        LOTRInvasions.BREE.invasionIcon = LOTRMod.pikeIron;
        LOTRInvasions.BREE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBreeGuard.class, 15));
        LOTRInvasions.BREE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBreeBannerBearer.class, 2));
        LOTRInvasions.RANGER_NORTH.invasionIcon = LOTRMod.rangerBow;
        LOTRInvasions.RANGER_NORTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerNorth.class, 15));
        LOTRInvasions.RANGER_NORTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerNorthBannerBearer.class, 2));
        LOTRInvasions.BLUE_MOUNTAINS.invasionIcon = LOTRMod.hammerBlueDwarven;
        LOTRInvasions.BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfWarrior.class, 10));
        LOTRInvasions.BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfAxeThrower.class, 5));
        LOTRInvasions.BLUE_MOUNTAINS.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlueDwarfBannerBearer.class, 2));
        LOTRInvasions.HIGH_ELF_LINDON.invasionIcon = LOTRMod.swordHighElven;
        LOTRInvasions.HIGH_ELF_LINDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHighElfWarrior.class, 15));
        LOTRInvasions.HIGH_ELF_LINDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHighElfBannerBearer.class, 2));
        LOTRInvasions.HIGH_ELF_RIVENDELL.invasionIcon = LOTRMod.swordRivendell;
        LOTRInvasions.HIGH_ELF_RIVENDELL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRivendellWarrior.class, 15));
        LOTRInvasions.HIGH_ELF_RIVENDELL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRivendellBannerBearer.class, 2));
        LOTRInvasions.GUNDABAD.invasionIcon = LOTRMod.swordGundabadUruk;
        LOTRInvasions.GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadOrc.class, 20));
        LOTRInvasions.GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadOrcArcher.class, 10));
        LOTRInvasions.GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadWarg.class, 20));
        LOTRInvasions.GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadBannerBearer.class, 5));
        LOTRInvasions.GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadUruk.class, 5));
        LOTRInvasions.GUNDABAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadUrukArcher.class, 2));
        LOTRInvasions.GUNDABAD_WARG.invasionIcon = LOTRMod.wargBone;
        LOTRInvasions.GUNDABAD_WARG.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGundabadWarg.class, 10));
        LOTRInvasions.ANGMAR.invasionIcon = LOTRMod.swordAngmar;
        LOTRInvasions.ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrc.class, 10));
        LOTRInvasions.ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcArcher.class, 5));
        LOTRInvasions.ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarOrcBombardier.class, 3));
        LOTRInvasions.ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarBannerBearer.class, 2));
        LOTRInvasions.ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
        LOTRInvasions.ANGMAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWargBombardier.class, 1));
        LOTRInvasions.ANGMAR_HILLMEN.invasionIcon = LOTRMod.swordBronze;
        LOTRInvasions.ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillman.class, 10));
        LOTRInvasions.ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillmanWarrior.class, 5));
        LOTRInvasions.ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillmanAxeThrower.class, 5));
        LOTRInvasions.ANGMAR_HILLMEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarHillmanBannerBearer.class, 2));
        LOTRInvasions.ANGMAR_WARG.invasionIcon = LOTRMod.wargBone;
        LOTRInvasions.ANGMAR_WARG.invasionMobs.add(new InvasionSpawnEntry(LOTREntityAngmarWarg.class, 10));
        LOTRInvasions.WOOD_ELF.invasionIcon = LOTRMod.swordWoodElven;
        LOTRInvasions.WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfWarrior.class, 10));
        LOTRInvasions.WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfScout.class, 5));
        LOTRInvasions.WOOD_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityWoodElfBannerBearer.class, 2));
        LOTRInvasions.DOL_GULDUR.invasionIcon = LOTRMod.swordDolGuldur;
        LOTRInvasions.DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMirkwoodSpider.class, 15));
        LOTRInvasions.DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurOrc.class, 10));
        LOTRInvasions.DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurOrcArcher.class, 5));
        LOTRInvasions.DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolGuldurBannerBearer.class, 2));
        LOTRInvasions.DOL_GULDUR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMirkTroll.class, 3));
        LOTRInvasions.DALE.invasionIcon = LOTRMod.swordDale;
        LOTRInvasions.DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleLevyman.class, 5));
        LOTRInvasions.DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleSoldier.class, 10));
        LOTRInvasions.DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleArcher.class, 5));
        LOTRInvasions.DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDaleBannerBearer.class, 1));
        LOTRInvasions.DALE.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEsgarothBannerBearer.class, 1));
        LOTRInvasions.DWARF.invasionIcon = LOTRMod.hammerDwarven;
        LOTRInvasions.DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfWarrior.class, 10));
        LOTRInvasions.DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfAxeThrower.class, 5));
        LOTRInvasions.DWARF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDwarfBannerBearer.class, 2));
        LOTRInvasions.GALADHRIM.invasionIcon = LOTRMod.swordElven;
        LOTRInvasions.GALADHRIM.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGaladhrimWarrior.class, 15));
        LOTRInvasions.GALADHRIM.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGaladhrimBannerBearer.class, 2));
        LOTRInvasions.DUNLAND.invasionIcon = LOTRMod.dunlendingClub;
        LOTRInvasions.DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlending.class, 10));
        LOTRInvasions.DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingWarrior.class, 5));
        LOTRInvasions.DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingArcher.class, 3));
        LOTRInvasions.DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingAxeThrower.class, 3));
        LOTRInvasions.DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingBerserker.class, 2));
        LOTRInvasions.DUNLAND.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDunlendingBannerBearer.class, 2));
        LOTRInvasions.URUK_HAI.invasionIcon = LOTRMod.scimitarUruk;
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityIsengardSnaga.class, 5));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityIsengardSnagaArcher.class, 5));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHai.class, 10));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiCrossbower.class, 5));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiBerserker.class, 5));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiSapper.class, 3));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukHaiBannerBearer.class, 2));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukWarg.class, 10));
        LOTRInvasions.URUK_HAI.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUrukWargBombardier.class, 1));
        LOTRInvasions.FANGORN.invasionIcon = Items.stick;
        LOTRInvasions.FANGORN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEnt.class, 10));
        LOTRInvasions.FANGORN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHuorn.class, 20));
        LOTRInvasions.ROHAN.invasionIcon = LOTRMod.swordRohan;
        LOTRInvasions.ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohirrimWarrior.class, 10));
        LOTRInvasions.ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohirrimArcher.class, 5));
        LOTRInvasions.ROHAN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRohanBannerBearer.class, 2));
        LOTRInvasions.GONDOR.invasionIcon = LOTRMod.swordGondor;
        LOTRInvasions.GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
        LOTRInvasions.GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
        LOTRInvasions.GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
        LOTRInvasions.GONDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorBannerBearer.class, 2));
        LOTRInvasions.GONDOR_ITHILIEN.invasionIcon = LOTRMod.gondorBow;
        LOTRInvasions.GONDOR_ITHILIEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerIthilien.class, 15));
        LOTRInvasions.GONDOR_ITHILIEN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityRangerIthilienBannerBearer.class, 2));
        LOTRInvasions.GONDOR_DOL_AMROTH.invasionIcon = LOTRMod.swordDolAmroth;
        LOTRInvasions.GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolAmrothSoldier.class, 10));
        LOTRInvasions.GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolAmrothArcher.class, 5));
        LOTRInvasions.GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntitySwanKnight.class, 5));
        LOTRInvasions.GONDOR_DOL_AMROTH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDolAmrothBannerBearer.class, 2));
        LOTRInvasions.GONDOR_LOSSARNACH.invasionIcon = LOTRMod.battleaxeLossarnach;
        LOTRInvasions.GONDOR_LOSSARNACH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
        LOTRInvasions.GONDOR_LOSSARNACH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLossarnachAxeman.class, 15));
        LOTRInvasions.GONDOR_LOSSARNACH.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLossarnachBannerBearer.class, 2));
        LOTRInvasions.GONDOR_PELARGIR.invasionIcon = LOTRMod.tridentPelargir;
        LOTRInvasions.GONDOR_PELARGIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLebenninLevyman.class, 5));
        LOTRInvasions.GONDOR_PELARGIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPelargirMarine.class, 15));
        LOTRInvasions.GONDOR_PELARGIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPelargirBannerBearer.class, 2));
        LOTRInvasions.GONDOR_PINNATH_GELIN.invasionIcon = LOTRMod.swordGondor;
        LOTRInvasions.GONDOR_PINNATH_GELIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
        LOTRInvasions.GONDOR_PINNATH_GELIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPinnathGelinSoldier.class, 15));
        LOTRInvasions.GONDOR_PINNATH_GELIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityPinnathGelinBannerBearer.class, 2));
        LOTRInvasions.GONDOR_BLACKROOT.invasionIcon = LOTRMod.blackrootBow;
        LOTRInvasions.GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorLevyman.class, 5));
        LOTRInvasions.GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackrootSoldier.class, 10));
        LOTRInvasions.GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackrootArcher.class, 5));
        LOTRInvasions.GONDOR_BLACKROOT.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackrootBannerBearer.class, 2));
        LOTRInvasions.GONDOR_LEBENNIN.invasionIcon = LOTRMod.swordGondor;
        LOTRInvasions.GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLebenninLevyman.class, 10));
        LOTRInvasions.GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorSoldier.class, 10));
        LOTRInvasions.GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorArcher.class, 5));
        LOTRInvasions.GONDOR_LEBENNIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLebenninBannerBearer.class, 2));
        LOTRInvasions.GONDOR_LAMEDON.invasionIcon = LOTRMod.hammerGondor;
        LOTRInvasions.GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonHillman.class, 5));
        LOTRInvasions.GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonSoldier.class, 10));
        LOTRInvasions.GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonArcher.class, 5));
        LOTRInvasions.GONDOR_LAMEDON.invasionMobs.add(new InvasionSpawnEntry(LOTREntityLamedonBannerBearer.class, 2));
        LOTRInvasions.MORDOR.invasionIcon = LOTRMod.scimitarOrc;
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrc.class, 10));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 5));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcBombardier.class, 2));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorBannerBearer.class, 2));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMinasMorgulBannerBearer.class, 1));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWarg.class, 10));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWargBombardier.class, 1));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUruk.class, 2));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 1));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukBannerBearer.class, 1));
        LOTRInvasions.MORDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityOlogHai.class, 3));
        LOTRInvasions.MORDOR_BLACK_URUK.invasionIcon = LOTRMod.scimitarBlackUruk;
        LOTRInvasions.MORDOR_BLACK_URUK.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUruk.class, 10));
        LOTRInvasions.MORDOR_BLACK_URUK.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukArcher.class, 5));
        LOTRInvasions.MORDOR_BLACK_URUK.invasionMobs.add(new InvasionSpawnEntry(LOTREntityBlackUrukBannerBearer.class, 2));
        LOTRInvasions.MORDOR_NAN_UNGOL.invasionIcon = LOTRMod.scimitarOrc;
        LOTRInvasions.MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrc.class, 20));
        LOTRInvasions.MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorOrcArcher.class, 10));
        LOTRInvasions.MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNanUngolBannerBearer.class, 5));
        LOTRInvasions.MORDOR_NAN_UNGOL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorSpider.class, 30));
        LOTRInvasions.MORDOR_WARG.invasionIcon = LOTRMod.wargBone;
        LOTRInvasions.MORDOR_WARG.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMordorWarg.class, 10));
        LOTRInvasions.DORWINION.invasionIcon = LOTRMod.mugRedWine;
        LOTRInvasions.DORWINION.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionGuard.class, 10));
        LOTRInvasions.DORWINION.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionCrossbower.class, 5));
        LOTRInvasions.DORWINION.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionBannerBearer.class, 2));
        LOTRInvasions.DORWINION_ELF.invasionIcon = LOTRMod.spearBladorthin;
        LOTRInvasions.DORWINION_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionElfWarrior.class, 10));
        LOTRInvasions.DORWINION_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionElfArcher.class, 5));
        LOTRInvasions.DORWINION_ELF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityDorwinionElfBannerBearer.class, 2));
        LOTRInvasions.RHUN.invasionIcon = LOTRMod.swordRhun;
        LOTRInvasions.RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingLevyman.class, 5));
        LOTRInvasions.RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingWarrior.class, 10));
        LOTRInvasions.RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingArcher.class, 5));
        LOTRInvasions.RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingGoldWarrior.class, 5));
        LOTRInvasions.RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingBannerBearer.class, 5));
        LOTRInvasions.RHUN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityEasterlingFireThrower.class, 3));
        LOTRInvasions.NEAR_HARAD_HARNEDOR.invasionIcon = LOTRMod.swordHarad;
        LOTRInvasions.NEAR_HARAD_HARNEDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHarnedorWarrior.class, 10));
        LOTRInvasions.NEAR_HARAD_HARNEDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHarnedorArcher.class, 5));
        LOTRInvasions.NEAR_HARAD_HARNEDOR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHarnedorBannerBearer.class, 2));
        LOTRInvasions.NEAR_HARAD_COAST.invasionIcon = LOTRMod.scimitarNearHarad;
        LOTRInvasions.NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradrimWarrior.class, 8));
        LOTRInvasions.NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradrimArcher.class, 5));
        LOTRInvasions.NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNearHaradBannerBearer.class, 2));
        LOTRInvasions.NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntitySouthronChampion.class, 2));
        LOTRInvasions.NEAR_HARAD_COAST.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainMercenary.class, 5));
        LOTRInvasions.NEAR_HARAD_UMBAR.invasionIcon = LOTRMod.scimitarNearHarad;
        LOTRInvasions.NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUmbarWarrior.class, 100));
        LOTRInvasions.NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUmbarArcher.class, 50));
        LOTRInvasions.NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityUmbarBannerBearer.class, 20));
        LOTRInvasions.NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainMercenary.class, 30));
        LOTRInvasions.NEAR_HARAD_UMBAR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGondorRenegade.class, 4));
        LOTRInvasions.NEAR_HARAD_CORSAIR.invasionIcon = LOTRMod.swordCorsair;
        LOTRInvasions.NEAR_HARAD_CORSAIR.invasionMobs.add(new InvasionSpawnEntry(LOTREntityCorsair.class, 10));
        LOTRInvasions.NEAR_HARAD_NOMAD.invasionIcon = LOTRMod.swordHarad;
        LOTRInvasions.NEAR_HARAD_NOMAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNomadWarrior.class, 10));
        LOTRInvasions.NEAR_HARAD_NOMAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNomadArcher.class, 5));
        LOTRInvasions.NEAR_HARAD_NOMAD.invasionMobs.add(new InvasionSpawnEntry(LOTREntityNomadBannerBearer.class, 2));
        LOTRInvasions.NEAR_HARAD_GULF.invasionIcon = LOTRMod.swordGulfHarad;
        LOTRInvasions.NEAR_HARAD_GULF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGulfHaradWarrior.class, 10));
        LOTRInvasions.NEAR_HARAD_GULF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGulfHaradArcher.class, 5));
        LOTRInvasions.NEAR_HARAD_GULF.invasionMobs.add(new InvasionSpawnEntry(LOTREntityGulfHaradBannerBearer.class, 2));
        LOTRInvasions.MOREDAIN.invasionIcon = LOTRMod.spearMoredain;
        LOTRInvasions.MOREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainWarrior.class, 15));
        LOTRInvasions.MOREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityMoredainBannerBearer.class, 2));
        LOTRInvasions.TAUREDAIN.invasionIcon = LOTRMod.swordTauredain;
        LOTRInvasions.TAUREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityTauredainWarrior.class, 10));
        LOTRInvasions.TAUREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityTauredainBlowgunner.class, 5));
        LOTRInvasions.TAUREDAIN.invasionMobs.add(new InvasionSpawnEntry(LOTREntityTauredainBannerBearer.class, 2));
        LOTRInvasions.HALF_TROLL.invasionIcon = LOTRMod.scimitarHalfTroll;
        LOTRInvasions.HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTroll.class, 10));
        LOTRInvasions.HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTrollWarrior.class, 10));
        LOTRInvasions.HALF_TROLL.invasionMobs.add(new InvasionSpawnEntry(LOTREntityHalfTrollBannerBearer.class, 2));
    }

    public static LOTRInvasions forName(String name) {
        for (LOTRInvasions i : LOTRInvasions.values()) {
            List<String> aliases = i.codeNameAndAliases();
            for (String al : aliases) {
                if (!al.equals(name)) continue;
                return i;
            }
        }
        return null;
    }

    public static LOTRInvasions forID(int ID) {
        for (LOTRInvasions i : LOTRInvasions.values()) {
            if (i.ordinal() != ID) continue;
            return i;
        }
        return null;
    }

    public static String[] listInvasionNames() {
        String[] names = new String[LOTRInvasions.values().length];
        for (int i = 0; i < names.length; ++i) {
            names[i] = LOTRInvasions.values()[i].codeName();
        }
        return names;
    }

    public static class InvasionSpawnEntry
    extends WeightedRandom.Item {
        private Class entityClass;

        public InvasionSpawnEntry(Class<? extends LOTREntityNPC> c, int chance) {
            super(chance);
            this.entityClass = c;
        }

        public Class getEntityClass() {
            return this.entityClass;
        }
    }

}

