package lotr.common.entity.animal;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityElk
extends LOTREntityHorse
implements LOTRRandomSkinEntity {
    public LOTREntityElk(World world) {
        super(world);
        this.setSize(1.6f, 1.8f);
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    protected boolean isMountHostile() {
        return true;
    }

    @Override
    protected EntityAIBase createMountAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.25, true);
    }

    public int getHorseType() {
        return 0;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
    }

    @Override
    protected void onLOTRHorseSpawn() {
        double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth *= 1.0f + this.rand.nextFloat() * 0.5f);
    }

    @Override
    protected double clampChildHealth(double health) {
        return MathHelper.clamp_double(health, 16.0, 50.0);
    }

    @Override
    protected double clampChildJump(double jump) {
        return MathHelper.clamp_double(jump, 0.3, 1.0);
    }

    @Override
    protected double clampChildSpeed(double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.34);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.wheat;
    }

    protected void dropFewItems(boolean flag, int i) {
        int hide = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for (int l = 0; l < hide; ++l) {
            this.dropItem(Items.leather, 1);
        }
        int meat = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for (int l = 0; l < meat; ++l) {
            if (this.isBurning()) {
                this.dropItem(LOTRMod.deerCooked, 1);
                continue;
            }
            this.dropItem(LOTRMod.deerRaw, 1);
        }
    }

    protected String getLivingSound() {
        super.getLivingSound();
        return "lotr:elk.say";
    }

    protected String getHurtSound() {
        super.getHurtSound();
        return "lotr:elk.hurt";
    }

    protected String getDeathSound() {
        super.getDeathSound();
        return "lotr:elk.death";
    }
}

