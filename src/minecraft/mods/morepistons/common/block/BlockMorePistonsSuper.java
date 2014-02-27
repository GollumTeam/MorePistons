package mods.morepistons.common.block;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;

import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockTorch;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsSuper extends BlockMorePistonsBase {
	
	public BlockMorePistonsSuper(int id, boolean isSticky) {
		this(id, isSticky, "");
	}
	public BlockMorePistonsSuper(int id, boolean isSticky, String texturePrefixe) {
		super(id, isSticky, "super_"+texturePrefixe);
	}
	

	/**
	 * Block maximal que peux pouser le piston
	 * @return
	 */
	public int getMaxBlockMove () {
		return 41;
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
		
		ArrayList<EMoveInfosExtend> blocksOrigin = new ArrayList<EMoveInfosExtend>();
		int size = lenghtOpened;
		
		for (int i = 0; i < (lenghtOpened + this.getMaxBlockMove ()) && size > 0; i++) {
			
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
			blocksOrigin.add (new EMoveInfosExtend(xExtension, yExtension, zExtension, size));
		}
		Collections.reverse (blocksOrigin);
		
		ArrayList<EMoveInfosExtend> blocksTop = new ArrayList<EMoveInfosExtend>();
		
		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
			int xBlock;
			int yBlock;
			int zBlock;
			
			// Si on est à l'orizontal
			if (orientation != 0 && orientation != 1) {

				xBlock = blockOrigin.x;
				yBlock = blockOrigin.y + 1;
				zBlock = blockOrigin.z;
				
				int id       = world.getBlockId(xBlock, yBlock, zBlock);
				int metadata = world.getBlockMetadata(xBlock, yBlock, zBlock);
				
				// Déplacement des block standare au dessus
				if (id != 0) {
					if (!this.isEmptyBlockBlock(id)) {
						int moveBlock = 0;
						if (this.isMovableBlock(id, world, xBlock, yBlock, zBlock)) {
							moveBlock = this.getMaximalOpenedLenght(world, xBlock, yBlock, zBlock, orientation, false, blockOrigin.move);
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
									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									this.dropMobilityFlag1(idNext, metadataNext, world, xExtension, yExtension, zExtension);
								}
							}
							
							blocksTop.add(new EMoveInfosExtend(id, metadata, xExtension, yExtension, zExtension, moveBlock));
						}
					} else {
						// nous avons la un block qui saccroche devrais dropper si c'etait un piston normal
						if (this.isAttachOnTop (id, metadata)) {
							int moveBlock = 0;
							xExtension = xBlock;
							yExtension = yBlock;
							zExtension = zBlock;
							for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
								xExtension += Facing.offsetsXForSide[orientation];
								yExtension += Facing.offsetsYForSide[orientation];
								zExtension += Facing.offsetsZForSide[orientation];
								if (world.getBlockId(xExtension, yExtension, zExtension) != 0) {
									break;
								}
							}
							
							// Si le mouvement ne peux etre complet alors on drop l'element
							if (moveBlock != blockOrigin.move) {
								// Drop les élements légés (fleurs, leviers, herbes ..)
								this.dropMobilityFlag1(id, metadata, world, xBlock, yBlock, zBlock);
							} else {
								world.setBlockToAir(xBlock, yBlock, zBlock);
								
								xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock;
								yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock;
								zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock;
								
								blocksTop.add(new EMoveInfosExtend(id, metadata, xExtension, yExtension, zExtension, moveBlock));
							}
						}
					}
				}
			}
			
		}
		
		super.extend(world, x, y, z, orientation, lenghtOpened);
		
		// Dépalcement des élement Top
		for (EMoveInfosExtend blockTop : blocksTop) {
			world.setBlock(blockTop.x, blockTop.y, blockTop.z, Block.pistonMoving.blockID, blockTop.metadata, 2);
			TileEntity teBlock = new TileEntityMorePistons (blockTop.id, blockTop.metadata, orientation, true, false, blockTop.move, false);
			world.setBlockTileEntity(blockTop.x, blockTop.y, blockTop.z, teBlock);
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
			(block instanceof BlockLever && metadata != 5 && metadata != 6) // Les leviers
			
		) {
			return false;
		}
		
		return true;
	}
	
}
