package lotr.common.entity.npc;

import lotr.common.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityUmbarWarrior extends LOTREntityUmbarian {
    private static ItemStack[] weaponsIron = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};

    public LOTREntityUmbarWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(6) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_UMBAR;
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
        int i = this.rand.nextInt(weaponsIron.length);
        this.npcItemsInv.setMeleeWeapon(weaponsIron[i].copy());
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUmbar));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUmbar));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUmbar));
        if(this.rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetUmbar));
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
                return "nearHarad/umbar/warrior/hired";
            }
            return "nearHarad/umbar/warrior/friendly";
        }
        return "nearHarad/umbar/warrior/hostile";
    }
}
