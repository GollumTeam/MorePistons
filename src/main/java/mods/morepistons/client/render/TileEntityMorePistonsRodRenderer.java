package mods.morepistons.client.render;

import mods.morepistons.ModMorePistons;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import mods.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import mods.morepistons.common.tileentities.TileEntityMorePistonsRod;
import mods.morepistons.inits.ModBlocks;
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

public class TileEntityMorePistonsRodRenderer extends ATileEntityMorePistonsRenderer {
	
	private void renderRod(TileEntityMorePistonsRod tileEntityRod, double posX, double posY, double posZ, float f) {
		
		TileEntityMorePistonsMoving tileEntityMoving = tileEntityRod.getTileEntityMoving ();
		
		Tessellator tessellator = this.startRender(tileEntityRod, posX, posY, posZ);
		
		float progress = 0.0F;
		
		if (tileEntityMoving != null) {
			
			progress = Math.abs(
				(tileEntityMoving.getOffsetX(f) - (float)Math.ceil(tileEntityMoving.getOffsetX(f))) +
				(tileEntityMoving.getOffsetY(f) - (float)Math.ceil(tileEntityMoving.getOffsetY(f))) +
				(tileEntityMoving.getOffsetZ(f) - (float)Math.ceil(tileEntityMoving.getOffsetZ(f)))
			);
			
		}
		
		this.blockRenderer.renderPistonRod(
			ModBlocks.blockPistonRod,
			tileEntityRod.xCoord,
			tileEntityRod.yCoord,
			tileEntityRod.zCoord,
			progress
		);
		
		
		this.endRender(tessellator);
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		if (tileEntity != null) {
			this.renderRod((TileEntityMorePistonsRod) tileEntity, x, y, z, f);
		}
	}
	
}
