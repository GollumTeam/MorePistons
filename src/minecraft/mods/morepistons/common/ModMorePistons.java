package mods.morepistons.common;

import net.minecraft.block.Block;
import mods.gollum.core.config.ConfigLoader;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.log.Logger;
import mods.gollum.core.version.VersionChecker;
import mods.morepistons.common.block.BlockMorePistonBase;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
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
	
	
	@ConfigProp(group = "Blocks Ids")  public static int blockDoublePistonBaseID              = 4095;
	@ConfigProp(group = "Blocks Ids")  public static int blockDoubleStickyPistonBaseID        = 4094;
	@ConfigProp(group = "Blocks Ids")  public static int blockTriplePistonBaseID              = 4093;
	@ConfigProp(group = "Blocks Ids")  public static int blockTripleStickyPistonBaseID        = 4092;
	@ConfigProp(group = "Blocks Ids")  public static int blockQuadPistonBaseID                = 4091;
	@ConfigProp(group = "Blocks Ids")  public static int blockQuadStickyPistonBaseID          = 4090;
	@ConfigProp(group = "Blocks Ids")  public static int blockQuintPistonBaseID               = 4083;
	@ConfigProp(group = "Blocks Ids")  public static int blockQuintStickyPistonBaseID         = 4082;
	@ConfigProp(group = "Blocks Ids")  public static int blockSextPistonBaseID                = 4081;
	@ConfigProp(group = "Blocks Ids")  public static int blockSextStickyPistonBaseID          = 4080;
	@ConfigProp(group = "Blocks Ids")  public static int blockSeptPistonBaseID                = 4079;
	@ConfigProp(group = "Blocks Ids")  public static int blockSeptStickyPistonBaseID          = 4078;
	@ConfigProp(group = "Blocks Ids")  public static int blockOctStickyPistonBaseID           = 4077;
	@ConfigProp(group = "Blocks Ids")  public static int blockOctPistonBaseID                 = 4076;
	
	@ConfigProp(group = "Blocks Ids")  public static int blockGravitationalPistonBaseID       = 4089;
	@ConfigProp(group = "Blocks Ids")  public static int blockGravitationalStickyPistonBaseID = 4088;
	
	@ConfigProp(group = "Blocks Ids")  public static int blockPistonExtensionID               = 4087;
	@ConfigProp(group = "Blocks Ids")  public static int blockPistonRodID                     = 4086;
	
	@ConfigProp(group = "Blocks Ids")  public static int blockStrongPistonBaseID              = 4085;
	@ConfigProp(group = "Blocks Ids")  public static int blockStrongStickyPistonBaseID        = 4084;
	
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase1ID           = 4075;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase2ID           = 4074;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase3ID           = 4073;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase4ID           = 4072;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase5ID           = 4071;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase6ID           = 4070;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase7ID           = 4069;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStonePistonBase8ID           = 4068;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase1ID     = 4067;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase2ID     = 4066;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase3ID     = 4065;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase4ID     = 4064;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase5ID     = 4063;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase6ID     = 4062;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase7ID     = 4061;
	@ConfigProp(group = "Blocks Ids")  public static int blockRedStoneStickyPistonBase8ID     = 4060;

	/////////////////////
	// Liste des blocs //
	/////////////////////
	public static Block blockDoublePistonBase;
	public static Block blockDoubleStickyPistonBase;
	public static Block blockTriplePistonBase;
	public static Block blockTripleStickyPistonBase;
	public static Block blockQuadruplePistonBase;
	public static Block blockQuadrupleStickyPistonBase;
	public static Block blockQuintuplePistonBase;
	public static Block blockQuintupleStickyPistonBase;
	public static Block blockSextuplePistonBase;
	public static Block blockSextupleStickyPistonBase;
	public static Block blockSeptuplePistonBase;
	public static Block blockSeptupleStickyPistonBase;
	public static Block blockOctuplePistonBase;
	public static Block blockOctupleStickyPistonBase;
	public static Block blockGravitationalPistonBase;
	public static Block blockGravitationalStickyPistonBase;
	public static Block blocksuperPistonBase;
	public static Block blocksuperStickyPistonBase;
	public static Block blockRedStonePistonBase1;
	public static Block blockRedStoneStickyPistonBase1;
	public static Block blockRedStonePistonBase2;
	public static Block blockRedStoneStickyPistonBase2;
	public static Block blockRedStonePistonBase3;
	public static Block blockRedStoneStickyPistonBase3;
	public static Block blockRedStonePistonBase4;
	public static Block blockRedStoneStickyPistonBase4;
	public static Block blockRedStonePistonBase5;
	public static Block blockRedStoneStickyPistonBase5;
	public static Block blockRedStonePistonBase6;
	public static Block blockRedStoneStickyPistonBase6;
	public static Block blockRedStonePistonBase7;
	public static Block blockRedStoneStickyPistonBase7;
	public static Block blockRedStonePistonBase8;
	public static Block blockRedStoneStickyPistonBase8;
	public static Block blockPistonExtension;
	public static Block blockPistonRod; 
	
	/**
	 * Path des texture du mod
	 */
	public static final String PATH_TEXTURES = "morepistons:";
	
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

		// Initialisation les TileEntities
		this.initTileEntities ();
		
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
		
		// Création des blocks
		this.blockDoublePistonBase  = (new BlockMorePistonBase(this.blockDoublePistonBaseID, false, "double_")).setLength (2).setUnlocalizedName("DoublePistonBase");
		
		// Enregistrement des blocks
		GameRegistry.registerBlock(this.blockDoublePistonBase , this.blockDoublePistonBase.getUnlocalizedName());
		
		// Nom des blocks
		LanguageRegistry.addName(this.blockDoublePistonBase , "Double Piston");
	}
	
	/**
	* // Nom des TileEntities
	*/
	private void initTileEntities () {
		GameRegistry.registerTileEntity(TileEntityMorePistons.class , "MorePistons");
	}
}
