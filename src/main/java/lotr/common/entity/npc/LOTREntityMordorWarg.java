package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityMordorWarg extends LOTREntityWarg {
    public LOTREntityMordorWarg(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.setWargType(LOTREntityWarg.WargType.BLACK);
    }

    @Override
    public LOTREntityNPC createWargRider() {
        if(this.rand.nextBoolean()) {
            this.setWargArmor(new ItemStack(LOTRMod.wargArmorMordor));
        }
        return this.worldObj.rand.nextBoolean() ? new LOTREntityMordorOrcArcher(this.worldObj) : new LOTREntityMordorOrc(this.worldObj);
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
