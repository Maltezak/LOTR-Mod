package lotr.common.fellowship;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import lotr.common.*;
import lotr.common.entity.item.LOTREntityBanner;
import net.minecraft.util.StatCollector;

public class LOTRFellowshipProfile extends GameProfile {
    public static final String fellowshipPrefix = "f/";
    private String fellowshipName;

    public LOTRFellowshipProfile(LOTREntityBanner banner, UUID fsID, String fsName) {
        super(fsID, fsName);
        this.fellowshipName = fsName;
    }

    public LOTRFellowship getFellowship() {
        LOTRFellowship fs = LOTRFellowshipData.getFellowship(this.getId());
        if(fs != null && !fs.isDisbanded()) {
            return fs;
        }
        return null;
    }

    public LOTRFellowshipClient getFellowshipClient() {
        return LOTRLevelData.getData(LOTRMod.proxy.getClientPlayer()).getClientFellowshipByName(this.fellowshipName);
    }

    @Override
    public String getName() {
        return LOTRFellowshipProfile.addFellowshipCode(super.getName());
    }

    public static boolean hasFellowshipCode(String s) {
        return s.toLowerCase().startsWith(fellowshipPrefix.toLowerCase());
    }

    public static String addFellowshipCode(String s) {
        return fellowshipPrefix + s;
    }

    public static String stripFellowshipCode(String s) {
        return s.substring(fellowshipPrefix.length());
    }

    public static String getFellowshipCodeHint() {
        return StatCollector.translateToLocalFormatted("lotr.gui.bannerEdit.fellowshipHint", fellowshipPrefix);
    }
}
