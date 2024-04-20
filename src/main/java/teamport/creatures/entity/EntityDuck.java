package teamport.creatures.entity;

import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.world.World;

public class EntityDuck extends EntityChicken {
	public EntityDuck(World world) {
		super(world);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/duck/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/duck/0.png";
	}

	@Override
	public String getLivingSound() {
		return "creatures.duck";
	}

	@Override
	public String getHurtSound() {
		return "creatures.duckhurt";
	}

	@Override
	public String getDeathSound() {
		return "creatures.duckhurt";
	}
}
