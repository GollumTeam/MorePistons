package com.gollum.morepistons.common.block;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMorePistonsMagneticExtension extends BlockMorePistonsExtension {

	protected Icon iconTop;
	protected Icon iconBack;
	protected Icon iconSide;
	
	public BlockMorePistonsMagneticExtension(int id, String registerName) {
		super(id, registerName);
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.iconTop  = helper.loadTexture(iconRegister, "_top" );
		this.iconBack = helper.loadTexture(iconRegister, "_back");
		this.iconSide = helper.loadTexture(iconRegister, "_side");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int metadata) {
		
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