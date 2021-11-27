package lotr.common.entity.npc;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.io.input.BOMInputStream;

import com.google.common.base.Charsets;

import cpw.mods.fml.common.*;
import lotr.common.*;

public class LOTRNames {
    private static Map<String, String[]> allNameBanks = new HashMap<>();

    public static void loadAllNameBanks() {
        HashMap<String, BufferedReader> nameBankNamesAndReaders = new HashMap<>();
        ZipFile zip = null;
        try {
            ModContainer mc = LOTRMod.getModContainer();
            if (mc.getSource().isFile()) {
                zip = new ZipFile(mc.getSource());
                Enumeration<? extends ZipEntry> entries = zip.entries();
                while (entries.hasMoreElements()) {
                    String path;
                    ZipEntry entry = entries.nextElement();
                    String s = entry.getName();
                    if (!s.startsWith(path = "assets/lotr/names/") || !s.endsWith(".txt")) continue;
                    s = s.substring(path.length());
                    int i = s.indexOf(".txt");
                    try {
                        s = s.substring(0, i);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(zip.getInputStream(entry)), Charsets.UTF_8.name()));
                        nameBankNamesAndReaders.put(s, reader);
                    }
                    catch (Exception e) {
                        FMLLog.severe("Failed to load LOTR name bank " + s + "from zip file");
                        e.printStackTrace();
                    }
                }
            } else {
                File nameBankDir = new File(LOTRMod.class.getResource("/assets/lotr/names").toURI());
                for (File file : nameBankDir.listFiles()) {
                    String s = file.getName();
                    int i = s.indexOf(".txt");
                    if (i < 0) {
                        FMLLog.severe("Failed to load LOTR name bank " + s + " from MCP folder; name bank files must be in .txt format");
                        continue;
                    }
                    try {
                        s = s.substring(0, i);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file)), Charsets.UTF_8.name()));
                        nameBankNamesAndReaders.put(s, reader);
                    }
                    catch (Exception e) {
                        FMLLog.severe("Failed to load LOTR name bank " + s + " from MCP folder");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e) {
            FMLLog.severe("Failed to load LOTR name banks");
            e.printStackTrace();
        }
        for (Map.Entry entry : nameBankNamesAndReaders.entrySet()) {
            String nameBankName = (String)entry.getKey();
            BufferedReader reader = (BufferedReader)entry.getValue();
            try {
                String line;
                ArrayList<String> nameList = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    nameList.add(line);
                }
                reader.close();
                if (nameList.isEmpty()) {
                    FMLLog.severe("LOTR name bank " + nameBankName + " is empty!");
                    continue;
                }
                String[] nameBank = nameList.toArray(new String[0]);
                allNameBanks.put(nameBankName, nameBank);
            }
            catch (Exception e) {
                FMLLog.severe("Failed to load LOTR name bank " + nameBankName);
                e.printStackTrace();
            }
        }
        if (zip != null) {
            try {
                zip.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String[] getNameBank(String nameBankName) {
        return allNameBanks.get(nameBankName);
    }

    public static boolean nameBankExists(String nameBankName) {
        return LOTRNames.getNameBank(nameBankName) != null;
    }

    public static String getRandomName(String nameBankName, Random rand) {
        if (allNameBanks.containsKey(nameBankName)) {
            String[] nameBank = LOTRNames.getNameBank(nameBankName);
            return nameBank[rand.nextInt(nameBank.length)];
        }
        return "Unnamed";
    }

    public static String getHobbitName(Random rand, boolean male) {
        String name = LOTRNames.getHobbitForename(rand, male);
        String surname = LOTRNames.getHobbitSurname(rand);
        return name + " " + surname;
    }

    private static String getHobbitForename(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "hobbit_male" : "hobbit_female", rand);
        return name;
    }

    private static String getHobbitSurname(Random rand) {
        String name = LOTRNames.getRandomName("hobbit_surname", rand);
        return name;
    }

    public static String[] getHobbitCoupleAndHomeNames(Random rand) {
        String[] names = new String[4];
        String surname = LOTRNames.getHobbitSurname(rand);
        String maleName = LOTRNames.getHobbitForename(rand, true);
        String femaleName = LOTRNames.getHobbitForename(rand, false);
        String homeName = LOTRNames.getRandomName("hobbit_home", rand);
        names[0] = maleName + " " + surname;
        names[1] = femaleName + " " + surname;
        names[2] = surname;
        names[3] = homeName;
        return names;
    }

    public static String[] getHobbitSign(Random rand) {
        String[] sign = new String[4];
        Arrays.fill(sign, "");
        String text = LOTRNames.getRandomName("hobbit_sign", rand);
        String[] split = text.split("#");
        sign[1] = split[0];
        sign[2] = split.length < 2 ? "" : split[1];
        if(rand.nextInt(1000) == 0) {
            sign[1] = "Vote";
            sign[2] = "UKIP";
        }
        return sign;
    }

    public static String getHobbitChildNameForParent(Random rand, boolean male, LOTREntityHobbit parent) {
        String name = LOTRNames.getHobbitForename(rand, male);
        String surname = parent.getNPCName().substring(parent.getNPCName().indexOf(" ") + 1);
        return name + " " + surname;
    }

    public static void changeHobbitSurnameForMarriage(LOTREntityHobbit maleHobbit, LOTREntityHobbit femaleHobbit) {
        String surname = maleHobbit.getNPCName().substring(maleHobbit.getNPCName().indexOf(" ") + 1);
        String femaleFirstName = femaleHobbit.getNPCName().substring(0, femaleHobbit.getNPCName().indexOf(" "));
        femaleHobbit.familyInfo.setName(femaleFirstName + " " + surname);
    }

    public static String[] getHobbitTavernName(Random rand) {
        String prefix = LOTRNames.getRandomName("hobbitTavern_prefix", rand);
        String suffix = LOTRNames.getRandomName("hobbitTavern_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String[] getHobbitTavernQuote(Random rand) {
    	String[] sign = new String[4];
        Arrays.fill(sign, "");
        String text = LOTRNames.getRandomName("hobbitTavern_quote", rand);
        String[] split = text.split("#");
        for (int l = 0; l < sign.length && l < split.length; ++l) {
            sign[l] = split[l];
        }
        return sign;
    }

    public static String getBreeName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "bree_male" : "bree_female", rand);
        String surname = LOTRNames.getRandomName("bree_surname", rand);
        return name + " " + surname;
    }

    public static String[] getBreeCoupleAndHomeNames(Random rand) {
        String[] names = new String[4];
        String surname = LOTRNames.getRandomName("bree_surname", rand);
        String maleName = LOTRNames.getRandomName("bree_male", rand);
        String femaleName = LOTRNames.getRandomName("bree_female", rand);
        names[0] = maleName + " " + surname;
        names[1] = femaleName + " " + surname;
        names[2] = surname;
        names[3] = "House";
        return names;
    }

    public static String[] getBreeInnName(Random rand) {
        String prefix = LOTRNames.getRandomName("breeInn_prefix", rand);
        String suffix = LOTRNames.getRandomName("breeInn_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String[] getBreeRuffianSign(Random rand) {
    	String[] sign = new String[4];
        Arrays.fill(sign, "");
        String text = LOTRNames.getRandomName("bree_ruffian_sign", rand);
        String[] split = text.split("#");
        sign[1] = split[0];
        sign[2] = split.length < 2 ? "" : split[1];
        return sign;
    }

    public static String getBreeHobbitName(Random rand, boolean male) {
        String name = LOTRNames.getBreeHobbitForename(rand, male);
        String surname = LOTRNames.getBreeHobbitSurname(rand);
        return name + " " + surname;
    }

    private static String getBreeHobbitForename(Random rand, boolean male) {
        boolean shirelike;
        shirelike = rand.nextInt(3) == 0;
        String name = LOTRNames.getRandomName(shirelike ? (male ? "hobbit_male" : "hobbit_female") : (male ? "bree_male" : "bree_female"), rand);
        return name;
    }

    private static String getBreeHobbitSurname(Random rand) {
        boolean shirelike = rand.nextInt(3) == 0;
        String name = LOTRNames.getRandomName(shirelike ? "hobbit_surname" : "bree_surname", rand);
        return name;
    }

    public static String getBreeHobbitChildNameForParent(Random rand, boolean male, LOTREntityHobbit parent) {
        String name = LOTRNames.getBreeHobbitForename(rand, male);
        String surname = parent.getNPCName().substring(parent.getNPCName().indexOf(" ") + 1);
        return name + " " + surname;
    }

    public static String[] getBreeHobbitCoupleAndHomeNames(Random rand) {
        String[] names = new String[4];
        String surname = LOTRNames.getBreeHobbitSurname(rand);
        String maleName = LOTRNames.getBreeHobbitForename(rand, true);
        String femaleName = LOTRNames.getBreeHobbitForename(rand, false);
        String homeName = LOTRNames.getRandomName("hobbit_home", rand);
        names[0] = maleName + " " + surname;
        names[1] = femaleName + " " + surname;
        names[2] = surname;
        names[3] = homeName;
        return names;
    }

    public static String getSindarinName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "sindarin_male" : "sindarin_female", rand);
        return name;
    }

    public static String getQuenyaName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "quenya_male" : "quenya_female", rand);
        if (rand.nextInt(5) == 0) {
            name = name + " " + LOTRNames.getRandomName("quenya_title", rand);
        }
        return name;
    }

    public static String getSindarinOrQuenyaName(Random rand, boolean male) {
        if (male) {
            String[] sNames = LOTRNames.getNameBank("sindarin_male");
            int i = sNames.length + (LOTRNames.getNameBank("quenya_male")).length;
            if (rand.nextInt(i) < sNames.length) {
                return LOTRNames.getSindarinName(rand, male);
            }
            return LOTRNames.getQuenyaName(rand, male);
        }
        String[] sNames = LOTRNames.getNameBank("sindarin_female");
        int i = sNames.length + (LOTRNames.getNameBank("quenya_female")).length;
        if (rand.nextInt(i) < sNames.length) {
            return LOTRNames.getSindarinName(rand, male);
        }
        return LOTRNames.getQuenyaName(rand, male);
    }

    public static String getRhudaurName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "rhudaur_male" : "rhudaur_female", rand);
        return name;
    }

    public static String getRohirricName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "rohan_male" : "rohan_female", rand);
        return name;
    }

    public static String[] getRohanMeadHallName(Random rand) {
        String prefix = LOTRNames.getRandomName("rohanMeadHall_prefix", rand);
        String suffix = LOTRNames.getRandomName("rohanMeadHall_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String[] getRohanVillageName(Random rand) {
        String suffix;
        String welcome = "Welcome to";
        String prefix = LOTRNames.getRandomName("rohanVillage_prefix", rand);
        if (prefix.endsWith((suffix = LOTRNames.getRandomName("rohanVillage_suffix", rand)).substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        String name = prefix + suffix;
        String date = LOTRNames.getRandomVillageDate(rand, 50, 500, 100);
        String est = "est. " + date;
        return new String[]{welcome, name, "", est};
    }

    public static String getDunlendingName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "dunlending_male" : "dunlending_female", rand);
        return name;
    }

    public static String[] getDunlendingTavernName(Random rand) {
        String prefix = LOTRNames.getRandomName("dunlendingTavern_prefix", rand);
        String suffix = LOTRNames.getRandomName("dunlendingTavern_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String getEntName(Random rand) {
        String prefix = LOTRNames.getRandomName("ent_prefix", rand);
        String suffix = LOTRNames.getRandomName("ent_suffix", rand);
        return prefix + suffix;
    }

    public static String getGondorName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "gondor_male" : "gondor_female", rand);
        return name;
    }

    public static String[] getGondorTavernName(Random rand) {
        String prefix = LOTRNames.getRandomName("gondorTavern_prefix", rand);
        String suffix = LOTRNames.getRandomName("gondorTavern_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String[] getGondorVillageName(Random rand) {
        String suffix;
        String welcome = "Welcome to";
        String prefix = LOTRNames.getRandomName("gondorVillage_prefix", rand);
        if (prefix.endsWith((suffix = LOTRNames.getRandomName("gondorVillage_suffix", rand)).substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        String name = prefix + suffix;
        String date = LOTRNames.getRandomVillageDate(rand, 50, 5000, 1500);
        String est = "est. " + date;
        return new String[]{welcome, name, "", est};
    }

    public static String getDorwinionName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "dorwinion_male" : "dorwinion_female", rand);
        return name;
    }

    public static String getDalishName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "dale_male" : "dale_female", rand);
        return name;
    }

    public static String[] getDaleBakeryName(Random rand, String name) {
        String title = LOTRNames.getRandomName("dale_bakery", rand);
        return new String[]{name + "'s", title};
    }

    public static String getDwarfName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "dwarf_male" : "dwarf_female", rand);
        String parentName = LOTRNames.getRandomName("dwarf_male", rand);
        return name + (male ? " son of " : " daughter of ") + parentName;
    }

    public static String getDwarfChildNameForParent(Random rand, boolean male, LOTREntityDwarf parent) {
        String name = LOTRNames.getRandomName(male ? "dwarf_male" : "dwarf_female", rand);
        String parentName = parent.getNPCName();
        parentName = parentName.substring(0, parentName.indexOf(" "));
        return name + (male ? " son of " : " daughter of ") + parentName;
    }

    public static String getRhunicName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "rhun_male" : "rhun_female", rand);
        return name;
    }

    public static String[] getRhunTavernName(Random rand) {
        String prefix = LOTRNames.getRandomName("rhunTavern_prefix", rand);
        String suffix = LOTRNames.getRandomName("rhunTavern_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String[] getRhunVillageName(Random rand) {
        String suffix;
        String welcome = "Welcome to";
        String prefix = LOTRNames.getRandomName("rhunVillage_prefix", rand);
        if (prefix.endsWith((suffix = LOTRNames.getRandomName("rhunVillage_suffix", rand)).substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        String name = prefix + suffix;
        String date = LOTRNames.getRandomVillageDate(rand, 50, 2000, 300);
        String est = "est. " + date;
        return new String[]{welcome, name, "", est};
    }

    public static String getOrcName(Random rand) {
        String prefix = LOTRNames.getRandomName("orc_prefix", rand);
        String suffix = LOTRNames.getRandomName("orc_suffix", rand);
        return prefix + suffix;
    }

    public static String getTrollName(Random rand) {
        String name = LOTRNames.getRandomName("troll", rand);
        return name;
    }

    public static String getUmbarName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "umbar_male" : "umbar_female", rand);
        return name;
    }

    public static String getHarnennorName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "nearHaradrim_male" : "nearHaradrim_female", rand);
        return name;
    }

    public static String getSouthronCoastName(Random rand, boolean male) {
        if (rand.nextInt(3) == 0) {
            return LOTRNames.getUmbarName(rand, male);
        }
        return LOTRNames.getHarnennorName(rand, male);
    }

    public static String getNomadName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "nomad_male" : "nomad_female", rand);
        return name;
    }

    public static String getGulfHaradName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "gulf_male" : "gulf_female", rand);
        return name;
    }

    public static String[] getHaradTavernName(Random rand) {
        String prefix = LOTRNames.getRandomName("haradTavern_prefix", rand);
        String suffix = LOTRNames.getRandomName("haradTavern_suffix", rand);
        return new String[]{prefix, suffix};
    }

    public static String[] getHaradVillageName(Random rand) {
        String suffix;
        String welcome = "Welcome to";
        String prefix = LOTRNames.getRandomName("haradVillage_prefix", rand);
        if (prefix.endsWith((suffix = LOTRNames.getRandomName("haradVillage_suffix", rand)).substring(0, 1))) {
            suffix = suffix.substring(1);
        }
        String name = prefix + suffix;
        String date = LOTRNames.getRandomVillageDate(rand, 50, 4000, 1000);
        String est = "est. " + date;
        return new String[]{welcome, name, "", est};
    }

    public static String getMoredainName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "moredain_male" : "moredain_female", rand);
        return name;
    }

    public static String getTauredainName(Random rand, boolean male) {
        String name = LOTRNames.getRandomName(male ? "tauredain_male" : "tauredain_female", rand);
        return name;
    }

    private static String getRandomVillageDate(Random rand, int min, int max, int std) {
        double d = rand.nextGaussian();
        d = Math.abs(d);
        int ago = min + (int)Math.round(d * std);
        int date = LOTRDate.THIRD_AGE_CURRENT - (ago = Math.min(ago, max));
        if (date >= 1) {
            return "T.A. " + date;
        }
        return "S.A. " + (date += LOTRDate.SECOND_AGE_LENGTH);
    }
}

