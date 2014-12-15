package mods.morepistons;

import mods.gollum.core.common.creativetab.GollumCreativeTabs;
import mods.gollum.core.common.i18n.I18n;
import mods.gollum.core.common.log.Logger;
import mods.gollum.core.common.mod.GollumMod;
import mods.gollum.core.common.version.VersionChecker;
import mods.morepistons.common.CommonProxyMorePistons;
import mods.morepistons.common.block.BlockMorePistonsBase;
import mods.morepistons.common.block.BlockMorePistonsExtension;
import mods.morepistons.common.block.BlockMorePistonsGravitational;
import mods.morepistons.common.block.BlockMorePistonsMagnetic;
import mods.morepistons.common.block.BlockMorePistonsRedStone;
import mods.morepistons.common.block.BlockMorePistonsRod;
import mods.morepistons.common.block.BlockMorePistonsSuper;
import mods.morepistons.common.config.ConfigMorePistons;
import mods.morepistons.common.tileentities.TileEntityMorePistons;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModMorePistons.MODID, name = ModMorePistons.MODNAME, version = ModMorePistons.VERSION, acceptedMinecraftVersions = ModMorePistons.MINECRAFT_VERSION, dependencies = ModMorePistons.DEPENDENCIES)
public class ModMorePistons extends GollumMod {

	public final static String MODID = "MorePistons";
	public final static String MODNAME = "More Pistons";
	public final static String VERSION = "2.0.0 Beta 1.0";
	public final static String MINECRAFT_VERSION = "1.7.10";
	public final static String DEPENDENCIES = "required-after:GollumCoreLib";
	
	@Instance(ModMorePistons.MODID)
	public static ModMorePistons instance;
	
	@SidedProxy(clientSide = "mods.morepistons.client.ClientProxyMorePistons", serverSide = "mods.morepistons.common.CommonProxyMorePistons")
	public static CommonProxyMorePistons proxy;

	/**
	 * Gestion des logs
	 */
	public static Logger log;
	
	/**
	 * Gestion de l'i18n
	 */
	public static I18n i18n;
	
	/**
	 * La configuration
	 */
	public static ConfigMorePistons config;
	
	/**
	 * Onglet du mod
	 */
	public static GollumCreativeTabs morePistonsTabs = new GollumCreativeTabs("Pistons");
	
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
	
	public static BlockMorePistonsMagnetic blockMagneticPistonBase1;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase1;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase2;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase2;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase3;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase3;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase4;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase4;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase5;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase5;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase6;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase6;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase7;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase7;
	public static BlockMorePistonsMagnetic blockMagneticPistonBase8;
	public static BlockMorePistonsMagnetic blockMagneticStickyPistonBase8;
	
	public static BlockMorePistonsExtension blockPistonExtension;
	public static BlockMorePistonsRod blockPistonRod;
	
	
	@EventHandler public void handler(FMLPreInitializationEvent event)  { super.handler (event); }
	@EventHandler public void handler(FMLInitializationEvent event)     { super.handler (event); }
	@EventHandler public void handler(FMLPostInitializationEvent event) { super.handler (event); }
	
	/** 1 **/
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		// Initialisation des blocks
		this.initBlocks ();
		
		//Test la version du mod
		new VersionChecker();
	}
	
	/** 2 **/
	@Override
	public void init(FMLInitializationEvent event) {
		
		// Initialisation du proxy
		proxy.registerRenderers();
		
		// Initialisation les TileEntities
		this.initTileEntities ();
		
		// Ajout des recettes
		this.initRecipes ();
		
		// Place le piston normal dans le creative tab
		Blocks.piston.setCreativeTab(morePistonsTabs);
		Blocks.sticky_piston.setCreativeTab(morePistonsTabs);
		
		// Set de l'icon du tab creative
		this.morePistonsTabs.setIcon(Blocks.piston);
	}
	
	/** 3 **/
	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	/**
	 * Initialisation des blocks
	 */
	public void initBlocks() {
		
		// Création des blocks
		this.blockPistonExtension = new BlockMorePistonsExtension("MorePistonsExtension");
		this.blockPistonRod       = new BlockMorePistonsRod      ("MorePistonsRod");
		
		this.blockGravitationalPistonBase       = new BlockMorePistonsGravitational("GravitationalPistonBase"      , false);
		this.blockGravitationalStickyPistonBase = new BlockMorePistonsGravitational("GravitationalStickyPistonBase", true);
		
		this.blockDoublePistonBase           = new BlockMorePistonsBase("DoublePistonBase"         , false).setLength (2);
		this.blockDoubleStickyPistonBase     = new BlockMorePistonsBase("DoubleStickyPistonBase"   , true ).setLength (2);
		this.blockTriplePistonBase           = new BlockMorePistonsBase("TriplePistonBase"         , false).setLength (3);
		this.blockTripleStickyPistonBase     = new BlockMorePistonsBase("TripleStickyPistonBase"   , true ).setLength (3);
		this.blockQuadruplePistonBase        = new BlockMorePistonsBase("QuadruplePistonBase"      , false).setLength (4);
		this.blockQuadrupleStickyPistonBase  = new BlockMorePistonsBase("QuadrupleStickyPistonBase", true ).setLength (4);
		this.blockQuintuplePistonBase        = new BlockMorePistonsBase("QuintuplePistonBase"      , false).setLength (5);
		this.blockQuintupleStickyPistonBase  = new BlockMorePistonsBase("QuintupleStickyPistonBase", true ).setLength (5);
		this.blockSextuplePistonBase         = new BlockMorePistonsBase("SextuplePistonBase"       , false).setLength (6);
		this.blockSextupleStickyPistonBase   = new BlockMorePistonsBase("SextupleStickyPistonBase" , true ).setLength (6);
		this.blockSeptuplePistonBase         = new BlockMorePistonsBase("SeptuplePistonBase"       , false).setLength (7);
		this.blockSeptupleStickyPistonBase   = new BlockMorePistonsBase("SeptupleStickyPistonBase" , true ).setLength (7);
		this.blockOctuplePistonBase          = new BlockMorePistonsBase("OctuplePistonBase"        , false).setLength (8);
		this.blockOctupleStickyPistonBase    = new BlockMorePistonsBase("OctupleStickyPistonBase"  , true ).setLength (8);
		
		this.blockSuperPistonBase                = new BlockMorePistonsSuper("SuperPistonBase"      , false);
		this.blockSuperStickyPistonBase          = new BlockMorePistonsSuper("SuperStickyPistonBase", true );
		
		this.blockSuperDoublePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperDoublePistonBase"         , false).setLength (2);
		this.blockSuperDoubleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperDoubleStickyPistonBase"   , true ).setLength (2);
		this.blockSuperTriplePistonBase          = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperTriplePistonBase"         , false).setLength (3);
		this.blockSuperTripleStickyPistonBase    = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperTripleStickyPistonBase"   , true ).setLength (3);
		this.blockSuperQuadruplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuadruplePistonBase"      , false).setLength (4);
		this.blockSuperQuadrupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuadrupleStickyPistonBase", true ).setLength (4);
		this.blockSuperQuintuplePistonBase       = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuintuplePistonBase"      , false).setLength (5);
		this.blockSuperQuintupleStickyPistonBase = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperQuintupleStickyPistonBase", true ).setLength (5);
		this.blockSuperSextuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSextuplePistonBase"       , false).setLength (6);
		this.blockSuperSextupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSextupleStickyPistonBase" , true ).setLength (6);
		this.blockSuperSeptuplePistonBase        = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSeptuplePistonBase"       , false).setLength (7);
		this.blockSuperSeptupleStickyPistonBase  = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperSeptupleStickyPistonBase" , true ).setLength (7);
		this.blockSuperOctuplePistonBase         = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperOctuplePistonBase"        , false).setLength (8);
		this.blockSuperOctupleStickyPistonBase   = (BlockMorePistonsSuper) new BlockMorePistonsSuper("SuperOctupleStickyPistonBase"  , true ).setLength (8);

		ModMorePistons.blockRedStonePistonBase1       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase1"      , false).setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStoneStickyPistonBase1 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase1", true ).setMultiplicateur(1).setCreativeTab(ModMorePistons.morePistonsTabs);
		ModMorePistons.blockRedStonePistonBase2       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase2"      , false).setMultiplicateur(2);
		ModMorePistons.blockRedStoneStickyPistonBase2 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase2", true ).setMultiplicateur(2);
		ModMorePistons.blockRedStonePistonBase3       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase3"      , false).setMultiplicateur(3);
		ModMorePistons.blockRedStoneStickyPistonBase3 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase3", true ).setMultiplicateur(3);
		ModMorePistons.blockRedStonePistonBase4       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase4"      , false).setMultiplicateur(4);
		ModMorePistons.blockRedStoneStickyPistonBase4 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase4", true ).setMultiplicateur(4);
		ModMorePistons.blockRedStonePistonBase5       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase5"      , false).setMultiplicateur(5);
		ModMorePistons.blockRedStoneStickyPistonBase5 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase5", true ).setMultiplicateur(5);
		ModMorePistons.blockRedStonePistonBase6       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase6"      , false).setMultiplicateur(6);
		ModMorePistons.blockRedStoneStickyPistonBase6 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase6", true ).setMultiplicateur(6);
		ModMorePistons.blockRedStonePistonBase7       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase7"      , false).setMultiplicateur(7);
		ModMorePistons.blockRedStoneStickyPistonBase7 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase7", true ).setMultiplicateur(7);
		ModMorePistons.blockRedStonePistonBase8       = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStonePistonBase8"      , false).setMultiplicateur(8);
		ModMorePistons.blockRedStoneStickyPistonBase8 = (BlockMorePistonsRedStone) new BlockMorePistonsRedStone("RedStoneStickyPistonBase8", true ).setMultiplicateur(8);
		
		ModMorePistons.blockMagneticPistonBase1       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticPistonBase1"               , false);
		ModMorePistons.blockMagneticStickyPistonBase1 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticStickyPistonBase1"         , true );
		ModMorePistons.blockMagneticPistonBase2       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticDoublePistonBase2"         , false).setLength(2);
		ModMorePistons.blockMagneticStickyPistonBase2 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticDoubleStickyPistonBase2"   , true ).setLength(2);
		ModMorePistons.blockMagneticPistonBase3       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticTriplePistonBase3"         , false).setLength(3);
		ModMorePistons.blockMagneticStickyPistonBase3 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticTripleStickyPistonBase3"   , true ).setLength(3);
		ModMorePistons.blockMagneticPistonBase4       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuadruplePistonBase4"      , false).setLength(4);
		ModMorePistons.blockMagneticStickyPistonBase4 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuadrupleStickyPistonBase4", true ).setLength(4);
		ModMorePistons.blockMagneticPistonBase5       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuintuplePistonBase5"      , false).setLength(5);
		ModMorePistons.blockMagneticStickyPistonBase5 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticQuintupleStickyPistonBase5", true ).setLength(5);
		ModMorePistons.blockMagneticPistonBase6       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSextuplePistonBase6"       , false).setLength(6);
		ModMorePistons.blockMagneticStickyPistonBase6 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSextupleStickyPistonBase6" , true ).setLength(6);
		ModMorePistons.blockMagneticPistonBase7       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSeptuplePistonBase7"       , false).setLength(7);
		ModMorePistons.blockMagneticStickyPistonBase7 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticSeptupleStickyPistonBase7" , true ).setLength(7);
		ModMorePistons.blockMagneticPistonBase8       = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticOctuplePistonBase8"        , false).setLength(8);
		ModMorePistons.blockMagneticStickyPistonBase8 = (BlockMorePistonsMagnetic) new BlockMorePistonsMagnetic("MagneticOctupleStickyPistonBase8"  , true ).setLength(8);
		
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
