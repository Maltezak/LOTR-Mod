package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockDwarvenTable
extends LOTRBlockCraftingTable {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] tableIcons;

    public LOTRBlockDwarvenTable() {
        super(Material.rock, LOTRFaction.DURINS_FOLK, 4);
        this.setStepSound(Block.soundTypeStone);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        if (i == 1) {
            return this.tableIcons[2];
        }
        if (i == 0) {
            return LOTRMod.brick.getIcon(0, 6);
        }
        return i == 4 || i == 5 ? this.tableIcons[0] : this.tableIcons[1];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.tableIcons = new IIcon[3];
        this.tableIcons[0] = iconregister.registerIcon(this.getTextureName() + "_side0");
        this.tableIcons[1] = iconregister.registerIcon(this.getTextureName() + "_side1");
        this.tableIcons[2] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}

