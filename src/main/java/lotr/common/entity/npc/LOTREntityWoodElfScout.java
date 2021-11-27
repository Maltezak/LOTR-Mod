package lotr.common.entity.npc;

import java.util.UUID;

import cpw.mods.fml.relauncher.*;
import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityWoodElfScout extends LOTREntityWoodElf {
    private static final UUID scoutArmorSpeedBoost_id = UUID.fromString("cf0ceb91-0f13-4788-be0e-a6c67a830308");
    public static final AttributeModifier scoutArmorSpeedBoost = new AttributeModifier(scoutArmorSpeedBoost_id, "WE Scout armor speed boost", 0.3, 2).setSaved(false);

    public LOTREntityWoodElfScout(World world) {
        super(world);
        this.tasks.addTask(2, this.rangedAttackAI);
    }

    @Override
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack(this, 1.25, 25, 35, 24.0f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getRangedWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsWoodElvenScout));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsWoodElvenScout));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyWoodElvenScout));
        if(this.rand.nextInt(10) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetWoodElvenScout));
        }
        return data;
    }

    @Override
    public void onLivingUpdate() {
        ItemStack currentItem;
        EntityLivingBase lastAttacker;
        super.onLivingUpdate();
        if(!this.worldObj.isRemote && this.isEntityAlive() && this.ridingEntity == null && (currentItem = this.getEquipmentInSlot(0)) != null && currentItem.getItem() instanceof ItemBow && (lastAttacker = this.getAITarget()) != null && this.getDistanceSqToEntity(lastAttacker) < 16.0 && this.rand.nextInt(20) == 0) {
            for(int l = 0; l < 32; ++l) {
                int k;
                int j;
                int i = MathHelper.floor_double(this.posX) - this.rand.nextInt(16) + this.rand.nextInt(16);
                if((this.getDistance(i, j = MathHelper.floor_double(this.posY) - this.rand.nextInt(3) + this.rand.nextInt(3), k = MathHelper.floor_double(this.posZ) - this.rand.nextInt(16) + this.rand.nextInt(16)) <= 6.0) || !this.worldObj.getBlock(i, j - 1, k).isNormalCube() || this.worldObj.getBlock(i, j, k).isNormalCube() || this.worldObj.getBlock(i, j + 1, k).isNormalCube()) continue;
                double d = i + 0.5;
                double d1 = j;
                double d2 = k + 0.5;
                AxisAlignedBB aabb = this.boundingBox.copy().offset(d - this.posX, d1 - this.posY, d2 - this.posZ);
                if(!this.worldObj.checkNoEntityCollision(aabb) || !this.worldObj.getCollidingBoundingBoxes(this, aabb).isEmpty() || this.worldObj.isAnyLiquid(aabb)) continue;
                this.doTeleportEffects();
                this.setPosition(d, d1, d2);
                break;
            }
        }
    }

    private void doTeleportEffects() {
        this.worldObj.playSoundAtEntity(this, "lotr:elf.woodElf_teleport", this.getSoundVolume(), 0.5f + this.rand.nextFloat());
        this.worldObj.setEntityState(this, (byte) 15);
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte b) {
        if(b == 15) {
            for(int i = 0; i < 16; ++i) {
                double d = this.posX + (this.rand.nextDouble() - 0.5) * this.width;
                double d1 = this.posY + this.rand.nextDouble() * this.height;
                double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * this.width;
                double d3 = -0.05 + this.rand.nextFloat() * 0.1f;
                double d4 = -0.05 + this.rand.nextFloat() * 0.1f;
                double d5 = -0.05 + this.rand.nextFloat() * 0.1f;
                LOTRMod.proxy.spawnParticle("leafGreen_" + (20 + this.rand.nextInt(30)), d, d1, d2, d3, d4, d5);
            }
        }
        else {
            super.handleHealthUpdate(b);
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
                return "woodElf/elf/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= LOTREntityWoodElf.getWoodlandTrustLevel()) {
                return "woodElf/warrior/friendly";
            }
            return "woodElf/elf/neutral";
        }
        return "woodElf/warrior/hostile";
    }
}
