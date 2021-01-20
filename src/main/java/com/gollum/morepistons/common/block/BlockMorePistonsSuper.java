package com.gollum.morepistons.common.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened, boolean removeAfter) {
		
		ArrayList<EMoveInfosExtend> infosExtend = super.listBlockExtend(world, pos, facing, currentOpened, lenghtOpened, false);
		ArrayList<EMoveInfosExtend> upBlocks    = this.listUpBlocks(infosExtend, world, pos, facing, currentOpened, lenghtOpened, true);
		ArrayList<EMoveInfosExtend> nextBlocks  = this.listNextBlocks(infosExtend, world, pos, facing, currentOpened, lenghtOpened, true);
		
		for (EMoveInfosExtend infos : infosExtend) {
			if (infos.state != null && infos.position != null) {
				world.setTileEntity(infos.position, null);
				world.setBlockToAir(infos.position);
			}
		}
		
		ArrayList<EMoveInfosExtend> all = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();
		
		all.addAll(upBlocks);
		all.addAll(nextBlocks);
		all.addAll(infosExtend);
		
		return all;
	}
	
	@Override
	protected ArrayList<EMoveInfosExtend> listBlockRetract (World world, BlockPos pos, EnumFacing facing, int lenghtClose, int stickySize, boolean removeAfter) {
			
		ArrayList<EMoveInfosExtend> infosRetract = super.listBlockRetract(world, pos, facing, lenghtClose, stickySize, false);
		ArrayList<EMoveInfosExtend> upBlocks     = this.listUpBlocks(infosRetract, world, pos, facing, 0, lenghtClose, false);
		ArrayList<EMoveInfosExtend> nextBlocks   = this.listNextBlocks(infosRetract, world, pos, facing, 0, lenghtClose, false);
		
		for (EMoveInfosExtend infos : infosRetract) {
			if (infos.state != null && infos.position != null) {
				world.setTileEntity(infos.position, null);
				world.setBlockToAir(infos.position);
			}
		}
		
		ArrayList<EMoveInfosExtend> all = new ArrayList<BlockMorePistonsBase.EMoveInfosExtend>();
		
		all.addAll(infosRetract);
		all.addAll(upBlocks);
		all.addAll(nextBlocks);
		
		return all;
		
	}
	
	protected ArrayList<EMoveInfosExtend> listUpBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened, boolean extend) {

		BlockPos posExtension;
		
		int direction = extend ? 1 : -1;
		
		ArrayList<EMoveInfosExtend> blocksTop = new ArrayList<EMoveInfosExtend>();
		ArrayList<EMoveInfosExtend> dropList  = new ArrayList<EMoveInfosExtend>();
		
		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
			BlockPos posBlock;
			
			// Si on est à l'orizontal
			if (blockOrigin.state != null && blockOrigin.position != null && facing != EnumFacing.DOWN && facing != EnumFacing.UP) {

				posBlock = new BlockPos(
					blockOrigin.position.getX(),
					blockOrigin.position.getY() + 1,
					blockOrigin.position.getZ()
				);
				
				this.cleanBlockMoving(world, posBlock);
				IBlockState blockState = world.getBlockState(posBlock);
				TileEntity te = world.getTileEntity(posBlock);
				
				// Déplacement des block standare au dessus
				if (blockState != null && blockState.getBlock() != Blocks.air) {
					
					if (
						!this.isEmptyBlockState(blockState) &&
						!SuperPistonManager.instance.dontMoveIfOnTop(blockState, world, posBlock, extend ? facing : facing.getOpposite())
					) {
						
//						int moveBlock = 0;
//						if (this.isMovableBlockState(blockState, world, posBlock)) {
//							posExtension = new BlockPos(posBlock);
//							
//							for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
//
//								posExtension = posExtension.add(
//									facing.getFrontOffsetX() * direction,
//									facing.getFrontOffsetY() * direction,
//									facing.getFrontOffsetZ() * direction
//								);
//								
//								this.cleanBlockMoving(world, posExtension);
//								IBlockState blockStateNext = world.getBlockState(posExtension);
//								
//								if (!this.isEmptyBlockState(blockStateNext)) {
//									break;
//								}
//								if (blockStateNext != null && blockStateNext.getBlock() != Blocks.air) {
//									// Drop les élements légés (fleurs, leviers, herbes ..)
//									if (blockStateNext.getBlock().getMobilityFlag() == 1) {
//										dropList.add(new EMoveInfosExtend(blockState, te, posExtension, 0, 1));
//									}
//								}
//							}
//						}
//						if (moveBlock > 0) {
//							world.setTileEntity(posBlock, null);
//							world.setBlockToAir(posBlock);
//							
//							posExtension = new BlockPos(posBlock);
//							
//							// Fait sauter les element avec un flag 1 qui non pas bouger 
//							for (int j = 0; j < moveBlock; j++) {
//								
//								posExtension = posExtension.add(
//									facing.getFrontOffsetX() * direction,
//									facing.getFrontOffsetY() * direction,
//									facing.getFrontOffsetZ() * direction
//								);
//								
//								this.cleanBlockMoving(world, posExtension);
//								IBlockState blockNextState = world.getBlockSate(posExtension);
//								
//								if (blockNext != null && block != Blocks.air) {
//									int metadataNext = world.getBlockMetadata(xExtension, yExtension, zExtension);
//									// Drop les élements légés (fleurs, leviers, herbes ..)
//									if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
//										dropList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), 0, 1));
//									}
//								}
//							}
//							
//							blocksTop.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xBlock, yBlock, zBlock), moveBlock, 1));
//						}
					} else if (!SuperPistonManager.instance.isntAttachOnTop (blockState, world, posBlock, extend ? facing : facing.getOpposite())) {
						
//						int moveBlock = 0;
//						
//						posExtension = new BlockPos(posBlock);
//						
//						for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
//							
//							 posExtension = posExtension.add(
//								facing.getFrontOffsetX() * direction,
//								facing.getFrontOffsetY() * direction,
//								facing.getFrontOffsetZ() * direction
//							);
//							
//							this.cleanBlockMoving(world, xExtension, yExtension, zExtension);
//							Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
//							if (blockNext != null && blockNext != Blocks.air) {
//								break;
//							}
//						}
//						
//						// Si le mouvement ne peux etre complet alors on drop l'element
//						if (moveBlock != blockOrigin.move) {
//							// Drop les élements légés (fleurs, leviers, herbes ..)
//							this.dropMobilityFlag1(block, metadata, world, xBlock, yBlock, zBlock);
//						} else {
//							world.setTileEntity(xBlock, yBlock, zBlock, null);
//							world.setBlock (xBlock, yBlock, zBlock, Blocks.air, 0, 0);
//							
//							xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock*direction;
//							yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock*direction;
//							zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock*direction;
//							
//							blocksTop.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xBlock, yBlock, zBlock), moveBlock, 1));
//						}
					}
				}
			}
			
		}
		
		for (EMoveInfosExtend info : dropList) {
			this.dropMobilityFlag1(info.state, world, info.position);
		}
		
		return blocksTop;
	}
	
	protected ArrayList<EMoveInfosExtend> listNextBlocks (ArrayList<EMoveInfosExtend> blocksOrigin, World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened, boolean extend) {
		
		int xExtension;
		int yExtension;
		int zExtension;
		
		int direction = extend ? 1 : -1;
		
		ArrayList<EMoveInfosExtend> blocksList = new ArrayList<EMoveInfosExtend>();
//		ArrayList<EMoveInfosExtend> dropList  = new ArrayList<EMoveInfosExtend>();
//		
//		for (EMoveInfosExtend blockOrigin : blocksOrigin) {
//			
//			if (blockOrigin.block != null && blockOrigin.position != null) {
//				
//				// On aprcour les 4 coins
//				for (int o = 2; o <= 5; o++) {
//					
//					if (o == orientation || o == Facing.oppositeSide[orientation]) {
//						continue;
//					}
//					
//					
//					int xBlock = blockOrigin.position.x + Facing.offsetsXForSide[o]*direction;
//					int yBlock = blockOrigin.position.y;
//					int zBlock = blockOrigin.position.z + Facing.offsetsZForSide[o]*direction;
//					
//					this.cleanBlockMoving(world, xBlock, yBlock, zBlock);
//					Block block   = world.getBlock(xBlock, yBlock, zBlock);
//					int metadata  = world.getBlockMetadata(xBlock, yBlock, zBlock);
//					TileEntity te = world.getTileEntity(xBlock, yBlock, zBlock);
//					
//					// nous avons la un block qui saccroche devrais dropper si c'etait un piston normal
//					if (
//						block != null && 
//						block != Blocks.air &&
//						(
//							this.isEmptyBlock(block) ||
//							SuperPistonManager.instance.isAttachableBlockOnNext (block, metadata, world, xBlock, yBlock, zBlock, extend ? o : Facing.oppositeSide[o])
//						)
//						&& SuperPistonManager.instance.isAttachOnNext (block, metadata, world, xBlock, yBlock, zBlock, extend ? o : Facing.oppositeSide[o])
//					) {
//						
//						int moveBlock = 0;
//						xExtension = xBlock;
//						yExtension = yBlock;
//						zExtension = zBlock;
//						for (moveBlock = 0; moveBlock < blockOrigin.move; moveBlock++) {
//							xExtension += Facing.offsetsXForSide[orientation]*direction;
//							yExtension += Facing.offsetsYForSide[orientation]*direction;
//							zExtension += Facing.offsetsZForSide[orientation]*direction;
//							
//							this.cleanBlockMoving(world, xExtension, yExtension, zExtension);
//							Block blockNext = world.getBlock(xExtension, yExtension, zExtension);
//							
//							if (blockNext != null && blockNext != Blocks.air) {
//								break;
//							}
//						}
//						
//						// Si le mouvement ne peux etre complet alors on drop l'element
//						if (moveBlock != blockOrigin.move) {
//							// Drop les élements légés (fleurs, leviers, herbes ..)
//							if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
//								dropList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), 0, 1));
//							}
//						} else {
//							world.setBlockToAir(xBlock, yBlock, zBlock);
//							
//							xExtension = xBlock + Facing.offsetsXForSide[orientation]*moveBlock;
//							yExtension = yBlock + Facing.offsetsYForSide[orientation]*moveBlock;
//							zExtension = zBlock + Facing.offsetsZForSide[orientation]*moveBlock;
//							
//							blocksList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xBlock, yBlock, zBlock), moveBlock, 1));
//						}
//					}
//				}
//			}
//		}
//		
//		for (EMoveInfosExtend info : dropList) {
//			this.dropMobilityFlag1(info.block, info.metadata, world, info.position.x, info.position.y, info.position.z);
//		}
		
		return blocksList;
	}
	
}
