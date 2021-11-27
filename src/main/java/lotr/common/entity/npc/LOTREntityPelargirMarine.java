package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityPelargirMarine extends LOTREntityGondorSoldier {
    public LOTREntityPelargirMarine(World world) {
        super(world);
        this.spawnRidingHorse = false;
        this.npcShield = LOTRShields.ALIGNMENT_PELARGIR;
    }

    @Override
    public EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.45, false);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if(this.rand.nextBoolean()) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.tridentPelargir));
        }
        else {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordPelargir));
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsPelargir));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsPelargir));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyPelargir));
        this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetPelargir));
        return data;
    }

    @Override
    protected void onAttackModeChange(LOTREntityNPC.AttackMode mode, boolean mounted) {
        if(mode == LOTREntityNPC.AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, this.npcItemsInv.getMeleeWeapon());
        }
    }
}
