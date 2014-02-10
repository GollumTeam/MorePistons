package morePistons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockMorePistonsExtension extends BlockPistonExtension
{
    private int headTexture = -1;
    public boolean northSouth = false;
    public boolean upDown = false;

    public BlockMorePistonsExtension(int var1, int var2)
    {
        super(var1, var2);
        this.setStepSound(soundStoneFootstep);
        this.setHardness(0.5F);
        this.setRequiresSelfNotify();
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        super.breakBlock(var1, var2, var3, var4, var5, var6);
        int var7 = getDirectionFromMetadata(var6);
        var2 -= Facing.offsetsXForSide[var7];
        var3 -= Facing.offsetsYForSide[var7];
        var4 -= Facing.offsetsZForSide[var7];
        int var8 = var1.getBlockId(var2, var3, var4);

        if (var8 == MorePistons.gravitationalPistonBase.blockID || var8 == MorePistons.superPistonBase.blockID || var8 == MorePistons.superStickyPistonBase.blockID || var8 == MorePistons.pistonRod.blockID)
        {
            if (var8 == MorePistons.pistonRod.blockID)
            {
                int var9 = var1.getBlockMetadata(var2, var3, var4);
                int var10 = getDirectionFromMetadata(var9);

                if (var10 == var7)
                {
                    var1.setBlockWithNotify(var2, var3, var4, 0);
                }
            }
            else
            {
                try
                {
                    Minecraft var13 = ModLoader.getMinecraftInstance();
                    EntityClientPlayerMP var12 = var13.thePlayer;

                    if (!var12.capabilities.isCreativeMode)
                    {
                        Block.blocksList[var8].dropBlockAsItem(var1, var2, var3, var4, 0, 0);
                    }
                }
                catch (Throwable var11)
                {
                    ;
                }

                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5)
    {
        int var6 = getDirectionFromMetadata(var1.getBlockMetadata(var2, var3, var4));
        int var7 = var2 - Facing.offsetsXForSide[var6];
        int var8 = var3 - Facing.offsetsYForSide[var6];
        int var9 = var4 - Facing.offsetsZForSide[var6];
        int var10 = var1.getBlockId(var7, var8, var9);

        if (var10 != MorePistons.gravitationalPistonBase.blockID && var10 != MorePistons.superPistonBase.blockID && var10 != MorePistons.superStickyPistonBase.blockID)
        {
            if (var10 == MorePistons.pistonRod.blockID)
            {
                int var11 = var1.getBlockMetadata(var7, var8, var9);
                int var12 = getDirectionFromMetadata(var11);

                if (var12 != var6)
                {
                    var1.setBlockWithNotify(var2, var3, var4, 0);
                }
            }
            else
            {
                var1.setBlockWithNotify(var2, var3, var4, 0);
            }
        }
    }

    public static int getDirectionFromMetadata(int var0)
    {
        return var0 & 7;
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World var1, int var2, int var3, int var4)
    {
        return 0;
    }
}
