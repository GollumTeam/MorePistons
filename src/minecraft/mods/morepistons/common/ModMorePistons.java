package mods.morepistons.common;

import net.minecraft.block.Block;
import mods.gollum.core.config.ConfigLoader;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.log.Logger;
import mods.gollum.core.tab.GollumCreativeTabs;
import mods.gollum.core.version.VersionChecker;
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
	
	/**
	 * Gestion des logs
	 */
	public static Logger log;
	
	/**
	 * Onglet du mod
	 */
	public static GollumCreativeTabs morePistonsTabs;
	
	
	@ConfigProp(group = "Blocks Ids")  public static int idBlockDoublePistonBase              = 4095;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockDoubleStickyPistonBase        = 4094;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockTriplePistonBase              = 4093;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockTripleStickyPistonBase        = 4092;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockQuadPistonBase                = 4091;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockQuadStickyPistonBase          = 4090;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockQuintPistonBase               = 4083;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockQuintStickyPistonBase         = 4082;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockSextPistonBase                = 4081;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockSextStickyPistonBase          = 4080;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockSeptPistonBase                = 4079;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockSeptStickyPistonBase          = 4078;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockOctStickyPistonBase           = 4077;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockOctPistonBase                 = 4076;
	
	@ConfigProp(group = "Blocks Ids")  public static int idBlockGravitationalPistonBase       = 4089;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockGravitationalStickyPistonBase = 4088;
	
	@ConfigProp(group = "Blocks Ids")  public static int idBlockPistonExtension               = 4087;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockPistonRod                     = 4086;
	
	@ConfigProp(group = "Blocks Ids")  public static int idBlockStrongPistonBase              = 4085;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockStrongStickyPistonBase        = 4084;
	
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase1           = 4075;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase2           = 4074;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase3           = 4073;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase4           = 4072;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase5           = 4071;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase6           = 4070;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase7           = 4069;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStonePistonBase8           = 4068;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase1     = 4067;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase2     = 4066;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase3     = 4065;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase4     = 4064;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase5     = 4063;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase6     = 4062;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase7     = 4061;
	@ConfigProp(group = "Blocks Ids")  public static int idBlockRedStoneStickyPistonBase8     = 4060;

	/////////////////////
	// Liste des blocs //
	/////////////////////
	public static Block doublePistonBase;
	public static Block doubleStickyPistonBase;
	public static Block triplePistonBase;
	public static Block tripleStickyPistonBase;
	public static Block quadruplePistonBase;
	public static Block quadrupleStickyPistonBase;
	public static Block quintuplePistonBase;
	public static Block quintupleStickyPistonBase;
	public static Block sextuplePistonBase;
	public static Block sextupleStickyPistonBase;
	public static Block septuplePistonBase;
	public static Block septupleStickyPistonBase;
	public static Block octuplePistonBase;
	public static Block octupleStickyPistonBase;
	public static Block gravitationalPistonBase;
	public static Block gravitationalStickyPistonBase;
	public static Block superPistonBase;
	public static Block superStickyPistonBase;
	public static Block redStonePistonBase1;
	public static Block redStoneStickyPistonBase1;
	public static Block redStonePistonBase2;
	public static Block redStoneStickyPistonBase2;
	public static Block redStonePistonBase3;
	public static Block redStoneStickyPistonBase3;
	public static Block redStonePistonBase4;
	public static Block redStoneStickyPistonBase4;
	public static Block redStonePistonBase5;
	public static Block redStoneStickyPistonBase5;
	public static Block redStonePistonBase6;
	public static Block redStoneStickyPistonBase6;
	public static Block redStonePistonBase7;
	public static Block redStoneStickyPistonBase7;
	public static Block redStonePistonBase8;
	public static Block redStoneStickyPistonBase8;
	public static Block pistonExtension;
	public static Block pistonRod; 
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		// Creation du logger
		log = new Logger(event);
		
		// Charge la configuration
		ConfigLoader configLoader = new ConfigLoader(this.getClass(), event);
		configLoader.loadConfig();
		
		//Test la version du mod
		VersionChecker.getInstance().check(this);
	}
	
	/** 2 **/
	@EventHandler
	public void load(FMLInitializationEvent event) {
		
		
		this.morePistonsTabs = new GollumCreativeTabs("Pistons");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Pistons", "en_US", "Pistons");
		
		proxy.registerRenderers();
		
		
		// Initialisation des blocks
		this.initBlocks ();
		
		// Place le piston normal dans le creative tab
		Block.pistonBase.setCreativeTab(morePistonsTabs);
		Block.pistonStickyBase.setCreativeTab(morePistonsTabs);
		
		// Set de l'icon du tab creative
		this.morePistonsTabs.setIcon(Block.pistonBase);
	}

	/** 3 **/
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	
	/**
	 * Initialisation des blocks
	 */
	public void initBlocks() {
		
	}
	
}
