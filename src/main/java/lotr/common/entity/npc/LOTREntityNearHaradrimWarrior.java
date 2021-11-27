package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityNearHaradrimWarrior extends LOTREntityNearHaradrim {
    private static ItemStack[] weaponsIron = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};
    private static ItemStack[] weaponsBronze = new ItemStack[] {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
    private static int[] turbanColors = new int[] {1643539, 6309443, 7014914, 7809314, 5978155};

    public LOTREntityNearHaradrimWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = false;
        this.npcShield = LOTRShields.ALIGNMENT_NEAR_HARAD;
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
        int i;
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(3) == 0) {
            i = this.rand.nextInt(weaponsBronze.length);
            this.npcItemsInv.setMeleeWeapon(weaponsBronze[i].copy());
            if(this.rand.nextInt(5) == 0) {
                this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
            }
        }
        else {
            i = this.rand.nextInt(weaponsIron.length);
            this.npcItemsInv.setMeleeWeapon(weaponsIron[i].copy());
            if(this.rand.nextInt(5) == 0) {
                this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
                this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
            }
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        if(this.rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else if(this.rand.nextInt(5) == 0) {
            ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            int robeColor = turbanColors[this.rand.nextInt(turbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetNearHarad));
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
                return "nearHarad/coast/warrior/hired";
            }
            return "nearHarad/coast/warrior/friendly";
        }
        return "nearHarad/coast/warrior/hostile";
    }
}
