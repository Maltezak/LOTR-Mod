package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockYamCrop extends BlockCrops {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] yamIcons;

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        if(world.getBlockMetadata(i, j, k) == 8) {
            return world.getBlock(i, j - 1, k).canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, Blocks.tallgrass);
        }
        return super.canBlockStay(world, i, j, k);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j < 7) {
            if(j == 6) {
                j = 5;
            }
            return this.yamIcons[j >> 1];
        }
        return this.yamIcons[3];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.yamIcons = new IIcon[4];
        for(int i = 0; i < this.yamIcons.length; ++i) {
            this.yamIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }

    @Override
    public Item func_149866_i() {
        return LOTRMod.yam;
    }

    @Override
    public Item func_149865_P() {
        return LOTRMod.yam;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
        return EnumPlantType.Crop;
    }
}
