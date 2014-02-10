package morePistons;

import java.io.Writer;
import java.text.SimpleDateFormat;

public class Monty_Util
{
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

    public Monty_Util(int var1, int var2, int var3, int var4, int var5, int var6)
    {
        this.x = var1;
        this.y = var2;
        this.z = var3;
        this.x2 = var4;
        this.y2 = var5;
        this.z2 = var6;
    }
}
