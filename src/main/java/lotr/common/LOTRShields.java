package lotr.common;

import java.util.*;

import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public enum LOTRShields {
    ALIGNMENT_BREE(LOTRFaction.BREE),
    ALIGNMENT_RANGER(LOTRFaction.RANGER_NORTH),
    ALIGNMENT_BLUE_MOUNTAINS(LOTRFaction.BLUE_MOUNTAINS),
    ALIGNMENT_HIGH_ELF(LOTRFaction.HIGH_ELF),
    ALIGNMENT_RIVENDELL(LOTRFaction.HIGH_ELF),
    ALIGNMENT_GUNDABAD(LOTRFaction.GUNDABAD),
    ALIGNMENT_ANGMAR(LOTRFaction.ANGMAR),
    ALIGNMENT_WOOD_ELF(LOTRFaction.WOOD_ELF),
    ALIGNMENT_DOL_GULDUR(LOTRFaction.DOL_GULDUR),
    ALIGNMENT_DALE(LOTRFaction.DALE),
    ALIGNMENT_ESGAROTH(LOTRFaction.DALE),
    ALIGNMENT_DWARF(LOTRFaction.DURINS_FOLK),
    ALIGNMENT_GALADHRIM(LOTRFaction.LOTHLORIEN),
    ALIGNMENT_DUNLAND(LOTRFaction.DUNLAND),
    ALIGNMENT_URUK_HAI(LOTRFaction.ISENGARD),
    ALIGNMENT_ROHAN(LOTRFaction.ROHAN),
    ALIGNMENT_GONDOR(LOTRFaction.GONDOR),
    ALIGNMENT_DOL_AMROTH(LOTRFaction.GONDOR),
    ALIGNMENT_LOSSARNACH(LOTRFaction.GONDOR),
    ALIGNMENT_LEBENNIN(LOTRFaction.GONDOR),
    ALIGNMENT_PELARGIR(LOTRFaction.GONDOR),
    ALIGNMENT_BLACKROOT_VALE(LOTRFaction.GONDOR),
    ALIGNMENT_PINNATH_GELIN(LOTRFaction.GONDOR),
    ALIGNMENT_LAMEDON(LOTRFaction.GONDOR),
    ALIGNMENT_MORDOR(LOTRFaction.MORDOR),
    ALIGNMENT_MINAS_MORGUL(LOTRFaction.MORDOR),
    ALIGNMENT_BLACK_URUK(LOTRFaction.MORDOR),
    ALIGNMENT_DORWINION(LOTRFaction.DORWINION),
    ALIGNMENT_DORWINION_ELF(LOTRFaction.DORWINION),
    ALIGNMENT_RHUN(LOTRFaction.RHUDEL),
    ALIGNMENT_HARNEDOR(LOTRFaction.NEAR_HARAD),
    ALIGNMENT_NEAR_HARAD(LOTRFaction.NEAR_HARAD),
    ALIGNMENT_UMBAR(LOTRFaction.NEAR_HARAD),
    ALIGNMENT_CORSAIR(LOTRFaction.NEAR_HARAD),
    ALIGNMENT_GULF(LOTRFaction.NEAR_HARAD),
    ALIGNMENT_MOREDAIN(LOTRFaction.MORWAITH),
    ALIGNMENT_TAUREDAIN(LOTRFaction.TAURETHRIM),
    ALIGNMENT_HALF_TROLL(LOTRFaction.HALF_TROLL),
    ACHIEVEMENT_BRONZE,
    ACHIEVEMENT_SILVER,
    ACHIEVEMENT_GOLD,
    ACHIEVEMENT_MITHRIL,
    ALCOHOLIC,
    DEFEAT_MTC,
    DEFEAT_MALLORN_ENT,
    ELVEN_CONTEST("7a19ce50-d5c8-4e16-b8f9-932d27ec3251"),
    EVIL_CONTEST("0c1eb454-df57-4b5c-91bf-16d8f15cae74", "2418e4fa-2483-4bd9-bf58-1e09c586959e"),
    SHIRE_CONTEST("a166423a-a1e3-45b1-a4b8-dea69f2bd64e", "3967d432-37ec-450d-ad37-9eec6bac9707", "bab181b6-7b76-49fe-baa2-b3ca4a48f486"),
    GONDOR_CONTEST("ccfdb788-46f6-4142-8d41-1a89ac745db6", "7c9bfb4c-6b65-4d2f-9cbb-7e037bbad14f", "bab181b6-7b76-49fe-baa2-b3ca4a48f486", "753cdbe5-414c-4f60-829a-f65fc38cc539", "3967d432-37ec-450d-ad37-9eec6bac9707"),
    HARAD_CONTEST("05d987d8-7e3d-44b2-a170-234efe19f907", "d4ff096b-a38c-47c6-8cec-2bf336affb6d", "2c862690-b9d4-42de-a1b9-cb2200b75e72"),
    RHUN_CONTEST("e69ddad2-db7a-48c5-aefe-20cc196d6ac0", "6b18abb7-5533-4ae3-908e-18a7f44a2092", "45255b67-6bfd-434c-8fed-e1f56e18da6a"),
    STRUCTURE_CONTEST("82f1c77b-07b8-4334-9edf-f29d8fc74bf8", "69f45bcd-4955-42a8-a740-dc0e16dc8565", "37f2bfb6-a613-43dd-8c1b-ffa4cbbbd172", "8bc4c772-f305-4545-a44d-a4f1b9eb2b98", "a166423a-a1e3-45b1-a4b8-dea69f2bd64e", "3967d432-37ec-450d-ad37-9eec6bac9707", "7e869d33-df0f-4433-b235-a62205c5f986", "f4d255ce-f7fc-4f17-bc87-e0139e74c83b"),
    RENEWED_CONTEST(RenewedBuildContestEntrants.WINNERS),
    MOD(false, "7bc56da6-f133-4e47-8d0f-a2776762bca6", "e9587327-156d-4fc4-91a5-f2e7cfaa9d66", "d054f0bf-a9cb-41d1-8496-709f97faa475", "8bc4c772-f305-4545-a44d-a4f1b9eb2b98", "3967d432-37ec-450d-ad37-9eec6bac9707", "12e96c35-6964-4993-8876-c04ac9f2fb8c", "6458975e-0160-4b45-b364-866b4922cc29", "d0b25c60-65ec-4d75-b0ac-07b3fa9dd6a6", "7a55441d-02d9-401c-b259-495fba1c3148", "963296be-a555-4151-aa45-b7df8598faba", "c0eb3931-701b-4bb3-ac12-c03017e09b8d", "0a283d3e-7fd7-4469-a219-4189c0e012b5", "972ecfe5-4164-44ea-944f-9d7a4fb80145", "bab181b6-7b76-49fe-baa2-b3ca4a48f486", "a166423a-a1e3-45b1-a4b8-dea69f2bd64e", "461c1adb-bbd1-4513-b02f-946b6388b496", "757223ca-bd8e-4cbf-bd69-397f244263af", "b03bd343-8fe8-42ca-a56f-ee3a5b17150f", "7646da8a-77fa-4697-ae05-ac75e0558106", "7de47d3a-3f3f-40a9-baa9-4214155eaef7", "d4ff096b-a38c-47c6-8cec-2bf336affb6d", "68c506d2-52da-43d7-b335-020c24ef00dd", "113c908b-a24e-43e8-a8a3-7243f2449964", "8dd99ac0-274c-4d3d-a8f8-f741eec7c75a", "6c94c61a-aebb-4b77-9699-4d5236d0e78a", "1b5e2703-b92b-40d5-a59a-c59a86289ba1", "961fdbd8-a08a-4c43-9adc-d9ee578dc786", "2eaae1cd-27c2-4b2d-85d8-105306165808", "f8cb980d-edce-445e-be8f-eb57d3294ff0", "4a011485-a30a-4c5c-93cf-622110669be6", "99fe6181-6342-4e3f-a304-a1a8c118e840", "9f55926f-7185-41e8-a468-9afc757b17f6", "29eaebce-9076-44b5-bf63-6bdd7b2730bc", "39fe02bc-1024-47cf-ad4b-853a4092ee69", "56097783-03b0-40a9-be4c-1746d2576cc8", "c9ee6e91-cfe9-4e31-882a-f5d6365128da"),
    GRUK(true, "7bc56da6-f133-4e47-8d0f-a2776762bca6", "6c94c61a-aebb-4b77-9699-4d5236d0e78a"),
    BOYD(true, "7bc56da6-f133-4e47-8d0f-a2776762bca6", "961fdbd8-a08a-4c43-9adc-d9ee578dc786"),
    ELECTRICIAN(true, "60241a10-eeb0-4cc5-831c-d5f7123d46f0"),
    OGRE(false, "90dd2523-cefb-48b9-be2c-36759289724a"),
    TRANSLATOR(false, LOTRTranslatorList.playerUUIDs),
    PATRON(false, LOTRPatron.getShieldPlayers()),
    LOREMASTER_2013("7bc56da6-f133-4e47-8d0f-a2776762bca6", "a166423a-a1e3-45b1-a4b8-dea69f2bd64e", "a041a9a7-286d-470f-8826-c9ab0b3166dd", "1f9b1c1d-d4fa-49a2-b6a7-fc58bb62d19b", "e3db1a07-846a-4843-98b2-64f4f9331b4a"),
    LOREMASTER_2014("cad6e5c7-5362-46fc-ac46-0b5f2eb18f97", "f1e02905-1659-468c-b80f-6301e13384ab", "519ad9d7-3a47-45e2-9933-0c416cd60553", "c674a50d-9dcb-464d-af43-a4c7c0af411b", "bd8086bf-80c5-4c93-ac1a-269e4beb59bd", "f2b2a972-7bec-412d-9a5b-e616877eca23", "24fbd7cf-bd84-4ea5-94b8-0cf96e44126e", "279083d4-03d7-4b2c-954d-eab49ed99492", "cf5519e2-1d45-4762-bc38-605de9ed5a07", "a166423a-a1e3-45b1-a4b8-dea69f2bd64e"),
    LOREMASTER_2015("24fbd7cf-bd84-4ea5-94b8-0cf96e44126e", "7bc56da6-f133-4e47-8d0f-a2776762bca6", "519ad9d7-3a47-45e2-9933-0c416cd60553", "279083d4-03d7-4b2c-954d-eab49ed99492", "cf5519e2-1d45-4762-bc38-605de9ed5a07", "da3c4298-17ff-4310-a20b-9523678bd5ef", "37b3bac5-96e5-463d-9a21-a2a398871ff1", "bd8086bf-80c5-4c93-ac1a-269e4beb59bd", "c674a50d-9dcb-464d-af43-a4c7c0af411b", "985940be-7bd8-4f7c-b05d-dd06bae2e2b9"),
    LOREMASTER_2016("3b1bcb28-084e-4c8e-8687-924912feedb2", "24fbd7cf-bd84-4ea5-94b8-0cf96e44126e", "c674a50d-9dcb-464d-af43-a4c7c0af411b", "bd8086bf-80c5-4c93-ac1a-269e4beb59bd", "9d8b2b0b-43c4-4abd-9d07-eee7e4dc2d19", "9793f61c-5bc7-479d-be11-1b122d4a249f", "10bb7b50-f974-4848-af88-23ee0319e3a4", "7bc56da6-f133-4e47-8d0f-a2776762bca6", "68360a81-5450-4ff9-8430-ec0243496d55", "543195a2-5616-446d-9376-0b7fd3647ab1");

    public ShieldType shieldType;
    public int shieldID;
    public UUID[] exclusiveUUIDs;
    private LOTRFaction alignmentFaction;
    public ResourceLocation shieldTexture;
    private boolean isHidden;

    LOTRShields(LOTRFaction faction) {
        this(ShieldType.ALIGNMENT, false, new String[0]);
        this.alignmentFaction = faction;
    }

    LOTRShields() {
        this(ShieldType.ACHIEVABLE, false, new String[0]);
    }

    LOTRShields(String ... players) {
        this(false, players);
    }

    LOTRShields(boolean hidden, String ... players) {
        this(ShieldType.EXCLUSIVE, hidden, players);
    }

    LOTRShields(ShieldType type, boolean hidden, String ... players) {
        this.shieldType = type;
        this.shieldID = this.shieldType.list.size();
        this.shieldType.list.add(this);
        this.shieldTexture = new ResourceLocation("lotr:shield/" + this.name().toLowerCase() + ".png");
        this.exclusiveUUIDs = new UUID[players.length];
        for (int i = 0; i < players.length; ++i) {
            String s = players[i];
            this.exclusiveUUIDs[i] = UUID.fromString(s);
        }
        this.isHidden = hidden;
    }

    public String getShieldName() {
        return StatCollector.translateToLocal("lotr.shields." + this.name() + ".name");
    }

    public String getShieldDesc() {
        return StatCollector.translateToLocal("lotr.shields." + this.name() + ".desc");
    }

    public boolean canDisplay(EntityPlayer entityplayer) {
        return !this.isHidden || this.canPlayerWear(entityplayer);
    }

    public boolean canPlayerWear(EntityPlayer entityplayer) {
        if (this.shieldType == ShieldType.ALIGNMENT) {
            return LOTRLevelData.getData(entityplayer).getAlignment(this.alignmentFaction) >= 1000.0f;
        }
        if (this == ACHIEVEMENT_BRONZE) {
            return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 25;
        }
        if (this == ACHIEVEMENT_SILVER) {
            return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 50;
        }
        if (this == ACHIEVEMENT_GOLD) {
            return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 100;
        }
        if (this == ACHIEVEMENT_MITHRIL) {
            return LOTRLevelData.getData(entityplayer).getEarnedAchievements(LOTRDimension.MIDDLE_EARTH).size() >= 200;
        }
        if (this == ALCOHOLIC) {
            return LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.gainHighAlcoholTolerance);
        }
        if (this == DEFEAT_MTC) {
            return LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.killMountainTrollChieftain);
        }
        if (this == DEFEAT_MALLORN_ENT) {
            return LOTRLevelData.getData(entityplayer).hasAchievement(LOTRAchievement.killMallornEnt);
        }
        if (this.shieldType == ShieldType.EXCLUSIVE) {
            for (UUID uuid : this.exclusiveUUIDs) {
                if (!uuid.equals(entityplayer.getUniqueID())) continue;
                return true;
            }
        }
        return false;
    }

    public static void forceClassLoad() {
    }

    public static LOTRShields shieldForName(String shieldName) {
        for (LOTRShields shield : LOTRShields.values()) {
            if (!shield.name().equals(shieldName)) continue;
            return shield;
        }
        return null;
    }

    public static enum ShieldType {
        ALIGNMENT,
        ACHIEVABLE,
        EXCLUSIVE;

        public List<LOTRShields> list = new ArrayList<>();

        public String getDisplayName() {
            return StatCollector.translateToLocal("lotr.shields.category." + this.name());
        }
    }

}

