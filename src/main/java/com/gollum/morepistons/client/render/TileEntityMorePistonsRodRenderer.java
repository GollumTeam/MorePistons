package com.gollum.morepistons.client.render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

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
