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
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class TileEntityMorePistonsPistonRenderer extends ATileEntityMorePistonsRenderer {
	
	private void renderPiston(TileEntityMorePistonsPiston tileEntity, double posX, double posY, double posZ, boolean allFaces) {
		
		BlockMorePistonsBase block       = tileEntity.getBlockPiston();
		int                  x           = tileEntity.xCoord;
		int                  y           = tileEntity.yCoord;
		int                  z           = tileEntity.zCoord;
		
		Tessellator tessellator = this.startRender(tileEntity, posX, posY, posZ);
		
		this.blockRenderer.renderPistonBase(block, x, y, z, false);
		this.endRender(tessellator);
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		if (tileEntity != null) {
			this.renderPiston((TileEntityMorePistonsPiston) tileEntity, x, y, z, false);
		}
	}
	
}
