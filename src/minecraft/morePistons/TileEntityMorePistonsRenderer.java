package morePistons;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class TileEntityMorePistonsRenderer extends TileEntitySpecialRenderer
{
    private RenderBlocks blockRenderer;

    public void renderPiston(TileEntityMorePistons var1, double var2, double var4, double var6, float var8)
    {
        Block var9 = Block.blocksList[var1.getStoredBlockID()];

        if (var9 != null && var1.getProgress(var8) < 1.0F)
        {
            Tessellator var10 = Tessellator.instance;
            this.bindTextureByName("/morePistons/blocks/block_textures.png");
            RenderHelper.disableStandardItemLighting();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);

            if (Minecraft.isAmbientOcclusionEnabled())
            {
                GL11.glShadeModel(GL11.GL_SMOOTH);
            }
            else
            {
                GL11.glShadeModel(GL11.GL_FLAT);
            }

            var10.startDrawingQuads();
            var10.setTranslation((double)((float)var2 - (float)var1.xCoord + var1.getOffsetX(var8)), (double)((float)var4 - (float)var1.yCoord + var1.getOffsetY(var8)), (double)((float)var6 - (float)var1.zCoord + var1.getOffsetZ(var8)));
            var10.setColorOpaque(1, 1, 1);

            if (var9 == MorePistons.pistonExtension && var1.getProgress(var8) < 0.5F)
            {
                int var11 = var1.xCoord;
                int var12 = var1.yCoord;
                int var13 = var1.zCoord;
                int var14 = var1.getPistonOrientation();
                var11 -= Facing.offsetsXForSide[var14];
                var12 -= Facing.offsetsYForSide[var14];
                var13 -= Facing.offsetsZForSide[var14];
                int var15 = var1.worldObj.getBlockId(var11, var12, var13);

                if (var15 != MorePistons.gravitationalPistonBase.blockID && var15 != MorePistons.superPistonBase.blockID && var15 != MorePistons.superStickyPistonBase.blockID)
                {
                    this.blockRenderer.renderPistonExtensionAllFaces(var9, var1.xCoord, var1.yCoord, var1.zCoord, true);
                }
                else
                {
                    this.blockRenderer.renderPistonExtensionAllFaces(var9, var1.xCoord, var1.yCoord, var1.zCoord, false);
                }
            }
            else if (var1.shouldRenderHead() && !var1.isExtending())
            {
                Block.pistonExtension.setHeadTexture(((BlockPistonBase)var9).getPistonExtensionTexture());
                this.blockRenderer.renderPistonExtensionAllFaces(Block.pistonExtension, var1.xCoord, var1.yCoord, var1.zCoord, var1.getProgress(var8) < 0.5F);
                Block.pistonExtension.clearHeadTexture();
                var10.setTranslation((double)((float)var2 - (float)var1.xCoord), (double)((float)var4 - (float)var1.yCoord), (double)((float)var6 - (float)var1.zCoord));
                this.blockRenderer.renderPistonBaseAllFaces(var9, var1.xCoord, var1.yCoord, var1.zCoord);
            }
            else
            {
                this.blockRenderer.renderBlockAllFaces(var9, var1.xCoord, var1.yCoord, var1.zCoord);
            }

            var10.setTranslation(0.0D, 0.0D, 0.0D);
            var10.draw();
            RenderHelper.enableStandardItemLighting();
        }
    }

    /**
     * Called when the ingame world being rendered changes (e.g. on world -> nether travel) due to using one renderer
     * per tile entity type, rather than instance
     */
    public void onWorldChange(World var1)
    {
        this.blockRenderer = new RenderBlocks(var1);
    }

    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8)
    {
        this.renderPiston((TileEntityMorePistons)var1, var2, var4, var6, var8);
    }
}
