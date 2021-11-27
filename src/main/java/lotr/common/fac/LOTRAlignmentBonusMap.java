package lotr.common.fac;

import java.util.*;

public class LOTRAlignmentBonusMap extends HashMap<LOTRFaction, Float> {
    public Set<LOTRFaction> getChangedFactions() {
        HashSet<LOTRFaction> changed = new HashSet<>();
        for(LOTRFaction fac : this.keySet()) {
            float bonus = this.get(fac);
            if(bonus == 0.0f) continue;
            changed.add(fac);
        }
        return changed;
    }
}
