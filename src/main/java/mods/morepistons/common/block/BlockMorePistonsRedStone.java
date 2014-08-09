package mods.morepistons.common.block;

import java.util.Random;

import mods.morepistons.ModMorePistons;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMorePistonsRedStone extends BlockMorePistonsBase {
	
	private boolean ignoreUpdates = false;
	public  int maxBlockMove = 12;
	
	private int mutiplicateur = 1;
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonsRedStone(boolean flag, String texturePrefixe) {
		super(flag, texturePrefixe);
		
		setCreativeTab(null);
	}
	
	public BlockMorePistonsRedStone setMultiplicateur (int mutiplicateur) {
		this.mutiplicateur = mutiplicateur;
		return this;
	}
	
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return Item.getItemFromBlock(ModMorePistons.blockRedStonePistonBase1);
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.textureFileTop    = this.loadTexture(iconRegister, ModMorePistons.MODID.toLowerCase() + ":" + "top" + (this.isSticky ? "_sticky" : ""));
		this.textureFileOpen   = this.loadTexture(iconRegister, ModMorePistons.MODID.toLowerCase() + ":" + this.texturePrefixe + "top");
		this.textureFileBottom = this.loadTexture(iconRegister, ModMorePistons.MODID.toLowerCase() + ":" + this.texturePrefixe + "bottom");
		this.textureFileSide   = this.loadTexture(iconRegister, ModMorePistons.MODID.toLowerCase() + ":" + this.texturePrefixe + "side_"+this.mutiplicateur);
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
		BlockMorePistonsRedStone newBlock = null;
		
		if (this.isSticky) {
			
			switch (multi) {
				case 1:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase1;  break;
				case 2:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase2;  break;
				case 3:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase3;  break;
				case 4:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase4;  break;
				case 5:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase5;  break;
				case 6:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase6;  break;
				case 7:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase7;  break;
				case 8:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase8;  break;
				default: newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStoneStickyPistonBase1;  break;
			}
		} else {
			switch (multi) {
				case 1:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase1;  break;
				case 2:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase2;  break;
				case 3:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase3;  break;
				case 4:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase4;  break;
				case 5:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase5;  break;
				case 6:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase6;  break;
				case 7:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase7;  break;
				case 8:  newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase8;  break;
				default: newBlock = (BlockMorePistonsRedStone) ModMorePistons.blockRedStonePistonBase1;  break;
			}
		}
		world.setBlock(x, y, z, newBlock);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
		
		newBlock.updatePistonState(world, x, y, z);
		
	}

	////////////////////////
	// Gestion des events //
	////////////////////////
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
		int power = 0;
		int multi    = getMutiplicateur();
		
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
		int orientation = this.getPistonOrientation(metadata);
		if (faceClicked == orientation) {
			return false;
		}
		
		int multi = getMutiplicateur() % 8 + 1;
		applyMutiplicateur(world, x, y, z, multi);
		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
		
		return true;
	}
	
}
