package mods.morepistons.client.render;

import org.lwjgl.opengl.GL11;

import mods.morepistons.client.ClientProxyMorePistons;
import mods.morepistons.common.block.BlockMorePistonsBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class MorePistonsInventoryRenderer implements ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

		Block p_147800_1_ = block;
		int p_147800_2_ = 1;
		float p_147800_3_ = 0;
		boolean flag = false;
		
		
		Tessellator tessellator = Tessellator.instance;
 
		renderer.setRenderBoundsFromBlock(p_147800_1_);
		int k;
		
        p_147800_1_.setBlockBoundsForItemRender();
//        renderer.setRenderBoundsFromBlock(p_147800_1_);
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,Block block, int modelId, RenderBlocks renderer) {
		return false;
	}
	
	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}
	
	@Override
	public int getRenderId() {
		return ClientProxyMorePistons.idMorePistonsBaseRenderer;
	}
	
}