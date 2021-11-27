package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.entity.projectile.LOTREntityDart;
import lotr.common.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityTauredainBlowgunner extends LOTREntityTauredain {
    public LOTREntityTauredainBlowgunner(World world) {
        super(world);
        this.addTargetTasks(true);
    }

    @Override
    public EntityAIBase createHaradrimAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.5, 10, 30, 16.0f);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.tauredainBlowgun));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsTauredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsTauredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyTauredain));
        return data;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        ItemStack heldItem = this.getHeldItem();
        float str = 1.0f + this.getDistanceToEntity(target) / 16.0f * 0.015f;
        LOTREntityDart dart = ((LOTRItemDart) LOTRMod.tauredainDart).createDart(this.worldObj, this, target, new ItemStack(LOTRMod.tauredainDart), str *= LOTRItemBlowgun.getBlowgunLaunchSpeedFactor(heldItem), 1.0f);
        if(heldItem != null) {
            LOTRItemBlowgun.applyBlowgunModifiers(dart, heldItem);
        }
        this.playSound("lotr:item.dart", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 1.2f) + 0.5f);
        this.worldObj.spawnEntityInWorld(dart);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        this.dropNPCAmmo(LOTRMod.tauredainDart, i);
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "tauredain/warrior/hired";
            }
            return "tauredain/warrior/friendly";
        }
        return "tauredain/warrior/hostile";
    }
}
