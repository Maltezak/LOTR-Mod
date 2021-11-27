package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityAngmarWarg
extends LOTREntityWarg {
    public LOTREntityAngmarWarg(World world) {
        super(world);
    }

    @Override
    public LOTREntityNPC createWargRider() {
        if (this.rand.nextBoolean()) {
            this.setWargArmor(new ItemStack(LOTRMod.wargArmorAngmar));
        }
        return this.worldObj.rand.nextBoolean() ? new LOTREntityAngmarOrcArcher(this.worldObj) : new LOTREntityAngmarOrc(this.worldObj);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ANGMAR;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}

