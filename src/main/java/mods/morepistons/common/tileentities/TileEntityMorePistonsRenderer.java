package mods.morepistons.common.tileentities;

import org.lwjgl.opengl.GL11;

import mods.morepistons.ModMorePistons;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityMorePistonsRenderer extends TileEntitySpecialRenderer {
	
	/** instance of RenderBlocks used to draw the piston base and extension. */
	private RenderBlocks blockRenderer;
	
	public void renderPiston(TileEntityMorePistons tileEntityPiston, double x, double y, double z, float par8) {
		
		Block block = tileEntityPiston.storedBlock;
		
		if (block != null && tileEntityPiston.getProgress(par8) < 1.0F) {
			
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
			
			if (tileEntityPiston.distance >= 0) {
				// Dans cette zone on va placer les pistons rods
				if (tileEntityPiston.storedBlock instanceof BlockMorePistonsExtension && tileEntityPiston.isExtending ()) {
					float distance = tileEntityPiston.getOffsetX(par8) + tileEntityPiston.getOffsetY(par8) + tileEntityPiston.getOffsetZ(par8);
					tileEntityPiston.displayPistonRod(tileEntityPiston.distance - MathHelper.ceiling_float_int(MathHelper.abs(distance)), tileEntityPiston.distance);
				}
				
				// On enleve au fur et a mesure les pistons rods
				if (tileEntityPiston.isBlockPiston && !tileEntityPiston.isExtending ()) {
					float distance = tileEntityPiston.getOffsetX(par8) + tileEntityPiston.getOffsetY(par8) + tileEntityPiston.getOffsetZ(par8);
					tileEntityPiston.removePistonRod(tileEntityPiston.distance - MathHelper.ceiling_float_int(MathHelper.abs(distance)), tileEntityPiston.distance);
				}
			}
			
			tessellator.startDrawingQuads();
			tessellator.setTranslation(
				(double) ((float) x - (float) tileEntityPiston.xCoord + tileEntityPiston.getOffsetX(par8)),
				(double) ((float) y - (float) tileEntityPiston.yCoord + tileEntityPiston.getOffsetY(par8)),
				(double) ((float) z - (float) tileEntityPiston.zCoord + tileEntityPiston.getOffsetZ(par8))
			);
			tessellator.setColorOpaque(1, 1, 1);
			
			float distance = MathHelper.abs(tileEntityPiston.getOffsetX(par8) + tileEntityPiston.getOffsetY(par8) + tileEntityPiston.getOffsetZ(par8));
			float reste = tileEntityPiston.distance - distance;
			
			// Le block extention mais en négatif
			if (tileEntityPiston.distance < 0) {
				
				this.blockRenderer.renderPistonExtensionAllFaces(
					Blocks.piston_head,
					tileEntityPiston.xCoord,
					tileEntityPiston.yCoord,
					tileEntityPiston.zCoord,
					distance > 0.5f
				);
				
				((BlockPistonExtension)ModMorePistons.blockPistonExtension).func_150087_e();
				
				tessellator.setTranslation(
					(double) ((float) x - (float) tileEntityPiston.xCoord),
					(double) ((float) y - (float) tileEntityPiston.yCoord),
					(double) ((float) z - (float) tileEntityPiston.zCoord)
				);
				
				this.blockRenderer.renderBlockAllFaces(
					ModMorePistons.blockPistonRod,
					tileEntityPiston.xCoord,
					tileEntityPiston.yCoord,
					tileEntityPiston.zCoord
				);
			
			// Le block extention
			} else if (block instanceof BlockMorePistonsExtension) {
				
				this.blockRenderer.renderPistonExtensionAllFaces(
					block,
					tileEntityPiston.xCoord,
					tileEntityPiston.yCoord,
					tileEntityPiston.zCoord,
					reste > 0.5f
				);
				
			} else if (tileEntityPiston.shouldRenderHead() && !tileEntityPiston.isExtending() && (block instanceof BlockPistonBase)) {
				
				Blocks.piston_head.func_150086_a(((BlockPistonBase) block).getPistonExtensionTexture());
				
				this.blockRenderer.renderPistonExtensionAllFaces(
					Blocks.piston_head,
					tileEntityPiston.xCoord,
					tileEntityPiston.yCoord,
					tileEntityPiston.zCoord,
					distance > 0.5f
				);
				
				((BlockPistonExtension)ModMorePistons.blockPistonExtension).func_150087_e();
				
				tessellator.setTranslation(
					(double) ((float) x - (float) tileEntityPiston.xCoord),
					(double) ((float) y - (float) tileEntityPiston.yCoord),
					(double) ((float) z - (float) tileEntityPiston.zCoord)
				);
				
				this.blockRenderer.renderPistonBaseAllFaces(
					block,
					tileEntityPiston.xCoord,
					tileEntityPiston.yCoord,
					tileEntityPiston.zCoord
				);
				
				if (distance > 1.0f) {
					
					tessellator.setTranslation(
						(double) ((float) x - (float) tileEntityPiston.xCoord) + Facing.offsetsXForSide[tileEntityPiston.storedOrientation],
						(double) ((float) y - (float) tileEntityPiston.yCoord) + Facing.offsetsYForSide[tileEntityPiston.storedOrientation],
						(double) ((float) z - (float) tileEntityPiston.zCoord) + Facing.offsetsZForSide[tileEntityPiston.storedOrientation]
					);
					this.blockRenderer.renderBlockAllFaces(
						ModMorePistons.blockPistonRod,
						tileEntityPiston.xCoord,
						tileEntityPiston.yCoord,
						tileEntityPiston.zCoord
					);
				}
				
			} else {
				this.blockRenderer.renderBlockAllFaces(
					block,
					tileEntityPiston.xCoord,
					tileEntityPiston.yCoord,
					tileEntityPiston.zCoord
				);
			}
			
			tessellator.setTranslation(0.0D, 0.0D, 0.0D);
			tessellator.draw();
			RenderHelper.enableStandardItemLighting();
		}
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
		this.renderPiston((TileEntityMorePistons) tileentity, x, y, z, f);
	}

}
