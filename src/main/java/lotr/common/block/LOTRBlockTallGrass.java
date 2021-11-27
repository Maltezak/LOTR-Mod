package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRDamage;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class LOTRBlockTallGrass extends LOTRBlockGrass {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] grassIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] overlayIcons;
    public static String[] grassNames = new String[] {"short", "flower", "wheat", "thistle", "nettle", "fernsprout"};
    public static boolean[] grassOverlay = new boolean[] {false, true, true, true, false, false};

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        int meta = world.getBlockMetadata(i, j, k);
        if(meta == 3 && entity.isSprinting() || meta == 4 && entity instanceof EntityPlayer) {
            EntityLivingBase living;
            boolean bootsLegs = false;
            if(entity instanceof EntityLivingBase && (living = (EntityLivingBase) entity).getEquipmentInSlot(1) != null && living.getEquipmentInSlot(2) != null) {
                bootsLegs = true;
            }
            if(!bootsLegs) {
                entity.attackEntityFrom(LOTRDamage.plantHurt, 0.25f);
            }
        }
    }

    @Override
    public ArrayList getDrops(World world, int i, int j, int k, int meta, int fortune) {
        if(meta == 3) {
            ArrayList<ItemStack> thistles = new ArrayList<>();
            thistles.add(new ItemStack(this, 1, 3));
            return thistles;
        }
        return super.getDrops(world, i, j, k, meta, fortune);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getBlockColor() {
        return Blocks.tallgrass.getBlockColor();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getRenderColor(int meta) {
        return this.getBlockColor();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
        return world.getBiomeGenForCoords(i, k).getBiomeGrassColor(i, j, k);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= grassNames.length) {
            j = 0;
        }
        if(i == -1) {
            return this.overlayIcons[j];
        }
        return this.grassIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.grassIcons = new IIcon[grassNames.length];
        this.overlayIcons = new IIcon[grassNames.length];
        for(int i = 0; i < grassNames.length; ++i) {
            this.grassIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + grassNames[i]);
            if(!grassOverlay[i]) continue;
            this.overlayIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + grassNames[i] + "_overlay");
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int j = 0; j < grassNames.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
