package com.gollum.morepistons.common.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class BlockMorePistonsMoving extends HBlockContainer {

	public BlockMorePistonsMoving(int id, String registerName) {
		super(id, registerName, Material.piston);
		this.setHardness(-1.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityMorePistonsMoving();
	}
	
	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityMorePistonsMoving) {
			Block b = ((TileEntityMorePistonsMoving)te).storedBlock;
			if (b != null) {
				return  b.getBlockDropped(world, x, y, z, te.getBlockMetadata(), 0);
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
	public void registerIcons(IconRegister iconRegister) {
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

		TileEntity te = world.getBlockTileEntity(x, y, z);
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
	
	public AxisAlignedBB getAxisAlignedBB(World world, int x, int y, int z, Block block, float progress, int direction) {
		if (block != null && block != this) {
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(world, x, y, z);
			
			if (axisalignedbb == null) {
				return null;
			} else {
				if (Facing.offsetsXForSide[direction] < 0) {
					axisalignedbb.minX -= (double) ((float) Facing.offsetsXForSide[direction] * progress);
				} else {
					axisalignedbb.maxX -= (double) ((float) Facing.offsetsXForSide[direction] * progress);
				}

				if (Facing.offsetsYForSide[direction] < 0) {
					axisalignedbb.minY -= (double) ((float) Facing.offsetsYForSide[direction] * progress);
				} else {
					axisalignedbb.maxY -= (double) ((float) Facing.offsetsYForSide[direction] * progress);
				}
				
				if (Facing.offsetsZForSide[direction] < 0) {
					axisalignedbb.minZ -= (double) ((float) Facing.offsetsZForSide[direction] * progress);
				} else {
					axisalignedbb.maxZ -= (double) ((float) Facing.offsetsZForSide[direction] * progress);
				}
				
				return axisalignedbb;
			}
		} else {
			return null;
		}
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		TileEntityMorePistonsMoving tileEntityMoving = null;
		if (te instanceof TileEntityMorePistonsMoving) {
			tileEntityMoving = (TileEntityMorePistonsMoving) te;
		}
		
		if (tileEntityMoving != null) {
			Block block = tileEntityMoving.storedBlock;

			if (block == null || block == this) {
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
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int metadata) {
		if (!BlockMorePistonsBase.cleanBlockMoving(world, x, y, z)) {
			super.breakBlock(world, x, y, z, blockId, metadata);
		}
	}
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			
			TileEntityMorePistonsMoving tem = (TileEntityMorePistonsMoving)te;
			TileEntityMorePistonsPiston tep = tem.getPistonOriginTE();
			
			if (
				tem.storedBlock instanceof BlockMorePistonsExtension && 
				tep != null
			) {
				world.destroyBlock(tep.xCoord, tep.yCoord, tep.zCoord, true);
			}
			
		}
		
		return super.removeBlockByPlayer(world, player, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			
			TileEntityMorePistonsMoving tem = (TileEntityMorePistonsMoving)te;
			TileEntityMorePistonsPiston tep = tem.getPistonOriginTE();
			
			if (
				tem.storedBlock instanceof BlockMorePistonsExtension && 
				tep == null
			) {
				world.setBlockToAir(x, y, z);
			}
			
		}
	}
	
	protected boolean cleanAndDestroy(World world, int x, int y, int z, int direction) {
		boolean cont;
		int   id    = world.getBlockId(x, y, z);
		Block block = Block.blocksList[id];
		
		cont = false;
		
		if (
			BlockPistonBase.getOrientation(world.getBlockMetadata(x, y, z)) == direction &&
			(
				block instanceof BlockMorePistonsRod ||
				block instanceof BlockMorePistonsExtension ||
				block instanceof BlockMorePistonsBase
			)
		) {
			cont = !(block instanceof BlockMorePistonsBase);
			world.destroyBlock(x, y, z, block instanceof BlockMorePistonsBase);
		} else if (block instanceof BlockMorePistonsMoving) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			
			if (
				te instanceof TileEntityMorePistonsMoving &&
				((TileEntityMorePistonsMoving)te).storedOrientation == direction
			) {
				cont = true;
				TileEntityMorePistonsMoving tem = (TileEntityMorePistonsMoving)te;
				
				if (
					tem.storedBlock instanceof BlockMorePistonsBase ||
					tem.storedBlock instanceof BlockMorePistonsExtension
				) {
					world.destroyBlock(x, y, z, false);
				}
			}
		}
		return cont;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		if (!world.isRemote && world.getBlockTileEntity(x, y, z) == null) {
			world.setBlockToAir(x, y, z);
			return true;
		} else {
			return false;
		}
	}

	
}