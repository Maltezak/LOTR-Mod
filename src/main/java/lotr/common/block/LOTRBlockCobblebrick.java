package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockCobblebrick
extends LOTRBlockBrickBase
implements LOTRConnectedBlock {
    public LOTRBlockCobblebrick() {
        this.setBrickNames("cob");
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.brickIcons = new IIcon[this.brickNames.length];
        for (int i = 0; i < this.brickNames.length; ++i) {
            LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        boolean[][] adjacentFlags = i == 0 || i == 1 ? new boolean[][]{{false, false, false}, {false, true, false}, {false, false, false}} : new boolean[][]{{false, true, false}, {false, true, false}, {false, true, false}};
        return LOTRConnectedTextures.getConnectedIconItem(this, j, adjacentFlags);
    }

    @Override
    public String getConnectedName(int meta) {
        return this.textureName + "_" + this.brickNames[meta];
    }

    @Override
    public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
        return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
    }
}

