package mm.init;

import mm.MonoMagic;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Class for common initialization.
 */
public class CommonSetup {

    /**
     * Perform common initialization, e.g. server and client
     */
    static public void init(final FMLCommonSetupEvent event) {                
        MonoMagic.getLogger().info("init...");
    }
    
}
