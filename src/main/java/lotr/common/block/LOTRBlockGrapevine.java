package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockGrapevine extends Block implements IPlantable, IGrowable {
    public final boolean hasGrapes;
    public static final int MAX_GROWTH = 7;
    public static final int MAX_HEIGHT = 3;
    public static boolean hoeing = false;
    @SideOnly(value = Side.CLIENT)
    private IIcon postIcon;
    @SideOnly(value = Side.CLIENT)
    private IIcon[] vineIcons;

    public LOTRBlockGrapevine(boolean grapes) {
        super(Material.plants);
        this.hasGrapes = grapes;
        if(!this.hasGrapes) {
            this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        }
        else {
            this.setCreativeTab(null);
        }
        if(this.hasGrapes) {
            this.setStepSound(Block.soundTypeGrass);
            this.setHardness(0.0f);
        }
        else {
            this.setStepSound(Block.soundTypeWood);
            this.setHardness(2.0f);
            this.setResistance(5.0f);
        }
        if(this.hasGrapes) {
            this.setTickRandomly(true);
        }
    }

    public Item getGrapeItem() {
        return null;
    }

    public Item getGrapeSeedsItem() {
        return null;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(i == -1) {
            if(j >= 7) {
                return this.vineIcons[1];
            }
            return this.vineIcons[0];
        }
        return this.postIcon;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.postIcon = !this.hasGrapes ? iconregister.registerIcon(this.getTextureName()) : LOTRMod.grapevine.getIcon(0, 0);
        if(this.hasGrapes) {
            this.vineIcons = new IIcon[2];
            this.vineIcons[0] = iconregister.registerIcon(this.getTextureName() + "_vine");
            this.vineIcons[1] = iconregister.registerIcon(this.getTextureName() + "_grapes");
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        this.setBlockBoundsBasedOnState(world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
        float f = 0.125f;
        if(this.hasGrapes) {
            float min = 0.1875f;
            float max = 0.375f;
            int meta = world.getBlockMetadata(i, j, k);
            float f1 = meta / 7.0f;
            f = min + (max - min) * f1;
        }
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        float f = 0.125f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
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
        if(this.hasGrapes) {
            return LOTRMod.proxy.getGrapevineRenderID();
        }
        return 0;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
        if(this.hasGrapes) {
            Block block = world.getBlock(i, j, k);
            int meta = world.getBlockMetadata(i, j, k);
            int i1 = i - Facing.offsetsXForSide[side];
            int j1 = j - Facing.offsetsYForSide[side];
            int k1 = k - Facing.offsetsZForSide[side];
            int metaThis = world.getBlockMetadata(i1, j1, k1);
            if(block instanceof LOTRBlockGrapevine && ((LOTRBlockGrapevine) block).hasGrapes && meta == metaThis && (side == 0 || side == 1)) {
                return false;
            }
        }
        return super.shouldSideBeRendered(world, i, j, k, side);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public Item getItem(World world, int i, int j, int k) {
        if(this.hasGrapes) {
            return this.getGrapeSeedsItem();
        }
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        Block below = world.getBlock(i, j - 1, k);
        return below.isSideSolid(world, i, j - 1, k, ForgeDirection.UP) || below.canSustainPlant(world, i, j, k, ForgeDirection.UP, this) || below instanceof LOTRBlockGrapevine;
    }

    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {
        return this.canPlaceBlockAt(world, i, j, k);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        this.checkCanStay(world, i, j, k);
    }

    private boolean checkCanStay(World world, int i, int j, int k) {
        if(!this.canBlockStay(world, i, j, k)) {
            int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            if(this.hasGrapes) {
                world.setBlock(i, j, k, LOTRMod.grapevine, 0, 3);
                Block newBlock = world.getBlock(i, j, k);
                newBlock.updateTick(world, i, j, k, world.rand);
            }
            else {
                world.setBlockToAir(i, j, k);
            }
            return false;
        }
        return true;
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        float growth;
        int meta;
        super.updateTick(world, i, j, k, random);
        if(!this.checkCanStay(world, i, j, k)) {
            return;
        }
        if(this.hasGrapes && world.getBlockLightValue(i, j + 1, k) >= 9 && (meta = world.getBlockMetadata(i, j, k)) < 7 && (growth = this.getGrowthFactor(world, i, j, k)) > 0.0f && random.nextInt((int) (80.0f / growth) + 1) == 0) {
            world.setBlockMetadataWithNotify(i, j, k, ++meta, 2);
        }
    }

    private float getGrowthFactor(World world, int i, int j, int k) {
        if(!LOTRBlockGrapevine.canPlantGrapesAt(world, i, j, k, this)) {
            return 0.0f;
        }
        int farmlandHeight = 0;
        for(int l = 1; l <= 3; ++l) {
            int j1 = j - l;
            Block block = world.getBlock(i, j1, k);
            if(!block.canSustainPlant(world, i, j1, k, ForgeDirection.UP, this)) continue;
            farmlandHeight = j1;
            break;
        }
        float growth = 1.0f;
        int range = 1;
        for(int i1 = -range; i1 <= range; ++i1) {
            for(int k1 = -range; k1 <= range; ++k1) {
                int i2 = i + i1;
                int k2 = k + k1;
                float f = 0.0f;
                Block block = world.getBlock(i2, farmlandHeight, k2);
                if(block.canSustainPlant(world, i2, farmlandHeight, k2, ForgeDirection.UP, this)) {
                    f = 1.0f;
                    if(block.isFertile(world, i2, farmlandHeight, k2)) {
                        f = 3.0f;
                    }
                }
                if(i1 != 0 || k1 != 0) {
                    f /= 4.0f;
                }
                growth += f;
            }
        }
        BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if(biome instanceof LOTRBiomeGenDorwinion) {
            growth *= 1.6f;
        }
        return growth;
    }

    public static boolean canPlantGrapesAt(World world, int i, int j, int k, IPlantable plantable) {
        for(int l = 1; l <= 3; ++l) {
            int j1 = j - l;
            Block block = world.getBlock(i, j1, k);
            if(block.canSustainPlant(world, i, j1, k, ForgeDirection.UP, plantable)) {
                return true;
            }
            if(block instanceof LOTRBlockGrapevine) continue;
            return false;
        }
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess world, int i, int j, int k) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        int meta;
        if(this.hasGrapes && (meta = world.getBlockMetadata(i, j, k)) >= 7) {
            if(!world.isRemote) {
                ArrayList<ItemStack> drops = this.getVineDrops(world, i, j, k, meta, 0);
                for(ItemStack itemstack : drops) {
                    this.dropBlockAsItem(world, i, j, k, itemstack);
                }
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.harvestGrapes);
            }
            world.setBlock(i, j, k, LOTRMod.grapevine, 0, 3);
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if(this.hasGrapes) {
            drops.addAll(this.getVineDrops(world, i, j, k, meta, fortune));
        }
        else {
            drops.add(new ItemStack(this));
        }
        return drops;
    }

    private ArrayList<ItemStack> getVineDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        int seeds = 3 + fortune;
        for(int l = 0; l < seeds; ++l) {
            if(world.rand.nextInt(15) > meta) continue;
            drops.add(new ItemStack(this.getGrapeSeedsItem()));
        }
        if(meta >= 7) {
            int grapes = 1 + world.rand.nextInt(fortune + 1);
            if(world.rand.nextInt(3) == 0) {
                ++grapes;
            }
            for(int l = 0; l < grapes; ++l) {
                drops.add(new ItemStack(this.getGrapeItem()));
            }
        }
        return drops;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer entityplayer, int i, int j, int k, boolean willHarvest) {
        if(this.hasGrapes && entityplayer != null) {
            if(!world.isRemote) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.harvestGrapes);
            }
            return world.setBlock(i, j, k, LOTRMod.grapevine, 0, 3);
        }
        return super.removedByPlayer(world, entityplayer, i, j, k, willHarvest);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int i, int j, int k) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
        return world.getBlockMetadata(i, j, k);
    }

    @Override
    public boolean func_149851_a(World world, int i, int j, int k, boolean flag) {
        return this.hasGrapes && world.getBlockMetadata(i, j, k) < 7;
    }

    @Override
    public boolean func_149852_a(World world, Random random, int i, int j, int k) {
        return true;
    }

    @Override
    public void func_149853_b(World world, Random random, int i, int j, int k) {
        if(this.hasGrapes) {
            int meta = world.getBlockMetadata(i, j, k) + MathHelper.getRandomIntegerInRange(random, 2, 5);
            if(meta > 7) {
                meta = 7;
            }
            world.setBlockMetadataWithNotify(i, j, k, meta, 2);
        }
    }

    @Override
    public boolean isAir(IBlockAccess world, int i, int j, int k) {
        if(hoeing) {
            hoeing = false;
            return true;
        }
        return super.isAir(world, i, j, k);
    }

    public static boolean isFullGrownGrapes(Block block, int meta) {
        return block instanceof LOTRBlockGrapevine && ((LOTRBlockGrapevine) block).hasGrapes && meta >= 7;
    }
}
