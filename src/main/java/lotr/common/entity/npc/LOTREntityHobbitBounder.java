package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.*;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.entity.projectile.LOTREntityPebble;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityHobbitBounder
extends LOTREntityHobbit {
    public EntityAIBase rangedAttackAI = this.createHobbitRangedAttackAI();
    public EntityAIBase meleeAttackAI = this.createHobbitMeleeAttackAI();

    public LOTREntityHobbitBounder(World world) {
        super(world);
        this.tasks.taskEntries.clear();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new LOTREntityAIHiredRemainStill(this));
        this.tasks.addTask(3, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest2(this, EntityPlayer.class, 8.0f, 0.02f));
        this.tasks.addTask(6, new EntityAIWatchClosest2(this, LOTREntityNPC.class, 5.0f, 0.02f));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f, 0.02f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        int target = this.addTargetTasks(true);
        this.targetTasks.addTask(target + 1, new LOTREntityAIHobbitTargetRuffian(this, LOTREntityBreeRuffian.class, 0, true));
        this.spawnRidingHorse = this.rand.nextInt(3) == 0;
    }

    public EntityAIBase createHobbitRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.5, 20, 40, 12.0f);
    }

    public EntityAIBase createHobbitMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(horseAttackSpeed).setBaseValue(2.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(3);
        if (i == 0 || i == 1) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        } else if (i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.sling));
        this.npcItemsInv.setIdleItem(null);
        ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 6834742);
        LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        return new LOTREntityShirePony(this.worldObj);
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if (mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        if (mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
        if (mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getRangedWeapon());
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float f) {
        EntityArrow template = new EntityArrow(this.worldObj, this, target, 1.0f, 0.5f);
        LOTREntityPebble pebble = new LOTREntityPebble(this.worldObj, this).setSling();
        pebble.setLocationAndAngles(template.posX, template.posY, template.posZ, template.rotationYaw, template.rotationPitch);
        pebble.motionX = template.motionX;
        pebble.motionY = template.motionY;
        pebble.motionZ = template.motionZ;
        this.playSound("random.bow", 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(pebble);
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int dropChance = 10 - i * 2;
        if (dropChance < 1) {
            dropChance = 1;
        }
        if (this.rand.nextInt(dropChance) == 0) {
            this.dropItem(LOTRMod.pebble, 1 + this.rand.nextInt(3) + this.rand.nextInt(i + 1));
        }
    }

    @Override
    protected int getExperiencePoints(EntityPlayer entityplayer) {
        return 2 + this.rand.nextInt(3);
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "hobbit/bounder/hired";
            }
            return "hobbit/bounder/friendly";
        }
        return "hobbit/bounder/hostile";
    }
}

