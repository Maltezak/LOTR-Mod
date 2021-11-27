package lotr.common.block;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public abstract class LOTRBlockOreStorageBase extends Block {
    @SideOnly(value = Side.CLIENT)
    protected IIcon[] oreStorageIcons;
    protected String[] oreStorageNames;

    public LOTRBlockOreStorageBase() {
        super(Material.iron);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
        this.setHardness(5.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeMetal);
    }

    protected void setOreStorageNames(String... names) {
        this.oreStorageNames = names;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.oreStorageIcons = new IIcon[this.oreStorageNames.length];
        for(int i = 0; i < this.oreStorageNames.length; ++i) {
            this.oreStorageIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.oreStorageNames[i]);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.oreStorageNames.length) {
            j = 0;
        }
        return this.oreStorageIcons[j];
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.oreStorageNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public boolean isBeaconBase(IBlockAccess world, int i, int j, int k, int beaconX, int beaconY, int beaconZ) {
        return true;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
