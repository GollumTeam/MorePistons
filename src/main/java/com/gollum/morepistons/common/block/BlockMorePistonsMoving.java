package com.gollum.morepistons.common.block;

import static net.minecraft.block.BlockPistonExtension.FACING;
import static net.minecraft.block.BlockPistonExtension.TYPE;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class BlockMorePistonsMoving extends HBlockContainer {
	
	public BlockMorePistonsMoving(String registerName) {
		super(registerName, Material.piston);
		
		this.setDefaultState(this.getDefaultState()
			.withProperty(FACING, EnumFacing.NORTH)
			.withProperty(TYPE, BlockPistonExtension.EnumPistonType.DEFAULT)
		);
		
		this.setHardness(-1.0F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return null;
	}
	
	////////////
	// States //
	////////////
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[]{
			FACING,
			TYPE,
		});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
			.withProperty(FACING, getFacing(meta))
			.withProperty(TYPE, (meta & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT
		);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(FACING).getIndex();
		
		if (state.getValue(TYPE) == BlockPistonExtension.EnumPistonType.STICKY) {
			meta |= 8;
		}
		
		return meta;
	}
	
	public static EnumFacing getFacing(int meta) {
		int i = meta & 7;
		return i > 5 ? null : EnumFacing.getFront(i);
	}
	
	///////////
	// Rendu //
	///////////
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	//////////////////////////////////
	// Gestion de la forme du block //
	//////////////////////////////////
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {

		TileEntity te = world.getTileEntity(pos);
		TileEntityMorePistonsMoving tileEntityMoving = null;
		if (te instanceof TileEntityMorePistonsMoving) {
			tileEntityMoving = (TileEntityMorePistonsMoving) te;
		}

		if (tileEntityMoving == null) {
			return null;
		} else {
			double f = -tileEntityMoving.getProgressWithDistance(0.0F);
			if (tileEntityMoving.root) {
				f = 0;
			}
			return this.getBoundingBox(world, pos, tileEntityMoving.storedState, f, tileEntityMoving.getFacing());
		}
	}
	
	public AxisAlignedBB getBoundingBox(World worldIn, BlockPos pos, IBlockState extendingBlock, double progress, EnumFacing facing) {
		if (extendingBlock != null && extendingBlock.getBlock() != this && extendingBlock.getBlock().getMaterial() != Material.air) {
			AxisAlignedBB axisalignedbb = extendingBlock.getBlock().getCollisionBoundingBox(worldIn, pos, extendingBlock);
			
			if (axisalignedbb == null) {
				return null;
			} else {
				
				double d0 = axisalignedbb.minX;
				double d1 = axisalignedbb.minY;
				double d2 = axisalignedbb.minZ;
				double d3 = axisalignedbb.maxX;
				double d4 = axisalignedbb.maxY;
				double d5 = axisalignedbb.maxZ;
				
				if (facing.getFrontOffsetX() < 0) {
					d0 -= facing.getFrontOffsetX() * progress;
				} else {
					d3 -= facing.getFrontOffsetX() * progress;
				}
				
				if (facing.getFrontOffsetY() < 0) {
					d1 -= facing.getFrontOffsetY() * progress;
				} else {
					d4 -= facing.getFrontOffsetY() * progress;
				}
				
				if (facing.getFrontOffsetZ() < 0) {
					d2 -= facing.getFrontOffsetZ() * progress;
				} else {
					d5 -= facing.getFrontOffsetZ() * progress;
				}
				
				return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
			}
		} else {
			return null;
		}
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		TileEntityMorePistonsMoving tileEntityMoving = null;
		if (te instanceof TileEntityMorePistonsMoving) {
			tileEntityMoving = (TileEntityMorePistonsMoving) te;
		}
		
		if (tileEntityMoving != null) {
			IBlockState state = tileEntityMoving.storedState;

			if (state == null || state.getBlock() == this || state.getBlock().getMaterial() == Material.air) {
				return;
			}
			
			Block block = state.getBlock();
			
			state.getBlock().setBlockBoundsBasedOnState(world, pos);
			double extend = -tileEntityMoving.getProgressWithDistance(0.0F);
			if (tileEntityMoving.root) {
				extend = 0;
			}
			
			EnumFacing facing = tileEntityMoving.getFacing();
			this.minX = block.getBlockBoundsMinX() - (double) ((float) facing.getFrontOffsetX() * extend);
			this.minY = block.getBlockBoundsMinY() - (double) ((float) facing.getFrontOffsetY() * extend);
			this.minZ = block.getBlockBoundsMinZ() - (double) ((float) facing.getFrontOffsetZ() * extend);
			this.maxX = block.getBlockBoundsMaxX() - (double) ((float) facing.getFrontOffsetX() * extend);
			this.maxY = block.getBlockBoundsMaxY() - (double) ((float) facing.getFrontOffsetY() * extend);
			this.maxZ = block.getBlockBoundsMaxZ() - (double) ((float) facing.getFrontOffsetZ() * extend);
		}
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!BlockMorePistonsBase.cleanBlockMoving(world, pos)) {
			super.breakBlock(world, pos, state);
		}
	}
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			
			TileEntityMorePistonsMoving tem = (TileEntityMorePistonsMoving)te;
			TileEntityMorePistonsPiston tep = tem.getPistonOriginTE();
			
			if (
				tem.storedState != null &&
				tem.storedState.getBlock() instanceof BlockMorePistonsExtension && 
				tep != null
			) {
				world.destroyBlock(tep.getPos(), true);
			}
			
		}
		
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {

		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			
			TileEntityMorePistonsMoving tem = (TileEntityMorePistonsMoving)te;
			TileEntityMorePistonsPiston tep = tem.getPistonOriginTE();
			
			if (
				tem.storedState != null &&
				tem.storedState.getBlock() instanceof BlockMorePistonsExtension && 
				tep == null
			) {
				world.setBlockToAir(pos);
			}
			
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && world.getTileEntity(pos) == null) {
			world.setBlockToAir(pos);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public java.util.List<net.minecraft.item.ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityMorePistonsMoving) {
			IBlockState s = ((TileEntityMorePistonsMoving)te).storedState;
			return s.getBlock().getDrops(world, pos, s, fortune);
		}
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
}