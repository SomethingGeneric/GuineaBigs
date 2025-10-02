package cloud.goober.guineabigs;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class CondensedCarboniteToolMaterial {
    // Stats between diamond and netherite
    // Diamond: durability 1561, mining speed 8.0, attack damage 3.0
    // Netherite: durability 2031, mining speed 9.0, attack damage 4.0
    // Condensed Carbonite: durability 1800, mining speed 8.5, attack damage 3.5
    
    public static final ToolMaterial INSTANCE = new ToolMaterial(
        BlockTags.INCORRECT_FOR_NETHERITE_TOOL,  // incorrectBlocksForDrops
        1800,                                      // durability
        8.5f,                                      // speed
        3.5f,                                      // attackDamageBonus
        12,                                        // enchantmentValue
        ItemTags.REPAIRS_NETHERITE_ARMOR          // repairItems (temporary, will create custom tag later)
    );
}
