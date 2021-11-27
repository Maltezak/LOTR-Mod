package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class LOTRBlockLettuceCrop extends BlockCrops {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] lettuceIcons;

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j < 7) {
            if(j == 6) {
                j = 5;
            }
            return this.lettuceIcons[j >> 1];
        }
        return this.lettuceIcons[3];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.lettuceIcons = new IIcon[4];
        for(int i = 0; i < this.lettuceIcons.length; ++i) {
            this.lettuceIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }

    @Override
    public Item func_149866_i() {
        return LOTRMod.lettuce;
    }

    @Override
    public Item func_149865_P() {
        return LOTRMod.lettuce;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
        return EnumPlantType.Crop;
    }

    @Override
    public int getRenderType() {
        return 1;
    }
}
