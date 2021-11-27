package lotr.common.entity.ai;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityAnimalMF;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTREntityAIMFMate extends EntityAIBase {
    private LOTREntityAnimalMF theAnimal;
    World theWorld;
    public LOTREntityAnimalMF targetMate;
    int breeding = 0;
    double moveSpeed;

    public LOTREntityAIMFMate(LOTREntityAnimalMF animal, double d) {
        this.theAnimal = animal;
        this.theWorld = animal.worldObj;
        this.moveSpeed = d;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.theAnimal.isInLove()) {
            return false;
        }
        this.targetMate = this.findMate();
        return this.targetMate != null;
    }

    @Override
    public boolean continueExecuting() {
        return this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.breeding < 60;
    }

    @Override
    public void resetTask() {
        this.targetMate = null;
        this.breeding = 0;
    }

    @Override
    public void updateTask() {
        this.theAnimal.getLookHelper().setLookPositionWithEntity(this.targetMate, 10.0f, this.theAnimal.getVerticalFaceSpeed());
        this.theAnimal.getNavigator().tryMoveToEntityLiving(this.targetMate, this.moveSpeed);
        ++this.breeding;
        if(this.breeding == 60 && this.theAnimal.getDistanceSqToEntity(this.targetMate) < 9.0) {
            this.procreate();
        }
    }

    private LOTREntityAnimalMF findMate() {
        LOTREntityAnimalMF mate;
        float f = 8.0f;
        Class mateClass = this.theAnimal.getAnimalMFBaseClass();
        List list = this.theWorld.getEntitiesWithinAABB(mateClass, this.theAnimal.boundingBox.expand(f, f, f));
        Iterator it = list.iterator();
        do {
            if(it.hasNext()) continue;
            return null;
        }
        while(!this.theAnimal.canMateWith(mate = (LOTREntityAnimalMF) it.next()));
        return mate;
    }

    private void procreate() {
        EntityAgeable babyAnimal = this.theAnimal.createChild(this.targetMate);
        if(babyAnimal != null) {
            this.theAnimal.setGrowingAge(6000);
            this.targetMate.setGrowingAge(6000);
            this.theAnimal.resetInLove();
            this.targetMate.resetInLove();
            babyAnimal.setGrowingAge(-24000);
            babyAnimal.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0f, 0.0f);
            this.theWorld.spawnEntityInWorld(babyAnimal);
            Random rand = this.theAnimal.getRNG();
            for(int i = 0; i < 7; ++i) {
                double d = this.theAnimal.posX + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * this.theAnimal.width;
                double d1 = this.theAnimal.posY + 0.5 + rand.nextFloat() * this.theAnimal.height;
                double d2 = this.theAnimal.posZ + MathHelper.randomFloatClamp(rand, -1.0f, 1.0f) * this.theAnimal.width;
                double d3 = rand.nextGaussian() * 0.02;
                double d4 = rand.nextGaussian() * 0.02;
                double d5 = rand.nextGaussian() * 0.02;
                this.theWorld.spawnParticle("heart", d, d1, d2, d3, d4, d5);
            }
            if(LOTRMod.canDropLoot(this.theWorld)) {
                this.theWorld.spawnEntityInWorld(new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, rand.nextInt(4) + 1));
            }
        }
    }
}
