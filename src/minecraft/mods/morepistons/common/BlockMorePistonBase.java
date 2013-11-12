package mods.morepistons.common;

import mods.morepistons.utils.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockMorePistonBase extends Block {

	protected String name;
	protected boolean isSticky;
	protected String texturePrefixe;
	protected int length = 1;
	
	
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
	public BlockMorePistonBase(int id, boolean flag, String name, String texturePrefixe) {
		
		super(id, Material.piston);

		Logger.log ("Create block id : " + id + " texturePrefixe : " + texturePrefixe);
		
		this.isSticky = flag;
		this.texturePrefixe = texturePrefixe;
		this.name = name;
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
	
	/**
	 * Retourne le name
	 * @return String
	 */
	public String getName () {
		return this.name;
	}
	
	//////////////////////
	// Status du piston //
	//////////////////////
	
	/**
     * Determine if the metadata is related to something powered.
     * @param metadata
     * @return boolean
     */
    public static boolean isExtended (int metadata) {
        return (metadata & 8) != 0;
    }
    
    /**
     * returns an int which describes the direction the piston faces
     * @param metadata
     * @return int
     */
    public static int getOrientation(int metadata)   {
        return metadata & 7;
    }
    
    /**
     * gets the way this piston should face for that entity that placed it.
     * @return int
     */
    public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityLivingBase)  {
        if (MathHelper.abs((float)par4EntityLivingBase.posX - (float)par1) < 2.0F && MathHelper.abs((float)par4EntityLivingBase.posZ - (float)par3) < 2.0F)  {
            double d0 = par4EntityLivingBase.posY + 1.82D - (double)par4EntityLivingBase.yOffset;

            if (d0 - (double)par2 > 2.0D) {
                return 1;
            }

            if ((double)par2 - d0 > 0.0D) {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
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
		Logger.log("Register icon More Piston :\"" + key + "\"");
		return iconRegister.registerIcon(key);
	}
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
	public void registerIcons(IconRegister iconRegister) {
		this.textureFileTop       = this.loadTexture(iconRegister, "morepistons:top");
		this.textureFileTopSticky = this.loadTexture(iconRegister, "morepistons:top_sticky");
		this.textureFileOpen      = this.loadTexture(iconRegister, "morepistons:" + this.texturePrefixe + "top");
		this.textureFileBottom    = this.loadTexture(iconRegister, "morepistons:" + this.texturePrefixe + "bottom");
		this.textureFileSide      = this.loadTexture(iconRegister, "morepistons:" + this.texturePrefixe + "side");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getPistonExtensionTexture() {
		return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
	}
	
	public Icon getIcon(int i, int metadata) {
		
		int orientation = getOrientation(metadata);
		
		if (orientation > 5) {
			return this.textureFileTopSticky;
		}
		if (i == orientation) {
			if (
				(isExtended (metadata)) || 
				(this.minX > 0.0D) || (this.minY > 0.0D) ||
				(this.minZ > 0.0D) || (this.maxX < 1.0D) ||
				(this.maxY < 1.0D) || (this.maxZ < 1.0D)
			) {
				return this.textureFileOpen;
			}

			return this.getPistonExtensionTexture ();
		}

		return i != Facing.oppositeSide[orientation] ? this.textureFileSide : this.textureFileBottom;
	}	
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	/**
	 * Called when the block is placed in the world.
	 * Envoie un event qunad on place un block sur le monde
	 */
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		int orientation  = determineOrientation (world, x, y, z, entityLiving);
		world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
	}
	
}
