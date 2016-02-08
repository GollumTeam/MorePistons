package com.gollum.morepistons.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMorePistonsMagneticExtension extends BlockMorePistonsExtension {

	protected IIcon iconTop;
	protected IIcon iconBack;
	protected IIcon iconSide;
	
	public BlockMorePistonsMagneticExtension(String registerName) {
		super(registerName);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop  = helper.loadTexture(iconRegister, "_top" );
		this.iconBack = helper.loadTexture(iconRegister, "_back");
		this.iconSide = helper.loadTexture(iconRegister, "_side");
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