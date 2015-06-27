package com.gollum.morepistons;

import com.gollum.core.ModGollumCoreLib;
import com.gollum.core.common.i18n.I18n;
import com.gollum.core.common.log.Logger;
import com.gollum.core.common.mod.GollumMod;
import com.gollum.core.common.version.VersionChecker;
import com.gollum.morepistons.common.CommonProxyMorePistons;
import com.gollum.morepistons.common.config.ConfigMorePistons;
import com.gollum.morepistons.inits.ModBlocks;
import com.gollum.morepistons.inits.ModCreativeTab;
import com.gollum.morepistons.inits.ModItems;
import com.gollum.morepistons.inits.ModRecipes;
import com.gollum.morepistons.inits.ModTileEntities;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid                     = ModMorePistons.MODID,
	name                      = ModMorePistons.MODNAME, 
	version                   = ModMorePistons.VERSION, 
	acceptedMinecraftVersions = ModMorePistons.MINECRAFT_VERSION, 
	dependencies              = ModMorePistons.DEPENDENCIES
)
public class ModMorePistons extends GollumMod {

	public final static String MODID = "MorePistons";
	public final static String MODNAME = "More Pistons";
	public final static String VERSION = "2.0.0DEV";
	public final static String MINECRAFT_VERSION = "1.7.10";
	public final static String DEPENDENCIES = "required-after:"+ModGollumCoreLib.MODID;
	
	@Instance(ModMorePistons.MODID)
	public static ModMorePistons instance;
	
	@SidedProxy(clientSide = "com.gollum.morepistons.client.ClientProxyMorePistons", serverSide = "com.gollum.morepistons.common.CommonProxyMorePistons")
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
	
	@EventHandler public void handler(FMLPreInitializationEvent event)  { super.handler (event); }
	@EventHandler public void handler(FMLInitializationEvent event)     { super.handler (event); }
	@EventHandler public void handler(FMLPostInitializationEvent event) { super.handler (event); }
	
	/** 1 **/
	@Override
	public void preInit(FMLPreInitializationEvent event) {

		// Initialisation des blocks
		ModItems.init();
		
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
		ModCreativeTab.init();
	}
	
	/** 3 **/
	@Override
	public void postInit(FMLPostInitializationEvent event) {
	}
	
}
