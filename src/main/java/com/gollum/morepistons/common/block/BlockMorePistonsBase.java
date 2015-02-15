package com.gollum.morepistons.common.block;

import static com.gollum.morepistons.ModMorePistons.log;

import java.util.ArrayList;
import java.util.List;

import com.gollum.core.common.blocks.IBlockDisplayInfos;
import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.core.utils.math.Integer3d;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.client.ClientProxyMorePistons;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.inits.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsBase extends HBlockContainer implements IBlockDisplayInfos {
	
	protected static class EMoveInfosExtend {
		
		public Block block = null;
		public int metadata = 0;
		public int move = 0;
		public Integer3d position = new Integer3d();
		public EMoveInfosExtend() {}
		
		public EMoveInfosExtend(Block block, int metadata, int move) {
			this.block    = block;
			this.metadata = metadata;
			this.move     = move;
		}

		public EMoveInfosExtend(Integer3d position, int move) {
			this.position = position;
			this.move = move;
		}

		public EMoveInfosExtend(Block block, int metadata, Integer3d position, int move) {
			this.block = block;
			this.metadata = metadata;
			this.position = position;
			this.move     = move;
		}
	}
	
	protected boolean isSticky;
	private   int     length = 1;
	protected boolean ignoreUpdates = false;
	
	protected IIcon iconTop;
	protected IIcon iconOpen;
	protected IIcon iconBottom;
	
	protected String suffixTop    = "_top";
	protected String suffixSticky = "_sticky";
	protected String suffixOpen   = "_open";
	protected String suffixBottom  = "_bottom";
	protected String suffixSide   = "_side";
	
	public BlockMorePistonsBase(String registerName, boolean isSticky) {
		super(registerName, Material.piston);
		
		this.isSticky = isSticky;
		this.setStepSound(soundTypePiston);
		this.setHardness(0.5F);
		this.setCreativeTab(ModMorePistons.morePistonsTabs);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
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
	
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		if (helper.vanillaTexture) {
			super.registerBlockIcons(iconRegister);
			return;
		};
		
		this.registerBlockIconsTop   (iconRegister);
		this.registerBlockIconsOpen  (iconRegister);
		this.registerBlockIconsBottom(iconRegister);
		this.registerBlockIconsSide  (iconRegister);
		
	}
	protected void registerBlockIconsTop   (IIconRegister iconRegister) { this.iconTop    = helper.loadTexture(iconRegister, "top" + (this.isSticky ? suffixSticky : ""), true); }
	protected void registerBlockIconsOpen  (IIconRegister iconRegister) { this.iconOpen   = helper.loadTexture(iconRegister, suffixOpen);   }
	protected void registerBlockIconsBottom(IIconRegister iconRegister) { this.iconBottom = helper.loadTexture(iconRegister, suffixBottom); }
	protected void registerBlockIconsSide  (IIconRegister iconRegister) { this.blockIcon  = helper.loadTexture(iconRegister, suffixSide);   }
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		if (helper.vanillaTexture) return super.getIcon(side, metadata);
		
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
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
	public IIcon getPistonExtensionTexture() {
		if (helper.vanillaTexture) return this.isSticky ? Blocks.sticky_piston.getPistonExtensionTexture() : Blocks.piston.getPistonExtensionTexture();
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
		
		TileEntity te = world.getTileEntity(x, y, z);
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
			switch (BlockPistonBase.getPistonOrientation(metadata)) {
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
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		log.debug("onNeighborBlockChange : "+x+", "+y+", "+z);
		
		if (!world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		log.debug("onBlockAdded : "+x+", "+y+", "+z);
		
		if (!world.isRemote && world.getTileEntity(x, y, z) == null) {
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
		
		int     metadata      = world.getBlockMetadata(x, y, z);
		int     orientation   = BlockPistonBase.getPistonOrientation(metadata);
		boolean powered       = this.isIndirectlyPowered(world, x, y, z, orientation);
		int     currentOpened = 0;
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsPiston) {
			currentOpened = ((TileEntityMorePistonsPiston)te).currentOpened;
		}
		
		log.debug("updatePistonState : ", x, y, z, "orientation="+orientation, "powered="+powered, "currentOpened="+currentOpened);
		
		if (metadata == 7) {
			return;
		}
		
		if (powered) {
			world.setBlockMetadataWithNotify (x, y, z, orientation | 0x8, 2);
			world.addBlockEvent(x, y, z, this, this.getMaximalOpenedLenght(world, x, y, z, orientation), currentOpened);
		} else {
			world.setBlockMetadataWithNotify (x, y, z, orientation, 2);
			world.addBlockEvent(x, y, z, this, 0, currentOpened);
		}
	}
	
	/**
	* Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	* entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	*/
	public boolean onBlockEventReceived(World world, int x, int y, int z, int lenghtOpened, int currentOpened) {

		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
		
		if (!this.ignoreUpdates) {
			
			log.debug("onBlockEventReceived : ",x, y, z, "orientation="+orientation, "lenghtOpened="+lenghtOpened, "remote="+world.isRemote);
			
			this.ignoreUpdates = true;
			
			boolean extendOpen = false;
			boolean extendClose = false;
			
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityMorePistonsPiston) {
				TileEntityMorePistonsPiston tileEntityPiston = (TileEntityMorePistonsPiston)te;
				
				if (lenghtOpened > 0) {
					
					log.debug("demande d'ouverture : ",x, y, z);
					
					if (currentOpened == lenghtOpened) {
						
						log.debug("Le piston reste immobile : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
						
					} else if (currentOpened == 0) {
						
						log.debug("Le piston s'ouvre : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
						
						world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
						tileEntityPiston.currentOpened = lenghtOpened;
						this.extend(world, x, y, z, orientation, lenghtOpened);
						extendOpen = true;

					} else if (currentOpened < lenghtOpened) {
						
						log.debug("Le piston continue : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
						
						world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
						tileEntityPiston.currentOpened = lenghtOpened;
						extendOpen = true;
						
					} else {
						
						log.debug("Le piston se retracte : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
						
						world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
						tileEntityPiston.currentOpened = lenghtOpened;
						extendClose = true;
						
					}
				} else {
					log.debug("demande de fermeture : ",x, y, z);
					
					if (currentOpened == 0) {
						world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
						log.debug("Le piston reste immobile : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					} else {
						log.debug("Le piston se ferme : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
						
						tileEntityPiston.currentOpened = 0;
						this.retract(world, x, y, z, orientation, currentOpened);
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
			
			this.ignoreUpdates = false;
		}
		return true;
	}
	
	////////////////////
	// Etat du piston //
	////////////////////
	

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
			
			Block block = world.getBlock(x, y, z);
			
			if (block instanceof BlockPistonMoving) {
				log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " find PistonMoving");
				return lenght;
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
			block instanceof BlockMorePistonsRod ||
			block instanceof BlockPistonMoving
		) {
			return orientation == BlockPistonBase.getPistonOrientation(world.getBlockMetadata(x, y, z));
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
			log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " Bloquer nbMoved="+nbMoved);
			return 0;
		}
		
		int walking = 0;
		
		for (int i = 0; i < distance; i++) {
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			

			if (y >= 255) {
				log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " y=>255");
				break;
			}
			
			Block blockNext = world.getBlock(x, y, z);
			log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " blockNext="+blockNext);
			
			if (this.isEmptyBlock(blockNext)) {
				walking++;
			} else {
				int moving = this.getMoveBlockOnDistance(distance - i, world, blockNext, x, y, z, orientation, nbMoved + 1);
				walking += moving;
				break;
			}
		}
		
		log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " walking="+walking+ " nbMoved="+nbMoved);
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
				block != Blocks.obsidian &&
				block.getMobilityFlag() != 2 &&
				!(block instanceof BlockMorePistonsRod) &&
				!(block instanceof BlockPistonExtension) &&
				!(block instanceof BlockMorePistonsExtension) &&
				!(block instanceof BlockMorePistonsMoving) &&
				world.getTileEntity(x, y, z) == null &&
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
		if (block != null && block != Blocks.air && block.getMobilityFlag() == 1) {
			float chance = (block instanceof BlockSnow ? -1.0f : 1.0f);
			
			block.dropBlockAsItemWithChance(world, x, y, z, metadata, chance, 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	protected ArrayList<EMoveInfosExtend> listBlockExtend (World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x;
		int yExtension = y;
		int zExtension = z;
		
		ArrayList<EMoveInfosExtend> infosExtend = new ArrayList<EMoveInfosExtend>();
		
		int size = lenghtOpened;
		
		for (int i = 0; i < (lenghtOpened + this.getMaxBlockMove ()) && size > 0; i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
			Block block = world.getBlock(xExtension, yExtension, zExtension);
			int metadata = world.getBlockMetadata(xExtension, yExtension, zExtension);
			
			// Drop les élements légés (fleurs, leviers, herbes ..)
			this.dropMobilityFlag1(block, metadata, world, xExtension, yExtension, zExtension);
			
			if (this.isEmptyBlock(block)) {
				
				infosExtend.add(new EMoveInfosExtend());
				size--;
				
			} else if (!this.isMovableBlock(block, world, xExtension, yExtension, zExtension)) {
				break;
			} else {
				infosExtend.add(new EMoveInfosExtend(block, metadata, size));
				
				world.setTileEntity(xExtension, yExtension, zExtension, null);
				world.setBlockToAir (xExtension, yExtension, zExtension);
			}
			
		}
		return infosExtend;
	}
	
	public void cleanBlockMoving(World world, int x, int y, int z) {
		TileEntity oldTe = world.getTileEntity(x, y, z);
		if (oldTe instanceof TileEntityMorePistonsMoving) {
			((TileEntityMorePistonsMoving)oldTe).setBlockFinalMove();
		}
		if (oldTe instanceof TileEntityPiston) {
			((TileEntityPiston)oldTe).clearPistonTileEntity();
		}
	}
	
	protected TileEntity cloneTileEntity(TileEntity te) {
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
	
	protected void retract(World world, int x, int y, int z, int orientation, int lenghtClose) {
		
		//Déplace avec une animation l'extention du piston
		log.debug("Create PistonMoving : "+x, y, z, "orientation="+orientation, "currentOpened="+lenghtClose);
		
		int x2 = x + Facing.offsetsXForSide[orientation] * lenghtClose;
		int y2 = y + Facing.offsetsYForSide[orientation] * lenghtClose;
		int z2 = z + Facing.offsetsZForSide[orientation] * lenghtClose;
		
		this.cleanBlockMoving(world, x2, y2, z2);
		
		this.createMoving(
			world,
			new Integer3d(x, y, z),
			new Integer3d(x, y, z),
			this,
			orientation | (this.isSticky ? 0x8 : 0x0),
			orientation, 
			lenghtClose,
			false,
			true
		);
		world.setBlockToAir (x2, y2, z2);
		
		if (this.isSticky) {
			this.retracSticky(world, x, y, z, orientation, lenghtClose);
		}
	}
	
	protected void retracSticky(World world, int x, int y, int z, int orientation, int lenghtClose) {

		int xP1 = x + Facing.offsetsXForSide[orientation] * (lenghtClose + 1);
		int yP1 = y + Facing.offsetsYForSide[orientation] * (lenghtClose + 1);
		int zP1 = z + Facing.offsetsZForSide[orientation] * (lenghtClose + 1);

		this.cleanBlockMoving(world, xP1, yP1, zP1);
		
		Block block    = world.getBlock(xP1, yP1, zP1);
		int   metadata = world.getBlockMetadata(xP1, yP1, zP1);
		
		if (!isEmptyBlock(block) && isMovableBlock(block, world, xP1, yP1, zP1)) {
			
			this.createMoving(
				world,
				new Integer3d(x, y, z),
				new Integer3d(
					x + Facing.offsetsXForSide[orientation],
					y + Facing.offsetsYForSide[orientation],
					z + Facing.offsetsZForSide[orientation]
				),
				block,
				metadata,
				orientation, 
				lenghtClose,
				false,
				false
			);
			world.setBlockToAir (xP1, yP1, zP1);
			
		}
	}
	
	protected void extend(World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		ArrayList<EMoveInfosExtend> infosExtend = this.listBlockExtend(world, x, y, z, orientation, lenghtOpened);
		this.moveBlockExtend(infosExtend, world, x, y, z, orientation, lenghtOpened);
		
	}
	
	protected void moveBlockExtend (ArrayList<EMoveInfosExtend> infosExtend, World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
		int yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
		int zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
		for (EMoveInfosExtend infos : infosExtend) {
			
			if (infos.block != null && infos.block != Blocks.air && infos.block != Blocks.piston_extension) {
				xExtension += Facing.offsetsXForSide[orientation];
				yExtension += Facing.offsetsYForSide[orientation];
				zExtension += Facing.offsetsZForSide[orientation];
				
				//Déplace avec une animation les blocks
				this.createMoving(
					world,
					new Integer3d(x, y, z),
					new Integer3d(xExtension, yExtension, zExtension),
					infos.block,
					infos.metadata,
					orientation, 
					infos.move,
					true,
					false
				);
			}
		}
		
		xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
		yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
		zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
		//Déplace avec une animation l'extention du piston
		log.debug("Create PistonMoving : "+xExtension, yExtension, zExtension, "orientation="+orientation, "lenghtOpened="+lenghtOpened);
		
		this.createMoving(
			world,
			new Integer3d(x, y, z),
			new Integer3d(xExtension, yExtension, zExtension),
			ModBlocks.blockPistonExtention,
			orientation | (this.isSticky ? 0x8 : 0x0),
			orientation, 
			lenghtOpened,
			true,
			false
		);
	}

	protected void createMoving(World world, Integer3d pistonPos, Integer3d dest, Block block, int metadata, int orientation, int lenghtOpened, boolean extending, boolean root) {
		
		TileEntity teCopy = this.cloneTileEntity(world.getTileEntity(
			dest.x + (extending ? -1 : 1) * lenghtOpened,
			dest.y + (extending ? -1 : 1) * lenghtOpened,
			dest.z + (extending ? -1 : 1) * lenghtOpened 
		));
		
		if (root) {
			teCopy = this.cloneTileEntity(world.getTileEntity(
				dest.x,
				dest.y,
				dest.z 
			));
		}
		
		world.setBlock(dest.x, dest.y, dest.z, ModBlocks.blockPistonMoving, metadata, 2);
		TileEntityMorePistonsMoving teExtension = new TileEntityMorePistonsMoving(
			block,
			orientation, 
			extending,
			lenghtOpened,
			pistonPos,
			root
		);
		teExtension.subTe = teCopy;
		world.setTileEntity(dest.x, dest.y, dest.z, teExtension);
		
		TileEntity te = world.getTileEntity(pistonPos.x, pistonPos.y, pistonPos.z);
		if (te instanceof TileEntityMorePistonsPiston) {
			((TileEntityMorePistonsPiston)te).extentionPos = new Integer3d(dest.x, dest.y, dest.z);
			world.notifyBlockOfNeighborChange(dest.x, dest.y, dest.z, this);
		}
		if (te instanceof TileEntityMorePistonsMoving) {
			((TileEntityMorePistonsMoving) te).getPistonOriginTE().extentionPos = new Integer3d(dest.x, dest.y, dest.z);
			world.notifyBlockOfNeighborChange(dest.x, dest.y, dest.z, ModBlocks.blockPistonMoving);
		}
	}
}
