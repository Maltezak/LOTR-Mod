package lotr.common.entity.animal;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class LOTREntitySeagull extends LOTREntityBird {
    public static float SEAGULL_SCALE = 1.4f;

    public LOTREntitySeagull(World world) {
        super(world);
        this.setSize(this.width * SEAGULL_SCALE, this.height * SEAGULL_SCALE);
    }

    @Override
    public String getBirdTextureDir() {
        return "seagull";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setBirdType(LOTREntityBird.BirdType.COMMON);
        return data;
    }

    @Override
    protected boolean canStealItems() {
        return true;
    }

    @Override
    protected boolean isStealable(ItemStack itemstack) {
        Item item = itemstack.getItem();
        if(item == Items.fish || item == Items.cooked_fished) {
            return true;
        }
        return super.isStealable(itemstack);
    }

    @Override
    protected boolean canBirdSpawnHere() {
        if(LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 40, 4, Material.leaves, Material.sand)) {
            double range = 16.0;
            List nearbyGulls = this.worldObj.getEntitiesWithinAABB(LOTREntitySeagull.class, this.boundingBox.expand(range, range, range));
            return nearbyGulls.size() < 2;
        }
        return false;
    }

    @Override
    protected String getLivingSound() {
        return "lotr:bird.seagull.say";
    }

    @Override
    protected String getHurtSound() {
        return "lotr:bird.seagull.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "lotr:bird.seagull.hurt";
    }
}
