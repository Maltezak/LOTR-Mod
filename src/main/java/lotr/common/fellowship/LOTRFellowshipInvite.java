package lotr.common.fellowship;

import java.util.UUID;

public class LOTRFellowshipInvite {
    public final UUID fellowshipID;
    public final UUID inviterID;

    public LOTRFellowshipInvite(UUID fs, UUID inviter) {
        this.fellowshipID = fs;
        this.inviterID = inviter;
    }
}
