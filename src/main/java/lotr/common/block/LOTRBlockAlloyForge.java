package lotr.common.block;

import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityAlloyForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockAlloyForge extends LOTRBlockForgeBase {
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityAlloyForge();
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        if(!world.isRemote) {
            entityplayer.openGui(LOTRMod.instance, 5, world, i, j, k);
        }
        return true;
    }

    @Override
    protected boolean useLargeSmoke() {
        return true;
    }
}
