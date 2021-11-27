package lotr.common.entity.ai;

import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityAINPCMate extends EntityAIBase {
    private LOTREntityNPC theNPC;
    private World theWorld;
    private LOTREntityNPC theSpouse;
    private int spawnBabyDelay = 0;
    private double moveSpeed;

    public LOTREntityAINPCMate(LOTREntityNPC npc, double d) {
        this.theNPC = npc;
        this.theWorld = npc.worldObj;
        this.moveSpeed = d;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(this.theNPC.getClass() != this.theNPC.familyInfo.marriageEntityClass || this.theNPC.familyInfo.spouseUniqueID == null || this.theNPC.familyInfo.children >= this.theNPC.familyInfo.maxChildren || this.theNPC.familyInfo.getAge() != 0) {
            return false;
        }
        this.theSpouse = this.theNPC.familyInfo.getSpouse();
        return this.theSpouse != null && this.theNPC.getDistanceToEntity(this.theSpouse) < 16.0 && this.theSpouse.familyInfo.children < this.theSpouse.familyInfo.maxChildren && this.theSpouse.familyInfo.getAge() == 0;
    }

    @Override
    public boolean continueExecuting() {
        return this.theSpouse.isEntityAlive() && this.spawnBabyDelay < 60 && this.theNPC.familyInfo.getAge() == 0 && this.theSpouse.familyInfo.getAge() == 0;
    }

    @Override
    public void resetTask() {
        this.theSpouse = null;
        this.spawnBabyDelay = 0;
    }

    @Override
    public void updateTask() {
        this.theNPC.getLookHelper().setLookPositionWithEntity(this.theSpouse, 10.0f, this.theNPC.getVerticalFaceSpeed());
        this.theNPC.getNavigator().tryMoveToEntityLiving(this.theSpouse, this.moveSpeed);
        ++this.spawnBabyDelay;
        if(this.spawnBabyDelay % 20 == 0) {
            this.theNPC.spawnHearts();
        }
        if(this.spawnBabyDelay >= 60 && this.theNPC.getDistanceSqToEntity(this.theSpouse) < 9.0) {
            this.spawnBaby();
        }
    }

    private void spawnBaby() {
        LOTREntityNPC baby = (LOTREntityNPC) EntityList.createEntityByName(LOTREntities.getStringFromClass(this.theNPC.familyInfo.marriageEntityClass), this.theWorld);
        LOTREntityNPC maleParent = this.theNPC.familyInfo.isMale() ? this.theNPC : this.theSpouse;
        LOTREntityNPC femaleParent = this.theNPC.familyInfo.isMale() ? this.theSpouse : this.theNPC;
        baby.familyInfo.setChild();
        baby.familyInfo.setMale(baby.getRNG().nextBoolean());
        baby.familyInfo.maleParentID = maleParent.getUniqueID();
        baby.familyInfo.femaleParentID = femaleParent.getUniqueID();
        baby.createNPCChildName(maleParent, femaleParent);
        baby.onSpawnWithEgg(null);
        baby.setLocationAndAngles(this.theNPC.posX, this.theNPC.posY, this.theNPC.posZ, 0.0f, 0.0f);
        baby.isNPCPersistent = true;
        this.theWorld.spawnEntityInWorld(baby);
        this.theNPC.familyInfo.setMaxBreedingDelay();
        this.theSpouse.familyInfo.setMaxBreedingDelay();
        ++this.theNPC.familyInfo.children;
        ++this.theSpouse.familyInfo.children;
        this.theNPC.spawnHearts();
        this.theSpouse.spawnHearts();
    }
}
