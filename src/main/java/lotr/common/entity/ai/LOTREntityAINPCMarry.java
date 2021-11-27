package lotr.common.entity.ai;

import java.util.List;

import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAINPCMarry
extends EntityAIBase {
    private LOTREntityNPC theNPC;
    private World theWorld;
    private LOTREntityNPC theSpouse;
    private int marryDelay = 0;
    private double moveSpeed;

    public LOTREntityAINPCMarry(LOTREntityNPC npc, double d) {
        this.theNPC = npc;
        this.theWorld = npc.worldObj;
        this.moveSpeed = d;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        if (this.theNPC.getClass() != this.theNPC.familyInfo.marriageEntityClass || this.theNPC.familyInfo.spouseUniqueID != null || this.theNPC.familyInfo.getAge() != 0 || this.theNPC.getEquipmentInSlot(4) != null || this.theNPC.getEquipmentInSlot(0) == null) {
            return false;
        }
        List<LOTREntityNPC> list = this.theNPC.worldObj.getEntitiesWithinAABB(this.theNPC.familyInfo.marriageEntityClass, this.theNPC.boundingBox.expand(16.0, 4.0, 16.0));
        LOTREntityNPC spouse = null;
        double distanceSq = Double.MAX_VALUE;
        for (LOTREntityNPC candidate : list) {
            double d;
            if (!this.theNPC.familyInfo.canMarryNPC(candidate) || !candidate.familyInfo.canMarryNPC(this.theNPC) || ((d = this.theNPC.getDistanceSqToEntity(candidate)) > distanceSq)) continue;
            distanceSq = d;
            spouse = candidate;
        }
        if (spouse == null) {
            return false;
        }
        this.theSpouse = spouse;
        return true;
    }

    public boolean continueExecuting() {
        return this.theSpouse != null && this.theSpouse.isEntityAlive() && this.theNPC.familyInfo.canMarryNPC(this.theSpouse) && this.theSpouse.familyInfo.canMarryNPC(this.theNPC);
    }

    public void resetTask() {
        this.theSpouse = null;
        this.marryDelay = 0;
    }

    public void updateTask() {
        this.theNPC.getLookHelper().setLookPositionWithEntity(this.theSpouse, 10.0f, this.theNPC.getVerticalFaceSpeed());
        this.theNPC.getNavigator().tryMoveToEntityLiving(this.theSpouse, this.moveSpeed);
        ++this.marryDelay;
        if (this.marryDelay % 20 == 0) {
            this.theNPC.spawnHearts();
        }
        if (this.marryDelay >= 60 && this.theNPC.getDistanceSqToEntity(this.theSpouse) < 9.0) {
            this.marry();
        }
    }

    private void marry() {
        int maxChildren;
        EntityPlayer ringPlayerSpouse;
        this.theNPC.familyInfo.spouseUniqueID = this.theSpouse.getUniqueID();
        this.theSpouse.familyInfo.spouseUniqueID = this.theNPC.getUniqueID();
        this.theNPC.setCurrentItemOrArmor(0, null);
        this.theNPC.setCurrentItemOrArmor(4, new ItemStack(this.theNPC.familyInfo.marriageRing));
        this.theSpouse.setCurrentItemOrArmor(0, null);
        this.theSpouse.setCurrentItemOrArmor(4, new ItemStack(this.theNPC.familyInfo.marriageRing));
        this.theNPC.changeNPCNameForMarriage(this.theSpouse);
        this.theSpouse.changeNPCNameForMarriage(this.theNPC);
        this.theNPC.familyInfo.maxChildren = maxChildren = this.theNPC.familyInfo.getRandomMaxChildren();
        this.theSpouse.familyInfo.maxChildren = maxChildren;
        this.theNPC.familyInfo.setMaxBreedingDelay();
        this.theSpouse.familyInfo.setMaxBreedingDelay();
        this.theNPC.spawnHearts();
        this.theSpouse.spawnHearts();
        EntityPlayer ringPlayer = this.theNPC.familyInfo.getRingGivingPlayer();
        if (ringPlayer != null) {
            LOTRLevelData.getData(ringPlayer).addAlignment(ringPlayer, LOTRAlignmentValues.MARRIAGE_BONUS, this.theNPC.getFaction(), this.theNPC);
            if (this.theNPC.familyInfo.marriageAchievement != null) {
                LOTRLevelData.getData(ringPlayer).addAchievement(this.theNPC.familyInfo.marriageAchievement);
            }
        }
        if ((ringPlayerSpouse = this.theSpouse.familyInfo.getRingGivingPlayer()) != null) {
            LOTRLevelData.getData(ringPlayerSpouse).addAlignment(ringPlayerSpouse, LOTRAlignmentValues.MARRIAGE_BONUS, this.theSpouse.getFaction(), this.theSpouse);
            if (this.theSpouse.familyInfo.marriageAchievement != null) {
                LOTRLevelData.getData(ringPlayerSpouse).addAchievement(this.theSpouse.familyInfo.marriageAchievement);
            }
        }
        this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theNPC.posX, this.theNPC.posY, this.theNPC.posZ, this.theNPC.getRNG().nextInt(8) + 2));
    }
}

