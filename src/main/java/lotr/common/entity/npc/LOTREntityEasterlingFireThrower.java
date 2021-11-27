package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityFirePot;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingFireThrower extends LOTREntityEasterlingWarrior {
    public EntityAIBase rangedAttackAI = this.createEasterlingRangedAI();
    public EntityAIBase meleeAttackAI;

    public LOTREntityEasterlingFireThrower(World world) {
        super(world);
        this.spawnRidingHorse = false;
    }

    @Override
    protected EntityAIBase createEasterlingAttackAI() {
        this.meleeAttackAI = super.createEasterlingAttackAI();
        return this.meleeAttackAI;
    }

    protected EntityAIBase createEasterlingRangedAI() {
        return new LOTREntityAIRangedAttack(this, 1.3, 20, 30, 16.0f);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRhun));
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rhunFirePot));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
        return data;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        EntityArrow template = new EntityArrow(this.worldObj, this, target, 1.0f, 0.5f);
        LOTREntityFirePot pot = new LOTREntityFirePot(this.worldObj, this);
        pot.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
        pot.motionX = template.motionX;
        pot.motionY = template.motionY;
        pot.motionZ = template.motionZ;
        this.playSound("random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(pot);
    }

    @Override
    public double getMeleeRange() {
        EntityLivingBase target = this.getAttackTarget();
        if(target != null && target.isBurning()) {
            return Double.MAX_VALUE;
        }
        return super.getMeleeRange();
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
