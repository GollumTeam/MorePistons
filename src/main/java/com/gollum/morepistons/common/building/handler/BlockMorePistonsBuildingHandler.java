
package com.gollum.morepistons.common.building.handler;

import java.util.HashMap;
import java.util.Random;

import com.gollum.core.ModGollumCoreLib;
import com.gollum.core.common.building.Building.Unity;
import com.gollum.core.common.building.handler.BlockDirectionalWithBit1BuildingHandler;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;
import com.gollum.morepistons.common.block.BlockMorePistonsRedStone;
import com.gollum.morepistons.common.tileentities.TileEntityMorePistonsPiston;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMorePistonsBuildingHandler extends BlockDirectionalWithBit1BuildingHandler {
	
	@Override
	protected boolean mustApply (World world, int x, int y, int z, Block block) {
		return block instanceof BlockMorePistonsBase;
	}
	
	@Override
	protected void applyExtra(
		Block block,
		World world,
		Random random, 
		int x, int y, int z, 
		HashMap<String, String> extra,
		int initX, int initY, int initZ, 
		int rotate,
		int dx, int dz,
		int maxX, int maxZ
	) {
		
		if (block instanceof BlockMorePistonsRedStone) {
			
			TileEntity te = world.getTileEntity(x, y, z);
			
			if (te instanceof TileEntityMorePistonsPiston) {
				
				int multiplier = 1; try { multiplier = Integer.parseInt(extra.get("multiplier")); } catch (Exception e) {}
				
				((TileEntityMorePistonsPiston) te).multiplier = ((multiplier-1) % 8) + 1;
			}
			
		}
		
	}
}
