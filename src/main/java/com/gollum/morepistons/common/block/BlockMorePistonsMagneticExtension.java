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
	
	public BlockMorePistonsMagneticExtension(String registerName) {
		super(registerName);
		this.helper.vanillaTexture = true;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(ModMorePistons.MODID+":top_magnetic");
		super.registerBlockIcons(iconRegister);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		int direction = getDirectionMeta(metadata);
		if (side == direction) {
			return this.iconTop;
		}
		return super.getIcon(side, metadata);
	}
}