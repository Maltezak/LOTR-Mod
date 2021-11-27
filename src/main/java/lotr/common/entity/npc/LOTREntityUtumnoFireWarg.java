package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class LOTREntityUtumnoFireWarg extends LOTREntityUtumnoWarg {
    public LOTREntityUtumnoFireWarg(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.setWargType(LOTREntityWarg.WargType.FIRE);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        String s = this.rand.nextInt(3) > 0 ? "flame" : "smoke";
        this.worldObj.spawnParticle(s, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = super.attackEntityAsMob(entity);
        if(!this.worldObj.isRemote && flag) {
            entity.setFire(4);
        }
        return flag;
    }
}
