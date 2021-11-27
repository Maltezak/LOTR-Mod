package lotr.common.block;

import net.minecraft.block.Block;

public class LOTRBlockBirdCageWood extends LOTRBlockBirdCage {
    public LOTRBlockBirdCageWood() {
        this.setStepSound(Block.soundTypeWood);
        this.setCageTypes("wood");
    }
}
