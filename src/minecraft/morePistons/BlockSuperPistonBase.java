package morePistons;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSuperPistonBase extends BlockPistonBase
{
    private boolean isSticky;
    private static boolean ignoreUpdates;

    public BlockSuperPistonBase(int var1, int var2, boolean var3)
    {
        super(var1, var2, var3);
        this.isSticky = var3;
        this.setStepSound(soundStoneFootstep);
        this.setHardness(0.5F);
        this.setRequiresSelfNotify();
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    public String getTextureFile()
    {
        return "/morePistons/blocks/block_textures.png";
    }

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
        return var3 > 5 ? this.blockIndexInTexture : (var1 == var3 ? (!isExtended(var2) && this.minX <= 0.0D && this.minY <= 0.0D && this.minZ <= 0.0D && this.maxX >= 1.0D && this.maxY >= 1.0D && this.maxZ >= 1.0D ? (!this.isSticky ? 1 : 0) : 29) : (var1 != Facing.faceToSide[var3] ? 30 : 28));
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

        if (!var1.isRemote && !ignoreUpdates)
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
        if (!var1.isRemote && !ignoreUpdates)
        {
            this.updatePistonState(var1, var2, var3, var4);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World var1, int var2, int var3, int var4)
    {
        if (!var1.isRemote && var1.getBlockTileEntity(var2, var3, var4) == null && !ignoreUpdates)
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
        return var5 != 0 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3 - 1, var4, 0) ? true : (var5 != 1 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 1, var4, 1) ? true : (var5 != 2 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 - 1, 2) ? true : (var5 != 3 && var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4 + 1, 3) ? true : (var5 != 5 && var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3, var4, 5) ? true : (var5 != 4 && var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3, var4, 4) ? true : (var1.isBlockIndirectlyProvidingPowerTo(var2, var3, var4, 0) ? true : (var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 2, var4, 1) ? true : (var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 1, var4 - 1, 2) ? true : (var1.isBlockIndirectlyProvidingPowerTo(var2, var3 + 1, var4 + 1, 3) ? true : (var1.isBlockIndirectlyProvidingPowerTo(var2 - 1, var3 + 1, var4, 4) ? true : var1.isBlockIndirectlyProvidingPowerTo(var2 + 1, var3 + 1, var4, 5)))))))))));
    }

    /**
     * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
     * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
     */
    public void onBlockEventReceived(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ignoreUpdates = true;

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
            TileEntity var7 = var1.getBlockTileEntity(var2 + Facing.offsetsXForSide[var6], var3 + Facing.offsetsYForSide[var6], var4 + Facing.offsetsZForSide[var6]);

            if (var7 != null && var7 instanceof TileEntityMorePistons)
            {
                ((TileEntityMorePistons)var7).clearPistonTileEntity();
            }

            var1.setBlockAndMetadata(var2, var3, var4, Block.pistonMoving.blockID, var6);
            var1.setBlockTileEntity(var2, var3, var4, BlockPistonMoving.getTileEntity(this.blockID, var6, var6, false, true));

            if (this.isSticky)
            {
                int var8 = var2 + Facing.offsetsXForSide[var6] * 2;
                int var9 = var3 + Facing.offsetsYForSide[var6] * 2;
                int var10 = var4 + Facing.offsetsZForSide[var6] * 2;
                int var11 = var1.getBlockId(var8, var9, var10);
                int var12 = var1.getBlockMetadata(var8, var9, var10);
                boolean var13 = false;

                if (var11 == Block.pistonMoving.blockID)
                {
                    TileEntity var14 = var1.getBlockTileEntity(var8, var9, var10);

                    if (var14 != null && var14 instanceof TileEntityMorePistons)
                    {
                        TileEntityMorePistons var15 = (TileEntityMorePistons)var14;

                        if (var15.getPistonOrientation() == var6 && var15.isExtending())
                        {
                            var15.clearPistonTileEntity();
                            var11 = var15.getStoredBlockID();
                            var12 = var15.getBlockMetadata();
                            var13 = true;
                        }
                    }
                }

                boolean var17 = false;

                for (int var16 = 0; var16 < MorePistons.pistonList.length; ++var16)
                {
                    if (var11 == MorePistons.pistonList[var16])
                    {
                        var17 = true;
                        break;
                    }

                    if (var16 == MorePistons.pistonList.length - 1 && !var17)
                    {
                        var17 = false;
                    }
                }

                if ((var13 || var11 <= 0 || !canPushBlock(var11, var1, var8, var9, var10, false)) && !var17)
                {
                    if (!var13)
                    {
                        ignoreUpdates = false;
                        var1.setBlockWithNotify(var2 + Facing.offsetsXForSide[var6], var3 + Facing.offsetsYForSide[var6], var4 + Facing.offsetsZForSide[var6], 0);
                        ignoreUpdates = true;
                    }
                }
                else
                {
                    var2 += Facing.offsetsXForSide[var6];
                    var3 += Facing.offsetsYForSide[var6];
                    var4 += Facing.offsetsZForSide[var6];
                    var1.setBlockAndMetadata(var2, var3, var4, Block.pistonMoving.blockID, var12);
                    var1.setBlockTileEntity(var2, var3, var4, BlockPistonMoving.getTileEntity(var11, var12, var6, false, false));
                    this.tryDependableBlocks(var1, var2, var3, var4, Facing.faceToSide[var6]);
                    ignoreUpdates = false;
                    ignoreUpdates = true;
                }
            }
            else
            {
                ignoreUpdates = false;
                var1.setBlockWithNotify(var2 + Facing.offsetsXForSide[var6], var3 + Facing.offsetsYForSide[var6], var4 + Facing.offsetsZForSide[var6], 0);
                ignoreUpdates = true;
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

        if (var0 != MorePistons.pistonRod.blockID && var0 != MorePistons.pistonExtension.blockID)
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
                if (var0 == Block.bedrock.blockID)
                {
                    return false;
                }
                else if (Block.blocksList[var0].blockMaterial != Material.piston && Block.blocksList[var0].blockID != Block.endPortal.blockID)
                {
                    boolean var8 = !(Block.blocksList[var0] instanceof BlockContainer);
                    return var8;
                }
                else
                {
                    return false;
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
            if (var8 < 41)
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

                    if (var8 == 40)
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

            return true;
        }
    }

    private boolean tryExtend(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var2 + Facing.offsetsXForSide[var5];
        int var7 = var3 + Facing.offsetsYForSide[var5];
        int var8 = var4 + Facing.offsetsZForSide[var5];
        int var9 = 0;

        while (true)
        {
            int var10;

            if (var9 < 41)
            {
                if (var7 <= 0 || var7 >= 255)
                {
                    return false;
                }

                var10 = var1.getBlockId(var6, var7, var8);

                if (var10 != 0)
                {
                    if (!canPushBlock(var10, var1, var6, var7, var8, true))
                    {
                        return false;
                    }

                    if (var9 == 40)
                    {
                        return false;
                    }

                    var6 += Facing.offsetsXForSide[var5];
                    var7 += Facing.offsetsYForSide[var5];
                    var8 += Facing.offsetsZForSide[var5];
                    ++var9;
                    continue;
                }
            }

            while (var6 != var2 || var7 != var3 || var8 != var4)
            {
                var9 = var6 - Facing.offsetsXForSide[var5];
                var10 = var7 - Facing.offsetsYForSide[var5];
                int var11 = var8 - Facing.offsetsZForSide[var5];
                int var12 = var1.getBlockId(var9, var10, var11);
                int var13 = var1.getBlockMetadata(var9, var10, var11);

                if (var12 == this.blockID && var9 == var2 && var10 == var3 && var11 == var4)
                {
                    var1.setBlockAndMetadata(var6, var7, var8, Block.pistonMoving.blockID, var5 | (this.isSticky ? 8 : 0));
                    var1.setBlockTileEntity(var6, var7, var8, mod_MorePistons.getTileEntity(MorePistons.pistonExtension.blockID, var5 | (this.isSticky ? 8 : 0), var5, true, false));
                }
                else
                {
                    var1.setBlockAndMetadata(var6, var7, var8, Block.pistonMoving.blockID, var13);
                    var1.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(var12, var13, var5, true, false));
                    this.tryDependableBlocks(var1, var6, var7, var8, var5);
                }

                var6 = var9;
                var7 = var10;
                var8 = var11;
            }

            return true;
        }
    }

    private void tryDependableBlocks(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = var2 - Facing.offsetsXForSide[var5];
        int var7 = var3 - Facing.offsetsYForSide[var5];
        int var8 = var4 - Facing.offsetsZForSide[var5];
        int var9 = var6 - Facing.offsetsXForSide[var5];
        int var10 = var7 - Facing.offsetsYForSide[var5];
        int var11 = var8 - Facing.offsetsZForSide[var5];
        int[] var12 = new int[] {0, 5, 3, 4, 1, 2};
        int var13 = var12[var5];
        int var14 = var1.getBlockId(var9, var10, var11);
        int var15 = var1.getBlockMetadata(var9, var10, var11);
        int var17;
        int var16;
        int var18;

        if (var5 != 0 && var5 != 1)
        {
            var16 = var1.getBlockId(var6, var7 + 1, var8);
            var17 = var1.getBlockMetadata(var6, var7 + 1, var8);

            if (var16 == Block.redstoneWire.blockID || var17 == 5 && (var16 == Block.torchRedstoneIdle.blockID || var16 == Block.torchRedstoneActive.blockID) || var16 == Block.redstoneRepeaterIdle.blockID || var16 == Block.redstoneRepeaterActive.blockID || var16 == Block.pressurePlateStone.blockID || var16 == Block.pressurePlatePlanks.blockID || ((var17 & 7) == 5 || (var17 & 7) == 6) && var16 == Block.lever.blockID || var16 == Block.flowerPot.blockID || var17 == 5 && var16 == Block.torchWood.blockID || var16 == Block.rail.blockID || var16 == Block.railDetector.blockID || var16 == Block.railPowered.blockID || var16 == Block.signPost.blockID || var16 == Block.netherStalk.blockID || var16 == Block.crops.blockID || var16 == Block.carrot.blockID || var16 == Block.cake.blockID || var16 == Block.snow.blockID || var16 == Block.deadBush.blockID || var16 == Block.fire.blockID || var16 == Block.melonStem.blockID || var16 == Block.mushroomBrown.blockID || var16 == Block.mushroomRed.blockID || var16 == Block.plantRed.blockID || var16 == Block.plantYellow.blockID || var16 == Block.pumpkinStem.blockID || var16 == Block.sapling.blockID || var16 == Block.tallGrass.blockID || var16 == Block.tripWire.blockID)
            {
                if (var1.getBlockId(var2, var3 + 1, var4) == 0)
                {
                    System.out.println("Not facing UP or DOWN");
                    var1.setBlockAndMetadata(var6, var7 + 1, var8, 0, 0);
                    var1.setBlockAndMetadata(var2, var3 + 1, var4, Block.pistonMoving.blockID, var17);
                    var1.setBlockTileEntity(var2, var3 + 1, var4, BlockPistonMoving.getTileEntity(var16, var17, var5, true, false));
                }
                else
                {
                    Block.blocksList[var16].dropBlockAsItem(var1, var6, var7 + 1, var8, var1.getBlockMetadata(var6, var7 + 1, var8), 0);
                    var1.setBlockAndMetadata(var6, var7 + 1, var8, 0, 0);
                }
            }

            if (var16 == Block.cactus.blockID || var16 == Block.reed.blockID || var16 == Block.doorWood.blockID || var16 == Block.doorSteel.blockID)
            {
                for (var18 = 2; var18 < 258; ++var18)
                {
                    if (var1.getBlockId(var2, var3 + 1, var4) == 0)
                    {
                        System.out.println("Not facing UP or DOWN");
                        var1.setBlockAndMetadata(var6, var7 + 1, var8, 0, 0);
                        var1.setBlockAndMetadata(var2, var3 + 1, var4, Block.pistonMoving.blockID, var17);
                        var1.setBlockTileEntity(var2, var3 + 1, var4, BlockPistonMoving.getTileEntity(var16, var17, var5, true, false));
                    }
                    else
                    {
                        Block.blocksList[var16].dropBlockAsItem(var1, var6, var7 + 1, var8, var1.getBlockMetadata(var6, var7 + 1, var8), 0);
                        var1.setBlockAndMetadata(var6, var7 + 1, var8, 0, 0);
                    }

                    var1.getBlockId(var6, var7 + 2, var8);

                    if (var1.getBlockId(var6, var7 + 2, var8) != Block.cactus.blockID && var1.getBlockId(var6, var7 + 2, var8) != Block.reed.blockID)
                    {
                        if (var1.getBlockId(var6, var7 + 2, var8) != Block.doorWood.blockID && var1.getBlockId(var6, var7 + 2, var8) != Block.doorSteel.blockID)
                        {
                            var3 -= var18 - 2;
                            var7 -= var18 - 2;
                            break;
                        }

                        var17 = var1.getBlockMetadata(var6, var7 + 2, var8);
                        ++var3;
                        ++var7;
                    }
                    else
                    {
                        ++var3;
                        ++var7;
                    }
                }
            }
        }

        if (var5 != 4 && var5 != 5)
        {
            for (var16 = -1; var16 < 2; var16 += 2)
            {
                var17 = var1.getBlockId(var6 + var16, var7, var8);
                var18 = var1.getBlockMetadata(var6 + var16, var7, var8);

                if (var17 == Block.torchRedstoneActive.blockID || var17 == Block.torchRedstoneIdle.blockID)
                {
                    System.out.println("METADATA = " + var18 + " l3 = " + var16);
                }

                if ((var17 != Block.torchRedstoneActive.blockID && var17 != Block.torchRedstoneIdle.blockID || (var16 != -1 || var18 != 5 && var18 != 1) && (var16 != 1 || var18 != 5 && var18 != 2)) && (var17 == Block.lever.blockID || var17 == Block.stoneButton.blockID || var17 == Block.woodenButton.blockID || var17 == Block.ladder.blockID || var17 == Block.torchWood.blockID || var17 == Block.signWall.blockID || var17 == Block.vine.blockID || var17 == Block.tripWireSource.blockID || var17 == Block.trapdoor.blockID || var17 == Block.torchRedstoneIdle.blockID || var17 == Block.torchRedstoneActive.blockID))
                {
                    if (var1.getBlockId(var2 + var16, var3, var4) == 0)
                    {
                        System.out.println("Not facing EAST or WEST");
                        var1.setBlockAndMetadata(var6 + var16, var7, var8, 0, 0);
                        var1.setBlockAndMetadata(var2 + var16, var3, var4, Block.pistonMoving.blockID, var18);
                        var1.setBlockTileEntity(var2 + var16, var3, var4, BlockPistonMoving.getTileEntity(var17, var18, var5, true, false));
                    }
                    else
                    {
                        Block.blocksList[var17].dropBlockAsItem(var1, var6 + var16, var7, var8, var18, 0);
                        var1.setBlockAndMetadata(var6 + var16, var7, var8, 0, 0);
                    }
                }
            }
        }

        if (var5 != 2 && var5 != 3)
        {
            for (var16 = -1; var16 < 2; var16 += 2)
            {
                var17 = var1.getBlockId(var6, var7, var8 + var16);
                var18 = var1.getBlockMetadata(var6, var7, var8 + var16);

                if ((var17 != Block.torchRedstoneActive.blockID && var17 != Block.torchRedstoneIdle.blockID || (var16 != -1 || var18 != 3 && var18 != 5) && (var16 != 1 || var18 != 4 && var18 != 5)) && (var17 == Block.lever.blockID || var17 == Block.stoneButton.blockID || var17 == Block.woodenButton.blockID || var17 == Block.ladder.blockID || var17 == Block.torchWood.blockID || var17 == Block.signWall.blockID || var17 == Block.vine.blockID || var17 == Block.tripWireSource.blockID || var17 == Block.trapdoor.blockID || var17 == Block.torchRedstoneIdle.blockID || var17 == Block.torchRedstoneActive.blockID))
                {
                    if (var1.getBlockId(var2, var3, var4 + var16) == 0)
                    {
                        System.out.println("Not facing NORTH or SOUTH");
                        var1.setBlockAndMetadata(var6, var7, var8 + var16, 0, 0);
                        var1.setBlockAndMetadata(var2, var3, var4 + var16, Block.pistonMoving.blockID, var18);
                        var1.setBlockTileEntity(var2, var3, var4 + var16, BlockPistonMoving.getTileEntity(var17, var18, var5, true, false));
                    }
                    else
                    {
                        Block.blocksList[var17].dropBlockAsItem(var1, var6, var7, var8 + var16, var18, 0);
                        var1.setBlockAndMetadata(var6, var7, var8 + var16, 0, 0);
                    }
                }
            }
        }

        if (var5 != 0 && var5 != 1)
        {
            if (((var15 & 7) != var13 || var14 != Block.torchRedstoneIdle.blockID && var14 != Block.torchRedstoneActive.blockID) && var14 != Block.lever.blockID && var14 != Block.stoneButton.blockID && var14 != Block.woodenButton.blockID && var14 != Block.ladder.blockID && var14 != Block.torchWood.blockID && var14 != Block.signWall.blockID && var14 != Block.vine.blockID && var14 != Block.tripWireSource.blockID && var14 != Block.trapdoor.blockID)
            {
                var1.setBlockAndMetadata(var6, var7, var8, 0, 0);
            }
            else
            {
                System.out.println("Not facing UP or DOWN 2");
                var1.setBlockAndMetadata(var9, var10, var11, 0, 0);
                var1.setBlockAndMetadata(var6, var7, var8, Block.pistonMoving.blockID, var15);
                var1.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(var14, var15, var5, true, false));
            }
        }
        else if (var5 == 0)
        {
            if (var14 != Block.redstoneWire.blockID && (var15 != 5 || var14 != Block.torchRedstoneIdle.blockID && var14 != Block.torchRedstoneActive.blockID) && var14 != Block.redstoneRepeaterIdle.blockID && var14 != Block.redstoneRepeaterActive.blockID && var14 != Block.pressurePlateStone.blockID && var14 != Block.pressurePlatePlanks.blockID && ((var15 & 7) != 5 && (var15 & 7) != 6 || var14 != Block.lever.blockID) && var14 != Block.flowerPot.blockID && var14 != Block.torchWood.blockID && var14 != Block.rail.blockID && var14 != Block.railDetector.blockID && var14 != Block.railPowered.blockID && var14 != Block.signPost.blockID && var14 != Block.cactus.blockID && var14 != Block.netherStalk.blockID && var14 != Block.crops.blockID && var14 != Block.carrot.blockID && var14 != Block.cake.blockID && var14 != Block.snow.blockID && var14 != Block.bed.blockID && var14 != Block.deadBush.blockID && var14 != Block.fire.blockID && var14 != Block.melonStem.blockID && var14 != Block.mushroomBrown.blockID && var14 != Block.mushroomRed.blockID && var14 != Block.plantRed.blockID && var14 != Block.plantYellow.blockID && var14 != Block.pumpkinStem.blockID && var14 != Block.reed.blockID && var14 != Block.sapling.blockID && var14 != Block.tallGrass.blockID && var14 != Block.tripWire.blockID)
            {
                var1.setBlockAndMetadata(var6, var7, var8, 0, 0);
            }
            else
            {
                System.out.println("Facing UP");
                var1.setBlockAndMetadata(var9, var10, var11, 0, 0);
                var1.setBlockAndMetadata(var6, var7, var8, Block.pistonMoving.blockID, var15);
                var1.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(var14, var15, var5, true, false));
            }
        }
        else
        {
            var1.setBlockAndMetadata(var6, var7, var8, 0, 0);
        }
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        super.breakBlock(var1, var2, var3, var4, var5, var6);
        int var7 = var1.getBlockMetadata(var2, var3, var4);
        int var8 = getDirectionMeta(var7);

        if (BlockGravitationalPistonBase.isExtended(var7))
        {
            int var9 = var2 + Facing.offsetsXForSide[var8];
            int var10 = var3 + Facing.offsetsYForSide[var8];
            int var11 = var4 + Facing.offsetsZForSide[var8];
            int var12 = var1.getBlockId(var9, var10, var11);

            if (var12 == MorePistons.pistonExtension.blockID)
            {
                var1.setBlockWithNotify(var9, var10, var11, 0);
            }

            var1.removeBlockTileEntity(var2, var3, var4);
        }
    }

    public static int getDirectionMeta(int var0)
    {
        return var0 & 7;
    }
}
