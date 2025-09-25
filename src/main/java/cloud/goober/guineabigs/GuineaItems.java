package cloud.goober.guineabigs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GuineaItems {

    public static Item FOOD_PELLET;
    public static Item TIMOTHY_HAY;
    public static Item GUINEA_PELLET;
    public static Item CARBONITE_INGOT;
    public static Item CONDENSED_CARBONITE_INGOT;
    public static Item GUINEA_PIG_SPAWN_EGG;

    // Creative tab menu definition
    public static final RegistryKey<ItemGroup> ITEM_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(GuineaBigs.MOD_ID, "item_group"));
    public static ItemGroup MY_ITEM_GROUP;

    // END STATIC FIELDS

    // START METHODS
    public static Item register(String id, Item item) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(GuineaBigs.MOD_ID, id);

        // Register the item.
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
        // Create registry keys for each item first
        RegistryKey<Item> foodPelletKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GuineaBigs.MOD_ID, "food_pellet"));
        RegistryKey<Item> timothyHayKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GuineaBigs.MOD_ID, "timothy_hay"));
        RegistryKey<Item> guineaPelletKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GuineaBigs.MOD_ID, "guinea_pellet"));
        RegistryKey<Item> carboniteIngotKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GuineaBigs.MOD_ID, "carbonite_ingot"));
        RegistryKey<Item> condensedCarboniteIngotKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GuineaBigs.MOD_ID, "condensed_carbonite_ingot"));
        RegistryKey<Item> guineaPigSpawnEggKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GuineaBigs.MOD_ID, "guinea_pig_spawn_egg"));

        // Register all items with their registry keys
        FOOD_PELLET = Registry.register(Registries.ITEM, foodPelletKey.getValue(), 
                new Item(new Item.Settings().registryKey(foodPelletKey))
        );

        TIMOTHY_HAY = Registry.register(Registries.ITEM, timothyHayKey.getValue(),
                new Item(new Item.Settings().registryKey(timothyHayKey))
        );

        GUINEA_PELLET = Registry.register(Registries.ITEM, guineaPelletKey.getValue(),
                new GuineaPelletItem(new Item.Settings().registryKey(guineaPelletKey))
        );

        CARBONITE_INGOT = Registry.register(Registries.ITEM, carboniteIngotKey.getValue(),
                new Item(new Item.Settings().registryKey(carboniteIngotKey))
        );

        CONDENSED_CARBONITE_INGOT = Registry.register(Registries.ITEM, condensedCarboniteIngotKey.getValue(),
                new Item(new Item.Settings().registryKey(condensedCarboniteIngotKey))
        );

        // Register the spawn egg item now that the entity type is available
        GUINEA_PIG_SPAWN_EGG = Registry.register(Registries.ITEM, guineaPigSpawnEggKey.getValue(),
                new SpawnEggItem(
                        GuineaBigs.GUINEA_PIG,
                        new Item.Settings().registryKey(guineaPigSpawnEggKey)
                )
        );

        // Create the item group after items are created
        MY_ITEM_GROUP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(GuineaItems.TIMOTHY_HAY))
                .displayName(Text.translatable("itemGroup.guineabigs"))
                .build();

        // Register the group.
        Registry.register(Registries.ITEM_GROUP, ITEM_KEY, MY_ITEM_GROUP);

        // Register items to the custom item group.
        ItemGroupEvents.modifyEntriesEvent(ITEM_KEY).register(itemGroup -> {
            itemGroup.add(GuineaItems.FOOD_PELLET);
            itemGroup.add(GuineaItems.TIMOTHY_HAY);
            itemGroup.add(GuineaItems.GUINEA_PELLET);
            itemGroup.add(GuineaItems.GUINEA_PIG_SPAWN_EGG);
            itemGroup.add(GuineaItems.CARBONITE_INGOT);
            itemGroup.add(GuineaItems.CONDENSED_CARBONITE_INGOT);
            // blocks are added automagically
        });

        // Compostable items
        CompostingChanceRegistry.INSTANCE.add(GuineaItems.GUINEA_PELLET, 0.1f); // 10% chance

        // Add to Flammable block registry with a burn time of 30 seconds.
        // Remember, Minecraft deals with logical based-time using ticks.
        // 20 ticks = 1 second.
        FuelRegistryEvents.BUILD.register((builder, context) -> {
            builder.add(GuineaItems.GUINEA_PELLET, 2 * 20);
        });
    }

}
