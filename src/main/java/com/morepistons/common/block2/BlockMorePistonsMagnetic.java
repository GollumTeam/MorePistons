package com.gollum.morepistons.common.block2;
//package mods.morepistons.common.block;
//
//import java.util.Random;
//
//import mods.morepistons.ModMorePistons;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.world.World;
//
//public class BlockMorePistonsMagnetic extends BlockMorePistonsBase {
//
//	/**
//	 * Constructeur
//	 * @param id
//	 * @param flag
//	 * @param texturePrefixe
//	 */
//	public BlockMorePistonsMagnetic(String registerName, boolean isSticky) {
//		super(registerName, isSticky);
//	}
//	
//	//////////////////////////
//	// Gestion des textures //
//	//////////////////////////
//	
//	@Override
//	public String getTextureKey() {
//		return super.getTextureKey().replace("magnetic", "");
//	}
//	
//	///////////////////////////////////
//	// Gestion du signal de redstone //
//	///////////////////////////////////
//	
//	/**
//	 * @param int metadata
//	 * @return int mutiplicateur
//	 */
//	public int getMagnetic (World world, int x, int y, int z) {
//		return 1;
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
//	public void applyMagnetic (World world, int x, int y, int z, int magnetic) {
//		
//	}
//	
//	////////////////////////
//	// Gestion des events //
//	////////////////////////
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
//		int magnetic = this.getMagnetic(world, x, y, z) % 8 + 1;
//		this.applyMagnetic(world, x, y, z, magnetic);
//		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
//		
//		return true;
//	}
//	
//}
