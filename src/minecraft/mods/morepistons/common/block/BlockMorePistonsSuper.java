package mods.morepistons.common.block;

import java.util.ArrayList;
import java.util.Collections;

import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsSuper extends BlockMorePistonsBase {
	
	public BlockMorePistonsSuper(int id, String registerName, boolean isSticky) {
		super(id, registerName, isSticky);
	}
	

	/**
	 * Block maximal que peux pouser le piston
	 * @return
	 */
	public int getMaxBlockMove () {
		return 41;
	}
	
	protected ArrayList<EMoveInfosExtend> getListOrigin (World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
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
		
		return blocksOrigin;
	}
	
	protected ArrayList<EMoveInfosExtend> getListUpBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, int x, int y, int z, int orientation, int lenghtOpened) {

		int xExtension = x;
		int yExtension = y;
		int zExtension = z;
		
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
					
					Block block = Block.blocksList[id];
					
					if (
						!this.isEmptyBlockBlock(id) &&
						!(block instanceof BlockTrapDoor)
					) {
						int moveBlock = 0;
						if (this.isMovableBlock(id, world, xBlock, yBlock, zBlock)) {
							xExtension = xBlock;
							yExtension = yBlock;
							zExtension = zBlock;
							for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
								xExtension += Facing.offsetsXForSide[orientation];
								yExtension += Facing.offsetsYForSide[orientation];
								zExtension += Facing.offsetsZForSide[orientation];
								int idNext = world.getBlockId(xExtension, yExtension, zExtension);
								if (!this.isEmptyBlockBlock(idNext)) {
									break;
								}
								if (idNext != 0) {
									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									this.dropMobilityFlag1(idNext, metadataNext, world, xExtension, yExtension, zExtension);
								}
							}
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
					} else if (
						block instanceof BlockDoor ||
						block instanceof BlockBed
					) {
						// Drop les élements légés (fleurs, leviers, herbes ..)
						this.dropMobilityFlag1(id, metadata, world, xBlock, yBlock, zBlock);
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
		
		return blocksTop;
	}
	
	protected ArrayList<EMoveInfosExtend> getListNextBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x;
		int yExtension = y;
		int zExtension = z;
		
		ArrayList<EMoveInfosExtend> blocksList = new ArrayList<EMoveInfosExtend>();
		
		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
			
			int xBlock;
			int yBlock;
			int zBlock;
			
			// On aprcour les 4 coins
			for (int o = 2; o <= 5; o++) {
				
				if (o == orientation || o == Facing.oppositeSide[orientation]) {
					continue;
				}
				
				
				xBlock = blockOrigin.x + Facing.offsetsXForSide[o];
				yBlock = blockOrigin.y;
				zBlock = blockOrigin.z + Facing.offsetsZForSide[o];
				
				int id       = world.getBlockId(xBlock, yBlock, zBlock);
				int metadata = world.getBlockMetadata(xBlock, yBlock, zBlock);
				Block block  = (id != 0) ? Block.blocksList[id] : null;
				
				if (
					id != 0 &&
					(
						block instanceof BlockDoor ||
						block instanceof BlockBed
					)
				) {
					continue;
				}
				
				// nous avons la un block qui saccroche devrais dropper si c'etait un piston normal
				if (
					id != 0 &&
					(	this.isEmptyBlockBlock(id) ||
						block instanceof BlockTrapDoor
					)
					&& this.isAttachOnNext (id, metadata, o)
				) {
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
						
						blocksList.add(new EMoveInfosExtend(id, metadata, xExtension, yExtension, zExtension, moveBlock));
					}
				}
			}
			
		}
		
		return blocksList;
	}
	
	
	/**
	 * Retracte le block qui ets collé au piston si le piston est un sticky
	 * piston
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param length
	 */
	protected void retracSticky(World world, int x, int y, int z, int orientation, int length) {
		if (this.isSticky) {

			int x2 = x + Facing.offsetsXForSide[orientation] * (length + 1);
			int y2 = y + Facing.offsetsYForSide[orientation] * (length + 1);
			int z2 = z + Facing.offsetsZForSide[orientation] * (length + 1);
			
			ArrayList<EMoveInfosExtend> blocksOrigin = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();
			blocksOrigin.add(new EMoveInfosExtend(x2, y2, z2, length));
			
			ArrayList<EMoveInfosExtend> blocksTop    = this.getListUpBlocks(blocksOrigin, world, x, y, z, Facing.oppositeSide[orientation], length);
			ArrayList<EMoveInfosExtend> blocksNext   = this.getListNextBlocks(blocksOrigin, world, x, y, z, Facing.oppositeSide[orientation], length);
			
			super.retracSticky(world, x, y, z, orientation, length);
			
			// Dépalcement des élement Top
			for (EMoveInfosExtend blockTop : blocksTop) {
				world.setBlock(blockTop.x, blockTop.y, blockTop.z, Block.pistonMoving.blockID, blockTop.metadata, 2);
				TileEntity teBlock = new TileEntityMorePistons (blockTop.id, blockTop.metadata, Facing.oppositeSide[orientation], true, false, blockTop.move, false);
				world.setBlockTileEntity(blockTop.x, blockTop.y, blockTop.z, teBlock);
			}
			// Dépalcement des élement a coté
			for (EMoveInfosExtend blockNext : blocksNext) {
				world.setBlock(blockNext.x, blockNext.y, blockNext.z, Block.pistonMoving.blockID, blockNext.metadata, 2);
				TileEntity teBlock = new TileEntityMorePistons (blockNext.id, blockNext.metadata, Facing.oppositeSide[orientation], true, false, blockNext.move, false);
				world.setBlockTileEntity(blockNext.x, blockNext.y, blockNext.z, teBlock);
			}
			
		}
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
		
		
		ArrayList<EMoveInfosExtend> blocksOrigin = this.getListOrigin(world, x, y, z, orientation, lenghtOpened);
		ArrayList<EMoveInfosExtend> blocksTop    = this.getListUpBlocks(blocksOrigin, world, x, y, z, orientation, lenghtOpened);
		ArrayList<EMoveInfosExtend> blocksNext   = this.getListNextBlocks(blocksOrigin, world, x, y, z, orientation, lenghtOpened);
		
		super.extend(world, x, y, z, orientation, lenghtOpened);
		
		// Dépalcement des élement Top
		for (EMoveInfosExtend blockTop : blocksTop) {
			world.setBlock(blockTop.x, blockTop.y, blockTop.z, Block.pistonMoving.blockID, blockTop.metadata, 2);
			TileEntity teBlock = new TileEntityMorePistons (blockTop.id, blockTop.metadata, orientation, true, false, blockTop.move, false);
			world.setBlockTileEntity(blockTop.x, blockTop.y, blockTop.z, teBlock);
		}
		// Dépalcement des élement a coté
		for (EMoveInfosExtend blockNext : blocksNext) {
			world.setBlock(blockNext.x, blockNext.y, blockNext.z, Block.pistonMoving.blockID, blockNext.metadata, 2);
			TileEntity teBlock = new TileEntityMorePistons (blockNext.id, blockNext.metadata, orientation, true, false, blockNext.move, false);
			world.setBlockTileEntity(blockNext.x, blockNext.y, blockNext.z, teBlock);
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
			(block instanceof BlockSkull) ||                                   // Les tête
			(block instanceof BlockLadder) ||                                  // Les echelles
			(block instanceof BlockButton) ||                                  // Les boutons
			(block instanceof BlockTripWireSource) ||                          // Les piège
			(block instanceof BlockTorch && metadata != 5) ||                  // Les Torches charbons et Redstones
			(block instanceof BlockTrapDoor && (metadata & 0x8) == 0x8) ||     // Les Trappe
			(block instanceof BlockLever && metadata != 5 && metadata != 6)    // Les leviers
			
		) {
			return false;
		}
		
		return true;
	}

	protected int convertOrientationFromTorch (int o) {
		switch (o) {
			case 1: return 5;
			case 2: return 4;
			case 3: return 3;
			case 4: return 2;
			default: return 0;
		}
	}
	
	protected int convertOrientationFromTripe (int o) {
		switch (o) {
			case 0: return 3;
			case 1: return 4;
			case 2: return 2;
			case 3: return 5;
			default: return 0;
		}
	}
	
	/**
	 * @param id
	 * @param metadata
	 * @return
	 */
	protected boolean isAttachOnNext (int id, int metadata, int orientation) {
		
		if (id == 0) {
			return false;
		}
//		
//		ModMorePistons.log.debug("======================================================");
//		ModMorePistons.log.debug("metadata  : "+metadata);
//		ModMorePistons.log.debug("metadataO : "+(metadata & 0x3));
//		ModMorePistons.log.debug("this.convertOrientationFromTripe (metadata) : "+this.convertOrientationFromTripe (metadata & 0x3));
//		ModMorePistons.log.debug("orientation : "+orientation);
//		ModMorePistons.log.debug("======================================================");
		
		Block block = Block.blocksList[id];
		if (
			(block instanceof BlockLadder          && metadata == orientation) ||                                    // Les echelles
			(block instanceof BlockTripWireSource  && this.convertOrientationFromTripe (metadata)       == orientation) || // Les piège
			(block instanceof BlockButton          && this.convertOrientationFromTorch (metadata)       == orientation) || // Les boutons
			(block instanceof BlockTorch           && this.convertOrientationFromTorch (metadata)       == orientation) || // Les Torches charbons et Redstones
			(block instanceof BlockLever           && this.convertOrientationFromTorch (metadata)       == orientation) || // Les leviers
			(block instanceof BlockTrapDoor        && ((metadata & 0x3) + 2 == orientation)) || // Les leviers
			false
			
		) {
			return true;
		}
		
		return false;
	}
}
