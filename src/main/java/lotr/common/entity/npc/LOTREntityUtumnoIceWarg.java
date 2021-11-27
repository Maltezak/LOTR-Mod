package lotr.common.entity.npc;

import lotr.common.LOTRDamage;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.*;
import net.minecraft.world.World;

public class LOTREntityUtumnoIceWarg extends LOTREntityUtumnoWarg {
    public LOTREntityUtumnoIceWarg(World world) {
        super(world);
        this.isChilly = true;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.setWargType(LOTREntityWarg.WargType.ICE);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if(super.attackEntityAsMob(entity)) {
            int difficulty;
            int duration;
            if(entity instanceof EntityPlayerMP) {
                LOTRDamage.doFrostDamage((EntityPlayerMP) entity);
            }
            if(entity instanceof EntityLivingBase && (duration = (difficulty = this.worldObj.difficultySetting.getDifficultyId()) * (difficulty + 5) / 2) > 0) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
            }
            return true;
        }
        return false;
    }
}
