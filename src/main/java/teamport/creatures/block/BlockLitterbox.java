package teamport.creatures.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import teamport.creatures.block.entity.TileEntityLitterbox;
import teamport.creatures.item.MCItems;

public class BlockLitterbox extends BlockTileEntityRotatable {

	public BlockLitterbox(String key, int id) {
		super(key, id, Material.wood);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new TileEntityLitterbox();
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		TileEntityLitterbox tileEntity = (TileEntityLitterbox) world.getBlockTileEntity(x, y, z);
		ItemStack heldItem = player.getHeldItem();

		if (tileEntity != null && tileEntity.isFilthy) {
			if (heldItem != null && heldItem.itemID == Block.sand.id) {
				tileEntity.isFilthy = false;
				heldItem.consumeItem(player);
			}
		}

		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{new ItemStack(MCItems.LITTERBOX)};
	}
}
