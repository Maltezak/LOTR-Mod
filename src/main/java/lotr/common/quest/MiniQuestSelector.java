package lotr.common.quest;

import java.util.UUID;

import com.google.common.base.Supplier;

import lotr.common.fac.LOTRFaction;

public interface MiniQuestSelector {
    boolean include(LOTRMiniQuest var1);

    public static class BountyActiveFaction
    extends BountyActiveAnyFaction {
        private final Supplier<LOTRFaction> factionGet;

        public BountyActiveFaction(Supplier<LOTRFaction> sup) {
            this.factionGet = sup;
        }

        @Override
        public boolean include(LOTRMiniQuest quest) {
            return super.include(quest) && quest.entityFaction == this.factionGet.get();
        }
    }

    public static class BountyActiveAnyFaction
    extends OptionalActive {
        public BountyActiveAnyFaction() {
            this.setActiveOnly();
        }

        @Override
        public boolean include(LOTRMiniQuest quest) {
            if (super.include(quest) && quest instanceof LOTRMiniQuestBounty) {
                LOTRMiniQuestBounty bQuest = (LOTRMiniQuestBounty)quest;
                return !bQuest.killed;
            }
            return false;
        }
    }

    public static class Faction
    extends OptionalActive {
        private final Supplier<LOTRFaction> factionGet;

        public Faction(Supplier<LOTRFaction> sup) {
            this.factionGet = sup;
        }

        @Override
        public boolean include(LOTRMiniQuest quest) {
            return super.include(quest) && quest.entityFaction == this.factionGet.get();
        }
    }

    public static class EntityId
    extends OptionalActive {
        private final UUID entityID;

        public EntityId(UUID id) {
            this.entityID = id;
        }

        @Override
        public boolean include(LOTRMiniQuest quest) {
            return super.include(quest) && quest.entityUUID.equals(this.entityID);
        }
    }

    public static class OptionalActive
    implements MiniQuestSelector {
        private boolean activeOnly = false;

        public OptionalActive setActiveOnly() {
            this.activeOnly = true;
            return this;
        }

        @Override
        public boolean include(LOTRMiniQuest quest) {
            if (this.activeOnly) {
                return quest.isActive();
            }
            return true;
        }
    }

}

