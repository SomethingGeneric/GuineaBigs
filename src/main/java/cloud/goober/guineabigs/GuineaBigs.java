package cloud.goober.guineabigs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

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

	public static final ArrayList<RegistryKey<Biome>> VALID_BIOMES = new ArrayList<>(Arrays.asList(
			BiomeKeys.PLAINS,
			BiomeKeys.SUNFLOWER_PLAINS,
			BiomeKeys.MEADOW
	));

	private void addEntityToBiomes() {
		// Add spawn settings for Grassland and Flower Fields biomes
		BiomeModifications.create(Identifier.of(MOD_ID, "entity_spawns"))
				.add(ModificationPhase.ADDITIONS, context -> VALID_BIOMES.contains(context.getBiomeKey()),
						context -> {
							context.getSpawnSettings().addSpawn(
									SpawnGroup.CREATURE, // Choose the spawn group (CREATURE, MONSTER, etc.)
									new SpawnSettings.SpawnEntry(GuineaBigs.GUINEA_PIG, 14, 5, 15) // Entity, weight, min group size, max group size
							);
						});
	}

	// Start methods
	@Override
	public void onInitialize() {
		GuineaItems.initialize();
		LOGGER.info("Items initialized");

		GuineaBlocks.initialize();
		LOGGER.info("Blocks initialized");

		FabricDefaultAttributeRegistry.register(GUINEA_PIG, GuineaPigEntity.createGuineaPigAttributes());
		LOGGER.info("The boys have been added");

		addEntityToBiomes();
		LOGGER.info("Set up entity spawn rules");
	}
}