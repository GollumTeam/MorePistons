package mods.morepistons.common.block;

import static mods.morepistons.ModMorePistons.log;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;

public class BlockMorePistonsVanilla extends BlockMorePistonsBase {
	
	public BlockMorePistonsVanilla(String registerName, boolean isSticky) {
		super(registerName, isSticky);
		helper.vanillaTexture = true;
	}
	
}
