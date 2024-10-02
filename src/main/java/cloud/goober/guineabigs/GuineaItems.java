package cloud.goober.guineabigs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
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
        });
    }

}
