package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemHaradRobes;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityEasterlingLevyman extends LOTREntityEasterling {
    private static ItemStack[] levyWeapons = new ItemStack[] {new ItemStack(LOTRMod.daggerRhun), new ItemStack(LOTRMod.daggerRhunPoisoned), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.swordRhun), new ItemStack(LOTRMod.battleaxeRhun), new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze)};
    private static ItemStack[] levySpears = new ItemStack[] {new ItemStack(LOTRMod.spearRhun), new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze)};
    private static ItemStack[] levyBodies = new ItemStack[] {new ItemStack(Items.leather_chestplate), new ItemStack(LOTRMod.bodyBronze)};
    private static ItemStack[] levyLegs = new ItemStack[] {new ItemStack(Items.leather_leggings), new ItemStack(LOTRMod.legsBronze)};
    private static ItemStack[] levyBoots = new ItemStack[] {new ItemStack(Items.leather_boots), new ItemStack(LOTRMod.bootsBronze)};
    private static final int[] kaftanColors = new int[] {14823729, 11862016, 5512477, 14196753, 11374145, 7366222};

    public LOTREntityEasterlingLevyman(World world) {
        super(world);
        this.addTargetTasks(true);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(levyWeapons.length);
        this.npcItemsInv.setMeleeWeapon(levyWeapons[i].copy());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        if(this.rand.nextInt(5) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            i = this.rand.nextInt(levySpears.length);
            this.npcItemsInv.setMeleeWeapon(levySpears[i].copy());
        }
        i = this.rand.nextInt(levyBoots.length);
        this.setCurrentItemOrArmor(1, levyBoots[i].copy());
        i = this.rand.nextInt(levyLegs.length);
        this.setCurrentItemOrArmor(2, levyLegs[i].copy());
        i = this.rand.nextInt(levyBodies.length);
        this.setCurrentItemOrArmor(3, levyBodies[i].copy());
        this.setCurrentItemOrArmor(4, null);
        if(this.rand.nextBoolean()) {
            ItemStack kaftan = new ItemStack(LOTRMod.bodyKaftan);
            int kaftanColor = kaftanColors[this.rand.nextInt(kaftanColors.length)];
            LOTRItemHaradRobes.setRobesColor(kaftan, kaftanColor);
            this.setCurrentItemOrArmor(3, kaftan);
            if(this.rand.nextBoolean()) {
                ItemStack kaftanLegs = new ItemStack(LOTRMod.legsKaftan);
                LOTRItemHaradRobes.setRobesColor(kaftanLegs, kaftanColor);
                this.setCurrentItemOrArmor(2, kaftanLegs);
            }
        }
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "rhun/warrior/hired";
            }
            return "rhun/warrior/friendly";
        }
        return "rhun/warrior/hostile";
    }
}
