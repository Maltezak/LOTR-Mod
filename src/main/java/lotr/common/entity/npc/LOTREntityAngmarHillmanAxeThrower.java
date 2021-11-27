package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarHillmanAxeThrower extends LOTREntityAngmarHillmanWarrior {
    public LOTREntityAngmarHillmanAxeThrower(World world) {
        super(world);
    }

    @Override
    public EntityAIBase getHillmanAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.4, 40, 60, 12.0f);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(3) == 0) {
            this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeIron));
        }
        else {
            this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeBronze));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
        return data;
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getRangedWeapon());
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        ItemStack axeItem = this.npcItemsInv.getRangedWeapon();
        if(axeItem == null) {
            axeItem = new ItemStack(LOTRMod.throwingAxeIron);
        }
        LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(this.worldObj, this, target, axeItem, 1.0f, (float) this.getEntityAttribute(npcRangedAccuracy).getAttributeValue());
        this.playSound("random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(axe);
        this.swingItem();
    }
}
