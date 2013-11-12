package mods.morepistons.common;

import net.minecraft.block.Block;
import mods.morepistons.utils.ConfigLoader;
import mods.morepistons.utils.ConfigProp;
import mods.morepistons.utils.Logger;
import mods.morepistons.utils.VersionChecker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "More Pistons", name = "More Pistons", version = "1.5.0 [Build Smeagol]", acceptedMinecraftVersions = "1.6.4")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModMorePistons {

	@Instance("ModMorePistons")
	public static ModMorePistons instance;
	
	@SidedProxy(clientSide = "mods.morepistons.common.ClientProxyMorePistons", serverSide = "mods.morepistons.common.CommonProxyMorePistons")
	public static CommonProxyMorePistons proxy;

	@ConfigProp (info = "Show debug infos")
	public static boolean DEBUG = false;
	
	@ConfigProp (info = "Display version checker message")
	public static boolean versionChecker = true;
	
//	public static boolean configFlag;
	
	/**
	 * Onglet du mod
	 */
	public static MorePistonsTabs morePistonsTabs;
	
	
	@ConfigProp public static int idBlockDoublePistonBase              = 4095;
	@ConfigProp public static int idBlockDoubleStickyPistonBase        = 4094;
	@ConfigProp public static int idBlockTriplePistonBase              = 4093;
	@ConfigProp public static int idBlockTripleStickyPistonBase        = 4092;
	@ConfigProp public static int idBlockQuadPistonBase                = 4091;
	@ConfigProp public static int idBlockQuadStickyPistonBase          = 4090;
	@ConfigProp public static int idBlockQuintPistonBase               = 4083;
	@ConfigProp public static int idBlockQuintStickyPistonBase         = 4082;
	@ConfigProp public static int idBlockSextPistonBase                = 4081;
	@ConfigProp public static int idBlockSextStickyPistonBase          = 4080;
	@ConfigProp public static int idBlockSeptPistonBase                = 4079;
	@ConfigProp public static int idBlockSeptStickyPistonBase          = 4078;
	@ConfigProp public static int idBlockOctStickyPistonBase           = 4077;
	@ConfigProp public static int idBlockOctPistonBase                 = 4076;
	
	@ConfigProp public static int idBlockGravitationalPistonBase       = 4089;
	@ConfigProp public static int idBlockGravitationalStickyPistonBase = 4088;
	
	@ConfigProp public static int idBlockPistonExtension               = 4087;
	@ConfigProp public static int idBlockPistonRod                     = 4086;
	
	@ConfigProp public static int idBlockStrongPistonBase              = 4085;
	@ConfigProp public static int idBlockStrongStickyPistonBase        = 4084;
	
	@ConfigProp public static int idBlockRedStonePistonBase1           = 4075;
	@ConfigProp public static int idBlockRedStonePistonBase2           = 4074;
	@ConfigProp public static int idBlockRedStonePistonBase3           = 4073;
	@ConfigProp public static int idBlockRedStonePistonBase4           = 4072;
	@ConfigProp public static int idBlockRedStonePistonBase5           = 4071;
	@ConfigProp public static int idBlockRedStonePistonBase6           = 4070;
	@ConfigProp public static int idBlockRedStonePistonBase7           = 4069;
	@ConfigProp public static int idBlockRedStonePistonBase8           = 4068;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase1     = 4067;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase2     = 4066;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase3     = 4065;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase4     = 4064;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase5     = 4063;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase6     = 4062;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase7     = 4061;
	@ConfigProp public static int idBlockRedStoneStickyPistonBase8     = 4060;
//	
//	private static String texturePath = "morepistons:";



	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigLoader configLoader = new ConfigLoader(this.getClass(), event);
		configLoader.loadConfig();
	}
	
	/** 2 **/
	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		Logger.init(this.getClass (), DEBUG);
		VersionChecker.getInstance(this.versionChecker).check(this);
		
		morePistonsTabs = new MorePistonsTabs("Pistons", Block.pistonBase.blockID);
		LanguageRegistry.instance().addStringLocalization("itemGroup.Pistons", "en_US", "Pistons");
		
		proxy.registerRenderers();
		new MorePistonsBlocks();
//		new MorePistons();
//		new MorePistonsRecipes();
		
		Block.pistonBase.setCreativeTab(morePistonsTabs);
		Block.pistonStickyBase.setCreativeTab(morePistonsTabs);
		
	}

	/** 3 **/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
	
//	/**
//	 * Renvoie la texture
//	 * @param key
//	 * @return 
//	 */
//	public static String getTexture (String key) {
//		return texturePath+key;
//	}
	

	
//	public static boolean isPistonId (int id) {
//		return 
//				id == Block.pistonBase.blockID ||
//				id == Block.pistonStickyBase.blockID ||
//				id == MorePistons.doublePistonBase.blockID ||
//				id == MorePistons.doubleStickyPistonBase.blockID ||
//				id == MorePistons.triplePistonBase.blockID ||
//				id == MorePistons.tripleStickyPistonBase.blockID ||
//				id == MorePistons.quadruplePistonBase.blockID ||
//				id == MorePistons.quadrupleStickyPistonBase.blockID ||
//				id == MorePistons.quintuplePistonBase.blockID ||
//				id == MorePistons.quintupleStickyPistonBase.blockID ||
//				id == MorePistons.sextuplePistonBase.blockID ||
//				id == MorePistons.sextupleStickyPistonBase.blockID ||
//				id == MorePistons.septuplePistonBase.blockID ||
//				id == MorePistons.septupleStickyPistonBase.blockID ||
//				id == MorePistons.octuplePistonBase.blockID ||
//				id == MorePistons.octupleStickyPistonBase.blockID ||
//				id == MorePistons.redStoneStickyPistonBase1.blockID ||
//				id == MorePistons.redStoneStickyPistonBase2.blockID ||
//				id == MorePistons.redStoneStickyPistonBase3.blockID ||
//				id == MorePistons.redStoneStickyPistonBase4.blockID ||
//				id == MorePistons.redStoneStickyPistonBase5.blockID ||
//				id == MorePistons.redStoneStickyPistonBase6.blockID ||
//				id == MorePistons.redStoneStickyPistonBase7.blockID ||
//				id == MorePistons.redStoneStickyPistonBase8.blockID ||
//				id == MorePistons.redStonePistonBase1.blockID ||
//				id == MorePistons.redStonePistonBase2.blockID ||
//				id == MorePistons.redStonePistonBase3.blockID ||
//				id == MorePistons.redStonePistonBase4.blockID ||
//				id == MorePistons.redStonePistonBase5.blockID ||
//				id == MorePistons.redStonePistonBase6.blockID ||
//				id == MorePistons.redStonePistonBase7.blockID ||
//				id == MorePistons.redStonePistonBase8.blockID ||
//				id == MorePistons.gravitationalPistonBase.blockID ||
//				id == MorePistons.gravitationalStickyPistonBase.blockID ||
//				id == MorePistons.superPistonBase.blockID ||
//				id == MorePistons.superStickyPistonBase.blockID;
//				
//	}
//	
//
//	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4, int length) {
//		return new TileEntityMorePistons(par0, par1, par2, par3, par4, length);
//	}
//
//	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4, int length, boolean isRoot) {
//		return new TileEntityMorePistons(par0, par1, par2, par3, par4, length, isRoot);
//	}
	
}
