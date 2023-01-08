package mm.util.proxy;

import mm.client.proxy.ClientProxy;
import mm.server.proxy.ServerProxy;
import net.minecraftforge.fml.DistExecutor;

/**
 * Implemntation of the {@linkplain Proxy} interface.
 * Used by the mod to resolved sided resources.
 */
public class DefaultProxy implements Proxy{
    
    /**
     * Sided proxy instance.
     */
    Proxy proxy; 

    /**
     * No-arg constructor.
     */
    DefaultProxy() {
        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

    @Override
    public String getUser() {
        return proxy.getUser();
    }

    @Override
    public void startAnalyticsSession() {
        proxy.startAnalyticsSession();        
    }
    
    /**
     * Factory method.
     * 
     * @return proxy instance.
     */
    public static Proxy getInstance() {
        return new DefaultProxy();
    }

}
