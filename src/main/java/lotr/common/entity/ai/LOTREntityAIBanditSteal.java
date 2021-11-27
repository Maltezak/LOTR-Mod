package lotr.common.entity.ai;

import java.util.*;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.*;
import lotr.common.item.*;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;

public class LOTREntityAIBanditSteal
extends EntityAIBase {
    private IBandit theBandit;
    private LOTREntityNPC theBanditAsNPC;
    private EntityPlayer targetPlayer;
    private EntityPlayer prevTargetPlayer;
    private double speed;
    private int chaseTimer;
    private int rePathDelay;

    public LOTREntityAIBanditSteal(IBandit bandit, double d) {
        this.theBandit = bandit;
        this.theBanditAsNPC = this.theBandit.getBanditAsNPC();
        this.speed = d;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        if (!this.theBandit.getBanditInventory().isEmpty()) {
            return false;
        }
        double range = 32.0;
        List players = this.theBanditAsNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.theBanditAsNPC.boundingBox.expand(range, range, range));
        ArrayList<EntityPlayer> validTargets = new ArrayList<>();
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)players.get(i);
            if (entityplayer.capabilities.isCreativeMode || !this.theBandit.canTargetPlayerForTheft(entityplayer) || !IBandit.Helper.canStealFromPlayerInv(this.theBandit, entityplayer)) continue;
            validTargets.add(entityplayer);
        }
        if (validTargets.isEmpty()) {
            return false;
        }
        this.targetPlayer = validTargets.get(this.theBanditAsNPC.getRNG().nextInt(validTargets.size()));
        if (this.targetPlayer != this.prevTargetPlayer) {
            this.theBanditAsNPC.sendSpeechBank(this.targetPlayer, this.theBandit.getTheftSpeechBank(this.targetPlayer));
        }
        return true;
    }

    public void startExecuting() {
        this.chaseTimer = 600;
    }

    public void updateTask() {
        --this.chaseTimer;
        this.theBanditAsNPC.getLookHelper().setLookPositionWithEntity(this.targetPlayer, 30.0f, 30.0f);
        --this.rePathDelay;
        if (this.rePathDelay <= 0) {
            this.rePathDelay = 10;
            this.theBanditAsNPC.getNavigator().tryMoveToEntityLiving(this.targetPlayer, this.speed);
        }
        if (this.theBanditAsNPC.getDistanceSqToEntity(this.targetPlayer) <= 2.0) {
            this.chaseTimer = 0;
            this.steal();
        }
    }

    public boolean continueExecuting() {
        if (this.targetPlayer == null || !this.targetPlayer.isEntityAlive() || this.targetPlayer.capabilities.isCreativeMode || !IBandit.Helper.canStealFromPlayerInv(this.theBandit, this.targetPlayer)) {
            return false;
        }
        return this.chaseTimer > 0 && this.theBanditAsNPC.getDistanceSqToEntity(this.targetPlayer) < 256.0;
    }

    public void resetTask() {
        this.chaseTimer = 0;
        this.rePathDelay = 0;
        if (this.targetPlayer != null) {
            this.prevTargetPlayer = this.targetPlayer;
        }
        this.targetPlayer = null;
    }

    private void steal() {
        InventoryPlayer inv = this.targetPlayer.inventory;
        int thefts = MathHelper.getRandomIntegerInRange(this.theBanditAsNPC.getRNG(), 1, this.theBandit.getMaxThefts());
        boolean stolenSomething = false;
        for (int i = 0; i < thefts; ++i) {
            if (this.tryStealItem(inv, LOTRItemCoin.class)) {
                stolenSomething = true;
            }
            if (this.tryStealItem(inv, LOTRItemGem.class)) {
                stolenSomething = true;
                continue;
            }
            if (this.tryStealItem(inv, LOTRValuableItems.getToolMaterials())) {
                stolenSomething = true;
                continue;
            }
            if (this.tryStealItem(inv, LOTRItemRing.class)) {
                stolenSomething = true;
                continue;
            }
            if (this.tryStealItem(inv, ItemArmor.class)) {
                stolenSomething = true;
                continue;
            }
            if (this.tryStealItem(inv, ItemSword.class)) {
                stolenSomething = true;
                continue;
            }
            if (this.tryStealItem(inv, ItemTool.class)) {
                stolenSomething = true;
                continue;
            }
            if (this.tryStealItem(inv, LOTRItemPouch.class)) {
                stolenSomething = true;
                continue;
            }
            if (!this.tryStealItem(inv)) continue;
            stolenSomething = true;
        }
        if (stolenSomething) {
            this.targetPlayer.addChatMessage(this.theBandit.getTheftChatMsg(this.targetPlayer));
            this.theBanditAsNPC.playSound("mob.horse.leather", 0.5f, 1.0f);
            if (this.theBanditAsNPC.getAttackTarget() != null) {
                this.theBanditAsNPC.setAttackTarget(null);
            }
            LOTRLevelData.getData(this.targetPlayer).cancelFastTravel();
        }
    }

    private boolean tryStealItem(InventoryPlayer inv, final Class itemclass) {
        return this.tryStealItem_do(inv, new BanditItemFilter(){

            @Override
            public boolean isApplicable(ItemStack itemstack) {
                return itemclass.isAssignableFrom(itemstack.getItem().getClass());
            }
        });
    }

    private boolean tryStealItem(InventoryPlayer inv, final List<ItemStack> itemList) {
        return this.tryStealItem_do(inv, new BanditItemFilter(){

            @Override
            public boolean isApplicable(ItemStack itemstack) {
                for (ItemStack listItem : itemList) {
                    if (!LOTRRecipes.checkItemEquals(listItem, itemstack)) continue;
                    return true;
                }
                return false;
            }
        });
    }

    private boolean tryStealItem(InventoryPlayer inv) {
        return this.tryStealItem_do(inv, new BanditItemFilter(){

            @Override
            public boolean isApplicable(ItemStack itemstack) {
                return true;
            }
        });
    }

    private boolean tryStealItem_do(InventoryPlayer inv, BanditItemFilter filter) {
        Integer[] inventorySlots = new Integer[inv.mainInventory.length];
        for (int l = 0; l < inventorySlots.length; ++l) {
            inventorySlots[l] = l;
        }
        List<Integer> slotsAsList = Arrays.asList(inventorySlots);
        Collections.shuffle(slotsAsList);
        Integer[] arrinteger = inventorySlots = slotsAsList.toArray(inventorySlots);
        int n = arrinteger.length;
        for (int i = 0; i < n; ++i) {
            ItemStack itemstack;
            int slot = arrinteger[i];
            if (slot == inv.currentItem || (itemstack = inv.getStackInSlot(slot)) == null || !filter.isApplicable(itemstack) || !this.stealItem(inv, slot)) continue;
            return true;
        }
        return false;
    }

    private int getRandomTheftAmount(ItemStack itemstack) {
        return MathHelper.getRandomIntegerInRange(this.theBanditAsNPC.getRNG(), 1, 8);
    }

    private boolean stealItem(InventoryPlayer inv, int slot) {
        ItemStack playerItem = inv.getStackInSlot(slot);
        int theft = this.getRandomTheftAmount(playerItem);
        if (theft > playerItem.stackSize) {
            theft = playerItem.stackSize;
        }
        int banditSlot = 0;
        while (this.theBandit.getBanditInventory().getStackInSlot(banditSlot) != null) {
            if (++banditSlot < this.theBandit.getBanditInventory().getSizeInventory()) continue;
            return false;
        }
        ItemStack stolenItem = playerItem.copy();
        stolenItem.stackSize = theft;
        this.theBandit.getBanditInventory().setInventorySlotContents(banditSlot, stolenItem);
        playerItem.stackSize -= theft;
        if (playerItem.stackSize <= 0) {
            inv.setInventorySlotContents(slot, null);
        }
        this.theBanditAsNPC.isNPCPersistent = true;
        return true;
    }

    private abstract class BanditItemFilter {
        private BanditItemFilter() {
        }

        public abstract boolean isApplicable(ItemStack var1);
    }

}

