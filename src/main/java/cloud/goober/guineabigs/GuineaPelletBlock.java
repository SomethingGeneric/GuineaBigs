package cloud.goober.guineabigs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GuineaPelletBlock extends Block {
    // Define a flat shape, similar to lily pad
    private static final VoxelShape SHAPE =
            Block.createCuboidShape(1.0D,
                                    0.0D,
                                    1.0D,
                                    15.0D,
                                    1.0D,
                                    15.0D);

    public GuineaPelletBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
        return SHAPE; // Keeps the flat overlay shape
    }
}
