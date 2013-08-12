package mods.morePistons.common;

import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.material.Material; // agi;
import net.minecraft.block.Block; // world.getBlockMetadata;
import net.minecraft.block.BlockPistonBase; // aoa;
import net.minecraft.block.BlockPistonMoving; // aoc;
import net.minecraft.util.AxisAlignedBB; // aoe;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.client.entity.EntityClientPlayerMP; // ays;
import net.minecraft.client.renderer.texture.IconRegister;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity; // lq;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerCapabilities; // qv;
import net.minecraft.world.World; // yc;
import net.minecraft.world.IBlockAccess; // ym;

public class BlockPistonRod extends Block {
	//private int headTexture;
	
	private Icon textureFileV;
	private Icon textureFileH;
	private Icon textureFileTop;
	public boolean northSouth = false;
	public boolean upDown = false;
	
	//
	public BlockPistonRod(int id) {
		super(id, Material.grass);
		//this.headTexture = -1;
		setStepSound(soundStoneFootstep);
		setHardness(0.3F);
	}
	
	// ////////////////////////
	// Gestion des textures //
	// ////////////////////////
	
	/**
	 * Charge une texture et affiche dans le log
	 * 
	 * @param iconRegister
	 * @param key
	 * @return
	 */
	public Icon loadTexture(IconRegister iconRegister, String key) {
		ModMorePistons.log("Register icon More Piston :\"" + key + "\"");
		return iconRegister.registerIcon(key);
	}

	/**
	 * Enregistre les textures Depuis la 1.5 on est obligé de charger les
	 * texture fichier par fichier
	 */
	public void registerIcons(IconRegister iconRegister) {
		this.textureFileV = this.loadTexture(iconRegister, ModMorePistons.getTexture("rod_v"));
		this.textureFileH = this.loadTexture(iconRegister, ModMorePistons.getTexture("rod_h"));
	}
	
	public Icon getBlockTexture (IBlockAccess iblockaccess, int x, int y, int z, int side) {
		
		if ((side == 1 || side == 0) && this.northSouth) {
			return this.textureFileV;
		}
		if ((side == 4 || side == 5) && this.northSouth) {
			return this.textureFileH;
		}
		if ((side == 0 || side == 1 || side == 2 || side == 3) && !this.northSouth && !this.upDown) {
			return this.textureFileH;
		}
		if ((side == 2 || side == 3 || side == 4 || side == 5) && this.upDown == true) {
			return this.textureFileV;
		}
		return this.textureFileV;
	}

	public Icon getIcon(int i, int j) {
		int k = getDirectionMeta(j);
		return i != Facing.oppositeSide[k] ? this.textureFileV : this.textureFileH;
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
		int id = MorePistons.pistonRod.blockID;
		while (id  == MorePistons.pistonRod.blockID) {
			
			x += Facing.offsetsXForSide[orientation];
			y += Facing.offsetsYForSide[orientation];
			z += Facing.offsetsZForSide[orientation];
			
			id = world.getBlockId(x, y, z);

			if (
				id  == MorePistons.pistonRod.blockID ||
				id  == MorePistons.pistonExtension.blockID
			) {
				world.destroyBlock(x, y, z, false);
			}
			
		}
		
		id = MorePistons.pistonRod.blockID;
		while (id  == MorePistons.pistonRod.blockID) {
			
			xx -= Facing.offsetsXForSide[orientation];
			yy -= Facing.offsetsYForSide[orientation];
			zz -= Facing.offsetsZForSide[orientation];
			
			id = world.getBlockId(xx, yy, zz);

			if (
				id  == MorePistons.pistonRod.blockID ||
				id  == MorePistons.pistonExtension.blockID
			) {
				world.destroyBlock(xx, yy, zz, false);
			}
			
		}
	}
	
	public static int getDirectionMeta(int i) {
		return i & 0x7;
	}
}
