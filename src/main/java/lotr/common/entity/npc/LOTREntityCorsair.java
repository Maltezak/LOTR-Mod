package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.*;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityCorsair extends LOTREntityUmbarian {
    private EntityAIBase rangedAttackAI = this.createHaradrimRangedAttackAI();
    private EntityAIBase meleeAttackAI;
    private static ItemStack[] weaponsCorsair = new ItemStack[] {new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.swordCorsair), new ItemStack(LOTRMod.daggerCorsair), new ItemStack(LOTRMod.daggerCorsairPoisoned), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.spearCorsair), new ItemStack(LOTRMod.battleaxeCorsair), new ItemStack(LOTRMod.battleaxeCorsair)};

    public LOTREntityCorsair(World world) {
        super(world);
        this.addTargetTasks(true);
        this.spawnRidingHorse = false;
        this.npcShield = LOTRShields.ALIGNMENT_CORSAIR;
    }

    @Override
    protected EntityAIBase createHaradrimAttackAI() {
        this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.6, true);
        return this.meleeAttackAI;
    }

    protected EntityAIBase createHaradrimRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.5, 30, 40, 16.0f);
    }

    @Override
    protected LOTRFoods getHaradrimFoods() {
        return LOTRFoods.CORSAIR;
    }

    @Override
    protected LOTRFoods getHaradrimDrinks() {
        return LOTRFoods.CORSAIR_DRINK;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(npcRangedAccuracy).setBaseValue(0.5);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(weaponsCorsair.length);
        this.npcItemsInv.setMeleeWeapon(weaponsCorsair[i].copy());
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearCorsair));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.nearHaradBow));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsCorsair));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsCorsair));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyCorsair));
        if(this.rand.nextInt(2) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetCorsair));
        }
        return data;
    }

    @Override
    public void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        if(mode == LOTREntityNPC.AttackMode.MELEE) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
        if(mode == LOTREntityNPC.AttackMode.RANGED) {
            this.tasks.removeTask(this.meleeAttackAI);
            this.tasks.removeTask(this.rangedAttackAI);
            this.tasks.addTask(2, this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getRangedWeapon());
        }
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        if(entity instanceof LOTREntityNPC && ((LOTREntityNPC) entity).canDropRares() && this.rand.nextInt(2) == 0) {
            int coins = this.getRandomCoinDropAmount();
            if((coins = (int) (coins * MathHelper.randomFloatClamp(this.rand, 1.0f, 3.0f))) > 0) {
                entity.dropItem(LOTRMod.silverCoin, coins);
            }
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        this.dropNPCArrows(i);
    }

    @Override
    protected void dropHaradrimItems(boolean flag, int i) {
        if(this.rand.nextInt(3) == 0) {
            this.dropChestContents(LOTRChestContents.CORSAIR, 1, 2 + i);
        }
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "nearHarad/umbar/corsair/hired";
            }
            return "nearHarad/umbar/corsair/friendly";
        }
        return "nearHarad/umbar/corsair/hostile";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.CORSAIR.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.CORSAIR;
    }
}
