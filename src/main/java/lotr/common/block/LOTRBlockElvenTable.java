package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class LOTRBlockElvenTable
extends LOTRBlockCraftingTable {
    @SideOnly(value=Side.CLIENT)
    private IIcon[] tableIcons;

    public LOTRBlockElvenTable() {
        super(Material.wood, LOTRFaction.LOTHLORIEN, 2);
        this.setStepSound(Block.soundTypeWood);
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        if (i == 1) {
            return this.tableIcons[1];
        }
        if (i == 0) {
            return LOTRMod.planks.getIcon(2, 1);
        }
        return this.tableIcons[0];
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.tableIcons = new IIcon[2];
        this.tableIcons[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.tableIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}

