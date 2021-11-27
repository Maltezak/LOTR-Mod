package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityLamedonHillman extends LOTREntityGondorLevyman {
    private static ItemStack[] hillmanWeapons = new ItemStack[] {new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze)};
    private static int[] dyedHatColors = new int[] {6316128, 2437173, 0};
    private static int[] featherColors = new int[] {16777215, 10526880, 5658198, 2179924, 798013};

    public LOTREntityLamedonHillman(World world) {
        super(world);
    }

    @Override
    protected EntityAIBase createGondorAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(hillmanWeapons.length);
        this.npcItemsInv.setMeleeWeapon(hillmanWeapons[i].copy());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots));
        this.setCurrentItemOrArmor(2, null);
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyLamedonJacket));
        if(this.rand.nextInt(3) == 0) {
            ItemStack hat = new ItemStack(LOTRMod.leatherHat);
            if(this.rand.nextBoolean()) {
                LOTRItemLeatherHat.setHatColor(hat, dyedHatColors[this.rand.nextInt(dyedHatColors.length)]);
            }
            if(this.rand.nextBoolean()) {
                LOTRItemLeatherHat.setFeatherColor(hat, featherColors[this.rand.nextInt(featherColors.length)]);
            }
            this.setCurrentItemOrArmor(4, hat);
        }
        else {
            this.setCurrentItemOrArmor(4, null);
        }
        return data;
    }
}
