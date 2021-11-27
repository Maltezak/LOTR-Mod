package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;

public class LOTRBlockBirdCage extends LOTRBlockAnimalJar {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] sideIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] topIcons;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] baseIcons;
    private String[] cageTypes;

    public LOTRBlockBirdCage() {
        super(Material.glass);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeMetal);
        this.setCageTypes("bronze", "iron", "silver", "gold");
    }

    protected void setCageTypes(String... s) {
        this.cageTypes = s;
    }

    @Override
    public boolean canCapture(Entity entity) {
        return entity instanceof LOTREntityBird;
    }

    @Override
    public float getJarEntityHeight() {
        return 0.5f;
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return true;
    }

    public static boolean isSameBirdCage(IBlockAccess world, int i, int j, int k, int i1, int j1, int k1) {
        Block block = world.getBlock(i, j, k);
        int meta = world.getBlockMetadata(i, j, k);
        Block block1 = world.getBlock(i1, j1, k1);
        int meta1 = world.getBlockMetadata(i1, j1, k1);
        return block instanceof LOTRBlockBirdCage && block == block1 && meta == meta1;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.cageTypes.length) {
            j = 0;
        }
        if(i == 0 || i == 1) {
            return this.topIcons[j];
        }
        if(i == -1) {
            return this.baseIcons[j];
        }
        return this.sideIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.sideIcons = new IIcon[this.cageTypes.length];
        this.topIcons = new IIcon[this.cageTypes.length];
        this.baseIcons = new IIcon[this.cageTypes.length];
        for(int i = 0; i < this.cageTypes.length; ++i) {
            this.sideIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.cageTypes[i] + "_side");
            this.topIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.cageTypes[i] + "_top");
            this.baseIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.cageTypes[i] + "_base");
        }
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getBirdCageRenderID();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.cageTypes.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
