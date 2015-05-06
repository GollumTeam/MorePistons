package com.gollum.morepistons.client.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.world.World;
//
//import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsExtension;
import com.gollum.morepistons.common.block.BlockMorePistonsRod;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.inits.ModBlocks;

public class TileEntityMorePistonsMovingRenderer extends ATileEntityMorePistonsRenderer {
	
	public void renderMoving(TileEntityMorePistonsMoving tileEntityMoving, double x, double y, double z, float f) {
		
		Block                block       = tileEntityMoving.storedBlock;
		BlockMorePistonsBase blockPiston = tileEntityMoving.pistonOrigin();
		
		Tessellator tessellator = this.startRender(tileEntityMoving, x, y, z);
		
		float distance = MathHelper.abs(tileEntityMoving.getOffsetX(f) + tileEntityMoving.getOffsetY(f) + tileEntityMoving.getOffsetZ(f));
		float reste = tileEntityMoving.distance - distance;
		
		if (blockPiston != null && block != null) {
			
			if (tileEntityMoving.root && block instanceof BlockMorePistonsBase) {
				this.blockRenderer.renderPistonBase(
					block,
					tileEntityMoving.xCoord,
					tileEntityMoving.yCoord,
					tileEntityMoving.zCoord,
					true
				);
			}
			
			if (tileEntityMoving.root && block instanceof BlockMorePistonsRod) {
				float progress = Math.abs(
					(tileEntityMoving.getOffsetX(0) - (float)Math.ceil(tileEntityMoving.getOffsetX(0))) +
					(tileEntityMoving.getOffsetY(0) - (float)Math.ceil(tileEntityMoving.getOffsetY(0))) +
					(tileEntityMoving.getOffsetZ(0) - (float)Math.ceil(tileEntityMoving.getOffsetZ(0)))
				);
					
				this.blockRenderer.renderPistonRod(
					ModBlocks.blockPistonRod,
					tileEntityMoving.xCoord,
					tileEntityMoving.yCoord,
					tileEntityMoving.zCoord,
					tileEntityMoving.storedOrientation,
					progress
				);
			}
			
			tessellator.addTranslation(
				tileEntityMoving.getOffsetX(f),
				tileEntityMoving.getOffsetY(f),
				tileEntityMoving.getOffsetZ(f)
			);
			
			if (tileEntityMoving.root) {
				
				blockPiston.getBlockExtention().func_150086_a(blockPiston.getPistonExtensionTexture());
				this.blockRenderer.renderPistonExtensionAllFaces(
					blockPiston.getBlockExtention(),
					tileEntityMoving.xCoord,
					tileEntityMoving.yCoord,
					tileEntityMoving.zCoord,
					distance > 0.5f
				);
				blockPiston.getBlockExtention().func_150087_e();
				
			} else if (block instanceof BlockMorePistonsExtension) {
				
				blockPiston.getBlockExtention().func_150086_a(blockPiston.getPistonExtensionTexture());
				this.blockRenderer.renderPistonExtensionAllFaces(
					block,
					tileEntityMoving.xCoord,
					tileEntityMoving.yCoord,
					tileEntityMoving.zCoord,
					reste > 0.5f
				);
				blockPiston.getBlockExtention().func_150087_e();
				
			} else  {
				if (block instanceof BlockMorePistonsBase) {
					this.blockRenderer.renderPistonBase (
						block,
						tileEntityMoving.xCoord,
						tileEntityMoving.yCoord,
						tileEntityMoving.zCoord,
						false
					);
					
				} else {
					this.blockRenderer.renderBlockAllFaces(
						block,
						tileEntityMoving.xCoord,
						tileEntityMoving.yCoord,
						tileEntityMoving.zCoord
					);
				}
				
				boolean isRod = false;

				TileEntityMorePistonsPiston tePiston = tileEntityMoving.getPistonOriginTE();
				
				if (!tileEntityMoving.extending && tePiston != null) {
					for (int i = 1; i < Math.ceil(reste) + 1 && i < blockPiston.getMaxBlockMove(); i++) {
						if (
							tileEntityMoving.xCoord == tePiston.xCoord + Facing.offsetsXForSide[tileEntityMoving.storedOrientation] * i &&
							tileEntityMoving.yCoord == tePiston.yCoord + Facing.offsetsYForSide[tileEntityMoving.storedOrientation] * i&&
							tileEntityMoving.zCoord == tePiston.zCoord + Facing.offsetsZForSide[tileEntityMoving.storedOrientation] * i
						) {
							isRod = true;
						}
					}
					if (isRod) {
						
						tessellator.addTranslation(
							-tileEntityMoving.getOffsetX(f),
							-tileEntityMoving.getOffsetY(f),
							-tileEntityMoving.getOffsetZ(f)
						);
						
						float progress = Math.abs(
							(tileEntityMoving.getOffsetX(0) - (float)Math.ceil(tileEntityMoving.getOffsetX(0))) +
							(tileEntityMoving.getOffsetY(0) - (float)Math.ceil(tileEntityMoving.getOffsetY(0))) +
							(tileEntityMoving.getOffsetZ(0) - (float)Math.ceil(tileEntityMoving.getOffsetZ(0)))
						);
							
						this.blockRenderer.renderPistonRod(
							ModBlocks.blockPistonRod,
							tileEntityMoving.xCoord,
							tileEntityMoving.yCoord,
							tileEntityMoving.zCoord,
							tileEntityMoving.storedOrientation,
							progress
						);
					}
				}
			}
		}
		
		this.endRender(tessellator);
	}
	
	/**
	 * Called when the ingame world being rendered changes (e.g. on world ->
	 * nether travel) due to using one renderer per tile entity type, rather
	 * than instance
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		this.renderMoving((TileEntityMorePistonsMoving) tileentity, x, y, z, f);
	}

}
