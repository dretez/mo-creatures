package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityBoar extends EntityAnimal {
	private boolean angry;
	private int angerCounter;
	public EntityBoar(World world) {
		super(world);
		setSize(0.9F, 0.9F);
		heartsHalvesLife = 10;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/boar/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/boar/0.png";
	}

	@Override
	public void tick() {
		super.tick();
        angry = angerCounter-- > 0 && world.difficultySetting != 0;
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {

		if (attacker instanceof EntityPlayer) angerCounter = 400;
		else setTarget(attacker);

		return super.hurt(attacker, damage, type);
	}

	@Override
	protected Entity findPlayerToAttack() {
		return angry ? world.getClosestPlayerToEntity(this, 16.0D) : null;
	}


	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (!(entity instanceof EntityItem)) {
			if (!(distance > 2.0F) || !(distance < 6.0F) || random.nextInt(10) != 0) {
				if ((double)distance < 1.5 && entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY) {
					attackTime = 20;
					entity.hurt(this, 2, DamageType.COMBAT);
				}
			} else if (onGround) {
				double d = entity.x - x;
				double d1 = entity.z - z;
				float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
				xd = d / (double)f1 * 0.5 * 0.8F + xd * 0.2F;
				zd = d1 / (double)f1 * 0.5 * 0.8F + zd * 0.2F;
				yd = 0.4F;
			}
		}
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		if (entityToAttack == null && !hasPath() && world.difficultySetting != 0 && world.rand.nextInt(200) == 0) {
			List<Entity> nearbyPlayers = world
				.getEntitiesWithinAABB(
					EntityPlayer.class, AABB.getBoundingBoxFromPool(x, y, z, x + 1.0, y + 1.0, z + 1.0).expand(16.0, 4.0, 16.0)
				);

			for (Entity entity : nearbyPlayers) {
				if (entity instanceof EntityPlayer && ((EntityPlayer) entity).gamemode.areMobsHostile())
					setTarget(entity);
			}
		}
	}

	@Override
	public void playLivingSound() {
		String s = getLivingSound();
		if (s != null && !world.isClientSide) {
			world.playSoundAtEntity(null, this, s, getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 0.6F);
		}
	}

	@Override
	public void playHurtSound() {
		String s = getHurtSound();
		if (s != null && !world.isClientSide) {
			world.playSoundAtEntity(null, this, s, getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 0.6F);
		}
	}

	@Override
	public void playDeathSound() {
		String s = getDeathSound();
		if (s != null && !world.isClientSide) {
			world.playSoundAtEntity(null, this, s, getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 0.6F);
		}
	}

	public String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	@Override
	protected int getDropItemId() {
		return isOnFire() ? Item.foodPorkchopCooked.id : Item.foodPorkchopRaw.id;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Angry", angry);
		tag.putInt("Anger", angerCounter);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		angry = tag.getBoolean("Angry");
		angerCounter = tag.getInteger("Anger");
	}
}
