package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDunlendingBerserker extends LOTREntityDunlendingWarrior {
    public LOTREntityDunlendingBerserker(World world) {
        super(world);
        this.npcShield = null;
        this.npcCape = LOTRCapes.DUNLENDING_BERSERKER;
    }

    @Override
    public EntityAIBase getDunlendingAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(npcAttackDamageExtra).setBaseValue(2.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(2);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeIron));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeBronze));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsFur));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsFur));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyBone));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDunlending));
        return data;
    }
}
