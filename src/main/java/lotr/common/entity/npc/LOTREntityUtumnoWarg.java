package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityUtumnoWarg extends LOTREntityWarg {
    public LOTREntityUtumnoWarg(World world) {
        super(world);
    }

    @Override
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    @Override
    public LOTREntityNPC createWargRider() {
        return this.worldObj.rand.nextBoolean() ? new LOTREntityUtumnoOrcArcher(this.worldObj) : new LOTREntityUtumnoOrc(this.worldObj);
    }

    @Override
    public boolean canWargBeRidden() {
        return false;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUtumnoWarg;
    }
}
