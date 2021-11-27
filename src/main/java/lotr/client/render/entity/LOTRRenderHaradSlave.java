package lotr.client.render.entity;

import java.util.*;

import lotr.client.model.LOTRModelHuman;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHaradSlave extends LOTRRenderBiped {
    private static Map<LOTREntityHaradSlave.SlaveType, LOTRRandomSkins> slaveSkinsMale = new HashMap<>();
    private static Map<LOTREntityHaradSlave.SlaveType, LOTRRandomSkins> slaveSkinsFemale = new HashMap<>();

    public LOTRRenderHaradSlave() {
        super(new LOTRModelHuman(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        LOTREntityHaradSlave slave = (LOTREntityHaradSlave) entity;
        boolean isMale = slave.familyInfo.isMale();
        LOTREntityHaradSlave.SlaveType type = slave.getSlaveType();
        String skinDir = "lotr:mob/nearHarad/slave/" + type.skinDir + "_" + (isMale ? "male" : "female");
        LOTRRandomSkins skins = LOTRRandomSkins.loadSkinsList(skinDir);
        return skins.getRandomSkin(slave);
    }
}
