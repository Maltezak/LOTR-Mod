package lotr.common.entity.npc;

import com.google.common.base.Predicate;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.inventory.LOTRInventoryNPC;
import lotr.common.item.*;
import lotr.common.quest.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityRuffianSpy
extends LOTREntityBreeRuffian
implements IBandit {
    private LOTRInventoryNPC ruffianInventory = IBandit.Helper.createInv(this);
    private EntityPlayer playerToRob;

    public LOTREntityRuffianSpy(World world) {
        super(world);
        this.questInfo.setBountyHelpPredicate(new Predicate<EntityPlayer>(){

            public boolean apply(EntityPlayer player) {
                ItemStack itemstack = player.getHeldItem();
                if (LOTRItemCoin.getStackValue(itemstack, true) > 0) {
                    return true;
                }
                if (itemstack != null) {
                    Item item = itemstack.getItem();
                    return item == Items.gold_ingot || item == LOTRMod.silver || item instanceof LOTRItemGem || item instanceof LOTRItemRing;
                }
                return false;
            }
        });
        this.questInfo.setBountyHelpConsumer(new Predicate<EntityPlayer>(){

            public boolean apply(EntityPlayer player) {
                ItemStack itemstack;
                if (!player.capabilities.isCreativeMode && (itemstack = player.getHeldItem()) != null) {
                    --itemstack.stackSize;
                    if (itemstack.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    }
                }
                LOTREntityRuffianSpy.this.playTradeSound();
                return true;
            }
        });
        this.questInfo.setActiveBountySelector(new MiniQuestSelector.BountyActiveAnyFaction());
    }

    @Override
    protected int addBreeAttackAI(int prio) {
        this.tasks.addTask(prio, new LOTREntityAIBanditSteal(this, 1.6));
        this.tasks.addTask(++prio, new LOTREntityAIAttackOnCollide(this, 1.4, false));
        this.tasks.addTask(++prio, new LOTREntityAIBanditFlee(this, 1.4));
        return prio;
    }

    @Override
    public LOTREntityNPC getBanditAsNPC() {
        return this;
    }

    @Override
    public int getMaxThefts() {
        return 1;
    }

    @Override
    public LOTRInventoryNPC getBanditInventory() {
        return this.ruffianInventory;
    }

    @Override
    public boolean canTargetPlayerForTheft(EntityPlayer player) {
        return player == this.playerToRob || this.canRuffianTarget(player);
    }

    @Override
    public String getTheftSpeechBank(EntityPlayer player) {
        return "bree/ruffian/hostile";
    }

    @Override
    public IChatComponent getTheftChatMsg(EntityPlayer player) {
        return new ChatComponentTranslation("chat.lotr.ruffianSteal");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        this.ruffianInventory.writeToNBT(nbt);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.ruffianInventory.readFromNBT(nbt);
    }

    @Override
    public void setAttackTarget(EntityLivingBase target, boolean speak) {
        if (target instanceof EntityPlayer && !((EntityPlayer)target).capabilities.isCreativeMode) {
            this.playerToRob = (EntityPlayer)target;
        }
        super.setAttackTarget(target, speak);
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!this.worldObj.isRemote) {
            this.ruffianInventory.dropAllItems();
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killRuffianSpy;
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.RUFFIAN_SPY.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.RUFFIAN_SPY;
    }

}

