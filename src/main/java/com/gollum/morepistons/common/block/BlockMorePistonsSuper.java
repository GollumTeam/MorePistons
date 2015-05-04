package com.gollum.morepistons.common.block;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.common.block.BlockMorePistonsBase.EMoveInfosExtend;
import com.gollum.morepistons.common.block.superpiston.SuperPistonManager;

public class BlockMorePistonsSuper extends BlockMorePistonsBase {
	
	public BlockMorePistonsSuper(String registerName, boolean isSticky) {
		super(registerName, isSticky);
	}
	
	/**
	 * Block maximal que peux pouser le piston
	 * @return
	 */
	@Override
	public int getMaxBlockMove () {
		return ModMorePistons.config.numberMovableBlockWithSuperPiston;
	}
	
	@Override
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened) {

		ArrayList<EMoveInfosExtend> infosExtend = super.listBlockExtend(world, x, y, z, orientation, currentOpened, lenghtOpened);
		ArrayList<EMoveInfosExtend> upBlocks    = this.listUpBlocks(infosExtend, world, x, y, z, orientation, currentOpened, lenghtOpened);
		
		infosExtend.addAll(upBlocks);
		
		ArrayList<EMoveInfosExtend> all = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();

		all.addAll(infosExtend);
		all.addAll(upBlocks);
		
		return all;
	}
	
	protected ArrayList<EMoveInfosExtend> listUpBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened) {

		int xExtension;
		int yExtension;
		int zExtension;
		
		ArrayList<EMoveInfosExtend> blocksTop = new ArrayList<EMoveInfosExtend>();
		ArrayList<EMoveInfosExtend> dropList  = new ArrayList<EMoveInfosExtend>();
		
		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
			int xBlock;
			int yBlock;
			int zBlock;
			
			// Si on est à l'orizontal
			if (orientation != 0 && orientation != 1) {

				xBlock = blockOrigin.position.x;
				yBlock = blockOrigin.position.y + 1;
				zBlock = blockOrigin.position.z;
				
				Block block    = world.getBlock(xBlock, yBlock, zBlock);
				int   metadata = world.getBlockMetadata(xBlock, yBlock, zBlock);
				
				
				// Déplacement des block standare au dessus
				if (block != null && block != Blocks.air) {
					
					if (
						!this.isEmptyBlock(block) &&
						!SuperPistonManager.instance.dontMoveIfOnTop(block)
					) {
						
						int moveBlock = 0;
						if (this.isMovableBlock(block, world, xBlock, yBlock, zBlock)) {
							xExtension = xBlock;
							yExtension = yBlock;
							zExtension = zBlock;
							for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
								xExtension += Facing.offsetsXForSide[orientation];
								yExtension += Facing.offsetsYForSide[orientation];
								zExtension += Facing.offsetsZForSide[orientation];
								Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
								if (!this.isEmptyBlock(blockNext)) {
									break;
								}
								if (blockNext != null && blockNext != Blocks.air) {
									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
										dropList.add(new EMoveInfosExtend(block, metadata, new Integer3d(xExtension, yExtension, zExtension), 0));
									}
								}
							}
						}
						if (moveBlock > 0) {
							world.setTileEntity(xBlock, yBlock, zBlock, null);
							world.setBlock (xBlock, yBlock, zBlock, Blocks.air, 0, 0);
							xExtension = xBlock;
							yExtension = yBlock;
							zExtension = zBlock;
							// Fait sauter les element avec un flag 1 qui non pas bouger 
							for (int j = 0; j < moveBlock; j++) {
								xExtension += Facing.offsetsXForSide[orientation];
								yExtension += Facing.offsetsYForSide[orientation];
								zExtension += Facing.offsetsZForSide[orientation];
								Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
								if (blockNext != null && block != Blocks.air) {
									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
										dropList.add(new EMoveInfosExtend(block, metadata, new Integer3d(xExtension, yExtension, zExtension), 0));
									}
								}
							}
							
							blocksTop.add(new EMoveInfosExtend(block, metadata, new Integer3d(xBlock, yBlock, zBlock), moveBlock));
						}
					} else if (!SuperPistonManager.instance.isntAttachOnTop (block, metadata)) {
						
						int moveBlock = 0;
						xExtension = xBlock;
						yExtension = yBlock;
						zExtension = zBlock;
						for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
							xExtension += Facing.offsetsXForSide[orientation];
							yExtension += Facing.offsetsYForSide[orientation];
							zExtension += Facing.offsetsZForSide[orientation];
							Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
							if (blockNext != null && blockNext != Blocks.air) {
								break;
							}
						}
						
						// Si le mouvement ne peux etre complet alors on drop l'element
						if (moveBlock != blockOrigin.move) {
							// Drop les élements légés (fleurs, leviers, herbes ..)
							this.dropMobilityFlag1(block, metadata, world, xBlock, yBlock, zBlock);
						} else {
							world.setTileEntity(xBlock, yBlock, zBlock, null);
							world.setBlock (xBlock, yBlock, zBlock, Blocks.air, 0, 0);
							
							xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock;
							yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock;
							zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock;
							
							blocksTop.add(new EMoveInfosExtend(block, metadata, new Integer3d(xBlock, yBlock, zBlock), moveBlock));
						}
					}
				}
			}
			
		}
		
		for (EMoveInfosExtend info : dropList) {
			this.dropMobilityFlag1(info.block, info.metadata, world, info.position.x, info.position.y, info.position.z);
		}
		
		return blocksTop;
	}
	
//	protected ArrayList<EMoveInfosExtend> getListOrigin (World world, int x, int y, int z, int orientation, int lenghtOpened) {
//		
//		int xExtension = x;
//		int yExtension = y;
//		int zExtension = z;
//		
//		ArrayList<EMoveInfosExtend> blocksOrigin = new ArrayList<EMoveInfosExtend>();
//		int size = lenghtOpened;
//		
//		for (int i = 0; i < (lenghtOpened + this.getMaxBlockMove ()) && size > 0; i++) {
//			
//			xExtension += Facing.offsetsXForSide[orientation];
//			yExtension += Facing.offsetsYForSide[orientation];
//			zExtension += Facing.offsetsZForSide[orientation];
//			
//			Block block = world.getBlock(xExtension, yExtension, zExtension);
//			int metadata = world.getBlockMetadata(xExtension, yExtension, zExtension);
//			
//			if (this.isEmptyBlock(block)) {
//				size--;
//				continue;
//			} else if (!this.isMovableBlock(block, world, xExtension, yExtension, zExtension)) {
//				break;
//			}
//			blocksOrigin.add (new EMoveInfosExtend(xExtension, yExtension, zExtension, size));
//		}
//		Collections.reverse (blocksOrigin);
//		
//		return blocksOrigin;
//	}
//	
//	
//	protected ArrayList<EMoveInfosExtend> getListNextBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, int x, int y, int z, int orientation, int lenghtOpened) {
//		
//		int xExtension = x;
//		int yExtension = y;
//		int zExtension = z;
//		
//		ArrayList<EMoveInfosExtend> blocksList = new ArrayList<EMoveInfosExtend>();
//		
//		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
//			
//			int xBlock;
//			int yBlock;
//			int zBlock;
//			
//			// On aprcour les 4 coins
//			for (int o = 2; o <= 5; o++) {
//				
//				if (o == orientation || o == Facing.oppositeSide[orientation]) {
//					continue;
//				}
//				
//				
//				xBlock = blockOrigin.x + Facing.offsetsXForSide[o];
//				yBlock = blockOrigin.y;
//				zBlock = blockOrigin.z + Facing.offsetsZForSide[o];
//				
//				Block block       = world.getBlock(xBlock, yBlock, zBlock);
//				int metadata = world.getBlockMetadata(xBlock, yBlock, zBlock);
//				
//				if (
//					block != null &&
//					(
//						block instanceof BlockDoor ||
//						block instanceof BlockBed
//					)
//				) {
//					continue;
//				}
//				
//				// nous avons la un block qui saccroche devrais dropper si c'etait un piston normal
//				if (
//					block != null && 
//					block != Blocks.air &&
//					(	this.isEmptyBlock(block) ||
//						block instanceof BlockTrapDoor
//					)
//					&& this.isAttachOnNext (block, metadata, o)
//				) {
//					int moveBlock = 0;
//					xExtension = xBlock;
//					yExtension = yBlock;
//					zExtension = zBlock;
//					for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
//						xExtension += Facing.offsetsXForSide[orientation];
//						yExtension += Facing.offsetsYForSide[orientation];
//						zExtension += Facing.offsetsZForSide[orientation];
//						Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
//						if (blockNext != null && blockNext != Blocks.air) {
//							break;
//						}
//					}
//					
//					// Si le mouvement ne peux etre complet alors on drop l'element
//					if (moveBlock != blockOrigin.move) {
//						// Drop les élements légés (fleurs, leviers, herbes ..)
//						this.dropMobilityFlag1(block, metadata, world, xBlock, yBlock, zBlock);
//					} else {
//						world.setBlockToAir(xBlock, yBlock, zBlock);
//						
//						xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock;
//						yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock;
//						zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock;
//						
//						blocksList.add(new EMoveInfosExtend(block, metadata, xExtension, yExtension, zExtension, moveBlock));
//					}
//				}
//			}
//			
//		}
//		
//		return blocksList;
//	}
//	
//	
//	/**
//	 * Retracte le block qui ets collé au piston si le piston est un sticky
//	 * piston
//	 * 
//	 * @param world
//	 * @param x
//	 * @param y
//	 * @param z
//	 * @param length
//	 */
//	protected void retracSticky(World world, int x, int y, int z, int orientation, int length) {
//		if (this.isSticky) {
//
//			int x2 = x + Facing.offsetsXForSide[orientation] * (length + 1);
//			int y2 = y + Facing.offsetsYForSide[orientation] * (length + 1);
//			int z2 = z + Facing.offsetsZForSide[orientation] * (length + 1);
//			
//			ArrayList<EMoveInfosExtend> blocksOrigin = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();
//			blocksOrigin.add(new EMoveInfosExtend(x2, y2, z2, length));
//			
//			ArrayList<EMoveInfosExtend> blocksTop    = this.getListUpBlocks(blocksOrigin, world, x, y, z, Facing.oppositeSide[orientation], length);
//			ArrayList<EMoveInfosExtend> blocksNext   = this.getListNextBlocks(blocksOrigin, world, x, y, z, Facing.oppositeSide[orientation], length);
//			
//			super.retracSticky(world, x, y, z, orientation, length);
//			
//			// Dépalcement des élement Top
//			for (EMoveInfosExtend blockTop : blocksTop) {
//				world.setBlock(blockTop.x, blockTop.y, blockTop.z, Blocks.piston_extension, blockTop.metadata, 2);
//				TileEntity teBlock = new TileEntityMorePistons (blockTop.block, blockTop.metadata, Facing.oppositeSide[orientation], true, false, blockTop.move, false);
//				world.setTileEntity(blockTop.x, blockTop.y, blockTop.z, teBlock);
//			}
//			// Dépalcement des élement a coté
//			for (EMoveInfosExtend blockNext : blocksNext) {
//				world.setBlock(blockNext.x, blockNext.y, blockNext.z, Blocks.piston_extension, blockNext.metadata, 2);
//				TileEntity teBlock = new TileEntityMorePistons (blockNext.block, blockNext.metadata, Facing.oppositeSide[orientation], true, false, blockNext.move, false);
//				world.setTileEntity(blockNext.x, blockNext.y, blockNext.z, teBlock);
//			}
//			
//		}
//	}
//	
//	/**
//	 * Ouvre un piston de la taille voulu
//	 * @param world
//	 * @param x
//	 * @param y
//	 * @param z
//	 * @param orientation
//	 * @param lenghtOpened
//	 */
//	protected void extend(World world, int x, int y, int z, int orientation, int lenghtOpened) {
//		
//		
//		ArrayList<EMoveInfosExtend> blocksOrigin = this.getListOrigin(world, x, y, z, orientation, lenghtOpened);
//		ArrayList<EMoveInfosExtend> blocksTop    = this.getListUpBlocks(blocksOrigin, world, x, y, z, orientation, lenghtOpened);
//		ArrayList<EMoveInfosExtend> blocksNext   = this.getListNextBlocks(blocksOrigin, world, x, y, z, orientation, lenghtOpened);
//		
//		super.extend(world, x, y, z, orientation, lenghtOpened);
//		
//		// Dépalcement des élement Top
//		for (EMoveInfosExtend blockTop : blocksTop) {
//			world.setBlock(blockTop.x, blockTop.y, blockTop.z, Blocks.piston_extension, blockTop.metadata, 2);
//			TileEntity teBlock = new TileEntityMorePistons (blockTop.block, blockTop.metadata, orientation, true, false, blockTop.move, false);
//			world.setTileEntity(blockTop.x, blockTop.y, blockTop.z, teBlock);
//		}
//		// Dépalcement des élement a coté
//		for (EMoveInfosExtend blockNext : blocksNext) {
//			world.setBlock(blockNext.x, blockNext.y, blockNext.z, Blocks.piston_extension, blockNext.metadata, 2);
//			TileEntity teBlock = new TileEntityMorePistons (blockNext.block, blockNext.metadata, orientation, true, false, blockNext.move, false);
//			world.setTileEntity(blockNext.x, blockNext.y, blockNext.z, teBlock);
//		}
//		
//		
//	}
//
//	protected int convertOrientationFromTorch (int o) {
//		switch (o) {
//			case 1: return 5;
//			case 2: return 4;
//			case 3: return 3;
//			case 4: return 2;
//			default: return 0;
//		}
//	}
//	
//	protected int convertOrientationFromTripe (int o) {
//		switch (o) {
//			case 0: return 3;
//			case 1: return 4;
//			case 2: return 2;
//			case 3: return 5;
//			default: return 0;
//		}
//	}
//	
//	/**
//	 * @param id
//	 * @param metadata
//	 * @return
//	 */
//	protected boolean isAttachOnNext (Block block, int metadata, int orientation) {
//		
//		if (block == null || block == Blocks.air) {
//			return false;
//		}
////		
//		if (
//			(block instanceof BlockLadder       && metadata == orientation) ||                                    // Les echelles
//			(block instanceof BlockTripWireHook && this.convertOrientationFromTripe (metadata)       == orientation) || // Les piège
//			(block instanceof BlockButton       && this.convertOrientationFromTorch (metadata)       == orientation) || // Les boutons
//			(block instanceof BlockTorch        && this.convertOrientationFromTorch (metadata)       == orientation) || // Les Torches charbons et Redstones
//			(block instanceof BlockLever        && this.convertOrientationFromTorch (metadata)       == orientation) || // Les leviers
//			(block instanceof BlockTrapDoor     && ((metadata & 0x3) + 2 == orientation)) || // Les leviers
//			false
//			
//		) {
//			return true;
//		}
//		
//		return false;
//	}
}
