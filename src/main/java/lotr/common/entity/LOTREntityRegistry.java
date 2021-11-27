package lotr.common.entity;

import java.io.*;
import java.util.*;

import org.apache.commons.io.input.BOMInputStream;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lotr.common.entity.npc.LOTREntityMordorOrc;
import lotr.common.fac.*;

public class LOTREntityRegistry {
    public static Map registeredNPCs = new HashMap();

    public static void loadRegisteredNPCs(FMLPreInitializationEvent event) {
        StringBuilder stringbuilder = new StringBuilder();
        for (LOTRFaction faction : LOTRFaction.values()) {
            if (!faction.allowEntityRegistry) continue;
            if (faction.ordinal() > 0) {
                stringbuilder.append(", ");
            }
            stringbuilder.append(faction.codeName());
        }
        String allFactions = stringbuilder.toString();
        try {
            File file = event.getModConfigurationDirectory();
            File config = new File(file, "LOTR_EntityRegistry.txt");
            if (!config.exists()) {
                if (config.createNewFile()) {
                    PrintStream writer = new PrintStream(new FileOutputStream(config));
                    writer.println("#Lines starting with '#' will be ignored");
                    writer.println("#");
                    writer.println("#Use this file to register entities with the LOTR alignment system.");
                    writer.println("#");
                    writer.println("#An example format for registering an entity is as follows: (do not use spaces)");
                    writer.println("#name=" + LOTREntities.getStringFromClass(LOTREntityMordorOrc.class) + ",faction=" + LOTRFaction.MORDOR.codeName() + ",targetEnemies=true,bonus=1");
                    writer.println("#");
                    writer.println("#'name' is the entity name, prefixed with the associated mod ID.");
                    writer.println("#The mod ID can be found in the Mod List on the main menu - for example, \"lotr\" for the LOTR mod.");
                    writer.println("#The entity name is not necessarily the in-game name. It is the name used to register the entity in the code.");
                    writer.println("#You may be able to discover the entity name in the mod's language file if there is one - otherwise, contact the mod author.");
                    writer.println("#The mod ID and entity name must be separated by a '.' character.");
                    writer.println("#Vanilla entities have no mod ID and therefore no prefix.");
                    writer.println("#");
                    writer.println("#'faction' can be " + allFactions);
                    writer.println("#");
                    writer.println("#'targetEnemies' can be true or false.");
                    writer.println("#If true, the entity will be equipped with AI modules to target its enemies.");
                    writer.println("#Actual combat behaviour may or may not be present, depending on whether the entity is designed with combat AI modules.");
                    writer.println("#");
                    writer.println("#'bonus' is the alignment bonus awarded to a player who kills the entity.");
                    writer.println("#It can be positive, negative, or zero, in which case no bonus will be awarded.");
                    writer.println("#");
                    writer.close();
                }
            } else {
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(config))));
                String s = "";
                while ((s = bufferedreader.readLine()) != null) {
                    int j;
                    int i;
                    boolean targetEnemies;
                    int k;
                    String name;
                    String line = s;
                    if (s.startsWith("#")) continue;
                    LOTRFaction faction = null;
                    if (!s.startsWith("name=") || (s = s.substring("name=".length())).toLowerCase().startsWith("lotr".toLowerCase()) || (i = s.indexOf(",faction=")) < 0 || (j = s.indexOf(",targetEnemies=")) < 0 || (k = s.indexOf(",bonus=")) < 0 || (name = s.substring(0, i)).length() == 0 || (faction = LOTRFaction.forName(s.substring(i + ",faction=".length(), j))) == null) continue;
                    String targetEnemiesString = s.substring(j + ",targetEnemies=".length(), k);
                    if (targetEnemiesString.equals("true")) {
                        targetEnemies = true;
                    } else {
                        if (!targetEnemiesString.equals("false")) continue;
                        targetEnemies = false;
                    }
                    String bonusString = s.substring(k + ",bonus=".length());
                    int bonus = Integer.parseInt(bonusString);
                    registeredNPCs.put(name, new RegistryInfo(name, faction, targetEnemies, bonus));
                    FMLLog.info("Successfully registered entity " + name + " with the LOTR alignment system as " + line);
                }
                bufferedreader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class RegistryInfo {
        public LOTRFaction alignmentFaction;
        public boolean shouldTargetEnemies;
        public LOTRAlignmentValues.AlignmentBonus alignmentBonus;

        public RegistryInfo(String entityName, LOTRFaction side, boolean flag, int bonus) {
            this.alignmentFaction = side;
            this.shouldTargetEnemies = flag;
            this.alignmentBonus = new LOTRAlignmentValues.AlignmentBonus(bonus, "entity." + entityName + ".name");
            this.alignmentBonus.needsTranslation = true;
        }
    }

}

