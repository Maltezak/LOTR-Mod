package lotr.common.entity.npc;

import java.util.List;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.*;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class LOTREntityBreeRuffian
extends LOTREntityBreeMan {
    private static ItemStack[] ruffianWeapons = new ItemStack[]{new ItemStack(Items.iron_sword), new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.battleaxeIron)};
    private int ruffianAngerTick;

    public LOTREntityBreeRuffian(World world) {
        super(world);
        int target = this.addTargetTasks(false);
        this.targetTasks.addTask(target + 1, new LOTREntityAINearestAttackableTargetBasic(this, EntityPlayer.class, 0, true, new IEntitySelector(){

            public boolean isEntityApplicable(Entity entity) {
                EntityPlayer player = (EntityPlayer)entity;
                return LOTREntityBreeRuffian.this.canRuffianTarget(player);
            }
        }));
    }

    @Override
    protected void addBreeAvoidAI(int prio) {
        this.tasks.addTask(prio, new EntityAIAvoidEntity(this, LOTREntityRanger.class, 12.0f, 1.0, 1.5));
        this.tasks.addTask(prio, new EntityAIAvoidEntity(this, LOTREntityBreeGuard.class, 12.0f, 1.0, 1.5));
    }

    public final boolean canRuffianTarget(EntityPlayer player) {
        PotionEffect nausea = player.getActivePotionEffect(Potion.confusion);
        if (nausea != null) {
            int nauseaTime = nausea.getDuration() / 20;
            int minNauseaTime = 20;
            int fullNauseaTime = 120;
            float chance = (float)(nauseaTime - minNauseaTime) / (float)(fullNauseaTime - minNauseaTime);
            return this.rand.nextFloat() < (chance *= 0.05f);
        }
        return false;
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public void setupNPCName() {
        boolean flag = this.rand.nextBoolean();
        if (flag) {
            this.familyInfo.setName(LOTRNames.getDunlendingName(this.rand, this.familyInfo.isMale()));
        } else {
            this.familyInfo.setName(LOTRNames.getBreeName(this.rand, this.familyInfo.isMale()));
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(ruffianWeapons.length);
        this.npcItemsInv.setMeleeWeapon(ruffianWeapons[i].copy());
        if (this.rand.nextInt(4) == 0) {
            ItemStack hat = new ItemStack(LOTRMod.leatherHat);
            if (this.rand.nextBoolean()) {
                LOTRItemLeatherHat.setHatColor(hat, 0);
            } else {
                LOTRItemLeatherHat.setHatColor(hat, 6834742);
            }
            LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
            this.setCurrentItemOrArmor(4, hat);
        }
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.RUFFIAN;
    }

    @Override
    public LOTRFaction getInfluenceZoneFaction() {
        return LOTRFaction.ISENGARD;
    }

    @Override
    public boolean isCivilianNPC() {
        return false;
    }

    @Override
    public float getAlignmentBonus() {
        return 0.0f;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        int coins = 2 + this.rand.nextInt(3) + this.rand.nextInt((i + 1) * 3);
        for (int l = 0; l < coins; ++l) {
            this.dropItem(LOTRMod.silverCoin, 1);
        }
        if (this.rand.nextInt(5) == 0) {
            this.entityDropItem(LOTRItemMug.Vessel.SKULL.getEmptyVessel(), 0.0f);
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.ruffianAngerTick > 0) {
            --this.ruffianAngerTick;
        }
    }

    @Override
    public boolean lootsExtraCoins() {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float f) {
        if (super.attackEntityFrom(damagesource, f)) {
            if (!this.worldObj.isRemote && damagesource.getEntity() instanceof EntityLivingBase) {
                EntityLivingBase attacker = (EntityLivingBase)damagesource.getEntity();
                this.ruffianAngerTick += 100;
                double range = this.ruffianAngerTick / 25.0;
                range = Math.min(range, 24.0);
                List nearbyRuffians = this.worldObj.getEntitiesWithinAABB(LOTREntityBreeRuffian.class, this.boundingBox.expand(range, range, range));
                for (Object o : nearbyRuffians) {
                    LOTREntityBreeRuffian ruffian = (LOTREntityBreeRuffian)o;
                    if (!ruffian.isEntityAlive() || ruffian.hiredNPCInfo.isActive && ruffian.hiredNPCInfo.getHiringPlayer() == attacker || ruffian.getAttackTarget() != null) continue;
                    ruffian.setAttackTarget(attacker);
                    if (!(attacker instanceof EntityPlayer)) continue;
                    EntityPlayer player = (EntityPlayer)attacker;
                    String speech = ruffian.getSpeechBank(player);
                    ruffian.sendSpeechBank(player, speech);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "bree/ruffian/hired";
            }
            return "bree/ruffian/friendly";
        }
        return "bree/ruffian/hostile";
    }

    @Override
    public int getMiniquestColor() {
        return LOTRFaction.ISENGARD.getFactionColor();
    }

    @Override
    public boolean canPickpocket() {
        return false;
    }

}

