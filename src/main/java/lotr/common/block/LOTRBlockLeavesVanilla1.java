package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.*;

public class LOTRBlockLeavesVanilla1 extends LOTRBlockLeavesBase {
    public LOTRBlockLeavesVanilla1() {
        super(true, "lotr:leavesV1");
        this.setLeafNames("oak", "spruce", "birch", "jungle");
        this.setSeasonal(true, false, true, false);
    }

    @Override
    public String[] func_150125_e() {
        return BlockOldLeaf.field_150131_O;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int getRenderColor(int i) {
        int meta = i & 3;
        if(meta == 0) {
            return ColorizerFoliage.getFoliageColorBasic();
        }
        return super.getRenderColor(i);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k) & 3;
        if(meta == 0) {
            return LOTRBlockLeavesBase.getBiomeLeafColor(world, i, j, k);
        }
        return super.colorMultiplier(world, i, j, k);
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(Blocks.sapling);
    }

    @Override
    protected int getSaplingChance(int meta) {
        if(meta == 3) {
            return 30;
        }
        return super.getSaplingChance(meta);
    }
}
