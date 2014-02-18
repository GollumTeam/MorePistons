package mods.morepistons.common.block;

import java.util.ArrayList;

import com.google.common.util.concurrent.MoreExecutors;

import mods.morepistons.common.ModMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.IFluidBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonBase extends BlockPistonBase {

	public static int MAX_BLOCK_MOVE = 12;
	
	private boolean ignoreUpdates = false;
	private int length = 1;
	protected boolean isSticky;
	
	protected String texturePrefixe;
	protected Icon textureFileTop;
	protected Icon textureFileTopSticky;
	protected Icon textureFileOpen;
	protected Icon textureFileSide;
	protected Icon textureFileBottom;
	
	public BlockMorePistonBase(int id, boolean isSticky, String texturePrefixe) {
		super(id, isSticky);
		
		ModMorePistons.log.info ("Create block id : " + id + " texturePrefixe : " + texturePrefixe);
		
		this.isSticky = isSticky;
		this.texturePrefixe = texturePrefixe;
		this.setCreativeTab(ModMorePistons.morePistonsTabs);
	}

	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public BlockMorePistonBase setLength(int length) {
		this.length = length;
		return this;
	}
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public int getLengthInWorld(World world, int x, int y, int z) {
		return this.length;
	}
	
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	
	/**
	* Charge une texture et affiche dans le log
	*
	* @param iconRegister
	* @param key
	* @return
	*/
	public Icon loadTexture(IconRegister iconRegister, String key) {
		ModMorePistons.log.debug ("Register icon More Piston :\"" + key + "\"");
		return iconRegister.registerIcon(key);
	}
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.textureFileTop       = this.loadTexture(iconRegister, ModMorePistons.PATH_TEXTURES + "top");
		this.textureFileTopSticky = this.loadTexture(iconRegister, ModMorePistons.PATH_TEXTURES + "top_sticky");
		this.textureFileOpen      = this.loadTexture(iconRegister, ModMorePistons.PATH_TEXTURES + this.texturePrefixe + "top");
		this.textureFileBottom    = this.loadTexture(iconRegister, ModMorePistons.PATH_TEXTURES + this.texturePrefixe + "bottom");
		this.textureFileSide      = this.loadTexture(iconRegister, ModMorePistons.PATH_TEXTURES + this.texturePrefixe + "side");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getPistonExtensionTexture() {
		return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
	}
	
	@Override
	public Icon getIcon(int i, int j) {
		int k = getOrientation(j);
		if (k > 5) {
			return this.textureFileTopSticky;
		}
		if (i == k) {
			if (
				(isExtended(j)) ||
				(this.minX > 0.0D) || (this.minY > 0.0D) || (this.minZ > 0.0D) ||
				(this.maxX < 1.0D) || (this.maxY < 1.0D) || (this.maxZ < 1.0D)
			) {
				return this.textureFileOpen;
			}
			
			return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
		}
		
		return i != Facing.oppositeSide[k] ? this.textureFileSide : this.textureFileBottom;
	}
	
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
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
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		
		ModMorePistons.log.debug("onNeighborBlockChange : "+x+", "+y+", "+z);
		
		if (!this.ignoreUpdates && !world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World world, int x, int y, int z) {
		return;
	}

	/////////////////////////
	// Ouverture du piston //
	/////////////////////////
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	private void updatePistonState(World world, int x, int y, int z) {
		int metadata    = world.getBlockMetadata(x, y, z);
		int orientation = getOrientation(metadata);

		if (metadata == 7) {
			return;
		}
		
		boolean powered = this.isIndirectlyPowered(world, x, y, z, orientation);
		boolean extended = isExtended(metadata);
		
		ModMorePistons.log.debug("updatePistonState : "+x+", "+y+", "+z+ ": powered="+powered+", extended="+extended);
		
		// Si redstone active et piston fermer alors il faut ouvrir
		if (powered && !extended) {
			world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
			world.addBlockEvent(x, y, z, this.blockID, this.getMaximalOpenedLenght(world, x, y, z, orientation), orientation);
		
		// Si redstone eteinte et piston ouvert alors il faut fermer
		} else if (!powered && extended) {
			world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
			world.addBlockEvent(x, y, z, this.blockID, 0, orientation);
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
		
		int maxlengt = this.getLengthInWorld(world, x, y, z);
		int lenght = 0;
		
//		if (lenght == 0) {	return maxlengt; }
		
		for (int i = 0; i < maxlengt; i++) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			if (y >= 255) {
				break;
			}
			
			int id = world.getBlockId(x, y, z);
			
			if (!this.isEmptyBlockBlock(id)) {
				lenght += this.getMoveBlockOnDistance (length - i, world, id, x, y, z, orientation);
				return lenght;
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
		
		if (nbMoved == this.MAX_BLOCK_MOVE || !this.isMovableBlock(id, world, x, y, z)) {
			return 0;
		}
		
		int walking = 0;
		
		for (int i = 0; i < distance; i++) {
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			

			if (y >= 255) {
				return walking;
			}
			
			int idNext = world.getBlockId(x, y, z);
			
			if (this.isEmptyBlockBlock(idNext)) {
				walking++;
			} else {
				int moving = this.getMoveBlockOnDistance(distance - i, world, idNext, x, y, z, orientation, nbMoved + 1);
				return walking + moving;
			}
		}
		
		return walking;
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
		
		boolean isPistonClosed = BlockMorePistonBase.isPiston (id);
		if (isPistonClosed) {
			isPistonClosed = !BlockMorePistonBase.isExtended(world.getBlockMetadata(x, y, z));
		}
		
		return
			BlockMorePistonBase.isEmptyBlockBlock (id) ||
			isPistonClosed ||
			(
				Block.blocksList[id].getMobilityFlag() != 2 &&
				!(Block.blocksList[id] instanceof BlockMorePistonsRod) &&
				!(Block.blocksList[id] instanceof BlockMorePistonsExtension) &&
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
		return Block.blocksList[id] instanceof BlockPistonBase;
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
				}
				
				if (currentOpened == 0) { //Le piston était fermer
					this.extend(world, x, y, z, orientation, lenghtOpened);
					extendOpen = true;
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
					
					world.setBlock(x, y, z, Block.pistonMoving.blockID, orientation, 2);
					world.setBlockTileEntity(x, y, z, new TileEntityMorePistons (this.blockID, orientation, orientation, false, true, currentOpened, true));
					
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
	 * Ouvr eun piston de la taille voulu
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param lenghtOpened
	 */
	private void extend(World world, int x, int y, int z, int orientation, int lenghtOpened) {
		
		int xExtension = x;
		int yExtension = y;
		int zExtension = z;
		
		ArrayList<Integer> listId       = new ArrayList<Integer>();
		ArrayList<Integer> listMetadata = new ArrayList<Integer>();
		ArrayList<Integer> sizes        = new ArrayList<Integer>();
		int size = lenghtOpened;
		
		for (int i = 0; i < (lenghtOpened + this.MAX_BLOCK_MOVE); i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
			int id = world.getBlockId(xExtension, yExtension, zExtension);
			
			// Drop les élements légés (fleurs, leviers, herbes ..)
			if (id != 0 && (Block.blocksList[id]).getMobilityFlag() == 1) {
				int metadata = world.getBlockMetadata(xExtension, yExtension, zExtension);
				float chance = (Block.blocksList[id] instanceof BlockSnow ? -1.0f : 1.0f);
				
				Block.blocksList[id].dropBlockAsItemWithChance(world, xExtension, yExtension, zExtension, metadata, chance, 0);
				world.setBlockToAir(xExtension, yExtension, zExtension);
			}
			
			if (this.isEmptyBlockBlock(id)) {
				listId.add(0);
				listMetadata.add(0);
				sizes.add(0);
				size--;
				if (size == 0) {
					break;
				}
				
			} else {
				listId.      add(world.getBlockId       (xExtension, yExtension, zExtension));
				listMetadata.add(world.getBlockMetadata (xExtension, yExtension, zExtension));
				sizes       .add(size);
			}
			
		}
		
		xExtension = x + Facing.offsetsXForSide[orientation] * lenghtOpened;
		yExtension = y + Facing.offsetsYForSide[orientation] * lenghtOpened;
		zExtension = z + Facing.offsetsZForSide[orientation] * lenghtOpened;
		
		for (int i = 0; i < listId.size(); i++) {
			
			int id = listId.get(i);
			int meta = listMetadata.get(i);
			int length = sizes.get(i);
			
			if (id != 0 && id != Block.pistonMoving.blockID) {
				xExtension += Facing.offsetsXForSide[orientation];
				yExtension += Facing.offsetsYForSide[orientation];
				zExtension += Facing.offsetsZForSide[orientation];
				
				//Déplace avec une animation les blocks
				world.setBlock(xExtension, yExtension, zExtension, Block.pistonMoving.blockID, meta, 2);
				TileEntity teBlock = new TileEntityMorePistons (id, meta, orientation, true, false, length, false);
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
	
}
