package cloud.goober.guineabigs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GuineaTreadmillBlock extends Block {

    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final IntProperty WATER_LEVEL = IntProperty.of("water_level", 0, 10);
    public static final IntProperty HAY_LEVEL = IntProperty.of("hay_level", 0, 10);

    public GuineaTreadmillBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
            .with(POWERED, false)
            .with(WATER_LEVEL, 10)
            .with(HAY_LEVEL, 10));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);

        if (!world.isClient) {
            if (entity.getType() == GuineaBigs.GUINEA_PIG) {
                int waterLevel = state.get(WATER_LEVEL);
                int hayLevel = state.get(HAY_LEVEL);
                
                if (!state.get(POWERED) && waterLevel > 0 && hayLevel > 0) {
                    GuineaBigs.LOGGER.info("Attempting to power block at " + pos.toString());
                    // Consume resources
                    BlockState newState = state
                        .with(POWERED, true)
                        .with(WATER_LEVEL, waterLevel - 1)
                        .with(HAY_LEVEL, hayLevel - 1);
                    world.setBlockState(pos, newState, 3);
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
        builder.add(POWERED, WATER_LEVEL, HAY_LEVEL);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            ItemStack heldItem = player.getStackInHand(player.getActiveHand());
            
            // Check for water bucket
            if (heldItem.getItem() == Items.WATER_BUCKET) {
                int currentWater = state.get(WATER_LEVEL);
                if (currentWater < 10) {
                    world.setBlockState(pos, state.with(WATER_LEVEL, 10), 3);
                    if (!player.getAbilities().creativeMode) {
                        player.setStackInHand(player.getActiveHand(), new ItemStack(Items.BUCKET));
                    }
                    return ActionResult.SUCCESS;
                }
            }
            // Check for timothy hay
            else if (heldItem.getItem() == GuineaItems.TIMOTHY_HAY) {
                int currentHay = state.get(HAY_LEVEL);
                if (currentHay < 10) {
                    world.setBlockState(pos, state.with(HAY_LEVEL, 10), 3);
                    if (!player.getAbilities().creativeMode) {
                        heldItem.decrement(1);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}
