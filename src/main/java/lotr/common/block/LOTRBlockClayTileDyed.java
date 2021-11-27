package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;

public class LOTRBlockClayTileDyed extends LOTRBlockClayTile {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] clayIcons;

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.clayIcons = new IIcon[16];
        for(int i = 0; i < this.clayIcons.length; ++i) {
            int dyeMeta = BlockColored.func_150031_c(i);
            this.clayIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + ItemDye.field_150923_a[dyeMeta]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= 16) {
            j = 0;
        }
        return this.clayIcons[j];
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < 16; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
