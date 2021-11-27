package lotr.common.tileentity;

import cpw.mods.fml.relauncher.*;

public class LOTRTileEntitySignCarvedIthildin
extends LOTRTileEntitySignCarved {
    private LOTRDwarvenGlowLogic glowLogic = new LOTRDwarvenGlowLogic().setPlayerRange(8);

    public void updateEntity() {
        super.updateEntity();
        this.glowLogic.update(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    public float getGlowBrightness(float f) {
        if (this.isFakeGuiSign) {
            return 1.0f;
        }
        return this.glowLogic.getGlowBrightness(this.worldObj, this.xCoord, this.yCoord, this.zCoord, f);
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public double getMaxRenderDistanceSquared() {
        return 1024.0;
    }
}

