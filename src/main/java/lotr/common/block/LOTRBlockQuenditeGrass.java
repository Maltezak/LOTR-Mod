package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockQuenditeGrass extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon grassSideIcon;

    public LOTRBlockQuenditeGrass() {
        super(Material.grass);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 0) {
            return Blocks.dirt.getIcon(i, j);
        }
        if(i == 1) {
            return this.blockIcon;
        }
        return this.grassSideIcon;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.blockIcon = iconregister.registerIcon("lotr:quenditeGrass_top");
        this.grassSideIcon = iconregister.registerIcon("lotr:quenditeGrass_side");
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(Blocks.dirt);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if(random.nextInt(8) == 0) {
            double d = i + random.nextFloat();
            double d1 = j + 1.0;
            double d2 = k + random.nextFloat();
            LOTRMod.proxy.spawnParticle("quenditeSmoke", d, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}
