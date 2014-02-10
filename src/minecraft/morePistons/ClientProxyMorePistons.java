package morePistons;

import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxyMorePistons extends CommonProxyMorePistons
{
    public void registerRenderThings()
    {
        ModLoader.registerTileEntity(TileEntityMorePistons.class, "MorePistons");
        ModLoader.registerTileEntity(TileEntityMorePistons.class, "MorePistonsRenderer", new TileEntityMorePistonsRenderer());
        MinecraftForgeClient.preloadTexture("/morePistons/blocks/block_textures");
    }
}
