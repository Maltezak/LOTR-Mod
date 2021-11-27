package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDwarfAxeThrower extends LOTREntityDwarfWarrior {
    public LOTREntityDwarfAxeThrower(World world) {
        super(world);
    }

    @Override
    public EntityAIBase getDwarfAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 40, 12.0f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeDwarven));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
        return data;
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
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
            axeItem = new ItemStack(LOTRMod.throwingAxeDwarven);
        }
        LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(this.worldObj, this, target, axeItem, 1.0f, (float) this.getEntityAttribute(npcRangedAccuracy).getAttributeValue());
        this.playSound("random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(axe);
        this.swingItem();
    }
}
