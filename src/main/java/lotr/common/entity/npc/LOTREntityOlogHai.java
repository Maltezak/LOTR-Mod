package lotr.common.entity.npc;

import java.util.List;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityOlogHai extends LOTREntityTroll {
    public LOTREntityOlogHai(World world) {
        super(world);
        this.tasks.taskEntries.clear();
        this.targetTasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(2, new LOTREntityAIAttackOnCollide(this, 2.0, false));
        this.tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0));
        this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityPlayer.class, 12.0f, 0.02f));
        this.tasks.addTask(5, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 8.0f, 0.02f));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 12.0f, 0.01f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
        this.trollImmuneToSun = true;
    }

    @Override
    public float getTrollScale() {
        return 1.25f;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
    }

    @Override
    public int getTotalArmorValue() {
        return 15;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }

    @Override
    protected boolean hasTrollName() {
        return false;
    }

    @Override
    protected boolean canTrollBeTickled(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            List entities;
            float attackDamage = (float) this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            float knockbackModifier = 0.25f * attackDamage;
            entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f, 0.0, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f);
            this.worldObj.playSoundAtEntity(entity, "lotr:troll.ologHai_hammer", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            if(!this.worldObj.isRemote && !(entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, entity.boundingBox.expand(4.0, 4.0, 4.0))).isEmpty()) {
                for(Object entitie : entities) {
                    EntityLivingBase hitEntity = (EntityLivingBase) entitie;
                    if(hitEntity == this || hitEntity == entity || !LOTRMod.canNPCAttackEntity(this, hitEntity, false)) continue;
                    float strength = 4.0f - entity.getDistanceToEntity(hitEntity);
                    if((strength += 1.0f) > 4.0f) {
                        strength = 4.0f;
                    }
                    if(!hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), strength / 4.0f * attackDamage)) continue;
                    float knockback = strength * 0.25f;
                    if(knockback < 0.75f) {
                        knockback = 0.75f;
                    }
                    hitEntity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f, 0.2 + 0.12 * knockback, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killOlogHai;
    }

    @Override
    public float getAlignmentBonus() {
        return 4.0f;
    }

    @Override
    public void dropTrollItems(boolean flag, int i) {
        if(flag) {
            int rareDropChance = 8 - i;
            if(rareDropChance < 1) {
                rareDropChance = 1;
            }
            if(this.rand.nextInt(rareDropChance) == 0) {
                int drops = 1 + this.rand.nextInt(2) + this.rand.nextInt(i + 1);
                for(int j = 0; j < drops; ++j) {
                    this.dropItem(LOTRMod.orcSteel, 1);
                }
            }
        }
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 5 + this.rand.nextInt(8);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return null;
    }
}
