package mm;


import static mm.ModConstants.MODID;
import static net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mm.init.ClientSetup;
import mm.init.CommonSetup;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(MODID)
public class MonoMagic {

	/**
	 * Log4j logger.
	 */
	static Logger logger = LogManager.getLogger();

	/**
	 * Random generator
	 */
	static Random random = new Random();

	/**
	 * Constructor.
	 */
	public MonoMagic() {
		logger.info("STARTING");

		// Get mod event bus
		IEventBus modEventBus = get().getModEventBus();

	    // Register the commonSetup method for modloading
    	modEventBus.addListener(CommonSetup::init);
			
		// perform client side initialization 
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::init);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);		
	}

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        logger.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            logger.info("HELLO FROM CLIENT SETUP");
            logger.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }


	/**
	 * Get logger.
	 * 
	 * @return logger.
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Get random generator.
	 * 
	 * @return random generator.
	 */
	public static Random getRandom() {
		return random;
	}


}
