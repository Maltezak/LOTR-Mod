package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class LOTRBlockDoor extends BlockDoor {
    public LOTRBlockDoor() {
        this(Material.wood);
        this.setStepSound(Block.soundTypeWood);
        this.setHardness(3.0f);
    }

    public LOTRBlockDoor(Material material) {
        super(material);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return (i & 8) != 0 ? null : Item.getItemFromBlock(this);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public Item getItem(World world, int i, int j, int k) {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public String getItemIconName() {
        return this.getTextureName();
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getDoorRenderID();
    }
}
