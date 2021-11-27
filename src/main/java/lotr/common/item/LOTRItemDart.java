package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import lotr.common.dispenser.LOTRDispenseDart;
import lotr.common.entity.projectile.LOTREntityDart;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTRItemDart extends Item {
    public boolean isPoisoned = false;

    public LOTRItemDart() {
        this.setCreativeTab(LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new LOTRDispenseDart(this));
    }

    public LOTRItemDart setPoisoned() {
        this.isPoisoned = true;
        return this;
    }

    public LOTREntityDart createDart(World world, ItemStack itemstack, double d, double d1, double d2) {
        LOTREntityDart dart = new LOTREntityDart(world, itemstack, d, d1, d2);
        return dart;
    }

    public LOTREntityDart createDart(World world, EntityLivingBase entity, ItemStack itemstack, float charge) {
        LOTREntityDart dart = new LOTREntityDart(world, entity, itemstack, charge);
        return dart;
    }

    public LOTREntityDart createDart(World world, EntityLivingBase entity, EntityLivingBase target, ItemStack itemstack, float charge, float inaccuracy) {
        LOTREntityDart dart = new LOTREntityDart(world, entity, target, itemstack, charge, inaccuracy);
        return dart;
    }
}
