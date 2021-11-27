package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.projectile.LOTREntityThrowingAxe;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLossarnachAxeman extends LOTREntityGondorSoldier {
    private EntityAIBase rangedAttackAI = this.createGondorRangedAI();
    private EntityAIBase meleeAttackAI;

    public LOTREntityLossarnachAxeman(World world) {
        super(world);
        this.spawnRidingHorse = false;
        this.npcShield = LOTRShields.ALIGNMENT_LOSSARNACH;
    }

    @Override
    public EntityAIBase createGondorAttackAI() {
        this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, false);
        return this.meleeAttackAI;
    }

    protected EntityAIBase createGondorRangedAI() {
        return new LOTREntityAIRangedAttack(this, 1.3, 30, 50, 16.0f);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeLossarnach));
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.throwingAxeLossarnach));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsLossarnach));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsLossarnach));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLossarnach));
        if(this.rand.nextInt(3) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetLossarnach));
        }
        else {
            this.setCurrentItemOrArmor(4, null);
        }
        return data;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        ItemStack axeItem = this.npcItemsInv.getRangedWeapon();
        if(axeItem == null) {
            axeItem = new ItemStack(LOTRMod.throwingAxeLossarnach);
        }
        LOTREntityThrowingAxe axe = new LOTREntityThrowingAxe(this.worldObj, this, target, axeItem, 1.0f, (float) this.getEntityAttribute(npcRangedAccuracy).getAttributeValue());
        this.playSound("random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(axe);
        this.swingItem();
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        if(mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
        if(mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getRangedWeapon());
        }
    }
}
