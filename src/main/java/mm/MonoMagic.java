package mm;


import static mm.ModConstants.MODID;
import static net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mm.init.ClientSetup;
import mm.init.CommonSetup;
import mm.util.analytics.Analytics;
import mm.util.analytics.DefaultAnalytics;
import mm.util.proxy.DefaultProxy;
import mm.util.proxy.Proxy;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
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
	
	/*
	 * Mod analytics.
	 */
	static Analytics analytics = DefaultAnalytics.getInstance();

	/*
	 * Mod proxy.
	 */
	static Proxy proxy = DefaultProxy.getInstance();

	/**
	 * Minecraft server.
	 */
	static MinecraftServer server;
		
	/**
	 * Mod instance.
	 */
	static MonoMagic instance;

	/**
	 * No-arg constructor.
	 */
	public MonoMagic() {

		// store instance
		instance = this;

        // Register ourselves for forge events
        MinecraftForge.EVENT_BUS.register(this);		

		// Get mod event bus
		IEventBus modEventBus = get().getModEventBus();

	    // Register the commonSetup method for modloading
    	modEventBus.addListener(CommonSetup::init);
	
		// perform client side initialization 
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::init);
	}

	@SubscribeEvent
	void serverAboutTostart(ServerAboutToStartEvent event) {
		server = event.getServer();
	}

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            //logger.info("HELLO FROM CLIENT SETUP");
            //logger.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
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
	public Random getRandom() {
		return random;
	}

	/**
	 * Get analytics.
	 * 
	 * @return analytics.
	 */
	public Analytics getAnalytics() {
		return analytics;
	}

	/**
	 * Get sided proxy.
	 * 
	 * @return sided proxy.
	 */
	public Proxy getProxy() {
		return proxy;
	}

	/**
	 * Get server.
	 * 
	 * @return server. Can be null in physical client or if an error happens before
	 *         the logical server is started.
	 */
	public Optional<MinecraftServer> getServer() {
		return ofNullable(server);
	}	
	/**
	 * Get mod instance.
	 * 
	 * @return mod instance.
	 */
	public static MonoMagic getMod() {
		return instance;
	}
}
