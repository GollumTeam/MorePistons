
package com.gollum.morepistons.common.building.handler;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gollum.core.common.building.Building.Unity;
import com.gollum.core.common.building.handler.BlockDirectionalWithBit1BuildingHandler;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsMagnetic;
import com.gollum.morepistons.common.block.BlockMorePistonsRedStone;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

public class BlockMorePistonsBuildingHandler extends BlockDirectionalWithBit1BuildingHandler {
	
	@Override
	protected boolean mustApply (World world, int x, int y, int z, Unity unity) {
		return unity.block instanceof BlockMorePistonsBase;
	}
	
	@Override
	protected void applyExtra(
		World world,
		int x, int y, int z,
		Unity unity,
		int initX, int initY, int initZ,
		int rotate,
		int dx, int dz,
		int maxX, int maxZ
	) {
		
		if (unity.block instanceof BlockMorePistonsRedStone) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			
			if (te instanceof TileEntityMorePistonsPiston) {
				
				int multiplier = 1; try { multiplier = Integer.parseInt(unity.extra.get("multiplier")); } catch (Exception e) {}
				
				((TileEntityMorePistonsPiston) te).multiplier = ((multiplier-1) % 8) + 1;
			}
			
		}
		
		if (unity.block instanceof BlockMorePistonsMagnetic) {
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			
			if (te instanceof TileEntityMorePistonsPiston) {
				
				int stickySize = 1; try { stickySize = Integer.parseInt(unity.extra.get("stickysize")); } catch (Exception e) {}
				
				((TileEntityMorePistonsPiston) te).stickySize = ((stickySize-1) % 8) + 1;
			}
			
		}
		
	}
}
