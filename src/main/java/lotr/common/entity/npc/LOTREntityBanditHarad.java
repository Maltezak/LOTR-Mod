package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityBanditHarad extends LOTREntityBandit {
    private static ItemStack[] weapons = new ItemStack[] {new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerNearHarad), new ItemStack(LOTRMod.daggerNearHaradPoisoned), new ItemStack(LOTRMod.daggerHarad), new ItemStack(LOTRMod.daggerHaradPoisoned)};
    private static int[] robeColors = new int[] {3354412, 5984843, 5968655, 3619908, 9007463, 3228720};

    public LOTREntityBanditHarad(World world) {
        super(world);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(weapons.length);
        this.npcItemsInv.setMeleeWeapon(weapons[i].copy());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(4, null);
        if(this.rand.nextInt(4) == 0) {
            ItemStack turban = new ItemStack(LOTRMod.helmetHaradRobes);
            int robeColor = robeColors[this.rand.nextInt(robeColors.length)];
            LOTRItemHaradRobes.setRobesColor(turban, robeColor);
            if(this.rand.nextInt(3) == 0) {
                LOTRItemHaradTurban.setHasOrnament(turban, true);
            }
            this.setCurrentItemOrArmor(4, turban);
        }
        return data;
    }
}
