package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHarnedorWarrior extends LOTREntityHarnedhrim {
    private static ItemStack[] weaponsBronze = new ItemStack[] {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
    private static int[] turbanColors = new int[] {1643539, 6309443, 7014914, 7809314, 5978155};

    public LOTREntityHarnedorWarrior(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = this.rand.nextInt(8) == 0;
        this.npcShield = LOTRShields.ALIGNMENT_HARNEDOR;
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
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearHarad));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHarnedor));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHarnedor));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHarnedor));
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
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetHarnedor));
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
                return "nearHarad/harnennor/warrior/hired";
            }
            return "nearHarad/harnennor/warrior/friendly";
        }
        return "nearHarad/harnennor/warrior/hostile";
    }
}
