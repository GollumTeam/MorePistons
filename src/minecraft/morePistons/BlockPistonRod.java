package morePistons;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.src.ModLoader;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonRod extends Block
{
    private int headTexture = -1;
    public boolean northSouth = false;
    public boolean upDown = false;
    public boolean broken = false;
    public static boolean pistonMoving2 = false;
    private int pistonRodSprTop = 32;
    private int pistonRodSprSide = 33;

    public BlockPistonRod(int var1, int var2)
    {
        super(var1, var2, Material.rock);
        this.setStepSound(soundStoneFootstep);
        this.setHardness(0.3F);
    }

    public String getTextureFile()
    {
        return "/morePistons/blocks/block_textures.png";
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5)
    {
        return (var5 == 1 || var5 == 0) && this.northSouth ? this.pistonRodSprTop : ((var5 == 4 || var5 == 5) && this.northSouth ? this.pistonRodSprSide : ((var5 == 0 || var5 == 1 || var5 == 2 || var5 == 3) && !this.northSouth && !this.upDown ? this.pistonRodSprSide : ((var5 == 2 || var5 == 3 || var5 == 4 || var5 == 5) && this.upDown ? this.pistonRodSprTop : this.pistonRodSprTop)));
    }

    public void setHeadTexture(int var1)
    {
        this.headTexture = this.pistonRodSprSide;
    }

    public void clearHeadTexture()
    {
        this.headTexture = -1;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        boolean var7 = false;
        boolean var8 = false;
        boolean var9 = false;
        super.breakBlock(var1, var2, var3, var4, var5, var6);
        int var10 = var1.getBlockMetadata(var2, var3, var4);
        int var11 = Facing.faceToSide[getDirectionMeta(var10)];
        var2 += Facing.offsetsXForSide[var11];
        var3 += Facing.offsetsYForSide[var11];
        var4 += Facing.offsetsZForSide[var11];
        int var12 = var1.getBlockId(var2, var3, var4);

        if (!pistonMoving2)
        {
            int var13;

            for (var13 = 0; var13 < MorePistons.pistonList.length; ++var13)
            {
                if (var12 == MorePistons.pistonList[var13])
                {
                    var7 = true;
                    break;
                }

                if (var12 == MorePistons.pistonList.length - 1)
                {
                    var7 = false;
                }
            }

            if (var7)
            {
                var13 = var1.getBlockMetadata(var2, var3, var4);

                if (BlockPistonBase.isExtended(var13))
                {
                    try
                    {
                        Minecraft var14 = ModLoader.getMinecraftInstance();
                        EntityClientPlayerMP var15 = var14.thePlayer;

                        if (!var15.capabilities.isCreativeMode)
                        {
                            Block.blocksList[var12].dropBlockAsItem(var1, var2, var3, var4, 0, 0);
                        }
                    }
                    catch (Throwable var24)
                    {
                        ;
                    }

                    var1.setBlockWithNotify(var2, var3, var4, 0);
                }
            }
            else if (var12 == MorePistons.pistonRod.blockID)
            {
                var13 = var1.getBlockMetadata(var2, var3, var4);
                int var26 = Facing.faceToSide[getDirectionMeta(var13)];
                var2 += Facing.offsetsXForSide[var26];
                var3 += Facing.offsetsYForSide[var26];
                var4 += Facing.offsetsZForSide[var26];
                int var25 = var1.getBlockId(var2, var3, var4);
                int var16;

                for (var16 = 0; var16 < MorePistons.pistonList.length; ++var16)
                {
                    if (var25 == MorePistons.pistonList[var16])
                    {
                        var8 = true;
                        break;
                    }

                    if (var25 == MorePistons.pistonList.length - 1)
                    {
                        var8 = false;
                    }
                }

                if (var8)
                {
                    var16 = var1.getBlockMetadata(var2, var3, var4);

                    if (BlockPistonBase.isExtended(var16))
                    {
                        try
                        {
                            Minecraft var17 = ModLoader.getMinecraftInstance();
                            EntityClientPlayerMP var18 = var17.thePlayer;

                            if (!var18.capabilities.isCreativeMode)
                            {
                                Block.blocksList[var12].dropBlockAsItem(var1, var2, var3, var4, 0, 0);
                            }
                        }
                        catch (Throwable var23)
                        {
                            ;
                        }

                        var1.setBlockWithNotify(var2, var3, var4, 0);
                    }
                }
                else if (var12 == MorePistons.pistonRod.blockID)
                {
                    var16 = var1.getBlockMetadata(var2, var3, var4);
                    int var27 = Facing.faceToSide[getDirectionMeta(var16)];
                    var2 += Facing.offsetsXForSide[var27];
                    var3 += Facing.offsetsYForSide[var27];
                    var4 += Facing.offsetsZForSide[var27];
                    int var28 = var1.getBlockId(var2, var3, var4);
                    int var19;

                    for (var19 = 0; var19 < MorePistons.pistonList.length; ++var19)
                    {
                        if (var28 == MorePistons.pistonList[var19])
                        {
                            var9 = true;
                            break;
                        }

                        if (var28 == MorePistons.pistonList.length - 1)
                        {
                            var9 = false;
                        }
                    }

                    if (var9)
                    {
                        var19 = var1.getBlockMetadata(var2, var3, var4);

                        if (BlockPistonBase.isExtended(var19))
                        {
                            try
                            {
                                Minecraft var20 = ModLoader.getMinecraftInstance();
                                EntityClientPlayerMP var21 = var20.thePlayer;

                                if (!var21.capabilities.isCreativeMode)
                                {
                                    Block.blocksList[var12].dropBlockAsItem(var1, var2, var3, var4, 0, 0);
                                }
                            }
                            catch (Throwable var22)
                            {
                                ;
                            }

                            var1.setBlockWithNotify(var2, var3, var4, 0);
                        }
                    }
                }
            }
            else if (var12 == Block.pistonMoving.blockID)
            {
                pistonMoving2 = true;
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int var1, int var2)
    {
        int var3 = getDirectionMeta(var2);
        return var1 != Facing.faceToSide[var3] ? this.pistonRodSprTop : this.pistonRodSprSide;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4)
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World var1, int var2, int var3, int var4, int var5)
    {
        return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random var1)
    {
        return 0;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
    }

    /**
     * if the specified block is in the given AABB, add its collision bounding box to the given list
     */
    public void addCollidingBlockToList(World var1, int var2, int var3, int var4, AxisAlignedBB var5, List var6, Entity var7)
    {
        int var8 = var1.getBlockMetadata(var2, var3, var4);

        switch (getDirectionMeta(var8))
        {
            case 0:
                this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.25F, 0.625F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                break;

            case 1:
                this.setBlockBounds(0.375F, -0.25F, 0.375F, 0.625F, 0.75F, 0.625F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                break;

            case 2:
                this.setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.625F, 1.25F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                break;

            case 3:
                this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                break;

            case 4:
                this.setBlockBounds(-0.25F, 0.25F, 0.25F, 1.25F, 0.75F, 0.75F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
                break;

            case 5:
                this.setBlockBounds(-0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
                super.addCollidingBlockToList(var1, var2, var3, var4, var5, var6, var7);
        }

        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4)
    {
        int var5 = var1.getBlockMetadata(var2, var3, var4);

        switch (getDirectionMeta(var5))
        {
            case 0:
                this.upDown = true;
                this.northSouth = false;
                this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.25F, 0.625F);
                break;

            case 1:
                this.upDown = true;
                this.northSouth = false;
                this.setBlockBounds(0.375F, -0.25F, 0.375F, 0.625F, 0.75F, 0.625F);
                break;

            case 2:
                this.upDown = false;
                this.northSouth = true;
                this.setBlockBounds(0.375F, 0.375F, 0.25F, 0.625F, 0.625F, 1.25F);
                break;

            case 3:
                this.upDown = false;
                this.northSouth = true;
                this.setBlockBounds(0.375F, 0.375F, -0.25F, 0.625F, 0.625F, 0.75F);
                break;

            case 4:
                this.upDown = false;
                this.northSouth = false;
                this.setBlockBounds(0.25F, 0.375F, 0.375F, 1.25F, 0.625F, 0.625F);
                break;

            case 5:
                this.upDown = false;
                this.northSouth = false;
                this.setBlockBounds(-0.25F, 0.375F, 0.375F, 0.75F, 0.625F, 0.625F);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = getDirectionMeta(var1.getBlockMetadata(var2, var3, var4));
        int var7 = var1.getBlockId(var2 - Facing.offsetsXForSide[var6], var3 - Facing.offsetsYForSide[var6], var4 - Facing.offsetsZForSide[var6]);
        int var8 = 0;
        boolean var9 = false;

        for (int var10 = 0; var10 < MorePistons.pistonList.length; ++var10)
        {
            if (var7 != MorePistons.pistonList[var10])
            {
                ++var8;
            }

            if (var8 == MorePistons.pistonList.length)
            {
                var9 = true;
                break;
            }

            if (var7 != MorePistons.pistonList.length - 1)
            {
                var9 = false;
            }
        }

        if (var9 && var7 != MorePistons.pistonRod.blockID && var7 != MorePistons.pistonExtension.blockID)
        {
            var1.setBlockWithNotify(var2, var3, var4, 0);
        }
        else
        {
            Block.blocksList[var7].onNeighborBlockChange(var1, var2 - Facing.offsetsXForSide[var6], var3 - Facing.offsetsYForSide[var6], var4 - Facing.offsetsZForSide[var6], var5);
        }
    }

    public static int getDirectionMeta(int var0)
    {
        return var0 & 7;
    }
}
