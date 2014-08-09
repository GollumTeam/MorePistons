package mods.morepistons;

import mods.gollum.core.config.ConfigLoader;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.facory.BlockFactory;
import mods.gollum.core.log.Logger;
import mods.gollum.core.version.VersionChecker;
import mods.morepistons.common.CommonProxyMorePistons;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsGravitational;
import mods.morepistons.common.block.BlockMorePistonsRedStone;
import mods.morepistons.common.block.BlockMorePistonsRod;
import mods.morepistons.common.block.BlockMorePistonsSuper;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

@Mod(modid = ModMorePistons.MODID, name = ModMorePistons.MODNAME, version = ModMorePistons.VERSION, acceptedMinecraftVersions = ModMorePistons.MINECRAFT_VERSION, dependencies = ModMorePistons.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModMorePistons {

	public final static String MODID = "MorePistons";
	public final static String MODNAME = "More Pistons";
	public final static String VERSION = "1.5.1 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.6.4";
	public final static String DEPENDENCIES = "required-after:GollumCoreLib";
	
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

	@ConfigProp(group = "Blocks Ids")  public static int blockSuperPistonBaseID               = 4085;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperStickyPistonBaseID         = 4084;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperDoublePistonBaseID         = 4059;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperDoubleStickyPistonBaseID   = 4058;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperTriplePistonBaseID         = 4057;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperTripleStickyPistonBaseID   = 4056;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperQuadPistonBaseID           = 4055;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperQuadStickyPistonBaseID     = 4054;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperQuintPistonBaseID          = 4053;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperQuintStickyPistonBaseID    = 4052;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperSextPistonBaseID           = 4051;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperSextStickyPistonBaseID     = 4050;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperSeptPistonBaseID           = 4049;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperSeptStickyPistonBaseID     = 4048;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperOctPistonBaseID            = 4047;
	@ConfigProp(group = "Blocks Ids")  public static int blockSuperOctStickyPistonBaseID      = 4046;
	
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
	public static Block blockSuperDoublePistonBase;
	public static Block blockSuperDoubleStickyPistonBase;
	public static Block blockSuperTriplePistonBase;
	public static Block blockSuperTripleStickyPistonBase;
	public static Block blockSuperQuadruplePistonBase;
	public static Block blockSuperQuadrupleStickyPistonBase;
	public static Block blockSuperQuintuplePistonBase;
	public static Block blockSuperQuintupleStickyPistonBase;
	public static Block blockSuperSextuplePistonBase;
	public static Block blockSuperSextupleStickyPistonBase;
	public static Block blockSuperSeptuplePistonBase;
	public static Block blockSuperSeptupleStickyPistonBase;
	public static Block blockSuperOctuplePistonBase;
	public static Block blockSuperOctupleStickyPistonBase;
	
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
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		// Creation du logger
		log = new Logger(event);
		
		// Charge la configuration
		ConfigLoader configLoader = new ConfigLoader(this.getClass(), event);
		configLoader.loadConfig();
		
		//Test la version du mod
		new VersionChecker(this);
	}
	
	/** 2 **/
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		
		this.morePistonsTabs = new GollumCreativeTabs("Pistons");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Pistons", "en_US", "Pistons");
		
		// Initialisation des blocks
		this.initBlocks ();
		
		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
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
		
		BlockFactory factory = new BlockFactory ();
		
		// Création des blocks
		this.blockPistonExtension = factory.create (new BlockMorePistonsExtension(this.blockPistonExtensionID), "MorePistonsExtension");
		this.blockPistonRod       = factory.create (new BlockMorePistonsRod      (this.blockPistonRodID)      , "MorePistonsRod");
		
		this.blockGravitationalPistonBase       = factory.create (new BlockMorePistonsGravitational(this.blockGravitationalPistonBaseID      , false), "GravitationalPistonBase"     , "Gravitational Piston");
		this.blockGravitationalStickyPistonBase = factory.create (new BlockMorePistonsGravitational(this.blockGravitationalStickyPistonBaseID, true) ,"GravitationalStickyPistonBase", "Gravitational Sticky Piston");
		
		this.blockDoublePistonBase           = factory.create (new BlockMorePistonsBase(this.blockDoublePistonBaseID      , false, "double_").setLength (2), "DoublePistonBase"         , "Double Piston");
		this.blockDoubleStickyPistonBase     = factory.create (new BlockMorePistonsBase(this.blockDoubleStickyPistonBaseID, true , "double_").setLength (2), "DoubleStickyPistonBase"   , "Double Sticky Piston");
		this.blockTriplePistonBase           = factory.create (new BlockMorePistonsBase(this.blockTriplePistonBaseID      , false, "triple_").setLength (3), "TriplePistonBase"         , "Triple Piston");
		this.blockTripleStickyPistonBase     = factory.create (new BlockMorePistonsBase(this.blockTripleStickyPistonBaseID, true , "triple_").setLength (3), "TripleStickyPistonBase"   , "Triple Sticky Piston");
		this.blockQuadruplePistonBase        = factory.create (new BlockMorePistonsBase(this.blockQuadPistonBaseID        , false, "quad_"  ).setLength (4), "QuadruplePistonBase"      , "Quadruple Piston");
		this.blockQuadrupleStickyPistonBase  = factory.create (new BlockMorePistonsBase(this.blockQuadStickyPistonBaseID  , true , "quad_"  ).setLength (4), "QuadrupleStickyPistonBase", "Quadruple Sticky Piston");
		this.blockQuintuplePistonBase        = factory.create (new BlockMorePistonsBase(this.blockQuintPistonBaseID       , false, "quint_" ).setLength (5), "QuintuplePistonBase"      , "Quintuple Piston");
		this.blockQuintupleStickyPistonBase  = factory.create (new BlockMorePistonsBase(this.blockQuintStickyPistonBaseID , true , "quint_" ).setLength (5), "QuintupleStickyPistonBase", "Quintuple Sticky Piston");
		this.blockSextuplePistonBase         = factory.create (new BlockMorePistonsBase(this.blockSextPistonBaseID        , false, "sext_"  ).setLength (6), "SextuplePistonBase"       , "Sextuple Piston");
		this.blockSextupleStickyPistonBase   = factory.create (new BlockMorePistonsBase(this.blockSextStickyPistonBaseID  , true , "sext_"  ).setLength (6), "SextupleStickyPistonBase" , "Sextuple Sticky Piston");
		this.blockSeptuplePistonBase         = factory.create (new BlockMorePistonsBase(this.blockSeptPistonBaseID        , false, "sept_"  ).setLength (7), "SeptuplePistonBase"       , "Sextuple Piston");
		this.blockSeptupleStickyPistonBase   = factory.create (new BlockMorePistonsBase(this.blockSeptStickyPistonBaseID  , true , "sept_"  ).setLength (7), "SeptupleStickyPistonBase" , "Sextuple Sticky Piston");
		this.blockOctuplePistonBase          = factory.create (new BlockMorePistonsBase(this.blockOctPistonBaseID         , false, "oct_"   ).setLength (8), "OctuplePistonBase"        , "Octuple Piston");
		this.blockOctupleStickyPistonBase    = factory.create (new BlockMorePistonsBase(this.blockOctStickyPistonBaseID   , true , "oct_"   ).setLength (8), "OctupleStickyPistonBase"  , "Octuple Sticky Piston");

		this.blockSuperPistonBase                = factory.create (new BlockMorePistonsSuper(this.blockSuperPistonBaseID            , false), "SuperPistonBase"      , "Super Piston");
		this.blockSuperStickyPistonBase          = factory.create (new BlockMorePistonsSuper(this.blockSuperStickyPistonBaseID      , true ), "SuperStickyPistonBase", "Super Sticky Piston");
		
		this.blockSuperDoublePistonBase          = factory.create (new BlockMorePistonsSuper(this.blockSuperDoublePistonBaseID      , false, "double_").setLength (2), "SuperDoublePistonBase"         , "Super Double Piston");
		this.blockSuperDoubleStickyPistonBase    = factory.create (new BlockMorePistonsSuper(this.blockSuperDoubleStickyPistonBaseID, true , "double_").setLength (2), "SuperDoubleStickyPistonBase"   , "Super Double Sticky Piston");
		this.blockSuperTriplePistonBase          = factory.create (new BlockMorePistonsSuper(this.blockSuperTriplePistonBaseID      , false, "triple_").setLength (3), "SuperTriplePistonBase"         , "Super Triple Piston");
		this.blockSuperTripleStickyPistonBase    = factory.create (new BlockMorePistonsSuper(this.blockSuperTripleStickyPistonBaseID, true , "triple_").setLength (3), "SuperTripleStickyPistonBase"   , "Super Triple Sticky Piston");
		this.blockSuperQuadruplePistonBase       = factory.create (new BlockMorePistonsSuper(this.blockSuperQuadPistonBaseID        , false, "quad_"  ).setLength (4), "SuperQuadruplePistonBase"      , "Super Quadruple Piston");
		this.blockSuperQuadrupleStickyPistonBase = factory.create (new BlockMorePistonsSuper(this.blockSuperQuadStickyPistonBaseID  , true , "quad_"  ).setLength (4), "SuperQuadrupleStickyPistonBase", "Super Quadruple Sticky Piston");
		this.blockSuperQuintuplePistonBase       = factory.create (new BlockMorePistonsSuper(this.blockSuperQuintPistonBaseID       , false, "quint_" ).setLength (5), "SuperQuintuplePistonBase"      , "Super Quintuple Piston");
		this.blockSuperQuintupleStickyPistonBase = factory.create (new BlockMorePistonsSuper(this.blockSuperQuintStickyPistonBaseID , true , "quint_" ).setLength (5), "SuperQuintupleStickyPistonBase", "Super Quintuple Sticky Piston");
		this.blockSuperSextuplePistonBase        = factory.create (new BlockMorePistonsSuper(this.blockSuperSextPistonBaseID        , false, "sext_"  ).setLength (6), "SuperSextuplePistonBase"       , "Super Sextuple Piston");
		this.blockSuperSextupleStickyPistonBase  = factory.create (new BlockMorePistonsSuper(this.blockSuperSextStickyPistonBaseID  , true , "sext_"  ).setLength (6), "SuperSextupleStickyPistonBase" , "Super Sextuple Sticky Piston");
		this.blockSuperSeptuplePistonBase        = factory.create (new BlockMorePistonsSuper(this.blockSuperSeptPistonBaseID        , false, "sept_"  ).setLength (7), "SuperSeptuplePistonBase"       , "Super Septuple Piston");
		this.blockSuperSeptupleStickyPistonBase  = factory.create (new BlockMorePistonsSuper(this.blockSuperSeptStickyPistonBaseID  , true , "sept_"  ).setLength (7), "SuperSeptupleStickyPistonBase" , "Super Septuple Sticky Piston");
		this.blockSuperOctuplePistonBase         = factory.create (new BlockMorePistonsSuper(this.blockSuperOctPistonBaseID         , false, "oct_"   ).setLength (8), "SuperOctuplePistonBase"        , "Super Octuple Piston");
		this.blockSuperOctupleStickyPistonBase   = factory.create (new BlockMorePistonsSuper(this.blockSuperOctStickyPistonBaseID   , true , "oct_"   ).setLength (8), "SuperOctupleStickyPistonBase"  , "Super Octuple Sticky Piston");
		
		ModMorePistons.blockRedStonePistonBase1       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase1ID      , false, "redstonepiston_").setMultiplicateur(1),"RedStonePistonBase"      , "Redstone Piston")       .setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStoneStickyPistonBase1 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase1ID, true , "redstonepiston_").setMultiplicateur(1),"RedStoneStickyPistonBase", "Redstone Sticky Piston").setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStonePistonBase2       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase2ID      , false, "redstonepiston_").setMultiplicateur(2),"RedStonePistonBase2");
		ModMorePistons.blockRedStoneStickyPistonBase2 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase2ID, true , "redstonepiston_").setMultiplicateur(2),"RedStoneStickyPistonBase2");
		ModMorePistons.blockRedStonePistonBase3       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase3ID      , false, "redstonepiston_").setMultiplicateur(3),"RedStonePistonBase3");
		ModMorePistons.blockRedStoneStickyPistonBase3 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase3ID, true , "redstonepiston_").setMultiplicateur(3),"RedStoneStickyPistonBase3");
		ModMorePistons.blockRedStonePistonBase4       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase4ID      , false, "redstonepiston_").setMultiplicateur(4),"RedStonePistonBase4");
		ModMorePistons.blockRedStoneStickyPistonBase4 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase4ID, true , "redstonepiston_").setMultiplicateur(4),"RedStoneStickyPistonBase4");
		ModMorePistons.blockRedStonePistonBase5       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase5ID      , false, "redstonepiston_").setMultiplicateur(5),"RedStonePistonBase5");
		ModMorePistons.blockRedStoneStickyPistonBase5 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase5ID, true , "redstonepiston_").setMultiplicateur(5),"RedStoneStickyPistonBase5");
		ModMorePistons.blockRedStonePistonBase6       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase6ID      , false, "redstonepiston_").setMultiplicateur(6),"RedStonePistonBase6");
		ModMorePistons.blockRedStoneStickyPistonBase6 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase6ID, true , "redstonepiston_").setMultiplicateur(6),"RedStoneStickyPistonBase6");
		ModMorePistons.blockRedStonePistonBase7       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase7ID      , false, "redstonepiston_").setMultiplicateur(7),"RedStonePistonBase7");
		ModMorePistons.blockRedStoneStickyPistonBase7 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase7ID, true , "redstonepiston_").setMultiplicateur(7),"RedStoneStickyPistonBase7");
		ModMorePistons.blockRedStonePistonBase8       = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStonePistonBase8ID      , false, "redstonepiston_").setMultiplicateur(8),"RedStonePistonBase8");
		ModMorePistons.blockRedStoneStickyPistonBase8 = factory.create (new BlockMorePistonsRedStone(ModMorePistons.blockRedStoneStickyPistonBase8ID, true , "redstonepiston_").setMultiplicateur(8),"RedStoneStickyPistonBase8");
		
	}
	
	/**
	 * Initialisation des TileEntities
	 */
	private void initTileEntities () {
		GameRegistry.registerTileEntity(TileEntityMorePistons.class , "MorePistons");
	}
	
	/**
	 * Ajout des recettes
	 */
	private void initRecipes () {
		
		GameRegistry.addRecipe(new ItemStack(this.blockDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), Block.pistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockDoublePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), Block.pistonStickyBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockDoublePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockTriplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockDoubleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockTriplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockQuadruplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockTripleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuadruplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockQuintuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuadrupleStickyPistonBase });
	
		GameRegistry.addRecipe(new ItemStack(this.blockSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuintuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSextuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuintupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSextuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSeptuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSextupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSeptuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockOctuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSeptupleStickyPistonBase });
		
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStonePistonBase1, 1), 
			new Object[] {
				"XXX",
				"DRE",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), this.blockOctuplePistonBase
			}
		);
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStonePistonBase1, 1), 
			new Object[] {
				"XXX",
				"ERD",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), this.blockOctuplePistonBase
			}
		);
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStoneStickyPistonBase1, 1), 
			new Object[] {
				"XXX",
				"ERD",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), this.blockOctupleStickyPistonBase
			}
		);
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStoneStickyPistonBase1, 1), 
			new Object[] {
				"XXX",
				"DRE",
				" Y ",
				Character.valueOf('X'), Block.planks,
				Character.valueOf('D'), Item.diamond,
				Character.valueOf('E'), Item.emerald,
				Character.valueOf('R'), Item.redstoneRepeater,
				Character.valueOf('Y'), this.blockOctupleStickyPistonBase
			}
		);
		GameRegistry.addRecipe(new ItemStack(this.blockRedStoneStickyPistonBase1, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockRedStonePistonBase1, Character.valueOf('Y'), Item.slimeBall });
		
		GameRegistry.addRecipe(new ItemStack(this.blockGravitationalPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), Block.pistonBase, Character.valueOf('Y'), Block.tnt });
		GameRegistry.addRecipe(new ItemStack(this.blockGravitationalStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockGravitationalPistonBase, Character.valueOf('Y'), Item.slimeBall });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperPistonBase, 1), new Object[] { "WWW", "CIC", "ORO", Character.valueOf('W'), Block.planks, Character.valueOf('C'), Block.cobblestone, Character.valueOf('I'), Item.ingotIron, Character.valueOf('O'), Block.obsidian, Character.valueOf('R'), Item.redstone });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperStickyPistonBase, 1), new Object[] { "S", "P", Character.valueOf('S'), Item.slimeBall, Character.valueOf('P'), this.blockSuperPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperPistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperDoublePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperDoublePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperTriplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperDoubleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperTriplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperQuadruplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperTripleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuadruplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperQuintuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuadrupleStickyPistonBase });
	
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuintuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperSextuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuintupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSextuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperSeptuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSextupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSeptuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperOctuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSeptupleStickyPistonBase });
		
	}
	
	
}
