package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockBrick
extends LOTRBlockBrickBase
implements LOTRConnectedBlock {
    @SideOnly(value=Side.CLIENT)
    private IIcon iconRohanSide;

    public LOTRBlockBrick() {
        this.setBrickNames("mordor", "gondor", "gondorMossy", "gondorCracked", "rohan", "gondorCarved", "dwarven", "mordorCracked", "dwarvenSilver", "dwarvenGold", "dwarvenMithril", "galadhrim", "galadhrimMossy", "galadhrimCracked", "blueRock", "nearHarad");
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.brickIcons = new IIcon[this.brickNames.length];
        for (int i = 0; i < this.brickNames.length; ++i) {
            if (i == 8 || i == 9 || i == 10) {
                LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
                continue;
            }
            this.brickIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.brickNames[i]);
            if (i != 4) continue;
            this.iconRohanSide = iconregister.registerIcon(this.getTextureName() + "_" + this.brickNames[i] + "_side");
        }
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        int meta = world.getBlockMetadata(i, j, k);
        if (meta == 8 || meta == 9 || meta == 10) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
        }
        return super.getIcon(world, i, j, k, side);
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if (j == 8 || j == 9 || j == 10) {
            return LOTRConnectedTextures.getConnectedIconItem(this, j);
        }
        if (j == 4 && i != 0 && i != 1) {
            return this.iconRohanSide;
        }
        return super.getIcon(i, j);
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

