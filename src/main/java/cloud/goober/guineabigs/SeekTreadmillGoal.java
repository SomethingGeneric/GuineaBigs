package cloud.goober.guineabigs;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class SeekTreadmillGoal extends Goal {
    private final PathAwareEntity entity;
    private final double speed;
    private final int searchRadius;
    private final int boredomThreshold; // Number of ticks before getting bored
    private BlockPos targetPos;
    private final Random random = new Random();
    private static final double STOP_DISTANCE = 20; // Distance to stop before reaching the target
    private static final double INTEREST_DISTANCE = 2.0; // Distance to check for other entities
    private int ticksAtTarget; // Counter for ticks spent at the target

    public SeekTreadmillGoal(PathAwareEntity entity, double speed, int searchRadius, int boredomThreshold) {
        this.entity = entity;
        this.speed = speed;
        this.searchRadius = searchRadius;
        this.boredomThreshold = boredomThreshold; // Initialize boredom threshold
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        // Check if there are other entities nearby
        if (isOtherEntityNearby()) {
            return false; // Not interested if another entity is nearby
        }
        return findNearestTreadmill();
    }

    @Override
    public boolean shouldContinue() {
        if (targetPos == null || entity.getNavigation().isIdle()) {
            return false;
        }
        // Check if the entity is close enough and has not gotten bored yet
        double distanceSquared = entity.squaredDistanceTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
        if (distanceSquared <= (STOP_DISTANCE * STOP_DISTANCE)) {
            ticksAtTarget++;
            return ticksAtTarget < boredomThreshold; // Continue if not bored yet
        }
        // Reset tick counter if not at target
        ticksAtTarget = 0;
        return true;
    }

    @Override
    public void start() {
        if (targetPos != null) {
            // Reset ticks counter when starting
            ticksAtTarget = 0;

            // Introduce a slight random offset for smoother movement
            double offsetX = (random.nextDouble() - 0.5) * 2; // Random offset up to ±2
            double offsetZ = (random.nextDouble() - 0.5) * 2; // Random offset up to ±2

            // Start moving towards the target position with offset
            entity.getNavigation().startMovingTo(targetPos.getX() + 0.5 + offsetX, targetPos.getY(), targetPos.getZ() + 0.5 + offsetZ, speed);
        }
    }

    @Override
    public void stop() {
        entity.getNavigation().stop();
        targetPos = null;
        ticksAtTarget = 0; // Reset tick counter when stopping
    }

    private boolean findNearestTreadmill() {
        World world = entity.getWorld();
        BlockPos entityPos = entity.getBlockPos();

        for (BlockPos pos : BlockPos.iterate(
                entityPos.add(-searchRadius, -searchRadius, -searchRadius),
                entityPos.add(searchRadius, searchRadius, searchRadius))) {
            if (world.getBlockState(pos).getBlock() == GuineaBlocks.GUINEA_TREADMILL_BLOCK) {
                targetPos = pos;
                return true;
            }
        }

        return false;
    }

    private boolean isOtherEntityNearby() {
        List<PathAwareEntity> nearbyEntities = entity.getWorld().getEntitiesByClass(PathAwareEntity.class, entity.getBoundingBox().expand(INTEREST_DISTANCE), e -> e != entity);
        // Check if there's at least one nearby entity of the same type
        for (PathAwareEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity.getType() == entity.getType()) {
                return true;
            }
        }
        return false;
    }

}
