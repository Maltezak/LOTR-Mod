package lotr.common.entity.animal;

import java.util.UUID;

import lotr.common.LOTRMod;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.*;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class LOTREntityWhiteOryx extends LOTREntityGemsbok implements LOTRRandomSkinEntity {
    public static final float ORYX_SCALE = 0.9f;

    public LOTREntityWhiteOryx(World world) {
        super(world);
        this.setSize(this.width * 0.9f, this.height * 0.9f);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
    }

    @Override
    public void setUniqueID(UUID uuid) {
        this.entityUniqueID = uuid;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        return new LOTREntityWhiteOryx(this.worldObj);
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        int hide = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < hide; ++l) {
            this.dropItem(Items.leather, 1);
        }
        int meat = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        for(int l = 0; l < meat; ++l) {
            if(this.isBurning()) {
                this.dropItem(LOTRMod.deerCooked, 1);
                continue;
            }
            this.dropItem(LOTRMod.deerRaw, 1);
        }
    }

    @Override
    protected float getGemsbokSoundPitch() {
        return 0.9f;
    }
}
