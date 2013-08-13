package mods.morePistons.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMorePistonBase extends BlockPistonBase {
	
	protected boolean isSticky;
	private boolean ignoreUpdates = false;
	public  int maxBlockMove = 12;
	protected int length = 1;
	
	protected String texturePrefixe;
	protected Icon textureFileTop;
	protected Icon textureFileTopSticky;
	protected Icon textureFileOpen;
	protected Icon textureFileSide;
	protected Icon textureFileBottom;
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonBase(int id, boolean flag, String texturePrefixe) {
		super(id, flag);
		
		ModMorePistons.log ("Create block id : " + id + " texturePrefixe : " + texturePrefixe);
		
		this.isSticky = flag;
		this.texturePrefixe = texturePrefixe;
        this.setCreativeTab(ModMorePistons.morePistonsTabs);
	}
	
	/**
	 * Affecte la longueur du piston
	 * @param length
	 * @return
	 */
	public BlockMorePistonBase setLength (int length) {
		this.length = length;
		return this;
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
		ModMorePistons.log("Register icon More Piston :\"" + key + "\"");
		return iconRegister.registerIcon(key);
	}
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
	public void registerIcons(IconRegister iconRegister) {
		this.textureFileTop       = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top"));
		this.textureFileTopSticky = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top_sticky"));
		this.textureFileOpen      = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "top"));
		this.textureFileBottom    = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "bottom"));
		this.textureFileSide      = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side"));
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getPistonExtensionTexture() {
		return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
	}
	
	public Icon getIcon(int i, int j) {
		int k = getOrientation(j);
		if (k > 5) {
			return this.textureFileTopSticky;
		}
		if (i == k) {
			if ((isExtended(j)) || (this.minX > 0.0D) || (this.minY > 0.0D)
					|| (this.minZ > 0.0D) || (this.maxX < 1.0D)
					|| (this.maxY < 1.0D) || (this.maxZ < 1.0D)) {
				return this.textureFileOpen;
			}

			return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
		}

		return i != Facing.oppositeSide[k] ? this.textureFileSide : this.textureFileBottom;
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	public void onBlockDestroyedByPlayer (World world, int x, int y, int z, int metadata) {
		
		int orientation = this.getOrientation(metadata);
		int id = MorePistons.pistonRod.blockID;
		while (id  == MorePistons.pistonRod.blockID) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			id = world.getBlockId(x, y, z);

			if (
				id  == MorePistons.pistonRod.blockID ||
				id  == MorePistons.pistonExtension.blockID
			) {
				world.destroyBlock(x, y, z, false);
			}
			
		}
	}
	
	/**
	 * Called when the block is placed in the world.
	 * Envoie un event qunad on place un block sur le monde
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityLiving, ItemStack itemStack) {
		int orientation  = determineOrientation(world, x, y, z, entityLiving);
		world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
		
		if (!this.ignoreUpdates) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		if (!this.ignoreUpdates) {
			this.updatePistonState(world, x, y, z);
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	public void onBlockAdded(World world, int x, int y, int z) {
		return;
	}
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	protected void updatePistonState(World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		int orientation = getOrientation(metadata);
		
		// Normalement c'est impossible. Il n'y a que 6 face sur un block. Mais il le mette de partout
		// On vera bien
		if (orientation == 7) {
			return;
		}
		
		boolean powered = this.isIndirectlyPowered(world, x, y, z, orientation);
		boolean extended = isExtended(metadata);
		
		// Si redstone active et piston fermer alors il faut ouvrir
		if (powered) {
			world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
			world.addBlockEvent(x, y, z, this.blockID, 0, orientation);
			
		// Si redstone eteinte et piston ouvert alors il faut fermer
		} else if (!powered && extended) {
			world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
			world.addBlockEvent(x, y, z, this.blockID, 1, orientation);
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
	public int getOpenedLenght (World world, int x, int y, int z, int orientation) {
		
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
			
			id       = world.getBlockId(x, y, z);
			metadata = world.getBlockMetadata(x, y, z);
			blockOrentation = this.getOrientation(metadata);
			
			moving = false;
			if (id == Block.pistonMoving.blockID) {
				TileEntity tileEntity= world.getBlockTileEntity(x, y, z);
				if (tileEntity instanceof TileEntityMorePistons) {
					int idMoving = ((TileEntityMorePistons)tileEntity).storedBlockID;
					if (
						idMoving == MorePistons.pistonRod.blockID ||
						idMoving == MorePistons.pistonExtension.blockID
					) {
						moving = true;
					}
					
				}
			}
			
		} while (
			(
				moving ||
				id == MorePistons.pistonRod.blockID ||
				id == MorePistons.pistonExtension.blockID
			) &&
			orientation == blockOrentation
		);
		
		return lenght;
	}

	/**
	 * Caclcule la longueur d'ouverture maximal que pourra effectuer le piston sans collision blocante
	 * @param World world
	 * @param int x
	 * @param int y
	 * @param int z
	 * @param int orientation
	 * @return int
	 */
	public int getMaximalOpenedLenght (World world, int x, int y, int z, int orientation, int length) {
		
		int max = 0;
		
		for (int i = 0; i < length; i++) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			if (y >= 255) {
				return max;
			}
			
			int id = world.getBlockId(x, y, z);
			
			if (id != 0) {
				
				// Le block ne bouge pas on ne peut plus avancer
				if (!this.canPushBlock(id, world, x, y, z, true)) {
					break;
				}
				
				// Ce n'est pas un morceau du piston déjà ouvert
				if (
					id != MorePistons.pistonExtension.blockID &&
					id != MorePistons.pistonRod.blockID&&
					id != Block.pistonMoving.blockID
				) {
					int walking = this.canMoveBlockOnDistance(length - i, world, id, x, y, z, orientation);
					max += walking;
					break;
				}
			}
			
			max++;
			
		}
		
		
		return max;
	}
	
	/**
	 * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	 * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	 */
	public boolean onBlockEventReceived(World world, int x, int y, int z, int idEvent, int orientation) {
		
		this.ignoreUpdates = true;
		boolean extendOpen = false;
		boolean extendClose = false;
		int openedLenght = this.getOpenedLenght(world, x, y, z, orientation); //On recupère l'ouverture actuel du piston
		
		if (idEvent == 0 && this.length != 0) { // Demande une ouverture du piston
			
			// Si le piston est fermer: on ouvre autant que l'on peu
			// Si le piston est ouvert mais que la longueur du piston est plus courte que l'ouverture actuel: On Retracte le piston à la longueur max du piston
			// Si le piston est ouvert m'et n'a aps atteint la longueur max on tente d'ouvrir le piston au maximum posible jusqu'a un obstacle
			
			if (openedLenght == this.length) {
				this.ignoreUpdates = false;
				return true;
			}
			
			if (openedLenght == 0) { //Le piston était fermer
				
				int maxOpen = this.getMaximalOpenedLenght(world, x, y, z, orientation, this.length); //On recupère l'ouverture actuel du piston
				
				if (maxOpen > 0) {
					if (this.tryExtend(world, x, y, z, orientation, maxOpen)) {
						extendOpen = true;
					}
				} else {
					world.setBlockMetadataWithNotify (x, y, z, orientation, 2);
				}
				
			} else  if (this.length > openedLenght) {
				
				int x2 = x + Facing.offsetsXForSide[orientation] * openedLenght;
				int y2 = y + Facing.offsetsYForSide[orientation] * openedLenght;
				int z2 = z + Facing.offsetsZForSide[orientation] * openedLenght;
				
				int maxOpen = this.getMaximalOpenedLenght(world, x2, y2, z2, orientation, this.length - openedLenght); //On recupère l'ouverture actuel du pistonl
				
				if (maxOpen > 0) {
					
					world.setBlock(x2, y2, z2, MorePistons.pistonRod.blockID, orientation, 2);
					if (this.tryExtend(world, x2, y2, z2, orientation, maxOpen)) {
						extendOpen = true;
					}
				}
				
			} else {
				
				extendClose = true;
				int diff = openedLenght - this.length;
				
				int x2 = x + Facing.offsetsXForSide[orientation] * (openedLenght);
				int y2 = y + Facing.offsetsYForSide[orientation] * (openedLenght);
				int z2 = z + Facing.offsetsZForSide[orientation] * (openedLenght);
				
				for (int i = 0; i < diff; i++) {

					world.setBlock(x2, y2, z2, 0, 0, 2);
					
					x2 -= Facing.offsetsXForSide[orientation];
					y2 -= Facing.offsetsYForSide[orientation];
					z2 -= Facing.offsetsZForSide[orientation];
				}
				
				
				world.setBlock(x2, y2, z2, Block.pistonMoving.blockID, orientation, 2);
				TileEntity teExtension = ModMorePistons.getTileEntity(MorePistons.pistonExtension.blockID, orientation, orientation, true, false, -diff);
				world.setBlockTileEntity(x2, y2, z2, teExtension);
				
				this.retracSticky(world, x2, y2, z2, orientation, diff);
				
			}
			
			
			
		} else {  // Demande une fermeture du piston
			
			// Debut de l'effet de fermeture adapter pour tous les pistons
			// On calcule la taille du piston et on retacte se que l'on peu
			
			int x2 = x;
			int y2 = y;
			int z2 = z;
			
			TileEntity tileentity = world.getBlockTileEntity(x + Facing.offsetsXForSide[orientation], y + Facing.offsetsYForSide[orientation], z + Facing.offsetsZForSide[orientation]);
			
			if (tileentity instanceof TileEntityPiston) {
				((TileEntityPiston)tileentity).clearPistonTileEntity();
			}
			
			world.setBlock(x, y, z, Block.pistonMoving.blockID, orientation, 2);
			world.setBlockTileEntity(x, y, z, ModMorePistons.getTileEntity (this.blockID, orientation, orientation, false, true, openedLenght, true));
			
			if (openedLenght != 0) { // Sinon le piston risque de disparaitre
				x2 += Facing.offsetsXForSide[orientation] * openedLenght;
				y2 += Facing.offsetsYForSide[orientation] * openedLenght;
				z2 += Facing.offsetsZForSide[orientation] * openedLenght;
	
				world.setBlock(x2, y2, z2, 0, orientation, 2);
				world.setBlockMetadataWithNotify (x2, y2, z2, 0, 2);
				
				extendClose = true;
			}
			
			this.retracSticky(world, x, y, z, orientation, openedLenght);
			
		}
		
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
		
		return true;
	}
	
	/**
	 * Retracte le block qui ets collé au piston si le piston est un sticky piston
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param length
	 */
	protected void retracSticky (World world, int x, int y, int z, int orientation, int length) {
		if (this.isSticky) {
			
			int x2 = x + Facing.offsetsXForSide[orientation] * (length + 1);
			int y2 = y + Facing.offsetsYForSide[orientation] * (length + 1);
			int z2 = z + Facing.offsetsZForSide[orientation] * (length + 1);
			
			int id = world.getBlockId(x2, y2, z2);
			
			if (
					id != 0 &&
					id != Block.waterMoving.blockID &&
					id != Block.waterStill.blockID &&
					id != Block.lavaMoving.blockID &&
					id != Block.lavaStill.blockID
				) {
					int blockMeta = world.getBlockMetadata(x2, y2, z2);
					
					world.setBlock(x2, y2, z2, 0);
					world.setBlockMetadataWithNotify (x2, y2, z2, 0, 2);
					
					world.setBlock(
						x + Facing.offsetsXForSide[orientation],
						y + Facing.offsetsYForSide[orientation],
						z + Facing.offsetsZForSide[orientation], 
						Block.pistonMoving.blockID, 
						orientation, 
						2
					);
					world.setBlockTileEntity(
						x + Facing.offsetsXForSide[orientation],
						y + Facing.offsetsYForSide[orientation],
						z + Facing.offsetsZForSide[orientation],
						ModMorePistons.getTileEntity (id, blockMeta, orientation, false, false, length)
					);
				}
		}
	}
	
	/**
	 * checks the block to that side to see if it is indirectly powered.
	 */
	protected boolean isIndirectlyPowered(World world, int x, int y, int z, int orientation) {
		if ((orientation != 0) && (world.getIndirectPowerOutput (x, y - 1, z, 0))) {
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
	 * Regarde si on peu déplacé un pistont sur la distance voulu
	 * @param distance
	 * @param world
	 * @param id
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @return
	 */
	private int canMoveBlockOnDistance (int distance, World world, int id, int x, int y, int z, int orientation) {
		return this.canMoveBlockOnDistance(distance, world, id, x, y, z, orientation, 1);
	}
	
	/**
	 * Regarde si on peu déplacé un pistont sur la distance voulu
	 * @param distance
	 * @param world
	 * @param id
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param walking
	 * @return
	 */
	private int canMoveBlockOnDistance (int distance, World world, int id, int x, int y, int z, int orientation, int moved) {
		
		if (moved == 12) {
			return 0;
		}
		
		int walking = 0;
		
		if (
			this.canPushBlock(id, world, x, y, z, true) &&
			id != MorePistons.pistonExtension.blockID &&
			id != MorePistons.pistonRod.blockID &&
			id != Block.pistonMoving.blockID
		) {
			
			for (int i = 0; i < distance; i++) {
				
				x += Facing.offsetsXForSide[orientation];
				y += Facing.offsetsYForSide[orientation];
				z += Facing.offsetsZForSide[orientation];
				
				if (y >= 255) {
					return walking;
				}
				
				int idNext = world.getBlockId(x, y, z);
				
				if (idNext == 0 || Block.blocksList[id].getMobilityFlag() == 1) {
					walking++;
					continue;
				}
				
				
				int moving = this.canMoveBlockOnDistance(distance - i, world, idNext, x, y, z, orientation, moved + 1);
				return walking + moving;
				
			}
		}
		
		return walking;
	}
	
	/**
	 * returns true if the piston can push the specified block
	 */
	private static boolean canPushBlock(int id, World world, int x, int y, int z, boolean par5) {
		if (
			id == Block.obsidian.blockID || 
			id == MorePistons.pistonRod.blockID || 
			id == MorePistons.pistonExtension.blockID
		) { // Si on a de l'obsidienne on ne peut deplacer
			return false;
		} else {
			if (id != Block.pistonBase.blockID && id != Block.pistonStickyBase.blockID) {
				if (Block.blocksList[id].getBlockHardness(world, x, y, z) == -1.0F) {
					return false;
				}
				
				if (Block.blocksList[id].getMobilityFlag() == 2) {
					return false;
				}
				
				if (Block.blocksList[id].getMobilityFlag() == 1) {
					if (!par5)  {
						return false;
					}
					
					return true;
				}
			} else if (isExtended(world.getBlockMetadata(x, y, z))) {
				return false;
			}
			
			return !world.blockHasTileEntity(x, y, z);
		}
	}
	
	/**
	 * Ouvre le piston de la longueur choisi
	 * Déplace les objets devant le piston
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param orientation
	 * @param lenghtMove
	 * @return
	 */
	private boolean tryExtend(World world, int x, int y, int z, int orientation, int lenghtMove) {
	
		
		
		int x1 = x;
		int y1 = y;
		int z1 = z;
		
		int xExtension = x1;
		int yExtension = y1;
		int zExtension = z1;
		
		for( int i = 0;i < lenghtMove; i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
		}
		
		int listId[]       = {0, 0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int listMetadata[] = {0, 0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int sizes[]        = {0, 0 ,0 ,0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int pos = 0;
		int size = lenghtMove;
		
		for (int i = 0; i < 14; i++) {
			
			x1 += Facing.offsetsXForSide[orientation];
			y1 += Facing.offsetsYForSide[orientation];
			z1 += Facing.offsetsZForSide[orientation];
			
			int id = world.getBlockId(x1, y1, z1);
			boolean skip = false;
			
			if (id == Block.pistonMoving.blockID) {
				TileEntity tileEntity= world.getBlockTileEntity(x1, y1, z1);
				int metadata = world.getBlockMetadata(x1, y1, z1);
				if (tileEntity instanceof TileEntityMorePistons) {
					int idMoving = ((TileEntityMorePistons)tileEntity).storedBlockID;
					if (!ModMorePistons.isPistonId(idMoving) && this.getOrientation(metadata) == orientation) {
						skip = true;
					}
					
				}
			}
			
			if (
				id != 0 && 
				id != MorePistons.pistonExtension.blockID && 
				id != MorePistons.pistonRod.blockID &&
				id != Block.waterMoving.blockID &&
				id != Block.waterStill.blockID &&
				id != Block.lavaMoving.blockID &&
				!skip
			) {
				listId[pos] = id;
				listMetadata[pos] = world.getBlockMetadata (x1, y1, z1);
				sizes[pos] = size;
				pos++;
			} else {
				size--;
				if (size == 0) {
					break;
				}
			}
			
		}
		
		
		x1 = xExtension;
		y1 = yExtension;
		z1 = zExtension;
		
		for (int i = 0; i < pos; i++) {
			int id = listId[i];
			int meta = listMetadata[i];
			int length = sizes[i];
			
			if (id != 0 && id != Block.pistonMoving.blockID) {
				
				x1 += Facing.offsetsXForSide[orientation];
				y1 += Facing.offsetsYForSide[orientation];
				z1 += Facing.offsetsZForSide[orientation];
				
				// Ajout d'une animation
				world.setBlock(x1, y1, z1, Block.pistonMoving.blockID, meta, 2);
				TileEntity teBlock = ModMorePistons.getTileEntity(id, meta, orientation, true, false, length);
				world.setBlockTileEntity(x1, y1, z1, teBlock);
				
			}
		}
		
		// Ajout d'une animation
		int metadata = orientation | (this.isSticky ? 0x8 : 0);
		
		world.setBlock(xExtension, yExtension, zExtension, Block.pistonMoving.blockID, metadata, 2);
		TileEntity teExtension = ModMorePistons.getTileEntity(MorePistons.pistonExtension.blockID, metadata, orientation, true, false, lenghtMove);
		world.setBlockTileEntity(xExtension, yExtension, zExtension, teExtension);
		
		return true;
	}
	
}
