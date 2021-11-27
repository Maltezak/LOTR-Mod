package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockStalactite extends Block {
    private Block modelBlock;
    private int modelMeta;

    public LOTRBlockStalactite(Block block, int meta) {
        super(block.getMaterial());
        this.modelBlock = block;
        this.modelMeta = meta;
        this.setStepSound(this.modelBlock.stepSound);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 1.0f, 0.75f);
    }

    @Override
    public float getBlockHardness(World world, int i, int j, int k) {
        return this.modelBlock.getBlockHardness(world, i, j, k);
    }

    @Override
    public float getExplosionResistance(Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return this.modelBlock.getIcon(i, this.modelMeta);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }

    @Override
    public int quantityDropped(Random random) {
        return this.modelBlock.quantityDropped(random);
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public boolean canSilkHarvest(World world, EntityPlayer entityplayer, int i, int j, int k, int meta) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return LOTRMod.proxy.getStalactiteRenderID();
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        int metadata = world.getBlockMetadata(i, j, k);
        if(metadata == 0) {
            return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
        }
        if(metadata == 1) {
            return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
        }
        return false;
    }

    @Override
    public boolean canReplace(World world, int i, int j, int k, int side, ItemStack itemstack) {
        int metadata = itemstack.getItemDamage();
        if(metadata == 0) {
            return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
        }
        if(metadata == 1) {
            return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if(!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int j = 0; j <= 1; ++j) {
            list.add(new ItemStack(item, 1, j));
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        Block above;
        if(random.nextInt(50) == 0 && world.getBlockMetadata(i, j, k) == 0 && (above = world.getBlock(i, j + 1, k)).isOpaqueCube() && above.getMaterial() == Material.rock) {
            world.spawnParticle("dripWater", i + 0.6, j, k + 0.6, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onFallenUpon(World world, int i, int j, int k, Entity entity, float fallDistance) {
        if(entity instanceof EntityLivingBase && world.getBlockMetadata(i, j, k) == 1) {
            int damage = (int) (fallDistance * 2.0f) + 1;
            entity.attackEntityFrom(DamageSource.fall, damage);
        }
    }
}
