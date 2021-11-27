package lotr.common.entity.animal;

import net.minecraft.entity.*;
import net.minecraft.world.World;

public class LOTREntityGorcrow extends LOTREntityBird {
    public static float GORCROW_SCALE = 1.4f;

    public LOTREntityGorcrow(World world) {
        super(world);
        this.setSize(this.width * GORCROW_SCALE, this.height * GORCROW_SCALE);
    }

    @Override
    public String getBirdTextureDir() {
        return "gorcrow";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setBirdType(LOTREntityBird.BirdType.CROW);
        return data;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
}
