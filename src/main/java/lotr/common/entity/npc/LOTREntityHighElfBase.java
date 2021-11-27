package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class LOTREntityHighElfBase extends LOTREntityElf {
    public LOTREntityHighElfBase(World world) {
        super(world);
    }

    @Override
    public void setupNPCName() {
        this.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(this.rand, this.familyInfo.isMale()));
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HIGH_ELF;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected void dropElfItems(boolean flag, int i) {
        super.dropElfItems(flag, i);
        if(flag) {
            int dropChance = 20 - i * 4;
            if(this.rand.nextInt(dropChance = Math.max(dropChance, 1)) == 0) {
                ItemStack elfDrink = new ItemStack(LOTRMod.mugMiruvor);
                elfDrink.setItemDamage(1 + this.rand.nextInt(3));
                LOTRItemMug.setVessel(elfDrink, LOTRFoods.ELF_DRINK.getRandomVessel(this.rand), true);
                this.entityDropItem(elfDrink, 0.0f);
            }
        }
    }

    @Override
    public boolean canElfSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return j > 62 && this.worldObj.getBlock(i, j - 1, k) == Blocks.grass;
    }
}
