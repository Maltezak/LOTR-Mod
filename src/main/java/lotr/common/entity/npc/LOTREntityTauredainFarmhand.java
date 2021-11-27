package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.entity.ai.LOTREntityAIFarm;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityTauredainFarmhand extends LOTREntityTauredain implements LOTRFarmhand {
    public Item seedsItem;

    public LOTREntityTauredainFarmhand(World world) {
        super(world);
        this.tasks.addTask(3, new LOTREntityAIFarm(this, 1.0, 1.2f));
        this.targetTasks.taskEntries.clear();
        this.addTargetTasks(false);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeTauredain));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        return data;
    }

    @Override
    public IPlantable getUnhiredSeeds() {
        if(this.seedsItem == null) {
            return (IPlantable) (Items.potato);
        }
        return (IPlantable) (this.seedsItem);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if(this.seedsItem != null) {
            nbt.setInteger("SeedsID", Item.getIdFromItem(this.seedsItem));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        Item item;
        super.readEntityFromNBT(nbt);
        if(nbt.hasKey("SeedsID") && (item = Item.getItemById(nbt.getInteger("SeedsID"))) != null && item instanceof IPlantable) {
            this.seedsItem = item;
        }
    }

    @Override
    public String getSpeechBank(EntityPlayer entityplayer) {
        if(this.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "tauredain/farmhand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}
