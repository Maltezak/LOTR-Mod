package lotr.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class LOTRBlockMorgulShroom extends LOTRBlockMordorPlant {
    public LOTRBlockMorgulShroom() {
        float f = 0.2f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
        this.setTickRandomly(true);
        this.setCreativeTab(LOTRCreativeTabs.tabFood);
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random) {
        if(random.nextInt(10) == 0) {
            int j1;
            int k1;
            int i1;
            boolean nearbyWater = false;
            for(i1 = i - 2; i1 <= i + 2 && !nearbyWater; ++i1) {
                for(j1 = j - 2; j1 <= j + 2 && !nearbyWater; ++j1) {
                    for(k1 = k - 2; k1 <= k + 2 && !nearbyWater; ++k1) {
                        if(world.getBlock(i1, j - 1, k1).getMaterial() != Material.water) continue;
                        nearbyWater = true;
                    }
                }
            }
            if(nearbyWater && world.isAirBlock(i1 = i - 1 + random.nextInt(3), j1 = j - 1 + random.nextInt(3), k1 = k - 1 + random.nextInt(3)) && this.canBlockStay(world, i1, j1, k1)) {
                world.setBlock(i1, j1, k1, this);
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public String getItemIconName() {
        return this.getTextureName();
    }
}
