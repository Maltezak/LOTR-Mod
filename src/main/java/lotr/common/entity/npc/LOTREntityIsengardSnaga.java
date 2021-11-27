package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityIsengardSnaga extends LOTREntityOrc {
    private static ItemStack[] weapons = new ItemStack[] {new ItemStack(Items.stone_sword), new ItemStack(Items.stone_axe), new ItemStack(Items.stone_pickaxe), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(Items.iron_pickaxe), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerIronPoisoned), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.pickaxeBronze), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerBronzePoisoned), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.scimitarUruk), new ItemStack(LOTRMod.axeUruk), new ItemStack(LOTRMod.pickaxeUruk), new ItemStack(LOTRMod.daggerUruk), new ItemStack(LOTRMod.daggerUrukPoisoned), new ItemStack(LOTRMod.battleaxeUruk), new ItemStack(LOTRMod.hammerUruk), new ItemStack(LOTRMod.pikeUruk)};
    private static ItemStack[] spears = new ItemStack[] {new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.spearUruk)};
    private static ItemStack[] helmets = new ItemStack[] {new ItemStack(Items.leather_helmet), new ItemStack(LOTRMod.helmetBronze), new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.helmetBone)};
    private static ItemStack[] bodies = new ItemStack[] {new ItemStack(Items.leather_chestplate), new ItemStack(LOTRMod.bodyBronze), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyUruk)};
    private static ItemStack[] legs = new ItemStack[] {new ItemStack(Items.leather_leggings), new ItemStack(LOTRMod.legsBronze), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsUruk)};
    private static ItemStack[] boots = new ItemStack[] {new ItemStack(Items.leather_boots), new ItemStack(LOTRMod.bootsBronze), new ItemStack(LOTRMod.bootsFur), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsUruk)};

    public LOTREntityIsengardSnaga(World world) {
        super(world);
    }

    @Override
    public EntityAIBase createOrcAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        int i = this.rand.nextInt(weapons.length);
        this.npcItemsInv.setMeleeWeapon(weapons[i].copy());
        if(this.rand.nextInt(6) == 0) {
            this.npcItemsInv.setSpearBackup(this.npcItemsInv.getMeleeWeapon());
            i = this.rand.nextInt(spears.length);
            this.npcItemsInv.setMeleeWeapon(spears[i].copy());
        }
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        i = this.rand.nextInt(boots.length);
        this.setCurrentItemOrArmor(1, boots[i].copy());
        i = this.rand.nextInt(legs.length);
        this.setCurrentItemOrArmor(2, legs[i].copy());
        i = this.rand.nextInt(bodies.length);
        this.setCurrentItemOrArmor(3, bodies[i].copy());
        if(this.rand.nextInt(3) != 0) {
            i = this.rand.nextInt(helmets.length);
            this.setCurrentItemOrArmor(4, helmets[i].copy());
        }
        return data;
    }

    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.ISENGARD;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killIsengardSnaga;
    }

    @Override
    protected void dropOrcItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.URUK_TENT, 1, 2 + i);
        }
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "isengard/orc/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
                return "isengard/orc/friendly";
            }
            return "isengard/orc/neutral";
        }
        return "isengard/orc/hostile";
    }

    @Override
    protected String getOrcSkirmishSpeech() {
        return "isengard/orc/skirmish";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.ISENGARD.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.ISENGARD;
    }
}
