package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockHearth extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] blockIcons;

    public LOTRBlockHearth() {
        super(Material.rock);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 0) {
            return this.blockIcons[0];
        }
        if(i == 1) {
            return this.blockIcons[1];
        }
        return this.blockIcons[2];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.blockIcons = new IIcon[3];
        this.blockIcons[0] = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.blockIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.blockIcons[2] = iconregister.registerIcon(this.getTextureName() + "_side");
    }

    @Override
    public boolean isFireSource(World world, int i, int j, int k, ForgeDirection side) {
        return side == ForgeDirection.UP;
    }

    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if(world.getBlock(i, j + 1, k) == Blocks.fire) {
            int smokeHeight = 5;
            for(int j1 = j + 1; j1 <= j + smokeHeight && !world.getBlock(i, j1, k).getMaterial().isSolid(); ++j1) {
                for(int l = 0; l < 3; ++l) {
                    float f = i + random.nextFloat();
                    float f1 = j1 + random.nextFloat();
                    float f2 = k + random.nextFloat();
                    world.spawnParticle("largesmoke", f, f1, f2, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}
