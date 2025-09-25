package cloud.goober.guineabigs;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class GuineaBlocks {

    public static Block TIMOTHY_HAY_BALE;
    public static Block GUINEA_PELLET;
    public static Block GUINEA_TREADMILL_BLOCK;

    // METHODS (vvv)
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(GuineaBigs.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {
        // Create registry keys for blocks
        RegistryKey<Block> timothyHayBaleKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(GuineaBigs.MOD_ID, "timothy_hay_bale"));
        RegistryKey<Block> guineaPelletKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(GuineaBigs.MOD_ID, "guinea_pellet_block"));  
        RegistryKey<Block> guineaTreadmillKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(GuineaBigs.MOD_ID, "guinea_treadmill_block"));

        // Register blocks
        TIMOTHY_HAY_BALE = register(
                new Block(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK).registryKey(timothyHayBaleKey)),
                "timothy_hay_bale",
                true
        );

        GUINEA_PELLET = register(
                new GuineaPelletBlock(Block.Settings.copy(Blocks.LILY_PAD).noCollision().strength(0.1F).registryKey(guineaPelletKey)),
                "guinea_pellet_block",
                true
        );

        GUINEA_TREADMILL_BLOCK = register(
                new GuineaTreadmillBlock(AbstractBlock.Settings.copy(Blocks.STONE).registryKey(guineaTreadmillKey)),
                "guinea_treadmill_block",
                true
        );

        ItemGroupEvents.modifyEntriesEvent(GuineaItems.ITEM_KEY).register((itemGroup) -> {
            itemGroup.add(GuineaBlocks.TIMOTHY_HAY_BALE.asItem());
            itemGroup.add(GuineaBlocks.GUINEA_TREADMILL_BLOCK.asItem());
        });
    }

}
