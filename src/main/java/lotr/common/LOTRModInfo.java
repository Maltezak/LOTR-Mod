package lotr.common;

public class LOTRModInfo {
    public static final String modID = "lotr";
    public static final String modName = "The Lord of the Rings Mod";
    public static final String version = "Update v36.10 for Minecraft 1.7.10";
    public static final String[] description = new String[]{"Powered by Hummel009"};

    public static final String concatenateDescription(int startIndex) {
        String s = "";
        for (int i = startIndex = java.lang.Math.min(startIndex, description.length - 1); i < description.length; ++i) {
            s = s + description[i] + "\n\n";
        }
        return s;
    }
}

