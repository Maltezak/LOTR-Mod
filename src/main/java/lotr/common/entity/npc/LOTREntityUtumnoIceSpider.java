package lotr.common.entity.npc;

import lotr.common.LOTRDamage;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceSpider extends LOTREntitySpiderBase {
    public LOTREntityUtumnoIceSpider(World world) {
        super(world);
        this.isImmuneToFrost = true;
        this.isChilly = true;
    }

    @Override
    protected int getRandomSpiderScale() {
        return this.rand.nextInt(4);
    }

    @Override
    protected int getRandomSpiderType() {
        return VENOM_SLOWNESS;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }

    @Override
    protected boolean canRideSpider() {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            if(entity instanceof EntityPlayerMP) {
                LOTRDamage.doFrostDamage((EntityPlayerMP) entity);
            }
            return true;
        }
        return false;
    }
}
