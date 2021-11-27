package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeHooks;

public abstract class LOTREntityTree extends LOTREntityNPC {
    public static Block[] WOOD_BLOCKS = new Block[] {Blocks.log, LOTRMod.wood2, Blocks.log};
    public static Block[] LEAF_BLOCKS = new Block[] {Blocks.leaves, LOTRMod.leaves2, Blocks.leaves};
    public static Block[] SAPLING_BLOCKS = new Block[] {Blocks.sapling, LOTRMod.sapling2, Blocks.sapling};
    public static int[] WOOD_META = new int[] {0, 1, 2};
    public static int[] LEAF_META = new int[] {0, 1, 2};
    public static int[] SAPLING_META = new int[] {0, 1, 2};
    public static String[] TYPES = new String[] {"oak", "beech", "birch"};

    public LOTREntityTree(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
        if(this.rand.nextInt(9) == 0) {
            this.setTreeType(2);
        }
        else if(this.rand.nextInt(3) == 0) {
            this.setTreeType(1);
        }
        else {
            this.setTreeType(0);
        }
    }

    public int getTreeType() {
        byte i = this.dataWatcher.getWatchableObjectByte(16);
        if(i < 0 || i >= TYPES.length) {
            i = 0;
        }
        return i;
    }

    public void setTreeType(int i) {
        this.dataWatcher.updateObject(16, (byte) i);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("EntType", (byte) this.getTreeType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setTreeType(nbt.getByte("EntType"));
    }

    @Override
    public void setAttackTarget(EntityLivingBase target, boolean speak) {
        if (target instanceof LOTREntityTree) {
            return;
        }
        super.setAttackTarget(target, speak);
    }

    @Override
    public void knockBack(Entity entity, float f, double d, double d1) {
        super.knockBack(entity, f, d, d1);
        this.motionX /= 2.0;
        this.motionY /= 2.0;
        this.motionZ /= 2.0;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if(this.doTreeDamageCalculation() && !this.isTreeEffectiveDamage(damagesource)) {
            f /= 3.0f;
        }
        return super.attackEntityFrom(damagesource, f);
    }

    protected boolean doTreeDamageCalculation() {
        return true;
    }

    protected final boolean isTreeEffectiveDamage(DamageSource damagesource) {
        ItemStack itemstack;
        if(damagesource.isFireDamage()) {
            return true;
        }
        return damagesource.getEntity() instanceof EntityLivingBase && damagesource.getSourceOfDamage() == damagesource.getEntity() && (itemstack = ((EntityLivingBase) damagesource.getEntity()).getHeldItem()) != null && ForgeHooks.canToolHarvestBlock(Blocks.log, 0, itemstack);
    }

    @Override
    public void addPotionEffect(PotionEffect effect) {
        if(effect.getPotionID() == Potion.poison.id) {
            return;
        }
        super.addPotionEffect(effect);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int logs = MathHelper.getRandomIntegerInRange(this.rand, 3, 10) + this.rand.nextInt(4 * (i + 1));
        for(int l = 0; l < logs; ++l) {
            int treeType = this.getTreeType();
            this.entityDropItem(new ItemStack(WOOD_BLOCKS[treeType], 1, WOOD_META[treeType]), 0.0f);
        }
        int sticks = MathHelper.getRandomIntegerInRange(this.rand, 6, 16) + this.rand.nextInt(5 * (i + 1));
        for(int l = 0; l < sticks; ++l) {
            this.dropItem(Items.stick, 1);
        }
    }

    @Override
    public boolean canDropRares() {
        return false;
    }

    @Override
    public boolean getCanSpawnHere() {
        if(super.getCanSpawnHere()) {
            if(this.liftSpawnRestrictions) {
                return true;
            }
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.boundingBox.minY);
            int k = MathHelper.floor_double(this.posZ);
            Block block = this.worldObj.getBlock(i, j - 1, k);
            this.worldObj.getBlockMetadata(i, j - 1, k);
            return j > 62 && (block == Blocks.grass || block == Blocks.dirt);
        }
        return false;
    }

    @Override
    public float getBlockPathWeight(int i, int j, int k) {
        float f = 0.0f;
        BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(i, k);
        if(this.isTreeHomeBiome(biome)) {
            f += 20.0f;
        }
        return f;
    }

    protected boolean isTreeHomeBiome(BiomeGenBase biome) {
        return biome instanceof LOTRBiomeGenFangorn;
    }

    @Override
    public boolean canReEquipHired(int slot, ItemStack itemstack) {
        return false;
    }
}
