package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRBlockChandelier extends Block {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] chandelierIcons;
    private String[] chandelierNames = new String[] {"bronze", "iron", "silver", "gold", "mithril", "mallornSilver", "woodElven", "orc", "dwarven", "uruk", "highElven", "blueDwarven", "morgul", "mallornBlue", "mallornGold", "mallornGreen"};

    public LOTRBlockChandelier() {
        super(Material.circuits);
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setResistance(2.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setLightLevel(0.9375f);
        this.setBlockBounds(0.0625f, 0.1875f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }

    @Override
    public IIcon getIcon(int i, int j) {
        if(j >= this.chandelierNames.length) {
            j = 0;
        }
        return this.chandelierIcons[j];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
        this.chandelierIcons = new IIcon[this.chandelierNames.length];
        for(int i = 0; i < this.chandelierNames.length; ++i) {
            this.chandelierIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.chandelierNames[i]);
        }
    }

    @Override
    public int damageDropped(int i) {
        return i;
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
    public boolean canBlockStay(World world, int i, int j, int k) {
        Block block = world.getBlock(i, j + 1, k);
        int meta = world.getBlockMetadata(i, j + 1, k);
        if(block instanceof BlockFence || block instanceof BlockWall) {
            return true;
        }
        if(block instanceof BlockSlab && !block.isOpaqueCube() && (meta & 8) == 0) {
            return true;
        }
        if(block instanceof BlockStairs && (meta & 4) == 0) {
            return true;
        }
        if(block instanceof LOTRBlockOrcChain) {
            return true;
        }
        return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return this.canBlockStay(world, i, j, k);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
        if(!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for(int i = 0; i < this.chandelierNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        int meta = world.getBlockMetadata(i, j, k);
        double d = 0.13;
        double d1 = 1.0 - d;
        double d2 = 0.6875;
        this.spawnChandelierParticles(world, i + d, j + d2, k + d, random, meta);
        this.spawnChandelierParticles(world, i + d1, j + d2, k + d1, random, meta);
        this.spawnChandelierParticles(world, i + d, j + d2, k + d1, random, meta);
        this.spawnChandelierParticles(world, i + d1, j + d2, k + d, random, meta);
    }

    private void spawnChandelierParticles(World world, double d, double d1, double d2, Random random, int meta) {
        if(meta == 5 || meta == 13 || meta == 14 || meta == 15) {
            LOTRBlockTorch torchBlock = null;
            if(meta == 5) {
                torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchSilver;
            }
            else if(meta == 13) {
                torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchBlue;
            }
            else if(meta == 14) {
                torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchGold;
            }
            else if(meta == 15) {
                torchBlock = (LOTRBlockTorch) LOTRMod.mallornTorchGreen;
            }
            LOTRBlockTorch.TorchParticle particle = torchBlock.createTorchParticle(random);
            if(particle != null) {
                particle.spawn(d, d1, d2);
            }
        }
        else if(meta == 6) {
            String s = "leafRed_" + (10 + random.nextInt(20));
            double d3 = -0.005 + random.nextFloat() * 0.01f;
            double d4 = -0.005 + random.nextFloat() * 0.01f;
            double d5 = -0.005 + random.nextFloat() * 0.01f;
            LOTRMod.proxy.spawnParticle(s, d, d1, d2, d3, d4, d5);
        }
        else if(meta == 10) {
            LOTRMod.proxy.spawnParticle("elvenGlow", d, d1, d2, 0.0, 0.0, 0.0);
        }
        else if(meta == 12) {
            double d3 = -0.05 + random.nextFloat() * 0.1;
            double d4 = 0.1 + random.nextFloat() * 0.1;
            double d5 = -0.05 + random.nextFloat() * 0.1;
            LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d3, d4, d5);
        }
        else {
            world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}
