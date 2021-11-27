package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.world.LOTRWorldProviderUtumno;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockUtumnoBrick
extends Block
implements LOTRWorldProviderUtumno.UtumnoBlock {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] brickIcons;
    @SideOnly(value=Side.CLIENT)
    private IIcon iceGlowingTop;
    @SideOnly(value=Side.CLIENT)
    private IIcon fireTileSide;
    private String[] brickNames = new String[]{"fire", "burning", "ice", "iceGlowing", "obsidian", "obsidianFire", "iceTile", "obsidianTile", "fireTile"};

    public LOTRBlockUtumnoBrick() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.5f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta >= this.brickNames.length) {
            meta = 0;
        }
        if (meta == 3 && side == 1) {
            return this.iceGlowingTop;
        }
        if (meta == 8 && side != 1 && side != 0) {
            return this.fireTileSide;
        }
        return this.brickIcons[meta];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.brickIcons = new IIcon[this.brickNames.length];
        for (int i = 0; i < this.brickNames.length; ++i) {
            String subName = this.getTextureName() + "_" + this.brickNames[i];
            this.brickIcons[i] = iconregister.registerIcon(subName);
            if (i == 3) {
                this.iceGlowingTop = iconregister.registerIcon(subName + "_top");
            }
            if (i != 8) continue;
            this.fireTileSide = iconregister.registerIcon(subName + "_side");
        }
    }

    public int damageDropped(int i) {
        return i;
    }

    public int getLightValue(IBlockAccess world, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k);
        if (meta == 1 || meta == 3 || meta == 5) {
            return 12;
        }
        return super.getLightValue(world, i, j, k);
    }

    public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
        return this.isFlammable(world, i, j, k, side);
    }

    public boolean isFlammable(IBlockAccess world, int i, int j, int k, ForgeDirection side) {
        int meta = world.getBlockMetadata(i, j, k);
        if (meta == 0 || meta == 1) {
            return true;
        }
        return super.isFlammable(world, i, j, k, side);
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < this.brickNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}

