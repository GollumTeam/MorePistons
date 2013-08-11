package mods.morePistons.common;

import net.minecraft.block.material.Material; // agi;
import net.minecraft.block.BlockFlower; // aje;
import net.minecraft.block.BlockDeadBush; // ajr;
import net.minecraft.block.BlockContainer; // akb;
import net.minecraft.block.BlockFire; // akf;
import net.minecraft.block.BlockTallGrass; // amm;
import net.minecraft.block.Block; // amq;
import net.minecraft.block.BlockTripWireSource; // anb;
import net.minecraft.tileentity.TileEntity; // any;
import net.minecraft.block.BlockPistonBase; // aoa;
import net.minecraft.block.BlockPistonMoving; // aoc;
import net.minecraft.util.AxisAlignedBB; // aoe;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;

import java.io.PrintStream;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper; // ke;
import net.minecraft.entity.EntityLiving; // md;
import net.minecraft.entity.player.EntityPlayer; // qx;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs; // tj;
import net.minecraft.world.World; // yc;
import net.minecraft.world.IBlockAccess; // ym;

public class BlockSuperPistonBase extends BlockPistonBase {
	private boolean isSticky;
	private static boolean ignoreUpdates;
	
	private Icon textureFileTop;
	private Icon textureFileTopSticky;
	private Icon textureFileOpen;
	private Icon textureFileSide;
	private Icon textureFileBottom;

	public BlockSuperPistonBase(int par1, boolean par3) {
		super(par1, par3);
		this.isSticky = par3;
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
        this.setCreativeTab(ModMorePistons.morePistonsTabs);
	}

	
	//////////////////////////
	// Gestion des textures //
	//////////////////////////
	
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
		this.textureFileTop       = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top"));
		this.textureFileTopSticky = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top_sticky"));
		this.textureFileOpen      = this.loadTexture(iconRegister, ModMorePistons.getTexture ("super_top"));
		this.textureFileBottom    = this.loadTexture(iconRegister, ModMorePistons.getTexture ("super_bottom"));
		this.textureFileSide      = this.loadTexture(iconRegister, ModMorePistons.getTexture ("super_side"));
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getPistonExtensionTexture() {
		return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
	}

	public Icon getIcon(int i, int j) {
		int k = getOrientation(j);
		if (k > 5) {
			return this.textureFileTopSticky;
		}
		if (i == k) {
			if ((isExtended(j)) || (this.minX > 0.0D) || (this.minY > 0.0D)
					|| (this.minZ > 0.0D) || (this.maxX < 1.0D)
					|| (this.maxY < 1.0D) || (this.maxZ < 1.0D)) {
				return this.textureFileOpen;
			}

			return this.isSticky ? this.textureFileTopSticky : this.textureFileTop;
		}

		return i != Facing.oppositeSide[k] ? this.textureFileSide : this.textureFileBottom;
	}
	
	////////////////////////
	// Gestion des events //
	////////////////////////

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		return false;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLiving par5EntityLiving) {
		int var6 = determineOrientation(par1World, par2, par3, par4,
				(EntityPlayer) par5EntityLiving);
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
		if ((!par1World.isRemote) && (!ignoreUpdates)) {
			updatePistonState(par1World, par2, par3, par4);
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		if ((!par1World.isRemote) && (!ignoreUpdates)) {
			updatePistonState(par1World, par2, par3, par4);
		}
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		return;
	}

	private void updatePistonState(World par1World, int par2, int par3, int par4) {
		int var5 = par1World.getBlockMetadata(par2, par3, par4);

		int var6 = getOrientation(var5);
		boolean var7 = isIndirectlyPowered(par1World, par2, par3, par4, var6);

		if (var5 != 7) {
			if ((var7) && (!isExtended(var5))) {
				if (canExtend(par1World, par2, par3, par4, var6)) {
					par1World.setBlockMetadataWithNotify (par2, par3, par4, var6 | 0x8, 2);
					par1World.addBlockEvent(par2, par3, par4, this.blockID, 0,
							var6);
				}
			} else if ((!var7) && (isExtended(var5))) {
				par1World.setBlockMetadataWithNotify (par2, par3, par4, var6, 2);
				par1World.addBlockEvent(par2, par3, par4, this.blockID, 1, var6);
			}
		}
	}

	private boolean isIndirectlyPowered(World par1World, int par2, int par3,
			int par4, int par5) {
		
		return par1World.getIndirectPowerOutput(par2 - 1, par3 + 1,
				par4, 4) ? true
				: par1World.getIndirectPowerOutput(par2, par3 + 1,
						par4 + 1, 3) ? true
						: par1World.getIndirectPowerOutput(par2,
								par3 + 1, par4 - 1, 2) ? true
								: par1World.getIndirectPowerOutput(
										par2, par3 + 2, par4, 1) ? true
										: par1World
												.getIndirectPowerOutput(
														par2, par3, par4, 0) ? true
												: (par5 != 4)
														&& (par1World
																.getIndirectPowerOutput(
																		par2 - 1,
																		par3,
																		par4, 4)) ? true
														: (par5 != 5)
																&& (par1World
																		.getIndirectPowerOutput(
																				par2 + 1,
																				par3,
																				par4,
																				5)) ? true
																: (par5 != 3)
																		&& (par1World
																				.getIndirectPowerOutput(
																						par2,
																						par3,
																						par4 + 1,
																						3)) ? true
																		: (par5 != 2)
																				&& (par1World
																						.getIndirectPowerOutput(
																								par2,
																								par3,
																								par4 - 1,
																								2)) ? true
																				: (par5 != 1)
																						&& (par1World
																								.getIndirectPowerOutput(
																										par2,
																										par3 + 1,
																										par4,
																										1)) ? true
																						: (par5 != 0)
																								&& (par1World
																										.getIndirectPowerOutput(
																												par2,
																												par3 - 1,
																												par4,
																												0)) ? true
																								: par1World
																										.getIndirectPowerOutput(
																												par2 + 1,
																												par3 + 1,
																												par4,
																												5);
	}

	public boolean onBlockEventReceived(World par1World, int par2, int par3,
			int par4, int par5, int par6) {
		ignoreUpdates = true;

		if (par5 == 0) {
			if (tryExtend(par1World, par2, par3, par4, par6)) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4,
						par6 | 0x8, 2);
				par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D,
						par4 + 0.5D, "tile.piston.out", 0.5F,
						par1World.rand.nextFloat() * 0.25F + 0.6F);
			} else {
				par1World.setBlockMetadataWithNotify (par2, par3, par4, par6, 2);
			}
		} else if (par5 == 1) {
			TileEntity var8 = par1World.getBlockTileEntity(par2
					+ Facing.offsetsXForSide[par6], par3
					+ Facing.offsetsYForSide[par6], par4
					+ Facing.offsetsZForSide[par6]);

			if ((var8 != null) && ((var8 instanceof TileEntityMorePistons))) {
				((TileEntityMorePistons) var8).clearPistonTileEntity();
			}

			par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, par6, 2);
			par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(this.blockID, par6, par6, false, true));

			if (this.isSticky) {
				int var9 = par2 + Facing.offsetsXForSide[par6] * 2;
				int var10 = par3 + Facing.offsetsYForSide[par6] * 2;
				int var11 = par4 + Facing.offsetsZForSide[par6] * 2;
				int var12 = par1World.getBlockId(var9, var10, var11);
				int var13 = par1World.getBlockMetadata(var9, var10, var11);
				boolean var14 = false;

				if (var12 == Block.pistonMoving.blockID) {
					TileEntity var15 = par1World.getBlockTileEntity(var9,
							var10, var11);

					if ((var15 != null)
							&& ((var15 instanceof TileEntityMorePistons))) {
						TileEntityMorePistons var16 = (TileEntityMorePistons) var15;

						if ((var16.getPistonOrientation() == par6)
								&& (var16.isExtending())) {
							var16.clearPistonTileEntity();
							var12 = var16.getStoredBlockID();
							var13 = var16.getBlockMetadata();
							var14 = true;
						}
					}
				}

				boolean found = false;
				for (int a = 0; a < MorePistons.pistonList.length; a++) {
					if (var12 == MorePistons.pistonList[a]) {
						found = true;
						break;
					}
					if ((a == MorePistons.pistonList.length - 1) && (!found)) {
						found = false;
					}
				}

				if (((!var14) && (var12 > 0) && (canPushBlock(var12, par1World,
						var9, var10, var11, false))) || (found == true)) {
					par2 += Facing.offsetsXForSide[par6];
					par3 += Facing.offsetsYForSide[par6];
					par4 += Facing.offsetsZForSide[par6];
					par1World.setBlock(par2, par3, par4, Block.pistonMoving.blockID, var13, 2);
					par1World.setBlockTileEntity(par2, par3, par4, BlockPistonMoving.getTileEntity(var12, var13, par6, false, false));
					tryDependableBlocks(par1World, par2, par3, par4,
							Facing.oppositeSide[par6]);
					ignoreUpdates = false;

					ignoreUpdates = true;
				} else if (!var14) {
					ignoreUpdates = false;
					par1World.setBlock(
						par2 + Facing.offsetsXForSide[par6],
						par3 + Facing.offsetsYForSide[par6],
						par4 + Facing.offsetsZForSide[par6], 0
					);
					par1World.setBlockMetadataWithNotify (
						par2 + Facing.offsetsXForSide[par6],
						par3 + Facing.offsetsYForSide[par6],
						par4 + Facing.offsetsZForSide[par6], 0, 2
					);
					ignoreUpdates = true;
				}
			} else {
				ignoreUpdates = false;
				par1World.setBlock(
					par2 + Facing.offsetsXForSide[par6],
					par3 + Facing.offsetsYForSide[par6],
					par4 + Facing.offsetsZForSide[par6], 0
				);
				par1World.setBlockMetadataWithNotify (
					par2 + Facing.offsetsXForSide[par6],
					par3 + Facing.offsetsYForSide[par6],
					par4 + Facing.offsetsZForSide[par6], 0, 2
				);
				ignoreUpdates = true;
			}

			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.5D, par4 + 0.5D,
					"tile.piston.in", 0.5F,
					par1World.rand.nextFloat() * 0.15F + 0.6F);
		}

		ignoreUpdates = false;
		
		return true;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess,
			int par2, int par3, int par4) {

		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

		if (isExtended(var5)) {
			switch (getOrientation(var5)) {
			case 0:
				setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
			case 1:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
				break;
			case 2:
				setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
				break;
			case 3:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
				break;
			case 4:
				setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
			case 5:
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
			}
		} else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3,
				par4);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public static int getOrientation(int par0) {
		return par0 & 0x7;
	}

	public static boolean isExtended(int par0) {
		return (par0 & 0x8) != 0;
	}

	public static int determineOrientation(World world, int i, int j, int k,
			EntityPlayer entityplayer) {
		if ((MathHelper.abs((float) entityplayer.posX - i) < 2.0F)
				&& (MathHelper.abs((float) entityplayer.posZ - k) < 2.0F)) {
			double d = entityplayer.posY + 1.82D - entityplayer.yOffset;
			if (d - j > 2.0D) {
				return 1;
			}
			if (j - d > 0.0D) {
				return 0;
			}
		}
		int l = MathHelper
				.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		if (l == 0) {
			return 2;
		}
		if (l == 1) {
			return 5;
		}
		if (l == 2) {
			return 3;
		}
		return l != 3 ? 0 : 4;
	}

	private static boolean canPushBlock(int par0, World par1World, int par2,
			int par3, int par4, boolean par5) {
		boolean found = false;

		if ((par0 == MorePistons.pistonRod.blockID)
				|| (par0 == MorePistons.pistonExtension.blockID)) {
			return false;
		}
		for (int a = 0; a < MorePistons.pistonList.length; a++) {
			if (par0 == MorePistons.pistonList[a]) {
				found = true;
				break;
			}

			if (found == true) {
				if (isExtended(par1World.getBlockMetadata(par2, par3, par4))) {
					return false;
				}
			} else if (a == MorePistons.pistonList.length - 1) {
				found = false;
			}
		}

		if (!found) {
			if (par0 == Block.bedrock.blockID) {
				return false;
			}
			if ((Block.blocksList[par0].blockMaterial == Material.iron)
					|| (Block.blocksList[par0].blockID == Block.endPortal.blockID)) {
				return false;
			}
		} else {
			return true;
		}
		boolean aFlag = !(Block.blocksList[par0] instanceof BlockContainer);
		if (aFlag) {
			return true;
		}
		return false;
	}

	private static boolean canExtend(World par0World, int par1, int par2,
			int par3, int par4) {
		int var5 = par1 + Facing.offsetsXForSide[par4];
		int var6 = par2 + Facing.offsetsYForSide[par4];
		int var7 = par3 + Facing.offsetsZForSide[par4];
		int var8 = 0;

		while (var8 < 41) {
			if ((var6 <= 0) || (var6 >= 255)) {
				return false;
			}

			int var9 = par0World.getBlockId(var5, var6, var7);

			if (var9 == 0)
				break;
			if (!canPushBlock(var9, par0World, var5, var6, var7, true)) {
				return false;
			}

			if (var8 == 40) {
				return false;
			}

			var5 += Facing.offsetsXForSide[par4];
			var6 += Facing.offsetsYForSide[par4];
			var7 += Facing.offsetsZForSide[par4];
			var8++;
		}

		return true;
	}

	private boolean tryExtend(World par1World, int par2, int par3, int par4, int par5) {
		
		int x = par2 + Facing.offsetsXForSide[par5];
		int y = par3 + Facing.offsetsYForSide[par5];
		int z = par4 + Facing.offsetsZForSide[par5];
		int var9 = 0;

		while (var9 < 41) {
			if ((y <= 0) || (y >= 255)) {
				return false;
			}

			int var10 = par1World.getBlockId(x, y, z);

			if (var10 == 0)
				break;
			if (!canPushBlock(var10, par1World, x, y, z, true)) {
				return false;
			}

			if (var9 == 40) {
				return false;
			}

			x += Facing.offsetsXForSide[par5];
			y += Facing.offsetsYForSide[par5];
			z += Facing.offsetsZForSide[par5];
			var9++;
		}

		while ((x != par2) || (y != par3) || (z != par4)) {
			var9 = x - Facing.offsetsXForSide[par5];
			int var10 = y - Facing.offsetsYForSide[par5];
			int var11 = z - Facing.offsetsZForSide[par5];
			int blockID = par1World.getBlockId(var9, var10, var11);
			int blockMetadata = par1World.getBlockMetadata(var9, var10, var11);

			if ((blockID == this.blockID) && (var9 == par2) && (var10 == par3)
					&& (var11 == par4)) {
				par1World.setBlock (x, y, z, Block.pistonMoving.blockID, par5 | (this.isSticky ? 8 : 0), 2);
				//TODO BlockPistonMoving 
				par1World.setBlockTileEntity(x, y, z,  BlockPistonMoving.getTileEntity(MorePistons.pistonExtension.blockID, par5 | (this.isSticky ? 8 : 0), par5, true, false));
			} else {
				par1World.setBlock(x, y, z, Block.pistonMoving.blockID, blockMetadata, 2);
				par1World.setBlockTileEntity(x, y, z, BlockPistonMoving.getTileEntity(blockID, blockMetadata, par5, true, false));
				tryDependableBlocks(par1World, x, y, z, par5);
			}
			
			
			x = var9;
			y = var10;
			z = var11;
		}

		return true;
	}

	private void tryDependableBlocks(World world, int x, int y, int z,
			int direction) {
		int xOffset = x - Facing.offsetsXForSide[direction];
		int yOffset = y - Facing.offsetsYForSide[direction];
		int zOffset = z - Facing.offsetsZForSide[direction];
		int l1 = xOffset - Facing.offsetsXForSide[direction];
		int i2 = yOffset - Facing.offsetsYForSide[direction];
		int j2 = zOffset - Facing.offsetsZForSide[direction];
		int[] ai = { 0, 5, 3, 4, 1, 2 };

		int k2 = ai[direction];
		int l2 = world.getBlockId(l1, i2, j2);
		int i3 = world.getBlockMetadata(l1, i2, j2);

		if ((direction != 0) && (direction != 1)) {
			int blockID = world.getBlockId(xOffset, yOffset + 1, zOffset);
			int metadata = world.getBlockMetadata(xOffset, yOffset + 1, zOffset);

			if (((blockID == Block.cactus.blockID) || blockID == Block.redstoneWire.blockID)
					|| ((metadata == 5) && ((blockID == Block.torchRedstoneIdle.blockID) || (blockID == Block.torchRedstoneActive.blockID)))
					|| (blockID == Block.redstoneRepeaterIdle.blockID)
					|| (blockID == Block.redstoneRepeaterActive.blockID)
					|| (blockID == Block.pressurePlateStone.blockID)
					|| (blockID == Block.pressurePlatePlanks.blockID)
					|| ((((metadata & 0x7) != 5) && ((metadata & 0x7) != 6)) || ((blockID == Block.lever.blockID)
							|| (blockID == Block.flowerPot.blockID)
							|| ((metadata == 5) && (blockID == Block.torchWood.blockID))
							|| (blockID == Block.rail.blockID)
							|| (blockID == Block.railDetector.blockID)
							|| (blockID == Block.railPowered.blockID)
							|| (blockID == Block.signPost.blockID)
							|| (blockID == Block.netherStalk.blockID)
							|| (blockID == Block.crops.blockID)
							|| (blockID == Block.carrot.blockID)
							|| (blockID == Block.cake.blockID)
							|| (blockID == Block.snow.blockID)
							|| (blockID == Block.deadBush.blockID)
							|| (blockID == Block.fire.blockID)
							|| (blockID == Block.melonStem.blockID)
							|| (blockID == Block.mushroomBrown.blockID)
							|| (blockID == Block.mushroomRed.blockID)
							|| (blockID == Block.plantRed.blockID)
							|| (blockID == Block.plantYellow.blockID)
							|| (blockID == Block.pumpkinStem.blockID)
							|| (blockID == Block.sapling.blockID)
							|| (blockID == Block.tallGrass.blockID) || (blockID == Block.tripWire.blockID)))) {
				if (world.getBlockId(x, y + 1, z) == 0) {
					System.out.println("Not facing UP or DOWN");
					world.setBlock (xOffset, yOffset + 1, zOffset, 0, 0, 2);
					world.setBlock (x, y + 1, z, Block.pistonMoving.blockID, metadata, 2);
					world.setBlockTileEntity (x, y + 1, z, BlockPistonMoving.getTileEntity (blockID, metadata, direction, true, false));
					
				} else {
					Block.blocksList[blockID].dropBlockAsItem (world, xOffset, yOffset + 1, zOffset, world.getBlockMetadata(xOffset, yOffset + 1, zOffset), 0);
					world.setBlock (xOffset, yOffset + 1, zOffset, 0, 0, 2);
				}

			}

			if ((blockID == Block.reed.blockID)
					|| (blockID == Block.doorWood.blockID)
					|| (blockID == Block.doorIron.blockID)) {
				for (int a = 2; a < 258; a++) {
					if (world.getBlockId(x, y + 1, z) == 0) {
						System.out.println("Not facing UP or DOWN");
						world.setBlock (xOffset, yOffset + 1, zOffset, 0, 0, 2);
						world.setBlock (x, y + 1, z, Block.pistonMoving.blockID, metadata, 2);
						world.setBlockTileEntity (x, y + 1, z, BlockPistonMoving.getTileEntity(blockID, metadata, direction, true, false));
					} else {
						Block.blocksList[blockID].dropBlockAsItem (world, xOffset, yOffset + 1, zOffset, world.getBlockMetadata(xOffset, yOffset + 1, zOffset), 0);
						world.setBlock (xOffset, yOffset + 1, zOffset, 0, 0, 2);
					}

					int i = world.getBlockId(xOffset, yOffset + 2, zOffset);

					if ((world.getBlockId(xOffset, yOffset + 2, zOffset) == Block.cactus.blockID) || (world.getBlockId(xOffset, yOffset + 2, zOffset) == Block.reed.blockID)) {
						y++;
						yOffset++;
					} else if ((world.getBlockId(xOffset, yOffset + 2, zOffset) == Block.doorWood.blockID)
							|| (world.getBlockId(xOffset, yOffset + 2, zOffset) == Block.doorIron.blockID)) {
						metadata = world.getBlockMetadata(xOffset, yOffset + 2, zOffset);
						y++;
						yOffset++;
					} else {
						y -= a - 2;
						yOffset -= a - 2;
						break;
					}

				}

			}

		}

		if ((direction != 4) && (direction != 5)) {
			for (int side = -1; side < 2; side += 2) {
				int blockID = world.getBlockId(xOffset + side, yOffset, zOffset);
				int metadata = world.getBlockMetadata(xOffset + side, yOffset, zOffset);

				if ((blockID == Block.torchRedstoneActive.blockID)
						|| (blockID == Block.torchRedstoneIdle.blockID)) {
					System.out.println("METADATA = " + metadata + " l3 = "
							+ side);
				}

				if ((blockID == Block.torchRedstoneActive.blockID)
						|| (blockID == Block.torchRedstoneIdle.blockID)) {
					if ((side != -1) || ((metadata != 5) && (metadata != 1))) {
						if ((side == 1) && ((metadata == 5) || (metadata == 2)))
							;
					}

				} else if ((blockID == Block.lever.blockID)
						|| (blockID == Block.stoneButton.blockID)
						|| (blockID == Block.woodenButton.blockID)
						|| (blockID == Block.ladder.blockID)
						|| (blockID == Block.torchWood.blockID)
						|| (blockID == Block.signWall.blockID)
						|| (blockID == Block.vine.blockID)
						|| (blockID == Block.tripWireSource.blockID)
						|| (blockID == Block.trapdoor.blockID)
						|| (blockID == Block.torchRedstoneIdle.blockID)
						|| (blockID == Block.torchRedstoneActive.blockID)) {
					if (world.getBlockId(x + side, y, z) == 0) {
						System.out.println("Not facing EAST or WEST");
						world.setBlock(xOffset + side, yOffset, zOffset, 0, 0, 2);
						world.setBlock(x + side, y, z, Block.pistonMoving.blockID, metadata, 2);
						world.setBlockTileEntity (x + side, y, z, BlockPistonMoving.getTileEntity(blockID, metadata, direction, true, false));
					} else {
						Block.blocksList[blockID].dropBlockAsItem(world, xOffset + side, yOffset, zOffset, metadata, 0);
						world.setBlock (xOffset + side, yOffset, zOffset, 0, 0, 2);
					}
				}
			}

		}

		if ((direction != 2) && (direction != 3)) {
			for (int side = -1; side < 2; side += 2) {
				int blockID = world.getBlockId(xOffset, yOffset, zOffset + side);
				int metadata = world.getBlockMetadata(xOffset, yOffset, zOffset + side);

				if ((blockID == Block.torchRedstoneActive.blockID)
						|| (blockID == Block.torchRedstoneIdle.blockID)) {
					if ((side != -1) || ((metadata != 3) && (metadata != 5))) {
						if ((side == 1) && ((metadata == 4) || (metadata == 5)))
							;
					}

				} else if ((blockID == Block.lever.blockID)
						|| (blockID == Block.stoneButton.blockID)
						|| (blockID == Block.woodenButton.blockID)
						|| (blockID == Block.ladder.blockID)
						|| (blockID == Block.torchWood.blockID)
						|| (blockID == Block.signWall.blockID)
						|| (blockID == Block.vine.blockID)
						|| (blockID == Block.tripWireSource.blockID)
						|| (blockID == Block.trapdoor.blockID)
						|| (blockID == Block.torchRedstoneIdle.blockID)
						|| (blockID == Block.torchRedstoneActive.blockID)) {
					if (world.getBlockId(x, y, z + side) == 0) {
						System.out.println("Not facing NORTH or SOUTH");
						world.setBlock (xOffset, yOffset, zOffset + side, 0, 0, 2);
						world.setBlock (x, y, z + side, Block.pistonMoving.blockID, metadata, 2);
						world.setBlockTileEntity (x, y, z + side, BlockPistonMoving.getTileEntity(blockID, metadata, direction, true, false));
					} else {
						Block.blocksList[blockID].dropBlockAsItem (world, xOffset, yOffset, zOffset + side, metadata, 0);
						world.setBlock (xOffset, yOffset, zOffset + side, 0, 0, 2);
					}
				}
			}

		}

		if ((direction != 0) && (direction != 1)) {
			if ((((i3 & 0x7) == k2) && ((l2 == Block.torchRedstoneIdle.blockID) || (l2 == Block.torchRedstoneActive.blockID)))
					|| (l2 == Block.lever.blockID)
					|| (l2 == Block.stoneButton.blockID)
					|| (l2 == Block.woodenButton.blockID)
					|| (l2 == Block.ladder.blockID)
					|| (l2 == Block.torchWood.blockID)
					|| (l2 == Block.signWall.blockID)
					|| (l2 == Block.vine.blockID)
					|| (l2 == Block.tripWireSource.blockID)
					|| (l2 == Block.trapdoor.blockID)) {
				System.out.println("Not facing UP or DOWN 2");
				world.setBlock (l1, i2, j2, 0, 0, 2);
				world.setBlock (xOffset, yOffset, zOffset, 0, 0, 2);
				world.setBlock (xOffset, yOffset, zOffset, Block.pistonMoving.blockID, i3, 2);
				world.setBlockTileEntity(xOffset, yOffset, zOffset, BlockPistonMoving.getTileEntity(l2, i3, direction, true, false));
			} else {
				world.setBlock (xOffset, yOffset, zOffset, 0, 0, 2);
			}

		} else if (direction == 0) {
			if ((l2 == Block.redstoneWire.blockID)
					|| ((i3 == 5) && ((l2 == Block.torchRedstoneIdle.blockID) || (l2 == Block.torchRedstoneActive.blockID)))
					|| (l2 == Block.redstoneRepeaterIdle.blockID)
					|| (l2 == Block.redstoneRepeaterActive.blockID)
					|| (l2 == Block.pressurePlateStone.blockID)
					|| (l2 == Block.pressurePlatePlanks.blockID)
					|| ((((i3 & 0x7) != 5) && ((i3 & 0x7) != 6)) || ((l2 == Block.lever.blockID)
							|| (l2 == Block.flowerPot.blockID)
							|| (l2 == Block.torchWood.blockID)
							|| (l2 == Block.rail.blockID)
							|| (l2 == Block.railDetector.blockID)
							|| (l2 == Block.railPowered.blockID)
							|| (l2 == Block.signPost.blockID)
							|| (l2 == Block.cactus.blockID)
							|| (l2 == Block.netherStalk.blockID)
							|| (l2 == Block.crops.blockID)
							|| (l2 == Block.carrot.blockID)
							|| (l2 == Block.cake.blockID)
							|| (l2 == Block.snow.blockID)
							|| (l2 == Block.bed.blockID)
							|| (l2 == Block.deadBush.blockID)
							|| (l2 == Block.fire.blockID)
							|| (l2 == Block.melonStem.blockID)
							|| (l2 == Block.mushroomBrown.blockID)
							|| (l2 == Block.mushroomRed.blockID)
							|| (l2 == Block.plantRed.blockID)
							|| (l2 == Block.plantYellow.blockID)
							|| (l2 == Block.pumpkinStem.blockID)
							|| (l2 == Block.reed.blockID)
							|| (l2 == Block.sapling.blockID)
							|| (l2 == Block.tallGrass.blockID) || (l2 == Block.tripWire.blockID)))) {
				System.out.println("Facing UP");
				world.setBlock (l1, i2, j2, 0, 0, 2);
				world.setBlock (xOffset, yOffset, zOffset, Block.pistonMoving.blockID, i3, 2);
				world.setBlockTileEntity (xOffset, yOffset, zOffset, BlockPistonMoving.getTileEntity (l2, i3, direction, true, false));
			} else {
				world.setBlock (xOffset, yOffset, zOffset, 0, 0, 2);
			}
		} else {
			world.setBlock (xOffset, yOffset, zOffset, 0, 0, 2);
		}
	}

	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);

		int md = world.getBlockMetadata(x, y, z);
		
		int orient = getOrientation (md);
		
		// TODO
		
//		if (!BlockGravitationalPistonBase.isExtended(md)) {
//			return;
//		}
		
		int extX = x;
		int extY = y;
		int extZ = z;

		extX += Facing.offsetsXForSide[orient];
		extY += Facing.offsetsYForSide[orient];
		extZ += Facing.offsetsZForSide[orient];

		int blockID = world.getBlockId(extX, extY, extZ);

		if (blockID == MorePistons.pistonExtension.blockID) {
			world.setBlock (extX, extY, extZ, 0);
			world.setBlockMetadataWithNotify (extX, extY, extZ, 0, 2);
		}

		world.removeBlockTileEntity(x, y, z);
	}
}