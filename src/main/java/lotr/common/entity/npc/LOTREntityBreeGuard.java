package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.util.LOTRColorUtil;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntityBreeGuard
extends LOTREntityBreeMan {
    private static ItemStack[] guardWeapons = new ItemStack[]{new ItemStack(Items.iron_sword), new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.pikeIron)};
    private static int[] leatherDyes = new int[]{11373426, 7823440, 5983041, 9535090};

    public LOTREntityBreeGuard(World world) {
        super(world);
        this.addTargetTasks(true);
        this.npcShield = LOTRShields.ALIGNMENT_BREE;
    }

    @Override
    protected int addBreeAttackAI(int prio) {
        this.tasks.addTask(prio, new LOTREntityAIAttackOnCollide(this, 1.45, false));
        return prio;
    }

    @Override
    protected void addBreeAvoidAI(int prio) {
    }

    @Override
    public void setupNPCGender() {
        this.familyInfo.setMale(true);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(guardWeapons.length);
        this.npcItemsInv.setMeleeWeapon(guardWeapons[i].copy());
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        if (this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(1, new ItemStack(Items.chainmail_boots));
        } else {
            this.setCurrentItemOrArmor(1, LOTRColorUtil.dyeLeather(new ItemStack(Items.leather_boots), 3354152));
        }
        if (this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(2, new ItemStack(Items.chainmail_leggings));
        } else {
            this.setCurrentItemOrArmor(2, LOTRColorUtil.dyeLeather(new ItemStack(Items.leather_leggings), leatherDyes, this.rand));
        }
        if (this.rand.nextInt(3) == 0) {
            this.setCurrentItemOrArmor(3, new ItemStack(Items.chainmail_chestplate));
        } else {
            this.setCurrentItemOrArmor(3, LOTRColorUtil.dyeLeather(new ItemStack(Items.leather_chestplate), leatherDyes, this.rand));
        }
        this.setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet));
        return data;
    }

    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.isFriendlyAndAligned(entityplayer)) {
            if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "bree/guard/hired";
            }
            return "bree/guard/friendly";
        }
        return "bree/guard/hostile";
    }
}

