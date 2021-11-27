package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockFence extends BlockFence {
    private Block plankBlock;

    public LOTRBlockFence(Block planks) {
        super("", Material.wood);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.plankBlock = planks;
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public int getRenderType() {
        if(LOTRMod.proxy.isClient()) {
            return LOTRMod.proxy.getFenceRenderID();
        }
        return super.getRenderType();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return this.plankBlock.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        ArrayList plankTypes = new ArrayList();
        this.plankBlock.getSubBlocks(Item.getItemFromBlock(this.plankBlock), this.plankBlock.getCreativeTabToDisplayOn(), plankTypes);
        for(Object plankType : plankTypes) {
            Object obj = plankType;
            if(!(obj instanceof ItemStack)) continue;
            int meta = ((ItemStack) obj).getItemDamage();
            list.add(new ItemStack(this, 1, meta));
        }
    }
}
