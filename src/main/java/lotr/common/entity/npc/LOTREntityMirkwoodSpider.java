package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.world.World;

public class LOTREntityMirkwoodSpider extends LOTREntitySpiderBase {
    public LOTREntityMirkwoodSpider(World world) {
        super(world);
    }

    @Override
    protected int getRandomSpiderScale() {
        return this.rand.nextInt(3);
    }

    @Override
    protected int getRandomSpiderType() {
        return this.rand.nextBoolean() ? 0 : 1 + this.rand.nextInt(2);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DOL_GULDUR;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        if(flag && this.rand.nextInt(4) == 0) {
            this.dropItem(LOTRMod.mysteryWeb, 1);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMirkwoodSpider;
    }
}
