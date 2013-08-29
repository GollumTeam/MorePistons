package mods.morePistons.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLEncoder;
import java.util.EnumSet;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import argo.jdom.JdomParser;
import argo.jdom.JsonRootNode;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class VersionChecker extends Thread {
	
	public class EnterWorldHandler implements ITickHandler {
		
		private boolean nagged = false;;
		
		@Override
		public void tickStart(EnumSet<TickType> type, Object... tickData) {
		}

		@Override
		public void tickEnd(EnumSet<TickType> type, Object... tickData) {
			
			if (nagged) {
				return;
			}
			if (_message != null) {
				EntityPlayer player = (EntityPlayer) tickData[0];
				player.addChatMessage(EnumChatFormatting.YELLOW + _message);
			}
			nagged = true;
		}

		@Override
		public EnumSet<TickType> ticks() {
			return EnumSet.of(TickType.PLAYER);
		}

		@Override
		public String getLabel() {
			return _getModid()+" - Player update tick";
		}
	}
	
	public static String URL_CHECKER = "";
	private static VersionChecker _intance;

	private static Object _mod = null;
	private static String _message = null;
	private static String _type = "";
	
	public static VersionChecker getInstance () {
		if (_intance == null) {
			_intance = new VersionChecker();
		}
		return _intance;
	}
	
	protected VersionChecker () {}

	public void check (Object mod) {
		_mod = mod;
		TickRegistry.registerTickHandler(new EnterWorldHandler(), Side.CLIENT);
		start ();
	}
	
	/**
	 * Renvoie la version du MOD
	 * @return String
	 */
	private String _getVersion () {
		String version = "0.0.0 [DEV]";
		
		for (Annotation annotation : this._mod.getClass().getAnnotations()) {
			if (annotation instanceof Mod) {;
				version = ((Mod)annotation).version();
			}
		}
		
		return version;
	}
	
	/**
	 * Renvoie le modID du MOD
	 * @return String
	 */
	private String _getModid () {
		String modid = "Error";
		
		for (Annotation annotation : this._mod.getClass().getAnnotations()) {
			if (annotation instanceof Mod) {
				modid = ((Mod)annotation).modid();
			}
		}
		
		return modid;
	}
	
	/**
	 * Renvoie la version de Minecraft
	 * @return String
	 */
	private String _getMVersion () {
		return Loader.instance().getMinecraftModContainer().getVersion();
	}
	
	public void run () {
		
		String player = "MINECRAFT_SERVER";
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
			player = Minecraft.getMinecraft().func_110432_I ().func_111285_a ();
		}
		
		try
        {
			URL url = new URL ("http://minecraft-mods.elewendyl.fr/index.php/mmods/default/version?mod="+URLEncoder.encode(_getModid (), "UTF-8")+"&version="+URLEncoder.encode(_getVersion (), "UTF-8")+"&player="+URLEncoder.encode(player, "UTF-8")+"&mversion="+URLEncoder.encode(_getMVersion (), "UTF-8"));
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(url.openStream()));
			String strJSON = bufferedreader.readLine();
			
			JdomParser parser = new JdomParser();
			JsonRootNode root = parser.parse(strJSON);

			try { _message = root.getStringValue("message");  } catch (Exception exception) {}
			try { _type    = root.getStringValue("type");     } catch (Exception exception) {}
			
			if (_type.equals("info")) {
				FMLLog.log("VersionChecker "+_getModid (), Level.INFO, _message);
			} else {
				FMLLog.log("VersionChecker "+_getModid (), Level.WARNING, _message);
			}
			
			
        } catch (Exception exception) {
        	 exception.printStackTrace();
        }
	}
	
	
}
