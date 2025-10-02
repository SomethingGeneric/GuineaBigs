package cloud.goober.guineabigs;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.Map;

public class CondensedCarboniteArmorMaterial {
    // Stats between diamond and netherite
    // Diamond: durability 33, protection [3, 6, 8, 3], enchantability 10, toughness 2.0, knockback resistance 0.0
    // Netherite: durability 37, protection [3, 6, 8, 3], enchantability 15, toughness 3.0, knockback resistance 0.1
    // Condensed Carbonite: durability 35, protection [3, 6, 8, 3], enchantability 12, toughness 2.5, knockback resistance 0.05
    
    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
        35,                                        // durability
        Util.make(new EnumMap<>(EquipmentType.class), map -> {
            map.put(EquipmentType.BOOTS, 3);
            map.put(EquipmentType.LEGGINGS, 6);
            map.put(EquipmentType.CHESTPLATE, 8);
            map.put(EquipmentType.HELMET, 3);
            map.put(EquipmentType.BODY, 11);
        }),
        12,                                        // enchantmentValue
        SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE,    // equipSound
        2.5f,                                      // toughness
        0.05f,                                     // knockbackResistance
        ItemTags.REPAIRS_NETHERITE_ARMOR,          // repairIngredient (will be updated later)
        EquipmentAssetKeys.NETHERITE               // assetId (use netherite assets for now)
    );
}
