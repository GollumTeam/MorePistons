package mods.morepistons.common.block;

import mods.morepistons.common.ModMorePistons;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonBase extends BlockPistonBase {
	
	protected boolean isSticky;
	private boolean ignoreUpdates = false;
	public int maxBlockMove = 12;
	protected int length = 1;
	
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
	
	
	//////////////////////////
	//Gestion des textures //
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
	//Gestion des events //
	////////////////////////
	
	/**
	 * Called when the block is placed in the world. Envoie un event qunad on
	 * place un block sur le monde
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		int orientation = determineOrientation(world, x, y, z, entityLiving);
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
	 * handles attempts to extend or retract the piston.
	 */
	protected void updatePistonState(World world, int x, int y, int z) {
		
	}
	
	
	
	
}
