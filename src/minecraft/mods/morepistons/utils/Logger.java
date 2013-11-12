package mods.morepistons.utils;

import java.lang.annotation.Annotation;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;

public class Logger {
	
	private static boolean _isInit = false;
	private static boolean _debug  = false;
	private static Class _mod;
	
	/**
	 * Initialise la class
	 */
	public static void init (Class mod, boolean debug) {
		_isInit  = true;
		_mod     = mod;
		_debug   = debug;
	}
	
	/**
	 * Affiche un log
	 * @param str
	 * @param force
	 */
	public static void log (Object obj, boolean force) {
		if (_isInit) {
			if (_debug || force) {
				FMLLog.log(_getModid (), Level.INFO, "", obj);
			}
		}
	}
	
	/**
	 * Affiche un log
	 * @param str
	 */
	public static void log (Object str) {
		log (str, false);
	}
	
	/**
	 * Renvoie le modID du MOD
	 * @return String
	 */
	private static String _getModid () {
		String modid = "Error";
		
		for (Annotation annotation : _mod.getAnnotations()) {
			if (annotation instanceof Mod) {
				modid = ((Mod)annotation).modid();
			}
		}
		
		return modid;
	}
	
}
