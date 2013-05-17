package mods.morePistons.common;

import net.minecraft.block.Block; // amq;
import net.minecraft.tileentity.TileEntity; // any;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.block.BlockPistonBase; // aoa;
import net.minecraft.block.BlockPistonExtension; // aob;
import net.minecraft.client.renderer.RenderHelper; // arw;
import net.minecraft.client.renderer.Tessellator; // baz;
import net.minecraft.client.renderer.RenderBlocks; // bbb;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer; // bdx
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.world.World; // yc;

public class TileEntityMorePistonsRenderer extends TileEntitySpecialRenderer {
	
	/** instance of RenderBlocks used to draw the piston base and extension. */
	private RenderBlocks blockRenderer;
	
	public void renderPiston(TileEntityPiston tileEntityPiston, double par2, double par4, double par6, float par8) {
		
		TileEntityMorePistons par1TileEntityPiston = (TileEntityMorePistons)tileEntityPiston;
		
		Block block = Block.blocksList[par1TileEntityPiston.getStoredBlockID()];
		
		if (block != null && par1TileEntityPiston.getProgress(par8) < 1.0F) {
			Tessellator tessellator = Tessellator.instance;
			this.bindTextureByName("/terrain.png");
			RenderHelper.disableStandardItemLighting();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			
			if (Minecraft.isAmbientOcclusionEnabled()) {
				GL11.glShadeModel(GL11.GL_SMOOTH);
			} else {
				GL11.glShadeModel(GL11.GL_FLAT);
			}
			
			// Dans cette zone on va placer les piston ROD
			if (par1TileEntityPiston.getStoredBlockID ()  == MorePistons.pistonExtension.blockID && par1TileEntityPiston.isExtending ()) {
				float distance =  par1TileEntityPiston.getOffsetX(par8) +  par1TileEntityPiston.getOffsetY(par8) + par1TileEntityPiston.getOffsetZ(par8);
				par1TileEntityPiston.displayPistonRod(par1TileEntityPiston.distance - MathHelper.ceiling_float_int(MathHelper.abs(distance)));
			}
			
			if (par1TileEntityPiston.isPistonRoot && !par1TileEntityPiston.isExtending ()) {
				float distance =  par1TileEntityPiston.getOffsetX(par8) +  par1TileEntityPiston.getOffsetY(par8) + par1TileEntityPiston.getOffsetZ(par8);
				par1TileEntityPiston.removePistonRod(par1TileEntityPiston.distance - MathHelper.ceiling_float_int(MathHelper.abs(distance)));
			}
			
			tessellator.startDrawingQuads();
			tessellator.setTranslation(
				(double) ((float) par2 - (float) par1TileEntityPiston.xCoord + par1TileEntityPiston.getOffsetX(par8)),
				(double) ((float) par4 - (float) par1TileEntityPiston.yCoord + par1TileEntityPiston.getOffsetY(par8)),
				(double) ((float) par6 - (float) par1TileEntityPiston.zCoord + par1TileEntityPiston.getOffsetZ(par8))
			);
			tessellator.setColorOpaque(1, 1, 1);
			
			if (block == Block.pistonExtension && par1TileEntityPiston.getProgress(par8) < 0.5F) {
				this.blockRenderer.renderPistonExtensionAllFaces(
					block,
					par1TileEntityPiston.xCoord,
					par1TileEntityPiston.yCoord,
					par1TileEntityPiston.zCoord,
					false
				);
			} else if (par1TileEntityPiston.shouldRenderHead() && !par1TileEntityPiston.isExtending()) {
				
				Block.pistonExtension.setHeadTexture(((BlockPistonBase) block).getPistonExtensionTexture());
				
				this.blockRenderer.renderPistonExtensionAllFaces(
						Block.pistonExtension, par1TileEntityPiston.xCoord,
						par1TileEntityPiston.yCoord,
						par1TileEntityPiston.zCoord,
						par1TileEntityPiston.getProgress(par8) < 0.5F
				);
				
				Block.pistonExtension.clearHeadTexture();
				
				tessellator.setTranslation(
					(double) ((float) par2 - (float) par1TileEntityPiston.xCoord),
					(double) ((float) par4 - (float) par1TileEntityPiston.yCoord),
					(double) ((float) par6 - (float) par1TileEntityPiston.zCoord)
				);
				this.blockRenderer.renderPistonBaseAllFaces(
					block,
					par1TileEntityPiston.xCoord,
					par1TileEntityPiston.yCoord,
					par1TileEntityPiston.zCoord
				);
			} else {
				this.blockRenderer.renderBlockAllFaces(
					block,
					par1TileEntityPiston.xCoord,
					par1TileEntityPiston.yCoord,
					par1TileEntityPiston.zCoord
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
	public void onWorldChange(World par1World) {
		this.blockRenderer = new RenderBlocks(par1World);
	}

	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8) {
		this.renderPiston((TileEntityPiston) par1TileEntity, par2, par4, par6, par8);
	}
	
}
