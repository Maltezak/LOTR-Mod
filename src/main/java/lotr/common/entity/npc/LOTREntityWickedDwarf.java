package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityWickedDwarf
extends LOTREntityDwarf
implements LOTRTradeable.Smith {
    private static ItemStack[] wickedWeapons = new ItemStack[]{new ItemStack(LOTRMod.swordDwarven), new ItemStack(LOTRMod.battleaxeDwarven), new ItemStack(LOTRMod.hammerDwarven)};

    public static LOTRFaction[] getTradeFactions() {
        return new LOTRFaction[]{LOTRFaction.MORDOR, LOTRFaction.ANGMAR, LOTRFaction.RHUDEL};
    }

    public LOTREntityWickedDwarf(World world) {
        super(world);
        this.addTargetTasks(true);
    }

    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.WICKED_DWARF_BUY;
    }

    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.WICKED_DWARF_SELL;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(wickedWeapons.length);
        this.npcItemsInv.setMeleeWeapon(wickedWeapons[i].copy());
        this.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.pickaxeDwarven));
        if (this.rand.nextInt(4) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDwarven));
            this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDwarven));
            this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDwarven));
        } else {
            this.setCurrentItemOrArmor(1, null);
            this.setCurrentItemOrArmor(2, null);
            this.setCurrentItemOrArmor(3, null);
        }
        this.setCurrentItemOrArmor(4, null);
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killWickedDwarf;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public boolean canTradeWith(EntityPlayer entityplayer) {
        boolean hasSuitableAlignment = false;
        for (LOTRFaction f : LOTREntityWickedDwarf.getTradeFactions()) {
            if ((LOTRLevelData.getData(entityplayer).getAlignment(f) < 100.0f)) continue;
            hasSuitableAlignment = true;
            break;
        }
        return hasSuitableAlignment && this.isFriendly(entityplayer);
    }

    @Override
    public void onPlayerTrade(EntityPlayer entityplayer, LOTRTradeEntries.TradeType type, ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeWickedDwarf);
    }

    @Override
    protected boolean canDwarfSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return j > 62 && this.worldObj.getBlock(i, j - 1, k) == this.worldObj.getBiomeGenForCoords(i, k).topBlock;
    }

    @Override
    protected boolean canDwarfSpawnAboveGround() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            if (this.canTradeWith(entityplayer)) {
                return "dwarf/wicked/friendly";
            }
            return "dwarf/wicked/neutral";
        }
        return "dwarf/wicked/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return null;
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return null;
    }
}

