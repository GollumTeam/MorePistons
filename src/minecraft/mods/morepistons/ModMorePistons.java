package mods.morepistons;

import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.helper.blocks.Block;
import mods.gollum.core.mod.GollumMod;
import mods.gollum.core.version.VersionChecker;
import mods.morepistons.common.CommonProxyMorePistons;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsGravitational;
import mods.morepistons.common.block.BlockMorePistonsRedStone;
import mods.morepistons.common.block.BlockMorePistonsRod;
import mods.morepistons.common.block.BlockMorePistonsSuper;
import mods.morepistons.common.config.ConfigMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModMorePistons.MODID, name = ModMorePistons.MODNAME, version = ModMorePistons.VERSION, acceptedMinecraftVersions = ModMorePistons.MINECRAFT_VERSION, dependencies = ModMorePistons.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModMorePistons extends GollumMod {

	public final static String MODID = "MorePistons";
	public final static String MODNAME = "More Pistons";
	public final static String VERSION = "1.5.1 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.6.4";
	public final static String DEPENDENCIES = "required-after:GollumCoreLib";
	
	@Instance("ModMorePistons")
	public static ModMorePistons instance;
	
	@SidedProxy(clientSide = "mods.morepistons.client.ClientProxyMorePistons", serverSide = "mods.morepistons.common.CommonProxyMorePistons")
	public static CommonProxyMorePistons proxy;
	
	/**
	 * La configuration
	 */
	public static ConfigMorePistons config;
	
	/**
	 * Onglet du mod
	 */
	public static GollumCreativeTabs morePistonsTabs;
	
	/////////////////////
	// Liste des blocs //
	/////////////////////
	public static BlockMorePistonsBase blockDoublePistonBase;
	public static BlockMorePistonsBase blockDoubleStickyPistonBase;
	public static BlockMorePistonsBase blockTriplePistonBase;
	public static BlockMorePistonsBase blockTripleStickyPistonBase;
	public static BlockMorePistonsBase blockQuadruplePistonBase;
	public static BlockMorePistonsBase blockQuadrupleStickyPistonBase;
	public static BlockMorePistonsBase blockQuintuplePistonBase;
	public static BlockMorePistonsBase blockQuintupleStickyPistonBase;
	public static BlockMorePistonsBase blockSextuplePistonBase;
	public static BlockMorePistonsBase blockSextupleStickyPistonBase;
	public static BlockMorePistonsBase blockSeptuplePistonBase;
	public static BlockMorePistonsBase blockSeptupleStickyPistonBase;
	public static BlockMorePistonsBase blockOctuplePistonBase;
	public static BlockMorePistonsBase blockOctupleStickyPistonBase;
	
	public static BlockMorePistonsGravitational blockGravitationalPistonBase;
	public static BlockMorePistonsGravitational blockGravitationalStickyPistonBase;

	public static BlockMorePistonsSuper blockSuperPistonBase;
	public static BlockMorePistonsSuper blockSuperStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperDoublePistonBase;
	public static BlockMorePistonsSuper blockSuperDoubleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperTriplePistonBase;
	public static BlockMorePistonsSuper blockSuperTripleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperQuadruplePistonBase;
	public static BlockMorePistonsSuper blockSuperQuadrupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperQuintuplePistonBase;
	public static BlockMorePistonsSuper blockSuperQuintupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperSextuplePistonBase;
	public static BlockMorePistonsSuper blockSuperSextupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperSeptuplePistonBase;
	public static BlockMorePistonsSuper blockSuperSeptupleStickyPistonBase;
	public static BlockMorePistonsSuper blockSuperOctuplePistonBase;
	public static BlockMorePistonsSuper blockSuperOctupleStickyPistonBase;
	
	public static BlockMorePistonsRedStone blockRedStonePistonBase1;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase1;
	public static BlockMorePistonsRedStone blockRedStonePistonBase2;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase2;
	public static BlockMorePistonsRedStone blockRedStonePistonBase3;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase3;
	public static BlockMorePistonsRedStone blockRedStonePistonBase4;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase4;
	public static BlockMorePistonsRedStone blockRedStonePistonBase5;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase5;
	public static BlockMorePistonsRedStone blockRedStonePistonBase6;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase6;
	public static BlockMorePistonsRedStone blockRedStonePistonBase7;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase7;
	public static BlockMorePistonsRedStone blockRedStonePistonBase8;
	public static BlockMorePistonsRedStone blockRedStoneStickyPistonBase8;
	
	public static BlockMorePistonsExtension blockPistonExtension;
	public static BlockMorePistonsRod blockPistonRod;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		
		// Charge la configuration
		this.config = new ConfigMorePistons();
		
		//Test la version du mod
		new VersionChecker();
	}
	
	/** 2 **/
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		// Initialisation du proxy
		proxy.registerRenderers();
		
		this.morePistonsTabs = new GollumCreativeTabs("Pistons");
		
		// Initialisation des blocks
		this.initBlocks ();
		
		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
		// Place le piston normal dans le creative tab
		Block.pistonBase.setCreativeTab(morePistonsTabs);
		Block.pistonStickyBase.setCreativeTab(morePistonsTabs);
		
		// Set de l'icon du tab creative
		this.morePistonsTabs.setIcon(Block.pistonBase);
	}
	
	/**
	 * Initialisation des blocks
	 */
	public void initBlocks() {
		
		// Création des blocks
		this.blockPistonExtension = new BlockMorePistonsExtension(this.config.blockPistonExtensionID, "MorePistonsExtension");
		this.blockPistonRod       = new BlockMorePistonsRod      (this.config.blockPistonRodID      , "MorePistonsRod");
		
		this.blockGravitationalPistonBase       = new BlockMorePistonsGravitational(this.config.blockGravitationalPistonBaseID      , "GravitationalPistonBase"      , false);
		this.blockGravitationalStickyPistonBase = new BlockMorePistonsGravitational(this.config.blockGravitationalStickyPistonBaseID, "GravitationalStickyPistonBase", true);
		
		this.blockDoublePistonBase           = new BlockMorePistonsBase(this.config.blockDoublePistonBaseID      , "DoublePistonBase"         , false).setLength (2);
		this.blockDoubleStickyPistonBase     = new BlockMorePistonsBase(this.config.blockDoubleStickyPistonBaseID, "DoubleStickyPistonBase"   , true ).setLength (2);
		this.blockTriplePistonBase           = new BlockMorePistonsBase(this.config.blockTriplePistonBaseID      , "TriplePistonBase"         , false).setLength (3);
		this.blockTripleStickyPistonBase     = new BlockMorePistonsBase(this.config.blockTripleStickyPistonBaseID, "TripleStickyPistonBase"   , true ).setLength (3);
		this.blockQuadruplePistonBase        = new BlockMorePistonsBase(this.config.blockQuadPistonBaseID        , "QuadruplePistonBase"      , false).setLength (4);
		this.blockQuadrupleStickyPistonBase  = new BlockMorePistonsBase(this.config.blockQuadStickyPistonBaseID  , "QuadrupleStickyPistonBase", true ).setLength (4);
		this.blockQuintuplePistonBase        = new BlockMorePistonsBase(this.config.blockQuintPistonBaseID       , "QuintuplePistonBase"      , false).setLength (5);
		this.blockQuintupleStickyPistonBase  = new BlockMorePistonsBase(this.config.blockQuintStickyPistonBaseID , "QuintupleStickyPistonBase", true ).setLength (5);
		this.blockSextuplePistonBase         = new BlockMorePistonsBase(this.config.blockSextPistonBaseID        , "SextuplePistonBase"       , false).setLength (6);
		this.blockSextupleStickyPistonBase   = new BlockMorePistonsBase(this.config.blockSextStickyPistonBaseID  , "SextupleStickyPistonBase" , true ).setLength (6);
		this.blockSeptuplePistonBase         = new BlockMorePistonsBase(this.config.blockSeptPistonBaseID        , "SeptuplePistonBase"       , false).setLength (7);
		this.blockSeptupleStickyPistonBase   = new BlockMorePistonsBase(this.config.blockSeptStickyPistonBaseID  , "SeptupleStickyPistonBase" , true ).setLength (7);
		this.blockOctuplePistonBase          = new BlockMorePistonsBase(this.config.blockOctPistonBaseID         , "OctuplePistonBase"        , false).setLength (8);
		this.blockOctupleStickyPistonBase    = new BlockMorePistonsBase(this.config.blockOctStickyPistonBaseID   , "OctupleStickyPistonBase"  , true ).setLength (8);

		this.blockSuperPistonBase                = new BlockMorePistonsSuper(this.config.blockSuperPistonBaseID            , "SuperPistonBase"      , false);
		this.blockSuperStickyPistonBase          = new BlockMorePistonsSuper(this.config.blockSuperStickyPistonBaseID      , "SuperStickyPistonBase", true );
		
		this.blockSuperDoublePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperDoublePistonBaseID      , "SuperDoublePistonBase"         , false).setLength (2);
		this.blockSuperDoubleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperDoubleStickyPistonBaseID, "SuperDoubleStickyPistonBase"   , true ).setLength (2);
		this.blockSuperTriplePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperTriplePistonBaseID      , "SuperTriplePistonBase"         , false).setLength (3);
		this.blockSuperTripleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperTripleStickyPistonBaseID, "SuperTripleStickyPistonBase"   , true ).setLength (3);
		this.blockSuperQuadruplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperQuadPistonBaseID        , "SuperQuadruplePistonBase"      , false).setLength (4);
		this.blockSuperQuadrupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperQuadStickyPistonBaseID  , "SuperQuadrupleStickyPistonBase", true ).setLength (4);
		this.blockSuperQuintuplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperQuintPistonBaseID       , "SuperQuintuplePistonBase"      , false).setLength (5);
		this.blockSuperQuintupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperQuintStickyPistonBaseID , "SuperQuintupleStickyPistonBase", true ).setLength (5);
		this.blockSuperSextuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperSextPistonBaseID        , "SuperSextuplePistonBase"       , false).setLength (6);
		this.blockSuperSextupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperSextStickyPistonBaseID  , "SuperSextupleStickyPistonBase" , true ).setLength (6);
		this.blockSuperSeptuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperSeptPistonBaseID        , "SuperSeptuplePistonBase"       , false).setLength (7);
		this.blockSuperSeptupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperSeptStickyPistonBaseID  , "SuperSeptupleStickyPistonBase" , true ).setLength (7);
		this.blockSuperOctuplePistonBase         = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperOctPistonBaseID         , "SuperOctuplePistonBase"        , false).setLength (8);
		this.blockSuperOctupleStickyPistonBase   = (BlockMorePistonsSuper) new BlockMorePistonsSuper(this.config.blockSuperOctStickyPistonBaseID   , "SuperOctupleStickyPistonBase"  , true ).setLength (8);
		
		ModMorePistons.blockRedStonePistonBase1       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase1ID       ,"RedStonePistonBase1"      , false).setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStoneStickyPistonBase1 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase1ID ,"RedStoneStickyPistonBase1", true ).setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStonePistonBase2       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase2ID       ,"RedStonePistonBase2"      , false).setMultiplicateur(2);
		ModMorePistons.blockRedStoneStickyPistonBase2 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase2ID ,"RedStoneStickyPistonBase2", true ).setMultiplicateur(2);
		ModMorePistons.blockRedStonePistonBase3       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase3ID       ,"RedStonePistonBase3"      , false).setMultiplicateur(3);
		ModMorePistons.blockRedStoneStickyPistonBase3 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase3ID ,"RedStoneStickyPistonBase3", true ).setMultiplicateur(3);
		ModMorePistons.blockRedStonePistonBase4       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase4ID       ,"RedStonePistonBase4"      , false).setMultiplicateur(4);
		ModMorePistons.blockRedStoneStickyPistonBase4 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase4ID ,"RedStoneStickyPistonBase4", true ).setMultiplicateur(4);
		ModMorePistons.blockRedStonePistonBase5       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase5ID       ,"RedStonePistonBase5"      , false).setMultiplicateur(5);
		ModMorePistons.blockRedStoneStickyPistonBase5 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase5ID ,"RedStoneStickyPistonBase5", true ).setMultiplicateur(5);
		ModMorePistons.blockRedStonePistonBase6       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase6ID       ,"RedStonePistonBase6"      , false).setMultiplicateur(6);
		ModMorePistons.blockRedStoneStickyPistonBase6 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase6ID ,"RedStoneStickyPistonBase6", true ).setMultiplicateur(6);
		ModMorePistons.blockRedStonePistonBase7       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase7ID       ,"RedStonePistonBase7"      , false).setMultiplicateur(7);
		ModMorePistons.blockRedStoneStickyPistonBase7 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase7ID ,"RedStoneStickyPistonBase7", true ).setMultiplicateur(7);
		ModMorePistons.blockRedStonePistonBase8       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStonePistonBase8ID       ,"RedStonePistonBase8"      , false).setMultiplicateur(8);
		ModMorePistons.blockRedStoneStickyPistonBase8 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone(this.config.blockRedStoneStickyPistonBase8ID ,"RedStoneStickyPistonBase8", true ).setMultiplicateur(8);
		
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
		
//		GameRegistry.addRecipe(new ItemStack(this.blockDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), Block.pistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockDoublePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), Block.pistonStickyBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockDoublePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockTriplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockDoubleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockTriplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockQuadruplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockTripleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuadruplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockQuintuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuadrupleStickyPistonBase });
//	
//		GameRegistry.addRecipe(new ItemStack(this.blockSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuintuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSextuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockQuintupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSextuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSeptuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSextupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSeptuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockOctuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSeptupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(
//			new ItemStack(this.blockRedStonePistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"DRE",
//				" Y ",
//				Character.valueOf('X'), Block.planks,
//				Character.valueOf('D'), Item.diamond,
//				Character.valueOf('E'), Item.emerald,
//				Character.valueOf('R'), Item.redstoneRepeater,
//				Character.valueOf('Y'), this.blockOctuplePistonBase
//			}
//		);
//		GameRegistry.addRecipe(
//			new ItemStack(this.blockRedStonePistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"ERD",
//				" Y ",
//				Character.valueOf('X'), Block.planks,
//				Character.valueOf('D'), Item.diamond,
//				Character.valueOf('E'), Item.emerald,
//				Character.valueOf('R'), Item.redstoneRepeater,
//				Character.valueOf('Y'), this.blockOctuplePistonBase
//			}
//		);
//		GameRegistry.addRecipe(
//			new ItemStack(this.blockRedStoneStickyPistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"ERD",
//				" Y ",
//				Character.valueOf('X'), Block.planks,
//				Character.valueOf('D'), Item.diamond,
//				Character.valueOf('E'), Item.emerald,
//				Character.valueOf('R'), Item.redstoneRepeater,
//				Character.valueOf('Y'), this.blockOctupleStickyPistonBase
//			}
//		);
//		GameRegistry.addRecipe(
//			new ItemStack(this.blockRedStoneStickyPistonBase1, 1), 
//			new Object[] {
//				"XXX",
//				"DRE",
//				" Y ",
//				Character.valueOf('X'), Block.planks,
//				Character.valueOf('D'), Item.diamond,
//				Character.valueOf('E'), Item.emerald,
//				Character.valueOf('R'), Item.redstoneRepeater,
//				Character.valueOf('Y'), this.blockOctupleStickyPistonBase
//			}
//		);
//		GameRegistry.addRecipe(new ItemStack(this.blockRedStoneStickyPistonBase1, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockRedStonePistonBase1, Character.valueOf('Y'), Item.slimeBall });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockGravitationalPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), Block.pistonBase, Character.valueOf('Y'), Block.tnt });
//		GameRegistry.addRecipe(new ItemStack(this.blockGravitationalStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockGravitationalPistonBase, Character.valueOf('Y'), Item.slimeBall });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperPistonBase, 1), new Object[] { "WWW", "CIC", "ORO", Character.valueOf('W'), Block.planks, Character.valueOf('C'), Block.cobblestone, Character.valueOf('I'), Item.ingotIron, Character.valueOf('O'), Block.obsidian, Character.valueOf('R'), Item.redstone });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperStickyPistonBase, 1), new Object[] { "S", "P", Character.valueOf('S'), Item.slimeBall, Character.valueOf('P'), this.blockSuperPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperPistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperDoublePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperDoublePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperTriplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperDoubleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperTriplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperQuadruplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperTripleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuadruplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperQuintuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuadrupleStickyPistonBase });
//	
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuintuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperSextuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperQuintupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSextuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperSeptuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSextupleStickyPistonBase });
//		
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSeptuplePistonBase });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperOctuplePistonBase, Character.valueOf('Y'), Item.slimeBall });
//		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Block.planks, Character.valueOf('Y'), Item.ingotIron, Character.valueOf('Z'), this.blockSuperSeptupleStickyPistonBase });
		
	}
	
	
}
