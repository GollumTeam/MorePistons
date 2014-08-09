package mods.morepistons;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = ModMorePistons.MODID, name = ModMorePistons.MODNAME, version = ModMorePistons.VERSION, acceptedMinecraftVersions = ModMorePistons.MINECRAFT_VERSION, dependencies = ModMorePistons.DEPENDENCIES)
public class ModMorePistons {

	public final static String MODID = "MorePistons";
	public final static String MODNAME = "More Pistons";
	public final static String VERSION = "1.5.1 [Build Smeagol]";
	public final static String MINECRAFT_VERSION = "1.7.10";
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
	
	/////////////////////
	// Liste des blocs //
	/////////////////////
	
	public static Block blockPistonExtension;
	public static Block blockPistonRod;
	
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
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		// Creation du logger
		log = new Logger(this);
		
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
		
		// Initialisation des blocks
		this.initBlocks ();
		
		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
		// Initialisation du proxy
		proxy.registerRenderers();
		
		// Place le piston normal dans le creative tab
		Blocks.piston.setCreativeTab(morePistonsTabs);
		Blocks.sticky_piston.setCreativeTab(morePistonsTabs);
		
		// Set de l'icon du tab creative
		this.morePistonsTabs.setIcon(Blocks.piston);
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
		this.blockPistonExtension = factory.create (new BlockMorePistonsExtension(), "MorePistonsExtension");
		this.blockPistonRod       = factory.create (new BlockMorePistonsRod      ()      , "MorePistonsRod");
		
		this.blockGravitationalPistonBase       = factory.create (new BlockMorePistonsGravitational(false), "GravitationalPistonBase");
		this.blockGravitationalStickyPistonBase = factory.create (new BlockMorePistonsGravitational(true) ,"GravitationalStickyPistonBase");
		
		this.blockDoublePistonBase           = factory.create (new BlockMorePistonsBase(false, "double_").setLength (2), "DoublePistonBase");
		this.blockDoubleStickyPistonBase     = factory.create (new BlockMorePistonsBase(true , "double_").setLength (2), "DoubleStickyPistonBase");
		this.blockTriplePistonBase           = factory.create (new BlockMorePistonsBase(false, "triple_").setLength (3), "TriplePistonBase");
		this.blockTripleStickyPistonBase     = factory.create (new BlockMorePistonsBase(true , "triple_").setLength (3), "TripleStickyPistonBase");
		this.blockQuadruplePistonBase        = factory.create (new BlockMorePistonsBase(false, "quad_"  ).setLength (4), "QuadruplePistonBase");
		this.blockQuadrupleStickyPistonBase  = factory.create (new BlockMorePistonsBase(true , "quad_"  ).setLength (4), "QuadrupleStickyPistonBase");
		this.blockQuintuplePistonBase        = factory.create (new BlockMorePistonsBase(false, "quint_" ).setLength (5), "QuintuplePistonBase");
		this.blockQuintupleStickyPistonBase  = factory.create (new BlockMorePistonsBase(true , "quint_" ).setLength (5), "QuintupleStickyPistonBase");
		this.blockSextuplePistonBase         = factory.create (new BlockMorePistonsBase(false, "sext_"  ).setLength (6), "SextuplePistonBase");
		this.blockSextupleStickyPistonBase   = factory.create (new BlockMorePistonsBase(true , "sext_"  ).setLength (6), "SextupleStickyPistonBase");
		this.blockSeptuplePistonBase         = factory.create (new BlockMorePistonsBase(false, "sept_"  ).setLength (7), "SeptuplePistonBase");
		this.blockSeptupleStickyPistonBase   = factory.create (new BlockMorePistonsBase(true , "sept_"  ).setLength (7), "SeptupleStickyPistonBase");
		this.blockOctuplePistonBase          = factory.create (new BlockMorePistonsBase(false, "oct_"   ).setLength (8), "OctuplePistonBase");
		this.blockOctupleStickyPistonBase    = factory.create (new BlockMorePistonsBase(true , "oct_"   ).setLength (8), "OctupleStickyPistonBase");

		this.blockSuperPistonBase                = factory.create (new BlockMorePistonsSuper(false), "SuperPistonBase");
		this.blockSuperStickyPistonBase          = factory.create (new BlockMorePistonsSuper(true ), "SuperStickyPistonBase");
		
		this.blockSuperDoublePistonBase          = factory.create (new BlockMorePistonsSuper(false, "double_").setLength (2), "SuperDoublePistonBase");
		this.blockSuperDoubleStickyPistonBase    = factory.create (new BlockMorePistonsSuper(true , "double_").setLength (2), "SuperDoubleStickyPistonBase");
		this.blockSuperTriplePistonBase          = factory.create (new BlockMorePistonsSuper(false, "triple_").setLength (3), "SuperTriplePistonBase");
		this.blockSuperTripleStickyPistonBase    = factory.create (new BlockMorePistonsSuper(true , "triple_").setLength (3), "SuperTripleStickyPistonBase");
		this.blockSuperQuadruplePistonBase       = factory.create (new BlockMorePistonsSuper(false, "quad_"  ).setLength (4), "SuperQuadruplePistonBase");
		this.blockSuperQuadrupleStickyPistonBase = factory.create (new BlockMorePistonsSuper(true , "quad_"  ).setLength (4), "SuperQuadrupleStickyPistonBase");
		this.blockSuperQuintuplePistonBase       = factory.create (new BlockMorePistonsSuper(false, "quint_" ).setLength (5), "SuperQuintuplePistonBase");
		this.blockSuperQuintupleStickyPistonBase = factory.create (new BlockMorePistonsSuper(true , "quint_" ).setLength (5), "SuperQuintupleStickyPistonBase");
		this.blockSuperSextuplePistonBase        = factory.create (new BlockMorePistonsSuper(false, "sext_"  ).setLength (6), "SuperSextuplePistonBase");
		this.blockSuperSextupleStickyPistonBase  = factory.create (new BlockMorePistonsSuper(true , "sext_"  ).setLength (6), "SuperSextupleStickyPistonBase");
		this.blockSuperSeptuplePistonBase        = factory.create (new BlockMorePistonsSuper(false, "sept_"  ).setLength (7), "SuperSeptuplePistonBase");
		this.blockSuperSeptupleStickyPistonBase  = factory.create (new BlockMorePistonsSuper(true , "sept_"  ).setLength (7), "SuperSeptupleStickyPistonBase");
		this.blockSuperOctuplePistonBase         = factory.create (new BlockMorePistonsSuper(false, "oct_"   ).setLength (8), "SuperOctuplePistonBase");
		this.blockSuperOctupleStickyPistonBase   = factory.create (new BlockMorePistonsSuper(true , "oct_"   ).setLength (8), "SuperOctupleStickyPistonBase");
		
		ModMorePistons.blockRedStonePistonBase1       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(1),"RedStonePistonBase")       .setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStoneStickyPistonBase1 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(1),"RedStoneStickyPistonBase").setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStonePistonBase2       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(2),"RedStonePistonBase2");
		ModMorePistons.blockRedStoneStickyPistonBase2 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(2),"RedStoneStickyPistonBase2");
		ModMorePistons.blockRedStonePistonBase3       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(3),"RedStonePistonBase3");
		ModMorePistons.blockRedStoneStickyPistonBase3 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(3),"RedStoneStickyPistonBase3");
		ModMorePistons.blockRedStonePistonBase4       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(4),"RedStonePistonBase4");
		ModMorePistons.blockRedStoneStickyPistonBase4 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(4),"RedStoneStickyPistonBase4");
		ModMorePistons.blockRedStonePistonBase5       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(5),"RedStonePistonBase5");
		ModMorePistons.blockRedStoneStickyPistonBase5 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(5),"RedStoneStickyPistonBase5");
		ModMorePistons.blockRedStonePistonBase6       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(6),"RedStonePistonBase6");
		ModMorePistons.blockRedStoneStickyPistonBase6 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(6),"RedStoneStickyPistonBase6");
		ModMorePistons.blockRedStonePistonBase7       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(7),"RedStonePistonBase7");
		ModMorePistons.blockRedStoneStickyPistonBase7 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(7),"RedStoneStickyPistonBase7");
		ModMorePistons.blockRedStonePistonBase8       = factory.create (new BlockMorePistonsRedStone(false, "redstonepiston_").setMultiplicateur(8),"RedStonePistonBase8");
		ModMorePistons.blockRedStoneStickyPistonBase8 = factory.create (new BlockMorePistonsRedStone(true , "redstonepiston_").setMultiplicateur(8),"RedStoneStickyPistonBase8");
		
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
		
		GameRegistry.addRecipe(new ItemStack(this.blockDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), Blocks.piston });
		GameRegistry.addRecipe(new ItemStack(this.blockDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockDoublePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), Blocks.sticky_piston });
		
		GameRegistry.addRecipe(new ItemStack(this.blockTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockDoublePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockTriplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockDoubleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockTriplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockQuadruplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockTripleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockQuadruplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockQuintuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockQuadrupleStickyPistonBase });
	
		GameRegistry.addRecipe(new ItemStack(this.blockSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockQuintuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSextuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockQuintupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSextuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSeptuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSextupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSeptuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockOctuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSeptupleStickyPistonBase });
		
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStonePistonBase1, 1), 
			new Object[] {
				"XXX",
				"DRE",
				" Y ",
				Character.valueOf('X'), Blocks.planks,
				Character.valueOf('D'), Items.diamond,
				Character.valueOf('E'), Items.emerald,
				Character.valueOf('R'), Items.repeater,
				Character.valueOf('Y'), this.blockOctuplePistonBase
			}
		);
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStonePistonBase1, 1), 
			new Object[] {
				"XXX",
				"ERD",
				" Y ",
				Character.valueOf('X'), Blocks.planks,
				Character.valueOf('D'), Items.diamond,
				Character.valueOf('E'), Items.emerald,
				Character.valueOf('R'), Items.repeater,
				Character.valueOf('Y'), this.blockOctuplePistonBase
			}
		);
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStoneStickyPistonBase1, 1), 
			new Object[] {
				"XXX",
				"ERD",
				" Y ",
				Character.valueOf('X'), Blocks.planks,
				Character.valueOf('D'), Items.diamond,
				Character.valueOf('E'), Items.emerald,
				Character.valueOf('R'), Items.repeater,
				Character.valueOf('Y'), this.blockOctupleStickyPistonBase
			}
		);
		GameRegistry.addRecipe(
			new ItemStack(this.blockRedStoneStickyPistonBase1, 1), 
			new Object[] {
				"XXX",
				"DRE",
				" Y ",
				Character.valueOf('X'), Blocks.planks,
				Character.valueOf('D'), Items.diamond,
				Character.valueOf('E'), Items.emerald,
				Character.valueOf('R'), Items.repeater,
				Character.valueOf('Y'), this.blockOctupleStickyPistonBase
			}
		);
		GameRegistry.addRecipe(new ItemStack(this.blockRedStoneStickyPistonBase1, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockRedStonePistonBase1, Character.valueOf('Y'), Items.slime_ball });
		
		GameRegistry.addRecipe(new ItemStack(this.blockGravitationalPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), Blocks.piston, Character.valueOf('Y'), Blocks.tnt });
		GameRegistry.addRecipe(new ItemStack(this.blockGravitationalStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockGravitationalPistonBase, Character.valueOf('Y'), Items.slime_ball });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperPistonBase, 1), new Object[] { "WWW", "CIC", "ORO", Character.valueOf('W'), Blocks.planks, Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('I'), Items.iron_ingot, Character.valueOf('O'), Blocks.obsidian, Character.valueOf('R'), Items.redstone });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperStickyPistonBase, 1), new Object[] { "S", "P", Character.valueOf('S'), Items.slime_ball, Character.valueOf('P'), this.blockSuperPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoublePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperPistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoubleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperDoublePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperDoubleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperTriplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperDoublePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperTripleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperTriplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperTripleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperDoubleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadruplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperTriplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperQuadruplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuadrupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperTripleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperQuadruplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperQuintuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperQuintupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperQuadrupleStickyPistonBase });
	
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperQuintuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperSextuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSextupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperQuintupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperSextuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperSeptuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperSeptupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperSextupleStickyPistonBase });
		
		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctuplePistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperSeptuplePistonBase });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctupleStickyPistonBase, 1), new Object[] { "Y", "X", Character.valueOf('X'), this.blockSuperOctuplePistonBase, Character.valueOf('Y'), Items.slime_ball });
		GameRegistry.addRecipe(new ItemStack(this.blockSuperOctupleStickyPistonBase, 1), new Object[] { "XXX", " Y ", " Z ", Character.valueOf('X'), Blocks.planks, Character.valueOf('Y'), Items.iron_ingot, Character.valueOf('Z'), this.blockSuperSeptupleStickyPistonBase });
		
	}
	
	
}
