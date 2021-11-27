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

public class LOTRBlockDirtPath extends Block {
    @SideOnly(value = Side.CLIENT)
    protected IIcon[] pathIcons;
    protected String[] pathNames = new String[] {"dirt", "mud"};

    public LOTRBlockDirtPath() {
        super(Material.ground);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGravel);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.pathNames.length) {
            j = 0;
        }
        return this.pathIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.pathIcons = new IIcon[this.pathNames.length];
        for(int i = 0; i < this.pathNames.length; ++i) {
            this.pathIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.pathNames[i]);
        }
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.pathNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
