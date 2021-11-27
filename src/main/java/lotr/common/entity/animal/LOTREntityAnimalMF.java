package lotr.common.entity.animal;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class LOTREntityAnimalMF extends EntityAnimal {
    public LOTREntityAnimalMF(World world) {
        super(world);
    }

    public abstract Class getAnimalMFBaseClass();

    public abstract boolean isMale();

    @Override
    public boolean canMateWith(EntityAnimal mate) {
        LOTREntityAnimalMF mfMate = (LOTREntityAnimalMF) mate;
        if(mate == this) {
            return false;
        }
        if(this.getAnimalMFBaseClass().equals(mfMate.getAnimalMFBaseClass()) && this.isInLove() && mate.isInLove()) {
            boolean thisMale = this.isMale();
            return thisMale != (mfMate.isMale());
        }
        return false;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID(this));
    }
}
