package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.quest.*;
import lotr.common.world.biome.LOTRBiomeGenRivendell;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityRivendellElf extends LOTREntityHighElfBase {
    public LOTREntityRivendellElf(World world) {
        super(world);
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorRivendell));
        return horse;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerRivendell));
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.rivendellBow));
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killRivendellElf;
    }

    @Override
    protected void dropElfItems(boolean flag, int i) {
        super.dropElfItems(flag, i);
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.RIVENDELL_HALL, 1, 1 + i);
        }
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenRivendell) {
            f += 20.0f;
        }
        return f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "rivendell/elf/hired";
            }
            return "rivendell/elf/friendly";
        }
        return "rivendell/elf/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.RIVENDELL.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.RIVENDELL;
    }
}
