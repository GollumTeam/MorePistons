package mods.morePistons.common;

import java.util.List;
import java.util.Random;

import javax.naming.ldap.HasControls;

import scala.collection.generic.Clearable;
import scala.collection.mutable.HashMap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMorePistonRedStone extends BlockMorePistonBase {
	
	private boolean isSticky;
	private boolean ignoreUpdates = false;
	public  int maxBlockMove = 12;
	
	private Icon textureFileSide1;
	private Icon textureFileSide2;
	private Icon textureFileSide3;
	private Icon textureFileSide4;
	private Icon textureFileSide5;
	private Icon textureFileSide6;
	private Icon textureFileSide7;
	private Icon textureFileSide8;
	
	private int mutiplicateur = 1;
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonRedStone(int id, boolean flag, String texturePrefixe) {
		super(id, flag, texturePrefixe);
		
		setCreativeTab(null);
	}
	
	public BlockMorePistonRedStone setMultiplicateur (int mutiplicateur) {
		this.mutiplicateur = mutiplicateur;
		return this;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return MorePistons.redStonePistonBase1.blockID;
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.textureFileTop       = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top"));
		this.textureFileTopSticky = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top_sticky"));
		this.textureFileOpen      = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "top"));
		this.textureFileBottom    = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "bottom"));
		this.textureFileSide1     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_1"));
		this.textureFileSide2     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_2"));
		this.textureFileSide3     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_3"));
		this.textureFileSide4     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_4"));
		this.textureFileSide5     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_5"));
		this.textureFileSide6     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_6"));
		this.textureFileSide7     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_7"));
		this.textureFileSide8     = this.loadTexture(iconRegister, ModMorePistons.getTexture (this.texturePrefixe + "side_8"));
		
		this.textureFileSide     = this.textureFileSide1;
	}
	
	
	public Icon getIcon (int i, int j) {
		
		switch (this.getMutiplicateur ()) {
			case 1: this.textureFileSide     = this.textureFileSide1;  break;
			case 2: this.textureFileSide     = this.textureFileSide2;  break;
			case 3: this.textureFileSide     = this.textureFileSide3;  break;
			case 4: this.textureFileSide     = this.textureFileSide4;  break;
			case 5: this.textureFileSide     = this.textureFileSide5;  break;
			case 6: this.textureFileSide     = this.textureFileSide6;  break;
			case 7: this.textureFileSide     = this.textureFileSide7;  break;
			case 8: this.textureFileSide     = this.textureFileSide8;  break;
			default: this.textureFileSide     = this.textureFileSide1; break;
		}
		
		return super.getIcon(i, j);
	}
	
	
	///////////////////////////////////
	// Gestion du signal de redstone //
	///////////////////////////////////
	
	/**
	 * @param int metadata
	 * @return int mutiplicateur
	 */
	public int getMutiplicateur () {
		return this.mutiplicateur;
	}
	
	/**
	 * Applique le bon multiplicateur
	 * @param par1World
	 * @param par2
	 * @param par3
	 * @param par4
	 * @param multi
	 */
	public void applyMutiplicateur (World world, int x, int y, int z, int multi) {
		
		int metadata = world.getBlockMetadata(x, y, z);
		int newIdBlock = MorePistons.redStonePistonBase1.blockID;
		switch (multi) {
			case 1:  newIdBlock = MorePistons.redStonePistonBase1.blockID;  break;
			case 2:  newIdBlock = MorePistons.redStonePistonBase2.blockID;  break;
			case 3:  newIdBlock = MorePistons.redStonePistonBase3.blockID;  break;
			case 4:  newIdBlock = MorePistons.redStonePistonBase4.blockID;  break;
			case 5:  newIdBlock = MorePistons.redStonePistonBase5.blockID;  break;
			case 6:  newIdBlock = MorePistons.redStonePistonBase6.blockID;  break;
			case 7:  newIdBlock = MorePistons.redStonePistonBase7.blockID;  break;
			case 8:  newIdBlock = MorePistons.redStonePistonBase8.blockID;  break;
			default: newIdBlock = MorePistons.redStonePistonBase1.blockID;  break;
		}

		world.setBlock(x, y, z, newIdBlock);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}

	////////////////////////
	// Gestion des events //
	////////////////////////
	//1657-4-781
	
	/**
	 * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	 * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	 */
	public boolean onBlockEventReceived(World world, int x, int y, int z, int fermer, int orientation) {
		
		int power    = world.getBlockPowerInput(x, y, z);		
		int multi    = getMutiplicateur();
		
		power = (power <= 0) ? 16 : power;
		power = (power > 16) ? 16 : power;
		
		this.setLength(power*multi);
		
		return super.onBlockEventReceived (world, x, y, z, fermer, orientation);
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	
		int multi    = getMutiplicateur() % 8 + 1;
		applyMutiplicateur(par1World, par2, par3, par4, multi);
    	par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
    	
        return true;
    }
	
}
