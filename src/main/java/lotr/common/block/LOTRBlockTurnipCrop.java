package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class LOTRBlockTurnipCrop extends BlockCrops {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] turnipIcons;

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j < 7) {
            if(j == 6) {
                j = 5;
            }
            return this.turnipIcons[j >> 1];
        }
        return this.turnipIcons[3];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.turnipIcons = new IIcon[4];
        for(int i = 0; i < this.turnipIcons.length; ++i) {
            this.turnipIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + i);
        }
    }

    @Override
    public Item func_149866_i() {
        return LOTRMod.turnip;
    }

    @Override
    public Item func_149865_P() {
        return LOTRMod.turnip;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
        return EnumPlantType.Crop;
    }
}
