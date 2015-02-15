package com.gollum.morepistons.common.block2;
//package mods.morepistons.common.block;
//
//import java.util.Random;
//
//import mods.morepistons.ModMorePistons;
//import mods.morepistons.inits.ModBlocks;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.world.World;
//
//public class BlockMorePistonsRedStone extends BlockMorePistonsBase {
//	
//	private int mutiplicateur = 1;
//	
//	/**
//	 * Constructeur
//	 * @param id
//	 * @param flag
//	 * @param texturePrefixe
//	 */
//	public BlockMorePistonsRedStone(String registerName, boolean isSticky) {
//		super(registerName, isSticky);
//		
//		setCreativeTab(null);
//	}
//	
//	public BlockMorePistonsRedStone setMultiplicateur (int mutiplicateur) {
//		this.mutiplicateur = mutiplicateur;
//		return this;
//	}
//	
//	@Override
//	public Item getItemDropped(int par1, Random par2Random, int par3) {
//		return Item.getItemFromBlock(ModBlocks.blockRedStonePistonBase1);
//	}
//	
//	//////////////////////////
//	// Gestion des textures //
//	//////////////////////////
//	
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
//	
//	
//	///////////////////////////////////
//	// Gestion du signal de redstone //
//	///////////////////////////////////
//	
//	/**
//	 * @param int metadata
//	 * @return int mutiplicateur
//	 */
//	public int getMutiplicateur () {
//		return this.mutiplicateur;
//	}
//	
//	/**
//	 * Applique le bon multiplicateur
//	 * @param par1World
//	 * @param par2
//	 * @param par3
//	 * @param par4
//	 * @param multi
//	 */
//	public void applyMutiplicateur (World world, int x, int y, int z, int multi) {
//		
//		int metadata = world.getBlockMetadata(x, y, z);
//		BlockMorePistonsRedStone newBlock = null;
//		
//		if (this.isSticky) {
//			
//			switch (multi) {
//				case 1:  newBlock = ModBlocks.blockRedStoneStickyPistonBase1;  break;
//				case 2:  newBlock = ModBlocks.blockRedStoneStickyPistonBase2;  break;
//				case 3:  newBlock = ModBlocks.blockRedStoneStickyPistonBase3;  break;
//				case 4:  newBlock = ModBlocks.blockRedStoneStickyPistonBase4;  break;
//				case 5:  newBlock = ModBlocks.blockRedStoneStickyPistonBase5;  break;
//				case 6:  newBlock = ModBlocks.blockRedStoneStickyPistonBase6;  break;
//				case 7:  newBlock = ModBlocks.blockRedStoneStickyPistonBase7;  break;
//				case 8:  newBlock = ModBlocks.blockRedStoneStickyPistonBase8;  break;
//				default: newBlock = ModBlocks.blockRedStoneStickyPistonBase1;  break;
//			}
//		} else {
//			switch (multi) {
//				case 1:  newBlock = ModBlocks.blockRedStonePistonBase1;  break;
//				case 2:  newBlock = ModBlocks.blockRedStonePistonBase2;  break;
//				case 3:  newBlock = ModBlocks.blockRedStonePistonBase3;  break;
//				case 4:  newBlock = ModBlocks.blockRedStonePistonBase4;  break;
//				case 5:  newBlock = ModBlocks.blockRedStonePistonBase5;  break;
//				case 6:  newBlock = ModBlocks.blockRedStonePistonBase6;  break;
//				case 7:  newBlock = ModBlocks.blockRedStonePistonBase7;  break;
//				case 8:  newBlock = ModBlocks.blockRedStonePistonBase8;  break;
//				default: newBlock = ModBlocks.blockRedStonePistonBase1;  break;
//			}
//		}
//		world.setBlock(x, y, z, newBlock);
//		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
//		
//		newBlock.updatePistonState(world, x, y, z);
//		
//	}
//
//	////////////////////////
//	// Gestion des events //
//	////////////////////////
//	
//	/**
//	 * Affecte la taille du piston
//	 * @param length
//	 * @return
//	 */
//	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
//		int power = 0;
//		int multi    = getMutiplicateur();
//		
//		if (this.isIndirectlyPowered(world, x, y, z, orientation)) {
//			
//			power = world.getBlockPowerInput(x, y, z);
//			
//			ModMorePistons.log.debug("getLengthInWorld: power="+power);
//			
//			power = (power <= 0) ? 16 : power;
//			power = (power > 16) ? 16 : power;
//			
//			ModMorePistons.log.debug("getLengthInWorld: power="+power);
//		}
//		
//		return power*multi;
//	}
//	
//	/**
//	 * Called upon block activation (right click on the block.)
//	 */
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int faceClicked, float par7, float par8, float par9)  {
//		int metadata = world.getBlockMetadata(x, y, z);
//		int orientation = this.getPistonOrientation(metadata);
//		if (faceClicked == orientation) {
//			return false;
//		}
//		
//		int multi = getMutiplicateur() % 8 + 1;
//		applyMutiplicateur(world, x, y, z, multi);
//		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
//		
//		return true;
//	}
//	
//}
