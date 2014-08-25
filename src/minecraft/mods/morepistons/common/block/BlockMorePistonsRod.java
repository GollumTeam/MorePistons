package mods.morepistons.common.block;

import java.util.List;
import java.util.Random;

import mods.gollum.core.tools.helper.blocks.HBlock;
import mods.morepistons.ModMorePistons;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMorePistonsRod extends HBlock {
	//private int headTexture;
	
	private Icon iconV;
	private Icon iconH;
	public boolean northSouth = false;
	public boolean upDown = false;
	
	//
	public BlockMorePistonsRod(int id, String registerName) {
		super(id, registerName, Material.grass);
		//this.headTexture = -1;
		setStepSound(soundStoneFootstep);
		setHardness(0.3F);
	}
	
	// ////////////////////////
	// Gestion des textures //
	// ////////////////////////
	
	/**
	 * Enregistre les textures Depuis la 1.5 on est obligé de charger les
	 * texture fichier par fichier
	 */
	public void registerIcons(IconRegister iconRegister) {
		this.iconV = this.helper.loadTexture(iconRegister, "_v");
		this.iconH = this.helper.loadTexture(iconRegister, "_h");
	}
	
	public Icon getBlockTexture (IBlockAccess iblockaccess, int x, int y, int z, int side) {
		
		if ((side == 1 || side == 0) && this.northSouth) {
			return this.iconV;
		}
		if ((side == 4 || side == 5) && this.northSouth) {
			return this.iconH;
		}
		if ((side == 0 || side == 1 || side == 2 || side == 3) && !this.northSouth && !this.upDown) {
			return this.iconH;
		}
		if ((side == 2 || side == 3 || side == 4 || side == 5) && this.upDown == true) {
			return this.iconV;
		}
		return this.iconV;
	}

	public Icon getIcon(int i, int j) {
		int k = getDirectionMeta(j);
		return i != Facing.oppositeSide[k] ? this.iconV : this.iconH;
	}
	
	//////////////////////////////////
	// Gestion de la forme du block //
	//////////////////////////////////
	
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
	
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return true;
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////
	
	public boolean canPlaceBlockAt(World world, int i, int j, int k) {
		return false;
	}

	public boolean canPlaceBlockOnSide(World world, int i, int j, int k, int l) {
		return false;
	}

	public int quantityDropped(Random random) {
		return 0;
	}
	
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
		
		int xx= x;
		int yy= y;
		int zz= z;
		
		int orientation = this.getDirectionMeta(metadata);
		int id = ModMorePistons.blockPistonRod.blockID;
		while (id  == ModMorePistons.blockPistonRod.blockID) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			id = world.getBlockId(x, y, z);

			if (
				id  == ModMorePistons.blockPistonRod.blockID ||
				id  == ModMorePistons.blockPistonExtension.blockID
			) {
				world.destroyBlock(x, y, z, false);
			}
			
		}
		
		id = ModMorePistons.blockPistonRod.blockID;
		while (id  == ModMorePistons.blockPistonRod.blockID) {
			
			xx -= Facing.offsetsXForSide[orientation];
			yy -= Facing.offsetsYForSide[orientation];
			zz -= Facing.offsetsZForSide[orientation];
			
			id = world.getBlockId(xx, yy, zz);

			if (
				id  == ModMorePistons.blockPistonRod.blockID ||
				id  == ModMorePistons.blockPistonExtension.blockID
			) {
				world.destroyBlock(xx, yy, zz, false);
			}
			
		}
	}
	
	public static int getDirectionMeta(int i) {
		return i & 0x7;
	}
}
