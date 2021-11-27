package lotr.common.entity.animal;

import lotr.common.*;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCamel extends LOTREntityHorse implements LOTRBiomeGenNearHarad.ImmuneToHeat {
    public LOTREntityCamel(World world) {
        super(world);
        this.setSize(1.6f, 1.8f);
    }

    @Override
    public int getHorseType() {
        return this.worldObj.isRemote ? 0 : 1;
    }

    @Override
    protected void onLOTRHorseSpawn() {
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength *= 0.5);
    }

    @Override
    protected double clampChildHealth(double health) {
        return MathHelper.clamp_double(health, 12.0, 36.0);
    }

    @Override
    protected double clampChildJump(double jump) {
        return MathHelper.clamp_double(jump, 0.1, 0.6);
    }

    @Override
    protected double clampChildSpeed(double speed) {
        return MathHelper.clamp_double(speed, 0.1, 0.35);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.wheat;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer && this.isMountSaddled()) {
            LOTRLevelData.getData((EntityPlayer) this.riddenByEntity).addAchievement(LOTRAchievement.rideCamel);
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int hides = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < hides; ++l) {
            this.dropItem(Items.leather, 1);
        }
        int meat = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < meat; ++l) {
            if(this.isBurning()) {
                this.dropItem(LOTRMod.camelCooked, 1);
                continue;
            }
            this.dropItem(LOTRMod.camelRaw, 1);
        }
    }

    @Override
    public boolean func_110259_cr() {
        return true;
    }

    @Override
    public boolean isMountArmorValid(ItemStack itemstack) {
        if(itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet)) {
            return true;
        }
        return super.isMountArmorValid(itemstack);
    }

    public boolean isCamelWearingCarpet() {
        ItemStack itemstack = this.getMountArmor();
        return itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet);
    }

    public int getCamelCarpetColor() {
        ItemStack itemstack = this.getMountArmor();
        if(itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.carpet)) {
            int meta = itemstack.getItemDamage();
            int dyeMeta = BlockColored.func_150031_c(meta);
            int[] colors = ItemDye.field_150922_c;
            dyeMeta = MathHelper.clamp_int(dyeMeta, 0, colors.length);
            return colors[dyeMeta];
        }
        return -1;
    }

    public void setNomadChestAndCarpet() {
        this.setChestedForWorldGen();
        ItemStack carpet = new ItemStack(Blocks.carpet, 1, this.rand.nextInt(16));
        this.setMountArmor(carpet);
    }
}
