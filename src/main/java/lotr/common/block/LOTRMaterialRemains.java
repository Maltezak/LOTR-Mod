package lotr.common.block;

import net.minecraft.block.material.*;

public class LOTRMaterialRemains extends Material {
    public static LOTRMaterialRemains remains = new LOTRMaterialRemains();

    public LOTRMaterialRemains() {
        super(MapColor.dirtColor);
        this.setRequiresTool();
    }
}
