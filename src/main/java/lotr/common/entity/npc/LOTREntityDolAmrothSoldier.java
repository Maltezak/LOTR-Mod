package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDolAmrothSoldier extends LOTREntityGondorLevyman {
    private static ItemStack[] manAtArmsWeapons = new ItemStack[] {new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordDolAmroth), new ItemStack(LOTRMod.swordGondor), new ItemStack(Items.iron_sword)};

    public LOTREntityDolAmrothSoldier(World world) {
        super(world);
        this.spawnRidingHorse = this.rand.nextInt(6) == 0;
    }

    @Override
    public LOTRNPCMount createMountToRide() {
        LOTREntityHorse horse = (LOTREntityHorse) super.createMountToRide();
        horse.setMountArmor(new ItemStack(LOTRMod.horseArmorDolAmroth));
        return horse;
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(manAtArmsWeapons.length);
        this.npcItemsInv.setMeleeWeapon(manAtArmsWeapons[i].copy());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDolAmrothGambeson));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDolAmrothGambeson));
        if(this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            i = this.rand.nextInt(3);
            if(i == 0) {
                this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDolAmroth));
            }
            else if(i == 1) {
                this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
            }
            else if(i == 2) {
                this.setCurrentItemOrArmor(4, new ItemStack(Items.leather_helmet));
            }
        }
        return data;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "gondor/swanKnight/hired";
            }
            return "gondor/swanKnight/friendly";
        }
        return "gondor/swanKnight/hostile";
    }
}
