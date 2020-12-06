package com.gollum.morepistons.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.inits.ModBlocks;

public class TileEntityMorePistonsMovingRenderer extends ATileEntityMorePistonsRenderer {
	
	public void renderMoving(TileEntityMorePistonsMoving tileEntityMoving, double x, double y, double z, float partialTicks) {
		
		IBlockState          state         = tileEntityMoving.storedState;
		BlockMorePistonsBase blockPiston   = tileEntityMoving.pistonOrigin();
		Tessellator          tessellator   = Tessellator.getInstance();
		WorldRenderer        worldrenderer = tessellator.getWorldRenderer();
		
		this.startRender(tileEntityMoving, x, y, z);
		
		double distance = Math.abs(tileEntityMoving.getOffsetX(partialTicks) + tileEntityMoving.getOffsetY(partialTicks) + tileEntityMoving.getOffsetZ(partialTicks));
		double reste = tileEntityMoving.distance - distance;
		
		if (blockPiston != null && state != null && state.getBlock() != null && state.getBlock().getMaterial() != Material.air) {
			
			Block block = state.getBlock();
			
			if (tileEntityMoving.root && block instanceof BlockMorePistonsBase) {
				this.renderBlockAllFaces(
					state
						.withProperty(BlockPistonBase.FACING, tileEntityMoving.getFacing())
						.withProperty(BlockPistonBase.EXTENDED, true)
					,
					tileEntityMoving.getPos()
				);
				
				double progress = Math.abs(
					(tileEntityMoving.getOffsetX(0) - Math.ceil(tileEntityMoving.getOffsetX(0))) +
					(tileEntityMoving.getOffsetY(0) - Math.ceil(tileEntityMoving.getOffsetY(0))) +
					(tileEntityMoving.getOffsetZ(0) - Math.ceil(tileEntityMoving.getOffsetZ(0)))
				);
//				
//				this.renderBlockAllFaces(
//					ModBlocks.blockPistonRod.getDefaultState()
//						.withProperty(BlockPistonExtension.FACING, tileEntityMoving.getFacing())
//					,
//					tileEntityMoving.getPos()
//				);
			}
//			
			this.addTranslation(
				tileEntityMoving.getOffsetX(partialTicks),
				tileEntityMoving.getOffsetY(partialTicks),
				tileEntityMoving.getOffsetZ(partialTicks)
			);
			
			if (tileEntityMoving.root) {
				
				this.renderBlockAllFaces(
					blockPiston.getBlockExtention().getDefaultState()
						.withProperty(BlockPistonExtension.TYPE, blockPiston.getEnumPistonType())
						.withProperty(BlockPistonExtension.FACING, tileEntityMoving.getFacing())
						.withProperty(BlockPistonExtension.SHORT, distance <= 0.5f)
					,
					tileEntityMoving.getPos()
					
				);
				
			} else if (block instanceof BlockMorePistonsExtension) {
				
				this.renderBlockAllFaces(
					state
						.withProperty(BlockPistonExtension.SHORT, reste <= 0.5f)
					,
					tileEntityMoving.getPos()
				);
				
			} else  {
				if (block instanceof BlockMorePistonsBase) {
					this.renderBlockAllFaces(
						state,
						tileEntityMoving.getPos()
					);
					
				} else {
					this.renderBlockAllFaces(
						state,
						tileEntityMoving.getPos()
					);
				}
				

				TileEntityMorePistonsPiston tePiston = tileEntityMoving.getPistonOriginTE();
				
				if (!tileEntityMoving.extending && tePiston != null) {
					if (distance > 1) {
						
						this.addTranslation(
							-tileEntityMoving.getOffsetX(partialTicks) - tileEntityMoving.getFacing().getFrontOffsetX() + (tileEntityMoving.getOffsetX(partialTicks) % 1D),
							-tileEntityMoving.getOffsetY(partialTicks) - tileEntityMoving.getFacing().getFrontOffsetY() + (tileEntityMoving.getOffsetY(partialTicks) % 1D),
							-tileEntityMoving.getOffsetZ(partialTicks) - tileEntityMoving.getFacing().getFrontOffsetZ() + (tileEntityMoving.getOffsetZ(partialTicks) % 1D)
						);
						
						this.renderBlockAllFaces(
							ModBlocks.blockPistonRod.getDefaultState()
								.withProperty(BlockPistonExtension.FACING, tileEntityMoving.getFacing())
								.withProperty(BlockPistonExtension.SHORT, (distance % 1D) <= 0.5f)
							,
							tileEntityMoving.getPos()
						);
					}
				}
			}
			
		}
		
		this.endRender();
		
	}
	
	private void renderBlockAllFaces(IBlockState state, BlockPos pos) {
		Tessellator   tessellator   = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		World         world         = this.getWorld();
		
		this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(state, world, pos), state, pos, worldrenderer, false);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks, int destroy) {
		this.renderMoving((TileEntityMorePistonsMoving) tileentity, x, y, z, partialTicks);
	}

}
