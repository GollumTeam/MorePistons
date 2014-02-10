package morePistons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.Configuration;

@NetworkMod(
        clientSideRequired = true,
        serverSideRequired = true
)
@Mod(
        modid = "More Pistons",
        name = "More Pistons",
        version = "1.2.4 for [1.4.7]"
)
public class mod_MorePistons
{
    public static boolean configFlag;
    public static int idBlockDoublePistonBase;
    public static int idBlockDoubleStickyPistonBase;
    public static int idBlockTriplePistonBase;
    public static int idBlockTripleStickyPistonBase;
    public static int idBlockQuadPistonBase;
    public static int idBlockQuadStickyPistonBase;
    public static int idBlockGravitationalPistonBase;
    public static int idBlockPistonExtension;
    public static int idBlockPistonRod;
    public static int idBlockStrongPistonBase;
    public static int idBlockStrongStickyPistonBase;
    @SidedProxy(
            clientSide = "morePistons.ClientProxyMorePistons",
            serverSide = "morePistons.CommonProxyMorePistons"
    )
    public static CommonProxyMorePistons proxy;

    @PreInit
    public void preInit(FMLPreInitializationEvent var1)
    {
        Configuration var2 = new Configuration(var1.getSuggestedConfigurationFile());
        var2.load();
        idBlockDoublePistonBase = var2.getBlock("Double Piston", 210).getInt();
        idBlockDoubleStickyPistonBase = var2.getBlock("Double Sticky Piston", 211).getInt();
        idBlockTriplePistonBase = var2.getBlock("Triple Piston", 212).getInt();
        idBlockTripleStickyPistonBase = var2.getBlock("Triple Sticky Piston", 213).getInt();
        idBlockQuadPistonBase = var2.getBlock("Quadruple Piston", 214).getInt();
        idBlockQuadStickyPistonBase = var2.getBlock("Quadruple Sticky Piston", 215).getInt();
        idBlockGravitationalPistonBase = var2.getBlock("Gravitational Piston", 216).getInt();
        idBlockPistonExtension = var2.getBlock("Piston Extension", 217).getInt();
        idBlockPistonRod = var2.getBlock("Piston Rod", 218).getInt();
        idBlockStrongPistonBase = var2.getBlock("Super Piston", 219).getInt();
        idBlockStrongStickyPistonBase = var2.getBlock("Super Sticky Piston", 220).getInt();
        var2.save();
    }

    @Init
    public void morePistons(FMLInitializationEvent var1)
    {
        proxy.registerRenderThings();
        new MorePistonsBlocks();
        new MorePistons();
        new MorePistonsRecipes();
        configFlag = false;
    }

    public static TileEntity getTileEntity(int var0, int var1, int var2, boolean var3, boolean var4)
    {
        return new TileEntityMorePistons(var0, var1, var2, var3, var4);
    }
}
