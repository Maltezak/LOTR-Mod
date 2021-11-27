package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMoredainMercenary extends LOTREntityMoredain implements LOTRMercenary {
    private static ItemStack[] weaponsIron = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};
    private static ItemStack[] weaponsBronze = new ItemStack[] {new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.swordHarad), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned), new ItemStack(LOTRMod.pikeHarad)};
    private static int[] turbanColors = new int[] {10487808, 5976610, 14864579, 10852752, 11498561, 12361037};

    public LOTREntityMoredainMercenary(World world) {
        super(world);
        this.npcShield = LOTRShields.ALIGNMENT_MOREDAIN;
        this.spawnRidingHorse = false;
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
    public LOTRFaction getFaction() {
        return LOTRFaction.NEAR_HARAD;
    }

    @Override
    public LOTRFaction getHiringFaction() {
        return LOTRFaction.NEAR_HARAD;
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
        if(this.rand.nextInt(8) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGulfHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGulfHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGulfHarad));
        }
        else if(this.rand.nextInt(5) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsHarnedor));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsHarnedor));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyHarnedor));
        }
        else if(this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsUmbar));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsUmbar));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyUmbar));
        }
        else {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsNearHarad));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsNearHarad));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyNearHarad));
        }
        if(this.rand.nextInt(10) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            int robeColor = turbanColors[this.rand.nextInt(turbanColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            this.setCurrentItemOrArmor(4, turban);
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public int getMercBaseCost() {
        return 20;
    }

    @Override
    public float getMercAlignmentRequired() {
        return 0.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 0.0f && this.isFriendly(entityplayer);
    }

    @Override
    public void onUnitTrade(EntityPlayer entityplayer) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.hireMoredainMercenary);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "nearHarad/mercenary/hired";
            }
            return "nearHarad/mercenary/friendly";
        }
        return "nearHarad/mercenary/hostile";
    }
}
