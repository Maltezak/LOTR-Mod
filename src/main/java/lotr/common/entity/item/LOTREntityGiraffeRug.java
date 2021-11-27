package lotr.common.entity.item;

import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGiraffeRug extends LOTREntityRugBase {
    public LOTREntityGiraffeRug(World world) {
        super(world);
        this.setSize(2.0f, 0.3f);
    }

    @Override
    protected String getRugNoise() {
        return "";
    }

    @Override
    protected ItemStack getRugItem() {
        return new ItemStack(LOTRMod.giraffeRug);
    }
}
