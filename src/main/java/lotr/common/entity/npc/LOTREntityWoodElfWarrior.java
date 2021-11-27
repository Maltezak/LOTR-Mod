package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityWoodElfWarrior extends LOTREntityWoodElf {
    public LOTREntityWoodElfWarrior(World world) {
        super(world);
        this.tasks.removeTask(this.rangedAttackAI);
        this.tasks.addTask(2, this.meleeAttackAI);
        this.spawnRidingHorse = this.rand.nextInt(4) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_WOOD_ELF;
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityElk elk = new LOTREntityElk(this.worldObj);
        elk.setMountArmor(new ItemStack(LOTRMod.elkArmorWoodElven));
        return elk;
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 25, 35, 24.0f);
    }

    @Override
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(6);
        if(i == 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.polearmWoodElven));
        }
        else if(i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.longspearWoodElven));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordWoodElven));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.mirkwoodBow));
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearWoodElven));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElven));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElven));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElven));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetWoodElven));
        }
        else {
            this.setCurrentItemOrArmor(4, null);
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "woodElf/elf/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
                return "woodElf/warrior/friendly";
            }
            return "woodElf/elf/neutral";
        }
        return "woodElf/warrior/hostile";
    }
}
