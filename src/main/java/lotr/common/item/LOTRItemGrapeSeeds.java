package lotr.common.item;

import lotr.common.*;
import lotr.common.block.LOTRBlockGrapevine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;

public class LOTRItemGrapeSeeds extends Item implements IPlantable {
    private Block grapevineBlock;

    public LOTRItemGrapeSeeds(Block block) {
        this.grapevineBlock = block;
        this.setCreativeTab(LOTRCreativeTabs.tabMaterials);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        if(entityplayer.canPlayerEdit(i, j, k, side, itemstack) && (world.getBlock(i, j, k)) == LOTRMod.grapevine && LOTRBlockGrapevine.canPlantGrapesAt(world, i, j, k, this)) {
            world.setBlock(i, j, k, this.grapevineBlock, 0, 3);
            --itemstack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int i, int j, int k) {
        return this.grapevineBlock;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
        return 0;
    }
}
