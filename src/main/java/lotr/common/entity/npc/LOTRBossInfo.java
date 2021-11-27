package lotr.common.entity.npc;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

import lotr.common.*;
import lotr.common.entity.ai.LOTRNPCTargetSelector;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class LOTRBossInfo {
    private LOTREntityNPC theNPC;
    private LOTRBoss theBoss;
    public EntityPlayer lastAttackingPlayer;
    private Map<UUID, Pair<Integer, Float>> playerHurtTimes = new HashMap<>();
    private static int PLAYER_HURT_COOLDOWN = 600;
    private static float PLAYER_DAMAGE_THRESHOLD = 40.0f;
    public boolean jumpAttack;

    public LOTRBossInfo(LOTRBoss boss) {
        this.theBoss = boss;
        this.theNPC = (LOTREntityNPC) (this.theBoss);
    }

    public float getHealthChanceModifier() {
        float f = 1.0f - this.theNPC.getHealth() / this.theNPC.getMaxHealth();
        return MathHelper.sqrt_float(f);
    }

    public List getNearbyEnemies() {
        ArrayList<EntityPlayer> enemies = new ArrayList<>();
        List players = this.theNPC.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.theNPC.boundingBox.expand(12.0, 6.0, 12.0));
        for(Object player : players) {
            EntityPlayer entityplayer = (EntityPlayer) player;
            if(entityplayer.capabilities.isCreativeMode || (LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction()) >= 0.0f)) continue;
            enemies.add(entityplayer);
        }
        enemies.addAll(this.theNPC.worldObj.selectEntitiesWithinAABB(EntityLiving.class, this.theNPC.boundingBox.expand(12.0, 6.0, 12.0), new LOTRNPCTargetSelector(this.theNPC)));
        return enemies;
    }

    public void doJumpAttack(double jumpSpeed) {
        this.jumpAttack = true;
        this.theNPC.motionY = jumpSpeed;
    }

    public void doTargetedJumpAttack(double jumpSpeed) {
        if(!this.theNPC.worldObj.isRemote && this.lastAttackingPlayer != null && (this.lastAttackingPlayer.posY - this.theNPC.posY > 10.0 || this.theNPC.getDistanceSqToEntity(this.lastAttackingPlayer) > 400.0) && this.theNPC.onGround) {
            this.doJumpAttack(jumpSpeed);
            this.theNPC.motionX = this.lastAttackingPlayer.posX - this.theNPC.posX;
            this.theNPC.motionY = this.lastAttackingPlayer.posY - this.theNPC.posY;
            this.theNPC.motionZ = this.lastAttackingPlayer.posZ - this.theNPC.posZ;
            this.theNPC.motionX /= 10.0;
            this.theNPC.motionY /= 10.0;
            this.theNPC.motionZ /= 10.0;
            if(this.theNPC.motionY < jumpSpeed) {
                this.theNPC.motionY = jumpSpeed;
            }
            this.theNPC.getLookHelper().setLookPositionWithEntity(this.lastAttackingPlayer, 100.0f, 100.0f);
            this.theNPC.getLookHelper().onUpdateLook();
            this.theNPC.rotationYaw = this.theNPC.rotationYawHead;
        }
    }

    public float onFall(float fall) {
        if(!this.theNPC.worldObj.isRemote && this.jumpAttack) {
            fall = 0.0f;
            this.jumpAttack = false;
            List enemies = this.getNearbyEnemies();
            float attackDamage = (float) this.theNPC.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            for(Object enemie : enemies) {
                EntityLivingBase entity = (EntityLivingBase) enemie;
                float strength = 12.0f - this.theNPC.getDistanceToEntity(entity) / 3.0f;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this.theNPC), (strength /= 12.0f) * attackDamage * 3.0f);
                float knockback = strength * 3.0f;
                entity.addVelocity(-MathHelper.sin(this.theNPC.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f, 0.25 * knockback, MathHelper.cos(this.theNPC.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f);
            }
            this.theBoss.onJumpAttackFall();
        }
        return fall;
    }

    private void clearSurroundingBlocks() {
        if(LOTRMod.canGrief(this.theNPC.worldObj)) {
            int xzRange = MathHelper.ceiling_float_int(this.theNPC.width / 2.0f * 1.5f);
            int yRange = MathHelper.ceiling_float_int(this.theNPC.height * 1.5f);
            int xzDist = xzRange * xzRange + xzRange * xzRange;
            int i = MathHelper.floor_double(this.theNPC.posX);
            int j = MathHelper.floor_double(this.theNPC.boundingBox.minY);
            int k = MathHelper.floor_double(this.theNPC.posZ);
            for(int i1 = i - xzRange; i1 <= i + xzRange; ++i1) {
                for(int j1 = j; j1 <= j + yRange; ++j1) {
                    for(int k1 = k - xzRange; k1 <= k + xzRange; ++k1) {
                        float resistance;
                        Block block;
                        int i2 = i1 - i;
                        int k2 = k1 - k;
                        int dist = i2 * i2 + k2 * k2;
                        if(dist >= xzDist || (block = this.theNPC.worldObj.getBlock(i1, j1, k1)) == null || block.getMaterial().isLiquid() || ((resistance = block.getExplosionResistance(this.theNPC, this.theNPC.worldObj, i1, j1, k1, this.theNPC.posX, this.theNPC.boundingBox.minY + this.theNPC.height / 2.0f, this.theNPC.posZ)) >= 2000.0f)) continue;
                        block.dropBlockAsItemWithChance(this.theNPC.worldObj, i1, j1, k1, this.theNPC.worldObj.getBlockMetadata(i1, j1, k1), resistance / 100.0f, 0);
                        this.theNPC.worldObj.setBlockToAir(i1, j1, k1);
                    }
                }
            }
        }
    }

    public void onUpdate() {
        if(this.lastAttackingPlayer != null && (!this.lastAttackingPlayer.isEntityAlive() || this.lastAttackingPlayer.capabilities.isCreativeMode)) {
            this.lastAttackingPlayer = null;
        }
        if(!this.theNPC.worldObj.isRemote) {
            HashMap<UUID, Pair<Integer, Float>> playerHurtTimes_new = new HashMap<>();
            for(Map.Entry<UUID, Pair<Integer, Float>> entry : this.playerHurtTimes.entrySet()) {
                UUID player = entry.getKey();
                int time = entry.getValue().getLeft();
                float damage = entry.getValue().getRight();
                if(--time <= 0) continue;
                playerHurtTimes_new.put(player, Pair.of(time, damage));
            }
            this.playerHurtTimes = playerHurtTimes_new;
        }
        if(!this.theNPC.worldObj.isRemote && this.jumpAttack && this.theNPC.ticksExisted % 5 == 0) {
            this.clearSurroundingBlocks();
        }
    }

    public void onHurt(DamageSource damagesource, float f) {
        if(!this.theNPC.worldObj.isRemote) {
            EntityPlayer playerSource;
            if(damagesource.getEntity() instanceof EntityPlayer) {
                EntityPlayer attackingPlayer = (EntityPlayer) damagesource.getEntity();
                if(!attackingPlayer.capabilities.isCreativeMode) {
                    this.lastAttackingPlayer = attackingPlayer;
                }
            }
            if((playerSource = LOTRMod.getDamagingPlayerIncludingUnits(damagesource)) != null) {
                UUID player = playerSource.getUniqueID();
                int time = PLAYER_HURT_COOLDOWN;
                float totalDamage = f;
                if(this.playerHurtTimes.containsKey(player)) {
                    Pair<Integer, Float> pair = this.playerHurtTimes.get(player);
                    totalDamage += pair.getRight();
                }
                this.playerHurtTimes.put(player, Pair.of(time, totalDamage));
            }
        }
    }

    public void onDeath(DamageSource damagesource) {
        this.onHurt(damagesource, 0.0f);
        if(!this.theNPC.worldObj.isRemote) {
            for(Map.Entry<UUID, Pair<Integer, Float>> e : this.playerHurtTimes.entrySet()) {
                UUID player = e.getKey();
                Pair<Integer, Float> pair = e.getValue();
                float damage = pair.getRight();
                if((damage < PLAYER_DAMAGE_THRESHOLD)) continue;
                LOTRLevelData.getData(player).addAchievement(this.theBoss.getBossKillAchievement());
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound data = new NBTTagCompound();
        NBTTagList playerHurtTags = new NBTTagList();
        for(Map.Entry<UUID, Pair<Integer, Float>> entry : this.playerHurtTimes.entrySet()) {
            UUID player = entry.getKey();
            Pair<Integer, Float> pair = entry.getValue();
            int time = pair.getLeft();
            float damage = pair.getRight();
            NBTTagCompound playerTag = new NBTTagCompound();
            playerTag.setString("UUID", player.toString());
            playerTag.setInteger("Time", time);
            playerTag.setFloat("Damage", damage);
            playerHurtTags.appendTag(playerTag);
        }
        data.setTag("PlayerHurtTimes", playerHurtTags);
        data.setBoolean("JumpAttack", this.jumpAttack);
        nbt.setTag("NPCBossInfo", data);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound data = nbt.getCompoundTag("NPCBossInfo");
        if(data != null) {
            NBTTagList playerHurtTags = data.getTagList("PlayerHurtTimes", 10);
            for(int i = 0; i < playerHurtTags.tagCount(); ++i) {
                NBTTagCompound playerTag = playerHurtTags.getCompoundTagAt(i);
                UUID player = UUID.fromString(playerTag.getString("UUID"));
                if(player == null) continue;
                int time = playerTag.getInteger("Time");
                float damage = playerTag.getFloat("Damage");
                this.playerHurtTimes.put(player, Pair.of(time, damage));
            }
            this.jumpAttack = data.getBoolean("JumpAttack");
        }
    }
}
