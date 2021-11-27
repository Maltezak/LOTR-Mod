package lotr.common.entity.npc;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMountainTroll extends LOTREntityTroll {
    public static IAttribute thrownRockDamage = new RangedAttribute("lotr.thrownRockDamage", 5.0, 0.0, 100.0).setDescription("LOTR Thrown Rock Damage");
    private EntityAIBase rangedAttackAI = this.getTrollRangedAttackAI();
    private EntityAIBase meleeAttackAI;

    public LOTREntityMountainTroll(World world) {
        super(world);
    }

    @Override
    public float getTrollScale() {
        return 1.6f;
    }

    @Override
    public EntityAIBase getTrollAttackAI() {
        this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.8, false);
        return this.meleeAttackAI;
    }

    protected EntityAIBase getTrollRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.2, 30, 60, 24.0f);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(21, (byte) 0);
    }

    public boolean isThrowingRocks() {
        return this.dataWatcher.getWatchableObjectByte(21) == 1;
    }

    public void setThrowingRocks(boolean flag) {
        this.dataWatcher.updateObject(21, flag ? (byte) 1 : 0);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0);
        this.getEntityAttribute(npcAttackDamage).setBaseValue(7.0);
        this.getAttributeMap().registerAttribute(thrownRockDamage);
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
    public double getMeleeRange() {
        return 12.0;
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setThrowingRocks(false);
        }
        if(mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(3, this.meleeAttackAI);
            this.setThrowingRocks(false);
        }
        if(mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(3, this.rangedAttackAI);
            this.setThrowingRocks(true);
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        EntityArrow template = new EntityArrow(this.worldObj, this, target, f * 1.5f, 0.5f);
        LOTREntityThrownRock rock = this.getThrownRock();
        rock.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
        rock.motionX = template.motionX;
        rock.motionY = template.motionY + 0.6;
        rock.motionZ = template.motionZ;
        this.worldObj.spawnEntityInWorld(rock);
        this.playSound(this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch() * 0.75f);
        this.swingItem();
    }

    protected LOTREntityThrownRock getThrownRock() {
        LOTREntityThrownRock rock = new LOTREntityThrownRock(this.worldObj, this);
        rock.setDamage((float) this.getEntityAttribute(thrownRockDamage).getAttributeValue());
        return rock;
    }

    @Override
    public void onTrollDeathBySun() {
        this.worldObj.playSoundAtEntity(this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        this.worldObj.setEntityState(this, (byte) 15);
        this.setDead();
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 15) {
            super.handleHealthUpdate(b);
            for(int l = 0; l < 64; ++l) {
                LOTRMod.proxy.spawnParticle("largeStone", this.posX + this.rand.nextGaussian() * this.width * 0.5, this.posY + this.rand.nextDouble() * this.height, this.posZ + this.rand.nextGaussian() * this.width * 0.5, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }

    @Override
    public void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int totemChance = 15 - i * 3;
        totemChance = Math.max(totemChance, 1);
        if(this.rand.nextInt(totemChance) == 0) {
            this.entityDropItem(new ItemStack(LOTRMod.trollTotem, 1, this.rand.nextInt(3)), 0.0f);
        }
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMountainTroll;
    }

    @Override
    public float getAlignmentBonus() {
        return 4.0f;
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 7 + this.rand.nextInt(6);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return null;
    }
}
