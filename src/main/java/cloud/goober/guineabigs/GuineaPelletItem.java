package cloud.goober.guineabigs;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuineaPelletItem extends Item {

    public GuineaPelletItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        // Check if the block can be fertilized like bonemeal
        if (state.getBlock() instanceof Fertilizable) {
            Fertilizable fertilizable = (Fertilizable) state.getBlock();

            // Check if the block can be fertilized
            if (fertilizable.isFertilizable(world, pos, state)) {

                // Apply fertilization effect (similar to bonemeal)
                if (world.isClient) {
                    return ActionResult.SUCCESS;
                } else {
                    if (fertilizable.canGrow(world, world.random, pos, state)) {
                        fertilizable.grow((ServerWorld) world, world.random, pos, state);
                        context.getStack().decrement(1); // Decrease item count like bonemeal
                        return ActionResult.CONSUME;
                    }
                }
            }
        }
        return ActionResult.PASS;
    }
}
