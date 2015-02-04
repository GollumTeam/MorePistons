package mods.morepistons.client.render;

import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

//
//import mods.morepistons.common.block.BlockMorePistonsExtension;
//import mods.morepistons.common.tileentities.TileEntityMorePistons;
//import mods.morepistons.inits.ModBlocks;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockPistonBase;
//import net.minecraft.block.BlockPistonExtension;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.texture.TextureMap;
//import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
//import net.minecraft.init.Blocks;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.Facing;
//import net.minecraft.util.MathHelper;
//import net.minecraft.world.World;
//
//import org.lwjgl.opengl.GL11;
//
public class TileEntityMorePistonsPistonRenderer extends TileEntitySpecialRenderer {
	
	private RenderBlocks blockRenderer;
	
	private void renderPiston(TileEntityMorePistonsPiston tileEntity, double posX, double posY, double posZ, boolean allFaces) {
		
		World                world       = tileEntity.getWorldObj();
		BlockMorePistonsBase block       = tileEntity.getBlockPiston();
		int                  metadata    = tileEntity.getBlockMetadata();
		boolean              isExtend    = allFaces || (metadata & 8) != 0;
		int                  orientation = BlockPistonBase.getPistonOrientation(metadata);
		float                f           = 0.25F;
		int                  x           = tileEntity.xCoord;
		int                  y           = tileEntity.yCoord;
		int                  z           = tileEntity.zCoord;
		
		Tessellator tessellator = Tessellator.instance;

		RenderHelper.disableStandardItemLighting();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		if (Minecraft.isAmbientOcclusionEnabled()) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
		} else {
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		tessellator.startDrawingQuads();
		tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.setTranslation(
			posX - (double)tileEntity.xCoord,
			posY - (double)tileEntity.yCoord,
			posZ - (double)tileEntity.zCoord
		);
		tessellator.setColorOpaque(1, 1, 1);
		
		block.setBlockBoundsBasedOnState(world, x, y, z);
		
		
		if (isExtend) {
			switch (orientation) {
				case 0:
					this.blockRenderer.uvRotateEast = 3;
					this.blockRenderer.uvRotateWest = 3;
					this.blockRenderer.uvRotateSouth = 3;
					this.blockRenderer.uvRotateNorth = 3;
					this.blockRenderer.setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
					break;
				case 1:
					this.blockRenderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
					break;
				case 2:
					this.blockRenderer.uvRotateSouth = 1;
					this.blockRenderer.uvRotateNorth = 2;
					this.blockRenderer.setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
					break;
				case 3:
					this.blockRenderer.uvRotateSouth = 2;
					this.blockRenderer.uvRotateNorth = 1;
					this.blockRenderer.uvRotateTop = 3;
					this.blockRenderer.uvRotateBottom = 3;
					this.blockRenderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
					break;
				case 4:
					this.blockRenderer.uvRotateEast = 1;
					this.blockRenderer.uvRotateWest = 2;
					this.blockRenderer.uvRotateTop = 2;
					this.blockRenderer.uvRotateBottom = 1;
					this.blockRenderer.setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
					break;
				case 5:
					this.blockRenderer.uvRotateEast = 2;
					this.blockRenderer.uvRotateWest = 1;
					this.blockRenderer.uvRotateTop = 1;
					this.blockRenderer.uvRotateBottom = 2;
					this.blockRenderer.setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
			}
			
			block.setBlockBounds((float)this.blockRenderer.renderMinX, (float)this.blockRenderer.renderMinY, (float)this.blockRenderer.renderMinZ, (float)this.blockRenderer.renderMaxX, (float)this.blockRenderer.renderMaxY, (float)this.blockRenderer.renderMaxZ);
			this.blockRenderer.renderStandardBlock(block, x, y, z);
			this.blockRenderer.uvRotateEast = 0;
			this.blockRenderer.uvRotateWest = 0;
			this.blockRenderer.uvRotateSouth = 0;
			this.blockRenderer.uvRotateNorth = 0;
			this.blockRenderer.uvRotateTop = 0;
			this.blockRenderer.uvRotateBottom = 0;
			this.blockRenderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			block.setBlockBounds((float)this.blockRenderer.renderMinX, (float)this.blockRenderer.renderMinY, (float)this.blockRenderer.renderMinZ, (float)this.blockRenderer.renderMaxX, (float)this.blockRenderer.renderMaxY, (float)this.blockRenderer.renderMaxZ);
			
		} else {
			switch (orientation) {
				case 0:
					this.blockRenderer.uvRotateEast = 3;
					this.blockRenderer.uvRotateWest = 3;
					this.blockRenderer.uvRotateSouth = 3;
					this.blockRenderer.uvRotateNorth = 3;
				case 1:
				default:
					break;
				case 2:
					this.blockRenderer.uvRotateSouth = 1;
					this.blockRenderer.uvRotateNorth = 2;
					break;
				case 3:
					this.blockRenderer.uvRotateSouth = 2;
					this.blockRenderer.uvRotateNorth = 1;
					this.blockRenderer.uvRotateTop = 3;
					this.blockRenderer.uvRotateBottom = 3;
					break;
				case 4:
					this.blockRenderer.uvRotateEast = 1;
					this.blockRenderer.uvRotateWest = 2;
					this.blockRenderer.uvRotateTop = 2;
					this.blockRenderer.uvRotateBottom = 1;
					break;
				case 5:
					this.blockRenderer.uvRotateEast = 2;
					this.blockRenderer.uvRotateWest = 1;
					this.blockRenderer.uvRotateTop = 1;
					this.blockRenderer.uvRotateBottom = 2;
			}
			
			this.blockRenderer.renderStandardBlock(block, x, y, z);
			this.blockRenderer.uvRotateEast = 0;
			this.blockRenderer.uvRotateWest = 0;
			this.blockRenderer.uvRotateSouth = 0;
			this.blockRenderer.uvRotateNorth = 0;
			this.blockRenderer.uvRotateTop = 0;
			this.blockRenderer.uvRotateBottom = 0;
		}
		
		tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		RenderHelper.disableStandardItemLighting();
		
	}
	
	/**
	 * Called when the ingame world being rendered changes (e.g. on world ->
	 * nether travel) due to using one renderer per tile entity type, rather
	 * than instance
	 */
//	public void onWorldChange(World world) {
	public void func_147496_a(World world) {
		this.blockRenderer = new RenderBlocks(world);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		if (tileEntity != null) {
			this.renderPiston((TileEntityMorePistonsPiston) tileEntity, x, y, z, false);
		}
	}
	
}
