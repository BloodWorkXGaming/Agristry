package bloodworkxgaming.agristry.Blocks.greenhouse;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Jonas on 21.05.2017.
 */
public class GreenhouseStructureData {

    StructureSingleBlocks[] structreGreenhouseBlocks = new StructureSingleBlocks[]{
        new StructureSingleBlocks(0, 0, 0, Blocks.LOG, false)

    };


    public GreenhouseStructureData(){

    }


    class StructureSingleBlocks{
        public BlockPos blockPos;
        public Block block;
        public boolean correctBlock;

        public StructureSingleBlocks(BlockPos blockPos, Block block, boolean correctBlock){
            this.blockPos = blockPos;
            this.block = block;
            this.correctBlock = correctBlock;
        }

        public StructureSingleBlocks(int x, int y, int z, Block block, boolean correctBlock){
            this.blockPos = new BlockPos(x, y, z);
            this.block = block;
            this.correctBlock = correctBlock;
        }

    }
}
// https://github.com/ZeroNoRyouki/ZeroCore/blob/master/src/main/java/it/zerono/mods/zerocore/api/multiblock/IMultiblockPart.java
// ^ might help