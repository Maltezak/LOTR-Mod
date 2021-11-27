package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockWoodBase extends BlockLog {
    @SideOnly(value = Side.CLIENT)
    private IIcon[][] woodIcons;
    private String[] woodNames;

    public LOTRBlockWoodBase() {
        this.setHardness(2.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    public void setWoodNames(String... s) {
        this.woodNames = s;
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        int j1 = j & 0xC;
        if((j &= 3) >= this.woodNames.length) {
            j = 0;
        }
        if(j1 == 0 && (i == 0 || i == 1) || j1 == 4 && (i == 4 || i == 5) || j1 == 8 && (i == 2 || i == 3)) {
            return this.woodIcons[j][0];
        }
        return this.woodIcons[j][1];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.woodIcons = new IIcon[this.woodNames.length][2];
        for(int i = 0; i < this.woodNames.length; ++i) {
            this.woodIcons[i][0] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_top");
            this.woodIcons[i][1] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_side");
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.woodNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
