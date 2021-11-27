package lotr.common;

import net.minecraft.util.StatCollector;

public enum LOTRGuiMessageTypes {
    FRIENDLY_FIRE("friendlyFire"), UTUMNO_WARN("utumnoWarn"), ENCHANTING("enchanting"), ALIGN_DRAIN("alignDrain");

    public final String messageName;

    LOTRGuiMessageTypes(String s) {
        this.messageName = s;
    }

    public String getMessage() {
        return StatCollector.translateToLocal("lotr.gui.message." + this.messageName);
    }

    public String getSaveName() {
        return this.messageName;
    }

    public static LOTRGuiMessageTypes forSaveName(String name) {
        for(LOTRGuiMessageTypes message : LOTRGuiMessageTypes.values()) {
            if(!message.getSaveName().equals(name)) continue;
            return message;
        }
        return null;
    }
}
