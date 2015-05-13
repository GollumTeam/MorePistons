package com.gollum.morepistons.common.block;

import static com.gollum.morepistons.ModMorePistons.log;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.inits.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsMagnetic extends BlockMorePistonsBase {

	private Icon[] sidesIcon = new Icon[8];
	
	/**
	 * Constructeur
	 * @param id
	 * @param flag
	 * @param texturePrefixe
	 */
	public BlockMorePistonsMagnetic(int id, String registerName) {
		super(id, registerName, true);
	}
	
	public BlockMorePistonsExtension getBlockExtention () {
		return ModBlocks.blockPistonMagneticExtention;
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	@Override
	protected void registerIconsSide  (IconRegister iconRegister) {
		for (int i = 0; i < this.sidesIcon.length; i++) {
			this.sidesIcon[i]  = helper.loadTexture(iconRegister, suffixSide+"_"+(i+1));
		}
		this.blockIcon = this.sidesIcon[0];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture (IBlockAccess world, int x, int y, int z, int side) {
		
		this.blockIcon = this.sidesIcon[(this.getStickySize(world, x, y, z)+7) % 8];
		Icon icon = super.getBlockTexture(world, x, y, z, side);
		this.blockIcon = this.sidesIcon[0];
		
		return icon;
	}
	
	@Override
	protected void registerIconsTop (IconRegister iconRegister) { 
		this.iconTop = helper.loadTexture(iconRegister, "morepistonsmagneticextension_top", true);
	}
	
	///////////////////////////////////
	// Gestion du signal de redstone //
	///////////////////////////////////
	
	public void applyStickySize (World world, int x, int y, int z, int size) {
		
		log.debug ("applyRetractSize = "+size, "remote="+world.isRemote);
		
		int metadata = world.getBlockMetadata(x, y, z);
		
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsMoving) {
			te = ((TileEntityMorePistonsMoving) te).subTe;
		}
		if (te instanceof TileEntityMorePistonsPiston) {
			((TileEntityMorePistonsPiston)te).stickySize = size;
		}
		
		world.notifyBlockOfNeighborChange(x, y, z, this.blockID);
	}

	////////////////////////
	// Gestion des events //
	////////////////////////
	
	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer par5EntityPlayer, int faceClicked, float par7, float par8, float par9)  {
		int metadata = world.getBlockMetadata(x, y, z);
		int orientation = BlockPistonBase.getOrientation(metadata);
		if (faceClicked == orientation) {
			return false;
		}
		
		int size = this.getStickySize(world, x, y, z) % 8 + 1;
		this.applyStickySize(world, x, y, z, size);
		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
		
		return true;
	}
	
}
