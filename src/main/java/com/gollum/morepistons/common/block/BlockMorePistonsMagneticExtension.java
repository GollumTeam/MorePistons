package com.gollum.morepistons.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.gollum.core.tools.helper.blocks.HBlockPistonExtension;
import com.gollum.morepistons.ModMorePistons;
import com.gollum.morepistons.inits.ModBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsMagneticExtension extends BlockMorePistonsExtension {

	protected IIcon iconTop;
	protected IIcon iconBack;
	protected IIcon iconSide;
	
	public BlockMorePistonsMagneticExtension(String registerName) {
		super(registerName);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop  = helper.loadTexture(iconRegister, "top" );
		this.iconBack = helper.loadTexture(iconRegister, "back");
		this.iconSide = helper.loadTexture(iconRegister, "side");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		int direction = getDirectionMeta(metadata);
		if (side == direction) {
			return this.iconTop;
		}
		
		if (direction < 6 && side == Facing.oppositeSide[direction]) {
			return this.iconBack;
		}
		
		return this.iconSide;
	}
}