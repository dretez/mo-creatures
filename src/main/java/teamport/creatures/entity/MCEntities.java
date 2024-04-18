package teamport.creatures.entity;

import teamport.creatures.entity.render.*;
import turniplabs.halplibe.helper.EntityHelper;

public class MCEntities {
	public static void initEntityCore() {
		EntityHelper.Core.createEntity(EntityFox.class, 100, "Fox");
		EntityHelper.Core.createEntity(EntityFoxArctic.class, 101, "ArcticFox");
		EntityHelper.Core.createEntity(EntityBoar.class, 102, "Boar");
		EntityHelper.Core.createEntity(EntityBunny.class, 103, "Bunny");
		EntityHelper.Core.createEntity(EntityBird.class, 104, "Bird");
		EntityHelper.Core.createEntity(EntityHorse.class, 105, "Horse");
		EntityHelper.Core.createEntity(EntityHorseUnicorn.class, 106, "Unicorn");
		EntityHelper.Core.createEntity(EntityHorsePegasus.class, 107, "Pegasus");
		EntityHelper.Core.createEntity(EntityBear.class, 108, "Bear");
		EntityHelper.Core.createEntity(EntityBearPolar.class, 109, "PolarBear");
		EntityHelper.Core.createEntity(EntityDeer.class, 110, "Deer");
	}

	public static void initEntityClient() {
		EntityHelper.Client.assignEntityRenderer(EntityFox.class, new FoxRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityFoxArctic.class, new FoxRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBoar.class, new BoarRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBunny.class, new BunnyRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBird.class, new BirdRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityHorse.class, new HorseRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityHorseUnicorn.class, new HorseUnicornRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityHorsePegasus.class, new HorsePegasusRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBear.class, new BearRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBearPolar.class, new BearRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityDeer.class, new DeerRenderer());
	}
}
