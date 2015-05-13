package com.gollum.morepistons.common.block;

import static com.gollum.morepistons.ModMorePistons.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.RotationHelper;
import net.minecraftforge.fluids.IFluidBlock;

import com.gollum.core.common.blocks.IBlockDisplayInfos;
import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.client.ClientProxyMorePistons;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;
import com.gollum.morepistons.inits.ModBlocks;
import com.gollum.morepistons.inits.ModCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsBase extends HBlockContainer implements IBlockDisplayInfos {
	
	protected static class EMoveInfosExtend {
		
		public Block block = null;
		public int metadata = 0;
		public Integer3d position = new Integer3d();
		public TileEntity tileEntity = null;
		public int move = 0;
		public EMoveInfosExtend() {}
		
		public EMoveInfosExtend(Block block, int metadata, TileEntity tileEntity, Integer3d position, int move) {
			this.block      = block;
			this.metadata   = metadata;
			this.position   = position;
			this.tileEntity = BlockMorePistonsBase.cloneTileEntity(tileEntity);
			this.move       = move;
		}
	}
	
	protected boolean isSticky;
	private   int     length = 1;
	
	protected Icon iconTop;
	protected Icon iconOpen;
	protected Icon iconBottom;
	
	protected String suffixTop    = "_top";
	protected String suffixSticky = "_sticky";
	protected String suffixOpen   = "_open";
	protected String suffixBottom  = "_bottom";
	protected String suffixSide   = "_side";
	
	public BlockMorePistonsBase(int id, String registerName, boolean isSticky) {
		super(id, registerName, Material.piston);
		
		this.isSticky = isSticky;
		this.setStepSound(soundStoneFootstep);
		this.setHardness(0.5F);
		this.setCreativeTab(ModCreativeTab.morePistonsTabs);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1) {
		return new TileEntityMorePistonsPiston();
	}
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public BlockMorePistonsBase setLength(int length) {
		this.length = length;
		return this;
	}
	
	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
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
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public BlockMorePistonsExtension getBlockExtention () {
		return ModBlocks.blockPistonExtention;
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	/**
	 * Nom d'enregistrement du mod
	 */
	@Override
	public String getTextureKey() {
		return super.getTextureKey().replace("sticky", "");
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		
		if (helper.vanillaTexture) {
			super.registerIcons(iconRegister);
			return;
		};
		
		this.registerIconsTop   (iconRegister);
		this.registerIconsOpen  (iconRegister);
		this.registerIconsBottom(iconRegister);
		this.registerIconsSide  (iconRegister);
		
	}
	protected void registerIconsTop   (IconRegister iconRegister) { this.iconTop    = helper.loadTexture(iconRegister, "top" + (this.isSticky ? suffixSticky : ""), true); }
	protected void registerIconsOpen  (IconRegister iconRegister) { this.iconOpen   = helper.loadTexture(iconRegister, suffixOpen);   }
	protected void registerIconsBottom(IconRegister iconRegister) { this.iconBottom = helper.loadTexture(iconRegister, suffixBottom); }
	protected void registerIconsSide  (IconRegister iconRegister) { this.blockIcon  = helper.loadTexture(iconRegister, suffixSide);   }
	
	@Override
	public Icon getIcon(int side, int metadata) {
		
		if (helper.vanillaTexture) return super.getIcon(side, metadata);
		
		int orientation = BlockPistonBase.getOrientation(metadata);
		if (orientation > 5) {
			return this.iconTop;
		}
		if (side == orientation) {
			if (
				(BlockPistonBase.isExtended(metadata)) ||
				(this.minX > 0.0D) || (this.minY > 0.0D) || (this.minZ > 0.0D) ||
				(this.maxX < 1.0D) || (this.maxY < 1.0D) || (this.maxZ < 1.0D)
			) {
				return this.iconOpen;
			}
			
			return this.iconTop;
		}
		
		return side != Facing.oppositeSide[orientation] ? this.blockIcon : this.iconBottom;
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getPistonExtensionTexture() {
		if (helper.vanillaTexture) return this.isSticky ? Block.pistonStickyBase.getPistonExtensionTexture() : Block.pistonBase.getPistonExtensionTexture();
		return this.iconTop;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return ClientProxyMorePistons.idMorePistonsBaseRenderer;
	}
	
	@Override
	public String displayDebugInfos(World world, int x, int y, int z) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
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
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		
		if (BlockPistonBase.isExtended(metadata)) {
			switch (BlockPistonBase.getOrientation(metadata)) {
				case 0:
					this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 1:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
					break;
				case 2:
					this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
					break;
				case 3:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
					break;
				case 4:
					this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 5:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
				}
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		
		log.debug("onBlockDestroyedByPlayer : "+x+", "+y+", "+z);
		
		int orientation = BlockPistonBase.getOrientation(metadata);
		Block block = ModBlocks.blockPistonRod;
		while (block instanceof BlockMorePistonsRod) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			BlockMorePistonsBase.cleanBlockMoving(world, x, y, z);
			int id = world.getBlockId(x, y, z);
			block = Block.blocksList[id];
			
			if (
				BlockPistonBase.getOrientation(world.getBlockMetadata(x, y, z)) == orientation &&
				(
					block instanceof BlockMorePistonsRod || 
					block instanceof BlockMorePistonsExtension
				)
			) {
				world.destroyBlock(x, y, z, false);
			}
			
		}
	}
	
	/**
	 * Called when the block is placed in the world. Envoie un event qunad on
	 * place un block sur le monde
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		int orientation = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
		world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
		
		log.debug("onBlockPlacedBy : "+x+", "+y+", "+z);
		
		if (!world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		
		log.debug("onNeighborBlockChange : "+x+", "+y+", "+z);
		
		if (!world.isRemote && !this.isRunning (world, x, y , z)) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		log.debug("onBlockAdded : "+x+", "+y+", "+z);
		
		if (!world.isRemote && world.getBlockTileEntity(x, y, z) == null) {
			this.updatePistonState(world, x, y, z);
		}
		return;
	}
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	public void updatePistonState(World world, int x, int y, int z) {
		if (world.isRemote) {
			return;
		}
		
		int metadata = world.getBlockMetadata(x, y, z);
		
		if (this.isRunning (world, x, y , z)) {
			log.debug("Piston already running");
			return;
		}
		
		if (metadata == 7) {
			return;
		}
		if (this.isRetract(world, x, y, z)) {
			log.debug("Piston work for retrac");
			return;
		}
		
		this.cleanBlockMoving(world, x, y, z);
		
		int     orientation   = BlockPistonBase.getOrientation(metadata);
		boolean powered       = this.isIndirectlyPowered(world, x, y, z, orientation);
		int     currentOpened = 0;

		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			currentOpened = ((TileEntityMorePistonsPiston)te).currentOpened;
		}
		this.setRunning(world, x, y, z, true);
		
		log.debug("updatePistonState : ", x, y, z, "orientation="+orientation, "powered="+powered, "currentOpened="+currentOpened);
		
		if (powered) {
			world.setBlockMetadataWithNotify (x, y, z, orientation | 0x8, 0);
		} else {
			world.setBlockMetadataWithNotify (x, y, z, orientation, 0);
		}
		world.addBlockEvent(x, y, z, this.blockID, 0, currentOpened);
	}
	
	/**
	* Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	* entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	*/
	public boolean onBlockEventReceived(World world, int x, int y, int z, int event, int currentOpened) {
		
		if (event != 0) {
			return false;
		}
		
		int lenghtOpened = 0;
		int metadata     = world.getBlockMetadata(x, y, z);
		int orientation  = BlockPistonBase.getOrientation(metadata);
		boolean powered  = this.isIndirectlyPowered(world, x, y, z, orientation);
		if (powered) {
			lenghtOpened = this.getMaximalOpenedLenght(world, x, y, z, orientation);
		}
		
		log.debug("onBlockEventReceived : ",x, y, z, "orientation="+orientation, "lenghtOpened="+lenghtOpened, "remote="+world.isRemote);
		
		boolean extendOpen = false;
		boolean extendClose = false;
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsPiston) {
			TileEntityMorePistonsPiston tileEntityPiston = (TileEntityMorePistonsPiston)te;
			
			if (lenghtOpened > 0) {
				
				log.debug("demande d'ouverture : ",x, y, z);
				
				if (currentOpened == lenghtOpened) {
					
					log.debug("Le piston reste immobile : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					
				} else if (currentOpened < lenghtOpened) {
					
					log.debug("Le piston s'ouvre : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					
					world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
					tileEntityPiston.currentOpened = lenghtOpened;
					this.extend(world, x, y, z, orientation, currentOpened, lenghtOpened);
					extendOpen = true;
					
				} else {
					
					log.debug("Le piston se retracte : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					
					tileEntityPiston.currentOpened = lenghtOpened;
					this.retract(world, x, y, z, orientation, currentOpened, lenghtOpened);
					extendClose = true;
					
				}
			} else {
				log.debug("demande de fermeture : ",x, y, z);
				
				if (currentOpened == 0) {
					log.debug("Le piston reste immobile : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					world.setBlockMetadataWithNotify(x, y, z, orientation, 0);
				} else {
					log.debug("Le piston se ferme : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					
					tileEntityPiston.currentOpened = lenghtOpened;
					this.retract(world, x, y, z, orientation, currentOpened, lenghtOpened);
					extendClose = true;
				}
				
			}
		}
		

		if (extendOpen) {
			// On joue le son
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
		}

		if (extendClose) {
			// On joue le son
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
		}
		
		this.setRunning(world, x, y, z, false);
		
		if (extendOpen || extendClose) {
			world.notifyBlockOfNeighborChange(x, y, z, this.blockID);
		}
		
		return true;
	}
	
	////////////////////
	// Etat du piston //
	////////////////////
	
	public int getStickySize (IBlockAccess world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te).stickySize;
		}
		return 1;
	}
	
	public boolean isRunning (World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			return ((TileEntityMorePistonsPiston)te).running;
		}
		return false;
	}
	
	public void setRunning (World world, int x, int y, int z, boolean running) {
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving)te).getPistonOriginTE();
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			((TileEntityMorePistonsPiston)te).running = running;
		}
	}

	/**
	 * checks the block to that side to see if it is indirectly powered.
	 */
	protected boolean isIndirectlyPowered(World world, int x, int y, int z, int orientation) {
		if ((orientation != 0) && (world.getIndirectPowerOutput(x, y - 1, z, 0))) {
			return true;
		}
		if ((orientation != 1) && (world.getIndirectPowerOutput(x, y + 1, z, 1))) {
			return true;
		}
		if ((orientation != 2) && (world.getIndirectPowerOutput(x, y, z - 1, 2))) {
			return true;
		}
		if ((orientation != 3) && (world.getIndirectPowerOutput(x, y, z + 1, 3))) {
			return true;
		}
		if ((orientation != 5) && (world.getIndirectPowerOutput(x + 1, y, z, 5))) {
			return true;
		}
		if ((orientation != 4) && (world.getIndirectPowerOutput(x - 1, y, z, 4))) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y, z, 0)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y + 2, z, 1)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y + 1, z - 1, 2)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y + 1, z + 1, 3)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x - 1, y + 1, z, 4)) {
			return true;
		}
		boolean flag = world.getIndirectPowerOutput(x + 1, y + 1, z, 5);
		if (flag) {
			return true;
		}
		
		return false;
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
	public int getMaximalOpenedLenght (World world, int x, int y, int z, int orientation) {
		return this.getMaximalOpenedLenght(world, x, y, z, orientation, this.getLengthInWorld(world, x, y, z, orientation));
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
	public int getMaximalOpenedLenght (World world, int x, int y, int z, int orientation, int maxlenght) {

		int oX = x;
		int oY = y;
		int oZ = z;
		
		log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " maxlenght="+maxlenght);
		
		int lenght = 0;
		
		for (int i = 0; i < maxlenght; i++) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			if (y >= 255) {
				log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " y>=255");
				break;
			}
			
			int id = world.getBlockId(x, y, z);
			Block block = Block.blocksList[id];
			
			if (block instanceof BlockPistonMoving || block instanceof BlockMorePistonsMoving) {
				log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " find PistonMoving");
				
				TileEntity te = world.getBlockTileEntity(x, y, z);
				
				if (
					block instanceof BlockMorePistonsMoving &&
					te instanceof TileEntityMorePistonsMoving && 
					((TileEntityMorePistonsMoving)te).storedBlock instanceof BlockMorePistonsExtension &&
					((TileEntityMorePistonsMoving)te).positionPiston.equals(new Integer3d(oX, oY, oZ))
				) {

					log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " is Extention");
					
					return lenght + 1;
				}
				log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " clean and continue");
				
				this.cleanBlockMoving(world, x, y, z);
				id = world.getBlockId(x, y, z);
				block = Block.blocksList[id];
			}
			log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z);
			if (! (this.isEmptyBlock(block)) && !this.isRodInOrientation(block, world, x, y, z, orientation)) {
				lenght += this.getMoveBlockOnDistance (maxlenght - i, world, block, x, y, z, orientation);
				break;
			}
			lenght++;
		}
		
		log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " : lenght="+lenght);
		
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
	private boolean isRodInOrientation (Block block, World world, int x, int y, int z, int orientation) {
		
		if (
			block instanceof BlockMorePistonsExtension ||
			block instanceof BlockPistonExtension ||
			block instanceof BlockMorePistonsRod
		) {
			return orientation == BlockPistonBase.getOrientation(world.getBlockMetadata(x, y, z));
		}
		return false;
	}
	
	/**
	 * Test if this block is movable
	 * @param block
	 * @return
	 */
	public boolean isEmptyBlock(Block block) {
		return
			block == null ||
			block.getMobilityFlag() == 1 ||
			block instanceof BlockFluid ||
			block instanceof IFluidBlock
		;
	}
	
	/**
	 * Regarde si on peu déplacé un piston sur la distance voulu
	 * @param distance
	 * @param world
	 * @param block
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @return
	 */
	private int getMoveBlockOnDistance (int distance, World world, Block block, int x, int y, int z, int orientation) {
		return this.getMoveBlockOnDistance(distance, world, block, x, y, z, orientation, 1);
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
	private int getMoveBlockOnDistance (int distance, World world, Block block, int x, int y, int z, int orientation, int nbMoved) {
		
		if (nbMoved == this.getMaxBlockMove () || !this.isMovableBlock(block, world, x, y, z)) {
//			log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " Bloquer nbMoved="+nbMoved);
			return 0;
		}
		
		int walking = 0;
		
		for (int i = 0; i < distance; i++) {
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			

			if (y >= 255) {
//				log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " y=>255");
				break;
			}

			int idNext = world.getBlockId(x, y, z);
			Block blockNext = Block.blocksList[idNext];
//			log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " blockNext="+blockNext);
			
			if (this.isEmptyBlock(blockNext)) {
				walking++;
			} else {
				int moving = this.getMoveBlockOnDistance(distance - i, world, blockNext, x, y, z, orientation, nbMoved + 1);
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
	public boolean isMovableBlock(Block block, World world, int x, int y, int z) {
		
		boolean isPistonClosed = BlockMorePistonsBase.isPiston (block);
		if (isPistonClosed) {
			isPistonClosed = !BlockPistonBase.isExtended(world.getBlockMetadata(x, y, z));
		}
		
		return
			this.isEmptyBlock (block) ||
			isPistonClosed ||
			(
				block != Block.obsidian &&
				block.getMobilityFlag() != 2 &&
				!(block instanceof BlockMorePistonsRod) &&
				!(block instanceof BlockPistonExtension) &&
				!(block instanceof BlockMorePistonsExtension) &&
				!(block instanceof BlockMorePistonsMoving) &&
				world.getBlockTileEntity(x, y, z) == null &&
				block.getBlockHardness(world, x, y, z) != -1.0F
			);
	}
	
	/**
	 * Test si on a un piston
	 * @param id
	 * @return
	 */
	public static boolean isPiston (Block block) {
		return block instanceof BlockPistonBase || block instanceof BlockMorePistonsBase;
	}
	
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
	protected void dropMobilityFlag1 (Block block, int metadata, World world, int x, int y, int z) {
		// Drop les élements légés (fleurs, leviers, herbes ..)
		if (block != null && block.getMobilityFlag() == 1) {
			float chance = (block instanceof BlockSnow ? -1.0f : 1.0f);
			
			block.dropBlockAsItemWithChance(world, x, y, z, metadata, chance, 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened) {
		return this.listBlockExtend(world, x, y, z, orientation, currentOpened, lenghtOpened, true);
	}
	
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened, boolean removeAfter) {
		
		int xExtension = x + Facing.offsetsXForSide[orientation] * currentOpened;
		int yExtension = y + Facing.offsetsYForSide[orientation] * currentOpened;
		int zExtension = z + Facing.offsetsZForSide[orientation] * currentOpened;
		
		ArrayList<EMoveInfosExtend> infosExtend = new ArrayList<EMoveInfosExtend>();
		ArrayList<EMoveInfosExtend> dropList    = new ArrayList<EMoveInfosExtend>();
		
		int size = lenghtOpened - currentOpened;
		
		for (int i = 0; i < (lenghtOpened + this.getMaxBlockMove ()) && size > 0; i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
			int   id      = world.getBlockId(xExtension, yExtension, zExtension);
			Block block   = Block.blocksList[id];
			TileEntity te = world.getBlockTileEntity(xExtension, yExtension, zExtension);
			int metadata  = world.getBlockMetadata(xExtension, yExtension, zExtension);
			
			// Drop les élements légés (fleurs, leviers, herbes ..)
			if (block != null && block.getMobilityFlag() == 1) {
				dropList.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), 0));
			}
			
			if (this.isEmptyBlock(block)) {
				
				infosExtend.add(new EMoveInfosExtend());
				size--;
				
			} else if (!this.isMovableBlock(block, world, xExtension, yExtension, zExtension)) {
				break;
			} else {
				infosExtend.add(new EMoveInfosExtend(block, metadata, te, new Integer3d(xExtension, yExtension, zExtension), size));

				if (removeAfter) {
					world.setBlockTileEntity(xExtension, yExtension, zExtension, null);
					world.setBlock (xExtension, yExtension, zExtension, 0, 0, 0);
				}
			}
			
		}
		
		Collections.reverse(infosExtend);
		
		for (EMoveInfosExtend info : dropList) {
			this.dropMobilityFlag1(info.block, info.metadata, world, info.position.x, info.position.y, info.position.z);
		}
		
		return infosExtend;
	}
	
	
	protected ArrayList<EMoveInfosExtend> listBlockRetract (World world, int x, int y, int z, int orientation, int lenghtClose, int stickySize) {
		return this.listBlockRetract(world, x, y, z, orientation, lenghtClose, stickySize, true);
	}
	
	protected ArrayList<EMoveInfosExtend> listBlockRetract (World world, int x, int y, int z, int orientation, int lenghtClose, int stickySize, boolean removeAfter) {
		ArrayList<EMoveInfosExtend> infosRetract = new ArrayList<EMoveInfosExtend>();
		
		for (int i = 1; i <= stickySize; i++) {
			int xP1 = x + Facing.offsetsXForSide[orientation] * (lenghtClose + i);
			int yP1 = y + Facing.offsetsYForSide[orientation] * (lenghtClose + i);
			int zP1 = z + Facing.offsetsZForSide[orientation] * (lenghtClose + i);
			
			this.cleanBlockMoving(world, xP1, yP1, zP1);
			
			int id                 = world.getBlockId(xP1, yP1, zP1);
			Block block            = Block.blocksList[id];
			int metadata           = world.getBlockMetadata(xP1, yP1, zP1);
			TileEntity  tileEntity = this.cloneTileEntity(world.getBlockTileEntity(xP1, yP1, zP1));

			if (!isEmptyBlock(block) && isMovableBlock(block, world, xP1, yP1, zP1)) {
				infosRetract.add(new EMoveInfosExtend (
					block,
					metadata,
					tileEntity,
					new Integer3d(xP1, yP1, zP1),
					lenghtClose
				));
				
				if (removeAfter) {
					world.setBlockTileEntity(xP1, yP1, zP1, null);
					world.setBlock (xP1, yP1, zP1, 0, 0, 0);
				}
			} else {
				break;
			}
		}
		
		return infosRetract;
	}
	
	public static boolean cleanBlockMoving(World world, int x, int y, int z) {
		TileEntity oldTe = world.getBlockTileEntity(x, y, z);
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
	
	public static boolean isRetract(World world, int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsPiston) {
			te = ((TileEntityMorePistonsPiston)te).getBlockTileEntityMoving();
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
			teCopy.setWorldObj(te.getWorldObj());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return teCopy;
	}
	
	protected void retract(World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpenned) {
		
		//Déplace avec une animation l'extention du piston
		log.debug("Create PistonMoving : "+x, y, z, "orientation="+orientation, "currentOpened="+currentOpened, "lenghtOpenned="+lenghtOpenned);

		int xOri = x + Facing.offsetsXForSide[orientation] * currentOpened;
		int yOri = y + Facing.offsetsYForSide[orientation] * currentOpened;
		int zOri = z + Facing.offsetsZForSide[orientation] * currentOpened;
		
		int xDest = x + Facing.offsetsXForSide[orientation] * lenghtOpenned;
		int yDest = y + Facing.offsetsYForSide[orientation] * lenghtOpenned;
		int zDest = z + Facing.offsetsZForSide[orientation] * lenghtOpenned;
		
		this.cleanBlockMoving(world, xOri, yOri, zOri);
		
		TileEntity teCopy = this.cloneTileEntity(world.getBlockTileEntity(x, y, z));
		
		Block block = this;
		if (!(new Integer3d(x, y, z).equals(new Integer3d(xDest, yDest, zDest)))) {
			block = ModBlocks.blockPistonRod;
		}
		
		this.createMoving(
			world,
			new Integer3d(x, y, z),
			new Integer3d(xDest, yDest, zDest),
			block,
			orientation | (this.isSticky ? 0x8 : 0x0),
			teCopy,
			orientation,
			0,
			currentOpened - lenghtOpenned,
			false,
			true
		);
		world.setBlockToAir (xOri, yOri, zOri);
		
		if (this.isSticky) {
			this.retracSticky(world, x, y, z, orientation, currentOpened, lenghtOpenned);
		}
	}
	
	protected void retracSticky(World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpenned) {

		int x2 = x + Facing.offsetsXForSide[orientation]*lenghtOpenned;
		int y2 = y + Facing.offsetsYForSide[orientation]*lenghtOpenned;
		int z2 = z + Facing.offsetsZForSide[orientation]*lenghtOpenned;
		
		ArrayList<EMoveInfosExtend> infosRetract = this.listBlockRetract(world, x2, y2, z2, orientation, currentOpened - lenghtOpenned, this.getStickySize(world, x, y, z));

		for (EMoveInfosExtend infos : infosRetract) {
			if (infos.block != null && infos.position != null) {
				
				this.createMoving(
					world,
					new Integer3d(x, y, z),
					new Integer3d(
						infos.position.x - Facing.offsetsXForSide[orientation] * infos.move,
						infos.position.y - Facing.offsetsYForSide[orientation] * infos.move,
						infos.position.z - Facing.offsetsZForSide[orientation] * infos.move
					),
					infos.block,
					infos.metadata,
					infos.tileEntity,
					orientation,
					0,
					infos.move,
					false,
					false
				);
				
			}
		}
		
		for (EMoveInfosExtend infos : infosRetract) {
			world.notifyBlockChange(infos.position.x, infos.position.y, infos.position.z, world.getBlockId(infos.position.x, infos.position.y, infos.position.z));
			world.markBlockForUpdate(infos.position.x, infos.position.y, infos.position.z);
		}
	}
	
	protected void extend(World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened) {
		
		ArrayList<EMoveInfosExtend> infosExtend = this.listBlockExtend(world, x, y, z, orientation, currentOpened, lenghtOpened);
		
		this.moveBlockExtend(infosExtend, world, x, y, z, orientation, currentOpened, lenghtOpened);
		
		for (EMoveInfosExtend infos : infosExtend) {
			world.notifyBlockChange(infos.position.x, infos.position.y, infos.position.z, world.getBlockId(infos.position.x, infos.position.y, infos.position.z));
			world.markBlockForUpdate(infos.position.x, infos.position.y, infos.position.z);
		}
	}
	
	protected void moveBlockExtend (ArrayList<EMoveInfosExtend> infosExtend, World world, int x, int y, int z, int orientation, int currentOpened, int lenghtOpened) {
		
		int xExtension;
		int yExtension;
		int zExtension;
		
		for (EMoveInfosExtend infos : infosExtend) {
			
			if (infos.block != null && infos.block != Block.pistonExtension) {
				xExtension = infos.position.x + Facing.offsetsXForSide[orientation] * infos.move;
				yExtension = infos.position.y + Facing.offsetsYForSide[orientation] * infos.move;
				zExtension = infos.position.z + Facing.offsetsZForSide[orientation] * infos.move;
				
				//Déplace avec une animation les blocks
				this.createMoving(
					world,
					new Integer3d(x, y, z),
					new Integer3d(xExtension, yExtension, zExtension),
					infos.block,
					infos.metadata,
					infos.tileEntity,
					orientation, 
					currentOpened,
					infos.move,
					true,
					false
				);
			}
		}
		
		xExtension = x + Facing.offsetsXForSide[orientation] * (currentOpened-1);
		yExtension = y + Facing.offsetsYForSide[orientation] * (currentOpened-1);
		zExtension = z + Facing.offsetsZForSide[orientation] * (currentOpened-1);
		
		for (int i = currentOpened; i < lenghtOpened; i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
			int   id = world.getBlockId(xExtension, yExtension, zExtension);
			Block b  = Block.blocksList[id];
			
			if (
				b == null ||
				b instanceof BlockMorePistonsExtension
			) {
				world.setBlock (xExtension, yExtension, zExtension, ModBlocks.blockPistonRod.blockID, orientation, 2);
				world.setBlockTileEntity(xExtension, yExtension, zExtension, new TileEntityMorePistonsRod(new Integer3d(x, y, z)));
			}
			
		}
		
		xExtension += Facing.offsetsXForSide[orientation];
		yExtension += Facing.offsetsYForSide[orientation];
		zExtension += Facing.offsetsZForSide[orientation];
		
		//Déplace avec une animation l'extention du piston
		log.debug("Create PistonMoving : "+xExtension, yExtension, zExtension, "orientation="+orientation, "currentOpened="+currentOpened, "lenghtOpened="+lenghtOpened);
		
		this.createMoving(
			world,
			new Integer3d(x, y, z),
			new Integer3d(xExtension, yExtension, zExtension),
			this.getBlockExtention(),
			orientation | (this.isSticky ? 0x8 : 0x0),
			null,
			orientation,
			currentOpened,
			lenghtOpened,
			true,
			false
		);
	}

	protected void createMoving(World world, Integer3d pistonPos, Integer3d dest, Block block, int metadata, TileEntity teCopy,  int orientation, int start, int lenghtOpened, boolean extending, boolean root) {
		
		world.setBlock(dest.x, dest.y, dest.z, ModBlocks.blockPistonMoving.blockID, metadata, 2);
		TileEntityMorePistonsMoving teExtension = new TileEntityMorePistonsMoving(
			block,
			orientation, 
			extending,
			start,
			lenghtOpened,
			pistonPos,
			root
		);
		teExtension.subTe = teCopy;
		world.setBlockTileEntity(dest.x, dest.y, dest.z, teExtension);
		
		TileEntity te = world.getBlockTileEntity(pistonPos.x, pistonPos.y, pistonPos.z);
		if (root || block instanceof BlockMorePistonsExtension) {
			if (te instanceof TileEntityMorePistonsPiston) {
				((TileEntityMorePistonsPiston)te).extentionPos = new Integer3d(dest.x, dest.y, dest.z);
				world.notifyBlockOfNeighborChange(dest.x, dest.y, dest.z, this.blockID);
			}
			if (te instanceof TileEntityMorePistonsMoving) {
				((TileEntityMorePistonsMoving) te).getPistonOriginTE().extentionPos = new Integer3d(dest.x, dest.y, dest.z);
				world.notifyBlockOfNeighborChange(dest.x, dest.y, dest.z, ModBlocks.blockPistonMoving.blockID);
			}
		}
	}
	
	////////////
	// Others //
	////////////
	
	/**
	 * Rotate the block. For vanilla blocks this rotates around the axis passed in (generally, it should be the "face" that was hit).
	 * Note: for mod blocks, this is up to the block and modder to decide. It is not mandated that it be a rotation around the
	 * face, but could be a rotation to orient *to* that face, or a visiting of possible rotations.
	 * The method should return true if the rotation was successful though.
	 *
	 * @param worldObj The world
	 * @param x X position
	 * @param y Y position
	 * @param z Z position
	 * @param axis The axis to rotate around
	 * @return True if the rotation was successful, False if the rotation failed, or is not possible
	 */
	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
		return RotationHelper.rotateVanillaBlock(Block.pistonBase, worldObj, x, y, z, axis);
	}
}
