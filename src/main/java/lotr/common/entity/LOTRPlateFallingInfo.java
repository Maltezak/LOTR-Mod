package lotr.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class LOTRPlateFallingInfo implements IExtendedEntityProperties {
    private static final String propID = "lotr_plateFall";
    private Entity theEntity;
    private int updateTick;
    private float[] posXTicksAgo = new float[65];
    private boolean[] isFalling = new boolean[65];
    private float[] fallerPos = new float[65];
    private float[] prevFallerPos = new float[65];
    private float[] fallerSpeed = new float[65];

    public LOTRPlateFallingInfo(Entity entity) {
        this.theEntity = entity;
    }

    public void update() {
        int l;
        float curPos = (float) this.theEntity.posY;
        if(!this.theEntity.onGround && this.theEntity.motionY > 0.0) {
            for(l = 0; l < this.posXTicksAgo.length; ++l) {
                this.posXTicksAgo[l] = Math.max(this.posXTicksAgo[l], curPos);
            }
        }
        if(this.updateTick % 1 == 0) {
            for(l = this.posXTicksAgo.length - 1; l > 0; --l) {
                this.posXTicksAgo[l] = this.posXTicksAgo[l - 1];
            }
            this.posXTicksAgo[0] = curPos;
        }
        ++this.updateTick;
        for(l = 0; l < this.fallerPos.length; ++l) {
            this.prevFallerPos[l] = this.fallerPos[l];
            float pos = this.fallerPos[l];
            float speed = this.fallerSpeed[l];
            boolean fall = this.isFalling[l];
            if(!fall && pos > this.posXTicksAgo[l]) {
                fall = true;
            }
            this.isFalling[l] = fall;
            if(fall) {
                speed = (float) (speed + 0.08);
                pos -= speed;
                speed = (float) (speed * 0.98);
            }
            else {
                speed = 0.0f;
            }
            if(pos < curPos) {
                pos = curPos;
                speed = 0.0f;
                this.isFalling[l] = false;
            }
            this.fallerPos[l] = pos;
            this.fallerSpeed[l] = speed;
        }
    }

    public float getPlateOffsetY(float f) {
        return this.getOffsetY(0, f);
    }

    public float getFoodOffsetY(int food, float f) {
        return this.getOffsetY(food - 1, f);
    }

    private float getOffsetY(int index, float f) {
        index = MathHelper.clamp_int(index, 0, this.fallerPos.length - 1);
        float pos = this.prevFallerPos[index] + (this.fallerPos[index] - this.prevFallerPos[index]) * f;
        float offset = pos - (float) (this.theEntity.prevPosY + (this.theEntity.posY - this.theEntity.prevPosY) * f);
        offset = Math.max(offset, 0.0f);
        return offset;
    }

    public static LOTRPlateFallingInfo getOrCreateFor(Entity entity, boolean create) {
        LOTRPlateFallingInfo props = (LOTRPlateFallingInfo) entity.getExtendedProperties(propID);
        if(props == null && create) {
            props = new LOTRPlateFallingInfo(entity);
            entity.registerExtendedProperties(propID, props);
        }
        return props;
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
    }

    @Override
    public void init(Entity entity, World world) {
    }
}
