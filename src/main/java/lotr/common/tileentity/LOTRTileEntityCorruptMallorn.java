package lotr.common.tileentity;

import java.util.*;

import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityEnt;
import lotr.common.world.biome.LOTRBiomeGenFangorn;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;

public class LOTRTileEntityCorruptMallorn extends TileEntity {
    @Override
    public void updateEntity() {
        super.updateEntity();
        Random rand = this.worldObj.rand;
        if(!this.worldObj.isRemote && LOTRMod.canSpawnMobs(this.worldObj) && rand.nextInt(40) == 0 && (this.worldObj.getBiomeGenForCoords(this.xCoord, this.zCoord)) instanceof LOTRBiomeGenFangorn) {
            int checkRange = 24;
            int spawnRange = 20;
            List nearbyEnts = this.worldObj.getEntitiesWithinAABB(LOTREntityEnt.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(checkRange, checkRange, checkRange));
            if(nearbyEnts.isEmpty()) {
                LOTREntityEnt ent = new LOTREntityEnt(this.worldObj);
                for(int l = 0; l < 16; ++l) {
                    int k;
                    int j;
                    int i = this.xCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange);
                    if(!this.worldObj.getBlock(i, (j = this.yCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange)) - 1, k = this.zCoord + MathHelper.getRandomIntegerInRange(rand, -spawnRange, spawnRange)).isNormalCube() || this.worldObj.getBlock(i, j, k).isNormalCube() || this.worldObj.getBlock(i, j + 1, k).isNormalCube()) continue;
                    ent.setLocationAndAngles(i + 0.5, j, k + 0.5, rand.nextFloat() * 360.0f, 0.0f);
                    ent.liftSpawnRestrictions = false;
                    if(!ent.getCanSpawnHere()) continue;
                    ent.onSpawnWithEgg(null);
                    ent.isNPCPersistent = false;
                    this.worldObj.spawnEntityInWorld(ent);
                    break;
                }
            }
        }
    }
}
