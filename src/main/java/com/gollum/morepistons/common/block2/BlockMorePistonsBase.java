package com.gollum.morepistons.common.block2;

import static com.gollum.morepistons.ModMorePistons.log;

import java.util.ArrayList;

import com.gollum.core.tools.helper.blocks.HBlockPistonBase;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.inits.ModBlocks;

import com.gollum.morepistons.inits.ModCreativeTab;

//import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class BlockMorePistonsBase extends HBlockPistonBase {

	private int   length = 1;
	protected boolean ignoreUpdates = false;
	
	public BlockMorePistonsBase(String registerName, boolean isSticky) {
		super(registerName, isSticky);
		
		this.setCreativeTab(ModCreativeTab.morePistonsTabs);
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
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
		return this.length;
	}
	
	/**
	 * Block maximal que peux pouser le piston
	 * @return
	 */
	public int getMaxBlockMove () {
		return ModMorePistons.config.numberMovableBlockWithDefaultPiston;
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
	protected void registerBlockIconsTop(IIconRegister iconRegister) {
		this.iconTop    = helper.loadTexture(iconRegister, "top" + (this.isSticky ? suffixSticky : ""), true);
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
//	/**
//	 * Called right before the block is destroyed by a player. Args: world, x,
//	 * y, z, metaData
//	 */
//	@Override
//	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
//		
//		log.debug("onBlockDestroyedByPlayer : "+x+", "+y+", "+z);
//		
//		int orientation = this.getPistonOrientation(metadata);
//		Block block = ModBlocks.blockPistonRod;
//		while (block instanceof BlockMorePistonsRod) {
//			
//			x += Facing.offsetsXForSide[orientation];
//			y += Facing.offsetsYForSide[orientation];
//			z += Facing.offsetsZForSide[orientation];
//			
//			block = world.getBlock(x, y, z);
//			
//			if (block instanceof BlockMorePistonsRod || block instanceof BlockMorePistonsExtension) {
//				world.func_147480_a(x, y, z, false);
//			}
//			
//		}
//	}
	
	/**
	 * Called when the block is placed in the world. Envoie un event qunad on
	 * place un block sur le monde
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		int orientation = determineOrientation(world, x, y, z, entityLiving);
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
		
		if (!world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
		return;
	}
	
	
	/**
	* Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	* entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	*/
	public boolean onBlockEventReceived(World world, int x, int y, int z, int lenghtOpened, int orientation) {
		
		if (!this.ignoreUpdates) {
			
			this.ignoreUpdates = true;
			
			boolean extendOpen = false;
			boolean extendClose = false;
			
			int currentOpened = this.getOpenedLenght (world, x, y, z, orientation); //On recupère l'ouverture actuel du piston
			log.debug("L'ouverture du piston : "+x+", "+y+", "+z+": currentOpened="+currentOpened+", lenghtOpened="+lenghtOpened);
			
			// Demande une ouverture du piston
			if (lenghtOpened > 0) {
				log.debug("demande d'ouverture : "+x+", "+y+", "+z);

				// Si le piston ne change pas de taille on ne fait rien
				// Si le piston est fermer: on ouvre
				// Si le piston est ouvert mais que la longueur du piston est plus courte que l'ouverture actuel: On Retracte le piston à la longueur max du piston
				// Si le piston est ouvert mais n'a pas atteint la longueur max on continue l'ouverture (obstaclque qui génait la place)
				
				if (currentOpened == lenghtOpened) {
					
					log.debug("Les piston ne change pas de taille : "+x+", "+y+", "+z);
					this.ignoreUpdates = false;
					return true;
				
				//Le piston était fermer
				} else if (currentOpened == 0) {
					log.debug("Les piston était fermé : "+x+", "+y+", "+z);
					
					this.extend(world, x, y, z, orientation, lenghtOpened);
//					extendOpen = true;
				
				// Le piston s'ouvre plus
				} else if (currentOpened < lenghtOpened) {
					
					log.debug("Le piston s'ouvre plus : "+x+", "+y+", "+z);
					
					int x2 = x + Facing.offsetsXForSide[orientation] * currentOpened;
					int y2 = y + Facing.offsetsYForSide[orientation] * currentOpened;
					int z2 = z + Facing.offsetsZForSide[orientation] * currentOpened;
					
//					this.extend(world, x2, y2, z2, orientation, lenghtOpened - currentOpened);
//					extendOpen = true;
				
				// On retracte le piston partielement
				} else {
					
					int diff = currentOpened - lenghtOpened;
					log.debug("Le piston se retract parielement : "+x+", "+y+", "+z+" diff="+diff);

					int x2 = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
					int y2 = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
					int z2 = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
					
					int cX2 = x + Facing.offsetsXForSide[orientation] * currentOpened;
					int cY2 = y + Facing.offsetsYForSide[orientation] * currentOpened;
					int cZ2 = z + Facing.offsetsZForSide[orientation] * currentOpened;

//					world.setBlockToAir (cX2, cY2, cZ2);
//					world.setBlock(x2, y2, z2, Blocks.piston_extension, orientation, 2);
//					TileEntity teExtension = new TileEntityMorePistons (ModBlocks.blockPistonExtension, orientation, orientation, true, false, -diff, false);
//					world.setTileEntity(x2, y2, z2, teExtension);
//					
					this.retracSticky(world, x2, y2, z2, orientation, diff);
					
					extendClose = true;
				}
				
			// Demande de fermeture du piston
			} else {
				
				if (currentOpened == 0) {

					world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
					
				} else {
					
					log.debug("demande de fermeture : "+x+", "+y+", "+z);
					
					// Debut de l'effet de fermeture adapter pour tous les pistons
					// On calcule la taille du piston et on retacte se que l'on peu
					TileEntity tileentity = world.getTileEntity(x + Facing.offsetsXForSide[orientation], y + Facing.offsetsYForSide[orientation], z + Facing.offsetsZForSide[orientation]);
					
					if (tileentity instanceof TileEntityPiston) {
						((TileEntityPiston)tileentity).clearPistonTileEntity();
					}

					int cX2 = x + Facing.offsetsXForSide[orientation] * currentOpened;
					int cY2 = y + Facing.offsetsYForSide[orientation] * currentOpened;
					int cZ2 = z + Facing.offsetsZForSide[orientation] * currentOpened;

//					world.setBlockToAir (cX2, cY2, cZ2);
//					world.setBlock(x, y, z, Blocks.piston_extension, orientation, 2);
//					world.setTileEntity(x, y, z, new TileEntityMorePistons (this, orientation, orientation, false, true, currentOpened, true));
//					
//					this.retracSticky(world, x, y, z, orientation, currentOpened);
					
					extendClose = true;
				}
			}
			
			///////////////////
			// Joue les sons //
			///////////////////
			
			if (extendOpen) {
				world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
				// On joue le son
				world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
			}

			if (extendClose) {
				world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
				// On joue le son
				world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
			}
			
			this.ignoreUpdates = false;
			
		}
		
		return true;
	}
	
	/////////////////////////
	// Ouverture du piston //
	/////////////////////////
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	public void updatePistonState(World world, int x, int y, int z) {
		
//		world.addBlockEvent(x, y, z, this, 0, 0);
//		
//		int metadata    = world.getBlockMetadata(x, y, z);
//		int orientation = getPistonOrientation(metadata);
//		
//		if (metadata == 7) {
//			return;
//		}
//		
//		boolean powered = this.isIndirectlyPowered(world, x, y, z, orientation);
//		boolean extended = isExtended(metadata);
//		
//		log.debug("updatePistonState : "+x+", "+y+", "+z+ ": powered="+powered+", extended="+extended);
//		
//		// Si redstone eteinte et piston ouvert alors il faut fermer
//		if (!powered && extended) {
//			int max = this.getMaximalOpenedLenght(world, x, y, z, orientation);
//			if (max == -1) {
//				log.debug("Piston en court de mouvement");
//				return;
//			}
//			world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
//			world.addBlockEvent(x, y, z, this, -1, orientation);
//		
//		// Si redstone active et piston fermer alors il faut ouvrir
//		} else if (powered) {
//			int max = this.getMaximalOpenedLenght(world, x, y, z, orientation);
//			if (max <= 0) {
//				log.debug("Piston en court de mouvement ou bloqué");
//				return;
//			}
//			world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
//			world.addBlockEvent(x, y, z, this, max, orientation);
//		}
//		
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
		return this.getMaximalOpenedLenght(world, x, y, z, orientation, true, this.getLengthInWorld(world, x, y, z, orientation));
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
	public int getMaximalOpenedLenght (World world, int x, int y, int z, int orientation, boolean detectMoving, int maxlenght) {
		
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
				if (detectMoving) {
					return -1;
				} else {
					return lenght;
				}
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
//			block instanceof BlockMorePistonsExtension ||
//			block instanceof BlockMorePistonsRod ||
			block instanceof BlockPistonMoving
		) {
			return orientation == BlockMorePistonsBase.getPistonOrientation(world.getBlockMetadata(x, y, z));
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
			block instanceof IFluidBlock;
	}
	
	/**
	 * Test if this block is movable
	 * @param id
	 * @return
	 */
	public boolean isMovableBlock(Block block, World world, int x, int y, int z) {
		
		boolean isPistonClosed = BlockMorePistonsBase.isPiston (block);
		if (isPistonClosed) {
			isPistonClosed = !((BlockPistonBase) block).isExtended(world.getBlockMetadata(x, y, z));
		}
		
		return
			this.isEmptyBlock (block) ||
			isPistonClosed ||
			(
				block != Blocks.obsidian &&
				block.getMobilityFlag() != 2 &&
//				!(block instanceof BlockMorePistonsRod) &&
//				!(block instanceof BlockMorePistonsExtension) &&
				!(block instanceof BlockPistonMoving) &&
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
		return block instanceof BlockPistonBase;
	}
	
	/**
	 * Recupère l'ouverture du piston
	 * @param World world
	 * @param int x
	 * @param int y
	 * @param int z
	 * @param int orientation
	 * @return int
	 */
	public int getOpenedLenght(World world, int x, int y, int z, int orientation) {
//
		int lenght = -1;
//
//		Block block = null;
//		int metadata = 0;
//		int blockOrentation = 0;
//		boolean moving = false;
//
//		do {
//
			lenght++;
//
//			x += Facing.offsetsXForSide[orientation];
//			y += Facing.offsetsYForSide[orientation];
//			z += Facing.offsetsZForSide[orientation];
//
//			block = world.getBlock(x, y, z);
//			metadata = world.getBlockMetadata(x, y, z);
//			blockOrentation = this.getPistonOrientation(metadata);
//
//			moving = false;
//			
////			TODO Penser a la TE
////			if (block instanceof BlockPistonMoving) {
////				TileEntity tileEntity = world.getTileEntity(x, y, z);
////				if (tileEntity instanceof TileEntityMorePistons) {
////					Block blockMoving = ((TileEntityMorePistons) tileEntity).storedBlock;
////					if (
////						blockMoving instanceof BlockMorePistonsRod || 
////						blockMoving instanceof BlockMorePistonsExtension
////					) {
////						moving = true;
////					}
////
////				}
////			}
//			
//		} while (
//			(
//				moving || 
//				block instanceof BlockMorePistonsRod || 
//				block instanceof BlockMorePistonsExtension
//			)
//			&& orientation == blockOrentation
//		);
//
		return lenght;
	}
	
	
	/**
	 * Retracte le block qui ets collé au piston si le piston est un sticky
	 * piston
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param length
	 */
	protected void retracSticky(World world, int x, int y, int z, int orientation, int length) {
		if (this.isSticky) {

			int x2 = x + Facing.offsetsXForSide[orientation] * (length + 1);
			int y2 = y + Facing.offsetsYForSide[orientation] * (length + 1);
			int z2 = z + Facing.offsetsZForSide[orientation] * (length + 1);
			
			Block block = world.getBlock(x2, y2, z2);
			
			if (!isEmptyBlock(block) && isMovableBlock(block, world, x2, y2, z2)) {
				log.debug("The sticky block : "+x2+", "+y2+", "+z2+" block="+block);
				int blockMeta = world.getBlockMetadata(x2, y2, z2);
				
				int xPlus1 = x + Facing.offsetsXForSide[orientation];
				int yPlus1 = y + Facing.offsetsYForSide[orientation];
				int zPlus1 = z + Facing.offsetsZForSide[orientation];
				
				world.setBlockToAir(x2, y2, z2);
				
				//Déplace avec une animation les blocks
				world.setBlock(xPlus1, yPlus1, zPlus1, Blocks.piston_extension, blockMeta, 2);
//				TileEntity teBlock = new TileEntityMorePistons (block, blockMeta, orientation, false, true, length, false);
//				world.setTileEntity(xPlus1, yPlus1, zPlus1, teBlock);
			}
		}
	}
	
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
	
	class EMoveInfosExtend {
		
		public Block block = null;
		public int metadata = 0;
		public int move = 0;
		public int x = 0;
		public int y = 0;
		public int z = 0;
		public EMoveInfosExtend() {}
		
		public EMoveInfosExtend(Block block, int metadata, int move) {
			this.block    = block;
			this.metadata = metadata;
			this.move     = move;
		}

		public EMoveInfosExtend(int x, int y, int z, int move) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.move = move;
		}

		public EMoveInfosExtend(Block block, int metadata, int x, int y, int z, int move) {
			this.block = block;
			this.metadata = metadata;
			this.x = x;
			this.y = y;
			this.z = z;
			this.move     = move;
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
	
	protected void moveBlockExtend (ArrayList<EMoveInfosExtend> infosExtend, World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
		int yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
		int zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
//		for (EMoveInfosExtend infos : infosExtend) {
			
//			if (infos.block != null && infos.block != Blocks.air && infos.block != Blocks.piston_extension) {
//				xExtension += Facing.offsetsXForSide[orientation];
//				yExtension += Facing.offsetsYForSide[orientation];
//				zExtension += Facing.offsetsZForSide[orientation];
//				
				//Déplace avec une animation les blocks
//				TileEntity teBlock = new TileEntityMorePistons (infos.block, infos.metadata, orientation, true, false, infos.move, false);
//				world.setBlock(xExtension, yExtension, zExtension, Blocks.piston_extension, infos.metadata, 2);
//				world.setTileEntity(xExtension, yExtension, zExtension, teBlock);
//			}
//		}

//		xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
//		yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
//		zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
		int metadata = orientation | (this.isSticky ? 0x8 : 0);
		
		for (int i = 0; i < lenghtOpened; i++) {
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			world.setBlock(x, y, z, Blocks.piston_extension, orientation, 2);
		}

		log.debug("Create PistonMoving : "+x+", "+y+", "+z+" orientation="+orientation+", lenghtOpened="+lenghtOpened);
		world.setBlock(x, y, z, Blocks.piston_extension, orientation, 2);
		
		//Déplace avec une animation l'extention du piston
		
//		TileEntity teExtension = new TileEntityMorePistons (ModBlocks.blockPistonExtension, metadata, orientation, true, false, lenghtOpened, true);
//		world.setTileEntity(xExtension, yExtension, zExtension, teExtension);
	}
	
	/**
	 * Ouvr eun piston de la taille voulu
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param lenghtOpened
	 */
	protected void extend(World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		ArrayList<EMoveInfosExtend> infosExtend = this.listBlockExtend(world, x, y, z, orientation, lenghtOpened);
		this.moveBlockExtend(infosExtend, world, x, y, z, orientation, lenghtOpened);
		
	}
	
}
