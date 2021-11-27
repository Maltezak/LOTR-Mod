package lotr.common.entity.ai;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;

public class LOTREntityAIHiringPlayerHurtByTarget extends EntityAITarget {
    private LOTREntityNPC theNPC;
    private EntityLivingBase theTarget;
    private int playerRevengeTimer;

    public LOTREntityAIHiringPlayerHurtByTarget(LOTREntityNPC entity) {
        super(entity, false);
        this.theNPC = entity;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if(!this.theNPC.hiredNPCInfo.isActive || this.theNPC.hiredNPCInfo.isHalted()) {
            return false;
        }
        EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if(entityplayer == null) {
            return false;
        }
        this.theTarget = entityplayer.getAITarget();
        int i = entityplayer.func_142015_aE();
        if(i == this.playerRevengeTimer) {
            return false;
        }
        return LOTRMod.canNPCAttackEntity(this.theNPC, this.theTarget, true) && this.isSuitableTarget(this.theTarget, false);
    }

    @Override
    public void startExecuting() {
        this.theNPC.setAttackTarget(this.theTarget);
        EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if(entityplayer != null) {
            this.playerRevengeTimer = entityplayer.func_142015_aE();
        }
        super.startExecuting();
    }
}
