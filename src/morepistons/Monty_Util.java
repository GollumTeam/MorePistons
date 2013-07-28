package morepistons;

import java.io.Writer;
import java.text.SimpleDateFormat;

public class Monty_Util {
	private static SimpleDateFormat LOG_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	private static boolean trace = false;
	private static Writer traceWriter;
	public static int extraDisp0 = 0;
	public static int extraDisp = 1;
	public static int extraDisp2 = 2;
	public static int extraDisp3 = 3;
	
	public int x = 0;
	public int y = 0;
	public int z = 0;
	public int blockId = 0;
	public int metadata = 0;
	
	public int x2 = 0;
	public int y2 = 0;
	public int z2 = 0;
	public int blockId2 = 0;
	public int metadata2 = 0;
	
	public Monty_Util(int x, int y, int z) {
		this.x2 = x;
		this.y2 = y;
		this.z2 = z;
	}
	
	public Monty_Util(int x, int y, int z, int x2, int y2, int z2) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
	}
	
	public Monty_Util(int id, int x, int y, int z, int id2, int x2, int y2, int z2) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockId = id;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.blockId2 = id2;
	}
}