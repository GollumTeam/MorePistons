package mods.morepistons.common.block;

import java.awt.List;
import java.util.ArrayList;

import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockTorch;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsSuper extends BlockMorePistonsBase {
	
	public static int MAX_BLOCK_MOVE = 41;
	
	public BlockMorePistonsSuper(int id, boolean isSticky) {
		super(id, isSticky, "super_");
	}
	
	/**
	 * Ouvre un piston de la taille voulu
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param lenghtOpened
	 */
	protected void extend(World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x;
		int yExtension = y;
		int zExtension = z;
		
		ArrayList<Integer> listX = new ArrayList<Integer>();
		ArrayList<Integer> listY = new ArrayList<Integer>();
		ArrayList<Integer> listZ = new ArrayList<Integer>();
		ArrayList<Integer> listSize = new ArrayList<Integer>();
		int size = lenghtOpened;
		
		for (int i = 0; i < (lenghtOpened + this.MAX_BLOCK_MOVE) && size > 0; i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
			int id = world.getBlockId(xExtension, yExtension, zExtension);
			int metadata = world.getBlockMetadata(xExtension, yExtension, zExtension);
			
			if (this.isEmptyBlockBlock(id)) {
				size--;
				continue;
			} else if (!this.isMovableBlock(id, world, xExtension, yExtension, zExtension)) {
				break;
			}
			listX.add (xExtension);
			listY.add (yExtension);
			listZ.add (zExtension);
			listSize.add (size);
		}
		
		super.extend(world, x, y, z, orientation, lenghtOpened);
		
		for (int i = listX.size()-1; i >= 0; i--) {
			int xOrigin    = listX.get(i);
			int yOrigin    = listY.get(i);
			int zOrigin    = listZ.get(i);
			int sizeOrigin = listSize.get(i);
			int xBlock;
			int yBlock;
			int zBlock;
			
			// Si on est à l'orizontal
			if (orientation != 0 && orientation != 1) {

				xBlock = xOrigin;
				yBlock = yOrigin + 1;
				zBlock = zOrigin;
				
				int id       = world.getBlockId(xBlock, yBlock, zBlock);
				int metadata = world.getBlockMetadata(xBlock, yBlock, zBlock);
				
				// Déplacement des block standare au dessus
				if (id != 0) {
					if (!this.isEmptyBlockBlock(id)) {
						int moveBlock = 0;
						if (this.isMovableBlock(id, world, xBlock, yBlock, zBlock)) {
							moveBlock = this.getMaximalOpenedLenght(world, xBlock, yBlock, zBlock, orientation, false, sizeOrigin);
						}
						if (moveBlock > 0) {
							world.setBlockToAir(xBlock, yBlock, zBlock);
							xExtension = xBlock;
							yExtension = yBlock;
							zExtension = zBlock;
							// Fait sauter les element avec un flag 1 qui non pas bouger 
							for (int j = 0; j < moveBlock; j++) {
								xExtension += Facing.offsetsXForSide[orientation];
								yExtension += Facing.offsetsYForSide[orientation];
								zExtension += Facing.offsetsZForSide[orientation];
								int idNext = world.getBlockId(xExtension, yExtension, zExtension);
								if (idNext != 0) {
									int metadataNext = world.getBlockMetadata(xBlock, yBlock, zBlock);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									this.dropMobilityFlag1(idNext, metadataNext, world, xExtension, yExtension, zExtension);
								}
							}
							
							//Déplace avec une animation les blocks
							world.setBlock(xExtension, yExtension, zExtension, Block.pistonMoving.blockID, metadata, 2);
							TileEntity teBlock = new TileEntityMorePistons (id, metadata, orientation, true, false, moveBlock, false);
							world.setBlockTileEntity(xExtension, yExtension, zExtension, teBlock);
						}
					} else {
						// nous avons la un block qui saccroche devrais dropper si c'etait un piston normal
						if (this.isAttachOnTop (id, metadata)) {
							int moveBlock = 0;
							xExtension = xBlock;
							yExtension = yBlock;
							zExtension = zBlock;
							for (moveBlock = 0; moveBlock < sizeOrigin; moveBlock++) {
								xExtension += Facing.offsetsXForSide[orientation];
								yExtension += Facing.offsetsYForSide[orientation];
								zExtension += Facing.offsetsZForSide[orientation];
								if (world.getBlockId(xExtension, yExtension, zExtension) != 0) {
									break;
								}
							}
							
							// Si le mouvement ne peux etre complet alors on drop l'element
							if (moveBlock != sizeOrigin) {
								// Drop les élements légés (fleurs, leviers, herbes ..)
								this.dropMobilityFlag1(id, metadata, world, xBlock, yBlock, zBlock);
							} else {
								world.setBlockToAir(xBlock, yBlock, zBlock);
								
								xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock;
								yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock;
								zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock;
								
								//Déplace avec une animation les blocks
								world.setBlock(xExtension, yExtension, zExtension, Block.pistonMoving.blockID, metadata, 2);
								TileEntity teBlock = new TileEntityMorePistons (id, metadata, orientation, true, false, moveBlock, false);
								world.setBlockTileEntity(xExtension, yExtension, zExtension, teBlock);
							}
						}
					}
				}
				
			}
			
		}
	}
	
	/**
	 * @param id
	 * @param metadata
	 * @return
	 */
	protected boolean isAttachOnTop (int id, int metadata) {
		
		if (id == 0) {
			return false;
		}
		
		Block block = Block.blocksList[id];
		if (
			(block instanceof BlockLadder) ||                                  // Les echelles
			(block instanceof BlockTorch && metadata != 5)||                   // Les Torches charbons et Redstones
			(block instanceof BlockLever && metadata != 5 && metadata != 6) || // Les leviers
			(id == Block.signWall.blockID)                                     // Les panneaux murals
			
		) {
			return false;
		}
		
		return true;
	}
	
}
