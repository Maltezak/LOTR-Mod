package lotr.common.quest;

import java.util.*;

import lotr.client.LOTRKeyHandler;
import lotr.common.*;
import lotr.common.entity.npc.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class LOTRMiniQuestWelcome
extends LOTRMiniQuest {
    private static final String SPEECHBANK = "char/gandalf/quest";
    public int stage = 0;
    public static final int STAGE_GET_ITEMS = 1;
    public static final int STAGE_READ_BOOK = 2;
    public static final int STAGE_EXPLAIN_BOOK = 3;
    public static final int STAGE_EXPLAIN_MAP = 4;
    public static final int STAGE_OPEN_MAP = 5;
    public static final int STAGE_EXPLAIN_FACTIONS = 6;
    public static final int STAGE_EXPLAIN_ALIGNMENT = 7;
    public static final int STAGE_CYCLE_ALIGNMENT = 8;
    public static final int STAGE_CYCLE_REGIONS = 9;
    public static final int STAGE_EXPLAIN_FACTION_GUIDE = 10;
    public static final int STAGE_OPEN_FACTIONS = 11;
    public static final int STAGE_TALK_ADVENTURES = 12;
    public static final int STAGE_GET_POUCHES = 13;
    public static final int STAGE_TALK_FINAL = 14;
    public static final int STAGE_COMPLETE = 15;
    public static final int NUM_STAGES = 15;
    private boolean movedOn;

    public LOTRMiniQuestWelcome(LOTRPlayerData pd) {
        super(pd);
    }

    public LOTRMiniQuestWelcome(LOTRPlayerData pd, LOTREntityGandalf gandalf) {
        this(pd);
        this.setNPCInfo(gandalf);
        this.speechBankStart = "";
        this.speechBankProgress = "";
        this.speechBankComplete = "";
        this.speechBankTooMany = "";
        this.quoteStart = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 2);
        this.quoteComplete = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 12);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("WStage", (byte)this.stage);
        nbt.setBoolean("WMovedOn", this.movedOn);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.stage = nbt.getByte("WStage");
        this.movedOn = nbt.getBoolean("WMovedOn");
    }

    @Override
    public String getFactionSubtitle() {
        return "";
    }

    @Override
    public String getQuestObjective() {
        if (this.stage == 2) {
            return StatCollector.translateToLocal("lotr.miniquest.welcome.book");
        }
        if (this.stage == 5) {
            KeyBinding keyMenu = LOTRKeyHandler.keyBindingMenu;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.map", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
        }
        if (this.stage == 8) {
            KeyBinding keyLeft = LOTRKeyHandler.keyBindingAlignmentCycleLeft;
            KeyBinding keyRight = LOTRKeyHandler.keyBindingAlignmentCycleRight;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.align", GameSettings.getKeyDisplayString(keyLeft.getKeyCode()), GameSettings.getKeyDisplayString(keyRight.getKeyCode()));
        }
        if (this.stage == 9) {
            KeyBinding keyUp = LOTRKeyHandler.keyBindingAlignmentGroupPrev;
            KeyBinding keyDown = LOTRKeyHandler.keyBindingAlignmentGroupNext;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.alignRegions", GameSettings.getKeyDisplayString(keyUp.getKeyCode()), GameSettings.getKeyDisplayString(keyDown.getKeyCode()));
        }
        if (this.stage == 11) {
            KeyBinding keyMenu = LOTRKeyHandler.keyBindingMenu;
            return StatCollector.translateToLocalFormatted("lotr.miniquest.welcome.factions", GameSettings.getKeyDisplayString(keyMenu.getKeyCode()));
        }
        return StatCollector.translateToLocal("lotr.miniquest.welcome.speak");
    }

    @Override
    public String getObjectiveInSpeech() {
        return "OBJECTIVE_SPEECH";
    }

    @Override
    public String getProgressedObjectiveInSpeech() {
        return "OBJECTIVE_SPEECH_PROGRESSED";
    }

    @Override
    public String getQuestProgress() {
        return this.getQuestProgressShorthand();
    }

    @Override
    public String getQuestProgressShorthand() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", this.stage, 15);
    }

    @Override
    public float getCompletionFactor() {
        return this.stage / 15.0f;
    }

    @Override
    public ItemStack getQuestIcon() {
        return new ItemStack(LOTRMod.redBook);
    }

    @Override
    public boolean canPlayerAccept(EntityPlayer entityplayer) {
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        return !pd.hasAnyGWQuest();
    }

    private void updateGreyWanderer() {
        LOTRGreyWandererTracker.setWandererActive(this.entityUUID);
    }

    @Override
    public void start(EntityPlayer entityplayer, LOTREntityNPC npc) {
        super.start(entityplayer, npc);
        String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 3);
        this.sendQuoteSpeech(entityplayer, npc, line);
        this.quotesStages.add(line);
        this.stage = 1;
        this.updateQuest();
        this.updateGreyWanderer();
    }

    @Override
    public void onInteract(EntityPlayer entityplayer, LOTREntityNPC npc) {
        this.updateGreyWanderer();
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if (this.stage == 1) {
            ArrayList<ItemStack> dropItems = new ArrayList<>();
            dropItems.add(new ItemStack(LOTRMod.redBook));
            npc.dropItemList(dropItems);
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 4);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 2;
            this.updateQuest();
        } else if (this.stage == 2) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 4);
            this.sendQuoteSpeech(entityplayer, npc, line);
        } else if (this.stage == 3) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 5);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 4;
            this.updateQuest();
        } else if (this.stage == 4) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 6);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 5;
            this.updateQuest();
        } else if (this.stage == 5) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 6);
            this.sendQuoteSpeech(entityplayer, npc, line);
        } else if (this.stage == 6) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 7);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 7;
            this.updateQuest();
        } else if (this.stage == 7) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 8);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 8;
            this.updateQuest();
        } else if (this.stage == 8) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 8);
            this.sendQuoteSpeech(entityplayer, npc, line);
        } else if (this.stage == 9) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 8);
            this.sendQuoteSpeech(entityplayer, npc, line);
        } else if (this.stage == 10) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 9);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 11;
            this.updateQuest();
        } else if (this.stage == 11) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 9);
            this.sendQuoteSpeech(entityplayer, npc, line);
        } else if (this.stage == 12) {
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 10);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 13;
            this.updateQuest();
        } else if (this.stage == 13) {
            ArrayList<ItemStack> dropItems = new ArrayList<>();
            if (!pd.getQuestData().getGivenFirstPouches()) {
                dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
                dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
                dropItems.add(new ItemStack(LOTRMod.pouch, 1, 0));
                pd.getQuestData().setGivenFirstPouches(true);
            }
            npc.dropItemList(dropItems);
            String line = LOTRSpeech.getSpeechAtLine(SPEECHBANK, 11);
            this.sendQuoteSpeech(entityplayer, npc, line);
            this.quotesStages.add(line);
            this.stage = 14;
            this.updateQuest();
        } else if (this.stage == 14) {
            this.stage = 15;
            this.updateQuest();
            this.complete(entityplayer, npc);
        }
    }

    @Override
    public void handleEvent(LOTRMiniQuestEvent event) {
        if (event instanceof LOTRMiniQuestEvent.OpenRedBook && this.stage == 2) {
            this.stage = 3;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.ViewMap && this.stage == 5) {
            this.stage = 6;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.CycleAlignment && this.stage == 8) {
            this.stage = 9;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.CycleAlignmentRegion && this.stage == 9) {
            this.stage = 10;
            this.updateQuest();
            this.updateGreyWanderer();
        }
        if (event instanceof LOTRMiniQuestEvent.ViewFactions && this.stage == 11) {
            this.stage = 12;
            this.updateQuest();
            this.updateGreyWanderer();
        }
    }

    @Override
    protected void complete(EntityPlayer entityplayer, LOTREntityNPC npc) {
        super.complete(entityplayer, npc);
        this.updateGreyWanderer();
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.doGreyQuest);
    }

    @Override
    public void onPlayerTick(EntityPlayer entityplayer) {
        if (!LOTRGreyWandererTracker.isWandererActive(this.entityUUID)) {
            this.movedOn = true;
            this.updateQuest();
        }
    }

    @Override
    public boolean isFailed() {
        return super.isFailed() || this.movedOn;
    }

    @Override
    public String getQuestFailure() {
        if (this.movedOn) {
            return StatCollector.translateToLocalFormatted("lotr.gui.redBook.mq.diary.movedOn", this.entityName);
        }
        return super.getQuestFailure();
    }

    @Override
    public String getQuestFailureShorthand() {
        if (this.movedOn) {
            return StatCollector.translateToLocal("lotr.gui.redBook.mq.movedOn");
        }
        return super.getQuestFailureShorthand();
    }

    @Override
    public float getAlignmentBonus() {
        return 0.0f;
    }

    @Override
    public int getCoinBonus() {
        return 0;
    }

    @Override
    protected boolean canRewardVariousExtraItems() {
        return false;
    }

    public static boolean[] forceMenu_Map_Factions(EntityPlayer entityplayer) {
        boolean[] flags = new boolean[]{false, false};
        LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        List<LOTRMiniQuest> activeQuests = pd.getActiveMiniQuests();
        for (LOTRMiniQuest quest : activeQuests) {
            if (!(quest instanceof LOTRMiniQuestWelcome)) continue;
            LOTRMiniQuestWelcome qw = (LOTRMiniQuestWelcome)quest;
            if (qw.stage == 5) {
                flags[0] = true;
                break;
            }
            if (qw.stage != 11) continue;
            flags[1] = true;
            break;
        }
        return flags;
    }
}

