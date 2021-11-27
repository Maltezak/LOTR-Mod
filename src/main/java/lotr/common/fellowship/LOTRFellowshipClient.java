package lotr.common.fellowship;

import java.util.*;

import lotr.common.LOTRTitle;
import net.minecraft.item.ItemStack;

public class LOTRFellowshipClient {
    private UUID fellowshipID;
    private String fellowshipName;
    private ItemStack fellowshipIcon;
    private boolean isOwned;
    private boolean isAdminned;
    private String ownerName;
    private List<String> memberNames = new ArrayList<>();
    private Map<String, LOTRTitle.PlayerTitle> titleMap = new HashMap<>();
    private Set<String> adminNames = new HashSet<>();
    private boolean preventPVP;
    private boolean preventHiredFF;
    private boolean showMapLocations;

    public LOTRFellowshipClient(UUID id, String name, boolean owned, boolean admin, String owner, List<String> members) {
        this.fellowshipID = id;
        this.fellowshipName = name;
        this.isOwned = owned;
        this.isAdminned = admin;
        this.ownerName = owner;
        this.memberNames = members;
    }

    public void setOwner(String newOwner, boolean owned) {
        String prevOwner = this.ownerName;
        if (!prevOwner.equals(newOwner)) {
            if (!this.memberNames.contains(prevOwner)) {
                this.memberNames.add(0, prevOwner);
            }
            this.ownerName = newOwner;
            if (this.memberNames.contains(newOwner)) {
                this.memberNames.remove(newOwner);
            }
            if (this.adminNames.contains(newOwner)) {
                this.adminNames.remove(newOwner);
            }
            this.isOwned = owned;
            if (this.isOwned) {
                this.isAdminned = false;
            }
        }
    }

    public void addMember(String member, LOTRTitle.PlayerTitle title) {
        if (!this.memberNames.contains(member)) {
            this.memberNames.add(member);
            this.titleMap.put(member, title);
        }
    }

    public void removeMember(String member) {
        if (this.memberNames.contains(member)) {
            this.memberNames.remove(member);
            if (this.adminNames.contains(member)) {
                this.adminNames.remove(member);
            }
            this.titleMap.remove(member);
        }
    }

    public void setTitles(Map<String, LOTRTitle.PlayerTitle> titles) {
        this.titleMap = titles;
    }

    public void updatePlayerTitle(String player, LOTRTitle.PlayerTitle title) {
        if (title == null) {
            this.titleMap.remove(player);
        } else {
            this.titleMap.put(player, title);
        }
    }

    public void setAdmins(Set<String> admins) {
        this.adminNames = admins;
    }

    public void setAdmin(String admin, boolean adminned) {
        if (!this.adminNames.contains(admin)) {
            this.adminNames.add(admin);
            this.isAdminned = adminned;
        }
    }

    public void removeAdmin(String admin, boolean adminned) {
        if (this.adminNames.contains(admin)) {
            this.adminNames.remove(admin);
            this.isAdminned = adminned;
        }
    }

    public void setName(String name) {
        this.fellowshipName = name;
    }

    public void setIcon(ItemStack itemstack) {
        this.fellowshipIcon = itemstack;
    }

    public void setPreventPVP(boolean flag) {
        this.preventPVP = flag;
    }

    public void setPreventHiredFriendlyFire(boolean flag) {
        this.preventHiredFF = flag;
    }

    public void setShowMapLocations(boolean flag) {
        this.showMapLocations = flag;
    }

    public UUID getFellowshipID() {
        return this.fellowshipID;
    }

    public String getName() {
        return this.fellowshipName;
    }

    public boolean isOwned() {
        return this.isOwned;
    }

    public boolean isAdminned() {
        return this.isAdminned;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public List<String> getMemberNames() {
        return this.memberNames;
    }

    public List<String> getAllPlayerNames() {
        ArrayList<String> allPlayers = new ArrayList<>();
        allPlayers.add(this.ownerName);
        allPlayers.addAll(this.memberNames);
        return allPlayers;
    }

    public boolean isPlayerIn(String name) {
        return this.ownerName.equals(name) || this.memberNames.contains(name);
    }

    public int getMemberCount() {
        return this.memberNames.size() + 1;
    }

    public LOTRTitle.PlayerTitle getTitleFor(String name) {
        return this.titleMap.get(name);
    }

    public boolean isAdmin(String name) {
        return this.adminNames.contains(name);
    }

    public ItemStack getIcon() {
        return this.fellowshipIcon;
    }

    public boolean getPreventPVP() {
        return this.preventPVP;
    }

    public boolean getPreventHiredFriendlyFire() {
        return this.preventHiredFF;
    }

    public boolean getShowMapLocations() {
        return this.showMapLocations;
    }

    public void updateDataFrom(LOTRFellowshipClient other) {
        this.fellowshipName = other.fellowshipName;
        this.fellowshipIcon = other.fellowshipIcon;
        this.isOwned = other.isOwned;
        this.isAdminned = other.isAdminned;
        this.ownerName = other.ownerName;
        this.memberNames = other.memberNames;
        this.titleMap = other.titleMap;
        this.adminNames = other.adminNames;
        this.preventPVP = other.preventPVP;
        this.preventHiredFF = other.preventHiredFF;
        this.showMapLocations = other.showMapLocations;
    }
}

