package com.gollum.morepistons.common.block;
import static net.minecraft.block.BlockPistonBase.EXTENDED;
import static net.minecraft.block.BlockPistonBase.FACING;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMorePistonsRedStone extends BlockMorePistonsBase {

    public static final PropertyInteger POWER = PropertyInteger.create("power", 1, 8);
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonsRedStone(String registerName, boolean isSticky) {
		super(registerName, isSticky);
	}
	
	////////////
	// States //
	////////////
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[]{
			FACING,
			EXTENDED,
			POWER
		});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta)
			.withProperty(POWER, 1)
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
				POWER,
				((TileEntityMorePistonsPiston)te).stickySize
			);
		}
        return state;
    }
//	
//	///////////////////////////////////
//	// Gestion du signal de redstone //
//	///////////////////////////////////
//	
//	/**
//	 * @param int metadata
//	 * @return int multiplier
//	 */
//	public int getMutiplier (World world, int x, int y, int z) {
//		
//		int multiplier = 1;
//		
//		TileEntity te = world.getTileEntity(x, y, z);
//		if (te instanceof TileEntityMorePistonsMoving) {
//			te = ((TileEntityMorePistonsMoving) te).subTe;
//		}
//		if (te instanceof TileEntityMorePistonsPiston) {
//			multiplier = ((TileEntityMorePistonsPiston)te).multiplier;
//		}
//		
//		return multiplier;
//	}
//	
//	public void applyMutiplier (World world, int x, int y, int z, int multi) {
//		
//		log.debug ("applyMultiplier = "+multi, "remote="+world.isRemote);
//		
//		int metadata = world.getBlockMetadata(x, y, z);
//		
//		TileEntity te = world.getTileEntity(x, y, z);
//		if (te instanceof TileEntityMorePistonsMoving) {
//			te = ((TileEntityMorePistonsMoving) te).subTe;
//		}
//		if (te instanceof TileEntityMorePistonsPiston) {
//			((TileEntityMorePistonsPiston)te).multiplier = multi;
//		}
//		
//		world.notifyBlockOfNeighborChange(x, y, z, this);
//	}
//
//	////////////////////////
//	// Gestion des events //
//	////////////////////////
//	
//	/**
//	 * Affecte la taille du piston
//	 * @param length
//	 * @return
//	 */
//	@Override
//	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
//		int power = 0;
//		int multi = this.getMutiplier(world, x, y, z);
//		
//		if (this.isIndirectlyPowered(world, x, y, z, orientation)) {
//			power = world.getStrongestIndirectPower(x, y, z);
//		}
//		
//		return power*multi;
//	}
//	
//	/**
//	 * Called upon block activation (right click on the block.)
//	 */
//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int faceClicked, float par7, float par8, float par9)  {
//		int metadata = world.getBlockMetadata(x, y, z);
//		int orientation = BlockPistonBase.getPistonOrientation(metadata);
//		if (faceClicked == orientation) {
//			return false;
//		}
//		
//		int multi = this.getMutiplier(world, x, y, z) % 8 + 1;
//		
//		this.applyMutiplier(world, x, y, z, multi);
//		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
//		
//		return true;
//	}
//	
}
