//package com.gollum.morepistons.common.block;
//
//import static com.gollum.morepistons.ModMorePistons.log;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockPistonBase;
//import net.minecraft.client.renderer.texture.IIconRegister;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.Facing;
//import net.minecraft.world.World;
//
//import com.gollum.core.tools.helper.blocks.HBlockPistonBase;
//import com.gollum.core.tools.registered.RegisteredObjects;
//import com.gollum.core.tools.registry.BlockRegistry;
//import com.gollum.core.tools.registry.ItemRegistry;
//import com.gollum.morepistons.common.item.ItemMorePistonsVanillaProxy;
//import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;
//import com.gollum.morepistons.inits.ModBlocks;
//
//public class BlockMorePistonsVanillaProxy extends HBlockPistonBase {
//	
//	public BlockMorePistonsVanilla target;
//	public BlockPistonBase vanillaPiston;
//	
//	public BlockMorePistonsVanillaProxy(BlockMorePistonsVanilla target) {
//		super(
//			target.isSticky() ? RegisteredObjects.instance().getRegisterName(Blocks.sticky_piston) : RegisteredObjects.instance().getRegisterName(Blocks.piston), 
//			target.isSticky()
//		);
//		this.target = target;
//		
//		if (target.isSticky()) {
//			this.vanillaPiston = Blocks.sticky_piston;
//		} else {
//			this.vanillaPiston = Blocks.piston;
//		}
//		
//		this.setBlockName(this.vanillaPiston.getUnlocalizedName().replace("tile.", ""));
//		helper.vanillaTexture = true;
//	}
//	
//	//////////////////////////
//	//Gestion des textures  //
//	//////////////////////////
//	
//	@Override protected void registerBlockIconsTop   (IIconRegister iconRegister) { this.iconTop    = iconRegister.registerIcon(this.isStickyPiston ? "piston_top_sticky" : "piston_top_normal"); }
//	@Override protected void registerBlockIconsOpen  (IIconRegister iconRegister) { this.iconOpen   = iconRegister.registerIcon("piston_inner");  }
//	@Override protected void registerBlockIconsBottom(IIconRegister iconRegister) { this.iconBottom = iconRegister.registerIcon("piston_bottom"); }
//	@Override protected void registerBlockIconsSide  (IIconRegister iconRegister) { this.blockIcon  = iconRegister.registerIcon("piston_side");   }
//	
//	////////////////////
//	// Helper methods //
//	////////////////////
//	
//	@Override
//	public void register() {
//		if (helper.vanillaRegister) return;
//		
//		BlockRegistry.instance().overrideBlocksClassField(this.vanillaPiston, this);
//		BlockRegistry.instance().overrideRegistered(this.getRegisterName(), this);
//		ItemRegistry .instance().overrideRegistered(this.getRegisterName(), new ItemMorePistonsVanillaProxy(Item.getItemFromBlock(this.vanillaPiston), this));
//	}
//	
//	////////////
//	// Events //
//	////////////
//	
//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
//		
//		log.debug("VanillaProxy replace onBlockActivated : ",x, y, z);
//		
//		world.addBlockEvent(x, y, z, this, 0, world.getBlockMetadata(x, y, z));
//		
//		return false;
//	}
//	
//	@Override
//	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
//		
//		log.debug("VanillaProxy replace onBlockPlacedBy : ",x, y, z);
//		world.setBlock(x, y, z, this.target);
//		this.target.onBlockPlacedBy(world, x, y, z, entityLiving, itemStack);
//	}
//	
//	@Override
//	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
//		
//		if (!world.isRemote) {
//			log.debug("VanillaProxy replace onNeighborBlockChange : ",x, y, z);
//			world.addBlockEvent(x, y, z, this, 0, world.getBlockMetadata(x, y, z));
//		}
//	}
//	
//	@Override
//	public void onBlockAdded(World world, int x, int y, int z) {
//		
//		if (!world.isRemote) {
//			log.debug("VanillaProxy replace onBlockAdded : ",x, y, z);
//			world.addBlockEvent(x, y, z, this, 0, world.getBlockMetadata(x, y, z));
//		}
//		
//	}
//	
//	public boolean onBlockEventReceived(World world, int x, int y, int z, int EventId, int metadata) {
//		
//		try {
//			
//			int     orientation = BlockPistonBase.getPistonOrientation(metadata);
//			boolean open        = BlockPistonBase.isExtended(metadata);
//			
//			log.debug("onBlockEventReceived : ",x, y, z, "metadata="+metadata,"open="+open);
//			
//			world.setBlock(x, y, z, this.target, metadata, 2);
//			TileEntityMorePistonsPiston te = new TileEntityMorePistonsPiston();
//			if (open) {
//				te.currentOpened = 1;
//			}
//			world.setTileEntity(x, y, z, te);
//			if (open) {
//				int x2 = x + Facing.offsetsXForSide[orientation];
//				int y2 = y + Facing.offsetsYForSide[orientation];
//				int z2 = z + Facing.offsetsZForSide[orientation];
//				
//				world.setBlock(x2, y2, z2, ModBlocks.blockPistonExtention, orientation | (this.target.isSticky ? 0x8 : 0x0), 2);
//			}
//			this.target.updatePistonState(world, x, y, z);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return true;
//	}
//	
//	@Override
//	public boolean equals(Object o) {
//		return super.equals(o) || o == this.vanillaPiston;
//	}
//}
