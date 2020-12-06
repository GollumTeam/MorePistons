
package com.gollum.morepistons.common.building.handler;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.gollum.core.common.building.Building.EnumRotate;
import com.gollum.core.common.building.Building.Unity;
import com.gollum.core.common.building.handler.BlockDirectionalBuildingHandler;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
//import com.gollum.morepistons.common.block.BlockMorePistonsMagnetic;
//import com.gollum.morepistons.common.block.BlockMorePistonsRedStone;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class BlockMorePistonsBuildingHandler extends BlockDirectionalBuildingHandler {
	
	@Override
	protected boolean mustApply (World world, BlockPos pos, Unity unity) {
		Block block = (unity.state != null) ? unity.state.getBlock() : null;
		return block instanceof BlockMorePistonsBase;
	}
	
	@Override
	protected void applyExtra(
		World world,
		BlockPos pos,
		Unity unity,
		BlockPos initPos,
		EnumRotate rotate,
		int maxX, int maxZ
	) {
		Block block = (unity.state != null) ? unity.state.getBlock() : null;
		
		// TODO
//		if (block instanceof BlockMorePistonsRedStone) {
//			
//			TileEntity te = world.getTileEntity(pos);
//			
//			if (te instanceof TileEntityMorePistonsPiston) {
//				
//				int multiplier = 1; try { multiplier = Integer.parseInt(unity.extra.get("multiplier")); } catch (Exception e) {}
//				
//				((TileEntityMorePistonsPiston) te).multiplier = ((multiplier-1) % 8) + 1;
//			}
//			
//		}
//		
//		if (block instanceof BlockMorePistonsMagnetic) {
//			
//			TileEntity te = world.getTileEntity(pos);
//			
//			if (te instanceof TileEntityMorePistonsPiston) {
//				
//				int stickySize = 1; try { stickySize = Integer.parseInt(unity.extra.get("stickysize")); } catch (Exception e) {}
//				
//				((TileEntityMorePistonsPiston) te).stickySize = ((stickySize-1) % 8) + 1;
//			}
//			
//		}
		
	}
}
