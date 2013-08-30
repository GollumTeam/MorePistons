package mods.morePistons.common;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;// any;

@Mod(modid = "More Pistons", name = "More Pistons", version="1.4.0 [Build Smeagol]", acceptedMinecraftVersions="1.5.2" )
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ModMorePistons {

	@Instance("ModMorePistons")
	public static ModMorePistons instance;
	
	@SidedProxy(clientSide = "mods.morePistons.common.ClientProxyMorePistons", serverSide = "mods.morePistons.common.CommonProxyMorePistons")
	public static CommonProxyMorePistons proxy;
	
	private static boolean DEBUG = false;
	
	public static boolean configFlag;
	
	public static MorePistonsTabs morePistonsTabs;
	
	public static int idBlockDoublePistonBase;
	public static int idBlockDoubleStickyPistonBase;
	public static int idBlockTriplePistonBase;
	public static int idBlockTripleStickyPistonBase;
	public static int idBlockQuadPistonBase;
	public static int idBlockQuadStickyPistonBase;
	public static int idBlockQuintPistonBase;
	public static int idBlockQuintStickyPistonBase;
	public static int idBlockSextPistonBase;
	public static int idBlockSextStickyPistonBase;
	public static int idBlockSeptPistonBase;
	public static int idBlockOctStickyPistonBase;
	public static int idBlockOctPistonBase;
	public static int idBlockSeptStickyPistonBase;
	public static int idBlockGravitationalPistonBase;
	public static int idBlockGravitationalStickyPistonBase;
	public static int idBlockPistonExtension;
	public static int idBlockPistonRod;
	public static int idBlockStrongPistonBase;
	public static int idBlockStrongStickyPistonBase;
	public static int idBlockRedStonePistonBase1;
	public static int idBlockRedStonePistonBase2;
	public static int idBlockRedStonePistonBase3;
	public static int idBlockRedStonePistonBase4;
	public static int idBlockRedStonePistonBase5;
	public static int idBlockRedStonePistonBase6;
	public static int idBlockRedStonePistonBase7;
	public static int idBlockRedStonePistonBase8;
	public static int idBlockRedStoneStickyPistonBase1;
	public static int idBlockRedStoneStickyPistonBase2;
	public static int idBlockRedStoneStickyPistonBase3;
	public static int idBlockRedStoneStickyPistonBase4;
	public static int idBlockRedStoneStickyPistonBase5;
	public static int idBlockRedStoneStickyPistonBase6;
	public static int idBlockRedStoneStickyPistonBase7;
	public static int idBlockRedStoneStickyPistonBase8;
	
	private static String texturePath = "morePistons:";



	
	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		
		config.load();
		
		idBlockDoublePistonBase              = config.getBlock("Double Piston", 210)              .getInt();
		idBlockDoubleStickyPistonBase        = config.getBlock("Double Sticky Piston", 211)       .getInt();
		idBlockTriplePistonBase              = config.getBlock("Triple Piston", 212)              .getInt();
		idBlockTripleStickyPistonBase        = config.getBlock("Triple Sticky Piston", 213)       .getInt();
		idBlockQuadPistonBase                = config.getBlock("Quadruple Piston", 214)           .getInt();
		idBlockQuadStickyPistonBase          = config.getBlock("Quadruple Sticky Piston", 215)    .getInt();
		idBlockGravitationalPistonBase       = config.getBlock("Gravitational Piston", 216)       .getInt();
		idBlockGravitationalStickyPistonBase = config.getBlock("Gravitational Sticky Piston", 223).getInt();
		idBlockPistonExtension               = config.getBlock("Piston Extension", 217)           .getInt();
		idBlockPistonRod                     = config.getBlock("Piston Rod", 218)                 .getInt();
		idBlockStrongPistonBase              = config.getBlock("Super Piston", 219)               .getInt();
		idBlockStrongStickyPistonBase        = config.getBlock("Super Sticky Piston", 220)        .getInt();
		idBlockQuintPistonBase               = config.getBlock("Quintuple Piston", 221)           .getInt();
		idBlockQuintStickyPistonBase         = config.getBlock("Quintuple Sticky Piston", 222)    .getInt();
		idBlockSextPistonBase                = config.getBlock("Sextuple Piston", 223)           .getInt();
		idBlockSextStickyPistonBase          = config.getBlock("Sextuple Sticky Piston", 224)    .getInt();
		idBlockSeptPistonBase                = config.getBlock("Septuple Piston", 225)           .getInt();
		idBlockSeptStickyPistonBase          = config.getBlock("Septuple Sticky Piston", 226)    .getInt();
		idBlockOctPistonBase                 = config.getBlock("Octuple Piston", 227)            .getInt();
		idBlockOctStickyPistonBase           = config.getBlock("Octuple Sticky Piston", 228)     .getInt();
		idBlockRedStonePistonBase1           = config.getBlock("Redstone Piston1", 229)           .getInt();
		idBlockRedStonePistonBase2           = config.getBlock("Redstone Piston2", 230)           .getInt();
		idBlockRedStonePistonBase3           = config.getBlock("Redstone Piston3", 231)           .getInt();
		idBlockRedStonePistonBase4           = config.getBlock("Redstone Piston4", 232)           .getInt();
		idBlockRedStonePistonBase5           = config.getBlock("Redstone Piston5", 233)           .getInt();
		idBlockRedStonePistonBase6           = config.getBlock("Redstone Piston6", 234)           .getInt();
		idBlockRedStonePistonBase7           = config.getBlock("Redstone Piston7", 235)           .getInt();
		idBlockRedStonePistonBase8           = config.getBlock("Redstone Piston8", 236)           .getInt();
		idBlockRedStoneStickyPistonBase1     = config.getBlock("Redstone Sticky Piston1", 237)    .getInt();
		idBlockRedStoneStickyPistonBase2     = config.getBlock("Redstone Sticky Piston2", 238)    .getInt();
		idBlockRedStoneStickyPistonBase3     = config.getBlock("Redstone Sticky Piston3", 239)    .getInt();
		idBlockRedStoneStickyPistonBase4     = config.getBlock("Redstone Sticky Piston4", 240)    .getInt();
		idBlockRedStoneStickyPistonBase5     = config.getBlock("Redstone Sticky Piston5", 241)    .getInt();
		idBlockRedStoneStickyPistonBase6     = config.getBlock("Redstone Sticky Piston6", 242)    .getInt();
		idBlockRedStoneStickyPistonBase7     = config.getBlock("Redstone Sticky Piston7", 243)    .getInt();
		idBlockRedStoneStickyPistonBase8     = config.getBlock("Redstone Sticky Piston8", 244)    .getInt();
		
		config.save();
	}
	
	/** 2 **/
	@Init
	public void load(FMLInitializationEvent event) {
		
		VersionChecker.getInstance().check(this);

		morePistonsTabs = new MorePistonsTabs("Pistons");
		LanguageRegistry.instance().addStringLocalization("itemGroup.Pistons", "en_US", "Pistons");
		
		proxy.registerRenderers();
		new MorePistonsBlocks();
		new MorePistons();
		new MorePistonsRecipes();
		
		morePistonsTabs.setIconItem(Block.pistonBase.blockID);
		Block.pistonBase.setCreativeTab(morePistonsTabs);
		Block.pistonStickyBase.setCreativeTab(morePistonsTabs);
		
		configFlag = false;
	}

	/** 3 **/
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}
	
	/**
	 * Renvoie la texture
	 * @param key
	 * @return 
	 */
	public static String getTexture (String key) {
		return texturePath+key;
	}
	
	/**
	 * Affiche un log
	 * @param str
	 */
	public static void log (String str) {
		if (DEBUG) {
			System.out.println (str);
		}
	}
	
	public static boolean isPistonId (int id) {
		return 
				id == Block.pistonBase.blockID ||
				id == Block.pistonStickyBase.blockID ||
				id == MorePistons.doublePistonBase.blockID ||
				id == MorePistons.doubleStickyPistonBase.blockID ||
				id == MorePistons.triplePistonBase.blockID ||
				id == MorePistons.tripleStickyPistonBase.blockID ||
				id == MorePistons.quadruplePistonBase.blockID ||
				id == MorePistons.quadrupleStickyPistonBase.blockID ||
				id == MorePistons.quintuplePistonBase.blockID ||
				id == MorePistons.quintupleStickyPistonBase.blockID ||
				id == MorePistons.sextuplePistonBase.blockID ||
				id == MorePistons.sextupleStickyPistonBase.blockID ||
				id == MorePistons.septuplePistonBase.blockID ||
				id == MorePistons.septupleStickyPistonBase.blockID ||
				id == MorePistons.octuplePistonBase.blockID ||
				id == MorePistons.octupleStickyPistonBase.blockID ||
				id == MorePistons.redStoneStickyPistonBase1.blockID ||
				id == MorePistons.redStoneStickyPistonBase2.blockID ||
				id == MorePistons.redStoneStickyPistonBase3.blockID ||
				id == MorePistons.redStoneStickyPistonBase4.blockID ||
				id == MorePistons.redStoneStickyPistonBase5.blockID ||
				id == MorePistons.redStoneStickyPistonBase6.blockID ||
				id == MorePistons.redStoneStickyPistonBase7.blockID ||
				id == MorePistons.redStoneStickyPistonBase8.blockID ||
				id == MorePistons.redStonePistonBase1.blockID ||
				id == MorePistons.redStonePistonBase2.blockID ||
				id == MorePistons.redStonePistonBase3.blockID ||
				id == MorePistons.redStonePistonBase4.blockID ||
				id == MorePistons.redStonePistonBase5.blockID ||
				id == MorePistons.redStonePistonBase6.blockID ||
				id == MorePistons.redStonePistonBase7.blockID ||
				id == MorePistons.redStonePistonBase8.blockID ||
				id == MorePistons.gravitationalPistonBase.blockID ||
				id == MorePistons.gravitationalStickyPistonBase.blockID ||
				id == MorePistons.superPistonBase.blockID ||
				id == MorePistons.superStickyPistonBase.blockID;
				
	}
	

	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4, int length) {
		return new TileEntityMorePistons(par0, par1, par2, par3, par4, length);
	}

	public static TileEntity getTileEntity(int par0, int par1, int par2, boolean par3, boolean par4, int length, boolean isRoot) {
		return new TileEntityMorePistons(par0, par1, par2, par3, par4, length, isRoot);
	}
	
}
