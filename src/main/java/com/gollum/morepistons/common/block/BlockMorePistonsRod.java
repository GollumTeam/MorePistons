package com.gollum.morepistons.common.block;

import static net.minecraft.block.BlockPistonExtension.FACING;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;

public class BlockMorePistonsRod extends HBlockContainer {
	
	public static final PropertyBool SHORT = PropertyBool.create("short");
	
	public BlockMorePistonsRod(String registerName) {
		super(registerName, Material.grass);
		
		this.setDefaultState(this.getDefaultState()
				.withProperty(FACING, EnumFacing.NORTH)
				.withProperty(SHORT, false)
		);
		
		setStepSound(soundTypeStone);
		setHardness(0.3F);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityMorePistonsRod();
	}
	
	////////////
	// States //
	////////////
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[]{
			FACING,
			SHORT,
		});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
			.withProperty(FACING, getFacing(meta)
		);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
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
	
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		
		IBlockState state = world.getBlockState(pos);
		EnumFacing facing = state.getValue(FACING);
		
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityMorePistonsRod ) {
			
			TileEntityMorePistonsRod tileEntityRod = (TileEntityMorePistonsRod) te;
//			TileEntityMorePistonsMoving tileEntityMoving = tileEntityRod.getTileEntityMoving();
			
			this.setBlockBoundsBasedOnState(state, facing);
			
//			if (tileEntityMoving != null) {
	
//				Block block = state.getBlock();
//				
//				float extend = -tileEntityMoving.getProgressWithDistance(0.0F);
//				if (tileEntityMoving.root) {
//					extend = 0;
//				}
//				
//				this.minX -= (double) ((float) facing.getFrontOffsetX() * extend);
//				this.minY -= (double) ((float) facing.getFrontOffsetY() * extend);
//				this.minZ -= (double) ((float) facing.getFrontOffsetZ() * extend);
//				this.maxX -= (double) ((float) facing.getFrontOffsetX() * extend);
//				this.maxY -= (double) ((float) facing.getFrontOffsetY() * extend);
//				this.maxZ -= (double) ((float) facing.getFrontOffsetZ() * extend);
//			}
		}
	}
	
	public void setBlockBoundsBasedOnState(IBlockState state, EnumFacing facing) {
		
		switch (state.getValue(FACING)) {
			case DOWN:
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.25F, 0.625F);
				break;
			case UP:
				setBlockBounds(0.375F, -0.25F, 0.375F, 0.625F, 0.75F, 0.625F);
				break;
			case NORTH:
				setBlockBounds(0.375F, 0.375F, 0.25F, 0.625F, 0.625F, 1.25F);
				break;
			case SOUTH:
				setBlockBounds(0.375F, 0.375F, -0.25F, 0.625F, 0.625F, 0.75F);
				break;
			case WEST:
				setBlockBounds(0.25F, 0.375F, 0.375F, 1.25F, 0.625F, 0.625F);
				break;
			case EAST:
				setBlockBounds(-0.25F, 0.375F, 0.375F, 0.75F, 0.625F, 0.625F);
		}
	}
	
	@Override
 	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing facing) {
		return false;
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsRod) {
			TileEntityMorePistonsPiston tep = ((TileEntityMorePistonsRod) te).getTileEntityPiston();
			if (tep != null) {
				world.destroyBlock(tep.getPos(), true);
			}
		}
		
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	@Override
   public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsRod) {
			TileEntityMorePistonsPiston tep = ((TileEntityMorePistonsRod) te).getTileEntityPiston();
			if (tep != null) {
				return;
			}
		}
		world.setBlockToAir(pos);
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos) {
		return null;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
}
