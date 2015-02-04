package mods.morepistons.client.render;

import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//
import mods.morepistons.common.block.BlockMorePistonsExtension;
//import mods.morepistons.common.tileentities.TileEntityMorePistons;
import mods.morepistons.inits.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
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
//
public class TileEntityMorePistonsMovingRenderer extends TileEntitySpecialRenderer {
//	
	/** 
	 * instance of RenderBlocks used to draw the piston base and extension.
	 */
	private RenderBlocks blockRenderer;
	
	
	public void renderPiston(TileEntityMorePistonsMoving tileEntityPiston, double x, double y, double z, float par8) {
		
//		Block block = tileEntityPiston.storedBlock;
//		BlockPistonBase blockPison = tileEntityPiston.getBlockPiston();
//		
//		if (blockPison != null && block != null && tileEntityPiston.getProgress(par8) < 1.0F) {
//			
			Tessellator tessellator = Tessellator.instance;
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			RenderHelper.disableStandardItemLighting();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			
			if (Minecraft.isAmbientOcclusionEnabled()) {
				GL11.glShadeModel(GL11.GL_SMOOTH);
			} else {
				GL11.glShadeModel(GL11.GL_FLAT);
			}
//			
//			// Dans cette zone on va placer les pistons rods
//			if (tileEntityPiston.storedBlock instanceof BlockMorePistonsExtension && tileEntityPiston.isExtending ()) {
//				float distance = tileEntityPiston.getOffsetX(par8) + tileEntityPiston.getOffsetY(par8) + tileEntityPiston.getOffsetZ(par8);
//				tileEntityPiston.displayPistonRod(tileEntityPiston.distance - MathHelper.ceiling_float_int(MathHelper.abs(distance)), tileEntityPiston.distance);
//			}
//			
//			// On enleve au fur et a mesure les pistons rods
//			if (tileEntityPiston.isBlockPiston && !tileEntityPiston.isExtending ()) {
//				float distance = tileEntityPiston.getOffsetX(par8) + tileEntityPiston.getOffsetY(par8) + tileEntityPiston.getOffsetZ(par8);
//				tileEntityPiston.removePistonRod(tileEntityPiston.distance - MathHelper.ceiling_float_int(MathHelper.abs(distance)), tileEntityPiston.distance);
//			}
//			
//			tessellator.startDrawingQuads();
//			tessellator.setTranslation(
//				x - (double) tileEntityPiston.xCoord + tileEntityPiston.getOffsetX(par8),
//				y - (double) tileEntityPiston.yCoord + tileEntityPiston.getOffsetY(par8),
//				z - (double) tileEntityPiston.zCoord + tileEntityPiston.getOffsetZ(par8)
//			);
//			tessellator.setColorOpaque(1, 1, 1);
//			
//			float distance = MathHelper.abs(tileEntityPiston.getOffsetX(par8) + tileEntityPiston.getOffsetY(par8) + tileEntityPiston.getOffsetZ(par8));
//			float reste = tileEntityPiston.distance - distance;
//			
//			if (block instanceof BlockMorePistonsExtension) {
//					
//				Blocks.piston_head.func_150086_a(blockPison.getPistonExtensionTexture());
//				
//				this.blockRenderer.renderPistonExtensionAllFaces(
//					block,
//					tileEntityPiston.xCoord,
//					tileEntityPiston.yCoord,
//					tileEntityPiston.zCoord,
//					reste > 0.5f
//				);
//				
//				((BlockPistonExtension)ModBlocks.blockPistonExtension).func_150087_e();
//				
//			} else if (tileEntityPiston.shouldRenderHead() && !tileEntityPiston.isExtending() && (block instanceof BlockPistonBase) && tileEntityPiston.isBlockPiston) {
//				
//				Blocks.piston_head.func_150086_a(blockPison.getPistonExtensionTexture());
//				
//				this.blockRenderer.renderPistonExtensionAllFaces(
//					Blocks.piston_head,
//					tileEntityPiston.xCoord,
//					tileEntityPiston.yCoord,
//					tileEntityPiston.zCoord,
//					distance > 0.5f
//				);
//				
//				((BlockPistonExtension)ModBlocks.blockPistonExtension).func_150087_e();
//				
//				tessellator.setTranslation(
//					(double) ((float) x - (float) tileEntityPiston.xCoord),
//					(double) ((float) y - (float) tileEntityPiston.yCoord),
//					(double) ((float) z - (float) tileEntityPiston.zCoord)
//				);
//				
//				this.blockRenderer.renderPistonBaseAllFaces(
//					block,
//					tileEntityPiston.xCoord,
//					tileEntityPiston.yCoord,
//					tileEntityPiston.zCoord
//				);
//				
//				if (distance > 1.0f) {
//					
//					tessellator.setTranslation(
//						(double) ((float) x - (float) tileEntityPiston.xCoord) + Facing.offsetsXForSide[tileEntityPiston.storedOrientation],
//						(double) ((float) y - (float) tileEntityPiston.yCoord) + Facing.offsetsYForSide[tileEntityPiston.storedOrientation],
//						(double) ((float) z - (float) tileEntityPiston.zCoord) + Facing.offsetsZForSide[tileEntityPiston.storedOrientation]
//					);
//					this.blockRenderer.renderBlockAllFaces(
//						ModBlocks.blockPistonRod,
//						tileEntityPiston.xCoord,
//						tileEntityPiston.yCoord,
//						tileEntityPiston.zCoord
//					);
//				}
//				
//			} else {
//				this.blockRenderer.renderBlockAllFaces(
//					block,
//					tileEntityPiston.xCoord,
//					tileEntityPiston.yCoord,
//					tileEntityPiston.zCoord
//				);
//			}
//			
//			tessellator.setTranslation(0.0D, 0.0D, 0.0D);
//			tessellator.draw();
//			RenderHelper.enableStandardItemLighting();
//		}
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
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		this.renderPiston((TileEntityMorePistonsMoving) tileentity, x, y, z, f);
	}

}
