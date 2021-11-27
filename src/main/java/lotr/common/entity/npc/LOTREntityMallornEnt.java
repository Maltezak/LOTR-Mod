package lotr.common.entity.npc;

import java.util.List;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.projectile.LOTREntityMallornLeafBomb;
import lotr.common.item.LOTRItemBossTrophy;
import lotr.common.network.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class LOTREntityMallornEnt extends LOTREntityEnt implements LOTRBoss {
    public static float BOSS_SCALE = 1.5f;
    private static int SPAWN_TIME = 150;
    private static int MAX_LEAF_HEALINGS = 5;
    private LeafHealInfo[] leafHealings;
    private EntityAIBase meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 2.0, false);
    private EntityAIBase rangedAttackAI = new LOTREntityAIRangedAttack(this, 1.5, 30, 50, 24.0f);

    public LOTREntityMallornEnt(World world) {
        super(world);
        this.setSize(this.npcWidth * BOSS_SCALE, this.npcHeight * BOSS_SCALE);
        this.tasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(0, new LOTREntityAIBossJumpAttack(this, 1.5, 0.02f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
        this.tasks.addTask(3, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLiving.class, 10.0f, 0.02f));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.resetLeafHealings();
    }

    private void resetLeafHealings() {
        this.leafHealings = new LeafHealInfo[MAX_LEAF_HEALINGS];
        for(int i = 0; i < MAX_LEAF_HEALINGS; ++i) {
            this.leafHealings[i] = new LeafHealInfo(this, i);
        }
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(22, (short) 0);
        this.dataWatcher.addObject(23, (byte) 0);
    }

    public int getEntSpawnTick() {
        return this.dataWatcher.getWatchableObjectShort(22);
    }

    public void setEntSpawnTick(int i) {
        this.dataWatcher.updateObject(22, (short) i);
    }

    public boolean hasWeaponShield() {
        return this.dataWatcher.getWatchableObjectByte(23) == 1;
    }

    public void setHasWeaponShield(boolean flag) {
        this.dataWatcher.updateObject(23, flag ? (byte) 1 : 0);
    }

    public boolean isWeaponShieldActive() {
        return this.hasWeaponShield() && !this.isBurning();
    }

    public float getSpawningOffset(float f) {
        float f1 = (this.getEntSpawnTick() + f) / SPAWN_TIME;
        f1 = Math.min(f1, 1.0f);
        return (1.0f - f1) * -5.0f;
    }

    @Override
    public boolean shouldBurningPanic() {
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(8.0);
    }

    @Override
    public int getExtraHeadBranches() {
        if(this.hasWeaponShield()) {
            return 0;
        }
        int max = 8;
        float healthR = this.getHealth() / this.getMaxHealth();
        int branches = MathHelper.ceiling_float_int(healthR * max);
        branches = MathHelper.clamp_int(branches, 1, max);
        return branches;
    }

    @Override
    public float getBaseChanceModifier() {
        return this.bossInfo.getHealthChanceModifier();
    }

    public void sendEntBossSpeech(String speechBank) {
        List players = this.worldObj.playerEntities;
        double range = 64.0;
        for(Object obj : players) {
            EntityPlayer entityplayer = (EntityPlayer) obj;
            if((this.getDistanceSqToEntity(entityplayer) > range * range)) continue;
            this.sendSpeechBank(entityplayer, "ent/mallornEnt/" + speechBank);
        }
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(1, this.meleeAttackAI);
        }
        if(mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(1, this.meleeAttackAI);
        }
        if(mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(1, this.rangedAttackAI);
        }
    }

    @Override
    public double getMeleeRange() {
        return 12.0;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        LOTREntityMallornLeafBomb leaves = new LOTREntityMallornLeafBomb(this.worldObj, this, target);
        leaves.leavesDamage = 6.0f;
        this.worldObj.spawnEntityInWorld(leaves);
        this.playSound("lotr:ent.mallorn.leafAttack", this.getSoundVolume(), this.getSoundPitch());
        this.swingItem();
    }

    @Override
    public void onLivingUpdate() {
        double d2;
        double d1;
        super.onLivingUpdate();
        if(this.getEntSpawnTick() < SPAWN_TIME) {
            if(!this.worldObj.isRemote) {
                this.setEntSpawnTick(this.getEntSpawnTick() + 1);
                if(this.getEntSpawnTick() == SPAWN_TIME) {
                    this.bossInfo.doJumpAttack(1.5);
                }
            }
            else {
                for(int l = 0; l < 16; ++l) {
                    double d = this.posX + this.rand.nextGaussian() * this.width * 0.5;
                    d1 = this.posY + this.rand.nextDouble() * this.height + this.getSpawningOffset(0.0f);
                    d2 = this.posZ + this.rand.nextGaussian() * this.width * 0.5;
                    LOTRMod.proxy.spawnParticle("mEntSpawn", d, d1, d2, 0.0, 0.0, 0.0);
                }
                int leaves = 8;
                for(int l = 0; l < leaves; ++l) {
                    int leafR = (int) ((float) l / (float) leaves);
                    float argBase = (float) this.getEntSpawnTick() + leafR;
                    double r = 3.5;
                    double up = 0.5;
                    for(float extra : new float[] {0.0f, 3.1415927f}) {
                        float arg = argBase + extra;
                        double x = this.posX + r * MathHelper.cos(arg);
                        double z = this.posZ + r * MathHelper.sin(arg);
                        double y = this.posY + leafR * up;
                        LOTRMod.proxy.spawnParticle("leafGold_40", x, y, z, 0.0, up, 0.0);
                    }
                }
            }
        }
        if(!this.worldObj.isRemote) {
            float f = this.getBaseChanceModifier();
            f *= 0.05f;
            if(this.rand.nextFloat() < f) {
                this.bossInfo.doTargetedJumpAttack(1.5);
            }
        }
        if(!this.worldObj.isRemote && this.getHealth() < this.getMaxHealth()) {
            block3: for(LeafHealInfo healing : this.leafHealings) {
                if(healing.active) continue;
                float f = this.getBaseChanceModifier();
                f *= 0.02f;
                if((this.rand.nextFloat() >= f)) continue;
                int range = 16;
                int i = MathHelper.floor_double(this.posX);
                int j = MathHelper.floor_double(this.posY);
                int k = MathHelper.floor_double(this.posZ);
                for(int l = 0; l < 30; ++l) {
                    int k1;
                    int j1;
                    int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, -range, range);
                    Block block = this.worldObj.getBlock(i1, j1 = j + MathHelper.getRandomIntegerInRange(this.rand, -range, range), k1 = k + MathHelper.getRandomIntegerInRange(this.rand, -range, range));
                    if(!(block instanceof BlockLeavesBase)) continue;
                    healing.active = true;
                    healing.leafX = i1;
                    healing.leafY = j1;
                    healing.leafZ = k1;
                    healing.healTime = 15 + this.rand.nextInt(15);
                    this.sendHealInfoToWatchers(healing);
                    continue block3;
                }
            }
        }
        for(LeafHealInfo healing : this.leafHealings) {
            if(!healing.active) continue;
            int leafX = healing.leafX;
            int leafY = healing.leafY;
            int leafZ = healing.leafZ;
            Block block = this.worldObj.getBlock(leafX, leafY, leafZ);
            int meta = this.worldObj.getBlockMetadata(leafX, leafY, leafZ);
            if(block instanceof BlockLeavesBase) {
                if(!this.worldObj.isRemote) {
                    if(this.ticksExisted % 20 != 0) continue;
                    this.heal(2.0f);
                    healing.healTime--;
                    if((this.getHealth() < this.getMaxHealth()) && healing.healTime > 0) continue;
                    healing.active = false;
                    this.sendHealInfoToWatchers(healing);
                    continue;
                }
                double d = leafX + 0.5f;
                double d12 = leafY + 0.5f;
                double d22 = leafZ + 0.5f;
                double d3 = this.posX - d;
                double d4 = this.posY + this.height * 0.9 - d12;
                double d5 = this.posZ - d22;
                LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(block) + "_" + meta, d, d12, d22, d3 /= 25.0, d4 /= 25.0, d5 /= 25.0);
                continue;
            }
            if(this.worldObj.isRemote) continue;
            healing.active = false;
            this.sendHealInfoToWatchers(healing);
        }
        if(!this.worldObj.isRemote) {
            if(this.getHealth() < this.getMaxHealth() && this.rand.nextInt(50) == 0) {
                this.trySummonEnts();
            }
        }
        else if(this.getEntSpawnTick() >= SPAWN_TIME) {
            for(int i = 0; i < 2; ++i) {
                double d = this.posX + (this.rand.nextDouble() - 0.5) * this.width;
                d1 = this.posY + this.height + this.rand.nextDouble() * this.height * 0.5;
                d2 = this.posZ + (this.rand.nextDouble() - 0.5) * this.width;
                double d3 = MathHelper.getRandomDoubleInRange(this.rand, -0.2, 0.2);
                double d4 = MathHelper.getRandomDoubleInRange(this.rand, -0.2, 0.0);
                double d5 = MathHelper.getRandomDoubleInRange(this.rand, -0.2, 0.2);
                int time = 30 + this.rand.nextInt(30);
                LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d1, d2, d3, d4, d5);
            }
        }
    }

    private void trySummonEnts() {
        float f = this.getBaseChanceModifier();
        f *= 0.5f;
        List nearbyTrees = this.worldObj.getEntitiesWithinAABB(LOTREntityTree.class, this.boundingBox.expand(24.0, 8.0, 24.0));
        int maxNearbyTrees = 6;
        float nearbyModifier = (float) (maxNearbyTrees - nearbyTrees.size()) / (float) maxNearbyTrees;
        f *= nearbyModifier;
        if(this.rand.nextFloat() < f) {
            LOTREntityTree tree = this.rand.nextInt(3) == 0 ? new LOTREntityHuorn(this.worldObj) : new LOTREntityEnt(this.worldObj);
            int range = 12;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY);
            int k = MathHelper.floor_double(this.posZ);
            for(int l = 0; l < 30; ++l) {
                int j1;
                int k1;
                int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, -range, range);
                if(!this.worldObj.getBlock(i1, (j1 = j + MathHelper.getRandomIntegerInRange(this.rand, -range, range)) - 1, k1 = k + MathHelper.getRandomIntegerInRange(this.rand, -range, range)).isNormalCube() || this.worldObj.getBlock(i1, j1, k1).isNormalCube() || this.worldObj.getBlock(i1, j1 + 1, k1).isNormalCube()) continue;
                tree.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, this.rand.nextFloat() * 360.0f, 0.0f);
                tree.liftSpawnRestrictions = true;
                if(!tree.getCanSpawnHere()) continue;
                tree.liftSpawnRestrictions = false;
                tree.onSpawnWithEgg(null);
                this.worldObj.spawnEntityInWorld(tree);
                this.sendEntSummon(tree);
                this.worldObj.playSoundAtEntity(tree, "lotr:ent.mallorn.summonEnt", this.getSoundVolume(), this.getSoundPitch());
                break;
            }
        }
    }

    private void sendEntSummon(LOTREntityTree tree) {
        LOTRPacketMallornEntSummon packet = new LOTRPacketMallornEntSummon(this.getEntityId(), tree.getEntityId());
        LOTRPacketHandler.networkWrapper.sendToAllAround(packet, LOTRPacketHandler.nearEntity(tree, 64.0));
    }

    @Override
    public void onPlayerStartTracking(EntityPlayerMP entityplayer) {
        super.onPlayerStartTracking(entityplayer);
        for(LeafHealInfo healing : this.leafHealings) {
            healing.sendData(entityplayer);
        }
    }

    private void sendHealInfoToWatchers(LeafHealInfo healing) {
        int x = MathHelper.floor_double(this.posX) >> 4;
        int z = MathHelper.floor_double(this.posZ) >> 4;
        PlayerManager playermanager = ((WorldServer) this.worldObj).getPlayerManager();
        List players = this.worldObj.playerEntities;
        for(Object obj : players) {
            EntityPlayerMP entityplayer = (EntityPlayerMP) obj;
            if(!playermanager.isPlayerWatchingChunk(entityplayer, x, z)) continue;
            healing.sendData(entityplayer);
        }
    }

    public void receiveClientHealing(NBTTagCompound data) {
        LeafHealInfo healing = new LeafHealInfo(this, 0);
        healing.receiveData(data);
        this.leafHealings[healing.slot] = healing;
    }

    public void spawnEntSummonParticles(LOTREntityTree tree) {
        int l;
        int type = tree.getTreeType();
        Block leafBlock = LOTREntityTree.LEAF_BLOCKS[type];
        int leafMeta = LOTREntityTree.LEAF_META[type];
        int particles = 60;
        for(l = 0; l < particles; ++l) {
            float t = (float) l / (float) particles;
            LOTRMod.proxy.spawnParticle("mEntSummon_" + this.getEntityId() + "_" + tree.getEntityId() + "_" + t + "_" + Block.getIdFromBlock(leafBlock) + "_" + leafMeta, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        for(l = 0; l < 120; ++l) {
            double d = tree.posX + (this.rand.nextDouble() - 0.5) * tree.width;
            double d1 = tree.posY + tree.height * 0.5;
            double d2 = tree.posZ + (this.rand.nextDouble() - 0.5) * tree.width;
            double d3 = MathHelper.getRandomDoubleInRange(this.rand, -0.4, 0.4);
            double d4 = MathHelper.getRandomDoubleInRange(this.rand, -0.4, 0.4);
            double d5 = MathHelper.getRandomDoubleInRange(this.rand, -0.4, 0.4);
            LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(leafBlock) + "_" + leafMeta, d, d1, d2, d3, d4, d5);
        }
    }

    @Override
    protected boolean isMovementBlocked() {
        if(this.getEntSpawnTick() < SPAWN_TIME) {
            return true;
        }
        return super.isMovementBlocked();
    }

    @Override
    public void onJumpAttackFall() {
        this.worldObj.setEntityState(this, (byte) 20);
        this.playSound("lotr:troll.rockSmash", 1.5f, 0.75f);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        int i;
        if(b == 20) {
            for(i = 0; i < 360; i += 2) {
                float angle = (float) Math.toRadians(i);
                double distance = 2.0;
                double d = distance * MathHelper.sin(angle);
                double d1 = distance * MathHelper.cos(angle);
                LOTRMod.proxy.spawnParticle("mEntJumpSmash", this.posX + d, this.boundingBox.minY + 0.1, this.posZ + d1, d * 0.2, 0.2, d1 * 0.2);
            }
        }
        if(b == 21) {
            for(i = 0; i < 200; ++i) {
                double d = this.posX;
                double d1 = this.posY + this.height * 0.5f;
                double d2 = this.posZ;
                double d3 = MathHelper.getRandomDoubleInRange(this.rand, -0.1, 0.1);
                double d4 = MathHelper.getRandomDoubleInRange(this.rand, -0.1, 0.1);
                double d5 = MathHelper.getRandomDoubleInRange(this.rand, -0.1, 0.1);
                int time = 40 + this.rand.nextInt(30);
                LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d1, d2, d3, d4, d5);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        NBTTagList leafHealingTags = new NBTTagList();
        for(LeafHealInfo healing : this.leafHealings) {
            NBTTagCompound healTag = new NBTTagCompound();
            healing.writeToNBT(healTag);
            leafHealingTags.appendTag(healTag);
        }
        nbt.setTag("LeafHealings", leafHealingTags);
        nbt.setInteger("EntSpawnTick", this.getEntSpawnTick());
        nbt.setBoolean("EntWeaponShield", this.hasWeaponShield());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.resetLeafHealings();
        NBTTagList leafHealingTags = nbt.getTagList("LeafHealings", 10);
        for(int i = 0; i < leafHealingTags.tagCount(); ++i) {
            NBTTagCompound healTag = leafHealingTags.getCompoundTagAt(i);
            byte slot = healTag.getByte("Slot");
            if(slot < 0 || slot >= this.leafHealings.length) continue;
            LeafHealInfo healing = this.leafHealings[slot];
            healing.readFromNBT(healTag);
        }
        this.setEntSpawnTick(nbt.getInteger("EntSpawnTick"));
        this.setHasWeaponShield(nbt.getBoolean("EntWeaponShield"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if(this.getEntSpawnTick() < SPAWN_TIME) {
            return false;
        }
        if(LOTRMod.getDamagingPlayerIncludingUnits(damagesource) == null && f > 1.0f) {
            f = 1.0f;
        }
        if(!this.isTreeEffectiveDamage(damagesource)) {
            f *= 0.5f;
        }
        if(this.isWeaponShieldActive() && !damagesource.isFireDamage()) {
            f = 0.0f;
        }
        boolean flag = super.attackEntityFrom(damagesource, f);
        return flag;
    }

    @Override
    protected boolean doTreeDamageCalculation() {
        return false;
    }

    @Override
    protected void damageEntity(DamageSource damagesource, float f) {
        super.damageEntity(damagesource, f);
        if(!this.worldObj.isRemote && !this.hasWeaponShield() && this.getHealth() <= 0.0f) {
            this.setHasWeaponShield(true);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
            this.setHealth(this.getMaxHealth());
            this.sendEntBossSpeech("shield");
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if(!this.worldObj.isRemote) {
            this.worldObj.setEntityState(this, (byte) 21);
            int fireRange = 12;
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY);
            int k = MathHelper.floor_double(this.posZ);
            for(int i1 = i - fireRange; i1 <= i + fireRange; ++i1) {
                for(int j1 = j - fireRange; j1 <= j + fireRange; ++j1) {
                    for(int k1 = k - fireRange; k1 <= k + fireRange; ++k1) {
                        Block block = this.worldObj.getBlock(i1, j1, k1);
                        if(!(block instanceof BlockFire)) continue;
                        this.worldObj.setBlockToAir(i1, j1, k1);
                    }
                }
            }
        }
        super.onDeath(damagesource);
    }

    @Override
    public void dropFewItems(boolean flag, int i) {
        int dropped;
        int dropped2;
        super.dropFewItems(flag, i);
        for(int wood = MathHelper.getRandomIntegerInRange(this.rand, 20, 30 + i * 20); wood > 0; wood -= dropped2) {
            dropped2 = Math.min(20, wood);
            this.entityDropItem(new ItemStack(LOTRMod.wood, dropped2, 1), 0.0f);
        }
        for(int sticks = MathHelper.getRandomIntegerInRange(this.rand, 30, 40 + i * 20); sticks > 0; sticks -= dropped) {
            dropped = Math.min(20, sticks);
            this.entityDropItem(new ItemStack(LOTRMod.mallornStick, dropped), 0.0f);
        }
        this.entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MALLORN_ENT.trophyID), 0.0f);
        float maceChance = 0.3f;
        maceChance += i * 0.1f;
        if(this.rand.nextFloat() < maceChance) {
            this.dropItem(LOTRMod.maceMallornCharred, 1);
        }
    }

    @Override
    public LOTRAchievement getBossKillAchievement() {
        return LOTRAchievement.killMallornEnt;
    }

    @Override
    public float getAlignmentBonus() {
        return 50.0f;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 100;
    }

    @Override
    protected LOTRAchievement getTalkAchievement() {
        return null;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return null;
    }

    private static class LeafHealInfo {
        private LOTREntityMallornEnt theEnt;
        private int slot;
        private boolean active;
        private int leafX;
        private int leafY;
        private int leafZ;
        private int healTime;

        public LeafHealInfo(LOTREntityMallornEnt ent, int i) {
            this.theEnt = ent;
            this.slot = i;
        }

        public void writeToNBT(NBTTagCompound nbt) {
            nbt.setByte("Slot", (byte) this.slot);
            nbt.setBoolean("Active", this.active);
            nbt.setInteger("X", this.leafX);
            nbt.setInteger("Y", this.leafY);
            nbt.setInteger("Z", this.leafZ);
            nbt.setShort("Time", (short) this.healTime);
        }

        public void readFromNBT(NBTTagCompound nbt) {
            this.slot = nbt.getByte("Slot");
            this.active = nbt.getBoolean("Active");
            this.leafX = nbt.getInteger("X");
            this.leafY = nbt.getInteger("Y");
            this.leafZ = nbt.getInteger("Z");
            this.healTime = nbt.getShort("healTime");
        }

        public void sendData(EntityPlayerMP entityplayer) {
            NBTTagCompound nbt = new NBTTagCompound();
            this.writeToNBT(nbt);
            LOTRPacketMallornEntHeal packet = new LOTRPacketMallornEntHeal(this.theEnt.getEntityId(), nbt);
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
        }

        public void receiveData(NBTTagCompound nbt) {
            this.readFromNBT(nbt);
        }
    }

}
