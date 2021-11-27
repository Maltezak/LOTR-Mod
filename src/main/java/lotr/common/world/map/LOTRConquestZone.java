package lotr.common.world.map;

import java.util.*;

import lotr.common.fac.LOTRFaction;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTRConquestZone {
    public final int gridX;
    public final int gridZ;
    public boolean isDummyZone = false;
    private float[] conquestStrengths;
    public static List<LOTRFaction> allPlayableFacs = null;
    private long lastChangeTime;
    private long isEmptyKey = 0L;
    private boolean isLoaded = true;
    private boolean clientSide = false;

    public LOTRConquestZone(int i, int k) {
        this.gridX = i;
        this.gridZ = k;
        if (allPlayableFacs == null && (allPlayableFacs = LOTRFaction.getPlayableAlignmentFactions()).size() >= 62) {
            throw new RuntimeException("Too many factions! Need to upgrade LOTRConquestZone data format.");
        }
        this.conquestStrengths = new float[allPlayableFacs.size()];
    }

    public LOTRConquestZone setClientSide() {
        this.clientSide = true;
        return this;
    }

    public LOTRConquestZone setDummyZone() {
        this.isDummyZone = true;
        return this;
    }

    public float getConquestStrength(LOTRFaction fac, World world) {
        return this.getConquestStrength(fac, world.getTotalWorldTime());
    }

    public float getConquestStrength(LOTRFaction fac, long worldTime) {
        float str = this.getConquestStrengthRaw(fac);
        str -= this.calcTimeStrReduction(worldTime);
        str = Math.max(str, 0.0f);
        return str;
    }

    public float getConquestStrengthRaw(LOTRFaction fac) {
        if (!fac.isPlayableAlignmentFaction()) {
            return 0.0f;
        }
        int index = allPlayableFacs.indexOf(fac);
        float str = this.conquestStrengths[index];
        return str;
    }

    public void setConquestStrengthRaw(LOTRFaction fac, float str) {
        if (!fac.isPlayableAlignmentFaction()) {
            return;
        }
        if (str < 0.0f) {
            str = 0.0f;
        }
        int index = allPlayableFacs.indexOf(fac);
        this.conquestStrengths[index] = str;
        this.isEmptyKey = str == 0.0f ? (this.isEmptyKey &= 1L << index ^ 0xFFFFFFFFFFFFFFFFL) : (this.isEmptyKey |= 1L << index);
        this.markDirty();
    }

    public void setConquestStrength(LOTRFaction fac, float str, World world) {
        this.setConquestStrengthRaw(fac, str);
        this.updateAllOtherFactions(fac, world);
        this.lastChangeTime = world.getTotalWorldTime();
        this.markDirty();
    }

    public void addConquestStrength(LOTRFaction fac, float add, World world) {
        float str = this.getConquestStrength(fac, world);
        this.setConquestStrength(fac, str += add, world);
    }

    private void updateAllOtherFactions(LOTRFaction fac, World world) {
        for (int i = 0; i < this.conquestStrengths.length; ++i) {
            LOTRFaction otherFac = allPlayableFacs.get(i);
            if (otherFac == fac || (this.conquestStrengths[i] <= 0.0f)) continue;
            float otherStr = this.getConquestStrength(otherFac, world);
            this.setConquestStrengthRaw(otherFac, otherStr);
        }
    }

    public void checkForEmptiness(World world) {
        boolean emptyCheck = true;
        for (LOTRFaction fac : allPlayableFacs) {
            float str = this.getConquestStrength(fac, world);
            if (str == 0.0f) continue;
            emptyCheck = false;
            break;
        }
        if (emptyCheck) {
            this.conquestStrengths = new float[allPlayableFacs.size()];
            this.isEmptyKey = 0L;
            this.markDirty();
        }
    }

    public void clearAllFactions(World world) {
        for (LOTRFaction fac : allPlayableFacs) {
            this.setConquestStrengthRaw(fac, 0.0f);
        }
        this.lastChangeTime = world.getTotalWorldTime();
        this.markDirty();
    }

    public long getLastChangeTime() {
        return this.lastChangeTime;
    }

    public void setLastChangeTime(long l) {
        this.lastChangeTime = l;
        this.markDirty();
    }

    private float calcTimeStrReduction(long worldTime) {
        int dl = (int)(worldTime - this.lastChangeTime);
        float s = dl / 20.0f;
        float graceCap = 3600.0f;
        if (s > graceCap) {
            float decayRate = 3600.0f;
            return (s -= graceCap) / decayRate;
        }
        return 0.0f;
    }

    public boolean isEmpty() {
        return this.isEmptyKey == 0L;
    }

    public void markDirty() {
        if (this.isLoaded && !this.clientSide) {
            LOTRConquestGrid.markZoneDirty(this);
        }
    }

    public String toString() {
        return "LOTRConquestZone: " + this.gridX + ", " + this.gridZ;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setShort("X", (short)this.gridX);
        nbt.setShort("Z", (short)this.gridZ);
        nbt.setLong("Time", this.lastChangeTime);
        for (int i = 0; i < this.conquestStrengths.length; ++i) {
            LOTRFaction fac = allPlayableFacs.get(i);
            String facKey = fac.codeName() + "_str";
            float str = this.conquestStrengths[i];
            if (str == 0.0f) continue;
            nbt.setFloat(facKey, str);
        }
    }

    public static LOTRConquestZone readFromNBT(NBTTagCompound nbt) {
        short x = nbt.getShort("X");
        short z = nbt.getShort("Z");
        long time = nbt.getLong("Time");
        LOTRConquestZone zone = new LOTRConquestZone(x, z);
        zone.isLoaded = false;
        zone.lastChangeTime = time;
        block0: for (int i = 0; i < allPlayableFacs.size(); ++i) {
            LOTRFaction fac = allPlayableFacs.get(i);
            ArrayList<String> nameAndAliases = new ArrayList<>();
            nameAndAliases.add(fac.codeName());
            nameAndAliases.addAll(fac.listAliases());
            for (String alias : nameAndAliases) {
                String facKey = alias + "_str";
                if (!nbt.hasKey(facKey)) continue;
                float str = nbt.getFloat(facKey);
                zone.setConquestStrengthRaw(fac, str);
                continue block0;
            }
        }
        zone.isLoaded = true;
        return zone;
    }
}

