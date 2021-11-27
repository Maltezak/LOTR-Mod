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

public class LOTRBlockThatch extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] thatchIcons;
    private static String[] thatchNames = new String[] {"thatch", "reed"};

    public LOTRBlockThatch() {
        super(Material.grass);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGrass);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= thatchNames.length) {
            j = 0;
        }
        return this.thatchIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.thatchIcons = new IIcon[thatchNames.length];
        for(int i = 0; i < thatchNames.length; ++i) {
            this.thatchIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + thatchNames[i]);
        }
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < thatchNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
