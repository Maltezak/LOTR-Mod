package lotr.common.entity.npc;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityNPCRideable extends LOTREntityNPC implements LOTRNPCMount {
    private UUID tamingPlayer;
    private int npcTemper;

    public LOTREntityNPCRideable(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public boolean isNPCTamed() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setNPCTamed(boolean flag) {
        this.dataWatcher.updateObject(17, (byte) (flag ? 1 : 0));
    }

    @Override
    public boolean isMountArmorValid(ItemStack itemstack) {
        if(itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            LOTRItemMountArmor armor = (LOTRItemMountArmor) itemstack.getItem();
            return armor.isValid(this);
        }
        return false;
    }

    public IInventory getMountInventory() {
        return null;
    }

    public void openGUI(EntityPlayer entityplayer) {
        IInventory inv = this.getMountInventory();
        if(inv != null && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityplayer) && this.isNPCTamed()) {
            entityplayer.openGui(LOTRMod.instance, 29, this.worldObj, this.getEntityId(), inv.getSizeInventory(), 0);
        }
    }

    public void tameNPC(EntityPlayer entityplayer) {
        this.setNPCTamed(true);
        this.tamingPlayer = entityplayer.getUniqueID();
    }

    public EntityPlayer getTamingPlayer() {
        return this.worldObj.func_152378_a(this.tamingPlayer);
    }

    @Override
    public boolean canDespawn() {
        return super.canDespawn() && !this.isNPCTamed();
    }

    @Override
    public boolean canRenameNPC() {
        return this.isNPCTamed() ? true : super.canRenameNPC();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        LOTRMountFunctions.update(this);
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        LOTRMountFunctions.move(this, strafe, forward);
    }

    @Override
    public void super_moveEntityWithHeading(float strafe, float forward) {
        super.moveEntityWithHeading(strafe, forward);
    }

    @Override
    public float getStepHeightWhileRiddenByPlayer() {
        return 1.0f;
    }

    @Override
    public final double getMountedYOffset() {
        double d = this.getBaseMountedYOffset();
        if(this.riddenByEntity != null) {
            d += this.riddenByEntity.yOffset - this.riddenByEntity.getYOffset();
        }
        return d;
    }

    protected double getBaseMountedYOffset() {
        return this.height * 0.5;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("NPCTamed", this.isNPCTamed());
        if(this.tamingPlayer != null) {
            nbt.setString("NPCTamer", this.tamingPlayer.toString());
        }
        nbt.setInteger("NPCTemper", this.npcTemper);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setNPCTamed(nbt.getBoolean("NPCTamed"));
        if(nbt.hasKey("NPCTamer")) {
            this.tamingPlayer = UUID.fromString(nbt.getString("NPCTamer"));
        }
        this.npcTemper = nbt.getInteger("NPCTemper");
    }

    public int getMaxNPCTemper() {
        return 100;
    }

    public int getNPCTemper() {
        return this.npcTemper;
    }

    public void setNPCTemper(int i) {
        this.npcTemper = i;
    }

    public int increaseNPCTemper(int i) {
        int temper = MathHelper.clamp_int(this.getNPCTemper() + i, 0, this.getMaxNPCTemper());
        this.setNPCTemper(temper);
        return this.getNPCTemper();
    }

    public void angerNPC() {
        this.playSound(this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch() * 1.5f);
    }
}
