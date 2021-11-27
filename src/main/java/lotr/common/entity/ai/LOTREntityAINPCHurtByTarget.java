package lotr.common.entity.ai;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class LOTREntityAINPCHurtByTarget extends EntityAIHurtByTarget {
    public LOTREntityAINPCHurtByTarget(LOTREntityNPC npc, boolean flag) {
        super(npc, flag);
    }

    @Override
    protected boolean isSuitableTarget(EntityLivingBase entity, boolean flag) {
        if(entity == this.taskOwner.ridingEntity || entity == this.taskOwner.riddenByEntity) {
            return false;
        }
        int homeX = this.taskOwner.getHomePosition().posX;
        int homeY = this.taskOwner.getHomePosition().posY;
        int homeZ = this.taskOwner.getHomePosition().posZ;
        int homeRange = (int) this.taskOwner.func_110174_bM();
        this.taskOwner.detachHome();
        boolean superSuitable = super.isSuitableTarget(entity, flag);
        this.taskOwner.setHomeArea(homeX, homeY, homeZ, homeRange);
        return superSuitable;
    }
}
