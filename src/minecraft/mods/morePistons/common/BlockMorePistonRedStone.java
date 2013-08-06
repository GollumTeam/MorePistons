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
	

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {

		int multi = this.getMutiplicateur (par1IBlockAccess, par2, par3, par4);
		
		switch (multi) {
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
		
		
        return this.getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
	
	///////////////////////////////////
	// Gestion du signal de redstone //
	///////////////////////////////////
	
	/**
	 * @param int metadata
	 * @return int mutiplicateur
	 */
	public int getMutiplicateur (IBlockAccess iBlockAccess, int x, int y, int z) {
		
		TileEntity tileEntity = iBlockAccess.getBlockTileEntity(x, y, z);
		System.out.println(tileEntity);
		if (tileEntity instanceof TileEntityRedStonePiston) {
			ModMorePistons.log("COOOOOOOOOOOOOOOOOOOOOOOOOOOLLLLLLLLLLLLLLl");
			return ((TileEntityRedStonePiston)tileEntity).multiplicateur;
		} else {
			ModMorePistons.log("NONNNNN");
		}
		
		return  1;
	}
	
	/**
	 * 
	 * @param World world
	 * @param int x
	 * @param int y
	 * @param int z
	 * @param int multi
	 */
	public void setMutiplicateur (World world, int x, int y, int z, int multi) {

		int meta1 = world.getBlockMetadata(x, y, z);
		int orient = getOrientation(meta1);
		int omulti = getMutiplicateur(world, x, y, z);
		
		int metadata = (world.getBlockMetadata(x, y, z) & 0xF) + ((multi-1) << 4);
		ModMorePistons.log("meta111: "+metadata);
		
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		ModMorePistons.log("tileEntity: "+tileEntity);
		if (tileEntity instanceof TileEntityRedStonePiston) {
			world.removeBlockTileEntity(x, y, z);
		}
		
		TileEntityRedStonePiston tileEntityRSP = new TileEntityRedStonePiston();
		tileEntityRSP.multiplicateur = (multi-1);
		world.setBlockTileEntity(x, y, z,  tileEntityRSP);
		//world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
		
		metadata = world.getBlockMetadata(x, y, z);
		ModMorePistons.log("meta222: "+metadata);
//		**
//		int multi2    = getMutiplicateur(metadata);
//		int orient2   = getOrientation(metadata);
//		
//		ModMorePistons.log("meta1: "+meta1);
//		ModMorePistons.log("orient1: "+orient);
//		ModMorePistons.log("multi1: "+omulti);
//		
//		ModMorePistons.log("============");
//		ModMorePistons.log("new multi: "+multi);
//		ModMorePistons.log("new multi << 4: "+((multi-1) << 4));
//		ModMorePistons.log("============");
//		
//		ModMorePistons.log("meta2: "+metadata);
//		ModMorePistons.log("orient2: "+orient2);
//		ModMorePistons.log("multi2: "+multi2);
//
//		ModMorePistons.log("============");
//		ModMorePistons.log("=          =");
//		ModMorePistons.log("============");
	}

	////////////////////////
	// Gestion des events //
	////////////////////////
	
	
	/**
	 * Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	 * entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	 */
	public boolean onBlockEventReceived(World world, int x, int y, int z, int fermer, int orientation) {
		
		int power    = world.getBlockPowerInput(x, y, z);		
		int multi    = getMutiplicateur(world, x, y, z);

		ModMorePistons.log("Power on Redstone Piston : "+power);
		ModMorePistons.log("Multiplicateur on Redstone Piston : "+multi);
		power = (power <= 0) ? 16 : power;
		power = (power > 16) ? 16 : power;
		
		this.setLength(power);
		
		return super.onBlockEventReceived (world, x, y, z, fermer, orientation);
	}
	
	/**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	
		int multi    = getMutiplicateur(par1World, par2, par3, par4) % 8 + 1;
		ModMorePistons.log("OM : "+getMutiplicateur(par1World, par2, par3, par4));
		ModMorePistons.log("NM : "+multi);
		setMutiplicateur(par1World, par2, par3, par4, multi);
    	
        par1World.playSoundEffect((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D, "random.click", 0.3F, 0.6F);
    	
        return true;
    }
	
}
