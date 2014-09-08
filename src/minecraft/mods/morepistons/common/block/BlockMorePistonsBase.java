package mods.morepistons.common.block;

import java.util.ArrayList;

import mods.gollum.core.tools.helper.blocks.HBlockPistonBase;
import mods.morepistons.ModMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class BlockMorePistonsBase extends HBlockPistonBase {
	
	private boolean ignoreUpdates = false;
	private int length = 1;
	
	public BlockMorePistonsBase(int id, String registerName, boolean isSticky) {
		super(id, registerName, isSticky);
		
		this.setCreativeTab(ModMorePistons.morePistonsTabs);
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
		return 12;
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
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.iconTop    = helper.loadTexture(iconRegister, "top" + (this.isSticky ? suffixSticky : ""), true);
		this.iconOpen   = helper.loadTexture(iconRegister, suffixOpen);
		this.iconBottom = helper.loadTexture(iconRegister, suffixBotom);
		this.iconSide   = helper.loadTexture(iconRegister, suffixSide);
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {

		int orientation = this.getOrientation(metadata);
		int id = ModMorePistons.blockPistonRod.blockID;
		while (id == ModMorePistons.blockPistonRod.blockID) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			id = world.getBlockId(x, y, z);
			
			if (id == ModMorePistons.blockPistonRod.blockID || id == ModMorePistons.blockPistonExtension.blockID) {
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
		
		int orientation = determineOrientation(world, x, y, z, entityLiving);
		world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
		
		ModMorePistons.log.debug("onBlockPlacedBy : "+x+", "+y+", "+z);
		
		if (!this.ignoreUpdates && !world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		
		ModMorePistons.log.debug("onNeighborBlockChange : "+x+", "+y+", "+z);
		
		if (!this.ignoreUpdates && !world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		return;
	}

	/////////////////////////
	// Ouverture du piston //
	/////////////////////////
	
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	public void updatePistonState(World world, int x, int y, int z) {
		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = getOrientation(metadata);

		if (metadata == 7) {
			return;
		}
		
		boolean powered = this.isIndirectlyPowered(world, x, y, z, orientation);
		boolean extended = isExtended(metadata);
		
		ModMorePistons.log.debug("updatePistonState : "+x+", "+y+", "+z+ ": powered="+powered+", extended="+extended);
		
		// Si redstone eteinte et piston ouvert alors il faut fermer
		if (!powered && extended) {
			int max = this.getMaximalOpenedLenght(world, x, y, z, orientation);
			if (max == -1) {
				ModMorePistons.log.debug("Piston en court de mouvement");
				return;
			}
			world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
			world.addBlockEvent(x, y, z, this.blockID, 0, orientation);
		
		// Si redstone active et piston fermer alors il faut ouvrir
		} else if (powered) {
			int max = this.getMaximalOpenedLenght(world, x, y, z, orientation);
			if (max <= 0) {
				ModMorePistons.log.debug("Piston en court de mouvement ou bloqué");
				return;
			}
			world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
			world.addBlockEvent(x, y, z, this.blockID, max, orientation);
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
		
		ModMorePistons.log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " maxlenght="+maxlenght);
		
		int lenght = 0;
		
		for (int i = 0; i < maxlenght; i++) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			if (y >= 255) {
				ModMorePistons.log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " y>=255");
				break;
			}
			
			int id = world.getBlockId(x, y, z);
			
			if (id == Block.pistonMoving.blockID) {
				ModMorePistons.log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " find PistonMoving");
				if (detectMoving) {
					return -1;
				} else {
					return lenght;
				}
			}
			ModMorePistons.log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " id = "+id);
			if (! (this.isEmptyBlockBlock(id)) && !this.isRodInOrientation(id, world, x, y, z, orientation)) {
				lenght += this.getMoveBlockOnDistance (maxlenght - i, world, id, x, y, z, orientation);
				break;
			}
			lenght++;
		}
		
		ModMorePistons.log.debug("getMaximalOpenedLenght : "+x+", "+y+", "+z+ " : lenght="+lenght);
		
		return lenght;
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
	 * @return
	 */
	private int getMoveBlockOnDistance (int distance, World world, int id, int x, int y, int z, int orientation) {
		return this.getMoveBlockOnDistance(distance, world, id, x, y, z, orientation, 1);
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
	private int getMoveBlockOnDistance (int distance, World world, int id, int x, int y, int z, int orientation, int nbMoved) {
		
		if (nbMoved == this.getMaxBlockMove () || !this.isMovableBlock(id, world, x, y, z)) {
			ModMorePistons.log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " Bloquer nbMoved="+nbMoved);
			return 0;
		}
		
		int walking = 0;
		
		for (int i = 0; i < distance; i++) {
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			

			if (y >= 255) {
				ModMorePistons.log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " y=>255");
				break;
			}
			
			int idNext = world.getBlockId(x, y, z);
			ModMorePistons.log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " idNext="+idNext);
			
			if (this.isEmptyBlockBlock(idNext)) {
				walking++;
			} else {
				int moving = this.getMoveBlockOnDistance(distance - i, world, idNext, x, y, z, orientation, nbMoved + 1);
				walking += moving;
				break;
			}
		}
		
		ModMorePistons.log.debug("getMoveBlockOnDistance : "+x+", "+y+", "+z+ " walking="+walking+ " nbMoved="+nbMoved);
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
	private static boolean isRodInOrientation (int id, World world, int x, int y, int z, int orientation) {
		
		if (
			id == ModMorePistons.blockPistonExtension.blockID ||
			id == ModMorePistons.blockPistonRod.blockID ||
			id == Block.pistonMoving.blockID
		) {
			return orientation == BlockMorePistonsBase.getOrientation(world.getBlockMetadata(x, y, z));
		}
		return false;
	}
	
	/**
	 * Test if this block is movable
	 * @param id
	 * @return
	 */
	public static boolean isEmptyBlockBlock(int id) {
		return
			id == 0 ||
			Block.blocksList[id].getMobilityFlag() == 1 ||
			Block.blocksList[id] instanceof BlockFluid ||
			Block.blocksList[id] instanceof IFluidBlock;
	}
	
	/**
	 * Test if this block is movable
	 * @param id
	 * @return
	 */
	public static boolean isMovableBlock(int id, World world, int x, int y, int z) {
		
		boolean isPistonClosed = BlockMorePistonsBase.isPiston (id);
		if (isPistonClosed) {
			HBlockPistonBase block = (HBlockPistonBase) Block.blocksList[id];
			isPistonClosed = !block.isExtended(world.getBlockMetadata(x, y, z));
		}
		
		return
			BlockMorePistonsBase.isEmptyBlockBlock (id) ||
			isPistonClosed ||
			(
				id != BlockObsidian.obsidian.blockID &&
				Block.blocksList[id].getMobilityFlag() != 2 &&
				!(Block.blocksList[id] instanceof BlockMorePistonsRod) &&
				!(Block.blocksList[id] instanceof BlockPistonExtension) &&
				!(Block.blocksList[id] instanceof BlockPistonMoving) &&
				!world.blockHasTileEntity(x, y, z) &&
				Block.blocksList[id].getBlockHardness(world, x, y, z) != -1.0F
			);
	}
	
	/**
	 * Test si on a un piston
	 * @param id
	 * @return
	 */
	public static boolean isPiston (int id) {
		return Block.blocksList[id] instanceof HBlockPistonBase;
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

		int lenght = -1;

		int id = 0;
		int metadata = 0;
		int blockOrentation = 0;
		boolean moving = false;

		do {

			lenght++;

			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];

			id = world.getBlockId(x, y, z);
			metadata = world.getBlockMetadata(x, y, z);
			blockOrentation = this.getOrientation(metadata);

			moving = false;
			if (id == Block.pistonMoving.blockID) {
				TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
				if (tileEntity instanceof TileEntityMorePistons) {
					int idMoving = ((TileEntityMorePistons) tileEntity).storedBlockID;
					if (
						idMoving == ModMorePistons.blockPistonRod.blockID || 
						idMoving == ModMorePistons.blockPistonExtension.blockID
					) {
						moving = true;
					}

				}
			}

		} while (
			(
				moving || 
				id == ModMorePistons.blockPistonRod.blockID ||
				id == ModMorePistons.blockPistonExtension.blockID
			)
			&& orientation == blockOrentation
		);

		return lenght;
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
			ModMorePistons.log.debug("L'ouverture du piston : "+x+", "+y+", "+z+": currentOpened="+currentOpened+", lenghtOpened="+lenghtOpened);
			
			// Demande une ouverture du piston
			if (lenghtOpened > 0) {
				ModMorePistons.log.debug("demande d'ouverture : "+x+", "+y+", "+z);

				// Si le piston ne change pas de taille on ne fait rien
				// Si le piston est fermer: on ouvre
				// Si le piston est ouvert mais que la longueur du piston est plus courte que l'ouverture actuel: On Retracte le piston à la longueur max du piston
				// Si le piston est ouvert mais n'a pas atteint la longueur max on continue l'ouverture (obstaclque qui génait la place)
				
				if (currentOpened == lenghtOpened) {
					
					ModMorePistons.log.debug("Les piston ne change pas de taille : "+x+", "+y+", "+z);
					this.ignoreUpdates = false;
					return true;
				
				//Le piston était fermer
				} else if (currentOpened == 0) {
					ModMorePistons.log.debug("Les piston était fermé : "+x+", "+y+", "+z);
					
					this.extend(world, x, y, z, orientation, lenghtOpened);
					extendOpen = true;
				
				// Le piston s'ouvre plus
				} else if (currentOpened < lenghtOpened) {
					
					ModMorePistons.log.debug("Le piston s'ouvre plus : "+x+", "+y+", "+z);
					
					int x2 = x + Facing.offsetsXForSide[orientation] * currentOpened;
					int y2 = y + Facing.offsetsYForSide[orientation] * currentOpened;
					int z2 = z + Facing.offsetsZForSide[orientation] * currentOpened;
					
					this.extend(world, x2, y2, z2, orientation, lenghtOpened - currentOpened);
					extendOpen = true;
				
				// On retracte le piston partielement
				} else {
					
					int diff = currentOpened - lenghtOpened;
					ModMorePistons.log.debug("Le piston se retract parielement : "+x+", "+y+", "+z+" diff="+diff);

					int x2 = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
					int y2 = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
					int z2 = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
					
					int cX2 = x + Facing.offsetsXForSide[orientation] * currentOpened;
					int cY2 = y + Facing.offsetsYForSide[orientation] * currentOpened;
					int cZ2 = z + Facing.offsetsZForSide[orientation] * currentOpened;

					world.setBlockToAir (cX2, cY2, cZ2);
					world.setBlock(x2, y2, z2, Block.pistonMoving.blockID, orientation, 2);
					TileEntity teExtension = new TileEntityMorePistons (ModMorePistons.blockPistonExtension.blockID, orientation, orientation, true, false, -diff, false);
					world.setBlockTileEntity(x2, y2, z2, teExtension);
					
					this.retracSticky(world, x2, y2, z2, orientation, diff);
					
					extendClose = true;
				}
				
			// Demande de fermeture du piston
			} else {
				
				if (currentOpened == 0) {

					world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
					
				} else {
					
					ModMorePistons.log.debug("demande de fermeture : "+x+", "+y+", "+z);
					
					// Debut de l'effet de fermeture adapter pour tous les pistons
					// On calcule la taille du piston et on retacte se que l'on peu
					TileEntity tileentity = world.getBlockTileEntity(x + Facing.offsetsXForSide[orientation], y + Facing.offsetsYForSide[orientation], z + Facing.offsetsZForSide[orientation]);
					
					if (tileentity instanceof TileEntityPiston) {
						((TileEntityPiston)tileentity).clearPistonTileEntity();
					}

					int cX2 = x + Facing.offsetsXForSide[orientation] * currentOpened;
					int cY2 = y + Facing.offsetsYForSide[orientation] * currentOpened;
					int cZ2 = z + Facing.offsetsZForSide[orientation] * currentOpened;

					world.setBlockToAir (cX2, cY2, cZ2);
					world.setBlock(x, y, z, Block.pistonMoving.blockID, orientation, 2);
					world.setBlockTileEntity(x, y, z, new TileEntityMorePistons (this.blockID, orientation, orientation, false, true, currentOpened, true));
					
					this.retracSticky(world, x, y, z, orientation, currentOpened);
					
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

			int id = world.getBlockId(x2, y2, z2);

			if (!isEmptyBlockBlock(id) && isMovableBlock(id, world, x2, y2, z2)) {
				ModMorePistons.log.debug("The sticky block : "+x2+", "+y2+", "+z2+" id="+id);
				int blockMeta = world.getBlockMetadata(x2, y2, z2);

				int xPlus1 = x + Facing.offsetsXForSide[orientation];
				int yPlus1 = y + Facing.offsetsYForSide[orientation];
				int zPlus1 = z + Facing.offsetsZForSide[orientation];

				world.setBlock(x2, y2, z2, 0);
				world.setBlockMetadataWithNotify(x2, y2, z2, 0, 2);
				

				//Déplace avec une animation les blocks
				world.setBlock(xPlus1, yPlus1, zPlus1, Block.pistonMoving.blockID, blockMeta, 2);
				TileEntity teBlock = new TileEntityMorePistons (id, blockMeta, orientation, false, true, length, false);
				world.setBlockTileEntity(xPlus1, yPlus1, zPlus1, teBlock);
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
	protected void dropMobilityFlag1 (int id, int metadata, World world, int x, int y, int z) {
		// Drop les élements légés (fleurs, leviers, herbes ..)
		if (id != 0 && (Block.blocksList[id]).getMobilityFlag() == 1) {
			float chance = (Block.blocksList[id] instanceof BlockSnow ? -1.0f : 1.0f);
			
			Block.blocksList[id].dropBlockAsItemWithChance(world, x, y, z, metadata, chance, 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	class EMoveInfosExtend {
		
		public int id = 0;
		public int metadata = 0;
		public int move = 0;
		public int x = 0;
		public int y = 0;
		public int z = 0;
		public EMoveInfosExtend() {}
		
		public EMoveInfosExtend(int id, int metadata, int move) {
			this.id       = id;
			this.metadata = metadata;
			this.move     = move;
		}

		public EMoveInfosExtend(int x, int y, int z, int move) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.move = move;
		}

		public EMoveInfosExtend(int id, int metadata, int x, int y, int z, int move) {
			this.id       = id;
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
			
			int id = world.getBlockId(xExtension, yExtension, zExtension);
			int metadata = world.getBlockMetadata(xExtension, yExtension, zExtension);
			
			// Drop les élements légés (fleurs, leviers, herbes ..)
			this.dropMobilityFlag1(id, metadata, world, xExtension, yExtension, zExtension);
			
			if (this.isEmptyBlockBlock(id)) {
				
				infosExtend.add(new EMoveInfosExtend());
				size--;
				
			} else if (!this.isMovableBlock(id, world, xExtension, yExtension, zExtension)) {
				break;
			} else {
				infosExtend.add(new EMoveInfosExtend(id, metadata, size));
				world.setBlockToAir (xExtension, yExtension, zExtension);
			}
			
		}
		return infosExtend;
	}
	
	protected void moveBlockExtend (ArrayList<EMoveInfosExtend> infosExtend, World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
		int yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
		int zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
		for (EMoveInfosExtend infos : infosExtend) {
			
			if (infos.id != 0 && infos.id != Block.pistonMoving.blockID) {
				xExtension += Facing.offsetsXForSide[orientation];
				yExtension += Facing.offsetsYForSide[orientation];
				zExtension += Facing.offsetsZForSide[orientation];
				
				//Déplace avec une animation les blocks
				world.setBlock(xExtension, yExtension, zExtension, Block.pistonMoving.blockID, infos.metadata, 2);
				TileEntity teBlock = new TileEntityMorePistons (infos.id, infos.metadata, orientation, true, false, infos.move, false);
				world.setBlockTileEntity(xExtension, yExtension, zExtension, teBlock);
			}
		}

		xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
		yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
		zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
		//Déplace avec une animation l'extention du piston
		ModMorePistons.log.debug("Create PistonMoving : "+xExtension+", "+yExtension+", "+zExtension+" orientation="+orientation+", lenghtOpened="+lenghtOpened);
		
		int metadata = orientation | (this.isSticky ? 0x8 : 0);
		world.setBlock(xExtension, yExtension, zExtension, Block.pistonMoving.blockID, orientation, 2);
		TileEntity teExtension = new TileEntityMorePistons (ModMorePistons.blockPistonExtension.blockID, metadata, orientation, true, false, lenghtOpened, true);
		world.setBlockTileEntity(xExtension, yExtension, zExtension, teExtension);
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
