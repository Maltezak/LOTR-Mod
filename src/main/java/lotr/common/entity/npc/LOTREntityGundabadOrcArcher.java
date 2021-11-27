package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import lotr.common.item.LOTRItemCrossbow;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGundabadOrcArcher extends LOTREntityGundabadOrc {
    public LOTREntityGundabadOrcArcher(World world) {
        super(world);
    }

    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 30, 60, 16.0f);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(4) == 0) {
            if(this.rand.nextBoolean()) {
                this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.ironCrossbow));
            }
            else {
                this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.bronzeCrossbow));
            }
        }
        else if(this.rand.nextBoolean()) {
            this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.orcBow));
        }
        else {
            this.npcItemsInv.setRangedWeapon(new ItemStack(Items.bow));
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

    private boolean isCrossbowOrc() {
        ItemStack itemstack = this.npcItemsInv.getRangedWeapon();
        return itemstack != null && itemstack.getItem() instanceof LOTRItemCrossbow;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        if(this.isCrossbowOrc()) {
            this.npcCrossbowAttack(target, f);
        }
        else {
            this.npcArrowAttack(target, f);
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        if(this.isCrossbowOrc()) {
            this.dropNPCCrossbowBolts(i);
        }
        else {
            this.dropNPCArrows(i);
        }
    }
}
