package mods.morepistons.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mods.gollum.core.tools.helper.blocks.HBlockContainer;
import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMorePistonsMoving extends HBlockContainer {

	public BlockMorePistonsMoving(String registerName) {
		super(registerName, Material.piston);
		this.setHardness(-1.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityMorePistonsMoving();
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityMorePistonsMoving) {
			Block b = ((TileEntityMorePistonsMoving)te).storedBlock;
			if (b != null) {
				return  b.getDrops(world, x, y, z, te.getBlockMetadata(), 0);
			}
		}
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		return false;
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon("piston_top_normal");
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	//////////////////////////////////
	// Gestion de la forme du block //
	//////////////////////////////////
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityMorePistonsMoving tileEntityMoving = null;
		if (te instanceof TileEntityMorePistonsMoving) {
			tileEntityMoving = (TileEntityMorePistonsMoving) te;
		}

		if (tileEntityMoving == null) {
			return null;
		} else {
			float f = -tileEntityMoving.getProgressWithDistance(0.0F);
			if (tileEntityMoving.root) {
				f = 0;
			}
			return this.getAxisAlignedBB(world, x, y, z, tileEntityMoving.storedBlock, f, tileEntityMoving.storedOrientation);
		}
	}
	
	public AxisAlignedBB getAxisAlignedBB(World world, int x, int y, int z, Block block, float progress, int p_149964_7_) {
		if (block != null && block != this && block.getMaterial() != Material.air) {
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(world, x, y, z);
			
			if (axisalignedbb == null) {
				return null;
			} else {
				if (Facing.offsetsXForSide[p_149964_7_] < 0) {
					axisalignedbb.minX -= (double) ((float) Facing.offsetsXForSide[p_149964_7_] * progress);
				} else {
					axisalignedbb.maxX -= (double) ((float) Facing.offsetsXForSide[p_149964_7_] * progress);
				}

				if (Facing.offsetsYForSide[p_149964_7_] < 0) {
					axisalignedbb.minY -= (double) ((float) Facing.offsetsYForSide[p_149964_7_] * progress);
				} else {
					axisalignedbb.maxY -= (double) ((float) Facing.offsetsYForSide[p_149964_7_] * progress);
				}
				
				if (Facing.offsetsZForSide[p_149964_7_] < 0) {
					axisalignedbb.minZ -= (double) ((float) Facing.offsetsZForSide[p_149964_7_] * progress);
				} else {
					axisalignedbb.maxZ -= (double) ((float) Facing.offsetsZForSide[p_149964_7_] * progress);
				}
				
				return axisalignedbb;
			}
		} else {
			return null;
		}
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		TileEntityMorePistonsMoving tileEntityMoving = null;
		if (te instanceof TileEntityMorePistonsMoving) {
			tileEntityMoving = (TileEntityMorePistonsMoving) te;
		}
		
		if (tileEntityMoving != null) {
			Block block = tileEntityMoving.storedBlock;

			if (block == null || block == this || block.getMaterial() == Material.air) {
				return;
			}

			block.setBlockBoundsBasedOnState(world, x, y, z);
			float f = -tileEntityMoving.getProgressWithDistance(0.0F);
			if (tileEntityMoving.root) {
				f = 0;
			}
			
			int l = tileEntityMoving.storedOrientation;
			this.minX = block.getBlockBoundsMinX() - (double) ((float) Facing.offsetsXForSide[l] * f);
			this.minY = block.getBlockBoundsMinY() - (double) ((float) Facing.offsetsYForSide[l] * f);
			this.minZ = block.getBlockBoundsMinZ() - (double) ((float) Facing.offsetsZForSide[l] * f);
			this.maxX = block.getBlockBoundsMaxX() - (double) ((float) Facing.offsetsXForSide[l] * f);
			this.maxY = block.getBlockBoundsMaxY() - (double) ((float) Facing.offsetsYForSide[l] * f);
			this.maxZ = block.getBlockBoundsMaxZ() - (double) ((float) Facing.offsetsZForSide[l] * f);
		}
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		TileEntity tileentity = world.getTileEntity(x, y, z);

		if (tileentity instanceof TileEntityMorePistonsMoving) {
			((TileEntityMorePistonsMoving) tileentity).setBlockFinalMove();
		} else {
			super.breakBlock(world, x, y, z, block, metadata);
		}
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		if (!world.isRemote && world.getTileEntity(x, y, z) == null) {
			world.setBlockToAir(x, y, z);
			return true;
		} else {
			return false;
		}
	}

	
}