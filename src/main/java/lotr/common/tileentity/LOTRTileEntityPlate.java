package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;
import lotr.common.entity.LOTRPlateFallingInfo;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.*;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class LOTRTileEntityPlate extends TileEntity {
    private ItemStack foodItem;
    public LOTRPlateFallingInfo plateFallInfo;

    public static boolean isValidFoodItem(ItemStack itemstack) {
        Item item;
        if(itemstack != null && (item = itemstack.getItem()) instanceof ItemFood) {
            if(item instanceof ItemSoup) {
                return false;
            }
            return !item.hasContainerItem(itemstack);
        }
        return false;
    }

    public ItemStack getFoodItem() {
        return this.foodItem;
    }

    public void setFoodItem(ItemStack item) {
        if(item != null && item.stackSize <= 0) {
            item = null;
        }
        this.foodItem = item;
        if(this.worldObj != null) {
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        this.markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("PlateEmpty", this.foodItem == null);
        if(this.foodItem != null) {
            nbt.setTag("FoodItem", this.foodItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if(nbt.hasKey("FoodID")) {
            Item item = Item.getItemById(nbt.getInteger("FoodID"));
            if(item != null) {
                int damage = nbt.getInteger("FoodDamage");
                this.foodItem = new ItemStack(item, 1, damage);
            }
        }
        else {
            boolean empty = nbt.getBoolean("PlateEmpty");
            this.foodItem = empty ? null : ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("FoodItem"));
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, data);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB bb = super.getRenderBoundingBox();
        if(this.foodItem != null) {
            bb = bb.addCoord(0.0, this.foodItem.stackSize * 0.03125f, 0.0);
        }
        return bb;
    }
}
