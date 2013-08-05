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

public class BlockMorePistonRedStone extends BlockMorePistonBase {
	
	private boolean isSticky;
	private boolean ignoreUpdates = false;
	public  int maxBlockMove = 12;
	
	
	// Texture attribut
	private Icon textureFileSide1;
	private Icon textureFileSide2;
	private Icon textureFileSide3;
	private Icon textureFileSide4;
	private Icon textureFileSide5;
	private Icon textureFileSide6;
	private Icon textureFileSide7;
	private Icon textureFileSide8;
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonRedStone(int id, boolean flag, String texturePrefixe) {
		super(id, flag, texturePrefixe);
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	
	/**
	 * Enregistre les textures
	 * Depuis la 1.5 on est obligé de charger les texture fichier par fichier
	 */
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
	
	public Icon getIcon(int i, int j) {
		
		int multi = this.getMutiplicateur ();
		
		switch (multi) {
			case 1: this.textureFileSide     = this.textureFileSide1; break;
			case 2: this.textureFileSide     = this.textureFileSide2; break;
			case 3: this.textureFileSide     = this.textureFileSide3; break;
			case 4: this.textureFileSide     = this.textureFileSide4; break;
			case 5: this.textureFileSide     = this.textureFileSide5; break;
			case 6: this.textureFileSide     = this.textureFileSide6; break;
			case 7: this.textureFileSide     = this.textureFileSide7; break;
			case 8: this.textureFileSide     = this.textureFileSide8; break;
			default: this.textureFileSide     = this.textureFileSide1; multi = 1; this.setMutiplicateur (1); break;
		}
		
		return super (i, j);
	}
	
	
	///////////////////////////////////
	// Gestion du signal de redstone //
	///////////////////////////////////
	
	public int getMutiplicateur () {
		return 1;
	}
	
	
	public int setMutiplicateur (int multi) {
		return;
	} 
	
	
}
