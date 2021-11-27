package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.client.render.LOTRConnectedTextures;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockOreStorage
extends LOTRBlockOreStorageBase
implements LOTRConnectedBlock {
    @SideOnly(value=Side.CLIENT)
    private IIcon orcSteelSideIcon;
    @SideOnly(value=Side.CLIENT)
    private IIcon urukSteelSideIcon;
    @SideOnly(value=Side.CLIENT)
    private IIcon morgulSteelSideIcon;

    public LOTRBlockOreStorage() {
        this.setOreStorageNames("copper", "tin", "bronze", "silver", "mithril", "orcSteel", "quendite", "dwarfSteel", "galvorn", "urukSteel", "naurite", "gulduril", "morgulSteel", "sulfur", "saltpeter", "blueDwarfSteel");
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.oreStorageIcons = new IIcon[this.oreStorageNames.length];
        for (int i = 0; i < this.oreStorageNames.length; ++i) {
            if (i == 4) {
                LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
                continue;
            }
            this.oreStorageIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.oreStorageNames[i]);
        }
        this.orcSteelSideIcon = iconregister.registerIcon(this.getTextureName() + "_orcSteel_side");
        this.urukSteelSideIcon = iconregister.registerIcon(this.getTextureName() + "_urukSteel_side");
        this.morgulSteelSideIcon = iconregister.registerIcon(this.getTextureName() + "_morgulSteel_side");
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        int meta = world.getBlockMetadata(i, j, k);
        if (meta == 4) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
        }
        return super.getIcon(world, i, j, k, side);
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        if (meta == 4) {
            return LOTRConnectedTextures.getConnectedIconItem(this, meta);
        }
        if (meta == 5 && side > 1) {
            return this.orcSteelSideIcon;
        }
        if (meta == 9 && side > 1) {
            return this.urukSteelSideIcon;
        }
        if (meta == 12 && side > 1) {
            return this.morgulSteelSideIcon;
        }
        return super.getIcon(side, meta);
    }

    @Override
    public String getConnectedName(int meta) {
        return this.textureName + "_" + this.oreStorageNames[meta];
    }

    @Override
    public boolean areBlocksConnected(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
        return LOTRConnectedBlock.Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
    }

    public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
        return world.getBlockMetadata(i, j, k) == 13 && side == ForgeDirection.UP;
    }

    public int getLightValue(IBlockAccess world, int i, int j, int k) {
        if (world.getBlockMetadata(i, j, k) == 6) {
            return LOTRMod.oreQuendite.getLightValue();
        }
        if (world.getBlockMetadata(i, j, k) == 10) {
            return LOTRMod.oreNaurite.getLightValue();
        }
        if (world.getBlockMetadata(i, j, k) == 11) {
            return LOTRMod.oreGulduril.getLightValue();
        }
        return 0;
    }

    public boolean hasTileEntity(int metadata) {
        return metadata == 11;
    }

    public TileEntity createTileEntity(World world, int metadata) {
        if (this.hasTileEntity(metadata)) {
            return new LOTRTileEntityGulduril();
        }
        return null;
    }
}

