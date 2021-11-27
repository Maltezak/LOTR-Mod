package lotr.common.fellowship;

import java.util.*;

import com.google.common.collect.ImmutableList;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.*;
import lotr.common.network.*;

public interface FellowshipUpdateType {
    IMessage createUpdatePacket(LOTRPlayerData var1, LOTRFellowship var2);

    List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship var1);

    public static class ToggleShowMapLocations
    implements FellowshipUpdateType {
        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.ToggleShowMap(fs);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class ToggleHiredFriendlyFire
    implements FellowshipUpdateType {
        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.ToggleHiredFriendlyFire(fs);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class TogglePvp
    implements FellowshipUpdateType {
        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.TogglePvp(fs);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class ChangeIcon
    implements FellowshipUpdateType {
        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.ChangeIcon(fs);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class Rename
    implements FellowshipUpdateType {
        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.Rename(fs);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class UpdatePlayerTitle
    implements FellowshipUpdateType {
        private final UUID playerID;
        private final LOTRTitle.PlayerTitle playerTitle;

        public UpdatePlayerTitle(UUID player, LOTRTitle.PlayerTitle title) {
            this.playerID = player;
            this.playerTitle = title;
        }

        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.UpdatePlayerTitle(fs, this.playerID, this.playerTitle);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class RemoveAdmin
    implements FellowshipUpdateType {
        private final UUID adminID;

        public RemoveAdmin(UUID admin) {
            this.adminID = admin;
        }

        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.RemoveAdmin(fs, this.adminID, fs.isAdmin(pd.getPlayerUUID()));
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class SetAdmin
    implements FellowshipUpdateType {
        private final UUID adminID;

        public SetAdmin(UUID admin) {
            this.adminID = admin;
        }

        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.SetAdmin(fs, this.adminID, fs.isAdmin(pd.getPlayerUUID()));
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return null;
        }
    }

    public static class RemoveMember
    implements FellowshipUpdateType {
        private final UUID memberID;

        public RemoveMember(UUID member) {
            this.memberID = member;
        }

        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.RemoveMember(fs, this.memberID);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return ImmutableList.of(this.memberID);
        }
    }

    public static class AddMember
    implements FellowshipUpdateType {
        private final UUID memberID;

        public AddMember(UUID member) {
            this.memberID = member;
        }

        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.AddMember(fs, this.memberID);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return ImmutableList.of(this.memberID);
        }
    }

    public static class SetOwner
    implements FellowshipUpdateType {
        private final UUID ownerID;

        public SetOwner(UUID owner) {
            this.ownerID = owner;
        }

        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowshipPartialUpdate.SetOwner(fs, this.ownerID, fs.isOwner(pd.getPlayerUUID()));
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return ImmutableList.of(this.ownerID);
        }
    }

    public static class Full
    implements FellowshipUpdateType {
        @Override
        public IMessage createUpdatePacket(LOTRPlayerData pd, LOTRFellowship fs) {
            return new LOTRPacketFellowship(pd, fs, false);
        }

        @Override
        public List<UUID> getPlayersToCheckSharedWaypointsFrom(LOTRFellowship fs) {
            return fs.getAllPlayerUUIDs();
        }
    }

}

