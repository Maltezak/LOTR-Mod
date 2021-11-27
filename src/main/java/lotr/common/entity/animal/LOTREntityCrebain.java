package lotr.common.entity.animal;

import net.minecraft.entity.*;
import net.minecraft.world.World;

public class LOTREntityCrebain extends LOTREntityBird {
    public static float CREBAIN_SCALE = 1.8f;

    public LOTREntityCrebain(World world) {
        super(world);
        this.setSize(this.width * CREBAIN_SCALE, this.height * CREBAIN_SCALE);
    }

    @Override
    public String getBirdTextureDir() {
        return "crebain";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setBirdType(LOTREntityBird.BirdType.CROW);
        return data;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.85f;
    }
}
