package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorRenegade extends LOTREntityGondorSoldier {
    private static ItemStack[] weaponsUmbar = new ItemStack[] {new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.scimitarNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.poleaxeNearHarad), new ItemStack(LOTRMod.maceNearHarad), new ItemStack(LOTRMod.pikeNearHarad)};

    public LOTREntityGondorRenegade(World world) {
        super(world);
        this.npcShield = null;
        this.spawnRidingHorse = false;
        this.questInfo.setOfferChance(4000);
        this.questInfo.setMinAlignment(50.0f);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(weaponsUmbar.length);
        this.npcItemsInv.setMeleeWeapon(weaponsUmbar[i].copy());
        this.npcItemsInv.setMeleeWeaponMounted(this.npcItemsInv.getMeleeWeapon());
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearNearHarad));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setIdleItemMounted(this.npcItemsInv.getMeleeWeaponMounted());
        if(this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsPelargir));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsPelargir));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyPelargir));
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsGondor));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsGondor));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyGondor));
            this.setCurrentItemOrArmor(4, null);
        }
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.NEAR_HARAD;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "nearHarad/renegade/hired";
            }
            return "nearHarad/renegade/friendly";
        }
        return "nearHarad/renegade/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GONDOR_RENEGADE.createQuest(this);
    }
}
