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

public abstract class LOTRBlockBrickBase extends Block {
    @SideOnly(value = Side.CLIENT)
    protected IIcon[] brickIcons;
    protected String[] brickNames;

    public LOTRBlockBrickBase() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.5f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    protected void setBrickNames(String... names) {
        this.brickNames = names;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.brickNames.length) {
            j = 0;
        }
        return this.brickIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.brickIcons = new IIcon[this.brickNames.length];
        for(int i = 0; i < this.brickNames.length; ++i) {
            this.brickIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.brickNames[i]);
        }
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.brickNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
