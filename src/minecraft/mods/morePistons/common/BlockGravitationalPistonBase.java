package mods.morePistons.common;
import net.minecraft.block.BlockContainer; // akb;
import net.minecraft.block.Block; // amq;
import net.minecraft.tileentity.TileEntity; // any;
import net.minecraft.block.BlockPistonBase; //aoa;
import net.minecraft.block.BlockPistonMoving; // aoc;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB; // aoe;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.util.MathHelper; // ke;
import net.minecraft.entity.Entity; // lq;
import net.minecraft.entity.EntityLiving; // md;
import net.minecraft.entity.item.EntityFallingSand; // pw;
import net.minecraft.entity.player.EntityPlayer; // qx;
import net.minecraft.world.World; // yc;
import net.minecraft.world.IBlockAccess; // ym;

public class BlockGravitationalPistonBase extends BlockPistonBase {
	private boolean isSticky;
	private static boolean ignoreUpdates;
	public double power = 1.35D;
	
	private Icon textureFileTop;
	private Icon textureFileTopSticky;
	private Icon textureFileOpen;
	private Icon textureFileSide;
	private Icon textureFileBottom;

	public BlockGravitationalPistonBase(int i, boolean flag) {
		super(i, flag);
		this.isSticky = flag;
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
	 * Enregistre les textures Depuis la 1.5 on est oblig� de charger les
	 * texture fichier par fichier
	 */
	public void registerIcons(IconRegister iconRegister) {
		this.textureFileTop       = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top"));
		this.textureFileTopSticky = this.loadTexture(iconRegister, ModMorePistons.getTexture ("top_sticky"));
		this.textureFileOpen      = this.loadTexture(iconRegister, ModMorePistons.getTexture ("gravi_top"));
		this.textureFileBottom    = this.loadTexture(iconRegister, ModMorePistons.getTexture ("gravi_bottom"));
		this.textureFileSide      = this.loadTexture(iconRegister, ModMorePistons.getTexture ("gravi_side"));
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
	
	
	public boolean onBlockActivated(World world, int i, int j, int k,
			EntityPlayer entityplayer) {
		return false;
	}

	public void onBlockPlacedBy (World world, int i, int j, int k, EntityLiving entityliving) {
		int l = determineOrientation(world, i, j, k, (EntityPlayer) entityliving);
		world.setBlockMetadataWithNotify (i, j, k, l, 2);
		if (!ignoreUpdates) {
			updatePistonState(world, i, j, k);
		}
	}

	public void a(World world, int i, int j, int k, int l) {
		if (!ignoreUpdates) {
			System.out.println("JC1008 Grav got neighbor change");
			updatePistonState(world, i, j, k);
		}
	}

	public void onBlockAdded (World world, int i, int j, int k) {
		return;
	}

	private void updatePistonState(World world, int i, int j, int k) {
		int l = world.getBlockMetadata (i, j, k);
		int i1 = getOrientation(l);
		boolean flag = isIndirectlyPowered(world, i, j, k, i1);
		if (l == 7) {
			return;
		}
		if ((flag) && (!isExtended(l))) {
			if (canExtend(world, i, j, k, i1)) {
				world.setBlockMetadataWithNotify (i, j, k, i1 | 0x8, 2);
				world.addBlockEvent(i, j, k, this.blockID, 0, i1);
			}
		} else if ((!flag) && (isExtended(l))) {
			world.setBlockMetadataWithNotify (i, j, k, i1, 2);
			world.addBlockEvent(i, j, k, this.blockID, 1, i1);
		}
	}

	private boolean isIndirectlyPowered(World world, int i, int j, int k, int l) {
		if ((l != 0) && (world.getIndirectPowerOutput(i, j - 1, k, 0))) {
			return true;
		}
		if ((l != 1) && (world.getIndirectPowerOutput(i, j + 1, k, 1))) {
			return true;
		}
		if ((l != 2) && (world.getIndirectPowerOutput(i, j, k - 1, 2))) {
			return true;
		}
		if ((l != 3) && (world.getIndirectPowerOutput(i, j, k + 1, 3))) {
			return true;
		}
		if ((l != 5) && (world.getIndirectPowerOutput(i + 1, j, k, 5))) {
			return true;
		}
		if ((l != 4) && (world.getIndirectPowerOutput(i - 1, j, k, 4))) {
			return true;
		}
		if (world.getIndirectPowerOutput(i, j, k, 0)) {
			return true;
		}
		if (world.getIndirectPowerOutput(i, j + 2, k, 1)) {
			return true;
		}
		if (world.getIndirectPowerOutput(i, j + 1, k - 1, 2)) {
			return true;
		}
		if (world.getIndirectPowerOutput(i, j + 1, k + 1, 3)) {
			return true;
		}
		if (world.getIndirectPowerOutput(i - 1, j + 1, k, 4)) {
			return true;
		}

		return world.getIndirectPowerOutput(i + 1, j + 1, k, 5);
	}

	public boolean onBlockEventReceived(World world, int i, int j, int k, int l, int i1) {
		ignoreUpdates = true;
		int j1 = i1;
		if (l == 0) {
			if (tryExtend(world, i, j, k, j1)) {
				world.setBlockMetadataWithNotify(i, j, k, j1 | 0x8, 2);
				int a = j1 | 0x8;
				world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
			} else {
				world.setBlockMetadataWithNotify (i, j, k, j1, 2);
			}
		} else if (l == 1) {
			TileEntity tileentity = world.getBlockTileEntity(i + Facing.offsetsXForSide[j1], j + Facing.offsetsYForSide[j1], k + Facing.offsetsZForSide[j1]);
			if ((tileentity != null) && ((tileentity instanceof TileEntityMorePistons))) {
				((TileEntityMorePistons) tileentity).clearPistonTileEntity();
			}
			world.setBlock(i, j, k, Block.pistonMoving.blockID, j1, 2);
			// TODO ModMorePistons
			world.setBlockTileEntity(i, j, k, BlockPistonMoving.getTileEntity(this.blockID, j1, j1, false, true));
			if (this.isSticky) {
				int k1 = i + Facing.offsetsXForSide[j1] * 2;
				int l1 = j + Facing.offsetsYForSide[j1] * 2;
				int i2 = k + Facing.offsetsZForSide[j1] * 2;
				int j2 = world.getBlockId(k1, l1, i2);
				int k2 = world.getBlockMetadata(k1, l1, i2);
				boolean flag = false;
				if (j2 == Block.pistonMoving.blockID) {
					TileEntity tileentity1 = world.getBlockTileEntity(k1, l1, i2);
					if ((tileentity1 != null)
							&& ((tileentity1 instanceof TileEntityMorePistons))) {
						TileEntityMorePistons tileentitypiston = (TileEntityMorePistons) tileentity1;
						if ((tileentitypiston.getPistonOrientation() == j1) && (tileentitypiston.isExtending())) {
							tileentitypiston.clearPistonTileEntity();
							j2 = tileentitypiston.getStoredBlockID();
							k2 = tileentitypiston.getBlockMetadata();
							flag = true;
						}
					}
				}
				boolean flag1 = false;

				while ((!flag)
						&& (j2 > 0)
						&& (canPushBlock(j2, world, k1, l1, i2, false))
						&& ((Block.blocksList[j2].getMobilityFlag() == 0) || (j2 == Block.pistonBase.blockID) || (j2 == Block.pistonStickyBase.blockID))) {
					i += Facing.offsetsXForSide[j1];
					j += Facing.offsetsYForSide[j1];
					k += Facing.offsetsZForSide[j1];
					world.setBlock(i, j, k, Block.pistonMoving.blockID, k2, 2);
					// TODO ModMorePistons
					world.setBlockTileEntity(i, j, k, BlockPistonMoving.getTileEntity(j2, k2, j1, false, false));
					ignoreUpdates = false;
					world.setBlock(k1, l1, i2, 0);
					world.setBlockMetadataWithNotify(k1, l1, i2, 0, 2);
					ignoreUpdates = true;
					flag1 = true;
					if ((j2 != Block.pistonStickyBase.blockID) || (k2 != (j1 & 0x7))) {
						break;
					}
					k1 = i + Facing.offsetsXForSide[j1] * 2;
					l1 = j + Facing.offsetsYForSide[j1] * 2;
					i2 = k + Facing.offsetsZForSide[j1] * 2;
					j2 = world.getBlockId(k1, l1, i2);
					k2 = world.getBlockMetadata(k1, l1, i2);
				}
				if ((!flag) && (!flag1)) {
					ignoreUpdates = false;
					world.setBlock(i + Facing.offsetsXForSide[j1], j + Facing.offsetsYForSide[j1], k + Facing.offsetsZForSide[j1], 0);
					world.setBlockMetadataWithNotify(i + Facing.offsetsXForSide[j1], j + Facing.offsetsYForSide[j1], k + Facing.offsetsZForSide[j1], 0, 2);
					ignoreUpdates = true;
				}
			} else {
				ignoreUpdates = false;
				world.setBlock(i + Facing.offsetsXForSide[j1], j + Facing.offsetsYForSide[j1], k + Facing.offsetsZForSide[j1], 0);
				world.setBlockMetadataWithNotify(i + Facing.offsetsXForSide[j1], j + Facing.offsetsYForSide[j1], k + Facing.offsetsZForSide[j1], 0, 2);
				ignoreUpdates = true;
			}
			world.playSoundEffect (i + 0.5D, j + 0.5D, k + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.15F + 0.6F);
		}
		ignoreUpdates = false;
		
		return true;
	}

	public void setBlockBoundsBasedOnState (IBlockAccess iblockaccess, int i, int j, int k) {
		int l = iblockaccess.getBlockMetadata(i, j, k);
		if (isExtended(l)) {
			switch (getOrientation(l)) {
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

	public void setBlockBoundsForItemRender () {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public static int getOrientation(int i) {
		return i & 0x7;
	}

	public static boolean isExtended(int i) {
		return (i & 0x8) != 0;
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
		int l = MathHelper.floor_double(entityplayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		if (l == 0) {
			return 2;
		}
		if (l == 1) {
			return 5;
		}
		if (l == 2) {
			return 3;
		}

		return l == 3 ? 4 : 0;
	}

	private static boolean canPushBlock(int i, World world, int j, int k, int l,
			boolean flag) {
		boolean found = false;
		if (i == Block.obsidian.blockID) {
			return false;
		}
		if ((i == MorePistons.pistonRod.blockID)
				|| (i == MorePistons.pistonExtension.blockID)) {
			return false;
		}
		for (int a = 0; a < MorePistons.pistonList.length; a++) {
			if (i == MorePistons.pistonList[a]) {
				found = true;
				break;
			}

			if (found == true) {
				if (isExtended(world.getBlockMetadata(j, k, l))) {
					return false;
				}
			} else if (a == MorePistons.pistonList.length - 1) {
				found = false;
			}
		}

		if (!found) {
			if (Block.blocksList[i].getBlockHardness(world, j, k, l) == -1.0F) {
				return false;
			}
			if (Block.blocksList[i].getMobilityFlag() == 2) {
				return false;
			}
			if ((!flag) && (Block.blocksList[i].getMobilityFlag() == 1)) {
				return false;
			}
		} else {
			return true;
		}
		return !(Block.blocksList[i] instanceof BlockContainer);
	}

	private static boolean canExtend(World world, int i, int j, int k, int l) {
		int i1 = i + Facing.offsetsXForSide[l];
		int j1 = j + Facing.offsetsYForSide[l];
		int k1 = k + Facing.offsetsZForSide[l];
		int l1 = 0;

		while (l1 < 13) {
			if ((j1 <= 0) || (j1 >= 255)) {
				return false;
			}
			int i2 = world.getBlockId(i1, j1, k1);
			if (i2 == 0) {
				break;
			}
			if (!canPushBlock(i2, world, i1, j1, k1, true)) {
				return false;
			}
			if (Block.blocksList[i2].getMobilityFlag() == 1) {
				break;
			}
			if (l1 == 12) {
				return false;
			}
			i1 += Facing.offsetsXForSide[l];
			j1 += Facing.offsetsYForSide[l];
			k1 += Facing.offsetsZForSide[l];
			l1++;
		}
		return true;
	}

	private boolean tryExtend(World world, int i, int j, int k, int l) {
		int i1 = i + Facing.offsetsXForSide[l];
		int j1 = j + Facing.offsetsYForSide[l];
		int k1 = k + Facing.offsetsZForSide[l];
		int l1 = 0;

		int isExtended = world.getBlockMetadata(i, j, k);

		boolean sandEntity = false;

		while (l1 < 13) {
			if ((j1 <= 0) || (j1 >= 255)) {
				return false;
			}
			int i2 = world.getBlockId(i1, j1, k1);
			if (i2 == 0) {
				break;
			}
			if (!canPushBlock(i2, world, i1, j1, k1, true)) {
				return false;
			}
			if (Block.blocksList[i2].getMobilityFlag() == 1) {
				Block.blocksList[i2].dropBlockAsItem(world, i1, j1, k1, world.getBlockMetadata(i1, j1, k1), 0);
				world.setBlock(i1, j1, k1, 0);
				world.setBlockMetadataWithNotify(i1, j1, k1, 0, 2);
				break;
			}
			if (l1 == 12) {
				return false;
			}
			i1 += Facing.offsetsXForSide[l];
			j1 += Facing.offsetsYForSide[l];
			k1 += Facing.offsetsZForSide[l];
			l1++;
		}

		List pistonList = world.getEntitiesWithinAABBExcludingEntity (null, AxisAlignedBB.getBoundingBox (i1, j1, k1, i1 + 1.0D, j1 + 1.0D, k1 + 1.0D));
		Iterator pistonIterator;
		if ((!this.isSticky) || (l1 != 0)) {
			for (pistonIterator = pistonList.iterator(); pistonIterator
					.hasNext();) {
				Entity entity = (Entity) pistonIterator.next();
				if ((entity instanceof EntityFallingSand)) {
					sandEntity = true;
				} else {
					entity.motionX += Facing.offsetsXForSide[l] * this.power;
					entity.motionY += Facing.offsetsYForSide[l] * this.power;
					entity.motionZ += Facing.offsetsZForSide[l] * this.power;
				}
			}
		}

		boolean flag5 = false;
		int j2;
		for (; (i1 != i) || (j1 != j) || (k1 != k); k1 = j2) {
			int k2 = i1 - Facing.offsetsXForSide[l];
			int l2 = j1 - Facing.offsetsYForSide[l];
			j2 = k1 - Facing.offsetsZForSide[l];
			int i3 = world.getBlockId(k2, l2, j2);
			int j3 = world.getBlockMetadata(k2, l2, j2);
			if ((i3 == this.blockID) && (k2 == i) && (l2 == j) && (j2 == k)) {
				world.setBlock (i1, j1, k1, Block.pistonMoving.blockID, l | (this.isSticky ? 8 : 0), 2);
				// TODO BlockPistonMoving
				world.setBlockTileEntity(i1, j1, k1, BlockPistonMoving.getTileEntity( MorePistons.pistonExtension.blockID, l | (this.isSticky ? 8 : 0), l, true, false));
			
			} else if ((!flag5) && (Facing.offsetsYForSide[l] > 0) && (!this.isSticky)
					&& ((i3 == Block.sand.blockID) || (i3 == Block.gravel.blockID))) {
				if ((isExtended != 1) && (!sandEntity)) {
					world.setBlock (i1, j1, k1, 0);
					world.setBlockMetadataWithNotify(i1, j1, k1, 0, 2);
					EntityFallingSand entityfallingsand = new EntityFallingSand(world, i1 + 0.5F, j1 + 0.5F,
							k1 + 0.5F, i3);
					entityfallingsand.motionY += 1.35D;
					entityfallingsand.fallTime = 1;
					world.spawnEntityInWorld(entityfallingsand);
				}
			} else if (i3 == Block.tnt.blockID) {
				world.setBlock (i1, j1, k1, 0);
				world.setBlockMetadataWithNotify(i1, j1, k1, 0, 2);
				System.out.println("FOUND TNT!!!");
			} else {
				flag5 = true;
				world.setBlock(i1, j1, k1, Block.pistonMoving.blockID, j3, 2);
				// TODO BlockPistonMoving
				world.setBlockTileEntity(i1, j1, k1, BlockPistonMoving.getTileEntity(i3, j3, l, true, false));
			}
			i1 = k2;
			j1 = l2;
		}

		return true;
	}

	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);

		int md = world.getBlockMetadata(x, y, z);

		int orient = getDirectionMeta(md);

		if (!isExtended(md)) {
			return;
		}

		int extX = x;
		int extY = y;
		int extZ = z;

		extX += Facing.offsetsXForSide[orient];
		extY += Facing.offsetsYForSide[orient];
		extZ += Facing.offsetsZForSide[orient];

		int blockID = world.getBlockId(extX, extY, extZ);

		if (blockID == MorePistons.pistonExtension.blockID) {
			world.setBlock(extX, extY, extZ, 0);
			world.setBlockMetadataWithNotify(extX, extY, extZ, 0, par5);
		}

		world.removeBlockTileEntity(x, y, z);
	}

	public static int getDirectionMeta(int i) {
		return i & 0x7;
	}
}
