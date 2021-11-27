package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemDart;
import net.minecraft.block.*;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.*;

public class LOTRTileEntityDartTrap extends TileEntityDispenser {
    private int fireCooldown;

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.field_146020_a : "container.lotr.dartTrap";
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!this.worldObj.isRemote) {
            if(this.fireCooldown > 0) {
                --this.fireCooldown;
            }
            else {
                ItemStack itemstack;
                int slot = this.func_146017_i();
                if(slot >= 0 && (itemstack = this.getStackInSlot(slot)).getItem() instanceof LOTRItemDart && !(this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.getTriggerRange(), LOTRMod.selectLivingExceptCreativePlayers())).isEmpty()) {
                    IBehaviorDispenseItem dispense = (IBehaviorDispenseItem) BlockDispenser.dispenseBehaviorRegistry.getObject(itemstack.getItem());
                    ItemStack result = dispense.dispense(new BlockSourceImpl(this.worldObj, this.xCoord, this.yCoord, this.zCoord), itemstack);
                    this.setInventorySlotContents(slot, result.stackSize == 0 ? null : result);
                    this.fireCooldown = 20;
                }
            }
        }
    }

    public AxisAlignedBB getTriggerRange() {
        Vec3 vecTarget;
        new BlockSourceImpl(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        EnumFacing facing = BlockDispenser.func_149937_b(this.getBlockMetadata());
        float front = 0.55f;
        float range = 16.0f;
        Vec3 vecPos = Vec3.createVectorHelper(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5);
        Vec3 vecFront = vecPos.addVector(facing.getFrontOffsetX() * front, facing.getFrontOffsetY() * front, facing.getFrontOffsetZ() * front);
        MovingObjectPosition hitBlock = this.worldObj.func_147447_a(vecFront, vecTarget = vecPos.addVector(facing.getFrontOffsetX() * range, facing.getFrontOffsetY() * range, facing.getFrontOffsetZ() * range), true, true, false);
        if(hitBlock != null) {
            vecTarget = Vec3.createVectorHelper(hitBlock.blockX + 0.5 - facing.getFrontOffsetX(), hitBlock.blockY + 0.5 - facing.getFrontOffsetY(), hitBlock.blockZ + 0.5 - facing.getFrontOffsetZ());
        }
        float f = 0.0f;
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.xCoord + f, this.yCoord + f, this.zCoord + f, this.xCoord + 1 - f, this.yCoord + 1 - f, this.zCoord + 1 - f);
        bb = bb.addCoord(vecTarget.xCoord - vecPos.xCoord, vecTarget.yCoord - vecPos.yCoord, vecTarget.zCoord - vecPos.zCoord);
        return bb;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return this.getTriggerRange();
    }
}
