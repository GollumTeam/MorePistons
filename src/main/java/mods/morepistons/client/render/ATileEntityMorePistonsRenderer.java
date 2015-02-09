package mods.morepistons.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public abstract class ATileEntityMorePistonsRenderer extends TileEntitySpecialRenderer {
	
	protected RenderBlocksMorePistons blockRenderer;
	
	/**
	 * Called when the ingame world being rendered changes (e.g. on world ->
	 * nether travel) due to using one renderer per tile entity type, rather
	 * than instance
	 */
	@Override
//	public void onWorldChange(World world) {
	public void func_147496_a(World world) {
		this.blockRenderer = new RenderBlocksMorePistons (world);
	}
	
	
	protected Tessellator startRender(TileEntity tileEntity, double posX, double posY, double posZ) {
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
		
		tessellator.startDrawingQuads();
		tessellator.setTranslation(
			posX - (double)tileEntity.xCoord,
			posY - (double)tileEntity.yCoord,
			posZ - (double)tileEntity.zCoord
		);
		tessellator.setColorOpaque(1, 1, 1);
		return tessellator;
	}
	
	protected void endRender(Tessellator tessellator) {
		tessellator.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		RenderHelper.disableStandardItemLighting();
	}
	
}
