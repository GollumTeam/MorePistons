package morePistons;

public class MorePistonsBlocks
{
    public MorePistonsBlocks()
    {
        MorePistons.doublePistonBase = (new BlockDoublePistonBase(mod_MorePistons.idBlockDoublePistonBase, 107, false)).setBlockName("doublePistonBase");
        MorePistons.doubleStickyPistonBase = (new BlockDoublePistonBase(mod_MorePistons.idBlockDoubleStickyPistonBase, 106, true)).setBlockName("doubleStickyPistonBase");
        MorePistons.triplePistonBase = (new BlockTriplePistonBase(mod_MorePistons.idBlockTriplePistonBase, 107, false)).setBlockName("triplePistonBase");
        MorePistons.tripleStickyPistonBase = (new BlockTriplePistonBase(mod_MorePistons.idBlockTripleStickyPistonBase, 106, true)).setBlockName("tripleStickyPistonBase");
        MorePistons.quadruplePistonBase = (new BlockQuadPistonBase(mod_MorePistons.idBlockQuadPistonBase, 107, false)).setBlockName("quadPistonBase");
        MorePistons.quadrupleStickyPistonBase = (new BlockQuadPistonBase(mod_MorePistons.idBlockQuadStickyPistonBase, 106, true)).setBlockName("quadStickyPistonBase");
        MorePistons.gravitationalPistonBase = (new BlockGravitationalPistonBase(mod_MorePistons.idBlockGravitationalPistonBase, 107, false)).setBlockName("gravitationalPistonBase");
        MorePistons.superPistonBase = (new BlockSuperPistonBase(mod_MorePistons.idBlockStrongPistonBase, 107, false)).setBlockName("strongPistonBase").setRequiresSelfNotify();
        MorePistons.superStickyPistonBase = (new BlockSuperPistonBase(mod_MorePistons.idBlockStrongStickyPistonBase, 106, true)).setBlockName("strongStickyPistonBase").setRequiresSelfNotify();
        MorePistons.pistonExtension = (BlockMorePistonsExtension)(new BlockMorePistonsExtension(mod_MorePistons.idBlockPistonExtension, 107)).setBlockName("morePistonsExtension");
        MorePistons.pistonRod = (new BlockPistonRod(mod_MorePistons.idBlockPistonRod, 107)).setBlockName("pistonRod");
    }
}
