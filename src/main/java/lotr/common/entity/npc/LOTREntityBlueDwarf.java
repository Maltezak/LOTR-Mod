package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityBlueDwarf extends LOTREntityDwarf {
    public LOTREntityBlueDwarf(World world) {
        super(world);
        this.familyInfo.marriageEntityClass = LOTREntityBlueDwarf.class;
        this.familyInfo.marriageAchievement = LOTRAchievement.marryBlueDwarf;
    }

    @Override
    protected LOTRFoods getDwarfFoods() {
        return LOTRFoods.BLUE_DWARF;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBlueDwarven));
        this.npcItemsInv.setIdleItem(null);
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.BLUE_MOUNTAINS;
    }

    @Override
    protected Item getDwarfSteelDrop() {
        return LOTRMod.blueDwarfSteel;
    }

    @Override
    protected LOTRChestContents getLarderDrops() {
        return LOTRChestContents.BLUE_DWARF_HOUSE_LARDER;
    }

    @Override
    protected LOTRChestContents getGenericDrops() {
        return LOTRChestContents.BLUE_MOUNTAINS_STRONGHOLD;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBlueDwarf;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "blueDwarf/dwarf/hired";
            }
            return this.isChild() ? "blueDwarf/child/friendly" : "blueDwarf/dwarf/friendly";
        }
        return this.isChild() ? "blueDwarf/child/hostile" : "blueDwarf/dwarf/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.BLUE_MOUNTAINS.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.BLUE_MOUNTAINS;
    }
}
