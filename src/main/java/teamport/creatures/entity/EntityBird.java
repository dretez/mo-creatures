package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemSeeds;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import useless.dragonfly.model.entity.AnimationState;

import java.util.List;

public class EntityBird extends EntityAnimal {
	public AnimationState flyState = new AnimationState();
	private int courseChangeCoolDown = 0;
	private int courseCoolDown = 200;
	private int skinVariant;
	private int afraidTick = 0;
	private boolean isFed = false;
	private boolean isAfraid = true;

	public EntityBird(World world) {
		super(world);
		setSize(0.25F, 0.25F);

		heartsHalvesLife = 5;
		skinVariant = random.nextInt(6);
		speed = 0.05f;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/bird/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/bird/0.png";
	}

	@Override
	public String getLivingSound() {
		switch (skinVariant) {
			default:
			case 0:
				return "creatures.birdred";
			case 1:
				return "creatures.birdblack";
			case 2:
				return "creatures.birdyellow";
			case 3:
				return "creatures.birdgreen";
			case 4:
				return "creatures.birdwhite";
			case 5:
				return "creatures.birdblue";
		}
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if ((courseCoolDown-- <= 0 && courseCoolDown > -400)) {
			xd *= 0.455;
			yd *= 0.455;
			zd *= 0.455;

			yd += y < (double) world.getHeightBlocks() / 2 ? 0.025f : -0.025f;

			if (random.nextFloat() < 0.05F) randomYawVelocity = (random.nextFloat() - 0.5F) * 20.0F;
			yRot += randomYawVelocity;
			xRot = defaultPitch;

			moveForward = 0.3f;
			moveStrafing = moveForward;
			moveRelative(moveStrafing, moveForward, 0.2f);

		} else if (courseCoolDown > 0) {
			xd = 0.0f;
			zd = 0.0f;

			if (!this.onGround) {
				yd = -0.05f;
				moveForward = 0.1f;
			}

			super.moveEntityWithHeading(moveStrafing, moveForward);
		}

		if (courseCoolDown <= -400) courseCoolDown = random.nextInt(400) + 600;

		if (isInWater() || isInLava()) jump();

		move(xd, yd, zd);
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		moveEntityWithHeading(moveStrafing, moveForward);
		flyState.animateWhen(courseCoolDown <= 0, tickCount);

		// Seed check
		List<Entity> nearbyItems = world
			.getEntitiesWithinAABB(EntityItem.class, AABB.getBoundingBoxFromPool(x, y, z, x + 1.0, y + 1.0, z + 1.0)
					.expand(16.0, 16.0, 16.0)
			);
		if (!nearbyItems.isEmpty() && !isAfraid) {
			Entity entityItem = null;
			for (Entity nearbyItem : nearbyItems) {
				entityItem = nearbyItem;
			}
			if (entityItem != null) {
				if (((EntityItem) entityItem).item.getItem() instanceof ItemSeeds) {
					courseCoolDown = random.nextInt(200) + 300;
					setTarget(entityItem);

					// If the item is seeds and the entities are colliding, feed the birb.
					if (bb.intersectsWith(entityItem.bb)) {
						entityItem.remove();
						isFed = true;
					}
				}
			}
		}

		// Nearby entity check
		List<Entity> nearbyLiving = world.
			getEntitiesWithinAABB(EntityLiving.class, AABB.getBoundingBoxFromPool(x, y, z, x + 1.0, y + 1.0, z + 1.0)
			.expand(12.0, 16.0, 12.0));

		if (!nearbyLiving.isEmpty() && !(nearbyLiving instanceof EntityBird) && !isFed) {
			speed = 0.1f;
			roamRandomPath();
		}

		// Afraid check
		if (afraidTick > 0) {
			afraidTick--;
			isFed = false;
			isAfraid = true;
			courseCoolDown = 0;

			speed = 0.1f;
			roamRandomPath();
		} else {
			isAfraid = false;
			speed = 0.05f;
		}
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		afraidTick = 1200;
		return super.hurt(attacker, damage, type);
	}

	@Override
	protected void causeFallDamage(float f) {
	}

	@Override
	protected void jump() {
		if (courseCoolDown <= 0 || isInWater()) {
			super.jump();
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkinVariant", skinVariant);
		tag.putInt("CourseCoolDown", courseCoolDown);
		tag.putInt("CourseChangeCoolDown", courseChangeCoolDown);
		tag.putInt("AfraidTick", afraidTick);
		tag.putBoolean("Fed", isFed);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		skinVariant = tag.getInteger("SkinVariant");
		courseCoolDown = tag.getInteger("CourseCoolDown");
		courseChangeCoolDown = tag.getInteger("CourseChangeCoolDown");
		afraidTick = tag.getInteger("AfraidTick");
		isFed = tag.getBoolean("Fed");
	}
}
