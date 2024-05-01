package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import useless.dragonfly.model.entity.AnimationState;

public class EntityBunny extends EntityAnimal {
	public AnimationState jumpState = new AnimationState();
	int jumpDelay;
	int skinVariant;

	public EntityBunny(World world) {
		super(world);
		heartsHalvesLife = 5;
		jumpDelay = 0;
		setSize(0.4F, 0.4F);
		skinVariant = random.nextInt(5);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/bunny/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/bunny/0.png";
	}

	@Override
	protected void updatePlayerActionState() {
		++entityAge;
		tryToDespawn();
		if (isInWater() || isInLava()) yd += 0.15F;

		if (onGround && jumpDelay-- <= 0) {

			jumpDelay = random.nextInt(20) + 10;
			isJumping = true;
			jumpState.start(tickCount);
			world.playSoundAtEntity(null,
				this,
				"creatures.rabbitland",
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

			moveStrafing = 1.0F - random.nextFloat() * 2.0F;
			moveForward = 1.0F;
			roamRandomPath();
		} else {
			isJumping = false;
			if (onGround) {
				jumpState.stop();
				moveStrafing = moveForward = 0.0F;

				if (random.nextFloat() < 0.05F) randomYawVelocity = (random.nextFloat() - 0.5F) * 20.0F;
				yRot += randomYawVelocity;
				xRot = defaultPitch;
			}
		}
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item == null && entityplayer.passenger == null) {
			startRiding(entityplayer);
			world.playSoundAtEntity(null,
				this,
				"creatures.rabbitland",
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		} else if (entityplayer.passenger != null) {
			entityplayer.ejectRider();
			world.playSoundAtEntity(null,
				this,
				"creatures.rabbitland",
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

			setPos(entityplayer.x, entityplayer.y, entityplayer.z);
			moveForward = 1.0F;
			jump();
			jumpState.start(tickCount);
		}

		return super.interact(entityplayer);
	}

	@Override
	protected String getDeathSound() {
		return "creatures.rabbitdeath";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.rabbithurt";
	}

	@Override
	public String getLivingSound() {
		return null;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkinVariant", skinVariant);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		skinVariant = tag.getInteger("SkinVariant");
	}
}
