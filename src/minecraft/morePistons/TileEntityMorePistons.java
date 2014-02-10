package morePistons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;

public class TileEntityMorePistons extends TileEntityPiston
{
    private int storedBlockID;
    private int storedMetadata;
    private int storedOrientation;
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private float progress;
    private float lastProgress;
    private List pushedObjects = new ArrayList();

    public TileEntityMorePistons() {}

    public TileEntityMorePistons(int var1, int var2, int var3, boolean var4, boolean var5)
    {
        this.storedBlockID = var1;
        this.storedMetadata = var2;
        this.storedOrientation = var3;
        this.extending = var4;
        this.shouldHeadBeRendered = var5;
    }

    public int getStoredBlockID()
    {
        return this.storedBlockID;
    }

    /**
     * Returns block data at the location of this entity (client-only).
     */
    public int getBlockMetadata()
    {
        return this.storedMetadata;
    }

    /**
     * Returns true if a piston is extending
     */
    public boolean isExtending()
    {
        return this.extending;
    }

    /**
     * Returns the orientation of the piston as an int
     */
    public int getPistonOrientation()
    {
        return this.storedOrientation;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRenderHead()
    {
        return this.shouldHeadBeRendered;
    }

    /**
     * Get interpolated progress value (between lastProgress and progress) given the fractional time between ticks as an
     * argument.
     */
    public float getProgress(float var1)
    {
        if (var1 > 1.0F)
        {
            var1 = 1.0F;
        }

        return this.lastProgress + (this.progress - this.lastProgress) * var1;
    }

    private void updatePushedObjects(float var1, float var2)
    {
        if (this.extending)
        {
            var1 = 1.0F - var1;
        }
        else
        {
            --var1;
        }

        AxisAlignedBB var3 = Block.pistonMoving.getAxisAlignedBB(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, var1, this.storedOrientation);

        if (var3 != null)
        {
            List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, var3);

            if (!var4.isEmpty())
            {
                this.pushedObjects.addAll(var4);
                Iterator var5 = this.pushedObjects.iterator();

                while (var5.hasNext())
                {
                    Entity var6 = (Entity)var5.next();
                    var6.moveEntity((double)(var2 * (float)Facing.offsetsXForSide[this.storedOrientation]), (double)(var2 * (float)Facing.offsetsYForSide[this.storedOrientation]), (double)(var2 * (float)Facing.offsetsZForSide[this.storedOrientation]));
                }

                this.pushedObjects.clear();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public float getOffsetX(float var1)
    {
        return this.extending ? (this.getProgress(var1) - 1.0F) * (float)Facing.offsetsXForSide[this.storedOrientation] : (1.0F - this.getProgress(var1)) * (float)Facing.offsetsXForSide[this.storedOrientation];
    }

    @SideOnly(Side.CLIENT)
    public float getOffsetY(float var1)
    {
        return this.extending ? (this.getProgress(var1) - 1.0F) * (float)Facing.offsetsYForSide[this.storedOrientation] : (1.0F - this.getProgress(var1)) * (float)Facing.offsetsYForSide[this.storedOrientation];
    }

    @SideOnly(Side.CLIENT)
    public float getOffsetZ(float var1)
    {
        return this.extending ? (this.getProgress(var1) - 1.0F) * (float)Facing.offsetsZForSide[this.storedOrientation] : (1.0F - this.getProgress(var1)) * (float)Facing.offsetsZForSide[this.storedOrientation];
    }

    /**
     * removes a pistons tile entity (and if the piston is moving, stops it)
     */
    public void clearPistonTileEntity()
    {
        if (this.lastProgress < 1.0F && this.worldObj != null)
        {
            this.lastProgress = this.progress = 1.0F;
            this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
            this.invalidate();

            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID)
            {
                this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, this.storedMetadata);
            }
        }
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        this.lastProgress = this.progress;

        if (this.lastProgress >= 1.0F)
        {
            this.updatePushedObjects(1.0F, 0.25F);
            this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
            this.invalidate();

            if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID)
            {
                this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, this.storedMetadata);
            }
        }
        else
        {
            this.progress += 0.5F;

            if (this.progress >= 1.0F)
            {
                this.progress = 1.0F;
            }

            if (this.extending)
            {
                this.updatePushedObjects(this.progress, this.progress - this.lastProgress + 0.0625F);
            }
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound var1)
    {
        super.readFromNBT(var1);
        this.storedBlockID = var1.getInteger("blockId");
        this.storedMetadata = var1.getInteger("blockData");
        this.storedOrientation = var1.getInteger("facing");
        this.lastProgress = this.progress = var1.getFloat("progress");
        this.extending = var1.getBoolean("extending");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound var1)
    {
        super.writeToNBT(var1);
        var1.setInteger("blockId", this.storedBlockID);
        var1.setInteger("blockData", this.storedMetadata);
        var1.setInteger("facing", this.storedOrientation);
        var1.setFloat("progress", this.lastProgress);
        var1.setBoolean("extending", this.extending);
    }
}
