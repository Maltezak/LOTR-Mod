package lotr.common.entity.npc;

import lotr.common.*;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.fac.LOTRFaction;
import lotr.common.quest.*;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityGundabadOrc extends LOTREntityOrc {
    private static ItemStack[] weapons = new ItemStack[] {new ItemStack(Items.stone_sword), new ItemStack(Items.stone_axe), new ItemStack(Items.stone_pickaxe), new ItemStack(Items.iron_sword), new ItemStack(Items.iron_axe), new ItemStack(Items.iron_pickaxe), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerIronPoisoned), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.swordBronze), new ItemStack(LOTRMod.axeBronze), new ItemStack(LOTRMod.pickaxeBronze), new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerBronzePoisoned), new ItemStack(LOTRMod.battleaxeBronze), new ItemStack(LOTRMod.swordAngmar), new ItemStack(LOTRMod.axeAngmar), new ItemStack(LOTRMod.pickaxeAngmar), new ItemStack(LOTRMod.daggerAngmar), new ItemStack(LOTRMod.daggerAngmarPoisoned), new ItemStack(LOTRMod.battleaxeAngmar), new ItemStack(LOTRMod.hammerAngmar), new ItemStack(LOTRMod.scimitarOrc), new ItemStack(LOTRMod.axeOrc), new ItemStack(LOTRMod.pickaxeOrc), new ItemStack(LOTRMod.daggerOrc), new ItemStack(LOTRMod.daggerOrcPoisoned), new ItemStack(LOTRMod.battleaxeOrc), new ItemStack(LOTRMod.hammerOrc), new ItemStack(LOTRMod.polearmOrc), new ItemStack(LOTRMod.swordDolGuldur), new ItemStack(LOTRMod.axeDolGuldur), new ItemStack(LOTRMod.pickaxeDolGuldur), new ItemStack(LOTRMod.daggerDolGuldur), new ItemStack(LOTRMod.daggerDolGuldurPoisoned), new ItemStack(LOTRMod.battleaxeDolGuldur), new ItemStack(LOTRMod.hammerDolGuldur), new ItemStack(LOTRMod.swordGundabadUruk), new ItemStack(LOTRMod.battleaxeGundabadUruk), new ItemStack(LOTRMod.hammerGundabadUruk), new ItemStack(LOTRMod.pikeGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUruk), new ItemStack(LOTRMod.daggerGundabadUrukPoisoned), new ItemStack(LOTRMod.polearmAngmar), new ItemStack(LOTRMod.pikeDolGuldur)};
    private static ItemStack[] spears = new ItemStack[] {new ItemStack(LOTRMod.spearIron), new ItemStack(LOTRMod.spearBronze), new ItemStack(LOTRMod.spearStone), new ItemStack(LOTRMod.spearAngmar), new ItemStack(LOTRMod.spearOrc), new ItemStack(LOTRMod.spearDolGuldur), new ItemStack(LOTRMod.spearGundabadUruk)};
    private static ItemStack[] helmets = new ItemStack[] {new ItemStack(Items.leather_helmet), new ItemStack(LOTRMod.helmetBronze), new ItemStack(LOTRMod.helmetFur), new ItemStack(LOTRMod.helmetBone), new ItemStack(LOTRMod.helmetAngmar), new ItemStack(LOTRMod.helmetOrc), new ItemStack(LOTRMod.helmetDolGuldur)};
    private static ItemStack[] bodies = new ItemStack[] {new ItemStack(Items.leather_chestplate), new ItemStack(LOTRMod.bodyBronze), new ItemStack(LOTRMod.bodyFur), new ItemStack(LOTRMod.bodyBone), new ItemStack(LOTRMod.bodyAngmar), new ItemStack(LOTRMod.bodyOrc), new ItemStack(LOTRMod.bodyDolGuldur), new ItemStack(LOTRMod.bodyGundabadUruk)};
    private static ItemStack[] legs = new ItemStack[] {new ItemStack(Items.leather_leggings), new ItemStack(LOTRMod.legsBronze), new ItemStack(LOTRMod.legsFur), new ItemStack(LOTRMod.legsBone), new ItemStack(LOTRMod.legsAngmar), new ItemStack(LOTRMod.legsOrc), new ItemStack(LOTRMod.legsDolGuldur), new ItemStack(LOTRMod.legsGundabadUruk)};
    private static ItemStack[] boots = new ItemStack[] {new ItemStack(Items.leather_boots), new ItemStack(LOTRMod.bootsBronze), new ItemStack(LOTRMod.bootsFur), new ItemStack(LOTRMod.bootsBone), new ItemStack(LOTRMod.bootsAngmar), new ItemStack(LOTRMod.bootsOrc), new ItemStack(LOTRMod.bootsDolGuldur), new ItemStack(LOTRMod.bootsGundabadUruk)};

    public LOTREntityGundabadOrc(World world) {
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
        return LOTRFaction.GUNDABAD;
    }

    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }

    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killGundabadOrc;
    }

    @Override
    protected void dropOrcItems(boolean flag, int i) {
        if(this.rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.GUNDABAD_TENT, 1, 2 + i);
        }
        if(this.rand.nextInt(4000) == 0) {
            ItemStack dirt = new ItemStack(Blocks.dirt);
            dirt.setStackDisplayName("Such Wealth");
            this.entityDropItem(dirt, 0.0f);
        }
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.isFriendly(entityplayer)) {
            if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "gundabad/orc/hired";
            }
            if(LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 100.0f) {
                return "gundabad/orc/friendly";
            }
            return "gundabad/orc/neutral";
        }
        return "gundabad/orc/hostile";
    }

    @Override
    protected String getOrcSkirmishSpeech() {
        return "gundabad/orc/skirmish";
    }

    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.GUNDABAD.createQuest(this);
    }

    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.GUNDABAD;
    }
}
