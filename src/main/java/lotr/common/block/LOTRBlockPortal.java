package lotr.common.block;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

public abstract class LOTRBlockPortal extends BlockContainer {
    private LOTRFaction[] portalFactions;
    private Class teleporterClass;

    public LOTRBlockPortal(LOTRFaction[] factions, Class c) {
        super(Material.portal);
        this.portalFactions = factions;
        this.teleporterClass = c;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int i, int j, int k) {
        float f = 0.0625f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int side) {
        return side != 0 ? false : super.shouldSideBeRendered(world, i, j, k, side);
    }

    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity) {
    }

    public abstract void setPlayerInPortal(EntityPlayer var1);

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        if(!LOTRConfig.enablePortals) {
            return;
        }
        if(entity instanceof EntityPlayer) {
            for(LOTRFaction faction : this.portalFactions) {
                if((LOTRLevelData.getData((EntityPlayer) entity).getAlignment(faction) < 1.0f)) continue;
                if(entity.ridingEntity == null && entity.riddenByEntity == null) {
                    this.setPlayerInPortal((EntityPlayer) entity);
                }
                return;
            }
        }
        else {
            for(LOTRFaction faction : this.portalFactions) {
                if(LOTRMod.getNPCFaction(entity).isBadRelation(faction)) continue;
                if(entity.ridingEntity == null && entity.riddenByEntity == null && entity.timeUntilPortal == 0) {
                    this.transferEntity(entity, world);
                }
                return;
            }
        }
        if(!world.isRemote) {
            entity.setFire(4);
            entity.attackEntityFrom(DamageSource.inFire, 2.0f);
            world.playSoundAtEntity(entity, "random.fizz", 0.5f, 1.5f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.5f);
        }
    }

    public Teleporter getPortalTeleporter(WorldServer world) {
        for(Teleporter obj : world.customTeleporters) {
            if(!this.teleporterClass.isInstance(obj)) continue;
            return obj;
        }
        Teleporter teleporter = null;
        try {
            teleporter = (Teleporter) this.teleporterClass.getConstructor(WorldServer.class).newInstance(world);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        world.customTeleporters.add(teleporter);
        return teleporter;
    }

    private void transferEntity(Entity entity, World world) {
        if(!world.isRemote) {
            int dimension = 0;
            if(entity.dimension == 0) {
                dimension = LOTRDimension.MIDDLE_EARTH.dimensionID;
            }
            else if(entity.dimension == LOTRDimension.MIDDLE_EARTH.dimensionID) {
                dimension = 0;
            }
            LOTRMod.transferEntityToDimension(entity, dimension, this.getPortalTeleporter(DimensionManager.getWorld(dimension)));
        }
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
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        if(random.nextInt(100) == 0) {
            world.playSound(i + 0.5, j + 0.5, k + 0.5, "portal.portal", 0.5f, random.nextFloat() * 0.4f + 0.8f, false);
        }
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k) {
        if(world.provider.dimensionId != 0 && world.provider.dimensionId != LOTRDimension.MIDDLE_EARTH.dimensionID) {
            world.setBlockToAir(i, j, k);
        }
    }

    public abstract boolean isValidPortalLocation(World var1, int var2, int var3, int var4, boolean var5);

    @SideOnly(value = Side.CLIENT)
    @Override
    public Item getItem(World world, int i, int j, int k) {
        return Item.getItemById(0);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIcon(int i, int j) {
        return Blocks.portal.getIcon(i, j);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconregister) {
    }
}
