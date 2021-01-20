package com.gollum.morepistons.common.block;

import static com.gollum.morepistons.ModMorePistons.log;
import static net.minecraft.block.BlockPistonBase.EXTENDED;
import static net.minecraft.block.BlockPistonBase.FACING;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gollum.core.common.blocks.IBlockDisplayInfos;
import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;
import com.gollum.morepistons.inits.ModCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonExtension.EnumPistonType;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMorePistonsBase extends HBlockContainer implements IBlockDisplayInfos {
    
	protected static class EMoveInfosExtend {
		
		public IBlockState state = null;
		public BlockPos position = new BlockPos(0, 0, 0);
		public TileEntity tileEntity = null;
		public int move = 0;
		public int waitFinish = 0;
		public EMoveInfosExtend() {}
		
		public EMoveInfosExtend(IBlockState state, TileEntity tileEntity, BlockPos position, int move) {
			this(state, tileEntity, position, move, 0);
		}
		
		public EMoveInfosExtend(IBlockState state, TileEntity tileEntity, BlockPos position, int move, int waitFinish) {
			this.state      = state;
			this.position   = position;
			this.tileEntity = BlockMorePistonsBase.cloneTileEntity(tileEntity);
			this.move       = move;
			this.waitFinish = waitFinish;
		}
	}
	
	protected boolean isSticky;
	private   int     length = 1;
	
	public BlockMorePistonsBase(String registerName, boolean isSticky) {
		super(registerName, Material.piston);
		
		this.setDefaultState(this.getDefaultState()
			.withProperty(FACING, EnumFacing.NORTH)
			.withProperty(EXTENDED, Boolean.valueOf(false))
		);
		
		this.isSticky = isSticky;
		this.setStepSound(soundTypePiston);
		this.setHardness(0.5F);
		this.setCreativeTab(ModCreativeTab.morePistonsTabs);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityMorePistonsPiston();
	}
	
	////////////
	// States //
	////////////
	
	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[]{
			FACING,
			EXTENDED,
		});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
			.withProperty(FACING, BlockPistonBase.getFacing(meta))
			.withProperty(EXTENDED, Boolean.valueOf((meta & 8) > 0)
		);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IBlockState getStateForEntityRender(IBlockState state) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.UP);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta = meta | ((EnumFacing)state.getValue(FACING)).getIndex();
		
		if (((Boolean)state.getValue(EXTENDED)).booleanValue()) {
			meta |= 8;
		}
		
		return meta;
	}
	
	public EnumPistonType getEnumPistonType () {
		return this.isSticky ? EnumPistonType.STICKY : EnumPistonType.DEFAULT;
	}
	///////////
	// Datas //
	///////////
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public BlockMorePistonsBase setLength(int length) {
		this.length = length;
		return this;
	}
	
	public int getLengthInWorld(World world, BlockPos pos, EnumFacing facing) {
		return this.length;
	}
	
	public boolean isSticky() {
		return this.isSticky;
	}
	
	/**
	 * Block maximal que peux pouser le piston
	 * @return
	 */
	public int getMaxBlockMove () {
		return ModMorePistons.config.numberMovableBlockWithDefaultPiston;
	}
	
	public BlockMorePistonsExtension getBlockExtention () {
		return ModBlocks.blockPistonExtention;
	}
	
	///////////
	// Rendu //
	///////////
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public String displayDebugInfos(World world, BlockPos pos) {
		
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsPiston) {
			TileEntityMorePistonsPiston tileEntityPiston = (TileEntityMorePistonsPiston)te;
			return "T.E.M.P.Piston : currentOpened :"+tileEntityPiston.currentOpened;
		}
		return null;
	}
	
	////////////////////////////
	// Gestion des collisions //
	////////////////////////////
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		
		if (state.getBlock() == this && state.getValue(EXTENDED).booleanValue()) {
			switch (state.getValue(FACING)) {
				case DOWN:
					this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case UP:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
					break;
				case NORTH:
					this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
					break;
				case SOUTH:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
					break;
				case WEST:
					this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case EAST:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
			}
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state) {
		this.setBlockBoundsBasedOnState(world, pos);
		return super.getCollisionBoundingBox(world, pos, state);
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, 0);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
		
		log.debug("onBlockDestroyedByPlayer : "+pos);
		
		EnumFacing facing = state.getValue(FACING);
		state = ModBlocks.blockPistonRod.getDefaultState();
		while (state.getBlock() instanceof BlockMorePistonsRod) {

			pos = pos.offset(facing);
			
			BlockMorePistonsBase.cleanBlockMoving(world, pos);
			state = world.getBlockState(pos);
			
			if (
				(
					state.getBlock() instanceof BlockMorePistonsRod || 
					state.getBlock() instanceof BlockMorePistonsExtension
				) &&
				state.getValue(BlockPistonExtension.FACING) == facing
			) {
				world.destroyBlock(pos, false);
			}
			
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		log.debug("onNeighborBlockChange : ", pos);
		this.updatePistonState(world, pos, state);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		log.debug("onBlockAdded : ", pos);
		if (world.getTileEntity(pos) != null) {
			this.updatePistonState(world, pos, state);
		}
		return;
	}

	public void updatePistonState(World world, BlockPos pos) {
		this.updatePistonState(world, pos, world.getBlockState(pos));
	}
	public void updatePistonState(World world, BlockPos pos, IBlockState state) {
		this.updatePistonState(world, pos, state, true);
		
	}
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	public void updatePistonState(World world, BlockPos pos, IBlockState state, boolean forceAdd) {
		
		
		if (this.isRetract(world, pos)) {
			log.debug("Piston work for retrac");
			return;
		}
		
		this.cleanBlockMoving(world, pos);
		
		EnumFacing facing = state.getValue(FACING);
		boolean powered = this.isIndirectlyPowered(world, pos, state.getValue(FACING));
		int lenghtOpened = 0;
		int currentOpened = 0;
		if (powered) {
			lenghtOpened = this.getMaximalOpenedLenght(world, pos, facing);	
		}
		
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsPiston) {
			currentOpened = ((TileEntityMorePistonsPiston)te).currentOpened;
		}
		
		log.debug("updatePistonState : ", pos, "state"+state, "currentOpened="+currentOpened, "powered="+powered, "lenghtOpened="+lenghtOpened);
		
		
		IBlockState state2 = this.getStateFromMeta(this.getMetaFromState(state));
		if (lenghtOpened > 0) {
			state2 = state2.withProperty(EXTENDED, true);
		} else {
			state2 = state2.withProperty(EXTENDED, false);
		}
		if (forceAdd || this.getMetaFromState(state) != this.getMetaFromState(state2)) {
			world.setBlockState(pos, state2, 3);
		}
		
		
		if(lenghtOpened <= 0 && state.getValue(EXTENDED)) {
			state = state.withProperty(EXTENDED, false);
			world.setBlockState(pos, state, 3);
		}
		
		if (currentOpened != lenghtOpened) {
			
			if (!world.isRemote) {
				if (this.isRunning (world, pos)) {
					log.debug("Piston already running");
					return;
				}
				this.setRunning(world, pos, true);
				
				world.addBlockEvent(pos, this, 0, lenghtOpened);
			}
		}
	}

	@Override
	public boolean onBlockEventReceived(World world, BlockPos pos, IBlockState state, int event, int lenghtOpened) {
		
		if (event != 0) {
			return false;
		}

		EnumFacing facing = state.getValue(FACING);
		
		int currentOpened = 0;
		boolean onOpen = false;
		boolean onClose = false;

		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntityMorePistonsPiston) {
			TileEntityMorePistonsPiston tileEntityPiston = (TileEntityMorePistonsPiston)te;
			
			currentOpened = tileEntityPiston.currentOpened;
			tileEntityPiston.currentOpened = lenghtOpened;
			tileEntityPiston.markDirty();
			
			if (currentOpened == lenghtOpened) {
				log.debug("Le piston reste immobile : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
			} else if (currentOpened < lenghtOpened) {
				log.debug("Le piston s'ouvre : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
				this.extend(world, pos, facing, currentOpened, lenghtOpened);
				onOpen = true;
			} else {
				log.debug("Le piston se ferme : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
				this.retract(world, pos, facing, currentOpened, lenghtOpened);
				onClose = true;
			}
		}
		
		if (onOpen) {
			// On joue le son
			log.debug("Play sound tile.piston.out");
			world.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
		}
	
		if (onClose) {
			// On joue le son
			log.debug("Play sound tile.piston.in");
			world.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
		}
		
		this.setRunning(world, pos, false);
		
		if (onOpen || onClose) {
			world.notifyNeighborsOfStateChange(pos, this);
			world.markBlockForUpdate(pos);;
		}
		
		this.setRunning(world, pos, false);
		
//		int lenghtOpened = 0;
//		EnumFacing facing = state.getValue(FACING);
//		boolean powered  = this.isIndirectlyPowered(world, pos, facing);
//		if (powered) {
//			lenghtOpened = this.getMaximalOpenedLenght(world, pos, facing);
//		}
//		
//		log.debug("onBlockEventReceived : ",pos, "facing="+facing, "lenghtOpened="+lenghtOpened, "remote="+world.isRemote);
//		
//		boolean extendOpen = false;
//		boolean extendClose = false;
//		
//		TileEntity te = world.getTileEntity(pos);
//		if (te instanceof TileEntityMorePistonsPiston) {
//			TileEntityMorePistonsPiston tileEntityPiston = (TileEntityMorePistonsPiston)te;
//			
//			if (lenghtOpened > 0) {
//				
//				log.debug("demande d'ouverture : ", pos);
//				
//				if (currentOpened == lenghtOpened) {
//					
//					log.debug("Le piston reste immobile : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
//					
//				} else if (currentOpened < lenghtOpened) {
//					
//					log.debug("Le piston s'ouvre : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
//					
//					world.setBlockState(pos, state.withProperty(EXTENDED, true), 2);
//					tileEntityPiston.currentOpened = lenghtOpened;
//					this.extend(world, pos, facing, currentOpened, lenghtOpened);
//					extendOpen = true;
//					
//				} else {
//					
//					log.debug("Le piston se retracte : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
//					
//					tileEntityPiston.currentOpened = lenghtOpened;
//					this.retract(world, pos, facing, currentOpened, lenghtOpened);
//					extendClose = true;
//					
//				}
//			} else {
//				log.debug("demande de fermeture : ", pos);
//				
//				if (currentOpened == 0) {
//					log.debug("Le piston reste immobile : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
//					world.setBlockState(pos, state.withProperty(EXTENDED, false), 0);
//				} else {
//					log.debug("Le piston se ferme : ", pos, "currentOpened="+currentOpened, "remote="+world.isRemote);
//					
//					tileEntityPiston.currentOpened = lenghtOpened;
//					this.retract(world, pos, facing, currentOpened, lenghtOpened);
//					extendClose = true;
//				}
//				
//			}
//		}
//		
//		
//		if (extendOpen) {
//			// On joue le son
//			world.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
//		}
//
//		if (extendClose) {
//			// On joue le son
//			world.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
//		}
//		
//		this.setRunning(world, pos, false);
//		
//		if (extendOpen || extendClose) {
//			world.notifyNeighborsOfStateChange(pos, this);
//		}
//		
		return true;
	}
	
	////////////////////
	// Etat du piston //
	////////////////////
	
	public int getStickySize (IBlockAccess world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te).stickySize;
		}
		return 1;
	}
	
	public boolean isRunning (World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te).running;
		}
		return false;
	}
	
	public void setRunning (World world, BlockPos pos, boolean running) {
		
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			((TileEntityMorePistonsPiston)te).running = running;
		}
	}
	
	protected boolean isIndirectlyPowered (World worldIn, BlockPos pos, EnumFacing facing) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (enumfacing != facing && worldIn.isSidePowered(pos.offset(enumfacing), enumfacing)) {
				return true;
			}
		}
		
		if (worldIn.isSidePowered(pos, EnumFacing.DOWN)) {
			return true;
		} else {
			BlockPos blockpos = pos.up();
			
			for (EnumFacing enumfacing1 : EnumFacing.values()) {
				if (enumfacing1 != EnumFacing.DOWN && worldIn.isSidePowered(blockpos.offset(enumfacing1), enumfacing1)) {
					return true;
				}
			}
			
			return false;
		}
	}
	

	/**
	* Caclcule la longueur d'ouverture d'un piston
	* @param World world
	* @param int x
	* @param int y
	* @param int z
	* @param int orientation
	* @return int
	*/
	public int getMaximalOpenedLenght (World world, BlockPos pos, EnumFacing facing) {
		return this.getMaximalOpenedLenght(world, pos, facing, this.getLengthInWorld(world, pos, facing));
	}
	
	/**
	* Caclcule la longueur d'ouverture d'un piston
	* @param World world
	* @param int x
	* @param int y
	* @param int z
	* @param int orientation
	* @return int
	*/
	public int getMaximalOpenedLenght (World world, BlockPos pos, EnumFacing facing, int maxlenght) {

		
		BlockPos oPos = new BlockPos(pos);
		
		log.debug("getCMaximalOpenedLenght : "+pos+ " maxlenght="+maxlenght);
		
		int lenght = 0;
		
		for (int i = 0; i < maxlenght; i++) {
			
			pos = pos.offset(facing);
			
			if (pos.getY() >= 255) {
				log.debug("getMaximalOpenedLenght : "+pos+ " y>=255");
				break;
			}
			
			IBlockState state = world.getBlockState(pos);
			
			
			if (
				state != null && 
				(
					state.getBlock() instanceof BlockPistonMoving || 
					state.getBlock() instanceof BlockMorePistonsMoving
				)
			) {
				log.debug("getMaximalOpenedLenght : "+pos+ " find PistonMoving");
				
				TileEntity te = world.getTileEntity(pos);
				
				if (
					state.getBlock() instanceof BlockMorePistonsMoving &&
					te instanceof TileEntityMorePistonsMoving && 
					((TileEntityMorePistonsMoving)te).storedState != null &&
					((TileEntityMorePistonsMoving)te).storedState.getBlock() != null &&
					((TileEntityMorePistonsMoving)te).storedState.getBlock() instanceof BlockMorePistonsExtension &&
					((TileEntityMorePistonsMoving)te).positionPiston.equals(oPos)
				) {

					log.debug("getMaximalOpenedLenght : "+pos+ " is Extention");
					
					return lenght + 1;
				}
				log.debug("getMaximalOpenedLenght : "+pos+ " clean and continue");
				
				this.cleanBlockMoving(world, pos);
				state = world.getBlockState(pos);
			}
			log.debug("getMaximalOpenedLenght : "+pos);
			if (!(this.isEmptyBlockState(state)) && !this.isRodInOrientation(world, pos, state, facing)) {
				lenght += this.getMoveBlockOnDistance (maxlenght - i, world, state, pos, facing);
				break;
			}
			lenght++;
		}
		
		log.debug("getMaximalOpenedLenght : "+pos+ " : lenght="+lenght);
		
		return lenght;
	}
	
	/**
	 * Test si le block n'est pas un route de piston et dans l 'orientation 
	 * @param id
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @return
	 */
	private boolean isRodInOrientation (World world, BlockPos pos, IBlockState state, EnumFacing facing) {
		Block block = state != null ? state.getBlock() : null;
		if (
			block instanceof BlockMorePistonsExtension ||
			block instanceof BlockPistonExtension ||
			block instanceof BlockMorePistonsRod
		) {
			return facing == world.getBlockState(pos).getValue(FACING);
		}
		return false;
	}
	
	/**
	 * Test if this block is movable
	 * @param block
	 * @return
	 */
	public boolean isEmptyBlockState(IBlockState state) {
		Block block = state != null ? state.getBlock() : null;
		return
			block == null ||
			block instanceof BlockAir ||
			block.getMobilityFlag() == 1 ||
			block instanceof BlockLiquid ||
			block instanceof IFluidBlock
		;
	}
	
	/**
	 * Regarde si on peu déplacé un piston sur la distance voulu
	 * @param distance
	 * @param world
	 * @param state
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @return
	 */
	private int getMoveBlockOnDistance (int distance, World world, IBlockState state, BlockPos pos, EnumFacing facing) {
		return this.getMoveBlockOnDistance(distance, world, state, pos, facing, 1);
	}

	/**
	 * Regarde si on peu déplacé un piston sur la distance voulu
	 * @param distance
	 * @param world
	 * @param id
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param nbMoved
	 * @return
	 */
	private int getMoveBlockOnDistance (int distance, World world, IBlockState state, BlockPos pos, EnumFacing facing, int nbMoved) {
		
		if (nbMoved == this.getMaxBlockMove () || !this.isMovableBlockState(state, world, pos)) {
//			log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " Bloquer nbMoved="+nbMoved);
			return 0;
		}
		
		int walking = 0;
		
		for (int i = 0; i < distance; i++) {
			pos = pos.offset(facing);

			if (pos.getY() >= 255) {
//				log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " y=>255");
				break;
			}
			
			IBlockState stateNext = world.getBlockState(pos);
//			log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " blockNext="+blockNext);
			
			if (this.isEmptyBlockState(stateNext)) {
				walking++;
			} else {
				int moving = this.getMoveBlockOnDistance(distance - i, world, stateNext, pos, facing, nbMoved + 1);
				walking += moving;
				break;
			}
		}
		
//		log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " walking="+walking+ " nbMoved="+nbMoved);
		return walking;
	}
	
	/**
	 * Test if this block is movable
	 * @param id
	 * @return
	 */
	public boolean isMovableBlockState(IBlockState state, World world, BlockPos pos) {
		
		boolean isPistonClosed = BlockMorePistonsBase.isPiston (state);
		if (isPistonClosed) {
			isPistonClosed = !state.getValue(EXTENDED);
		}
		return
			this.isEmptyBlockState (state) ||
			isPistonClosed ||
			(
				state.getBlock() != Blocks.obsidian &&
				state.getBlock().getMobilityFlag() != 2 &&
				!(state.getBlock() instanceof BlockMorePistonsRod) &&
				!(state.getBlock() instanceof BlockPistonExtension) &&
				!(state.getBlock() instanceof BlockMorePistonsExtension) &&
				!(state.getBlock() instanceof BlockMorePistonsMoving) &&
				world.getTileEntity(pos) == null &&
				state.getBlock().getBlockHardness(world, pos) != -1.0F
			);
	}
	
	/**
	 * Test si on a un piston
	 * @param id
	 * @return
	 */
	public static boolean isPiston (IBlockState state) {
		Block block = state != null ? state.getBlock() : null;
		return block instanceof BlockPistonBase || block instanceof BlockMorePistonsBase;
	}
//	
	/////////////////////////
	// Ouverture du piston //
	/////////////////////////
	
	/**
	 * Drop les élemebnt avec la mobilité de 1
	 * @param id
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	protected void dropMobilityFlag1 (IBlockState state, World world, BlockPos pos) {
		// Drop les élements légés (fleurs, leviers, herbes ..)
		if (state != null && state.getBlock() != null && state.getBlock() != Blocks.air && state.getBlock().getMobilityFlag() == 1) {
			float chance = (state.getBlock() instanceof BlockSnow ? -1.0f : 1.0f);
			
			state.getBlock().dropBlockAsItemWithChance(world, pos, state, chance, 0);
			world.setBlockToAir(pos);
		}
	}
	
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened) {
		return this.listBlockExtend(world, pos, facing, currentOpened, lenghtOpened, true);
	}
	 
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened, boolean removeAfter) {
		
		BlockPos posExtension = pos.offset(facing, currentOpened);
		
		ArrayList<EMoveInfosExtend> infosExtend = new ArrayList<EMoveInfosExtend>();
		ArrayList<EMoveInfosExtend> dropList    = new ArrayList<EMoveInfosExtend>();
		
		int size = lenghtOpened - currentOpened;
		
		for (int i = 0; i < (lenghtOpened + this.getMaxBlockMove ()) && size > 0; i++) {
			
			posExtension = posExtension.offset(facing);
			
			IBlockState state = world.getBlockState(posExtension);
			TileEntity  te = world.getTileEntity(posExtension);
			
			// Drop les élements légés (fleurs, leviers, herbes ..)
			if (state != null && state.getBlock() != Blocks.air && state.getBlock().getMobilityFlag() == 1) {
				dropList.add(new EMoveInfosExtend(state, te, posExtension, 0));
			}
			
			if (this.isEmptyBlockState(state)) {
				
				infosExtend.add(new EMoveInfosExtend());
				size--;
				
			} else if (!this.isMovableBlockState(state, world, posExtension)) {
				break;
			} else {
				infosExtend.add(new EMoveInfosExtend(state, te, posExtension, size));

				if (removeAfter) {
					world.setTileEntity(posExtension, null);
					world.setBlockState(posExtension, Blocks.air.getDefaultState(), 0);
				}
			}
			
		}
		
		Collections.reverse(infosExtend);
		
		for (EMoveInfosExtend info : dropList) {
			this.dropMobilityFlag1 (info.state, world, info.position);
		}
		
		return infosExtend;
	}
	
	
	protected ArrayList<EMoveInfosExtend> listBlockRetract (World world, BlockPos pos,  EnumFacing facing, int lenghtClose, int stickySize) {
		return this.listBlockRetract(world, pos, facing, lenghtClose, stickySize, true);
	}
	
	protected ArrayList<EMoveInfosExtend> listBlockRetract (World world, BlockPos pos, EnumFacing facing, int lenghtClose, int stickySize, boolean removeAfter) {
		ArrayList<EMoveInfosExtend> infosRetract = new ArrayList<EMoveInfosExtend>();
		
		for (int i = 1; i <= stickySize; i++) {
			BlockPos pos1 = pos.offset(facing, lenghtClose + i);
			
			this.cleanBlockMoving(world, pos1);
			
			IBlockState state     = world.getBlockState(pos1);
			TileEntity tileEntity = this.cloneTileEntity(world.getTileEntity(pos1));

			if (!isEmptyBlockState(state) && isMovableBlockState(state, world, pos1)) {
				infosRetract.add(new EMoveInfosExtend (
					state,
					tileEntity,
					pos1,
					lenghtClose
				));
				
				if (removeAfter) {
					world.setTileEntity(pos1, null);
					world.setBlockState (pos1, Blocks.air.getDefaultState(), 0);
				}
			} else {
				break;
			}
		}
		
		return infosRetract;
	}
	
	public static boolean cleanBlockMoving(World world, BlockPos pos) {
		TileEntity oldTe = world.getTileEntity(pos);
		if (oldTe instanceof TileEntityMorePistonsMoving) {
			((TileEntityMorePistonsMoving)oldTe).setBlockFinalMove();
			return true;
		}
		if (oldTe instanceof TileEntityPiston) {
			((TileEntityPiston)oldTe).clearPistonTileEntity();
			return true;
		}
		return false;
	}
//	
	public static boolean isRetract(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TileEntityMorePistonsPiston) {
			te = ((TileEntityMorePistonsPiston)te).getTileEntityMoving();
		}
		if (te instanceof TileEntityMorePistonsMoving) {
			return !((TileEntityMorePistonsMoving)te).extending;
		}
		return false;
	}
	
	public static TileEntity cloneTileEntity(TileEntity te) {
		if (te == null) {
			return null;
		}
		TileEntity teCopy = te;
		try {
			NBTTagCompound nbtTagCompound = new NBTTagCompound();
			te.writeToNBT(nbtTagCompound);
			teCopy = TileEntity.createAndLoadEntity(nbtTagCompound);
			teCopy.setWorldObj(te.getWorld());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teCopy;
	}
	
	protected void retracSticky(World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpenned) {

		BlockPos pos2  = pos.offset(facing, lenghtOpenned);
		
		ArrayList<EMoveInfosExtend> infosRetract = this.listBlockRetract(world, pos2, facing, currentOpened - lenghtOpenned, this.getStickySize(world, pos));
		
		for (EMoveInfosExtend infos : infosRetract) {
			if (infos.state != null && infos.position != null) {
				
				this.createMoving(
					world,
					pos,
					infos.position.offset(facing, -infos.move),
					infos.state,
					infos.tileEntity,
					facing,
					0,
					infos.move,
					false,
					false,
					infos.waitFinish
				);
				
			}
		}
		
		for (EMoveInfosExtend infos : infosRetract) {
			world.notifyNeighborsOfStateChange(infos.position, world.getBlockState(infos.position).getBlock());
			world.markBlockForUpdate(infos.position);
		} 
	}
	
	

	protected void retract(World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpenned) {
		
		//Déplace avec une animation l'extention du piston
		log.debug("Create PistonMoving : "+pos, "facing="+facing, "currentOpened="+currentOpened, "lenghtOpenned="+lenghtOpenned);

		BlockPos posOri  = pos.offset(facing, currentOpened);
		BlockPos posDest = pos.offset(facing, lenghtOpenned);
		
		this.cleanBlockMoving(world, posOri);
		
		TileEntity teCopy = this.cloneTileEntity(world.getTileEntity(pos));
		IBlockState state = this.getDefaultState()
			.withProperty(FACING, facing)
		;
		if (!(pos.equals(posDest))) {
			state = ModBlocks.blockPistonRod.getDefaultState()
				.withProperty(BlockPistonExtension.FACING, facing)
			;
		}
		
		this.createMoving(
			world,
			pos,
			posDest,
			state,
			teCopy,
			facing,
			0,
			currentOpened - lenghtOpenned,
			false,
			true,
			0
		);
		world.setBlockToAir (posOri);
		
		if (this.isSticky) {
			this.retracSticky(world, pos, facing, currentOpened, lenghtOpenned);
		}
	}
	
	protected void extend(World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened) {

		world.notifyNeighborsOfStateChange(pos, null);

		
		// list des blocks
		ArrayList<EMoveInfosExtend> infosExtend = this.listBlockExtend(world, pos, facing, currentOpened, lenghtOpened);
		
		// Rod

		int i = 0;
		for (i = 1; i <= currentOpened; i++) {
			BlockPos posRod = new BlockPos(
				pos.getX() + facing.getFrontOffsetX() * i,
				pos.getY() + facing.getFrontOffsetY() * i,
				pos.getZ() + facing.getFrontOffsetZ() * i
			);
			world.notifyNeighborsOfStateChange(posRod, null);
		}
		
		for (; i < lenghtOpened; i++) {
			BlockPos posRod = new BlockPos(
				pos.getX() + facing.getFrontOffsetX() * i,
				pos.getY() + facing.getFrontOffsetY() * i,
				pos.getZ() + facing.getFrontOffsetZ() * i
			);
			world.setBlockState (posRod, ModBlocks.blockPistonRod.getDefaultState().withProperty(BlockPistonExtension.FACING, facing), 2);
			world.setTileEntity(posRod, new TileEntityMorePistonsRod(pos));
			world.notifyNeighborsOfStateChange(posRod, null);
		}
		
		this.moveBlockExtend(infosExtend, world, pos, facing, currentOpened, lenghtOpened);
		
		for (EMoveInfosExtend infos : infosExtend) {
			world.notifyNeighborsOfStateChange(infos.position, world.getBlockState(pos).getBlock());
			world.markBlockForUpdate(infos.position);
		}
	}
	
	
	protected void moveBlockExtend (ArrayList<EMoveInfosExtend> infosExtend, World world, BlockPos pos, EnumFacing facing, int currentOpened, int lenghtOpened) {
		
		BlockPos posExtension;
		
		for (EMoveInfosExtend infos : infosExtend) {
			
			if (infos.state != null && infos.state.getBlock() != null && infos.state.getBlock() != Blocks.air && infos.state.getBlock() != Blocks.piston_extension) {
				
				posExtension = infos.position.offset(facing, infos.move);
				
				//Déplace avec une animation les blocks
				this.createMoving(
					world,
					pos,
					posExtension,
					infos.state,
					infos.tileEntity,
					facing, 
					currentOpened,
					infos.move,
					true,
					false,
					infos.waitFinish
				);
			}
		}
		
		posExtension = pos.offset(facing, currentOpened-1);
		
		for (int i = currentOpened; i < lenghtOpened; i++) {
			
			posExtension = posExtension.offset(facing);
			
			IBlockState s = world.getBlockState(posExtension);
			
			if (
				s == null ||
				s.getBlock() == null ||
				s.getBlock() instanceof BlockAir ||
				s.getBlock() instanceof BlockMorePistonsExtension
			) {
				world.setBlockState (posExtension, ModBlocks.blockPistonRod.getDefaultState().withProperty(BlockPistonExtension.FACING, facing), 2);
				world.setTileEntity(posExtension, new TileEntityMorePistonsRod(pos));
			}
			
		}
		
		posExtension = posExtension.offset(facing);
		
		//Déplace avec une animation l'extention du piston
		log.debug("Create PistonMoving : "+posExtension, "facing="+facing, "currentOpened="+currentOpened, "lenghtOpened="+lenghtOpened);
		
		this.createMoving(
			world,
			pos,
			posExtension,
			this.getBlockExtention().getDefaultState()
				.withProperty(BlockPistonExtension.FACING, facing)
				.withProperty(BlockPistonExtension.TYPE, this.getEnumPistonType())
			,
			null,
			facing,
			currentOpened,
			lenghtOpened,
			true,
			false,
			0
		);
	}
	

	protected void createMoving(
			World world,
			BlockPos pistonPos,
			BlockPos dest,
			IBlockState state, 
			TileEntity teCopy,
			EnumFacing facing,
			int start,
			int lenghtOpened,
			boolean extending,
			boolean root, 
			int waitFinish
		) {
		
		world.setBlockState(
			dest,
			ModBlocks.blockPistonMoving.getDefaultState()
				.withProperty(BlockPistonExtension.FACING, facing)
				.withProperty(BlockPistonExtension.TYPE, this.getEnumPistonType())
			, 
			0
		);
		TileEntityMorePistonsMoving teExtension = new TileEntityMorePistonsMoving(
			state, 
			extending,
			start,
			lenghtOpened,
			pistonPos,
			root,
			waitFinish
		);
		teExtension.subTe = teCopy;
		world.setTileEntity(dest, teExtension);
		
		TileEntity te = world.getTileEntity(pistonPos);
		if (root || (state != null && state.getBlock() instanceof BlockMorePistonsExtension)) {
			if (te instanceof TileEntityMorePistonsPiston) {
				((TileEntityMorePistonsPiston)te).extentionPos = dest;
			}
			if (te instanceof TileEntityMorePistonsMoving) {
				((TileEntityMorePistonsMoving) te).getPistonOriginTE().extentionPos = dest;
			}
		}
		world.notifyNeighborsOfStateChange(dest, ModBlocks.blockPistonMoving);
	}
	
}
