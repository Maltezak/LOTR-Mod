package lotr.common.fac;

import lotr.common.*;
import net.minecraft.util.StatCollector;

public class LOTRFactionRank
implements Comparable<LOTRFactionRank> {
    public static final LOTRFactionRank RANK_NEUTRAL = new Dummy("lotr.faction.rank.neutral");
    public static final LOTRFactionRank RANK_ENEMY = new Dummy("lotr.faction.rank.enemy");
    public final LOTRFaction fac;
    public final float alignment;
    public final String name;
    private LOTRAchievementRank rankAchievement;
    private boolean isGendered;
    private LOTRTitle rankTitle;
    private LOTRTitle rankTitleMasc;
    private LOTRTitle rankTitleFem;

    public LOTRFactionRank(LOTRFaction f, float al, String s, boolean gend) {
        this.fac = f;
        this.alignment = al;
        this.name = s;
        this.isGendered = gend;
    }

    public String getCodeName() {
        return "lotr.faction." + this.fac.codeName() + ".rank." + this.name;
    }

    public String getCodeNameFem() {
        return this.getCodeName() + "_fm";
    }

    public String getCodeFullName() {
        return this.getCodeName() + ".f";
    }

    public String getCodeFullNameFem() {
        return this.getCodeNameFem() + ".f";
    }

    public String getCodeFullNameWithGender(LOTRPlayerData pd) {
        if (this.isGendered() && pd.useFeminineRanks()) {
            return this.getCodeFullNameFem();
        }
        return this.getCodeFullName();
    }

    public String getDisplayName() {
        return StatCollector.translateToLocal(this.getCodeName());
    }

    public String getDisplayNameFem() {
        return StatCollector.translateToLocal(this.getCodeNameFem());
    }

    public String getDisplayFullName() {
        return StatCollector.translateToLocal(this.getCodeFullName());
    }

    public String getDisplayFullNameFem() {
        return StatCollector.translateToLocal(this.getCodeFullNameFem());
    }

    public String getShortNameWithGender(LOTRPlayerData pd) {
        if (this.isGendered() && pd.useFeminineRanks()) {
            return this.getDisplayNameFem();
        }
        return this.getDisplayName();
    }

    public String getFullNameWithGender(LOTRPlayerData pd) {
        if (this.isGendered() && pd.useFeminineRanks()) {
            return this.getDisplayFullNameFem();
        }
        return this.getDisplayFullName();
    }

    public boolean isGendered() {
        return this.isGendered;
    }

    public boolean isDummyRank() {
        return false;
    }

    public LOTRFactionRank makeTitle() {
        if (this.isGendered) {
            this.rankTitleMasc = new LOTRTitle(this, false);
            this.rankTitleFem = new LOTRTitle(this, true);
            return this;
        }
        this.rankTitle = new LOTRTitle(this, false);
        return this;
    }

    public LOTRFactionRank makeAchievement() {
        this.rankAchievement = new LOTRAchievementRank(this);
        return this;
    }

    public LOTRAchievementRank getRankAchievement() {
        return this.rankAchievement;
    }

    public LOTRFactionRank setPledgeRank() {
        this.fac.setPledgeRank(this);
        return this;
    }

    public boolean isPledgeRank() {
        return this == this.fac.getPledgeRank();
    }

    public boolean isAbovePledgeRank() {
        return this.alignment > this.fac.getPledgeAlignment();
    }

    @Override
    public int compareTo(LOTRFactionRank other) {
        if (this.fac != other.fac) {
            throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
        }
        float al1 = this.alignment;
        float al2 = other.alignment;
        if (al1 == al2) {
            throw new IllegalArgumentException("Two ranks cannot have the same alignment value!");
        }
        return -Float.valueOf(al1).compareTo(al2);
    }

    public static final class Dummy
    extends LOTRFactionRank {
        public Dummy(String s) {
            super(null, 0.0f, s, false);
        }

        @Override
        public String getCodeName() {
            return this.name;
        }

        @Override
        public String getDisplayName() {
            return StatCollector.translateToLocal(this.getCodeName());
        }

        @Override
        public String getDisplayFullName() {
            return this.getDisplayName();
        }

        @Override
        public boolean isDummyRank() {
            return true;
        }
    }

}

