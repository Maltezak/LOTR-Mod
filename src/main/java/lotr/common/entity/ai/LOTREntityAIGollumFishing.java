package lotr.common.entity.ai;

import java.util.Random;

import lotr.common.entity.npc.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class LOTREntityAIGollumFishing
extends EntityAIBase {
    private LOTREntityGollum theGollum;
    private double moveSpeed;
    private boolean avoidsWater;
    private World theWorld;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private int moveTick;
    private int fishTick;
    private boolean finished;

    public LOTREntityAIGollumFishing(LOTREntityGollum entity, double d) {
        this.theGollum = entity;
        this.moveSpeed = d;
        this.theWorld = entity.worldObj;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        if (this.theGollum.getGollumOwner() == null) {
            return false;
        }
        if (this.theGollum.isGollumSitting()) {
            return false;
        }
        if (this.theGollum.prevFishTime > 0) {
            return false;
        }
        if (this.theGollum.isFishing) {
            return false;
        }
        if (this.theGollum.getEquipmentInSlot(0) != null) {
            return false;
        }
        if (this.theGollum.getRNG().nextInt(60) == 0) {
            Vec3 vec3 = this.findPossibleFishingLocation();
            if (vec3 == null) {
                return false;
            }
            this.xPosition = vec3.xCoord;
            this.yPosition = vec3.yCoord;
            this.zPosition = vec3.zCoord;
            return true;
        }
        return false;
    }

    private Vec3 findPossibleFishingLocation() {
        Random random = this.theGollum.getRNG();
        for (int l = 0; l < 32; ++l) {
            int j;
            int k;
            int i = MathHelper.floor_double(this.theGollum.posX) - 16 + random.nextInt(33);
            if (this.theWorld.getBlock(i, (j = MathHelper.floor_double(this.theGollum.boundingBox.minY) - 8 + random.nextInt(17)) + 1, k = MathHelper.floor_double(this.theGollum.posZ) - 16 + random.nextInt(33)).isNormalCube() || this.theWorld.getBlock(i, j, k).isNormalCube() || this.theWorld.getBlock(i, j - 1, k).getMaterial() != Material.water) continue;
            return Vec3.createVectorHelper(i + 0.5, j + 0.5, k + 0.5);
        }
        return null;
    }

    public boolean continueExecuting() {
        return this.theGollum.getGollumOwner() != null && !this.theGollum.isGollumSitting() && this.moveTick < 300 && !this.finished;
    }

    public void startExecuting() {
        this.avoidsWater = this.theGollum.getNavigator().getAvoidsWater();
        this.theGollum.getNavigator().setAvoidsWater(false);
        this.theGollum.isFishing = true;
    }

    public void resetTask() {
        this.theGollum.getNavigator().clearPathEntity();
        this.theGollum.getNavigator().setAvoidsWater(this.avoidsWater);
        this.moveTick = 0;
        this.fishTick = 0;
        if (this.finished) {
            this.finished = false;
            this.theGollum.prevFishTime = 3000;
        } else {
            this.theGollum.prevFishTime = 600;
        }
        this.theGollum.isFishing = false;
    }

    public void updateTask() {
        if (this.atFishingLocation()) {
            if (this.theGollum.isInWater()) {
                this.theWorld.setEntityState(this.theGollum, (byte)15);
                if (this.theGollum.getRNG().nextInt(4) == 0) {
                    this.theWorld.playSoundAtEntity(this.theGollum, this.theGollum.getSplashSound(), 1.0f, 1.0f + (this.theGollum.getRNG().nextFloat() - this.theGollum.getRNG().nextFloat()) * 0.4f);
                }
                this.theGollum.getJumpHelper().setJumping();
                if (this.theGollum.getRNG().nextInt(50) == 0) {
                    LOTRSpeech.sendSpeech(this.theGollum.getGollumOwner(), this.theGollum, LOTRSpeech.getRandomSpeechForPlayer(this.theGollum, "char/gollum/fishing", this.theGollum.getGollumOwner()));
                }
            }
            ++this.fishTick;
            if (this.fishTick > 100) {
                this.theGollum.setCurrentItemOrArmor(0, new ItemStack(Items.fish, 4 + this.theGollum.getRNG().nextInt(9)));
                this.finished = true;
                LOTRSpeech.sendSpeech(this.theGollum.getGollumOwner(), this.theGollum, LOTRSpeech.getRandomSpeechForPlayer(this.theGollum, "char/gollum/catchFish", this.theGollum.getGollumOwner()));
            }
        } else {
            this.theGollum.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.moveSpeed);
            ++this.moveTick;
        }
    }

    private boolean atFishingLocation() {
        if (this.theGollum.getDistanceSq(this.xPosition, this.yPosition, this.zPosition) < 4.0) {
            int j;
            int k;
            int i = MathHelper.floor_double(this.theGollum.posX);
            return this.theWorld.getBlock(i, j = MathHelper.floor_double(this.theGollum.boundingBox.minY), k = MathHelper.floor_double(this.theGollum.posZ)).getMaterial() == Material.water || this.theWorld.getBlock(i, j - 1, k).getMaterial() == Material.water;
        }
        return false;
    }
}

