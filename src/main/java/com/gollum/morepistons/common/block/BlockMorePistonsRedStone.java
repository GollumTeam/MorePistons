package com.gollum.morepistons.common.block;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class BlockMorePistonsRedStone extends BlockMorePistonsBase {
	
	private IIcon[] sidesIcon = new IIcon[6];
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonsRedStone(String registerName, boolean isSticky) {
		super(registerName, isSticky);
		
		setCreativeTab(null);
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	protected void registerBlockIconsSide  (IIconRegister iconRegister) {
		for (int i = 0; i < this.sidesIcon.length; i++) {
			this.sidesIcon[i]  = helper.loadTexture(iconRegister, suffixSide);
		}
	}
	
//	/**
//	 * Nom d'enregistrement du mod
//	 */
//	@Override
//	public String getTextureKey() {
//		
//		String ori = super.getTextureKey();
//		
//		// Charge toujour la meme texture quelque soit le reston
//		return ori.substring(0, ori.length() - 1);
//	}
//	
//	/**
//	 * Enregistre les textures
//	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
//	 */
//	@Override
//	public void registerBlockIcons(IIconRegister iconRegister) {
//		this.suffixSide = "_side_"+this.mutiplicateur;
//		super.registerBlockIcons(iconRegister);
//	}
	
	
	///////////////////////////////////
	// Gestion du signal de redstone //
	///////////////////////////////////
	
	/**
	 * @param int metadata
	 * @return int mutiplicateur
	 */
	public int getMutiplicateur (World world, int x, int y, int z) {
		
		int mutiplicateur = 1;
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving) te).subTe;
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			mutiplicateur = ((TileEntityMorePistonsPiston)te).mutiplicateur;
		}
		
		return mutiplicateur;
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
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving) te).subTe;
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			((TileEntityMorePistonsPiston)te).mutiplicateur = multi;
		}
	}

	////////////////////////
	// Gestion des events //
	////////////////////////
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	@Override
	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
		int power = 0;
		int multi = this.getMutiplicateur(world, x, y, z);
		
		if (this.isIndirectlyPowered(world, x, y, z, orientation)) {
			
			power = world.getBlockPowerInput(x, y, z);
			
			ModMorePistons.log.debug("getLengthInWorld: power="+power);
			
			power = (power <= 0) ? 16 : power;
			power = (power > 16) ? 16 : power;
			
			ModMorePistons.log.debug("getLengthInWorld: power="+power);
		}
		
		return power*multi;
	}
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int faceClicked, float par7, float par8, float par9)  {
		int metadata = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
		if (faceClicked == orientation) {
			return false;
		}
		
		int multi = this.getMutiplicateur(world, x, y, z) % 8 + 1;
		this.applyMutiplicateur(world, x, y, z, multi);
		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
		
		return true;
	}
	
}
