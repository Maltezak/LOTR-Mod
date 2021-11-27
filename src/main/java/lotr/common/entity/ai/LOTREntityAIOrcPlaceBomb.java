package lotr.common.entity.ai;

import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;

public class LOTREntityAIOrcPlaceBomb extends EntityAIBase {
    private World worldObj;
    private LOTREntityOrc attacker;
    private EntityLivingBase entityTarget;
    private double moveSpeed;
    private PathEntity entityPathEntity;
    private int rePathDelay;

    public LOTREntityAIOrcPlaceBomb(LOTREntityOrc entity, double speed) {
        this.attacker = entity;
        this.worldObj = entity.worldObj;
        this.moveSpeed = speed;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityLivingBase entity = this.attacker.getAttackTarget();
        if(entity == null) {
            return false;
        }
        if(this.attacker.npcItemsInv.getBomb() == null) {
            return false;
        }
        this.entityTarget = entity;
        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(this.entityTarget);
        return this.entityPathEntity != null;
    }

    @Override
    public boolean continueExecuting() {
        if(this.attacker.npcItemsInv.getBomb() == null) {
            return false;
        }
        EntityLivingBase entity = this.attacker.getAttackTarget();
        return entity == null ? false : (!this.entityTarget.isEntityAlive() ? false : !this.attacker.getNavigator().noPath());
    }

    @Override
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.moveSpeed);
        this.rePathDelay = 0;
    }

    @Override
    public void resetTask() {
        this.entityTarget = null;
        this.attacker.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask() {
        this.attacker.getLookHelper().setLookPositionWithEntity(this.entityTarget, 30.0f, 30.0f);
        if(this.attacker.getEntitySenses().canSee(this.entityTarget) && --this.rePathDelay <= 0) {
            this.rePathDelay = 4 + this.attacker.getRNG().nextInt(7);
            this.attacker.getNavigator().tryMoveToEntityLiving(this.entityTarget, this.moveSpeed);
        }
        if(this.attacker.getDistanceToEntity(this.entityTarget) < 3.0) {
            LOTREntityOrcBomb bomb = new LOTREntityOrcBomb(this.worldObj, this.attacker.posX, this.attacker.posY + 1.0, this.attacker.posZ, this.attacker);
            int meta = 0;
            ItemStack bombItem = this.attacker.npcItemsInv.getBomb();
            if(bombItem != null && Block.getBlockFromItem(bombItem.getItem()) instanceof LOTRBlockOrcBomb) {
                meta = bombItem.getItemDamage();
            }
            bomb.setBombStrengthLevel(meta);
            bomb.setFuseFromHiredUnit();
            bomb.droppedByHiredUnit = this.attacker.hiredNPCInfo.isActive;
            bomb.droppedTargetingPlayer = this.entityTarget instanceof EntityPlayer;
            this.worldObj.spawnEntityInWorld(bomb);
            this.worldObj.playSoundAtEntity(this.attacker, "game.tnt.primed", 1.0f, 1.0f);
            this.worldObj.playSoundAtEntity(this.attacker, "lotr:orc.fire", 1.0f, (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f + 1.0f);
            this.attacker.npcItemsInv.setBomb(null);
            int bombSlot = 5;
            if(this.attacker.hiredReplacedInv.hasReplacedEquipment(bombSlot)) {
                this.attacker.hiredReplacedInv.onEquipmentChanged(bombSlot, null);
            }
            this.attacker.refreshCurrentAttackMode();
        }
    }
}
