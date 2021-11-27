package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import net.minecraft.entity.boss.IBossDisplayData;

public interface LOTRBoss extends IBossDisplayData {
    LOTRAchievement getBossKillAchievement();

    float getBaseChanceModifier();

    void onJumpAttackFall();
}
