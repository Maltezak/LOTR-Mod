package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityGundabadWarg extends LOTREntityWarg {
    public LOTREntityGundabadWarg(World world) {
        super(world);
    }

    @Override
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.75, false);
    }

    @Override
    public LOTREntityNPC createWargRider() {
        return this.worldObj.rand.nextBoolean() ? new LOTREntityGundabadOrcArcher(this.worldObj) : new LOTREntityGundabadOrc(this.worldObj);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GUNDABAD;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
