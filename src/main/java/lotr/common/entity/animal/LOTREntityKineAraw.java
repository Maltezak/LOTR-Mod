package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.world.World;

public class LOTREntityKineAraw extends LOTREntityAurochs {
    public static float KINE_SCALE = 1.15f;

    public LOTREntityKineAraw(World world) {
        super(world);
        this.setSize(this.aurochsWidth * KINE_SCALE, this.aurochsHeight * KINE_SCALE);
    }

    @Override
    protected EntityAIBase createAurochsAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.9, true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }

    @Override
    protected void dropHornItem(boolean flag, int i) {
        this.dropItem(LOTRMod.kineArawHorn, 1);
    }

    @Override
    public EntityCow createChild(EntityAgeable entity) {
        return new LOTREntityKineAraw(this.worldObj);
    }
}
