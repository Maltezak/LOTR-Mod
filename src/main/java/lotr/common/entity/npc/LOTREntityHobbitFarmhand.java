package lotr.common.entity.npc;

import lotr.common.entity.ai.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class LOTREntityHobbitFarmhand extends LOTREntityHobbit implements LOTRFarmhand {
    public Item seedsItem;

    public LOTREntityHobbitFarmhand(World world) {
        super(world);
        this.tasks.addTask(4, new LOTREntityAIAttackOnCollide(this, 1.3, false));
        this.tasks.addTask(5, new LOTREntityAIFollowHiringPlayer(this));
        this.tasks.addTask(6, new LOTREntityAIFarm(this, 1.0, 1.5f));
        this.targetTasks.taskEntries.clear();
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
        if(this.seedsItem == null) {
            return (IPlantable) (Items.wheat_seeds);
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
            return "hobbit/farmhand/hired";
        }
        return super.getSpeechBank(entityplayer);
    }
}
