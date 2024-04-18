package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityDeer;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class DeerRenderer extends LivingRenderer<EntityDeer> {

	public DeerRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/deer.json", DeerModel.class), 0.4f);
	}
}
