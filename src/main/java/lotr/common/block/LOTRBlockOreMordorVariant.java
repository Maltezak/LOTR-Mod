package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockOreMordorVariant extends LOTRBlockOre {
    @SideOnly(value = Side.CLIENT)
    private IIcon mordorIcon;
    private boolean dropsBlock;

    public LOTRBlockOreMordorVariant(boolean flag) {
        this.dropsBlock = flag;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j == 1) {
            return this.mordorIcon;
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.mordorIcon = iconregister.registerIcon(this.getTextureName() + "_mordor");
    }

    @Override
    public int damageDropped(int i) {
        if(this.dropsBlock) {
            return i;
        }
        return super.damageDropped(i);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int j = 0; j <= 1; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer entityplayer) {
        return new ItemStack(this, 1, world.getBlockMetadata(i, j, k));
    }
}
