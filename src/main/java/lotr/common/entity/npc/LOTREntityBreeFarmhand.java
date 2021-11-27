package lotr.common.entity.npc;

import lotr.common.entity.ai.LOTREntityAIFarm;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityBreeFarmhand
extends LOTREntityBreeMan
implements LOTRFarmhand {
    public Item seedsItem;

    public LOTREntityBreeFarmhand(World world) {
        super(world);
        this.targetTasks.taskEntries.clear();
        this.addTargetTasks(false);
    }

    @Override
    protected void addBreeHiringAI(int prio) {
        super.addBreeHiringAI(prio);
        this.tasks.addTask(prio, new LOTREntityAIFarm(this, 1.0, 1.0f));
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
    }

    @Override
    public IPlantable getUnhiredSeeds() {
        if (this.seedsItem == null) {
            return (IPlantable)Items.wheat_seeds;
        }
        return (IPlantable)this.seedsItem;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.seedsItem != null) {
            nbt.setInteger("SeedsID", Item.getIdFromItem(this.seedsItem));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        Item item;
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("SeedsID") && (item = Item.getItemById(nbt.getInteger("SeedsID"))) != null && item instanceof IPlantable) {
            this.seedsItem = item;
        }
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if (this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "bree/farmhand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}

