package mods.morepistons.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader {
	
	private boolean updateFile = false;
	private File dir;
	private String fileName;
	private Class configClass;
	private LinkedList<Field> configFields;
	
	/**
	 * Constructeur
	 * @param clss
	 * @param dir
	 * @param fileName
	 */
	public ConfigLoader (Class clss, FMLPreInitializationEvent event) {
		
		this.dir = new File(ModLoader.getMinecraftInstance().mcDataDir, "config");
		
		if (!this.dir.exists()) {
			this.dir.mkdir();
		}
		
		this.configClass = clss;
		this.configFields = new LinkedList<Field>();
		this.fileName = event.getSuggestedConfigurationFile().getName();
		
		Field[] fields = this.configClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ConfigProp.class)) {
				this.configFields.add(field);
			}
		}
	}
	
	/**
	 * Charge la config
	 */
	public void loadConfig() {
		
		try {
			
			File configFile = new File(this.dir, this.fileName);
			HashMap<String, Field> types = new HashMap<String, Field>();
			
			for (Field field : this.configFields) {
				ConfigProp prop = (ConfigProp) field.getAnnotation(ConfigProp.class);
				types.put( ((!prop.name().isEmpty()) ? prop.name() : field.getName()), field);
			}
			
			HashMap<String, Field> properties;
			
			if (configFile.exists()) {
				
				properties = parseConfig(configFile, types);
				for (String prop : properties.keySet()) {
					Field field = (Field) types.get(prop);
					Object obj = properties.get(prop);
					if (!obj.equals(field.get(null))) {
						field.set(null, obj);
					}
				}
				
				for (String type : types.keySet()) {
					if (!properties.containsKey(type)) {
						this.updateFile = true;
					}
				}
				
			} else {
				this.updateFile = true;
			}
			
		} catch (Exception e) {
			this.updateFile = true;
			System.err.println(e.getMessage());
		}
		if (this.updateFile) {
			updateConfig();
		}
		this.updateFile = false;
	}
	
	/**
	 * Lit le fichier de config
	 * @param file
	 * @param types
	 * @return
	 * @throws Exception
	 */
	private HashMap parseConfig(File file, HashMap types) throws Exception {
		
		HashMap config = new HashMap();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String strLine;
		
		while ((strLine = reader.readLine()) != null) {
			
			if ((!strLine.startsWith("#")) && (strLine.length() != 0)) {
				
				int index = strLine.indexOf("=");
				
				if ((index <= 0) || (index == strLine.length())) {
					
					this.updateFile = true;
					
				} else {
					
					String name = strLine.substring(0, index);
					String prop = strLine.substring(index + 1);
					
					if (!types.containsKey(name)) {
						this.updateFile = true;
						
					} else {
						Object obj = null;
						Class class2 = ((Field) types.get(name)).getType();
						
						if (class2.isAssignableFrom(String.class)) { obj = prop;                                        } else
						if (class2.isAssignableFrom(Integer.TYPE)) { obj = Integer.valueOf(Integer.parseInt(prop));     } else
						if (class2.isAssignableFrom(Short.TYPE))   { obj = Short.valueOf(Short.parseShort(prop));       } else
						if (class2.isAssignableFrom(Byte.TYPE))    { obj = Byte.valueOf(Byte.parseByte(prop));          } else
						if (class2.isAssignableFrom(Boolean.TYPE)) { obj = Boolean.valueOf(Boolean.parseBoolean(prop)); } else
						if (class2.isAssignableFrom(Float.TYPE))   { obj = Float.valueOf(Float.parseFloat(prop));       } else
						if (class2.isAssignableFrom(Double.TYPE))  { obj = Double.valueOf(Double.parseDouble(prop));    }
						
						if (obj != null) {
							config.put(name, obj);
						}
					}
				}
			}
		}
		
		reader.close();
		return config;
	}
	
	/**
	 * Met à jour la config
	 */
	public void updateConfig() {
		
		File file = new File(this.dir, this.fileName);
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for (Field field : this.configFields) {
				
				ConfigProp prop = (ConfigProp) field .getAnnotation(ConfigProp.class);
				if (prop.info().length() != 0) {
					out.write("#" + prop.info() + System.getProperty("line.separator"));
				}
				
				String name = !prop.name().isEmpty() ? prop.name() : field.getName();
				
				try {
					
					out.write(name + "=" + field.get(null).toString() + System.getProperty("line.separator"));
					out.write(System.getProperty("line.separator"));
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}