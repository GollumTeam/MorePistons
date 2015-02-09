package mods.morepistons.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlocksMorePistons extends RenderBlocks {

	public RenderBlocksMorePistons(IBlockAccess world) {
		super(world);
	}
	
	@Override
	public boolean renderPistonBase(Block block, int x, int y, int z, boolean forceExtend) {
		
		block.setBlockBoundsBasedOnState(this.blockAccess, x, y, z);
		
		int     metadata    = this.blockAccess.getBlockMetadata(x, y, z);
		boolean extend      = forceExtend || (metadata & 8) != 0;
		int     orientation = BlockPistonBase.getPistonOrientation(metadata);
		
		if (extend) {
			switch (orientation) {
				case 0:
					this.uvRotateEast = 3;
					this.uvRotateWest = 3;
					this.uvRotateSouth = 3;
					this.uvRotateNorth = 3;
					this.setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
					break;
				case 1:
					this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
					break;
				case 2:
					this.uvRotateSouth = 1;
					this.uvRotateNorth = 2;
					this.setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
					break;
				case 3:
					this.uvRotateSouth = 2;
					this.uvRotateNorth = 1;
					this.uvRotateTop = 3;
					this.uvRotateBottom = 3;
					this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
					break;
				case 4:
					this.uvRotateEast = 1;
					this.uvRotateWest = 2;
					this.uvRotateTop = 2;
					this.uvRotateBottom = 1;
					this.setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
					break;
				case 5:
					this.uvRotateEast = 2;
					this.uvRotateWest = 1;
					this.uvRotateTop = 1;
					this.uvRotateBottom = 2;
					this.setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
			}
			
			block.setBlockBounds((float) this.renderMinX, (float) this.renderMinY, (float) this.renderMinZ, (float) this.renderMaxX, (float) this.renderMaxY, (float) this.renderMaxZ);
			this.renderStandardBlock(block, x, y, z);
			this.uvRotateEast = 0;
			this.uvRotateWest = 0;
			this.uvRotateSouth = 0;
			this.uvRotateNorth = 0;
			this.uvRotateTop = 0;
			this.uvRotateBottom = 0;
			this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			block.setBlockBounds( (float) this.renderMinX, (float) this.renderMinY, (float) this.renderMinZ, (float) this.renderMaxX, (float) this.renderMaxY, (float) this.renderMaxZ);
			
		} else {
			
			switch (orientation) {
				case 0:
					this.uvRotateEast = 3;
					this.uvRotateWest = 3;
					this.uvRotateSouth = 3;
					this.uvRotateNorth = 3;
				case 1:
				default:
					break;
				case 2:
					this.uvRotateSouth = 1;
					this.uvRotateNorth = 2;
					break;
				case 3:
					this.uvRotateSouth = 2;
					this.uvRotateNorth = 1;
					this.uvRotateTop = 3;
					this.uvRotateBottom = 3;
					break;
				case 4:
					this.uvRotateEast = 1;
					this.uvRotateWest = 2;
					this.uvRotateTop = 2;
					this.uvRotateBottom = 1;
					break;
				case 5:
					this.uvRotateEast = 2;
					this.uvRotateWest = 1;
					this.uvRotateTop = 1;
					this.uvRotateBottom = 2;
			}
			
			this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			this.renderStandardBlock(block, x, y, z);
			this.uvRotateEast = 0;
			this.uvRotateWest = 0;
			this.uvRotateSouth = 0;
			this.uvRotateNorth = 0;
			this.uvRotateTop = 0;
			this.uvRotateBottom = 0;
		}
		
		return true;
	}
	
	public void renderPistonRod(Block block, int x, int y, int z, float progress) {
		
		block.setBlockBoundsBasedOnState(this.blockAccess, x, y, z);
		this.setRenderBoundsFromBlock(block);
		
		
		int metadata = this.blockAccess.getBlockMetadata(x, y, z);
		int direction = BlockPistonExtension.getDirectionMeta(metadata);
		float f1 = 0.375F;
		float f2 = 0.625F;
		float f3 = 1.0F;
		double d0 = 16.0D;
		
		switch (direction) {
			
			case 0:
				this.uvRotateEast = 3;
				this.uvRotateWest = 3;
				this.uvRotateSouth = 3;
				this.uvRotateNorth = 3;
				this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
				this.renderPistonRodUD((double)((float)x + 0.375F), (double)((float)x + 0.625F), (double)((float)y + 0.25F), (double)((float)y + 0.25F + f3), (double)((float)z + 0.625F), (double)((float)z + 0.625F), 0.8F, d0);
				this.renderPistonRodUD((double)((float)x + 0.625F), (double)((float)x + 0.375F), (double)((float)y + 0.25F), (double)((float)y + 0.25F + f3), (double)((float)z + 0.375F), (double)((float)z + 0.375F), 0.8F, d0);
				this.renderPistonRodUD((double)((float)x + 0.375F), (double)((float)x + 0.375F), (double)((float)y + 0.25F), (double)((float)y + 0.25F + f3), (double)((float)z + 0.375F), (double)((float)z + 0.625F), 0.6F, d0);
				this.renderPistonRodUD((double)((float)x + 0.625F), (double)((float)x + 0.625F), (double)((float)y + 0.25F), (double)((float)y + 0.25F + f3), (double)((float)z + 0.625F), (double)((float)z + 0.375F), 0.6F, d0);
				break;
			case 1:
				this.setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
				this.renderPistonRodUD((double)((float)x + 0.375F), (double)((float)x + 0.625F), (double)((float)y - 0.25F + 1.0F - f3), (double)((float)y - 0.25F + 1.0F), (double)((float)z + 0.625F), (double)((float)z + 0.625F), 0.8F, d0);
				this.renderPistonRodUD((double)((float)x + 0.625F), (double)((float)x + 0.375F), (double)((float)y - 0.25F + 1.0F - f3), (double)((float)y - 0.25F + 1.0F), (double)((float)z + 0.375F), (double)((float)z + 0.375F), 0.8F, d0);
				this.renderPistonRodUD((double)((float)x + 0.375F), (double)((float)x + 0.375F), (double)((float)y - 0.25F + 1.0F - f3), (double)((float)y - 0.25F + 1.0F), (double)((float)z + 0.375F), (double)((float)z + 0.625F), 0.6F, d0);
				this.renderPistonRodUD((double)((float)x + 0.625F), (double)((float)x + 0.625F), (double)((float)y - 0.25F + 1.0F - f3), (double)((float)y - 0.25F + 1.0F), (double)((float)z + 0.625F), (double)((float)z + 0.375F), 0.6F, d0);
				break;
			case 2:
				this.uvRotateSouth = 1;
				this.uvRotateNorth = 2;
				this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
				this.renderPistonRodSN((double)((float)x + 0.375F), (double)((float)x + 0.375F), (double)((float)y + 0.625F), (double)((float)y + 0.375F), (double)((float)z + 0.25F), (double)((float)z + 0.25F + f3), 0.6F, d0);
				this.renderPistonRodSN((double)((float)x + 0.625F), (double)((float)x + 0.625F), (double)((float)y + 0.375F), (double)((float)y + 0.625F), (double)((float)z + 0.25F), (double)((float)z + 0.25F + f3), 0.6F, d0);
				this.renderPistonRodSN((double)((float)x + 0.375F), (double)((float)x + 0.625F), (double)((float)y + 0.375F), (double)((float)y + 0.375F), (double)((float)z + 0.25F), (double)((float)z + 0.25F + f3), 0.5F, d0);
				this.renderPistonRodSN((double)((float)x + 0.625F), (double)((float)x + 0.375F), (double)((float)y + 0.625F), (double)((float)y + 0.625F), (double)((float)z + 0.25F), (double)((float)z + 0.25F + f3), 1.0F, d0);
				break;
			case 3:
				this.uvRotateSouth = 2;
				this.uvRotateNorth = 1;
				this.uvRotateTop = 3;
				this.uvRotateBottom = 3;
				this.setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
				
				this.renderPistonRodSN((double)((float)x + 0.375F), (double)((float)x + 0.375F), (double)((float)y + 0.625F), (double)((float)y + 0.375F), (double)((float)z - 0.25F + 1.0F - f3           ), (double)((float)z - 0.25F + 1.0F - progress), 0.6F, 16.0F * progress, 16.0F           );
				this.renderPistonRodSN((double)((float)x + 0.375F), (double)((float)x + 0.375F), (double)((float)y + 0.625F), (double)((float)y + 0.375F), (double)((float)z - 0.25F + 1.0F - progress     ), (double)((float)z - 0.25F + 1.0F           ), 0.6F, 0               , 16.0F * progress);
				
				this.renderPistonRodSN((double)((float)x + 0.625F), (double)((float)x + 0.625F), (double)((float)y + 0.375F), (double)((float)y + 0.625F), (double)((float)z - 0.25F + 1.0F - f3           ), (double)((float)z - 0.25F + 1.0F - progress), 0.6F, 16.0F * progress, 16.0F           );
				this.renderPistonRodSN((double)((float)x + 0.625F), (double)((float)x + 0.625F), (double)((float)y + 0.375F), (double)((float)y + 0.625F), (double)((float)z - 0.25F + 1.0F - progress     ), (double)((float)z - 0.25F + 1.0F           ), 0.6F, 0               , 16.0F * progress);
				
				this.renderPistonRodSN((double)((float)x + 0.375F), (double)((float)x + 0.625F), (double)((float)y + 0.375F), (double)((float)y + 0.375F), (double)((float)z - 0.25F + 1.0F - f3           ), (double)((float)z - 0.25F + 1.0F - progress), 0.5F, 16.0F * progress, 16.0F           );
				this.renderPistonRodSN((double)((float)x + 0.375F), (double)((float)x + 0.625F), (double)((float)y + 0.375F), (double)((float)y + 0.375F), (double)((float)z - 0.25F + 1.0F - progress     ), (double)((float)z - 0.25F + 1.0F           ), 0.5F, 0               , 16.0F * progress);
				
				this.renderPistonRodSN((double)((float)x + 0.625F), (double)((float)x + 0.375F), (double)((float)y + 0.625F), (double)((float)y + 0.625F), (double)((float)z - 0.25F + 1.0F - f3           ), (double)((float)z - 0.25F + 1.0F - progress), 1.0F, 16.0F * progress, 16.0F           );
				this.renderPistonRodSN((double)((float)x + 0.625F), (double)((float)x + 0.375F), (double)((float)y + 0.625F), (double)((float)y + 0.625F), (double)((float)z - 0.25F + 1.0F - progress     ), (double)((float)z - 0.25F + 1.0F           ), 1.0F, 0               , 16.0F * progress);
				
				break;
			case 4:
				this.uvRotateEast = 1;
				this.uvRotateWest = 2;
				this.uvRotateTop = 2;
				this.uvRotateBottom = 1;
				this.setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
				
				this.renderPistonRodEW((double)((float)x + 0.25F + f3 - progress), (double)((float)x + 0.25F + f3           ), (double)((float)y + 0.375F), (double)((float)y + 0.375F), (double)((float)z + 0.625F), (double)((float)z + 0.375F), 0.5F, 0.0F            , 16.0F * progress);
				this.renderPistonRodEW((double)((float)x + 0.25F                ), (double)((float)x + 0.25F + f3 - progress), (double)((float)y + 0.375F), (double)((float)y + 0.375F), (double)((float)z + 0.625F), (double)((float)z + 0.375F), 0.5F, 16.0F * progress, 16.0F           );
				
				this.renderPistonRodEW((double)((float)x + 0.25F + f3 - progress), (double)((float)x + 0.25F + f3           ), (double)((float)y + 0.625F), (double)((float)y + 0.625F), (double)((float)z + 0.375F), (double)((float)z + 0.625F), 1.0F, 0.0F            , 16.0F * progress);
				this.renderPistonRodEW((double)((float)x + 0.25F)                , (double)((float)x + 0.25F + f3 - progress), (double)((float)y + 0.625F), (double)((float)y + 0.625F), (double)((float)z + 0.375F), (double)((float)z + 0.625F), 1.0F, 16.0F * progress, 16.0F           );
					
				this.renderPistonRodEW((double)((float)x + 0.25F + f3 - progress), (double)((float)x + 0.25F + f3)           , (double)((float)y + 0.375F), (double)((float)y + 0.625F), (double)((float)z + 0.375F), (double)((float)z + 0.375F), 0.6F, 0.0F            , 16.0F * progress);
				this.renderPistonRodEW((double)((float)x + 0.25F)                , (double)((float)x + 0.25F + f3 - progress), (double)((float)y + 0.375F), (double)((float)y + 0.625F), (double)((float)z + 0.375F), (double)((float)z + 0.375F), 0.6F, 16.0F * progress, 16.0F           );
				
				this.renderPistonRodEW((double)((float)x + 0.25F + f3 - progress), (double)((float)x + 0.25F + f3)           , (double)((float)y + 0.625F), (double)((float)y + 0.375F), (double)((float)z + 0.625F), (double)((float)z + 0.625F), 0.6F, 0.0F            , 16.0F * progress);
				this.renderPistonRodEW((double)((float)x + 0.25F)                , (double)((float)x + 0.25F + f3 - progress), (double)((float)y + 0.625F), (double)((float)y + 0.375F), (double)((float)z + 0.625F), (double)((float)z + 0.625F), 0.6F, 16.0F * progress, 16.0F           );
				
				break;
			case 5:
				this.uvRotateEast = 2;
				this.uvRotateWest = 1;
				this.uvRotateTop = 1;
				this.uvRotateBottom = 2;
				this.setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
				this.renderPistonRodEW((double)((float)x - 0.25F + 1.0F - f3), (double)((float)x - 0.25F + 1.0F), (double)((float)y + 0.375F), (double)((float)y + 0.375F), (double)((float)z + 0.625F), (double)((float)z + 0.375F), 0.5F, d0);
				this.renderPistonRodEW((double)((float)x - 0.25F + 1.0F - f3), (double)((float)x - 0.25F + 1.0F), (double)((float)y + 0.625F), (double)((float)y + 0.625F), (double)((float)z + 0.375F), (double)((float)z + 0.625F), 1.0F, d0);
				this.renderPistonRodEW((double)((float)x - 0.25F + 1.0F - f3), (double)((float)x - 0.25F + 1.0F), (double)((float)y + 0.375F), (double)((float)y + 0.625F), (double)((float)z + 0.375F), (double)((float)z + 0.375F), 0.6F, d0);
				this.renderPistonRodEW((double)((float)x - 0.25F + 1.0F - f3), (double)((float)x - 0.25F + 1.0F), (double)((float)y + 0.625F), (double)((float)y + 0.375F), (double)((float)z + 0.625F), (double)((float)z + 0.625F), 0.6F, d0);
		}
		
		this.uvRotateEast = 0;
		this.uvRotateWest = 0;
		this.uvRotateSouth = 0;
		this.uvRotateNorth = 0;
		this.uvRotateTop = 0;
		this.uvRotateBottom = 0;
		this.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}
	
	public void renderPistonRodUD(double p_147763_1_, double p_147763_3_, double p_147763_5_, double p_147763_7_, double p_147763_9_, double p_147763_11_, float p_147763_13_, double p_147763_14_) {
		IIcon iicon = BlockPistonBase.getPistonBaseIcon("piston_side");
		
		if (this.hasOverrideBlockTexture()) {
			iicon = this.overrideBlockTexture;
		}
		
		Tessellator tessellator = Tessellator.instance;
		double d7 = (double)iicon.getMinU();
		double d8 = (double)iicon.getMinV();
		double d9 = (double)iicon.getInterpolatedU(p_147763_14_);
		double d10 = (double)iicon.getInterpolatedV(4.0D);
		tessellator.setColorOpaque_F(p_147763_13_, p_147763_13_, p_147763_13_);
		tessellator.addVertexWithUV(p_147763_1_, p_147763_7_, p_147763_9_, d9, d8);
		tessellator.addVertexWithUV(p_147763_1_, p_147763_5_, p_147763_9_, d7, d8);
		tessellator.addVertexWithUV(p_147763_3_, p_147763_5_, p_147763_11_, d7, d10);
		tessellator.addVertexWithUV(p_147763_3_, p_147763_7_, p_147763_11_, d9, d10);
	}
	
	public void renderPistonRodSN(double x1, double x2, double y1, double y2, double z1, double z2, float alpha, double start, double end) {
		IIcon iicon = BlockPistonBase.getPistonBaseIcon("piston_side");
		
		if (this.hasOverrideBlockTexture()) {
			iicon = this.overrideBlockTexture;
		}
		
		Tessellator tessellator = Tessellator.instance;
		double d7 = (double)iicon.getInterpolatedU(start);
		double d8 = (double)iicon.getMinV();
		double d9 = (double)iicon.getInterpolatedU(end);
		double d10 = (double)iicon.getInterpolatedV(4.0D);
		tessellator.setColorOpaque_F(alpha, alpha, alpha);
		tessellator.addVertexWithUV(x1, y1, z2, d9, d8);
		tessellator.addVertexWithUV(x1, y1, z1, d7, d8);
		tessellator.addVertexWithUV(x2, y2, z1, d7, d10);
		tessellator.addVertexWithUV(x2, y2, z2, d9, d10);
	}
	
	public void renderPistonRodEW(double x1, double x2, double y1, double y2, double z1, double z2, float alpha, double start, double end) {
		IIcon iicon = BlockPistonBase.getPistonBaseIcon("piston_side");

		if (this.hasOverrideBlockTexture()) {
			iicon = this.overrideBlockTexture;
		}

		Tessellator tessellator = Tessellator.instance;
		double d7 = (double) iicon.getInterpolatedU(start);
		double d8 = (double) iicon.getMinV();
		double d9 = (double) iicon.getInterpolatedU(end);
		double d10 = (double) iicon.getInterpolatedV(4.0D);
		tessellator.setColorOpaque_F(alpha, alpha, alpha);
		tessellator.addVertexWithUV(x2, y1, z1, d9, d8);
		tessellator.addVertexWithUV(x1, y1, z1, d7, d8);
		tessellator.addVertexWithUV(x1, y2, z2, d7, d10);
		tessellator.addVertexWithUV(x2, y2, z2, d9, d10);
	}
	
}
