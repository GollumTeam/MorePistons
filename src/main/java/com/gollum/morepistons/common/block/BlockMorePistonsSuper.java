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

		ArrayList<EMoveInfosExtend> infosExtend = super.listBlockExtend(world, x, y, z, orientation, currentOpened, lenghtOpened, false);
		ArrayList<EMoveInfosExtend> upBlocks    = this.listUpBlocks(infosExtend, world, x, y, z, orientation, currentOpened, lenghtOpened, true);
		ArrayList<EMoveInfosExtend> nextBlocks  = this.listNextBlocks(infosExtend, world, x, y, z, orientation, currentOpened, lenghtOpened, true);
		
		for (EMoveInfosExtend infos : infosExtend) {
			if (infos.block != null && infos.position != null) {
				world.setTileEntity(infos.position.x, infos.position.y, infos.position.z, null);
				world.setBlock (infos.position.x, infos.position.y, infos.position.z, Blocks.air, 0, 0);
			}
		}
		
		ArrayList<EMoveInfosExtend> all = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();
		
		all.addAll(infosExtend);
		all.addAll(upBlocks);
		all.addAll(nextBlocks);
		
		return all;
	}
	
	@Override
	protected ArrayList<EMoveInfosExtend> listBlockRetract (World world, int x, int y, int z, int orientation, int lenghtClose) {
		
		ArrayList<EMoveInfosExtend> infosRetract = super.listBlockRetract(world, x, y, z, orientation, lenghtClose, false);
		ArrayList<EMoveInfosExtend> upBlocks     = this.listUpBlocks(infosRetract, world, x, y, z, orientation, 0, lenghtClose, false);
		ArrayList<EMoveInfosExtend> nextBlocks   = this.listNextBlocks(infosRetract, world, x, y, z, orientation, 0, lenghtClose, false);
		
		for (EMoveInfosExtend infos : infosRetract) {
			if (infos.block != null && infos.position != null) {
				world.setTileEntity(infos.position.x, infos.position.y, infos.position.z, null);
				world.setBlock (infos.position.x, infos.position.y, infos.position.z, Blocks.air, 0, 0);
			}
		}
		
		ArrayList<EMoveInfosExtend> all = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();
		
		all.addAll(infosRetract);
		all.addAll(upBlocks);
		all.addAll(nextBlocks);
		
		return all;
		
	}
	
	protected ArrayList<EMoveInfosExtend> listUpBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened, boolean extend) {

		int xExtension;
		int yExtension;
		int zExtension;
		
		int direction = extend ? 1 : -1;
		
		ArrayList<EMoveInfosExtend> blocksTop = new ArrayList<EMoveInfosExtend>();
		ArrayList<EMoveInfosExtend> dropList  = new ArrayList<EMoveInfosExtend>();
		
		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
			int xBlock;
			int yBlock;
			int zBlock;
			
			// Si on est à l'orizontal
			if (blockOrigin.block != null && blockOrigin.position != null && orientation != 0 && orientation != 1) {

				xBlock = blockOrigin.position.x;
				yBlock = blockOrigin.position.y + 1;
				zBlock = blockOrigin.position.z;
				
				Block block   = world.getBlock(xBlock, yBlock, zBlock);
				int metadata  = world.getBlockMetadata(xBlock, yBlock, zBlock);
				TileEntity te = world.getTileEntity(xBlock, yBlock, zBlock);
				
				
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
								xExtension += Facing.offsetsXForSide[orientation]*direction;
								yExtension += Facing.offsetsYForSide[orientation]*direction;
								zExtension += Facing.offsetsZForSide[orientation]*direction;
								Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
								if (!this.isEmptyBlock(blockNext)) {
									break;
								}
								if (blockNext != null && blockNext != Blocks.air) {
									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
										dropList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), 0));
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
								xExtension += Facing.offsetsXForSide[orientation]*direction;
								yExtension += Facing.offsetsYForSide[orientation]*direction;
								zExtension += Facing.offsetsZForSide[orientation]*direction;
								Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
								if (blockNext != null && block != Blocks.air) {
									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
									// Drop les élements légés (fleurs, leviers, herbes ..)
									if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
										dropList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), 0));
									}
								}
							}
							
							blocksTop.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xBlock, yBlock, zBlock), moveBlock));
						}
					} else if (!SuperPistonManager.instance.isntAttachOnTop (block, metadata)) {
						
						int moveBlock = 0;
						xExtension = xBlock;
						yExtension = yBlock;
						zExtension = zBlock;
						for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
							xExtension += Facing.offsetsXForSide[orientation]*direction;
							yExtension += Facing.offsetsYForSide[orientation]*direction;
							zExtension += Facing.offsetsZForSide[orientation]*direction;
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
							
							xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock*direction;
							yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock*direction;
							zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock*direction;
							
							blocksTop.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xBlock, yBlock, zBlock), moveBlock));
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
	
	protected ArrayList<EMoveInfosExtend> listNextBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened, boolean extend) {
		
		int xExtension;
		int yExtension;
		int zExtension;
		
		int direction = extend ? 1 : -1;
		
		ArrayList<EMoveInfosExtend> blocksList = new ArrayList<EMoveInfosExtend>();
		ArrayList<EMoveInfosExtend> dropList  = new ArrayList<EMoveInfosExtend>();
		
		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
			
			if (blockOrigin.block != null && blockOrigin.position != null) {
				
				// On aprcour les 4 coins
				for (int o = 2; o <= 5; o++) {
					
					if (o == orientation || o == Facing.oppositeSide[orientation]) {
						continue;
					}
					
					
					int xBlock = blockOrigin.position.x + Facing.offsetsXForSide[o]*direction;
					int yBlock = blockOrigin.position.y;
					int zBlock = blockOrigin.position.z + Facing.offsetsZForSide[o]*direction;
					
					Block block   = world.getBlock(xBlock, yBlock, zBlock);
					int metadata  = world.getBlockMetadata(xBlock, yBlock, zBlock);
					TileEntity te = world.getTileEntity(xBlock, yBlock, zBlock);
					
					// nous avons la un block qui saccroche devrais dropper si c'etait un piston normal
					if (
						block != null && 
						block != Blocks.air &&
						(	this.isEmptyBlock(block) ||
							block instanceof BlockTrapDoor
						)
						&& SuperPistonManager.instance.isAttachOnNext (block, metadata, extend ? o : Facing.oppositeSide[o])
					) {
						int moveBlock = 0;
						xExtension = xBlock;
						yExtension = yBlock;
						zExtension = zBlock;
						for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
							xExtension += Facing.offsetsXForSide[orientation]*direction;
							yExtension += Facing.offsetsYForSide[orientation]*direction;
							zExtension += Facing.offsetsZForSide[orientation]*direction;
							Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
							if (blockNext != null && blockNext != Blocks.air) {
								break;
							}
						}
						
						// Si le mouvement ne peux etre complet alors on drop l'element
						if (moveBlock != blockOrigin.move) {
							// Drop les élements légés (fleurs, leviers, herbes ..)
							if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
								dropList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), 0));
							}
						} else {
							world.setBlockToAir(xBlock, yBlock, zBlock);
							
							xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock;
							yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock;
							zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock;
							
							blocksList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xBlock, yBlock, zBlock), moveBlock));
						}
					}
				}
			}
		}
		
		for (EMoveInfosExtend info : dropList) {
			this.dropMobilityFlag1(info.block, info.metadata, world, info.position.x, info.position.y, info.position.z);
		}
		
		return blocksList;
	}
	
}
