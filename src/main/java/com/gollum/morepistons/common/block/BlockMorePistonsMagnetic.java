package com.gollum.morepistons.common.block;

import static com.gollum.morepistons.ModMorePistons.log;
import static net.minecraft.block.BlockPistonBase.EXTENDED;
import static net.minecraft.block.BlockPistonBase.FACING;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.inits.ModBlocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMorePistonsMagnetic extends BlockMorePistonsBase {

    public static final PropertyInteger SIZE = PropertyInteger.create("size", 1, 8);

	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonsMagnetic(String registerName) {
		super(registerName, true);
	}
	
	public BlockMorePistonsExtension getBlockExtention () {
		return ModBlocks.blockPistonMagneticExtention;
	}
	
	////////////
	// States //
	////////////
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[]{
			FACING,
			EXTENDED,
			SIZE
		});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta)
			.withProperty(SIZE, 1)
		;
	}
	
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving) te).subTe;
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			state.withProperty(
				SIZE,
				((TileEntityMorePistonsPiston)te).stickySize
			);
		}
        return state;
    }
	
	///////////////////////////////////
	// Gestion du signal de redstone //
	///////////////////////////////////
	
	public void applyStickySize (World world, BlockPos pos, int size) {
		
		log.debug ("applyRetractSize = "+size, "remote="+world.isRemote);
		
		IBlockState state = world.getBlockState(pos);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving) te).subTe;
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			((TileEntityMorePistonsPiston)te).stickySize = size;
		}
		
		world.notifyBlockOfStateChange(pos, this);
	}

	////////////////////////
	// Gestion des events //
	////////////////////////
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing facing, float hitX, float hitY, float hitZ) {
		EnumFacing orientation = state.getValue(FACING);
		if (facing == orientation) {
			return false;
		}
		
		int size = this.getStickySize(world, pos) % 8 + 1;
		this.applyStickySize(world, pos, size);
		world.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
		
		return true;
	}
	
}
