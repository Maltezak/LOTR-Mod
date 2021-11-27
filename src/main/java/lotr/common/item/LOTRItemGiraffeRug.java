package lotr.common.item;

import lotr.common.entity.item.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRItemGiraffeRug extends LOTRItemRugBase {
    public LOTRItemGiraffeRug() {
        super("giraffe");
    }

    @Override
    protected LOTREntityRugBase createRug(World world, ItemStack itemstack) {
        LOTREntityGiraffeRug rug = new LOTREntityGiraffeRug(world);
        return rug;
    }
}
