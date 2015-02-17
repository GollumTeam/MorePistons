package com.gollum.morepistons.client.render;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
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
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;

public class TileEntityMorePistonsRodRenderer extends ATileEntityMorePistonsRenderer {
	
	private void renderRod(TileEntityMorePistonsRod tileEntityRod, double posX, double posY, double posZ, float f) {

		TileEntityMorePistonsMoving tileEntityMoving = tileEntityRod.getTileEntityMoving ();
		TileEntityMorePistonsPiston tileEntityPiston = tileEntityRod.getTileEntityPiston ();
		
		Tessellator tessellator = this.startRender(tileEntityRod, posX, posY, posZ);
		
		if (tileEntityPiston != null) {
			float progress = 0.0F;
			
			if (tileEntityMoving != null) {
				
				progress = Math.abs(
					(tileEntityMoving.getOffsetX(f) - (float)Math.ceil(tileEntityMoving.getOffsetX(f))) +
					(tileEntityMoving.getOffsetY(f) - (float)Math.ceil(tileEntityMoving.getOffsetY(f))) +
					(tileEntityMoving.getOffsetZ(f) - (float)Math.ceil(tileEntityMoving.getOffsetZ(f)))
				);
				
				if (tileEntityRod.isDisplay ()) {
					this.blockRenderer.renderPistonRod(
						ModBlocks.blockPistonRod,
						tileEntityRod.xCoord,
						tileEntityRod.yCoord,
						tileEntityRod.zCoord,
						progress
					);
				}
				
			} else {
				this.blockRenderer.renderPistonRod(
					ModBlocks.blockPistonRod,
					tileEntityRod.xCoord,
					tileEntityRod.yCoord,
					tileEntityRod.zCoord,
					progress
				);
			}
		}
		
		this.endRender(tessellator);
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		if (tileEntity != null) {
			this.renderRod((TileEntityMorePistonsRod) tileEntity, x, y, z, f);
		}
	}
	
}
