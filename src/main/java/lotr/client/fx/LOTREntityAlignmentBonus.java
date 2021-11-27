package lotr.client.fx;

import lotr.common.fac.*;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityAlignmentBonus
extends Entity {
    public int particleAge;
    public int particleMaxAge;
    public String name;
    public LOTRFaction mainFaction;
    public float prevMainAlignment;
    public LOTRAlignmentBonusMap factionBonusMap;
    public boolean isKill;
    public boolean isHiredKill;
    public float conquestBonus;

    public LOTREntityAlignmentBonus(World world, double d, double d1, double d2, String s, LOTRFaction f, float pre, LOTRAlignmentBonusMap fMap, boolean kill, boolean hiredKill, float conqBonus) {
        super(world);
        this.setSize(0.5f, 0.5f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(d, d1, d2);
        this.particleAge = 0;
        this.name = s;
        this.mainFaction = f;
        this.prevMainAlignment = pre;
        this.factionBonusMap = fMap;
        this.isKill = kill;
        this.isHiredKill = hiredKill;
        this.conquestBonus = conqBonus;
        this.calcMaxAge();
    }

    private void calcMaxAge() {
        float highestBonus = 0.0f;
        for (LOTRFaction fac : this.factionBonusMap.getChangedFactions()) {
            float bonus = Math.abs(this.factionBonusMap.get(fac));
            if ((bonus <= highestBonus)) continue;
            highestBonus = bonus;
        }
        float conq = Math.abs(this.conquestBonus);
        if (conq > highestBonus) {
            highestBonus = conq;
        }
        this.particleMaxAge = 80;
        int extra = (int)(Math.min(1.0f, highestBonus / 50.0f) * 220.0f);
        this.particleMaxAge += extra;
    }

    protected void entityInit() {
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.particleAge;
        if (this.particleAge >= this.particleMaxAge) {
            this.setDead();
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean isEntityInvulnerable() {
        return true;
    }

    public boolean canBePushed() {
        return false;
    }
}

