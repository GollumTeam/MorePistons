package mods.morepistons.common;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import mods.gollum.core.config.ConfigLoader;
import mods.gollum.core.config.ConfigProp;
import mods.gollum.core.creativetab.GollumCreativeTabs;
import mods.gollum.core.log.Logger;
import mods.gollum.core.version.VersionChecker;
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

@Mod(modid = ModMorePistons.MODID, name = ModMorePistons.MODNAME, version = "1.5.0 [Build Smeagol]", acceptedMinecraftVersions = "1.6.4")
public class ModMorePistons {

	public static final String MODID = "MorePistons";
	public static final String MODNAME = "More Pistons";
	
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
		
		// Création des blocks
		this.blockPistonExtension = new BlockMorePistonsExtension().setBlockName("MorePistonsExtension");
		this.blockPistonRod       = new BlockMorePistonsRod      ().setBlockName("MorePistonsRod");
		
		this.blockGravitationalPistonBase       = new BlockMorePistonsGravitational(false).setBlockName("GravitationalPistonBase");
		this.blockGravitationalStickyPistonBase = new BlockMorePistonsGravitational(true) .setBlockName("GravitationalStickyPistonBase");
		
		this.blockDoublePistonBase           = (new BlockMorePistonsBase(false, "double_")).setLength (2).setBlockName("DoublePistonBase");
		this.blockDoubleStickyPistonBase     = (new BlockMorePistonsBase(true , "double_")).setLength (2).setBlockName("DoubleStickyPistonBase");
		this.blockTriplePistonBase           = (new BlockMorePistonsBase(false, "triple_")).setLength (3).setBlockName("TriplePistonBase");
		this.blockTripleStickyPistonBase     = (new BlockMorePistonsBase(true , "triple_")).setLength (3).setBlockName("TripleStickyPistonBase");
		this.blockQuadruplePistonBase        = (new BlockMorePistonsBase(false, "quad_"  )).setLength (4).setBlockName("QuadruplePistonBase");
		this.blockQuadrupleStickyPistonBase  = (new BlockMorePistonsBase(true , "quad_"  )).setLength (4).setBlockName("QuadrupleStickyPistonBase");
		this.blockQuintuplePistonBase        = (new BlockMorePistonsBase(false, "quint_" )).setLength (5).setBlockName("QuintuplePistonBase");
		this.blockQuintupleStickyPistonBase  = (new BlockMorePistonsBase(true , "quint_" )).setLength (5).setBlockName("QuintupleStickyPistonBase");
		this.blockSextuplePistonBase         = (new BlockMorePistonsBase(false, "sext_"  )).setLength (6).setBlockName("SextuplePistonBase");
		this.blockSextupleStickyPistonBase   = (new BlockMorePistonsBase(true , "sext_"  )).setLength (6).setBlockName("SextupleStickyPistonBase");
		this.blockSeptuplePistonBase         = (new BlockMorePistonsBase(false, "sept_"  )).setLength (7).setBlockName("SeptuplePistonBase");
		this.blockSeptupleStickyPistonBase   = (new BlockMorePistonsBase(true , "sept_"  )).setLength (7).setBlockName("SeptupleStickyPistonBase");
		this.blockOctuplePistonBase          = (new BlockMorePistonsBase(false, "oct_"   )).setLength (8).setBlockName("OctuplePistonBase");
		this.blockOctupleStickyPistonBase    = (new BlockMorePistonsBase(true , "oct_"   )).setLength (8).setBlockName("OctupleStickyPistonBase");

		this.blockSuperPistonBase                = (new BlockMorePistonsSuper(false)).setBlockName("SuperPistonBase");
		this.blockSuperStickyPistonBase          = (new BlockMorePistonsSuper(true) ).setBlockName("SuperStickyPistonBase");
		this.blockSuperDoublePistonBase          = (new BlockMorePistonsSuper(false, "double_")).setLength (2).setBlockName("SuperDoublePistonBase");
		this.blockSuperDoubleStickyPistonBase    = (new BlockMorePistonsSuper(true , "double_")).setLength (2).setBlockName("SuperDoubleStickyPistonBase");
		this.blockSuperTriplePistonBase          = (new BlockMorePistonsSuper(false, "triple_")).setLength (3).setBlockName("SuperTriplePistonBase");
		this.blockSuperTripleStickyPistonBase    = (new BlockMorePistonsSuper(true , "triple_")).setLength (3).setBlockName("SuperTripleStickyPistonBase");
		this.blockSuperQuadruplePistonBase       = (new BlockMorePistonsSuper(false, "quad_"  )).setLength (4).setBlockName("SuperQuadruplePistonBase");
		this.blockSuperQuadrupleStickyPistonBase = (new BlockMorePistonsSuper(true , "quad_"  )).setLength (4).setBlockName("SuperQuadrupleStickyPistonBase");
		this.blockSuperQuintuplePistonBase       = (new BlockMorePistonsSuper(false, "quint_" )).setLength (5).setBlockName("SuperQuintuplePistonBase");
		this.blockSuperQuintupleStickyPistonBase = (new BlockMorePistonsSuper(true , "quint_" )).setLength (5).setBlockName("SuperQuintupleStickyPistonBase");
		this.blockSuperSextuplePistonBase        = (new BlockMorePistonsSuper(false, "sext_"  )).setLength (6).setBlockName("SuperSextuplePistonBase");
		this.blockSuperSextupleStickyPistonBase  = (new BlockMorePistonsSuper(true , "sext_"  )).setLength (6).setBlockName("SuperSextupleStickyPistonBase");
		this.blockSuperSeptuplePistonBase        = (new BlockMorePistonsSuper(false, "sept_"  )).setLength (7).setBlockName("SuperSeptuplePistonBase");
		this.blockSuperSeptupleStickyPistonBase  = (new BlockMorePistonsSuper(true , "sept_"  )).setLength (7).setBlockName("SuperSeptupleStickyPistonBase");
		this.blockSuperOctuplePistonBase         = (new BlockMorePistonsSuper(false, "oct_"   )).setLength (8).setBlockName("SuperOctuplePistonBase");
		this.blockSuperOctupleStickyPistonBase   = (new BlockMorePistonsSuper(true , "oct_"   )).setLength (8).setBlockName("SuperOctupleStickyPistonBase");
		
		ModMorePistons.blockRedStonePistonBase1       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(1).setBlockName("RedStonePistonBase")      .setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStoneStickyPistonBase1 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(1).setBlockName("RedStoneStickyPistonBase").setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStonePistonBase2       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(2).setBlockName("RedStonePistonBase2");
		ModMorePistons.blockRedStoneStickyPistonBase2 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(2).setBlockName("RedStoneStickyPistonBase2");
		ModMorePistons.blockRedStonePistonBase3       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(3).setBlockName("RedStonePistonBase3");
		ModMorePistons.blockRedStoneStickyPistonBase3 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(3).setBlockName("RedStoneStickyPistonBase3");
		ModMorePistons.blockRedStonePistonBase4       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(4).setBlockName("RedStonePistonBase4");
		ModMorePistons.blockRedStoneStickyPistonBase4 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(4).setBlockName("RedStoneStickyPistonBase4");
		ModMorePistons.blockRedStonePistonBase5       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(5).setBlockName("RedStonePistonBase5");
		ModMorePistons.blockRedStoneStickyPistonBase5 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(5).setBlockName("RedStoneStickyPistonBase5");
		ModMorePistons.blockRedStonePistonBase6       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(6).setBlockName("RedStonePistonBase6");
		ModMorePistons.blockRedStoneStickyPistonBase6 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(6).setBlockName("RedStoneStickyPistonBase6");
		ModMorePistons.blockRedStonePistonBase7       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(7).setBlockName("RedStonePistonBase7");
		ModMorePistons.blockRedStoneStickyPistonBase7 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(7).setBlockName("RedStoneStickyPistonBase7");
		ModMorePistons.blockRedStonePistonBase8       = (new BlockMorePistonsRedStone(false, "redstonepiston_")).setMultiplicateur(8).setBlockName("RedStonePistonBase8");
		ModMorePistons.blockRedStoneStickyPistonBase8 = (new BlockMorePistonsRedStone(true , "redstonepiston_")).setMultiplicateur(8).setBlockName("RedStoneStickyPistonBase8");


		
		// Enregistrement des blocks
		GameRegistry.registerBlock(this.blockPistonExtension, this.blockPistonExtension.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockPistonRod      , this.blockPistonRod      .getUnlocalizedName());

		GameRegistry.registerBlock(this.blockGravitationalPistonBase      , this.blockGravitationalPistonBase      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockGravitationalStickyPistonBase, this.blockGravitationalStickyPistonBase.getUnlocalizedName());
		
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

		GameRegistry.registerBlock(this.blockSuperPistonBase               , this.blockSuperPistonBase               .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperStickyPistonBase         , this.blockSuperStickyPistonBase         .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperDoublePistonBase         , this.blockSuperDoublePistonBase         .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperDoubleStickyPistonBase   , this.blockSuperDoubleStickyPistonBase   .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperTriplePistonBase         , this.blockSuperTriplePistonBase         .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperTripleStickyPistonBase   , this.blockSuperTripleStickyPistonBase   .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperQuadruplePistonBase      , this.blockSuperQuadruplePistonBase      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperQuadrupleStickyPistonBase, this.blockSuperQuadrupleStickyPistonBase.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperQuintuplePistonBase      , this.blockSuperQuintuplePistonBase      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperQuintupleStickyPistonBase, this.blockSuperQuintupleStickyPistonBase.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperSextuplePistonBase       , this.blockSuperSextuplePistonBase       .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperSextupleStickyPistonBase , this.blockSuperSextupleStickyPistonBase .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperSeptuplePistonBase       , this.blockSuperSeptuplePistonBase       .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperSeptupleStickyPistonBase , this.blockSuperSeptupleStickyPistonBase .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperOctuplePistonBase        , this.blockSuperOctuplePistonBase        .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockSuperOctupleStickyPistonBase  , this.blockSuperOctupleStickyPistonBase  .getUnlocalizedName());
		
		GameRegistry.registerBlock(this.blockRedStonePistonBase1      , this.blockRedStonePistonBase1      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase1, this.blockRedStoneStickyPistonBase1.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase2      , this.blockRedStonePistonBase2      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase2, this.blockRedStoneStickyPistonBase2.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase3      , this.blockRedStonePistonBase3      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase3, this.blockRedStoneStickyPistonBase3.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase4      , this.blockRedStonePistonBase4      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase4, this.blockRedStoneStickyPistonBase4.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase5      , this.blockRedStonePistonBase5      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase5, this.blockRedStoneStickyPistonBase5.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase6      , this.blockRedStonePistonBase6      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase6, this.blockRedStoneStickyPistonBase6.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase7      , this.blockRedStonePistonBase7      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase7, this.blockRedStoneStickyPistonBase7.getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStonePistonBase8      , this.blockRedStonePistonBase8      .getUnlocalizedName());
		GameRegistry.registerBlock(this.blockRedStoneStickyPistonBase8, this.blockRedStoneStickyPistonBase8.getUnlocalizedName());
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
