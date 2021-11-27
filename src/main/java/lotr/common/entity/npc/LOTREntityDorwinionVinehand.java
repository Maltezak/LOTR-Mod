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

public class LOTREntityDorwinionVinehand extends LOTREntityDorwinionMan implements LOTRFarmhand {
    private Item seedsItem;

    public LOTREntityDorwinionVinehand(World world) {
        super(world);
        this.tasks.addTask(3, new LOTREntityAIFarm(this, 1.0, 1.0f));
        this.targetTasks.taskEntries.clear();
        this.addTargetTasks(false);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_hoe));
        this.npcItemsInv.setIdleItem(this.npcItemsInv.getMeleeWeapon());
        this.seedsItem = this.rand.nextBoolean() ? LOTRMod.seedsGrapeRed : LOTRMod.seedsGrapeWhite;
        return data;
    }

    @Override
    public IPlantable getUnhiredSeeds() {
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
            return "dorwinion/vinehand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}
