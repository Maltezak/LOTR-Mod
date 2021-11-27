package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class LOTRBlockRock
extends Block {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] rockIcons;
    @SideOnly(value=Side.CLIENT)
    private IIcon iconMordorSide;
    @SideOnly(value=Side.CLIENT)
    private IIcon iconMordorMoss;
    private String[] rockNames = new String[]{"mordor", "gondor", "rohan", "blue", "red", "chalk"};

    public LOTRBlockRock() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(1.5f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
    }

    public boolean isReplaceableOreGen(World world, int i, int j, int k, Block target) {
        if (target == this) {
            return world.getBlockMetadata(i, j, k) == 0;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.rockIcons = new IIcon[this.rockNames.length];
        for (int i = 0; i < this.rockNames.length; ++i) {
            String subName = this.getTextureName() + "_" + this.rockNames[i];
            this.rockIcons[i] = iconregister.registerIcon(subName);
            if (i != 0) continue;
            this.iconMordorSide = iconregister.registerIcon(subName + "_side");
            this.iconMordorMoss = iconregister.registerIcon(subName + "_moss");
        }
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int i, int j, int k, int side) {
        int meta = world.getBlockMetadata(i, j, k);
        if (meta == 0 && side != 1 && side != 0 && (world.getBlock(i, j + 1, k)) == LOTRMod.mordorMoss) {
            return this.iconMordorMoss;
        }
        return super.getIcon(world, i, j, k, side);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta >= this.rockNames.length) {
            meta = 0;
        }
        if (meta == 0 && side != 1 && side != 0) {
            return this.iconMordorSide;
        }
        return this.rockIcons[meta];
    }

    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < this.rockNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if (world.getBlock(i, j, k) == this && world.getBlockMetadata(i, j, k) == 0 && random.nextInt(10) == 0) {
            world.spawnParticle("smoke", i + random.nextFloat(), j + 1.1f, k + random.nextFloat(), 0.0, 0.0, 0.0);
        }
    }
}

