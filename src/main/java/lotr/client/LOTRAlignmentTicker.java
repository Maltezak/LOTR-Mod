package lotr.client;

import java.util.*;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRAlignmentTicker {
    private static Map<LOTRFaction, LOTRAlignmentTicker> allFactionTickers = new HashMap<>();
    private final LOTRFaction theFac;
    private float oldAlign;
    private float newAlign;
    private int moveTick = 0;
    private int prevMoveTick = 0;
    public int flashTick;
    public int numericalTick;
    public static LOTRAlignmentTicker forFaction(LOTRFaction fac) {
        LOTRAlignmentTicker ticker = allFactionTickers.get(fac);
        if(ticker == null) {
            ticker = new LOTRAlignmentTicker(fac);
            allFactionTickers.put(fac, ticker);
        }
        return ticker;
    }

    public static void updateAll(EntityPlayer entityplayer, boolean forceInstant) {
        for(LOTRDimension dim : LOTRDimension.values()) {
            for(LOTRFaction fac : dim.factionList) {
                LOTRAlignmentTicker.forFaction(fac).update(entityplayer, forceInstant);
            }
        }
    }

    public LOTRAlignmentTicker(LOTRFaction f) {
        this.theFac = f;
    }

    public void update(EntityPlayer entityplayer, boolean forceInstant) {
        float curAlign = LOTRLevelData.getData(entityplayer).getAlignment(this.theFac);
        if(forceInstant) {
            this.oldAlign = this.newAlign = curAlign;
            this.moveTick = 0;
            this.prevMoveTick = 0;
            this.flashTick = 0;
            this.numericalTick = 0;
        }
        else {
            if(this.newAlign != curAlign) {
                this.oldAlign = this.newAlign;
                this.newAlign = curAlign;
                this.moveTick = 20;
                this.prevMoveTick = 20;
                this.flashTick = 30;
                this.numericalTick = 200;
            }
            this.prevMoveTick = this.moveTick;
            if(this.moveTick > 0) {
                --this.moveTick;
                if(this.moveTick <= 0) {
                    this.oldAlign = this.newAlign;
                }
            }
            if(this.flashTick > 0) {
                --this.flashTick;
            }
            if(this.numericalTick > 0) {
                --this.numericalTick;
            }
        }
    }

    public float getInterpolatedAlignment(float f) {
        if(this.moveTick == 0) {
            return this.oldAlign;
        }
        float tickF = this.prevMoveTick + (this.moveTick - this.prevMoveTick) * f;
        tickF /= 20.0f;
        tickF = 1.0f - tickF;
        float align = this.oldAlign + (this.newAlign - this.oldAlign) * tickF;
        return align;
    }
}
