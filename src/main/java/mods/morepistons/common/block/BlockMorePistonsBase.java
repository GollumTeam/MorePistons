package mods.morepistons.common.block;

import static mods.morepistons.ModMorePistons.log;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.gollum.core.tools.helper.blocks.HBlockContainer;
import mods.gollum.core.tools.helper.blocks.HBlockPistonBase;
import mods.morepistons.ModMorePistons;
import mods.morepistons.client.ClientProxyMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import mods.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMorePistonsBase extends HBlockContainer {
	
	protected boolean isSticky;
	private   int     length = 1;
	private   float   speed  = 0.05F;
	protected boolean ignoreUpdates = false;
	
	protected IIcon iconTop;
	protected IIcon iconOpen;
	protected IIcon iconBottom;
	protected IIcon iconSide;

	protected String suffixTop    = "_top";
	protected String suffixSticky = "_sticky";
	protected String suffixOpen   = "_open";
	protected String suffixBottom  = "_bottom";
	protected String suffixSide   = "_side";
	
	public BlockMorePistonsBase(String registerName, boolean isSticky) {
		super(registerName, Material.piston);
		
		this.isSticky = isSticky;
		this.setCreativeTab(ModMorePistons.morePistonsTabs);
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMorePistonsPiston();
	}
	
	/**
	 * Affecte la taille du piston
	 * @param length
	 * @return
	 */
	public BlockMorePistonsBase setLength(int length) {
		this.length = length;
		return this;
	}
	
	/**
	 * Affecte la vitesse du piston
	 * @param length
	 * @return
	 */
	public BlockMorePistonsBase setSpeed(float speed) {
		this.speed = speed;
		return this;
	}
	
	public int getLengthInWorld(World world, int x, int y, int z, int orientation) {
		return this.length;
	}
	
	public float getSpeedInWorld(World world, int x, int y, int z, int orientation) {
		return this.speed;
	}
	
	/**
	 * Block maximal que peux pouser le piston
	 * @return
	 */
	public int getMaxBlockMove () {
		return ModMorePistons.config.numberMovableBlockWithDefaultPiston;
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	/**
	 * Nom d'enregistrement du mod
	 */
	@Override
	public String getTextureKey() {
		return super.getTextureKey().replace("sticky", "");
	}
	
	public void registerBlockIcons(IIconRegister iconRegister) {
		
		if (helper.vanillaTexture) {
			super.registerBlockIcons(iconRegister);
			return;
		};
		
		this.registerBlockIconsTop   (iconRegister);
		this.registerBlockIconsOpen  (iconRegister);
		this.registerBlockIconsBottom(iconRegister);
		this.registerBlockIconsSide  (iconRegister);
		
	}
	protected void registerBlockIconsTop   (IIconRegister iconRegister) { this.iconTop    = helper.loadTexture(iconRegister, "top" + (this.isSticky ? suffixSticky : ""), true); }
	protected void registerBlockIconsOpen  (IIconRegister iconRegister) { this.iconOpen   = helper.loadTexture(iconRegister, suffixOpen);   }
	protected void registerBlockIconsBottom(IIconRegister iconRegister) { this.iconBottom = helper.loadTexture(iconRegister, suffixBottom); }
	protected void registerBlockIconsSide  (IIconRegister iconRegister) { this.iconSide   = helper.loadTexture(iconRegister, suffixSide);   }
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
		if (orientation > 5) {
			return this.iconTop;
		}
		if (side == orientation) {
			if (
				(BlockPistonBase.isExtended(metadata)) ||
				(this.minX > 0.0D) || (this.minY > 0.0D) || (this.minZ > 0.0D) ||
				(this.maxX < 1.0D) || (this.maxY < 1.0D) || (this.maxZ < 1.0D)
			) {
				return this.iconOpen;
			}
			
			return this.iconTop;
		}
		
		return side != Facing.oppositeSide[orientation] ? this.iconSide : this.iconBottom;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getPistonExtensionTexture() {
		return this.iconTop;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ClientProxyMorePistons.idMorePistonsBaseRenderer;
	}
	
	////////////////////////////
	// Gestion des collisions //
	////////////////////////////
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		
		if (BlockPistonBase.isExtended(metadata)) {
			switch (BlockPistonBase.getPistonOrientation(metadata)) {
				case 0:
					this.setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 1:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
					break;
				case 2:
					this.setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
					break;
				case 3:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
					break;
				case 4:
					this.setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 5:
					this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
				}
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	/**
	 * Called when the block is placed in the world. Envoie un event qunad on
	 * place un block sur le monde
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		int orientation = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
		world.setBlockMetadataWithNotify(x, y, z, orientation, 2);
		
		log.debug("onBlockPlacedBy : "+x+", "+y+", "+z);
		
		if (!world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		log.debug("onNeighborBlockChange : "+x+", "+y+", "+z);
		
		if (!world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
	}
	
	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		log.debug("onBlockAdded : "+x+", "+y+", "+z);
		
		if (!world.isRemote) {
			this.updatePistonState(world, x, y, z);
		}
		return;
	}
	
	/**
	 * handles attempts to extend or retract the piston.
	 */
	public void updatePistonState(World world, int x, int y, int z) {
		if (world.isRemote) {
			return;
		}
		
		int     metadata    = world.getBlockMetadata(x, y, z);
		int     orientation = BlockPistonBase.getPistonOrientation(metadata);
		boolean powered     = this.isIndirectlyPowered(world, x, y, z, orientation);
		
		log.debug("updatePistonState : ", x, y, z, "powered="+powered);
		
		if (metadata == 7) {
			return;
		}
		
		if (powered) {
			world.setBlockMetadataWithNotify (x, y, z, orientation | 0x8, 2);
			world.addBlockEvent(x, y, z, this, 5, orientation);
		} else {
			world.setBlockMetadataWithNotify (x, y, z, orientation, 2);
			world.addBlockEvent(x, y, z, this, 0, orientation);
		}
	}
	
	/**
	* Called when the block receives a BlockEvent - see World.addBlockEvent. By default, passes it on to the tile
	* entity at this location. Args: world, x, y, z, blockID, EventID, event parameter
	*/
	public boolean onBlockEventReceived(World world, int x, int y, int z, int lenghtOpened, int orientation) {
		
		if (!this.ignoreUpdates) {
			
			log.debug("onBlockEventReceived : ",x, y, z, "lenghtOpened="+lenghtOpened, "remote="+world.isRemote);
			
			this.ignoreUpdates = true;
			
			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityMorePistonsPiston) {
				TileEntityMorePistonsPiston tileEntityPiston = (TileEntityMorePistonsPiston)te;
				
				int currentOpened = tileEntityPiston.getCurrentOpened ();
				
				if (currentOpened == lenghtOpened) {
					
					log.debug("Le piston reste immobile : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					
				} else if (currentOpened < lenghtOpened) {
					
					log.debug("Le piston s'ouvre : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					tileEntityPiston.setCurrentOpened(lenghtOpened); 
					
	//				world.setBlock(xExtension, yExtension, zExtension, Blocks.piston_extension, orientation, 2);
	//				TileEntity teExtension = new TileEntityMorePistons (ModBlocks.blockPistonExtension, metadata, orientation, lenghtOpened);
	//				TileEntity teExtension = new TileEntityMorePistonsMoving ();
	//				world.setTileEntity(xExtension, yExtension, zExtension, teExtension);
					
				} else {
					log.debug("Ls piston se ferme : ", x, y, z, "currentOpened="+currentOpened, "remote="+world.isRemote);
					tileEntityPiston.setCurrentOpened(lenghtOpened); 
				}
			}
			
			this.ignoreUpdates = false;
		}
		return true;
	}
	
	/////////////////////////
	// Ouverture du piston //
	/////////////////////////
	
	/**
	 * checks the block to that side to see if it is indirectly powered.
	 */
	protected boolean isIndirectlyPowered(World world, int x, int y, int z, int orientation) {
		if ((orientation != 0) && (world.getIndirectPowerOutput(x, y - 1, z, 0))) {
			return true;
		}
		if ((orientation != 1) && (world.getIndirectPowerOutput(x, y + 1, z, 1))) {
			return true;
		}
		if ((orientation != 2) && (world.getIndirectPowerOutput(x, y, z - 1, 2))) {
			return true;
		}
		if ((orientation != 3) && (world.getIndirectPowerOutput(x, y, z + 1, 3))) {
			return true;
		}
		if ((orientation != 5) && (world.getIndirectPowerOutput(x + 1, y, z, 5))) {
			return true;
		}
		if ((orientation != 4) && (world.getIndirectPowerOutput(x - 1, y, z, 4))) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y, z, 0)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y + 2, z, 1)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y + 1, z - 1, 2)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x, y + 1, z + 1, 3)) {
			return true;
		}
		if (world.getIndirectPowerOutput(x - 1, y + 1, z, 4)) {
			return true;
		}
		boolean flag = world.getIndirectPowerOutput(x + 1, y + 1, z, 5);
		if (flag) {
			return true;
		}
		
		return false;
	}
	
}
