package com.gollum.morepistons.common.block.superpiston;

import net.minecraft.block.Block;
import net.minecraft.world.World;


public abstract class AbstractSuperPistonHandler {
	
	public abstract boolean dontMoveIfOnTop (Block block, int metadata, World world, int x, int y, int z, int orientation);
	
	public abstract boolean isntAttachOnTop(Block block, int metadata, World world, int x, int y, int z, int orientation);
	
	public abstract boolean isAttachableBlockOnNext(Block block, int metadata, World world, int x, int y, int z, int orientation);
	
	public abstract boolean isAttachOnNext(Block block, int metadata, World world, int x, int y, int z, int orientation);
	
}
