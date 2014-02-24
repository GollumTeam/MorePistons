package mods.morepistons.common;

import net.minecraft.block.Block;
import mods.gollum.core.config.ConfigLoader;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.log.Logger;
import mods.gollum.core.version.VersionChecker;
import mods.morepistons.common.block.BlockMorePistonBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsRod;
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

@Mod(modid = "MorePistons", name = "More Pistons", version = "1.5.0 [Build Smeagol]", acceptedMinecraftVersions = "1.6.4")
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
	
	public static Block blockSuperPistonBase;
	public static Block blockSuperStickyPistonBase;
	
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
		
		// Initialisation des blocks
		this.initBlocks ();
		
		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Initialisation du proxy
		proxy.registerRenderers();
		
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
		this.blockPistonExtension = new BlockMorePistonsExtension(this.blockPistonExtensionID).setUnlocalizedName("MorePistonsExtension");
		this.blockPistonRod       = new BlockMorePistonsRod      (this.blockPistonRodID)      .setUnlocalizedName("MorePistonsRod");
		
		this.blockDoublePistonBase           = (new BlockMorePistonBase(this.blockDoublePistonBaseID      , false, "double_")).setLength (2).setUnlocalizedName("DoublePistonBase");
		this.blockDoubleStickyPistonBase     = (new BlockMorePistonBase(this.blockDoubleStickyPistonBaseID, true , "double_")).setLength (2).setUnlocalizedName("DoubleStikyPistonBase");
		this.blockTriplePistonBase           = (new BlockMorePistonBase(this.blockTriplePistonBaseID      , false, "triple_")).setLength (3).setUnlocalizedName("TriplePistonBase");
		this.blockTripleStickyPistonBase     = (new BlockMorePistonBase(this.blockTripleStickyPistonBaseID, true , "triple_")).setLength (3).setUnlocalizedName("TripleStikyPistonBase");
		this.blockQuadruplePistonBase        = (new BlockMorePistonBase(this.blockQuadPistonBaseID        , false, "quad_"  )).setLength (4).setUnlocalizedName("QuadruplePistonBase");
		this.blockQuadrupleStickyPistonBase  = (new BlockMorePistonBase(this.blockQuadStickyPistonBaseID  , true , "quad_"  )).setLength (4).setUnlocalizedName("QuadrupleStikyPistonBase");
		this.blockQuintuplePistonBase        = (new BlockMorePistonBase(this.blockQuintPistonBaseID       , false, "quint_" )).setLength (5).setUnlocalizedName("QuintuplePistonBase");
		this.blockQuintupleStickyPistonBase  = (new BlockMorePistonBase(this.blockQuintStickyPistonBaseID , true , "quint_" )).setLength (5).setUnlocalizedName("QuintupleStikyPistonBase");
		this.blockSextuplePistonBase         = (new BlockMorePistonBase(this.blockSextPistonBaseID        , false, "sext_"  )).setLength (6).setUnlocalizedName("SextuplePistonBase");
		this.blockSextupleStickyPistonBase   = (new BlockMorePistonBase(this.blockSextStickyPistonBaseID  , true , "sext_"  )).setLength (6).setUnlocalizedName("SextupleStikyPistonBase");
		this.blockSeptuplePistonBase         = (new BlockMorePistonBase(this.blockSeptPistonBaseID        , false, "sept_"  )).setLength (7).setUnlocalizedName("SeptuplePistonBase");
		this.blockSeptupleStickyPistonBase   = (new BlockMorePistonBase(this.blockSeptStickyPistonBaseID  , true , "sept_"  )).setLength (7).setUnlocalizedName("SeptupleStikyPistonBase");
		this.blockOctuplePistonBase          = (new BlockMorePistonBase(this.blockOctPistonBaseID         , false, "oct_"   )).setLength (8).setUnlocalizedName("OctuplePistonBase");
		this.blockOctupleStickyPistonBase    = (new BlockMorePistonBase(this.blockOctStickyPistonBaseID   , true , "oct_"   )).setLength (8).setUnlocalizedName("OctupleStikyPistonBase");
		
		// Enregistrement des blocks
		GameRegistry.registerBlock(this.blockPistonExtension          , this.blockPistonExtension          .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockPistonRod                , this.blockPistonRod                .getUnlocalizedName());
		
		GameRegistry.registerBlock(this.blockDoublePistonBase         , this.blockDoublePistonBase         .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockDoubleStickyPistonBase   , this.blockDoubleStickyPistonBase   .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockTriplePistonBase         , this.blockTriplePistonBase         .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockTripleStickyPistonBase   , this.blockTripleStickyPistonBase   .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockQuadruplePistonBase      , this.blockQuadruplePistonBase      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockQuadrupleStickyPistonBase, this.blockQuadrupleStickyPistonBase.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockQuintuplePistonBase      , this.blockQuintuplePistonBase      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockQuintupleStickyPistonBase, this.blockQuintupleStickyPistonBase.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSextuplePistonBase       , this.blockSextuplePistonBase       .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSextupleStickyPistonBase , this.blockSextupleStickyPistonBase .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSeptuplePistonBase       , this.blockSeptuplePistonBase       .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSeptupleStickyPistonBase , this.blockSeptupleStickyPistonBase .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockOctuplePistonBase        , this.blockOctuplePistonBase        .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockOctupleStickyPistonBase  , this.blockOctupleStickyPistonBase  .getUnlocalizedName());
		
		// Nom des blocks
		LanguageRegistry.addName(this.blockDoublePistonBase         , "Double Piston");
		LanguageRegistry.addName(this.blockDoubleStickyPistonBase   , "Double Sticky Piston");
		LanguageRegistry.addName(this.blockTriplePistonBase         , "Triple Piston");
		LanguageRegistry.addName(this.blockTripleStickyPistonBase   , "Triple Sticky Piston");
		LanguageRegistry.addName(this.blockQuadruplePistonBase      , "Quadruple Piston");
		LanguageRegistry.addName(this.blockQuadrupleStickyPistonBase, "Quadruple Sticky Piston");
		LanguageRegistry.addName(this.blockQuintuplePistonBase      , "Quintuple Piston");
		LanguageRegistry.addName(this.blockQuintupleStickyPistonBase, "Quintuple Sticky Piston");
		LanguageRegistry.addName(this.blockSextuplePistonBase       , "Sextuple Piston");
		LanguageRegistry.addName(this.blockSextupleStickyPistonBase , "Sextuple Sticky Piston");
		LanguageRegistry.addName(this.blockSeptuplePistonBase       , "Septuple Sticky Piston");
		LanguageRegistry.addName(this.blockSeptupleStickyPistonBase , "Septuple Piston");
		LanguageRegistry.addName(this.blockOctuplePistonBase        , "Octuple Sticky Piston");
		LanguageRegistry.addName(this.blockOctupleStickyPistonBase  , "Octuple Sticky Piston");
	}
	
	/**
	* // Nom des TileEntities
	*/
	private void initTileEntities () {
		GameRegistry.registerTileEntity(TileEntityMorePistons.class , "MorePistons");
	}
}
