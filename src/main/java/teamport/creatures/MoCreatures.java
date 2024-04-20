package teamport.creatures;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.creatures.entity.MCEntities;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class MoCreatures implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "creatures";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// IDEAS LIST //
	// Baby animals
	// Handcannon bunnies
	// TNT bunnies

	@Override
	public void onInitialize() {
		LOGGER.info("Mo Creatures has been initialized.");
	}

	@Override
	public void beforeGameStart() {
		MCEntities.initEntityCore();
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void beforeClientStart() {
		MCSounds.initializeSounds();
		MCEntities.initEntityClient();
	}

	@Override
	public void afterClientStart() {

	}
}
