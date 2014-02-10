package morePistons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockQuadPistonBase extends BlockPistonBase
{
    private boolean isSticky;
    private static boolean ignoreUpdates;

    public BlockQuadPistonBase(int var1, int var2, boolean var3)
    {
        super(var1, var2, var3);
        this.isSticky = var3;
        this.setRequiresSelfNotify();
    }

    public String getTextureFile()
    {
        return "/morePistons/blocks/block_textures.png";
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return the either 106 or 107 as the texture index depending on the isSticky flag. This will actually never get
     * called by TileEntityRendererPiston.renderPiston() because TileEntityPiston.shouldRenderHead() will always return
     * false.
     */
    public int getPistonExtensionTexture()
    {
        return !this.isSticky ? 1 : 0;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        int var3 = getOrientation(var2);
        return var3 > 5 ? this.blockIndexInTexture : (var1 == var3 ? (!isExtended(var2) && this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D ? (!this.isSticky ? 1 : 0) : 23) : (var1 != Facing.faceToSide[var3] ? 24 : 22));
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 16;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5)
    {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5)
    {
        int var6 = determineOrientation(var1, var2, var3, var4, (EntityPlayer)var5);
        var1.setBlockMetadataWithNotify(var2, var3, var4, var6);

        if (!ignoreUpdates)
        {
            this.updatePistonState(var1, var2, var3, var4);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        if (!ignoreUpdates)
        {
            this.updatePistonState(var1, var2, var3, var4);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        if (var1.getBlockTileEntity(var2, var3, var4) == null && !ignoreUpdates)
        {
            this.updatePistonState(var1, var2, var3, var4);
        }
    }

    private void updatePistonState(World var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);
        int var6 = getOrientation(var5);
        boolean var7 = this.isIndirectlyPowered(var1, var2, var3, var4, var6);

        if (var5 != 7)
        {
            if (var7 && !isExtended(var5))
            {
                if (canExtend(var1, var2, var3, var4, var6))
                {
                    var1.setBlockMetadata(var2, var3, var4, var6 | 8);
                    var1.addBlockEvent(var2, var3, var4, this.blockID, 0, var6);
                }
            }
            else if (!var7 && isExtended(var5))
            {
                var1.setBlockMetadata(var2, var3, var4, var6);
                var1.addBlockEvent(var2, var3, var4, this.blockID, 1, var6);
            }
        }
    }

    private boolean isIndirectlyPowered(World var1, int var2, int var3, int var4, int var5)
    {
        if (var5 != 0 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3 - 1, var4, 0))
        {
            return true;
        }
        else if (var5 != 1 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 1, var4, 1))
        {
            return true;
        }
        else if (var5 != 2 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 - 1, 2))
        {
            return true;
        }
        else if (var5 != 3 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 + 1, 3))
        {
            return true;
        }
        else if (var5 != 5 && var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3, var4, 5))
        {
            return true;
        }
        else if (var5 != 4 && var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3, var4, 4))
        {
            return true;
        }
        else if (var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4, 0))
        {
            return true;
        }
        else if (var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 2, var4, 1))
        {
            return true;
        }
        else if (var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 1, var4 - 1, 2))
        {
            return true;
        }
        else if (var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 1, var4 + 1, 3))
        {
            return true;
        }
        else if (var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3 + 1, var4, 4))
        {
            return true;
        }
        else
        {
            boolean var6 = var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3 + 1, var4, 5);
            return var6;
        }
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public void onBlockEventReceived(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ignoreUpdates = true;
        int var7 = var6;

        if (var5 == 0)
        {
            if (this.tryExtend(var1, var2, var3, var4, var6))
            {
                var1.setBlockMetadataWithNotify(var2, var3, var4, var6 | 8);
                var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "tile.piston.out", 0.5F, var1.rand.nextFloat() * 0.25F + 0.6F);
            }
            else
            {
                var1.setBlockMetadata(var2, var3, var4, var6);
            }
        }
        else if (var5 == 1)
        {
            int var8 = Monty_Util.extraDisp3 + 1;
            int var9 = var2;
            int var10 = var3;
            int var11 = var4;

            for (int var12 = 0; var12 < var8; ++var12)
            {
                var9 += Facing.offsetsXForSide[var7];
                var10 += Facing.offsetsYForSide[var7];
                var11 += Facing.offsetsZForSide[var7];
                TileEntity var13 = var1.getBlockTileEntity(var9, var10, var11);

                if (var13 != null && var13 instanceof TileEntityMorePistons)
                {
                    ((TileEntityMorePistons)var13).clearPistonTileEntity();
                }
            }

            var1.setBlockAndMetadata(var2, var3, var4, Block.pistonMoving.blockID, var7);
            TileEntity var25 = mod_MorePistons.getTileEntity(this.blockID, var7, var7, false, true);
            var1.setBlockTileEntity(var2, var3, var4, var25);
            int var14;
            int var15;
            int var16;
            int var26;

            if (this.isSticky)
            {
                var26 = var2;
                var14 = var3;
                var15 = var4;

                for (var16 = 0; var16 < var8 + 1; ++var16)
                {
                    var26 += Facing.offsetsXForSide[var7];
                    var14 += Facing.offsetsYForSide[var7];
                    var15 += Facing.offsetsZForSide[var7];
                }

                int var19 = var1.getBlockId(var26, var14, var15);
                int var20 = var1.getBlockMetadata(var26, var14, var15);
                boolean var21 = false;

                if (var19 == Block.pistonMoving.blockID)
                {
                    TileEntity var22 = var1.getBlockTileEntity(var26, var14, var15);

                    if (var22 != null && var22 instanceof TileEntityMorePistons)
                    {
                        TileEntityMorePistons var23 = (TileEntityMorePistons)var22;

                        if (var23.getPistonOrientation() == var7 && var23.isExtending())
                        {
                            var23.clearPistonTileEntity();
                            var19 = var23.getStoredBlockID();
                            var20 = var23.getBlockMetadata();
                            var21 = true;
                        }
                    }
                }

                if (var19 > 0)
                {
                    ;
                }

                boolean var27 = false;
                int var28;

                for (var28 = 0; var28 < MorePistons.pistonList.length; ++var28)
                {
                    if (var19 == MorePistons.pistonList[var28])
                    {
                        var27 = true;
                        break;
                    }

                    if (var28 == MorePistons.pistonList.length - 1 && !var27)
                    {
                        var27 = false;
                    }
                }

                if (!var21 && var19 > 0 && canPushBlock(var19, var1, var26, var14, var15, false) && (Block.blocksList[var19].getMobilityFlag() == 0 || var27))
                {
                    for (var28 = 0; var28 < 1; ++var28)
                    {
                        var2 += Facing.offsetsXForSide[var7];
                        var3 += Facing.offsetsYForSide[var7];
                        var4 += Facing.offsetsZForSide[var7];
                    }

                    var1.setBlockAndMetadata(var2, var3, var4, Block.pistonMoving.blockID, var20);
                    TileEntity var29 = mod_MorePistons.getTileEntity(var19, var20, var7, false, false);
                    var1.setBlockTileEntity(var2, var3, var4, var29);
                    ignoreUpdates = false;
                    var1.setBlockWithNotify(var26, var14, var15, 0);
                    var26 = var2;
                    var14 = var3;
                    var15 = var4;

                    for (int var24 = 0; var24 < var8 - 1; ++var24)
                    {
                        var26 += Facing.offsetsXForSide[var7];
                        var14 += Facing.offsetsYForSide[var7];
                        var15 += Facing.offsetsZForSide[var7];
                        var1.setBlockWithNotify(var26, var14, var15, 0);
                    }

                    ignoreUpdates = true;
                }
                else if (!var21)
                {
                    ignoreUpdates = false;

                    for (var28 = 0; var28 < var8; ++var28)
                    {
                        var2 += Facing.offsetsXForSide[var7];
                        var3 += Facing.offsetsYForSide[var7];
                        var4 += Facing.offsetsZForSide[var7];
                    }

                    var1.setBlockWithNotify(var2, var3, var4, 0);
                    ignoreUpdates = true;
                }
            }
            else
            {
                ignoreUpdates = false;
                var26 = var2;
                var14 = var3;
                var15 = var4;

                for (var16 = 0; var16 < var8; ++var16)
                {
                    var26 += Facing.offsetsXForSide[var7];
                    var14 += Facing.offsetsYForSide[var7];
                    var15 += Facing.offsetsZForSide[var7];
                    int var17 = var1.getBlockId(var26, var14, var15);

                    if (var17 == MorePistons.pistonRod.blockID || var17 == MorePistons.pistonExtension.blockID)
                    {
                        var1.setBlockWithNotify(var26, var14, var15, 0);
                    }
                }

                ignoreUpdates = true;
                BlockPistonRod.pistonMoving2 = false;
            }

            var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "tile.piston.in", 0.5F, var1.rand.nextFloat() * 0.15F + 0.6F);
        }

        ignoreUpdates = false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);

        if (isExtended(var5))
        {
            int var6 = getOrientation(var5);

            switch (getOrientation(var5))
            {
                case 0:
                    this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 1:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
                    break;

                case 2:
                    this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
                    break;

                case 3:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
                    break;

                case 4:
                    this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case 5:
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            }
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4)
    {
        this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
        return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public static int getOrientation(int var0)
    {
        return var0 & 7;
    }

    public static boolean isExtended(int var0)
    {
        return (var0 & 8) != 0;
    }

    public static int determineOrientation(World var0, int var1, int var2, int var3, EntityPlayer var4)
    {
        if (MathHelper.abs((float)var4.posX - (float)var1) < 2.0F && MathHelper.abs((float)var4.posZ - (float)var3) < 2.0F)
        {
            double var5 = var4.posY + 1.82D - (double)var4.yOffset;

            if (var5 - (double)var2 > 2.0D)
            {
                return 1;
            }

            if ((double)var2 - var5 > 0.0D)
            {
                return 0;
            }
        }

        int var7 = MathHelper.floor_double((double)(var4.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return var7 == 0 ? 2 : (var7 == 1 ? 5 : (var7 == 2 ? 3 : (var7 != 3 ? 0 : 4)));
    }

    private static boolean canPushBlock(int var0, World var1, int var2, int var3, int var4, boolean var5)
    {
        boolean var6 = false;

        if (var0 == Block.obsidian.blockID)
        {
            return false;
        }
        else if (var0 != MorePistons.pistonRod.blockID && var0 != MorePistons.pistonExtension.blockID)
        {
            for (int var7 = 0; var7 < MorePistons.pistonList.length; ++var7)
            {
                if (var0 == MorePistons.pistonList[var7])
                {
                    var6 = true;
                    break;
                }

                if (var6)
                {
                    if (isExtended(var1.getBlockMetadata(var2, var3, var4)))
                    {
                        return false;
                    }
                }
                else if (var7 == MorePistons.pistonList.length - 1)
                {
                    var6 = false;
                }
            }

            if (!var6)
            {
                if (Block.blocksList[var0].getBlockHardness(var1, var2, var3, var4) == -1.0F)
                {
                    return false;
                }
                else if (Block.blocksList[var0].getMobilityFlag() == 2)
                {
                    return false;
                }
                else if (!var5 && Block.blocksList[var0].getMobilityFlag() == 1)
                {
                    return false;
                }
                else
                {
                    boolean var8 = !(Block.blocksList[var0] instanceof BlockContainer);
                    return var8;
                }
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    private static boolean canExtend(World var0, int var1, int var2, int var3, int var4)
    {
        int var5 = var1 + Facing.offsetsXForSide[var4];
        int var6 = var2 + Facing.offsetsYForSide[var4];
        int var7 = var3 + Facing.offsetsZForSide[var4];
        int var8 = 0;

        while (true)
        {
            if (var8 < 13)
            {
                if (var6 <= 0 || var6 >= 255)
                {
                    return false;
                }

                int var9 = var0.getBlockId(var5, var6, var7);

                if (var9 != 0)
                {
                    if (!canPushBlock(var9, var0, var5, var6, var7, true))
                    {
                        return false;
                    }

                    if (Block.blocksList[var9].getMobilityFlag() != 1)
                    {
                        if (var8 == 12)
                        {
                            return false;
                        }

                        var5 += Facing.offsetsXForSide[var4];
                        var6 += Facing.offsetsYForSide[var4];
                        var7 += Facing.offsetsZForSide[var4];
                        ++var8;
                        continue;
                    }
                }
            }

            return true;
        }
    }

    private boolean tryExtend(World var1, int var2, int var3, int var4, int var5)
    {
        ArrayList var6 = new ArrayList();
        int var7 = Monty_Util.extraDisp3;
        int var8 = 1 + var7;
        int var9 = 0;
        int var10 = var2;
        int var11 = var3;
        int var12 = var4;
        int var13;

        for (var13 = 0; var13 < var8; ++var13)
        {
            var10 += Facing.offsetsXForSide[var5];
            var11 += Facing.offsetsYForSide[var5];
            var12 += Facing.offsetsZForSide[var5];
            Monty_Util var14 = new Monty_Util(0, 0, 0, var10, var11, var12);
            var14.blockId2 = MorePistons.pistonRod.blockID;

            if (var13 == var8 - 1)
            {
                var14.blockId2 = MorePistons.pistonExtension.blockID;
            }

            var6.add(var14);
        }

        var13 = var2 + Facing.offsetsXForSide[var5];
        int var24 = var3 + Facing.offsetsYForSide[var5];
        int var15 = var4 + Facing.offsetsZForSide[var5];
        int var16 = 0;

        while (true)
        {
            int var18;
            int var20;

            if (var16 < 13)
            {
                label102:
                {
                    if (var24 <= 0 || var24 >= 255)
                    {
                        return false;
                    }

                    int var17 = var1.getBlockId(var13, var24, var15);
                    var18 = var1.getBlockMetadata(var13, var24, var15);

                    if (var17 == 0)
                    {
                        ++var9;
                    }
                    else
                    {
                        if (!canPushBlock(var17, var1, var13, var24, var15, true))
                        {
                            return false;
                        }

                        if (Block.blocksList[var17].getMobilityFlag() == 1)
                        {
                            Block.blocksList[var17].dropBlockAsItem(var1, var13, var24, var15, var1.getBlockMetadata(var13, var24, var15), 0);
                            var1.setBlockWithNotify(var13, var24, var15, 0);
                            break label102;
                        }
                    }

                    if (var16 == 12)
                    {
                        return false;
                    }

                    if (var17 != 0)
                    {
                        int var19 = var8 - var9;
                        var20 = var13;
                        int var21 = var24;
                        int var22 = var15;

                        for (int var23 = 0; var23 < var19; ++var23)
                        {
                            var20 += Facing.offsetsXForSide[var5];
                            var21 += Facing.offsetsYForSide[var5];
                            var22 += Facing.offsetsZForSide[var5];
                        }

                        Monty_Util var29 = new Monty_Util(var13, var24, var15, var20, var21, var22);
                        var29.blockId2 = var17;
                        var29.metadata2 = var18;
                        var6.add(var29);
                    }

                    if (var9 != var8)
                    {
                        var13 += Facing.offsetsXForSide[var5];
                        var24 += Facing.offsetsYForSide[var5];
                        var15 += Facing.offsetsZForSide[var5];
                        ++var16;
                        continue;
                    }
                }
            }

            Object var25 = null;

            for (var18 = 0; var18 < var6.size(); ++var18)
            {
                Monty_Util var26 = (Monty_Util)var6.get(var18);
                TileEntity var28;

                if (var26.blockId2 == MorePistons.pistonRod.blockID)
                {
                    var20 = var5 | (this.isSticky ? 8 : 0);
                    var1.setBlockAndMetadata(var26.x2, var26.y2, var26.z2, Block.pistonMoving.blockID, var20);
                    var28 = mod_MorePistons.getTileEntity(MorePistons.pistonRod.blockID, var20, var5, true, false);
                    var1.setBlockTileEntity(var26.x2, var26.y2, var26.z2, var28);
                }
                else if (var26.blockId2 == MorePistons.pistonExtension.blockID)
                {
                    var20 = var5 | (this.isSticky ? 8 : 0);
                    var1.setBlockAndMetadata(var26.x2, var26.y2, var26.z2, Block.pistonMoving.blockID, var20);
                    var28 = mod_MorePistons.getTileEntity(MorePistons.pistonExtension.blockID, var20, var5, true, false);
                    var1.setBlockTileEntity(var26.x2, var26.y2, var26.z2, var28);
                }
                else
                {
                    var1.setBlockAndMetadata(var26.x2, var26.y2, var26.z2, Block.pistonMoving.blockID, var26.metadata2);
                    TileEntity var27 = mod_MorePistons.getTileEntity(var26.blockId2, var26.metadata2, var5, true, false);
                    var1.setBlockTileEntity(var26.x2, var26.y2, var26.z2, var27);
                }
            }

            return true;
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        super.breakBlock(var1, var2, var3, var4, var5, var6);
        int var7 = Monty_Util.extraDisp3;
        int var8 = 1 + var7;
        var1.getBlockId(var2, var3, var4);
        int var10 = var1.getBlockMetadata(var2, var3, var4);
        int var11 = getDirectionMeta(var10);

        if (BlockTriplePistonBase.isExtended(var10))
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
            int var12 = var2;
            int var13 = var3;
            int var14 = var4;

            for (int var15 = 0; var15 < var8; ++var15)
            {
                var12 += Facing.offsetsXForSide[var11];
                var13 += Facing.offsetsYForSide[var11];
                var14 += Facing.offsetsZForSide[var11];
                int var16 = var1.getBlockId(var12, var13, var14);

                if (var16 == MorePistons.pistonRod.blockID || var16 == MorePistons.pistonExtension.blockID)
                {
                    var1.setBlockWithNotify(var12, var13, var14, 0);
                }
            }
        }
    }

    public static int getDirectionMeta(int var0)
    {
        return var0 & 7;
    }
}
