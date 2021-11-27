package lotr.common.block;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTRBlockUtumnoBrickEntrance extends Block {
    public LOTRBlockUtumnoBrickEntrance() {
        super(Material.rock);
        this.setHardness(-1.0f);
        this.setResistance(Float.MAX_VALUE);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return LOTRMod.utumnoBrick.getIcon(i, 2);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(LOTRMod.utumnoBrick, 1, 2));
        return drops;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int i, int j, int k, EntityPlayer player) {
        return new ItemStack(LOTRMod.utumnoBrick, 1, 2);
    }
}
