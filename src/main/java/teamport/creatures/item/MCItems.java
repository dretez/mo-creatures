package teamport.creatures.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPlaceable;
import teamport.creatures.block.MCBlocks;
import turniplabs.halplibe.helper.ItemHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCItems {
	public static Item LITTERBOX;

	public static void initializeItems() {
		LITTERBOX = ItemHelper.createItem(MOD_ID, new ItemPlaceable("litterbox", 16700, MCBlocks.LITTERBOX), "litterbox.png");
	}
}
