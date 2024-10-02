package cloud.goober.guineabigs;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuineaBigs implements ModInitializer {
	// Variables
	public static final String MOD_ID = "guineabigs";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Start methods
	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		GuineaItems.initialize();
		LOGGER.info("Items initialized");
		GuineaBlocks.initialize();
		LOGGER.info("Blocks initialized");
	}
}