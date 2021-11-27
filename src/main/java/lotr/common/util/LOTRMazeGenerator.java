package lotr.common.util;

import java.util.*;

import net.minecraft.util.MathHelper;

public class LOTRMazeGenerator {
    public final int xSize;
    public final int zSize;
    private short[][] mazeFlags;
    private int startX = -1;
    private int startZ = -1;
    private int endX = -1;
    private int endZ = -1;
    private float windyness = 0.3f;
    private float branchingness = 0.2f;

    public LOTRMazeGenerator(int x, int z) {
        this.xSize = x;
        this.zSize = z;
        this.setupMaze();
    }

    private void setupMaze() {
        this.mazeFlags = new short[this.xSize][this.zSize];
    }

    public void setStart(int x, int z) {
        this.startX = x;
        this.startZ = z;
    }

    public int[] getEnd() {
        return new int[] {this.endX, this.endZ};
    }

    public void setWindyness(float f) {
        this.windyness = f;
    }

    public void clear(int x, int z) {
        this.setFlag(x, z, (short) 1, true);
    }

    public void exclude(int x, int z) {
        this.setFlag(x, z, (short) 2, true);
    }

    public boolean isPath(int x, int z) {
        return this.getFlag(x, z, (short) 1);
    }

    public boolean isDeadEnd(int x, int z) {
        return this.getFlag(x, z, (short) 4);
    }

    private void setFlag(int x, int z, short flag, boolean val) {
        if(val) {
            short[] arrs = this.mazeFlags[x];
            int n = z;
            arrs[n] = (short) (arrs[n] | flag);
        }
        else {
            short[] arrs = this.mazeFlags[x];
            int n = z;
            arrs[n] = (short) (arrs[n] & ~flag);
        }
    }

    private boolean getFlag(int x, int z, short flag) {
        return (this.mazeFlags[x][z] & flag) == flag;
    }

    public void generate(Random random) {
        ArrayList<MazePos> positions = new ArrayList<>();
        Dir lastDir = null;
        this.clear(this.startX, this.startZ);
        positions.add(new MazePos(this.startX, this.startZ));
        while(!positions.isEmpty()) {
            int maxIndex = positions.size() - 1;
            int randPosIndex = MathHelper.getRandomIntegerInRange(random, (int) (maxIndex * (1.0f - this.branchingness)), maxIndex);
            MazePos pos = positions.get(randPosIndex);
            ArrayList<Dir> validDirs = new ArrayList<>();
            block1: for(Dir dir : Dir.values()) {
                for(int l = 1; l <= 2; ++l) {
                    int x = pos.xPos + dir.xDir * l;
                    int z = pos.zPos + dir.zDir * l;
                    if(x < 0 || x >= this.xSize || z < 0 || z >= this.zSize || this.isPath(x, z) || this.getFlag(x, z, (short) 2)) continue block1;
                }
                validDirs.add(dir);
            }
            if(!validDirs.isEmpty()) {
                Dir dir = lastDir != null && validDirs.contains(lastDir) && random.nextFloat() >= this.windyness ? lastDir : (Dir) (validDirs.get(random.nextInt(validDirs.size())));
                int x = pos.xPos;
                int z = pos.zPos;
                if(this.getFlag(x, z, (short) 4)) {
                    this.setFlag(x, z, (short) 4, false);
                }
                for(int l = 0; l < 2; ++l) {
                    this.clear(x += dir.xDir, z += dir.zDir);
                }
                if(!this.getFlag(x, z, (short) 4)) {
                    this.setFlag(x, z, (short) 4, true);
                }
                positions.add(new MazePos(x, z));
                lastDir = dir;
                continue;
            }
            positions.remove(randPosIndex);
            lastDir = null;
        }
    }

    public void selectOuterEndpoint(Random random) {
        int startXHalf = this.startX / (this.xSize / 2);
        int startZHalf = this.startZ / (this.zSize / 2);
        int wx = 0;
        int wz = 0;
        do {
            ArrayList<MazePos> positions = new ArrayList<>();
            for(int x = 0; x < this.xSize; ++x) {
                for(int z = 0; z < this.zSize; ++z) {
                    boolean outer;
                    outer = x == 0 + wx || x == this.xSize - 1 - wx || z == 0 + wz || z == this.zSize - 1 - wz;
                    if(!outer || !this.isPath(x, z)) continue;
                    int xHalf = x / (this.xSize / 2);
                    int zHalf = z / (this.zSize / 2);
                    if(startXHalf == xHalf || startZHalf == zHalf) continue;
                    positions.add(new MazePos(x, z));
                }
            }
            if(positions.isEmpty()) continue;
            MazePos pos = positions.get(random.nextInt(positions.size()));
            this.endX = pos.xPos;
            this.endZ = pos.zPos;
            return;
        }
        while(++wx <= this.xSize / 2 + 1 && ++wz <= this.zSize / 2 + 1);
    }

    private static class MazePos {
        public int xPos;
        public int zPos;

        public MazePos(int x, int z) {
            this.xPos = x;
            this.zPos = z;
        }
    }

    private enum Dir {
        XNEG(-1, 0), XPOS(1, 0), ZNEG(0, -1), ZPOS(0, 1);

        public final int xDir;
        public final int zDir;

        Dir(int x, int z) {
            this.xDir = x;
            this.zDir = z;
        }
    }

}
