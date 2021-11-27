package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGondorTowerGuard extends LOTREntityGondorSoldier {
    public LOTREntityGondorTowerGuard(World world) {
        super(world);
        this.spawnRidingHorse = false;
        this.npcCape = LOTRCapes.TOWER_GUARD;
    }

    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.24);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearGondor));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.npcItemsInv.setSpearBackup(null);
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetGondorWinged));
        return data;
    }
}
