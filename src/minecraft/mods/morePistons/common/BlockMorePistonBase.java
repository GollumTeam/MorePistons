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
		
		setCreativeTab(CreativeTabs.tabRedstone); // Le place dans orientation 'onglet redStone
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
		ModMorePistons.log("On change Block");
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
	private void updatePistonState(World world, int x, int y, int z) {
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
		if (powered && !extended) {
			//ModMorePistons.log("Etat du piston : "+x+"x"+y+"x"+z+" metadata = "+metadata+" orientation = "+orientation);
			//ModMorePistons.log("Powered : "+powered+" Extended : "+extended);
			if (this.canExtend(world, x, y, z, orientation)) {
				world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
				world.addBlockEvent(x, y, z, this.blockID, 0, orientation);
			} else {
				//ModMorePistons.log("Le piston est bloqué");
			}
			
		// Si redstone eteinte et piston ouvert alors il faut fermer
		} else if (!powered && extended) {
			//ModMorePistons.log("Etat du piston : "+x+"x"+y+"x"+z+" metadata = "+metadata+" orientation = "+orientation);
			//ModMorePistons.log("Powered : "+powered+" Extended : "+extended);
			world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
			world.addBlockEvent(x, y, z, this.blockID, 1, orientation);
		}
		
	}
	
	/**
	 * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	 * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	 */
	public boolean onBlockEventReceived(World world, int x, int y, int z, int fermer, int orientation) {
		
		this.ignoreUpdates = true;
		
		if (fermer == 0) {
			//ModMorePistons.log("Ouvrir le piston");
			
			if (tryExtend(world, x, y, z, orientation)) {
				
				world.setBlockMetadataWithNotify(x, y, z, orientation | 0x8, 2);
				// On joue le son
				world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
				
			} else {
				ModMorePistons.log("Le piston est bloqué - mais la methode canExtend ne la pas détecté");
				world.setBlockMetadataWithNotify (x, y, z, orientation, 2);
			}
		} else {
			//ModMorePistons.log("Fermer le piston");
			
			// Debut de l'effet d'ouverture :) l'adapter pour tous les pistons
			
			TileEntity tileentity = world.getBlockTileEntity(x + Facing.offsetsXForSide[orientation], y + Facing.offsetsYForSide[orientation], z + Facing.offsetsZForSide[orientation]);
			
			if (tileentity instanceof TileEntityPiston) {
				((TileEntityPiston)tileentity).clearPistonTileEntity();
			}
			
			world.setBlock(x, y, z, Block.pistonMoving.blockID, orientation, 2);
			world.setBlockTileEntity(x, y, z, ModMorePistons.getTileEntity (this.blockID, orientation, orientation, false, true, this.length, true));
			
			if (this.isSticky) {
				
				x += Facing.offsetsXForSide[orientation];
				y += Facing.offsetsYForSide[orientation];
				z += Facing.offsetsZForSide[orientation];
				
				int destX = x;
				int destY = y;
				int destZ = z;
				
				for(int i = 0; i < this.length; i++) {
					destX += Facing.offsetsXForSide[orientation];
					destY += Facing.offsetsYForSide[orientation];
					destZ += Facing.offsetsZForSide[orientation];
				}
				
				int id = world.getBlockId(destX, destY, destZ);
				
				if (
					id != 0 &&
					id != Block.waterMoving.blockID &&
					id != Block.waterStill.blockID &&
					id != Block.lavaMoving.blockID &&
					id != Block.lavaStill.blockID
				) {
					int blockMeta = world.getBlockMetadata(destX, destY, destZ);
					
					world.setBlock(destX, destY, destZ, 0);
					world.setBlockMetadataWithNotify (destX, destY, destZ, 0, 2);
					
					world.setBlock(x, y, z, Block.pistonMoving.blockID, orientation, 2);
					world.setBlockTileEntity(x, y, z, ModMorePistons.getTileEntity (id, blockMeta, orientation, false, false, this.length));
				}

				x -= Facing.offsetsXForSide[orientation];
				y -= Facing.offsetsYForSide[orientation];
				z -= Facing.offsetsZForSide[orientation];
			}
			
			
			// Fin de l'effet de fermeture
			
			
			// On joue le son
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
		}
		
		
		this.ignoreUpdates = false;
		
		return true;
	}
	
	/**
	 * checks the block to that side to see if it is indirectly powered.
	 */
	private boolean isIndirectlyPowered(World world, int x, int y, int z, int orientation) {
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
	 * @param blockmoved
	 * @return
	 */
	private boolean canMoveBlockOnDistance (int distance, World world, int id, int x, int y, int z, int orientation, int blockmoved) {
		
		if (blockmoved == this.maxBlockMove) {
			return false;
		}
		
		if (id == 0 || Block.blocksList[id].getMobilityFlag() == 1) {
			ModMorePistons.log ("Erreur d'algo on ne devrait jamais rentrer ici");
			return false;
		}
		
		if (!this.canPushBlock(id, world, x, y, z, true)) {
			return false;
		}
		
		int x1 = x;
		int y1 = y;
		int z1 = z;
		for (int i = 0; i < distance; i++) {
			
			x1 += Facing.offsetsXForSide[orientation];
			y1 += Facing.offsetsYForSide[orientation];
			z1 += Facing.offsetsZForSide[orientation];
			int id1 = world.getBlockId(x1, y1, z1);
			if (id1 == 0 || Block.blocksList[id1].getMobilityFlag() == 1) {
				continue;
			} else {
				return canMoveBlockOnDistance (distance - i, world, id1, x1, y1, z1, orientation, blockmoved + 1);
			}
			
		}
		
		return true;
	}
	
	/**
	 * checks to see if this piston could push the blocks in front of it.
	 */
	protected boolean canExtend(World world, int x, int y, int z, int orientation) {
		
		
		int x1 = x;
		int y1 = y;
		int z1 = z;
		
		for (int i  = 0; i < this.length; i++) {
			x1 += Facing.offsetsXForSide[orientation];
			y1 += Facing.offsetsYForSide[orientation];
			z1 += Facing.offsetsZForSide[orientation];
			int id1 = world.getBlockId(x1, y1, z1);
			
			if (id1 == 0 || Block.blocksList[id1].getMobilityFlag() == 1) {
				continue;
			} else {
				return canMoveBlockOnDistance (this.length - i, world, id1, x1, y1, z1, orientation, 0);
			}
			
		}
		
		return true;
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
	
	private boolean tryExtend(World world, int x, int y, int z, int orientation) {
	
		
		
		int x1 = x;
		int y1 = y;
		int z1 = z;
		
		int xExtension = x1;
		int yExtension = y1;
		int zExtension = z1;
		
		
		for( int i = 0;i < this.length; i++) {
			
			xExtension += Facing.offsetsXForSide[orientation];
			yExtension += Facing.offsetsYForSide[orientation];
			zExtension += Facing.offsetsZForSide[orientation];
			
		}
		
		int listId[] = {0, 0 ,0 ,0 , 0, 0 , 0, 0 ,0 ,0 , 0, 0, 0, 0};
		int listMetadata[] = {0, 0 ,0 ,0 , 0, 0 , 0, 0 ,0 ,0 , 0, 0, 0, 0};
		int sizes[] = {0, 0 ,0 ,0 , 0, 0 , 0, 0 ,0 ,0 , 0, 0, 0, 0};
		int pos = 0;
		int size = this.length;
		
		for (int i = 0; i < 14; i++) {
			
			x1 += Facing.offsetsXForSide[orientation];
			y1 += Facing.offsetsYForSide[orientation];
			z1 += Facing.offsetsZForSide[orientation];
			
			int id = world.getBlockId(x1, y1, z1);
			
			if (
				id != 0 && 
				id != MorePistons.pistonExtension.blockID && 
				id != MorePistons.pistonRod.blockID &&
				id != Block.waterMoving.blockID &&
				id != Block.waterStill.blockID &&
				id != Block.lavaMoving.blockID &&
				id != Block.lavaStill.blockID
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
		TileEntity teExtension = ModMorePistons.getTileEntity(MorePistons.pistonExtension.blockID, metadata, orientation, true, false, this.length);
		world.setBlockTileEntity(xExtension, yExtension, zExtension, teExtension);
		
		return true;
	}
	
}
