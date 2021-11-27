package lotr.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRTileEntityGulduril extends TileEntity {
    public int ticksExisted;

    @Override
    public void setWorldObj(World world) {
        super.setWorldObj(world);
        this.ticksExisted = world.rand.nextInt(200);
    }

    @Override
    public void updateEntity() {
        ++this.ticksExisted;
    }
}
