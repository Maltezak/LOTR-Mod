package lotr.common.world.mapgen.tpyr;

import java.util.*;

import lotr.common.world.structure2.LOTRWorldGenTauredainPyramid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

public class LOTRComponentTauredainPyramid extends StructureComponent {
    private int posX;
    private int posY = -1;
    private int posZ;
    private static LOTRWorldGenTauredainPyramid pyramidGen = new LOTRWorldGenTauredainPyramid(false);
    private int direction;
    private static Random pyramidRand;
    private long pyramidSeed = -1L;

    public LOTRComponentTauredainPyramid() {
    }

    public LOTRComponentTauredainPyramid(World world, int l, Random random, int i, int k) {
        super(l);
        int r = LOTRWorldGenTauredainPyramid.RADIUS + 5;
        this.boundingBox = new StructureBoundingBox(i - r, 0, k - r, i + r, 255, k + r);
        this.posX = i;
        this.posZ = k;
        this.direction = random.nextInt(4);
    }

    @Override
    protected void func_143012_a(NBTTagCompound nbt) {
        nbt.setInteger("PyrX", this.posX);
        nbt.setInteger("PyrY", this.posY);
        nbt.setInteger("PyrZ", this.posZ);
        nbt.setInteger("Direction", this.direction);
        nbt.setLong("Seed", this.pyramidSeed);
    }

    @Override
    protected void func_143011_b(NBTTagCompound nbt) {
        this.posX = nbt.getInteger("PyrX");
        this.posY = nbt.getInteger("PyrY");
        this.posZ = nbt.getInteger("PyrZ");
        this.direction = nbt.getInteger("Direction");
        this.pyramidSeed = nbt.getLong("Seed");
    }

    @Override
    public void buildComponent(StructureComponent component, List list, Random random) {
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
        if(this.posY == -1) {
            this.posY = world.getTopSolidOrLiquidBlock(structureBoundingBox.getCenterX(), structureBoundingBox.getCenterZ());
        }
        if(this.pyramidSeed == -1L) {
            this.pyramidSeed = random.nextLong();
        }
        pyramidGen.setStructureBB(structureBoundingBox);
        pyramidRand.setSeed(this.pyramidSeed);
        pyramidGen.generateWithSetRotation(world, pyramidRand, this.posX, this.posY, this.posZ, this.direction);
        return true;
    }

    static {
        LOTRComponentTauredainPyramid.pyramidGen.restrictions = false;
        pyramidRand = new Random();
    }
}
