package mods.morepistons;

import mods.gollum.core.common.creativetab.GollumCreativeTabs;
import mods.gollum.core.common.i18n.I18n;
import mods.gollum.core.common.log.Logger;
import mods.gollum.core.common.mod.GollumMod;
import mods.gollum.core.common.version.VersionChecker;
import mods.morepistons.common.CommonProxyMorePistons;
import mods.morepistons.common.config.ConfigMorePistons;
import mods.morepistons.inits.ModBlocks;
import mods.morepistons.inits.ModRecipes;
import mods.morepistons.inits.ModTileEntities;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

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
	
	@EventHandler public void handler(FMLPreInitializationEvent event)  { super.handler (event); }
	@EventHandler public void handler(FMLInitializationEvent event)     { super.handler (event); }
	@EventHandler public void handler(FMLPostInitializationEvent event) { super.handler (event); }
	
	/** 1 **/
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		// Initialisation des blocks
		ModBlocks.init();
		
		//Test la version du mod
		new VersionChecker();
	}
	
	/** 2 **/
	@Override
	public void init(FMLInitializationEvent event) {
		
		// Initialisation du proxy
		proxy.registerRenderers();
		
		// Initialisation les TileEntities
		ModTileEntities.init ();
		
		// Ajout des recettes
		ModRecipes.init ();
		
		// Set de l'icon du tab creative
		this.morePistonsTabs.setIcon(Blocks.piston);
		Blocks.piston       .setCreativeTab(null);
		Blocks.sticky_piston.setCreativeTab(null);
	}
	
	/** 3 **/
	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
	
}
