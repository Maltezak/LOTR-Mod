package lotr.common.coremod;

import java.util.*;

import cpw.mods.fml.common.network.internal.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import io.netty.buffer.*;
import lotr.common.*;
import lotr.common.block.*;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.item.LOTRWeaponStats;
import lotr.common.util.*;
import lotr.common.world.spawning.LOTRSpawnerAnimals;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.command.IEntitySelector;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTRReplacedMethods {

    public static class BlockRendering {
        private static Random blockRand = new Random();
        private static Map<Class<? extends Block>, boolean[]> naturalBlockClassTable = new HashMap<>();
        private static Map<BlockAndMeta, boolean[]> naturalBlockTable = new HashMap<>();
        private static Map<BlockAndMeta, boolean[]> cachedNaturalBlocks = new HashMap<>();

        private static void add(Class<? extends Block> cls) {
            BlockRendering.add(cls, 0, 1, 2, 3, 4, 5);
        }

        private static void add(Class<? extends Block> cls, Integer ... sides) {
            naturalBlockClassTable.put(cls, BlockRendering.getSideFlagsFrom(sides));
        }

        private static void add(Block block, int meta) {
            BlockRendering.add(block, -1, 0, 1, 2, 3, 4, 5);
        }

        private static void add(Block block, int meta, Integer ... sides) {
            naturalBlockTable.put(new BlockAndMeta(block, meta), BlockRendering.getSideFlagsFrom(sides));
        }

        private static boolean[] getSideFlagsFrom(Integer ... sides) {
            List<Integer> sidesAsList = Arrays.asList(sides);
            boolean[] sideFlags = new boolean[6];
            for (int side = 0; side < sideFlags.length; ++side) {
                if (!sidesAsList.contains(side)) continue;
                sideFlags[side] = true;
            }
            return sideFlags;
        }

        private static void setupNaturalBlockTable() {
            BlockRendering.add(BlockGrass.class, 1, 0);
            BlockRendering.add(Blocks.dirt, 0);
            BlockRendering.add(Blocks.dirt, 1);
            BlockRendering.add(Blocks.dirt, 2, 1, 0);
            BlockRendering.add(LOTRBlockSlabDirt.class);
            BlockRendering.add(LOTRBlockMudGrass.class, 1, 0);
            BlockRendering.add(LOTRBlockMud.class);
            BlockRendering.add(BlockSand.class);
            BlockRendering.add(LOTRBlockSand.class);
            BlockRendering.add(LOTRBlockSlabSand.class);
            BlockRendering.add(Blocks.sandstone, 0, 1, 0);
            BlockRendering.add(Blocks.stone_slab, 1, 1, 0);
            BlockRendering.add(Blocks.double_stone_slab, 1, 1, 0);
            BlockRendering.add(Blocks.sandstone_stairs, 1, 0);
            BlockRendering.add(LOTRMod.wallStoneV, 4, 1, 0);
            BlockRendering.add(LOTRBlockSandstone.class, 1, 0);
            BlockRendering.add(LOTRMod.slabSingle7, 5, 1, 0);
            BlockRendering.add(LOTRMod.slabDouble7, 5, 1, 0);
            BlockRendering.add(LOTRMod.stairsRedSandstone, 1, 0);
            BlockRendering.add(LOTRMod.wallStoneV, 5, 1, 0);
            BlockRendering.add(LOTRMod.slabSingle10, 6, 1, 0);
            BlockRendering.add(LOTRMod.slabDouble10, 6, 1, 0);
            BlockRendering.add(LOTRMod.stairsWhiteSandstone, 1, 0);
            BlockRendering.add(LOTRMod.wall3, 14, 1, 0);
            BlockRendering.add(LOTRMod.rock, 0, 1, 0);
            BlockRendering.add(BlockGravel.class);
            BlockRendering.add(LOTRBlockSlabGravel.class);
            BlockRendering.add(BlockClay.class);
            BlockRendering.add(BlockSnow.class);
            BlockRendering.add(BlockSnowBlock.class);
            BlockRendering.add(BlockMycelium.class, 1, 0);
            BlockRendering.add(LOTRBlockMordorDirt.class);
            BlockRendering.add(LOTRBlockDirtPath.class);
            BlockRendering.add(LOTRBlockMordorMoss.class);
        }

        public static boolean renderStandardBlock(RenderBlocks renderblocks, Block block, int i, int j, int k) {
            if (naturalBlockClassTable.isEmpty()) {
                BlockRendering.setupNaturalBlockTable();
            }
            if (LOTRConfig.naturalBlocks) {
                int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);
                BlockAndMeta bam = new BlockAndMeta(block, meta);
                boolean[] sideFlags = null;
                if (cachedNaturalBlocks.containsKey(bam)) {
                    sideFlags = cachedNaturalBlocks.get(bam);
                } else if (naturalBlockTable.containsKey(bam)) {
                    sideFlags = naturalBlockTable.get(bam);
                    cachedNaturalBlocks.put(bam, sideFlags);
                } else {
                    for (Class<? extends Block> cls : naturalBlockClassTable.keySet()) {
                        if (!cls.isAssignableFrom(block.getClass())) continue;
                        sideFlags = naturalBlockClassTable.get(cls);
                        cachedNaturalBlocks.put(bam, sideFlags);
                    }
                }
                if (sideFlags != null) {
                    int[] randomSides = new int[6];
                    for (int l = 0; l < randomSides.length; ++l) {
                        int hash = i * 234890405 ^ k * 37383934 ^ j;
                        blockRand.setSeed(hash += l * 285502);
                        blockRand.setSeed(blockRand.nextLong());
                        randomSides[l] = blockRand.nextInt(4);
                    }
                    if (sideFlags[0]) {
                        renderblocks.uvRotateBottom = randomSides[0];
                    }
                    if (sideFlags[1]) {
                        renderblocks.uvRotateTop = randomSides[1];
                    }
                    if (sideFlags[2]) {
                        renderblocks.uvRotateNorth = randomSides[2];
                    }
                    if (sideFlags[3]) {
                        renderblocks.uvRotateSouth = randomSides[3];
                    }
                    if (sideFlags[4]) {
                        renderblocks.uvRotateWest = randomSides[4];
                    }
                    if (sideFlags[5]) {
                        renderblocks.uvRotateEast = randomSides[5];
                    }
                }
                boolean flag = renderblocks.renderStandardBlock(block, i, j, k);
                if (sideFlags != null) {
                    renderblocks.uvRotateEast = 0;
                    renderblocks.uvRotateWest = 0;
                    renderblocks.uvRotateSouth = 0;
                    renderblocks.uvRotateNorth = 0;
                    renderblocks.uvRotateTop = 0;
                    renderblocks.uvRotateBottom = 0;
                }
                return flag;
            }
            return renderblocks.renderStandardBlock(block, i, j, k);
        }

        private static class BlockAndMeta {
            public final Block block;
            public final int meta;

            public BlockAndMeta(Block b, int m) {
                this.block = b;
                this.meta = m;
            }

            public boolean equals(Object other) {
                if (other instanceof BlockAndMeta) {
                    BlockAndMeta otherBM = (BlockAndMeta)other;
                    return otherBM.block == this.block && otherBM.meta == this.meta;
                }
                return false;
            }

            public int hashCode() {
                return (this.block.getUnlocalizedName() + "_" + this.meta).hashCode();
            }
        }

    }

    public static class EntityPackets {
        public static Packet getMobSpawnPacket(Entity entity) {
            EntityRegistry.EntityRegistration er = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
            if (er == null) {
                return null;
            }
            if (er.usesVanillaSpawning()) {
                return null;
            }
            FMLMessage.EntitySpawnMessage msg = new FMLMessage.EntitySpawnMessage(er, entity, er.getContainer());
            ByteBuf data = Unpooled.buffer();
            data.writeByte(2);
            try {
                new FMLRuntimeCodec().encodeInto(null, msg, data);
            }
            catch (Exception e) {
                LOTRLog.logger.error("***********************************************");
                LOTRLog.logger.error("LOTR: ERROR sending mob spawn packet to client!");
                LOTRLog.logger.error("***********************************************");
            }
            return new FMLProxyPacket(data, "FML");
        }
    }

    public static class NetHandlerClient {
        public static void handleEntityTeleport(NetHandlerPlayClient handler, S18PacketEntityTeleport packet) {
            World world = LOTRMod.proxy.getClientWorld();
            Entity entity = world.getEntityByID(packet.func_149451_c());
            if (entity != null) {
                entity.serverPosX = packet.func_149449_d();
                entity.serverPosY = packet.func_149448_e();
                entity.serverPosZ = packet.func_149446_f();
                if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
                    double d0 = entity.serverPosX / 32.0;
                    double d1 = entity.serverPosY / 32.0 + 0.015625;
                    double d2 = entity.serverPosZ / 32.0;
                    float f = packet.func_149450_g() * 360 / 256.0f;
                    float f1 = packet.func_149447_h() * 360 / 256.0f;
                    entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3);
                }
            }
        }

        public static void handleEntityMovement(NetHandlerPlayClient handler, S14PacketEntity packet) {
            World world = LOTRMod.proxy.getClientWorld();
            Entity entity = packet.func_149065_a(world);
            if (entity != null) {
                entity.serverPosX += packet.func_149062_c();
                entity.serverPosY += packet.func_149061_d();
                entity.serverPosZ += packet.func_149064_e();
                if (!LOTRMountFunctions.isPlayerControlledMount(entity)) {
                    double d0 = entity.serverPosX / 32.0;
                    double d1 = entity.serverPosY / 32.0;
                    double d2 = entity.serverPosZ / 32.0;
                    float f = packet.func_149060_h() ? packet.func_149066_f() * 360 / 256.0f : entity.rotationYaw;
                    float f1 = packet.func_149060_h() ? packet.func_149063_g() * 360 / 256.0f : entity.rotationPitch;
                    entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3);
                }
            }
        }
    }

    public static class ClientPlayer {
        public static void horseJump(EntityClientPlayerMP entityplayer) {
            int jump = (int)(entityplayer.getHorseJumpPower() * 100.0f);
            Entity mount = entityplayer.ridingEntity;
            if (mount instanceof EntityHorse) {
                ((EntityHorse)mount).setJumpPower(jump);
            }
            entityplayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(entityplayer, 6, jump));
        }
    }

    public static class Potions {
        public static double getStrengthModifier(Potion thisPotion, int level, AttributeModifier modifier) {
            if (thisPotion.id == Potion.weakness.id) {
                return -0.5 * (level + 1);
            }
            return 0.5 * (level + 1);
        }
    }

    public static class Enchants {
        public static boolean isPlayerMeleeKill(Entity entity, DamageSource source) {
            boolean flag = entity instanceof EntityPlayer && source.getSourceOfDamage() == entity;
            return flag;
        }

        public static float getEnchantmentModifierLiving(float base, EntityLivingBase attacker, EntityLivingBase target) {
            float f = base;
            return f += LOTREnchantmentHelper.calcEntitySpecificDamage(attacker.getHeldItem(), target);
        }

        public static float func_152377_a(float base, ItemStack itemstack, EnumCreatureAttribute creatureAttribute) {
            float f = base;
            return f += LOTREnchantmentHelper.calcBaseMeleeDamageBoost(itemstack);
        }

        public static boolean attemptDamageItem(ItemStack itemstack, int damages, Random random) {
            if (!itemstack.isItemStackDamageable()) {
                return false;
            }
            if (damages > 0) {
                int l;
                int unbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemstack);
                int negated = 0;
                if (unbreaking > 0) {
                    for (l = 0; l < damages; ++l) {
                        if (!EnchantmentDurability.negateDamage(itemstack, unbreaking, random)) continue;
                        ++negated;
                    }
                }
                for (l = 0; l < damages; ++l) {
                    if (!LOTREnchantmentHelper.negateDamage(itemstack, random)) continue;
                    ++negated;
                }
                if ((damages -= negated) <= 0) {
                    return false;
                }
            }
            itemstack.setItemDamage(itemstack.getItemDamage() + damages);
            return itemstack.getItemDamage() > itemstack.getMaxDamage();
        }

        public static int c_attemptDamageItem(int unmodified, ItemStack stack, int damages, Random random, EntityLivingBase elb) {
            int ret = unmodified;
            for (int i = 0; i < damages; ++i) {
                if (!LOTREnchantmentHelper.negateDamage(stack, random)) continue;
                --ret;
            }
            return ret;
        }

        public static boolean getSilkTouchModifier(boolean base, EntityLivingBase entity) {
            boolean flag = base;
            if (LOTREnchantmentHelper.isSilkTouch(entity.getHeldItem())) {
                flag = true;
            }
            return flag;
        }

        public static int getKnockbackModifier(int base, EntityLivingBase attacker, EntityLivingBase target) {
            int i = base;
            i += LOTRWeaponStats.getBaseExtraKnockback(attacker.getHeldItem());
            return i += LOTREnchantmentHelper.calcExtraKnockback(attacker.getHeldItem());
        }

        public static int getFortuneModifier(int base, EntityLivingBase entity) {
            int i = base;
            return i += LOTREnchantmentHelper.calcLootingLevel(entity.getHeldItem());
        }

        public static int getLootingModifier(int base, EntityLivingBase entity) {
            int i = base;
            return i += LOTREnchantmentHelper.calcLootingLevel(entity.getHeldItem());
        }

        public static int getSpecialArmorProtection(int base, ItemStack[] armor, DamageSource source) {
            int i = base;
            i += LOTREnchantmentHelper.calcSpecialArmorSetProtection(armor, source);
            i = MathHelper.clamp_int(i, 0, 25);
            return i;
        }

        public static int getMaxFireProtectionLevel(int base, Entity entity) {
            int i = base;
            i = Math.max(i, LOTREnchantmentHelper.getMaxFireProtectionLevel(entity.getLastActiveItems()));
            return i;
        }

        public static int getFireAspectModifier(int base, EntityLivingBase entity) {
            int i = base;
            return i += LOTREnchantmentHelper.calcFireAspectForMelee(entity.getHeldItem());
        }

        public static int getDamageReduceAmount(ItemStack itemstack) {
            return LOTRWeaponStats.getArmorProtection(itemstack);
        }
    }

    public static class PathFinder {
        public static boolean isWoodenDoor(Block block) {
            return block instanceof BlockDoor && block.getMaterial() == Material.wood;
        }

        public static boolean isFenceGate(Block block) {
            return block instanceof BlockFenceGate;
        }
    }

    public static class Spawner {
        public static int performSpawning_optimised(WorldServer world, boolean hostiles, boolean peacefuls, boolean rareTick) {
            return LOTRSpawnerAnimals.performSpawning(world, hostiles, peacefuls, rareTick);
        }
    }

    public static class Minecart {
        public static boolean checkForPoweredRail(EntityMinecart cart, int x, int y, int z, Block block, boolean flagIn) {
            World world = cart.worldObj;
            if (block instanceof LOTRBlockMechanisedRail) {
                LOTRBlockMechanisedRail mechRailBlock = (LOTRBlockMechanisedRail)block;
                int meta = world.getBlockMetadata(x, y, z);
                flagIn = mechRailBlock.isPowerOn(meta);
            }
            return flagIn;
        }

        public static boolean checkForDepoweredRail(EntityMinecart cart, int x, int y, int z, Block block, boolean flagIn) {
            World world = cart.worldObj;
            if (block instanceof LOTRBlockMechanisedRail) {
                LOTRBlockMechanisedRail mechRailBlock = (LOTRBlockMechanisedRail)block;
                int meta = world.getBlockMetadata(x, y, z);
                flagIn = !mechRailBlock.isPowerOn(meta);
            }
            return flagIn;
        }
    }

    public static class Lightning {
        public static void init(EntityLightningBolt bolt) {
            bolt.renderDistanceWeight = 5.0;
        }

        public static void doSetBlock(World world, int i, int j, int k, Block block) {
            if (block == Blocks.fire && LOTRConfig.disableLightningGrief) {
                return;
            }
            world.setBlock(i, j, k, block);
        }
    }

    public static class Food {
        public static float getExhaustionFactor() {
            if (LOTRConfig.changedHunger) {
                return 0.3f;
            }
            return 1.0f;
        }
    }

    public static class Player {
        public static boolean canEat(EntityPlayer entityplayer, boolean forced) {
            if (entityplayer.capabilities.disableDamage) {
                return false;
            }
            if (forced) {
                return true;
            }
            if (entityplayer.getFoodStats().needFood()) {
                return true;
            }
            boolean feastMode = LOTRConfig.canAlwaysEat;
            if (entityplayer.worldObj.isRemote) {
                feastMode = LOTRLevelData.clientside_thisServer_feastMode;
            }
            return feastMode && entityplayer.ridingEntity == null;
        }
    }

    public static class Anvil {
        public static AxisAlignedBB getCollisionBoundingBoxFromPool(Block block, World world, int i, int j, int k) {
            block.setBlockBoundsBasedOnState(world, i, j, k);
            return AxisAlignedBB.getBoundingBox(i + block.getBlockBoundsMinX(), j + block.getBlockBoundsMinY(), k + block.getBlockBoundsMinZ(), i + block.getBlockBoundsMaxX(), j + block.getBlockBoundsMaxY(), k + block.getBlockBoundsMaxZ());
        }
    }

    public static class Cauldron {
        public static int getRenderType() {
            if (LOTRMod.proxy == null) {
                return 24;
            }
            return LOTRMod.proxy.getVCauldronRenderID();
        }
    }

    public static class Piston {
        public static boolean canPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
            AxisAlignedBB bannerSearchBox = AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 4, k + 1);
            List banners = world.selectEntitiesWithinAABB(LOTREntityBanner.class, bannerSearchBox, new IEntitySelector(){

                public boolean isEntityApplicable(Entity entity) {
                    LOTREntityBanner banner = (LOTREntityBanner)entity;
                    return !banner.isDead && banner.isProtectingTerritory();
                }
            });
            if (!banners.isEmpty()) {
                return false;
            }
            return LOTRReflection.canPistonPushBlock(block, world, i, j, k, flag);
        }

    }

    public static class Wall {
        public static boolean canConnectWallTo(IBlockAccess world, int i, int j, int k) {
            return Fence.canConnectFenceTo(world, i, j, k);
        }
    }

    public static class Trapdoor {
        public static boolean canPlaceBlockOnSide(World world, int i, int j, int k, int side) {
            return true;
        }

        public static boolean isValidSupportBlock(Block block) {
            return true;
        }

        public static int getRenderType(Block block) {
            if (LOTRMod.proxy != null) {
                return LOTRMod.proxy.getTrapdoorRenderID();
            }
            return 0;
        }
    }

    public static class Pumpkin {
        public static int alterIconSideParam(Block block, int side, int meta) {
            if (block == Blocks.pumpkin) {
                if (meta == 2 && side == 2) {
                    side = 3;
                } else if (meta == 3 && side == 5) {
                    side = 4;
                } else if (meta == 0 && side == 3) {
                    side = 2;
                } else if (meta == 1 && side == 4) {
                    side = 5;
                }
            }
            return side;
        }
    }

    public static class Fence {
        public static boolean canConnectFenceTo(IBlockAccess world, int i, int j, int k) {
            Block block = world.getBlock(i, j, k);
            if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall) {
                return true;
            }
            return block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd;
        }

        public static boolean canPlacePressurePlate(Block block) {
            return block instanceof BlockFence;
        }
    }

    public static class Fire {
        public static boolean isFireSpreadDisabled() {
            return LOTRConfig.disableFireSpread;
        }
    }

    public static class StaticLiquid {
        public static void updateTick_optimised(Block thisBlock, World world, int i, int j, int k, Random random) {
            if (thisBlock.getMaterial() == Material.lava) {
                int tries = random.nextInt(3);
                for (int l = 0; l < tries; ++l) {
                    if (world.blockExists(i += random.nextInt(3) - 1, ++j, k += random.nextInt(3) - 1)) {
                        Block block = world.getBlock(i, j, k);
                        if (block.getMaterial() == Material.air) {
                            if (!StaticLiquid.isFlammable(world, i - 1, j, k) && !StaticLiquid.isFlammable(world, i + 1, j, k) && !StaticLiquid.isFlammable(world, i, j, k - 1) && !StaticLiquid.isFlammable(world, i, j, k + 1) && !StaticLiquid.isFlammable(world, i, j - 1, k) && !StaticLiquid.isFlammable(world, i, j + 1, k)) continue;
                            world.setBlock(i, j, k, Blocks.fire);
                            return;
                        }
                        if (!block.getMaterial().blocksMovement()) continue;
                        return;
                    }
                    return;
                }
                if (tries == 0) {
                    int i1 = i;
                    int k1 = k;
                    for (int l = 0; l < 3; ++l) {
                        i = i1 + random.nextInt(3) - 1;
                        if (!world.blockExists(i, j, k = k1 + random.nextInt(3) - 1) || !world.isAirBlock(i, j + 1, k) || !StaticLiquid.isFlammable(world, i, j, k)) continue;
                        world.setBlock(i, j + 1, k, Blocks.fire);
                    }
                }
            }
        }

        private static boolean isFlammable(World world, int i, int j, int k) {
            return world.blockExists(i, j, k) && world.getBlock(i, j, k).getMaterial().getCanBurn();
        }
    }

    public static class Dirt {
        public static String nameIndex1 = "coarse";

        public static int damageDropped(int i) {
            if (i == 1) {
                return 1;
            }
            return 0;
        }

        public static ItemStack createStackedBlock(Block thisBlock, int i) {
            Item item = Item.getItemFromBlock(thisBlock);
            return new ItemStack(item, 1, i);
        }

        public static void getSubBlocks(Block thisBlock, Item item, CreativeTabs tab, List list) {
            list.add(new ItemStack(thisBlock, 1, 0));
            list.add(new ItemStack(thisBlock, 1, 1));
            list.add(new ItemStack(thisBlock, 1, 2));
        }

        public static int getDamageValue(World world, int i, int j, int k) {
            int meta = world.getBlockMetadata(i, j, k);
            return meta;
        }
    }

    public static class Grass {
        public static final int MIN_GRASS_LIGHT = 4;
        public static final int MAX_GRASS_OPACITY = 2;
        public static final int MIN_SPREAD_LIGHT = 9;

        public static void updateTick_optimised(World world, int i, int j, int k, Random random) {
            if (!world.isRemote) {
                int checkRange = 1;
                if (!world.checkChunksExist(i - checkRange, j - checkRange, k - checkRange, i + checkRange, j + checkRange, k + checkRange)) {
                    return;
                }
                if (world.getBlockLightValue(i, j + 1, k) < 4 && world.getBlockLightOpacity(i, j + 1, k) > 2) {
                    Block block = world.getBlock(i, j, k);
                    if (block == Blocks.grass) {
                        world.setBlock(i, j, k, Blocks.dirt);
                    }
                    if (block == LOTRMod.mudGrass) {
                        world.setBlock(i, j, k, LOTRMod.mud);
                    }
                } else if (world.getBlockLightValue(i, j + 1, k) >= 9) {
                    for (int l = 0; l < 4; ++l) {
                        int k1;
                        int j1;
                        int i1 = i + random.nextInt(3) - 1;
                        if (!world.blockExists(i1, j1 = j + random.nextInt(5) - 3, k1 = k + random.nextInt(3) - 1) || !world.checkChunksExist(i1 - checkRange, j1 - checkRange, k1 - checkRange, i1 + checkRange, j1 + checkRange, k1 + checkRange) || world.getBlockLightValue(i1, j1 + 1, k1) < 4 || world.getBlockLightOpacity(i1, j1 + 1, k1) > 2) continue;
                        Block block = world.getBlock(i1, j1, k1);
                        int meta = world.getBlockMetadata(i1, j1, k1);
                        if (block == Blocks.dirt && meta == 0) {
                            world.setBlock(i1, j1, k1, Blocks.grass, 0, 3);
                        }
                        if (block != LOTRMod.mud || meta != 0) continue;
                        world.setBlock(i1, j1, k1, LOTRMod.mudGrass, 0, 3);
                    }
                }
            }
        }
    }

    public static class Stone {
        public static IIcon getIconWorld(Block block, IBlockAccess world, int i, int j, int k, int side) {
            Material aboveMat;
            if (LOTRConfig.snowyStone && block == Blocks.stone && side != 0 && side != 1 && ((aboveMat = (world.getBlock(i, j + 1, k)).getMaterial()) == Material.snow || aboveMat == Material.craftedSnow)) {
                return LOTRCommonIcons.iconStoneSnow;
            }
            return block.getIcon(side, world.getBlockMetadata(i, j, k));
        }

        public static IIcon getIconSideMeta(Block block, IIcon defaultIcon, int side, int meta) {
            if (LOTRConfig.snowyStone && block == Blocks.stone && meta == 1000) {
                if (side == 1) {
                    return Blocks.snow.getIcon(1, 0);
                }
                if (side != 0) {
                    return LOTRCommonIcons.iconStoneSnow;
                }
            }
            return defaultIcon;
        }
    }

}

