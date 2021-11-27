package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockCorn extends Block implements IPlantable, IGrowable {
    public static int MAX_GROW_HEIGHT = 3;
    private static int META_GROW_END = 7;
    @SideOnly(value = Side.CLIENT)
    private IIcon cornIcon;

    public LOTRBlockCorn() {
        super(Material.plants);
        float f = 0.375f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
        this.setTickRandomly(true);
        this.setHardness(0.0f);
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        if(this.checkCanStay(world, i, j, k)) {
            int cornHeight = 1;
            while(world.getBlock(i, j - cornHeight, k) == this) {
                ++cornHeight;
            }
            float growth = this.getGrowthFactor(world, i, j - cornHeight + 1, k);
            if(world.isAirBlock(i, j + 1, k) && cornHeight < MAX_GROW_HEIGHT) {
                int meta = world.getBlockMetadata(i, j, k);
                int corn = meta & 8;
                int grow = meta & 7;
                if(grow == META_GROW_END) {
                    world.setBlock(i, j + 1, k, this, 0, 3);
                    grow = 0;
                }
                else {
                    ++grow;
                }
                meta = corn | grow;
                world.setBlockMetadataWithNotify(i, j, k, meta, 4);
            }
            if(!LOTRBlockCorn.hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k) && world.rand.nextFloat() < growth) {
                LOTRBlockCorn.setHasCorn(world, i, j, k, true);
            }
        }
    }

    private float getGrowthFactor(World world, int i, int j, int k) {
        float growth = 1.0f;
        Block below = world.getBlock(i, j - 1, k);
        if(below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) (Blocks.wheat))) {
            growth = 3.0f;
            if(below.isFertile(world, i, j - 1, k)) {
                growth = 9.0f;
            }
        }
        if(world.isRaining()) {
            growth *= 3.0f;
        }
        return growth / 250.0f;
    }

    private boolean canGrowCorn(World world, int i, int j, int k) {
        return world.getBlock(i, j - 1, k) == this;
    }

    public static boolean hasCorn(World world, int i, int j, int k) {
        int meta = world.getBlockMetadata(i, j, k);
        return LOTRBlockCorn.metaHasCorn(meta);
    }

    private static boolean metaHasCorn(int l) {
        return (l & 8) != 0;
    }

    public static void setHasCorn(World world, int i, int j, int k, boolean flag) {
        int meta = world.getBlockMetadata(i, j, k);
        meta = flag ? (meta |= 8) : (meta &= 7);
        world.setBlockMetadataWithNotify(i, j, k, meta, 3);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        Block below = world.getBlock(i, j - 1, k);
        if(below == this) {
            return true;
        }
        IPlantable beachTest = new IPlantable() {

            @Override
            public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
                return EnumPlantType.Beach;
            }

            @Override
            public Block getPlant(IBlockAccess world, int i, int j, int k) {
                return LOTRBlockCorn.this;
            }

            @Override
            public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
                return 0;
            }
        };
        return below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, this) || below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, beachTest);
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
            world.setBlockToAir(i, j, k);
            return false;
        }
        return true;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
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
        return 1;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
        if(LOTRBlockCorn.hasCorn(world, i, j, k)) {
            if(!world.isRemote) {
                int preMeta = world.getBlockMetadata(i, j, k);
                LOTRBlockCorn.setHasCorn(world, i, j, k, false);
                if(!world.isRemote) {
                    ArrayList<ItemStack> cornDrops = this.getCornDrops(world, i, j, k, preMeta);
                    for(ItemStack corn : cornDrops) {
                        this.dropBlockAsItem(world, i, j, k, corn);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int damageDropped(int i) {
        return 0;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>(super.getDrops(world, i, j, k, meta, fortune));
        drops.addAll(this.getCornDrops(world, i, j, k, meta));
        return drops;
    }

    public ArrayList<ItemStack> getCornDrops(World world, int i, int j, int k, int meta) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if(LOTRBlockCorn.metaHasCorn(meta)) {
            int corns = 1;
            if(world.rand.nextInt(4) == 0) {
                ++corns;
            }
            for(int l = 0; l < corns; ++l) {
                drops.add(new ItemStack(LOTRMod.corn));
            }
        }
        return drops;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        if(LOTRBlockCorn.metaHasCorn(j)) {
            return this.cornIcon;
        }
        return super.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.cornIcon = iconregister.registerIcon(this.getTextureName() + "_corn");
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public String getItemIconName() {
        return this.getTextureName();
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
    public boolean func_149851_a(World world, int i, int j, int k, boolean isRemote) {
        return world.getBlock(i, j - 1, k) != this && world.isAirBlock(i, j + 1, k) || !LOTRBlockCorn.hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k);
    }

    @Override
    public boolean func_149852_a(World world, Random random, int i, int j, int k) {
        return true;
    }

    @Override
    public void func_149853_b(World world, Random random, int i, int j, int k) {
        if(world.getBlock(i, j - 1, k) != this && world.isAirBlock(i, j + 1, k)) {
            world.setBlock(i, j + 1, k, this, 0, 3);
        }
        else if(!LOTRBlockCorn.hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k) && random.nextInt(2) == 0) {
            LOTRBlockCorn.setHasCorn(world, i, j, k, true);
        }
    }

}
