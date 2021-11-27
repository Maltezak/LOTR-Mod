package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public abstract class LOTRBlockPillarBase extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] pillarFaceIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] pillarSideIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] pillarSideTopIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] pillarSideMiddleIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] pillarSideBottomIcons;
    private String[] pillarNames;

    public LOTRBlockPillarBase() {
        this(Material.rock);
        this.setHardness(1.5f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    public LOTRBlockPillarBase(Material material) {
        super(material);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    protected void setPillarNames(String... names) {
        this.pillarNames = names;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        boolean pillarAbove = this.isPillarAt(world, i, j + 1, k);
        boolean pillarBelow = this.isPillarAt(world, i, j - 1, k);
        int meta = world.getBlockMetadata(i, j, k);
        if(meta >= this.pillarNames.length) {
            meta = 0;
        }
        if(side == 0 || side == 1) {
            return this.pillarFaceIcons[meta];
        }
        if(pillarAbove && pillarBelow) {
            return this.pillarSideMiddleIcons[meta];
        }
        if(pillarAbove) {
            return this.pillarSideBottomIcons[meta];
        }
        if(pillarBelow) {
            return this.pillarSideTopIcons[meta];
        }
        return this.pillarSideIcons[meta];
    }

    private boolean isPillarAt(IBlockAccess world, int i, int j, int k) {
        Block block = world.getBlock(i, j, k);
        return block instanceof LOTRBlockPillarBase;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.pillarNames.length) {
            j = 0;
        }
        if(i == 0 || i == 1) {
            return this.pillarFaceIcons[j];
        }
        return this.pillarSideIcons[j];
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.pillarNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.pillarFaceIcons = new IIcon[this.pillarNames.length];
        this.pillarSideIcons = new IIcon[this.pillarNames.length];
        this.pillarSideTopIcons = new IIcon[this.pillarNames.length];
        this.pillarSideMiddleIcons = new IIcon[this.pillarNames.length];
        this.pillarSideBottomIcons = new IIcon[this.pillarNames.length];
        for(int i = 0; i < this.pillarNames.length; ++i) {
            String s = this.getTextureName() + "_" + this.pillarNames[i];
            this.pillarFaceIcons[i] = iconregister.registerIcon(s + "_face");
            this.pillarSideIcons[i] = iconregister.registerIcon(s + "_side");
            this.pillarSideTopIcons[i] = iconregister.registerIcon(s + "_sideTop");
            this.pillarSideMiddleIcons[i] = iconregister.registerIcon(s + "_sideMiddle");
            this.pillarSideBottomIcons[i] = iconregister.registerIcon(s + "_sideBottom");
        }
    }
}
