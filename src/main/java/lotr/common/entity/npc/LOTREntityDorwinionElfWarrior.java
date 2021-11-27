package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDorwinionElfWarrior extends LOTREntityDorwinionElf {
    public LOTREntityDorwinionElfWarrior(World world) {
        super(world);
        this.tasks.addTask(2, this.meleeAttackAI);
        this.npcShield = LOTRShields.ALIGNMENT_DORWINION_ELF;
    }

    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(2);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordDorwinionElf));
            if(this.rand.nextInt(5) == 0) {
                this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
            }
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearBladorthin));
            this.npcItemsInv.setSpearBackup(null);
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinionElf));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinionElf));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinionElf));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDorwinionElf));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "dorwinion/elfWarrior/hired";
            }
            return "dorwinion/elfWarrior/friendly";
        }
        return "dorwinion/elfWarrior/hostile";
    }
}
