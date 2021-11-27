package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityDaleLevyman extends LOTREntityDaleMan {
    private static ItemStack[] militiaWeapons = new ItemStack[] {new ItemStack(LOTRMod.swordDale), new ItemStack(LOTRMod.battleaxeDale), new ItemStack(LOTRMod.pikeDale), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.battleaxeBronze)};
    private static int[] leatherDyes = new int[] {7034184, 5650986, 7039851, 5331051, 2305612, 2698291, 1973790};

    public LOTREntityDaleLevyman(World world) {
        super(world);
        this.addTargetTasks(true);
    }

    @Override
    protected EntityAIBase createDaleAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, true);
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(militiaWeapons.length);
        this.npcItemsInv.setMeleeWeapon(militiaWeapons[i].copy());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, this.dyeLeather(new ItemStack(Items.leather_boots)));
        this.setCurrentItemOrArmor(2, this.dyeLeather(new ItemStack(Items.leather_leggings)));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDaleGambeson));
        if(this.rand.nextInt(3) != 0) {
            this.setCurrentItemOrArmor(4, null);
        }
        else {
            i = this.rand.nextInt(3);
            if(i == 0) {
                this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDale));
            }
            else if(i == 1) {
                this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
            }
            else if(i == 2) {
                this.setCurrentItemOrArmor(4, this.dyeLeather(new ItemStack(Items.leather_helmet)));
            }
        }
        return data;
    }

    private ItemStack dyeLeather(ItemStack itemstack) {
        int i = this.rand.nextInt(leatherDyes.length);
        int color = leatherDyes[i];
        ((ItemArmor) itemstack.getItem()).func_82813_b(itemstack, color);
        return itemstack;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "dale/soldier/hired";
            }
            return "dale/soldier/friendly";
        }
        return "dale/soldier/hostile";
    }
}
