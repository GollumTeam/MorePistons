package mods.morepistons.common.block;

import java.util.List;
import java.util.Random;

import mods.gollum.core.tools.helper.blocks.HBlock;
import mods.morepistons.ModMorePistons;
import net.minecraft.block.Block; // world.getBlockMetadata;
import net.minecraft.block.material.Material; // agi;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity; // lq;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB; // aoe;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess; // ym;
import net.minecraft.world.World; // yc;

public class BlockMorePistonsRod extends HBlock {
	
	private IIcon iconV;
	private IIcon iconH;
	public boolean northSouth = false;
	public boolean upDown = false;
	
	public BlockMorePistonsRod(String registerName) {
		super(registerName, Material.grass);
		setStepSound(soundTypeStone);
		setHardness(0.3F);
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
	public IIcon getIcon(int i, int j) {
		
		int k = this.getDirectionMeta (j);
		
		if (this.upDown) {
			return this.iconV;
		}
		
		if (this.northSouth) {
			if (i == 1 || i == 0) {
				return this.iconV;
			}
		}
		
		return this.iconH;
	}
	
	//////////////////////////////////
	// Gestion de la forme du block //
	//////////////////////////////////
	
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		int l = par1World.getBlockMetadata(par2, par3, par4);
		switch (getDirectionMeta(l)) {
			case 0:
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.25F, 0.625F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				break;
		case 1:
			setBlockBounds(0.375F, -0.25F, 0.375F, 0.625F, 0.75F, 0.625F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			break;
		case 2:
			setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.625F, 1.25F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			break;
		case 3:
			setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			break;
		case 4:
			setBlockBounds(-0.25F, 0.25F, 0.25F, 1.25F, 0.75F, 0.75F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			break;
		case 5:
			setBlockBounds(-0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		
		switch (getDirectionMeta(l)) {
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
		return true;
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
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		
		int xx= x;
		int yy= y;
		int zz= z;
		
		int orientation = this.getDirectionMeta(metadata);
		Block block = ModMorePistons.blockPistonRod;
		while (block instanceof BlockMorePistonsRod) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			block = world.getBlock(x, y, z);

			if (
				block instanceof BlockMorePistonsRod ||
				block instanceof BlockMorePistonsExtension
			) {
				world.func_147480_a(x, y, z, false);
			}
			
		}
		
		block = ModMorePistons.blockPistonRod;
		while (block instanceof BlockMorePistonsRod) {
			
			xx -= Facing.offsetsXForSide[orientation];
			yy -= Facing.offsetsYForSide[orientation];
			zz -= Facing.offsetsZForSide[orientation];
			
			block = world.getBlock(xx, yy, zz);

			if (
				block instanceof BlockMorePistonsRod ||
				block instanceof BlockMorePistonsExtension
			) {
				world.func_147480_a(xx, yy, zz, false);
			}
			
		}
	}
	
	public static int getDirectionMeta(int i) {
		return i & 0x7;
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
