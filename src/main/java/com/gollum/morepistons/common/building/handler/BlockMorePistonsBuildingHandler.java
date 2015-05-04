
package com.gollum.morepistons.common.building.handler;

import com.gollum.core.ModGollumCoreLib;
import com.gollum.core.common.building.Building.Unity;
import com.gollum.core.common.building.handler.BlockDirectionalWithBit1BuildingHandler;
import com.gollum.morepistons.common.block.BlockMorePistonsBase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.world.World;

public class BlockMorePistonsBuildingHandler extends BlockDirectionalWithBit1BuildingHandler {

	@Override
	protected boolean mustApply (World world, int x, int y, int z, Block block) {
		return block instanceof BlockMorePistonsBase;
	}
}
