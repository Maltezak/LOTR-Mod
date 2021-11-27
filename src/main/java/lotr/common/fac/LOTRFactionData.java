package lotr.common.fac;

import lotr.common.LOTRPlayerData;
import net.minecraft.nbt.NBTTagCompound;

public class LOTRFactionData {
    private LOTRPlayerData playerData;
    private LOTRFaction theFaction;
    private int npcsKilled;
    private int enemiesKilled;
    private int tradeCount;
    private int hireCount;
    private int miniQuestsCompleted;
    private float conquestEarned;
    private boolean hasConquestHorn;

    public LOTRFactionData(LOTRPlayerData data, LOTRFaction faction) {
        this.playerData = data;
        this.theFaction = faction;
    }

    public void save(NBTTagCompound nbt) {
        nbt.setInteger("NPCKill", this.npcsKilled);
        nbt.setInteger("EnemyKill", this.enemiesKilled);
        nbt.setInteger("Trades", this.tradeCount);
        nbt.setInteger("Hired", this.hireCount);
        nbt.setInteger("MiniQuests", this.miniQuestsCompleted);
        if(this.conquestEarned != 0.0f) {
            nbt.setFloat("Conquest", this.conquestEarned);
        }
        nbt.setBoolean("ConquestHorn", this.hasConquestHorn);
    }

    public void load(NBTTagCompound nbt) {
        this.npcsKilled = nbt.getInteger("NPCKill");
        this.enemiesKilled = nbt.getInteger("EnemyKill");
        this.tradeCount = nbt.getInteger("Trades");
        this.hireCount = nbt.getInteger("Hired");
        this.miniQuestsCompleted = nbt.getInteger("MiniQuests");
        this.conquestEarned = nbt.getFloat("Conquest");
        this.hasConquestHorn = nbt.getBoolean("ConquestHorn");
    }

    private void updateFactionData() {
        this.playerData.updateFactionData(this.theFaction, this);
    }

    public int getNPCsKilled() {
        return this.npcsKilled;
    }

    public void addNPCKill() {
        ++this.npcsKilled;
        this.updateFactionData();
    }

    public int getEnemiesKilled() {
        return this.enemiesKilled;
    }

    public void addEnemyKill() {
        ++this.enemiesKilled;
        this.updateFactionData();
    }

    public int getTradeCount() {
        return this.tradeCount;
    }

    public void addTrade() {
        ++this.tradeCount;
        this.updateFactionData();
    }

    public int getHireCount() {
        return this.hireCount;
    }

    public void addHire() {
        ++this.hireCount;
        this.updateFactionData();
    }

    public int getMiniQuestsCompleted() {
        return this.miniQuestsCompleted;
    }

    public void completeMiniQuest() {
        ++this.miniQuestsCompleted;
        this.updateFactionData();
    }

    public float getConquestEarned() {
        return this.conquestEarned;
    }

    public void addConquest(float f) {
        this.conquestEarned += f;
        this.updateFactionData();
    }

    public boolean hasConquestHorn() {
        return this.hasConquestHorn;
    }

    public void takeConquestHorn() {
        this.hasConquestHorn = true;
        this.updateFactionData();
    }
}
