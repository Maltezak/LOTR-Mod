package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGulfHaradWarrior extends LOTREntityGulfHaradrim {
    public LOTREntityGulfHaradWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(10) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_GULF;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(3) != 0) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordGulfHarad));
        }
        else {
            int i = this.rand.nextInt(5);
            if(i == 0 || i == 1) {
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordHarad));
            }
            else if(i == 2) {
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHarad));
            }
            else if(i == 3) {
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerHaradPoisoned));
            }
            else if(i == 4) {
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.pikeHarad));
            }
        }
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
        if(this.rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGulfHarad));
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
                return "nearHarad/gulf/warrior/hired";
            }
            return "nearHarad/gulf/warrior/friendly";
        }
        return "nearHarad/gulf/warrior/hostile";
    }
}
