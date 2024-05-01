package teamport.creatures.block;

import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import teamport.creatures.block.entity.LitterboxRenderer;
import teamport.creatures.block.entity.TileEntityLitterbox;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCBlocks {
	public static Block LITTERBOX;

	public static void initializeBlocks() {
		LITTERBOX = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setBlockModel(new BlockModelRenderBlocks(-1))
			.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
			.build(new BlockLitterbox("litterbox", 1700));
	}

	public static void initializeClientTiles() {
		EntityHelper.Client.assignTileEntityRenderer(TileEntityLitterbox.class, new LitterboxRenderer());
	}

	public static void initializeCoreTiles() {
		EntityHelper.Core.createTileEntity(TileEntityLitterbox.class, "Litterbox");
	}
}
