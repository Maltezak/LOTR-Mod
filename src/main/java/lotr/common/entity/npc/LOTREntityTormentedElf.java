package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityTormentedElf extends LOTREntityElf {
    public LOTREntityTormentedElf(World world) {
        super(world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(5);
        if(i == 0 || i == 1 || i == 2) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
        }
        else if(i == 3) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumno));
        }
        else if(i == 4) {
            this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerUtumnoPoisoned));
        }
        this.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.utumnoBow));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killTormentedElf;
    }

    @Override
    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
    }

    @Override
    protected void dropElfItems(boolean flag, int i) {
    }

    @Override
    public boolean canElfSpawnHere() {
        return true;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        return "utumno/elf/hostile";
    }

    @Override
    public String getLivingSound() {
        return null;
    }

    @Override
    public String getAttackSound() {
        return null;
    }
}
