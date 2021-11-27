package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.block.LOTRBlockPortal;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class LOTRTileEntityPortalBase extends TileEntity {
    @Override
    public void updateEntity() {
        if(!this.worldObj.isRemote) {
            LOTRBlockPortal portalBlock = (LOTRBlockPortal) this.getBlockType();
            for(int i1 = this.xCoord - 1; i1 <= this.xCoord + 1; ++i1) {
                for(int k1 = this.zCoord - 1; k1 <= this.zCoord + 1; ++k1) {
                    if(!portalBlock.isValidPortalLocation(this.worldObj, i1, this.yCoord, k1, true)) continue;
                    return;
                }
            }
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(this.xCoord - 1, this.yCoord, this.zCoord - 1, this.xCoord + 2, this.yCoord + 2, this.zCoord + 2);
    }
}
