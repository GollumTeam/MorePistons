package morePistons;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;

public class MorePistons
{
    public static Block doublePistonBase;
    public static Block doubleStickyPistonBase;
    public static Block triplePistonBase;
    public static Block tripleStickyPistonBase;
    public static Block quadruplePistonBase;
    public static Block quadrupleStickyPistonBase;
    public static Block gravitationalPistonBase;
    public static Block superPistonBase;
    public static Block superStickyPistonBase;
    public static Block pistonExtension;
    public static Block pistonRod;
    public static int[] pistonList = new int[] {Block.pistonBase.blockID, Block.pistonStickyBase.blockID, mod_MorePistons.idBlockDoublePistonBase, mod_MorePistons.idBlockDoubleStickyPistonBase, mod_MorePistons.idBlockTriplePistonBase, mod_MorePistons.idBlockTripleStickyPistonBase, mod_MorePistons.idBlockQuadPistonBase, mod_MorePistons.idBlockQuadStickyPistonBase, mod_MorePistons.idBlockGravitationalPistonBase, mod_MorePistons.idBlockStrongPistonBase, mod_MorePistons.idBlockStrongStickyPistonBase};

    public MorePistons()
    {
        this.RegisterBlocks(new Block[] {doublePistonBase, doubleStickyPistonBase, triplePistonBase, tripleStickyPistonBase, quadruplePistonBase, quadrupleStickyPistonBase, gravitationalPistonBase, pistonExtension, pistonRod, superPistonBase, superStickyPistonBase});
        LanguageRegistry.addName(doublePistonBase, "Double Piston");
        LanguageRegistry.addName(doubleStickyPistonBase, "Double Sticky Piston");
        LanguageRegistry.addName(triplePistonBase, "Triple Piston");
        LanguageRegistry.addName(tripleStickyPistonBase, "Triple Sticky Piston");
        LanguageRegistry.addName(quadruplePistonBase, "Quadruple Piston");
        LanguageRegistry.addName(quadrupleStickyPistonBase, "Quadruple Sticky Piston");
        LanguageRegistry.addName(gravitationalPistonBase, "Gravitational Piston");
        LanguageRegistry.addName(superPistonBase, "Super Piston");
        LanguageRegistry.addName(superStickyPistonBase, "Super Sticky Piston");
        LanguageRegistry.addName(pistonExtension, "Piston Extension");
        LanguageRegistry.addName(pistonRod, "Piston Rod");
    }

    public void RegisterBlocks(Block[] var1)
    {
        Block[] var2 = var1;
        int var3 = var1.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            Block var5 = var2[var4];
            GameRegistry.registerBlock(var5);
        }
    }
}
