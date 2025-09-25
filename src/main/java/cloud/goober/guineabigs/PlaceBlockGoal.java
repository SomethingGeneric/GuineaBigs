package cloud.goober.guineabigs;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class PlaceBlockGoal extends Goal {
    private final AnimalEntity entity;
    private int tickCounter;

    public PlaceBlockGoal(AnimalEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK)); // Add movement and look control
    }

    @Override
    public boolean canStart() {
        // Start the goal if the entity is in a valid world (e.g., not in a cave)
        return entity.getWorld() != null;
    }

    @Override
    public void tick() {
        tickCounter++;

        // Place a block every 100 ticks (about every 5 seconds)
        if (tickCounter >= 100) {
            tickCounter = 0; // Reset the counter
            placeBlock(); // Call the block placement method
        }
    }

    private void placeBlock() {
        // Only place blocks on the server side
        if (entity.getWorld().isClient()) {
            return;
        }
        
        ServerWorld world = (ServerWorld) entity.getWorld();
        BlockPos pos = entity.getBlockPos().offset(entity.getHorizontalFacing());

        // Check if the block can be placed here (you may want to implement more conditions)
        if (world.isAir(pos)) {
            world.setBlockState(pos, GuineaBlocks.GUINEA_PELLET.getDefaultState());
        }
    }
}
