package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityKitty;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class KittyRenderer extends LivingRenderer<EntityKitty> {
	public KittyRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/kitty.json", KittyModel.class), 0.25F);
	}
}
