package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.block.LOTRBlockPlate;
import lotr.common.dispenser.LOTRDispensePlate;
import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemPlate extends ItemReed {
    public Block plateBlock;

    public LOTRItemPlate(Block block) {
        super(block);
        this.plateBlock = block;
        ((LOTRBlockPlate) this.plateBlock).setPlateItem(this);
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispensePlate(this.plateBlock));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        LOTREntityPlate plate = new LOTREntityPlate(world, this.plateBlock, entityplayer);
        world.playSoundAtEntity(entityplayer, "random.bow", 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.25f);
        if(!world.isRemote) {
            world.spawnEntityInWorld(plate);
        }
        if(!entityplayer.capabilities.isCreativeMode) {
            --itemstack.stackSize;
        }
        return itemstack;
    }

    @Override
    public boolean isValidArmor(ItemStack itemstack, int armorType, Entity entity) {
        return armorType == 0;
    }
}
