package mm.init;

import static mm.MonoMagic.getLogger;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Class for common initialization.
 */
public class CommonSetup {

    /**
     * Perform common initialization, e.g. server and client
     */
    static public void init(final FMLCommonSetupEvent event) {            
        getLogger().info("init...");
    }
    
}
