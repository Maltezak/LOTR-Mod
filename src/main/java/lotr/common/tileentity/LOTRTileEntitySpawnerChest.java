package lotr.common.tileentity;

import lotr.common.entity.LOTREntities;
import net.minecraft.entity.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;

public class LOTRTileEntitySpawnerChest extends TileEntityChest {
    private String entityClassName = "";

    public void setMobID(Class entityClass) {
        this.entityClassName = LOTREntities.getStringFromClass(entityClass);
    }

    public Entity createMob() {
        return EntityList.createEntityByName(this.entityClassName, this.worldObj);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.entityClassName = nbt.getString("MobID");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("MobID", this.entityClassName);
    }
}
