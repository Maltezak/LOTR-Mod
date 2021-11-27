package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRBlockOrcPlating
extends Block {
    @SideOnly(value=Side.CLIENT)
    protected IIcon[] blockIcons;
    protected String[] blockNames = new String[]{"iron", "rust"};

    public LOTRBlockOrcPlating() {
        super(Material.iron);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(3.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeMetal);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        if (j >= this.blockNames.length) {
            j = 0;
        }
        return this.blockIcons[j];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.blockIcons = new IIcon[this.blockNames.length];
        for (int i = 0; i < this.blockNames.length; ++i) {
            this.blockIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.blockNames[i]);
        }
    }

    public int damageDropped(int i) {
        return i;
    }

    public int getRenderType() {
        return LOTRMod.proxy.getOrcPlatingRenderID();
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < this.blockNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}

