package lotr.common.entity.item;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.LOTRBannerProtectable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityStoneTroll extends Entity implements LOTRBannerProtectable {
    private float trollHealth = 40.0f;
    public boolean placedByPlayer;
    private int entityAge;

    public LOTREntityStoneTroll(World world) {
        super(world);
        this.setSize(1.6f, 3.2f);
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte) 0);
        this.dataWatcher.addObject(17, (byte) 0);
    }

    public int getTrollOutfit() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void setTrollOutfit(int i) {
        this.dataWatcher.updateObject(16, (byte) i);
    }

    public boolean hasTwoHeads() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void setHasTwoHeads(boolean flag) {
        this.dataWatcher.updateObject(17, flag ? (byte) 1 : 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setFloat("TrollHealth", this.trollHealth);
        nbt.setByte("TrollOutfit", (byte) this.getTrollOutfit());
        nbt.setBoolean("PlacedByPlayer", this.placedByPlayer);
        nbt.setBoolean("TwoHeads", this.hasTwoHeads());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        this.trollHealth = nbt.getFloat("TrollHealth");
        this.setTrollOutfit(nbt.getByte("TrollOutfit"));
        this.placedByPlayer = nbt.getBoolean("PlacedByPlayer");
        this.setHasTwoHeads(nbt.getBoolean("TwoHeads"));
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033;
        this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98f;
        if(this.onGround) {
            f = 0.58800006f;
            Block i = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
            if(i.getMaterial() != Material.air) {
                f = i.slipperiness * 0.98f;
            }
        }
        this.motionX *= f;
        this.motionY *= 0.98;
        this.motionZ *= f;
        if(this.onGround) {
            this.motionY *= -0.5;
        }
        if(!this.worldObj.isRemote && !this.placedByPlayer) {
            ++this.entityAge;
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, -1.0);
            if(entityplayer != null) {
                double d = entityplayer.posX - this.posX;
                double d1 = entityplayer.posY - this.posY;
                double d2 = entityplayer.posZ - this.posZ;
                double distanceSq = d * d + d1 * d1 + d2 * d2;
                if(distanceSq > 16384.0) {
                    this.setDead();
                }
                if(this.entityAge > 600 && this.rand.nextInt(800) == 0 && distanceSq > 1024.0) {
                    this.setDead();
                }
                else if(distanceSq < 1024.0) {
                    this.entityAge = 0;
                }
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if(!this.worldObj.isRemote && !this.isDead) {
            EntityPlayer entityplayer;
            if(this.placedByPlayer) {
                if(damagesource.getSourceOfDamage() instanceof EntityPlayer) {
                    this.worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
                    this.worldObj.setEntityState(this, (byte) 17);
                    this.setDead();
                    EntityPlayer entityplayer2 = (EntityPlayer) damagesource.getSourceOfDamage();
                    if(!entityplayer2.capabilities.isCreativeMode) {
                        this.dropAsStatue();
                    }
                    return true;
                }
                return false;
            }
            boolean drops = true;
            boolean dropStatue = false;
            if(damagesource.getSourceOfDamage() instanceof EntityPlayer) {
                entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();
                if(entityplayer.capabilities.isCreativeMode) {
                    drops = false;
                    f = this.trollHealth;
                }
                else {
                    drops = true;
                    ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                    if(itemstack != null && itemstack.getItem() instanceof ItemPickaxe) {
                        dropStatue = true;
                        f = 1.0f + (float) entityplayer.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
                    }
                    else {
                        dropStatue = false;
                        f = 1.0f;
                    }
                    if(itemstack != null) {
                        itemstack.damageItem(1, entityplayer);
                        if(itemstack.stackSize <= 0) {
                            entityplayer.destroyCurrentEquippedItem();
                        }
                    }
                }
            }
            this.trollHealth -= f;
            if(this.trollHealth <= 0.0f) {
                this.worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.8f);
                this.worldObj.setEntityState(this, (byte) 17);
                if(drops) {
                    if(dropStatue) {
                        if(damagesource.getSourceOfDamage() instanceof EntityPlayer) {
                            entityplayer = (EntityPlayer) damagesource.getSourceOfDamage();
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.getTrollStatue);
                        }
                        this.dropAsStatue();
                    }
                    else {
                        int stone = 6 + this.rand.nextInt(7);
                        for(int l = 0; l < stone; ++l) {
                            this.dropItem(Item.getItemFromBlock(Blocks.cobblestone), 1);
                        }
                    }
                }
                this.setDead();
            }
            else {
                this.worldObj.playSoundAtEntity(this, Blocks.stone.stepSound.getBreakSound(), (Blocks.stone.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.stone.stepSound.getPitch() * 0.5f);
                this.worldObj.setEntityState(this, (byte) 16);
            }
            return true;
        }
        return false;
    }

    private ItemStack getStatueItem() {
        ItemStack itemstack = new ItemStack(LOTRMod.trollStatue);
        itemstack.setItemDamage(this.getTrollOutfit());
        itemstack.setTagCompound(new NBTTagCompound());
        itemstack.getTagCompound().setBoolean("TwoHeads", this.hasTwoHeads());
        return itemstack;
    }

    private void dropAsStatue() {
        this.entityDropItem(this.getStatueItem(), 0.0f);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 16) {
            for(int l = 0; l < 16; ++l) {
                this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
            }
        }
        else if(b == 17) {
            for(int l = 0; l < 64; ++l) {
                this.worldObj.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone) + "_0", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return this.getStatueItem();
    }
}
