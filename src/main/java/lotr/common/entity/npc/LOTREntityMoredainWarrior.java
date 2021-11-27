package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainWarrior extends LOTREntityMoredain {
    private static ItemStack[] weaponsMoredain = new ItemStack[] {new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.battleaxeMoredain), new ItemStack(LOTRMod.daggerMoredain), new ItemStack(LOTRMod.daggerMoredainPoisoned), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.clubMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.spearMoredain), new ItemStack(LOTRMod.swordMoredain), new ItemStack(LOTRMod.swordMoredain)};
    private static ItemStack[] weaponsIron = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.spearNearHarad)};
    private static ItemStack[] weaponsBronze = new ItemStack[] {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.spearHarad)};

    public LOTREntityMoredainWarrior(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
        this.spawnRidingHorse = this.rand.nextInt(10) == 0;
    }

    @Override
    protected EntityAIBase createHaradrimAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, true);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextInt(5) == 0) {
            if(this.rand.nextBoolean()) {
                int i = this.rand.nextInt(weaponsIron.length);
                this.npcItemsInv.setMeleeWeapon(weaponsIron[i].copy());
            }
            else {
                int i = this.rand.nextInt(weaponsBronze.length);
                this.npcItemsInv.setMeleeWeapon(weaponsBronze[i].copy());
            }
        }
        else {
            int i = this.rand.nextInt(weaponsMoredain.length);
            this.npcItemsInv.setMeleeWeapon(weaponsMoredain[i].copy());
        }
        if(this.rand.nextInt(3) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearMoredain));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsMoredain));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsMoredain));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyMoredain));
        if(this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetMoredain));
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
