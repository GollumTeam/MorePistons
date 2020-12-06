package com.gollum.morepistons.client.render;

import static net.minecraft.block.BlockPistonExtension.FACING;
import static com.gollum.morepistons.common.block.BlockMorePistonsRod.SHORT;

import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
//
public class TileEntityMorePistonsRodRenderer extends ATileEntityMorePistonsRenderer {
	
	private void renderRod(TileEntityMorePistonsRod tileEntityRod, double x, double y, double z, float partialTicks) {
		
		TileEntityMorePistonsMoving tileEntityMoving = tileEntityRod.getTileEntityMoving ();
		
		EnumFacing  facing     = tileEntityRod.getFacing();
		BlockPos    pos        = tileEntityRod.getPos();
		int         distanceTo = tileEntityRod.getDistanceToPiston();
		IBlockState state  = ModBlocks.blockPistonRod.getDefaultState()
			.withProperty(FACING, facing)
			.withProperty(SHORT, tileEntityRod.isShort())
		;
		
		this.startRender(tileEntityRod, x, y, z);

		double offsetX = 0;
		double offsetY = 0;
		double offsetZ = 0;
		
		if (tileEntityMoving != null) {
			
			double distance = Math.abs(tileEntityMoving.getOffsetX(partialTicks) + tileEntityMoving.getOffsetY(partialTicks) + tileEntityMoving.getOffsetZ(partialTicks));
			double reste = tileEntityMoving.distance - distance;
			
			if (tileEntityMoving.extending) {
				
				offsetX -= (distance * facing.getFrontOffsetX()) % 1;
				offsetY -= (distance * facing.getFrontOffsetY()) % 1;
				offsetZ -= (distance * facing.getFrontOffsetZ()) % 1;

			} else {
				
				offsetX += distance * facing.getFrontOffsetX() % 1 - facing.getFrontOffsetX();
				offsetY += distance * facing.getFrontOffsetY() % 1 - facing.getFrontOffsetY();
				offsetZ += distance * facing.getFrontOffsetZ() % 1 - facing.getFrontOffsetZ();

			}
		}
		
		
		this.addTranslation(
			offsetX,
			offsetY,
			offsetZ
		);
		
		if (tileEntityRod.isDisplay ()) {
			this.renderModel(state, pos);
		}

		this.endRender();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks, int destroy) {
		if (tileEntity != null) {
			this.renderRod((TileEntityMorePistonsRod) tileEntity, x, y, z, partialTicks);
		}
	}
	
}
