package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.animal.LOTREntityTermite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class LOTRBlockTermite
extends Block {
    @SideOnly(value=Side.CLIENT)
    protected IIcon sideIcon;
    @SideOnly(value=Side.CLIENT)
    protected IIcon topIcon;

    public LOTRBlockTermite() {
        super(Material.ground);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(0.5f);
        this.setResistance(3.0f);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        if (i == 0 || i == 1) {
            return this.topIcon;
        }
        return this.sideIcon;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.topIcon = iconregister.registerIcon(this.getTextureName());
        this.sideIcon = iconregister.registerIcon(this.getTextureName() + "_side");
    }

    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int meta) {
        if (!world.isRemote && meta == 0 && world.rand.nextBoolean()) {
            int termites = 1 + world.rand.nextInt(3);
            for (int l = 0; l < termites; ++l) {
                this.spawnTermite(world, i, j, k);
            }
        }
    }

    public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
        int meta = world.getBlockMetadata(i, j, k);
        if (!world.isRemote && meta == 0 && world.rand.nextBoolean()) {
            this.spawnTermite(world, i, j, k);
        }
        super.onBlockExploded(world, i, j, k, explosion);
    }

    private void spawnTermite(World world, int i, int j, int k) {
        LOTREntityTermite termite = new LOTREntityTermite(world);
        termite.setLocationAndAngles(i + 0.5, j, k + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
        world.spawnEntityInWorld(termite);
    }

    public int damageDropped(int i) {
        return i;
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        return meta == 1 ? 1 : 0;
    }

    protected ItemStack createStackedBlock(int i) {
        return new ItemStack(this, 1, 1);
    }

    @SideOnly(value=Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i <= 1; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}

