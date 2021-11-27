package lotr.compatibility;

import lotr.common.util.LOTRLog;

public class LOTRModChecker {
    private static int hasNEI = -1;
    private static int isCauldron = -1;
    private static int hasShaders = -1;

    public static boolean hasNEI() {
        if (hasNEI == -1) {
            try {
                if (Class.forName("codechicken.nei.api.API") != null) {
                    System.out.println("LOTR: Found NEI installed");
                    hasNEI = 1;
                } else {
                    hasNEI = 0;
                }
            }
            catch (ClassNotFoundException e) {
                hasNEI = 0;
            }
        }
        return hasNEI == 1;
    }

    public static boolean isCauldronServer() {
        if (isCauldron == -1) {
            try {
                if (Class.forName("kcauldron.KCauldronClassTransformer") != null) {
                    System.out.println("LOTR: Found Cauldron installed");
                    if (LOTRLog.logger != null) {
                        LOTRLog.logger.info("LOTR: Found Cauldron installed");
                    }
                    isCauldron = 1;
                    return true;
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            try {
                if (Class.forName("thermos.ThermosClassTransformer") != null) {
                    System.out.println("LOTR: Found Thermos installed");
                    if (LOTRLog.logger != null) {
                        LOTRLog.logger.info("LOTR: Found Thermos installed");
                    }
                    isCauldron = 1;
                    return true;
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
            isCauldron = 0;
            return false;
        }
        return isCauldron == 1;
    }

    public static boolean hasShaders() {
        if (hasShaders == -1) {
            try {
                if (Class.forName("shadersmodcore.client.Shaders") != null) {
                    LOTRLog.logger.info("LOTR: Found shaders installed");
                    hasShaders = 1;
                } else {
                    hasShaders = 0;
                }
            }
            catch (ClassNotFoundException e) {
                hasShaders = 0;
            }
        }
        return hasShaders == 1;
    }
}

