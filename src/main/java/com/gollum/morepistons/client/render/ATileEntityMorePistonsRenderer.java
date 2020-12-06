package com.gollum.morepistons.client.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public abstract class ATileEntityMorePistonsRenderer extends TileEntitySpecialRenderer {

	protected final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
	
	private double offsetX = 0;
	private double offsetY = 0;
	private double offsetZ = 0;
	
	protected Tessellator startRender(TileEntity tileEntity, double posX, double posY, double posZ) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		GL11.glPushMatrix();
		this.bindTexture(TextureMap.locationBlocksTexture);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();

		if (Minecraft.isAmbientOcclusionEnabled()) {
			GlStateManager.shadeModel(7425);
		} else {
			GlStateManager.shadeModel(7424);
		}
		
		worldrenderer.func_181668_a(7, DefaultVertexFormats.BLOCK);
		this.setTranslation(
			posX - (double)tileEntity.getPos().getX(),
			posY - (double)tileEntity.getPos().getY(),
			posZ - (double)tileEntity.getPos().getZ()
		);
		return tessellator;
	}
	
	protected void renderModel(IBlockState state, BlockPos pos) {
		Tessellator   tessellator   = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		World         world         = this.getWorld();
		this.blockRenderer.getBlockModelRenderer().renderModel(world, this.blockRenderer.getModelFromBlockState(state, world, pos), state, pos, worldrenderer, true);
	}
	
	protected void endRender() {
		Tessellator tessellator = Tessellator.getInstance();
		
		tessellator.draw();
		RenderHelper.enableStandardItemLighting();
		this.setTranslation(0.0D, 0.0D, 0.0D);
		GL11.glPopMatrix();
		
	}
	
	protected void setTranslation(double offsetX, double offsetY, double offsetZ) {
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		
		worldrenderer.setTranslation(
			this.offsetX,
			this.offsetY,
			this.offsetZ
		);
	}
	
	protected void addTranslation(double offsetX, double offsetY, double offsetZ) {
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		
		this.offsetX += offsetX;
		this.offsetY += offsetY;
		this.offsetZ += offsetZ;
		
		worldrenderer.setTranslation(
			this.offsetX,
			this.offsetY,
			this.offsetZ
		);
	}
	
}
