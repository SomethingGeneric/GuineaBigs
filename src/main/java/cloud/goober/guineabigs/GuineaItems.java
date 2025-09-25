package cloud.goober.guineabigs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GuineaItems {

    public static final Item FOOD_PELLET = register(
            new Item(new Item.Settings()),
            "food_pellet"
    );

    public static final Item TIMOTHY_HAY = register(
            new Item(new Item.Settings()),
            "timothy_hay"
    );

    public static final Item GUINEA_PELLET = register(
            new GuineaPelletItem(new Item.Settings()),
            "guinea_pellet"
    );

    public static final Item CARBONITE_INGOT = register(
            new Item(new Item.Settings()),
            "carbonite_ingot"
    );

    public static final Item CONDENSED_CARBONITE_INGOT = register(
            new Item(new Item.Settings()),
            "condensed_carbonite_ingot"
    );

    public static final Item GUINEA_PIG_SPAWN_EGG = register(
            new SpawnEggItem(
                    GuineaBigs.GUINEA_PIG,
                    new Item.Settings()
            ),
            "guinea_pig_spawn_egg"
    );

    // Creative tab menu definition
    public static final RegistryKey<ItemGroup> ITEM_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(GuineaBigs.MOD_ID, "item_group"));
    public static final ItemGroup MY_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(GuineaItems.TIMOTHY_HAY))
            .displayName(Text.translatable("itemGroup.guineabigs"))
            .build();

    // END STATIC FIELDS

    // START METHODS
    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(GuineaBigs.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }

    public static void initialize() {
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
