package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockWoodBeam extends BlockRotatedPillar {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] sideIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] topIcons;
    private String[] woodNames;

    public LOTRBlockWoodBeam() {
        super(Material.wood);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(Block.soundTypeWood);
    }

    protected void setWoodNames(String... s) {
        this.woodNames = s;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.sideIcons = new IIcon[this.woodNames.length];
        this.topIcons = new IIcon[this.woodNames.length];
        for(int i = 0; i < this.woodNames.length; ++i) {
            this.topIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_top");
            this.sideIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_side");
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int j = 0; j < this.woodNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    protected IIcon getSideIcon(int i) {
        if(i < 0 || i >= this.woodNames.length) {
            i = 0;
        }
        return this.sideIcons[i];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    protected IIcon getTopIcon(int i) {
        if(i < 0 || i >= this.woodNames.length) {
            i = 0;
        }
        return this.topIcons[i];
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getBeamRenderID();
    }
}
