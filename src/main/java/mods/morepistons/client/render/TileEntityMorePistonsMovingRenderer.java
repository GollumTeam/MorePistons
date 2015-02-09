package mods.morepistons.client.render;

import mods.morepistons.ModMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import mods.morepistons.common.block.BlockMorePistonsBase;
//
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsRod;
//import mods.morepistons.common.tileentities.TileEntityMorePistons;
import mods.morepistons.inits.ModBlocks;
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

public class TileEntityMorePistonsMovingRenderer extends ATileEntityMorePistonsRenderer {
	
	public void renderMoving(TileEntityMorePistonsMoving tileEntityMoving, double x, double y, double z, float f) {
		
		Block                block       = tileEntityMoving.storedBlock;
		BlockMorePistonsBase blockPiston = tileEntityMoving.pistonOrigin();

		Tessellator tessellator = this.startRender(tileEntityMoving, x, y, z);
		
		tessellator.addTranslation(
			tileEntityMoving.getOffsetX(f),
			tileEntityMoving.getOffsetY(f),
			tileEntityMoving.getOffsetZ(f)
		);
		
		float distance = MathHelper.abs(tileEntityMoving.getOffsetX(f) + tileEntityMoving.getOffsetY(f) + tileEntityMoving.getOffsetZ(f));
		float reste = tileEntityMoving.distance - distance;
		
		if (blockPiston != null && block != null) {
			
			if (block instanceof BlockMorePistonsExtension) {
				
				Blocks.piston_head.func_150086_a(blockPiston.getPistonExtensionTexture());
				this.blockRenderer.renderPistonExtensionAllFaces(
					block,
					tileEntityMoving.xCoord,
					tileEntityMoving.yCoord,
					tileEntityMoving.zCoord,
					reste > 0.5f
				);
				((BlockPistonExtension)ModBlocks.blockPistonExtention).func_150087_e();
				
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
