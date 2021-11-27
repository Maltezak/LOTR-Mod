package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNomadWarrior extends LOTREntityNomad {
    private static ItemStack[] weaponsBronze = new ItemStack[] {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};

    public LOTREntityNomadWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(8) == 0;
        this.npcShield = null;
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
        int i = this.rand.nextInt(weaponsBronze.length);
        this.npcItemsInv.setMeleeWeapon(weaponsBronze[i].copy());
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNomad));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNomad));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNomad));
        if(this.rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else if(this.rand.nextInt(3) == 0) {
            ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            int robeColor = nomadTurbanColors[this.rand.nextInt(nomadTurbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetNomad));
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
                return "nearHarad/nomad/warrior/hired";
            }
            return "nearHarad/nomad/warrior/friendly";
        }
        return "nearHarad/nomad/warrior/hostile";
    }
}
