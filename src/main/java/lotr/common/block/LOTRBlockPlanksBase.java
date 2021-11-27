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

public abstract class LOTRBlockPlanksBase extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] plankIcons;
    private String[] plankTypes;

    public LOTRBlockPlanksBase() {
        super(Material.wood);
        this.setHardness(2.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(LOTRCreativeTabs.tabBlock);
    }

    protected void setPlankTypes(String... types) {
        this.plankTypes = types;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.plankTypes.length) {
            j = 0;
        }
        return this.plankIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.plankIcons = new IIcon[this.plankTypes.length];
        for(int i = 0; i < this.plankTypes.length; ++i) {
            this.plankIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.plankTypes[i]);
        }
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int j = 0; j < this.plankTypes.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }
}
