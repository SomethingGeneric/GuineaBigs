package cloud.goober.guineabigs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuineaBigs implements ModInitializer {
	// Variables
	public static final String MOD_ID = "guineabigs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<GuineaPigEntity> GUINEA_PIG = Registry.register(
		Registries.ENTITY_TYPE,
		Identifier.of(MOD_ID+":guineapig"),
		FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GuineaPigEntity::new)
				.dimensions(EntityDimensions.fixed(0.6f, 0.5f))
				.build()
	);

	// Start methods
	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		GuineaItems.initialize();
		LOGGER.info("Items initialized");
		GuineaBlocks.initialize();
		LOGGER.info("Blocks initialized");
		FabricDefaultAttributeRegistry.register(GUINEA_PIG, GuineaPigEntity.createGuineaPigAttributes());
	}
}