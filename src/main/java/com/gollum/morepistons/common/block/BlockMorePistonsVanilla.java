package com.gollum.morepistons.common.block;

import net.minecraft.init.Blocks;

public class BlockMorePistonsVanilla extends BlockMorePistonsBase {
	
	public BlockMorePistonsVanilla(String registerName, boolean isSticky) {
		super(registerName, isSticky);
	}
	
	public String getUnlocalizedName()
    {
        return isSticky ? Blocks.sticky_piston.getUnlocalizedName() : Blocks.piston.getUnlocalizedName();
    }
}
