package morepistons;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraftforge.common.Configuration;
import net.minecraft.tileentity.TileEntity;// any;

@Mod(modid = "more-pistons", name = "More Pistons", version = "X.X.X for [1.6.2 Build By: Master801]")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModMorePistons {

	@Instance("ModMorePistons")
	public static ModMorePistons instance;
	
	@SidedProxy(clientSide = "morepistons.ClientProxyMorePistons", serverSide = "morepistons.CommonProxyMorePistons")
	public static CommonProxyMorePistons proxy;
	
	private static boolean DEBUG = true;
	
	public static boolean configFlag;
	public static int idBlockDoublePistonBase;
	public static int idBlockDoubleStickyPistonBase;
	public static int idBlockTriplePistonBase;
	public static int idBlockTripleStickyPistonBase;
	public static int idBlockQuadPistonBase;
	public static int idBlockQuadStickyPistonBase;
	public static int idBlockQuintPistonBase;
	public static int idBlockQuintStickyPistonBase;
	public static int idBlockSextPistonBase;
	public static int idBlockSextStickyPistonBase;
	public static int idBlockSeptPistonBase;
	public static int idBlockOctStickyPistonBase;
	public static int idBlockOctPistonBase;
	public static int idBlockSeptStickyPistonBase;
	public static int idBlockGravitationalPistonBase;
	public static int idBlockGravitationalStickyPistonBase;
	public static int idBlockPistonExtension;
	public static int idBlockPistonRod;
	public static int idBlockStrongPistonBase;
	public static int idBlockStrongStickyPistonBase;
	
	private static String texturePath = "morepistons:";


	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		idBlockDoublePistonBase              = config.getBlock("Double Piston", 1000)              .getInt();
		idBlockDoubleStickyPistonBase        = config.getBlock("Double Sticky Piston", 1001)       .getInt();
		idBlockTriplePistonBase              = config.getBlock("Triple Piston", 1002)              .getInt();
		idBlockTripleStickyPistonBase        = config.getBlock("Triple Sticky Piston", 1003)       .getInt();
		idBlockQuadPistonBase                = config.getBlock("Quadruple Piston", 1004)           .getInt();
		idBlockQuadStickyPistonBase          = config.getBlock("Quadruple Sticky Piston", 1005)    .getInt();
		idBlockGravitationalPistonBase       = config.getBlock("Gravitational Piston", 1006)       .getInt();
		idBlockGravitationalStickyPistonBase = config.getBlock("Gravitational Sticky Piston", 1007).getInt();
		idBlockPistonExtension               = config.getBlock("Piston Extension", 1008)           .getInt();
		idBlockPistonRod                     = config.getBlock("Piston Rod", 1009)                 .getInt();
		idBlockStrongPistonBase              = config.getBlock("Super Piston", 1010)               .getInt();
		idBlockStrongStickyPistonBase        = config.getBlock("Super Sticky Piston", 1011)        .getInt();
		idBlockQuintPistonBase               = config.getBlock("Quintuple Piston", 1012)           .getInt();
		idBlockQuintStickyPistonBase         = config.getBlock("Quintuple Sticky Piston", 1013)    .getInt();
		idBlockSextPistonBase                = config.getBlock("Sextuple Piston", 1014)           .getInt();
		idBlockSextStickyPistonBase          = config.getBlock("Sextuple Sticky Piston", 1015)    .getInt();
		idBlockSeptPistonBase                = config.getBlock("Septuple Piston", 1016)           .getInt();
		idBlockSeptStickyPistonBase          = config.getBlock("Septuple Sticky Piston", 1017)    .getInt();
		idBlockOctPistonBase                = config.getBlock("Octuple Piston", 1018)           .getInt();
		idBlockOctStickyPistonBase          = config.getBlock("Octuple Sticky Piston", 1019)    .getInt();
		
		config.save();
	}
	
	/** 2 **/
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
		new MorePistonsBlocks();
		new MorePistons();
		new MorePistonsRecipes();
		
		configFlag = false;
	}

	/** 3 **/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
	
	/**
	 * Renvoie la texture
	 * @param key
	 * @return 
	 */
	public static String getTexture (String key) {
		return texturePath+key;
	}
	
	/**
	 * Affiche un log
	 * @param str
	 */
	public static void log (String str) {
		if (DEBUG) {
			System.out.println (str);
		}
	}
	

	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4, int length) {
		return new TileEntityMorePistons(par0, par1, par2, par3, par4, length);
	}

	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4, int length, boolean isRoot) {
		return new TileEntityMorePistons(par0, par1, par2, par3, par4, length, isRoot);
	}
	
}
