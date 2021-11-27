package lotr.common.entity.ai;

import lotr.common.*;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.fac.LOTRFaction;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRNPCTargetSelector implements IEntitySelector {
    private EntityLiving owner;
    private LOTRFaction ownerFaction;

    public LOTRNPCTargetSelector(EntityLiving entity) {
        this.owner = entity;
        this.ownerFaction = LOTRMod.getNPCFaction(entity);
    }

    @Override
    public boolean isEntityApplicable(Entity target) {
        if(this.ownerFaction == LOTRFaction.HOSTILE && (target.getClass().isAssignableFrom(this.owner.getClass()) || this.owner.getClass().isAssignableFrom(target.getClass()))) {
            return false;
        }
        if(target.isEntityAlive()) {
            if(target instanceof LOTREntityNPC && !((LOTREntityNPC) target).canBeFreelyTargetedBy(this.owner)) {
                return false;
            }
            if(!this.ownerFaction.approvesWarCrimes && target instanceof LOTREntityNPC && ((LOTREntityNPC) target).isCivilianNPC()) {
                return false;
            }
            LOTRFaction targetFaction = LOTRMod.getNPCFaction(target);
            if(this.ownerFaction.isBadRelation(targetFaction)) {
                return true;
            }
            if(this.ownerFaction.isNeutral(targetFaction)) {
                EntityPlayer hiringPlayer = null;
                if(this.owner instanceof LOTREntityNPC) {
                    LOTREntityNPC npc = (LOTREntityNPC) this.owner;
                    if(npc.hiredNPCInfo.isActive) {
                        hiringPlayer = npc.hiredNPCInfo.getHiringPlayer();
                    }
                }
                if(hiringPlayer != null && (LOTRLevelData.getData(hiringPlayer).getAlignment(targetFaction)) < 0.0f) {
                    return true;
                }
            }
        }
        return false;
    }
}
