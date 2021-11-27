package lotr.common.world.mapgen.dwarvenmine;

import java.util.*;

import lotr.common.world.structure2.LOTRWorldGenDwarvenMineEntrance;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

public class LOTRComponentDwarvenMineEntrance extends StructureComponent {
    private int posX;
    private int posY = -1;
    private int posZ;
    private static LOTRWorldGenDwarvenMineEntrance entranceGen = new LOTRWorldGenDwarvenMineEntrance(false);
    private int direction;
    private boolean ruined;

    public LOTRComponentDwarvenMineEntrance() {
    }

    public LOTRComponentDwarvenMineEntrance(World world, int l, Random random, int i, int k, boolean r) {
        super(l);
        this.boundingBox = new StructureBoundingBox(i - 4, 40, k - 4, i + 4, 256, k + 4);
        this.posX = i;
        this.posZ = k;
        this.ruined = r;
    }

    @Override
    protected void func_143012_a(NBTTagCompound nbt) {
        nbt.setInteger("EntranceX", this.posX);
        nbt.setInteger("EntranceY", this.posY);
        nbt.setInteger("EntranceZ", this.posZ);
        nbt.setInteger("Direction", this.direction);
        nbt.setBoolean("Ruined", this.ruined);
    }

    @Override
    protected void func_143011_b(NBTTagCompound nbt) {
        this.posX = nbt.getInteger("EntranceX");
        this.posY = nbt.getInteger("EntranceY");
        this.posZ = nbt.getInteger("EntranceZ");
        this.direction = nbt.getInteger("Direction");
        this.ruined = nbt.getBoolean("Ruined");
    }

    @Override
    public void buildComponent(StructureComponent component, List list, Random random) {
        StructureBoundingBox structureBoundingBox = null;
        this.direction = random.nextInt(4);
        switch(this.direction) {
            case 0: {
                structureBoundingBox = new StructureBoundingBox(this.posX - 1, this.boundingBox.minY + 1, this.posZ + 4, this.posX + 1, this.boundingBox.minY + 4, this.posZ + 15);
                break;
            }
            case 1: {
                structureBoundingBox = new StructureBoundingBox(this.posX - 15, this.boundingBox.minY + 1, this.posZ - 1, this.posX - 4, this.boundingBox.minY + 4, this.posZ + 1);
                break;
            }
            case 2: {
                structureBoundingBox = new StructureBoundingBox(this.posX - 1, this.boundingBox.minY + 1, this.posZ - 15, this.posX + 1, this.boundingBox.minY + 4, this.posZ - 4);
                break;
            }
            case 3: {
                structureBoundingBox = new StructureBoundingBox(this.posX + 4, this.boundingBox.minY + 1, this.posZ - 1, this.posX + 15, this.boundingBox.minY + 4, this.posZ + 1);
            }
        }
        LOTRComponentDwarvenMineCorridor corridor = new LOTRComponentDwarvenMineCorridor(0, random, structureBoundingBox, this.direction, this.ruined);
        list.add(corridor);
        corridor.buildComponent(component, list, random);
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
        if(this.posY == -1) {
            this.posY = world.getTopSolidOrLiquidBlock(this.posX, this.posZ);
        }
        if((world.getBlock(this.posX, this.posY - 1, this.posZ)) != Blocks.grass) {
            return false;
        }
        LOTRComponentDwarvenMineEntrance.entranceGen.isRuined = this.ruined;
        entranceGen.generateWithSetRotation(world, random, this.posX, this.posY, this.posZ, this.direction);
        return true;
    }

    static {
        LOTRComponentDwarvenMineEntrance.entranceGen.restrictions = false;
    }
}
