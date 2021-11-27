package lotr.common.entity.animal;

import lotr.common.*;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class LOTREntityGiraffe extends LOTREntityHorse {
    public LOTREntityGiraffe(World world) {
        super(world);
        this.setSize(1.7f, 4.0f);
    }

    @Override
    public int getHorseType() {
        return 0;
    }

    @Override
    protected void onLOTRHorseSpawn() {
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength *= 0.8);
    }

    @Override
    protected double clampChildHealth(double health) {
        return MathHelper.clamp_double(health, 12.0, 34.0);
    }

    @Override
    protected double clampChildJump(double jump) {
        return MathHelper.clamp_double(jump, 0.2, 1.0);
    }

    @Override
    protected double clampChildSpeed(double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.35);
    }

    @Override
    public boolean isBreedingItem(ItemStack itemstack) {
        return itemstack != null && Block.getBlockFromItem(itemstack.getItem()) instanceof BlockLeavesBase;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.riddenByEntity instanceof EntityPlayer && this.isMountSaddled()) {
            EntityPlayer entityplayer = (EntityPlayer) this.riddenByEntity;
            BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if(biome instanceof LOTRBiomeGenShire) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideGiraffeShire);
            }
        }
    }

    @Override
    protected Item getDropItem() {
        return Items.leather;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        if(flag) {
            int rugChance = 30 - i * 5;
            if(this.rand.nextInt(rugChance = Math.max(rugChance, 1)) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.giraffeRug), 0.0f);
            }
        }
    }
}
