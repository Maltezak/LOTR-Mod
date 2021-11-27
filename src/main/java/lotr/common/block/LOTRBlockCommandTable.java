package lotr.common.block;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import lotr.common.world.map.LOTRConquestGrid;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LOTRBlockCommandTable
extends BlockContainer {
    @SideOnly(value=Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(value=Side.CLIENT)
    private IIcon sideIcon;

    public LOTRBlockCommandTable() {
        super(Material.iron);
        this.setCreativeTab(LOTRCreativeTabs.tabUtil);
        this.setHardness(2.5f);
        this.setStepSound(Block.soundTypeMetal);
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new LOTRTileEntityCommandTable();
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return LOTRMod.proxy.getCommandTableRenderID();
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
        LOTRTileEntityCommandTable table;
        if (entityplayer.isSneaking() && (table = (LOTRTileEntityCommandTable)world.getTileEntity(i, j, k)) != null) {
            if (!world.isRemote) {
                table.toggleZoomExp();
            }
            return true;
        }
        ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != null && itemstack.getItem() instanceof LOTRSquadrons.SquadronItem) {
            if (!world.isRemote) {
                LOTRCommonProxy.sendClientsideGUI((EntityPlayerMP)entityplayer, 33, 0, 0, 0);
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, this.stepSound.getBreakSound(), (this.stepSound.getVolume() + 1.0f) / 2.0f, this.stepSound.getPitch() * 0.5f);
            }
            return true;
        }
        if (LOTRConquestGrid.conquestEnabled(world)) {
            if (!world.isRemote) {
                LOTRCommonProxy.sendClientsideGUI((EntityPlayerMP)entityplayer, 60, 0, 0, 0);
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, this.stepSound.getBreakSound(), (this.stepSound.getVolume() + 1.0f) / 2.0f, this.stepSound.getPitch() * 0.5f);
            }
            return true;
        }
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public IIcon getIcon(int i, int j) {
        if (i == 1 || i == 0) {
            return this.topIcon;
        }
        return this.sideIcon;
    }

    @SideOnly(value=Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconregister) {
        this.sideIcon = iconregister.registerIcon(this.getTextureName() + "_side");
        this.topIcon = iconregister.registerIcon(this.getTextureName() + "_top");
    }
}

