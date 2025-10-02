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

	public static final RegistryKey<EntityType<?>> GUINEA_PIG_KEY = RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(MOD_ID, "guineapig"));
	
	public static final EntityType<GuineaPigEntity> GUINEA_PIG = Registry.register(
		Registries.ENTITY_TYPE,
		GUINEA_PIG_KEY,
		FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GuineaPigEntity::new)
				.dimensions(EntityDimensions.fixed(0.6f, 0.5f))
				.build(GUINEA_PIG_KEY)
	);

	// Tiny vehicle entity types
	public static final RegistryKey<EntityType<?>> TINY_CAR_KEY = RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(MOD_ID, "tiny_car"));
	public static final EntityType<TinyCarEntity> TINY_CAR = Registry.register(
		Registries.ENTITY_TYPE,
		TINY_CAR_KEY,
		FabricEntityTypeBuilder.create(SpawnGroup.MISC, TinyCarEntity::new)
				.dimensions(EntityDimensions.fixed(0.8f, 0.5f))
				.build(TINY_CAR_KEY)
	);

	public static final RegistryKey<EntityType<?>> TINY_TANK_KEY = RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(MOD_ID, "tiny_tank"));
	public static final EntityType<TinyTankEntity> TINY_TANK = Registry.register(
		Registries.ENTITY_TYPE,
		TINY_TANK_KEY,
		FabricEntityTypeBuilder.create(SpawnGroup.MISC, TinyTankEntity::new)
				.dimensions(EntityDimensions.fixed(0.8f, 0.6f))
				.build(TINY_TANK_KEY)
	);

	public static final RegistryKey<EntityType<?>> TINY_HELICOPTER_KEY = RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(MOD_ID, "tiny_helicopter"));
	public static final EntityType<TinyHelicopterEntity> TINY_HELICOPTER = Registry.register(
		Registries.ENTITY_TYPE,
		TINY_HELICOPTER_KEY,
		FabricEntityTypeBuilder.create(SpawnGroup.MISC, TinyHelicopterEntity::new)
				.dimensions(EntityDimensions.fixed(0.9f, 0.7f))
				.build(TINY_HELICOPTER_KEY)
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
									new SpawnSettings.SpawnEntry(GuineaBigs.GUINEA_PIG, 5, 2), // Entity, weight, min group size
									4 // max group size
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