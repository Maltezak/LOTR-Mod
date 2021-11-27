package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class LOTRBlockRivendellTable extends LOTRBlockCraftingTable {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] tableIcons;

    public LOTRBlockRivendellTable() {
        super(Material.wood, LOTRFaction.HIGH_ELF, 51);
        this.setStepSound(Block.soundTypeWood);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == 1) {
            return this.tableIcons[1];
        }
        if(i == 0) {
            return Blocks.planks.getIcon(2, 0);
        }
        return this.tableIcons[0];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.tableIcons = new IIcon[2];
        this.tableIcons[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.tableIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}
