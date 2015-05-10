package com.gollum.morepistons.common.block;

import static com.gollum.morepistons.ModMorePistons.log;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockPistonBase;
import com.gollum.morepistons.common.item.ItemMorePistonsRedStoneProxy;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsMoving;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
import com.gollum.morepistons.inits.ModBlocks;

public class BlockMorePistonsRedStoneProxy extends HBlockPistonBase {

	public BlockMorePistonsRedStone target;
	public int multiplicator;
	
	public BlockMorePistonsRedStoneProxy(String registerName, boolean sticky, int multiplicator) {
		super(registerName, sticky);
		
		this.multiplicator = multiplicator;
		
		if (sticky) {
			this.target = ModBlocks.blockRedStonePistonBase;
		} else {
			this.target = ModBlocks.blockRedStoneStickyPistonBase;
		}
		
		this.setItemBlockClass(ItemMorePistonsRedStoneProxy.class);
	}
	
	//////////////////////////
	//Gestion des textures  //
	//////////////////////////
	
	@Override
	public String getTextureKey() {
		return super.getTextureKey().substring(0, super.getTextureKey().length()-1).replace("sticky", "");
	}
	
	@Override
	protected void registerBlockIconsTop (IIconRegister iconRegister) {
		this.iconTop = helper.loadTexture(iconRegister, "top" + (this.isStickyPiston ? suffixSticky : ""), true);
	}
	
	@Override
	protected void registerBlockIconsSide (IIconRegister iconRegister) { 
		this.blockIcon =  helper.loadTexture(iconRegister, this.suffixSide+"_"+this.multiplicator);
	}
	
	////////////
	// Events //
	////////////
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
		
		log.debug("RedStoneProxy replace onBlockActivated : ",x, y, z);
		
		world.addBlockEvent(x, y, z, this, 0, world.getBlockMetadata(x, y, z));
		
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
		
		log.debug("RedStoneProxy replace onBlockPlacedBy : ",x, y, z);
		world.setBlock(x, y, z, this.target);
		this.target.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		
		if (!world.isRemote) {
			log.debug("RedStoneProxy replace onNeighborBlockChange : ",x, y, z);
			world.addBlockEvent(x, y, z, this, 0, world.getBlockMetadata(x, y, z));
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		if (!world.isRemote) {
			log.debug("RedStoneProxy replace onBlockAdded : ",x, y, z);
			world.addBlockEvent(x, y, z, this, 0, world.getBlockMetadata(x, y, z));
		}
		
	}
	
	public boolean onBlockEventReceived(World world, int x, int y, int z, int EventId, int metadata) {
		
		try {
			
			int     orientation = BlockPistonBase.getPistonOrientation(metadata);
			boolean open        = BlockPistonBase.isExtended(metadata);
			
			log.debug("RedStoneProxy replace onBlockEventReceived : ",x, y, z, "metadata="+metadata,"open="+open);
			
			world.setBlock(x, y, z, this.target, orientation, 2);
			TileEntityMorePistonsPiston te = new TileEntityMorePistonsPiston();
			te.multiplier = this.multiplicator;
			world.setTileEntity(x, y, z, te);
			
			if (open) {
					
				int x2 = x;
				int y2 = y;
				int z2 = z;
				boolean out = false;
				
				do {
					x2 += Facing.offsetsXForSide[orientation];
					y2 += Facing.offsetsYForSide[orientation];
					z2 += Facing.offsetsZForSide[orientation];
					
					Block      b = world.getBlock(x2, y2, z2);
					int        o = BlockPistonBase.getPistonOrientation(world.getBlockMetadata(x2, y2, z2));
					TileEntity t = world.getTileEntity(x2, y2, z2);
					
					if (
						orientation == o &&
						!(t instanceof TileEntityPiston) &&
						(
							b == Blocks.piston_head ||
							b == Blocks.piston_extension ||
							b == ModBlocks.blockPistonExtention ||
							b == ModBlocks.blockPistonRod
						)
					) {
						
						world.setBlockToAir(x2, y2, z2);
						
					} else if (
						orientation == o &&
						t instanceof TileEntityMorePistonsMoving &&
						(
							((TileEntityMorePistonsMoving)t).storedBlock == Blocks.piston_head ||
							((TileEntityMorePistonsMoving)t).storedBlock == Blocks.piston_extension ||
							((TileEntityMorePistonsMoving)t).storedBlock == ModBlocks.blockPistonExtention
						)
					) {
						
						world.setBlockToAir(x2, y2, z2);
						out = true;
						
					} else {
						out = true;
					}
					
				} while (!out);
			}
			this.target.updatePistonState(world, x, y, z);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
