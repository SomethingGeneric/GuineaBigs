package cloud.goober.guineabigs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

public class GuineaTreadmillBlock extends Block {

    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    public GuineaTreadmillBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(POWERED, false));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);

        if (!world.isClient) {
            if (entity.getType() == GuineaBigs.GUINEA_PIG) {
                if (!state.get(POWERED)) {
                    GuineaBigs.LOGGER.info("Attempting to power block at " + pos.toString());
                    world.setBlockState(pos, state.with(POWERED, true), 3);
                    world.updateNeighbors(pos, this);  // Update redstone neighbors

                    world.scheduleBlockTick(pos, this, 45);
                }
            }
        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        /* EMPTY since the tick event does this */
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClient) {
            // Check if a Guinea Pig is still on the block
            boolean hasGuineaPig = !world.getEntitiesByClass(Entity.class, state.getOutlineShape(world, pos).getBoundingBox().offset(pos), entity -> entity.getType() == GuineaBigs.GUINEA_PIG).isEmpty();

            // Turn off redstone power if no guinea pig is present
            if (!hasGuineaPig && state.get(POWERED)) {
                world.setBlockState(pos, state.with(POWERED, false), 3);
                world.updateNeighbors(pos, this);
            }
        }
    }

    // Ensure redstone power is emitted when the block is powered
    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getWeakRedstonePower(state, world, pos, direction);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return state.get(POWERED);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
