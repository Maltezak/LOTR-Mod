package lotr.common.item;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import lotr.common.entity.item.LOTREntityBossTrophy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LOTRItemBossTrophy extends Item {
    @SideOnly(value = Side.CLIENT)
    private IIcon[] trophyIcons;

    public LOTRItemBossTrophy() {
        this.setCreativeTab(LOTRCreativeTabs.tabDeco);
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public static TrophyType getTrophyType(ItemStack itemstack) {
        if(itemstack.getItem() instanceof LOTRItemBossTrophy) {
            return LOTRItemBossTrophy.getTrophyType(itemstack.getItemDamage());
        }
        return null;
    }

    public static TrophyType getTrophyType(int i) {
        return TrophyType.forID(i);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int i) {
        if(i >= this.trophyIcons.length) {
            i = 0;
        }
        return this.trophyIcons[i];
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconregister) {
        this.trophyIcons = new IIcon[TrophyType.trophyTypes.size()];
        for(int i = 0; i < this.trophyIcons.length; ++i) {
            this.trophyIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + TrophyType.trophyTypes.get(i).trophyName);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + LOTRItemBossTrophy.getTrophyType(itemstack).trophyName;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
        TrophyType trophyType = LOTRItemBossTrophy.getTrophyType(itemstack);
        Block.SoundType blockSound = Blocks.stone.stepSound;
        if(world.getBlock(i, j, k).isReplaceable(world, i, j, k)) {
            side = 1;
        }
        else if(side == 1) {
            ++j;
        }
        if(side == 0) {
            return false;
        }
        if(side == 1) {
            if(!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            Block block = world.getBlock(i, j - 1, k);
            world.getBlockMetadata(i, j - 1, k);
            if(block.isSideSolid(world, i, j - 1, k, ForgeDirection.UP) && !world.isRemote) {
                LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
                trophy.setLocationAndAngles(i + 0.5f, j, k + 0.5f, 180.0f - entityplayer.rotationYaw % 360.0f, 0.0f);
                trophy.setTrophyHanging(false);
                if(world.checkNoEntityCollision(trophy.boundingBox) && world.getCollidingBoundingBoxes(trophy, trophy.boundingBox).size() == 0 && !world.isAnyLiquid(trophy.boundingBox)) {
                    trophy.setTrophyType(trophyType);
                    world.spawnEntityInWorld(trophy);
                    world.playSoundAtEntity(trophy, blockSound.func_150496_b(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
                    --itemstack.stackSize;
                    return true;
                }
                trophy.setDead();
                return false;
            }
        }
        else {
            if(!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
                return false;
            }
            if(!world.isRemote) {
                int direction = Direction.facingToDirection[side];
                LOTREntityBossTrophy trophy = new LOTREntityBossTrophy(world);
                trophy.setLocationAndAngles(i + Direction.offsetX[direction] + 0.5f, j, k + Direction.offsetZ[direction] + 0.5f, direction * 90.0f, 0.0f);
                trophy.setTrophyHanging(true);
                trophy.setTrophyFacing(direction);
                if(world.checkNoEntityCollision(trophy.boundingBox) && !world.isAnyLiquid(trophy.boundingBox) && trophy.hangingOnValidSurface()) {
                    trophy.setTrophyType(trophyType);
                    world.spawnEntityInWorld(trophy);
                    world.playSoundAtEntity(trophy, blockSound.func_150496_b(), (blockSound.getVolume() + 1.0f) / 2.0f, blockSound.getPitch() * 0.8f);
                    --itemstack.stackSize;
                    return true;
                }
                trophy.setDead();
                return false;
            }
        }
        return false;
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(TrophyType type : TrophyType.trophyTypes) {
            list.add(new ItemStack(item, 1, type.trophyID));
        }
    }

    public enum TrophyType {
        MOUNTAIN_TROLL_CHIEFTAIN(0, "mtc"), MALLORN_ENT(1, "mallornEnt");

        public static List<TrophyType> trophyTypes;
        private static Map<Integer, TrophyType> trophyForID;
        public final int trophyID;
        public final String trophyName;

        TrophyType(int i, String s) {
            this.trophyID = i;
            this.trophyName = s;
        }

        public static TrophyType forID(int ID) {
            return trophyForID.get(ID);
        }

        static {
            trophyTypes = new ArrayList<>();
            trophyForID = new HashMap<>();
            for(TrophyType t : TrophyType.values()) {
                trophyTypes.add(t);
                trophyForID.put(t.trophyID, t);
            }
        }
    }

}
