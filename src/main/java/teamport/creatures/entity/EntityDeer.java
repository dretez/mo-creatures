package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

public class EntityDeer extends EntityAnimal {
	public boolean isScared;
	private boolean isBuck;
	private int scaredTick = 0;

	public EntityDeer(World world) {
		super(world);
		heartsHalvesLife = 10;
		setSize(0.3f, 2.0f);

		if (random.nextInt(2) == 0) {
			isBuck = true;
		}
	}

	@Override
	public String getEntityTexture() {
		return isBuck ? "/assets/creatures/entity/deer/b_0.png" : "/assets/creatures/entity/deer/f_0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/deer/b_0.png";
	}

	@Override
	protected int getDropItemId() {
		return isOnFire() ? Item.foodPorkchopCooked.id : Item.foodPorkchopRaw.id;
	}

	@Override
	public String getLivingSound() {
		return "creatures.deerfgrunt";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.deerhurt";
	}

	@Override
	protected String getDeathSound() {
		return "creatures.deerdying";
	}


	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0f);

		// Just a simple flee system;
		// Checks if the player isn't null, isn't sneaking, and isn't in creative
		// or if it's in a fear state
		if (player != null && !player.isSneaking() && player.gamemode.areMobsHostile()) {
			faceEntity(player, 0, 0);

			speed = 0.2f;
			roamRandomPath();
		} else if (isScared) {
			speed = 0.2f;
			roamRandomPath();
		} else {
			speed = 0.1f;
		}

		if (scaredTick > 0) {
			isScared = true;
			scaredTick--;
		}
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		scaredTick = 1200;
		return super.hurt(attacker, damage, type);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("ScaredTick", scaredTick);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		scaredTick = tag.getInteger("ScaredTick");
	}
}
