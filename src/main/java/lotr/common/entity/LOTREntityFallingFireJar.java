package lotr.common.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class LOTREntityFallingFireJar extends EntityFallingBlock implements IEntityAdditionalSpawnData {
    public LOTREntityFallingFireJar(World world) {
        super(world);
    }

    public LOTREntityFallingFireJar(World world, double d, double d1, double d2, Block block) {
        super(world, d, d1, d2, block);
    }

    public LOTREntityFallingFireJar(World world, double d, double d1, double d2, Block block, int meta) {
        super(world, d, d1, d2, block, meta);
    }

    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeDouble(this.prevPosX);
        data.writeDouble(this.prevPosY);
        data.writeDouble(this.prevPosZ);
        data.writeInt(Block.getIdFromBlock(this.func_145805_f()));
        data.writeByte(this.field_145814_a);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        double x = data.readDouble();
        double y = data.readDouble();
        double z = data.readDouble();
        Block block = Block.getBlockById(data.readInt());
        byte meta = data.readByte();
        EntityFallingBlock proxy = new EntityFallingBlock(this.worldObj, x, y, z, block, meta);
        NBTTagCompound nbt = new NBTTagCompound();
        proxy.writeToNBT(nbt);
        this.readFromNBT(nbt);
    }
}
