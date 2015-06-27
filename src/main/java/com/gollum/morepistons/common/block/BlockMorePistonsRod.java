package com.gollum.morepistons.common.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block; // world.getBlockMetadata;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material; // agi;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity; // lq;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB; // aoe;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess; // ym;
import net.minecraft.world.World; // yc;
// world.getBlockMetadata;

import com.gollum.core.tools.helper.blocks.HBlockContainer;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsRod;

public class BlockMorePistonsRod extends HBlockContainer {
	
	private IIcon iconV;
	private IIcon iconH;
	public boolean northSouth = false;
	public boolean upDown = false;
	
	public BlockMorePistonsRod(String registerName) {
		super(registerName, Material.grass);
		setStepSound(soundTypeStone);
		setHardness(0.3F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityMorePistonsRod();
	}
	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
	/**
	 * Enregistre les textures Depuis la 1.5 on est obligé de charger les
	 * texture fichier par fichier
	 */
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconV = this.helper.loadTexture(iconRegister, "_v");
		this.iconH = this.helper.loadTexture(iconRegister, "_h");
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		int orientation = BlockPistonBase.getPistonOrientation(metadata);
		
		if (this.upDown) {
			return this.iconV;
		}
		
		if (this.northSouth) {
			if (side == 1 || side == 0) {
				return this.iconV;
			}
		}
		
		return this.iconH;
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	//////////////////////////////////
	// Gestion de la forme du block //
	//////////////////////////////////
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsRod && !((TileEntityMorePistonsRod) te).isDisplay()) {
			return;
		}
		this.setBlockBoundsBasedOnState(world.getBlockMetadata(x, y, z));
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
 	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsRod && !((TileEntityMorePistonsRod) te).isDisplay()) {
			setBlockBounds(0, 0, 0, 0, 0, 0);
			return;
		}
		
		this.setBlockBoundsBasedOnState(world.getBlockMetadata(x, y, z));
	}
	
	public void setBlockBoundsBasedOnState(int metadata) {
		
		switch (BlockPistonBase.getPistonOrientation(metadata)) {
			case 0:
				this.upDown = true;
				this.northSouth = false;
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.25F, 0.625F);
				break;
			case 1:
				this.upDown = true;
				this.northSouth = false;
				setBlockBounds(0.375F, -0.25F, 0.375F, 0.625F, 0.75F, 0.625F);
				break;
			case 2:
				this.upDown = false;
				this.northSouth = true;
				setBlockBounds(0.375F, 0.375F, 0.25F, 0.625F, 0.625F, 1.25F);
				break;
			case 3:
				this.upDown = false;
				this.northSouth = true;
				setBlockBounds(0.375F, 0.375F, -0.25F, 0.625F, 0.625F, 0.75F);
				break;
			case 4:
				this.upDown = false;
				this.northSouth = false;
				setBlockBounds(0.25F, 0.375F, 0.375F, 1.25F, 0.625F, 0.625F);
				break;
			case 5:
				this.upDown = false;
				this.northSouth = false;
				setBlockBounds(-0.25F, 0.375F, 0.375F, 0.75F, 0.625F, 0.625F);
		}
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		return false;
	}

	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsRod) {
			TileEntityMorePistonsPiston tep = ((TileEntityMorePistonsRod) te).getTileEntityPiston();
			if (tep != null) {
				world.func_147480_a(tep.xCoord, tep.yCoord, tep.zCoord, true);
			}
		}
		
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityMorePistonsRod) {
			TileEntityMorePistonsPiston tep = ((TileEntityMorePistonsRod) te).getTileEntityPiston();
			if (tep != null) {
				return;
			}
		}
		world.setBlockToAir(x, y, z);
	}
	
	/**
	 * Called when a user uses the creative pick block button on this block
	 * 
	 * @param target The full target the player is looking at
	 * @return A ItemStack to add to the player's inventory, Null if nothing should be added.
	 */
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return null;
	}
	
}
